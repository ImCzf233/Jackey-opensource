package jdk.internal.dynalink.beans;

import java.lang.invoke.MethodHandle;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.LinkerServices;

/* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/beans/DynamicMethod.class */
public abstract class DynamicMethod {
    private final String name;

    public abstract MethodHandle getInvocation(CallSiteDescriptor callSiteDescriptor, LinkerServices linkerServices);

    public abstract SingleDynamicMethod getMethodForExactParamTypes(String str);

    public abstract boolean contains(SingleDynamicMethod singleDynamicMethod);

    public DynamicMethod(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static String getClassAndMethodName(Class<?> clazz, String name) {
        String clazzName = clazz.getCanonicalName();
        return (clazzName == null ? clazz.getName() : clazzName) + "." + name;
    }

    public String toString() {
        return "[" + getClass().getName() + " " + getName() + "]";
    }

    public boolean isConstructor() {
        return false;
    }
}
