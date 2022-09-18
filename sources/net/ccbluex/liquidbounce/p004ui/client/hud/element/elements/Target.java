package net.ccbluex.liquidbounce.p004ui.client.hud.element.elements;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.modules.color.ColorMixer;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.features.module.modules.combat.TeleportAura;
import net.ccbluex.liquidbounce.p004ui.client.hud.designer.GuiHudDesigner;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.TargetStyle;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.impl.Chill;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.impl.Exhibition;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.impl.LiquidBounce;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.impl.Remix;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.impl.Rice;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.impl.Slowly;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.BlendUtils;
import net.ccbluex.liquidbounce.utils.render.BlurUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.ShadowUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.ccbluex.liquidbounce.value.Value;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

/* compiled from: Target.kt */
@ElementInfo(name = "Target", disableScale = true, retrieveDamage = true)
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u001b\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n��\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J'\u0010]\u001a\b\u0012\u0004\u0012\u00020^0X2\u0012\u0010_\u001a\n\u0012\u0006\b\u0001\u0012\u00020R0`\"\u00020RH\u0007¢\u0006\u0002\u0010aJ\n\u0010b\u001a\u0004\u0018\u00010cH\u0016J\u0010\u0010d\u001a\u0004\u0018\u00010R2\u0006\u0010e\u001a\u00020^J\u0006\u0010f\u001a\u00020\u0004J\u0010\u0010g\u001a\u00020h2\u0006\u0010i\u001a\u000205H\u0016R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u0011\u0010\u000f\u001a\u00020\u0010¢\u0006\b\n��\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0013\u001a\u00020\u0010¢\u0006\b\n��\u001a\u0004\b\u0014\u0010\u0012R\u001a\u0010\u0015\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u0016\u0010\f\"\u0004\b\u0017\u0010\u000eR\u0011\u0010\u0018\u001a\u00020\u0010¢\u0006\b\n��\u001a\u0004\b\u0019\u0010\u0012R\u0011\u0010\u001a\u001a\u00020\u0010¢\u0006\b\n��\u001a\u0004\b\u001b\u0010\u0012R\u0011\u0010\u001c\u001a\u00020\u0010¢\u0006\b\n��\u001a\u0004\b\u001d\u0010\u0012R\u0011\u0010\u001e\u001a\u00020\u001f¢\u0006\b\n��\u001a\u0004\b \u0010!R\u0011\u0010\"\u001a\u00020#¢\u0006\b\n��\u001a\u0004\b$\u0010%R\u0011\u0010&\u001a\u00020\u001f¢\u0006\b\n��\u001a\u0004\b'\u0010!R\u0011\u0010(\u001a\u00020)¢\u0006\b\n��\u001a\u0004\b*\u0010+R\u0011\u0010,\u001a\u00020\u001f¢\u0006\b\n��\u001a\u0004\b-\u0010!R\u0011\u0010.\u001a\u00020#¢\u0006\b\n��\u001a\u0004\b/\u0010%R\u0011\u00100\u001a\u00020\u001f¢\u0006\b\n��\u001a\u0004\b1\u0010!R\u0011\u00102\u001a\u00020\u0010¢\u0006\b\n��\u001a\u0004\b3\u0010\u0012R\u001c\u00104\u001a\u0004\u0018\u000105X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b6\u00107\"\u0004\b8\u00109R\u0011\u0010:\u001a\u00020#¢\u0006\b\n��\u001a\u0004\b;\u0010%R\u0011\u0010<\u001a\u00020\u0010¢\u0006\b\n��\u001a\u0004\b=\u0010\u0012R\u0011\u0010>\u001a\u00020#¢\u0006\b\n��\u001a\u0004\b?\u0010%R\u0011\u0010@\u001a\u00020\u001f¢\u0006\b\n��\u001a\u0004\bA\u0010!R\u0011\u0010B\u001a\u00020\u0010¢\u0006\b\n��\u001a\u0004\bC\u0010\u0012R\u0011\u0010D\u001a\u00020\u0010¢\u0006\b\n��\u001a\u0004\bE\u0010\u0012R\u0011\u0010F\u001a\u00020)¢\u0006\b\n��\u001a\u0004\bG\u0010+R\u0011\u0010H\u001a\u00020\u0010¢\u0006\b\n��\u001a\u0004\bI\u0010\u0012R\u0011\u0010J\u001a\u00020\u001f¢\u0006\b\n��\u001a\u0004\bK\u0010!R\u0011\u0010L\u001a\u00020#¢\u0006\b\n��\u001a\u0004\bM\u0010%R\u0011\u0010N\u001a\u00020#¢\u0006\b\n��\u001a\u0004\bO\u0010%R\u0017\u0010P\u001a\b\u0012\u0004\u0012\u00020R0Q¢\u0006\b\n��\u001a\u0004\bS\u0010TR\u0011\u0010U\u001a\u00020)¢\u0006\b\n��\u001a\u0004\bV\u0010+R\u001e\u0010W\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030Y0X8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bZ\u0010TR\u0011\u0010[\u001a\u00020\u0010¢\u0006\b\n��\u001a\u0004\b\\\u0010\u0012¨\u0006j"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Target;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "()V", "animProgress", "", "getAnimProgress", "()F", "setAnimProgress", "(F)V", "barColor", "Ljava/awt/Color;", "getBarColor", "()Ljava/awt/Color;", "setBarColor", "(Ljava/awt/Color;)V", "bgAlphaValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "getBgAlphaValue", "()Lnet/ccbluex/liquidbounce/value/IntegerValue;", "bgBlueValue", "getBgBlueValue", "bgColor", "getBgColor", "setBgColor", "bgGreenValue", "getBgGreenValue", "bgRedValue", "getBgRedValue", "blueValue", "getBlueValue", "blurStrength", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "getBlurStrength", "()Lnet/ccbluex/liquidbounce/value/FloatValue;", "blurValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "getBlurValue", "()Lnet/ccbluex/liquidbounce/value/BoolValue;", "brightnessValue", "getBrightnessValue", "colorModeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "getColorModeValue", "()Lnet/ccbluex/liquidbounce/value/ListValue;", "fadeSpeed", "getFadeSpeed", "fadeValue", "getFadeValue", "globalAnimSpeed", "getGlobalAnimSpeed", "greenValue", "getGreenValue", "mainTarget", "Lnet/minecraft/entity/player/EntityPlayer;", "getMainTarget", "()Lnet/minecraft/entity/player/EntityPlayer;", "setMainTarget", "(Lnet/minecraft/entity/player/EntityPlayer;)V", "noAnimValue", "getNoAnimValue", "redValue", "getRedValue", "resetBar", "getResetBar", "saturationValue", "getSaturationValue", "shadowColorBlueValue", "getShadowColorBlueValue", "shadowColorGreenValue", "getShadowColorGreenValue", "shadowColorMode", "getShadowColorMode", "shadowColorRedValue", "getShadowColorRedValue", "shadowStrength", "getShadowStrength", "shadowValue", "getShadowValue", "showWithChatOpen", "getShowWithChatOpen", "styleList", "", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/targets/TargetStyle;", "getStyleList", "()Ljava/util/List;", "styleValue", "getStyleValue", "values", "", "Lnet/ccbluex/liquidbounce/value/Value;", "getValues", "waveSecondValue", "getWaveSecondValue", "addStyles", "", "styles", "", "([Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/targets/TargetStyle;)Ljava/util/List;", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "getCurrentStyle", "styleName", "getFadeProgress", "handleDamage", "", "ent", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.elements.Target */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/Target.class */
public final class Target extends Element {
    @NotNull
    private final ListValue styleValue;
    @Nullable
    private EntityPlayer mainTarget;
    private float animProgress;
    @NotNull
    private Color barColor;
    @NotNull
    private Color bgColor;
    @NotNull
    private final List<TargetStyle> styleList = new ArrayList();
    @NotNull
    private final BoolValue blurValue = new BoolValue("Blur", false);
    @NotNull
    private final FloatValue blurStrength = new FloatValue("Blur-Strength", 1.0f, 0.01f, 40.0f, new Target$blurStrength$1(this));
    @NotNull
    private final BoolValue shadowValue = new BoolValue("Shadow", false);
    @NotNull
    private final FloatValue shadowStrength = new FloatValue("Shadow-Strength", 1.0f, 0.01f, 40.0f, new Target$shadowStrength$1(this));
    @NotNull
    private final ListValue shadowColorMode = new ListValue("Shadow-Color", new String[]{"Background", "Custom", "Bar"}, "Background", new Target$shadowColorMode$1(this));
    @NotNull
    private final IntegerValue shadowColorRedValue = new IntegerValue("Shadow-Red", 0, 0, 255, new Target$shadowColorRedValue$1(this));
    @NotNull
    private final IntegerValue shadowColorGreenValue = new IntegerValue("Shadow-Green", 111, 0, 255, new Target$shadowColorGreenValue$1(this));
    @NotNull
    private final IntegerValue shadowColorBlueValue = new IntegerValue("Shadow-Blue", 255, 0, 255, new Target$shadowColorBlueValue$1(this));
    @NotNull
    private final BoolValue fadeValue = new BoolValue("FadeAnim", false);
    @NotNull
    private final FloatValue fadeSpeed = new FloatValue("Fade-Speed", 1.0f, 0.0f, 5.0f, new Target$fadeSpeed$1(this));
    @NotNull
    private final BoolValue noAnimValue = new BoolValue("No-Animation", false);
    @NotNull
    private final FloatValue globalAnimSpeed = new FloatValue("Global-AnimSpeed", 3.0f, 1.0f, 9.0f, new Target$globalAnimSpeed$1(this));
    @NotNull
    private final BoolValue showWithChatOpen = new BoolValue("Show-ChatOpen", true);
    @NotNull
    private final BoolValue resetBar = new BoolValue("ResetBarWhenHiding", false);
    @NotNull
    private final ListValue colorModeValue = new ListValue("Color", new String[]{"Custom", "Rainbow", "Sky", "Slowly", "Fade", "Mixer", "Health"}, "Custom");
    @NotNull
    private final IntegerValue redValue = new IntegerValue("Red", 252, 0, 255);
    @NotNull
    private final IntegerValue greenValue = new IntegerValue("Green", 96, 0, 255);
    @NotNull
    private final IntegerValue blueValue = new IntegerValue("Blue", 66, 0, 255);
    @NotNull
    private final FloatValue saturationValue = new FloatValue("Saturation", 1.0f, 0.0f, 1.0f);
    @NotNull
    private final FloatValue brightnessValue = new FloatValue("Brightness", 1.0f, 0.0f, 1.0f);
    @NotNull
    private final IntegerValue waveSecondValue = new IntegerValue("Seconds", 2, 1, 10);
    @NotNull
    private final IntegerValue bgRedValue = new IntegerValue("Background-Red", 0, 0, 255);
    @NotNull
    private final IntegerValue bgGreenValue = new IntegerValue("Background-Green", 0, 0, 255);
    @NotNull
    private final IntegerValue bgBlueValue = new IntegerValue("Background-Blue", 0, 0, 255);
    @NotNull
    private final IntegerValue bgAlphaValue = new IntegerValue("Background-Alpha", 160, 0, 255);

