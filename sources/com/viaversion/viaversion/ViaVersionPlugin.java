package com.viaversion.viaversion;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.ViaAPI;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.configuration.ConfigurationProvider;
import com.viaversion.viaversion.api.data.MappingDataLoader;
import com.viaversion.viaversion.api.platform.PlatformTask;
import com.viaversion.viaversion.api.platform.UnsupportedSoftware;
import com.viaversion.viaversion.api.platform.ViaPlatform;
import com.viaversion.viaversion.bukkit.classgenerator.ClassGenerator;
import com.viaversion.viaversion.bukkit.commands.BukkitCommandHandler;
import com.viaversion.viaversion.bukkit.commands.BukkitCommandSender;
import com.viaversion.viaversion.bukkit.listeners.ProtocolLibEnableListener;
import com.viaversion.viaversion.bukkit.platform.BukkitViaAPI;
import com.viaversion.viaversion.bukkit.platform.BukkitViaConfig;
import com.viaversion.viaversion.bukkit.platform.BukkitViaInjector;
import com.viaversion.viaversion.bukkit.platform.BukkitViaLoader;
import com.viaversion.viaversion.bukkit.platform.BukkitViaTask;
import com.viaversion.viaversion.bukkit.util.NMSUtil;
import com.viaversion.viaversion.dump.PluginInfo;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.unsupported.UnsupportedSoftwareImpl;
import com.viaversion.viaversion.util.GsonUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/ViaVersionPlugin.class */
public class ViaVersionPlugin extends JavaPlugin implements ViaPlatform<Player> {
    private static ViaVersionPlugin instance;
    private final boolean protocolSupport;
    private boolean compatSpigotBuild;
    private boolean lateBind;
    private final ViaAPI<Player> api = new BukkitViaAPI(this);
    private final List<Runnable> queuedTasks = new ArrayList();
    private final List<Runnable> asyncQueuedTasks = new ArrayList();
    private boolean spigot = true;
    private final BukkitCommandHandler commandHandler = new BukkitCommandHandler();
    private final BukkitViaConfig conf = new BukkitViaConfig();

    public ViaVersionPlugin() {
        instance = this;
        BukkitViaInjector injector = new BukkitViaInjector();
        Via.init(ViaManagerImpl.builder().platform(this).commandHandler(this.commandHandler).injector(injector).loader(new BukkitViaLoader(this)).build());
        this.protocolSupport = Bukkit.getPluginManager().getPlugin("ProtocolSupport") != null;
    }

    public void onLoad() {
        boolean hasProtocolLib = Bukkit.getPluginManager().getPlugin("ProtocolLib") != null;
        ((BukkitViaInjector) Via.getManager().getInjector()).setProtocolLib(hasProtocolLib);
        try {
            Class.forName("org.spigotmc.SpigotConfig");
        } catch (ClassNotFoundException e) {
            this.spigot = false;
        }
        try {
            NMSUtil.nms("PacketEncoder", "net.minecraft.network.PacketEncoder").getDeclaredField("version");
            this.compatSpigotBuild = true;
        } catch (Exception e2) {
            this.compatSpigotBuild = false;
        }
        if (getServer().getPluginManager().getPlugin("ViaBackwards") != null) {
            MappingDataLoader.enableMappingsCache();
        }
        ClassGenerator.generate();
        this.lateBind = !((BukkitViaInjector) Via.getManager().getInjector()).isBinded();
        getLogger().info("ViaVersion " + getDescription().getVersion() + (this.compatSpigotBuild ? "compat" : "") + " is now loaded" + (this.lateBind ? ", waiting for boot. (late-bind)" : ", injecting!"));
        if (!this.lateBind) {
            ((ViaManagerImpl) Via.getManager()).init();
        }
    }

    public void onEnable() {
        if (this.lateBind) {
            ((ViaManagerImpl) Via.getManager()).init();
        }
        getCommand("viaversion").setExecutor(this.commandHandler);
        getCommand("viaversion").setTabCompleter(this.commandHandler);
        getServer().getPluginManager().registerEvents(new ProtocolLibEnableListener(), this);
        if (this.conf.isAntiXRay() && !this.spigot) {
            getLogger().info("You have anti-xray on in your config, since you're not using spigot it won't fix xray!");
        }
        for (Runnable r : this.queuedTasks) {
            Bukkit.getScheduler().runTask(this, r);
        }
        this.queuedTasks.clear();
        for (Runnable r2 : this.asyncQueuedTasks) {
            Bukkit.getScheduler().runTaskAsynchronously(this, r2);
        }
        this.asyncQueuedTasks.clear();
    }

