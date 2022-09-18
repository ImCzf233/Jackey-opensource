package com.viaversion.viaversion.api.minecraft.chunks;

import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntity;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import java.util.BitSet;
import java.util.List;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/minecraft/chunks/Chunk1_18.class */
public class Chunk1_18 implements Chunk {

    /* renamed from: x */
    protected final int f42x;

    /* renamed from: z */
    protected final int f43z;
    protected ChunkSection[] sections;
    protected CompoundTag heightMap;
    protected final List<BlockEntity> blockEntities;

    public Chunk1_18(int x, int z, ChunkSection[] sections, CompoundTag heightMap, List<BlockEntity> blockEntities) {
        this.f42x = x;
        this.f43z = z;
        this.sections = sections;
        this.heightMap = heightMap;
        this.blockEntities = blockEntities;
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.Chunk
    public boolean isBiomeData() {
        return false;
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.Chunk
    public int getX() {
        return this.f42x;
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.Chunk
    public int getZ() {
        return this.f43z;
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.Chunk
    public boolean isFullChunk() {
        return true;
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.Chunk
    public boolean isIgnoreOldLightData() {
        return false;
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.Chunk
    public void setIgnoreOldLightData(boolean ignoreOldLightData) {
        throw new UnsupportedOperationException();
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.Chunk
    public int getBitmask() {
        return -1;
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.Chunk
    public void setBitmask(int bitmask) {
        throw new UnsupportedOperationException();
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.Chunk
    public BitSet getChunkMask() {
        return null;
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.Chunk
    public void setChunkMask(BitSet chunkSectionMask) {
        throw new UnsupportedOperationException();
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.Chunk
    public ChunkSection[] getSections() {
        return this.sections;
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.Chunk
    public void setSections(ChunkSection[] sections) {
        this.sections = sections;
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.Chunk
    public int[] getBiomeData() {
        return null;
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.Chunk
    public void setBiomeData(int[] biomeData) {
        throw new UnsupportedOperationException();
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.Chunk
    public CompoundTag getHeightMap() {
        return this.heightMap;
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.Chunk
    public void setHeightMap(CompoundTag heightMap) {
        this.heightMap = heightMap;
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.Chunk
    public List<CompoundTag> getBlockEntities() {
        throw new UnsupportedOperationException();
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.Chunk
    public List<BlockEntity> blockEntities() {
        return this.blockEntities;
    }
}
