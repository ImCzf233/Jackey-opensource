package net.ccbluex.liquidbounce.features.module.modules.misc;

import java.io.PrintStream;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.text.CharsKt;
import net.ccbluex.liquidbounce.utils.misc.HttpUtils;

/* compiled from: AntiBan.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\b\n��\n\u0002\u0010\u0002\n��\u0010��\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, m53d2 = {"<anonymous>", "", "invoke"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/features/module/modules/misc/AntiBan$onInitialize$1.class */
final class AntiBan$onInitialize$1 extends Lambda implements Functions<Unit> {
    final /* synthetic */ AntiBan this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AntiBan$onInitialize$1(AntiBan $receiver) {
        super(0);
        this.this$0 = $receiver;
    }

    @Override // kotlin.jvm.functions.Functions
    public final void invoke() {
        String str;
        CharSequence $this$filter$iv;
        String str2;
        AntiBan antiBan = this.this$0;
        str = this.this$0.staff_fallback;
        if (str == null) {
            Intrinsics.throwUninitializedPropertyAccessException("staff_fallback");
            str = null;
        }
        antiBan.obStaffs = HttpUtils.get(str);
        AntiBan antiBan2 = this.this$0;
        $this$filter$iv = this.this$0.obStaffs;
        CharSequence $this$filterTo$iv$iv = $this$filter$iv;
        Appendable destination$iv$iv = new StringBuilder();
        int i = 0;
        int length = $this$filterTo$iv$iv.length();
        while (i < length) {
            int index$iv$iv = i;
            i++;
            char element$iv$iv = $this$filterTo$iv$iv.charAt(index$iv$iv);
            if (CharsKt.isWhitespace(element$iv$iv)) {
                destination$iv$iv.append(element$iv$iv);
            }
        }
        String sb = ((StringBuilder) destination$iv$iv).toString();
        Intrinsics.checkNotNullExpressionValue(sb, "filterTo(StringBuilder(), predicate).toString()");
        antiBan2.totalCount = sb.length();
        PrintStream printStream = System.out;
        str2 = this.this$0.obStaffs;
        printStream.println((Object) Intrinsics.stringPlus("[Staff/fallback] ", str2));
    }
}
