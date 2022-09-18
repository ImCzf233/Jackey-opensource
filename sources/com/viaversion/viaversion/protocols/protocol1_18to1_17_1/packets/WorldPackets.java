package com.viaversion.viaversion.protocols.protocol1_18to1_17_1.packets;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntity;
import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntityImpl;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk1_18;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionImpl;
import com.viaversion.viaversion.api.minecraft.chunks.DataPaletteImpl;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.protocols.protocol1_17_1to1_17.ClientboundPackets1_17_1;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.types.Chunk1_17Type;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.BlockEntityIds;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.Protocol1_18To1_17_1;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.storage.ChunkLightStorage;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.types.Chunk1_18Type;
import com.viaversion.viaversion.util.MathUtil;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_18to1_17_1/packets/WorldPackets.class */
public final class WorldPackets {
    public static void register(final Protocol1_18To1_17_1 protocol) {
        protocol.registerClientbound((Protocol1_18To1_17_1) ClientboundPackets1_17_1.BLOCK_ENTITY_DATA, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_18to1_17_1.packets.WorldPackets.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.POSITION1_14);
                handler(wrapper -> {
                    short id = ((Short) wrapper.read(Type.UNSIGNED_BYTE)).shortValue();
                    int newId = BlockEntityIds.newId(id);
                    wrapper.write(Type.VAR_INT, Integer.valueOf(newId));
                    WorldPackets.handleSpawners(newId, (CompoundTag) wrapper.passthrough(Type.NBT));
                });
            }
        });
        protocol.registerClientbound((Protocol1_18To1_17_1) ClientboundPackets1_17_1.UPDATE_LIGHT, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_18to1_17_1.packets.WorldPackets.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    int chunkX = ((Integer) wrapper.passthrough(Type.VAR_INT)).intValue();
                    int chunkZ = ((Integer) wrapper.passthrough(Type.VAR_INT)).intValue();
                    if (((ChunkLightStorage) wrapper.user().get(ChunkLightStorage.class)).isLoaded(chunkX, chunkZ)) {
                        if (!Via.getConfig().cache1_17Light()) {
                            return;
                        }
                    } else {
                        wrapper.cancel();
                    }
                    boolean trustEdges = ((Boolean) wrapper.passthrough(Type.BOOLEAN)).booleanValue();
                    long[] skyLightMask = (long[]) wrapper.passthrough(Type.LONG_ARRAY_PRIMITIVE);
                    long[] blockLightMask = (long[]) wrapper.passthrough(Type.LONG_ARRAY_PRIMITIVE);
                    long[] emptySkyLightMask = (long[]) wrapper.passthrough(Type.LONG_ARRAY_PRIMITIVE);
                    long[] emptyBlockLightMask = (long[]) wrapper.passthrough(Type.LONG_ARRAY_PRIMITIVE);
                    int skyLightLenght = ((Integer) wrapper.passthrough(Type.VAR_INT)).intValue();
                    ?? r0 = new byte[skyLightLenght];
                    for (int i = 0; i < skyLightLenght; i++) {
                        r0[i] = (byte[]) wrapper.passthrough(Type.BYTE_ARRAY_PRIMITIVE);
                    }
                    int blockLightLength = ((Integer) wrapper.passthrough(Type.VAR_INT)).intValue();
                    ?? r02 = new byte[blockLightLength];
                    for (int i2 = 0; i2 < blockLightLength; i2++) {
                        r02[i2] = (byte[]) wrapper.passthrough(Type.BYTE_ARRAY_PRIMITIVE);
                    }
                    ChunkLightStorage lightStorage = (ChunkLightStorage) wrapper.user().get(ChunkLightStorage.class);
                    lightStorage.storeLight(chunkX, chunkZ, new ChunkLightStorage.ChunkLight(trustEdges, skyLightMask, blockLightMask, emptySkyLightMask, emptyBlockLightMask, r0, r02));
                });
            }
        });
        protocol.registerClientbound((Protocol1_18To1_17_1) ClientboundPackets1_17_1.CHUNK_DATA, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_18to1_17_1.packets.WorldPackets.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                Protocol1_18To1_17_1 protocol1_18To1_17_1 = protocol;
                handler(wrapper -> {
                    byte[][] skyLight;
                    byte[][] blockLight;
                    EntityTracker tracker = protocol1_18To1_17_1.getEntityRewriter().tracker(wrapper.user());
                    Chunk oldChunk = (Chunk) wrapper.read(new Chunk1_17Type(tracker.currentWorldSectionHeight()));
                    List<BlockEntity> blockEntities = new ArrayList<>(oldChunk.getBlockEntities().size());
                    for (CompoundTag tag : oldChunk.getBlockEntities()) {
                        NumberTag xTag = (NumberTag) tag.get("x");
                        NumberTag yTag = (NumberTag) tag.get("y");
                        NumberTag zTag = (NumberTag) tag.get("z");
                        StringTag idTag = (StringTag) tag.get("id");
                        if (xTag != null && yTag != null && zTag != null && idTag != null) {
                            String id = idTag.getValue();
                            int typeId = protocol1_18To1_17_1.getMappingData().blockEntityIds().getInt(id.replace("minecraft:", ""));
                            if (typeId == -1) {
                                Via.getPlatform().getLogger().warning("Unknown block entity: " + id);
                            }
                            WorldPackets.handleSpawners(typeId, tag);
                            byte packedXZ = (byte) (((xTag.asInt() & 15) << 4) | (zTag.asInt() & 15));
                            blockEntities.add(new BlockEntityImpl(packedXZ, yTag.asShort(), typeId, tag));
                        }
                    }
                    int[] biomeData = oldChunk.getBiomeData();
                    ChunkSection[] sections = oldChunk.getSections();
                    for (int i = 0; i < sections.length; i++) {
                        ChunkSection section = sections[i];
                        if (section == null) {
                            section = new ChunkSectionImpl();
                            sections[i] = section;
                            section.setNonAirBlocksCount(0);
                            DataPaletteImpl blockPalette = new DataPaletteImpl(4096);
                            blockPalette.addId(0);
                            section.addPalette(PaletteType.BLOCKS, blockPalette);
                        }
                        DataPaletteImpl biomePalette = new DataPaletteImpl(64);
                        section.addPalette(PaletteType.BIOMES, biomePalette);
                        int offset = i * 64;
                        int biomeIndex = 0;
                        int biomeArrayIndex = offset;
                        while (biomeIndex < 64) {
                            int biome = biomeData[biomeArrayIndex];
                            biomePalette.setIdAt(biomeIndex, biome != -1 ? biome : 0);
                            biomeIndex++;
                            biomeArrayIndex++;
                        }
                    }
                    Chunk1_18 chunk1_18 = new Chunk1_18(oldChunk.getX(), oldChunk.getZ(), sections, oldChunk.getHeightMap(), blockEntities);
                    wrapper.write(new Chunk1_18Type(tracker.currentWorldSectionHeight(), MathUtil.ceilLog2(protocol1_18To1_17_1.getMappingData().getBlockStateMappings().mappedSize()), MathUtil.ceilLog2(tracker.biomesSent())), chunk1_18);
                    ChunkLightStorage lightStorage = (ChunkLightStorage) wrapper.user().get(ChunkLightStorage.class);
                    boolean alreadyLoaded = !lightStorage.addLoadedChunk(chunk1_18.getX(), chunk1_18.getZ());
                    ChunkLightStorage.ChunkLight light = Via.getConfig().cache1_17Light() ? lightStorage.getLight(chunk1_18.getX(), chunk1_18.getZ()) : lightStorage.removeLight(chunk1_18.getX(), chunk1_18.getZ());
                    if (light == null) {
                        Via.getPlatform().getLogger().warning("No light data found for chunk at " + chunk1_18.getX() + ", " + chunk1_18.getZ() + ". Chunk was already loaded: " + alreadyLoaded);
                        BitSet emptyLightMask = new BitSet();
                        emptyLightMask.set(0, tracker.currentWorldSectionHeight() + 2);
                        wrapper.write(Type.BOOLEAN, false);
                        wrapper.write(Type.LONG_ARRAY_PRIMITIVE, new long[0]);
                        wrapper.write(Type.LONG_ARRAY_PRIMITIVE, new long[0]);
                        wrapper.write(Type.LONG_ARRAY_PRIMITIVE, emptyLightMask.toLongArray());
                        wrapper.write(Type.LONG_ARRAY_PRIMITIVE, emptyLightMask.toLongArray());
                        wrapper.write(Type.VAR_INT, 0);
                        wrapper.write(Type.VAR_INT, 0);
                        return;
                    }
                    wrapper.write(Type.BOOLEAN, Boolean.valueOf(light.trustEdges()));
                    wrapper.write(Type.LONG_ARRAY_PRIMITIVE, light.skyLightMask());
                    wrapper.write(Type.LONG_ARRAY_PRIMITIVE, light.blockLightMask());
                    wrapper.write(Type.LONG_ARRAY_PRIMITIVE, light.emptySkyLightMask());
                    wrapper.write(Type.LONG_ARRAY_PRIMITIVE, light.emptyBlockLightMask());
                    wrapper.write(Type.VAR_INT, Integer.valueOf(light.skyLight().length));
                    for (byte[] skyLight2 : light.skyLight()) {
                        wrapper.write(Type.BYTE_ARRAY_PRIMITIVE, skyLight2);
                    }
                    wrapper.write(Type.VAR_INT, Integer.valueOf(light.blockLight().length));
                    for (byte[] blockLight2 : light.blockLight()) {
                        wrapper.write(Type.BYTE_ARRAY_PRIMITIVE, blockLight2);
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol1_18To1_17_1) ClientboundPackets1_17_1.UNLOAD_CHUNK, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_18to1_17_1.packets.WorldPackets.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    int chunkX = ((Integer) wrapper.passthrough(Type.INT)).intValue();
                    int chunkZ = ((Integer) wrapper.passthrough(Type.INT)).intValue();
                    ((ChunkLightStorage) wrapper.user().get(ChunkLightStorage.class)).clear(chunkX, chunkZ);
                });
            }
        });
    }

    public static void handleSpawners(int typeId, CompoundTag tag) {
        CompoundTag entity;
        if (typeId == 8 && (entity = (CompoundTag) tag.get("SpawnData")) != null) {
            CompoundTag spawnData = new CompoundTag();
            tag.put("SpawnData", spawnData);
            spawnData.put("entity", entity);
        }
    }
}
