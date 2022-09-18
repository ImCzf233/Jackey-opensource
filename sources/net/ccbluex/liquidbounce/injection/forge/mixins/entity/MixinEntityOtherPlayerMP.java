package net.ccbluex.liquidbounce.injection.forge.mixins.entity;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({EntityOtherPlayerMP.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/entity/MixinEntityOtherPlayerMP.class */
public class MixinEntityOtherPlayerMP {
    @Inject(method = {"onLivingUpdate"}, m23at = {@AbstractC1790At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityOtherPlayerMP;updateArmSwingProgress()V", shift = AbstractC1790At.Shift.AFTER)}, cancellable = true)
    private void removeUselessAnimations(CallbackInfo ci) {
        ci.cancel();
    }
}
