package jdk.internal.dynalink.beans;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import jdk.internal.dynalink.beans.GuardedInvocationComponent;
import jdk.internal.dynalink.support.Lookup;

/* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/beans/ClassLinker.class */
class ClassLinker extends BeanLinker {
    private static final MethodHandle FOR_CLASS = new Lookup(MethodHandles.lookup()).findStatic(StaticClass.class, "forClass", MethodType.methodType(StaticClass.class, Class.class));

    public ClassLinker() {
        super(Class.class);
        setPropertyGetter("static", FOR_CLASS, GuardedInvocationComponent.ValidationType.EXACT_CLASS);
    }
}
