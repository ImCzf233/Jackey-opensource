package net.ccbluex.liquidbounce.p004ui.client.hud.element.elements;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Lambda;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.gui.FontRenderer;

/* compiled from: Arraylist.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\b\n��\n\u0002\u0010\u0002\n��\u0010��\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, m53d2 = {"<anonymous>", "", "invoke"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.elements.Arraylist$drawElement$9 */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/Arraylist$drawElement$9.class */
final class Arraylist$drawElement$9 extends Lambda implements Functions<Unit> {
    final /* synthetic */ Arraylist this$0;
    final /* synthetic */ FontRenderer $fontRenderer;
    final /* synthetic */ float $floatX;
    final /* synthetic */ float $floatY;
    final /* synthetic */ float $textHeight;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Arraylist$drawElement$9(Arraylist $receiver, FontRenderer $fontRenderer, float $floatX, float $floatY, float $textHeight) {
        super(0);
        this.this$0 = $receiver;
        this.$fontRenderer = $fontRenderer;
        this.$floatX = $floatX;
        this.$floatY = $floatY;
        this.$textHeight = $textHeight;
    }

    @Override // kotlin.jvm.functions.Functions
    public final void invoke() {
        Iterable iterable;
        ListValue listValue;
        ListValue listValue2;
        iterable = this.this$0.modules;
        Iterable $this$forEachIndexed$iv = iterable;
        Arraylist arraylist = this.this$0;
        FontRenderer fontRenderer = this.$fontRenderer;
        float f = this.$floatX;
        float f2 = this.$floatY;
        float f3 = this.$textHeight;
        int index$iv = 0;
        for (Object item$iv : $this$forEachIndexed$iv) {
            int i = index$iv;
            index$iv = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            Module module = (Module) item$iv;
            String displayString = arraylist.getModName(module);
            int width = fontRenderer.func_78256_a(displayString);
            float f4 = -(width - module.getSlide());
            listValue = arraylist.rectLeftValue;
            float xPos = f4 + (StringsKt.equals(listValue.get(), "left", true) ? 3 : 2);
            float arrayY = f2 + module.getArrayY();
            float f5 = f + xPos + width;
            listValue2 = arraylist.rectLeftValue;
            RenderUtils.quickDrawRect(f, arrayY, f5 + (StringsKt.equals(listValue2.get(), "right", true) ? 3 : 2), f2 + module.getArrayY() + f3);
        }
    }
}
