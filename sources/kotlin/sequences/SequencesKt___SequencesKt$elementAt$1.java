package kotlin.sequences;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: _Sequences.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\f\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\u0010��\u001a\u0002H\u0001\"\u0004\b��\u0010\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, m53d2 = {"<anonymous>", "T", "it", "", "invoke", "(I)Ljava/lang/Object;"})
/* loaded from: Jackey Client b2.jar:kotlin/sequences/SequencesKt___SequencesKt$elementAt$1.class */
final class SequencesKt___SequencesKt$elementAt$1 extends Lambda implements Function1<Integer, T> {
    final /* synthetic */ int $index;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SequencesKt___SequencesKt$elementAt$1(int $index) {
        super(1);
        this.$index = $index;
    }

    public final T invoke(int it) {
        throw new IndexOutOfBoundsException("Sequence doesn't contain element at index " + this.$index + '.');
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Object invoke(Integer num) {
        return invoke(num.intValue());
    }
}
