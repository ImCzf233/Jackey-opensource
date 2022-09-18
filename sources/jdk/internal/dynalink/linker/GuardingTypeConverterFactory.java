package jdk.internal.dynalink.linker;

/* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/linker/GuardingTypeConverterFactory.class */
public interface GuardingTypeConverterFactory {
    GuardedTypeConversion convertToType(Class<?> cls, Class<?> cls2) throws Exception;
}
