package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.storage;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_15_2to1_16/storage/WolfDataMaskStorage.class */
public final class WolfDataMaskStorage {
    private byte tameableMask;

    public WolfDataMaskStorage(byte tameableMask) {
        this.tameableMask = tameableMask;
    }

    public void setTameableMask(byte tameableMask) {
        this.tameableMask = tameableMask;
    }

    public byte tameableMask() {
        return this.tameableMask;
    }
}
