package net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.Target;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.TargetStyle;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.utils.CharRenderer;
import net.ccbluex.liquidbounce.p004ui.font.Fonts;
import net.ccbluex.liquidbounce.p004ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.extensions.ColorExtension;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.Stencil;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

/* compiled from: Chill.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\t\u0018��2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0014\u0010\u0018\u001a\u0004\u0018\u00010\u00192\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016J\u0010\u0010\u001a\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0010\u0010\u001b\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0010\u0010\u001c\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J&\u0010\u001d\u001a\u00020\u00152\u0006\u0010\u001e\u001a\u00020\u00062\u0006\u0010\u001f\u001a\u00020\u00062\u0006\u0010 \u001a\u00020\u00062\u0006\u0010!\u001a\u00020\u0006R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\t\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n��R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n��\u001a\u0004\b\f\u0010\rR\u0011\u0010\u000e\u001a\u00020\u000f¢\u0006\b\n��\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n��¨\u0006\""}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/targets/impl/Chill;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/targets/TargetStyle;", "inst", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Target;", "(Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Target;)V", "calcScaleX", "", "calcScaleY", "calcTranslateX", "calcTranslateY", "chillFontSpeed", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "getChillFontSpeed", "()Lnet/ccbluex/liquidbounce/value/FloatValue;", "chillRoundValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "getChillRoundValue", "()Lnet/ccbluex/liquidbounce/value/BoolValue;", "numberRenderer", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/targets/utils/CharRenderer;", "drawTarget", "", "entity", "Lnet/minecraft/entity/player/EntityPlayer;", "getBorder", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "handleBlur", "handleShadow", "handleShadowCut", "updateData", "_a", "_b", "_c", "_d", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.elements.targets.impl.Chill */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/targets/impl/Chill.class */
public final class Chill extends TargetStyle {
    @NotNull
    private final FloatValue chillFontSpeed = new FloatValue("Chill-FontSpeed", 0.5f, 0.01f, 1.0f, new Chill$chillFontSpeed$1(this));
    @NotNull
    private final BoolValue chillRoundValue = new BoolValue("Chill-RoundedBar", true, new Chill$chillRoundValue$1(this));
    @NotNull
    private final CharRenderer numberRenderer = new CharRenderer(false);
    private float calcScaleX;
    private float calcScaleY;
    private float calcTranslateX;
    private float calcTranslateY;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Chill(@NotNull Target inst) {
        super("Chill", inst, true);
        Intrinsics.checkNotNullParameter(inst, "inst");
    }

    @NotNull
    public final FloatValue getChillFontSpeed() {
        return this.chillFontSpeed;
    }

    @NotNull
    public final BoolValue getChillRoundValue() {
        return this.chillRoundValue;
    }

    public final void updateData(float _a, float _b, float _c, float _d) {
        this.calcTranslateX = _a;
        this.calcTranslateY = _b;
        this.calcScaleX = _c;
        this.calcScaleY = _d;
    }

    @Override // net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.TargetStyle
    public void drawTarget(@NotNull EntityPlayer entity) {
        Intrinsics.checkNotNullParameter(entity, "entity");
        updateAnim(entity.func_110143_aJ());
        String name = entity.func_70005_c_();
        float health = entity.func_110143_aJ();
        GameFontRenderer gameFontRenderer = Fonts.font40;
        Intrinsics.checkNotNullExpressionValue(name, "name");
        int func_78256_a = gameFontRenderer.func_78256_a(name);
        GameFontRenderer gameFontRenderer2 = Fonts.font72;
        String format = getDecimalFormat().format(Float.valueOf(health));
        Intrinsics.checkNotNullExpressionValue(format, "decimalFormat.format(health)");
        float tWidth = RangesKt.coerceAtLeast(45.0f + RangesKt.coerceAtLeast(func_78256_a, gameFontRenderer2.func_78256_a(format)), 120.0f);
        NetworkPlayerInfo playerInfo = MinecraftInstance.f362mc.func_147114_u().func_175102_a(entity.func_110124_au());
        RenderUtils.drawRoundedRect(0.0f, 0.0f, tWidth, 48.0f, 7.0f, getTargetInstance().getBgColor().getRGB());
        GlStateManager.func_179117_G();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        if (playerInfo != null) {
            Stencil.write(false);
            GL11.glDisable(3553);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            RenderUtils.fastRoundedRect(4.0f, 4.0f, 34.0f, 34.0f, 7.0f);
            GL11.glDisable(3042);
            GL11.glEnable(3553);
            Stencil.erase(true);
            ResourceLocation func_178837_g = playerInfo.func_178837_g();
            Intrinsics.checkNotNullExpressionValue(func_178837_g, "playerInfo.locationSkin");
            drawHead(func_178837_g, 4, 4, 30, 30, 1.0f - getTargetInstance().getFadeProgress());
            Stencil.dispose();
        }
        GlStateManager.func_179117_G();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        Fonts.font40.drawString(name, 38.0f, 6.0f, getColor(-1).getRGB());
        this.numberRenderer.renderChar(health, this.calcTranslateX, this.calcTranslateY, 38.0f, 17.0f, this.calcScaleX, this.calcScaleY, false, this.chillFontSpeed.get().floatValue(), getColor(-1).getRGB());
        RenderUtils.drawRoundedRect(4.0f, 38.0f, tWidth - 4.0f, 44.0f, 3.0f, ColorExtension.darker(getTargetInstance().getBarColor(), 0.5f).getRGB());
        Stencil.write(false);
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        RenderUtils.fastRoundedRect(4.0f, 38.0f, tWidth - 4.0f, 44.0f, 3.0f);
        GL11.glDisable(3042);
        Stencil.erase(true);
        if (this.chillRoundValue.get().booleanValue()) {
            RenderUtils.customRounded(4.0f, 38.0f, 4.0f + ((getEasingHealth() / entity.func_110138_aP()) * (tWidth - 8.0f)), 44.0f, 0.0f, 3.0f, 3.0f, 0.0f, getTargetInstance().getBarColor().getRGB());
        } else {
            RenderUtils.drawRect(4.0f, 38.0f, 4.0f + ((getEasingHealth() / entity.func_110138_aP()) * (tWidth - 8.0f)), 44.0f, getTargetInstance().getBarColor().getRGB());
        }
        Stencil.dispose();
    }

