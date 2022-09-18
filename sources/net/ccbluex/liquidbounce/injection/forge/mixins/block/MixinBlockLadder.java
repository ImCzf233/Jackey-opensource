package net.ccbluex.liquidbounce.injection.forge.mixins.block;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.movement.FastClimb;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({BlockLadder.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/block/MixinBlockLadder.class */
public abstract class MixinBlockLadder extends MixinBlock {
    @Shadow
    @Final
    public static PropertyDirection field_176382_a;

    @Overwrite
    public void func_180654_a(IBlockAccess worldIn, BlockPos pos) {
        IBlockState iblockstate = worldIn.func_180495_p(pos);
        if (iblockstate.func_177230_c() instanceof BlockLadder) {
            FastClimb fastClimb = (FastClimb) LiquidBounce.moduleManager.getModule(FastClimb.class);
            float f = (!fastClimb.getState() || !fastClimb.getModeValue().get().equalsIgnoreCase("AAC3.0.0")) ? 0.125f : 0.99f;
            switch (C16841.$SwitchMap$net$minecraft$util$EnumFacing[iblockstate.func_177229_b(field_176382_a).ordinal()]) {
                case 1:
                    func_149676_a(0.0f, 0.0f, 1.0f - f, 1.0f, 1.0f, 1.0f);
                    return;
                case 2:
                    func_149676_a(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, f);
                    return;
                case 3:
                    func_149676_a(1.0f - f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
                    return;
                case 4:
                default:
                    func_149676_a(0.0f, 0.0f, 0.0f, f, 1.0f, 1.0f);
                    return;
            }
        }
    }

    /* renamed from: net.ccbluex.liquidbounce.injection.forge.mixins.block.MixinBlockLadder$1 */
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/block/MixinBlockLadder$1.class */
    static /* synthetic */ class C16841 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$util$EnumFacing = new int[EnumFacing.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$util$EnumFacing[EnumFacing.NORTH.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$util$EnumFacing[EnumFacing.SOUTH.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$util$EnumFacing[EnumFacing.WEST.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$minecraft$util$EnumFacing[EnumFacing.EAST.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }
}
