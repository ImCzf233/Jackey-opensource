package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import net.minecraft.client.renderer.entity.layers.LayerArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({LayerArrow.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/render/MixinLayerArrow.class */
public class MixinLayerArrow {
    @Redirect(method = {"doRenderLayer"}, m17at = @AbstractC1790At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderHelper;disableStandardItemLighting()V"))
    private void removeDisable() {
    }

    @Redirect(method = {"doRenderLayer"}, m17at = @AbstractC1790At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderHelper;enableStandardItemLighting()V"))
    private void removeEnable() {
    }
}
