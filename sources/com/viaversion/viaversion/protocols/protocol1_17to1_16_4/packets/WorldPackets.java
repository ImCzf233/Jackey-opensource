package com.viaversion.viaversion.protocols.protocol1_17to1_16_4.packets;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord1_16_2;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_17Types;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.types.Chunk1_16_2Type;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ClientboundPackets1_17;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.Protocol1_17To1_16_4;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.types.Chunk1_17Type;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_17to1_16_4/packets/WorldPackets.class */
public final class WorldPackets {
    public static void register(final Protocol1_17To1_16_4 protocol) {
        BlockRewriter blockRewriter = new BlockRewriter(protocol, Type.POSITION1_14);
        blockRewriter.registerBlockAction(ClientboundPackets1_16_2.BLOCK_ACTION);
        blockRewriter.registerBlockChange(ClientboundPackets1_16_2.BLOCK_CHANGE);
        blockRewriter.registerVarLongMultiBlockChange(ClientboundPackets1_16_2.MULTI_BLOCK_CHANGE);
        blockRewriter.registerAcknowledgePlayerDigging(ClientboundPackets1_16_2.ACKNOWLEDGE_PLAYER_DIGGING);
        protocol.registerClientbound((Protocol1_17To1_16_4) ClientboundPackets1_16_2.WORLD_BORDER, (ClientboundPackets1_16_2) null, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_17to1_16_4.packets.WorldPackets.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    ClientboundPacketType packetType;
                    int type = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                    switch (type) {
                        case 0:
                            packetType = ClientboundPackets1_17.WORLD_BORDER_SIZE;
                            break;
                        case 1:
                            packetType = ClientboundPackets1_17.WORLD_BORDER_LERP_SIZE;
                            break;
                        case 2:
                            packetType = ClientboundPackets1_17.WORLD_BORDER_CENTER;
                            break;
                        case 3:
                            packetType = ClientboundPackets1_17.WORLD_BORDER_INIT;
                            break;
                        case 4:
                            packetType = ClientboundPackets1_17.WORLD_BORDER_WARNING_DELAY;
                            break;
                        case 5:
                            packetType = ClientboundPackets1_17.WORLD_BORDER_WARNING_DISTANCE;
                            break;
                        default:
                            throw new IllegalArgumentException("Invalid world border type received: " + type);
                    }
                    wrapper.setId(packetType.getId());
                });
            }
        });
        protocol.registerClientbound((Protocol1_17To1_16_4) ClientboundPackets1_16_2.UPDATE_LIGHT, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_17to1_16_4.packets.WorldPackets.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.VAR_INT);
                map(Type.BOOLEAN);
                handler(wrapper -> {
                    int skyLightMask = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                    int blockLightMask = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                    wrapper.write(Type.LONG_ARRAY_PRIMITIVE, toBitSetLongArray(skyLightMask));
                    wrapper.write(Type.LONG_ARRAY_PRIMITIVE, toBitSetLongArray(blockLightMask));
                    wrapper.write(Type.LONG_ARRAY_PRIMITIVE, toBitSetLongArray(((Integer) wrapper.read(Type.VAR_INT)).intValue()));
                    wrapper.write(Type.LONG_ARRAY_PRIMITIVE, toBitSetLongArray(((Integer) wrapper.read(Type.VAR_INT)).intValue()));
                    writeLightArrays(wrapper, skyLightMask);
                    writeLightArrays(wrapper, blockLightMask);
                });
            }

            private void writeLightArrays(PacketWrapper wrapper, int bitMask) throws Exception {
                ArrayList<byte[]> arrayList = new ArrayList();
                for (int i = 0; i < 18; i++) {
                    if (isSet(bitMask, i)) {
                        arrayList.add(wrapper.read(Type.BYTE_ARRAY_PRIMITIVE));
                    }
                }
                wrapper.write(Type.VAR_INT, Integer.valueOf(arrayList.size()));
                for (byte[] bytes : arrayList) {
                    wrapper.write(Type.BYTE_ARRAY_PRIMITIVE, bytes);
                }
            }

            private long[] toBitSetLongArray(int bitmask) {
                return new long[]{bitmask};
            }

            private boolean isSet(int mask, int i) {
                return (mask & (1 << i)) != 0;
            }
        });
        protocol.registerClientbound((Protocol1_17To1_16_4) ClientboundPackets1_16_2.CHUNK_DATA, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_17to1_16_4.packets.WorldPackets.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                Protocol1_17To1_16_4 protocol1_17To1_16_4 = protocol;
                handler(wrapper -> {
                    Chunk chunk = (Chunk) wrapper.read(new Chunk1_16_2Type());
                    if (!chunk.isFullChunk()) {
                        WorldPackets.writeMultiBlockChangePacket(wrapper, chunk);
                        wrapper.cancel();
                        return;
                    }
                    wrapper.write(new Chunk1_17Type(chunk.getSections().length), chunk);
                    chunk.setChunkMask(BitSet.valueOf(new long[]{chunk.getBitmask()}));
                    for (int s = 0; s < chunk.getSections().length; s++) {
                        ChunkSection section = chunk.getSections()[s];
                        if (section != null) {
                            for (int i = 0; i < section.getPaletteSize(); i++) {
                                int old = section.getPaletteEntry(i);
                                section.setPaletteEntry(i, protocol1_17To1_16_4.getMappingData().getNewBlockStateId(old));
                            }
                        }
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol1_17To1_16_4) ClientboundPackets1_16_2.JOIN_GAME, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_17to1_16_4.packets.WorldPackets.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.BOOLEAN);
                map(Type.UNSIGNED_BYTE);
                map(Type.BYTE);
                map(Type.STRING_ARRAY);
                map(Type.NBT);
                map(Type.NBT);
                handler(wrapper -> {
                    CompoundTag dimensionRegistry = (CompoundTag) ((CompoundTag) wrapper.get(Type.NBT, 0)).get("minecraft:dimension_type");
                    ListTag dimensions = (ListTag) dimensionRegistry.get("value");
                    Iterator<Tag> it = dimensions.iterator();
                    while (it.hasNext()) {
                        Tag dimension = it.next();
                        CompoundTag dimensionCompound = (CompoundTag) ((CompoundTag) dimension).get("element");
                        WorldPackets.addNewDimensionData(dimensionCompound);
                    }
                    CompoundTag currentDimensionTag = (CompoundTag) wrapper.get(Type.NBT, 1);
                    WorldPackets.addNewDimensionData(currentDimensionTag);
                    UserConnection user = wrapper.user();
                    user.getEntityTracker(Protocol1_17To1_16_4.class).addEntity(((Integer) wrapper.get(Type.INT, 0)).intValue(), Entity1_17Types.PLAYER);
                });
            }
        });
        protocol.registerClientbound((Protocol1_17To1_16_4) ClientboundPackets1_16_2.RESPAWN, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_17to1_16_4.packets.WorldPackets.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    CompoundTag dimensionData = (CompoundTag) wrapper.passthrough(Type.NBT);
                    WorldPackets.addNewDimensionData(dimensionData);
                });
            }
        });
        blockRewriter.registerEffect(ClientboundPackets1_16_2.EFFECT, 1010, 2001);
    }

    public static void writeMultiBlockChangePacket(PacketWrapper wrapper, Chunk chunk) throws Exception {
        long chunkPosition = (chunk.getX() & 4194303) << 42;
        long chunkPosition2 = chunkPosition | ((chunk.getZ() & 4194303) << 20);
        ChunkSection[] sections = chunk.getSections();
        for (int chunkY = 0; chunkY < sections.length; chunkY++) {
            ChunkSection section = sections[chunkY];
            if (section != null) {
                PacketWrapper blockChangePacket = wrapper.create(ClientboundPackets1_17.MULTI_BLOCK_CHANGE);
                blockChangePacket.write(Type.LONG, Long.valueOf(chunkPosition2 | (chunkY & 1048575)));
                blockChangePacket.write(Type.BOOLEAN, true);
                BlockChangeRecord[] blockChangeRecords = new BlockChangeRecord[4096];
                int j = 0;
                for (int x = 0; x < 16; x++) {
                    for (int y = 0; y < 16; y++) {
                        for (int z = 0; z < 16; z++) {
                            int blockStateId = Protocol1_17To1_16_4.MAPPINGS.getNewBlockStateId(section.getFlatBlock(x, y, z));
                            int i = j;
                            j++;
                            blockChangeRecords[i] = new BlockChangeRecord1_16_2(x, y, z, blockStateId);
                        }
                    }
                }
                blockChangePacket.write(Type.VAR_LONG_BLOCK_CHANGE_RECORD_ARRAY, blockChangeRecords);
                blockChangePacket.send(Protocol1_17To1_16_4.class);
            }
        }
    }

    public static void addNewDimensionData(CompoundTag tag) {
        tag.put("min_y", new IntTag(0));
        tag.put("height", new IntTag(256));
    }
}
