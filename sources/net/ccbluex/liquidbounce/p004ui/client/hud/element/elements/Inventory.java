package net.ccbluex.liquidbounce.p004ui.client.hud.element.elements;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Inventory.kt */
@ElementInfo(name = "Inventory")
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n��\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B#\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\b\u0010\u001a\u001a\u00020\u001bH\u0016J\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u0010\u0010 \u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u0010\u0010!\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J \u0010\"\u001a\u00020\u001d2\u0006\u0010#\u001a\u00020$2\u0006\u0010\u0002\u001a\u00020\u00152\u0006\u0010\u0004\u001a\u00020\u0015H\u0002R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\f\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u000f\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0010\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0011\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0012\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0013\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u000e¢\u0006\u0002\n��R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0018\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0019\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��¨\u0006%"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Inventory;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "x", "", "y", "scale", "", "(DDF)V", "Mode", "Lnet/ccbluex/liquidbounce/value/ListValue;", "alpha", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "blueValue", "bordRad", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "bordalpha", "bordblueValue", "bordgreenValue", "bordredValue", "greenValue", "inventoryRows", "", "lowerInv", "Lnet/minecraft/inventory/IInventory;", "redValue", "width", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "renderInventory1", "", "player", "Lnet/minecraft/entity/player/EntityPlayer;", "renderInventory2", "renderInventory3", "renderItemStack", "stack", "Lnet/minecraft/item/ItemStack;", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.elements.Inventory */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/Inventory.class */
public final class Inventory extends Element {
    private int inventoryRows;
    @Nullable
    private final IInventory lowerInv;
    @NotNull
    private final ListValue Mode;
    @NotNull
    private final IntegerValue width;
    @NotNull
    private final IntegerValue redValue;
    @NotNull
    private final IntegerValue greenValue;
    @NotNull
    private final IntegerValue blueValue;
    @NotNull
    private final IntegerValue alpha;
    @NotNull
    private final IntegerValue bordredValue;
    @NotNull
    private final IntegerValue bordgreenValue;
    @NotNull
    private final IntegerValue bordblueValue;
    @NotNull
    private final IntegerValue bordalpha;
    @NotNull
    private final FloatValue bordRad;

    public Inventory() {
        this(0.0d, 0.0d, 0.0f, 7, null);
    }

    public Inventory(double x, double y, float scale) {
        super(x, y, scale, null, 8, null);
        this.Mode = new ListValue("Background-Mode", new String[]{"Bordered", "Rounded"}, "Bordered");
        this.width = new IntegerValue("BorderWidth", 1, 0, 10);
        this.redValue = new IntegerValue("Red", 0, 0, 255);
        this.greenValue = new IntegerValue("Green", 0, 0, 255);
        this.blueValue = new IntegerValue("Blue", 0, 0, 255);
        this.alpha = new IntegerValue("Alpha", 120, 0, 255);
        this.bordredValue = new IntegerValue("BorderRed", 255, 0, 255);
        this.bordgreenValue = new IntegerValue("BorderGreen", 255, 0, 255);
        this.bordblueValue = new IntegerValue("BorderBlue", 255, 0, 255);
        this.bordalpha = new IntegerValue("BorderAlpha", 255, 0, 255);
        this.bordRad = new FloatValue("BorderRadius", 3.0f, 0.0f, 10.0f);
    }

