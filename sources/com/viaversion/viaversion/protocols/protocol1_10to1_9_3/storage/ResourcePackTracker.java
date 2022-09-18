package com.viaversion.viaversion.protocols.protocol1_10to1_9_3.storage;

import com.viaversion.viaversion.api.connection.StorableObject;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_10to1_9_3/storage/ResourcePackTracker.class */
public class ResourcePackTracker implements StorableObject {
    private String lastHash = "";

    public String getLastHash() {
        return this.lastHash;
    }

    public void setLastHash(String lastHash) {
        this.lastHash = lastHash;
    }

    public String toString() {
        return "ResourcePackTracker{lastHash='" + this.lastHash + "'}";
    }
}
