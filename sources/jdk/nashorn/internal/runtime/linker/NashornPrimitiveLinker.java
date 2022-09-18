package jdk.nashorn.internal.runtime.linker;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import jdk.internal.dynalink.linker.ConversionComparator;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.GuardedTypeConversion;
import jdk.internal.dynalink.linker.GuardingTypeConverterFactory;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker;
import jdk.internal.dynalink.support.TypeUtilities;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.runtime.ConsString;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptRuntime;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/linker/NashornPrimitiveLinker.class */
final class NashornPrimitiveLinker implements TypeBasedGuardingDynamicLinker, GuardingTypeConverterFactory, ConversionComparator {
    private static final GuardedTypeConversion VOID_TO_OBJECT = new GuardedTypeConversion(new GuardedInvocation(MethodHandles.constant(Object.class, ScriptRuntime.UNDEFINED)), true);
    private static final MethodHandle GUARD_PRIMITIVE = findOwnMH("isJavaScriptPrimitive", Boolean.TYPE, Object.class);

    @Override // jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker
    public boolean canLinkType(Class<?> type) {
        return canLinkTypeStatic(type);
    }

    private static boolean canLinkTypeStatic(Class<?> type) {
        return type == String.class || type == Boolean.class || type == ConsString.class || type == Integer.class || type == Double.class || type == Float.class || type == Short.class || type == Byte.class;
    }

    @Override // jdk.internal.dynalink.linker.GuardingDynamicLinker
    public GuardedInvocation getGuardedInvocation(LinkRequest origRequest, LinkerServices linkerServices) throws Exception {
        LinkRequest request = origRequest.withoutRuntimeContext();
        Object self = request.getReceiver();
        NashornCallSiteDescriptor desc = (NashornCallSiteDescriptor) request.getCallSiteDescriptor();
        return Bootstrap.asTypeSafeReturn(Global.primitiveLookup(request, self), linkerServices, desc);
    }

    @Override // jdk.internal.dynalink.linker.GuardingTypeConverterFactory
    public GuardedTypeConversion convertToType(Class<?> sourceType, Class<?> targetType) {
        MethodHandle mh = JavaArgumentConverters.getConverter(targetType);
        if (mh == null) {
            if (targetType == Object.class && sourceType == Void.TYPE) {
                return VOID_TO_OBJECT;
            }
            return null;
        }
        return new GuardedTypeConversion(new GuardedInvocation(mh, canLinkTypeStatic(sourceType) ? null : GUARD_PRIMITIVE).asType(mh.type().changeParameterType(0, sourceType)), true);
    }

    @Override // jdk.internal.dynalink.linker.ConversionComparator
    public ConversionComparator.Comparison compareConversion(Class<?> sourceType, Class<?> targetType1, Class<?> targetType2) {
        Class<?> wrapper1 = getWrapperTypeOrSelf(targetType1);
        if (sourceType == wrapper1) {
            return ConversionComparator.Comparison.TYPE_1_BETTER;
        }
        Class<?> wrapper2 = getWrapperTypeOrSelf(targetType2);
        if (sourceType == wrapper2) {
            return ConversionComparator.Comparison.TYPE_2_BETTER;
        }
        if (Number.class.isAssignableFrom(sourceType)) {
            if (Number.class.isAssignableFrom(wrapper1)) {
                if (!Number.class.isAssignableFrom(wrapper2)) {
                    return ConversionComparator.Comparison.TYPE_1_BETTER;
                }
            } else if (Number.class.isAssignableFrom(wrapper2)) {
                return ConversionComparator.Comparison.TYPE_2_BETTER;
            }
            if (Character.class == wrapper1) {
                return ConversionComparator.Comparison.TYPE_1_BETTER;
            }
            if (Character.class == wrapper2) {
                return ConversionComparator.Comparison.TYPE_2_BETTER;
            }
        }
        if (sourceType == String.class || sourceType == Boolean.class || Number.class.isAssignableFrom(sourceType)) {
            Class<?> primitiveType1 = getPrimitiveTypeOrSelf(targetType1);
            Class<?> primitiveType2 = getPrimitiveTypeOrSelf(targetType2);
            if (TypeUtilities.isMethodInvocationConvertible(primitiveType1, primitiveType2)) {
                return ConversionComparator.Comparison.TYPE_2_BETTER;
            }
            if (TypeUtilities.isMethodInvocationConvertible(primitiveType2, primitiveType1)) {
                return ConversionComparator.Comparison.TYPE_1_BETTER;
            }
            if (targetType1 == String.class) {
                return ConversionComparator.Comparison.TYPE_1_BETTER;
            }
            if (targetType2 == String.class) {
                return ConversionComparator.Comparison.TYPE_2_BETTER;
            }
        }
        return ConversionComparator.Comparison.INDETERMINATE;
    }

    private static Class<?> getPrimitiveTypeOrSelf(Class<?> type) {
        Class<?> primitive = TypeUtilities.getPrimitiveType(type);
        return primitive == null ? type : primitive;
    }

    private static Class<?> getWrapperTypeOrSelf(Class<?> type) {
        Class<?> wrapper = TypeUtilities.getWrapperType(type);
        return wrapper == null ? type : wrapper;
    }

    private static boolean isJavaScriptPrimitive(Object o) {
        return JSType.isString(o) || (o instanceof Boolean) || JSType.isNumber(o) || o == null;
    }

    private static MethodHandle findOwnMH(String name, Class<?> rtype, Class<?>... types) {
        return Lookup.f248MH.findStatic(MethodHandles.lookup(), NashornPrimitiveLinker.class, name, Lookup.f248MH.type(rtype, types));
    }
}
