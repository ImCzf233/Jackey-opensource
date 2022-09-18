package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.MappedItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import java.util.Iterator;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/api/rewriters/ItemRewriter.class */
public abstract class ItemRewriter<T extends BackwardsProtocol> extends ItemRewriterBase<T> {
    public ItemRewriter(T protocol) {
        super(protocol, true);
    }

    @Override // com.viaversion.viaversion.rewriter.ItemRewriter, com.viaversion.viaversion.api.rewriter.ItemRewriter
    public Item handleItemToClient(Item item) {
        if (item == null) {
            return null;
        }
        CompoundTag display = item.tag() != null ? (CompoundTag) item.tag().get("display") : null;
        if (((BackwardsProtocol) this.protocol).getTranslatableRewriter() != null && display != null) {
            StringTag name = (StringTag) display.get("Name");
            if (name != null) {
                String newValue = ((BackwardsProtocol) this.protocol).getTranslatableRewriter().processText(name.getValue()).toString();
                if (!newValue.equals(name.getValue())) {
                    saveStringTag(display, name, "Name");
                }
                name.setValue(newValue);
            }
            ListTag lore = (ListTag) display.get("Lore");
            if (lore != null) {
                boolean changed = false;
                Iterator<Tag> it = lore.iterator();
                while (it.hasNext()) {
                    Tag loreEntryTag = it.next();
                    if (loreEntryTag instanceof StringTag) {
                        StringTag loreEntry = (StringTag) loreEntryTag;
                        String newValue2 = ((BackwardsProtocol) this.protocol).getTranslatableRewriter().processText(loreEntry.getValue()).toString();
                        if (!changed && !newValue2.equals(loreEntry.getValue())) {
                            changed = true;
                            saveListTag(display, lore, "Lore");
                        }
                        loreEntry.setValue(newValue2);
                    }
                }
            }
        }
        MappedItem data = ((BackwardsProtocol) this.protocol).getMappingData().getMappedItem(item.identifier());
        if (data == null) {
            return super.handleItemToClient(item);
        }
        if (item.tag() == null) {
            item.setTag(new CompoundTag());
        }
        item.tag().put(this.nbtTagName + "|id", new IntTag(item.identifier()));
        item.setIdentifier(data.getId());
        if (display == null) {
            CompoundTag tag = item.tag();
            CompoundTag compoundTag = new CompoundTag();
            display = compoundTag;
            tag.put("display", compoundTag);
        }
        if (!display.contains("Name")) {
            display.put("Name", new StringTag(data.getJsonName()));
            display.put(this.nbtTagName + "|customName", new ByteTag());
        }
        return item;
    }

    @Override // com.viaversion.viabackwards.api.rewriters.ItemRewriterBase, com.viaversion.viaversion.rewriter.ItemRewriter, com.viaversion.viaversion.api.rewriter.ItemRewriter
    public Item handleItemToServer(Item item) {
        IntTag originalId;
        if (item == null) {
            return null;
        }
        super.handleItemToServer(item);
        if (item.tag() != null && (originalId = (IntTag) item.tag().remove(this.nbtTagName + "|id")) != null) {
            item.setIdentifier(originalId.asInt());
        }
        return item;
    }

    @Override // com.viaversion.viaversion.rewriter.ItemRewriter
    public void registerAdvancements(ClientboundPacketType packetType, final Type<Item> type) {
        ((BackwardsProtocol) this.protocol).registerClientbound((BackwardsProtocol) packetType, new PacketRemapper() { // from class: com.viaversion.viabackwards.api.rewriters.ItemRewriter.1
            @Override // com.viaversion.viaversion.api.protocol.remapper.PacketRemapper
            public void registerMap() {
                Type type2 = type;
                handler(wrapper -> {
                    type2.passthrough(Type.BOOLEAN);
                    int size = ((Integer) type2.passthrough(Type.VAR_INT)).intValue();
                    for (int i = 0; i < size; i++) {
                        type2.passthrough(Type.STRING);
                        if (((Boolean) type2.passthrough(Type.BOOLEAN)).booleanValue()) {
                            type2.passthrough(Type.STRING);
                        }
                        if (((Boolean) type2.passthrough(Type.BOOLEAN)).booleanValue()) {
                            JsonElement title = (JsonElement) type2.passthrough(Type.COMPONENT);
                            JsonElement description = (JsonElement) type2.passthrough(Type.COMPONENT);
                            TranslatableRewriter translatableRewriter = ((BackwardsProtocol) ItemRewriter.this.protocol).getTranslatableRewriter();
                            if (translatableRewriter != null) {
                                translatableRewriter.processText(title);
                                translatableRewriter.processText(description);
                            }
                            ItemRewriter.this.handleItemToClient((Item) type2.passthrough(type));
                            type2.passthrough(Type.VAR_INT);
                            int flags = ((Integer) type2.passthrough(Type.INT)).intValue();
                            if ((flags & 1) != 0) {
                                type2.passthrough(Type.STRING);
                            }
                            type2.passthrough(Type.FLOAT);
                            type2.passthrough(Type.FLOAT);
                        }
                        type2.passthrough(Type.STRING_ARRAY);
                        int arrayLength = ((Integer) type2.passthrough(Type.VAR_INT)).intValue();
                        for (int array = 0; array < arrayLength; array++) {
                            type2.passthrough(Type.STRING_ARRAY);
                        }
                    }
                });
            }
        });
    }
}
