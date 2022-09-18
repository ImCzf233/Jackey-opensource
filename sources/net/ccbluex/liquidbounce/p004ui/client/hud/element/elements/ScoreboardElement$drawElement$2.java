package net.ccbluex.liquidbounce.p004ui.client.hud.element.elements;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Lambda;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

/* compiled from: ScoreboardElement.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\b\n��\n\u0002\u0010\u0002\n��\u0010��\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, m53d2 = {"<anonymous>", "", "invoke"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.elements.ScoreboardElement$drawElement$2 */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/ScoreboardElement$drawElement$2.class */
final class ScoreboardElement$drawElement$2 extends Lambda implements Functions<Unit> {
    final /* synthetic */ ScoreboardElement this$0;
    final /* synthetic */ int $l1;
    final /* synthetic */ int $maxHeight;
    final /* synthetic */ FontRenderer $fontRenderer;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScoreboardElement$drawElement$2(ScoreboardElement $receiver, int $l1, int $maxHeight, FontRenderer $fontRenderer) {
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
        IntegerValue integerValue;
        BoolValue boolValue3;
        float f2;
        FloatValue floatValue;
        IntegerValue integerValue2;
        GL11.glPushMatrix();
        GL11.glTranslated(this.this$0.getRenderX(), this.this$0.getRenderY(), 0.0d);
        GL11.glScalef(this.this$0.getScale(), this.this$0.getScale(), this.this$0.getScale());
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179120_a(770, 771, 1, 0);
        boolValue = this.this$0.bgRoundedValue;
        if (boolValue.get().booleanValue()) {
            float f3 = this.$l1 + (this.this$0.getSide().getHorizontal() == Side.Horizontal.LEFT ? 2.0f : -2.0f);
            boolValue3 = this.this$0.rectValue;
            if (boolValue3.get().booleanValue()) {
                integerValue2 = this.this$0.rectHeight;
                f2 = (-2.0f) - integerValue2.get().intValue();
            } else {
                f2 = -2.0f;
            }
            float f4 = this.this$0.getSide().getHorizontal() == Side.Horizontal.LEFT ? -5.0f : 5.0f;
            floatValue = this.this$0.roundStrength;
            RenderUtils.fastRoundedRect(f3, f2, f4, this.$maxHeight + this.$fontRenderer.field_78288_b, floatValue.get().floatValue());
        } else {
            float f5 = this.$l1 + (this.this$0.getSide().getHorizontal() == Side.Horizontal.LEFT ? 2.0f : -2.0f);
            boolValue2 = this.this$0.rectValue;
            if (boolValue2.get().booleanValue()) {
                integerValue = this.this$0.rectHeight;
                f = (-2.0f) - integerValue.get().intValue();
            } else {
                f = -2.0f;
            }
            RenderUtils.quickDrawRect(f5, f, this.this$0.getSide().getHorizontal() == Side.Horizontal.LEFT ? -5.0f : 5.0f, this.$maxHeight + this.$fontRenderer.field_78288_b);
        }
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
        GL11.glPopMatrix();
    }
}
