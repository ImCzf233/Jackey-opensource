package net.ccbluex.liquidbounce.p004ui.client.hud.element.elements;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/* compiled from: Armor.kt */
@ElementInfo(name = "Armor")
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0007\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\b\u0007\u0018��2\u00020\u0001B-\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\b\u0010\r\u001a\u00020\u000eH\u0016R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\f\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n��¨\u0006\u000f"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Armor;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "x", "", "y", "scale", "", "side", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side;", "(DDFLnet/ccbluex/liquidbounce/ui/client/hud/element/Side;)V", "alignment", "Lnet/ccbluex/liquidbounce/value/ListValue;", "modeValue", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.elements.Armor */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/Armor.class */
public final class Armor extends Element {
    @NotNull
    private final ListValue modeValue;
    @NotNull
    private final ListValue alignment;

    public Armor() {
        this(0.0d, 0.0d, 0.0f, null, 15, null);
    }

    public /* synthetic */ Armor(double d, double d2, float f, Side side, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? -8.0d : d, (i & 2) != 0 ? 57.0d : d2, (i & 4) != 0 ? 1.0f : f, (i & 8) != 0 ? new Side(Side.Horizontal.MIDDLE, Side.Vertical.DOWN) : side);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Armor(double x, double y, float scale, @NotNull Side side) {
        super(x, y, scale, side);
        Intrinsics.checkNotNullParameter(side, "side");
        this.modeValue = new ListValue("Mode", new String[]{"LiquidBounce", "Exhibition"}, "LiquidBounce");
        this.alignment = new ListValue("Alignment", new String[]{"Horizontal", "Vertical"}, "Horizontal");
    }

    @Override // net.ccbluex.liquidbounce.p004ui.client.hud.element.Element
    @NotNull
    public Border drawElement() {
        ItemStack mainStack;
        if (MinecraftInstance.f362mc.field_71442_b.func_78762_g()) {
            RenderItem renderItem = MinecraftInstance.f362mc.func_175599_af();
            boolean isInsideWater = MinecraftInstance.f362mc.field_71439_g.func_70055_a(Material.field_151586_h);
            String mode = this.modeValue.get();
            String align = this.alignment.get();
            int x = 1;
            int y = isInsideWater ? -10 : 0;
            RenderHelper.func_74520_c();
            int i = 3;
            do {
                int index = i;
                i--;
                ItemStack stack = MinecraftInstance.f362mc.field_71439_g.field_71071_by.field_70460_b[index];
                if (stack != null) {
                    renderItem.func_175042_a(stack, x, y);
                    renderItem.func_175030_a(MinecraftInstance.f362mc.field_71466_p, stack, x, y);
                    if (StringsKt.equals(mode, "Exhibition", true)) {
                        RenderUtils.drawExhiEnchants(stack, x, y);
                        if (StringsKt.equals(align, "Horizontal", true)) {
                            x += 16;
                        } else if (StringsKt.equals(align, "Vertical", true)) {
                            y += 16;
                        }
                    } else if (StringsKt.equals(align, "Horizontal", true)) {
                        x += 18;
                    } else if (StringsKt.equals(align, "Vertical", true)) {
                        y += 18;
                    }
                }
            } while (0 <= i);
            if (StringsKt.equals(mode, "Exhibition", true) && (mainStack = MinecraftInstance.f362mc.field_71439_g.func_70694_bm()) != null && mainStack.func_77973_b() != null) {
                renderItem.func_175042_a(mainStack, x, y);
                renderItem.func_175030_a(MinecraftInstance.f362mc.field_71466_p, mainStack, x, y);
                RenderUtils.drawExhiEnchants(mainStack, x, y);
            }
            RenderHelper.func_74518_a();
            GlStateManager.func_179141_d();
            GlStateManager.func_179084_k();
            GlStateManager.func_179140_f();
            GlStateManager.func_179129_p();
        }
        if (StringsKt.equals(this.modeValue.get(), "Exhibition", true)) {
            if (StringsKt.equals(this.alignment.get(), "Horizontal", true)) {
                return new Border(0.0f, 0.0f, 80.0f, 17.0f);
            }
            return new Border(0.0f, 0.0f, 18.0f, 80.0f);
        } else if (StringsKt.equals(this.alignment.get(), "Horizontal", true)) {
            return new Border(0.0f, 0.0f, 72.0f, 17.0f);
        } else {
            return new Border(0.0f, 0.0f, 18.0f, 72.0f);
        }
    }
}
