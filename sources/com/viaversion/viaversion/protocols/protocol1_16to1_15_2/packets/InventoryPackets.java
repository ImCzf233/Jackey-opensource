package com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.UUIDIntArrayType;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.data.RecipeRewriter1_14;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.Protocol1_16To1_15_2;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ServerboundPackets1_16;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.storage.InventoryTracker1_16;
import com.viaversion.viaversion.rewriter.ItemRewriter;
import java.util.Iterator;
import java.util.UUID;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_16to1_15_2/packets/InventoryPackets.class */
public class InventoryPackets extends ItemRewriter<Protocol1_16To1_15_2> {
    public InventoryPackets(Protocol1_16To1_15_2 protocol) {
        super(protocol);
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    public void registerPackets() {
        final PacketHandler cursorRemapper = wrapper -> {
            PacketWrapper clearPacket = wrapper.create(ClientboundPackets1_16.SET_SLOT);
            clearPacket.write(Type.UNSIGNED_BYTE, (short) -1);
            clearPacket.write(Type.SHORT, (short) -1);
            clearPacket.write(Type.FLAT_VAR_INT_ITEM, null);
            clearPacket.send(Protocol1_16To1_15_2.class);
        };
        ((Protocol1_16To1_15_2) this.protocol).registerClientbound((Protocol1_16To1_15_2) ClientboundPackets1_15.OPEN_WINDOW, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets.InventoryPackets.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.VAR_INT);
                map(Type.COMPONENT);
                handler(cursorRemapper);
                handler(wrapper2 -> {
                    InventoryTracker1_16 inventoryTracker = (InventoryTracker1_16) wrapper2.user().get(InventoryTracker1_16.class);
                    int windowId = ((Integer) wrapper2.get(Type.VAR_INT, 0)).intValue();
                    int windowType = ((Integer) wrapper2.get(Type.VAR_INT, 1)).intValue();
                    if (windowType >= 20) {
                        wrapper2.set(Type.VAR_INT, 1, Integer.valueOf(windowType + 1));
                    }
                    inventoryTracker.setInventory((short) windowId);
                });
            }
        });
        ((Protocol1_16To1_15_2) this.protocol).registerClientbound((Protocol1_16To1_15_2) ClientboundPackets1_15.CLOSE_WINDOW, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets.InventoryPackets.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(cursorRemapper);
                handler(wrapper2 -> {
                    InventoryTracker1_16 inventoryTracker = (InventoryTracker1_16) wrapper2.user().get(InventoryTracker1_16.class);
                    inventoryTracker.setInventory((short) -1);
                });
            }
        });
        ((Protocol1_16To1_15_2) this.protocol).registerClientbound((Protocol1_16To1_15_2) ClientboundPackets1_15.WINDOW_PROPERTY, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets.InventoryPackets.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                map(Type.SHORT);
                map(Type.SHORT);
                handler(wrapper2 -> {
                    short enchantmentId;
                    short property = ((Short) wrapper2.get(Type.SHORT, 0)).shortValue();
                    if (property >= 4 && property <= 6 && (enchantmentId = ((Short) wrapper2.get(Type.SHORT, 1)).shortValue()) >= 11) {
                        wrapper2.set(Type.SHORT, 1, Short.valueOf((short) (enchantmentId + 1)));
                    }
                });
            }
        });
        registerSetCooldown(ClientboundPackets1_15.COOLDOWN);
        registerWindowItems(ClientboundPackets1_15.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY);
        registerTradeList(ClientboundPackets1_15.TRADE_LIST, Type.FLAT_VAR_INT_ITEM);
        registerSetSlot(ClientboundPackets1_15.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
        registerAdvancements(ClientboundPackets1_15.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_16To1_15_2) this.protocol).registerClientbound((Protocol1_16To1_15_2) ClientboundPackets1_15.ENTITY_EQUIPMENT, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets.InventoryPackets.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                handler(wrapper2 -> {
                    int slot = ((Integer) wrapper2.read(Type.VAR_INT)).intValue();
                    wrapper2.write(Type.BYTE, Byte.valueOf((byte) slot));
                    InventoryPackets.this.handleItemToClient((Item) wrapper2.passthrough(Type.FLAT_VAR_INT_ITEM));
                });
            }
        });
        new RecipeRewriter1_14(this.protocol).registerDefaultHandler(ClientboundPackets1_15.DECLARE_RECIPES);
        registerClickWindow(ServerboundPackets1_16.CLICK_WINDOW, Type.FLAT_VAR_INT_ITEM);
        registerCreativeInvAction(ServerboundPackets1_16.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_16To1_15_2) this.protocol).registerServerbound((Protocol1_16To1_15_2) ServerboundPackets1_16.CLOSE_WINDOW, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets.InventoryPackets.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper2 -> {
                    InventoryTracker1_16 inventoryTracker = (InventoryTracker1_16) wrapper2.user().get(InventoryTracker1_16.class);
                    inventoryTracker.setInventory((short) -1);
                });
            }
        });
        ((Protocol1_16To1_15_2) this.protocol).registerServerbound((Protocol1_16To1_15_2) ServerboundPackets1_16.EDIT_BOOK, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets.InventoryPackets.6
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper2 -> {
                    InventoryPackets.this.handleItemToServer((Item) wrapper2.passthrough(Type.FLAT_VAR_INT_ITEM));
                });
            }
        });
        registerSpawnParticle(ClientboundPackets1_15.SPAWN_PARTICLE, Type.FLAT_VAR_INT_ITEM, Type.DOUBLE);
    }

    @Override // com.viaversion.viaversion.rewriter.ItemRewriter, com.viaversion.viaversion.api.rewriter.ItemRewriter
    public Item handleItemToClient(Item item) {
        if (item == null) {
            return null;
        }
        if (item.identifier() == 771 && item.tag() != null) {
            CompoundTag tag = item.tag();
            Tag ownerTag = tag.get("SkullOwner");
            if (ownerTag instanceof CompoundTag) {
                CompoundTag ownerCompundTag = (CompoundTag) ownerTag;
                Tag idTag = ownerCompundTag.get("Id");
                if (idTag instanceof StringTag) {
                    UUID id = UUID.fromString((String) idTag.getValue());
                    ownerCompundTag.put("Id", new IntArrayTag(UUIDIntArrayType.uuidToIntArray(id)));
                }
            }
        }
        oldToNewAttributes(item);
        item.setIdentifier(Protocol1_16To1_15_2.MAPPINGS.getNewItemId(item.identifier()));
        return item;
    }

    @Override // com.viaversion.viaversion.rewriter.ItemRewriter, com.viaversion.viaversion.api.rewriter.ItemRewriter
    public Item handleItemToServer(Item item) {
        if (item == null) {
            return null;
        }
        item.setIdentifier(Protocol1_16To1_15_2.MAPPINGS.getOldItemId(item.identifier()));
        if (item.identifier() == 771 && item.tag() != null) {
            CompoundTag tag = item.tag();
            Tag ownerTag = tag.get("SkullOwner");
            if (ownerTag instanceof CompoundTag) {
                CompoundTag ownerCompundTag = (CompoundTag) ownerTag;
                Tag idTag = ownerCompundTag.get("Id");
                if (idTag instanceof IntArrayTag) {
                    UUID id = UUIDIntArrayType.uuidFromIntArray((int[]) idTag.getValue());
                    ownerCompundTag.put("Id", new StringTag(id.toString()));
                }
            }
        }
        newToOldAttributes(item);
        return item;
    }

    public static void oldToNewAttributes(Item item) {
        ListTag attributes;
        if (item.tag() == null || (attributes = (ListTag) item.tag().get("AttributeModifiers")) == null) {
            return;
        }
        Iterator<Tag> it = attributes.iterator();
        while (it.hasNext()) {
            Tag tag = it.next();
            CompoundTag attribute = (CompoundTag) tag;
            rewriteAttributeName(attribute, "AttributeName", false);
            rewriteAttributeName(attribute, "Name", false);
            Tag leastTag = attribute.get("UUIDLeast");
            if (leastTag != null) {
                Tag mostTag = attribute.get("UUIDMost");
                int[] uuidIntArray = UUIDIntArrayType.bitsToIntArray(((NumberTag) leastTag).asLong(), ((NumberTag) mostTag).asLong());
                attribute.put("UUID", new IntArrayTag(uuidIntArray));
            }
        }
    }

    public static void newToOldAttributes(Item item) {
        ListTag attributes;
        if (item.tag() == null || (attributes = (ListTag) item.tag().get("AttributeModifiers")) == null) {
            return;
        }
        Iterator<Tag> it = attributes.iterator();
        while (it.hasNext()) {
            Tag tag = it.next();
            CompoundTag attribute = (CompoundTag) tag;
            rewriteAttributeName(attribute, "AttributeName", true);
            rewriteAttributeName(attribute, "Name", true);
            IntArrayTag uuidTag = (IntArrayTag) attribute.get("UUID");
            if (uuidTag != null && uuidTag.getValue().length == 4) {
                UUID uuid = UUIDIntArrayType.uuidFromIntArray(uuidTag.getValue());
                attribute.put("UUIDLeast", new LongTag(uuid.getLeastSignificantBits()));
                attribute.put("UUIDMost", new LongTag(uuid.getMostSignificantBits()));
            }
        }
    }

    public static void rewriteAttributeName(CompoundTag compoundTag, String entryName, boolean inverse) {
        StringTag attributeNameTag = (StringTag) compoundTag.get(entryName);
        if (attributeNameTag == null) {
            return;
        }
        String attributeName = attributeNameTag.getValue();
        if (inverse && !attributeName.startsWith("minecraft:")) {
            attributeName = "minecraft:" + attributeName;
        }
        String mappedAttribute = (String) (inverse ? Protocol1_16To1_15_2.MAPPINGS.getAttributeMappings().inverse() : Protocol1_16To1_15_2.MAPPINGS.getAttributeMappings()).get(attributeName);
        if (mappedAttribute == null) {
            return;
        }
        attributeNameTag.setValue(mappedAttribute);
    }
}
