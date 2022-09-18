package kotlin.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;

@Metadata(m51mv = {1, 6, 0}, m52k = 2, m49xi = 48, m54d1 = {"�� \n��\n\u0002\u0010\u001e\n��\n\u0002\u0010\u0011\n��\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n��\u001a#\u0010��\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b��\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H��¢\u0006\u0002\u0010\u0004\u001a\u001e\u0010��\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b��\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0005H��\u001a\u001e\u0010��\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b��\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0006H��\u001a,\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b��\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00052\f\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0005H��\u001a\u0018\u0010\t\u001a\u00020\n\"\u0004\b��\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0001H\u0002¨\u0006\u000b"}, m53d2 = {"convertToSetForSetOperation", "", "T", "", "([Ljava/lang/Object;)Ljava/util/Collection;", "", "Lkotlin/sequences/Sequence;", "convertToSetForSetOperationWith", "source", "safeToConvertToSet", "", "kotlin-stdlib"})
/* renamed from: kotlin.collections.BrittleContainsOptimizationKt */
/* loaded from: Jackey Client b2.jar:kotlin/collections/BrittleContainsOptimizationKt.class */
public final class BrittleContainsOptimization {
    private static final <T> boolean safeToConvertToSet(Collection<? extends T> collection) {
        return CollectionsJVM.brittleContainsOptimizationEnabled && collection.size() > 2 && (collection instanceof ArrayList);
    }

    @NotNull
    public static final <T> Collection<T> convertToSetForSetOperationWith(@NotNull Iterable<? extends T> iterable, @NotNull Iterable<? extends T> source) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        Intrinsics.checkNotNullParameter(source, "source");
        if (iterable instanceof Set) {
            return (Collection) iterable;
        }
        if (!(iterable instanceof Collection)) {
            return CollectionsJVM.brittleContainsOptimizationEnabled ? CollectionsKt.toHashSet(iterable) : CollectionsKt.toList(iterable);
        }
        if ((!(source instanceof Collection) || ((Collection) source).size() >= 2) && safeToConvertToSet((Collection) iterable)) {
            return CollectionsKt.toHashSet(iterable);
        }
        return (Collection) iterable;
    }

    @NotNull
    public static final <T> Collection<T> convertToSetForSetOperation(@NotNull Iterable<? extends T> iterable) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        return iterable instanceof Set ? (Collection) iterable : iterable instanceof Collection ? safeToConvertToSet((Collection) iterable) ? CollectionsKt.toHashSet(iterable) : (Collection) iterable : CollectionsJVM.brittleContainsOptimizationEnabled ? CollectionsKt.toHashSet(iterable) : CollectionsKt.toList(iterable);
    }

    @NotNull
    public static final <T> Collection<T> convertToSetForSetOperation(@NotNull Sequence<? extends T> sequence) {
        Intrinsics.checkNotNullParameter(sequence, "<this>");
        return CollectionsJVM.brittleContainsOptimizationEnabled ? SequencesKt.toHashSet(sequence) : SequencesKt.toList(sequence);
    }

    @NotNull
    public static final <T> Collection<T> convertToSetForSetOperation(@NotNull T[] tArr) {
        Intrinsics.checkNotNullParameter(tArr, "<this>");
        return CollectionsJVM.brittleContainsOptimizationEnabled ? ArraysKt.toHashSet(tArr) : ArraysKt.asList(tArr);
    }
}
