package com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.packets;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.rewriters.ItemRewriter;
import com.viaversion.viabackwards.api.rewriters.MapColorRewriter;
import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.Protocol1_16_4To1_17;
import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.data.MapColorRewrites;
import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.storage.PingRequests;
import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.storage.PlayerLastCursorItem;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ServerboundPackets1_16_2;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.types.Chunk1_16_2Type;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.data.RecipeRewriter1_16;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ClientboundPackets1_17;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ServerboundPackets1_17;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.types.Chunk1_17Type;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.util.CompactArrayUtil;
import com.viaversion.viaversion.util.MathUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_16_4to1_17/packets/BlockItemPackets1_17.class */
public final class BlockItemPackets1_17 extends ItemRewriter<Protocol1_16_4To1_17> {
    public BlockItemPackets1_17(Protocol1_16_4To1_17 protocol) {
        super(protocol);
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    protected void registerPackets() {
        BlockRewriter blockRewriter = new BlockRewriter(this.protocol, Type.POSITION1_14);
        new RecipeRewriter1_16(this.protocol).registerDefaultHandler(ClientboundPackets1_17.DECLARE_RECIPES);
        registerSetCooldown(ClientboundPackets1_17.COOLDOWN);
        registerWindowItems(ClientboundPackets1_17.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY);
        registerEntityEquipmentArray(ClientboundPackets1_17.ENTITY_EQUIPMENT, Type.FLAT_VAR_INT_ITEM);
        registerTradeList(ClientboundPackets1_17.TRADE_LIST, Type.FLAT_VAR_INT_ITEM);
        registerAdvancements(ClientboundPackets1_17.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
        blockRewriter.registerAcknowledgePlayerDigging(ClientboundPackets1_17.ACKNOWLEDGE_PLAYER_DIGGING);
        blockRewriter.registerBlockAction(ClientboundPackets1_17.BLOCK_ACTION);
        blockRewriter.registerEffect(ClientboundPackets1_17.EFFECT, 1010, 2001);
        registerCreativeInvAction(ServerboundPackets1_16_2.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_16_4To1_17) this.protocol).registerServerbound((Protocol1_16_4To1_17) ServerboundPackets1_16_2.EDIT_BOOK, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.packets.BlockItemPackets1_17.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    BlockItemPackets1_17.this.handleItemToServer((Item) wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
                });
            }
        });
        ((Protocol1_16_4To1_17) this.protocol).registerServerbound((Protocol1_16_4To1_17) ServerboundPackets1_16_2.CLICK_WINDOW, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.packets.BlockItemPackets1_17.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                handler(wrapper -> {
                    short slot = ((Short) wrapper.passthrough(Type.SHORT)).shortValue();
                    byte button = ((Byte) wrapper.passthrough(Type.BYTE)).byteValue();
                    wrapper.read(Type.SHORT);
                    int mode = ((Integer) wrapper.passthrough(Type.VAR_INT)).intValue();
                    Item clicked = BlockItemPackets1_17.this.handleItemToServer((Item) wrapper.read(Type.FLAT_VAR_INT_ITEM));
                    wrapper.write(Type.VAR_INT, 0);
                    PlayerLastCursorItem state = (PlayerLastCursorItem) wrapper.user().get(PlayerLastCursorItem.class);
                    if (mode == 0 && button == 0 && clicked != null) {
                        state.setLastCursorItem(clicked);
                    } else if (mode == 0 && button == 1 && clicked != null) {
                        if (state.isSet()) {
                            state.setLastCursorItem(clicked);
                        } else {
                            state.setLastCursorItem(clicked, (clicked.amount() + 1) / 2);
                        }
                    } else if (mode != 5 || slot != -999 || (button != 0 && button != 4)) {
                        state.setLastCursorItem(null);
                    }
                    Item carried = state.getLastCursorItem();
                    if (carried == null) {
                        wrapper.write(Type.FLAT_VAR_INT_ITEM, clicked);
                    } else {
                        wrapper.write(Type.FLAT_VAR_INT_ITEM, carried);
                    }
                });
            }
        });
        ((Protocol1_16_4To1_17) this.protocol).registerClientbound((Protocol1_16_4To1_17) ClientboundPackets1_17.SET_SLOT, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.packets.BlockItemPackets1_17.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    short windowId = ((Short) wrapper.passthrough(Type.UNSIGNED_BYTE)).shortValue();
                    short slot = ((Short) wrapper.passthrough(Type.SHORT)).shortValue();
                    Item carried = (Item) wrapper.read(Type.FLAT_VAR_INT_ITEM);
                    if (carried != null && windowId == -1 && slot == -1) {
                        ((PlayerLastCursorItem) wrapper.user().get(PlayerLastCursorItem.class)).setLastCursorItem(carried);
                    }
                    wrapper.write(Type.FLAT_VAR_INT_ITEM, BlockItemPackets1_17.this.handleItemToClient(carried));
                });
            }
        });
        ((Protocol1_16_4To1_17) this.protocol).registerServerbound((Protocol1_16_4To1_17) ServerboundPackets1_16_2.WINDOW_CONFIRMATION, (ServerboundPackets1_16_2) null, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.packets.BlockItemPackets1_17.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    wrapper.cancel();
                    if (!ViaBackwards.getConfig().handlePingsAsInvAcknowledgements()) {
                        return;
                    }
                    short inventoryId = ((Short) wrapper.read(Type.UNSIGNED_BYTE)).shortValue();
                    short confirmationId = ((Short) wrapper.read(Type.SHORT)).shortValue();
                    boolean accepted = ((Boolean) wrapper.read(Type.BOOLEAN)).booleanValue();
                    if (inventoryId == 0 && accepted && ((PingRequests) wrapper.user().get(PingRequests.class)).removeId(confirmationId)) {
                        PacketWrapper pongPacket = wrapper.create(ServerboundPackets1_17.PONG);
                        pongPacket.write(Type.INT, Integer.valueOf(confirmationId));
                        pongPacket.sendToServer(Protocol1_16_4To1_17.class);
                    }
                });
            }
        });
        ((Protocol1_16_4To1_17) this.protocol).registerClientbound((Protocol1_16_4To1_17) ClientboundPackets1_17.SPAWN_PARTICLE, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.packets.BlockItemPackets1_17.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.BOOLEAN);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.DOUBLE);
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.INT);
                handler(wrapper -> {
                    int id = ((Integer) wrapper.get(Type.INT, 0)).intValue();
                    if (id == 16) {
                        wrapper.passthrough(Type.FLOAT);
                        wrapper.passthrough(Type.FLOAT);
                        wrapper.passthrough(Type.FLOAT);
                        wrapper.passthrough(Type.FLOAT);
                        wrapper.read(Type.FLOAT);
                        wrapper.read(Type.FLOAT);
                        wrapper.read(Type.FLOAT);
                    } else if (id == 37) {
                        wrapper.set(Type.INT, 0, -1);
                        wrapper.cancel();
                    }
                });
                handler(BlockItemPackets1_17.this.getSpawnParticleHandler(Type.FLAT_VAR_INT_ITEM));
            }
        });
        ((Protocol1_16_4To1_17) this.protocol).mergePacket(ClientboundPackets1_17.WORLD_BORDER_SIZE, ClientboundPackets1_16_2.WORLD_BORDER, 0);
        ((Protocol1_16_4To1_17) this.protocol).mergePacket(ClientboundPackets1_17.WORLD_BORDER_LERP_SIZE, ClientboundPackets1_16_2.WORLD_BORDER, 1);
        ((Protocol1_16_4To1_17) this.protocol).mergePacket(ClientboundPackets1_17.WORLD_BORDER_CENTER, ClientboundPackets1_16_2.WORLD_BORDER, 2);
        ((Protocol1_16_4To1_17) this.protocol).mergePacket(ClientboundPackets1_17.WORLD_BORDER_INIT, ClientboundPackets1_16_2.WORLD_BORDER, 3);
        ((Protocol1_16_4To1_17) this.protocol).mergePacket(ClientboundPackets1_17.WORLD_BORDER_WARNING_DELAY, ClientboundPackets1_16_2.WORLD_BORDER, 4);
        ((Protocol1_16_4To1_17) this.protocol).mergePacket(ClientboundPackets1_17.WORLD_BORDER_WARNING_DISTANCE, ClientboundPackets1_16_2.WORLD_BORDER, 5);
        ((Protocol1_16_4To1_17) this.protocol).registerClientbound((Protocol1_16_4To1_17) ClientboundPackets1_17.UPDATE_LIGHT, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.packets.BlockItemPackets1_17.6
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.VAR_INT);
                map(Type.BOOLEAN);
                handler(wrapper -> {
                    EntityTracker tracker = wrapper.user().getEntityTracker(Protocol1_16_4To1_17.class);
                    int startFromSection = Math.max(0, -(tracker.currentMinY() >> 4));
                    long[] skyLightMask = (long[]) wrapper.read(Type.LONG_ARRAY_PRIMITIVE);
                    long[] blockLightMask = (long[]) wrapper.read(Type.LONG_ARRAY_PRIMITIVE);
                    int cutSkyLightMask = BlockItemPackets1_17.this.cutLightMask(skyLightMask, startFromSection);
                    int cutBlockLightMask = BlockItemPackets1_17.this.cutLightMask(blockLightMask, startFromSection);
                    wrapper.write(Type.VAR_INT, Integer.valueOf(cutSkyLightMask));
                    wrapper.write(Type.VAR_INT, Integer.valueOf(cutBlockLightMask));
                    long[] emptySkyLightMask = (long[]) wrapper.read(Type.LONG_ARRAY_PRIMITIVE);
                    long[] emptyBlockLightMask = (long[]) wrapper.read(Type.LONG_ARRAY_PRIMITIVE);
                    wrapper.write(Type.VAR_INT, Integer.valueOf(BlockItemPackets1_17.this.cutLightMask(emptySkyLightMask, startFromSection)));
                    wrapper.write(Type.VAR_INT, Integer.valueOf(BlockItemPackets1_17.this.cutLightMask(emptyBlockLightMask, startFromSection)));
                    writeLightArrays(wrapper, BitSet.valueOf(skyLightMask), cutSkyLightMask, startFromSection, tracker.currentWorldSectionHeight());
                    writeLightArrays(wrapper, BitSet.valueOf(blockLightMask), cutBlockLightMask, startFromSection, tracker.currentWorldSectionHeight());
                });
            }

            private void writeLightArrays(PacketWrapper wrapper, BitSet bitMask, int cutBitMask, int startFromSection, int sectionHeight) throws Exception {
                wrapper.read(Type.VAR_INT);
                List<byte[]> light = new ArrayList<>();
                for (int i = 0; i < startFromSection; i++) {
                    if (bitMask.get(i)) {
                        wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                    }
                }
                for (int i2 = 0; i2 < 18; i2++) {
                    if (isSet(cutBitMask, i2)) {
                        light.add((byte[]) wrapper.read(Type.BYTE_ARRAY_PRIMITIVE));
                    }
                }
                for (int i3 = startFromSection + 18; i3 < sectionHeight + 2; i3++) {
                    if (bitMask.get(i3)) {
                        wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                    }
                }
                for (byte[] bytes : light) {
                    wrapper.write(Type.BYTE_ARRAY_PRIMITIVE, bytes);
                }
            }

            private boolean isSet(int mask, int i) {
                return (mask & (1 << i)) != 0;
            }
        });
        ((Protocol1_16_4To1_17) this.protocol).registerClientbound((Protocol1_16_4To1_17) ClientboundPackets1_17.MULTI_BLOCK_CHANGE, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.packets.BlockItemPackets1_17.7
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.LONG);
                map(Type.BOOLEAN);
                handler(wrapper -> {
                    long chunkPos = ((Long) wrapper.get(Type.LONG, 0)).longValue();
                    int chunkY = (int) ((chunkPos << 44) >> 44);
                    if (chunkY < 0 || chunkY > 15) {
                        wrapper.cancel();
                        return;
                    }
                    BlockChangeRecord[] records = (BlockChangeRecord[]) wrapper.passthrough(Type.VAR_LONG_BLOCK_CHANGE_RECORD_ARRAY);
                    for (BlockChangeRecord record : records) {
                        record.setBlockId(((Protocol1_16_4To1_17) BlockItemPackets1_17.this.protocol).getMappingData().getNewBlockStateId(record.getBlockId()));
                    }
                });
            }
        });
        ((Protocol1_16_4To1_17) this.protocol).registerClientbound((Protocol1_16_4To1_17) ClientboundPackets1_17.BLOCK_CHANGE, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.packets.BlockItemPackets1_17.8
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.POSITION1_14);
                map(Type.VAR_INT);
                handler(wrapper -> {
                    int y = ((Position) wrapper.get(Type.POSITION1_14, 0)).getY();
                    if (y < 0 || y > 255) {
                        wrapper.cancel();
                    } else {
                        wrapper.set(Type.VAR_INT, 0, Integer.valueOf(((Protocol1_16_4To1_17) BlockItemPackets1_17.this.protocol).getMappingData().getNewBlockStateId(((Integer) wrapper.get(Type.VAR_INT, 0)).intValue())));
                    }
                });
            }
        });
        ((Protocol1_16_4To1_17) this.protocol).registerClientbound((Protocol1_16_4To1_17) ClientboundPackets1_17.CHUNK_DATA, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.packets.BlockItemPackets1_17.9
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    EntityTracker tracker = wrapper.user().getEntityTracker(Protocol1_16_4To1_17.class);
                    int currentWorldSectionHeight = tracker.currentWorldSectionHeight();
                    Chunk chunk = (Chunk) wrapper.read(new Chunk1_17Type(currentWorldSectionHeight));
                    wrapper.write(new Chunk1_16_2Type(), chunk);
                    int startFromSection = Math.max(0, -(tracker.currentMinY() >> 4));
                    chunk.setBiomeData(Arrays.copyOfRange(chunk.getBiomeData(), startFromSection * 64, (startFromSection * 64) + 1024));
                    chunk.setBitmask(BlockItemPackets1_17.this.cutMask(chunk.getChunkMask(), startFromSection, false));
                    chunk.setChunkMask(null);
                    ChunkSection[] sections = (ChunkSection[]) Arrays.copyOfRange(chunk.getSections(), startFromSection, startFromSection + 16);
                    chunk.setSections(sections);
                    CompoundTag heightMaps = chunk.getHeightMap();
                    for (Tag heightMapTag : heightMaps.values()) {
                        LongArrayTag heightMap = (LongArrayTag) heightMapTag;
                        int[] heightMapData = new int[256];
                        int bitsPerEntry = MathUtil.ceilLog2((currentWorldSectionHeight << 4) + 1);
                        CompactArrayUtil.iterateCompactArrayWithPadding(bitsPerEntry, heightMapData.length, heightMap.getValue(), i, v -> {
                            heightMapData[i] = MathUtil.clamp(v + tracker.currentMinY(), 0, 255);
                        });
                        heightMap.setValue(CompactArrayUtil.createCompactArrayWithPadding(9, heightMapData.length, i2 -> {
                            return heightMapData[i2];
                        }));
                    }
                    for (int i3 = 0; i3 < 16; i3++) {
                        ChunkSection section = sections[i3];
                        if (section != null) {
                            for (int j = 0; j < section.getPaletteSize(); j++) {
                                int old = section.getPaletteEntry(j);
                                section.setPaletteEntry(j, ((Protocol1_16_4To1_17) BlockItemPackets1_17.this.protocol).getMappingData().getNewBlockStateId(old));
                            }
                        }
                    }
                    chunk.getBlockEntities().removeIf(compound -> {
                        NumberTag tag = (NumberTag) compound.get("y");
                        return tag != null && (tag.asInt() < 0 || tag.asInt() > 255);
                    });
                });
            }
        });
        ((Protocol1_16_4To1_17) this.protocol).registerClientbound((Protocol1_16_4To1_17) ClientboundPackets1_17.BLOCK_ENTITY_DATA, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.packets.BlockItemPackets1_17.10
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    int y = ((Position) wrapper.passthrough(Type.POSITION1_14)).getY();
                    if (y < 0 || y > 255) {
                        wrapper.cancel();
                    }
                });
            }
        });
        ((Protocol1_16_4To1_17) this.protocol).registerClientbound((Protocol1_16_4To1_17) ClientboundPackets1_17.BLOCK_BREAK_ANIMATION, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.packets.BlockItemPackets1_17.11
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                handler(wrapper -> {
                    int y = ((Position) wrapper.passthrough(Type.POSITION1_14)).getY();
                    if (y < 0 || y > 255) {
                        wrapper.cancel();
                    }
                });
            }
        });
        ((Protocol1_16_4To1_17) this.protocol).registerClientbound((Protocol1_16_4To1_17) ClientboundPackets1_17.MAP_DATA, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.packets.BlockItemPackets1_17.12
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.BYTE);
                handler(wrapper -> {
                    wrapper.write(Type.BOOLEAN, true);
                });
                map(Type.BOOLEAN);
                handler(wrapper2 -> {
                    boolean hasMarkers = ((Boolean) wrapper2.read(Type.BOOLEAN)).booleanValue();
                    if (!hasMarkers) {
                        wrapper2.write(Type.VAR_INT, 0);
                    } else {
                        MapColorRewriter.getRewriteHandler(MapColorRewrites::getMappedColor).handle(wrapper2);
                    }
                });
            }
        });
    }

    public int cutLightMask(long[] mask, int startFromSection) {
        if (mask.length == 0) {
            return 0;
        }
        return cutMask(BitSet.valueOf(mask), startFromSection, true);
    }

    public int cutMask(BitSet mask, int startFromSection, boolean lightMask) {
        int cutMask = 0;
        int to = startFromSection + (lightMask ? 18 : 16);
        int i = startFromSection;
        int j = 0;
        while (i < to) {
            if (mask.get(i)) {
                cutMask |= 1 << j;
            }
            i++;
            j++;
        }
        return cutMask;
    }
}
