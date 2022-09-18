package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.packets;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.rewriters.EnchantmentRewriter;
import com.viaversion.viabackwards.api.rewriters.ItemRewriter;
import com.viaversion.viabackwards.api.rewriters.MapColorRewriter;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.Protocol1_15_2To1_16;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.data.MapColorRewrites;
import com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.storage.BiomeStorage;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.UUIDIntArrayType;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.data.RecipeRewriter1_14;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.types.Chunk1_15Type;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets.InventoryPackets;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.types.Chunk1_16Type;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.util.CompactArrayUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_15_2to1_16/packets/BlockItemPackets1_16.class */
public class BlockItemPackets1_16 extends ItemRewriter<Protocol1_15_2To1_16> {
    private EnchantmentRewriter enchantmentRewriter;

    public BlockItemPackets1_16(Protocol1_15_2To1_16 protocol) {
        super(protocol);
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    protected void registerPackets() {
        BlockRewriter blockRewriter = new BlockRewriter(this.protocol, Type.POSITION1_14);
        final RecipeRewriter1_14 recipeRewriter = new RecipeRewriter1_14(this.protocol);
        ((Protocol1_15_2To1_16) this.protocol).registerClientbound((Protocol1_15_2To1_16) ClientboundPackets1_16.DECLARE_RECIPES, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.packets.BlockItemPackets1_16.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                RecipeRewriter1_14 recipeRewriter1_14 = recipeRewriter;
                handler(wrapper -> {
                    int size = ((Integer) wrapper.passthrough(Type.VAR_INT)).intValue();
                    int newSize = size;
                    for (int i = 0; i < size; i++) {
                        String originalType = (String) wrapper.read(Type.STRING);
                        String type = originalType.replace("minecraft:", "");
                        if (type.equals("smithing")) {
                            newSize--;
                            wrapper.read(Type.STRING);
                            wrapper.read(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
                            wrapper.read(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
                            wrapper.read(Type.FLAT_VAR_INT_ITEM);
                        } else {
                            wrapper.write(Type.STRING, originalType);
                            String str = (String) wrapper.passthrough(Type.STRING);
                            recipeRewriter1_14.handle(wrapper, type);
                        }
                    }
                    wrapper.set(Type.VAR_INT, 0, Integer.valueOf(newSize));
                });
            }
        });
        registerSetCooldown(ClientboundPackets1_16.COOLDOWN);
        registerWindowItems(ClientboundPackets1_16.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY);
        registerSetSlot(ClientboundPackets1_16.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
        registerTradeList(ClientboundPackets1_16.TRADE_LIST, Type.FLAT_VAR_INT_ITEM);
        registerAdvancements(ClientboundPackets1_16.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
        blockRewriter.registerAcknowledgePlayerDigging(ClientboundPackets1_16.ACKNOWLEDGE_PLAYER_DIGGING);
        blockRewriter.registerBlockAction(ClientboundPackets1_16.BLOCK_ACTION);
        blockRewriter.registerBlockChange(ClientboundPackets1_16.BLOCK_CHANGE);
        blockRewriter.registerMultiBlockChange(ClientboundPackets1_16.MULTI_BLOCK_CHANGE);
        ((Protocol1_15_2To1_16) this.protocol).registerClientbound((Protocol1_15_2To1_16) ClientboundPackets1_16.ENTITY_EQUIPMENT, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.packets.BlockItemPackets1_16.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    byte slot;
                    int entityId = ((Integer) wrapper.passthrough(Type.VAR_INT)).intValue();
                    List<EquipmentData> equipmentData = new ArrayList<>();
                    do {
                        slot = ((Byte) wrapper.read(Type.BYTE)).byteValue();
                        Item item = BlockItemPackets1_16.this.handleItemToClient((Item) wrapper.read(Type.FLAT_VAR_INT_ITEM));
                        int rawSlot = slot & Byte.MAX_VALUE;
                        equipmentData.add(new EquipmentData(rawSlot, item));
                    } while ((slot & Byte.MIN_VALUE) != 0);
                    EquipmentData firstData = equipmentData.get(0);
                    wrapper.write(Type.VAR_INT, Integer.valueOf(firstData.slot));
                    wrapper.write(Type.FLAT_VAR_INT_ITEM, firstData.item);
                    for (int i = 1; i < equipmentData.size(); i++) {
                        PacketWrapper equipmentPacket = wrapper.create(ClientboundPackets1_15.ENTITY_EQUIPMENT);
                        EquipmentData data = equipmentData.get(i);
                        equipmentPacket.write(Type.VAR_INT, Integer.valueOf(entityId));
                        equipmentPacket.write(Type.VAR_INT, Integer.valueOf(data.slot));
                        equipmentPacket.write(Type.FLAT_VAR_INT_ITEM, data.item);
                        equipmentPacket.send(Protocol1_15_2To1_16.class);
                    }
                });
            }
        });
        ((Protocol1_15_2To1_16) this.protocol).registerClientbound((Protocol1_15_2To1_16) ClientboundPackets1_16.UPDATE_LIGHT, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.packets.BlockItemPackets1_16.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.VAR_INT);
                map(Type.BOOLEAN, Type.NOTHING);
            }
        });
        ((Protocol1_15_2To1_16) this.protocol).registerClientbound((Protocol1_15_2To1_16) ClientboundPackets1_16.CHUNK_DATA, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.packets.BlockItemPackets1_16.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    Chunk chunk = (Chunk) wrapper.read(new Chunk1_16Type());
                    wrapper.write(new Chunk1_15Type(), chunk);
                    for (int i = 0; i < chunk.getSections().length; i++) {
                        ChunkSection section = chunk.getSections()[i];
                        if (section != null) {
                            for (int j = 0; j < section.getPaletteSize(); j++) {
                                int old = section.getPaletteEntry(j);
                                section.setPaletteEntry(j, ((Protocol1_15_2To1_16) BlockItemPackets1_16.this.protocol).getMappingData().getNewBlockStateId(old));
                            }
                        }
                    }
                    CompoundTag heightMaps = chunk.getHeightMap();
                    for (Tag heightMapTag : heightMaps.values()) {
                        LongArrayTag heightMap = (LongArrayTag) heightMapTag;
                        int[] heightMapData = new int[256];
                        CompactArrayUtil.iterateCompactArrayWithPadding(9, heightMapData.length, heightMap.getValue(), i2, v -> {
                            heightMapData[i2] = v;
                        });
                        heightMap.setValue(CompactArrayUtil.createCompactArray(9, heightMapData.length, i3 -> {
                            return heightMapData[i3];
                        }));
                    }
                    if (chunk.isBiomeData()) {
                        if (wrapper.user().getProtocolInfo().getServerProtocolVersion() >= ProtocolVersion.v1_16_2.getVersion()) {
                            BiomeStorage biomeStorage = (BiomeStorage) wrapper.user().get(BiomeStorage.class);
                            for (int i4 = 0; i4 < 1024; i4++) {
                                int biome = chunk.getBiomeData()[i4];
                                int legacyBiome = biomeStorage.legacyBiome(biome);
                                if (legacyBiome == -1) {
                                    ViaBackwards.getPlatform().getLogger().warning("Biome sent that does not exist in the biome registry: " + biome);
                                    legacyBiome = 1;
                                }
                                chunk.getBiomeData()[i4] = legacyBiome;
                            }
                        } else {
                            for (int i5 = 0; i5 < 1024; i5++) {
                                switch (chunk.getBiomeData()[i5]) {
                                    case 170:
                                    case 171:
                                    case 172:
                                    case 173:
                                        chunk.getBiomeData()[i5] = 8;
                                        break;
                                }
                            }
                        }
                    }
                    if (chunk.getBlockEntities() == null) {
                        return;
                    }
                    for (CompoundTag blockEntity : chunk.getBlockEntities()) {
                        BlockItemPackets1_16.this.handleBlockEntity(blockEntity);
                    }
                });
            }
        });
        blockRewriter.registerEffect(ClientboundPackets1_16.EFFECT, 1010, 2001);
        registerSpawnParticle(ClientboundPackets1_16.SPAWN_PARTICLE, Type.FLAT_VAR_INT_ITEM, Type.DOUBLE);
        ((Protocol1_15_2To1_16) this.protocol).registerClientbound((Protocol1_15_2To1_16) ClientboundPackets1_16.WINDOW_PROPERTY, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.packets.BlockItemPackets1_16.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                map(Type.SHORT);
                map(Type.SHORT);
                handler(wrapper -> {
                    short property = ((Short) wrapper.get(Type.SHORT, 0)).shortValue();
                    if (property >= 4 && property <= 6) {
                        short enchantmentId = ((Short) wrapper.get(Type.SHORT, 1)).shortValue();
                        if (enchantmentId > 11) {
                            wrapper.set(Type.SHORT, 1, Short.valueOf((short) (enchantmentId - 1)));
                        } else if (enchantmentId == 11) {
                            wrapper.set(Type.SHORT, 1, (short) 9);
                        }
                    }
                });
            }
        });
        ((Protocol1_15_2To1_16) this.protocol).registerClientbound((Protocol1_15_2To1_16) ClientboundPackets1_16.MAP_DATA, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.packets.BlockItemPackets1_16.6
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.BYTE);
                map(Type.BOOLEAN);
                map(Type.BOOLEAN);
                handler(MapColorRewriter.getRewriteHandler(MapColorRewrites::getMappedColor));
            }
        });
        ((Protocol1_15_2To1_16) this.protocol).registerClientbound((Protocol1_15_2To1_16) ClientboundPackets1_16.BLOCK_ENTITY_DATA, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.packets.BlockItemPackets1_16.7
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    Position position = (Position) wrapper.passthrough(Type.POSITION1_14);
                    ((Short) wrapper.passthrough(Type.UNSIGNED_BYTE)).shortValue();
                    CompoundTag tag = (CompoundTag) wrapper.passthrough(Type.NBT);
                    BlockItemPackets1_16.this.handleBlockEntity(tag);
                });
            }
        });
        registerClickWindow(ServerboundPackets1_14.CLICK_WINDOW, Type.FLAT_VAR_INT_ITEM);
        registerCreativeInvAction(ServerboundPackets1_14.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_15_2To1_16) this.protocol).registerServerbound((Protocol1_15_2To1_16) ServerboundPackets1_14.EDIT_BOOK, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.packets.BlockItemPackets1_16.8
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    BlockItemPackets1_16.this.handleItemToServer((Item) wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
                });
            }
        });
    }

    public void handleBlockEntity(CompoundTag tag) {
        StringTag idTag = (StringTag) tag.get("id");
        if (idTag == null) {
            return;
        }
        String id = idTag.getValue();
        if (id.equals("minecraft:conduit")) {
            Tag targetUuidTag = tag.remove("Target");
            if (!(targetUuidTag instanceof IntArrayTag)) {
                return;
            }
            UUID targetUuid = UUIDIntArrayType.uuidFromIntArray((int[]) targetUuidTag.getValue());
            tag.put("target_uuid", new StringTag(targetUuid.toString()));
        } else if (id.equals("minecraft:skull")) {
            Tag skullOwnerTag = tag.remove("SkullOwner");
            if (!(skullOwnerTag instanceof CompoundTag)) {
                return;
            }
            CompoundTag skullOwnerCompoundTag = (CompoundTag) skullOwnerTag;
            Tag ownerUuidTag = skullOwnerCompoundTag.remove("Id");
            if (ownerUuidTag instanceof IntArrayTag) {
                UUID ownerUuid = UUIDIntArrayType.uuidFromIntArray((int[]) ownerUuidTag.getValue());
                skullOwnerCompoundTag.put("Id", new StringTag(ownerUuid.toString()));
            }
            CompoundTag ownerTag = new CompoundTag();
            Iterator<Map.Entry<String, Tag>> it = skullOwnerCompoundTag.iterator();
            while (it.hasNext()) {
                Map.Entry<String, Tag> entry = it.next();
                ownerTag.put(entry.getKey(), entry.getValue());
            }
            tag.put("Owner", ownerTag);
        }
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    protected void registerRewrites() {
        this.enchantmentRewriter = new EnchantmentRewriter(this);
        this.enchantmentRewriter.registerEnchantment("minecraft:soul_speed", "§7Soul Speed");
    }

    @Override // com.viaversion.viabackwards.api.rewriters.ItemRewriter, com.viaversion.viaversion.rewriter.ItemRewriter, com.viaversion.viaversion.api.rewriter.ItemRewriter
    public Item handleItemToClient(Item item) {
        if (item == null) {
            return null;
        }
        super.handleItemToClient(item);
        CompoundTag tag = item.tag();
        if (item.identifier() == 771 && tag != null) {
            Tag ownerTag = tag.get("SkullOwner");
            if (ownerTag instanceof CompoundTag) {
                CompoundTag ownerCompundTag = (CompoundTag) ownerTag;
                Tag idTag = ownerCompundTag.get("Id");
                if (idTag instanceof IntArrayTag) {
                    UUID ownerUuid = UUIDIntArrayType.uuidFromIntArray((int[]) idTag.getValue());
                    ownerCompundTag.put("Id", new StringTag(ownerUuid.toString()));
                }
            }
        }
        InventoryPackets.newToOldAttributes(item);
        this.enchantmentRewriter.handleToClient(item);
        return item;
    }

    @Override // com.viaversion.viabackwards.api.rewriters.ItemRewriter, com.viaversion.viabackwards.api.rewriters.ItemRewriterBase, com.viaversion.viaversion.rewriter.ItemRewriter, com.viaversion.viaversion.api.rewriter.ItemRewriter
    public Item handleItemToServer(Item item) {
        if (item == null) {
            return null;
        }
        int identifier = item.identifier();
        super.handleItemToServer(item);
        CompoundTag tag = item.tag();
        if (identifier == 771 && tag != null) {
            Tag ownerTag = tag.get("SkullOwner");
            if (ownerTag instanceof CompoundTag) {
                CompoundTag ownerCompundTag = (CompoundTag) ownerTag;
                Tag idTag = ownerCompundTag.get("Id");
                if (idTag instanceof StringTag) {
                    UUID ownerUuid = UUID.fromString((String) idTag.getValue());
                    ownerCompundTag.put("Id", new IntArrayTag(UUIDIntArrayType.uuidToIntArray(ownerUuid)));
                }
            }
        }
        InventoryPackets.oldToNewAttributes(item);
        this.enchantmentRewriter.handleToServer(item);
        return item;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_15_2to1_16/packets/BlockItemPackets1_16$EquipmentData.class */
    public static final class EquipmentData {
        private final int slot;
        private final Item item;

        private EquipmentData(int slot, Item item) {
            this.slot = slot;
            this.item = item;
        }
    }
}
