package org.spongepowered.asm.mixin.injection.modify;

import org.spongepowered.asm.mixin.throwables.MixinException;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/injection/modify/InvalidImplicitDiscriminatorException.class */
public class InvalidImplicitDiscriminatorException extends MixinException {
    private static final long serialVersionUID = 1;

    public InvalidImplicitDiscriminatorException(String message) {
        super(message);
    }

    public InvalidImplicitDiscriminatorException(String message, Throwable cause) {
        super(message, cause);
    }
}
