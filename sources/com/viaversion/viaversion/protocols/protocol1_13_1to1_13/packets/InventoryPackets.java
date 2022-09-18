package com.viaversion.viaversion.protocols.protocol1_13_1to1_13.packets;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.Protocol1_13_1To1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.RecipeRewriter1_13_2;
import com.viaversion.viaversion.rewriter.ItemRewriter;
import com.viaversion.viaversion.rewriter.RecipeRewriter;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_13_1to1_13/packets/InventoryPackets.class */
public class InventoryPackets extends ItemRewriter<Protocol1_13_1To1_13> {
    public InventoryPackets(Protocol1_13_1To1_13 protocol) {
        super(protocol);
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    public void registerPackets() {
        registerSetSlot(ClientboundPackets1_13.SET_SLOT, Type.FLAT_ITEM);
        registerWindowItems(ClientboundPackets1_13.WINDOW_ITEMS, Type.FLAT_ITEM_ARRAY);
        registerAdvancements(ClientboundPackets1_13.ADVANCEMENTS, Type.FLAT_ITEM);
        registerSetCooldown(ClientboundPackets1_13.COOLDOWN);
        ((Protocol1_13_1To1_13) this.protocol).registerClientbound((Protocol1_13_1To1_13) ClientboundPackets1_13.PLUGIN_MESSAGE, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13_1to1_13.packets.InventoryPackets.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_13_1to1_13.packets.InventoryPackets.1.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        String channel = (String) wrapper.get(Type.STRING, 0);
                        if (channel.equals("minecraft:trader_list") || channel.equals("trader_list")) {
                            wrapper.passthrough(Type.INT);
                            int size = ((Short) wrapper.passthrough(Type.UNSIGNED_BYTE)).shortValue();
                            for (int i = 0; i < size; i++) {
                                InventoryPackets.this.handleItemToClient((Item) wrapper.passthrough(Type.FLAT_ITEM));
                                InventoryPackets.this.handleItemToClient((Item) wrapper.passthrough(Type.FLAT_ITEM));
                                boolean secondItem = ((Boolean) wrapper.passthrough(Type.BOOLEAN)).booleanValue();
                                if (secondItem) {
                                    InventoryPackets.this.handleItemToClient((Item) wrapper.passthrough(Type.FLAT_ITEM));
                                }
                                wrapper.passthrough(Type.BOOLEAN);
                                wrapper.passthrough(Type.INT);
                                wrapper.passthrough(Type.INT);
                            }
                        }
                    }
                });
            }
        });
        registerEntityEquipment(ClientboundPackets1_13.ENTITY_EQUIPMENT, Type.FLAT_ITEM);
        final RecipeRewriter recipeRewriter = new RecipeRewriter1_13_2(this.protocol);
        ((Protocol1_13_1To1_13) this.protocol).registerClientbound((Protocol1_13_1To1_13) ClientboundPackets1_13.DECLARE_RECIPES, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13_1to1_13.packets.InventoryPackets.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                RecipeRewriter recipeRewriter2 = recipeRewriter;
                handler(wrapper -> {
                    int size = ((Integer) wrapper.passthrough(Type.VAR_INT)).intValue();
                    for (int i = 0; i < size; i++) {
                        String str = (String) wrapper.passthrough(Type.STRING);
                        String type = ((String) wrapper.passthrough(Type.STRING)).replace("minecraft:", "");
                        recipeRewriter2.handle(wrapper, type);
                    }
                });
            }
        });
        registerClickWindow(ServerboundPackets1_13.CLICK_WINDOW, Type.FLAT_ITEM);
        registerCreativeInvAction(ServerboundPackets1_13.CREATIVE_INVENTORY_ACTION, Type.FLAT_ITEM);
        registerSpawnParticle(ClientboundPackets1_13.SPAWN_PARTICLE, Type.FLAT_ITEM, Type.FLOAT);
    }
}
