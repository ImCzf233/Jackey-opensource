package de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonParser;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_8.ServerboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ServerboundPackets1_9;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.items.ItemRewriter;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.Windows;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/protocol/protocol1_8to1_9/packets/InventoryPackets.class */
public class InventoryPackets {
    public static void register(Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8> protocol) {
        protocol.registerClientbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ClientboundPackets1_9.CLOSE_WINDOW, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.InventoryPackets.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                handler(packetWrapper -> {
                    short windowsId = ((Short) packetWrapper.get(Type.UNSIGNED_BYTE, 0)).shortValue();
                    ((Windows) packetWrapper.user().get(Windows.class)).remove(windowsId);
                });
            }
        });
        protocol.registerClientbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ClientboundPackets1_9.OPEN_WINDOW, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.InventoryPackets.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                map(Type.STRING);
                map(Type.COMPONENT);
                map(Type.UNSIGNED_BYTE);
                handler(packetWrapper -> {
                    String type = (String) packetWrapper.get(Type.STRING, 0);
                    if (type.equals("EntityHorse")) {
                        packetWrapper.passthrough(Type.INT);
                    }
                });
                handler(packetWrapper2 -> {
                    short windowId = ((Short) packetWrapper2.get(Type.UNSIGNED_BYTE, 0)).shortValue();
                    String windowType = (String) packetWrapper2.get(Type.STRING, 0);
                    ((Windows) packetWrapper2.user().get(Windows.class)).put(windowId, windowType);
                });
                handler(packetWrapper3 -> {
                    String type = (String) packetWrapper3.get(Type.STRING, 0);
                    if (type.equalsIgnoreCase("minecraft:shulker_box")) {
                        packetWrapper3.set(Type.STRING, 0, "minecraft:container");
                    }
                    String name = ((JsonElement) packetWrapper3.get(Type.COMPONENT, 0)).toString();
                    if (name.equalsIgnoreCase("{\"translate\":\"container.shulkerBox\"}")) {
                        packetWrapper3.set(Type.COMPONENT, 0, JsonParser.parseString("{\"text\":\"Shulker Box\"}"));
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ClientboundPackets1_9.WINDOW_ITEMS, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.InventoryPackets.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                handler(packetWrapper -> {
                    short windowId = ((Short) packetWrapper.get(Type.UNSIGNED_BYTE, 0)).shortValue();
                    Item[] items = (Item[]) packetWrapper.read(Type.ITEM_ARRAY);
                    for (int i = 0; i < items.length; i++) {
                        items[i] = ItemRewriter.toClient(items[i]);
                    }
                    if (windowId == 0 && items.length == 46) {
                        items = new Item[45];
                        System.arraycopy(items, 0, items, 0, 45);
                    } else {
                        String type = ((Windows) packetWrapper.user().get(Windows.class)).get(windowId);
                        if (type != null && type.equalsIgnoreCase("minecraft:brewing_stand")) {
                            System.arraycopy(items, 0, ((Windows) packetWrapper.user().get(Windows.class)).getBrewingItems(windowId), 0, 4);
                            Windows.updateBrewingStand(packetWrapper.user(), items[4], windowId);
                            items = new Item[items.length - 1];
                            System.arraycopy(items, 0, items, 0, 4);
                            System.arraycopy(items, 5, items, 4, items.length - 5);
                        }
                    }
                    packetWrapper.write(Type.ITEM_ARRAY, items);
                });
            }
        });
        protocol.registerClientbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ClientboundPackets1_9.SET_SLOT, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.InventoryPackets.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                map(Type.SHORT);
                map(Type.ITEM);
                handler(packetWrapper -> {
                    packetWrapper.set(Type.ITEM, 0, ItemRewriter.toClient((Item) packetWrapper.get(Type.ITEM, 0)));
                    byte windowId = ((Short) packetWrapper.get(Type.UNSIGNED_BYTE, 0)).byteValue();
                    short slot = ((Short) packetWrapper.get(Type.SHORT, 0)).shortValue();
                    if (windowId == 0 && slot == 45) {
                        packetWrapper.cancel();
                        return;
                    }
                    String type = ((Windows) packetWrapper.user().get(Windows.class)).get(windowId);
                    if (type != null && type.equalsIgnoreCase("minecraft:brewing_stand")) {
                        if (slot > 4) {
                            packetWrapper.set(Type.SHORT, 0, Short.valueOf((short) (slot - 1)));
                        } else if (slot == 4) {
                            packetWrapper.cancel();
                            Windows.updateBrewingStand(packetWrapper.user(), (Item) packetWrapper.get(Type.ITEM, 0), windowId);
                        } else {
                            ((Windows) packetWrapper.user().get(Windows.class)).getBrewingItems(windowId)[slot] = (Item) packetWrapper.get(Type.ITEM, 0);
                        }
                    }
                });
            }
        });
        protocol.registerServerbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ServerboundPackets1_8.CLOSE_WINDOW, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.InventoryPackets.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                handler(packetWrapper -> {
                    short windowsId = ((Short) packetWrapper.get(Type.UNSIGNED_BYTE, 0)).shortValue();
                    ((Windows) packetWrapper.user().get(Windows.class)).remove(windowsId);
                });
            }
        });
        protocol.registerServerbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ServerboundPackets1_8.CLICK_WINDOW, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.InventoryPackets.6
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                map(Type.SHORT);
                map(Type.BYTE);
                map(Type.SHORT);
                map(Type.BYTE, Type.VAR_INT);
                map(Type.ITEM);
                handler(packetWrapper -> {
                    packetWrapper.set(Type.ITEM, 0, ItemRewriter.toServer((Item) packetWrapper.get(Type.ITEM, 0)));
                });
                handler(packetWrapper2 -> {
                    short slot;
                    short windowId = ((Short) packetWrapper2.get(Type.UNSIGNED_BYTE, 0)).shortValue();
                    Windows windows = (Windows) packetWrapper2.user().get(Windows.class);
                    String type = windows.get(windowId);
                    if (type != null && type.equalsIgnoreCase("minecraft:brewing_stand") && (slot = ((Short) packetWrapper2.get(Type.SHORT, 0)).shortValue()) > 3) {
                        packetWrapper2.set(Type.SHORT, 0, Short.valueOf((short) (slot + 1)));
                    }
                });
            }
        });
        protocol.registerServerbound((Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>) ServerboundPackets1_8.CREATIVE_INVENTORY_ACTION, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.InventoryPackets.7
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.SHORT);
                map(Type.ITEM);
                handler(packetWrapper -> {
                    packetWrapper.set(Type.ITEM, 0, ItemRewriter.toServer((Item) packetWrapper.get(Type.ITEM, 0)));
                });
            }
        });
    }
}
