package jdk.internal.dynalink.linker;

/* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/linker/ConversionComparator.class */
public interface ConversionComparator {

    /* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/linker/ConversionComparator$Comparison.class */
    public enum Comparison {
        INDETERMINATE,
        TYPE_1_BETTER,
        TYPE_2_BETTER
    }

    Comparison compareConversion(Class<?> cls, Class<?> cls2, Class<?> cls3);
}
