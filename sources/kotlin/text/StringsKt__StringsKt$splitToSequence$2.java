package kotlin.text;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.ranges.IntRange;
import org.jetbrains.annotations.NotNull;

/* compiled from: Strings.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\u000e\n��\n\u0002\u0010\u000e\n��\n\u0002\u0018\u0002\n��\u0010��\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, m53d2 = {"<anonymous>", "", "it", "Lkotlin/ranges/IntRange;", "invoke"})
/* loaded from: Jackey Client b2.jar:kotlin/text/StringsKt__StringsKt$splitToSequence$2.class */
public final class StringsKt__StringsKt$splitToSequence$2 extends Lambda implements Function1<IntRange, String> {
    final /* synthetic */ CharSequence $this_splitToSequence;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StringsKt__StringsKt$splitToSequence$2(CharSequence $receiver) {
        super(1);
        this.$this_splitToSequence = $receiver;
    }

    @NotNull
    public final String invoke(@NotNull IntRange it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return StringsKt.substring(this.$this_splitToSequence, it);
    }
}