    public Target() {
        super(0.0d, 0.0d, 0.0f, null, 15, null);
        Collection $this$toTypedArray$iv = addStyles(new LiquidBounce(this), new Chill(this), new Rice(this), new Exhibition(this), new Remix(this), new Slowly(this));
        Object[] array = $this$toTypedArray$iv.toArray(new String[0]);
        if (array == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
        }
        this.styleValue = new ListValue("Style", (String[]) array, "LiquidBounce");
        this.barColor = new Color(-1);
        this.bgColor = new Color(-1);
    }

    @NotNull
    public final List<TargetStyle> getStyleList() {
        return this.styleList;
    }

    @NotNull
    public final ListValue getStyleValue() {
        return this.styleValue;
    }

    @NotNull
    public final BoolValue getBlurValue() {
        return this.blurValue;
    }

    @NotNull
    public final FloatValue getBlurStrength() {
        return this.blurStrength;
    }

    @NotNull
    public final BoolValue getShadowValue() {
        return this.shadowValue;
    }

    @NotNull
    public final FloatValue getShadowStrength() {
        return this.shadowStrength;
    }

    @NotNull
    public final ListValue getShadowColorMode() {
        return this.shadowColorMode;
    }

    @NotNull
    public final IntegerValue getShadowColorRedValue() {
        return this.shadowColorRedValue;
    }

