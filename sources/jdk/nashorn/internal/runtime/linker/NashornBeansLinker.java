package jdk.nashorn.internal.runtime.linker;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.beans.BeansLinker;
import jdk.internal.dynalink.linker.ConversionComparator;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.GuardingDynamicLinker;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.linker.MethodHandleTransformer;
import jdk.internal.dynalink.support.DefaultInternalObjectFilter;
import jdk.internal.dynalink.support.Lookup;
import jdk.nashorn.api.scripting.ScriptUtils;
import jdk.nashorn.internal.runtime.ConsString;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.options.Options;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/linker/NashornBeansLinker.class */
public class NashornBeansLinker implements GuardingDynamicLinker {
    private static final MethodHandle EXPORT_ARGUMENT;
    private static final MethodHandle IMPORT_RESULT;
    private static final MethodHandle FILTER_CONSSTRING;
    private final BeansLinker beansLinker = new BeansLinker();
    private static final boolean MIRROR_ALWAYS = Options.getBooleanProperty("nashorn.mirror.always", true);
    private static final ClassValue<String> FUNCTIONAL_IFACE_METHOD_NAME = new ClassValue<String>() { // from class: jdk.nashorn.internal.runtime.linker.NashornBeansLinker.1
        protected String computeValue(Class<?> type) {
            return NashornBeansLinker.findFunctionalInterfaceMethodName(type);
        }
    };

    static {
        Lookup lookup = new Lookup(MethodHandles.lookup());
        EXPORT_ARGUMENT = lookup.findOwnStatic("exportArgument", Object.class, Object.class);
        IMPORT_RESULT = lookup.findOwnStatic("importResult", Object.class, Object.class);
        FILTER_CONSSTRING = lookup.findOwnStatic("consStringFilter", Object.class, Object.class);
    }

    @Override // jdk.internal.dynalink.linker.GuardingDynamicLinker
    public GuardedInvocation getGuardedInvocation(LinkRequest linkRequest, LinkerServices linkerServices) throws Exception {
        String name;
        Object self = linkRequest.getReceiver();
        CallSiteDescriptor desc = linkRequest.getCallSiteDescriptor();
        if (self instanceof ConsString) {
            Object[] arguments = linkRequest.getArguments();
            arguments[0] = "";
            LinkRequest forgedLinkRequest = linkRequest.replaceArguments(desc, arguments);
            GuardedInvocation invocation = getGuardedInvocation(this.beansLinker, forgedLinkRequest, linkerServices);
            if (invocation != null) {
                return invocation.filterArguments(0, FILTER_CONSSTRING);
            }
            return null;
        } else if (self != null && "call".equals(desc.getNameToken(1)) && (name = getFunctionalInterfaceMethodName(self.getClass())) != null) {
            MethodType callType = desc.getMethodType();
            NashornCallSiteDescriptor newDesc = NashornCallSiteDescriptor.get(desc.getLookup(), "dyn:callMethod:" + name, desc.getMethodType().dropParameterTypes(1, 2), NashornCallSiteDescriptor.getFlags(desc));
            GuardedInvocation gi = getGuardedInvocation(this.beansLinker, linkRequest.replaceArguments(newDesc, linkRequest.getArguments()), new NashornBeansLinkerServices(linkerServices));
            return gi.replaceMethods(jdk.nashorn.internal.lookup.Lookup.f248MH.dropArguments(linkerServices.filterInternalObjects(gi.getInvocation()), 1, callType.parameterType(1)), gi.getGuard());
        } else {
            return getGuardedInvocation(this.beansLinker, linkRequest, linkerServices);
        }
    }

    public static GuardedInvocation getGuardedInvocation(GuardingDynamicLinker delegateLinker, LinkRequest linkRequest, LinkerServices linkerServices) throws Exception {
        return delegateLinker.getGuardedInvocation(linkRequest, new NashornBeansLinkerServices(linkerServices));
    }

    private static Object exportArgument(Object arg) {
        return exportArgument(arg, MIRROR_ALWAYS);
    }

    public static Object exportArgument(Object arg, boolean mirrorAlways) {
        if (arg instanceof ConsString) {
            return arg.toString();
        }
        if (mirrorAlways && (arg instanceof ScriptObject)) {
            return ScriptUtils.wrap((ScriptObject) arg);
        }
        return arg;
    }

    private static Object importResult(Object arg) {
        return ScriptUtils.unwrap(arg);
    }

