package com.viaversion.viaversion.protocol;

import com.viaversion.viaversion.api.protocol.ProtocolPathKey;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocol/ProtocolPathKeyImpl.class */
public class ProtocolPathKeyImpl implements ProtocolPathKey {
    private final int clientProtocolVersion;
    private final int serverProtocolVersion;

    public ProtocolPathKeyImpl(int clientProtocolVersion, int serverProtocolVersion) {
        this.clientProtocolVersion = clientProtocolVersion;
        this.serverProtocolVersion = serverProtocolVersion;
    }

    @Override // com.viaversion.viaversion.api.protocol.ProtocolPathKey
    public int clientProtocolVersion() {
        return this.clientProtocolVersion;
    }

    @Override // com.viaversion.viaversion.api.protocol.ProtocolPathKey
    public int serverProtocolVersion() {
        return this.serverProtocolVersion;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProtocolPathKeyImpl that = (ProtocolPathKeyImpl) o;
        return this.clientProtocolVersion == that.clientProtocolVersion && this.serverProtocolVersion == that.serverProtocolVersion;
    }

    public int hashCode() {
        int result = this.clientProtocolVersion;
        return (31 * result) + this.serverProtocolVersion;
    }
}
