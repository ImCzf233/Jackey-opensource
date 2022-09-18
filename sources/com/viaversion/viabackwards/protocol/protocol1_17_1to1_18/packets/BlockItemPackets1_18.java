package com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.packets;

import com.viaversion.viabackwards.api.rewriters.ItemRewriter;
import com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.Protocol1_17_1To1_18;
import com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.data.BlockEntityIds;
import com.viaversion.viaversion.api.data.ParticleMappings;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntity;
import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.data.RecipeRewriter1_16;
import com.viaversion.viaversion.protocols.protocol1_17_1to1_17.ClientboundPackets1_17_1;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ServerboundPackets1_17;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.types.Chunk1_17Type;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.ClientboundPackets1_18;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.types.Chunk1_18Type;
import com.viaversion.viaversion.util.MathUtil;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_17_1to1_18/packets/BlockItemPackets1_18.class */
public final class BlockItemPackets1_18 extends ItemRewriter<Protocol1_17_1To1_18> {
    public BlockItemPackets1_18(Protocol1_17_1To1_18 protocol) {
        super(protocol);
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    protected void registerPackets() {
        new RecipeRewriter1_16(this.protocol).registerDefaultHandler(ClientboundPackets1_18.DECLARE_RECIPES);
        registerSetCooldown(ClientboundPackets1_18.COOLDOWN);
        registerWindowItems1_17_1(ClientboundPackets1_18.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT, Type.FLAT_VAR_INT_ITEM);
        registerSetSlot1_17_1(ClientboundPackets1_18.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
        registerEntityEquipmentArray(ClientboundPackets1_18.ENTITY_EQUIPMENT, Type.FLAT_VAR_INT_ITEM);
        registerTradeList(ClientboundPackets1_18.TRADE_LIST, Type.FLAT_VAR_INT_ITEM);
        registerAdvancements(ClientboundPackets1_18.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
        registerClickWindow1_17_1(ServerboundPackets1_17.CLICK_WINDOW, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_17_1To1_18) this.protocol).registerClientbound((Protocol1_17_1To1_18) ClientboundPackets1_18.EFFECT, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.packets.BlockItemPackets1_18.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.POSITION1_14);
                map(Type.INT);
                handler(wrapper -> {
                    int id = ((Integer) wrapper.get(Type.INT, 0)).intValue();
                    int data = ((Integer) wrapper.get(Type.INT, 1)).intValue();
                    if (id == 1010) {
                        wrapper.set(Type.INT, 1, Integer.valueOf(((Protocol1_17_1To1_18) BlockItemPackets1_18.this.protocol).getMappingData().getNewItemId(data)));
                    }
                });
            }
        });
        registerCreativeInvAction(ServerboundPackets1_17.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_17_1To1_18) this.protocol).registerClientbound((Protocol1_17_1To1_18) ClientboundPackets1_18.SPAWN_PARTICLE, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.packets.BlockItemPackets1_18.2
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
                    if (id != 3) {
                        ParticleMappings mappings = ((Protocol1_17_1To1_18) BlockItemPackets1_18.this.protocol).getMappingData().getParticleMappings();
                        if (mappings.isBlockParticle(id)) {
                            int data = ((Integer) wrapper.passthrough(Type.VAR_INT)).intValue();
                            wrapper.set(Type.VAR_INT, 0, Integer.valueOf(((Protocol1_17_1To1_18) BlockItemPackets1_18.this.protocol).getMappingData().getNewBlockStateId(data)));
                        } else if (mappings.isItemParticle(id)) {
                            BlockItemPackets1_18.this.handleItemToClient((Item) wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
                        }
                        int newId = ((Protocol1_17_1To1_18) BlockItemPackets1_18.this.protocol).getMappingData().getNewParticleId(id);
                        if (newId != id) {
                            wrapper.set(Type.INT, 0, Integer.valueOf(newId));
                            return;
                        }
                        return;
                    }
                    int blockState = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                    if (blockState == 7786) {
                        wrapper.set(Type.INT, 0, 3);
                    } else {
                        wrapper.set(Type.INT, 0, 2);
                    }
                });
            }
        });
        ((Protocol1_17_1To1_18) this.protocol).registerClientbound((Protocol1_17_1To1_18) ClientboundPackets1_18.BLOCK_ENTITY_DATA, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.packets.BlockItemPackets1_18.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.POSITION1_14);
                handler(wrapper -> {
                    int id = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                    CompoundTag tag = (CompoundTag) wrapper.read(Type.NBT);
                    int mappedId = BlockEntityIds.mappedId(id);
                    if (mappedId != -1) {
                        String identifier = ((Protocol1_17_1To1_18) BlockItemPackets1_18.this.protocol).getMappingData().blockEntities().get(id);
                        if (identifier == null) {
                            wrapper.cancel();
                            return;
                        }
                        CompoundTag newTag = tag == null ? new CompoundTag() : tag;
                        Position pos = (Position) wrapper.get(Type.POSITION1_14, 0);
                        newTag.put("id", new StringTag("minecraft:" + identifier));
                        newTag.put("x", new IntTag(pos.m228x()));
                        newTag.put("y", new IntTag(pos.m227y()));
                        newTag.put("z", new IntTag(pos.m226z()));
                        BlockItemPackets1_18.this.handleSpawner(id, newTag);
                        wrapper.write(Type.UNSIGNED_BYTE, Short.valueOf((short) mappedId));
                        wrapper.write(Type.NBT, newTag);
                        return;
                    }
                    wrapper.cancel();
                });
            }
        });
        ((Protocol1_17_1To1_18) this.protocol).registerClientbound((Protocol1_17_1To1_18) ClientboundPackets1_18.CHUNK_DATA, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.packets.BlockItemPackets1_18.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    CompoundTag tag;
                    EntityTracker tracker = ((Protocol1_17_1To1_18) BlockItemPackets1_18.this.protocol).getEntityRewriter().tracker(wrapper.user());
                    Chunk1_18Type chunkType = new Chunk1_18Type(tracker.currentWorldSectionHeight(), MathUtil.ceilLog2(((Protocol1_17_1To1_18) BlockItemPackets1_18.this.protocol).getMappingData().getBlockStateMappings().size()), MathUtil.ceilLog2(tracker.biomesSent()));
                    Chunk oldChunk = (Chunk) wrapper.read(chunkType);
                    ChunkSection[] sections = oldChunk.getSections();
                    BitSet mask = new BitSet(oldChunk.getSections().length);
                    int[] biomeData = new int[sections.length * 64];
                    int biomeIndex = 0;
                    for (int j = 0; j < sections.length; j++) {
                        ChunkSection section = sections[j];
                        DataPalette biomePalette = section.palette(PaletteType.BIOMES);
                        for (int i = 0; i < 64; i++) {
                            int i2 = biomeIndex;
                            biomeIndex++;
                            biomeData[i2] = biomePalette.idAt(i);
                        }
                        if (section.getNonAirBlocksCount() == 0) {
                            sections[j] = null;
                        } else {
                            mask.set(j);
                        }
                    }
                    List<CompoundTag> blockEntityTags = new ArrayList<>(oldChunk.blockEntities().size());
                    for (BlockEntity blockEntity : oldChunk.blockEntities()) {
                        String id = ((Protocol1_17_1To1_18) BlockItemPackets1_18.this.protocol).getMappingData().blockEntities().get(blockEntity.typeId());
                        if (id != null) {
                            if (blockEntity.tag() != null) {
                                tag = blockEntity.tag();
                                BlockItemPackets1_18.this.handleSpawner(blockEntity.typeId(), tag);
                            } else {
                                tag = new CompoundTag();
                            }
                            blockEntityTags.add(tag);
                            tag.put("x", new IntTag((oldChunk.getX() << 4) + blockEntity.sectionX()));
                            tag.put("y", new IntTag(blockEntity.mo225y()));
                            tag.put("z", new IntTag((oldChunk.getZ() << 4) + blockEntity.sectionZ()));
                            tag.put("id", new StringTag("minecraft:" + id));
                        }
                    }
                    BaseChunk baseChunk = new BaseChunk(oldChunk.getX(), oldChunk.getZ(), true, false, mask, oldChunk.getSections(), biomeData, oldChunk.getHeightMap(), blockEntityTags);
                    wrapper.write(new Chunk1_17Type(tracker.currentWorldSectionHeight()), baseChunk);
                    PacketWrapper lightPacket = wrapper.create(ClientboundPackets1_17_1.UPDATE_LIGHT);
                    lightPacket.write(Type.VAR_INT, Integer.valueOf(baseChunk.getX()));
                    lightPacket.write(Type.VAR_INT, Integer.valueOf(baseChunk.getZ()));
                    lightPacket.write(Type.BOOLEAN, (Boolean) wrapper.read(Type.BOOLEAN));
                    lightPacket.write(Type.LONG_ARRAY_PRIMITIVE, (long[]) wrapper.read(Type.LONG_ARRAY_PRIMITIVE));
                    lightPacket.write(Type.LONG_ARRAY_PRIMITIVE, (long[]) wrapper.read(Type.LONG_ARRAY_PRIMITIVE));
                    lightPacket.write(Type.LONG_ARRAY_PRIMITIVE, (long[]) wrapper.read(Type.LONG_ARRAY_PRIMITIVE));
                    lightPacket.write(Type.LONG_ARRAY_PRIMITIVE, (long[]) wrapper.read(Type.LONG_ARRAY_PRIMITIVE));
                    int skyLightLength = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                    lightPacket.write(Type.VAR_INT, Integer.valueOf(skyLightLength));
                    for (int i3 = 0; i3 < skyLightLength; i3++) {
                        lightPacket.write(Type.BYTE_ARRAY_PRIMITIVE, (byte[]) wrapper.read(Type.BYTE_ARRAY_PRIMITIVE));
                    }
                    int blockLightLength = ((Integer) wrapper.read(Type.VAR_INT)).intValue();
                    lightPacket.write(Type.VAR_INT, Integer.valueOf(blockLightLength));
                    for (int i4 = 0; i4 < blockLightLength; i4++) {
                        lightPacket.write(Type.BYTE_ARRAY_PRIMITIVE, (byte[]) wrapper.read(Type.BYTE_ARRAY_PRIMITIVE));
                    }
                    lightPacket.send(Protocol1_17_1To1_18.class);
                });
            }
        });
        ((Protocol1_17_1To1_18) this.protocol).cancelClientbound(ClientboundPackets1_18.SET_SIMULATION_DISTANCE);
    }

    public void handleSpawner(int typeId, CompoundTag tag) {
        CompoundTag spawnData;
        CompoundTag entity;
        if (typeId == 8 && (spawnData = (CompoundTag) tag.get("SpawnData")) != null && (entity = (CompoundTag) spawnData.get("entity")) != null) {
            tag.put("SpawnData", entity);
        }
    }
}
