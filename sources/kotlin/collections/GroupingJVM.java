package kotlin.collections;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.annotations.NotNull;

@Metadata(m51mv = {1, 6, 0}, m52k = 5, m49xi = 49, m54d1 = {"��&\n��\n\u0002\u0010$\n��\n\u0002\u0010\b\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010%\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010&\n��\u001a0\u0010��\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00030\u0001\"\u0004\b��\u0010\u0004\"\u0004\b\u0001\u0010\u0002*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u0005H\u0007\u001aZ\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\b0\u0007\"\u0004\b��\u0010\u0002\"\u0004\b\u0001\u0010\t\"\u0004\b\u0002\u0010\b*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\t0\u00072\u001e\u0010\n\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\t0\f\u0012\u0004\u0012\u0002H\b0\u000bH\u0081\bø\u0001��\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\r"}, m53d2 = {"eachCount", "", "K", "", "T", "Lkotlin/collections/Grouping;", "mapValuesInPlace", "", "R", "V", "f", "Lkotlin/Function1;", "", "kotlin-stdlib"}, m48xs = "kotlin/collections/GroupingKt")
/* renamed from: kotlin.collections.GroupingKt__GroupingJVMKt */
/* loaded from: Jackey Client b2.jar:kotlin/collections/GroupingKt__GroupingJVMKt.class */
class GroupingJVM {
    @SinceKotlin(version = "1.1")
    @NotNull
    public static final <T, K> Map<K, Integer> eachCount(@NotNull Grouping<T, ? extends K> grouping) {
        Ref.IntRef intRef;
        Intrinsics.checkNotNullParameter(grouping, "<this>");
        Map destination$iv = new LinkedHashMap();
        Iterator<T> sourceIterator = grouping.sourceIterator();
        while (sourceIterator.hasNext()) {
            K keyOf = grouping.keyOf(sourceIterator.next());
            Object accumulator$iv$iv = destination$iv.get(keyOf);
            boolean first$iv = accumulator$iv$iv == null && !destination$iv.containsKey(keyOf);
            K k = keyOf;
            if (first$iv) {
                k = k;
                intRef = new Ref.IntRef();
            } else {
                intRef = accumulator$iv$iv;
            }
            Ref.IntRef acc = (Ref.IntRef) intRef;
            acc.element++;
            destination$iv.put(keyOf, acc);
        }
        for (Map.Entry it : destination$iv.entrySet()) {
            it.setValue(Integer.valueOf(((Ref.IntRef) it.getValue()).element));
        }
        return destination$iv;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @PublishedApi
    @InlineOnly
    private static final <K, V, R> Map<K, R> mapValuesInPlace(Map<K, V> map, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> f) {
        Intrinsics.checkNotNullParameter(map, "<this>");
        Intrinsics.checkNotNullParameter(f, "f");
        Iterable $this$forEach$iv = map.entrySet();
        for (Object element$iv : $this$forEach$iv) {
            Map.Entry it = (Map.Entry) element$iv;
            it.setValue(f.invoke(it));
        }
        return map;
    }
}
