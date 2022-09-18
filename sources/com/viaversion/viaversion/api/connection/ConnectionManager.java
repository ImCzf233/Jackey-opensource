package com.viaversion.viaversion.api.connection;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/connection/ConnectionManager.class */
public interface ConnectionManager {
    boolean isClientConnected(UUID uuid);

    UserConnection getConnectedClient(UUID uuid);

    UUID getConnectedClientId(UserConnection userConnection);

    Set<UserConnection> getConnections();

    Map<UUID, UserConnection> getConnectedClients();

    void onLoginSuccess(UserConnection userConnection);

    void onDisconnect(UserConnection userConnection);

    default boolean isFrontEnd(UserConnection connection) {
        return !connection.isClientSide();
    }
}
