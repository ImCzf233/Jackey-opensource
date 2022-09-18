package com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.packets;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.Protocol1_16_2To1_16_1;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ServerboundPackets1_16_2;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.data.RecipeRewriter1_16;
import com.viaversion.viaversion.rewriter.ItemRewriter;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_16_2to1_16_1/packets/InventoryPackets.class */
public class InventoryPackets extends ItemRewriter<Protocol1_16_2To1_16_1> {
    public InventoryPackets(Protocol1_16_2To1_16_1 protocol) {
        super(protocol);
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    public void registerPackets() {
        registerSetCooldown(ClientboundPackets1_16.COOLDOWN);
        registerWindowItems(ClientboundPackets1_16.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY);
        registerTradeList(ClientboundPackets1_16.TRADE_LIST, Type.FLAT_VAR_INT_ITEM);
        registerSetSlot(ClientboundPackets1_16.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
        registerEntityEquipmentArray(ClientboundPackets1_16.ENTITY_EQUIPMENT, Type.FLAT_VAR_INT_ITEM);
        registerAdvancements(ClientboundPackets1_16.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_16_2To1_16_1) this.protocol).registerClientbound((Protocol1_16_2To1_16_1) ClientboundPackets1_16.UNLOCK_RECIPES, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.packets.InventoryPackets.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    wrapper.passthrough(Type.VAR_INT);
                    wrapper.passthrough(Type.BOOLEAN);
                    wrapper.passthrough(Type.BOOLEAN);
                    wrapper.passthrough(Type.BOOLEAN);
                    wrapper.passthrough(Type.BOOLEAN);
                    wrapper.write(Type.BOOLEAN, false);
                    wrapper.write(Type.BOOLEAN, false);
                    wrapper.write(Type.BOOLEAN, false);
                    wrapper.write(Type.BOOLEAN, false);
                });
            }
        });
        new RecipeRewriter1_16(this.protocol).registerDefaultHandler(ClientboundPackets1_16.DECLARE_RECIPES);
        registerClickWindow(ServerboundPackets1_16_2.CLICK_WINDOW, Type.FLAT_VAR_INT_ITEM);
        registerCreativeInvAction(ServerboundPackets1_16_2.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_16_2To1_16_1) this.protocol).registerServerbound((Protocol1_16_2To1_16_1) ServerboundPackets1_16_2.EDIT_BOOK, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.packets.InventoryPackets.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    InventoryPackets.this.handleItemToServer((Item) wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
                });
            }
        });
        registerSpawnParticle(ClientboundPackets1_16.SPAWN_PARTICLE, Type.FLAT_VAR_INT_ITEM, Type.DOUBLE);
    }
}
