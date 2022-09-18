package com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.packets;

import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord1_16_2;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.Protocol1_16_2To1_16_1;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.types.Chunk1_16_2Type;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.types.Chunk1_16Type;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import java.util.ArrayList;
import java.util.List;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_16_2to1_16_1/packets/WorldPackets.class */
public class WorldPackets {
    private static final BlockChangeRecord[] EMPTY_RECORDS = new BlockChangeRecord[0];

    public static void register(final Protocol protocol) {
        BlockRewriter blockRewriter = new BlockRewriter(protocol, Type.POSITION1_14);
        blockRewriter.registerBlockAction(ClientboundPackets1_16.BLOCK_ACTION);
        blockRewriter.registerBlockChange(ClientboundPackets1_16.BLOCK_CHANGE);
        blockRewriter.registerAcknowledgePlayerDigging(ClientboundPackets1_16.ACKNOWLEDGE_PLAYER_DIGGING);
        protocol.registerClientbound((Protocol) ClientboundPackets1_16.CHUNK_DATA, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.packets.WorldPackets.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                Protocol protocol2 = protocol;
                handler(wrapper -> {
                    Chunk chunk = (Chunk) wrapper.read(new Chunk1_16Type());
                    wrapper.write(new Chunk1_16_2Type(), chunk);
                    for (int s = 0; s < chunk.getSections().length; s++) {
                        ChunkSection section = chunk.getSections()[s];
                        if (section != null) {
                            for (int i = 0; i < section.getPaletteSize(); i++) {
                                int old = section.getPaletteEntry(i);
                                section.setPaletteEntry(i, protocol2.getMappingData().getNewBlockStateId(old));
                            }
                        }
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol) ClientboundPackets1_16.MULTI_BLOCK_CHANGE, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.packets.WorldPackets.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                Protocol protocol2 = protocol;
                handler(wrapper -> {
                    wrapper.cancel();
                    int chunkX = ((Integer) wrapper.read(Type.INT)).intValue();
                    int chunkZ = ((Integer) wrapper.read(Type.INT)).intValue();
                    long chunkPosition = 0 | ((chunkX & 4194303) << 42);
                    long chunkPosition2 = chunkPosition | ((chunkZ & 4194303) << 20);
                    List<BlockChangeRecord>[] sectionRecords = new List[16];
                    BlockChangeRecord[] blockChangeRecord = (BlockChangeRecord[]) wrapper.read(Type.BLOCK_CHANGE_RECORD_ARRAY);
                    for (BlockChangeRecord record : blockChangeRecord) {
                        int chunkY = record.getY() >> 4;
                        List<BlockChangeRecord> list = sectionRecords[chunkY];
                        if (list == null) {
                            List<BlockChangeRecord> arrayList = new ArrayList<>();
                            list = arrayList;
                            sectionRecords[chunkY] = arrayList;
                        }
                        int blockId = protocol2.getMappingData().getNewBlockStateId(record.getBlockId());
                        list.add(new BlockChangeRecord1_16_2(record.getSectionX(), record.getSectionY(), record.getSectionZ(), blockId));
                    }
                    for (int chunkY2 = 0; chunkY2 < sectionRecords.length; chunkY2++) {
                        List<BlockChangeRecord> sectionRecord = sectionRecords[chunkY2];
                        if (sectionRecord != null) {
                            PacketWrapper newPacket = wrapper.create(ClientboundPackets1_16_2.MULTI_BLOCK_CHANGE);
                            newPacket.write(Type.LONG, Long.valueOf(chunkPosition2 | (chunkY2 & 1048575)));
                            newPacket.write(Type.BOOLEAN, false);
                            newPacket.write(Type.VAR_LONG_BLOCK_CHANGE_RECORD_ARRAY, sectionRecord.toArray(WorldPackets.EMPTY_RECORDS));
                            newPacket.send(Protocol1_16_2To1_16_1.class);
                        }
                    }
                });
            }
        });
        blockRewriter.registerEffect(ClientboundPackets1_16.EFFECT, 1010, 2001);
    }
}
