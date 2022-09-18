package org.spongepowered.asm.util.throwables;

import org.spongepowered.asm.mixin.throwables.MixinException;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/util/throwables/LVTGeneratorException.class */
public class LVTGeneratorException extends MixinException {
    private static final long serialVersionUID = 1;

    public LVTGeneratorException(String message) {
        super(message);
    }

    public LVTGeneratorException(String message, Throwable cause) {
        super(message, cause);
    }
}
