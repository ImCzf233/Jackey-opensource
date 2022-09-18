package kotlin.sequences;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: _Sequences.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\u0004\n\u0002\b\u0005\u0010��\u001a\u0002H\u0001\"\u0004\b��\u0010\u00012\u0006\u0010\u0002\u001a\u0002H\u0001H\n¢\u0006\u0004\b\u0003\u0010\u0004"}, m53d2 = {"<anonymous>", "T", "it", "invoke", "(Ljava/lang/Object;)Ljava/lang/Object;"})
/* loaded from: Jackey Client b2.jar:kotlin/sequences/SequencesKt___SequencesKt$onEach$1.class */
final class SequencesKt___SequencesKt$onEach$1 extends Lambda implements Function1<T, T> {
    final /* synthetic */ Function1<T, Unit> $action;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public SequencesKt___SequencesKt$onEach$1(Function1<? super T, Unit> function1) {
        super(1);
        this.$action = function1;
    }

    @Override // kotlin.jvm.functions.Function1
    public final T invoke(T t) {
        this.$action.invoke(t);
        return t;
    }
}
