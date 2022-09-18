package com.viaversion.viaversion.protocol;

import com.viaversion.viaversion.api.protocol.version.ServerProtocolVersion;
import com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSortedSets;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocol/ServerProtocolVersionSingleton.class */
public class ServerProtocolVersionSingleton implements ServerProtocolVersion {
    private final int protocolVersion;

    public ServerProtocolVersionSingleton(int protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    @Override // com.viaversion.viaversion.api.protocol.version.ServerProtocolVersion
    public int lowestSupportedVersion() {
        return this.protocolVersion;
    }

    @Override // com.viaversion.viaversion.api.protocol.version.ServerProtocolVersion
    public int highestSupportedVersion() {
        return this.protocolVersion;
    }

    @Override // com.viaversion.viaversion.api.protocol.version.ServerProtocolVersion
    public IntSortedSet supportedVersions() {
        return IntSortedSets.singleton(this.protocolVersion);
    }
}
