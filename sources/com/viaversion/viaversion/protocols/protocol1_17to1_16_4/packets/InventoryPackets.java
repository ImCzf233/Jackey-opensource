package com.viaversion.viaversion.protocols.protocol1_17to1_16_4.packets;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ServerboundPackets1_16_2;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.data.RecipeRewriter1_16;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ClientboundPackets1_17;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.Protocol1_17To1_16_4;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ServerboundPackets1_17;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.storage.InventoryAcknowledgements;
import com.viaversion.viaversion.rewriter.ItemRewriter;
import kotlin.jvm.internal.CharCompanionObject;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/protocols/protocol1_17to1_16_4/packets/InventoryPackets.class */
public final class InventoryPackets extends ItemRewriter<Protocol1_17To1_16_4> {
    public InventoryPackets(Protocol1_17To1_16_4 protocol) {
        super(protocol);
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    public void registerPackets() {
        registerSetCooldown(ClientboundPackets1_16_2.COOLDOWN);
        registerWindowItems(ClientboundPackets1_16_2.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY);
        registerTradeList(ClientboundPackets1_16_2.TRADE_LIST, Type.FLAT_VAR_INT_ITEM);
        registerSetSlot(ClientboundPackets1_16_2.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
        registerAdvancements(ClientboundPackets1_16_2.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
        registerEntityEquipmentArray(ClientboundPackets1_16_2.ENTITY_EQUIPMENT, Type.FLAT_VAR_INT_ITEM);
        registerSpawnParticle(ClientboundPackets1_16_2.SPAWN_PARTICLE, Type.FLAT_VAR_INT_ITEM, Type.DOUBLE);
        new RecipeRewriter1_16(this.protocol).registerDefaultHandler(ClientboundPackets1_16_2.DECLARE_RECIPES);
        registerCreativeInvAction(ServerboundPackets1_17.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_17To1_16_4) this.protocol).registerServerbound((Protocol1_17To1_16_4) ServerboundPackets1_17.EDIT_BOOK, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_17to1_16_4.packets.InventoryPackets.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    InventoryPackets.this.handleItemToServer((Item) wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
                });
            }
        });
        ((Protocol1_17To1_16_4) this.protocol).registerServerbound((Protocol1_17To1_16_4) ServerboundPackets1_17.CLICK_WINDOW, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_17to1_16_4.packets.InventoryPackets.2
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.UNSIGNED_BYTE);
                map(Type.SHORT);
                map(Type.BYTE);
                handler(wrapper -> {
                    wrapper.write(Type.SHORT, (short) 0);
                });
                map(Type.VAR_INT);
                handler(wrapper2 -> {
                    int length = ((Integer) wrapper2.read(Type.VAR_INT)).intValue();
                    for (int i = 0; i < length; i++) {
                        wrapper2.read(Type.SHORT);
                        wrapper2.read(Type.FLAT_VAR_INT_ITEM);
                    }
                    Item item = (Item) wrapper2.read(Type.FLAT_VAR_INT_ITEM);
                    int action = ((Integer) wrapper2.get(Type.VAR_INT, 0)).intValue();
                    if (action == 5 || action == 1) {
                        item = null;
                    } else {
                        InventoryPackets.this.handleItemToServer(item);
                    }
                    wrapper2.write(Type.FLAT_VAR_INT_ITEM, item);
                });
            }
        });
        ((Protocol1_17To1_16_4) this.protocol).registerClientbound((Protocol1_17To1_16_4) ClientboundPackets1_16_2.WINDOW_CONFIRMATION, (ClientboundPackets1_16_2) null, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_17to1_16_4.packets.InventoryPackets.3
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    short inventoryId = ((Short) wrapper.read(Type.UNSIGNED_BYTE)).shortValue();
                    short confirmationId = ((Short) wrapper.read(Type.SHORT)).shortValue();
                    boolean accepted = ((Boolean) wrapper.read(Type.BOOLEAN)).booleanValue();
                    if (!accepted) {
                        int id = 1073741824 | (inventoryId << 16) | (confirmationId & 65535);
                        ((InventoryAcknowledgements) wrapper.user().get(InventoryAcknowledgements.class)).addId(id);
                        PacketWrapper pingPacket = wrapper.create(ClientboundPackets1_17.PING);
                        pingPacket.write(Type.INT, Integer.valueOf(id));
                        pingPacket.send(Protocol1_17To1_16_4.class);
                    }
                    wrapper.cancel();
                });
            }
        });
        ((Protocol1_17To1_16_4) this.protocol).registerServerbound((Protocol1_17To1_16_4) ServerboundPackets1_17.PONG, (ServerboundPackets1_17) null, new PacketRemapper() { // from class: com.viaversion.viaversion.protocols.protocol1_17to1_16_4.packets.InventoryPackets.4
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                handler(wrapper -> {
                    int id = ((Integer) wrapper.read(Type.INT)).intValue();
                    if ((id & 1073741824) != 0 && ((InventoryAcknowledgements) wrapper.user().get(InventoryAcknowledgements.class)).removeId(id)) {
                        short inventoryId = (short) ((id >> 16) & 255);
                        short confirmationId = (short) (id & CharCompanionObject.MAX_VALUE);
                        PacketWrapper packet = wrapper.create(ServerboundPackets1_16_2.WINDOW_CONFIRMATION);
                        packet.write(Type.UNSIGNED_BYTE, Short.valueOf(inventoryId));
                        packet.write(Type.SHORT, Short.valueOf(confirmationId));
                        packet.write(Type.BOOLEAN, true);
                        packet.sendToServer(Protocol1_17To1_16_4.class);
                    }
                    wrapper.cancel();
                });
            }
        });
    }
}
