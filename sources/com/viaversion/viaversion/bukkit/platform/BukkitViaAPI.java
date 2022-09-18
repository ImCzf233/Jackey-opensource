package com.viaversion.viaversion.bukkit.platform;

import com.viaversion.viaversion.ViaAPIBase;
import com.viaversion.viaversion.ViaVersionPlugin;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.bukkit.util.ProtocolSupportUtil;
import io.netty.buffer.ByteBuf;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/bukkit/platform/BukkitViaAPI.class */
public class BukkitViaAPI extends ViaAPIBase<Player> {
    private final ViaVersionPlugin plugin;

    public BukkitViaAPI(ViaVersionPlugin plugin) {
        this.plugin = plugin;
    }

    public int getPlayerVersion(Player player) {
        return getPlayerVersion(player.getUniqueId());
    }

    @Override // com.viaversion.viaversion.ViaAPIBase, com.viaversion.viaversion.api.ViaAPI
    public int getPlayerVersion(UUID uuid) {
        UserConnection connection = Via.getManager().getConnectionManager().getConnectedClient(uuid);
        if (connection == null) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null && isProtocolSupport()) {
                return ProtocolSupportUtil.getProtocolVersion(player);
            }
            return -1;
        }
        return connection.getProtocolInfo().getProtocolVersion();
    }

    public void sendRawPacket(Player player, ByteBuf packet) throws IllegalArgumentException {
        sendRawPacket(player.getUniqueId(), packet);
    }

    public boolean isCompatSpigotBuild() {
        return this.plugin.isCompatSpigotBuild();
    }

    public boolean isProtocolSupport() {
        return this.plugin.isProtocolSupport();
    }
}
