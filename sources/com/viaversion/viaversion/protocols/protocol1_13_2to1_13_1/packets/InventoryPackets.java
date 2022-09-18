package com.viaversion.viaversion.protocols.protocol1_13_2to1_13_1.packets;

import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_13_2to1_13_1/packets/InventoryPackets.class */
public class InventoryPackets {
    public static void register(Protocol protocol) {
        protocol.registerClientbound((Protocol) ClientboundPackets1_13.SET_SLOT, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13_2to1_13_1.packets.InventoryPackets.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                map(Type.SHORT);
                map(Type.FLAT_ITEM, Type.FLAT_VAR_INT_ITEM);
            }
        });
        protocol.registerClientbound((Protocol) ClientboundPackets1_13.WINDOW_ITEMS, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13_2to1_13_1.packets.InventoryPackets.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                map(Type.FLAT_ITEM_ARRAY, Type.FLAT_VAR_INT_ITEM_ARRAY);
            }
        });
        protocol.registerClientbound((Protocol) ClientboundPackets1_13.PLUGIN_MESSAGE, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13_2to1_13_1.packets.InventoryPackets.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_13_2to1_13_1.packets.InventoryPackets.3.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        String channel = (String) wrapper.get(Type.STRING, 0);
                        if (channel.equals("minecraft:trader_list") || channel.equals("trader_list")) {
                            wrapper.passthrough(Type.INT);
                            int size = ((Short) wrapper.passthrough(Type.UNSIGNED_BYTE)).shortValue();
                            for (int i = 0; i < size; i++) {
                                wrapper.write(Type.FLAT_VAR_INT_ITEM, wrapper.read(Type.FLAT_ITEM));
                                wrapper.write(Type.FLAT_VAR_INT_ITEM, wrapper.read(Type.FLAT_ITEM));
                                boolean secondItem = ((Boolean) wrapper.passthrough(Type.BOOLEAN)).booleanValue();
                                if (secondItem) {
                                    wrapper.write(Type.FLAT_VAR_INT_ITEM, wrapper.read(Type.FLAT_ITEM));
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
        protocol.registerClientbound((Protocol) ClientboundPackets1_13.ENTITY_EQUIPMENT, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13_2to1_13_1.packets.InventoryPackets.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.VAR_INT);
                map(Type.FLAT_ITEM, Type.FLAT_VAR_INT_ITEM);
            }
        });
        protocol.registerClientbound((Protocol) ClientboundPackets1_13.DECLARE_RECIPES, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13_2to1_13_1.packets.InventoryPackets.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_13_2to1_13_1.packets.InventoryPackets.5.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        int recipesNo = ((Integer) wrapper.passthrough(Type.VAR_INT)).intValue();
                        for (int i = 0; i < recipesNo; i++) {
                            wrapper.passthrough(Type.STRING);
                            String type = (String) wrapper.passthrough(Type.STRING);
                            if (type.equals("crafting_shapeless")) {
                                wrapper.passthrough(Type.STRING);
                                int ingredientsNo = ((Integer) wrapper.passthrough(Type.VAR_INT)).intValue();
                                for (int i1 = 0; i1 < ingredientsNo; i1++) {
                                    wrapper.write(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT, wrapper.read(Type.FLAT_ITEM_ARRAY_VAR_INT));
                                }
                                wrapper.write(Type.FLAT_VAR_INT_ITEM, wrapper.read(Type.FLAT_ITEM));
                            } else if (type.equals("crafting_shaped")) {
                                int ingredientsNo2 = ((Integer) wrapper.passthrough(Type.VAR_INT)).intValue() * ((Integer) wrapper.passthrough(Type.VAR_INT)).intValue();
                                wrapper.passthrough(Type.STRING);
                                for (int i12 = 0; i12 < ingredientsNo2; i12++) {
                                    wrapper.write(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT, wrapper.read(Type.FLAT_ITEM_ARRAY_VAR_INT));
                                }
                                wrapper.write(Type.FLAT_VAR_INT_ITEM, wrapper.read(Type.FLAT_ITEM));
                            } else if (type.equals("smelting")) {
                                wrapper.passthrough(Type.STRING);
                                wrapper.write(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT, wrapper.read(Type.FLAT_ITEM_ARRAY_VAR_INT));
                                wrapper.write(Type.FLAT_VAR_INT_ITEM, wrapper.read(Type.FLAT_ITEM));
                                wrapper.passthrough(Type.FLOAT);
                                wrapper.passthrough(Type.VAR_INT);
                            }
                        }
                    }
                });
            }
        });
        protocol.registerServerbound((Protocol) ServerboundPackets1_13.CLICK_WINDOW, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13_2to1_13_1.packets.InventoryPackets.6
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                map(Type.SHORT);
                map(Type.BYTE);
                map(Type.SHORT);
                map(Type.VAR_INT);
                map(Type.FLAT_VAR_INT_ITEM, Type.FLAT_ITEM);
            }
        });
        protocol.registerServerbound((Protocol) ServerboundPackets1_13.CREATIVE_INVENTORY_ACTION, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_13_2to1_13_1.packets.InventoryPackets.7
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.SHORT);
                map(Type.FLAT_VAR_INT_ITEM, Type.FLAT_ITEM);
            }
        });
    }
}
