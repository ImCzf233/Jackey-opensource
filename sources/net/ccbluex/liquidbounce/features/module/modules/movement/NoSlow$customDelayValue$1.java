package net.ccbluex.liquidbounce.features.module.modules.movement;

import kotlin.Metadata;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Lambda;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;

/* compiled from: NoSlow.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\n\n��\n\u0002\u0010\u000b\n\u0002\b\u0002\u0010��\u001a\u00020\u0001H\n¢\u0006\u0004\b\u0002\u0010\u0003"}, m53d2 = {"<anonymous>", "", "invoke", "()Ljava/lang/Boolean;"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/movement/NoSlow$customDelayValue$1.class */
final class NoSlow$customDelayValue$1 extends Lambda implements Functions<Boolean> {
    final /* synthetic */ NoSlow this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NoSlow$customDelayValue$1(NoSlow $receiver) {
        super(0);
        this.this$0 = $receiver;
    }

    @Override // kotlin.jvm.functions.Functions
    @NotNull
    public final Boolean invoke() {
        ListValue listValue;
        listValue = this.this$0.modeValue;
        return Boolean.valueOf(StringsKt.equals(listValue.get(), "custom", true));
    }
}
