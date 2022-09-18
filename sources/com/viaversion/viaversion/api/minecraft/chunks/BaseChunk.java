package com.viaversion.viaversion.api.minecraft.chunks;

import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntity;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import java.util.BitSet;
import java.util.List;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/minecraft/chunks/BaseChunk.class */
public class BaseChunk implements Chunk {

    /* renamed from: x */
    protected final int f40x;

    /* renamed from: z */
    protected final int f41z;
    protected final boolean fullChunk;
    protected boolean ignoreOldLightData;
    protected BitSet chunkSectionBitSet;
    protected int bitmask;
    protected ChunkSection[] sections;
    protected int[] biomeData;
    protected CompoundTag heightMap;
    protected final List<CompoundTag> blockEntities;

    public BaseChunk(int x, int z, boolean fullChunk, boolean ignoreOldLightData, BitSet chunkSectionBitSet, ChunkSection[] sections, int[] biomeData, CompoundTag heightMap, List<CompoundTag> blockEntities) {
        this.f40x = x;
        this.f41z = z;
        this.fullChunk = fullChunk;
        this.ignoreOldLightData = ignoreOldLightData;
        this.chunkSectionBitSet = chunkSectionBitSet;
        this.sections = sections;
        this.biomeData = biomeData;
        this.heightMap = heightMap;
        this.blockEntities = blockEntities;
    }

    public BaseChunk(int x, int z, boolean fullChunk, boolean ignoreOldLightData, int bitmask, ChunkSection[] sections, int[] biomeData, CompoundTag heightMap, List<CompoundTag> blockEntities) {
        this(x, z, fullChunk, ignoreOldLightData, (BitSet) null, sections, biomeData, heightMap, blockEntities);
        this.bitmask = bitmask;
    }

    public BaseChunk(int x, int z, boolean fullChunk, boolean ignoreOldLightData, int bitmask, ChunkSection[] sections, int[] biomeData, List<CompoundTag> blockEntities) {
        this(x, z, fullChunk, ignoreOldLightData, bitmask, sections, biomeData, (CompoundTag) null, blockEntities);
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.Chunk
    public boolean isBiomeData() {
        return this.biomeData != null;
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.Chunk
    public int getX() {
        return this.f40x;
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.Chunk
    public int getZ() {
        return this.f41z;
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.Chunk
    public boolean isFullChunk() {
        return this.fullChunk;
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.Chunk
    public boolean isIgnoreOldLightData() {
        return this.ignoreOldLightData;
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.Chunk
    public void setIgnoreOldLightData(boolean ignoreOldLightData) {
        this.ignoreOldLightData = ignoreOldLightData;
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.Chunk
    public int getBitmask() {
        return this.bitmask;
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.Chunk
    public void setBitmask(int bitmask) {
        this.bitmask = bitmask;
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.Chunk
    public BitSet getChunkMask() {
        return this.chunkSectionBitSet;
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.Chunk
    public void setChunkMask(BitSet chunkSectionMask) {
        this.chunkSectionBitSet = chunkSectionMask;
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
        return this.biomeData;
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.Chunk
    public void setBiomeData(int[] biomeData) {
        this.biomeData = biomeData;
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
        return this.blockEntities;
    }

    @Override // com.viaversion.viaversion.api.minecraft.chunks.Chunk
    public List<BlockEntity> blockEntities() {
        throw new UnsupportedOperationException();
    }
}
