package com.viaversion.viaversion.sponge.platform;

import com.viaversion.viaversion.ViaAPIBase;
import io.netty.buffer.ByteBuf;
import org.spongepowered.api.entity.living.player.Player;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/sponge/platform/SpongeViaAPI.class */
public class SpongeViaAPI extends ViaAPIBase<Player> {
    public int getPlayerVersion(Player player) {
        return getPlayerVersion(player.uniqueId());
    }

    public void sendRawPacket(Player player, ByteBuf packet) throws IllegalArgumentException {
        sendRawPacket(player.uniqueId(), packet);
    }
}
