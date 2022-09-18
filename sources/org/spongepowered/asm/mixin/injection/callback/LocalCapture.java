package org.spongepowered.asm.mixin.injection.callback;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/injection/callback/LocalCapture.class */
public enum LocalCapture {
    NO_CAPTURE(false, false),
    PRINT(false, true),
    CAPTURE_FAILSOFT,
    CAPTURE_FAILHARD,
    CAPTURE_FAILEXCEPTION;
    
    private final boolean captureLocals;
    private final boolean printLocals;

    LocalCapture() {
        this(true, false);
    }

    LocalCapture(boolean captureLocals, boolean printLocals) {
        this.captureLocals = captureLocals;
        this.printLocals = printLocals;
    }

    public boolean isCaptureLocals() {
        return this.captureLocals;
    }

    public boolean isPrintLocals() {
        return this.printLocals;
    }
}
