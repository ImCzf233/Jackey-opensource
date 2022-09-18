package com.viaversion.viaversion.protocols.protocol1_15to1_14_4.packets;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.data.RecipeRewriter1_14;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.Protocol1_15To1_14_4;
import com.viaversion.viaversion.rewriter.ItemRewriter;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_15to1_14_4/packets/InventoryPackets.class */
public class InventoryPackets extends ItemRewriter<Protocol1_15To1_14_4> {
    public InventoryPackets(Protocol1_15To1_14_4 protocol) {
        super(protocol);
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    public void registerPackets() {
        registerSetCooldown(ClientboundPackets1_14.COOLDOWN);
        registerWindowItems(ClientboundPackets1_14.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY);
        registerTradeList(ClientboundPackets1_14.TRADE_LIST, Type.FLAT_VAR_INT_ITEM);
        registerSetSlot(ClientboundPackets1_14.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
        registerEntityEquipment(ClientboundPackets1_14.ENTITY_EQUIPMENT, Type.FLAT_VAR_INT_ITEM);
        registerAdvancements(ClientboundPackets1_14.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
        new RecipeRewriter1_14(this.protocol).registerDefaultHandler(ClientboundPackets1_14.DECLARE_RECIPES);
        registerClickWindow(ServerboundPackets1_14.CLICK_WINDOW, Type.FLAT_VAR_INT_ITEM);
        registerCreativeInvAction(ServerboundPackets1_14.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
    }
}
