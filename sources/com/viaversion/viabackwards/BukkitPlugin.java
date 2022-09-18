package com.viaversion.viabackwards;

import com.viaversion.viabackwards.api.ViaBackwardsPlatform;
import com.viaversion.viabackwards.listener.FireDamageListener;
import com.viaversion.viabackwards.listener.FireExtinguishListener;
import com.viaversion.viabackwards.listener.LecternInteractListener;
import com.viaversion.viaversion.ViaVersionPlugin;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.bukkit.platform.BukkitViaLoader;
import org.bukkit.plugin.java.JavaPlugin;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/BukkitPlugin.class */
public class BukkitPlugin extends JavaPlugin implements ViaBackwardsPlatform {
    public void onLoad() {
        if (!ViaVersionPlugin.getInstance().isLateBind()) {
            init();
        }
    }

    public void onEnable() {
        if (ViaVersionPlugin.getInstance().isLateBind()) {
            init();
        }
    }

    private void init() {
        init(getDataFolder());
        Via.getPlatform().runSync(this::onServerLoaded);
    }

    private void onServerLoaded() {
        BukkitViaLoader loader = (BukkitViaLoader) Via.getManager().getLoader();
        int protocolVersion = Via.getAPI().getServerVersion().highestSupportedVersion();
        if (protocolVersion >= ProtocolVersion.v1_16.getVersion()) {
            ((FireExtinguishListener) loader.storeListener(new FireExtinguishListener(this))).register();
        }
        if (protocolVersion >= ProtocolVersion.v1_14.getVersion()) {
            ((LecternInteractListener) loader.storeListener(new LecternInteractListener(this))).register();
        }
        if (protocolVersion >= ProtocolVersion.v1_12.getVersion()) {
            ((FireDamageListener) loader.storeListener(new FireDamageListener(this))).register();
        }
    }

    @Override // com.viaversion.viabackwards.api.ViaBackwardsPlatform
    public void disable() {
        getPluginLoader().disablePlugin(this);
    }
}
