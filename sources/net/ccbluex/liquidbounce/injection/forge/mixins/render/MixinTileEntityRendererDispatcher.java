package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.render.XRay;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({TileEntityRendererDispatcher.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/render/MixinTileEntityRendererDispatcher.class */
public class MixinTileEntityRendererDispatcher {
    @Inject(method = {"renderTileEntity"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true)
    private void renderTileEntity(TileEntity tileentityIn, float partialTicks, int destroyStage, CallbackInfo callbackInfo) {
        XRay xray = (XRay) LiquidBounce.moduleManager.getModule(XRay.class);
        if (xray.getState() && !xray.getXrayBlocks().contains(tileentityIn.func_145838_q())) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = {"renderTileEntity"}, m23at = {@AbstractC1790At(value = "INVOKE", target = "Lnet/minecraft/world/World;getCombinedLight(Lnet/minecraft/util/BlockPos;I)I")})
    private void enableLighting(CallbackInfo ci) {
        RenderHelper.func_74519_b();
    }
}
