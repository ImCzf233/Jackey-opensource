package com.viaversion.viaversion.bungee.handlers;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.connection.UserConnectionImpl;
import com.viaversion.viaversion.protocol.ProtocolPipelineImpl;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import java.lang.reflect.Method;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/bungee/handlers/BungeeChannelInitializer.class */
public class BungeeChannelInitializer extends ChannelInitializer<Channel> {
    private final ChannelInitializer<Channel> original;
    private Method method;

    public BungeeChannelInitializer(ChannelInitializer<Channel> oldInit) {
        this.original = oldInit;
        try {
            this.method = ChannelInitializer.class.getDeclaredMethod("initChannel", Channel.class);
            this.method.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    protected void initChannel(Channel socketChannel) throws Exception {
        if (!socketChannel.isActive()) {
            return;
        }
        UserConnection info = new UserConnectionImpl(socketChannel);
        new ProtocolPipelineImpl(info);
        this.method.invoke(this.original, socketChannel);
        if (!socketChannel.isActive() || socketChannel.pipeline().get("packet-encoder") == null || socketChannel.pipeline().get("packet-decoder") == null) {
            return;
        }
        BungeeEncodeHandler encoder = new BungeeEncodeHandler(info);
        BungeeDecodeHandler decoder = new BungeeDecodeHandler(info);
        socketChannel.pipeline().addBefore("packet-encoder", "via-encoder", encoder);
        socketChannel.pipeline().addBefore("packet-decoder", "via-decoder", decoder);
    }

    public ChannelInitializer<Channel> getOriginal() {
        return this.original;
    }
}
