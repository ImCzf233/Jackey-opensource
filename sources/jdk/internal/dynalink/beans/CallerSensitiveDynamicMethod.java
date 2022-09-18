package jdk.internal.dynalink.beans;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import jdk.internal.dynalink.support.Lookup;

/* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/beans/CallerSensitiveDynamicMethod.class */
class CallerSensitiveDynamicMethod extends SingleDynamicMethod {
    private final AccessibleObject target;
    private final MethodType type;

    public CallerSensitiveDynamicMethod(AccessibleObject target) {
        super(getName(target));
        this.target = target;
        this.type = getMethodType(target);
    }

    private static String getName(AccessibleObject target) {
        Member m = (Member) target;
        boolean constructor = m instanceof Constructor;
        return getMethodNameWithSignature(getMethodType(target), constructor ? m.getName() : getClassAndMethodName(m.getDeclaringClass(), m.getName()), !constructor);
    }

    @Override // jdk.internal.dynalink.beans.SingleDynamicMethod
    public MethodType getMethodType() {
        return this.type;
    }

    private static MethodType getMethodType(AccessibleObject ao) {
        Class cls;
        boolean isMethod = ao instanceof Method;
        Class<?> rtype = isMethod ? ((Method) ao).getReturnType() : ((Constructor) ao).getDeclaringClass();
        Class<?>[] ptypes = isMethod ? ((Method) ao).getParameterTypes() : ((Constructor) ao).getParameterTypes();
        MethodType type = MethodType.methodType(rtype, ptypes);
        Member m = (Member) ao;
        Class<?>[] clsArr = new Class[1];
        if (isMethod) {
            cls = Modifier.isStatic(m.getModifiers()) ? Object.class : m.getDeclaringClass();
        } else {
            cls = StaticClass.class;
        }
        clsArr[0] = cls;
        return type.insertParameterTypes(0, clsArr);
    }

    @Override // jdk.internal.dynalink.beans.SingleDynamicMethod
    public boolean isVarArgs() {
        return this.target instanceof Method ? ((Method) this.target).isVarArgs() : ((Constructor) this.target).isVarArgs();
    }

    @Override // jdk.internal.dynalink.beans.SingleDynamicMethod
    public MethodHandle getTarget(MethodHandles.Lookup lookup) {
        if (this.target instanceof Method) {
            MethodHandle mh = Lookup.unreflect(lookup, (Method) this.target);
            if (Modifier.isStatic(((Member) this.target).getModifiers())) {
                return StaticClassIntrospector.editStaticMethodHandle(mh);
            }
            return mh;
        }
        return StaticClassIntrospector.editConstructorMethodHandle(Lookup.unreflectConstructor(lookup, (Constructor) this.target));
    }

    @Override // jdk.internal.dynalink.beans.DynamicMethod
    public boolean isConstructor() {
        return this.target instanceof Constructor;
    }
}
