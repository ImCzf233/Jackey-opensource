package jdk.nashorn.internal;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/AssertsEnabled.class */
public final class AssertsEnabled {
    private static boolean assertsEnabled;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !AssertsEnabled.class.desiredAssertionStatus();
        assertsEnabled = false;
        if (!$assertionsDisabled) {
            assertsEnabled = true;
            if (1 != 0) {
                return;
            }
            throw new AssertionError();
        }
    }

    public static boolean assertsEnabled() {
        return assertsEnabled;
    }
}
