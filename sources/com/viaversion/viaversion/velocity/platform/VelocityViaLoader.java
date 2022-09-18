package com.viaversion.viaversion.velocity.platform;

import com.viaversion.viaversion.VelocityPlugin;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.platform.ViaPlatformLoader;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.protocol.version.VersionProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.BossBarProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.MovementTransmitterProvider;
import com.viaversion.viaversion.velocity.listeners.UpdateListener;
import com.viaversion.viaversion.velocity.providers.VelocityBossBarProvider;
import com.viaversion.viaversion.velocity.providers.VelocityMovementTransmitter;
import com.viaversion.viaversion.velocity.providers.VelocityVersionProvider;
import com.viaversion.viaversion.velocity.service.ProtocolDetectorService;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/velocity/platform/VelocityViaLoader.class */
public class VelocityViaLoader implements ViaPlatformLoader {
    @Override // com.viaversion.viaversion.api.platform.ViaPlatformLoader
    public void load() {
        Object plugin = VelocityPlugin.PROXY.getPluginManager().getPlugin("viaversion").flatMap((v0) -> {
            return v0.getInstance();
        }).get();
        if (Via.getAPI().getServerVersion().lowestSupportedVersion() < ProtocolVersion.v1_9.getVersion()) {
            Via.getManager().getProviders().use(MovementTransmitterProvider.class, new VelocityMovementTransmitter());
            Via.getManager().getProviders().use(BossBarProvider.class, new VelocityBossBarProvider());
        }
        Via.getManager().getProviders().use(VersionProvider.class, new VelocityVersionProvider());
        VelocityPlugin.PROXY.getEventManager().register(plugin, new UpdateListener());
        int pingInterval = ((VelocityViaConfig) Via.getPlatform().getConf()).getVelocityPingInterval();
        if (pingInterval > 0) {
            Via.getPlatform().runRepeatingSync(new ProtocolDetectorService(), pingInterval * 20);
        }
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatformLoader
    public void unload() {
    }
}
