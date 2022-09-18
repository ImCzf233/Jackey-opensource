package net.ccbluex.liquidbounce.utils.item;

import net.minecraft.item.ItemStack;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/item/ArmorPiece.class */
public class ArmorPiece {
    private ItemStack itemStack;
    private int slot;

    public ArmorPiece(ItemStack itemStack, int slot) {
        this.itemStack = itemStack;
        this.slot = slot;
    }

    public int getArmorType() {
        return this.itemStack.func_77973_b().field_77881_a;
    }

    public int getSlot() {
        return this.slot;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }
}
