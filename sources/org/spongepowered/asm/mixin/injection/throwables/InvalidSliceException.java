package org.spongepowered.asm.mixin.injection.throwables;

import org.spongepowered.asm.mixin.injection.code.ISliceContext;
import org.spongepowered.asm.mixin.refmap.IMixinContext;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/injection/throwables/InvalidSliceException.class */
public class InvalidSliceException extends InvalidInjectionException {
    private static final long serialVersionUID = 1;

    public InvalidSliceException(IMixinContext context, String message) {
        super(context, message);
    }

    public InvalidSliceException(ISliceContext owner, String message) {
        super(owner.getContext(), message);
    }

    public InvalidSliceException(IMixinContext context, Throwable cause) {
        super(context, cause);
    }

    public InvalidSliceException(ISliceContext owner, Throwable cause) {
        super(owner.getContext(), cause);
    }

    public InvalidSliceException(IMixinContext context, String message, Throwable cause) {
        super(context, message, cause);
    }

    public InvalidSliceException(ISliceContext owner, String message, Throwable cause) {
        super(owner.getContext(), message, cause);
    }
}
