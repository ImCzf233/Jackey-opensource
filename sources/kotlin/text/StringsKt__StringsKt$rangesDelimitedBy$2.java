package kotlin.text;

import java.util.List;
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
/* loaded from: Jackey Client b2.jar:kotlin/text/StringsKt__StringsKt$rangesDelimitedBy$2.class */
public final class StringsKt__StringsKt$rangesDelimitedBy$2 extends Lambda implements Function2<CharSequence, Integer, Tuples<? extends Integer, ? extends Integer>> {
    final /* synthetic */ List<String> $delimitersList;
    final /* synthetic */ boolean $ignoreCase;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StringsKt__StringsKt$rangesDelimitedBy$2(List<String> list, boolean $ignoreCase) {
        super(2);
        this.$delimitersList = list;
        this.$ignoreCase = $ignoreCase;
    }

    @Nullable
    public final Tuples<Integer, Integer> invoke(@NotNull CharSequence $receiver, int currentIndex) {
        Tuples it;
        Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
        it = StringsKt__StringsKt.findAnyOf$StringsKt__StringsKt($receiver, this.$delimitersList, currentIndex, this.$ignoreCase, false);
        if (it == null) {
            return null;
        }
        return TuplesKt.m46to(it.getFirst(), Integer.valueOf(((String) it.getSecond()).length()));
    }

    @Override // kotlin.jvm.functions.Function2
    public /* bridge */ /* synthetic */ Tuples<? extends Integer, ? extends Integer> invoke(CharSequence charSequence, Integer num) {
        return invoke(charSequence, num.intValue());
    }
}
