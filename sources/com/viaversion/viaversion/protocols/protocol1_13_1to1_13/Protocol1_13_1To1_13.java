package com.viaversion.viaversion.protocols.protocol1_13_1to1_13;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.MappingData;
import com.viaversion.viaversion.api.data.MappingDataBase;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_13Types;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.rewriter.EntityRewriter;
import com.viaversion.viaversion.api.rewriter.ItemRewriter;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.metadata.MetadataRewriter1_13_1To1_13;
import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.packets.EntityPackets;
import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.packets.InventoryPackets;
import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.packets.WorldPackets;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_13_1to1_13/Protocol1_13_1To1_13.class */
public class Protocol1_13_1To1_13 extends AbstractProtocol<ClientboundPackets1_13, ClientboundPackets1_13, ServerboundPackets1_13, ServerboundPackets1_13> {
    public static final MappingData MAPPINGS = new MappingDataBase("1.13", "1.13.2", true);
    private final EntityRewriter entityRewriter = new MetadataRewriter1_13_1To1_13(this);
    private final ItemRewriter itemRewriter = new InventoryPackets(this);

    public Protocol1_13_1To1_13() {
        super(ClientboundPackets1_13.class, ClientboundPackets1_13.class, ServerboundPackets1_13.class, ServerboundPackets1_13.class);
    }

    @Override // com.viaversion.viaversion.api.protocol.AbstractProtocol
    protected void registerPackets() {
        this.entityRewriter.register();
        this.itemRewriter.register();
        EntityPackets.register(this);
        WorldPackets.register(this);
        registerServerbound((Protocol1_13_1To1_13) ServerboundPackets1_13.TAB_COMPLETE, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13_1to1_13.Protocol1_13_1To1_13.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.STRING, new ValueTransformer<String, String>(Type.STRING) { // from class: com.viaversion.viaversion.protocols.protocol1_13_1to1_13.Protocol1_13_1To1_13.1.1
                    public String transform(PacketWrapper wrapper, String inputValue) {
                        return inputValue.startsWith("/") ? inputValue.substring(1) : inputValue;
                    }
                });
            }
        });
        registerServerbound((Protocol1_13_1To1_13) ServerboundPackets1_13.EDIT_BOOK, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13_1to1_13.Protocol1_13_1To1_13.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.FLAT_ITEM);
                map(Type.BOOLEAN);
                handler(wrapper -> {
                    Item item = (Item) wrapper.get(Type.FLAT_ITEM, 0);
                    Protocol1_13_1To1_13.this.itemRewriter.handleItemToServer(item);
                });
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_13_1to1_13.Protocol1_13_1To1_13.2.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper2) throws Exception {
                        int hand = ((Integer) wrapper2.read(Type.VAR_INT)).intValue();
                        if (hand == 1) {
                            wrapper2.cancel();
                        }
                    }
                });
            }
        });
        registerClientbound((Protocol1_13_1To1_13) ClientboundPackets1_13.TAB_COMPLETE, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13_1to1_13.Protocol1_13_1To1_13.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.VAR_INT);
                map(Type.VAR_INT);
                map(Type.VAR_INT);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_13_1to1_13.Protocol1_13_1To1_13.3.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int start = ((Integer) wrapper.get(Type.VAR_INT, 1)).intValue();
                        wrapper.set(Type.VAR_INT, 1, Integer.valueOf(start + 1));
                        int count = ((Integer) wrapper.get(Type.VAR_INT, 3)).intValue();
                        for (int i = 0; i < count; i++) {
                            wrapper.passthrough(Type.STRING);
                            boolean hasTooltip = ((Boolean) wrapper.passthrough(Type.BOOLEAN)).booleanValue();
                            if (hasTooltip) {
                                wrapper.passthrough(Type.STRING);
                            }
                        }
                    }
                });
            }
        });
        registerClientbound((Protocol1_13_1To1_13) ClientboundPackets1_13.BOSSBAR, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13_1to1_13.Protocol1_13_1To1_13.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UUID);
                map(Type.VAR_INT);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_13_1to1_13.Protocol1_13_1To1_13.4.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int action = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                        if (action == 0) {
                            wrapper.passthrough(Type.COMPONENT);
                            wrapper.passthrough(Type.FLOAT);
                            wrapper.passthrough(Type.VAR_INT);
                            wrapper.passthrough(Type.VAR_INT);
                            short flags = ((Byte) wrapper.read(Type.BYTE)).byteValue();
                            if ((flags & 2) != 0) {
                                flags = (short) (flags | 4);
                            }
                            wrapper.write(Type.UNSIGNED_BYTE, Short.valueOf(flags));
                        }
                    }
                });
            }
        });
        new TagRewriter(this).register(ClientboundPackets1_13.TAGS, RegistryType.ITEM);
        new StatisticsRewriter(this).register(ClientboundPackets1_13.STATISTICS);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public void init(UserConnection userConnection) {
        userConnection.addEntityTracker(getClass(), new EntityTrackerBase(userConnection, Entity1_13Types.EntityType.PLAYER));
        if (!userConnection.has(ClientWorld.class)) {
            userConnection.put(new ClientWorld(userConnection));
        }
    }

    @Override // com.viaversion.viaversion.api.protocol.Protocol
    public MappingData getMappingData() {
        return MAPPINGS;
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
