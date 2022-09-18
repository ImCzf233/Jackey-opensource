package com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_16_4to1_17/storage/PingRequests.class */
public final class PingRequests implements StorableObject {
    private final IntSet ids = new IntOpenHashSet();

    public void addId(short id) {
        this.ids.add(id);
    }

    public boolean removeId(short id) {
        return this.ids.remove(id);
    }
}
