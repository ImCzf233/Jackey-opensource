package com.viaversion.viaversion.api;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.legacy.LegacyViaAPI;
import com.viaversion.viaversion.api.protocol.version.ServerProtocolVersion;
import io.netty.buffer.ByteBuf;
import java.util.SortedSet;
import java.util.UUID;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/ViaAPI.class */
public interface ViaAPI<T> {
    ServerProtocolVersion getServerVersion();

    int getPlayerVersion(T t);

    int getPlayerVersion(UUID uuid);

    boolean isInjected(UUID uuid);

    UserConnection getConnection(UUID uuid);

    String getVersion();

    void sendRawPacket(T t, ByteBuf byteBuf);

    void sendRawPacket(UUID uuid, ByteBuf byteBuf);

    SortedSet<Integer> getSupportedVersions();

    SortedSet<Integer> getFullSupportedVersions();

    LegacyViaAPI<T> legacyAPI();

    default int majorVersion() {
        return 4;
    }

    default int apiVersion() {
        return 10;
    }
}
