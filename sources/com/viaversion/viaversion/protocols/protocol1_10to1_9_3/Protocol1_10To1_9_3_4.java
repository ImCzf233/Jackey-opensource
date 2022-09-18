package com.viaversion.viaversion.protocols.protocol1_10to1_9_3;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.rewriter.ItemRewriter;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_9;
import com.viaversion.viaversion.protocols.protocol1_10to1_9_3.packets.InventoryPackets;
import com.viaversion.viaversion.protocols.protocol1_10to1_9_3.storage.ResourcePackTracker;
import com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.types.Chunk1_9_3_4Type;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_10to1_9_3/Protocol1_10To1_9_3_4.class */
public class Protocol1_10To1_9_3_4 extends AbstractProtocol<ClientboundPackets1_9_3, ClientboundPackets1_9_3, ServerboundPackets1_9_3, ServerboundPackets1_9_3> {
    public static final ValueTransformer<Short, Float> TO_NEW_PITCH = new ValueTransformer<Short, Float>(Type.FLOAT) { // from class: com.viaversion.viaversion.protocols.protocol1_10to1_9_3.Protocol1_10To1_9_3_4.1
        public Float transform(PacketWrapper wrapper, Short inputValue) throws Exception {
            return Float.valueOf(inputValue.shortValue() / 63.0f);
        }
    };
    public static final ValueTransformer<List<Metadata>, List<Metadata>> TRANSFORM_METADATA = new ValueTransformer<List<Metadata>, List<Metadata>>(Types1_9.METADATA_LIST) { // from class: com.viaversion.viaversion.protocols.protocol1_10to1_9_3.Protocol1_10To1_9_3_4.2
        public List<Metadata> transform(PacketWrapper wrapper, List<Metadata> inputValue) throws Exception {
            List<Metadata> metaList = new CopyOnWriteArrayList<>(inputValue);
            for (Metadata m : metaList) {
                if (m.m222id() >= 5) {
                    m.setId(m.m222id() + 1);
                }
            }
            return metaList;
        }
    };
    private final ItemRewriter itemRewriter = new InventoryPackets(this);

    public Protocol1_10To1_9_3_4() {
        super(ClientboundPackets1_9_3.class, ClientboundPackets1_9_3.class, ServerboundPackets1_9_3.class, ServerboundPackets1_9_3.class);
    }

