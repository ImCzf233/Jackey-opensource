package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets;

import com.google.common.base.Joiner;
import com.google.common.primitives.Ints;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.conversion.ConverterRegistry;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ShortTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ClientboundPackets1_12_1;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.BlockIdData;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.MappingData;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.SoundSource;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.SpawnEggRewriter;
import com.viaversion.viaversion.rewriter.ItemRewriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import kotlin.jvm.internal.CharCompanionObject;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_13to1_12_2/packets/InventoryPackets.class */
public class InventoryPackets extends ItemRewriter<Protocol1_13To1_12_2> {
    private static final String NBT_TAG_NAME = "ViaVersion|" + Protocol1_13To1_12_2.class.getSimpleName();

    public InventoryPackets(Protocol1_13To1_12_2 protocol) {
        super(protocol);
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    public void registerPackets() {
        ((Protocol1_13To1_12_2) this.protocol).registerClientbound((Protocol1_13To1_12_2) ClientboundPackets1_12_1.SET_SLOT, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.InventoryPackets.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                map(Type.SHORT);
                map(Type.ITEM, Type.FLAT_ITEM);
                handler(InventoryPackets.this.itemToClientHandler(Type.FLAT_ITEM));
            }
        });
        ((Protocol1_13To1_12_2) this.protocol).registerClientbound((Protocol1_13To1_12_2) ClientboundPackets1_12_1.WINDOW_ITEMS, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.InventoryPackets.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                map(Type.ITEM_ARRAY, Type.FLAT_ITEM_ARRAY);
                handler(InventoryPackets.this.itemArrayHandler(Type.FLAT_ITEM_ARRAY));
            }
        });
        ((Protocol1_13To1_12_2) this.protocol).registerClientbound((Protocol1_13To1_12_2) ClientboundPackets1_12_1.WINDOW_PROPERTY, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.InventoryPackets.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                map(Type.SHORT);
                map(Type.SHORT);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.InventoryPackets.3.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        short property = ((Short) wrapper.get(Type.SHORT, 0)).shortValue();
                        if (property >= 4 && property <= 6) {
                            wrapper.set(Type.SHORT, 1, Short.valueOf((short) ((Protocol1_13To1_12_2) InventoryPackets.this.protocol).getMappingData().getEnchantmentMappings().getNewId(((Short) wrapper.get(Type.SHORT, 1)).shortValue())));
                        }
                    }
                });
            }
        });
        ((Protocol1_13To1_12_2) this.protocol).registerClientbound((Protocol1_13To1_12_2) ClientboundPackets1_12_1.PLUGIN_MESSAGE, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.InventoryPackets.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.InventoryPackets.4.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        String channel;
                        String channel2 = (String) wrapper.get(Type.STRING, 0);
                        if (channel2.equalsIgnoreCase("MC|StopSound")) {
                            String originalSource = (String) wrapper.read(Type.STRING);
                            String originalSound = (String) wrapper.read(Type.STRING);
                            wrapper.clearPacket();
                            wrapper.setId(76);
                            byte flags = 0;
                            wrapper.write(Type.BYTE, (byte) 0);
                            if (!originalSource.isEmpty()) {
                                flags = (byte) (0 | 1);
                                Optional<SoundSource> finalSource = SoundSource.findBySource(originalSource);
                                if (!finalSource.isPresent()) {
                                    if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                                        Via.getPlatform().getLogger().info("Could not handle unknown sound source " + originalSource + " falling back to default: master");
                                    }
                                    finalSource = Optional.of(SoundSource.MASTER);
                                }
                                wrapper.write(Type.VAR_INT, Integer.valueOf(finalSource.get().getId()));
                            }
                            if (!originalSound.isEmpty()) {
                                flags = (byte) (flags | 2);
                                wrapper.write(Type.STRING, originalSound);
                            }
                            wrapper.set(Type.BYTE, 0, Byte.valueOf(flags));
                            return;
                        }
                        if (channel2.equalsIgnoreCase("MC|TrList")) {
                            channel = "minecraft:trader_list";
                            wrapper.passthrough(Type.INT);
                            int size = ((Short) wrapper.passthrough(Type.UNSIGNED_BYTE)).shortValue();
                            for (int i = 0; i < size; i++) {
                                Item input = (Item) wrapper.read(Type.ITEM);
                                InventoryPackets.this.handleItemToClient(input);
                                wrapper.write(Type.FLAT_ITEM, input);
                                Item output = (Item) wrapper.read(Type.ITEM);
                                InventoryPackets.this.handleItemToClient(output);
                                wrapper.write(Type.FLAT_ITEM, output);
                                boolean secondItem = ((Boolean) wrapper.passthrough(Type.BOOLEAN)).booleanValue();
                                if (secondItem) {
                                    Item second = (Item) wrapper.read(Type.ITEM);
                                    InventoryPackets.this.handleItemToClient(second);
                                    wrapper.write(Type.FLAT_ITEM, second);
                                }
                                wrapper.passthrough(Type.BOOLEAN);
                                wrapper.passthrough(Type.INT);
                                wrapper.passthrough(Type.INT);
                            }
                        } else {
                            channel = InventoryPackets.getNewPluginChannelId(channel2);
                            if (channel == null) {
                                if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                                    Via.getPlatform().getLogger().warning("Ignoring outgoing plugin message with channel: " + channel2);
                                }
                                wrapper.cancel();
                                return;
                            } else if (channel.equals("minecraft:register") || channel.equals("minecraft:unregister")) {
                                String[] channels = new String((byte[]) wrapper.read(Type.REMAINING_BYTES), StandardCharsets.UTF_8).split("��");
                                List<String> rewrittenChannels = new ArrayList<>();
                                for (int i2 = 0; i2 < channels.length; i2++) {
                                    String rewritten = InventoryPackets.getNewPluginChannelId(channels[i2]);
                                    if (rewritten != null) {
                                        rewrittenChannels.add(rewritten);
                                    } else if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                                        Via.getPlatform().getLogger().warning("Ignoring plugin channel in outgoing REGISTER: " + channels[i2]);
                                    }
                                }
                                if (!rewrittenChannels.isEmpty()) {
                                    wrapper.write(Type.REMAINING_BYTES, Joiner.on((char) 0).join(rewrittenChannels).getBytes(StandardCharsets.UTF_8));
                                } else {
                                    wrapper.cancel();
                                    return;
                                }
                            }
                        }
                        wrapper.set(Type.STRING, 0, channel);
                    }
                });
            }
        });
        ((Protocol1_13To1_12_2) this.protocol).registerClientbound((Protocol1_13To1_12_2) ClientboundPackets1_12_1.ENTITY_EQUIPMENT, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.InventoryPackets.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.VAR_INT);
                map(Type.ITEM, Type.FLAT_ITEM);
                handler(InventoryPackets.this.itemToClientHandler(Type.FLAT_ITEM));
            }
        });
        ((Protocol1_13To1_12_2) this.protocol).registerServerbound((Protocol1_13To1_12_2) ServerboundPackets1_13.CLICK_WINDOW, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.InventoryPackets.6
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                map(Type.SHORT);
                map(Type.BYTE);
                map(Type.SHORT);
                map(Type.VAR_INT);
                map(Type.FLAT_ITEM, Type.ITEM);
                handler(InventoryPackets.this.itemToServerHandler(Type.ITEM));
            }
        });
        ((Protocol1_13To1_12_2) this.protocol).registerServerbound((Protocol1_13To1_12_2) ServerboundPackets1_13.PLUGIN_MESSAGE, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.InventoryPackets.7
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.InventoryPackets.7.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        String channel = (String) wrapper.get(Type.STRING, 0);
                        String channel2 = InventoryPackets.getOldPluginChannelId(channel);
                        if (channel2 == null) {
                            if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                                Via.getPlatform().getLogger().warning("Ignoring incoming plugin message with channel: " + channel);
                            }
                            wrapper.cancel();
                            return;
                        }
                        if (channel2.equals("REGISTER") || channel2.equals("UNREGISTER")) {
                            String[] channels = new String((byte[]) wrapper.read(Type.REMAINING_BYTES), StandardCharsets.UTF_8).split("��");
                            List<String> rewrittenChannels = new ArrayList<>();
                            for (int i = 0; i < channels.length; i++) {
                                String rewritten = InventoryPackets.getOldPluginChannelId(channels[i]);
                                if (rewritten != null) {
                                    rewrittenChannels.add(rewritten);
                                } else if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                                    Via.getPlatform().getLogger().warning("Ignoring plugin channel in incoming REGISTER: " + channels[i]);
                                }
                            }
                            wrapper.write(Type.REMAINING_BYTES, Joiner.on((char) 0).join(rewrittenChannels).getBytes(StandardCharsets.UTF_8));
                        }
                        wrapper.set(Type.STRING, 0, channel2);
                    }
                });
            }
        });
        ((Protocol1_13To1_12_2) this.protocol).registerServerbound((Protocol1_13To1_12_2) ServerboundPackets1_13.CREATIVE_INVENTORY_ACTION, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.InventoryPackets.8
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.SHORT);
                map(Type.FLAT_ITEM, Type.ITEM);
                handler(InventoryPackets.this.itemToServerHandler(Type.ITEM));
            }
        });
    }

    @Override // com.viaversion.viaversion.rewriter.ItemRewriter, com.viaversion.viaversion.api.rewriter.ItemRewriter
    public Item handleItemToClient(Item item) {
        NumberTag idTag;
        if (item == null) {
            return null;
        }
        CompoundTag tag = item.tag();
        int originalId = (item.identifier() << 16) | (item.data() & 65535);
        int rawId = (item.identifier() << 4) | (item.data() & 15);
        if (isDamageable(item.identifier())) {
            if (tag == null) {
                CompoundTag compoundTag = new CompoundTag();
                tag = compoundTag;
                item.setTag(compoundTag);
            }
            tag.put("Damage", new IntTag(item.data()));
        }
        if (item.identifier() == 358) {
            if (tag == null) {
                CompoundTag compoundTag2 = new CompoundTag();
                tag = compoundTag2;
                item.setTag(compoundTag2);
            }
            tag.put("map", new IntTag(item.data()));
        }
        if (tag != null) {
            boolean banner = item.identifier() == 425;
            if ((banner || item.identifier() == 442) && (tag.get("BlockEntityTag") instanceof CompoundTag)) {
                CompoundTag blockEntityTag = (CompoundTag) tag.get("BlockEntityTag");
                if (blockEntityTag.get("Base") instanceof IntTag) {
                    IntTag base = (IntTag) blockEntityTag.get("Base");
                    if (banner) {
                        rawId = 6800 + base.asInt();
                    }
                    base.setValue(15 - base.asInt());
                }
                if (blockEntityTag.get("Patterns") instanceof ListTag) {
                    Iterator<Tag> it = ((ListTag) blockEntityTag.get("Patterns")).iterator();
                    while (it.hasNext()) {
                        Tag pattern = it.next();
                        if (pattern instanceof CompoundTag) {
                            IntTag c = (IntTag) ((CompoundTag) pattern).get("Color");
                            c.setValue(15 - c.asInt());
                        }
                    }
                }
            }
            if (tag.get("display") instanceof CompoundTag) {
                CompoundTag display = (CompoundTag) tag.get("display");
                if (display.get("Name") instanceof StringTag) {
                    StringTag name = (StringTag) display.get("Name");
                    display.put(NBT_TAG_NAME + "|Name", new StringTag(name.getValue()));
                    name.setValue(ChatRewriter.legacyTextToJsonString(name.getValue(), true));
                }
            }
            if (tag.get("ench") instanceof ListTag) {
                ListTag ench = (ListTag) tag.get("ench");
                ListTag enchantments = new ListTag(CompoundTag.class);
                Iterator<Tag> it2 = ench.iterator();
                while (it2.hasNext()) {
                    Tag enchEntry = it2.next();
                    if ((enchEntry instanceof CompoundTag) && (idTag = (NumberTag) ((CompoundTag) enchEntry).get("id")) != null) {
                        CompoundTag enchantmentEntry = new CompoundTag();
                        short oldId = idTag.asShort();
                        String newId = (String) Protocol1_13To1_12_2.MAPPINGS.getOldEnchantmentsIds().get(Short.valueOf(oldId));
                        if (newId == null) {
                            newId = "viaversion:legacy/" + ((int) oldId);
                        }
                        enchantmentEntry.put("id", new StringTag(newId));
                        enchantmentEntry.put("lvl", new ShortTag(((NumberTag) ((CompoundTag) enchEntry).get("lvl")).asShort()));
                        enchantments.add(enchantmentEntry);
                    }
                }
                tag.remove("ench");
                tag.put("Enchantments", enchantments);
            }
            if (tag.get("StoredEnchantments") instanceof ListTag) {
                ListTag storedEnch = (ListTag) tag.get("StoredEnchantments");
                ListTag newStoredEnch = new ListTag(CompoundTag.class);
                Iterator<Tag> it3 = storedEnch.iterator();
                while (it3.hasNext()) {
                    Tag enchEntry2 = it3.next();
                    if (enchEntry2 instanceof CompoundTag) {
                        CompoundTag enchantmentEntry2 = new CompoundTag();
                        short oldId2 = ((NumberTag) ((CompoundTag) enchEntry2).get("id")).asShort();
                        String newId2 = (String) Protocol1_13To1_12_2.MAPPINGS.getOldEnchantmentsIds().get(Short.valueOf(oldId2));
                        if (newId2 == null) {
                            newId2 = "viaversion:legacy/" + ((int) oldId2);
                        }
                        enchantmentEntry2.put("id", new StringTag(newId2));
                        enchantmentEntry2.put("lvl", new ShortTag(((NumberTag) ((CompoundTag) enchEntry2).get("lvl")).asShort()));
                        newStoredEnch.add(enchantmentEntry2);
                    }
                }
                tag.remove("StoredEnchantments");
                tag.put("StoredEnchantments", newStoredEnch);
            }
            if (tag.get("CanPlaceOn") instanceof ListTag) {
                ListTag old = (ListTag) tag.get("CanPlaceOn");
                ListTag newCanPlaceOn = new ListTag(StringTag.class);
                tag.put(NBT_TAG_NAME + "|CanPlaceOn", ConverterRegistry.convertToTag(ConverterRegistry.convertToValue(old)));
                Iterator<Tag> it4 = old.iterator();
                while (it4.hasNext()) {
                    Tag oldTag = it4.next();
                    Object value = oldTag.getValue();
                    String oldId3 = value.toString().replace("minecraft:", "");
                    String numberConverted = BlockIdData.numberIdToString.get(Ints.tryParse(oldId3));
                    if (numberConverted != null) {
                        oldId3 = numberConverted;
                    }
                    String[] newValues = BlockIdData.blockIdMapping.get(oldId3.toLowerCase(Locale.ROOT));
                    if (newValues != null) {
                        for (String newValue : newValues) {
                            newCanPlaceOn.add(new StringTag(newValue));
                        }
                    } else {
                        newCanPlaceOn.add(new StringTag(oldId3.toLowerCase(Locale.ROOT)));
                    }
                }
                tag.put("CanPlaceOn", newCanPlaceOn);
            }
            if (tag.get("CanDestroy") instanceof ListTag) {
                ListTag old2 = (ListTag) tag.get("CanDestroy");
                ListTag newCanDestroy = new ListTag(StringTag.class);
                tag.put(NBT_TAG_NAME + "|CanDestroy", ConverterRegistry.convertToTag(ConverterRegistry.convertToValue(old2)));
                Iterator<Tag> it5 = old2.iterator();
                while (it5.hasNext()) {
                    Tag oldTag2 = it5.next();
                    Object value2 = oldTag2.getValue();
                    String oldId4 = value2.toString().replace("minecraft:", "");
                    String numberConverted2 = BlockIdData.numberIdToString.get(Ints.tryParse(oldId4));
                    if (numberConverted2 != null) {
                        oldId4 = numberConverted2;
                    }
                    String[] newValues2 = BlockIdData.blockIdMapping.get(oldId4.toLowerCase(Locale.ROOT));
                    if (newValues2 != null) {
                        for (String newValue2 : newValues2) {
                            newCanDestroy.add(new StringTag(newValue2));
                        }
                    } else {
                        newCanDestroy.add(new StringTag(oldId4.toLowerCase(Locale.ROOT)));
                    }
                }
                tag.put("CanDestroy", newCanDestroy);
            }
            if (item.identifier() == 383) {
                if (tag.get("EntityTag") instanceof CompoundTag) {
                    CompoundTag entityTag = (CompoundTag) tag.get("EntityTag");
                    if (entityTag.get("id") instanceof StringTag) {
                        StringTag identifier = (StringTag) entityTag.get("id");
                        rawId = SpawnEggRewriter.getSpawnEggId(identifier.getValue());
                        if (rawId == -1) {
                            rawId = 25100288;
                        } else {
                            entityTag.remove("id");
                            if (entityTag.isEmpty()) {
                                tag.remove("EntityTag");
                            }
                        }
                    } else {
                        rawId = 25100288;
                    }
                } else {
                    rawId = 25100288;
                }
            }
            if (tag.isEmpty()) {
                tag = null;
                item.setTag(null);
            }
        }
        if (!Protocol1_13To1_12_2.MAPPINGS.getItemMappings().containsKey(rawId)) {
            if (!isDamageable(item.identifier()) && item.identifier() != 358) {
                if (tag == null) {
                    CompoundTag compoundTag3 = new CompoundTag();
                    tag = compoundTag3;
                    item.setTag(compoundTag3);
                }
                tag.put(NBT_TAG_NAME, new IntTag(originalId));
            }
            if (item.identifier() == 31 && item.data() == 0) {
                rawId = 512;
            } else if (Protocol1_13To1_12_2.MAPPINGS.getItemMappings().containsKey(rawId & (-16))) {
                rawId &= -16;
            } else {
                if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                    Via.getPlatform().getLogger().warning("Failed to get 1.13 item for " + item.identifier());
                }
                rawId = 16;
            }
        }
        item.setIdentifier(Protocol1_13To1_12_2.MAPPINGS.getItemMappings().get(rawId));
        item.setData((short) 0);
        return item;
    }

    public static String getNewPluginChannelId(String old) {
        boolean z = true;
        switch (old.hashCode()) {
            case -295921722:
                if (old.equals("MC|BOpen")) {
                    z = true;
                    break;
                }
                break;
            case -294893183:
                if (old.equals("MC|Brand")) {
                    z = true;
                    break;
                }
                break;
            case -234943831:
                if (old.equals("bungeecord:main")) {
                    z = true;
                    break;
                }
                break;
            case -37059198:
                if (old.equals("MC|TrList")) {
                    z = false;
                    break;
                }
                break;
            case 92413603:
                if (old.equals("REGISTER")) {
                    z = true;
                    break;
                }
                break;
            case 125533714:
                if (old.equals("MC|DebugPath")) {
                    z = true;
                    break;
                }
                break;
            case 1321107516:
                if (old.equals("UNREGISTER")) {
                    z = true;
                    break;
                }
                break;
            case 1537336522:
                if (old.equals("BungeeCord")) {
                    z = true;
                    break;
                }
                break;
            case 2076087261:
                if (old.equals("MC|DebugNeighborsUpdate")) {
                    z = true;
                    break;
                }
                break;
        }
        switch (z) {
            case false:
                return "minecraft:trader_list";
            case true:
                return "minecraft:brand";
            case true:
                return "minecraft:book_open";
            case true:
                return "minecraft:debug/paths";
            case true:
                return "minecraft:debug/neighbors_update";
            case true:
                return "minecraft:register";
            case true:
                return "minecraft:unregister";
            case true:
                return "bungeecord:main";
            case true:
                return null;
            default:
                String mappedChannel = (String) Protocol1_13To1_12_2.MAPPINGS.getChannelMappings().get(old);
                return mappedChannel != null ? mappedChannel : MappingData.validateNewChannel(old);
        }
    }

    @Override // com.viaversion.viaversion.rewriter.ItemRewriter, com.viaversion.viaversion.api.rewriter.ItemRewriter
    public Item handleItemToServer(Item item) {
        int oldId;
        if (item == null) {
            return null;
        }
        Integer rawId = null;
        boolean gotRawIdFromTag = false;
        CompoundTag tag = item.tag();
        if (tag != null && (tag.get(NBT_TAG_NAME) instanceof IntTag)) {
            rawId = Integer.valueOf(((NumberTag) tag.get(NBT_TAG_NAME)).asInt());
            tag.remove(NBT_TAG_NAME);
            gotRawIdFromTag = true;
        }
        if (rawId == null && (oldId = Protocol1_13To1_12_2.MAPPINGS.getItemMappings().inverse().get(item.identifier())) != -1) {
            Optional<String> eggEntityId = SpawnEggRewriter.getEntityId(oldId);
            if (eggEntityId.isPresent()) {
                rawId = 25100288;
                if (tag == null) {
                    CompoundTag compoundTag = new CompoundTag();
                    tag = compoundTag;
                    item.setTag(compoundTag);
                }
                if (!tag.contains("EntityTag")) {
                    CompoundTag entityTag = new CompoundTag();
                    entityTag.put("id", new StringTag(eggEntityId.get()));
                    tag.put("EntityTag", entityTag);
                }
            } else {
                rawId = Integer.valueOf(((oldId >> 4) << 16) | (oldId & 15));
            }
        }
        if (rawId == null) {
            if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                Via.getPlatform().getLogger().warning("Failed to get 1.12 item for " + item.identifier());
            }
            rawId = 65536;
        }
        item.setIdentifier((short) (rawId.intValue() >> 16));
        item.setData((short) (rawId.intValue() & CharCompanionObject.MAX_VALUE));
        if (tag != null) {
            if (isDamageable(item.identifier()) && (tag.get("Damage") instanceof IntTag)) {
                if (!gotRawIdFromTag) {
                    item.setData((short) ((Integer) tag.get("Damage").getValue()).intValue());
                }
                tag.remove("Damage");
            }
            if (item.identifier() == 358 && (tag.get("map") instanceof IntTag)) {
                if (!gotRawIdFromTag) {
                    item.setData((short) ((Integer) tag.get("map").getValue()).intValue());
                }
                tag.remove("map");
            }
            if ((item.identifier() == 442 || item.identifier() == 425) && (tag.get("BlockEntityTag") instanceof CompoundTag)) {
                CompoundTag blockEntityTag = (CompoundTag) tag.get("BlockEntityTag");
                if (blockEntityTag.get("Base") instanceof IntTag) {
                    IntTag base = (IntTag) blockEntityTag.get("Base");
                    base.setValue(15 - base.asInt());
                }
                if (blockEntityTag.get("Patterns") instanceof ListTag) {
                    Iterator<Tag> it = ((ListTag) blockEntityTag.get("Patterns")).iterator();
                    while (it.hasNext()) {
                        Tag pattern = it.next();
                        if (pattern instanceof CompoundTag) {
                            IntTag c = (IntTag) ((CompoundTag) pattern).get("Color");
                            c.setValue(15 - c.asInt());
                        }
                    }
                }
            }
            if (tag.get("display") instanceof CompoundTag) {
                CompoundTag display = (CompoundTag) tag.get("display");
                if (display.get("Name") instanceof StringTag) {
                    StringTag name = (StringTag) display.get("Name");
                    StringTag via = (StringTag) display.remove(NBT_TAG_NAME + "|Name");
                    name.setValue(via != null ? via.getValue() : ChatRewriter.jsonToLegacyText(name.getValue()));
                }
            }
            if (tag.get("Enchantments") instanceof ListTag) {
                ListTag enchantments = (ListTag) tag.get("Enchantments");
                ListTag ench = new ListTag(CompoundTag.class);
                Iterator<Tag> it2 = enchantments.iterator();
                while (it2.hasNext()) {
                    Tag enchantmentEntry = it2.next();
                    if (enchantmentEntry instanceof CompoundTag) {
                        CompoundTag enchEntry = new CompoundTag();
                        String newId = (String) ((CompoundTag) enchantmentEntry).get("id").getValue();
                        Short oldId2 = (Short) Protocol1_13To1_12_2.MAPPINGS.getOldEnchantmentsIds().inverse().get(newId);
                        if (oldId2 == null && newId.startsWith("viaversion:legacy/")) {
                            oldId2 = Short.valueOf(newId.substring(18));
                        }
                        if (oldId2 != null) {
                            enchEntry.put("id", new ShortTag(oldId2.shortValue()));
                            enchEntry.put("lvl", new ShortTag(((NumberTag) ((CompoundTag) enchantmentEntry).get("lvl")).asShort()));
                            ench.add(enchEntry);
                        }
                    }
                }
                tag.remove("Enchantments");
                tag.put("ench", ench);
            }
            if (tag.get("StoredEnchantments") instanceof ListTag) {
                ListTag storedEnch = (ListTag) tag.get("StoredEnchantments");
                ListTag newStoredEnch = new ListTag(CompoundTag.class);
                Iterator<Tag> it3 = storedEnch.iterator();
                while (it3.hasNext()) {
                    Tag enchantmentEntry2 = it3.next();
                    if (enchantmentEntry2 instanceof CompoundTag) {
                        CompoundTag enchEntry2 = new CompoundTag();
                        String newId2 = (String) ((CompoundTag) enchantmentEntry2).get("id").getValue();
                        Short oldId3 = (Short) Protocol1_13To1_12_2.MAPPINGS.getOldEnchantmentsIds().inverse().get(newId2);
                        if (oldId3 == null && newId2.startsWith("viaversion:legacy/")) {
                            oldId3 = Short.valueOf(newId2.substring(18));
                        }
                        if (oldId3 != null) {
                            enchEntry2.put("id", new ShortTag(oldId3.shortValue()));
                            enchEntry2.put("lvl", new ShortTag(((NumberTag) ((CompoundTag) enchantmentEntry2).get("lvl")).asShort()));
                            newStoredEnch.add(enchEntry2);
                        }
                    }
                }
                tag.remove("StoredEnchantments");
                tag.put("StoredEnchantments", newStoredEnch);
            }
            if (tag.get(NBT_TAG_NAME + "|CanPlaceOn") instanceof ListTag) {
                tag.put("CanPlaceOn", ConverterRegistry.convertToTag(ConverterRegistry.convertToValue(tag.get(NBT_TAG_NAME + "|CanPlaceOn"))));
                tag.remove(NBT_TAG_NAME + "|CanPlaceOn");
            } else if (tag.get("CanPlaceOn") instanceof ListTag) {
                ListTag old = (ListTag) tag.get("CanPlaceOn");
                ListTag newCanPlaceOn = new ListTag(StringTag.class);
                Iterator<Tag> it4 = old.iterator();
                while (it4.hasNext()) {
                    Tag oldTag = it4.next();
                    Object value = oldTag.getValue();
                    String[] newValues = BlockIdData.fallbackReverseMapping.get(value instanceof String ? ((String) value).replace("minecraft:", "") : null);
                    if (newValues != null) {
                        for (String newValue : newValues) {
                            newCanPlaceOn.add(new StringTag(newValue));
                        }
                    } else {
                        newCanPlaceOn.add(oldTag);
                    }
                }
                tag.put("CanPlaceOn", newCanPlaceOn);
            }
            if (tag.get(NBT_TAG_NAME + "|CanDestroy") instanceof ListTag) {
                tag.put("CanDestroy", ConverterRegistry.convertToTag(ConverterRegistry.convertToValue(tag.get(NBT_TAG_NAME + "|CanDestroy"))));
                tag.remove(NBT_TAG_NAME + "|CanDestroy");
            } else if (tag.get("CanDestroy") instanceof ListTag) {
                ListTag old2 = (ListTag) tag.get("CanDestroy");
                ListTag newCanDestroy = new ListTag(StringTag.class);
                Iterator<Tag> it5 = old2.iterator();
                while (it5.hasNext()) {
                    Tag oldTag2 = it5.next();
                    Object value2 = oldTag2.getValue();
                    String[] newValues2 = BlockIdData.fallbackReverseMapping.get(value2 instanceof String ? ((String) value2).replace("minecraft:", "") : null);
                    if (newValues2 != null) {
                        for (String newValue2 : newValues2) {
                            newCanDestroy.add(new StringTag(newValue2));
                        }
                    } else {
                        newCanDestroy.add(oldTag2);
                    }
                }
                tag.put("CanDestroy", newCanDestroy);
            }
        }
        return item;
    }

    public static String getOldPluginChannelId(String newId) {
        String newId2 = MappingData.validateNewChannel(newId);
        if (newId2 == null) {
            return null;
        }
        boolean z = true;
        switch (newId2.hashCode()) {
            case -1963049943:
                if (newId2.equals("minecraft:unregister")) {
                    z = true;
                    break;
                }
                break;
            case -1149721734:
                if (newId2.equals("minecraft:brand")) {
                    z = true;
                    break;
                }
                break;
            case -420924333:
                if (newId2.equals("minecraft:book_open")) {
                    z = true;
                    break;
                }
                break;
            case -234943831:
                if (newId2.equals("bungeecord:main")) {
                    z = true;
                    break;
                }
                break;
            case 339275216:
                if (newId2.equals("minecraft:register")) {
                    z = true;
                    break;
                }
                break;
            case 832866277:
                if (newId2.equals("minecraft:debug/paths")) {
                    z = true;
                    break;
                }
                break;
            case 1745645488:
                if (newId2.equals("minecraft:debug/neighbors_update")) {
                    z = true;
                    break;
                }
                break;
            case 1963953250:
                if (newId2.equals("minecraft:trader_list")) {
                    z = false;
                    break;
                }
                break;
        }
        switch (z) {
            case false:
                return "MC|TrList";
            case true:
                return "MC|BOpen";
            case true:
                return "MC|DebugPath";
            case true:
                return "MC|DebugNeighborsUpdate";
            case true:
                return "REGISTER";
            case true:
                return "UNREGISTER";
            case true:
                return "MC|Brand";
            case true:
                return "BungeeCord";
            default:
                String mappedChannel = (String) Protocol1_13To1_12_2.MAPPINGS.getChannelMappings().inverse().get(newId2);
                return mappedChannel != null ? mappedChannel : newId2.length() > 20 ? newId2.substring(0, 20) : newId2;
        }
    }

    public static boolean isDamageable(int id) {
        return (id >= 256 && id <= 259) || id == 261 || (id >= 267 && id <= 279) || ((id >= 283 && id <= 286) || ((id >= 290 && id <= 294) || ((id >= 298 && id <= 317) || id == 346 || id == 359 || id == 398 || id == 442 || id == 443)));
    }
}
