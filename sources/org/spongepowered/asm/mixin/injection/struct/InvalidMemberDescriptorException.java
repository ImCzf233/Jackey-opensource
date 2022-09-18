package org.spongepowered.asm.mixin.injection.struct;

import org.spongepowered.asm.mixin.throwables.MixinException;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/injection/struct/InvalidMemberDescriptorException.class */
public class InvalidMemberDescriptorException extends MixinException {
    private static final long serialVersionUID = 1;

    public InvalidMemberDescriptorException(String message) {
        super(message);
    }

    public InvalidMemberDescriptorException(Throwable cause) {
        super(cause);
    }

    public InvalidMemberDescriptorException(String message, Throwable cause) {
        super(message, cause);
    }
}