    private static Object consStringFilter(Object arg) {
        return arg instanceof ConsString ? arg.toString() : arg;
    }

    public static String findFunctionalInterfaceMethodName(Class<?> clazz) {
        Class<?>[] interfaces;
        Method[] methods;
        if (clazz == null) {
            return null;
        }
        for (Class<?> iface : clazz.getInterfaces()) {
            if (Context.isAccessibleClass(iface) && iface.isAnnotationPresent(FunctionalInterface.class)) {
                for (Method m : iface.getMethods()) {
                    if (Modifier.isAbstract(m.getModifiers()) && !isOverridableObjectMethod(m)) {
                        return m.getName();
                    }
                }
                continue;
            }
        }
        return findFunctionalInterfaceMethodName(clazz.getSuperclass());
    }

    private static boolean isOverridableObjectMethod(Method m) {
        String name = m.getName();
        boolean z = true;
        switch (name.hashCode()) {
            case -1776922004:
                if (name.equals("toString")) {
                    z = true;
                    break;
                }
                break;
            case -1295482945:
                if (name.equals("equals")) {
                    z = false;
                    break;
                }
                break;
            case 147696667:
                if (name.equals("hashCode")) {
                    z = true;
                    break;
                }
                break;
        }
        switch (z) {
            case false:
                if (m.getReturnType() == Boolean.TYPE) {
                    Class<?>[] params = m.getParameterTypes();
                    return params.length == 1 && params[0] == Object.class;
                }
                return false;
            case true:
                return m.getReturnType() == Integer.TYPE && m.getParameterCount() == 0;
            case true:
                return m.getReturnType() == String.class && m.getParameterCount() == 0;
            default:
                return false;
        }
    }

    public static String getFunctionalInterfaceMethodName(Class<?> clazz) {
        return (String) FUNCTIONAL_IFACE_METHOD_NAME.get(clazz);
    }

    public static MethodHandleTransformer createHiddenObjectFilter() {
        return new DefaultInternalObjectFilter(EXPORT_ARGUMENT, MIRROR_ALWAYS ? IMPORT_RESULT : null);
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/linker/NashornBeansLinker$NashornBeansLinkerServices.class */
    public static class NashornBeansLinkerServices implements LinkerServices {
        private final LinkerServices linkerServices;

        NashornBeansLinkerServices(LinkerServices linkerServices) {
            this.linkerServices = linkerServices;
        }

        @Override // jdk.internal.dynalink.linker.LinkerServices
        public MethodHandle asType(MethodHandle handle, MethodType fromType) {
            return this.linkerServices.asType(handle, fromType);
        }

        @Override // jdk.internal.dynalink.linker.LinkerServices
        public MethodHandle asTypeLosslessReturn(MethodHandle handle, MethodType fromType) {
            return LinkerServices.Implementation.asTypeLosslessReturn(this, handle, fromType);
        }

        @Override // jdk.internal.dynalink.linker.LinkerServices
        public MethodHandle getTypeConverter(Class<?> sourceType, Class<?> targetType) {
            return this.linkerServices.getTypeConverter(sourceType, targetType);
        }

        @Override // jdk.internal.dynalink.linker.LinkerServices
        public boolean canConvert(Class<?> from, Class<?> to) {
            return this.linkerServices.canConvert(from, to);
        }

        @Override // jdk.internal.dynalink.linker.LinkerServices
        public GuardedInvocation getGuardedInvocation(LinkRequest linkRequest) throws Exception {
            return this.linkerServices.getGuardedInvocation(linkRequest);
        }

        @Override // jdk.internal.dynalink.linker.LinkerServices
        public ConversionComparator.Comparison compareConversion(Class<?> sourceType, Class<?> targetType1, Class<?> targetType2) {
            if (sourceType == ConsString.class) {
                if (String.class == targetType1 || CharSequence.class == targetType1) {
                    return ConversionComparator.Comparison.TYPE_1_BETTER;
                }
                if (String.class == targetType2 || CharSequence.class == targetType2) {
                    return ConversionComparator.Comparison.TYPE_2_BETTER;
                }
            }
            return this.linkerServices.compareConversion(sourceType, targetType1, targetType2);
        }

        @Override // jdk.internal.dynalink.linker.LinkerServices
        public MethodHandle filterInternalObjects(MethodHandle target) {
            return this.linkerServices.filterInternalObjects(target);
        }
    }
}
