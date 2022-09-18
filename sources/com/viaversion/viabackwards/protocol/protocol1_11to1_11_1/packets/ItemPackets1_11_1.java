package com.viaversion.viabackwards.protocol.protocol1_11to1_11_1.packets;

import com.viaversion.viabackwards.api.rewriters.LegacyBlockItemRewriter;
import com.viaversion.viabackwards.api.rewriters.LegacyEnchantmentRewriter;
import com.viaversion.viabackwards.protocol.protocol1_11to1_11_1.Protocol1_11To1_11_1;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/protocol/protocol1_11to1_11_1/packets/ItemPackets1_11_1.class */
public class ItemPackets1_11_1 extends LegacyBlockItemRewriter<Protocol1_11To1_11_1> {
    private LegacyEnchantmentRewriter enchantmentRewriter;

    public ItemPackets1_11_1(Protocol1_11To1_11_1 protocol) {
        super(protocol);
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    protected void registerPackets() {
        registerSetSlot(ClientboundPackets1_9_3.SET_SLOT, Type.ITEM);
        registerWindowItems(ClientboundPackets1_9_3.WINDOW_ITEMS, Type.ITEM_ARRAY);
        registerEntityEquipment(ClientboundPackets1_9_3.ENTITY_EQUIPMENT, Type.ITEM);
        ((Protocol1_11To1_11_1) this.protocol).registerClientbound((Protocol1_11To1_11_1) ClientboundPackets1_9_3.PLUGIN_MESSAGE, new PacketRemapper() { // from class: com.viaversion.viabackwards.protocol.protocol1_11to1_11_1.packets.ItemPackets1_11_1.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                map(Type.STRING);
                handler(new PacketHandler() { // from class: com.viaversion.viabackwards.protocol.protocol1_11to1_11_1.packets.ItemPackets1_11_1.1.1
                    @Override // com.viaversion.viaversion.api.protocol.remapper.PacketHandler
                    public void handle(PacketWrapper wrapper) throws Exception {
                        if (((String) wrapper.get(Type.STRING, 0)).equalsIgnoreCase("MC|TrList")) {
                            wrapper.passthrough(Type.INT);
                            int size = ((Short) wrapper.passthrough(Type.UNSIGNED_BYTE)).shortValue();
                            for (int i = 0; i < size; i++) {
                                wrapper.write(Type.ITEM, ItemPackets1_11_1.this.handleItemToClient((Item) wrapper.read(Type.ITEM)));
                                wrapper.write(Type.ITEM, ItemPackets1_11_1.this.handleItemToClient((Item) wrapper.read(Type.ITEM)));
                                boolean secondItem = ((Boolean) wrapper.passthrough(Type.BOOLEAN)).booleanValue();
                                if (secondItem) {
                                    wrapper.write(Type.ITEM, ItemPackets1_11_1.this.handleItemToClient((Item) wrapper.read(Type.ITEM)));
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
        ((Protocol1_11To1_11_1) this.protocol).getEntityRewriter().filter().handler(event, meta -> {
            if (meta.metaType().type().equals(Type.ITEM)) {
                meta.setValue(handleItemToClient((Item) meta.getValue()));
            }
        });
    }

    @Override // com.viaversion.viaversion.api.rewriter.RewriterBase
    protected void registerRewrites() {
        this.enchantmentRewriter = new LegacyEnchantmentRewriter(this.nbtTagName);
        this.enchantmentRewriter.registerEnchantment(22, "§7Sweeping Edge");
    }

    @Override // com.viaversion.viabackwards.api.rewriters.LegacyBlockItemRewriter, com.viaversion.viaversion.rewriter.ItemRewriter, com.viaversion.viaversion.api.rewriter.ItemRewriter
    public Item handleItemToClient(Item item) {
        if (item == null) {
            return null;
        }
        super.handleItemToClient(item);
        CompoundTag tag = item.tag();
        if (tag == null) {
            return item;
        }
        if (tag.get("ench") instanceof ListTag) {
            this.enchantmentRewriter.rewriteEnchantmentsToClient(tag, false);
        }
        if (tag.get("StoredEnchantments") instanceof ListTag) {
            this.enchantmentRewriter.rewriteEnchantmentsToClient(tag, true);
        }
        return item;
    }

    @Override // com.viaversion.viabackwards.api.rewriters.ItemRewriterBase, com.viaversion.viaversion.rewriter.ItemRewriter, com.viaversion.viaversion.api.rewriter.ItemRewriter
    public Item handleItemToServer(Item item) {
        if (item == null) {
            return null;
        }
        super.handleItemToServer(item);
        CompoundTag tag = item.tag();
        if (tag == null) {
            return item;
        }
        if (tag.contains(this.nbtTagName + "|ench")) {
            this.enchantmentRewriter.rewriteEnchantmentsToServer(tag, false);
        }
        if (tag.contains(this.nbtTagName + "|StoredEnchantments")) {
            this.enchantmentRewriter.rewriteEnchantmentsToServer(tag, true);
        }
        return item;
    }
}
