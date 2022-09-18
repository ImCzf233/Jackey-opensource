package com.viaversion.viaversion.api.minecraft.item;

import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/minecraft/item/Item.class */
public interface Item {
    int identifier();

    void setIdentifier(int i);

    int amount();

    void setAmount(int i);

    CompoundTag tag();

    void setTag(CompoundTag compoundTag);

    Item copy();

    default short data() {
        return (short) 0;
    }

    default void setData(short data) {
        throw new UnsupportedOperationException();
    }
}
