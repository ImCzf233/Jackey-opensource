package org.spongepowered.asm.mixin.transformer.ext;

import org.spongepowered.asm.mixin.MixinEnvironment;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/transformer/ext/IExtension.class */
public interface IExtension {
    boolean checkActive(MixinEnvironment mixinEnvironment);

    void preApply(ITargetClassContext iTargetClassContext);

    void postApply(ITargetClassContext iTargetClassContext);

    void export(MixinEnvironment mixinEnvironment, String str, boolean z, byte[] bArr);
}
