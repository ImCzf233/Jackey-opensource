package org.spongepowered.asm.mixin.injection.throwables;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/injection/throwables/InjectionError.class */
public class InjectionError extends Error {
    private static final long serialVersionUID = 1;

    public InjectionError() {
    }

    public InjectionError(String message) {
        super(message);
    }

    public InjectionError(Throwable cause) {
        super(cause);
    }

    public InjectionError(String message, Throwable cause) {
        super(message, cause);
    }
}
