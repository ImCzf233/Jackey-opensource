package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.SortedPair;
import java.io.Serializable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntIntSortedPair.class */
public interface IntIntSortedPair extends IntIntPair, SortedPair<Integer>, Serializable {
    /* renamed from: of */
    static IntIntSortedPair m207of(int left, int right) {
        return IntIntImmutableSortedPair.m210of(left, right);
    }

    default boolean contains(int e) {
        return e == leftInt() || e == rightInt();
    }

    @Override // com.viaversion.viaversion.libs.fastutil.SortedPair
    @Deprecated
    default boolean contains(Object o) {
        if (o == null) {
            return false;
        }
        return contains(((Integer) o).intValue());
    }
}
