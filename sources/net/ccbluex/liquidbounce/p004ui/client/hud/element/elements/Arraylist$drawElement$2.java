package net.ccbluex.liquidbounce.p004ui.client.hud.element.elements;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Lambda;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.lwjgl.opengl.GL11;

/* compiled from: Arraylist.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\b\n��\n\u0002\u0010\u0002\n��\u0010��\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, m53d2 = {"<anonymous>", "", "invoke"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.elements.Arraylist$drawElement$2 */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/Arraylist$drawElement$2.class */
final class Arraylist$drawElement$2 extends Lambda implements Functions<Unit> {
    final /* synthetic */ Arraylist this$0;
    final /* synthetic */ float $textHeight;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Arraylist$drawElement$2(Arraylist $receiver, float $textHeight) {
        super(0);
        this.this$0 = $receiver;
        this.$textHeight = $textHeight;
    }

    @Override // kotlin.jvm.functions.Functions
    public final void invoke() {
        BoolValue boolValue;
        Iterable iterable;
        ListValue listValue;
        ListValue listValue2;
        boolValue = this.this$0.shadowNoCutValue;
        if (!boolValue.get().booleanValue()) {
            GL11.glPushMatrix();
            GL11.glTranslated(this.this$0.getRenderX(), this.this$0.getRenderY(), 0.0d);
            iterable = this.this$0.modules;
            Iterable $this$forEachIndexed$iv = iterable;
            Arraylist arraylist = this.this$0;
            float f = this.$textHeight;
            int index$iv = 0;
            for (Object item$iv : $this$forEachIndexed$iv) {
                int i = index$iv;
                index$iv = i + 1;
                if (i < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                Module module = (Module) item$iv;
                float xPos = (-module.getSlide()) - 2;
                listValue = arraylist.rectRightValue;
                float f2 = xPos - (StringsKt.equals(listValue.get(), "right", true) ? 3 : 2);
                float arrayY = module.getArrayY();
                listValue2 = arraylist.rectRightValue;
                RenderUtils.quickDrawRect(f2, arrayY, StringsKt.equals(listValue2.get(), "right", true) ? -1.0f : 0.0f, module.getArrayY() + f);
            }
            GL11.glPopMatrix();
        }
    }
}
