package net.ccbluex.liquidbounce.injection.forge.mixins.patcher.performance;

import io.netty.buffer.ByteBuf;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin({MinecraftServer.class})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/patcher/performance/MixinMinecraftServer.class */
public class MixinMinecraftServer {
    @ModifyVariable(method = {"addFaviconToStatusResponse"}, m18at = @AbstractC1790At(value = "INVOKE", target = "Lnet/minecraft/network/ServerStatusResponse;setFavicon(Ljava/lang/String;)V", shift = AbstractC1790At.Shift.AFTER), ordinal = 1)
    private ByteBuf patcher$releaseByteBuf(ByteBuf buf1) {
        buf1.release();
        return buf1;
    }
}
