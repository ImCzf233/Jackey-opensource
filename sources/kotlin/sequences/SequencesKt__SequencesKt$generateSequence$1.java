package kotlin.sequences;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Sequences.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\f\n\u0002\b\u0002\n\u0002\u0010��\n\u0002\b\u0003\u0010��\u001a\u0004\u0018\u0001H\u0001\"\b\b��\u0010\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u0002H\u0001H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, m53d2 = {"<anonymous>", "T", "", "it", "invoke", "(Ljava/lang/Object;)Ljava/lang/Object;"})
/* loaded from: Jackey Client b2.jar:kotlin/sequences/SequencesKt__SequencesKt$generateSequence$1.class */
final class SequencesKt__SequencesKt$generateSequence$1 extends Lambda implements Function1<T, T> {
    final /* synthetic */ Functions<T> $nextFunction;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public SequencesKt__SequencesKt$generateSequence$1(Functions<? extends T> functions) {
        super(1);
        this.$nextFunction = functions;
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [T, java.lang.Object] */
    @Override // kotlin.jvm.functions.Function1
    @Nullable
    public final T invoke(@NotNull T it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return this.$nextFunction.invoke();
    }
}
