package net.ccbluex.liquidbounce.script.api;

import java.util.List;
import jdk.nashorn.api.scripting.JSObject;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.item.IItem;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.util.WrappedCreativeTabs;
import net.ccbluex.liquidbounce.utils.item.ItemUtils;
import org.jetbrains.annotations.NotNull;

/* compiled from: ScriptTab.kt */
@Metadata(m51mv = {1, 1, 16}, m55bv = {1, 0, 3}, m52k = 1, m54d1 = {"��4\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\u0010!\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000e\n��\u0018��2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0016\u0010\u000b\u001a\u00020\f2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\rH\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016R\u0019\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\n\n\u0002\u0010\n\u001a\u0004\b\b\u0010\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n��¨\u0006\u0012"}, m53d2 = {"Lnet/ccbluex/liquidbounce/script/api/ScriptTab;", "Lnet/ccbluex/liquidbounce/api/util/WrappedCreativeTabs;", "tabObject", "Ljdk/nashorn/api/scripting/JSObject;", "(Ljdk/nashorn/api/scripting/JSObject;)V", "items", "", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "getItems", "()[Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "[Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "displayAllReleventItems", "", "", "getTabIconItem", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItem;", "getTranslatedTabLabel", "", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/script/api/ScriptTab.class */
public final class ScriptTab extends WrappedCreativeTabs {
    @NotNull
    private final IItemStack[] items;
    private final JSObject tabObject;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ScriptTab(@org.jetbrains.annotations.NotNull jdk.nashorn.api.scripting.JSObject r7) {
        /*
            r6 = this;
            r0 = r7
            java.lang.String r1 = "tabObject"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r0, r1)
            r0 = r6
            r1 = r7
            java.lang.String r2 = "name"
            java.lang.Object r1 = r1.getMember(r2)
            r2 = r1
            if (r2 != 0) goto L1d
            kotlin.TypeCastException r2 = new kotlin.TypeCastException
            r3 = r2
            java.lang.String r4 = "null cannot be cast to non-null type kotlin.String"
            r3.<init>(r4)
            throw r2
        L1d:
            java.lang.String r1 = (java.lang.String) r1
            r0.<init>(r1)
            r0 = r6
            r1 = r7
            r0.tabObject = r1
            r0 = r6
            r1 = r6
            jdk.nashorn.api.scripting.JSObject r1 = r1.tabObject
            java.lang.String r2 = "items"
            java.lang.Object r1 = r1.getMember(r2)
            java.lang.Class<net.ccbluex.liquidbounce.api.minecraft.item.IItemStack[]> r2 = net.ccbluex.liquidbounce.api.minecraft.item.IItemStack[].class
            java.lang.Object r1 = jdk.nashorn.api.scripting.ScriptUtils.convert(r1, r2)
            r2 = r1
            if (r2 != 0) goto L47
            kotlin.TypeCastException r2 = new kotlin.TypeCastException
            r3 = r2
            java.lang.String r4 = "null cannot be cast to non-null type kotlin.Array<net.ccbluex.liquidbounce.api.minecraft.item.IItemStack>"
            r3.<init>(r4)
            throw r2
        L47:
            net.ccbluex.liquidbounce.api.minecraft.item.IItemStack[] r1 = (net.ccbluex.liquidbounce.api.minecraft.item.IItemStack[]) r1
            r0.items = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: net.ccbluex.liquidbounce.script.api.ScriptTab.<init>(jdk.nashorn.api.scripting.JSObject):void");
    }

    @NotNull
    public final IItemStack[] getItems() {
        return this.items;
    }

    @NotNull
    public IItem getTabIconItem() {
        Object member = this.tabObject.getMember("icon");
        if (member == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
        }
        IItemStack createItem = ItemUtils.createItem((String) member);
        IItem item = createItem != null ? createItem.getItem() : null;
        if (item == null) {
            Intrinsics.throwNpe();
        }
        return item;
    }

    @NotNull
    public String getTranslatedTabLabel() {
        Object member = this.tabObject.getMember("name");
        if (member == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
        }
        return (String) member;
    }

    public void displayAllReleventItems(@NotNull List<IItemStack> items) {
        Intrinsics.checkParameterIsNotNull(items, "items");
        List<IItemStack> $this$forEach$iv = items;
        for (Object element$iv : $this$forEach$iv) {
            IItemStack it = (IItemStack) element$iv;
            items.add(it);
        }
    }
}
