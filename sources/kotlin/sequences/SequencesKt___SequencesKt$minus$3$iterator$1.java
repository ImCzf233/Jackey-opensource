package kotlin.sequences;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

/* compiled from: _Sequences.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\n\n��\n\u0002\u0010\u000b\n\u0002\b\u0004\u0010��\u001a\u00020\u0001\"\u0004\b��\u0010\u00022\u0006\u0010\u0003\u001a\u0002H\u0002H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, m53d2 = {"<anonymous>", "", "T", "it", "invoke", "(Ljava/lang/Object;)Ljava/lang/Boolean;"})
/* loaded from: Jackey Client b2.jar:kotlin/sequences/SequencesKt___SequencesKt$minus$3$iterator$1.class */
final class SequencesKt___SequencesKt$minus$3$iterator$1 extends Lambda implements Function1<T, Boolean> {
    final /* synthetic */ Collection<T> $other;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public SequencesKt___SequencesKt$minus$3$iterator$1(Collection<? extends T> collection) {
        super(1);
        this.$other = collection;
    }

    @Override // kotlin.jvm.functions.Function1
    @NotNull
    public final Boolean invoke(T t) {
        return Boolean.valueOf(this.$other.contains(t));
    }
}
