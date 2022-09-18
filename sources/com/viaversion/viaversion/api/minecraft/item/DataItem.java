package com.viaversion.viaversion.api.minecraft.item;

import com.viaversion.viaversion.libs.gson.annotations.SerializedName;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import java.util.Objects;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/api/minecraft/item/DataItem.class */
public class DataItem implements Item {
    @SerializedName(value = "identifier", alternate = {"id"})
    private int identifier;
    private byte amount;
    private short data;
    private CompoundTag tag;

    public DataItem() {
    }

    public DataItem(int identifier, byte amount, short data, CompoundTag tag) {
        this.identifier = identifier;
        this.amount = amount;
        this.data = data;
        this.tag = tag;
    }

    public DataItem(Item toCopy) {
        this(toCopy.identifier(), (byte) toCopy.amount(), toCopy.data(), toCopy.tag());
    }

    @Override // com.viaversion.viaversion.api.minecraft.item.Item
    public int identifier() {
        return this.identifier;
    }

    @Override // com.viaversion.viaversion.api.minecraft.item.Item
    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    @Override // com.viaversion.viaversion.api.minecraft.item.Item
    public int amount() {
        return this.amount;
    }

    @Override // com.viaversion.viaversion.api.minecraft.item.Item
    public void setAmount(int amount) {
        if (amount > 127 || amount < -128) {
            throw new IllegalArgumentException("Invalid item amount: " + amount);
        }
        this.amount = (byte) amount;
    }

    @Override // com.viaversion.viaversion.api.minecraft.item.Item
    public short data() {
        return this.data;
    }

    @Override // com.viaversion.viaversion.api.minecraft.item.Item
    public void setData(short data) {
        this.data = data;
    }

    @Override // com.viaversion.viaversion.api.minecraft.item.Item
    public CompoundTag tag() {
        return this.tag;
    }

    @Override // com.viaversion.viaversion.api.minecraft.item.Item
    public void setTag(CompoundTag tag) {
        this.tag = tag;
    }

    @Override // com.viaversion.viaversion.api.minecraft.item.Item
    public Item copy() {
        return new DataItem(this.identifier, this.amount, this.data, this.tag);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DataItem item = (DataItem) o;
        if (this.identifier != item.identifier || this.amount != item.amount || this.data != item.data) {
            return false;
        }
        return Objects.equals(this.tag, item.tag);
    }

    public int hashCode() {
        int result = this.identifier;
        return (31 * ((31 * ((31 * result) + this.amount)) + this.data)) + (this.tag != null ? this.tag.hashCode() : 0);
    }

    public String toString() {
        return "Item{identifier=" + this.identifier + ", amount=" + ((int) this.amount) + ", data=" + ((int) this.data) + ", tag=" + this.tag + '}';
    }
}
