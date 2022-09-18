package com.viaversion.viaversion.bukkit.listeners;

import com.viaversion.viaversion.ViaListener;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.Protocol;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/bukkit/listeners/ViaBukkitListener.class */
public class ViaBukkitListener extends ViaListener implements Listener {
    private final Plugin plugin;

    public ViaBukkitListener(Plugin plugin, Class<? extends Protocol> requiredPipeline) {
        super(requiredPipeline);
        this.plugin = plugin;
    }

    public UserConnection getUserConnection(Player player) {
        return getUserConnection(player.getUniqueId());
    }

    public boolean isOnPipe(Player player) {
        return isOnPipe(player.getUniqueId());
    }

    @Override // com.viaversion.viaversion.ViaListener
    public void register() {
        if (isRegistered()) {
            return;
        }
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
        setRegistered(true);
    }

    public Plugin getPlugin() {
        return this.plugin;
    }
}
