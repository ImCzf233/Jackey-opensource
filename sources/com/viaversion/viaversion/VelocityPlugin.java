package com.viaversion.viaversion;

import com.google.inject.Inject;
import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.ViaAPI;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.configuration.ConfigurationProvider;
import com.viaversion.viaversion.api.data.MappingDataLoader;
import com.viaversion.viaversion.api.platform.PlatformTask;
import com.viaversion.viaversion.api.platform.ViaPlatform;
import com.viaversion.viaversion.dump.PluginInfo;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.util.GsonUtil;
import com.viaversion.viaversion.velocity.command.VelocityCommandHandler;
import com.viaversion.viaversion.velocity.command.VelocityCommandSender;
import com.viaversion.viaversion.velocity.platform.VelocityViaAPI;
import com.viaversion.viaversion.velocity.platform.VelocityViaConfig;
import com.viaversion.viaversion.velocity.platform.VelocityViaInjector;
import com.viaversion.viaversion.velocity.platform.VelocityViaLoader;
import com.viaversion.viaversion.velocity.platform.VelocityViaTask;
import com.viaversion.viaversion.velocity.service.ProtocolDetectorService;
import com.viaversion.viaversion.velocity.util.LoggerWrapper;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.slf4j.Logger;

@Plugin(id = "viaversion", name = "ViaVersion", version = "4.2.1", authors = {"_MylesC", "creeper123123321", "Gerrygames", "kennytv", "Matsv"}, description = "Allow newer Minecraft versions to connect to an older server version.", url = "https://viaversion.com")
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/VelocityPlugin.class */
public class VelocityPlugin implements ViaPlatform<Player> {
    public static final LegacyComponentSerializer COMPONENT_SERIALIZER = LegacyComponentSerializer.builder().character(167).extractUrls().build();
    public static ProxyServer PROXY;
    @Inject
    private ProxyServer proxy;
    @Inject
    private Logger loggerslf4j;
    @Inject
    @DataDirectory
    private Path configDir;
    private VelocityViaAPI api;
    private java.util.logging.Logger logger;
    private VelocityViaConfig conf;

    @Subscribe
    public void onProxyInit(ProxyInitializeEvent e) {
        if (!hasConnectionEvent()) {
            Logger logger = this.loggerslf4j;
            logger.error("      / \\");
            logger.error("     /   \\");
            logger.error("    /  |  \\");
            logger.error("   /   |   \\        VELOCITY 3.0.0 IS REQUIRED");
            logger.error("  /         \\   VIAVERSION WILL NOT WORK AS INTENDED");
            logger.error(" /     o     \\");
            logger.error("/_____________\\");
        }
        PROXY = this.proxy;
        VelocityCommandHandler commandHandler = new VelocityCommandHandler();
        PROXY.getCommandManager().register("viaver", commandHandler, new String[]{"vvvelocity", "viaversion"});
        this.api = new VelocityViaAPI();
        this.conf = new VelocityViaConfig(this.configDir.toFile());
        this.logger = new LoggerWrapper(this.loggerslf4j);
        Via.init(ViaManagerImpl.builder().platform(this).commandHandler(commandHandler).loader(new VelocityViaLoader()).injector(new VelocityViaInjector()).build());
        if (this.proxy.getPluginManager().getPlugin("viabackwards").isPresent()) {
            MappingDataLoader.enableMappingsCache();
        }
    }

    @Subscribe(order = PostOrder.LAST)
    public void onProxyLateInit(ProxyInitializeEvent e) {
        ((ViaManagerImpl) Via.getManager()).init();
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public String getPlatformName() {
        String proxyImpl = ProxyServer.class.getPackage().getImplementationTitle();
        return proxyImpl != null ? proxyImpl : "Velocity";
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public String getPlatformVersion() {
        String version = ProxyServer.class.getPackage().getImplementationVersion();
        return version != null ? version : "Unknown";
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public boolean isProxy() {
        return true;
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public String getPluginVersion() {
        return "4.2.1";
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public PlatformTask runAsync(Runnable runnable) {
        return runSync(runnable);
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public PlatformTask runSync(Runnable runnable) {
        return runSync(runnable, 0L);
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public PlatformTask runSync(Runnable runnable, long ticks) {
        return new VelocityViaTask(PROXY.getScheduler().buildTask(this, runnable).delay(ticks * 50, TimeUnit.MILLISECONDS).schedule());
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public PlatformTask runRepeatingSync(Runnable runnable, long ticks) {
        return new VelocityViaTask(PROXY.getScheduler().buildTask(this, runnable).repeat(ticks * 50, TimeUnit.MILLISECONDS).schedule());
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public ViaCommandSender[] getOnlinePlayers() {
        return (ViaCommandSender[]) PROXY.getAllPlayers().stream().map((v1) -> {
            return new VelocityCommandSender(v1);
        }).toArray(x$0 -> {
            return new ViaCommandSender[x$0];
        });
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public void sendMessage(UUID uuid, String message) {
        PROXY.getPlayer(uuid).ifPresent(player -> {
            player.sendMessage(COMPONENT_SERIALIZER.deserialize(message));
        });
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public boolean kickPlayer(UUID uuid, String message) {
        return ((Boolean) PROXY.getPlayer(uuid).map(it -> {
            it.disconnect(LegacyComponentSerializer.legacySection().deserialize(message));
            return true;
        }).orElse(false)).booleanValue();
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public boolean isPluginEnabled() {
        return true;
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public ConfigurationProvider getConfigurationProvider() {
        return this.conf;
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public File getDataFolder() {
        return this.configDir.toFile();
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    /* renamed from: getApi */
    public ViaAPI<Player> getApi2() {
        return this.api;
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public VelocityViaConfig getConf() {
        return this.conf;
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public void onReload() {
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public JsonObject getDump() {
        JsonObject extra = new JsonObject();
        List<PluginInfo> plugins = new ArrayList<>();
        for (PluginContainer p : PROXY.getPluginManager().getPlugins()) {
            plugins.add(new PluginInfo(true, (String) p.getDescription().getName().orElse(p.getDescription().getId()), (String) p.getDescription().getVersion().orElse("Unknown Version"), p.getInstance().isPresent() ? p.getInstance().get().getClass().getCanonicalName() : "Unknown", p.getDescription().getAuthors()));
        }
        extra.add("plugins", GsonUtil.getGson().toJsonTree(plugins));
        extra.add("servers", GsonUtil.getGson().toJsonTree(ProtocolDetectorService.getDetectedIds()));
        return extra;
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public boolean isOldClientsAllowed() {
        return true;
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public java.util.logging.Logger getLogger() {
        return this.logger;
    }

    private boolean hasConnectionEvent() {
        try {
            Class.forName("com.velocitypowered.proxy.protocol.VelocityConnectionEvent");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
