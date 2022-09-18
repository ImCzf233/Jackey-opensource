package jdk.nashorn.internal.runtime.linker;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.beans.BeansLinker;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker;
import jdk.internal.dynalink.support.CallSiteDescriptorFactory;
import jdk.internal.dynalink.support.Lookup;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import org.spongepowered.asm.util.Constants;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/linker/JavaSuperAdapterLinker.class */
final class JavaSuperAdapterLinker implements TypeBasedGuardingDynamicLinker {
    private static final String GET_METHOD = "getMethod";
    private static final String DYN_GET_METHOD = "dyn:getMethod";
    private static final String DYN_GET_METHOD_FIXED = "dyn:getMethod:super$";
    private static final MethodHandle ADD_PREFIX_TO_METHOD_NAME;
    private static final MethodHandle BIND_DYNAMIC_METHOD;
    private static final MethodHandle GET_ADAPTER;
    private static final MethodHandle IS_ADAPTER_OF_CLASS;

    static {
        Lookup lookup = new Lookup(MethodHandles.lookup());
        ADD_PREFIX_TO_METHOD_NAME = lookup.findOwnStatic("addPrefixToMethodName", Object.class, Object.class);
        BIND_DYNAMIC_METHOD = lookup.findOwnStatic("bindDynamicMethod", Object.class, Object.class, Object.class);
        GET_ADAPTER = lookup.findVirtual(JavaSuperAdapter.class, "getAdapter", MethodType.methodType(Object.class));
        IS_ADAPTER_OF_CLASS = lookup.findOwnStatic("isAdapterOfClass", Boolean.TYPE, Class.class, Object.class);
    }

    @Override // jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker
    public boolean canLinkType(Class<?> type) {
        return type == JavaSuperAdapter.class;
    }

    @Override // jdk.internal.dynalink.linker.GuardingDynamicLinker
    public GuardedInvocation getGuardedInvocation(LinkRequest linkRequest, LinkerServices linkerServices) throws Exception {
        MethodHandle adaptedInvocation;
        Object objSuperAdapter = linkRequest.getReceiver();
        if (!(objSuperAdapter instanceof JavaSuperAdapter)) {
            return null;
        }
        CallSiteDescriptor descriptor = linkRequest.getCallSiteDescriptor();
        if (!CallSiteDescriptorFactory.tokenizeOperators(descriptor).contains(GET_METHOD)) {
            return null;
        }
        Object adapter = ((JavaSuperAdapter) objSuperAdapter).getAdapter();
        Object[] args = linkRequest.getArguments();
        args[0] = adapter;
        MethodType type = descriptor.getMethodType();
        Class<?> adapterClass = adapter.getClass();
        boolean hasFixedName = descriptor.getNameTokenCount() > 2;
        String opName = hasFixedName ? DYN_GET_METHOD_FIXED + descriptor.getNameToken(2) : DYN_GET_METHOD;
        CallSiteDescriptor newDescriptor = NashornCallSiteDescriptor.get(descriptor.getLookup(), opName, type.changeParameterType(0, adapterClass), 0);
        GuardedInvocation guardedInv = NashornBeansLinker.getGuardedInvocation(BeansLinker.getLinkerForClass(adapterClass), linkRequest.replaceArguments(newDescriptor, args), linkerServices);
        MethodHandle guard = IS_ADAPTER_OF_CLASS.bindTo(adapterClass);
        if (guardedInv == null) {
            return new GuardedInvocation(MethodHandles.dropArguments(jdk.nashorn.internal.lookup.Lookup.EMPTY_GETTER, 1, type.parameterList().subList(1, type.parameterCount())), guard).asType(descriptor);
        }
        MethodHandle invocation = guardedInv.getInvocation();
        MethodType invType = invocation.type();
        MethodHandle typedBinder = BIND_DYNAMIC_METHOD.asType(MethodType.methodType(Object.class, invType.returnType(), invType.parameterType(0)));
        MethodHandle droppingBinder = MethodHandles.dropArguments(typedBinder, 2, invType.parameterList().subList(1, invType.parameterCount()));
        MethodHandle bindingInvocation = MethodHandles.foldArguments(droppingBinder, invocation);
        MethodHandle typedGetAdapter = asFilterType(GET_ADAPTER, 0, invType, type);
        if (hasFixedName) {
            adaptedInvocation = MethodHandles.filterArguments(bindingInvocation, 0, typedGetAdapter);
        } else {
            MethodHandle typedAddPrefix = asFilterType(ADD_PREFIX_TO_METHOD_NAME, 1, invType, type);
            adaptedInvocation = MethodHandles.filterArguments(bindingInvocation, 0, typedGetAdapter, typedAddPrefix);
        }
        return guardedInv.replaceMethods(adaptedInvocation, guard).asType(descriptor);
    }

    private static MethodHandle asFilterType(MethodHandle filter, int pos, MethodType targetType, MethodType sourceType) {
        return filter.asType(MethodType.methodType(targetType.parameterType(pos), sourceType.parameterType(pos)));
    }

    private static Object addPrefixToMethodName(Object name) {
        return Constants.IMAGINARY_SUPER.concat(String.valueOf(name));
    }

    private static Object bindDynamicMethod(Object dynamicMethod, Object boundThis) {
        return dynamicMethod == null ? ScriptRuntime.UNDEFINED : Bootstrap.bindCallable(dynamicMethod, boundThis, null);
    }

    private static boolean isAdapterOfClass(Class<?> clazz, Object obj) {
        return (obj instanceof JavaSuperAdapter) && clazz == ((JavaSuperAdapter) obj).getAdapter().getClass();
    }
}
