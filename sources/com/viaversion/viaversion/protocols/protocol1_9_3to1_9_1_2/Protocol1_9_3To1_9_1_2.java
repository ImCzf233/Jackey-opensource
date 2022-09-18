package com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.types.Chunk1_9_3_4Type;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.chunks.FakeTileEntity;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.types.Chunk1_9_1_2Type;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ServerboundPackets1_9;
import java.util.List;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_9_3to1_9_1_2/Protocol1_9_3To1_9_1_2.class */
public class Protocol1_9_3To1_9_1_2 extends AbstractProtocol<ClientboundPackets1_9, ClientboundPackets1_9_3, ServerboundPackets1_9, ServerboundPackets1_9_3> {
    public static final ValueTransformer<Short, Short> ADJUST_PITCH = new ValueTransformer<Short, Short>(Type.UNSIGNED_BYTE, Type.UNSIGNED_BYTE) { // from class: com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.Protocol1_9_3To1_9_1_2.1
        public Short transform(PacketWrapper wrapper, Short inputValue) throws Exception {
            return Short.valueOf((short) Math.round((inputValue.shortValue() / 63.5f) * 63.0f));
        }
    };

    public Protocol1_9_3To1_9_1_2() {
        super(ClientboundPackets1_9.class, ClientboundPackets1_9_3.class, ServerboundPackets1_9.class, ServerboundPackets1_9_3.class);
    }

    @Override // com.viaversion.viaversion.api.protocol.AbstractProtocol
    protected void registerPackets() {
        registerClientbound((Protocol1_9_3To1_9_1_2) ClientboundPackets1_9.UPDATE_SIGN, (ClientboundPackets1_9) null, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.Protocol1_9_3To1_9_1_2.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.Protocol1_9_3To1_9_1_2.2.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        Position position = (Position) wrapper.read(Type.POSITION);
                        JsonElement[] lines = new JsonElement[4];
                        for (int i = 0; i < 4; i++) {
                            lines[i] = (JsonElement) wrapper.read(Type.COMPONENT);
                        }
                        wrapper.clearInputBuffer();
                        wrapper.setId(9);
                        wrapper.write(Type.POSITION, position);
                        wrapper.write(Type.UNSIGNED_BYTE, (short) 9);
                        CompoundTag tag = new CompoundTag();
                        tag.put("id", new StringTag("Sign"));
                        tag.put("x", new IntTag(position.m228x()));
                        tag.put("y", new IntTag(position.m227y()));
                        tag.put("z", new IntTag(position.m226z()));
                        for (int i2 = 0; i2 < lines.length; i2++) {
                            tag.put("Text" + (i2 + 1), new StringTag(lines[i2].toString()));
                        }
                        wrapper.write(Type.NBT, tag);
                    }
                });
            }
        });
        registerClientbound((Protocol1_9_3To1_9_1_2) ClientboundPackets1_9.CHUNK_DATA, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.Protocol1_9_3To1_9_1_2.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.Protocol1_9_3To1_9_1_2.3.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        ClientWorld clientWorld = (ClientWorld) wrapper.user().get(ClientWorld.class);
                        Chunk chunk = (Chunk) wrapper.read(new Chunk1_9_1_2Type(clientWorld));
                        wrapper.write(new Chunk1_9_3_4Type(clientWorld), chunk);
                        List<CompoundTag> tags = chunk.getBlockEntities();
                        for (int i = 0; i < chunk.getSections().length; i++) {
                            ChunkSection section = chunk.getSections()[i];
                            if (section != null) {
                                for (int y = 0; y < 16; y++) {
                                    for (int z = 0; z < 16; z++) {
                                        for (int x = 0; x < 16; x++) {
                                            int block = section.getBlockWithoutData(x, y, z);
                                            if (FakeTileEntity.isTileEntity(block)) {
                                                tags.add(FakeTileEntity.createTileEntity(x + (chunk.getX() << 4), y + (i << 4), z + (chunk.getZ() << 4), block));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
            }
        });
        registerClientbound((Protocol1_9_3To1_9_1_2) ClientboundPackets1_9.JOIN_GAME, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.Protocol1_9_3To1_9_1_2.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.UNSIGNED_BYTE);
                map(Type.INT);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.Protocol1_9_3To1_9_1_2.4.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        ClientWorld clientWorld = (ClientWorld) wrapper.user().get(ClientWorld.class);
                        int dimensionId = ((Integer) wrapper.get(Type.INT, 1)).intValue();
                        clientWorld.setEnvironment(dimensionId);
                    }
                });
            }
        });
        registerClientbound((Protocol1_9_3To1_9_1_2) ClientboundPackets1_9.RESPAWN, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.Protocol1_9_3To1_9_1_2.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.Protocol1_9_3To1_9_1_2.5.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        ClientWorld clientWorld = (ClientWorld) wrapper.user().get(ClientWorld.class);
                        int dimensionId = ((Integer) wrapper.get(Type.INT, 0)).intValue();
                        clientWorld.setEnvironment(dimensionId);
                    }
                });
            }
        });
        registerClientbound((Protocol1_9_3To1_9_1_2) ClientboundPackets1_9.SOUND, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.Protocol1_9_3To1_9_1_2.6
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.VAR_INT);
                map(Type.INT);
                map(Type.INT);
                map(Type.INT);
                map(Type.FLOAT);
                map(Protocol1_9_3To1_9_1_2.ADJUST_PITCH);
            }
        });
    }

    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public void init(UserConnection user) {
        if (!user.has(ClientWorld.class)) {
            user.put(new ClientWorld(user));
        }
    }
}
