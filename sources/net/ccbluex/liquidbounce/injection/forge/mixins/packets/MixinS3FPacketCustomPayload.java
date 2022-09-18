package net.ccbluex.liquidbounce.injection.forge.mixins.packets;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({S3FPacketCustomPayload.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/packets/MixinS3FPacketCustomPayload.class */
public class MixinS3FPacketCustomPayload {
    @Shadow
    private PacketBuffer field_149171_b;

    @Inject(method = {"processPacket(Lnet/minecraft/network/play/INetHandlerPlayClient;)V"}, m23at = {@AbstractC1790At("TAIL")})
    private void releaseData(INetHandlerPlayClient handler, CallbackInfo ci) {
        if (this.field_149171_b != null) {
            this.field_149171_b.release();
        }
    }
}
