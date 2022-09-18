package org.spongepowered.asm.mixin.transformer.throwables;

import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/transformer/throwables/MixinTargetAlreadyLoadedException.class */
public class MixinTargetAlreadyLoadedException extends InvalidMixinException {
    private static final long serialVersionUID = 1;
    private final String target;

    public MixinTargetAlreadyLoadedException(IMixinInfo mixin, String message, String target) {
        super(mixin, message);
        this.target = target;
    }

    public MixinTargetAlreadyLoadedException(IMixinInfo mixin, String message, String target, Throwable cause) {
        super(mixin, message, cause);
        this.target = target;
    }

    public String getTarget() {
        return this.target;
    }
}
