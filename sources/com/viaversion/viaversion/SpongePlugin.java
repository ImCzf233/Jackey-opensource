package com.viaversion.viaversion;

import com.google.inject.Inject;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.ViaAPI;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.configuration.ConfigurationProvider;
import com.viaversion.viaversion.api.data.MappingDataLoader;
import com.viaversion.viaversion.api.platform.PlatformTask;
import com.viaversion.viaversion.api.platform.ViaPlatform;
import com.viaversion.viaversion.dump.PluginInfo;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.sponge.commands.SpongeCommandHandler;
import com.viaversion.viaversion.sponge.commands.SpongePlayer;
import com.viaversion.viaversion.sponge.platform.SpongeViaAPI;
import com.viaversion.viaversion.sponge.platform.SpongeViaConfig;
import com.viaversion.viaversion.sponge.platform.SpongeViaInjector;
import com.viaversion.viaversion.sponge.platform.SpongeViaLoader;
import com.viaversion.viaversion.sponge.platform.SpongeViaTask;
import com.viaversion.viaversion.sponge.util.LoggerWrapper;
import com.viaversion.viaversion.util.GsonUtil;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.spongepowered.api.Game;
import org.spongepowered.api.Platform;
import org.spongepowered.api.Server;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.Command;
import org.spongepowered.api.command.registrar.CommandRegistrar;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.ConstructPluginEvent;
import org.spongepowered.api.event.lifecycle.StartingEngineEvent;
import org.spongepowered.api.event.lifecycle.StoppingEngineEvent;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.util.Ticks;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.builtin.jvm.Plugin;
import org.spongepowered.plugin.metadata.PluginMetadata;

@Plugin("viaversion")
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/SpongePlugin.class */
public class SpongePlugin implements ViaPlatform<Player> {
    public static final LegacyComponentSerializer LEGACY_SERIALIZER = LegacyComponentSerializer.builder().extractUrls().build();
    private final SpongeViaAPI api = new SpongeViaAPI();
    private final PluginContainer container;
    private final Game game;
    private final Logger logger;
    private SpongeViaConfig conf;
    @Inject
    @ConfigDir(sharedRoot = false)
    private Path configDir;

    @Inject
    SpongePlugin(PluginContainer container, Game game, org.apache.logging.log4j.Logger logger) {
        this.container = container;
        this.game = game;
        this.logger = new LoggerWrapper(logger);
    }

    @Listener
    public void constructPlugin(ConstructPluginEvent event) {
        this.conf = new SpongeViaConfig(this.configDir.toFile());
        this.logger.info("ViaVersion " + getPluginVersion() + " is now loaded!");
        Via.init(ViaManagerImpl.builder().platform(this).commandHandler(new SpongeCommandHandler()).injector(new SpongeViaInjector()).loader(new SpongeViaLoader(this)).build());
    }

    @Listener
    public void onServerStart(StartingEngineEvent<Server> event) {
        ((CommandRegistrar) Sponge.server().commandManager().registrar(Command.Raw.class).get()).register(this.container, Via.getManager().getCommandHandler(), "viaversion", new String[]{"viaver", "vvsponge"});
        if (this.game.pluginManager().plugin("viabackwards").isPresent()) {
            MappingDataLoader.enableMappingsCache();
        }
        this.logger.info("ViaVersion is injecting!");
        ((ViaManagerImpl) Via.getManager()).init();
    }

    @Listener
    public void onServerStop(StoppingEngineEvent<Server> event) {
        ((ViaManagerImpl) Via.getManager()).destroy();
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public String getPlatformName() {
        return (String) this.game.platform().container(Platform.Component.IMPLEMENTATION).metadata().name().orElse("unknown");
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public String getPlatformVersion() {
        return this.game.platform().container(Platform.Component.IMPLEMENTATION).metadata().version().toString();
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public String getPluginVersion() {
        return this.container.metadata().version().toString();
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public PlatformTask runAsync(Runnable runnable) {
        Task task = Task.builder().plugin(this.container).execute(runnable).build();
        return new SpongeViaTask(this.game.asyncScheduler().submit(task));
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public PlatformTask runSync(Runnable runnable) {
        Task task = Task.builder().plugin(this.container).execute(runnable).build();
        return new SpongeViaTask(this.game.server().scheduler().submit(task));
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public PlatformTask runSync(Runnable runnable, long ticks) {
        Task task = Task.builder().plugin(this.container).execute(runnable).delay(Ticks.of(ticks)).build();
        return new SpongeViaTask(this.game.server().scheduler().submit(task));
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public PlatformTask runRepeatingSync(Runnable runnable, long ticks) {
        Task task = Task.builder().plugin(this.container).execute(runnable).interval(Ticks.of(ticks)).build();
        return new SpongeViaTask(this.game.server().scheduler().submit(task));
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public ViaCommandSender[] getOnlinePlayers() {
        Collection<ServerPlayer> players = this.game.server().onlinePlayers();
        ViaCommandSender[] array = new ViaCommandSender[players.size()];
        int i = 0;
        for (ServerPlayer player : players) {
            int i2 = i;
            i++;
            array[i2] = new SpongePlayer(player);
        }
        return array;
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public void sendMessage(UUID uuid, String message) {
        this.game.server().player(uuid).ifPresent(player -> {
            player.sendMessage(LEGACY_SERIALIZER.deserialize(message));
        });
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public boolean kickPlayer(UUID uuid, String message) {
        return ((Boolean) this.game.server().player(uuid).map(player -> {
            player.kick(LegacyComponentSerializer.legacySection().deserialize(message));
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
    public void onReload() {
        this.logger.severe("ViaVersion is already loaded, this should work fine. If you get any console errors, try rebooting.");
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public JsonObject getDump() {
        JsonObject platformSpecific = new JsonObject();
        List<PluginInfo> plugins = new ArrayList<>();
        for (PluginContainer plugin : this.game.pluginManager().plugins()) {
            PluginMetadata metadata = plugin.metadata();
            plugins.add(new PluginInfo(true, (String) metadata.name().orElse("Unknown"), metadata.version().toString(), plugin.instance().getClass().getCanonicalName(), (List) metadata.contributors().stream().map((v0) -> {
                return v0.name();
            }).collect(Collectors.toList())));
        }
        platformSpecific.add("plugins", GsonUtil.getGson().toJsonTree(plugins));
        return platformSpecific;
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public boolean isOldClientsAllowed() {
        return true;
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    /* renamed from: getApi */
    public ViaAPI<Player> getApi2() {
        return this.api;
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public SpongeViaConfig getConf() {
        return this.conf;
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public Logger getLogger() {
        return this.logger;
    }

    public PluginContainer container() {
        return this.container;
    }
}
