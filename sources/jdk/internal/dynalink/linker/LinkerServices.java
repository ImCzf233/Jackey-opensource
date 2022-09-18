package jdk.internal.dynalink.linker;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import jdk.internal.dynalink.linker.ConversionComparator;
import jdk.internal.dynalink.support.TypeUtilities;

/* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/linker/LinkerServices.class */
public interface LinkerServices {
    MethodHandle asType(MethodHandle methodHandle, MethodType methodType);

    MethodHandle asTypeLosslessReturn(MethodHandle methodHandle, MethodType methodType);

    MethodHandle getTypeConverter(Class<?> cls, Class<?> cls2);

    boolean canConvert(Class<?> cls, Class<?> cls2);

    GuardedInvocation getGuardedInvocation(LinkRequest linkRequest) throws Exception;

    ConversionComparator.Comparison compareConversion(Class<?> cls, Class<?> cls2, Class<?> cls3);

    MethodHandle filterInternalObjects(MethodHandle methodHandle);

    /* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/linker/LinkerServices$Implementation.class */
    public static class Implementation {
        public static MethodHandle asTypeLosslessReturn(LinkerServices linkerServices, MethodHandle handle, MethodType fromType) {
            Class<?> handleReturnType = handle.type().returnType();
            return linkerServices.asType(handle, TypeUtilities.isConvertibleWithoutLoss(handleReturnType, fromType.returnType()) ? fromType : fromType.changeReturnType(handleReturnType));
        }
    }
}