    public void onDisable() {
        ((ViaManagerImpl) Via.getManager()).destroy();
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public String getPlatformName() {
        return Bukkit.getServer().getName();
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public String getPlatformVersion() {
        return Bukkit.getServer().getVersion();
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public String getPluginVersion() {
        return getDescription().getVersion();
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public PlatformTask runAsync(Runnable runnable) {
        if (isPluginEnabled()) {
            return new BukkitViaTask(getServer().getScheduler().runTaskAsynchronously(this, runnable));
        }
        this.asyncQueuedTasks.add(runnable);
        return new BukkitViaTask(null);
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public PlatformTask runSync(Runnable runnable) {
        if (isPluginEnabled()) {
            return new BukkitViaTask(getServer().getScheduler().runTask(this, runnable));
        }
        this.queuedTasks.add(runnable);
        return new BukkitViaTask(null);
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public PlatformTask runSync(Runnable runnable, long ticks) {
        return new BukkitViaTask(getServer().getScheduler().runTaskLater(this, runnable, ticks));
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public PlatformTask runRepeatingSync(Runnable runnable, long ticks) {
        return new BukkitViaTask(getServer().getScheduler().runTaskTimer(this, runnable, 0L, ticks));
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public ViaCommandSender[] getOnlinePlayers() {
        ViaCommandSender[] array = new ViaCommandSender[Bukkit.getOnlinePlayers().size()];
        int i = 0;
        for (Player player : Bukkit.getOnlinePlayers()) {
            int i2 = i;
            i++;
            array[i2] = new BukkitCommandSender(player);
        }
        return array;
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public void sendMessage(UUID uuid, String message) {
        Player player = Bukkit.getPlayer(uuid);
        if (player != null) {
            player.sendMessage(message);
        }
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public boolean kickPlayer(UUID uuid, String message) {
        Player player = Bukkit.getPlayer(uuid);
        if (player != null) {
            player.kickPlayer(message);
            return true;
        }
        return false;
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public boolean isPluginEnabled() {
        return Bukkit.getPluginManager().getPlugin("ViaVersion").isEnabled();
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public ConfigurationProvider getConfigurationProvider() {
        return this.conf;
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public void onReload() {
        if (Bukkit.getPluginManager().getPlugin("ProtocolLib") != null) {
            getLogger().severe("ViaVersion is already loaded, we're going to kick all the players... because otherwise we'll crash because of ProtocolLib.");
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.kickPlayer(ChatColor.translateAlternateColorCodes('&', this.conf.getReloadDisconnectMsg()));
            }
            return;
        }
        getLogger().severe("ViaVersion is already loaded, this should work fine. If you get any console errors, try rebooting.");
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public JsonObject getDump() {
        Plugin[] plugins;
        JsonObject platformSpecific = new JsonObject();
        List<PluginInfo> plugins2 = new ArrayList<>();
        for (Plugin p : Bukkit.getPluginManager().getPlugins()) {
            plugins2.add(new PluginInfo(p.isEnabled(), p.getDescription().getName(), p.getDescription().getVersion(), p.getDescription().getMain(), p.getDescription().getAuthors()));
        }
        platformSpecific.add("plugins", GsonUtil.getGson().toJsonTree(plugins2));
        return platformSpecific;
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public boolean isOldClientsAllowed() {
        return !this.protocolSupport;
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public BukkitViaConfig getConf() {
        return this.conf;
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public ViaAPI<Player> getApi() {
        return this.api;
    }

    @Override // com.viaversion.viaversion.api.platform.ViaPlatform
    public final Collection<UnsupportedSoftware> getUnsupportedSoftwareClasses() {
        List<UnsupportedSoftware> list = new ArrayList<>(super.getUnsupportedSoftwareClasses());
        list.add(new UnsupportedSoftwareImpl.Builder().name("Yatopia").reason(UnsupportedSoftwareImpl.Reason.DANGEROUS_SERVER_SOFTWARE).addClassName("org.yatopiamc.yatopia.server.YatopiaConfig").addClassName("net.yatopia.api.event.PlayerAttackEntityEvent").addClassName("yatopiamc.org.yatopia.server.YatopiaConfig").addMethod("org.bukkit.Server", "getLastTickTime").build());
        return Collections.unmodifiableList(list);
    }

    public boolean isLateBind() {
        return this.lateBind;
    }

    public boolean isCompatSpigotBuild() {
        return this.compatSpigotBuild;
    }

    public boolean isSpigot() {
        return this.spigot;
    }

    public boolean isProtocolSupport() {
        return this.protocolSupport;
    }

    public static ViaVersionPlugin getInstance() {
        return instance;
    }
}
