package com.viaversion.viaversion.protocol;

import com.viaversion.viaversion.api.protocol.version.BlockedProtocolVersions;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocol/BlockedProtocolVersionsImpl.class */
public class BlockedProtocolVersionsImpl implements BlockedProtocolVersions {
    private final IntSet singleBlockedVersions;
    private final int blocksBelow;
    private final int blocksAbove;

    public BlockedProtocolVersionsImpl(IntSet singleBlockedVersions, int blocksBelow, int blocksAbove) {
        this.singleBlockedVersions = singleBlockedVersions;
        this.blocksBelow = blocksBelow;
        this.blocksAbove = blocksAbove;
    }

    @Override // com.viaversion.viaversion.api.protocol.version.BlockedProtocolVersions
    public boolean contains(int protocolVersion) {
        return (this.blocksBelow != -1 && protocolVersion < this.blocksBelow) || (this.blocksAbove != -1 && protocolVersion > this.blocksAbove) || this.singleBlockedVersions.contains(protocolVersion);
    }

    @Override // com.viaversion.viaversion.api.protocol.version.BlockedProtocolVersions
    public int blocksBelow() {
        return this.blocksBelow;
    }

    @Override // com.viaversion.viaversion.api.protocol.version.BlockedProtocolVersions
    public int blocksAbove() {
        return this.blocksAbove;
    }

    @Override // com.viaversion.viaversion.api.protocol.version.BlockedProtocolVersions
    public IntSet singleBlockedVersions() {
        return this.singleBlockedVersions;
    }
}
