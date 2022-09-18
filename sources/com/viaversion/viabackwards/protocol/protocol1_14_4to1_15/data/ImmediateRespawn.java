package com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.data;

import com.viaversion.viaversion.api.connection.StorableObject;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_14_4to1_15/data/ImmediateRespawn.class */
public class ImmediateRespawn implements StorableObject {
    private boolean immediateRespawn;

    public boolean isImmediateRespawn() {
        return this.immediateRespawn;
    }

    public void setImmediateRespawn(boolean immediateRespawn) {
        this.immediateRespawn = immediateRespawn;
    }
}
