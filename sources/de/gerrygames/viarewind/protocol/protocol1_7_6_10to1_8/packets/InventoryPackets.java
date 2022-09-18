package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10TO1_8;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.ServerboundPackets1_7;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.items.ItemRewriter;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.GameProfileStorage;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.Windows;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
import de.gerrygames.viarewind.utils.ChatUtil;
import java.util.UUID;

/* loaded from: Jackey Client b2.jar:de/gerrygames/viarewind/protocol/protocol1_7_6_10to1_8/packets/InventoryPackets.class */
public class InventoryPackets {
    public static void register(Protocol1_7_6_10TO1_8 protocol) {
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.OPEN_WINDOW, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.InventoryPackets.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(packetWrapper -> {
                    short windowId = ((Short) packetWrapper.passthrough(Type.UNSIGNED_BYTE)).shortValue();
                    String windowType = (String) packetWrapper.read(Type.STRING);
                    short windowtypeId = (short) Windows.getInventoryType(windowType);
                    ((Windows) packetWrapper.user().get(Windows.class)).types.put(Short.valueOf(windowId), Short.valueOf(windowtypeId));
                    packetWrapper.write(Type.UNSIGNED_BYTE, Short.valueOf(windowtypeId));
                    JsonElement titleComponent = (JsonElement) packetWrapper.read(Type.COMPONENT);
                    String title = ChatUtil.removeUnusedColor(ChatUtil.jsonToLegacy(titleComponent), '8');
                    if (title.length() > 32) {
                        title = title.substring(0, 32);
                    }
                    packetWrapper.write(Type.STRING, title);
                    packetWrapper.passthrough(Type.UNSIGNED_BYTE);
                    packetWrapper.write(Type.BOOLEAN, true);
                    if (windowtypeId == 11) {
                        packetWrapper.passthrough(Type.INT);
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.CLOSE_WINDOW, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.InventoryPackets.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                handler(packetWrapper -> {
                    short windowsId = ((Short) packetWrapper.get(Type.UNSIGNED_BYTE, 0)).shortValue();
                    ((Windows) packetWrapper.user().get(Windows.class)).remove(windowsId);
                });
            }
        });
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.SET_SLOT, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.InventoryPackets.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(packetWrapper -> {
                    short windowId = ((Short) packetWrapper.read(Type.UNSIGNED_BYTE)).shortValue();
                    short windowType = ((Windows) packetWrapper.user().get(Windows.class)).get(windowId);
                    packetWrapper.write(Type.UNSIGNED_BYTE, Short.valueOf(windowId));
                    short slot = ((Short) packetWrapper.read(Type.SHORT)).shortValue();
                    if (windowType == 4) {
                        if (slot == 1) {
                            packetWrapper.cancel();
                            return;
                        } else if (slot >= 2) {
                            slot = (short) (slot - 1);
                        }
                    }
                    packetWrapper.write(Type.SHORT, Short.valueOf(slot));
                });
                map(Type.ITEM, Types1_7_6_10.COMPRESSED_NBT_ITEM);
                handler(packetWrapper2 -> {
                    Item item = (Item) packetWrapper2.get(Types1_7_6_10.COMPRESSED_NBT_ITEM, 0);
                    ItemRewriter.toClient(item);
                    packetWrapper2.set(Types1_7_6_10.COMPRESSED_NBT_ITEM, 0, item);
                });
                handler(packetWrapper3 -> {
                    short slot;
                    short windowId = ((Short) packetWrapper3.get(Type.UNSIGNED_BYTE, 0)).shortValue();
                    if (windowId == 0 && (slot = ((Short) packetWrapper3.get(Type.SHORT, 0)).shortValue()) >= 5 && slot <= 8) {
                        Item item = (Item) packetWrapper3.get(Types1_7_6_10.COMPRESSED_NBT_ITEM, 0);
                        EntityTracker tracker = (EntityTracker) packetWrapper3.user().get(EntityTracker.class);
                        UUID uuid = packetWrapper3.user().getProtocolInfo().getUuid();
                        Item[] equipment = tracker.getPlayerEquipment(uuid);
                        if (equipment == null) {
                            Item[] itemArr = new Item[5];
                            equipment = itemArr;
                            tracker.setPlayerEquipment(uuid, itemArr);
                        }
                        equipment[9 - slot] = item;
                        if (tracker.getGamemode() == 3) {
                            packetWrapper3.cancel();
                        }
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.WINDOW_ITEMS, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.InventoryPackets.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(packetWrapper -> {
                    short windowId = ((Short) packetWrapper.read(Type.UNSIGNED_BYTE)).shortValue();
                    short windowType = ((Windows) packetWrapper.user().get(Windows.class)).get(windowId);
                    packetWrapper.write(Type.UNSIGNED_BYTE, Short.valueOf(windowId));
                    Item[] items = (Item[]) packetWrapper.read(Type.ITEM_ARRAY);
                    if (windowType == 4) {
                        items = new Item[items.length - 1];
                        items[0] = items[0];
                        System.arraycopy(items, 2, items, 1, items.length - 3);
                    }
                    for (int i = 0; i < items.length; i++) {
                        items[i] = ItemRewriter.toClient(items[i]);
                    }
                    packetWrapper.write(Types1_7_6_10.COMPRESSED_NBT_ITEM_ARRAY, items);
                });
                handler(packetWrapper2 -> {
                    GameProfileStorage.GameProfile profile;
                    short windowId = ((Short) packetWrapper2.get(Type.UNSIGNED_BYTE, 0)).shortValue();
                    if (windowId != 0) {
                        return;
                    }
                    Item[] items = (Item[]) packetWrapper2.get(Types1_7_6_10.COMPRESSED_NBT_ITEM_ARRAY, 0);
                    EntityTracker tracker = (EntityTracker) packetWrapper2.user().get(EntityTracker.class);
                    UUID uuid = packetWrapper2.user().getProtocolInfo().getUuid();
                    Item[] equipment = tracker.getPlayerEquipment(uuid);
                    if (equipment == null) {
                        Item[] itemArr = new Item[5];
                        equipment = itemArr;
                        tracker.setPlayerEquipment(uuid, itemArr);
                    }
                    for (int i = 5; i < 9; i++) {
                        equipment[9 - i] = items[i];
                        if (tracker.getGamemode() == 3) {
                            items[i] = null;
                        }
                    }
                    if (tracker.getGamemode() != 3 || (profile = ((GameProfileStorage) packetWrapper2.user().get(GameProfileStorage.class)).get(uuid)) == null) {
                        return;
                    }
                    items[5] = profile.getSkull();
                });
            }
        });
        protocol.registerClientbound((Protocol1_7_6_10TO1_8) ClientboundPackets1_8.WINDOW_PROPERTY, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.InventoryPackets.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                map(Type.SHORT);
                map(Type.SHORT);
                handler(packetWrapper -> {
                    short windowId = ((Short) packetWrapper.get(Type.UNSIGNED_BYTE, 0)).shortValue();
                    Windows windows = (Windows) packetWrapper.user().get(Windows.class);
                    short windowType = windows.get(windowId);
                    short property = ((Short) packetWrapper.get(Type.SHORT, 0)).shortValue();
                    short value = ((Short) packetWrapper.get(Type.SHORT, 1)).shortValue();
                    if (windowType == -1) {
                        return;
                    }
                    if (windowType != 2) {
                        if (windowType == 4) {
                            if (property > 2) {
                                packetWrapper.cancel();
                                return;
                            }
                            return;
                        } else if (windowType == 8) {
                            windows.levelCost = value;
                            windows.anvilId = windowId;
                            return;
                        } else {
                            return;
                        }
                    }
                    Windows.Furnace furnace = windows.furnace.computeIfAbsent(Short.valueOf(windowId), x -> {
                        return new Windows.Furnace();
                    });
                    if (property == 0 || property == 1) {
                        if (property == 0) {
                            furnace.setFuelLeft(value);
                        } else {
                            furnace.setMaxFuel(value);
                        }
                        if (furnace.getMaxFuel() == 0) {
                            packetWrapper.cancel();
                            return;
                        }
                        packetWrapper.set(Type.SHORT, 0, (short) 1);
                        packetWrapper.set(Type.SHORT, 1, Short.valueOf((short) ((200 * furnace.getFuelLeft()) / furnace.getMaxFuel())));
                    } else if (property == 2 || property == 3) {
                        if (property == 2) {
                            furnace.setProgress(value);
                        } else {
                            furnace.setMaxProgress(value);
                        }
                        if (furnace.getMaxProgress() == 0) {
                            packetWrapper.cancel();
                            return;
                        }
                        packetWrapper.set(Type.SHORT, 0, (short) 0);
                        packetWrapper.set(Type.SHORT, 1, Short.valueOf((short) ((200 * furnace.getProgress()) / furnace.getMaxProgress())));
                    }
                });
            }
        });
        protocol.registerServerbound((Protocol1_7_6_10TO1_8) ServerboundPackets1_7.CLOSE_WINDOW, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.InventoryPackets.6
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                handler(packetWrapper -> {
                    short windowsId = ((Short) packetWrapper.get(Type.UNSIGNED_BYTE, 0)).shortValue();
                    ((Windows) packetWrapper.user().get(Windows.class)).remove(windowsId);
                });
            }
        });
        protocol.registerServerbound((Protocol1_7_6_10TO1_8) ServerboundPackets1_7.CLICK_WINDOW, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.InventoryPackets.7
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(packetWrapper -> {
                    short windowId = ((Byte) packetWrapper.read(Type.BYTE)).byteValue();
                    packetWrapper.write(Type.UNSIGNED_BYTE, Short.valueOf(windowId));
                    short windowType = ((Windows) packetWrapper.user().get(Windows.class)).get(windowId);
                    short slot = ((Short) packetWrapper.read(Type.SHORT)).shortValue();
                    if (windowType == 4 && slot > 0) {
                        slot = (short) (slot + 1);
                    }
                    packetWrapper.write(Type.SHORT, Short.valueOf(slot));
                });
                map(Type.BYTE);
                map(Type.SHORT);
                map(Type.BYTE);
                map(Types1_7_6_10.COMPRESSED_NBT_ITEM, Type.ITEM);
                handler(packetWrapper2 -> {
                    Item item = (Item) packetWrapper2.get(Type.ITEM, 0);
                    ItemRewriter.toServer(item);
                    packetWrapper2.set(Type.ITEM, 0, item);
                });
            }
        });
        protocol.registerServerbound((Protocol1_7_6_10TO1_8) ServerboundPackets1_7.WINDOW_CONFIRMATION, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.InventoryPackets.8
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.BYTE);
                map(Type.SHORT);
                map(Type.BOOLEAN);
                handler(packetWrapper -> {
                    int action = ((Short) packetWrapper.get(Type.SHORT, 0)).shortValue();
                    if (action == -89) {
                        packetWrapper.cancel();
                    }
                });
            }
        });
        protocol.registerServerbound((Protocol1_7_6_10TO1_8) ServerboundPackets1_7.CREATIVE_INVENTORY_ACTION, new PacketRemapper() { // from class: de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.InventoryPackets.9
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.SHORT);
                map(Types1_7_6_10.COMPRESSED_NBT_ITEM, Type.ITEM);
                handler(packetWrapper -> {
                    Item item = (Item) packetWrapper.get(Type.ITEM, 0);
                    ItemRewriter.toServer(item);
                    packetWrapper.set(Type.ITEM, 0, item);
                });
            }
        });
    }
}
