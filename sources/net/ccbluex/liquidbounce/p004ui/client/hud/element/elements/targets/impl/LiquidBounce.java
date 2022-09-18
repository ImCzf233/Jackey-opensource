package net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.impl;

import com.viaversion.viaversion.libs.javassist.bytecode.Opcode;
import java.awt.Color;
import java.text.DecimalFormat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.Target;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.TargetStyle;
import net.ccbluex.liquidbounce.p004ui.font.Fonts;
import net.ccbluex.liquidbounce.p004ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.extensions.PlayerExtension;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: LiquidBounce.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018��2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u001cH\u0016J\u0014\u0010 \u001a\u0004\u0018\u00010!2\b\u0010\u001f\u001a\u0004\u0018\u00010\u001cH\u0016J\u0010\u0010\"\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u001cH\u0016J\u0010\u0010#\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u001cH\u0016J\u0010\u0010$\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u001cH\u0016R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n��\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\u0006¢\u0006\b\n��\u001a\u0004\b\n\u0010\bR\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n��\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u000f\u001a\u00020\u0006¢\u0006\b\n��\u001a\u0004\b\u0010\u0010\bR\u0011\u0010\u0011\u001a\u00020\u0006¢\u0006\b\n��\u001a\u0004\b\u0012\u0010\bR\u0011\u0010\u0013\u001a\u00020\u0014¢\u0006\b\n��\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0017\u001a\u00020\u0018¢\u0006\b\n��\u001a\u0004\b\u0019\u0010\u001aR\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0082\u000e¢\u0006\u0002\n��¨\u0006%"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/targets/impl/LiquidBounce;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/targets/TargetStyle;", "inst", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Target;", "(Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Target;)V", "borderAlphaValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "getBorderAlphaValue", "()Lnet/ccbluex/liquidbounce/value/IntegerValue;", "borderBlueValue", "getBorderBlueValue", "borderColorMode", "Lnet/ccbluex/liquidbounce/value/ListValue;", "getBorderColorMode", "()Lnet/ccbluex/liquidbounce/value/ListValue;", "borderGreenValue", "getBorderGreenValue", "borderRedValue", "getBorderRedValue", "borderWidthValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "getBorderWidthValue", "()Lnet/ccbluex/liquidbounce/value/FloatValue;", "hurtTimeAnim", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "getHurtTimeAnim", "()Lnet/ccbluex/liquidbounce/value/BoolValue;", "lastTarget", "Lnet/minecraft/entity/player/EntityPlayer;", "drawTarget", "", "entity", "getBorder", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "handleBlur", "handleShadow", "handleShadowCut", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.elements.targets.impl.LiquidBounce */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/targets/impl/LiquidBounce.class */
public final class LiquidBounce extends TargetStyle {
    @NotNull
    private final BoolValue hurtTimeAnim = new BoolValue("HurtTimeAnim", true, new LiquidBounce$hurtTimeAnim$1(this));
    @NotNull
    private final ListValue borderColorMode = new ListValue("Border-Color", new String[]{"Custom", "MatchBar", "None"}, "None", new LiquidBounce$borderColorMode$1(this));
    @NotNull
    private final FloatValue borderWidthValue = new FloatValue("Border-Width", 3.0f, 0.5f, 5.0f, new LiquidBounce$borderWidthValue$1(this));
    @NotNull
    private final IntegerValue borderRedValue = new IntegerValue("Border-Red", 0, 0, 255, new LiquidBounce$borderRedValue$1(this));
    @NotNull
    private final IntegerValue borderGreenValue = new IntegerValue("Border-Green", 0, 0, 255, new LiquidBounce$borderGreenValue$1(this));
    @NotNull
    private final IntegerValue borderBlueValue = new IntegerValue("Border-Blue", 0, 0, 255, new LiquidBounce$borderBlueValue$1(this));
    @NotNull
    private final IntegerValue borderAlphaValue = new IntegerValue("Border-Alpha", 0, 0, 255, new LiquidBounce$borderAlphaValue$1(this));
    @Nullable
    private EntityPlayer lastTarget;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LiquidBounce(@NotNull Target inst) {
        super("LiquidBounce", inst, true);
        Intrinsics.checkNotNullParameter(inst, "inst");
    }

    @NotNull
    public final BoolValue getHurtTimeAnim() {
        return this.hurtTimeAnim;
    }

    @NotNull
    public final ListValue getBorderColorMode() {
        return this.borderColorMode;
    }

    @NotNull
    public final FloatValue getBorderWidthValue() {
        return this.borderWidthValue;
    }

    @NotNull
    public final IntegerValue getBorderRedValue() {
        return this.borderRedValue;
    }

    @NotNull
    public final IntegerValue getBorderGreenValue() {
        return this.borderGreenValue;
    }

    @NotNull
    public final IntegerValue getBorderBlueValue() {
        return this.borderBlueValue;
    }

    @NotNull
    public final IntegerValue getBorderAlphaValue() {
        return this.borderAlphaValue;
    }

