package com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets;

import com.google.common.collect.Sets;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.DoubleTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.RecipeRewriter1_13_2;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.Protocol1_14To1_13_2;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.storage.EntityTracker1_14;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import com.viaversion.viaversion.rewriter.ItemRewriter;
import com.viaversion.viaversion.rewriter.RecipeRewriter;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_14to1_13_2/packets/InventoryPackets.class */
public class InventoryPackets extends ItemRewriter<Protocol1_14To1_13_2> {
    private static final String NBT_TAG_NAME = "ViaVersion|" + Protocol1_14To1_13_2.class.getSimpleName();
    private static final Set<String> REMOVED_RECIPE_TYPES = Sets.newHashSet(new String[]{"crafting_special_banneraddpattern", "crafting_special_repairitem"});
    private static final ComponentRewriter COMPONENT_REWRITER = new ComponentRewriter() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.InventoryPackets.1
        @Override // com.viaversion.viaversion.rewriter.ComponentRewriter
        public void handleTranslate(JsonObject object, String translate) {
            super.handleTranslate(object, translate);
            if (translate.startsWith("block.") && translate.endsWith(".name")) {
                object.addProperty("translate", translate.substring(0, translate.length() - 5));
            }
        }
    };

    public InventoryPackets(Protocol1_14To1_13_2 protocol) {
        super(protocol);
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    public void registerPackets() {
        registerSetCooldown(ClientboundPackets1_13.COOLDOWN);
        registerAdvancements(ClientboundPackets1_13.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_14To1_13_2) this.protocol).registerClientbound((Protocol1_14To1_13_2) ClientboundPackets1_13.OPEN_WINDOW, (ClientboundPackets1_13) null, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.InventoryPackets.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.InventoryPackets.2.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        Short windowId = (Short) wrapper.read(Type.UNSIGNED_BYTE);
                        String type = (String) wrapper.read(Type.STRING);
                        JsonElement title = (JsonElement) wrapper.read(Type.COMPONENT);
                        InventoryPackets.COMPONENT_REWRITER.processText(title);
                        Short slots = (Short) wrapper.read(Type.UNSIGNED_BYTE);
                        if (type.equals("EntityHorse")) {
                            wrapper.setId(31);
                            int entityId = ((Integer) wrapper.read(Type.INT)).intValue();
                            wrapper.write(Type.UNSIGNED_BYTE, windowId);
                            wrapper.write(Type.VAR_INT, Integer.valueOf(slots.intValue()));
                            wrapper.write(Type.INT, Integer.valueOf(entityId));
                            return;
                        }
                        wrapper.setId(46);
                        wrapper.write(Type.VAR_INT, Integer.valueOf(windowId.intValue()));
                        int typeId = -1;
                        boolean z = true;
                        switch (type.hashCode()) {
                            case -1879003021:
                                if (type.equals("minecraft:villager")) {
                                    z = true;
                                    break;
                                }
                                break;
                            case -1719356277:
                                if (type.equals("minecraft:furnace")) {
                                    z = true;
                                    break;
                                }
                                break;
                            case -1293651279:
                                if (type.equals("minecraft:beacon")) {
                                    z = true;
                                    break;
                                }
                                break;
                            case -1150744385:
                                if (type.equals("minecraft:anvil")) {
                                    z = true;
                                    break;
                                }
                                break;
                            case -1149092108:
                                if (type.equals("minecraft:chest")) {
                                    z = true;
                                    break;
                                }
                                break;
                            case -1124126594:
                                if (type.equals("minecraft:crafting_table")) {
                                    z = false;
                                    break;
                                }
                                break;
                            case -1112182111:
                                if (type.equals("minecraft:hopper")) {
                                    z = true;
                                    break;
                                }
                                break;
                            case 319164197:
                                if (type.equals("minecraft:enchanting_table")) {
                                    z = true;
                                    break;
                                }
                                break;
                            case 712019713:
                                if (type.equals("minecraft:dropper")) {
                                    z = true;
                                    break;
                                }
                                break;
                            case 1374330859:
                                if (type.equals("minecraft:shulker_box")) {
                                    z = true;
                                    break;
                                }
                                break;
                            case 1438413556:
                                if (type.equals("minecraft:container")) {
                                    z = true;
                                    break;
                                }
                                break;
                            case 1649065834:
                                if (type.equals("minecraft:brewing_stand")) {
                                    z = true;
                                    break;
                                }
                                break;
                            case 2090881320:
                                if (type.equals("minecraft:dispenser")) {
                                    z = true;
                                    break;
                                }
                                break;
                        }
                        switch (z) {
                            case false:
                                typeId = 11;
                                break;
                            case true:
                                typeId = 13;
                                break;
                            case true:
                            case true:
                                typeId = 6;
                                break;
                            case true:
                                typeId = 12;
                                break;
                            case true:
                                typeId = 10;
                                break;
                            case true:
                                typeId = 18;
                                break;
                            case true:
                                typeId = 8;
                                break;
                            case true:
                                typeId = 7;
                                break;
                            case true:
                                typeId = 15;
                                break;
                            case true:
                                typeId = 19;
                                break;
                            case true:
                            case true:
                            default:
                                if (slots.shortValue() > 0 && slots.shortValue() <= 54) {
                                    typeId = (slots.shortValue() / 9) - 1;
                                    break;
                                }
                                break;
                        }
                        if (typeId == -1) {
                            Via.getPlatform().getLogger().warning("Can't open inventory for 1.14 player! Type: " + type + " Size: " + slots);
                        }
                        wrapper.write(Type.VAR_INT, Integer.valueOf(typeId));
                        wrapper.write(Type.COMPONENT, title);
                    }
                });
            }
        });
        registerWindowItems(ClientboundPackets1_13.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY);
        registerSetSlot(ClientboundPackets1_13.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_14To1_13_2) this.protocol).registerClientbound((Protocol1_14To1_13_2) ClientboundPackets1_13.PLUGIN_MESSAGE, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.InventoryPackets.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.InventoryPackets.3.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        String channel = (String) wrapper.get(Type.STRING, 0);
                        if (!channel.equals("minecraft:trader_list") && !channel.equals("trader_list")) {
                            if (channel.equals("minecraft:book_open") || channel.equals("book_open")) {
                                int hand = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                                wrapper.clearPacket();
                                wrapper.setId(45);
                                wrapper.write(Type.VAR_INT, Integer.valueOf(hand));
                                return;
                            }
                            return;
                        }
                        wrapper.setId(39);
                        wrapper.resetReader();
                        wrapper.read(Type.STRING);
                        int windowId = ((Integer) wrapper.read(Type.INT)).intValue();
                        EntityTracker1_14 tracker = (EntityTracker1_14) wrapper.user().getEntityTracker(Protocol1_14To1_13_2.class);
                        tracker.setLatestTradeWindowId(windowId);
                        wrapper.write(Type.VAR_INT, Integer.valueOf(windowId));
                        int size = ((Short) wrapper.passthrough(Type.UNSIGNED_BYTE)).shortValue();
                        for (int i = 0; i < size; i++) {
                            InventoryPackets.this.handleItemToClient((Item) wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
                            InventoryPackets.this.handleItemToClient((Item) wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
                            boolean secondItem = ((Boolean) wrapper.passthrough(Type.BOOLEAN)).booleanValue();
                            if (secondItem) {
                                InventoryPackets.this.handleItemToClient((Item) wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
                            }
                            wrapper.passthrough(Type.BOOLEAN);
                            wrapper.passthrough(Type.INT);
                            wrapper.passthrough(Type.INT);
                            wrapper.write(Type.INT, 0);
                            wrapper.write(Type.INT, 0);
                            wrapper.write(Type.FLOAT, Float.valueOf(0.0f));
                        }
                        wrapper.write(Type.VAR_INT, 0);
                        wrapper.write(Type.VAR_INT, 0);
                        wrapper.write(Type.BOOLEAN, false);
                    }
                });
            }
        });
        registerEntityEquipment(ClientboundPackets1_13.ENTITY_EQUIPMENT, Type.FLAT_VAR_INT_ITEM);
        final RecipeRewriter recipeRewriter = new RecipeRewriter1_13_2(this.protocol);
        ((Protocol1_14To1_13_2) this.protocol).registerClientbound((Protocol1_14To1_13_2) ClientboundPackets1_13.DECLARE_RECIPES, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.InventoryPackets.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                RecipeRewriter recipeRewriter2 = recipeRewriter;
                handler(wrapper -> {
                    int size = ((Integer) wrapper.passthrough(Type.VAR_INT)).intValue();
                    int deleted = 0;
                    for (int i = 0; i < size; i++) {
                        String id = (String) wrapper.read(Type.STRING);
                        String type = (String) wrapper.read(Type.STRING);
                        if (InventoryPackets.REMOVED_RECIPE_TYPES.contains(type)) {
                            deleted++;
                        } else {
                            wrapper.write(Type.STRING, type);
                            wrapper.write(Type.STRING, id);
                            recipeRewriter2.handle(wrapper, type);
                        }
                    }
                    wrapper.set(Type.VAR_INT, 0, Integer.valueOf(size - deleted));
                });
            }
        });
        registerClickWindow(ServerboundPackets1_14.CLICK_WINDOW, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_14To1_13_2) this.protocol).registerServerbound((Protocol1_14To1_13_2) ServerboundPackets1_14.SELECT_TRADE, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.InventoryPackets.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.InventoryPackets.5.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        PacketWrapper resyncPacket = wrapper.create(8);
                        EntityTracker1_14 tracker = (EntityTracker1_14) wrapper.user().getEntityTracker(Protocol1_14To1_13_2.class);
                        resyncPacket.write(Type.UNSIGNED_BYTE, Short.valueOf((short) tracker.getLatestTradeWindowId()));
                        resyncPacket.write(Type.SHORT, (short) -999);
                        resyncPacket.write(Type.BYTE, (byte) 2);
                        resyncPacket.write(Type.SHORT, Short.valueOf((short) ThreadLocalRandom.current().nextInt()));
                        resyncPacket.write(Type.VAR_INT, 5);
                        CompoundTag tag = new CompoundTag();
                        tag.put("force_resync", new DoubleTag(Double.NaN));
                        resyncPacket.write(Type.FLAT_VAR_INT_ITEM, new DataItem(1, (byte) 1, (short) 0, tag));
                        resyncPacket.scheduleSendToServer(Protocol1_14To1_13_2.class);
                    }
                });
            }
        });
        registerCreativeInvAction(ServerboundPackets1_14.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
        registerSpawnParticle(ClientboundPackets1_13.SPAWN_PARTICLE, Type.FLAT_VAR_INT_ITEM, Type.FLOAT);
    }

    @Override // com.viaversion.viaversion.rewriter.ItemRewriter, com.viaversion.viaversion.api.rewriter.ItemRewriter
    public Item handleItemToClient(Item item) {
        if (item == null) {
            return null;
        }
        item.setIdentifier(Protocol1_14To1_13_2.MAPPINGS.getNewItemId(item.identifier()));
        if (item.tag() == null) {
            return item;
        }
        Tag displayTag = item.tag().get("display");
        if (displayTag instanceof CompoundTag) {
            CompoundTag display = (CompoundTag) displayTag;
            Tag loreTag = display.get("Lore");
            if (loreTag instanceof ListTag) {
                ListTag lore = (ListTag) loreTag;
                display.put(NBT_TAG_NAME + "|Lore", new ListTag(lore.clone().getValue()));
                Iterator<Tag> it = lore.iterator();
                while (it.hasNext()) {
                    Tag loreEntry = it.next();
                    if (loreEntry instanceof StringTag) {
                        String jsonText = ChatRewriter.legacyTextToJsonString(((StringTag) loreEntry).getValue(), true);
                        ((StringTag) loreEntry).setValue(jsonText);
                    }
                }
            }
        }
        return item;
    }

    @Override // com.viaversion.viaversion.rewriter.ItemRewriter, com.viaversion.viaversion.api.rewriter.ItemRewriter
    public Item handleItemToServer(Item item) {
        if (item == null) {
            return null;
        }
        item.setIdentifier(Protocol1_14To1_13_2.MAPPINGS.getOldItemId(item.identifier()));
        if (item.tag() == null) {
            return item;
        }
        Tag displayTag = item.tag().get("display");
        if (displayTag instanceof CompoundTag) {
            CompoundTag display = (CompoundTag) displayTag;
            Tag loreTag = display.get("Lore");
            if (loreTag instanceof ListTag) {
                ListTag lore = (ListTag) loreTag;
                ListTag savedLore = (ListTag) display.remove(NBT_TAG_NAME + "|Lore");
                if (savedLore != null) {
                    display.put("Lore", new ListTag(savedLore.getValue()));
                } else {
                    Iterator<Tag> it = lore.iterator();
                    while (it.hasNext()) {
                        Tag loreEntry = it.next();
                        if (loreEntry instanceof StringTag) {
                            ((StringTag) loreEntry).setValue(ChatRewriter.jsonToLegacyText(((StringTag) loreEntry).getValue()));
                        }
                    }
                }
            }
        }
        return item;
    }
}
