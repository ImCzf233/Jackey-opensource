package kotlin.sequences;

import kotlin.Metadata;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.Nullable;

/* compiled from: Sequences.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\f\n\u0002\b\u0002\n\u0002\u0010��\n\u0002\b\u0002\u0010��\u001a\u0004\u0018\u0001H\u0001\"\b\b��\u0010\u0001*\u00020\u0002H\n¢\u0006\u0004\b\u0003\u0010\u0004"}, m53d2 = {"<anonymous>", "T", "", "invoke", "()Ljava/lang/Object;"})
/* loaded from: Jackey Client b2.jar:kotlin/sequences/SequencesKt__SequencesKt$generateSequence$2.class */
final class SequencesKt__SequencesKt$generateSequence$2 extends Lambda implements Functions<T> {
    final /* synthetic */ T $seed;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SequencesKt__SequencesKt$generateSequence$2(T t) {
        super(0);
        this.$seed = t;
    }

    @Override // kotlin.jvm.functions.Functions
    @Nullable
    public final T invoke() {
        return this.$seed;
    }
}
