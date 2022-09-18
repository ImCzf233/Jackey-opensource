package com.viaversion.viaversion.protocols.protocol1_9to1_8.packets;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ItemRewriter;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ServerboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.EntityTracker1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.InventoryTracker;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_9to1_8/packets/InventoryPackets.class */
public class InventoryPackets {
    public static void register(Protocol protocol) {
        protocol.registerClientbound((Protocol) ClientboundPackets1_8.WINDOW_PROPERTY, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.InventoryPackets.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                map(Type.SHORT);
                map(Type.SHORT);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.InventoryPackets.1.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        final short windowId = ((Short) wrapper.get(Type.UNSIGNED_BYTE, 0)).shortValue();
                        final short property = ((Short) wrapper.get(Type.SHORT, 0)).shortValue();
                        short value = ((Short) wrapper.get(Type.SHORT, 1)).shortValue();
                        InventoryTracker inventoryTracker = (InventoryTracker) wrapper.user().get(InventoryTracker.class);
                        if (inventoryTracker.getInventory() != null && inventoryTracker.getInventory().equalsIgnoreCase("minecraft:enchanting_table") && property > 3 && property < 7) {
                            short level = (short) (value >> 8);
                            final short enchantID = (short) (value & 255);
                            wrapper.create(wrapper.getId(), new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.InventoryPackets.1.1.1
                                @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                                public void handle(PacketWrapper wrapper2) throws Exception {
                                    wrapper2.write(Type.UNSIGNED_BYTE, Short.valueOf(windowId));
                                    wrapper2.write(Type.SHORT, Short.valueOf(property));
                                    wrapper2.write(Type.SHORT, Short.valueOf(enchantID));
                                }
                            }).scheduleSend(Protocol1_9To1_8.class);
                            wrapper.set(Type.SHORT, 0, Short.valueOf((short) (property + 3)));
                            wrapper.set(Type.SHORT, 1, Short.valueOf(level));
                        }
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol) ClientboundPackets1_8.OPEN_WINDOW, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.InventoryPackets.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                map(Type.STRING);
                map(Type.STRING, Protocol1_9To1_8.FIX_JSON);
                map(Type.UNSIGNED_BYTE);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.InventoryPackets.2.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        String inventory = (String) wrapper.get(Type.STRING, 0);
                        InventoryTracker inventoryTracker = (InventoryTracker) wrapper.user().get(InventoryTracker.class);
                        inventoryTracker.setInventory(inventory);
                    }
                });
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.InventoryPackets.2.2
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        String inventory = (String) wrapper.get(Type.STRING, 0);
                        if (inventory.equals("minecraft:brewing_stand")) {
                            wrapper.set(Type.UNSIGNED_BYTE, 1, Short.valueOf((short) (((Short) wrapper.get(Type.UNSIGNED_BYTE, 1)).shortValue() + 1)));
                        }
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol) ClientboundPackets1_8.SET_SLOT, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.InventoryPackets.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                map(Type.SHORT);
                map(Type.ITEM);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.InventoryPackets.3.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        Item stack = (Item) wrapper.get(Type.ITEM, 0);
                        boolean showShieldWhenSwordInHand = Via.getConfig().isShowShieldWhenSwordInHand() && Via.getConfig().isShieldBlocking();
                        if (showShieldWhenSwordInHand) {
                            InventoryTracker inventoryTracker = (InventoryTracker) wrapper.user().get(InventoryTracker.class);
                            EntityTracker1_9 entityTracker = (EntityTracker1_9) wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                            short slotID = ((Short) wrapper.get(Type.SHORT, 0)).shortValue();
                            byte windowId = ((Short) wrapper.get(Type.UNSIGNED_BYTE, 0)).byteValue();
                            inventoryTracker.setItemId(windowId, slotID, stack == null ? 0 : stack.identifier());
                            entityTracker.syncShieldWithSword();
                        }
                        ItemRewriter.toClient(stack);
                    }
                });
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.InventoryPackets.3.2
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        InventoryTracker inventoryTracker = (InventoryTracker) wrapper.user().get(InventoryTracker.class);
                        short slotID = ((Short) wrapper.get(Type.SHORT, 0)).shortValue();
                        if (inventoryTracker.getInventory() != null && inventoryTracker.getInventory().equals("minecraft:brewing_stand") && slotID >= 4) {
                            wrapper.set(Type.SHORT, 0, Short.valueOf((short) (slotID + 1)));
                        }
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol) ClientboundPackets1_8.WINDOW_ITEMS, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.InventoryPackets.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                map(Type.ITEM_ARRAY);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.InventoryPackets.4.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        Item[] stacks = (Item[]) wrapper.get(Type.ITEM_ARRAY, 0);
                        Short windowId = (Short) wrapper.get(Type.UNSIGNED_BYTE, 0);
                        InventoryTracker inventoryTracker = (InventoryTracker) wrapper.user().get(InventoryTracker.class);
                        EntityTracker1_9 entityTracker = (EntityTracker1_9) wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                        boolean showShieldWhenSwordInHand = Via.getConfig().isShowShieldWhenSwordInHand() && Via.getConfig().isShieldBlocking();
                        short s = 0;
                        while (true) {
                            short i = s;
                            if (i >= stacks.length) {
                                break;
                            }
                            Item stack = stacks[i];
                            if (showShieldWhenSwordInHand) {
                                inventoryTracker.setItemId(windowId.shortValue(), i, stack == null ? 0 : stack.identifier());
                            }
                            ItemRewriter.toClient(stack);
                            s = (short) (i + 1);
                        }
                        if (showShieldWhenSwordInHand) {
                            entityTracker.syncShieldWithSword();
                        }
                    }
                });
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.InventoryPackets.4.2
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        InventoryTracker inventoryTracker = (InventoryTracker) wrapper.user().get(InventoryTracker.class);
                        if (inventoryTracker.getInventory() != null && inventoryTracker.getInventory().equals("minecraft:brewing_stand")) {
                            Item[] oldStack = (Item[]) wrapper.get(Type.ITEM_ARRAY, 0);
                            Item[] newStack = new Item[oldStack.length + 1];
                            for (int i = 0; i < newStack.length; i++) {
                                if (i > 4) {
                                    newStack[i] = oldStack[i - 1];
                                } else if (i != 4) {
                                    newStack[i] = oldStack[i];
                                }
                            }
                            wrapper.set(Type.ITEM_ARRAY, 0, newStack);
                        }
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol) ClientboundPackets1_8.CLOSE_WINDOW, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.InventoryPackets.5
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.InventoryPackets.5.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        InventoryTracker inventoryTracker = (InventoryTracker) wrapper.user().get(InventoryTracker.class);
                        inventoryTracker.setInventory(null);
                        inventoryTracker.resetInventory(((Short) wrapper.get(Type.UNSIGNED_BYTE, 0)).shortValue());
                    }
                });
            }
        });
        protocol.registerClientbound((Protocol) ClientboundPackets1_8.MAP_DATA, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.InventoryPackets.6
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.VAR_INT);
                map(Type.BYTE);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.InventoryPackets.6.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) {
                        wrapper.write(Type.BOOLEAN, true);
                    }
                });
            }
        });
        protocol.registerServerbound((Protocol) ServerboundPackets1_9.CREATIVE_INVENTORY_ACTION, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.InventoryPackets.7
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.SHORT);
                map(Type.ITEM);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.InventoryPackets.7.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        Item stack = (Item) wrapper.get(Type.ITEM, 0);
                        boolean showShieldWhenSwordInHand = Via.getConfig().isShowShieldWhenSwordInHand() && Via.getConfig().isShieldBlocking();
                        if (showShieldWhenSwordInHand) {
                            InventoryTracker inventoryTracker = (InventoryTracker) wrapper.user().get(InventoryTracker.class);
                            EntityTracker1_9 entityTracker = (EntityTracker1_9) wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                            short slotID = ((Short) wrapper.get(Type.SHORT, 0)).shortValue();
                            inventoryTracker.setItemId((short) 0, slotID, stack == null ? 0 : stack.identifier());
                            entityTracker.syncShieldWithSword();
                        }
                        ItemRewriter.toServer(stack);
                    }
                });
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.InventoryPackets.7.2
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        final short slot = ((Short) wrapper.get(Type.SHORT, 0)).shortValue();
                        boolean throwItem = slot == 45;
                        if (throwItem) {
                            wrapper.create(ClientboundPackets1_9.SET_SLOT, new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.InventoryPackets.7.2.1
                                @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                                public void handle(PacketWrapper wrapper2) throws Exception {
                                    wrapper2.write(Type.UNSIGNED_BYTE, (short) 0);
                                    wrapper2.write(Type.SHORT, Short.valueOf(slot));
                                    wrapper2.write(Type.ITEM, null);
                                }
                            }).send(Protocol1_9To1_8.class);
                            wrapper.set(Type.SHORT, 0, (short) -999);
                        }
                    }
                });
            }
        });
        protocol.registerServerbound((Protocol) ServerboundPackets1_9.CLICK_WINDOW, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.InventoryPackets.8
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                map(Type.SHORT);
                map(Type.BYTE);
                map(Type.SHORT);
                map(Type.VAR_INT, Type.BYTE);
                map(Type.ITEM);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.InventoryPackets.8.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        Item stack = (Item) wrapper.get(Type.ITEM, 0);
                        if (Via.getConfig().isShowShieldWhenSwordInHand()) {
                            Short windowId = (Short) wrapper.get(Type.UNSIGNED_BYTE, 0);
                            byte mode = ((Byte) wrapper.get(Type.BYTE, 1)).byteValue();
                            short hoverSlot = ((Short) wrapper.get(Type.SHORT, 0)).shortValue();
                            byte button = ((Byte) wrapper.get(Type.BYTE, 0)).byteValue();
                            InventoryTracker inventoryTracker = (InventoryTracker) wrapper.user().get(InventoryTracker.class);
                            inventoryTracker.handleWindowClick(wrapper.user(), windowId.shortValue(), mode, hoverSlot, button);
                        }
                        ItemRewriter.toServer(stack);
                    }
                });
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.InventoryPackets.8.2
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        final short windowID = ((Short) wrapper.get(Type.UNSIGNED_BYTE, 0)).shortValue();
                        final short slot = ((Short) wrapper.get(Type.SHORT, 0)).shortValue();
                        boolean throwItem = slot == 45 && windowID == 0;
                        InventoryTracker inventoryTracker = (InventoryTracker) wrapper.user().get(InventoryTracker.class);
                        if (inventoryTracker.getInventory() != null && inventoryTracker.getInventory().equals("minecraft:brewing_stand")) {
                            if (slot == 4) {
                                throwItem = true;
                            }
                            if (slot > 4) {
                                wrapper.set(Type.SHORT, 0, Short.valueOf((short) (slot - 1)));
                            }
                        }
                        if (throwItem) {
                            wrapper.create(ClientboundPackets1_9.SET_SLOT, new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.InventoryPackets.8.2.1
                                @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                                public void handle(PacketWrapper wrapper2) throws Exception {
                                    wrapper2.write(Type.UNSIGNED_BYTE, Short.valueOf(windowID));
                                    wrapper2.write(Type.SHORT, Short.valueOf(slot));
                                    wrapper2.write(Type.ITEM, null);
                                }
                            }).scheduleSend(Protocol1_9To1_8.class);
                            wrapper.set(Type.BYTE, 0, (byte) 0);
                            wrapper.set(Type.BYTE, 1, (byte) 0);
                            wrapper.set(Type.SHORT, 0, (short) -999);
                        }
                    }
                });
            }
        });
        protocol.registerServerbound((Protocol) ServerboundPackets1_9.CLOSE_WINDOW, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.InventoryPackets.9
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.InventoryPackets.9.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        InventoryTracker inventoryTracker = (InventoryTracker) wrapper.user().get(InventoryTracker.class);
                        inventoryTracker.setInventory(null);
                        inventoryTracker.resetInventory(((Short) wrapper.get(Type.UNSIGNED_BYTE, 0)).shortValue());
                    }
                });
            }
        });
        protocol.registerServerbound((Protocol) ServerboundPackets1_9.HELD_ITEM_CHANGE, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.InventoryPackets.10
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.SHORT);
                handler(new PacketHandler() { // from class: com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.InventoryPackets.10.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        boolean showShieldWhenSwordInHand = Via.getConfig().isShowShieldWhenSwordInHand() && Via.getConfig().isShieldBlocking();
                        EntityTracker1_9 entityTracker = (EntityTracker1_9) wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
                        if (entityTracker.isBlocking()) {
                            entityTracker.setBlocking(false);
                            if (!showShieldWhenSwordInHand) {
                                entityTracker.setSecondHand(null);
                            }
                        }
                        if (showShieldWhenSwordInHand) {
                            entityTracker.setHeldItemSlot(((Short) wrapper.get(Type.SHORT, 0)).shortValue());
                            entityTracker.syncShieldWithSword();
                        }
                    }
                });
            }
        });
    }
}
