package com.viaversion.viaversion.api.connection;

import com.viaversion.viaversion.api.protocol.ProtocolPipeline;
import com.viaversion.viaversion.api.protocol.packet.State;
import java.util.UUID;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/connection/ProtocolInfo.class */
public interface ProtocolInfo {
    State getState();

    void setState(State state);

    int getProtocolVersion();

    void setProtocolVersion(int i);

    int getServerProtocolVersion();

    void setServerProtocolVersion(int i);

    String getUsername();

    void setUsername(String str);

    UUID getUuid();

    void setUuid(UUID uuid);

    ProtocolPipeline getPipeline();

    void setPipeline(ProtocolPipeline protocolPipeline);

    UserConnection getUser();
}
