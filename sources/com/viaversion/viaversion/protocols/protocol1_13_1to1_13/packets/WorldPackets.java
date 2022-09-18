package com.viaversion.viaversion.protocols.protocol1_13_1to1_13.packets;

import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.types.Chunk1_13Type;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.rewriter.BlockRewriter;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_13_1to1_13/packets/WorldPackets.class */
public class WorldPackets {
    public static void register(final Protocol protocol) {
        BlockRewriter blockRewriter = new BlockRewriter(protocol, Type.POSITION);
        protocol.registerClientbound((Protocol) ClientboundPackets1_13.CHUNK_DATA, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13_1to1_13.packets.WorldPackets.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_13_1to1_13.packets.WorldPackets.1.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        ChunkSection[] sections;
                        ClientWorld clientWorld = (ClientWorld) wrapper.user().get(ClientWorld.class);
                        Chunk chunk = (Chunk) wrapper.passthrough(new Chunk1_13Type(clientWorld));
                        for (ChunkSection section : chunk.getSections()) {
                            if (section != null) {
                                for (int i = 0; i < section.getPaletteSize(); i++) {
                                    section.setPaletteEntry(i, protocol.getMappingData().getNewBlockStateId(section.getPaletteEntry(i)));
                                }
                            }
                        }
                    }
                });
            }
        });
        blockRewriter.registerBlockAction(ClientboundPackets1_13.BLOCK_ACTION);
        blockRewriter.registerBlockChange(ClientboundPackets1_13.BLOCK_CHANGE);
        blockRewriter.registerMultiBlockChange(ClientboundPackets1_13.MULTI_BLOCK_CHANGE);
        protocol.registerClientbound((Protocol) ClientboundPackets1_13.EFFECT, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13_1to1_13.packets.WorldPackets.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.POSITION);
                map(Type.INT);
                Protocol protocol2 = protocol;
                handler(wrapper -> {
                    int id = ((Integer) wrapper.get(Type.INT, 0)).intValue();
                    if (id != 2000) {
                        if (id == 1010) {
                            wrapper.set(Type.INT, 1, Integer.valueOf(protocol2.getMappingData().getNewItemId(((Integer) wrapper.get(Type.INT, 1)).intValue())));
                            return;
                        } else if (id == 2001) {
                            wrapper.set(Type.INT, 1, Integer.valueOf(protocol2.getMappingData().getNewBlockStateId(((Integer) wrapper.get(Type.INT, 1)).intValue())));
                            return;
                        } else {
                            return;
                        }
                    }
                    int data = ((Integer) wrapper.get(Type.INT, 1)).intValue();
                    switch (data) {
                        case 0:
                        case 3:
                        case 6:
                            wrapper.set(Type.INT, 1, 4);
                            return;
                        case 1:
                            wrapper.set(Type.INT, 1, 2);
                            return;
                        case 2:
                        case 5:
                        case 8:
                            wrapper.set(Type.INT, 1, 5);
                            return;
                        case 4:
                        default:
                            wrapper.set(Type.INT, 1, 0);
                            return;
                        case 7:
                            wrapper.set(Type.INT, 1, 3);
                            return;
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol) ClientboundPackets1_13.JOIN_GAME, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13_1to1_13.packets.WorldPackets.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.UNSIGNED_BYTE);
                map(Type.INT);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_13_1to1_13.packets.WorldPackets.3.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        ClientWorld clientChunks = (ClientWorld) wrapper.user().get(ClientWorld.class);
                        int dimensionId = ((Integer) wrapper.get(Type.INT, 1)).intValue();
                        clientChunks.setEnvironment(dimensionId);
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol) ClientboundPackets1_13.RESPAWN, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13_1to1_13.packets.WorldPackets.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_13_1to1_13.packets.WorldPackets.4.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        ClientWorld clientWorld = (ClientWorld) wrapper.user().get(ClientWorld.class);
                        int dimensionId = ((Integer) wrapper.get(Type.INT, 0)).intValue();
                        clientWorld.setEnvironment(dimensionId);
                    }
                });
            }
        });
    }
}
