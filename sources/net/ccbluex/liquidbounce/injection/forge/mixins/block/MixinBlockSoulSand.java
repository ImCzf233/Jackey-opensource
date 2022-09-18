package net.ccbluex.liquidbounce.injection.forge.mixins.block;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.movement.NoSlow;
import net.minecraft.block.BlockSoulSand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({BlockSoulSand.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/block/MixinBlockSoulSand.class */
public class MixinBlockSoulSand {
    @Inject(method = {"onEntityCollidedWithBlock"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true)
    private void onEntityCollidedWithBlock(CallbackInfo callbackInfo) {
        NoSlow noSlow = (NoSlow) LiquidBounce.moduleManager.getModule(NoSlow.class);
        if (noSlow.getState() && noSlow.getSoulsandValue().get().booleanValue()) {
            callbackInfo.cancel();
        }
    }
}
