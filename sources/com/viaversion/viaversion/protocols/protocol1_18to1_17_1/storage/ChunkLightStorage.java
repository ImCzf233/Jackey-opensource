package com.viaversion.viaversion.protocols.protocol1_18to1_17_1.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_18to1_17_1/storage/ChunkLightStorage.class */
public final class ChunkLightStorage implements StorableObject {
    private final Map<Long, ChunkLight> lightPackets = new HashMap();
    private final Set<Long> loadedChunks = new HashSet();

    public void storeLight(int x, int z, ChunkLight chunkLight) {
        this.lightPackets.put(Long.valueOf(getChunkSectionIndex(x, z)), chunkLight);
    }

    public ChunkLight removeLight(int x, int z) {
        return this.lightPackets.remove(Long.valueOf(getChunkSectionIndex(x, z)));
    }

    public ChunkLight getLight(int x, int z) {
        return this.lightPackets.get(Long.valueOf(getChunkSectionIndex(x, z)));
    }

    public boolean addLoadedChunk(int x, int z) {
        return this.loadedChunks.add(Long.valueOf(getChunkSectionIndex(x, z)));
    }

    public boolean isLoaded(int x, int z) {
        return this.loadedChunks.contains(Long.valueOf(getChunkSectionIndex(x, z)));
    }

    public void clear(int x, int z) {
        long index = getChunkSectionIndex(x, z);
        this.lightPackets.remove(Long.valueOf(index));
        this.loadedChunks.remove(Long.valueOf(index));
    }

    public void clear() {
        this.loadedChunks.clear();
        this.lightPackets.clear();
    }

    private long getChunkSectionIndex(int x, int z) {
        return ((x & 67108863) << 38) | (z & 67108863);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_18to1_17_1/storage/ChunkLightStorage$ChunkLight.class */
    public static final class ChunkLight {
        private final boolean trustEdges;
        private final long[] skyLightMask;
        private final long[] blockLightMask;
        private final long[] emptySkyLightMask;
        private final long[] emptyBlockLightMask;
        private final byte[][] skyLight;
        private final byte[][] blockLight;

        public ChunkLight(boolean trustEdges, long[] skyLightMask, long[] blockLightMask, long[] emptySkyLightMask, long[] emptyBlockLightMask, byte[][] skyLight, byte[][] blockLight) {
            this.trustEdges = trustEdges;
            this.skyLightMask = skyLightMask;
            this.emptySkyLightMask = emptySkyLightMask;
            this.blockLightMask = blockLightMask;
            this.emptyBlockLightMask = emptyBlockLightMask;
            this.skyLight = skyLight;
            this.blockLight = blockLight;
        }

        public boolean trustEdges() {
            return this.trustEdges;
        }

        public long[] skyLightMask() {
            return this.skyLightMask;
        }

        public long[] emptySkyLightMask() {
            return this.emptySkyLightMask;
        }

        public long[] blockLightMask() {
            return this.blockLightMask;
        }

        public long[] emptyBlockLightMask() {
            return this.emptyBlockLightMask;
        }

        public byte[][] skyLight() {
            return this.skyLight;
        }

        public byte[][] blockLight() {
            return this.blockLight;
        }
    }
}
