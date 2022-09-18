package jdk.nashorn.internal.runtime;

import java.lang.invoke.MethodHandle;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.lookup.MethodHandleFunctionality;
import jdk.nashorn.internal.objects.Global;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/FindProperty.class */
public final class FindProperty {
    private final ScriptObject self;
    private final ScriptObject prototype;
    private final Property property;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !FindProperty.class.desiredAssertionStatus();
    }

    public FindProperty(ScriptObject self, ScriptObject prototype, Property property) {
        this.self = self;
        this.prototype = prototype;
        this.property = property;
    }

    public FindProperty replaceProperty(Property newProperty) {
        if ($assertionsDisabled || this.property.getKey().equals(newProperty.getKey())) {
            if (!$assertionsDisabled && this.property.getSlot() != newProperty.getSlot()) {
                throw new AssertionError();
            }
            return new FindProperty(this.self, this.prototype, newProperty);
        }
        throw new AssertionError();
    }

    public MethodHandle getGetter(Class<?> type, int programPoint, LinkRequest request) {
        MethodHandle getter;
        if (UnwarrantedOptimismException.isValid(programPoint)) {
            getter = this.property.getOptimisticGetter(type, programPoint);
        } else {
            getter = this.property.getGetter(type);
        }
        if (this.property instanceof UserAccessorProperty) {
            MethodHandle getter2 = Lookup.f248MH.insertArguments(getter, 1, UserAccessorProperty.getINVOKE_UA_GETTER(type, programPoint));
            if (UnwarrantedOptimismException.isValid(programPoint) && type.isPrimitive()) {
                getter2 = Lookup.f248MH.insertArguments(getter2, 1, Integer.valueOf(programPoint));
            }
            this.property.setType(type);
            return insertAccessorsGetter((UserAccessorProperty) this.property, request, getter2);
        }
        return getter;
    }

    public MethodHandle getSetter(Class<?> type, boolean strict, LinkRequest request) {
        MethodHandle setter = this.property.getSetter(type, getOwner().getMap());
        if (this.property instanceof UserAccessorProperty) {
            MethodHandleFunctionality methodHandleFunctionality = Lookup.f248MH;
            Object[] objArr = new Object[2];
            objArr[0] = UserAccessorProperty.getINVOKE_UA_SETTER(type);
            objArr[1] = strict ? this.property.getKey() : null;
            MethodHandle setter2 = methodHandleFunctionality.insertArguments(setter, 1, objArr);
            this.property.setType(type);
            return insertAccessorsGetter((UserAccessorProperty) this.property, request, setter2);
        }
        return setter;
    }

    private MethodHandle insertAccessorsGetter(UserAccessorProperty uap, LinkRequest request, MethodHandle mh) {
        MethodHandle superGetter = uap.getAccessorsGetter();
        if (isInherited()) {
            superGetter = ScriptObject.addProtoFilter(superGetter, getProtoChainLength());
        }
        if (request != null && !(request.getReceiver() instanceof ScriptObject)) {
            MethodHandle wrapFilter = Global.getPrimitiveWrapFilter(request.getReceiver());
            superGetter = Lookup.f248MH.filterArguments(superGetter, 0, wrapFilter.asType(wrapFilter.type().changeReturnType(superGetter.type().parameterType(0))));
        }
        return Lookup.f248MH.foldArguments(mh, Lookup.f248MH.asType(superGetter, superGetter.type().changeParameterType(0, Object.class)));
    }

    public ScriptObject getOwner() {
        return this.prototype;
    }

    public ScriptObject getSelf() {
        return this.self;
    }

    public ScriptObject getGetterReceiver() {
        return (this.property == null || !(this.property instanceof UserAccessorProperty)) ? this.prototype : this.self;
    }

    public ScriptObject getSetterReceiver() {
        return (this.property == null || !this.property.hasSetterFunction(this.prototype)) ? this.prototype : this.self;
    }

    public Property getProperty() {
        return this.property;
    }

    public boolean isInherited() {
        return this.self != this.prototype;
    }

    public boolean isSelf() {
        return this.self == this.prototype;
    }

    public boolean isScope() {
        return this.prototype.isScope();
    }

    public int getIntValue() {
        return this.property.getIntValue(getGetterReceiver(), getOwner());
    }

    public double getDoubleValue() {
        return this.property.getDoubleValue(getGetterReceiver(), getOwner());
    }

    public Object getObjectValue() {
        return this.property.getObjectValue(getGetterReceiver(), getOwner());
    }

    public void setValue(int value, boolean strict) {
        this.property.setValue(getSetterReceiver(), getOwner(), value, strict);
    }

    public void setValue(double value, boolean strict) {
        this.property.setValue(getSetterReceiver(), getOwner(), value, strict);
    }

    public void setValue(Object value, boolean strict) {
        this.property.setValue(getSetterReceiver(), getOwner(), value, strict);
    }

    public int getProtoChainLength() {
        if ($assertionsDisabled || this.self != null) {
            int length = 0;
            ScriptObject scriptObject = this.self;
            while (true) {
                ScriptObject obj = scriptObject;
                if (obj != this.prototype) {
                    if (!$assertionsDisabled && (obj instanceof WithObject)) {
                        throw new AssertionError();
                    }
                    length++;
                    scriptObject = obj.getProto();
                } else {
                    return length;
                }
            }
        } else {
            throw new AssertionError();
        }
    }

    public String toString() {
        return "[FindProperty: " + this.property.getKey() + ']';
    }
}
