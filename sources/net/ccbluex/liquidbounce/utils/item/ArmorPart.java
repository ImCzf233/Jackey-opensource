package net.ccbluex.liquidbounce.utils.item;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/* compiled from: ArmorPart.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0018\n\u0002\u0018\u0002\n\u0002\u0010��\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\b\n\u0002\b\b\u0018��2\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0005¢\u0006\b\n��\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n��\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n��\u001a\u0004\b\f\u0010\t¨\u0006\r"}, m53d2 = {"Lnet/ccbluex/liquidbounce/utils/item/ArmorPart;", "", "itemStack", "Lnet/minecraft/item/ItemStack;", "slot", "", "(Lnet/minecraft/item/ItemStack;I)V", "armorType", "getArmorType", "()I", "getItemStack", "()Lnet/minecraft/item/ItemStack;", "getSlot", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/item/ArmorPart.class */
public final class ArmorPart {
    @NotNull
    private final ItemStack itemStack;
    private final int slot;
    private final int armorType;

    public ArmorPart(@NotNull ItemStack itemStack, int slot) {
        Intrinsics.checkNotNullParameter(itemStack, "itemStack");
        this.itemStack = itemStack;
        this.slot = slot;
        ItemArmor func_77973_b = this.itemStack.func_77973_b();
        if (func_77973_b == null) {
            throw new NullPointerException("null cannot be cast to non-null type net.minecraft.item.ItemArmor");
        }
        this.armorType = func_77973_b.field_77881_a;
    }

    @NotNull
    public final ItemStack getItemStack() {
        return this.itemStack;
    }

    public final int getSlot() {
        return this.slot;
    }

    public final int getArmorType() {
        return this.armorType;
    }
}
