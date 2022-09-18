package com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.packets;

import com.viaversion.viabackwards.api.rewriters.ItemRewriter;
import com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.Protocol1_16_1To1_16_2;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord1_8;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.types.Chunk1_16_2Type;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ServerboundPackets1_16;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.data.RecipeRewriter1_16;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.types.Chunk1_16Type;
import com.viaversion.viaversion.rewriter.BlockRewriter;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_16_1to1_16_2/packets/BlockItemPackets1_16_2.class */
public class BlockItemPackets1_16_2 extends ItemRewriter<Protocol1_16_1To1_16_2> {
    public BlockItemPackets1_16_2(Protocol1_16_1To1_16_2 protocol) {
        super(protocol);
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    protected void registerPackets() {
        BlockRewriter blockRewriter = new BlockRewriter(this.protocol, Type.POSITION1_14);
        new RecipeRewriter1_16(this.protocol).registerDefaultHandler(ClientboundPackets1_16_2.DECLARE_RECIPES);
        registerSetCooldown(ClientboundPackets1_16_2.COOLDOWN);
        registerWindowItems(ClientboundPackets1_16_2.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY);
        registerSetSlot(ClientboundPackets1_16_2.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
        registerEntityEquipmentArray(ClientboundPackets1_16_2.ENTITY_EQUIPMENT, Type.FLAT_VAR_INT_ITEM);
        registerTradeList(ClientboundPackets1_16_2.TRADE_LIST, Type.FLAT_VAR_INT_ITEM);
        registerAdvancements(ClientboundPackets1_16_2.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_16_1To1_16_2) this.protocol).registerClientbound((Protocol1_16_1To1_16_2) ClientboundPackets1_16_2.UNLOCK_RECIPES, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.packets.BlockItemPackets1_16_2.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    wrapper.passthrough(Type.VAR_INT);
                    wrapper.passthrough(Type.BOOLEAN);
                    wrapper.passthrough(Type.BOOLEAN);
                    wrapper.passthrough(Type.BOOLEAN);
                    wrapper.passthrough(Type.BOOLEAN);
                    wrapper.read(Type.BOOLEAN);
                    wrapper.read(Type.BOOLEAN);
                    wrapper.read(Type.BOOLEAN);
                    wrapper.read(Type.BOOLEAN);
                });
            }
        });
        blockRewriter.registerAcknowledgePlayerDigging(ClientboundPackets1_16_2.ACKNOWLEDGE_PLAYER_DIGGING);
        blockRewriter.registerBlockAction(ClientboundPackets1_16_2.BLOCK_ACTION);
        blockRewriter.registerBlockChange(ClientboundPackets1_16_2.BLOCK_CHANGE);
        ((Protocol1_16_1To1_16_2) this.protocol).registerClientbound((Protocol1_16_1To1_16_2) ClientboundPackets1_16_2.CHUNK_DATA, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.packets.BlockItemPackets1_16_2.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    Chunk chunk = (Chunk) wrapper.read(new Chunk1_16_2Type());
                    wrapper.write(new Chunk1_16Type(), chunk);
                    chunk.setIgnoreOldLightData(true);
                    for (int i = 0; i < chunk.getSections().length; i++) {
                        ChunkSection section = chunk.getSections()[i];
                        if (section != null) {
                            for (int j = 0; j < section.getPaletteSize(); j++) {
                                int old = section.getPaletteEntry(j);
                                section.setPaletteEntry(j, ((Protocol1_16_1To1_16_2) BlockItemPackets1_16_2.this.protocol).getMappingData().getNewBlockStateId(old));
                            }
                        }
                    }
                    for (CompoundTag blockEntity : chunk.getBlockEntities()) {
                        if (blockEntity != null) {
                            BlockItemPackets1_16_2.this.handleBlockEntity(blockEntity);
                        }
                    }
                });
            }
        });
        ((Protocol1_16_1To1_16_2) this.protocol).registerClientbound((Protocol1_16_1To1_16_2) ClientboundPackets1_16_2.BLOCK_ENTITY_DATA, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.packets.BlockItemPackets1_16_2.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.POSITION1_14);
                map(Type.UNSIGNED_BYTE);
                handler(wrapper -> {
                    BlockItemPackets1_16_2.this.handleBlockEntity((CompoundTag) wrapper.passthrough(Type.NBT));
                });
            }
        });
        ((Protocol1_16_1To1_16_2) this.protocol).registerClientbound((Protocol1_16_1To1_16_2) ClientboundPackets1_16_2.MULTI_BLOCK_CHANGE, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.packets.BlockItemPackets1_16_2.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    long chunkPosition = ((Long) wrapper.read(Type.LONG)).longValue();
                    wrapper.read(Type.BOOLEAN);
                    int chunkX = (int) (chunkPosition >> 42);
                    int chunkY = (int) ((chunkPosition << 44) >> 44);
                    int chunkZ = (int) ((chunkPosition << 22) >> 42);
                    wrapper.write(Type.INT, Integer.valueOf(chunkX));
                    wrapper.write(Type.INT, Integer.valueOf(chunkZ));
                    BlockChangeRecord[] blockChangeRecord = (BlockChangeRecord[]) wrapper.read(Type.VAR_LONG_BLOCK_CHANGE_RECORD_ARRAY);
                    wrapper.write(Type.BLOCK_CHANGE_RECORD_ARRAY, blockChangeRecord);
                    for (int i = 0; i < blockChangeRecord.length; i++) {
                        BlockChangeRecord record = blockChangeRecord[i];
                        int blockId = ((Protocol1_16_1To1_16_2) BlockItemPackets1_16_2.this.protocol).getMappingData().getNewBlockStateId(record.getBlockId());
                        blockChangeRecord[i] = new BlockChangeRecord1_8(record.getSectionX(), record.getY(chunkY), record.getSectionZ(), blockId);
                    }
                });
            }
        });
        blockRewriter.registerEffect(ClientboundPackets1_16_2.EFFECT, 1010, 2001);
        registerSpawnParticle(ClientboundPackets1_16_2.SPAWN_PARTICLE, Type.FLAT_VAR_INT_ITEM, Type.DOUBLE);
        registerClickWindow(ServerboundPackets1_16.CLICK_WINDOW, Type.FLAT_VAR_INT_ITEM);
        registerCreativeInvAction(ServerboundPackets1_16.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_16_1To1_16_2) this.protocol).registerServerbound((Protocol1_16_1To1_16_2) ServerboundPackets1_16.EDIT_BOOK, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.packets.BlockItemPackets1_16_2.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    BlockItemPackets1_16_2.this.handleItemToServer((Item) wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
                });
            }
        });
    }

    public void handleBlockEntity(CompoundTag tag) {
        CompoundTag properties;
        ListTag textures;
        StringTag idTag = (StringTag) tag.get("id");
        if (idTag != null && idTag.getValue().equals("minecraft:skull")) {
            Tag skullOwnerTag = tag.get("SkullOwner");
            if (!(skullOwnerTag instanceof CompoundTag)) {
                return;
            }
            CompoundTag skullOwnerCompoundTag = (CompoundTag) skullOwnerTag;
            if (!skullOwnerCompoundTag.contains("Id") || (properties = (CompoundTag) skullOwnerCompoundTag.get("Properties")) == null || (textures = (ListTag) properties.get("textures")) == null) {
                return;
            }
            CompoundTag first = textures.size() > 0 ? (CompoundTag) textures.get(0) : null;
            if (first == null) {
                return;
            }
            int hashCode = first.get("Value").getValue().hashCode();
            int[] uuidIntArray = {hashCode, 0, 0, 0};
            skullOwnerCompoundTag.put("Id", new IntArrayTag(uuidIntArray));
        }
    }
}
