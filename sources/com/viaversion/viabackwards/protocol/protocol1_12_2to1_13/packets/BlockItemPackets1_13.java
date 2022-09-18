package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets;

import com.google.common.primitives.Ints;
import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.rewriters.EnchantmentRewriter;
import com.viaversion.viabackwards.api.rewriters.ItemRewriter;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers.FlowerPotHandler;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.providers.BackwardsBlockEntityProvider;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.storage.BackwardsBlockStorage;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.conversion.ConverterRegistry;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ShortTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ServerboundPackets1_12_1;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.BlockIdData;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.SpawnEggRewriter;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.types.Chunk1_13Type;
import com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.types.Chunk1_9_3_4Type;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import jdk.internal.dynalink.CallSiteDescriptor;
import kotlin.jvm.internal.CharCompanionObject;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_12_2to1_13/packets/BlockItemPackets1_13.class */
public class BlockItemPackets1_13 extends ItemRewriter<Protocol1_12_2To1_13> {
    private final Map<String, String> enchantmentMappings = new HashMap();
    private final String extraNbtTag;

    public BlockItemPackets1_13(Protocol1_12_2To1_13 protocol) {
        super(protocol);
        this.extraNbtTag = "VB|" + protocol.getClass().getSimpleName() + "|2";
    }

