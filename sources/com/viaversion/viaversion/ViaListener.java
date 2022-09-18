package com.viaversion.viaversion;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.Protocol;
import java.util.UUID;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/ViaListener.class */
public abstract class ViaListener {
    private final Class<? extends Protocol> requiredPipeline;
    private boolean registered;

    public abstract void register();

    public ViaListener(Class<? extends Protocol> requiredPipeline) {
        this.requiredPipeline = requiredPipeline;
    }

    public UserConnection getUserConnection(UUID uuid) {
        return Via.getManager().getConnectionManager().getConnectedClient(uuid);
    }

    public boolean isOnPipe(UUID uuid) {
        UserConnection userConnection = getUserConnection(uuid);
        return userConnection != null && (this.requiredPipeline == null || userConnection.getProtocolInfo().getPipeline().contains(this.requiredPipeline));
    }

    protected Class<? extends Protocol> getRequiredPipeline() {
        return this.requiredPipeline;
    }

    public boolean isRegistered() {
        return this.registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }
}
