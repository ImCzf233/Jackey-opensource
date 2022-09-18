package net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.impl;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.Target;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.TargetStyle;
import net.ccbluex.liquidbounce.p004ui.font.Fonts;
import net.ccbluex.liquidbounce.p004ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.p004ui.font.TTFFontRenderer;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.extensions.ColorExtension;
import net.ccbluex.liquidbounce.utils.extensions.PlayerExtension;
import net.ccbluex.liquidbounce.utils.render.BlendUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

/* compiled from: Exhibition.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\u0018��2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0014\u0010\t\u001a\u0004\u0018\u00010\n2\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0016¨\u0006\u000b"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/targets/impl/Exhibition;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/targets/TargetStyle;", "inst", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Target;", "(Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Target;)V", "drawTarget", "", "entity", "Lnet/minecraft/entity/player/EntityPlayer;", "getBorder", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.elements.targets.impl.Exhibition */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/targets/impl/Exhibition.class */
public final class Exhibition extends TargetStyle {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Exhibition(@NotNull Target inst) {
        super("Exhibition", inst, false);
        Intrinsics.checkNotNullParameter(inst, "inst");
    }

    @Override // net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.TargetStyle
    public void drawTarget(@NotNull EntityPlayer entity) {
        String func_70005_c_;
        Intrinsics.checkNotNullParameter(entity, "entity");
        GameFontRenderer font = Fonts.fontTahoma;
        Intrinsics.checkNotNullExpressionValue(entity.func_70005_c_(), "entity.name");
        float minWidth = RangesKt.coerceAtLeast(126.0f, 47.0f + font.func_78256_a(func_70005_c_));
        RenderUtils.drawExhiRect(0.0f, 0.0f, minWidth, 45.0f, 1.0f - getTargetInstance().getFadeProgress());
        RenderUtils.drawRect(2.5f, 2.5f, 42.5f, 42.5f, getColor(new Color(59, 59, 59)).getRGB());
        RenderUtils.drawRect(3.0f, 3.0f, 42.0f, 42.0f, getColor(new Color(19, 19, 19)).getRGB());
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f - getTargetInstance().getFadeProgress());
        RenderUtils.drawEntityOnScreen(22, 40, 16, (EntityLivingBase) entity);
        font.func_78276_b(entity.func_70005_c_(), 46, 5, getColor(-1).getRGB());
        float barLength = 70.0f * RangesKt.coerceIn(entity.func_110143_aJ() / entity.func_110138_aP(), 0.0f, 1.0f);
        Color healthColor = BlendUtils.getHealthColor(entity.func_110143_aJ(), entity.func_110138_aP());
        Intrinsics.checkNotNullExpressionValue(healthColor, "getHealthColor(entity.health, entity.maxHealth)");
        RenderUtils.drawRect(45.0f, 14.0f, 115.0f, 18.0f, getColor(ColorExtension.darker(healthColor, 0.3f)).getRGB());
        Color healthColor2 = BlendUtils.getHealthColor(entity.func_110143_aJ(), entity.func_110138_aP());
        Intrinsics.checkNotNullExpressionValue(healthColor2, "getHealthColor(entity.health, entity.maxHealth)");
        RenderUtils.drawRect(45.0f, 14.0f, 45.0f + barLength, 18.0f, getColor(healthColor2).getRGB());
        int i = 0;
        while (i < 10) {
            int i2 = i;
            i++;
            Color black = Color.black;
            Intrinsics.checkNotNullExpressionValue(black, "black");
            RenderUtils.drawRectBasedBorder(45.0f + (i2 * 7.0f), 14.0f, 45.0f + ((i2 + 1) * 7.0f), 18.0f, 0.5f, getColor(black).getRGB());
        }
        TTFFontRenderer tTFFontRenderer = Fonts.fontTahomaSmall;
        StringBuilder append = new StringBuilder().append("HP:").append((int) entity.func_110143_aJ()).append(" | Dist:");
        Entity entity2 = MinecraftInstance.f362mc.field_71439_g;
        Intrinsics.checkNotNullExpressionValue(entity2, "mc.thePlayer");
        tTFFontRenderer.drawString(append.append((int) PlayerExtension.getDistanceToEntityBox(entity2, (Entity) entity)).toString(), 45.0f, 21.0f, getColor(-1).getRGB());
        GlStateManager.func_179117_G();
        GL11.glPushMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f - getTargetInstance().getFadeProgress());
        GlStateManager.func_179091_B();
        GlStateManager.func_179147_l();
        GlStateManager.func_179120_a(770, 771, 1, 0);
        RenderHelper.func_74520_c();
        RenderItem renderItem = MinecraftInstance.f362mc.func_175599_af();
        int x = 45;
        int i3 = 3;
        do {
            int index = i3;
            i3--;
            ItemStack stack = entity.field_71071_by.field_70460_b[index];
            if (stack != null && stack.func_77973_b() != null) {
                renderItem.func_175042_a(stack, x, 28);
                renderItem.func_175030_a(MinecraftInstance.f362mc.field_71466_p, stack, x, 28);
                RenderUtils.drawExhiEnchants(stack, x, 28);
                x += 16;
            }
        } while (0 <= i3);
        ItemStack mainStack = entity.func_70694_bm();
        if (mainStack != null && mainStack.func_77973_b() != null) {
            renderItem.func_175042_a(mainStack, x, 28);
            renderItem.func_175030_a(MinecraftInstance.f362mc.field_71466_p, mainStack, x, 28);
            RenderUtils.drawExhiEnchants(mainStack, x, 28);
        }
        RenderHelper.func_74518_a();
        GlStateManager.func_179101_C();
        GlStateManager.func_179141_d();
        GlStateManager.func_179084_k();
        GlStateManager.func_179140_f();
        GlStateManager.func_179129_p();
        GL11.glPopMatrix();
    }

    @Override // net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.TargetStyle
    @Nullable
    public Border getBorder(@Nullable EntityPlayer entity) {
        if (entity == null) {
            return new Border(0.0f, 0.0f, 126.0f, 45.0f);
        }
        GameFontRenderer font = Fonts.fontTahoma;
        String func_70005_c_ = entity.func_70005_c_();
        Intrinsics.checkNotNullExpressionValue(func_70005_c_, "entity.name");
        float minWidth = RangesKt.coerceAtLeast(126.0f, 47.0f + font.func_78256_a(func_70005_c_));
        return new Border(0.0f, 0.0f, minWidth, 45.0f);
    }
}
