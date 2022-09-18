package net.ccbluex.liquidbounce.injection.forge.mixins.block;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.render.XRay;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({BlockModelRenderer.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/block/MixinBlockModelRenderer.class */
public class MixinBlockModelRenderer {
    @Inject(method = {"renderModelAmbientOcclusion"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true)
    private void renderModelAmbientOcclusion(IBlockAccess blockAccessIn, IBakedModel modelIn, Block blockIn, BlockPos blockPosIn, WorldRenderer worldRendererIn, boolean checkSide, CallbackInfoReturnable<Boolean> booleanCallbackInfoReturnable) {
        XRay xray = (XRay) LiquidBounce.moduleManager.getModule(XRay.class);
        if (xray.getState() && !xray.getXrayBlocks().contains(blockIn)) {
            booleanCallbackInfoReturnable.setReturnValue(false);
        }
    }

    @Inject(method = {"renderModelStandard"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true)
    private void renderModelStandard(IBlockAccess blockAccessIn, IBakedModel modelIn, Block blockIn, BlockPos blockPosIn, WorldRenderer worldRendererIn, boolean checkSides, CallbackInfoReturnable<Boolean> booleanCallbackInfoReturnable) {
        XRay xray = (XRay) LiquidBounce.moduleManager.getModule(XRay.class);
        if (xray.getState() && !xray.getXrayBlocks().contains(blockIn)) {
            booleanCallbackInfoReturnable.setReturnValue(false);
        }
    }
}
