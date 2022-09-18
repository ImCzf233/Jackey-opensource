package net.ccbluex.liquidbounce.script.api.global;

import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.utils.item.ItemUtils;
import org.jetbrains.annotations.NotNull;

/* compiled from: Item.kt */
@Metadata(m51mv = {1, 1, 16}, m55bv = {1, 0, 3}, m52k = 1, m54d1 = {"��\u0018\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000e\n��\bÆ\u0002\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"}, m53d2 = {"Lnet/ccbluex/liquidbounce/script/api/global/Item;", "", "()V", "create", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "itemArguments", "", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/script/api/global/Item.class */
public final class Item {
    public static final Item INSTANCE = new Item();

    private Item() {
    }

    @JvmStatic
    @NotNull
    public static final IItemStack create(@NotNull String itemArguments) {
        Intrinsics.checkParameterIsNotNull(itemArguments, "itemArguments");
        IItemStack createItem = ItemUtils.createItem(itemArguments);
        Intrinsics.checkExpressionValueIsNotNull(createItem, "ItemUtils.createItem(itemArguments)");
        return createItem;
    }
}
