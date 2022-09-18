package net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.Target;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.TargetStyle;
import net.ccbluex.liquidbounce.p004ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Slowly.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018��2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0014\u0010\t\u001a\u0004\u0018\u00010\n2\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0016J\u0010\u0010\u000b\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\f\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\r\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\u000e"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/targets/impl/Slowly;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/targets/TargetStyle;", "inst", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Target;", "(Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Target;)V", "drawTarget", "", "entity", "Lnet/minecraft/entity/player/EntityPlayer;", "getBorder", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "handleBlur", "handleShadow", "handleShadowCut", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.elements.targets.impl.Slowly */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/targets/impl/Slowly.class */
public final class Slowly extends TargetStyle {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Slowly(@NotNull Target inst) {
        super("Slowly", inst, true);
        Intrinsics.checkNotNullParameter(inst, "inst");
    }

    @Override // net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.TargetStyle
    public void drawTarget(@NotNull EntityPlayer entity) {
        Intrinsics.checkNotNullParameter(entity, "entity");
        FontRenderer font = Fonts.minecraftFont;
        String healthString = Intrinsics.stringPlus(getDecimalFormat2().format(Float.valueOf(entity.func_110143_aJ())), " ❤");
        float length = RangesKt.coerceAtLeast(RangesKt.coerceAtLeast(60, font.func_78256_a(entity.func_70005_c_())), font.func_78256_a(healthString)) + 10.0f;
        updateAnim(entity.func_110143_aJ());
        RenderUtils.drawRect(0.0f, 0.0f, 32.0f + length, 36.0f, getTargetInstance().getBgColor().getRGB());
        if (MinecraftInstance.f362mc.func_147114_u().func_175102_a(entity.func_110124_au()) != null) {
            ResourceLocation func_178837_g = MinecraftInstance.f362mc.func_147114_u().func_175102_a(entity.func_110124_au()).func_178837_g();
            Intrinsics.checkNotNullExpressionValue(func_178837_g, "mc.netHandler.getPlayerI…ty.uniqueID).locationSkin");
            drawHead(func_178837_g, 1, 1, 30, 30, 1.0f - getTargetInstance().getFadeProgress());
        }
        font.func_175063_a(entity.func_70005_c_(), 33.0f, 2.0f, getColor(-1).getRGB());
        font.func_175063_a(healthString, (length + 31.0f) - font.func_78256_a(healthString), 22.0f, getTargetInstance().getBarColor().getRGB());
        RenderUtils.drawRect(0.0f, 32.0f, RangesKt.coerceIn(getEasingHealth() / entity.func_110138_aP(), 0.0f, entity.func_110138_aP()) * (length + 32.0f), 36.0f, getTargetInstance().getBarColor().getRGB());
    }

    @Override // net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.TargetStyle
    public void handleBlur(@NotNull EntityPlayer entity) {
        Intrinsics.checkNotNullParameter(entity, "entity");
        FontRenderer font = Fonts.minecraftFont;
        String healthString = Intrinsics.stringPlus(getDecimalFormat2().format(Float.valueOf(entity.func_110143_aJ())), " ❤");
        float length = RangesKt.coerceAtLeast(RangesKt.coerceAtLeast(60, font.func_78256_a(entity.func_70005_c_())), font.func_78256_a(healthString)) + 10.0f;
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179120_a(770, 771, 1, 0);
        RenderUtils.quickDrawRect(0.0f, 0.0f, 32.0f + length, 36.0f);
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
    }

    @Override // net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.TargetStyle
    public void handleShadowCut(@NotNull EntityPlayer entity) {
        Intrinsics.checkNotNullParameter(entity, "entity");
        handleBlur(entity);
    }

    @Override // net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.TargetStyle
    public void handleShadow(@NotNull EntityPlayer entity) {
        Intrinsics.checkNotNullParameter(entity, "entity");
        FontRenderer font = Fonts.minecraftFont;
        String healthString = Intrinsics.stringPlus(getDecimalFormat2().format(Float.valueOf(entity.func_110143_aJ())), " ❤");
        float length = RangesKt.coerceAtLeast(RangesKt.coerceAtLeast(60, font.func_78256_a(entity.func_70005_c_())), font.func_78256_a(healthString)) + 10.0f;
        RenderUtils.newDrawRect(0.0f, 0.0f, 32.0f + length, 36.0f, getShadowOpaque().getRGB());
    }

    @Override // net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.TargetStyle
    @Nullable
    public Border getBorder(@Nullable EntityPlayer entity) {
        if (entity == null) {
            return new Border(0.0f, 0.0f, 102.0f, 36.0f);
        }
        FontRenderer font = Fonts.minecraftFont;
        String healthString = Intrinsics.stringPlus(getDecimalFormat2().format(Float.valueOf(entity.func_110143_aJ())), " ❤");
        float length = RangesKt.coerceAtLeast(RangesKt.coerceAtLeast(60, font.func_78256_a(entity.func_70005_c_())), font.func_78256_a(healthString)) + 10.0f;
        return new Border(0.0f, 0.0f, 32.0f + length, 36.0f);
    }
}
