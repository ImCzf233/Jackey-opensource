package net.ccbluex.liquidbounce.injection.forge.mixins.entity;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.ClickWindowEvent;
import net.ccbluex.liquidbounce.features.module.modules.exploit.AbortBreaking;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({PlayerControllerMP.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/entity/MixinPlayerControllerMP.class */
public class MixinPlayerControllerMP {
    @Inject(method = {"attackEntity"}, m23at = {@AbstractC1790At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;syncCurrentPlayItem()V")})
    private void attackEntity(EntityPlayer entityPlayer, Entity targetEntity, CallbackInfo callbackInfo) {
        LiquidBounce.eventManager.callEvent(new AttackEvent(targetEntity));
    }

    @Inject(method = {"getIsHittingBlock"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true)
    private void getIsHittingBlock(CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (LiquidBounce.moduleManager.getModule(AbortBreaking.class).getState()) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }

    @Inject(method = {"windowClick"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true)
    private void windowClick(int windowId, int slotId, int mouseButtonClicked, int mode, EntityPlayer playerIn, CallbackInfoReturnable<ItemStack> callbackInfo) {
        ClickWindowEvent event = new ClickWindowEvent(windowId, slotId, mouseButtonClicked, mode);
        LiquidBounce.eventManager.callEvent(event);
        if (event.isCancelled()) {
            callbackInfo.cancel();
        }
    }
}
