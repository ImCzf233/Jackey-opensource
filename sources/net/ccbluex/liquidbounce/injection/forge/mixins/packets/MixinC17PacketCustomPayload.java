package net.ccbluex.liquidbounce.injection.forge.mixins.packets;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({C17PacketCustomPayload.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/packets/MixinC17PacketCustomPayload.class */
public class MixinC17PacketCustomPayload {
    @Shadow
    private PacketBuffer field_149561_c;

    @Inject(method = {"processPacket(Lnet/minecraft/network/play/INetHandlerPlayServer;)V"}, m23at = {@AbstractC1790At("TAIL")})
    private void releaseData(INetHandlerPlayServer handler, CallbackInfo ci) {
        if (this.field_149561_c != null) {
            this.field_149561_c.release();
        }
    }
}
