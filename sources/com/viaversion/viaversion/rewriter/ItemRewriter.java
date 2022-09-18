package com.viaversion.viaversion.rewriter;

import com.viaversion.viaversion.api.data.ParticleMappings;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.rewriter.RewriterBase;
import com.viaversion.viaversion.api.type.Type;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/rewriter/ItemRewriter.class */
public abstract class ItemRewriter<T extends Protocol> extends RewriterBase<T> implements com.viaversion.viaversion.api.rewriter.ItemRewriter<T> {
    public ItemRewriter(T protocol) {
        super(protocol);
    }

    public Item handleItemToClient(Item item) {
        if (item == null) {
            return null;
        }
        if (this.protocol.getMappingData() != null && this.protocol.getMappingData().getItemMappings() != null) {
            item.setIdentifier(this.protocol.getMappingData().getNewItemId(item.identifier()));
        }
        return item;
    }

    public Item handleItemToServer(Item item) {
        if (item == null) {
            return null;
        }
        if (this.protocol.getMappingData() != null && this.protocol.getMappingData().getItemMappings() != null) {
            item.setIdentifier(this.protocol.getMappingData().getOldItemId(item.identifier()));
        }
        return item;
    }

    public void registerWindowItems(ClientboundPacketType packetType, final Type<Item[]> type) {
        this.protocol.registerClientbound(packetType, new PacketRemapper() { // from class: com.viaversion.viaversion.rewriter.ItemRewriter.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                map(type);
                handler(ItemRewriter.this.itemArrayHandler(type));
            }
        });
    }

    public void registerWindowItems1_17_1(ClientboundPacketType packetType, final Type<Item[]> itemsType, final Type<Item> carriedItemType) {
        this.protocol.registerClientbound(packetType, new PacketRemapper() { // from class: com.viaversion.viaversion.rewriter.ItemRewriter.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                map(Type.VAR_INT);
                map(itemsType);
                map(carriedItemType);
                handler(wrapper -> {
                    Item[] items = (Item[]) wrapper.get(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT, 0);
                    for (Item item : items) {
                        ItemRewriter.this.handleItemToClient(item);
                    }
                    ItemRewriter.this.handleItemToClient((Item) wrapper.get(Type.FLAT_VAR_INT_ITEM, 0));
                });
            }
        });
    }

    public void registerSetSlot(ClientboundPacketType packetType, final Type<Item> type) {
        this.protocol.registerClientbound(packetType, new PacketRemapper() { // from class: com.viaversion.viaversion.rewriter.ItemRewriter.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                map(Type.SHORT);
                map(type);
                handler(ItemRewriter.this.itemToClientHandler(type));
            }
        });
    }

    public void registerSetSlot1_17_1(ClientboundPacketType packetType, final Type<Item> type) {
        this.protocol.registerClientbound(packetType, new PacketRemapper() { // from class: com.viaversion.viaversion.rewriter.ItemRewriter.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                map(Type.VAR_INT);
                map(Type.SHORT);
                map(type);
                handler(ItemRewriter.this.itemToClientHandler(type));
            }
        });
    }

    public void registerEntityEquipment(ClientboundPacketType packetType, final Type<Item> type) {
        this.protocol.registerClientbound(packetType, new PacketRemapper() { // from class: com.viaversion.viaversion.rewriter.ItemRewriter.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.VAR_INT);
                map(type);
                handler(ItemRewriter.this.itemToClientHandler(type));
            }
        });
    }

    public void registerEntityEquipmentArray(ClientboundPacketType packetType, final Type<Item> type) {
        this.protocol.registerClientbound(packetType, new PacketRemapper() { // from class: com.viaversion.viaversion.rewriter.ItemRewriter.6
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                Type type2 = type;
                handler(wrapper -> {
                    byte slot;
                    do {
                        slot = ((Byte) type2.passthrough(Type.BYTE)).byteValue();
                        ItemRewriter.this.handleItemToClient((Item) type2.passthrough(type));
                    } while ((slot & Byte.MIN_VALUE) != 0);
                });
            }
        });
    }

    public void registerCreativeInvAction(ServerboundPacketType packetType, final Type<Item> type) {
        this.protocol.registerServerbound(packetType, new PacketRemapper() { // from class: com.viaversion.viaversion.rewriter.ItemRewriter.7
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.SHORT);
                map(type);
                handler(ItemRewriter.this.itemToServerHandler(type));
            }
        });
    }

    public void registerClickWindow(ServerboundPacketType packetType, final Type<Item> type) {
        this.protocol.registerServerbound(packetType, new PacketRemapper() { // from class: com.viaversion.viaversion.rewriter.ItemRewriter.8
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                map(Type.SHORT);
                map(Type.BYTE);
                map(Type.SHORT);
                map(Type.VAR_INT);
                map(type);
                handler(ItemRewriter.this.itemToServerHandler(type));
            }
        });
    }

    public void registerClickWindow1_17(ServerboundPacketType packetType, final Type<Item> type) {
        this.protocol.registerServerbound(packetType, new PacketRemapper() { // from class: com.viaversion.viaversion.rewriter.ItemRewriter.9
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                map(Type.SHORT);
                map(Type.BYTE);
                map(Type.VAR_INT);
                Type type2 = type;
                handler(wrapper -> {
                    int length = ((Integer) type2.passthrough(Type.VAR_INT)).intValue();
                    for (int i = 0; i < length; i++) {
                        type2.passthrough(Type.SHORT);
                        ItemRewriter.this.handleItemToServer((Item) type2.passthrough(type));
                    }
                    ItemRewriter.this.handleItemToServer((Item) type2.passthrough(type));
                });
            }
        });
    }

    public void registerClickWindow1_17_1(ServerboundPacketType packetType, final Type<Item> type) {
        this.protocol.registerServerbound(packetType, new PacketRemapper() { // from class: com.viaversion.viaversion.rewriter.ItemRewriter.10
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                map(Type.VAR_INT);
                map(Type.SHORT);
                map(Type.BYTE);
                map(Type.VAR_INT);
                Type type2 = type;
                handler(wrapper -> {
                    int length = ((Integer) type2.passthrough(Type.VAR_INT)).intValue();
                    for (int i = 0; i < length; i++) {
                        type2.passthrough(Type.SHORT);
                        ItemRewriter.this.handleItemToServer((Item) type2.passthrough(type));
                    }
                    ItemRewriter.this.handleItemToServer((Item) type2.passthrough(type));
                });
            }
        });
    }

    public void registerSetCooldown(ClientboundPacketType packetType) {
        this.protocol.registerClientbound(packetType, new PacketRemapper() { // from class: com.viaversion.viaversion.rewriter.ItemRewriter.11
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    int itemId = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                    wrapper.write(Type.VAR_INT, Integer.valueOf(ItemRewriter.this.protocol.getMappingData().getNewItemId(itemId)));
                });
            }
        });
    }

    public void registerTradeList(ClientboundPacketType packetType, final Type<Item> type) {
        this.protocol.registerClientbound(packetType, new PacketRemapper() { // from class: com.viaversion.viaversion.rewriter.ItemRewriter.12
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                Type type2 = type;
                handler(wrapper -> {
                    type2.passthrough(Type.VAR_INT);
                    int size = ((Short) type2.passthrough(Type.UNSIGNED_BYTE)).shortValue();
                    for (int i = 0; i < size; i++) {
                        ItemRewriter.this.handleItemToClient((Item) type2.passthrough(type));
                        ItemRewriter.this.handleItemToClient((Item) type2.passthrough(type));
                        if (((Boolean) type2.passthrough(Type.BOOLEAN)).booleanValue()) {
                            ItemRewriter.this.handleItemToClient((Item) type2.passthrough(type));
                        }
                        type2.passthrough(Type.BOOLEAN);
                        type2.passthrough(Type.INT);
                        type2.passthrough(Type.INT);
                        type2.passthrough(Type.INT);
                        type2.passthrough(Type.INT);
                        type2.passthrough(Type.FLOAT);
                        type2.passthrough(Type.INT);
                    }
                });
            }
        });
    }

    public void registerAdvancements(ClientboundPacketType packetType, final Type<Item> type) {
        this.protocol.registerClientbound(packetType, new PacketRemapper() { // from class: com.viaversion.viaversion.rewriter.ItemRewriter.13
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                Type type2 = type;
                handler(wrapper -> {
                    type2.passthrough(Type.BOOLEAN);
                    int size = ((Integer) type2.passthrough(Type.VAR_INT)).intValue();
                    for (int i = 0; i < size; i++) {
                        type2.passthrough(Type.STRING);
                        if (((Boolean) type2.passthrough(Type.BOOLEAN)).booleanValue()) {
                            type2.passthrough(Type.STRING);
                        }
                        if (((Boolean) type2.passthrough(Type.BOOLEAN)).booleanValue()) {
                            type2.passthrough(Type.COMPONENT);
                            type2.passthrough(Type.COMPONENT);
                            ItemRewriter.this.handleItemToClient((Item) type2.passthrough(type));
                            type2.passthrough(Type.VAR_INT);
                            int flags = ((Integer) type2.passthrough(Type.INT)).intValue();
                            if ((flags & 1) != 0) {
                                type2.passthrough(Type.STRING);
                            }
                            type2.passthrough(Type.FLOAT);
                            type2.passthrough(Type.FLOAT);
                        }
                        type2.passthrough(Type.STRING_ARRAY);
                        int arrayLength = ((Integer) type2.passthrough(Type.VAR_INT)).intValue();
                        for (int array = 0; array < arrayLength; array++) {
                            type2.passthrough(Type.STRING_ARRAY);
                        }
                    }
                });
            }
        });
    }

    public void registerSpawnParticle(ClientboundPacketType packetType, final Type<Item> itemType, final Type<?> coordType) {
        this.protocol.registerClientbound(packetType, new PacketRemapper() { // from class: com.viaversion.viaversion.rewriter.ItemRewriter.14
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.BOOLEAN);
                map(coordType);
                map(coordType);
                map(coordType);
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.INT);
                handler(ItemRewriter.this.getSpawnParticleHandler(itemType));
            }
        });
    }

    public PacketHandler getSpawnParticleHandler(Type<Item> itemType) {
        return wrapper -> {
            int id = ((Integer) itemType.get(Type.INT, 0)).intValue();
            if (id == -1) {
                return;
            }
            ParticleMappings mappings = this.protocol.getMappingData().getParticleMappings();
            if (mappings.isBlockParticle(id)) {
                int data = ((Integer) itemType.passthrough(Type.VAR_INT)).intValue();
                itemType.set(Type.VAR_INT, 0, Integer.valueOf(this.protocol.getMappingData().getNewBlockStateId(data)));
            } else if (mappings.isItemParticle(id)) {
                handleItemToClient((Item) itemType.passthrough(itemType));
            }
            int newId = this.protocol.getMappingData().getNewParticleId(id);
            if (newId != id) {
                itemType.set(Type.INT, 0, Integer.valueOf(newId));
            }
        };
    }

    public PacketHandler itemArrayHandler(Type<Item[]> type) {
        return wrapper -> {
            Item[] items = (Item[]) type.get(type, 0);
            for (Item item : items) {
                handleItemToClient(item);
            }
        };
    }

    public PacketHandler itemToClientHandler(Type<Item> type) {
        return wrapper -> {
            handleItemToClient((Item) type.get(type, 0));
        };
    }

    public PacketHandler itemToServerHandler(Type<Item> type) {
        return wrapper -> {
            handleItemToServer((Item) type.get(type, 0));
        };
    }
}
