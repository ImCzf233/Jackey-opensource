package com.viaversion.viaversion.bukkit.listeners.multiversion;

import com.viaversion.viaversion.ViaVersionPlugin;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.bukkit.listeners.ViaBukkitListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.WeakHashMap;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/bukkit/listeners/multiversion/PlayerSneakListener.class */
public class PlayerSneakListener extends ViaBukkitListener {
    private static final float STANDING_HEIGHT = 1.8f;
    private static final float HEIGHT_1_14 = 1.5f;
    private static final float HEIGHT_1_9 = 1.6f;
    private static final float DEFAULT_WIDTH = 0.6f;
    private final boolean is1_9Fix;
    private final boolean is1_14Fix;
    private Map<Player, Boolean> sneaking;
    private Set<UUID> sneakingUuids;
    private final Method getHandle;
    private Method setSize;
    private boolean useCache;

    public PlayerSneakListener(ViaVersionPlugin plugin, boolean is1_9Fix, boolean is1_14Fix) throws ReflectiveOperationException {
        super(plugin, null);
        this.is1_9Fix = is1_9Fix;
        this.is1_14Fix = is1_14Fix;
        String packageName = plugin.getServer().getClass().getPackage().getName();
        this.getHandle = Class.forName(packageName + ".entity.CraftPlayer").getMethod("getHandle", new Class[0]);
        Class<?> entityPlayerClass = Class.forName(packageName.replace("org.bukkit.craftbukkit", "net.minecraft.server") + ".EntityPlayer");
        try {
            this.setSize = entityPlayerClass.getMethod("setSize", Float.TYPE, Float.TYPE);
        } catch (NoSuchMethodException e) {
            this.setSize = entityPlayerClass.getMethod("a", Float.TYPE, Float.TYPE);
        }
        if (Via.getAPI().getServerVersion().lowestSupportedVersion() >= ProtocolVersion.v1_9.getVersion()) {
            this.sneaking = new WeakHashMap();
            this.useCache = true;
            plugin.getServer().getScheduler().runTaskTimer(plugin, new Runnable() { // from class: com.viaversion.viaversion.bukkit.listeners.multiversion.PlayerSneakListener.1
                @Override // java.lang.Runnable
                public void run() {
                    for (Map.Entry<Player, Boolean> entry : PlayerSneakListener.this.sneaking.entrySet()) {
                        PlayerSneakListener.this.setHeight(entry.getKey(), entry.getValue().booleanValue() ? PlayerSneakListener.HEIGHT_1_14 : PlayerSneakListener.HEIGHT_1_9);
                    }
                }
            }, 1L, 1L);
        }
        if (is1_14Fix) {
            this.sneakingUuids = new HashSet();
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void playerToggleSneak(PlayerToggleSneakEvent event) {
        ProtocolInfo info;
        Player player = event.getPlayer();
        UserConnection userConnection = getUserConnection(player);
        if (userConnection == null || (info = userConnection.getProtocolInfo()) == null) {
            return;
        }
        int protocolVersion = info.getProtocolVersion();
        if (this.is1_14Fix && protocolVersion >= ProtocolVersion.v1_14.getVersion()) {
            setHeight(player, event.isSneaking() ? HEIGHT_1_14 : STANDING_HEIGHT);
            if (event.isSneaking()) {
                this.sneakingUuids.add(player.getUniqueId());
            } else {
                this.sneakingUuids.remove(player.getUniqueId());
            }
            if (!this.useCache) {
                return;
            }
            if (event.isSneaking()) {
                this.sneaking.put(player, true);
            } else {
                this.sneaking.remove(player);
            }
        } else if (this.is1_9Fix && protocolVersion >= ProtocolVersion.v1_9.getVersion()) {
            setHeight(player, event.isSneaking() ? HEIGHT_1_9 : STANDING_HEIGHT);
            if (!this.useCache) {
                return;
            }
            if (event.isSneaking()) {
                this.sneaking.put(player, false);
            } else {
                this.sneaking.remove(player);
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void playerDamage(EntityDamageEvent event) {
        if (this.is1_14Fix && event.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION && event.getEntityType() == EntityType.PLAYER) {
            Player player = event.getEntity();
            if (!this.sneakingUuids.contains(player.getUniqueId())) {
                return;
            }
            double y = player.getEyeLocation().getY() + 0.045d;
            if (y - ((int) y) < 0.09d) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void playerQuit(PlayerQuitEvent event) {
        if (this.sneaking != null) {
            this.sneaking.remove(event.getPlayer());
        }
        if (this.sneakingUuids != null) {
            this.sneakingUuids.remove(event.getPlayer().getUniqueId());
        }
    }

    public void setHeight(Player player, float height) {
        try {
            this.setSize.invoke(this.getHandle.invoke(player, new Object[0]), Float.valueOf((float) DEFAULT_WIDTH), Float.valueOf(height));
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
