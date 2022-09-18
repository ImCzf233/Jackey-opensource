package com.viaversion.viaversion.protocol;

import com.viaversion.viaversion.api.protocol.version.ServerProtocolVersion;
import com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocol/ServerProtocolVersionRange.class */
public class ServerProtocolVersionRange implements ServerProtocolVersion {
    private final int lowestSupportedVersion;
    private final int highestSupportedVersion;
    private final IntSortedSet supportedVersions;

    public ServerProtocolVersionRange(int lowestSupportedVersion, int highestSupportedVersion, IntSortedSet supportedVersions) {
        this.lowestSupportedVersion = lowestSupportedVersion;
        this.highestSupportedVersion = highestSupportedVersion;
        this.supportedVersions = supportedVersions;
    }

    @Override // com.viaversion.viaversion.api.protocol.version.ServerProtocolVersion
    public int lowestSupportedVersion() {
        return this.lowestSupportedVersion;
    }

    @Override // com.viaversion.viaversion.api.protocol.version.ServerProtocolVersion
    public int highestSupportedVersion() {
        return this.highestSupportedVersion;
    }

    @Override // com.viaversion.viaversion.api.protocol.version.ServerProtocolVersion
    public IntSortedSet supportedVersions() {
        return this.supportedVersions;
    }
}
