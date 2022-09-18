package com.viaversion.viaversion.protocol;

import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.ProtocolPathEntry;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocol/ProtocolPathEntryImpl.class */
public class ProtocolPathEntryImpl implements ProtocolPathEntry {
    private final int outputProtocolVersion;
    private final Protocol<?, ?, ?, ?> protocol;

    public ProtocolPathEntryImpl(int outputProtocolVersion, Protocol<?, ?, ?, ?> protocol) {
        this.outputProtocolVersion = outputProtocolVersion;
        this.protocol = protocol;
    }

    @Override // com.viaversion.viaversion.api.protocol.ProtocolPathEntry
    public int outputProtocolVersion() {
        return this.outputProtocolVersion;
    }

    @Override // com.viaversion.viaversion.api.protocol.ProtocolPathEntry
    public Protocol<?, ?, ?, ?> protocol() {
        return this.protocol;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProtocolPathEntryImpl that = (ProtocolPathEntryImpl) o;
        if (this.outputProtocolVersion == that.outputProtocolVersion) {
            return this.protocol.equals(that.protocol);
        }
        return false;
    }

    public int hashCode() {
        int result = this.outputProtocolVersion;
        return (31 * result) + this.protocol.hashCode();
    }
}