    @NotNull
    public final IntegerValue getShadowColorGreenValue() {
        return this.shadowColorGreenValue;
    }

    @NotNull
    public final IntegerValue getShadowColorBlueValue() {
        return this.shadowColorBlueValue;
    }

    @NotNull
    public final BoolValue getFadeValue() {
        return this.fadeValue;
    }

    @NotNull
    public final FloatValue getFadeSpeed() {
        return this.fadeSpeed;
    }

    @NotNull
    public final BoolValue getNoAnimValue() {
        return this.noAnimValue;
    }

    @NotNull
    public final FloatValue getGlobalAnimSpeed() {
        return this.globalAnimSpeed;
    }

    @NotNull
    public final BoolValue getShowWithChatOpen() {
        return this.showWithChatOpen;
    }

    @NotNull
    public final BoolValue getResetBar() {
        return this.resetBar;
    }

    @NotNull
    public final ListValue getColorModeValue() {
        return this.colorModeValue;
    }

    @NotNull
    public final IntegerValue getRedValue() {
        return this.redValue;
    }

    @NotNull
    public final IntegerValue getGreenValue() {
        return this.greenValue;
    }

    @NotNull
    public final IntegerValue getBlueValue() {
        return this.blueValue;
    }

    @NotNull
    public final FloatValue getSaturationValue() {
        return this.saturationValue;
    }

