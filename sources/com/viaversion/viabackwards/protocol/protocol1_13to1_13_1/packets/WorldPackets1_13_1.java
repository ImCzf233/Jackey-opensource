package com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.packets;

import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.Position;
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

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_13to1_13_1/packets/WorldPackets1_13_1.class */
public class WorldPackets1_13_1 {
    public static void register(final Protocol protocol) {
        BlockRewriter blockRewriter = new BlockRewriter(protocol, Type.POSITION);
        protocol.registerClientbound((Protocol) ClientboundPackets1_13.CHUNK_DATA, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.packets.WorldPackets1_13_1.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.packets.WorldPackets1_13_1.1.1
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
        protocol.registerClientbound((Protocol) ClientboundPackets1_13.EFFECT, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.packets.WorldPackets1_13_1.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.POSITION);
                map(Type.INT);
                Protocol protocol2 = protocol;
                handler(wrapper -> {
                    int id = ((Integer) wrapper.get(Type.INT, 0)).intValue();
                    int data = ((Integer) wrapper.get(Type.INT, 1)).intValue();
                    if (id == 1010) {
                        wrapper.set(Type.INT, 1, Integer.valueOf(protocol2.getMappingData().getNewItemId(data)));
                    } else if (id == 2001) {
                        wrapper.set(Type.INT, 1, Integer.valueOf(protocol2.getMappingData().getNewBlockStateId(data)));
                    } else if (id == 2000) {
                        switch (data) {
                            case 0:
                            case 1:
                                Position pos = (Position) wrapper.get(Type.POSITION, 0);
                                BlockFace relative = data == 0 ? BlockFace.BOTTOM : BlockFace.TOP;
                                wrapper.set(Type.POSITION, 0, pos.getRelative(relative));
                                wrapper.set(Type.INT, 1, 4);
                                return;
                            case 2:
                                wrapper.set(Type.INT, 1, 1);
                                return;
                            case 3:
                                wrapper.set(Type.INT, 1, 7);
                                return;
                            case 4:
                                wrapper.set(Type.INT, 1, 3);
                                return;
                            case 5:
                                wrapper.set(Type.INT, 1, 5);
                                return;
                            default:
                                return;
                        }
                    }
                });
            }
        });
    }
}
