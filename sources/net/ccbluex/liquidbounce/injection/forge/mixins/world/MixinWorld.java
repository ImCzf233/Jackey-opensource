package net.ccbluex.liquidbounce.injection.forge.mixins.world;

import java.util.List;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin({World.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/world/MixinWorld.class */
public abstract class MixinWorld {
    @Shadow
    @Final
    public boolean field_72995_K;

    @Shadow
    public abstract IBlockState func_180495_p(BlockPos blockPos);

    @ModifyVariable(method = {"updateEntityWithOptionalForce"}, m18at = @AbstractC1790At("STORE"), ordinal = 1)
    private boolean checkIfWorldIsRemoteBeforeForceUpdating(boolean isForced) {
        return isForced && !this.field_72995_K;
    }

    @Inject(method = {"getCollidingBoundingBoxes"}, m23at = {@AbstractC1790At(value = "INVOKE", target = "Lnet/minecraft/world/World;getEntitiesWithinAABBExcludingEntity(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/AxisAlignedBB;)Ljava/util/List;")}, cancellable = true, locals = LocalCapture.CAPTURE_FAILSOFT)
    private void filterEntities(Entity entityIn, AxisAlignedBB bb, CallbackInfoReturnable<List<AxisAlignedBB>> cir, List<AxisAlignedBB> list) {
        if ((entityIn instanceof EntityTNTPrimed) || (entityIn instanceof EntityFallingBlock) || (entityIn instanceof EntityItem) || (entityIn instanceof EntityFX)) {
            cir.setReturnValue(list);
        }
    }
}