    public /* synthetic */ Inventory(double d, double d2, float f, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 10.0d : d, (i & 2) != 0 ? 10.0d : d2, (i & 4) != 0 ? 1.0f : f);
    }

    @Override // net.ccbluex.liquidbounce.p004ui.client.hud.element.Element
    @NotNull
    public Border drawElement() {
        if (Intrinsics.areEqual(this.Mode.get(), "Rounded")) {
            RenderUtils.drawRoundedRect(0.0f, (this.inventoryRows * 18.0f) + 17.0f, 176.0f, 96.0f, this.bordRad.get().floatValue(), new Color(this.redValue.get().intValue(), this.greenValue.get().intValue(), this.blueValue.get().intValue(), this.alpha.get().intValue()).getRGB());
        }
        if (Intrinsics.areEqual(this.Mode.get(), "Bordered")) {
            RenderUtils.drawBorderedRect(0.0f, (this.inventoryRows * 18.0f) + 17.0f, 176.0f, 96.0f, this.width.get().intValue(), new Color(this.bordredValue.get().intValue(), this.bordgreenValue.get().intValue(), this.bordblueValue.get().intValue(), this.bordalpha.get().intValue()).getRGB(), new Color(this.redValue.get().intValue(), this.greenValue.get().intValue(), this.blueValue.get().intValue(), this.alpha.get().intValue()).getRGB());
        }
        if (this.lowerInv != null) {
            this.inventoryRows = this.lowerInv.func_70302_i_();
        }
        EntityPlayerSP entityPlayerSP = MinecraftInstance.f362mc.field_71439_g;
        Intrinsics.checkNotNullExpressionValue(entityPlayerSP, "mc.thePlayer");
        renderInventory1((EntityPlayer) entityPlayerSP);
        EntityPlayerSP entityPlayerSP2 = MinecraftInstance.f362mc.field_71439_g;
        Intrinsics.checkNotNullExpressionValue(entityPlayerSP2, "mc.thePlayer");
        renderInventory2((EntityPlayer) entityPlayerSP2);
        EntityPlayerSP entityPlayerSP3 = MinecraftInstance.f362mc.field_71439_g;
        Intrinsics.checkNotNullExpressionValue(entityPlayerSP3, "mc.thePlayer");
        renderInventory3((EntityPlayer) entityPlayerSP3);
        return new Border(0.0f, (this.inventoryRows * 18.0f) + 17.0f, 176.0f, 96.0f);
    }

    private final void renderInventory1(EntityPlayer player) {
        ItemStack[] itemStackArr = player.field_71071_by.field_70462_a;
        int xOffset = 8;
        ItemStack[] renderStack = player.field_71071_by.field_70462_a;
        int i = 9;
        while (i < 18) {
            int index = i;
            i++;
            ItemStack armourStack = renderStack[index];
            if (armourStack != null) {
                renderItemStack(armourStack, xOffset, 30);
            }
            xOffset += 18;
        }
    }

    private final void renderInventory2(EntityPlayer player) {
        ItemStack[] itemStackArr = player.field_71071_by.field_70462_a;
        int xOffset = 8;
        ItemStack[] renderStack = player.field_71071_by.field_70462_a;
        int i = 18;
        while (i < 27) {
            int index = i;
            i++;
            ItemStack armourStack = renderStack[index];
            if (armourStack != null) {
                renderItemStack(armourStack, xOffset, 48);
            }
            xOffset += 18;
        }
    }

    private final void renderInventory3(EntityPlayer player) {
        ItemStack[] itemStackArr = player.field_71071_by.field_70462_a;
        int xOffset = 8;
        ItemStack[] renderStack = player.field_71071_by.field_70462_a;
        int i = 27;
        while (i < 36) {
            int index = i;
            i++;
            ItemStack armourStack = renderStack[index];
            if (armourStack != null) {
                renderItemStack(armourStack, xOffset, 66);
            }
            xOffset += 18;
        }
    }

    private final void renderItemStack(ItemStack stack, int x, int y) {
        GlStateManager.func_179094_E();
        GlStateManager.func_179091_B();
        GlStateManager.func_179147_l();
        GlStateManager.func_179120_a(770, 771, 1, 0);
        RenderHelper.func_74520_c();
        MinecraftInstance.f362mc.func_175599_af().func_180450_b(stack, x, y);
        MinecraftInstance.f362mc.func_175599_af().func_175030_a(MinecraftInstance.f362mc.field_71466_p, stack, x, y);
        RenderHelper.func_74518_a();
        GlStateManager.func_179101_C();
        GlStateManager.func_179084_k();
        GlStateManager.func_179121_F();
    }
}
