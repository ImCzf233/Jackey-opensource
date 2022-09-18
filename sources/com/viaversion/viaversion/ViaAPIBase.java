package com.viaversion.viaversion;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.ViaAPI;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.legacy.LegacyViaAPI;
import com.viaversion.viaversion.api.protocol.version.BlockedProtocolVersions;
import com.viaversion.viaversion.api.protocol.version.ServerProtocolVersion;
import com.viaversion.viaversion.legacy.LegacyAPI;
import io.netty.buffer.ByteBuf;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/ViaAPIBase.class */
public abstract class ViaAPIBase<T> implements ViaAPI<T> {
    private final LegacyAPI<T> legacy = new LegacyAPI<>();

    @Override // com.viaversion.viaversion.api.ViaAPI
    public ServerProtocolVersion getServerVersion() {
        return Via.getManager().getProtocolManager().getServerProtocolVersion();
    }

    @Override // com.viaversion.viaversion.api.ViaAPI
    public int getPlayerVersion(UUID uuid) {
        UserConnection connection = Via.getManager().getConnectionManager().getConnectedClient(uuid);
        if (connection != null) {
            return connection.getProtocolInfo().getProtocolVersion();
        }
        return -1;
    }

    @Override // com.viaversion.viaversion.api.ViaAPI
    public String getVersion() {
        return Via.getPlatform().getPluginVersion();
    }

    @Override // com.viaversion.viaversion.api.ViaAPI
    public boolean isInjected(UUID uuid) {
        return Via.getManager().getConnectionManager().isClientConnected(uuid);
    }

    @Override // com.viaversion.viaversion.api.ViaAPI
    public UserConnection getConnection(UUID uuid) {
        return Via.getManager().getConnectionManager().getConnectedClient(uuid);
    }

    @Override // com.viaversion.viaversion.api.ViaAPI
    public void sendRawPacket(UUID uuid, ByteBuf packet) throws IllegalArgumentException {
        if (!isInjected(uuid)) {
            throw new IllegalArgumentException("This player is not controlled by ViaVersion!");
        }
        UserConnection user = Via.getManager().getConnectionManager().getConnectedClient(uuid);
        user.scheduleSendRawPacket(packet);
    }

    @Override // com.viaversion.viaversion.api.ViaAPI
    public SortedSet<Integer> getSupportedVersions() {
        SortedSet<Integer> outputSet = new TreeSet<>(Via.getManager().getProtocolManager().getSupportedVersions());
        BlockedProtocolVersions blockedVersions = Via.getPlatform().getConf().blockedProtocolVersions();
        blockedVersions.getClass();
        outputSet.removeIf((v1) -> {
            return r1.contains(v1);
        });
        return outputSet;
    }

    @Override // com.viaversion.viaversion.api.ViaAPI
    public SortedSet<Integer> getFullSupportedVersions() {
        return Via.getManager().getProtocolManager().getSupportedVersions();
    }

    @Override // com.viaversion.viaversion.api.ViaAPI
    public LegacyViaAPI<T> legacyAPI() {
        return this.legacy;
    }
}
