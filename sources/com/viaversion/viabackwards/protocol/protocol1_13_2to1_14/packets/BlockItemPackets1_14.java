package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets;

import com.google.common.collect.ImmutableSet;
import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.rewriters.EnchantmentRewriter;
import com.viaversion.viabackwards.api.rewriters.ItemRewriter;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.Protocol1_13_2To1_14;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.storage.ChunkLightStorage;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.Environment;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionLight;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionLightImpl;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_14Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_13;
import com.viaversion.viaversion.api.type.types.version.Types1_13_2;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.RecipeRewriter1_13_2;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.types.Chunk1_13Type;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.Protocol1_14To1_13_2;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.types.Chunk1_14Type;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.rewriter.RecipeRewriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_13_2to1_14/packets/BlockItemPackets1_14.class */
public class BlockItemPackets1_14 extends ItemRewriter<Protocol1_13_2To1_14> {
    private EnchantmentRewriter enchantmentRewriter;

    public BlockItemPackets1_14(Protocol1_13_2To1_14 protocol) {
        super(protocol);
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    protected void registerPackets() {
        ((Protocol1_13_2To1_14) this.protocol).registerServerbound((Protocol1_13_2To1_14) ServerboundPackets1_13.EDIT_BOOK, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.BlockItemPackets1_14.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    BlockItemPackets1_14.this.handleItemToServer((Item) wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
                });
            }
        });
        ((Protocol1_13_2To1_14) this.protocol).registerClientbound((Protocol1_13_2To1_14) ClientboundPackets1_14.OPEN_WINDOW, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.BlockItemPackets1_14.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.BlockItemPackets1_14.2.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int windowId = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                        wrapper.write(Type.UNSIGNED_BYTE, Short.valueOf((short) windowId));
                        int type = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                        boolean z = false;
                        String containerTitle = null;
                        int slotSize = 0;
                        if (type < 6) {
                            if (type == 2) {
                                containerTitle = "Barrel";
                            }
                            z = "minecraft:container";
                            slotSize = (type + 1) * 9;
                        } else {
                            switch (type) {
                                case 6:
                                    z = "minecraft:dropper";
                                    slotSize = 9;
                                    break;
                                case 7:
                                case 21:
                                    if (type == 21) {
                                        containerTitle = "Cartography Table";
                                    }
                                    z = "minecraft:anvil";
                                    break;
                                case 8:
                                    z = "minecraft:beacon";
                                    slotSize = 1;
                                    break;
                                case 9:
                                case 13:
                                case 14:
                                case 20:
                                    if (type == 9) {
                                        containerTitle = "Blast Furnace";
                                    } else if (type == 20) {
                                        containerTitle = "Smoker";
                                    } else if (type == 14) {
                                        containerTitle = "Grindstone";
                                    }
                                    z = "minecraft:furnace";
                                    slotSize = 3;
                                    break;
                                case 10:
                                    z = "minecraft:brewing_stand";
                                    slotSize = 5;
                                    break;
                                case 11:
                                    z = "minecraft:crafting_table";
                                    break;
                                case 12:
                                    z = "minecraft:enchanting_table";
                                    break;
                                case 15:
                                    z = "minecraft:hopper";
                                    slotSize = 5;
                                    break;
                                case 18:
                                    z = "minecraft:villager";
                                    break;
                                case 19:
                                    z = "minecraft:shulker_box";
                                    slotSize = 27;
                                    break;
                            }
                        }
                        if (!z) {
                            ViaBackwards.getPlatform().getLogger().warning("Can't open inventory for 1.13 player! Type: " + type);
                            wrapper.cancel();
                            return;
                        }
                        wrapper.write(Type.STRING, z);
                        JsonElement title = (JsonElement) wrapper.read(Type.COMPONENT);
                        if (containerTitle != null && title.isJsonObject()) {
                            JsonObject object = title.getAsJsonObject();
                            if (object.has("translate") && (type != 2 || object.getAsJsonPrimitive("translate").getAsString().equals("container.barrel"))) {
                                title = ChatRewriter.legacyTextToJson(containerTitle);
                            }
                        }
                        wrapper.write(Type.COMPONENT, title);
                        wrapper.write(Type.UNSIGNED_BYTE, Short.valueOf((short) slotSize));
                    }
                });
            }
        });
        ((Protocol1_13_2To1_14) this.protocol).registerClientbound((Protocol1_13_2To1_14) ClientboundPackets1_14.OPEN_HORSE_WINDOW, (ClientboundPackets1_14) ClientboundPackets1_13.OPEN_WINDOW, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.BlockItemPackets1_14.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.BlockItemPackets1_14.3.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        wrapper.passthrough(Type.UNSIGNED_BYTE);
                        wrapper.write(Type.STRING, "EntityHorse");
                        JsonObject object = new JsonObject();
                        object.addProperty("translate", "minecraft.horse");
                        wrapper.write(Type.COMPONENT, object);
                        wrapper.write(Type.UNSIGNED_BYTE, Short.valueOf(((Integer) wrapper.read(Type.VAR_INT)).shortValue()));
                        wrapper.passthrough(Type.INT);
                    }
                });
            }
        });
        BlockRewriter blockRewriter = new BlockRewriter(this.protocol, Type.POSITION);
        registerSetCooldown(ClientboundPackets1_14.COOLDOWN);
        registerWindowItems(ClientboundPackets1_14.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY);
        registerSetSlot(ClientboundPackets1_14.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
        registerAdvancements(ClientboundPackets1_14.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_13_2To1_14) this.protocol).registerClientbound((Protocol1_13_2To1_14) ClientboundPackets1_14.TRADE_LIST, (ClientboundPackets1_14) ClientboundPackets1_13.PLUGIN_MESSAGE, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.BlockItemPackets1_14.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.BlockItemPackets1_14.4.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        wrapper.write(Type.STRING, "minecraft:trader_list");
                        int windowId = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                        wrapper.write(Type.INT, Integer.valueOf(windowId));
                        int size = ((Short) wrapper.passthrough(Type.UNSIGNED_BYTE)).shortValue();
                        for (int i = 0; i < size; i++) {
                            Item input = (Item) wrapper.read(Type.FLAT_VAR_INT_ITEM);
                            wrapper.write(Type.FLAT_VAR_INT_ITEM, BlockItemPackets1_14.this.handleItemToClient(input));
                            Item output = (Item) wrapper.read(Type.FLAT_VAR_INT_ITEM);
                            wrapper.write(Type.FLAT_VAR_INT_ITEM, BlockItemPackets1_14.this.handleItemToClient(output));
                            boolean secondItem = ((Boolean) wrapper.passthrough(Type.BOOLEAN)).booleanValue();
                            if (secondItem) {
                                Item second = (Item) wrapper.read(Type.FLAT_VAR_INT_ITEM);
                                wrapper.write(Type.FLAT_VAR_INT_ITEM, BlockItemPackets1_14.this.handleItemToClient(second));
                            }
                            wrapper.passthrough(Type.BOOLEAN);
                            wrapper.passthrough(Type.INT);
                            wrapper.passthrough(Type.INT);
                            wrapper.read(Type.INT);
                            wrapper.read(Type.INT);
                            wrapper.read(Type.FLOAT);
                        }
                        wrapper.read(Type.VAR_INT);
                        wrapper.read(Type.VAR_INT);
                        wrapper.read(Type.BOOLEAN);
                    }
                });
            }
        });
        ((Protocol1_13_2To1_14) this.protocol).registerClientbound((Protocol1_13_2To1_14) ClientboundPackets1_14.OPEN_BOOK, (ClientboundPackets1_14) ClientboundPackets1_13.PLUGIN_MESSAGE, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.BlockItemPackets1_14.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.BlockItemPackets1_14.5.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        wrapper.write(Type.STRING, "minecraft:book_open");
                        wrapper.passthrough(Type.VAR_INT);
                    }
                });
            }
        });
        ((Protocol1_13_2To1_14) this.protocol).registerClientbound((Protocol1_13_2To1_14) ClientboundPackets1_14.ENTITY_EQUIPMENT, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.BlockItemPackets1_14.6
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.VAR_INT);
                map(Type.FLAT_VAR_INT_ITEM);
                handler(BlockItemPackets1_14.this.itemToClientHandler(Type.FLAT_VAR_INT_ITEM));
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.BlockItemPackets1_14.6.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int entityId = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                        EntityType entityType = wrapper.user().getEntityTracker(Protocol1_13_2To1_14.class).entityType(entityId);
                        if (entityType != null && entityType.isOrHasParent(Entity1_14Types.ABSTRACT_HORSE)) {
                            wrapper.setId(63);
                            wrapper.resetReader();
                            wrapper.passthrough(Type.VAR_INT);
                            wrapper.read(Type.VAR_INT);
                            Item item = (Item) wrapper.read(Type.FLAT_VAR_INT_ITEM);
                            int armorType = (item == null || item.identifier() == 0) ? 0 : item.identifier() - 726;
                            if (armorType < 0 || armorType > 3) {
                                ViaBackwards.getPlatform().getLogger().warning("Received invalid horse armor: " + item);
                                wrapper.cancel();
                                return;
                            }
                            ArrayList arrayList = new ArrayList();
                            arrayList.add(new Metadata(16, Types1_13_2.META_TYPES.varIntType, Integer.valueOf(armorType)));
                            wrapper.write(Types1_13.METADATA_LIST, arrayList);
                        }
                    }
                });
            }
        });
        final RecipeRewriter recipeHandler = new RecipeRewriter1_13_2(this.protocol);
        ((Protocol1_13_2To1_14) this.protocol).registerClientbound((Protocol1_13_2To1_14) ClientboundPackets1_14.DECLARE_RECIPES, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.BlockItemPackets1_14.7
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.BlockItemPackets1_14.7.1
                    private final Set<String> removedTypes = ImmutableSet.of("crafting_special_suspiciousstew", "blasting", "smoking", "campfire_cooking", "stonecutting");

                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int size = ((Integer) wrapper.passthrough(Type.VAR_INT)).intValue();
                        int deleted = 0;
                        for (int i = 0; i < size; i++) {
                            String type = (String) wrapper.read(Type.STRING);
                            String id = (String) wrapper.read(Type.STRING);
                            String type2 = type.replace("minecraft:", "");
                            if (this.removedTypes.contains(type2)) {
                                boolean z = true;
                                switch (type2.hashCode()) {
                                    case -2084878740:
                                        if (type2.equals("smoking")) {
                                            z = true;
                                            break;
                                        }
                                        break;
                                    case -1050336534:
                                        if (type2.equals("blasting")) {
                                            z = false;
                                            break;
                                        }
                                        break;
                                    case -858939349:
                                        if (type2.equals("stonecutting")) {
                                            z = true;
                                            break;
                                        }
                                        break;
                                    case -68678766:
                                        if (type2.equals("campfire_cooking")) {
                                            z = true;
                                            break;
                                        }
                                        break;
                                }
                                switch (z) {
                                    case false:
                                    case true:
                                    case true:
                                        wrapper.read(Type.STRING);
                                        wrapper.read(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
                                        wrapper.read(Type.FLAT_VAR_INT_ITEM);
                                        wrapper.read(Type.FLOAT);
                                        wrapper.read(Type.VAR_INT);
                                        break;
                                    case true:
                                        wrapper.read(Type.STRING);
                                        wrapper.read(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
                                        wrapper.read(Type.FLAT_VAR_INT_ITEM);
                                        break;
                                }
                                deleted++;
                            } else {
                                wrapper.write(Type.STRING, id);
                                wrapper.write(Type.STRING, type2);
                                recipeHandler.handle(wrapper, type2);
                            }
                        }
                        wrapper.set(Type.VAR_INT, 0, Integer.valueOf(size - deleted));
                    }
                });
            }
        });
        registerClickWindow(ServerboundPackets1_13.CLICK_WINDOW, Type.FLAT_VAR_INT_ITEM);
        registerCreativeInvAction(ServerboundPackets1_13.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_13_2To1_14) this.protocol).registerClientbound((Protocol1_13_2To1_14) ClientboundPackets1_14.BLOCK_BREAK_ANIMATION, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.BlockItemPackets1_14.8
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.POSITION1_14, Type.POSITION);
                map(Type.BYTE);
            }
        });
        ((Protocol1_13_2To1_14) this.protocol).registerClientbound((Protocol1_13_2To1_14) ClientboundPackets1_14.BLOCK_ENTITY_DATA, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.BlockItemPackets1_14.9
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.POSITION1_14, Type.POSITION);
            }
        });
        ((Protocol1_13_2To1_14) this.protocol).registerClientbound((Protocol1_13_2To1_14) ClientboundPackets1_14.BLOCK_ACTION, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.BlockItemPackets1_14.10
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.POSITION1_14, Type.POSITION);
                map(Type.UNSIGNED_BYTE);
                map(Type.UNSIGNED_BYTE);
                map(Type.VAR_INT);
                handler(wrapper -> {
                    int mappedId = ((Protocol1_13_2To1_14) BlockItemPackets1_14.this.protocol).getMappingData().getNewBlockId(((Integer) wrapper.get(Type.VAR_INT, 0)).intValue());
                    if (mappedId == -1) {
                        wrapper.cancel();
                    } else {
                        wrapper.set(Type.VAR_INT, 0, Integer.valueOf(mappedId));
                    }
                });
            }
        });
        ((Protocol1_13_2To1_14) this.protocol).registerClientbound((Protocol1_13_2To1_14) ClientboundPackets1_14.BLOCK_CHANGE, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.BlockItemPackets1_14.11
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.POSITION1_14, Type.POSITION);
                map(Type.VAR_INT);
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.BlockItemPackets1_14.11.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int id = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                        wrapper.set(Type.VAR_INT, 0, Integer.valueOf(((Protocol1_13_2To1_14) BlockItemPackets1_14.this.protocol).getMappingData().getNewBlockStateId(id)));
                    }
                });
            }
        });
        blockRewriter.registerMultiBlockChange(ClientboundPackets1_14.MULTI_BLOCK_CHANGE);
        ((Protocol1_13_2To1_14) this.protocol).registerClientbound((Protocol1_13_2To1_14) ClientboundPackets1_14.EXPLOSION, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.BlockItemPackets1_14.12
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.FLOAT);
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.BlockItemPackets1_14.12.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        for (int i = 0; i < 3; i++) {
                            float coord = ((Float) wrapper.get(Type.FLOAT, i)).floatValue();
                            if (coord < 0.0f) {
                                wrapper.set(Type.FLOAT, i, Float.valueOf((float) Math.floor(coord)));
                            }
                        }
                    }
                });
            }
        });
        ((Protocol1_13_2To1_14) this.protocol).registerClientbound((Protocol1_13_2To1_14) ClientboundPackets1_14.CHUNK_DATA, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.BlockItemPackets1_14.13
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.BlockItemPackets1_14.13.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        ClientWorld clientWorld = (ClientWorld) wrapper.user().get(ClientWorld.class);
                        Chunk chunk = (Chunk) wrapper.read(new Chunk1_14Type());
                        wrapper.write(new Chunk1_13Type(clientWorld), chunk);
                        ChunkLightStorage.ChunkLight chunkLight = ((ChunkLightStorage) wrapper.user().get(ChunkLightStorage.class)).getStoredLight(chunk.getX(), chunk.getZ());
                        for (int i = 0; i < chunk.getSections().length; i++) {
                            ChunkSection section = chunk.getSections()[i];
                            if (section != null) {
                                ChunkSectionLight sectionLight = new ChunkSectionLightImpl();
                                section.setLight(sectionLight);
                                if (chunkLight == null) {
                                    sectionLight.setBlockLight(ChunkLightStorage.FULL_LIGHT);
                                    if (clientWorld.getEnvironment() == Environment.NORMAL) {
                                        sectionLight.setSkyLight(ChunkLightStorage.FULL_LIGHT);
                                    }
                                } else {
                                    byte[] blockLight = chunkLight.getBlockLight()[i];
                                    sectionLight.setBlockLight(blockLight != null ? blockLight : ChunkLightStorage.FULL_LIGHT);
                                    if (clientWorld.getEnvironment() == Environment.NORMAL) {
                                        byte[] skyLight = chunkLight.getSkyLight()[i];
                                        sectionLight.setSkyLight(skyLight != null ? skyLight : ChunkLightStorage.FULL_LIGHT);
                                    }
                                }
                                if (Via.getConfig().isNonFullBlockLightFix() && section.getNonAirBlocksCount() != 0 && sectionLight.hasBlockLight()) {
                                    for (int x = 0; x < 16; x++) {
                                        for (int y = 0; y < 16; y++) {
                                            for (int z = 0; z < 16; z++) {
                                                int id = section.getFlatBlock(x, y, z);
                                                if (Protocol1_14To1_13_2.MAPPINGS.getNonFullBlocks().contains(id)) {
                                                    sectionLight.getBlockLightNibbleArray().set(x, y, z, 0);
                                                }
                                            }
                                        }
                                    }
                                }
                                for (int j = 0; j < section.getPaletteSize(); j++) {
                                    int old = section.getPaletteEntry(j);
                                    int newId = ((Protocol1_13_2To1_14) BlockItemPackets1_14.this.protocol).getMappingData().getNewBlockStateId(old);
                                    section.setPaletteEntry(j, newId);
                                }
                            }
                        }
                    }
                });
            }
        });
        ((Protocol1_13_2To1_14) this.protocol).registerClientbound((Protocol1_13_2To1_14) ClientboundPackets1_14.UNLOAD_CHUNK, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.BlockItemPackets1_14.14
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.BlockItemPackets1_14.14.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int x = ((Integer) wrapper.passthrough(Type.INT)).intValue();
                        int z = ((Integer) wrapper.passthrough(Type.INT)).intValue();
                        ((ChunkLightStorage) wrapper.user().get(ChunkLightStorage.class)).unloadChunk(x, z);
                    }
                });
            }
        });
        ((Protocol1_13_2To1_14) this.protocol).registerClientbound((Protocol1_13_2To1_14) ClientboundPackets1_14.EFFECT, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.BlockItemPackets1_14.15
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.POSITION1_14, Type.POSITION);
                map(Type.INT);
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.BlockItemPackets1_14.15.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int id = ((Integer) wrapper.get(Type.INT, 0)).intValue();
                        int data = ((Integer) wrapper.get(Type.INT, 1)).intValue();
                        if (id == 1010) {
                            wrapper.set(Type.INT, 1, Integer.valueOf(((Protocol1_13_2To1_14) BlockItemPackets1_14.this.protocol).getMappingData().getNewItemId(data)));
                        } else if (id == 2001) {
                            wrapper.set(Type.INT, 1, Integer.valueOf(((Protocol1_13_2To1_14) BlockItemPackets1_14.this.protocol).getMappingData().getNewBlockStateId(data)));
                        }
                    }
                });
            }
        });
        registerSpawnParticle(ClientboundPackets1_14.SPAWN_PARTICLE, Type.FLAT_VAR_INT_ITEM, Type.FLOAT);
        ((Protocol1_13_2To1_14) this.protocol).registerClientbound((Protocol1_13_2To1_14) ClientboundPackets1_14.MAP_DATA, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.BlockItemPackets1_14.16
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.BYTE);
                map(Type.BOOLEAN);
                map(Type.BOOLEAN, Type.NOTHING);
            }
        });
        ((Protocol1_13_2To1_14) this.protocol).registerClientbound((Protocol1_13_2To1_14) ClientboundPackets1_14.SPAWN_POSITION, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.BlockItemPackets1_14.17
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.POSITION1_14, Type.POSITION);
            }
        });
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    protected void registerRewrites() {
        this.enchantmentRewriter = new EnchantmentRewriter(this, false);
        this.enchantmentRewriter.registerEnchantment("minecraft:multishot", "§7Multishot");
        this.enchantmentRewriter.registerEnchantment("minecraft:quick_charge", "§7Quick Charge");
        this.enchantmentRewriter.registerEnchantment("minecraft:piercing", "§7Piercing");
    }

    @Override // com.viaversion.viabackwards.api.rewriters.ItemRewriter, com.viaversion.viaversion.rewriter.ItemRewriter, com.viaversion.viaversion.api.rewriter.ItemRewriter
    public Item handleItemToClient(Item item) {
        CompoundTag display;
        ListTag lore;
        StringTag loreEntryTag;
        String value;
        if (item == null) {
            return null;
        }
        super.handleItemToClient(item);
        CompoundTag tag = item.tag();
        if (tag != null && (display = (CompoundTag) tag.get("display")) != null && (lore = (ListTag) display.get("Lore")) != null) {
            saveListTag(display, lore, "Lore");
            Iterator<Tag> it = lore.iterator();
            while (it.hasNext()) {
                Tag loreEntry = it.next();
                if ((loreEntry instanceof StringTag) && (value = (loreEntryTag = (StringTag) loreEntry).getValue()) != null && !value.isEmpty()) {
                    loreEntryTag.setValue(ChatRewriter.jsonToLegacyText(value));
                }
            }
        }
        this.enchantmentRewriter.handleToClient(item);
        return item;
    }

    @Override // com.viaversion.viabackwards.api.rewriters.ItemRewriter, com.viaversion.viabackwards.api.rewriters.ItemRewriterBase, com.viaversion.viaversion.rewriter.ItemRewriter, com.viaversion.viaversion.api.rewriter.ItemRewriter
    public Item handleItemToServer(Item item) {
        CompoundTag display;
        ListTag lore;
        if (item == null) {
            return null;
        }
        CompoundTag tag = item.tag();
        if (tag != null && (display = (CompoundTag) tag.get("display")) != null && (lore = (ListTag) display.get("Lore")) != null && !hasBackupTag(display, "Lore")) {
            Iterator<Tag> it = lore.iterator();
            while (it.hasNext()) {
                Tag loreEntry = it.next();
                if (loreEntry instanceof StringTag) {
                    StringTag loreEntryTag = (StringTag) loreEntry;
                    loreEntryTag.setValue(ChatRewriter.legacyTextToJsonString(loreEntryTag.getValue()));
                }
            }
        }
        this.enchantmentRewriter.handleToServer(item);
        super.handleItemToServer(item);
        return item;
    }
}
