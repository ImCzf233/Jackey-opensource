package net.ccbluex.liquidbounce.injection.forge.mixins.block;

import java.util.List;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.BlockBBEvent;
import net.ccbluex.liquidbounce.features.module.modules.combat.Criticals;
import net.ccbluex.liquidbounce.features.module.modules.exploit.GhostHand;
import net.ccbluex.liquidbounce.features.module.modules.player.NoFall;
import net.ccbluex.liquidbounce.features.module.modules.render.XRay;
import net.ccbluex.liquidbounce.features.module.modules.world.NoSlowBreak;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Block.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/block/MixinBlock.class */
public abstract class MixinBlock {
    @Shadow
    @Final
    protected BlockState field_176227_L;

    @Shadow
    public abstract AxisAlignedBB func_180640_a(World world, BlockPos blockPos, IBlockState iBlockState);

    @Shadow
    public abstract void func_149676_a(float f, float f2, float f3, float f4, float f5, float f6);

    @Shadow
    public IBlockState func_180642_a(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return null;
    }

    @Overwrite
    public void func_180638_a(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity) {
        BlockBBEvent blockBBEvent = new BlockBBEvent(pos, this.field_176227_L.func_177622_c(), func_180640_a(worldIn, pos, state));
        LiquidBounce.eventManager.callEvent(blockBBEvent);
        AxisAlignedBB axisalignedbb = blockBBEvent.getBoundingBox();
        if (axisalignedbb != null && mask.func_72326_a(axisalignedbb)) {
            list.add(axisalignedbb);
        }
    }

    @Inject(method = {"shouldSideBeRendered"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true)
    private void shouldSideBeRendered(CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        XRay xray = (XRay) LiquidBounce.moduleManager.getModule(XRay.class);
        if (xray.getState()) {
            callbackInfoReturnable.setReturnValue(Boolean.valueOf(xray.getXrayBlocks().contains(this)));
        }
    }

    @Inject(method = {"isCollidable"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true)
    private void isCollidable(CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        GhostHand ghostHand = (GhostHand) LiquidBounce.moduleManager.getModule(GhostHand.class);
        if (ghostHand.getState() && ghostHand.getBlockValue().get().intValue() != Block.func_149682_b((Block) this)) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }

    @Inject(method = {"getAmbientOcclusionLightValue"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true)
    private void getAmbientOcclusionLightValue(CallbackInfoReturnable<Float> floatCallbackInfoReturnable) {
        if (LiquidBounce.moduleManager.getModule(XRay.class).getState()) {
            floatCallbackInfoReturnable.setReturnValue(Float.valueOf(1.0f));
        }
    }

    @Inject(method = {"getPlayerRelativeBlockHardness"}, m23at = {@AbstractC1790At("RETURN")}, cancellable = true)
    public void modifyBreakSpeed(EntityPlayer playerIn, World worldIn, BlockPos pos, CallbackInfoReturnable<Float> callbackInfo) {
        float f = callbackInfo.getReturnValue().floatValue();
        NoSlowBreak noSlowBreak = (NoSlowBreak) LiquidBounce.moduleManager.getModule(NoSlowBreak.class);
        if (noSlowBreak.getState()) {
            if (noSlowBreak.getWaterValue().get().booleanValue() && playerIn.func_70055_a(Material.field_151586_h) && !EnchantmentHelper.func_77510_g(playerIn)) {
                f *= 5.0f;
            }
            if (noSlowBreak.getAirValue().get().booleanValue() && !playerIn.field_70122_E) {
                f *= 5.0f;
            }
        } else if (playerIn.field_70122_E) {
            NoFall noFall = (NoFall) LiquidBounce.moduleManager.getModule(NoFall.class);
            Criticals criticals = (Criticals) LiquidBounce.moduleManager.getModule(Criticals.class);
            if ((noFall.getState() && noFall.getTypeValue().get().equalsIgnoreCase("edit") && noFall.getEditMode().get().equalsIgnoreCase("noground")) || (criticals.getState() && criticals.getModeValue().get().equalsIgnoreCase("NoGround"))) {
                f /= 5.0f;
            }
        }
        callbackInfo.setReturnValue(Float.valueOf(f));
    }
}
