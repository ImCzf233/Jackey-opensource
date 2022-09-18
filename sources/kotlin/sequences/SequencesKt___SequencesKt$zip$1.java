package kotlin.sequences;

import kotlin.Metadata;
import kotlin.Tuples;
import kotlin.TuplesKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

/* compiled from: _Sequences.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\n\n��\n\u0002\u0018\u0002\n\u0002\b\u0006\u0010��\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b��\u0010\u0002\"\u0004\b\u0001\u0010\u00032\u0006\u0010\u0004\u001a\u0002H\u00022\u0006\u0010\u0005\u001a\u0002H\u0003H\n¢\u0006\u0004\b\u0006\u0010\u0007"}, m53d2 = {"<anonymous>", "Lkotlin/Pair;", "T", "R", "t1", "t2", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;"})
/* loaded from: Jackey Client b2.jar:kotlin/sequences/SequencesKt___SequencesKt$zip$1.class */
final class SequencesKt___SequencesKt$zip$1 extends Lambda implements Function2<T, R, Tuples<? extends T, ? extends R>> {
    public static final SequencesKt___SequencesKt$zip$1 INSTANCE = new SequencesKt___SequencesKt$zip$1();

    SequencesKt___SequencesKt$zip$1() {
        super(2);
    }

    @Override // kotlin.jvm.functions.Function2
    @NotNull
    public final Tuples<T, R> invoke(T t, R r) {
        return TuplesKt.m46to(t, r);
    }
}
