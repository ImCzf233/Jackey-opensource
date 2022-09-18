package com.viaversion.viaversion.api.protocol.version;

import com.viaversion.viaversion.libs.fastutil.ints.IntSet;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/protocol/version/BlockedProtocolVersions.class */
public interface BlockedProtocolVersions {
    boolean contains(int i);

    int blocksBelow();

    int blocksAbove();

    IntSet singleBlockedVersions();
}
