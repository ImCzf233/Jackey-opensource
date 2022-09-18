package net.ccbluex.liquidbounce.p004ui.client.hud.element.elements;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Lambda;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.opengl.GL11;

/* compiled from: ScoreboardElement.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\b\n��\n\u0002\u0010\u0002\n��\u0010��\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, m53d2 = {"<anonymous>", "", "invoke"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.elements.ScoreboardElement$drawElement$1 */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/ScoreboardElement$drawElement$1.class */
final class ScoreboardElement$drawElement$1 extends Lambda implements Functions<Unit> {
    final /* synthetic */ ScoreboardElement this$0;
    final /* synthetic */ int $l1;
    final /* synthetic */ int $maxHeight;
    final /* synthetic */ FontRenderer $fontRenderer;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScoreboardElement$drawElement$1(ScoreboardElement $receiver, int $l1, int $maxHeight, FontRenderer $fontRenderer) {
        super(0);
        this.this$0 = $receiver;
        this.$l1 = $l1;
        this.$maxHeight = $maxHeight;
        this.$fontRenderer = $fontRenderer;
    }

    @Override // kotlin.jvm.functions.Functions
    public final void invoke() {
        BoolValue boolValue;
        BoolValue boolValue2;
        float f;
        ListValue listValue;
        int i;
        IntegerValue integerValue;
        IntegerValue integerValue2;
        IntegerValue integerValue3;
        IntegerValue integerValue4;
        IntegerValue integerValue5;
        IntegerValue integerValue6;
        IntegerValue integerValue7;
        BoolValue boolValue3;
        float f2;
        FloatValue floatValue;
        ListValue listValue2;
        int i2;
        IntegerValue integerValue8;
        IntegerValue integerValue9;
        IntegerValue integerValue10;
        IntegerValue integerValue11;
        IntegerValue integerValue12;
        IntegerValue integerValue13;
        IntegerValue integerValue14;
        GL11.glPushMatrix();
        GL11.glTranslated(this.this$0.getRenderX(), this.this$0.getRenderY(), 0.0d);
        GL11.glScalef(this.this$0.getScale(), this.this$0.getScale(), this.this$0.getScale());
        boolValue = this.this$0.bgRoundedValue;
        if (boolValue.get().booleanValue()) {
            float f3 = this.$l1 + (this.this$0.getSide().getHorizontal() == Side.Horizontal.LEFT ? 2.0f : -2.0f);
            boolValue3 = this.this$0.rectValue;
            if (boolValue3.get().booleanValue()) {
                integerValue14 = this.this$0.rectHeight;
                f2 = (-2.0f) - integerValue14.get().intValue();
            } else {
                f2 = -2.0f;
            }
            float f4 = this.this$0.getSide().getHorizontal() == Side.Horizontal.LEFT ? -5.0f : 5.0f;
            float f5 = this.$maxHeight + this.$fontRenderer.field_78288_b;
            floatValue = this.this$0.roundStrength;
            float floatValue2 = floatValue.get().floatValue();
            listValue2 = this.this$0.shadowColorMode;
            if (StringsKt.equals(listValue2.get(), "background", true)) {
                integerValue11 = this.this$0.backgroundColorRedValue;
                int intValue = integerValue11.get().intValue();
                integerValue12 = this.this$0.backgroundColorGreenValue;
                int intValue2 = integerValue12.get().intValue();
                integerValue13 = this.this$0.backgroundColorBlueValue;
                i2 = new Color(intValue, intValue2, integerValue13.get().intValue()).getRGB();
            } else {
                integerValue8 = this.this$0.shadowColorRedValue;
                int intValue3 = integerValue8.get().intValue();
                integerValue9 = this.this$0.shadowColorGreenValue;
                int intValue4 = integerValue9.get().intValue();
                integerValue10 = this.this$0.shadowColorBlueValue;
                i2 = new Color(intValue3, intValue4, integerValue10.get().intValue()).getRGB();
            }
            RenderUtils.originalRoundedRect(f3, f2, f4, f5, floatValue2, i2);
        } else {
            float f6 = this.$l1 + (this.this$0.getSide().getHorizontal() == Side.Horizontal.LEFT ? 2.0f : -2.0f);
            boolValue2 = this.this$0.rectValue;
            if (boolValue2.get().booleanValue()) {
                integerValue7 = this.this$0.rectHeight;
                f = (-2.0f) - integerValue7.get().intValue();
            } else {
                f = -2.0f;
            }
            float f7 = this.this$0.getSide().getHorizontal() == Side.Horizontal.LEFT ? -5.0f : 5.0f;
            float f8 = this.$maxHeight + this.$fontRenderer.field_78288_b;
            listValue = this.this$0.shadowColorMode;
            if (StringsKt.equals(listValue.get(), "background", true)) {
                integerValue4 = this.this$0.backgroundColorRedValue;
                int intValue5 = integerValue4.get().intValue();
                integerValue5 = this.this$0.backgroundColorGreenValue;
                int intValue6 = integerValue5.get().intValue();
                integerValue6 = this.this$0.backgroundColorBlueValue;
                i = new Color(intValue5, intValue6, integerValue6.get().intValue()).getRGB();
            } else {
                integerValue = this.this$0.shadowColorRedValue;
                int intValue7 = integerValue.get().intValue();
                integerValue2 = this.this$0.shadowColorGreenValue;
                int intValue8 = integerValue2.get().intValue();
                integerValue3 = this.this$0.shadowColorBlueValue;
                i = new Color(intValue7, intValue8, integerValue3.get().intValue()).getRGB();
            }
            RenderUtils.newDrawRect(f6, f, f7, f8, i);
        }
        GL11.glPopMatrix();
    }
}
