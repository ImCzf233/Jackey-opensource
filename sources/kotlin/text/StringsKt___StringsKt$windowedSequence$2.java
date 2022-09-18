package kotlin.text;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: _Strings.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\f\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\u0010��\u001a\u0002H\u0001\"\u0004\b��\u0010\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, m53d2 = {"<anonymous>", "R", "index", "", "invoke", "(I)Ljava/lang/Object;"})
/* loaded from: Jackey Client b2.jar:kotlin/text/StringsKt___StringsKt$windowedSequence$2.class */
final class StringsKt___StringsKt$windowedSequence$2 extends Lambda implements Function1<Integer, R> {
    final /* synthetic */ int $size;
    final /* synthetic */ CharSequence $this_windowedSequence;
    final /* synthetic */ Function1<CharSequence, R> $transform;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public StringsKt___StringsKt$windowedSequence$2(int $size, CharSequence $receiver, Function1<? super CharSequence, ? extends R> function1) {
        super(1);
        this.$size = $size;
        this.$this_windowedSequence = $receiver;
        this.$transform = function1;
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Object invoke(Integer num) {
        return invoke(num.intValue());
    }

    /* JADX WARN: Type inference failed for: r0v6, types: [R, java.lang.Object] */
    public final R invoke(int index) {
        int end = index + this.$size;
        int coercedEnd = (end < 0 || end > this.$this_windowedSequence.length()) ? this.$this_windowedSequence.length() : end;
        return this.$transform.invoke(this.$this_windowedSequence.subSequence(index, coercedEnd));
    }
}
