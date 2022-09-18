package com.viaversion.viaversion.api.minecraft.blockentity;

import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/minecraft/blockentity/BlockEntityImpl.class */
public final class BlockEntityImpl implements BlockEntity {
    private final byte packedXZ;

    /* renamed from: y */
    private final short f39y;
    private final int typeId;
    private final CompoundTag tag;

    public BlockEntityImpl(byte packedXZ, short y, int typeId, CompoundTag tag) {
        this.packedXZ = packedXZ;
        this.f39y = y;
        this.typeId = typeId;
        this.tag = tag;
    }

    @Override // com.viaversion.viaversion.api.minecraft.blockentity.BlockEntity
    public byte packedXZ() {
        return this.packedXZ;
    }

    @Override // com.viaversion.viaversion.api.minecraft.blockentity.BlockEntity
    /* renamed from: y */
    public short mo225y() {
        return this.f39y;
    }

    @Override // com.viaversion.viaversion.api.minecraft.blockentity.BlockEntity
    public int typeId() {
        return this.typeId;
    }

    @Override // com.viaversion.viaversion.api.minecraft.blockentity.BlockEntity
    public CompoundTag tag() {
        return this.tag;
    }
}
