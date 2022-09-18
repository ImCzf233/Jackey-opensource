package com.viaversion.viaversion.protocols.protocol1_11to1_10.packets;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_11to1_10.EntityIdRewriter;
import com.viaversion.viaversion.protocols.protocol1_11to1_10.Protocol1_11To1_10;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;
import com.viaversion.viaversion.rewriter.ItemRewriter;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_11to1_10/packets/InventoryPackets.class */
public class InventoryPackets extends ItemRewriter<Protocol1_11To1_10> {
    public InventoryPackets(Protocol1_11To1_10 protocol) {
        super(protocol);
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    public void registerPackets() {
        registerSetSlot(ClientboundPackets1_9_3.SET_SLOT, Type.ITEM);
        registerWindowItems(ClientboundPackets1_9_3.WINDOW_ITEMS, Type.ITEM_ARRAY);
        registerEntityEquipment(ClientboundPackets1_9_3.ENTITY_EQUIPMENT, Type.ITEM);
        ((Protocol1_11To1_10) this.protocol).registerClientbound((Protocol1_11To1_10) ClientboundPackets1_9_3.PLUGIN_MESSAGE, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_11to1_10.packets.InventoryPackets.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_11to1_10.packets.InventoryPackets.1.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        if (((String) wrapper.get(Type.STRING, 0)).equalsIgnoreCase("MC|TrList")) {
                            wrapper.passthrough(Type.INT);
                            int size = ((Short) wrapper.passthrough(Type.UNSIGNED_BYTE)).shortValue();
                            for (int i = 0; i < size; i++) {
                                EntityIdRewriter.toClientItem((Item) wrapper.passthrough(Type.ITEM));
                                EntityIdRewriter.toClientItem((Item) wrapper.passthrough(Type.ITEM));
                                boolean secondItem = ((Boolean) wrapper.passthrough(Type.BOOLEAN)).booleanValue();
                                if (secondItem) {
                                    EntityIdRewriter.toClientItem((Item) wrapper.passthrough(Type.ITEM));
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
        registerClickWindow(ServerboundPackets1_9_3.CLICK_WINDOW, Type.ITEM);
        registerCreativeInvAction(ServerboundPackets1_9_3.CREATIVE_INVENTORY_ACTION, Type.ITEM);
    }

    @Override // com.viaversion.viaversion.rewriter.ItemRewriter, com.viaversion.viaversion.api.rewriter.ItemRewriter
    public Item handleItemToClient(Item item) {
        EntityIdRewriter.toClientItem(item);
        return item;
    }

    @Override // com.viaversion.viaversion.rewriter.ItemRewriter, com.viaversion.viaversion.api.rewriter.ItemRewriter
    public Item handleItemToServer(Item item) {
        EntityIdRewriter.toServerItem(item);
        if (item == null) {
            return null;
        }
        boolean newItem = item.identifier() >= 218 && item.identifier() <= 234;
        if (newItem | (item.identifier() == 449 || item.identifier() == 450)) {
            item.setIdentifier(1);
            item.setData((short) 0);
        }
        return item;
    }
}