    @NotNull
    public final FloatValue getBrightnessValue() {
        return this.brightnessValue;
    }

    @NotNull
    public final IntegerValue getWaveSecondValue() {
        return this.waveSecondValue;
    }

    @NotNull
    public final IntegerValue getBgRedValue() {
        return this.bgRedValue;
    }

    @NotNull
    public final IntegerValue getBgGreenValue() {
        return this.bgGreenValue;
    }

    @NotNull
    public final IntegerValue getBgBlueValue() {
        return this.bgBlueValue;
    }

    @NotNull
    public final IntegerValue getBgAlphaValue() {
        return this.bgAlphaValue;
    }

    @Override // net.ccbluex.liquidbounce.p004ui.client.hud.element.Element
    @NotNull
    public List<Value<?>> getValues() {
        List valueList = new ArrayList();
        Iterable $this$forEach$iv = this.styleList;
        for (Object element$iv : $this$forEach$iv) {
            TargetStyle it = (TargetStyle) element$iv;
            valueList.addAll(it.getValues());
        }
        return CollectionsKt.plus((Collection) CollectionsKt.toMutableList((Collection) super.getValues()), (Iterable) valueList);
    }

    @Nullable
    public final EntityPlayer getMainTarget() {
        return this.mainTarget;
    }

    public final void setMainTarget(@Nullable EntityPlayer entityPlayer) {
        this.mainTarget = entityPlayer;
    }

    public final float getAnimProgress() {
        return this.animProgress;
    }

    public final void setAnimProgress(float f) {
        this.animProgress = f;
    }

    @NotNull
    public final Color getBarColor() {
        return this.barColor;
    }

    public final void setBarColor(@NotNull Color color) {
        Intrinsics.checkNotNullParameter(color, "<set-?>");
        this.barColor = color;
    }

    @NotNull
    public final Color getBgColor() {
        return this.bgColor;
    }

