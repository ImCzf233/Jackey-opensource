package com.viaversion.viaversion.protocols.protocol1_18to1_17_1.packets;

import com.viaversion.viaversion.api.data.ParticleMappings;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.data.RecipeRewriter1_16;
import com.viaversion.viaversion.protocols.protocol1_17_1to1_17.ClientboundPackets1_17_1;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ServerboundPackets1_17;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.Protocol1_18To1_17_1;
import com.viaversion.viaversion.rewriter.ItemRewriter;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_18to1_17_1/packets/InventoryPackets.class */
public final class InventoryPackets extends ItemRewriter<Protocol1_18To1_17_1> {
    public InventoryPackets(Protocol1_18To1_17_1 protocol) {
        super(protocol);
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    public void registerPackets() {
        registerSetCooldown(ClientboundPackets1_17_1.COOLDOWN);
        registerWindowItems1_17_1(ClientboundPackets1_17_1.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT, Type.FLAT_VAR_INT_ITEM);
        registerTradeList(ClientboundPackets1_17_1.TRADE_LIST, Type.FLAT_VAR_INT_ITEM);
        registerSetSlot1_17_1(ClientboundPackets1_17_1.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
        registerAdvancements(ClientboundPackets1_17_1.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
        registerEntityEquipmentArray(ClientboundPackets1_17_1.ENTITY_EQUIPMENT, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_18To1_17_1) this.protocol).registerClientbound((Protocol1_18To1_17_1) ClientboundPackets1_17_1.EFFECT, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_18to1_17_1.packets.InventoryPackets.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.INT);
                map(Type.POSITION1_14);
                map(Type.INT);
                handler(wrapper -> {
                    int id = ((Integer) wrapper.get(Type.INT, 0)).intValue();
                    int data = ((Integer) wrapper.get(Type.INT, 1)).intValue();
                    if (id == 1010) {
                        wrapper.set(Type.INT, 1, Integer.valueOf(((Protocol1_18To1_17_1) InventoryPackets.this.protocol).getMappingData().getNewItemId(data)));
                    }
                });
            }
        });
        ((Protocol1_18To1_17_1) this.protocol).registerClientbound((Protocol1_18To1_17_1) ClientboundPackets1_17_1.SPAWN_PARTICLE, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_18to1_17_1.packets.InventoryPackets.2
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
                    if (id == 2) {
                        wrapper.set(Type.INT, 0, 3);
                        wrapper.write(Type.VAR_INT, 7754);
                    } else if (id != 3) {
                        ParticleMappings mappings = ((Protocol1_18To1_17_1) InventoryPackets.this.protocol).getMappingData().getParticleMappings();
                        if (mappings.isBlockParticle(id)) {
                            int data = ((Integer) wrapper.passthrough(Type.VAR_INT)).intValue();
                            wrapper.set(Type.VAR_INT, 0, Integer.valueOf(((Protocol1_18To1_17_1) InventoryPackets.this.protocol).getMappingData().getNewBlockStateId(data)));
                        } else if (mappings.isItemParticle(id)) {
                            InventoryPackets.this.handleItemToClient((Item) wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
                        }
                        int newId = ((Protocol1_18To1_17_1) InventoryPackets.this.protocol).getMappingData().getNewParticleId(id);
                        if (newId != id) {
                            wrapper.set(Type.INT, 0, Integer.valueOf(newId));
                        }
                    } else {
                        wrapper.write(Type.VAR_INT, 7786);
                    }
                });
            }
        });
        new RecipeRewriter1_16(this.protocol).registerDefaultHandler(ClientboundPackets1_17_1.DECLARE_RECIPES);
        registerClickWindow1_17_1(ServerboundPackets1_17.CLICK_WINDOW, Type.FLAT_VAR_INT_ITEM);
        registerCreativeInvAction(ServerboundPackets1_17.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
    }
}
