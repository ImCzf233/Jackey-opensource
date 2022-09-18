package com.viaversion.viaversion;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.ViaAPI;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.configuration.ConfigurationProvider;
import com.viaversion.viaversion.api.data.MappingDataLoader;
import com.viaversion.viaversion.api.platform.PlatformTask;
import com.viaversion.viaversion.api.platform.ViaPlatform;
import com.viaversion.viaversion.bungee.commands.BungeeCommand;
import com.viaversion.viaversion.bungee.commands.BungeeCommandHandler;
import com.viaversion.viaversion.bungee.commands.BungeeCommandSender;
import com.viaversion.viaversion.bungee.platform.BungeeViaAPI;
import com.viaversion.viaversion.bungee.platform.BungeeViaConfig;
import com.viaversion.viaversion.bungee.platform.BungeeViaInjector;
import com.viaversion.viaversion.bungee.platform.BungeeViaLoader;
import com.viaversion.viaversion.bungee.platform.BungeeViaTask;
import com.viaversion.viaversion.bungee.service.ProtocolDetectorService;
import com.viaversion.viaversion.dump.PluginInfo;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.util.GsonUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.protocol.ProtocolConstants;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/BungeePlugin.class */
public class BungeePlugin extends Plugin implements ViaPlatform<ProxiedPlayer>, Listener {
    private BungeeViaAPI api;
    private BungeeViaConfig config;

    public void onLoad() {
        try {
            ProtocolConstants.class.getField("MINECRAFT_1_18");
        } catch (NoSuchFieldException e) {
            getLogger().warning("      / \\");
            getLogger().warning("     /   \\");
            getLogger().warning("    /  |  \\");
            getLogger().warning("   /   |   \\         BUNGEECORD IS OUTDATED");
            getLogger().warning("  /         \\   VIAVERSION MAY NOT WORK AS INTENDED");
            getLogger().warning(" /     o     \\");
            getLogger().warning("/_____________\\");
        }
        this.api = new BungeeViaAPI();
        this.config = new BungeeViaConfig(getDataFolder());
        BungeeCommandHandler commandHandler = new BungeeCommandHandler();
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new BungeeCommand(commandHandler));
        Via.init(ViaManagerImpl.builder().platform(this).injector(new BungeeViaInjector()).loader(new BungeeViaLoader(this)).commandHandler(commandHandler).build());
    }

    public void onEnable() {
        if (ProxyServer.getInstance().getPluginManager().getPlugin("ViaBackwards") != null) {
            MappingDataLoader.enableMappingsCache();
        }
        ((ViaManagerImpl) Via.getManager()).init();
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public String getPlatformName() {
        return getProxy().getName();
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public String getPlatformVersion() {
        return getProxy().getVersion();
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public boolean isProxy() {
        return true;
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public String getPluginVersion() {
        return getDescription().getVersion();
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public PlatformTask runAsync(Runnable runnable) {
        return new BungeeViaTask(getProxy().getScheduler().runAsync(this, runnable));
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public PlatformTask runSync(Runnable runnable) {
        return runAsync(runnable);
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public PlatformTask runSync(Runnable runnable, long ticks) {
        return new BungeeViaTask(getProxy().getScheduler().schedule(this, runnable, ticks * 50, TimeUnit.MILLISECONDS));
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public PlatformTask runRepeatingSync(Runnable runnable, long ticks) {
        return new BungeeViaTask(getProxy().getScheduler().schedule(this, runnable, 0L, ticks * 50, TimeUnit.MILLISECONDS));
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public ViaCommandSender[] getOnlinePlayers() {
        Collection<ProxiedPlayer> players = getProxy().getPlayers();
        ViaCommandSender[] array = new ViaCommandSender[players.size()];
        int i = 0;
        for (ProxiedPlayer player : players) {
            int i2 = i;
            i++;
            array[i2] = new BungeeCommandSender(player);
        }
        return array;
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public void sendMessage(UUID uuid, String message) {
        getProxy().getPlayer(uuid).sendMessage(message);
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public boolean kickPlayer(UUID uuid, String message) {
        ProxiedPlayer player = getProxy().getPlayer(uuid);
        if (player != null) {
            player.disconnect(message);
            return true;
        }
        return false;
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public boolean isPluginEnabled() {
        return true;
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public ViaAPI<ProxiedPlayer> getApi() {
        return this.api;
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public BungeeViaConfig getConf() {
        return this.config;
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public ConfigurationProvider getConfigurationProvider() {
        return this.config;
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public void onReload() {
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public JsonObject getDump() {
        JsonObject platformSpecific = new JsonObject();
        List<PluginInfo> plugins = new ArrayList<>();
        for (Plugin p : ProxyServer.getInstance().getPluginManager().getPlugins()) {
            plugins.add(new PluginInfo(true, p.getDescription().getName(), p.getDescription().getVersion(), p.getDescription().getMain(), Collections.singletonList(p.getDescription().getAuthor())));
        }
        platformSpecific.add("plugins", GsonUtil.getGson().toJsonTree(plugins));
        platformSpecific.add("servers", GsonUtil.getGson().toJsonTree(ProtocolDetectorService.getDetectedIds()));
        return platformSpecific;
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public boolean isOldClientsAllowed() {
        return true;
    }
}
