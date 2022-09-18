package com.viaversion.viaversion.protocols.protocol1_11to1_10;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_11Types;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.rewriter.ItemRewriter;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_9;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.protocols.protocol1_11to1_10.data.PotionColorMapping;
import com.viaversion.viaversion.protocols.protocol1_11to1_10.metadata.MetadataRewriter1_11To1_10;
import com.viaversion.viaversion.protocols.protocol1_11to1_10.packets.InventoryPackets;
import com.viaversion.viaversion.protocols.protocol1_11to1_10.storage.EntityTracker1_11;
import com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.types.Chunk1_9_3_4Type;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import com.viaversion.viaversion.rewriter.SoundRewriter;
import com.viaversion.viaversion.util.Pair;
import java.util.List;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_11to1_10/Protocol1_11To1_10.class */
public class Protocol1_11To1_10 extends AbstractProtocol<ClientboundPackets1_9_3, ClientboundPackets1_9_3, ServerboundPackets1_9_3, ServerboundPackets1_9_3> {
    private static final ValueTransformer<Float, Short> toOldByte = new ValueTransformer<Float, Short>(Type.UNSIGNED_BYTE) { // from class: com.viaversion.viaversion.protocols.protocol1_11to1_10.Protocol1_11To1_10.1
        public Short transform(PacketWrapper wrapper, Float inputValue) throws Exception {
            return Short.valueOf((short) (inputValue.floatValue() * 16.0f));
        }
    };
    private final EntityRewriter entityRewriter = new MetadataRewriter1_11To1_10(this);
    private final ItemRewriter itemRewriter = new InventoryPackets(this);

    public Protocol1_11To1_10() {
        super(ClientboundPackets1_9_3.class, ClientboundPackets1_9_3.class, ServerboundPackets1_9_3.class, ServerboundPackets1_9_3.class);
    }

