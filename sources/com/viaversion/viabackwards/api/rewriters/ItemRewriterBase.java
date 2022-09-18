package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;

/* loaded from: Jackey Client b2.jar:com/viaversion/viabackwards/api/rewriters/ItemRewriterBase.class */
public abstract class ItemRewriterBase<T extends BackwardsProtocol> extends com.viaversion.viaversion.rewriter.ItemRewriter<T> {
    protected final String nbtTagName;
    protected final boolean jsonNameFormat;

    public ItemRewriterBase(T protocol, boolean jsonNameFormat) {
        super(protocol);
        this.jsonNameFormat = jsonNameFormat;
        this.nbtTagName = "VB|" + protocol.getClass().getSimpleName();
    }

    @Override // com.viaversion.viaversion.rewriter.ItemRewriter, com.viaversion.viaversion.api.rewriter.ItemRewriter
    public Item handleItemToServer(Item item) {
        if (item == null) {
            return null;
        }
        super.handleItemToServer(item);
        restoreDisplayTag(item);
        return item;
    }

    public boolean hasBackupTag(CompoundTag displayTag, String tagName) {
        return displayTag.contains(this.nbtTagName + "|o" + tagName);
    }

    public void saveStringTag(CompoundTag displayTag, StringTag original, String name) {
        String backupName = this.nbtTagName + "|o" + name;
        if (!displayTag.contains(backupName)) {
            displayTag.put(backupName, new StringTag(original.getValue()));
        }
    }

    public void saveListTag(CompoundTag displayTag, ListTag original, String name) {
        String backupName = this.nbtTagName + "|o" + name;
        if (!displayTag.contains(backupName)) {
            ListTag listTag = new ListTag();
            for (Tag tag : original.getValue()) {
                listTag.add(tag.clone());
            }
            displayTag.put(backupName, listTag);
        }
    }

    protected void restoreDisplayTag(Item item) {
        CompoundTag display;
        if (item.tag() != null && (display = (CompoundTag) item.tag().get("display")) != null) {
            if (display.remove(this.nbtTagName + "|customName") != null) {
                display.remove("Name");
            } else {
                restoreStringTag(display, "Name");
            }
            restoreListTag(display, "Lore");
        }
    }

    protected void restoreStringTag(CompoundTag tag, String tagName) {
        StringTag original = (StringTag) tag.remove(this.nbtTagName + "|o" + tagName);
        if (original != null) {
            tag.put(tagName, new StringTag(original.getValue()));
        }
    }

    public void restoreListTag(CompoundTag tag, String tagName) {
        ListTag original = (ListTag) tag.remove(this.nbtTagName + "|o" + tagName);
        if (original != null) {
            tag.put(tagName, new ListTag(original.getValue()));
        }
    }

    public String getNbtTagName() {
        return this.nbtTagName;
    }
}
