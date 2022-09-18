package org.spongepowered.asm.mixin.throwables;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/throwables/MixinApplyError.class */
public class MixinApplyError extends Error {
    private static final long serialVersionUID = 1;

    public MixinApplyError(String message) {
        super(message);
    }

    public MixinApplyError(Throwable cause) {
        super(cause);
    }

    public MixinApplyError(String message, Throwable cause) {
        super(message, cause);
    }
}
