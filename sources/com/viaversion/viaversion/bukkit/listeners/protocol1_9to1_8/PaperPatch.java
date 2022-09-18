package com.viaversion.viaversion.bukkit.listeners.protocol1_9to1_8;

import com.viaversion.viaversion.bukkit.listeners.ViaBukkitListener;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.Plugin;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/bukkit/listeners/protocol1_9to1_8/PaperPatch.class */
public class PaperPatch extends ViaBukkitListener {
    public PaperPatch(Plugin plugin) {
        super(plugin, Protocol1_9To1_8.class);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onPlace(BlockPlaceEvent e) {
        if (isOnPipe(e.getPlayer())) {
            Material block = e.getBlockPlaced().getType();
            if (isPlacable(block)) {
                return;
            }
            Location location = e.getPlayer().getLocation();
            Block locationBlock = location.getBlock();
            if (locationBlock.equals(e.getBlock())) {
                e.setCancelled(true);
            } else if (locationBlock.getRelative(BlockFace.UP).equals(e.getBlock())) {
                e.setCancelled(true);
            } else {
                Location diff = location.clone().subtract(e.getBlock().getLocation().add(0.5d, 0.0d, 0.5d));
                if (Math.abs(diff.getX()) <= 0.8d && Math.abs(diff.getZ()) <= 0.8d) {
                    if (diff.getY() <= 0.1d && diff.getY() >= -0.1d) {
                        e.setCancelled(true);
                        return;
                    }
                    BlockFace relative = e.getBlockAgainst().getFace(e.getBlock());
                    if (relative == BlockFace.UP && diff.getY() < 1.0d && diff.getY() >= 0.0d) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    private boolean isPlacable(Material material) {
        if (!material.isSolid()) {
            return true;
        }
        switch (material.getId()) {
            case 63:
            case 68:
            case 176:
            case 177:
                return true;
            default:
                return false;
        }
    }
}