    public final void setBgColor(@NotNull Color color) {
        Intrinsics.checkNotNullParameter(color, "<set-?>");
        this.bgColor = color;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // net.ccbluex.liquidbounce.p004ui.client.hud.element.Element
    @Nullable
    public Border drawElement() {
        EntityPlayer entityPlayer;
        Color color;
        TargetStyle mainStyle = getCurrentStyle(this.styleValue.get());
        if (mainStyle == null) {
            return null;
        }
        Module module = net.ccbluex.liquidbounce.LiquidBounce.INSTANCE.getModuleManager().get(KillAura.class);
        if (module == null) {
            throw new NullPointerException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.combat.KillAura");
        }
        EntityPlayer target = ((KillAura) module).getTarget();
        Module module2 = net.ccbluex.liquidbounce.LiquidBounce.INSTANCE.getModuleManager().get(TeleportAura.class);
        if (module2 == null) {
            throw new NullPointerException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.combat.TeleportAura");
        }
        EntityPlayer lastTarget = ((TeleportAura) module2).getLastTarget();
        if (target != null && (target instanceof EntityPlayer)) {
            entityPlayer = target;
        } else if (lastTarget == null || !(lastTarget instanceof EntityPlayer)) {
            entityPlayer = ((!(MinecraftInstance.f362mc.field_71462_r instanceof GuiChat) || !this.showWithChatOpen.get().booleanValue()) && !(MinecraftInstance.f362mc.field_71462_r instanceof GuiHudDesigner)) ? null : (EntityPlayer) MinecraftInstance.f362mc.field_71439_g;
        } else {
            entityPlayer = lastTarget;
        }
        EntityPlayer actualTarget = entityPlayer;
        String str = this.colorModeValue.get();
        switch (str.hashCode()) {
            case -2137395588:
                if (str.equals("Health")) {
                    if (actualTarget == null) {
                        color = Color.green;
                        break;
                    } else {
                        color = BlendUtils.getHealthColor(actualTarget.func_110143_aJ(), actualTarget.func_110138_aP());
                        break;
                    }
                }
                color = ColorUtils.LiquidSlowly(System.nanoTime(), 0, this.saturationValue.get().floatValue(), this.brightnessValue.get().floatValue());
                Intrinsics.checkNotNull(color);
                break;
            case -1656737386:
                if (str.equals("Rainbow")) {
                    color = new Color(RenderUtils.getRainbowOpaque(this.waveSecondValue.get().intValue(), this.saturationValue.get().floatValue(), this.brightnessValue.get().floatValue(), 0));
                    break;
                }
                color = ColorUtils.LiquidSlowly(System.nanoTime(), 0, this.saturationValue.get().floatValue(), this.brightnessValue.get().floatValue());
                Intrinsics.checkNotNull(color);
                break;
            case 83201:
                if (str.equals("Sky")) {
                    color = RenderUtils.skyRainbow(0, this.saturationValue.get().floatValue(), this.brightnessValue.get().floatValue());
                    break;
                }
                color = ColorUtils.LiquidSlowly(System.nanoTime(), 0, this.saturationValue.get().floatValue(), this.brightnessValue.get().floatValue());
                Intrinsics.checkNotNull(color);
                break;
            case 2181788:
                if (str.equals("Fade")) {
                    color = ColorUtils.fade(new Color(this.redValue.get().intValue(), this.greenValue.get().intValue(), this.blueValue.get().intValue()), 0, 100);
                    break;
                }
                color = ColorUtils.LiquidSlowly(System.nanoTime(), 0, this.saturationValue.get().floatValue(), this.brightnessValue.get().floatValue());
                Intrinsics.checkNotNull(color);
                break;
            case 74357737:
                if (str.equals("Mixer")) {
                    color = ColorMixer.getMixedColor(0, this.waveSecondValue.get().intValue());
                    break;
                }
                color = ColorUtils.LiquidSlowly(System.nanoTime(), 0, this.saturationValue.get().floatValue(), this.brightnessValue.get().floatValue());
                Intrinsics.checkNotNull(color);
                break;
            case 2029746065:
                if (str.equals("Custom")) {
                    color = new Color(this.redValue.get().intValue(), this.greenValue.get().intValue(), this.blueValue.get().intValue());
                    break;
                }
                color = ColorUtils.LiquidSlowly(System.nanoTime(), 0, this.saturationValue.get().floatValue(), this.brightnessValue.get().floatValue());
                Intrinsics.checkNotNull(color);
                break;
            default:
                color = ColorUtils.LiquidSlowly(System.nanoTime(), 0, this.saturationValue.get().floatValue(), this.brightnessValue.get().floatValue());
                Intrinsics.checkNotNull(color);
                break;
        }
        Color preBarColor = color;
        Color preBgColor = new Color(this.bgRedValue.get().intValue(), this.bgGreenValue.get().intValue(), this.bgBlueValue.get().intValue(), this.bgAlphaValue.get().intValue());
        if (this.fadeValue.get().booleanValue()) {
            this.animProgress += 0.0075f * this.fadeSpeed.get().floatValue() * RenderUtils.deltaTime * (actualTarget != null ? -1.0f : 1.0f);
        } else {
            this.animProgress = 0.0f;
        }
        this.animProgress = RangesKt.coerceIn(this.animProgress, 0.0f, 1.0f);
        Intrinsics.checkNotNullExpressionValue(preBarColor, "preBarColor");
        this.barColor = ColorUtils.reAlpha(preBarColor, (preBarColor.getAlpha() / 255.0f) * (1.0f - this.animProgress));
        this.bgColor = ColorUtils.reAlpha(preBgColor, (preBgColor.getAlpha() / 255.0f) * (1.0f - this.animProgress));
        if (actualTarget != null || !this.fadeValue.get().booleanValue()) {
            this.mainTarget = actualTarget;
        } else if (this.animProgress >= 1.0f) {
            this.mainTarget = null;
        }
        Border returnBorder = mainStyle.getBorder(this.mainTarget);
        if (returnBorder == null) {
            return null;
        }
        float borderWidth = returnBorder.getX2() - returnBorder.getX();
        float borderHeight = returnBorder.getY2() - returnBorder.getY();
        if (this.mainTarget == null) {
            if (this.resetBar.get().booleanValue()) {
                mainStyle.setEasingHealth(0.0f);
            }
            if (mainStyle instanceof Rice) {
                ((Rice) mainStyle).getParticleList().clear();
            }
            return returnBorder;
        }
        EntityPlayer convertTarget = this.mainTarget;
        Intrinsics.checkNotNull(convertTarget);
        float calcScaleX = this.animProgress * (4.0f / (borderWidth / 2.0f));
        float calcScaleY = this.animProgress * (4.0f / (borderHeight / 2.0f));
        float calcTranslateX = (borderWidth / 2.0f) * calcScaleX;
        float calcTranslateY = (borderHeight / 2.0f) * calcScaleY;
        if (this.shadowValue.get().booleanValue() && mainStyle.getShaderSupport()) {
            float renderX = (float) getRenderX();
            float renderY = (float) getRenderY();
            GL11.glTranslated(-getRenderX(), -getRenderY(), 0.0d);
            GL11.glPushMatrix();
            ShadowUtils.INSTANCE.shadow(this.shadowStrength.get().floatValue(), new Target$drawElement$1(this, calcTranslateX, calcTranslateY, calcScaleX, calcScaleY, mainStyle, convertTarget), new Target$drawElement$2(this, calcTranslateX, calcTranslateY, calcScaleX, calcScaleY, mainStyle, convertTarget));
            GL11.glPopMatrix();
            GL11.glTranslated(getRenderX(), getRenderY(), 0.0d);
        }
        if (this.blurValue.get().booleanValue() && mainStyle.getShaderSupport()) {
            float floatX = (float) getRenderX();
            float floatY = (float) getRenderY();
            GL11.glTranslated(-getRenderX(), -getRenderY(), 0.0d);
            GL11.glPushMatrix();
            BlurUtils.blur(floatX + returnBorder.getX(), floatY + returnBorder.getY(), floatX + returnBorder.getX2(), floatY + returnBorder.getY2(), this.blurStrength.get().floatValue() * (1.0f - this.animProgress), false, new Target$drawElement$3(this, calcTranslateX, calcTranslateY, calcScaleX, calcScaleY, mainStyle, convertTarget));
            GL11.glPopMatrix();
            GL11.glTranslated(getRenderX(), getRenderY(), 0.0d);
        }
        if (this.fadeValue.get().booleanValue()) {
            GL11.glPushMatrix();
            GL11.glTranslatef(calcTranslateX, calcTranslateY, 0.0f);
            GL11.glScalef(1.0f - calcScaleX, 1.0f - calcScaleY, 1.0f - calcScaleX);
        }
        if (mainStyle instanceof Chill) {
            ((Chill) mainStyle).updateData(((float) getRenderX()) + calcTranslateX, ((float) getRenderY()) + calcTranslateY, calcScaleX, calcScaleY);
        }
        mainStyle.drawTarget(convertTarget);
        if (this.fadeValue.get().booleanValue()) {
            GL11.glPopMatrix();
        }
        GlStateManager.func_179117_G();
        return returnBorder;
    }

    @Override // net.ccbluex.liquidbounce.p004ui.client.hud.element.Element
    public void handleDamage(@NotNull EntityPlayer ent) {
        Intrinsics.checkNotNullParameter(ent, "ent");
        if (this.mainTarget != null && Intrinsics.areEqual(ent, this.mainTarget)) {
            TargetStyle currentStyle = getCurrentStyle(this.styleValue.get());
            if (currentStyle == null) {
                return;
            }
            currentStyle.handleDamage(ent);
        }
    }

    public final float getFadeProgress() {
        return this.animProgress;
    }

    @SafeVarargs
    @NotNull
    public final List<String> addStyles(@NotNull TargetStyle... styles) {
        Intrinsics.checkNotNullParameter(styles, "styles");
        List nameList = new ArrayList();
        int i = 0;
        int length = styles.length;
        while (i < length) {
            TargetStyle targetStyle = styles[i];
            i++;
            getStyleList().add(targetStyle);
            nameList.add(targetStyle.getName());
        }
        return nameList;
    }

    @Nullable
    public final TargetStyle getCurrentStyle(@NotNull String styleName) {
        Object obj;
        Intrinsics.checkNotNullParameter(styleName, "styleName");
        Iterator<T> it = this.styleList.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            Object next = it.next();
            TargetStyle it2 = (TargetStyle) next;
            if (StringsKt.equals(it2.getName(), styleName, true)) {
                obj = next;
                break;
            }
        }
        return (TargetStyle) obj;
    }
}
