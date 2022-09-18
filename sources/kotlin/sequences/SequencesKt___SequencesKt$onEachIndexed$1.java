package kotlin.sequences;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Lambda;

/* compiled from: _Sequences.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\f\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\u0010��\u001a\u0002H\u0001\"\u0004\b��\u0010\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u0002H\u0001H\n¢\u0006\u0004\b\u0005\u0010\u0006"}, m53d2 = {"<anonymous>", "T", "index", "", "element", "invoke", "(ILjava/lang/Object;)Ljava/lang/Object;"})
/* loaded from: Jackey Client b2.jar:kotlin/sequences/SequencesKt___SequencesKt$onEachIndexed$1.class */
final class SequencesKt___SequencesKt$onEachIndexed$1 extends Lambda implements Function2<Integer, T, T> {
    final /* synthetic */ Function2<Integer, T, Unit> $action;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public SequencesKt___SequencesKt$onEachIndexed$1(Function2<? super Integer, ? super T, Unit> function2) {
        super(2);
        this.$action = function2;
    }

    @Override // kotlin.jvm.functions.Function2
    public /* bridge */ /* synthetic */ Object invoke(Integer num, Object p2) {
        return invoke(num.intValue(), (int) p2);
    }

    public final T invoke(int index, T t) {
        this.$action.invoke(Integer.valueOf(index), t);
        return t;
    }
}
