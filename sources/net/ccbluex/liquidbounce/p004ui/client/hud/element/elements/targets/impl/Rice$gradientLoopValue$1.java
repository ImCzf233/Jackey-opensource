package net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.impl;

import kotlin.Metadata;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Lambda;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

/* compiled from: Rice.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\n\n��\n\u0002\u0010\u000b\n\u0002\b\u0002\u0010��\u001a\u00020\u0001H\n¢\u0006\u0004\b\u0002\u0010\u0003"}, m53d2 = {"<anonymous>", "", "invoke", "()Ljava/lang/Boolean;"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.elements.targets.impl.Rice$gradientLoopValue$1 */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/targets/impl/Rice$gradientLoopValue$1.class */
public final class Rice$gradientLoopValue$1 extends Lambda implements Functions<Boolean> {
    final /* synthetic */ Rice this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Rice$gradientLoopValue$1(Rice $receiver) {
        super(0);
        this.this$0 = $receiver;
    }

    @Override // kotlin.jvm.functions.Functions
    @NotNull
    public final Boolean invoke() {
        return Boolean.valueOf(StringsKt.equals(this.this$0.getTargetInstance().getStyleValue().get(), "Rice", true));
    }
}
