package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({TileEntitySkullRenderer.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/render/MixinTileEntitySkullRenderer.class */
public class MixinTileEntitySkullRenderer {
    @Inject(method = {"renderSkull"}, m23at = {@AbstractC1790At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V")})
    private void enableBlending(CallbackInfo ci) {
        GlStateManager.func_179147_l();
        GlStateManager.func_179120_a(770, 771, 1, 0);
    }
}
