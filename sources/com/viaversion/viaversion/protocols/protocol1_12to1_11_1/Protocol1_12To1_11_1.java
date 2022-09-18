package com.viaversion.viaversion.protocols.protocol1_12to1_11_1;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_12Types;
import com.viaversion.viaversion.api.platform.providers.ViaProviders;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.rewriter.ItemRewriter;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_12;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.metadata.MetadataRewriter1_12To1_11_1;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.packets.InventoryPackets;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.providers.InventoryQuickMoveProvider;
import com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.types.Chunk1_9_3_4Type;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import com.viaversion.viaversion.rewriter.SoundRewriter;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_12to1_11_1/Protocol1_12To1_11_1.class */
public class Protocol1_12To1_11_1 extends AbstractProtocol<ClientboundPackets1_9_3, ClientboundPackets1_12, ServerboundPackets1_9_3, ServerboundPackets1_12> {
    private final EntityRewriter metadataRewriter = new MetadataRewriter1_12To1_11_1(this);
    private final ItemRewriter itemRewriter = new InventoryPackets(this);

    public Protocol1_12To1_11_1() {
        super(ClientboundPackets1_9_3.class, ClientboundPackets1_12.class, ServerboundPackets1_9_3.class, ServerboundPackets1_12.class);
    }

    @Override // com.viaversion.viaversion.api.protocol.AbstractProtocol
    protected void registerPackets() {
        this.metadataRewriter.register();
        this.itemRewriter.register();
        registerClientbound((Protocol1_12To1_11_1) ClientboundPackets1_9_3.SPAWN_ENTITY, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_12to1_11_1.Protocol1_12To1_11_1.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.UUID);
                map(Type.BYTE);
                handler(Protocol1_12To1_11_1.this.metadataRewriter.objectTrackerHandler());
            }
        });
        registerClientbound((Protocol1_12To1_11_1) ClientboundPackets1_9_3.SPAWN_MOB, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_12to1_11_1.Protocol1_12To1_11_1.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.UUID);
                map(Type.VAR_INT);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Type.BYTE);
                map(Type.SHORT);
                map(Type.SHORT);
                map(Type.SHORT);
                map(Types1_12.METADATA_LIST);
                handler(Protocol1_12To1_11_1.this.metadataRewriter.trackerAndRewriterHandler(Types1_12.METADATA_LIST));
            }
        });
        registerClientbound((Protocol1_12To1_11_1) ClientboundPackets1_9_3.CHAT_MESSAGE, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_12to1_11_1.Protocol1_12To1_11_1.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_12to1_11_1.Protocol1_12To1_11_1.3.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        if (!Via.getConfig().is1_12NBTArrayFix()) {
                            return;
                        }
                        try {
                            JsonElement obj = Protocol1_9To1_8.FIX_JSON.transform(null, ((JsonElement) wrapper.passthrough(Type.COMPONENT)).toString());
                            TranslateRewriter.toClient(obj, wrapper.user());
                            ChatItemRewriter.toClient(obj, wrapper.user());
                            wrapper.set(Type.COMPONENT, 0, obj);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        registerClientbound((Protocol1_12To1_11_1) ClientboundPackets1_9_3.CHUNK_DATA, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_12to1_11_1.Protocol1_12To1_11_1.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_12to1_11_1.Protocol1_12To1_11_1.4.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        ClientWorld clientWorld = (ClientWorld) wrapper.user().get(ClientWorld.class);
                        Chunk1_9_3_4Type type = new Chunk1_9_3_4Type(clientWorld);
                        Chunk chunk = (Chunk) wrapper.passthrough(type);
                        for (int i = 0; i < chunk.getSections().length; i++) {
                            ChunkSection section = chunk.getSections()[i];
                            if (section != null) {
                                for (int y = 0; y < 16; y++) {
                                    for (int z = 0; z < 16; z++) {
                                        for (int x = 0; x < 16; x++) {
                                            int block = section.getBlockWithoutData(x, y, z);
                                            if (block == 26) {
                                                CompoundTag tag = new CompoundTag();
                                                tag.put("color", new IntTag(14));
                                                tag.put("x", new IntTag(x + (chunk.getX() << 4)));
                                                tag.put("y", new IntTag(y + (i << 4)));
                                                tag.put("z", new IntTag(z + (chunk.getZ() << 4)));
                                                tag.put("id", new StringTag("minecraft:bed"));
                                                chunk.getBlockEntities().add(tag);
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
        this.metadataRewriter.registerRemoveEntities(ClientboundPackets1_9_3.DESTROY_ENTITIES);
        this.metadataRewriter.registerMetadataRewriter(ClientboundPackets1_9_3.ENTITY_METADATA, Types1_12.METADATA_LIST);
        registerClientbound((Protocol1_12To1_11_1) ClientboundPackets1_9_3.JOIN_GAME, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_12to1_11_1.Protocol1_12To1_11_1.5
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
        registerClientbound((Protocol1_12To1_11_1) ClientboundPackets1_9_3.RESPAWN, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_12to1_11_1.Protocol1_12To1_11_1.6
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
        new SoundRewriter(this, this::getNewSoundId).registerSound(ClientboundPackets1_9_3.SOUND);
        cancelServerbound(ServerboundPackets1_12.PREPARE_CRAFTING_GRID);
        registerServerbound((Protocol1_12To1_11_1) ServerboundPackets1_12.CLIENT_SETTINGS, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_12to1_11_1.Protocol1_12To1_11_1.7
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING);
                map(Type.BYTE);
                map(Type.VAR_INT);
                map(Type.BOOLEAN);
                map(Type.UNSIGNED_BYTE);
                map(Type.VAR_INT);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_12to1_11_1.Protocol1_12To1_11_1.7.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        String locale = (String) wrapper.get(Type.STRING, 0);
                        if (locale.length() > 7) {
                            wrapper.set(Type.STRING, 0, locale.substring(0, 7));
                        }
                    }
                });
            }
        });
        cancelServerbound(ServerboundPackets1_12.RECIPE_BOOK_DATA);
        cancelServerbound(ServerboundPackets1_12.ADVANCEMENT_TAB);
    }

    private int getNewSoundId(int id) {
        int newId = id;
        if (id >= 26) {
            newId += 2;
        }
        if (id >= 70) {
            newId += 4;
        }
        if (id >= 74) {
            newId++;
        }
        if (id >= 143) {
            newId += 3;
        }
        if (id >= 185) {
            newId++;
        }
        if (id >= 263) {
            newId += 7;
        }
        if (id >= 301) {
            newId += 33;
        }
        if (id >= 317) {
            newId += 2;
        }
        if (id >= 491) {
            newId += 3;
        }
        return newId;
    }

    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public void register(ViaProviders providers) {
        providers.register(InventoryQuickMoveProvider.class, new InventoryQuickMoveProvider());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public void init(UserConnection userConnection) {
        userConnection.addEntityTracker(getClass(), new EntityTrackerBase(userConnection, Entity1_12Types.EntityType.PLAYER));
        if (!userConnection.has(ClientWorld.class)) {
            userConnection.put(new ClientWorld(userConnection));
        }
    }

    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public EntityRewriter getEntityRewriter() {
        return this.metadataRewriter;
    }

    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public ItemRewriter getItemRewriter() {
        return this.itemRewriter;
    }
}
