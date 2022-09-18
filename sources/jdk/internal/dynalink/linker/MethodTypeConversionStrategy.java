package jdk.internal.dynalink.linker;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

/* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/linker/MethodTypeConversionStrategy.class */
public interface MethodTypeConversionStrategy {
    MethodHandle asType(MethodHandle methodHandle, MethodType methodType);
}
