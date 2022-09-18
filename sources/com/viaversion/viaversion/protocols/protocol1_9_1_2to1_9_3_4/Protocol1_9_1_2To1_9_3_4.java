package com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.chunks.BlockEntity;
import com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.types.Chunk1_9_3_4Type;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.types.Chunk1_9_1_2Type;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ServerboundPackets1_9;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_9_1_2to1_9_3_4/Protocol1_9_1_2To1_9_3_4.class */
public class Protocol1_9_1_2To1_9_3_4 extends AbstractProtocol<ClientboundPackets1_9_3, ClientboundPackets1_9, ServerboundPackets1_9_3, ServerboundPackets1_9> {
    public Protocol1_9_1_2To1_9_3_4() {
        super(ClientboundPackets1_9_3.class, ClientboundPackets1_9.class, ServerboundPackets1_9_3.class, ServerboundPackets1_9.class);
    }

    @Override // com.viaversion.viaversion.api.protocol.AbstractProtocol
    protected void registerPackets() {
        registerClientbound((Protocol1_9_1_2To1_9_3_4) ClientboundPackets1_9_3.BLOCK_ENTITY_DATA, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.Protocol1_9_1_2To1_9_3_4.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.POSITION);
                map(Type.UNSIGNED_BYTE);
                map(Type.NBT);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.Protocol1_9_1_2To1_9_3_4.1.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        if (((Short) wrapper.get(Type.UNSIGNED_BYTE, 0)).shortValue() == 9) {
                            Position position = (Position) wrapper.get(Type.POSITION, 0);
                            CompoundTag tag = (CompoundTag) wrapper.get(Type.NBT, 0);
                            wrapper.clearPacket();
                            wrapper.setId(ClientboundPackets1_9.UPDATE_SIGN.ordinal());
                            wrapper.write(Type.POSITION, position);
                            for (int i = 1; i < 5; i++) {
                                wrapper.write(Type.STRING, (String) tag.get("Text" + i).getValue());
                            }
                        }
                    }
                });
            }
        });
        registerClientbound((Protocol1_9_1_2To1_9_3_4) ClientboundPackets1_9_3.CHUNK_DATA, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.Protocol1_9_1_2To1_9_3_4.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.Protocol1_9_1_2To1_9_3_4.2.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        ClientWorld clientWorld = (ClientWorld) wrapper.user().get(ClientWorld.class);
                        Chunk1_9_3_4Type newType = new Chunk1_9_3_4Type(clientWorld);
                        Chunk1_9_1_2Type oldType = new Chunk1_9_1_2Type(clientWorld);
                        Chunk chunk = (Chunk) wrapper.read(newType);
                        wrapper.write(oldType, chunk);
                        BlockEntity.handle(chunk.getBlockEntities(), wrapper.user());
                    }
                });
            }
        });
        registerClientbound((Protocol1_9_1_2To1_9_3_4) ClientboundPackets1_9_3.JOIN_GAME, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.Protocol1_9_1_2To1_9_3_4.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.UNSIGNED_BYTE);
                map(Type.INT);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.Protocol1_9_1_2To1_9_3_4.3.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        ClientWorld clientChunks = (ClientWorld) wrapper.user().get(ClientWorld.class);
                        int dimensionId = ((Integer) wrapper.get(Type.INT, 1)).intValue();
                        clientChunks.setEnvironment(dimensionId);
                    }
                });
            }
        });
        registerClientbound((Protocol1_9_1_2To1_9_3_4) ClientboundPackets1_9_3.RESPAWN, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.Protocol1_9_1_2To1_9_3_4.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.Protocol1_9_1_2To1_9_3_4.4.1
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

    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public void init(UserConnection userConnection) {
        if (!userConnection.has(ClientWorld.class)) {
            userConnection.put(new ClientWorld(userConnection));
        }
    }
}
