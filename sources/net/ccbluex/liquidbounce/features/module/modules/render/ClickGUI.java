package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.color.ColorMixer;
import net.ccbluex.liquidbounce.p004ui.client.clickgui.style.styles.BlackStyle;
import net.ccbluex.liquidbounce.p004ui.client.clickgui.style.styles.LiquidBounceStyle;
import net.ccbluex.liquidbounce.p004ui.client.clickgui.style.styles.NullStyle;
import net.ccbluex.liquidbounce.p004ui.client.clickgui.style.styles.SlowlyStyle;
import net.ccbluex.liquidbounce.p004ui.client.clickgui.style.styles.WhiteStyle;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.apache.log4j.spi.Configurator;

@ModuleInfo(name = "ClickGUI", description = "Opens the ClickGUI.", category = ModuleCategory.RENDER, keyBind = 54, forceNoSound = true, onlyEnable = true)
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/render/ClickGUI.class */
public class ClickGUI extends Module {
    private static final ListValue colorModeValue = new ListValue("Color", new String[]{"Custom", "Sky", "Rainbow", "LiquidSlowly", "Fade", "Mixer"}, "Custom");
    private static final IntegerValue colorRedValue = new IntegerValue("Red", 0, 0, 255);
    private static final IntegerValue colorGreenValue = new IntegerValue("Green", 160, 0, 255);
    private static final IntegerValue colorBlueValue = new IntegerValue("Blue", 255, 0, 255);
    private static final FloatValue saturationValue = new FloatValue("Saturation", 1.0f, 0.0f, 1.0f);
    private static final FloatValue brightnessValue = new FloatValue("Brightness", 1.0f, 0.0f, 1.0f);
    private static final IntegerValue mixerSecondsValue = new IntegerValue("Seconds", 2, 1, 10);
    private final ListValue styleValue = new ListValue("Style", new String[]{"LiquidBounce", "Null", "Slowly", "Black", "White"}, "Null") { // from class: net.ccbluex.liquidbounce.features.module.modules.render.ClickGUI.1
        public void onChanged(String oldValue, String newValue) {
            ClickGUI.this.updateStyle();
        }
    };
    public final FloatValue scaleValue = new FloatValue("Scale", 1.0f, 0.7f, 2.0f);
    public final IntegerValue maxElementsValue = new IntegerValue("MaxElements", 15, 1, 20);
    public final ListValue backgroundValue = new ListValue("Background", new String[]{"Default", "Gradient", "None"}, "Default");
    public final IntegerValue gradStartValue = new IntegerValue("GradientStartAlpha", 255, 0, 255, () -> {
        return Boolean.valueOf(this.backgroundValue.get().equalsIgnoreCase("gradient"));
    });
    public final IntegerValue gradEndValue = new IntegerValue("GradientEndAlpha", 0, 0, 255, () -> {
        return Boolean.valueOf(this.backgroundValue.get().equalsIgnoreCase("gradient"));
    });
    public final ListValue animationValue = new ListValue("Animation", new String[]{"Azura", "Slide", "SlideBounce", "Zoom", "ZoomBounce", "None"}, "Azura");
    public final FloatValue animSpeedValue = new FloatValue("AnimSpeed", 1.0f, 0.01f, 5.0f, "x");

    public static Color generateColor() {
        Color c = new Color(255, 255, 255, 255);
        String lowerCase = colorModeValue.get().toLowerCase();
        boolean z = true;
        switch (lowerCase.hashCode()) {
            case -1349088399:
                if (lowerCase.equals("custom")) {
                    z = false;
                    break;
                }
                break;
            case -132200566:
                if (lowerCase.equals("liquidslowly")) {
                    z = true;
                    break;
                }
                break;
            case 113953:
                if (lowerCase.equals("sky")) {
                    z = true;
                    break;
                }
                break;
            case 3135100:
                if (lowerCase.equals("fade")) {
                    z = true;
                    break;
                }
                break;
            case 103910409:
                if (lowerCase.equals("mixer")) {
                    z = true;
                    break;
                }
                break;
            case 973576630:
                if (lowerCase.equals("rainbow")) {
                    z = true;
                    break;
                }
                break;
        }
        switch (z) {
            case false:
                c = new Color(colorRedValue.get().intValue(), colorGreenValue.get().intValue(), colorBlueValue.get().intValue());
                break;
            case true:
                c = new Color(RenderUtils.getRainbowOpaque(mixerSecondsValue.get().intValue(), saturationValue.get().floatValue(), brightnessValue.get().floatValue(), 0));
                break;
            case true:
                c = RenderUtils.skyRainbow(0, saturationValue.get().floatValue(), brightnessValue.get().floatValue());
                break;
            case true:
                c = ColorUtils.LiquidSlowly(System.nanoTime(), 0, saturationValue.get().floatValue(), brightnessValue.get().floatValue());
                break;
            case true:
                c = ColorUtils.fade(new Color(colorRedValue.get().intValue(), colorGreenValue.get().intValue(), colorBlueValue.get().intValue()), 0, 100);
                break;
            case true:
                c = ColorMixer.getMixedColor(0, mixerSecondsValue.get().intValue());
                break;
        }
        return c;
    }

    @Override // net.ccbluex.liquidbounce.features.module.Module
    public void onEnable() {
        updateStyle();
        LiquidBounce.clickGui.progress = 0.0d;
        LiquidBounce.clickGui.slide = 0.0d;
        LiquidBounce.clickGui.lastMS = System.currentTimeMillis();
        f362mc.func_147108_a(LiquidBounce.clickGui);
    }

    public void updateStyle() {
        String lowerCase = this.styleValue.get().toLowerCase();
        boolean z = true;
        switch (lowerCase.hashCode()) {
            case -899450034:
                if (lowerCase.equals("slowly")) {
                    z = true;
                    break;
                }
                break;
            case -615955772:
                if (lowerCase.equals("liquidbounce")) {
                    z = false;
                    break;
                }
                break;
            case 3392903:
                if (lowerCase.equals(Configurator.NULL)) {
                    z = true;
                    break;
                }
                break;
            case 93818879:
                if (lowerCase.equals("black")) {
                    z = true;
                    break;
                }
                break;
            case 113101865:
                if (lowerCase.equals("white")) {
                    z = true;
                    break;
                }
                break;
        }
        switch (z) {
            case false:
                LiquidBounce.clickGui.style = new LiquidBounceStyle();
                return;
            case true:
                LiquidBounce.clickGui.style = new NullStyle();
                return;
            case true:
                LiquidBounce.clickGui.style = new SlowlyStyle();
                return;
            case true:
                LiquidBounce.clickGui.style = new BlackStyle();
                return;
            case true:
                LiquidBounce.clickGui.style = new WhiteStyle();
                return;
            default:
                return;
        }
    }
}
