package jdk.internal.dynalink.linker;

/* loaded from: Jackey Client b2.jar:jdk/internal/dynalink/linker/GuardedTypeConversion.class */
public class GuardedTypeConversion {
    private final GuardedInvocation conversionInvocation;
    private final boolean cacheable;

    public GuardedTypeConversion(GuardedInvocation conversionInvocation, boolean cacheable) {
        this.conversionInvocation = conversionInvocation;
        this.cacheable = cacheable;
    }

    public GuardedInvocation getConversionInvocation() {
        return this.conversionInvocation;
    }

    public boolean isCacheable() {
        return this.cacheable;
    }
}
