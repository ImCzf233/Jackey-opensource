package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerWitherAura;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({LayerWitherAura.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/render/MixinLayerWitherAura.class */
public class MixinLayerWitherAura {
    @Inject(method = {"doRenderLayer(Lnet/minecraft/entity/boss/EntityWither;FFFFFFF)V"}, m23at = {@AbstractC1790At("TAIL")})
    private void fixDepth(CallbackInfo ci) {
        GlStateManager.func_179132_a(true);
    }
}
