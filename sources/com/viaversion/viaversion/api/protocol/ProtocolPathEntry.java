package com.viaversion.viaversion.api.protocol;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/protocol/ProtocolPathEntry.class */
public interface ProtocolPathEntry {
    int outputProtocolVersion();

    Protocol<?, ?, ?, ?> protocol();

    @Deprecated
    default int getOutputProtocolVersion() {
        return outputProtocolVersion();
    }

    @Deprecated
    default Protocol getProtocol() {
        return protocol();
    }
}
