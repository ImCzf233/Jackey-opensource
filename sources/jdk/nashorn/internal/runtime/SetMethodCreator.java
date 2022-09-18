package jdk.nashorn.internal.runtime;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.SwitchPoint;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.linker.NashornCallSiteDescriptor;
import jdk.nashorn.internal.runtime.linker.NashornGuards;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/SetMethodCreator.class */
public final class SetMethodCreator {
    private final ScriptObject sobj;
    private final PropertyMap map;
    private final FindProperty find;
    private final CallSiteDescriptor desc;
    private final Class<?> type;
    private final LinkRequest request;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !SetMethodCreator.class.desiredAssertionStatus();
    }

    public SetMethodCreator(ScriptObject sobj, FindProperty find, CallSiteDescriptor desc, LinkRequest request) {
        this.sobj = sobj;
        this.map = sobj.getMap();
        this.find = find;
        this.desc = desc;
        this.type = desc.getMethodType().parameterType(1);
        this.request = request;
    }

    private String getName() {
        return this.desc.getNameToken(2);
    }

    private PropertyMap getMap() {
        return this.map;
    }

    public GuardedInvocation createGuardedInvocation(SwitchPoint builtinSwitchPoint) {
        return createSetMethod(builtinSwitchPoint).createGuardedInvocation();
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/SetMethodCreator$SetMethod.class */
    public class SetMethod {
        private final MethodHandle methodHandle;
        private final Property property;
        static final /* synthetic */ boolean $assertionsDisabled;

        static {
            $assertionsDisabled = !SetMethodCreator.class.desiredAssertionStatus();
        }

        SetMethod(MethodHandle methodHandle, Property property) {
            SetMethodCreator.this = r4;
            if ($assertionsDisabled || methodHandle != null) {
                this.methodHandle = methodHandle;
                this.property = property;
                return;
            }
            throw new AssertionError();
        }

        GuardedInvocation createGuardedInvocation() {
            boolean explicitInstanceOfCheck = NashornGuards.explicitInstanceOfCheck(SetMethodCreator.this.desc, SetMethodCreator.this.request);
            return new GuardedInvocation(this.methodHandle, NashornGuards.getGuard(SetMethodCreator.this.sobj, this.property, SetMethodCreator.this.desc, explicitInstanceOfCheck), (SwitchPoint) null, explicitInstanceOfCheck ? null : ClassCastException.class);
        }
    }

    private SetMethod createSetMethod(SwitchPoint builtinSwitchPoint) {
        if (this.find != null) {
            return createExistingPropertySetter();
        }
        checkStrictCreateNewVariable();
        if (this.sobj.isScope()) {
            return createGlobalPropertySetter();
        }
        return createNewPropertySetter(builtinSwitchPoint);
    }

    private void checkStrictCreateNewVariable() {
        if (NashornCallSiteDescriptor.isScope(this.desc) && NashornCallSiteDescriptor.isStrict(this.desc)) {
            throw ECMAErrors.referenceError("not.defined", getName());
        }
    }

    private SetMethod createExistingPropertySetter() {
        MethodHandle methodHandle;
        MethodHandle boundHandle;
        Property property = this.find.getProperty();
        boolean isStrict = NashornCallSiteDescriptor.isStrict(this.desc);
        if (NashornCallSiteDescriptor.isDeclaration(this.desc)) {
            if (!$assertionsDisabled && !property.needsDeclaration()) {
                throw new AssertionError();
            }
            PropertyMap oldMap = getMap();
            Property newProperty = property.removeFlags(512);
            PropertyMap newMap = oldMap.replaceProperty(property, newProperty);
            MethodHandle fastSetter = this.find.replaceProperty(newProperty).getSetter(this.type, isStrict, this.request);
            MethodHandle slowSetter = Lookup.f248MH.insertArguments(ScriptObject.DECLARE_AND_SET, 1, getName()).asType(fastSetter.type());
            MethodHandle casMap = Lookup.f248MH.dropArguments(Lookup.f248MH.insertArguments(ScriptObject.CAS_MAP, 1, oldMap, newMap), 1, this.type);
            methodHandle = Lookup.f248MH.guardWithTest(Lookup.f248MH.asType(casMap, casMap.type().changeParameterType(0, Object.class)), fastSetter, slowSetter);
        } else {
            methodHandle = this.find.getSetter(this.type, isStrict, this.request);
        }
        if ($assertionsDisabled || methodHandle != null) {
            if (!$assertionsDisabled && property == null) {
                throw new AssertionError();
            }
            if (!(property instanceof UserAccessorProperty) && this.find.isInherited()) {
                boundHandle = ScriptObject.addProtoFilter(methodHandle, this.find.getProtoChainLength());
            } else {
                boundHandle = methodHandle;
            }
            return new SetMethod(boundHandle, property);
        }
        throw new AssertionError();
    }

    private SetMethod createGlobalPropertySetter() {
        ScriptObject global = Context.getGlobal();
        return new SetMethod(Lookup.f248MH.filterArguments(global.addSpill(this.type, getName()), 0, ScriptObject.GLOBALFILTER), null);
    }

    private SetMethod createNewPropertySetter(SwitchPoint builtinSwitchPoint) {
        SetMethod sm = this.map.getFreeFieldSlot() > -1 ? createNewFieldSetter(builtinSwitchPoint) : createNewSpillPropertySetter(builtinSwitchPoint);
        this.map.propertyAdded(sm.property, true);
        return sm;
    }

    private SetMethod createNewSetter(Property property, SwitchPoint builtinSwitchPoint) {
        property.setBuiltinSwitchPoint(builtinSwitchPoint);
        PropertyMap oldMap = getMap();
        PropertyMap newMap = getNewMap(property);
        boolean isStrict = NashornCallSiteDescriptor.isStrict(this.desc);
        String name = this.desc.getNameToken(2);
        MethodHandle fastSetter = property.getSetter(this.type, newMap);
        MethodHandle slowSetter = ScriptObject.SET_SLOW[JSType.getAccessorTypeIndex(this.type)];
        MethodHandle slowSetter2 = Lookup.f248MH.insertArguments(Lookup.f248MH.insertArguments(slowSetter, 3, Integer.valueOf(NashornCallSiteDescriptor.getFlags(this.desc))), 1, name);
        MethodHandle slowSetter3 = Lookup.f248MH.asType(slowSetter2, slowSetter2.type().changeParameterType(0, Object.class));
        if ($assertionsDisabled || slowSetter3.type().equals(fastSetter.type())) {
            MethodHandle casMap = Lookup.f248MH.dropArguments(Lookup.f248MH.insertArguments(ScriptObject.CAS_MAP, 1, oldMap, newMap), 1, this.type);
            MethodHandle casGuard = Lookup.f248MH.guardWithTest(Lookup.f248MH.asType(casMap, casMap.type().changeParameterType(0, Object.class)), fastSetter, slowSetter3);
            MethodHandle extCheck = Lookup.f248MH.insertArguments(ScriptObject.EXTENSION_CHECK, 1, Boolean.valueOf(isStrict), name);
            MethodHandle extCheck2 = Lookup.f248MH.dropArguments(Lookup.f248MH.asType(extCheck, extCheck.type().changeParameterType(0, Object.class)), 1, this.type);
            MethodHandle nop = JSType.VOID_RETURN.methodHandle();
            return new SetMethod(Lookup.f248MH.asType(Lookup.f248MH.guardWithTest(extCheck2, casGuard, Lookup.f248MH.dropArguments(nop, 0, Object.class, this.type)), fastSetter.type()), property);
        }
        throw new AssertionError("slow=" + slowSetter3 + " != fast=" + fastSetter);
    }

    private SetMethod createNewFieldSetter(SwitchPoint builtinSwitchPoint) {
        return createNewSetter(new AccessorProperty(getName(), getFlags(this.sobj), this.sobj.getClass(), getMap().getFreeFieldSlot(), this.type), builtinSwitchPoint);
    }

    private SetMethod createNewSpillPropertySetter(SwitchPoint builtinSwitchPoint) {
        return createNewSetter(new SpillProperty(getName(), getFlags(this.sobj), getMap().getFreeSpillSlot(), this.type), builtinSwitchPoint);
    }

    private PropertyMap getNewMap(Property property) {
        return getMap().addProperty(property);
    }

    private static int getFlags(ScriptObject scriptObject) {
        return scriptObject.useDualFields() ? 2048 : 0;
    }
}