    @Override // com.viaversion.viaversion.api.protocol.AbstractProtocol
    protected void registerPackets() {
        this.entityRewriter.register();
        this.itemRewriter.register();
        registerClientbound((Protocol1_11To1_10) ClientboundPackets1_9_3.SPAWN_ENTITY, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_11to1_10.Protocol1_11To1_10.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.UUID);
                map(Type.BYTE);
                handler(Protocol1_11To1_10.this.entityRewriter.objectTrackerHandler());
            }
        });
        registerClientbound((Protocol1_11To1_10) ClientboundPackets1_9_3.SPAWN_MOB, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_11to1_10.Protocol1_11To1_10.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.UUID);
                map(Type.UNSIGNED_BYTE, Type.VAR_INT);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Type.SHORT);
                map(Type.SHORT);
                map(Type.SHORT);
                map(Types1_9.METADATA_LIST);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_11to1_10.Protocol1_11To1_10.3.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int entityId = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                        int type = ((Integer) wrapper.get(Type.VAR_INT, 1)).intValue();
                        Entity1_11Types.EntityType entType = MetadataRewriter1_11To1_10.rewriteEntityType(type, (List) wrapper.get(Types1_9.METADATA_LIST, 0));
                        if (entType != null) {
                            wrapper.set(Type.VAR_INT, 1, Integer.valueOf(entType.getId()));
                            wrapper.user().getEntityTracker(Protocol1_11To1_10.class).addEntity(entityId, entType);
                            Protocol1_11To1_10.this.entityRewriter.handleMetadata(entityId, (List) wrapper.get(Types1_9.METADATA_LIST, 0), wrapper.user());
                        }
                    }
                });
            }
        });
        new SoundRewriter(this, this::getNewSoundId).registerSound(ClientboundPackets1_9_3.SOUND);
        registerClientbound((Protocol1_11To1_10) ClientboundPackets1_9_3.COLLECT_ITEM, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_11to1_10.Protocol1_11To1_10.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.VAR_INT);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_11to1_10.Protocol1_11To1_10.4.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        wrapper.write(Type.VAR_INT, 1);
                    }
                });
            }
        });
        this.entityRewriter.registerMetadataRewriter(ClientboundPackets1_9_3.ENTITY_METADATA, Types1_9.METADATA_LIST);
        registerClientbound((Protocol1_11To1_10) ClientboundPackets1_9_3.ENTITY_TELEPORT, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_11to1_10.Protocol1_11To1_10.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Type.BOOLEAN);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_11to1_10.Protocol1_11To1_10.5.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int entityID = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                        if (Via.getConfig().isHologramPatch()) {
                            EntityTracker1_11 tracker = (EntityTracker1_11) wrapper.user().getEntityTracker(Protocol1_11To1_10.class);
                            if (tracker.isHologram(entityID)) {
                                Double newValue = (Double) wrapper.get(Type.DOUBLE, 1);
                                wrapper.set(Type.DOUBLE, 1, Double.valueOf(newValue.doubleValue() - Via.getConfig().getHologramYOffset()));
                            }
                        }
                    }
                });
            }
        });
        this.entityRewriter.registerRemoveEntities(ClientboundPackets1_9_3.DESTROY_ENTITIES);
        registerClientbound((Protocol1_11To1_10) ClientboundPackets1_9_3.TITLE, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_11to1_10.Protocol1_11To1_10.6
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_11to1_10.Protocol1_11To1_10.6.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int action = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                        if (action >= 2) {
                            wrapper.set(Type.VAR_INT, 0, Integer.valueOf(action + 1));
                        }
                    }
                });
            }
        });
        registerClientbound((Protocol1_11To1_10) ClientboundPackets1_9_3.BLOCK_ACTION, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_11to1_10.Protocol1_11To1_10.7
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.POSITION);
                map(Type.UNSIGNED_BYTE);
                map(Type.UNSIGNED_BYTE);
                map(Type.VAR_INT);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_11to1_10.Protocol1_11To1_10.7.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper actionWrapper) throws Exception {
                        if (Via.getConfig().isPistonAnimationPatch()) {
                            int id = ((Integer) actionWrapper.get(Type.VAR_INT, 0)).intValue();
                            if (id == 33 || id == 29) {
                                actionWrapper.cancel();
                            }
                        }
                    }
                });
            }
        });
        registerClientbound((Protocol1_11To1_10) ClientboundPackets1_9_3.BLOCK_ENTITY_DATA, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_11to1_10.Protocol1_11To1_10.8
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.POSITION);
                map(Type.UNSIGNED_BYTE);
                map(Type.NBT);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_11to1_10.Protocol1_11To1_10.8.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        CompoundTag tag = (CompoundTag) wrapper.get(Type.NBT, 0);
                        if (((Short) wrapper.get(Type.UNSIGNED_BYTE, 0)).shortValue() == 1) {
                            EntityIdRewriter.toClientSpawner(tag);
                        }
                        if (tag.contains("id")) {
                            ((StringTag) tag.get("id")).setValue(BlockEntityRewriter.toNewIdentifier((String) tag.get("id").getValue()));
                        }
                    }
                });
            }
        });
        registerClientbound((Protocol1_11To1_10) ClientboundPackets1_9_3.CHUNK_DATA, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_11to1_10.Protocol1_11To1_10.9
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_11to1_10.Protocol1_11To1_10.9.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        ClientWorld clientWorld = (ClientWorld) wrapper.user().get(ClientWorld.class);
                        Chunk chunk = (Chunk) wrapper.passthrough(new Chunk1_9_3_4Type(clientWorld));
                        if (chunk.getBlockEntities() == null) {
                            return;
                        }
                        for (CompoundTag tag : chunk.getBlockEntities()) {
                            if (tag.contains("id")) {
                                String identifier = ((StringTag) tag.get("id")).getValue();
                                if (identifier.equals("MobSpawner")) {
                                    EntityIdRewriter.toClientSpawner(tag);
                                }
                                ((StringTag) tag.get("id")).setValue(BlockEntityRewriter.toNewIdentifier(identifier));
                            }
                        }
                    }
                });
            }
        });
        registerClientbound((Protocol1_11To1_10) ClientboundPackets1_9_3.JOIN_GAME, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_11to1_10.Protocol1_11To1_10.10
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.UNSIGNED_BYTE);
                map(Type.INT);
                handler(wrapper -> {
                    ClientWorld clientChunks = (ClientWorld) wrapper.user().get(ClientWorld.class);
                    int dimensionId = ((Integer) wrapper.get(Type.INT, 1)).intValue();
                    clientChunks.setEnvironment(dimensionId);
                });
            }
        });
        registerClientbound((Protocol1_11To1_10) ClientboundPackets1_9_3.RESPAWN, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_11to1_10.Protocol1_11To1_10.11
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                handler(wrapper -> {
                    ClientWorld clientWorld = (ClientWorld) wrapper.user().get(ClientWorld.class);
                    int dimensionId = ((Integer) wrapper.get(Type.INT, 0)).intValue();
                    clientWorld.setEnvironment(dimensionId);
                });
            }
        });
        registerClientbound((Protocol1_11To1_10) ClientboundPackets1_9_3.EFFECT, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_11to1_10.Protocol1_11To1_10.12
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.POSITION);
                map(Type.INT);
                map(Type.BOOLEAN);
                handler(packetWrapper -> {
                    int data;
                    int effectID = ((Integer) packetWrapper.get(Type.INT, 0)).intValue();
                    if (effectID == 2002) {
                        int data2 = ((Integer) packetWrapper.get(Type.INT, 1)).intValue();
                        boolean isInstant = false;
                        Pair<Integer, Boolean> newData = PotionColorMapping.getNewData(data2);
                        if (newData == null) {
                            Via.getPlatform().getLogger().warning("Received unknown 1.11 -> 1.10.2 potion data (" + data2 + ")");
                            data = 0;
                        } else {
                            data = newData.key().intValue();
                            isInstant = newData.value().booleanValue();
                        }
                        if (isInstant) {
                            packetWrapper.set(Type.INT, 0, 2007);
                        }
                        packetWrapper.set(Type.INT, 1, Integer.valueOf(data));
                    }
                });
            }
        });
        registerServerbound((Protocol1_11To1_10) ServerboundPackets1_9_3.PLAYER_BLOCK_PLACEMENT, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_11to1_10.Protocol1_11To1_10.13
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.POSITION);
                map(Type.VAR_INT);
                map(Type.VAR_INT);
                map(Type.FLOAT, Protocol1_11To1_10.toOldByte);
                map(Type.FLOAT, Protocol1_11To1_10.toOldByte);
                map(Type.FLOAT, Protocol1_11To1_10.toOldByte);
            }
        });
        registerServerbound((Protocol1_11To1_10) ServerboundPackets1_9_3.CHAT_MESSAGE, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_11to1_10.Protocol1_11To1_10.14
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_11to1_10.Protocol1_11To1_10.14.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        String msg = (String) wrapper.get(Type.STRING, 0);
                        if (msg.length() > 100) {
                            wrapper.set(Type.STRING, 0, msg.substring(0, 100));
                        }
                    }
                });
            }
        });
    }

    private int getNewSoundId(int id) {
        if (id == 196) {
            return -1;
        }
        if (id >= 85) {
            id += 2;
        }
        if (id >= 176) {
            id++;
        }
        if (id >= 197) {
            id += 8;
        }
        if (id >= 207) {
            id--;
        }
        if (id >= 279) {
            id += 9;
        }
        if (id >= 296) {
            id++;
        }
        if (id >= 390) {
            id += 4;
        }
        if (id >= 400) {
            id += 3;
        }
        if (id >= 450) {
            id++;
        }
        if (id >= 455) {
            id++;
        }
        if (id >= 470) {
            id++;
        }
        return id;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public void init(UserConnection userConnection) {
        userConnection.addEntityTracker(getClass(), new EntityTracker1_11(userConnection));
        if (!userConnection.has(ClientWorld.class)) {
            userConnection.put(new ClientWorld(userConnection));
        }
    }

    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public EntityRewriter getEntityRewriter() {
        return this.entityRewriter;
    }

    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public ItemRewriter getItemRewriter() {
        return this.itemRewriter;
    }
}
