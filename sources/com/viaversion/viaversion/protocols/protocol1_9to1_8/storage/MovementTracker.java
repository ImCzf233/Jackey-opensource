package com.viaversion.viaversion.protocols.protocol1_9to1_8.storage;

import com.viaversion.viaversion.api.connection.StorableObject;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_9to1_8/storage/MovementTracker.class */
public class MovementTracker implements StorableObject {
    private static final long IDLE_PACKET_DELAY = 50;
    private static final long IDLE_PACKET_LIMIT = 20;
    private long nextIdlePacket = 0;
    private boolean ground = true;

    public void incrementIdlePacket() {
        this.nextIdlePacket = Math.max(this.nextIdlePacket + 50, System.currentTimeMillis() - 1000);
    }

    public long getNextIdlePacket() {
        return this.nextIdlePacket;
    }

    public boolean isGround() {
        return this.ground;
    }

    public void setGround(boolean ground) {
        this.ground = ground;
    }
}
