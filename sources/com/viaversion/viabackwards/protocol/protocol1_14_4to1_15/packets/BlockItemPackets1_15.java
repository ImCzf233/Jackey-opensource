package com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.packets;

import com.viaversion.viabackwards.api.rewriters.ItemRewriter;
import com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.Protocol1_14_4To1_15;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.data.RecipeRewriter1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.types.Chunk1_14Type;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.types.Chunk1_15Type;
import com.viaversion.viaversion.rewriter.BlockRewriter;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_14_4to1_15/packets/BlockItemPackets1_15.class */
public class BlockItemPackets1_15 extends ItemRewriter<Protocol1_14_4To1_15> {
    public BlockItemPackets1_15(Protocol1_14_4To1_15 protocol) {
        super(protocol);
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    protected void registerPackets() {
        BlockRewriter blockRewriter = new BlockRewriter(this.protocol, Type.POSITION1_14);
        new RecipeRewriter1_14(this.protocol).registerDefaultHandler(ClientboundPackets1_15.DECLARE_RECIPES);
        ((Protocol1_14_4To1_15) this.protocol).registerServerbound((Protocol1_14_4To1_15) ServerboundPackets1_14.EDIT_BOOK, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.packets.BlockItemPackets1_15.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    BlockItemPackets1_15.this.handleItemToServer((Item) wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
                });
            }
        });
        registerSetCooldown(ClientboundPackets1_15.COOLDOWN);
        registerWindowItems(ClientboundPackets1_15.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY);
        registerSetSlot(ClientboundPackets1_15.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
        registerTradeList(ClientboundPackets1_15.TRADE_LIST, Type.FLAT_VAR_INT_ITEM);
        registerEntityEquipment(ClientboundPackets1_15.ENTITY_EQUIPMENT, Type.FLAT_VAR_INT_ITEM);
        registerAdvancements(ClientboundPackets1_15.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
        registerClickWindow(ServerboundPackets1_14.CLICK_WINDOW, Type.FLAT_VAR_INT_ITEM);
        registerCreativeInvAction(ServerboundPackets1_14.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
        blockRewriter.registerAcknowledgePlayerDigging(ClientboundPackets1_15.ACKNOWLEDGE_PLAYER_DIGGING);
        blockRewriter.registerBlockAction(ClientboundPackets1_15.BLOCK_ACTION);
        blockRewriter.registerBlockChange(ClientboundPackets1_15.BLOCK_CHANGE);
        blockRewriter.registerMultiBlockChange(ClientboundPackets1_15.MULTI_BLOCK_CHANGE);
        ((Protocol1_14_4To1_15) this.protocol).registerClientbound((Protocol1_14_4To1_15) ClientboundPackets1_15.CHUNK_DATA, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.packets.BlockItemPackets1_15.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.packets.BlockItemPackets1_15.2.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        Chunk chunk = (Chunk) wrapper.read(new Chunk1_15Type());
                        wrapper.write(new Chunk1_14Type(), chunk);
                        if (chunk.isFullChunk()) {
                            int[] biomeData = chunk.getBiomeData();
                            int[] newBiomeData = new int[256];
                            for (int i = 0; i < 4; i++) {
                                for (int j = 0; j < 4; j++) {
                                    int x = j << 2;
                                    int z = i << 2;
                                    int newIndex = (z << 4) | x;
                                    int oldIndex = (i << 2) | j;
                                    int biome = biomeData[oldIndex];
                                    for (int k = 0; k < 4; k++) {
                                        int offX = newIndex + (k << 4);
                                        for (int l = 0; l < 4; l++) {
                                            newBiomeData[offX + l] = biome;
                                        }
                                    }
                                }
                            }
                            chunk.setBiomeData(newBiomeData);
                        }
                        for (int i2 = 0; i2 < chunk.getSections().length; i2++) {
                            ChunkSection section = chunk.getSections()[i2];
                            if (section != null) {
                                for (int j2 = 0; j2 < section.getPaletteSize(); j2++) {
                                    int old = section.getPaletteEntry(j2);
                                    int newId = ((Protocol1_14_4To1_15) BlockItemPackets1_15.this.protocol).getMappingData().getNewBlockStateId(old);
                                    section.setPaletteEntry(j2, newId);
                                }
                            }
                        }
                    }
                });
            }
        });
        blockRewriter.registerEffect(ClientboundPackets1_15.EFFECT, 1010, 2001);
        ((Protocol1_14_4To1_15) this.protocol).registerClientbound((Protocol1_14_4To1_15) ClientboundPackets1_15.SPAWN_PARTICLE, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.packets.BlockItemPackets1_15.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.BOOLEAN);
                map(Type.DOUBLE, Type.FLOAT);
                map(Type.DOUBLE, Type.FLOAT);
                map(Type.DOUBLE, Type.FLOAT);
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.FLOAT);
                map(Type.INT);
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.packets.BlockItemPackets1_15.3.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int id = ((Integer) wrapper.get(Type.INT, 0)).intValue();
                        if (id == 3 || id == 23) {
                            int data = ((Integer) wrapper.passthrough(Type.VAR_INT)).intValue();
                            wrapper.set(Type.VAR_INT, 0, Integer.valueOf(((Protocol1_14_4To1_15) BlockItemPackets1_15.this.protocol).getMappingData().getNewBlockStateId(data)));
                        } else if (id == 32) {
                            Item item = BlockItemPackets1_15.this.handleItemToClient((Item) wrapper.read(Type.FLAT_VAR_INT_ITEM));
                            wrapper.write(Type.FLAT_VAR_INT_ITEM, item);
                        }
                        int mappedId = ((Protocol1_14_4To1_15) BlockItemPackets1_15.this.protocol).getMappingData().getNewParticleId(id);
                        if (id != mappedId) {
                            wrapper.set(Type.INT, 0, Integer.valueOf(mappedId));
                        }
                    }
                });
            }
        });
    }
}
