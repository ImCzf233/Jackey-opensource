package net.ccbluex.liquidbounce.injection.forge.mixins.block;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.movement.NoSlow;
import net.ccbluex.liquidbounce.features.module.modules.world.Liquids;
import net.minecraft.block.BlockLiquid;
import net.minecraft.util.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({BlockLiquid.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/block/MixinBlockLiquid.class */
public class MixinBlockLiquid {
    @Inject(method = {"canCollideCheck"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true)
    private void onCollideCheck(CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (LiquidBounce.moduleManager.getModule(Liquids.class).getState()) {
            callbackInfoReturnable.setReturnValue(true);
        }
    }

    @Inject(method = {"modifyAcceleration"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true)
    private void onModifyAcceleration(CallbackInfoReturnable<Vec3> callbackInfoReturnable) {
        NoSlow noSlow = (NoSlow) LiquidBounce.moduleManager.getModule(NoSlow.class);
        if (noSlow.getState() && noSlow.getLiquidPushValue().get().booleanValue()) {
            callbackInfoReturnable.setReturnValue(new Vec3(0.0d, 0.0d, 0.0d));
        }
    }
}