    public static boolean isDamageable(int id) {
        return (id >= 256 && id <= 259) || id == 261 || (id >= 267 && id <= 279) || ((id >= 283 && id <= 286) || ((id >= 290 && id <= 294) || ((id >= 298 && id <= 317) || id == 346 || id == 359 || id == 398 || id == 442 || id == 443)));
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    protected void registerPackets() {
        ((Protocol1_12_2To1_13) this.protocol).registerClientbound((Protocol1_12_2To1_13) ClientboundPackets1_13.COOLDOWN, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.BlockItemPackets1_13.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.BlockItemPackets1_13.1.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int itemId = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                        int oldId = ((Protocol1_12_2To1_13) BlockItemPackets1_13.this.protocol).getMappingData().getItemMappings().get(itemId);
                        if (oldId != -1) {
                            Optional<String> eggEntityId = SpawnEggRewriter.getEntityId(oldId);
                            if (eggEntityId.isPresent()) {
                                itemId = 25100288;
                            } else {
                                itemId = ((oldId >> 4) << 16) | (oldId & 15);
                            }
                        }
                        wrapper.write(Type.VAR_INT, Integer.valueOf(itemId));
                    }
                });
            }
        });
        ((Protocol1_12_2To1_13) this.protocol).registerClientbound((Protocol1_12_2To1_13) ClientboundPackets1_13.BLOCK_ACTION, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.BlockItemPackets1_13.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.POSITION);
                map(Type.UNSIGNED_BYTE);
                map(Type.UNSIGNED_BYTE);
                map(Type.VAR_INT);
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.BlockItemPackets1_13.2.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int blockId = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                        if (blockId == 73) {
                            blockId = 25;
                        } else if (blockId == 99) {
                            blockId = 33;
                        } else if (blockId == 92) {
                            blockId = 29;
                        } else if (blockId == 142) {
                            blockId = 54;
                        } else if (blockId == 305) {
                            blockId = 146;
                        } else if (blockId == 249) {
                            blockId = 130;
                        } else if (blockId == 257) {
                            blockId = 138;
                        } else if (blockId == 140) {
                            blockId = 52;
                        } else if (blockId == 472) {
                            blockId = 209;
                        } else if (blockId >= 483 && blockId <= 498) {
                            blockId = (blockId - 483) + 219;
                        }
                        wrapper.set(Type.VAR_INT, 0, Integer.valueOf(blockId));
                    }
                });
            }
        });
        ((Protocol1_12_2To1_13) this.protocol).registerClientbound((Protocol1_12_2To1_13) ClientboundPackets1_13.BLOCK_ENTITY_DATA, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.BlockItemPackets1_13.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.POSITION);
                map(Type.UNSIGNED_BYTE);
                map(Type.NBT);
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.BlockItemPackets1_13.3.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        BackwardsBlockEntityProvider provider = (BackwardsBlockEntityProvider) Via.getManager().getProviders().get(BackwardsBlockEntityProvider.class);
                        if (((Short) wrapper.get(Type.UNSIGNED_BYTE, 0)).shortValue() == 5) {
                            wrapper.cancel();
                        }
                        wrapper.set(Type.NBT, 0, provider.transform(wrapper.user(), (Position) wrapper.get(Type.POSITION, 0), (CompoundTag) wrapper.get(Type.NBT, 0)));
                    }
                });
            }
        });
        ((Protocol1_12_2To1_13) this.protocol).registerClientbound((Protocol1_12_2To1_13) ClientboundPackets1_13.UNLOAD_CHUNK, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.BlockItemPackets1_13.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.BlockItemPackets1_13.4.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int chunkMinX = ((Integer) wrapper.passthrough(Type.INT)).intValue() << 4;
                        int chunkMinZ = ((Integer) wrapper.passthrough(Type.INT)).intValue() << 4;
                        int chunkMaxX = chunkMinX + 15;
                        int chunkMaxZ = chunkMinZ + 15;
                        BackwardsBlockStorage blockStorage = (BackwardsBlockStorage) wrapper.user().get(BackwardsBlockStorage.class);
                        blockStorage.getBlocks().entrySet().removeIf(entry -> {
                            Position position = (Position) entry.getKey();
                            return position.getX() >= chunkMinX && position.getZ() >= chunkMinZ && position.getX() <= chunkMaxX && position.getZ() <= chunkMaxZ;
                        });
                    }
                });
            }
        });
        ((Protocol1_12_2To1_13) this.protocol).registerClientbound((Protocol1_12_2To1_13) ClientboundPackets1_13.BLOCK_CHANGE, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.BlockItemPackets1_13.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.POSITION);
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.BlockItemPackets1_13.5.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int blockState = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                        Position position = (Position) wrapper.get(Type.POSITION, 0);
                        BackwardsBlockStorage storage = (BackwardsBlockStorage) wrapper.user().get(BackwardsBlockStorage.class);
                        storage.checkAndStore(position, blockState);
                        wrapper.write(Type.VAR_INT, Integer.valueOf(((Protocol1_12_2To1_13) BlockItemPackets1_13.this.protocol).getMappingData().getNewBlockStateId(blockState)));
                        BlockItemPackets1_13.flowerPotSpecialTreatment(wrapper.user(), blockState, position);
                    }
                });
            }
        });
        ((Protocol1_12_2To1_13) this.protocol).registerClientbound((Protocol1_12_2To1_13) ClientboundPackets1_13.MULTI_BLOCK_CHANGE, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.BlockItemPackets1_13.6
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.INT);
                map(Type.BLOCK_CHANGE_RECORD_ARRAY);
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.BlockItemPackets1_13.6.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        BlockChangeRecord[] blockChangeRecordArr;
                        BackwardsBlockStorage storage = (BackwardsBlockStorage) wrapper.user().get(BackwardsBlockStorage.class);
                        for (BlockChangeRecord record : (BlockChangeRecord[]) wrapper.get(Type.BLOCK_CHANGE_RECORD_ARRAY, 0)) {
                            int chunkX = ((Integer) wrapper.get(Type.INT, 0)).intValue();
                            int chunkZ = ((Integer) wrapper.get(Type.INT, 1)).intValue();
                            int block = record.getBlockId();
                            Position position = new Position(record.getSectionX() + (chunkX * 16), record.getY(), record.getSectionZ() + (chunkZ * 16));
                            storage.checkAndStore(position, block);
                            BlockItemPackets1_13.flowerPotSpecialTreatment(wrapper.user(), block, position);
                            record.setBlockId(((Protocol1_12_2To1_13) BlockItemPackets1_13.this.protocol).getMappingData().getNewBlockStateId(block));
                        }
                    }
                });
            }
        });
        ((Protocol1_12_2To1_13) this.protocol).registerClientbound((Protocol1_12_2To1_13) ClientboundPackets1_13.WINDOW_ITEMS, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.BlockItemPackets1_13.7
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                map(Type.FLAT_ITEM_ARRAY, Type.ITEM_ARRAY);
                handler(BlockItemPackets1_13.this.itemArrayHandler(Type.ITEM_ARRAY));
            }
        });
        ((Protocol1_12_2To1_13) this.protocol).registerClientbound((Protocol1_12_2To1_13) ClientboundPackets1_13.SET_SLOT, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.BlockItemPackets1_13.8
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                map(Type.SHORT);
                map(Type.FLAT_ITEM, Type.ITEM);
                handler(BlockItemPackets1_13.this.itemToClientHandler(Type.ITEM));
            }
        });
        ((Protocol1_12_2To1_13) this.protocol).registerClientbound((Protocol1_12_2To1_13) ClientboundPackets1_13.CHUNK_DATA, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.BlockItemPackets1_13.9
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    int sectionIndex;
                    ClientWorld clientWorld = (ClientWorld) wrapper.user().get(ClientWorld.class);
                    Chunk1_9_3_4Type type_old = new Chunk1_9_3_4Type(clientWorld);
                    Chunk1_13Type type = new Chunk1_13Type(clientWorld);
                    Chunk chunk = (Chunk) wrapper.read(type);
                    BackwardsBlockEntityProvider provider = (BackwardsBlockEntityProvider) Via.getManager().getProviders().get(BackwardsBlockEntityProvider.class);
                    BackwardsBlockStorage storage = (BackwardsBlockStorage) wrapper.user().get(BackwardsBlockStorage.class);
                    for (CompoundTag tag : chunk.getBlockEntities()) {
                        Tag idTag = tag.get("id");
                        if (idTag != null) {
                            String id = (String) idTag.getValue();
                            if (provider.isHandled(id) && (sectionIndex = ((NumberTag) tag.get("y")).asInt() >> 4) >= 0 && sectionIndex <= 15) {
                                ChunkSection section = chunk.getSections()[sectionIndex];
                                int x = ((NumberTag) tag.get("x")).asInt();
                                int y = ((NumberTag) tag.get("y")).asInt();
                                int z = ((NumberTag) tag.get("z")).asInt();
                                Position position = new Position(x, (short) y, z);
                                storage.checkAndStore(position, section.getFlatBlock(x & 15, y & 15, z & 15));
                                provider.transform(wrapper.user(), position, tag);
                            }
                        }
                    }
                    for (int i = 0; i < chunk.getSections().length; i++) {
                        ChunkSection section2 = chunk.getSections()[i];
                        if (section2 != null) {
                            for (int y2 = 0; y2 < 16; y2++) {
                                for (int z2 = 0; z2 < 16; z2++) {
                                    for (int x2 = 0; x2 < 16; x2++) {
                                        int block = section2.getFlatBlock(x2, y2, z2);
                                        if (FlowerPotHandler.isFlowah(block)) {
                                            Position pos = new Position(x2 + (chunk.getX() << 4), (short) (y2 + (i << 4)), z2 + (chunk.getZ() << 4));
                                            storage.checkAndStore(pos, block);
                                            CompoundTag nbt = provider.transform(wrapper.user(), pos, "minecraft:flower_pot");
                                            chunk.getBlockEntities().add(nbt);
                                        }
                                    }
                                }
                            }
                            for (int p = 0; p < section2.getPaletteSize(); p++) {
                                int old = section2.getPaletteEntry(p);
                                if (old != 0) {
                                    int oldId = ((Protocol1_12_2To1_13) BlockItemPackets1_13.this.protocol).getMappingData().getNewBlockStateId(old);
                                    section2.setPaletteEntry(p, oldId);
                                }
                            }
                        }
                    }
                    if (chunk.isBiomeData()) {
                        for (int i2 = 0; i2 < 256; i2++) {
                            int biome = chunk.getBiomeData()[i2];
                            int newId = -1;
                            switch (biome) {
                                case 40:
                                case 41:
                                case 42:
                                case 43:
                                    newId = 9;
                                    break;
                                case 44:
                                case 45:
                                case 46:
                                    newId = 0;
                                    break;
                                case 47:
                                case 48:
                                case 49:
                                    newId = 24;
                                    break;
                                case 50:
                                    newId = 10;
                                    break;
                            }
                            if (newId != -1) {
                                chunk.getBiomeData()[i2] = newId;
                            }
                        }
                    }
                    wrapper.write(type_old, chunk);
                });
            }
        });
        ((Protocol1_12_2To1_13) this.protocol).registerClientbound((Protocol1_12_2To1_13) ClientboundPackets1_13.EFFECT, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.BlockItemPackets1_13.10
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.POSITION);
                map(Type.INT);
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.BlockItemPackets1_13.10.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int id = ((Integer) wrapper.get(Type.INT, 0)).intValue();
                        int data = ((Integer) wrapper.get(Type.INT, 1)).intValue();
                        if (id == 1010) {
                            wrapper.set(Type.INT, 1, Integer.valueOf(((Protocol1_12_2To1_13) BlockItemPackets1_13.this.protocol).getMappingData().getItemMappings().get(data) >> 4));
                        } else if (id == 2001) {
                            int data2 = ((Protocol1_12_2To1_13) BlockItemPackets1_13.this.protocol).getMappingData().getNewBlockStateId(data);
                            int blockId = data2 >> 4;
                            int blockData = data2 & 15;
                            wrapper.set(Type.INT, 1, Integer.valueOf((blockId & 4095) | (blockData << 12)));
                        }
                    }
                });
            }
        });
        ((Protocol1_12_2To1_13) this.protocol).registerClientbound((Protocol1_12_2To1_13) ClientboundPackets1_13.MAP_DATA, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.BlockItemPackets1_13.11
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.BYTE);
                map(Type.BOOLEAN);
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.BlockItemPackets1_13.11.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int iconCount = ((Integer) wrapper.passthrough(Type.VAR_INT)).intValue();
                        for (int i = 0; i < iconCount; i++) {
                            int type = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                            byte x = ((Byte) wrapper.read(Type.BYTE)).byteValue();
                            byte z = ((Byte) wrapper.read(Type.BYTE)).byteValue();
                            byte direction = ((Byte) wrapper.read(Type.BYTE)).byteValue();
                            if (((Boolean) wrapper.read(Type.BOOLEAN)).booleanValue()) {
                                wrapper.read(Type.COMPONENT);
                            }
                            if (type > 9) {
                                wrapper.set(Type.VAR_INT, 1, Integer.valueOf(((Integer) wrapper.get(Type.VAR_INT, 1)).intValue() - 1));
                            } else {
                                wrapper.write(Type.BYTE, Byte.valueOf((byte) ((type << 4) | (direction & 15))));
                                wrapper.write(Type.BYTE, Byte.valueOf(x));
                                wrapper.write(Type.BYTE, Byte.valueOf(z));
                            }
                        }
                    }
                });
            }
        });
        ((Protocol1_12_2To1_13) this.protocol).registerClientbound((Protocol1_12_2To1_13) ClientboundPackets1_13.ENTITY_EQUIPMENT, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.BlockItemPackets1_13.12
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.VAR_INT);
                map(Type.FLAT_ITEM, Type.ITEM);
                handler(BlockItemPackets1_13.this.itemToClientHandler(Type.ITEM));
            }
        });
        ((Protocol1_12_2To1_13) this.protocol).registerClientbound((Protocol1_12_2To1_13) ClientboundPackets1_13.WINDOW_PROPERTY, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.BlockItemPackets1_13.13
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                map(Type.SHORT);
                map(Type.SHORT);
                handler(wrapper -> {
                    short property = ((Short) wrapper.get(Type.SHORT, 0)).shortValue();
                    if (property >= 4 && property <= 6) {
                        short oldId = ((Short) wrapper.get(Type.SHORT, 1)).shortValue();
                        wrapper.set(Type.SHORT, 1, Short.valueOf((short) ((Protocol1_12_2To1_13) BlockItemPackets1_13.this.protocol).getMappingData().getEnchantmentMappings().getNewId(oldId)));
                    }
                });
            }
        });
        ((Protocol1_12_2To1_13) this.protocol).registerServerbound((Protocol1_12_2To1_13) ServerboundPackets1_12_1.CREATIVE_INVENTORY_ACTION, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.BlockItemPackets1_13.14
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.SHORT);
                map(Type.ITEM, Type.FLAT_ITEM);
                handler(BlockItemPackets1_13.this.itemToServerHandler(Type.FLAT_ITEM));
            }
        });
        ((Protocol1_12_2To1_13) this.protocol).registerServerbound((Protocol1_12_2To1_13) ServerboundPackets1_12_1.CLICK_WINDOW, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.BlockItemPackets1_13.15
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                map(Type.SHORT);
                map(Type.BYTE);
                map(Type.SHORT);
                map(Type.VAR_INT);
                map(Type.ITEM, Type.FLAT_ITEM);
                handler(BlockItemPackets1_13.this.itemToServerHandler(Type.FLAT_ITEM));
            }
        });
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    protected void registerRewrites() {
        this.enchantmentMappings.put("minecraft:loyalty", "§7Loyalty");
        this.enchantmentMappings.put("minecraft:impaling", "§7Impaling");
        this.enchantmentMappings.put("minecraft:riptide", "§7Riptide");
        this.enchantmentMappings.put("minecraft:channeling", "§7Channeling");
    }

    @Override // com.viaversion.viabackwards.api.rewriters.ItemRewriter, com.viaversion.viaversion.rewriter.ItemRewriter, com.viaversion.viaversion.api.rewriter.ItemRewriter
    public Item handleItemToClient(Item item) {
        StringTag name;
        Tag originalIdTag;
        if (item == null) {
            return null;
        }
        int originalId = item.identifier();
        Integer rawId = null;
        boolean gotRawIdFromTag = false;
        CompoundTag tag = item.tag();
        if (tag != null && (originalIdTag = tag.remove(this.extraNbtTag)) != null) {
            rawId = Integer.valueOf(((NumberTag) originalIdTag).asInt());
            gotRawIdFromTag = true;
        }
        if (rawId == null) {
            super.handleItemToClient(item);
            if (item.identifier() == -1) {
                if (originalId == 362) {
                    rawId = 15007744;
                } else {
                    if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                        ViaBackwards.getPlatform().getLogger().warning("Failed to get 1.12 item for " + originalId);
                    }
                    rawId = 65536;
                }
            } else {
                if (tag == null) {
                    tag = item.tag();
                }
                rawId = Integer.valueOf(itemIdToRaw(item.identifier(), item, tag));
            }
        }
        item.setIdentifier(rawId.intValue() >> 16);
        item.setData((short) (rawId.intValue() & CharCompanionObject.MAX_VALUE));
        if (tag != null) {
            if (isDamageable(item.identifier())) {
                Tag damageTag = tag.remove("Damage");
                if (!gotRawIdFromTag && (damageTag instanceof IntTag)) {
                    item.setData((short) ((Integer) damageTag.getValue()).intValue());
                }
            }
            if (item.identifier() == 358) {
                Tag mapTag = tag.remove("map");
                if (!gotRawIdFromTag && (mapTag instanceof IntTag)) {
                    item.setData((short) ((Integer) mapTag.getValue()).intValue());
                }
            }
            invertShieldAndBannerId(item, tag);
            CompoundTag display = (CompoundTag) tag.get("display");
            if (display != null && (name = (StringTag) display.get("Name")) != null) {
                display.put(this.extraNbtTag + "|Name", new StringTag(name.getValue()));
                name.setValue(ChatRewriter.jsonToLegacyText(name.getValue()));
            }
            rewriteEnchantmentsToClient(tag, false);
            rewriteEnchantmentsToClient(tag, true);
            rewriteCanPlaceToClient(tag, "CanPlaceOn");
            rewriteCanPlaceToClient(tag, "CanDestroy");
        }
        return item;
    }

    private int itemIdToRaw(int oldId, Item item, CompoundTag tag) {
        Optional<String> eggEntityId = SpawnEggRewriter.getEntityId(oldId);
        if (eggEntityId.isPresent()) {
            if (tag == null) {
                CompoundTag compoundTag = new CompoundTag();
                tag = compoundTag;
                item.setTag(compoundTag);
            }
            if (!tag.contains("EntityTag")) {
                CompoundTag entityTag = new CompoundTag();
                entityTag.put("id", new StringTag(eggEntityId.get()));
                tag.put("EntityTag", entityTag);
                return 25100288;
            }
            return 25100288;
        }
        return ((oldId >> 4) << 16) | (oldId & 15);
    }

    private void rewriteCanPlaceToClient(CompoundTag tag, String tagName) {
        ListTag blockTag;
        if ((tag.get(tagName) instanceof ListTag) && (blockTag = (ListTag) tag.get(tagName)) != null) {
            ListTag newCanPlaceOn = new ListTag(StringTag.class);
            tag.put(this.extraNbtTag + CallSiteDescriptor.OPERATOR_DELIMITER + tagName, ConverterRegistry.convertToTag(ConverterRegistry.convertToValue(blockTag)));
            Iterator<Tag> it = blockTag.iterator();
            while (it.hasNext()) {
                Tag oldTag = it.next();
                Object value = oldTag.getValue();
                String[] newValues = value instanceof String ? BlockIdData.fallbackReverseMapping.get(((String) value).replace("minecraft:", "")) : null;
                if (newValues != null) {
                    for (String newValue : newValues) {
                        newCanPlaceOn.add(new StringTag(newValue));
                    }
                } else {
                    newCanPlaceOn.add(oldTag);
                }
            }
            tag.put(tagName, newCanPlaceOn);
        }
    }

    private void rewriteEnchantmentsToClient(CompoundTag tag, boolean storedEnch) {
        String key = storedEnch ? "StoredEnchantments" : "Enchantments";
        ListTag enchantments = (ListTag) tag.get(key);
        if (enchantments == null) {
            return;
        }
        ListTag noMapped = new ListTag(CompoundTag.class);
        ListTag newEnchantments = new ListTag(CompoundTag.class);
        List<Tag> lore = new ArrayList<>();
        boolean hasValidEnchants = false;
        Iterator<Tag> it = enchantments.clone().iterator();
        while (it.hasNext()) {
            Tag enchantmentEntryTag = it.next();
            CompoundTag enchantmentEntry = (CompoundTag) enchantmentEntryTag;
            Tag idTag = enchantmentEntry.get("id");
            if (idTag instanceof StringTag) {
                String newId = (String) idTag.getValue();
                int levelValue = ((NumberTag) enchantmentEntry.get("lvl")).asInt();
                short level = levelValue < 32767 ? (short) levelValue : Short.MAX_VALUE;
                String mappedEnchantmentId = this.enchantmentMappings.get(newId);
                if (mappedEnchantmentId != null) {
                    lore.add(new StringTag(mappedEnchantmentId + " " + EnchantmentRewriter.getRomanNumber(level)));
                    noMapped.add(enchantmentEntry);
                } else if (!newId.isEmpty()) {
                    Short oldId = (Short) Protocol1_13To1_12_2.MAPPINGS.getOldEnchantmentsIds().inverse().get(newId);
                    if (oldId == null) {
                        if (!newId.startsWith("viaversion:legacy/")) {
                            noMapped.add(enchantmentEntry);
                            if (ViaBackwards.getConfig().addCustomEnchantsToLore()) {
                                String name = newId;
                                int index = name.indexOf(58) + 1;
                                if (index != 0 && index != name.length()) {
                                    name = name.substring(index);
                                }
                                lore.add(new StringTag(("§7" + Character.toUpperCase(name.charAt(0)) + name.substring(1).toLowerCase(Locale.ENGLISH)) + " " + EnchantmentRewriter.getRomanNumber(level)));
                            }
                            if (Via.getManager().isDebug()) {
                                ViaBackwards.getPlatform().getLogger().warning("Found unknown enchant: " + newId);
                            }
                        } else {
                            oldId = Short.valueOf(newId.substring(18));
                        }
                    }
                    if (level != 0) {
                        hasValidEnchants = true;
                    }
                    CompoundTag newEntry = new CompoundTag();
                    newEntry.put("id", new ShortTag(oldId.shortValue()));
                    newEntry.put("lvl", new ShortTag(level));
                    newEnchantments.add(newEntry);
                }
            }
        }
        if (!storedEnch && !hasValidEnchants) {
            IntTag hideFlags = (IntTag) tag.get("HideFlags");
            if (hideFlags == null) {
                hideFlags = new IntTag();
                tag.put(this.extraNbtTag + "|DummyEnchant", new ByteTag());
            } else {
                tag.put(this.extraNbtTag + "|OldHideFlags", new IntTag(hideFlags.asByte()));
            }
            if (newEnchantments.size() == 0) {
                CompoundTag enchEntry = new CompoundTag();
                enchEntry.put("id", new ShortTag((short) 0));
                enchEntry.put("lvl", new ShortTag((short) 0));
                newEnchantments.add(enchEntry);
            }
            int value = hideFlags.asByte() | 1;
            hideFlags.setValue(value);
            tag.put("HideFlags", hideFlags);
        }
        if (noMapped.size() != 0) {
            tag.put(this.extraNbtTag + CallSiteDescriptor.OPERATOR_DELIMITER + key, noMapped);
            if (!lore.isEmpty()) {
                CompoundTag display = (CompoundTag) tag.get("display");
                if (display == null) {
                    CompoundTag compoundTag = new CompoundTag();
                    display = compoundTag;
                    tag.put("display", compoundTag);
                }
                ListTag loreTag = (ListTag) display.get("Lore");
                if (loreTag == null) {
                    ListTag listTag = new ListTag(StringTag.class);
                    loreTag = listTag;
                    display.put("Lore", listTag);
                    tag.put(this.extraNbtTag + "|DummyLore", new ByteTag());
                } else if (loreTag.size() != 0) {
                    ListTag oldLore = new ListTag(StringTag.class);
                    Iterator<Tag> it2 = loreTag.iterator();
                    while (it2.hasNext()) {
                        Tag value2 = it2.next();
                        oldLore.add(value2.clone());
                    }
                    tag.put(this.extraNbtTag + "|OldLore", oldLore);
                    lore.addAll(loreTag.getValue());
                }
                loreTag.setValue(lore);
            }
        }
        tag.remove("Enchantments");
        tag.put(storedEnch ? key : "ench", newEnchantments);
    }

    @Override // com.viaversion.viabackwards.api.rewriters.ItemRewriter, com.viaversion.viabackwards.api.rewriters.ItemRewriterBase, com.viaversion.viaversion.rewriter.ItemRewriter, com.viaversion.viaversion.api.rewriter.ItemRewriter
    public Item handleItemToServer(Item item) {
        StringTag identifier;
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
            invertShieldAndBannerId(item, tag);
            Tag display = tag.get("display");
            if (display instanceof CompoundTag) {
                CompoundTag displayTag = (CompoundTag) display;
                StringTag name = (StringTag) displayTag.get("Name");
                if (name != null) {
                    StringTag via = (StringTag) displayTag.remove(this.extraNbtTag + "|Name");
                    name.setValue(via != null ? via.getValue() : ChatRewriter.legacyTextToJsonString(name.getValue()));
                }
            }
            rewriteEnchantmentsToServer(tag, false);
            rewriteEnchantmentsToServer(tag, true);
            rewriteCanPlaceToServer(tag, "CanPlaceOn");
            rewriteCanPlaceToServer(tag, "CanDestroy");
            if (item.identifier() == 383) {
                CompoundTag entityTag = (CompoundTag) tag.get("EntityTag");
                if (entityTag != null && (identifier = (StringTag) entityTag.get("id")) != null) {
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
            }
            if (tag.isEmpty()) {
                tag = null;
                item.setTag(null);
            }
        }
        int identifier2 = item.identifier();
        item.setIdentifier(rawId);
        super.handleItemToServer(item);
        if (item.identifier() != rawId && item.identifier() != -1) {
            return item;
        }
        item.setIdentifier(identifier2);
        int newId = -1;
        if (!((Protocol1_12_2To1_13) this.protocol).getMappingData().getItemMappings().inverse().containsKey(rawId)) {
            if (!isDamageable(item.identifier()) && item.identifier() != 358) {
                if (tag == null) {
                    CompoundTag compoundTag3 = new CompoundTag();
                    tag = compoundTag3;
                    item.setTag(compoundTag3);
                }
                tag.put(this.extraNbtTag, new IntTag(originalId));
            }
            if (item.identifier() == 229) {
                newId = 362;
            } else if (item.identifier() == 31 && item.data() == 0) {
                rawId = 512;
            } else if (((Protocol1_12_2To1_13) this.protocol).getMappingData().getItemMappings().inverse().containsKey(rawId & (-16))) {
                rawId &= -16;
            } else {
                if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                    ViaBackwards.getPlatform().getLogger().warning("Failed to get 1.13 item for " + item.identifier());
                }
                rawId = 16;
            }
        }
        if (newId == -1) {
            newId = ((Protocol1_12_2To1_13) this.protocol).getMappingData().getItemMappings().inverse().get(rawId);
        }
        item.setIdentifier(newId);
        item.setData((short) 0);
        return item;
    }

    private void rewriteCanPlaceToServer(CompoundTag tag, String tagName) {
        if (!(tag.get(tagName) instanceof ListTag)) {
            return;
        }
        ListTag blockTag = (ListTag) tag.remove(this.extraNbtTag + CallSiteDescriptor.OPERATOR_DELIMITER + tagName);
        if (blockTag != null) {
            tag.put(tagName, ConverterRegistry.convertToTag(ConverterRegistry.convertToValue(blockTag)));
            return;
        }
        ListTag blockTag2 = (ListTag) tag.get(tagName);
        if (blockTag2 != null) {
            ListTag newCanPlaceOn = new ListTag(StringTag.class);
            Iterator<Tag> it = blockTag2.iterator();
            while (it.hasNext()) {
                Tag oldTag = it.next();
                Object value = oldTag.getValue();
                String oldId = value.toString().replace("minecraft:", "");
                int key = Ints.tryParse(oldId).intValue();
                String numberConverted = BlockIdData.numberIdToString.get(key);
                if (numberConverted != null) {
                    oldId = numberConverted;
                }
                String lowerCaseId = oldId.toLowerCase(Locale.ROOT);
                String[] newValues = BlockIdData.blockIdMapping.get(lowerCaseId);
                if (newValues != null) {
                    for (String newValue : newValues) {
                        newCanPlaceOn.add(new StringTag(newValue));
                    }
                } else {
                    newCanPlaceOn.add(new StringTag(lowerCaseId));
                }
            }
            tag.put(tagName, newCanPlaceOn);
        }
    }

    private void rewriteEnchantmentsToServer(CompoundTag tag, boolean storedEnch) {
        String key = storedEnch ? "StoredEnchantments" : "Enchantments";
        ListTag enchantments = (ListTag) tag.get(storedEnch ? key : "ench");
        if (enchantments == null) {
            return;
        }
        ListTag newEnchantments = new ListTag(CompoundTag.class);
        boolean dummyEnchant = false;
        if (!storedEnch) {
            IntTag hideFlags = (IntTag) tag.remove(this.extraNbtTag + "|OldHideFlags");
            if (hideFlags != null) {
                tag.put("HideFlags", new IntTag(hideFlags.asByte()));
                dummyEnchant = true;
            } else if (tag.remove(this.extraNbtTag + "|DummyEnchant") != null) {
                tag.remove("HideFlags");
                dummyEnchant = true;
            }
        }
        Iterator<Tag> it = enchantments.iterator();
        while (it.hasNext()) {
            Tag enchEntry = it.next();
            CompoundTag enchantmentEntry = new CompoundTag();
            short oldId = ((NumberTag) ((CompoundTag) enchEntry).get("id")).asShort();
            short level = ((NumberTag) ((CompoundTag) enchEntry).get("lvl")).asShort();
            if (!dummyEnchant || oldId != 0 || level != 0) {
                String newId = (String) Protocol1_13To1_12_2.MAPPINGS.getOldEnchantmentsIds().get(Short.valueOf(oldId));
                if (newId == null) {
                    newId = "viaversion:legacy/" + ((int) oldId);
                }
                enchantmentEntry.put("id", new StringTag(newId));
                enchantmentEntry.put("lvl", new ShortTag(level));
                newEnchantments.add(enchantmentEntry);
            }
        }
        ListTag noMapped = (ListTag) tag.remove(this.extraNbtTag + "|Enchantments");
        if (noMapped != null) {
            Iterator<Tag> it2 = noMapped.iterator();
            while (it2.hasNext()) {
                Tag value = it2.next();
                newEnchantments.add(value);
            }
        }
        CompoundTag display = (CompoundTag) tag.get("display");
        if (display == null) {
            CompoundTag compoundTag = new CompoundTag();
            display = compoundTag;
            tag.put("display", compoundTag);
        }
        ListTag oldLore = (ListTag) tag.remove(this.extraNbtTag + "|OldLore");
        if (oldLore != null) {
            ListTag lore = (ListTag) display.get("Lore");
            if (lore == null) {
                ListTag listTag = new ListTag();
                lore = listTag;
                tag.put("Lore", listTag);
            }
            lore.setValue(oldLore.getValue());
        } else if (tag.remove(this.extraNbtTag + "|DummyLore") != null) {
            display.remove("Lore");
            if (display.isEmpty()) {
                tag.remove("display");
            }
        }
        if (!storedEnch) {
            tag.remove("ench");
        }
        tag.put(key, newEnchantments);
    }

    private void invertShieldAndBannerId(Item item, CompoundTag tag) {
        if (item.identifier() == 442 || item.identifier() == 425) {
            Tag blockEntityTag = tag.get("BlockEntityTag");
            if (!(blockEntityTag instanceof CompoundTag)) {
                return;
            }
            CompoundTag blockEntityCompoundTag = (CompoundTag) blockEntityTag;
            Tag base = blockEntityCompoundTag.get("Base");
            if (base instanceof IntTag) {
                IntTag baseTag = (IntTag) base;
                baseTag.setValue(15 - baseTag.asInt());
            }
            Tag patterns = blockEntityCompoundTag.get("Patterns");
            if (patterns instanceof ListTag) {
                ListTag patternsTag = (ListTag) patterns;
                Iterator<Tag> it = patternsTag.iterator();
                while (it.hasNext()) {
                    Tag pattern = it.next();
                    if (pattern instanceof CompoundTag) {
                        IntTag colorTag = (IntTag) ((CompoundTag) pattern).get("Color");
                        colorTag.setValue(15 - colorTag.asInt());
                    }
                }
            }
        }
    }

    public static void flowerPotSpecialTreatment(UserConnection user, int blockState, Position position) throws Exception {
        if (FlowerPotHandler.isFlowah(blockState)) {
            BackwardsBlockEntityProvider beProvider = (BackwardsBlockEntityProvider) Via.getManager().getProviders().get(BackwardsBlockEntityProvider.class);
            CompoundTag nbt = beProvider.transform(user, position, "minecraft:flower_pot");
            PacketWrapper blockUpdateRemove = PacketWrapper.create(11, (ByteBuf) null, user);
            blockUpdateRemove.write(Type.POSITION, position);
            blockUpdateRemove.write(Type.VAR_INT, 0);
            blockUpdateRemove.scheduleSend(Protocol1_12_2To1_13.class);
            PacketWrapper blockCreate = PacketWrapper.create(11, (ByteBuf) null, user);
            blockCreate.write(Type.POSITION, position);
            blockCreate.write(Type.VAR_INT, Integer.valueOf(Protocol1_12_2To1_13.MAPPINGS.getNewBlockStateId(blockState)));
            blockCreate.scheduleSend(Protocol1_12_2To1_13.class);
            PacketWrapper wrapper = PacketWrapper.create(9, (ByteBuf) null, user);
            wrapper.write(Type.POSITION, position);
            wrapper.write(Type.UNSIGNED_BYTE, (short) 5);
            wrapper.write(Type.NBT, nbt);
            wrapper.scheduleSend(Protocol1_12_2To1_13.class);
        }
    }
}
