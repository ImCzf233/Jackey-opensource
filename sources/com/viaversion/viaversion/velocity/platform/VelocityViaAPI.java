package com.viaversion.viaversion.velocity.platform;

import com.velocitypowered.api.proxy.Player;
import com.viaversion.viaversion.ViaAPIBase;
import io.netty.buffer.ByteBuf;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/velocity/platform/VelocityViaAPI.class */
public class VelocityViaAPI extends ViaAPIBase<Player> {
    public int getPlayerVersion(Player player) {
        return getPlayerVersion(player.getUniqueId());
    }

    public void sendRawPacket(Player player, ByteBuf packet) throws IllegalArgumentException {
        sendRawPacket(player.getUniqueId(), packet);
    }
}
