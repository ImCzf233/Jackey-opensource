package com.viaversion.viaversion.sponge.platform;

import com.viaversion.viaversion.platform.LegacyViaInjector;
import com.viaversion.viaversion.platform.WrappedChannelInitializer;
import com.viaversion.viaversion.sponge.handlers.SpongeChannelInitializer;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import org.spongepowered.api.MinecraftVersion;
import org.spongepowered.api.Sponge;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/sponge/platform/SpongeViaInjector.class */
public class SpongeViaInjector extends LegacyViaInjector {
    @Override // com.viaversion.viaversion.api.platform.ViaInjector
    public int getServerProtocolVersion() throws ReflectiveOperationException {
        MinecraftVersion version = Sponge.platform().minecraftVersion();
        return ((Integer) version.getClass().getDeclaredMethod("getProtocol", new Class[0]).invoke(version, new Object[0])).intValue();
    }

    @Override // com.viaversion.viaversion.platform.LegacyViaInjector
    protected Object getServerConnection() throws ReflectiveOperationException {
        Class<?> serverClazz = Class.forName("net.minecraft.server.MinecraftServer");
        return serverClazz.getDeclaredMethod("getConnection", new Class[0]).invoke(Sponge.server(), new Object[0]);
    }

    @Override // com.viaversion.viaversion.platform.LegacyViaInjector
    protected WrappedChannelInitializer createChannelInitializer(ChannelInitializer<Channel> oldInitializer) {
        return new SpongeChannelInitializer(oldInitializer);
    }

    @Override // com.viaversion.viaversion.platform.LegacyViaInjector
    protected void blame(ChannelHandler bootstrapAcceptor) {
        throw new RuntimeException("Unable to find core component 'childHandler', please check your plugins. Issue: " + bootstrapAcceptor.getClass().getName());
    }
}
