package kotlin.comparisons;

import java.util.Comparator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;

/* compiled from: Comparisons.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 176, m54d1 = {"��\n\n��\n\u0002\u0010\b\n\u0002\b\u0006\u0010��\u001a\u00020\u0001\"\u0004\b��\u0010\u00022\u000e\u0010\u0003\u001a\n \u0004*\u0004\u0018\u0001H\u0002H\u00022\u000e\u0010\u0005\u001a\n \u0004*\u0004\u0018\u0001H\u0002H\u0002H\n¢\u0006\u0004\b\u0006\u0010\u0007"}, m53d2 = {"<anonymous>", "", "T", "a", "kotlin.jvm.PlatformType", "b", "compare", "(Ljava/lang/Object;Ljava/lang/Object;)I"})
/* loaded from: Jackey Client b2.jar:kotlin/comparisons/ComparisonsKt__ComparisonsKt$compareByDescending$1.class */
public final class ComparisonsKt__ComparisonsKt$compareByDescending$1<T> implements Comparator {
    final /* synthetic */ Function1<T, Comparable<?>> $selector;

    /* JADX WARN: Multi-variable type inference failed */
    public ComparisonsKt__ComparisonsKt$compareByDescending$1(Function1<? super T, ? extends Comparable<?>> function1) {
        this.$selector = function1;
    }

    @Override // java.util.Comparator
    public final int compare(T t, T t2) {
        Function1<T, Comparable<?>> function1 = this.$selector;
        return ComparisonsKt.compareValues(function1.invoke(t2), function1.invoke(t));
    }
}
