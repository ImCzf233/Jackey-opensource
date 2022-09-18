package com.viaversion.viaversion.connection;

import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.ProtocolPipeline;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import java.util.UUID;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/connection/ProtocolInfoImpl.class */
public class ProtocolInfoImpl implements ProtocolInfo {
    private final UserConnection connection;
    private State state = State.HANDSHAKE;
    private int protocolVersion = -1;
    private int serverProtocolVersion = -1;
    private String username;
    private UUID uuid;
    private ProtocolPipeline pipeline;

    public ProtocolInfoImpl(UserConnection connection) {
        this.connection = connection;
    }

    @Override // com.viaversion.viaversion.api.connection.ProtocolInfo
    public State getState() {
        return this.state;
    }

    @Override // com.viaversion.viaversion.api.connection.ProtocolInfo
    public void setState(State state) {
        this.state = state;
    }

    @Override // com.viaversion.viaversion.api.connection.ProtocolInfo
    public int getProtocolVersion() {
        return this.protocolVersion;
    }

    @Override // com.viaversion.viaversion.api.connection.ProtocolInfo
    public void setProtocolVersion(int protocolVersion) {
        ProtocolVersion protocol = ProtocolVersion.getProtocol(protocolVersion);
        this.protocolVersion = protocol.getVersion();
    }

    @Override // com.viaversion.viaversion.api.connection.ProtocolInfo
    public int getServerProtocolVersion() {
        return this.serverProtocolVersion;
    }

    @Override // com.viaversion.viaversion.api.connection.ProtocolInfo
    public void setServerProtocolVersion(int serverProtocolVersion) {
        ProtocolVersion protocol = ProtocolVersion.getProtocol(serverProtocolVersion);
        this.serverProtocolVersion = protocol.getVersion();
    }

    @Override // com.viaversion.viaversion.api.connection.ProtocolInfo
    public String getUsername() {
        return this.username;
    }

    @Override // com.viaversion.viaversion.api.connection.ProtocolInfo
    public void setUsername(String username) {
        this.username = username;
    }

    @Override // com.viaversion.viaversion.api.connection.ProtocolInfo
    public UUID getUuid() {
        return this.uuid;
    }

    @Override // com.viaversion.viaversion.api.connection.ProtocolInfo
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override // com.viaversion.viaversion.api.connection.ProtocolInfo
    public ProtocolPipeline getPipeline() {
        return this.pipeline;
    }

    @Override // com.viaversion.viaversion.api.connection.ProtocolInfo
    public void setPipeline(ProtocolPipeline pipeline) {
        this.pipeline = pipeline;
    }

    @Override // com.viaversion.viaversion.api.connection.ProtocolInfo
    public UserConnection getUser() {
        return this.connection;
    }

    public String toString() {
        return "ProtocolInfo{state=" + this.state + ", protocolVersion=" + this.protocolVersion + ", serverProtocolVersion=" + this.serverProtocolVersion + ", username='" + this.username + "', uuid=" + this.uuid + '}';
    }
}
