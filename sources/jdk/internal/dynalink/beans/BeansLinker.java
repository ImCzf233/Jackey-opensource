package jdk.internal.dynalink.beans;

import java.util.Collection;
import java.util.Collections;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.GuardingDynamicLinker;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker;

/* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/beans/BeansLinker.class */
public class BeansLinker implements GuardingDynamicLinker {
    private static final ClassValue<TypeBasedGuardingDynamicLinker> linkers = new ClassValue<TypeBasedGuardingDynamicLinker>() { // from class: jdk.internal.dynalink.beans.BeansLinker.1
        protected TypeBasedGuardingDynamicLinker computeValue(Class<?> clazz) {
            return clazz == Class.class ? new ClassLinker() : clazz == StaticClass.class ? new StaticClassLinker() : DynamicMethod.class.isAssignableFrom(clazz) ? new DynamicMethodLinker() : new BeanLinker(clazz);
        }
    };

    public static TypeBasedGuardingDynamicLinker getLinkerForClass(Class<?> clazz) {
        return (TypeBasedGuardingDynamicLinker) linkers.get(clazz);
    }

    public static boolean isDynamicMethod(Object obj) {
        return obj instanceof DynamicMethod;
    }

    public static boolean isDynamicConstructor(Object obj) {
        return (obj instanceof DynamicMethod) && ((DynamicMethod) obj).isConstructor();
    }

    public static Object getConstructorMethod(Class<?> clazz, String signature) {
        return StaticClassLinker.getConstructorMethod(clazz, signature);
    }

    public static Collection<String> getReadableInstancePropertyNames(Class<?> clazz) {
        TypeBasedGuardingDynamicLinker linker = getLinkerForClass(clazz);
        if (linker instanceof BeanLinker) {
            return ((BeanLinker) linker).getReadablePropertyNames();
        }
        return Collections.emptySet();
    }

    public static Collection<String> getWritableInstancePropertyNames(Class<?> clazz) {
        TypeBasedGuardingDynamicLinker linker = getLinkerForClass(clazz);
        if (linker instanceof BeanLinker) {
            return ((BeanLinker) linker).getWritablePropertyNames();
        }
        return Collections.emptySet();
    }

    public static Collection<String> getInstanceMethodNames(Class<?> clazz) {
        TypeBasedGuardingDynamicLinker linker = getLinkerForClass(clazz);
        if (linker instanceof BeanLinker) {
            return ((BeanLinker) linker).getMethodNames();
        }
        return Collections.emptySet();
    }

    public static Collection<String> getReadableStaticPropertyNames(Class<?> clazz) {
        return StaticClassLinker.getReadableStaticPropertyNames(clazz);
    }

    public static Collection<String> getWritableStaticPropertyNames(Class<?> clazz) {
        return StaticClassLinker.getWritableStaticPropertyNames(clazz);
    }

    public static Collection<String> getStaticMethodNames(Class<?> clazz) {
        return StaticClassLinker.getStaticMethodNames(clazz);
    }

    @Override // jdk.internal.dynalink.linker.GuardingDynamicLinker
    public GuardedInvocation getGuardedInvocation(LinkRequest request, LinkerServices linkerServices) throws Exception {
        Object receiver;
        CallSiteDescriptor callSiteDescriptor = request.getCallSiteDescriptor();
        int l = callSiteDescriptor.getNameTokenCount();
        if (l < 2 || "dyn" != callSiteDescriptor.getNameToken(0) || (receiver = request.getReceiver()) == null) {
            return null;
        }
        return getLinkerForClass(receiver.getClass()).getGuardedInvocation(request, linkerServices);
    }
}
