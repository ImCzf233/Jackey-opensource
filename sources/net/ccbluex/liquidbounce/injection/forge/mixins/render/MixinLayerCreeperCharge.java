package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import net.minecraft.client.renderer.entity.layers.LayerCreeperCharge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin({LayerCreeperCharge.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/render/MixinLayerCreeperCharge.class */
public class MixinLayerCreeperCharge {
    @ModifyArg(method = {"doRenderLayer(Lnet/minecraft/entity/monster/EntityCreeper;FFFFFFF)V"}, slice = @Slice(from = @AbstractC1790At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelCreeper;render(Lnet/minecraft/entity/Entity;FFFFFF)V")), m20at = @AbstractC1790At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;depthMask(Z)V"))
    private boolean fixDepth(boolean original) {
        return true;
    }
}
