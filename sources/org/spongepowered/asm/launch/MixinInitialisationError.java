package org.spongepowered.asm.launch;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/launch/MixinInitialisationError.class */
public class MixinInitialisationError extends Error {
    private static final long serialVersionUID = 1;

    public MixinInitialisationError() {
    }

    public MixinInitialisationError(String message) {
        super(message);
    }

    public MixinInitialisationError(Throwable cause) {
        super(cause);
    }

    public MixinInitialisationError(String message, Throwable cause) {
        super(message, cause);
    }
}
