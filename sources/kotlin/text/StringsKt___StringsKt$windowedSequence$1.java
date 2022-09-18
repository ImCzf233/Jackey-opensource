package kotlin.text;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

/* compiled from: _Strings.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\u000e\n��\n\u0002\u0010\u000e\n��\n\u0002\u0010\r\n��\u0010��\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, m53d2 = {"<anonymous>", "", "it", "", "invoke"})
/* loaded from: Jackey Client b2.jar:kotlin/text/StringsKt___StringsKt$windowedSequence$1.class */
final class StringsKt___StringsKt$windowedSequence$1 extends Lambda implements Function1<CharSequence, String> {
    public static final StringsKt___StringsKt$windowedSequence$1 INSTANCE = new StringsKt___StringsKt$windowedSequence$1();

    StringsKt___StringsKt$windowedSequence$1() {
        super(1);
    }

    @NotNull
    public final String invoke(@NotNull CharSequence it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return it.toString();
    }
}
