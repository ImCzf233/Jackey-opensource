package com.viaversion.viaversion.protocols.protocol1_12to1_11_1.packets;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.Protocol1_12To1_11_1;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.ServerboundPackets1_12;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.providers.InventoryQuickMoveProvider;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import com.viaversion.viaversion.rewriter.ItemRewriter;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_12to1_11_1/packets/InventoryPackets.class */
public class InventoryPackets extends ItemRewriter<Protocol1_12To1_11_1> {
    public InventoryPackets(Protocol1_12To1_11_1 protocol) {
        super(protocol);
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    public void registerPackets() {
        registerSetSlot(ClientboundPackets1_9_3.SET_SLOT, Type.ITEM);
        registerWindowItems(ClientboundPackets1_9_3.WINDOW_ITEMS, Type.ITEM_ARRAY);
        registerEntityEquipment(ClientboundPackets1_9_3.ENTITY_EQUIPMENT, Type.ITEM);
        ((Protocol1_12To1_11_1) this.protocol).registerClientbound((Protocol1_12To1_11_1) ClientboundPackets1_9_3.PLUGIN_MESSAGE, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_12to1_11_1.packets.InventoryPackets.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_12to1_11_1.packets.InventoryPackets.1.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        if (((String) wrapper.get(Type.STRING, 0)).equalsIgnoreCase("MC|TrList")) {
                            wrapper.passthrough(Type.INT);
                            int size = ((Short) wrapper.passthrough(Type.UNSIGNED_BYTE)).shortValue();
                            for (int i = 0; i < size; i++) {
                                InventoryPackets.this.handleItemToClient((Item) wrapper.passthrough(Type.ITEM));
                                InventoryPackets.this.handleItemToClient((Item) wrapper.passthrough(Type.ITEM));
                                boolean secondItem = ((Boolean) wrapper.passthrough(Type.BOOLEAN)).booleanValue();
                                if (secondItem) {
                                    InventoryPackets.this.handleItemToClient((Item) wrapper.passthrough(Type.ITEM));
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
        ((Protocol1_12To1_11_1) this.protocol).registerServerbound((Protocol1_12To1_11_1) ServerboundPackets1_12.CLICK_WINDOW, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_12to1_11_1.packets.InventoryPackets.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                map(Type.SHORT);
                map(Type.BYTE);
                map(Type.SHORT);
                map(Type.VAR_INT);
                map(Type.ITEM);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_12to1_11_1.packets.InventoryPackets.2.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        Item item = (Item) wrapper.get(Type.ITEM, 0);
                        if (!Via.getConfig().is1_12QuickMoveActionFix()) {
                            InventoryPackets.this.handleItemToServer(item);
                            return;
                        }
                        byte button = ((Byte) wrapper.get(Type.BYTE, 0)).byteValue();
                        int mode = ((Integer) wrapper.get(Type.VAR_INT, 0)).intValue();
                        if (mode == 1 && button == 0 && item == null) {
                            short windowId = ((Short) wrapper.get(Type.UNSIGNED_BYTE, 0)).shortValue();
                            short slotId = ((Short) wrapper.get(Type.SHORT, 0)).shortValue();
                            short actionId = ((Short) wrapper.get(Type.SHORT, 1)).shortValue();
                            InventoryQuickMoveProvider provider = (InventoryQuickMoveProvider) Via.getManager().getProviders().get(InventoryQuickMoveProvider.class);
                            boolean succeed = provider.registerQuickMoveAction(windowId, slotId, actionId, wrapper.user());
                            if (succeed) {
                                wrapper.cancel();
                                return;
                            }
                            return;
                        }
                        InventoryPackets.this.handleItemToServer(item);
                    }
                });
            }
        });
        registerCreativeInvAction(ServerboundPackets1_12.CREATIVE_INVENTORY_ACTION, Type.ITEM);
    }

    @Override // com.viaversion.viaversion.rewriter.ItemRewriter, com.viaversion.viaversion.api.rewriter.ItemRewriter
    public Item handleItemToServer(Item item) {
        if (item == null) {
            return null;
        }
        if (item.identifier() == 355) {
            item.setData((short) 0);
        }
        boolean newItem = item.identifier() >= 235 && item.identifier() <= 252;
        if (newItem | (item.identifier() == 453)) {
            item.setIdentifier(1);
            item.setData((short) 0);
        }
        return item;
    }

    @Override // com.viaversion.viaversion.rewriter.ItemRewriter, com.viaversion.viaversion.api.rewriter.ItemRewriter
    public Item handleItemToClient(Item item) {
        if (item == null) {
            return null;
        }
        if (item.identifier() == 355) {
            item.setData((short) 14);
        }
        return item;
    }
}
