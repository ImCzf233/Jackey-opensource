package net.ccbluex.liquidbounce.injection.forge.mixins.network;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.connection.UserConnectionImpl;
import com.viaversion.viaversion.protocol.ProtocolPipelineImpl;
import de.enzaxd.viaforge.ViaForge;
import de.enzaxd.viaforge.handler.DecodeHandler;
import de.enzaxd.viaforge.handler.EncodeHandler;
import io.netty.channel.Channel;
import io.netty.channel.socket.SocketChannel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.AbstractC1790At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = {"net.minecraft.network.NetworkManager$5"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/injection/forge/mixins/network/MixinNetworkManagerChInit.class */
public abstract class MixinNetworkManagerChInit {
    @Inject(method = {"initChannel"}, m23at = {@AbstractC1790At("TAIL")}, remap = false)
    private void onInitChannel(Channel channel, CallbackInfo ci) {
        if ((channel instanceof SocketChannel) && ViaForge.getInstance().getVersion() != 47) {
            UserConnection user = new UserConnectionImpl(channel, true);
            new ProtocolPipelineImpl(user);
            channel.pipeline().addBefore("encoder", "via-encoder", new EncodeHandler(user)).addBefore("decoder", "via-decoder", new DecodeHandler(user));
        }
    }
}
