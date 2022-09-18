package jdk.internal.dynalink.beans;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/beans/SimpleDynamicMethod.class */
class SimpleDynamicMethod extends SingleDynamicMethod {
    private final MethodHandle target;
    private final boolean constructor;

    public SimpleDynamicMethod(MethodHandle target, Class<?> clazz, String name) {
        this(target, clazz, name, false);
    }

    public SimpleDynamicMethod(MethodHandle target, Class<?> clazz, String name, boolean constructor) {
        super(getName(target, clazz, name, constructor));
        this.target = target;
        this.constructor = constructor;
    }

    private static String getName(MethodHandle target, Class<?> clazz, String name, boolean constructor) {
        return getMethodNameWithSignature(target.type(), constructor ? name : getClassAndMethodName(clazz, name), !constructor);
    }

    @Override // jdk.internal.dynalink.beans.SingleDynamicMethod
    public boolean isVarArgs() {
        return this.target.isVarargsCollector();
    }

    @Override // jdk.internal.dynalink.beans.SingleDynamicMethod
    public MethodType getMethodType() {
        return this.target.type();
    }

    @Override // jdk.internal.dynalink.beans.SingleDynamicMethod
    public MethodHandle getTarget(MethodHandles.Lookup lookup) {
        return this.target;
    }

    @Override // jdk.internal.dynalink.beans.DynamicMethod
    public boolean isConstructor() {
        return this.constructor;
    }
}
