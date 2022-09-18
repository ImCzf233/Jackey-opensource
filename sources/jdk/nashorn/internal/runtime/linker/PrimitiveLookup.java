package jdk.nashorn.internal.runtime.linker;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.SwitchPoint;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.support.CallSiteDescriptorFactory;
import jdk.internal.dynalink.support.Guards;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.FindProperty;
import jdk.nashorn.internal.runtime.GlobalConstants;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.UserAccessorProperty;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/linker/PrimitiveLookup.class */
public final class PrimitiveLookup {
    private static final MethodHandle PRIMITIVE_SETTER;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !PrimitiveLookup.class.desiredAssertionStatus();
        PRIMITIVE_SETTER = findOwnMH("primitiveSetter", Lookup.f248MH.type(Void.TYPE, ScriptObject.class, Object.class, Object.class, Boolean.TYPE, Object.class));
    }

    private PrimitiveLookup() {
    }

    public static GuardedInvocation lookupPrimitive(LinkRequest request, Class<?> receiverClass, ScriptObject wrappedReceiver, MethodHandle wrapFilter, MethodHandle protoFilter) {
        return lookupPrimitive(request, Guards.getInstanceOfGuard(receiverClass), wrappedReceiver, wrapFilter, protoFilter);
    }

    public static GuardedInvocation lookupPrimitive(LinkRequest request, MethodHandle guard, ScriptObject wrappedReceiver, MethodHandle wrapFilter, MethodHandle protoFilter) {
        FindProperty find;
        String name;
        CallSiteDescriptor desc = request.getCallSiteDescriptor();
        if (desc.getNameTokenCount() > 2) {
            name = desc.getNameToken(2);
            find = wrappedReceiver.findProperty(name, true);
        } else {
            name = null;
            find = null;
        }
        String firstOp = CallSiteDescriptorFactory.tokenizeOperators(desc).get(0);
        boolean z = true;
        switch (firstOp.hashCode()) {
            case -75566075:
                if (firstOp.equals("getElem")) {
                    z = true;
                    break;
                }
                break;
            case -75232295:
                if (firstOp.equals("getProp")) {
                    z = false;
                    break;
                }
                break;
            case 618460119:
                if (firstOp.equals("getMethod")) {
                    z = true;
                    break;
                }
                break;
            case 1984543505:
                if (firstOp.equals("setElem")) {
                    z = true;
                    break;
                }
                break;
            case 1984877285:
                if (firstOp.equals("setProp")) {
                    z = true;
                    break;
                }
                break;
        }
        switch (z) {
            case true:
            case true:
                return getPrimitiveSetter(name, guard, wrapFilter, NashornCallSiteDescriptor.isStrict(desc));
            case false:
            case true:
            case true:
                if (name != null) {
                    if (find == null) {
                        return null;
                    }
                    SwitchPoint sp = find.getProperty().getBuiltinSwitchPoint();
                    if ((sp instanceof Context.BuiltinSwitchPoint) && !sp.hasBeenInvalidated()) {
                        return new GuardedInvocation(GlobalConstants.staticConstantGetter(find.getObjectValue()), guard, sp, (Class<? extends Throwable>) null);
                    }
                    if (find.isInherited() && !(find.getProperty() instanceof UserAccessorProperty)) {
                        ScriptObject proto = wrappedReceiver.getProto();
                        GuardedInvocation link = proto.lookup(desc, request);
                        if (link != null) {
                            MethodHandle invocation = link.getInvocation();
                            MethodHandle adaptedInvocation = Lookup.f248MH.asType(invocation, invocation.type().changeParameterType(0, Object.class));
                            MethodHandle method = Lookup.f248MH.filterArguments(adaptedInvocation, 0, protoFilter);
                            MethodHandle protoGuard = Lookup.f248MH.filterArguments(link.getGuard(), 0, protoFilter);
                            return new GuardedInvocation(method, NashornGuards.combineGuards(guard, protoGuard));
                        }
                    }
                }
                break;
        }
        GuardedInvocation link2 = wrappedReceiver.lookup(desc, request);
        if (link2 != null) {
            MethodHandle method2 = link2.getInvocation();
            Class<?> receiverType = method2.type().parameterType(0);
            if (receiverType != Object.class) {
                MethodType wrapType = wrapFilter.type();
                if (!$assertionsDisabled && !receiverType.isAssignableFrom(wrapType.returnType())) {
                    throw new AssertionError();
                }
                method2 = Lookup.f248MH.filterArguments(method2, 0, Lookup.f248MH.asType(wrapFilter, wrapType.changeReturnType(receiverType)));
            }
            return new GuardedInvocation(method2, guard, link2.getSwitchPoints(), (Class<? extends Throwable>) null);
        }
        return null;
    }

    private static GuardedInvocation getPrimitiveSetter(String name, MethodHandle guard, MethodHandle wrapFilter, boolean isStrict) {
        MethodHandle target;
        MethodHandle filter;
        MethodHandle filter2 = Lookup.f248MH.asType(wrapFilter, wrapFilter.type().changeReturnType(ScriptObject.class));
        if (name == null) {
            filter = Lookup.f248MH.dropArguments(filter2, 1, Object.class, Object.class);
            target = Lookup.f248MH.insertArguments(PRIMITIVE_SETTER, 3, Boolean.valueOf(isStrict));
        } else {
            filter = Lookup.f248MH.dropArguments(filter2, 1, Object.class);
            target = Lookup.f248MH.insertArguments(PRIMITIVE_SETTER, 2, name, Boolean.valueOf(isStrict));
        }
        return new GuardedInvocation(Lookup.f248MH.foldArguments(target, filter), guard);
    }

    private static void primitiveSetter(ScriptObject wrappedSelf, Object self, Object key, boolean strict, Object value) {
        String name = JSType.toString(key);
        FindProperty find = wrappedSelf.findProperty(name, true);
        if (find == null || !(find.getProperty() instanceof UserAccessorProperty) || !find.getProperty().isWritable()) {
            if (strict) {
                throw ECMAErrors.typeError("property.not.writable", name, ScriptRuntime.safeToString(self));
            }
            return;
        }
        find.setValue(value, strict);
    }

    private static MethodHandle findOwnMH(String name, MethodType type) {
        return Lookup.f248MH.findStatic(MethodHandles.lookup(), PrimitiveLookup.class, name, type);
    }
}
