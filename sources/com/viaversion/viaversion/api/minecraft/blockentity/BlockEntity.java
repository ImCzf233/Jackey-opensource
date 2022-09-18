package com.viaversion.viaversion.api.minecraft.blockentity;

import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/minecraft/blockentity/BlockEntity.class */
public interface BlockEntity {
    byte packedXZ();

    /* renamed from: y */
    short mo225y();

    int typeId();

    CompoundTag tag();

    default byte sectionX() {
        return (byte) ((packedXZ() >> 4) & 15);
    }

    default byte sectionZ() {
        return (byte) (packedXZ() & 15);
    }
}
