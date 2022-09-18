package net.ccbluex.liquidbounce.p004ui.client.hud.element.elements;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.modules.color.ColorMixer;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.opengl.GL11;

/* compiled from: Arraylist.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\b\n��\n\u0002\u0010\u0002\n��\u0010��\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, m53d2 = {"<anonymous>", "", "invoke"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.elements.Arraylist$drawElement$6 */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/Arraylist$drawElement$6.class */
final class Arraylist$drawElement$6 extends Lambda implements Functions<Unit> {
    final /* synthetic */ Arraylist this$0;
    final /* synthetic */ FontRenderer $fontRenderer;
    final /* synthetic */ float $textHeight;
    final /* synthetic */ float $saturation;
    final /* synthetic */ float $brightness;
    final /* synthetic */ int[] $counter;
    final /* synthetic */ String $colorMode;
    final /* synthetic */ int $customColor;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Arraylist$drawElement$6(Arraylist $receiver, FontRenderer $fontRenderer, float $textHeight, float $saturation, float $brightness, int[] $counter, String $colorMode, int $customColor) {
        super(0);
        this.this$0 = $receiver;
        this.$fontRenderer = $fontRenderer;
        this.$textHeight = $textHeight;
        this.$saturation = $saturation;
        this.$brightness = $brightness;
        this.$counter = $counter;
        this.$colorMode = $colorMode;
        this.$customColor = $customColor;
    }

