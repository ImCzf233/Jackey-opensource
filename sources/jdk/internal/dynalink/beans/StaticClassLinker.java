package jdk.internal.dynalink.beans;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.beans.GuardedInvocationComponent;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker;
import jdk.internal.dynalink.support.Lookup;

/* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/beans/StaticClassLinker.class */
public class StaticClassLinker implements TypeBasedGuardingDynamicLinker {
    static final MethodHandle GET_CLASS;
    static final MethodHandle IS_CLASS;
    private static final ClassValue<SingleClassStaticsLinker> linkers = new ClassValue<SingleClassStaticsLinker>() { // from class: jdk.internal.dynalink.beans.StaticClassLinker.1
        protected SingleClassStaticsLinker computeValue(Class<?> clazz) {
            return new SingleClassStaticsLinker(clazz);
        }
    };
    static final MethodHandle ARRAY_CTOR = Lookup.PUBLIC.findStatic(Array.class, "newInstance", MethodType.methodType(Object.class, Class.class, Integer.TYPE));

    static {
        Lookup lookup = new Lookup(MethodHandles.lookup());
        GET_CLASS = lookup.findVirtual(StaticClass.class, "getRepresentedClass", MethodType.methodType(Class.class));
        IS_CLASS = lookup.findOwnStatic("isClass", Boolean.TYPE, Class.class, Object.class);
    }

    /* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/beans/StaticClassLinker$SingleClassStaticsLinker.class */
    public static class SingleClassStaticsLinker extends AbstractJavaLinker {
        private final DynamicMethod constructor;

        SingleClassStaticsLinker(Class<?> clazz) {
            super(clazz, StaticClassLinker.IS_CLASS.bindTo(clazz));
            setPropertyGetter("class", StaticClassLinker.GET_CLASS, GuardedInvocationComponent.ValidationType.INSTANCE_OF);
            this.constructor = createConstructorMethod(clazz);
        }

        private static DynamicMethod createConstructorMethod(Class<?> clazz) {
            if (clazz.isArray()) {
                MethodHandle boundArrayCtor = StaticClassLinker.ARRAY_CTOR.bindTo(clazz.getComponentType());
                return new SimpleDynamicMethod(StaticClassIntrospector.editConstructorMethodHandle(boundArrayCtor.asType(boundArrayCtor.type().changeReturnType(clazz))), clazz, "<init>");
            } else if (CheckRestrictedPackage.isRestrictedClass(clazz)) {
                return null;
            } else {
                return createDynamicMethod(Arrays.asList(clazz.getConstructors()), clazz, "<init>");
            }
        }

        @Override // jdk.internal.dynalink.beans.AbstractJavaLinker
        FacetIntrospector createFacetIntrospector() {
            return new StaticClassIntrospector(this.clazz);
        }

        @Override // jdk.internal.dynalink.beans.AbstractJavaLinker, jdk.internal.dynalink.linker.GuardingDynamicLinker
        public GuardedInvocation getGuardedInvocation(LinkRequest request, LinkerServices linkerServices) throws Exception {
            MethodHandle ctorInvocation;
            GuardedInvocation gi = super.getGuardedInvocation(request, linkerServices);
            if (gi != null) {
                return gi;
            }
            CallSiteDescriptor desc = request.getCallSiteDescriptor();
            String op = desc.getNameToken(1);
            if ("new" == op && this.constructor != null && (ctorInvocation = this.constructor.getInvocation(desc, linkerServices)) != null) {
                return new GuardedInvocation(ctorInvocation, getClassGuard(desc.getMethodType()));
            }
            return null;
        }

        @Override // jdk.internal.dynalink.beans.AbstractJavaLinker
        SingleDynamicMethod getConstructorMethod(String signature) {
            if (this.constructor != null) {
                return this.constructor.getMethodForExactParamTypes(signature);
            }
            return null;
        }
    }

    public static Object getConstructorMethod(Class<?> clazz, String signature) {
        return ((SingleClassStaticsLinker) linkers.get(clazz)).getConstructorMethod(signature);
    }

    public static Collection<String> getReadableStaticPropertyNames(Class<?> clazz) {
        return ((SingleClassStaticsLinker) linkers.get(clazz)).getReadablePropertyNames();
    }

    public static Collection<String> getWritableStaticPropertyNames(Class<?> clazz) {
        return ((SingleClassStaticsLinker) linkers.get(clazz)).getWritablePropertyNames();
    }

    public static Collection<String> getStaticMethodNames(Class<?> clazz) {
        return ((SingleClassStaticsLinker) linkers.get(clazz)).getMethodNames();
    }

    @Override // jdk.internal.dynalink.linker.GuardingDynamicLinker
    public GuardedInvocation getGuardedInvocation(LinkRequest request, LinkerServices linkerServices) throws Exception {
        Object receiver = request.getReceiver();
        if (receiver instanceof StaticClass) {
            return ((SingleClassStaticsLinker) linkers.get(((StaticClass) receiver).getRepresentedClass())).getGuardedInvocation(request, linkerServices);
        }
        return null;
    }

    @Override // jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker
    public boolean canLinkType(Class<?> type) {
        return type == StaticClass.class;
    }

    private static boolean isClass(Class<?> clazz, Object obj) {
        return (obj instanceof StaticClass) && ((StaticClass) obj).getRepresentedClass() == clazz;
    }
}
