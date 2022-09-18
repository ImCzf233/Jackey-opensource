package net.ccbluex.liquidbounce.injection.forge.mixins.network;

import io.netty.channel.ChannelHandlerContext;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.modules.render.HUD;
import net.ccbluex.liquidbounce.utils.PacketUtils;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({NetworkManager.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/network/MixinNetworkManager.class */
public class MixinNetworkManager {
    @Inject(method = {"channelRead0"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true)
    private void read(ChannelHandlerContext context, Packet<?> packet, CallbackInfo callback) {
        PacketEvent event = new PacketEvent(packet);
        LiquidBounce.eventManager.callEvent(event);
        if (event.isCancelled()) {
            callback.cancel();
        }
    }

    @Inject(method = {"sendPacket(Lnet/minecraft/network/Packet;)V"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true)
    private void send(Packet<?> packet, CallbackInfo callback) {
        if (PacketUtils.handleSendPacket(packet)) {
            return;
        }
        PacketEvent event = new PacketEvent(packet);
        LiquidBounce.eventManager.callEvent(event);
        if (event.isCancelled()) {
            callback.cancel();
        }
    }

    @Inject(method = {"getIsencrypted"}, m23at = {@AbstractC1790At("HEAD")}, cancellable = true)
    private void injectEncryption(CallbackInfoReturnable<Boolean> cir) {
        HUD hud = (HUD) LiquidBounce.moduleManager.getModule(HUD.class);
        if (hud != null && hud.getTabHead().get().booleanValue()) {
            cir.setReturnValue(true);
        }
    }
}
