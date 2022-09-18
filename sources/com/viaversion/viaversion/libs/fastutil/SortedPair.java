package com.viaversion.viaversion.libs.fastutil;

import com.viaversion.viaversion.libs.fastutil.objects.ObjectObjectImmutableSortedPair;
import java.lang.Comparable;
import java.util.Objects;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/SortedPair.class */
public interface SortedPair<K extends Comparable<K>> extends Pair<K, K> {
    /* renamed from: of */
    static <K extends Comparable<K>> SortedPair<K> m219of(K l, K r) {
        return ObjectObjectImmutableSortedPair.m160of((Comparable) l, (Comparable) r);
    }

    default boolean contains(Object o) {
        return Objects.equals(o, left()) || Objects.equals(o, right());
    }
}
