package net.ccbluex.liquidbounce.injection.forge.mixins.block;

import net.minecraft.block.BlockAnvil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({BlockAnvil.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/block/MixinBlockAnvil.class */
public abstract class MixinBlockAnvil extends MixinBlock {
    @Inject(method = {"onBlockPlaced"}, cancellable = true, m23at = {@AbstractC1790At("HEAD")})
    private void injectAnvilCrashFix(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, CallbackInfoReturnable<IBlockState> cir) {
        if (((meta >> 2) & (-4)) != 0) {
            cir.setReturnValue(super.func_180642_a(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).func_177226_a(BlockAnvil.field_176506_a, placer.func_174811_aO().func_176746_e()).func_177226_a(BlockAnvil.field_176505_b, 2));
            cir.cancel();
        }
    }
}