    @Override // net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.TargetStyle
    public void handleBlur(@NotNull EntityPlayer entity) {
        Intrinsics.checkNotNullParameter(entity, "entity");
        GameFontRenderer gameFontRenderer = Fonts.font40;
        String func_70005_c_ = entity.func_70005_c_();
        Intrinsics.checkNotNullExpressionValue(func_70005_c_, "entity.name");
        int func_78256_a = gameFontRenderer.func_78256_a(func_70005_c_);
        GameFontRenderer gameFontRenderer2 = Fonts.font72;
        String format = getDecimalFormat().format(Float.valueOf(entity.func_110143_aJ()));
        Intrinsics.checkNotNullExpressionValue(format, "decimalFormat.format(entity.health)");
        float tWidth = RangesKt.coerceAtLeast(45.0f + RangesKt.coerceAtLeast(func_78256_a, gameFontRenderer2.func_78256_a(format)), 120.0f);
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179120_a(770, 771, 1, 0);
        RenderUtils.fastRoundedRect(0.0f, 0.0f, tWidth, 48.0f, 7.0f);
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
        GameFontRenderer gameFontRenderer = Fonts.font40;
        String func_70005_c_ = entity.func_70005_c_();
        Intrinsics.checkNotNullExpressionValue(func_70005_c_, "entity.name");
        int func_78256_a = gameFontRenderer.func_78256_a(func_70005_c_);
        GameFontRenderer gameFontRenderer2 = Fonts.font72;
        String format = getDecimalFormat().format(Float.valueOf(entity.func_110143_aJ()));
        Intrinsics.checkNotNullExpressionValue(format, "decimalFormat.format(entity.health)");
        float tWidth = RangesKt.coerceAtLeast(45.0f + RangesKt.coerceAtLeast(func_78256_a, gameFontRenderer2.func_78256_a(format)), 120.0f);
        RenderUtils.originalRoundedRect(0.0f, 0.0f, tWidth, 48.0f, 7.0f, getShadowOpaque().getRGB());
    }

    @Override // net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.TargetStyle
    @Nullable
    public Border getBorder(@Nullable EntityPlayer entity) {
        if (entity == null) {
            return new Border(0.0f, 0.0f, 120.0f, 48.0f);
        }
        GameFontRenderer gameFontRenderer = Fonts.font40;
        String func_70005_c_ = entity.func_70005_c_();
        Intrinsics.checkNotNullExpressionValue(func_70005_c_, "entity.name");
        int func_78256_a = gameFontRenderer.func_78256_a(func_70005_c_);
        GameFontRenderer gameFontRenderer2 = Fonts.font72;
        String format = getDecimalFormat().format(Float.valueOf(entity.func_110143_aJ()));
        Intrinsics.checkNotNullExpressionValue(format, "decimalFormat.format(entity.health)");
        float tWidth = RangesKt.coerceAtLeast(45.0f + RangesKt.coerceAtLeast(func_78256_a, gameFontRenderer2.func_78256_a(format)), 120.0f);
        return new Border(0.0f, 0.0f, tWidth, 48.0f);
    }
}
