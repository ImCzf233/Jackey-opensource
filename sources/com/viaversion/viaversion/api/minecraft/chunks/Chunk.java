package com.viaversion.viaversion.api.minecraft.chunks;

import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntity;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import java.util.BitSet;
import java.util.List;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/minecraft/chunks/Chunk.class */
public interface Chunk {
    int getX();

    int getZ();

    boolean isBiomeData();

    boolean isFullChunk();

    boolean isIgnoreOldLightData();

    void setIgnoreOldLightData(boolean z);

    int getBitmask();

    void setBitmask(int i);

    BitSet getChunkMask();

    void setChunkMask(BitSet bitSet);

    ChunkSection[] getSections();

    void setSections(ChunkSection[] chunkSectionArr);

    int[] getBiomeData();

    void setBiomeData(int[] iArr);

    CompoundTag getHeightMap();

    void setHeightMap(CompoundTag compoundTag);

    List<CompoundTag> getBlockEntities();

    List<BlockEntity> blockEntities();
}
