package com.viaversion.viaversion.api.protocol.version;

import com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/protocol/version/ServerProtocolVersion.class */
public interface ServerProtocolVersion {
    int lowestSupportedVersion();

    int highestSupportedVersion();

    IntSortedSet supportedVersions();

    default boolean isKnown() {
        return (lowestSupportedVersion() == -1 || highestSupportedVersion() == -1) ? false : true;
    }
}
