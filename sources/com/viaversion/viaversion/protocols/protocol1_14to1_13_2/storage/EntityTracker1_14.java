package com.viaversion.viaversion.protocols.protocol1_14to1_13_2.storage;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_14Types;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.flare.fastutil.Int2ObjectSyncMap;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_14to1_13_2/storage/EntityTracker1_14.class */
public class EntityTracker1_14 extends EntityTrackerBase {
    private int latestTradeWindowId;
    private int chunkCenterX;
    private int chunkCenterZ;
    private final Int2ObjectMap<Byte> insentientData = Int2ObjectSyncMap.hashmap();
    private final Int2ObjectMap<Byte> sleepingAndRiptideData = Int2ObjectSyncMap.hashmap();
    private final Int2ObjectMap<Byte> playerEntityFlags = Int2ObjectSyncMap.hashmap();
    private boolean forceSendCenterChunk = true;

    public EntityTracker1_14(UserConnection user) {
        super(user, Entity1_14Types.PLAYER);
    }

    @Override // com.viaversion.viaversion.data.entity.EntityTrackerBase, com.viaversion.viaversion.api.data.entity.EntityTracker
    public void removeEntity(int entityId) {
        super.removeEntity(entityId);
        this.insentientData.remove(entityId);
        this.sleepingAndRiptideData.remove(entityId);
        this.playerEntityFlags.remove(entityId);
    }

    public byte getInsentientData(int entity) {
        Byte val = this.insentientData.get(entity);
        if (val == null) {
            return (byte) 0;
        }
        return val.byteValue();
    }

    public void setInsentientData(int entity, byte value) {
        this.insentientData.put(entity, (int) Byte.valueOf(value));
    }

    private static byte zeroIfNull(Byte val) {
        if (val == null) {
            return (byte) 0;
        }
        return val.byteValue();
    }

    public boolean isSleeping(int player) {
        return (zeroIfNull(this.sleepingAndRiptideData.get(player)) & 1) != 0;
    }

    public void setSleeping(int player, boolean value) {
        byte newValue = (byte) ((zeroIfNull(this.sleepingAndRiptideData.get(player)) & (-2)) | (value ? 1 : 0));
        if (newValue == 0) {
            this.sleepingAndRiptideData.remove(player);
        } else {
            this.sleepingAndRiptideData.put(player, (int) Byte.valueOf(newValue));
        }
    }

    public boolean isRiptide(int player) {
        return (zeroIfNull(this.sleepingAndRiptideData.get(player)) & 2) != 0;
    }

    public void setRiptide(int player, boolean value) {
        byte newValue = (byte) ((zeroIfNull(this.sleepingAndRiptideData.get(player)) & (-3)) | (value ? 2 : 0));
        if (newValue == 0) {
            this.sleepingAndRiptideData.remove(player);
        } else {
            this.sleepingAndRiptideData.put(player, (int) Byte.valueOf(newValue));
        }
    }

    public byte getEntityFlags(int player) {
        return zeroIfNull(this.playerEntityFlags.get(player));
    }

    public void setEntityFlags(int player, byte data) {
        this.playerEntityFlags.put(player, (int) Byte.valueOf(data));
    }

    public int getLatestTradeWindowId() {
        return this.latestTradeWindowId;
    }

    public void setLatestTradeWindowId(int latestTradeWindowId) {
        this.latestTradeWindowId = latestTradeWindowId;
    }

    public boolean isForceSendCenterChunk() {
        return this.forceSendCenterChunk;
    }

    public void setForceSendCenterChunk(boolean forceSendCenterChunk) {
        this.forceSendCenterChunk = forceSendCenterChunk;
    }

    public int getChunkCenterX() {
        return this.chunkCenterX;
    }

    public void setChunkCenterX(int chunkCenterX) {
        this.chunkCenterX = chunkCenterX;
    }

    public int getChunkCenterZ() {
        return this.chunkCenterZ;
    }

    public void setChunkCenterZ(int chunkCenterZ) {
        this.chunkCenterZ = chunkCenterZ;
    }
}