    @Override // net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.TargetStyle
    public void drawTarget(@NotNull EntityPlayer entity) {
        Intrinsics.checkNotNullParameter(entity, "entity");
        if (!Intrinsics.areEqual(entity, this.lastTarget) || getEasingHealth() < 0.0f || getEasingHealth() > entity.func_110138_aP() || Math.abs(getEasingHealth() - entity.func_110143_aJ()) < 0.01d) {
            setEasingHealth(entity.func_110143_aJ());
        }
        GameFontRenderer gameFontRenderer = Fonts.font40;
        String func_70005_c_ = entity.func_70005_c_();
        Intrinsics.checkNotNullExpressionValue(func_70005_c_, "entity.name");
        float width = RangesKt.coerceAtLeast(38 + gameFontRenderer.func_78256_a(func_70005_c_), 118);
        Color borderColor = getColor(new Color(this.borderRedValue.get().intValue(), this.borderGreenValue.get().intValue(), this.borderBlueValue.get().intValue(), this.borderAlphaValue.get().intValue()));
        if (StringsKt.equals(this.borderColorMode.get(), "none", true)) {
            RenderUtils.drawRect(0.0f, 0.0f, width, 36.0f, getTargetInstance().getBgColor().getRGB());
        } else {
            RenderUtils.drawBorderedRect(0.0f, 0.0f, width, 36.0f, this.borderWidthValue.get().floatValue(), StringsKt.equals(this.borderColorMode.get(), "matchbar", true) ? getTargetInstance().getBarColor().getRGB() : borderColor.getRGB(), getTargetInstance().getBgColor().getRGB());
        }
        if (getEasingHealth() > entity.func_110143_aJ()) {
            RenderUtils.drawRect(0.0f, 34.0f, (getEasingHealth() / entity.func_110138_aP()) * width, 36.0f, getColor(new Color(252, 185, 65)).getRGB());
        }
        RenderUtils.drawRect(0.0f, 34.0f, (entity.func_110143_aJ() / entity.func_110138_aP()) * width, 36.0f, getTargetInstance().getBarColor().getRGB());
        if (getEasingHealth() < entity.func_110143_aJ()) {
            RenderUtils.drawRect((getEasingHealth() / entity.func_110138_aP()) * width, 34.0f, (entity.func_110143_aJ() / entity.func_110138_aP()) * width, 36.0f, getColor(new Color(44, (int) Opcode.JSR_W, 144)).getRGB());
        }
        updateAnim(entity.func_110143_aJ());
        Fonts.font40.func_78276_b(entity.func_70005_c_(), 36, 3, getColor(-1).getRGB());
        GameFontRenderer gameFontRenderer2 = Fonts.font35;
        DecimalFormat decimalFormat = getDecimalFormat();
        Entity entity2 = MinecraftInstance.f362mc.field_71439_g;
        Intrinsics.checkNotNullExpressionValue(entity2, "mc.thePlayer");
        gameFontRenderer2.func_78276_b(Intrinsics.stringPlus("Distance: ", decimalFormat.format(PlayerExtension.getDistanceToEntityBox(entity2, (Entity) entity))), 36, 15, getColor(-1).getRGB());
        NetworkPlayerInfo playerInfo = MinecraftInstance.f362mc.func_147114_u().func_175102_a(entity.func_110124_au());
        if (playerInfo != null) {
            Fonts.font35.func_78276_b(Intrinsics.stringPlus("Ping: ", Integer.valueOf(RangesKt.coerceAtLeast(playerInfo.func_178853_c(), 0))), 36, 24, getColor(-1).getRGB());
            ResourceLocation locationSkin = playerInfo.func_178837_g();
            if (this.hurtTimeAnim.get().booleanValue()) {
                float scaleHT = RangesKt.coerceIn(entity.field_70737_aN / RangesKt.coerceAtLeast(entity.field_70738_aO, 1), 0.0f, 1.0f);
                Intrinsics.checkNotNullExpressionValue(locationSkin, "locationSkin");
                TargetStyle.drawHead$default(this, locationSkin, 2.0f + (15.0f * scaleHT * 0.2f), 2.0f + (15.0f * scaleHT * 0.2f), 1.0f - (scaleHT * 0.2f), 30, 30, 1.0f, 0.4f + ((1.0f - scaleHT) * 0.6f), 0.4f + ((1.0f - scaleHT) * 0.6f), 0.0f, 512, null);
            } else {
                Intrinsics.checkNotNullExpressionValue(locationSkin, "locationSkin");
                TargetStyle.drawHead$default(this, locationSkin, 0, 0, 30, 30, 1.0f - getTargetInstance().getFadeProgress(), 6, null);
            }
        }
        this.lastTarget = entity;
    }

    @Override // net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.TargetStyle
    public void handleBlur(@NotNull EntityPlayer entity) {
        Intrinsics.checkNotNullParameter(entity, "entity");
        GameFontRenderer gameFontRenderer = Fonts.font40;
        String func_70005_c_ = entity.func_70005_c_();
        Intrinsics.checkNotNullExpressionValue(func_70005_c_, "entity.name");
        float width = RangesKt.coerceAtLeast(38 + gameFontRenderer.func_78256_a(func_70005_c_), 118);
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179120_a(770, 771, 1, 0);
        RenderUtils.quickDrawRect(0.0f, 0.0f, width, 36.0f);
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
        float width = RangesKt.coerceAtLeast(38 + gameFontRenderer.func_78256_a(func_70005_c_), 118);
        RenderUtils.newDrawRect(0.0f, 0.0f, width, 36.0f, getShadowOpaque().getRGB());
    }

    @Override // net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.TargetStyle
    @Nullable
    public Border getBorder(@Nullable EntityPlayer entity) {
        if (entity == null) {
            return new Border(0.0f, 0.0f, 118.0f, 36.0f);
        }
        GameFontRenderer gameFontRenderer = Fonts.font40;
        String func_70005_c_ = entity.func_70005_c_();
        Intrinsics.checkNotNullExpressionValue(func_70005_c_, "entity.name");
        float width = RangesKt.coerceAtLeast(38 + gameFontRenderer.func_78256_a(func_70005_c_), 118);
        return new Border(0.0f, 0.0f, width, 36.0f);
    }
}
