package org.spongepowered.asm.mixin.throwables;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/throwables/ClassAlreadyLoadedException.class */
public class ClassAlreadyLoadedException extends MixinException {
    private static final long serialVersionUID = 1;

    public ClassAlreadyLoadedException(String message) {
        super(message);
    }

    public ClassAlreadyLoadedException(Throwable cause) {
        super(cause);
    }

    public ClassAlreadyLoadedException(String message, Throwable cause) {
        super(message, cause);
    }
}
