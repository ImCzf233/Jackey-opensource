package net.ccbluex.liquidbounce.features.module.modules.render;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;

/* compiled from: Chams.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0015\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n��\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n��\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\u0004¢\u0006\b\n��\u001a\u0004\b\f\u0010\u0006R\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n��\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\u0012¢\u0006\b\n��\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0015\u001a\u00020\b¢\u0006\b\n��\u001a\u0004\b\u0016\u0010\nR\u0011\u0010\u0017\u001a\u00020\u0004¢\u0006\b\n��\u001a\u0004\b\u0018\u0010\u0006R\u0011\u0010\u0019\u001a\u00020\u0012¢\u0006\b\n��\u001a\u0004\b\u001a\u0010\u0014R\u0011\u0010\u001b\u001a\u00020\u0012¢\u0006\b\n��\u001a\u0004\b\u001c\u0010\u0014R\u0011\u0010\u001d\u001a\u00020\u0004¢\u0006\b\n��\u001a\u0004\b\u001e\u0010\u0006R\u0011\u0010\u001f\u001a\u00020\u0004¢\u0006\b\n��\u001a\u0004\b \u0010\u0006R\u0011\u0010!\u001a\u00020\u000e¢\u0006\b\n��\u001a\u0004\b\"\u0010\u0010R\u0011\u0010#\u001a\u00020\u0012¢\u0006\b\n��\u001a\u0004\b$\u0010\u0014R\u0011\u0010%\u001a\u00020\u0012¢\u0006\b\n��\u001a\u0004\b&\u0010\u0014¨\u0006'"}, m53d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/Chams;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "alphaValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "getAlphaValue", "()Lnet/ccbluex/liquidbounce/value/IntegerValue;", "behindColorModeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "getBehindColorModeValue", "()Lnet/ccbluex/liquidbounce/value/ListValue;", "blueValue", "getBlueValue", "brightnessValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "getBrightnessValue", "()Lnet/ccbluex/liquidbounce/value/FloatValue;", "chestsValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "getChestsValue", "()Lnet/ccbluex/liquidbounce/value/BoolValue;", "colorModeValue", "getColorModeValue", "greenValue", "getGreenValue", "itemsValue", "getItemsValue", "legacyMode", "getLegacyMode", "mixerSecondsValue", "getMixerSecondsValue", "redValue", "getRedValue", "saturationValue", "getSaturationValue", "targetsValue", "getTargetsValue", "texturedValue", "getTexturedValue", "LiquidBounce"})
@ModuleInfo(name = "Chams", description = "Allows you to see targets through blocks.", category = ModuleCategory.RENDER)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/render/Chams.class */
public final class Chams extends Module {
    @NotNull
    private final BoolValue targetsValue = new BoolValue("Targets", true);
    @NotNull
    private final BoolValue chestsValue = new BoolValue("Chests", true);
    @NotNull
    private final BoolValue itemsValue = new BoolValue("Items", true);
    @NotNull
    private final BoolValue legacyMode = new BoolValue("Legacy-Mode", false);
    @NotNull
    private final BoolValue texturedValue = new BoolValue("Textured", true, new Chams$texturedValue$1(this));
    @NotNull
    private final ListValue colorModeValue = new ListValue("Color", new String[]{"Custom", "Rainbow", "Sky", "LiquidSlowly", "Fade", "Mixer"}, "Custom", new Chams$colorModeValue$1(this));
    @NotNull
    private final ListValue behindColorModeValue = new ListValue("Behind-Color", new String[]{"Same", "Opposite", "Red"}, "Same", new Chams$behindColorModeValue$1(this));
    @NotNull
    private final IntegerValue redValue = new IntegerValue("Red", 255, 0, 255, new Chams$redValue$1(this));
    @NotNull
    private final IntegerValue greenValue = new IntegerValue("Green", 255, 0, 255, new Chams$greenValue$1(this));
    @NotNull
    private final IntegerValue blueValue = new IntegerValue("Blue", 255, 0, 255, new Chams$blueValue$1(this));
    @NotNull
    private final IntegerValue alphaValue = new IntegerValue("Alpha", 255, 0, 255, new Chams$alphaValue$1(this));
    @NotNull
    private final FloatValue saturationValue = new FloatValue("Saturation", 1.0f, 0.0f, 1.0f, new Chams$saturationValue$1(this));
    @NotNull
    private final FloatValue brightnessValue = new FloatValue("Brightness", 1.0f, 0.0f, 1.0f, new Chams$brightnessValue$1(this));
    @NotNull
    private final IntegerValue mixerSecondsValue = new IntegerValue("Seconds", 2, 1, 10, new Chams$mixerSecondsValue$1(this));

    @NotNull
    public final BoolValue getTargetsValue() {
        return this.targetsValue;
    }

    @NotNull
    public final BoolValue getChestsValue() {
        return this.chestsValue;
    }

    @NotNull
    public final BoolValue getItemsValue() {
        return this.itemsValue;
    }

    @NotNull
    public final BoolValue getLegacyMode() {
        return this.legacyMode;
    }

    @NotNull
    public final BoolValue getTexturedValue() {
        return this.texturedValue;
    }

    @NotNull
    public final ListValue getColorModeValue() {
        return this.colorModeValue;
    }

    @NotNull
    public final ListValue getBehindColorModeValue() {
        return this.behindColorModeValue;
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
    public final IntegerValue getAlphaValue() {
        return this.alphaValue;
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
    public final IntegerValue getMixerSecondsValue() {
        return this.mixerSecondsValue;
    }
}
