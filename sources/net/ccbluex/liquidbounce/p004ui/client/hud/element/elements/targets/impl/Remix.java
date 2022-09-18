package net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.impl;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.Target;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.TargetStyle;
import net.ccbluex.liquidbounce.p004ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

/* compiled from: Remix.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\u0018��2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0014\u0010\t\u001a\u0004\u0018\u00010\n2\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0016¨\u0006\u000b"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/targets/impl/Remix;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/targets/TargetStyle;", "inst", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Target;", "(Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Target;)V", "drawTarget", "", "entity", "Lnet/minecraft/entity/player/EntityPlayer;", "getBorder", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.elements.targets.impl.Remix */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/targets/impl/Remix.class */
public final class Remix extends TargetStyle {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Remix(@NotNull Target inst) {
        super("Remix", inst, false);
        Intrinsics.checkNotNullParameter(inst, "inst");
    }

    @Override // net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.TargetStyle
    public void drawTarget(@NotNull EntityPlayer entity) {
        int j;
        Intrinsics.checkNotNullParameter(entity, "entity");
        updateAnim(entity.func_110143_aJ());
        RenderUtils.newDrawRect(0.0f, 0.0f, 146.0f, 49.0f, getColor(new Color(25, 25, 25)).getRGB());
        RenderUtils.newDrawRect(1.0f, 1.0f, 145.0f, 48.0f, getColor(new Color(35, 35, 35)).getRGB());
        Color darker = Color.red.darker().darker();
        Intrinsics.checkNotNullExpressionValue(darker, "red.darker().darker()");
        RenderUtils.newDrawRect(4.0f, 40.0f, 142.0f, 45.0f, getColor(darker).getRGB());
        RenderUtils.newDrawRect(4.0f, 40.0f, 4.0f + (RangesKt.coerceIn(getEasingHealth() / entity.func_110138_aP(), 0.0f, 1.0f) * 138.0f), 45.0f, getTargetInstance().getBarColor().getRGB());
        RenderUtils.newDrawRect(4.0f, 4.0f, 38.0f, 38.0f, getColor(new Color(150, 150, 150)).getRGB());
        RenderUtils.newDrawRect(5.0f, 5.0f, 37.0f, 37.0f, getColor(new Color(0, 0, 0)).getRGB());
        Color darker2 = Color.blue.darker();
        Intrinsics.checkNotNullExpressionValue(darker2, "blue.darker()");
        RenderUtils.newDrawRect(40.0f, 36.0f, 141.5f, 38.0f, getColor(darker2).getRGB());
        Color blue = Color.blue;
        Intrinsics.checkNotNullExpressionValue(blue, "blue");
        RenderUtils.newDrawRect(40.0f, 36.0f, 40.0f + (RangesKt.coerceIn(entity.func_70658_aO() / 20.0f, 0.0f, 1.0f) * 101.5f), 38.0f, getColor(blue).getRGB());
        RenderUtils.newDrawRect(40.0f, 16.0f, 58.0f, 34.0f, getColor(new Color(25, 25, 25)).getRGB());
        RenderUtils.newDrawRect(41.0f, 17.0f, 57.0f, 33.0f, getColor(new Color(95, 95, 95)).getRGB());
        RenderUtils.newDrawRect(60.0f, 16.0f, 78.0f, 34.0f, getColor(new Color(25, 25, 25)).getRGB());
        RenderUtils.newDrawRect(61.0f, 17.0f, 77.0f, 33.0f, getColor(new Color(95, 95, 95)).getRGB());
        RenderUtils.newDrawRect(80.0f, 16.0f, 98.0f, 34.0f, getColor(new Color(25, 25, 25)).getRGB());
        RenderUtils.newDrawRect(81.0f, 17.0f, 97.0f, 33.0f, getColor(new Color(95, 95, 95)).getRGB());
        RenderUtils.newDrawRect(100.0f, 16.0f, 118.0f, 34.0f, getColor(new Color(25, 25, 25)).getRGB());
        RenderUtils.newDrawRect(101.0f, 17.0f, 117.0f, 33.0f, getColor(new Color(95, 95, 95)).getRGB());
        Fonts.minecraftFont.func_175063_a(entity.func_70005_c_(), 41.0f, 5.0f, getColor(-1).getRGB());
        if (MinecraftInstance.f362mc.func_147114_u().func_175102_a(entity.func_110124_au()) != null) {
            ResourceLocation func_178837_g = MinecraftInstance.f362mc.func_147114_u().func_175102_a(entity.func_110124_au()).func_178837_g();
            Intrinsics.checkNotNullExpressionValue(func_178837_g, "mc.netHandler.getPlayerI…ty.uniqueID).locationSkin");
            drawHead(func_178837_g, 5, 5, 32, 32, 1.0f - getTargetInstance().getFadeProgress());
            int responseTime = MinecraftInstance.f362mc.func_147114_u().func_175102_a(entity.func_110124_au()).func_178853_c();
            String stringTime = RangesKt.coerceAtLeast(responseTime, 0) + "ms";
            if (responseTime < 0) {
                j = 5;
            } else if (responseTime < 150) {
                j = 0;
            } else if (responseTime < 300) {
                j = 1;
            } else if (responseTime < 600) {
                j = 2;
            } else if (responseTime < 1000) {
                j = 3;
            } else {
                j = 4;
            }
            MinecraftInstance.f362mc.func_110434_K().func_110577_a(Gui.field_110324_m);
            RenderUtils.drawTexturedModalRect(132, 18, 0, 176 + (j * 8), 10, 8, 100.0f);
            GL11.glPushMatrix();
            GL11.glTranslatef(142.0f - (Fonts.minecraftFont.func_78256_a(stringTime) / 2.0f), 28.0f, 0.0f);
            GL11.glScalef(0.5f, 0.5f, 0.5f);
            Fonts.minecraftFont.func_175063_a(stringTime, 0.0f, 0.0f, getColor(-1).getRGB());
            GL11.glPopMatrix();
        }
        GL11.glPushMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f - getTargetInstance().getFadeProgress());
        RenderHelper.func_74520_c();
        RenderItem renderItem = MinecraftInstance.f362mc.func_175599_af();
        int x = 41;
        int i = 3;
        do {
            int index = i;
            i--;
            ItemStack stack = entity.field_71071_by.field_70460_b[index];
            if (stack != null && stack.func_77973_b() != null) {
                renderItem.func_180450_b(stack, x, 17);
                x += 20;
            }
        } while (0 <= i);
        RenderHelper.func_74518_a();
        GlStateManager.func_179141_d();
        GlStateManager.func_179084_k();
        GlStateManager.func_179140_f();
        GlStateManager.func_179129_p();
        GL11.glPopMatrix();
    }

    @Override // net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.TargetStyle
    @Nullable
    public Border getBorder(@Nullable EntityPlayer entity) {
        return new Border(0.0f, 0.0f, 146.0f, 49.0f);
    }
}
