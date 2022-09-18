package kotlin.text;

import kotlin.Metadata;
import kotlin.Tuples;
import kotlin.TuplesKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Strings.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\u0012\n��\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\r\n\u0002\b\u0002\u0010��\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u0001*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0002H\n¢\u0006\u0002\b\u0005"}, m53d2 = {"<anonymous>", "Lkotlin/Pair;", "", "", "currentIndex", "invoke"})
/* loaded from: Jackey Client b2.jar:kotlin/text/StringsKt__StringsKt$rangesDelimitedBy$1.class */
public final class StringsKt__StringsKt$rangesDelimitedBy$1 extends Lambda implements Function2<CharSequence, Integer, Tuples<? extends Integer, ? extends Integer>> {
    final /* synthetic */ char[] $delimiters;
    final /* synthetic */ boolean $ignoreCase;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StringsKt__StringsKt$rangesDelimitedBy$1(char[] $delimiters, boolean $ignoreCase) {
        super(2);
        this.$delimiters = $delimiters;
        this.$ignoreCase = $ignoreCase;
    }

    @Override // kotlin.jvm.functions.Function2
    public /* bridge */ /* synthetic */ Tuples<? extends Integer, ? extends Integer> invoke(CharSequence charSequence, Integer num) {
        return invoke(charSequence, num.intValue());
    }

    @Nullable
    public final Tuples<Integer, Integer> invoke(@NotNull CharSequence $receiver, int currentIndex) {
        Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
        int it = StringsKt.indexOfAny($receiver, this.$delimiters, currentIndex, this.$ignoreCase);
        if (it < 0) {
            return null;
        }
        return TuplesKt.m46to(Integer.valueOf(it), 1);
    }
}
