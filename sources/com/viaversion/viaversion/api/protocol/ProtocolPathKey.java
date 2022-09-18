package com.viaversion.viaversion.api.protocol;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/protocol/ProtocolPathKey.class */
public interface ProtocolPathKey {
    int clientProtocolVersion();

    int serverProtocolVersion();

    @Deprecated
    default int getClientProtocolVersion() {
        return clientProtocolVersion();
    }

    @Deprecated
    default int getServerProtocolVersion() {
        return serverProtocolVersion();
    }
}
