package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Size64;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.Spliterator;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntSortedSet.class */
public interface IntSortedSet extends IntSet, SortedSet<Integer>, IntBidirectionalIterable {
    IntBidirectionalIterator iterator(int i);

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSet, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, java.util.Set
    Iterator<Integer> iterator();

    IntSortedSet subSet(int i, int i2);

    IntSortedSet headSet(int i);

    IntSortedSet tailSet(int i);

    IntComparator comparator();

    int firstInt();

    int lastInt();

    /* JADX WARN: Type inference failed for: r0v1, types: [com.viaversion.viaversion.libs.fastutil.ints.IntBidirectionalIterator, com.viaversion.viaversion.libs.fastutil.ints.IntIterator] */
    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSet, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, java.util.Set
    /* renamed from: spliterator */
    default Spliterator<Integer> spliterator2() {
        return IntSpliterators.asSpliteratorFromSorted(iterator(), Size64.sizeOf(this), 341, comparator());
    }

    @Deprecated
    default IntSortedSet subSet(Integer from, Integer to) {
        return subSet(from.intValue(), to.intValue());
    }

    @Deprecated
    default IntSortedSet headSet(Integer to) {
        return headSet(to.intValue());
    }

    @Deprecated
    default IntSortedSet tailSet(Integer from) {
        return tailSet(from.intValue());
    }

    @Override // java.util.SortedSet
    @Deprecated
    default Integer first() {
        return Integer.valueOf(firstInt());
    }

    @Override // java.util.SortedSet
    @Deprecated
    default Integer last() {
        return Integer.valueOf(lastInt());
    }
}