    @Override // com.viaversion.viaversion.api.protocol.AbstractProtocol
    protected void registerPackets() {
        this.itemRewriter.register();
        registerClientbound((Protocol1_10To1_9_3_4) ClientboundPackets1_9_3.NAMED_SOUND, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_10to1_9_3.Protocol1_10To1_9_3_4.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING);
                map(Type.VAR_INT);
                map(Type.INT);
                map(Type.INT);
                map(Type.INT);
                map(Type.FLOAT);
                map(Type.UNSIGNED_BYTE, Protocol1_10To1_9_3_4.TO_NEW_PITCH);
            }
        });
        registerClientbound((Protocol1_10To1_9_3_4) ClientboundPackets1_9_3.SOUND, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_10to1_9_3.Protocol1_10To1_9_3_4.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.VAR_INT);
                map(Type.INT);
                map(Type.INT);
                map(Type.INT);
                map(Type.FLOAT);
                map(Type.UNSIGNED_BYTE, Protocol1_10To1_9_3_4.TO_NEW_PITCH);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_10to1_9_3.Protocol1_10To1_9_3_4.4.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int id = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                        wrapper.set(Type.VAR_INT, 0, Integer.valueOf(Protocol1_10To1_9_3_4.this.getNewSoundId(id)));
                    }
                });
            }
        });
        registerClientbound((Protocol1_10To1_9_3_4) ClientboundPackets1_9_3.ENTITY_METADATA, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_10to1_9_3.Protocol1_10To1_9_3_4.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Types1_9.METADATA_LIST, Protocol1_10To1_9_3_4.TRANSFORM_METADATA);
            }
        });
        registerClientbound((Protocol1_10To1_9_3_4) ClientboundPackets1_9_3.SPAWN_MOB, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_10to1_9_3.Protocol1_10To1_9_3_4.6
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.UUID);
                map(Type.UNSIGNED_BYTE);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Type.SHORT);
                map(Type.SHORT);
                map(Type.SHORT);
                map(Types1_9.METADATA_LIST, Protocol1_10To1_9_3_4.TRANSFORM_METADATA);
            }
        });
        registerClientbound((Protocol1_10To1_9_3_4) ClientboundPackets1_9_3.SPAWN_PLAYER, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_10to1_9_3.Protocol1_10To1_9_3_4.7
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.UUID);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Types1_9.METADATA_LIST, Protocol1_10To1_9_3_4.TRANSFORM_METADATA);
            }
        });
        registerClientbound((Protocol1_10To1_9_3_4) ClientboundPackets1_9_3.JOIN_GAME, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_10to1_9_3.Protocol1_10To1_9_3_4.8
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.UNSIGNED_BYTE);
                map(Type.INT);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_10to1_9_3.Protocol1_10To1_9_3_4.8.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        ClientWorld clientWorld = (ClientWorld) wrapper.user().get(ClientWorld.class);
                        int dimensionId = ((Integer) wrapper.get(Type.INT, 1)).intValue();
                        clientWorld.setEnvironment(dimensionId);
                    }
                });
            }
        });
        registerClientbound((Protocol1_10To1_9_3_4) ClientboundPackets1_9_3.RESPAWN, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_10to1_9_3.Protocol1_10To1_9_3_4.9
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_10to1_9_3.Protocol1_10To1_9_3_4.9.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        ClientWorld clientWorld = (ClientWorld) wrapper.user().get(ClientWorld.class);
                        int dimensionId = ((Integer) wrapper.get(Type.INT, 0)).intValue();
                        clientWorld.setEnvironment(dimensionId);
                    }
                });
            }
        });
        registerClientbound((Protocol1_10To1_9_3_4) ClientboundPackets1_9_3.CHUNK_DATA, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_10to1_9_3.Protocol1_10To1_9_3_4.10
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_10to1_9_3.Protocol1_10To1_9_3_4.10.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        ChunkSection[] sections;
                        ClientWorld clientWorld = (ClientWorld) wrapper.user().get(ClientWorld.class);
                        Chunk chunk = (Chunk) wrapper.passthrough(new Chunk1_9_3_4Type(clientWorld));
                        if (Via.getConfig().isReplacePistons()) {
                            int replacementId = Via.getConfig().getPistonReplacementId();
                            for (ChunkSection section : chunk.getSections()) {
                                if (section != null) {
                                    section.replacePaletteEntry(36, replacementId);
                                }
                            }
                        }
                    }
                });
            }
        });
        registerClientbound((Protocol1_10To1_9_3_4) ClientboundPackets1_9_3.RESOURCE_PACK, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_10to1_9_3.Protocol1_10To1_9_3_4.11
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING);
                map(Type.STRING);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_10to1_9_3.Protocol1_10To1_9_3_4.11.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        ResourcePackTracker tracker = (ResourcePackTracker) wrapper.user().get(ResourcePackTracker.class);
                        tracker.setLastHash((String) wrapper.get(Type.STRING, 1));
                    }
                });
            }
        });
        registerServerbound((Protocol1_10To1_9_3_4) ServerboundPackets1_9_3.RESOURCE_PACK_STATUS, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_10to1_9_3.Protocol1_10To1_9_3_4.12
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_10to1_9_3.Protocol1_10To1_9_3_4.12.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        ResourcePackTracker tracker = (ResourcePackTracker) wrapper.user().get(ResourcePackTracker.class);
                        wrapper.write(Type.STRING, tracker.getLastHash());
                        wrapper.write(Type.VAR_INT, wrapper.read(Type.VAR_INT));
                    }
                });
            }
        });
    }

    public int getNewSoundId(int id) {
        int newId = id;
        if (id >= 24) {
            newId++;
        }
        if (id >= 248) {
            newId += 4;
        }
        if (id >= 296) {
            newId += 6;
        }
        if (id >= 354) {
            newId += 4;
        }
        if (id >= 372) {
            newId += 4;
        }
        return newId;
    }

    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public void init(UserConnection userConnection) {
        userConnection.put(new ResourcePackTracker());
        if (!userConnection.has(ClientWorld.class)) {
            userConnection.put(new ClientWorld(userConnection));
        }
    }

    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public ItemRewriter getItemRewriter() {
        return this.itemRewriter;
    }
}
