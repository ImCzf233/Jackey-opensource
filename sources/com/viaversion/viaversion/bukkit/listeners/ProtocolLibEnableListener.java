package com.viaversion.viaversion.bukkit.listeners;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.bukkit.platform.BukkitViaInjector;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/bukkit/listeners/ProtocolLibEnableListener.class */
public class ProtocolLibEnableListener implements Listener {
    @EventHandler
    public void onPluginEnable(PluginEnableEvent e) {
        if (e.getPlugin().getName().equals("ProtocolLib")) {
            ((BukkitViaInjector) Via.getManager().getInjector()).setProtocolLib(true);
        }
    }

    @EventHandler
    public void onPluginDisable(PluginDisableEvent e) {
        if (e.getPlugin().getName().equals("ProtocolLib")) {
            ((BukkitViaInjector) Via.getManager().getInjector()).setProtocolLib(false);
        }
    }
}
