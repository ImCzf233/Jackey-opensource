package org.spongepowered.asm.mixin.transformer.throwables;

import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.throwables.MixinException;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/transformer/throwables/MixinReloadException.class */
public class MixinReloadException extends MixinException {
    private static final long serialVersionUID = 2;
    private final IMixinInfo mixinInfo;

    public MixinReloadException(IMixinInfo mixinInfo, String message) {
        super(message);
        this.mixinInfo = mixinInfo;
    }

    public IMixinInfo getMixinInfo() {
        return this.mixinInfo;
    }
}
