package net.ccbluex.liquidbounce.injection.forge.mixins.patcher.bugfixes;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.RegionRenderCache;
import net.minecraft.util.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin({RegionRenderCache.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/patcher/bugfixes/MixinRegionRenderCache.class */
public class MixinRegionRenderCache {
    @Shadow
    @Final
    private static IBlockState field_175632_f;
    @Shadow
    private IBlockState[] field_175635_i;

    @Inject(method = {"getBlockState"}, m23at = {@AbstractC1790At(value = "FIELD", target = "Lnet/minecraft/client/renderer/RegionRenderCache;blockStates:[Lnet/minecraft/block/state/IBlockState;", ordinal = 0, shift = AbstractC1790At.Shift.AFTER)}, locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    private void connectedTexturesBoundsCheck(BlockPos pos, CallbackInfoReturnable<IBlockState> cir, int positionIndex) {
        if (positionIndex < 0 || positionIndex >= this.field_175635_i.length) {
            cir.setReturnValue(field_175632_f);
        }
    }
}