    @Override // kotlin.jvm.functions.Functions
    public final void invoke() {
        Iterable iterable;
        ListValue listValue;
        ListValue listValue2;
        ListValue listValue3;
        int i;
        IntegerValue integerValue;
        IntegerValue integerValue2;
        IntegerValue integerValue3;
        IntegerValue integerValue4;
        FloatValue floatValue;
        FloatValue floatValue2;
        IntegerValue integerValue5;
        FloatValue floatValue3;
        FloatValue floatValue4;
        IntegerValue integerValue6;
        IntegerValue integerValue7;
        IntegerValue integerValue8;
        FloatValue floatValue5;
        FloatValue floatValue6;
        IntegerValue integerValue9;
        IntegerValue integerValue10;
        IntegerValue integerValue11;
        IntegerValue integerValue12;
        IntegerValue integerValue13;
        GL11.glPushMatrix();
        GL11.glTranslated(this.this$0.getRenderX(), this.this$0.getRenderY(), 0.0d);
        iterable = this.this$0.modules;
        Iterable $this$forEachIndexed$iv = iterable;
        Arraylist arraylist = this.this$0;
        FontRenderer fontRenderer = this.$fontRenderer;
        float f = this.$textHeight;
        float f2 = this.$saturation;
        float f3 = this.$brightness;
        int[] iArr = this.$counter;
        String str = this.$colorMode;
        int i2 = this.$customColor;
        int index$iv = 0;
        for (Object item$iv : $this$forEachIndexed$iv) {
            int index = index$iv;
            index$iv = index + 1;
            if (index < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            Module module = (Module) item$iv;
            String displayString = arraylist.getModName(module);
            int width = fontRenderer.func_78256_a(displayString);
            float f4 = -(width - module.getSlide());
            listValue = arraylist.rectLeftValue;
            float xPos = f4 + (StringsKt.equals(listValue.get(), "left", true) ? 3 : 2);
            float arrayY = module.getArrayY();
            float f5 = xPos + width;
            listValue2 = arraylist.rectLeftValue;
            float f6 = f5 + (StringsKt.equals(listValue2.get(), "right", true) ? 3.0f : 2.0f);
            float arrayY2 = module.getArrayY() + f;
            listValue3 = arraylist.shadowColorMode;
            String lowerCase = listValue3.get().toLowerCase();
            Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase()");
            if (Intrinsics.areEqual(lowerCase, "background")) {
                integerValue11 = arraylist.backgroundColorRedValue;
                int intValue = integerValue11.get().intValue();
                integerValue12 = arraylist.backgroundColorGreenValue;
                int intValue2 = integerValue12.get().intValue();
                integerValue13 = arraylist.backgroundColorBlueValue;
                i = new Color(intValue, intValue2, integerValue13.get().intValue()).getRGB();
            } else if (!Intrinsics.areEqual(lowerCase, "text")) {
                integerValue = arraylist.shadowColorRedValue;
                int intValue3 = integerValue.get().intValue();
                integerValue2 = arraylist.shadowColorGreenValue;
                int intValue4 = integerValue2.get().intValue();
                integerValue3 = arraylist.shadowColorBlueValue;
                i = new Color(intValue3, intValue4, integerValue3.get().intValue()).getRGB();
            } else {
                int moduleColor = Color.getHSBColor(module.getHue(), f2, f3).getRGB();
                int i3 = iArr[0];
                integerValue4 = arraylist.skyDistanceValue;
                int intValue5 = i3 * integerValue4.get().intValue() * 50;
                floatValue = arraylist.saturationValue;
                float floatValue7 = floatValue.get().floatValue();
                floatValue2 = arraylist.brightnessValue;
                int Sky = RenderUtils.SkyRainbow(intValue5, floatValue7, floatValue2.get().floatValue());
                integerValue5 = arraylist.cRainbowSecValue;
                int intValue6 = integerValue5.get().intValue();
                floatValue3 = arraylist.saturationValue;
                float floatValue8 = floatValue3.get().floatValue();
                floatValue4 = arraylist.brightnessValue;
                float floatValue9 = floatValue4.get().floatValue();
                int i4 = iArr[0];
                integerValue6 = arraylist.cRainbowDistValue;
                int CRainbow = RenderUtils.getRainbowOpaque(intValue6, floatValue8, floatValue9, i4 * 50 * integerValue6.get().intValue());
                Color color = new Color(arraylist.getColorRedValue().get().intValue(), arraylist.getColorGreenValue().get().intValue(), arraylist.getColorBlueValue().get().intValue(), arraylist.getColorAlphaValue().get().intValue());
                integerValue7 = arraylist.fadeDistanceValue;
                int FadeColor = ColorUtils.fade(color, index * integerValue7.get().intValue(), 100).getRGB();
                iArr[0] = iArr[0] - 1;
                long nanoTime = System.nanoTime();
                integerValue8 = arraylist.liquidSlowlyDistanceValue;
                int intValue7 = index * integerValue8.get().intValue();
                floatValue5 = arraylist.saturationValue;
                float floatValue10 = floatValue5.get().floatValue();
                floatValue6 = arraylist.brightnessValue;
                Color LiquidSlowly = ColorUtils.LiquidSlowly(nanoTime, intValue7, floatValue10, floatValue6.get().floatValue());
                Integer test = LiquidSlowly == null ? null : Integer.valueOf(LiquidSlowly.getRGB());
                Intrinsics.checkNotNull(test);
                int LiquidSlowly2 = test.intValue();
                integerValue9 = arraylist.mixerDistValue;
                integerValue10 = arraylist.mixerSecValue;
                int mixerColor = ColorMixer.getMixedColor((-index) * integerValue9.get().intValue() * 10, integerValue10.get().intValue()).getRGB();
                if (StringsKt.equals(str, "Random", true)) {
                    i = moduleColor;
                } else if (StringsKt.equals(str, "Sky", true)) {
                    i = Sky;
                } else if (StringsKt.equals(str, "CRainbow", true)) {
                    i = CRainbow;
                } else if (StringsKt.equals(str, "LiquidSlowly", true)) {
                    i = LiquidSlowly2;
                } else if (StringsKt.equals(str, "Fade", true)) {
                    i = FadeColor;
                } else {
                    i = StringsKt.equals(str, "Mixer", true) ? mixerColor : i2;
                }
            }
            RenderUtils.newDrawRect(0.0f, arrayY, f6, arrayY2, i);
        }
        GL11.glPopMatrix();
    }
}
