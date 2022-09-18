package com.viaversion.viaversion.bungee.platform;

import com.viaversion.viaversion.BungeePlugin;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.platform.ViaPlatformLoader;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.protocol.version.VersionProvider;
import com.viaversion.viaversion.bungee.handlers.BungeeServerHandler;
import com.viaversion.viaversion.bungee.listeners.ElytraPatch;
import com.viaversion.viaversion.bungee.listeners.UpdateListener;
import com.viaversion.viaversion.bungee.providers.BungeeBossBarProvider;
import com.viaversion.viaversion.bungee.providers.BungeeEntityIdProvider;
import com.viaversion.viaversion.bungee.providers.BungeeMainHandProvider;
import com.viaversion.viaversion.bungee.providers.BungeeMovementTransmitter;
import com.viaversion.viaversion.bungee.providers.BungeeVersionProvider;
import com.viaversion.viaversion.bungee.service.ProtocolDetectorService;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.BossBarProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.EntityIdProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.MainHandProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.MovementTransmitterProvider;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.scheduler.ScheduledTask;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/bungee/platform/BungeeViaLoader.class */
public class BungeeViaLoader implements ViaPlatformLoader {
    private final BungeePlugin plugin;
    private final Set<Listener> listeners = new HashSet();
    private final Set<ScheduledTask> tasks = new HashSet();

    public BungeeViaLoader(BungeePlugin plugin) {
        this.plugin = plugin;
    }

    private void registerListener(Listener listener) {
        this.listeners.add(listener);
        ProxyServer.getInstance().getPluginManager().registerListener(this.plugin, listener);
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatformLoader
    public void load() {
        registerListener(this.plugin);
        registerListener(new UpdateListener());
        registerListener(new BungeeServerHandler());
        if (Via.getAPI().getServerVersion().lowestSupportedVersion() < ProtocolVersion.v1_9.getVersion()) {
            registerListener(new ElytraPatch());
        }
        Via.getManager().getProviders().use(VersionProvider.class, new BungeeVersionProvider());
        Via.getManager().getProviders().use(EntityIdProvider.class, new BungeeEntityIdProvider());
        if (Via.getAPI().getServerVersion().lowestSupportedVersion() < ProtocolVersion.v1_9.getVersion()) {
            Via.getManager().getProviders().use(MovementTransmitterProvider.class, new BungeeMovementTransmitter());
            Via.getManager().getProviders().use(BossBarProvider.class, new BungeeBossBarProvider());
            Via.getManager().getProviders().use(MainHandProvider.class, new BungeeMainHandProvider());
        }
        if (this.plugin.getConf().getBungeePingInterval() > 0) {
            this.tasks.add(this.plugin.getProxy().getScheduler().schedule(this.plugin, new ProtocolDetectorService(this.plugin), 0L, this.plugin.getConf().getBungeePingInterval(), TimeUnit.SECONDS));
        }
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatformLoader
    public void unload() {
        for (Listener listener : this.listeners) {
            ProxyServer.getInstance().getPluginManager().unregisterListener(listener);
        }
        this.listeners.clear();
        for (ScheduledTask task : this.tasks) {
            task.cancel();
        }
        this.tasks.clear();
    }
}
