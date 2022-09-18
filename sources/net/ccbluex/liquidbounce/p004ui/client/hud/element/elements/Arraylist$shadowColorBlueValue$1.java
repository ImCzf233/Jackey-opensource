package net.ccbluex.liquidbounce.p004ui.client.hud.element.elements;

import kotlin.Metadata;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Lambda;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;

/* compiled from: Arraylist.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\n\n��\n\u0002\u0010\u000b\n\u0002\b\u0002\u0010��\u001a\u00020\u0001H\n¢\u0006\u0004\b\u0002\u0010\u0003"}, m53d2 = {"<anonymous>", "", "invoke", "()Ljava/lang/Boolean;"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.elements.Arraylist$shadowColorBlueValue$1 */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/Arraylist$shadowColorBlueValue$1.class */
public final class Arraylist$shadowColorBlueValue$1 extends Lambda implements Functions<Boolean> {
    final /* synthetic */ Arraylist this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Arraylist$shadowColorBlueValue$1(Arraylist $receiver) {
        super(0);
        this.this$0 = $receiver;
    }

    @Override // kotlin.jvm.functions.Functions
    @NotNull
    public final Boolean invoke() {
        BoolValue boolValue;
        boolean z;
        ListValue listValue;
        boolValue = this.this$0.shadowShaderValue;
        if (boolValue.get().booleanValue()) {
            listValue = this.this$0.shadowColorMode;
            if (StringsKt.equals(listValue.get(), "custom", true)) {
                z = true;
                return Boolean.valueOf(z);
            }
        }
        z = false;
        return Boolean.valueOf(z);
    }
}
