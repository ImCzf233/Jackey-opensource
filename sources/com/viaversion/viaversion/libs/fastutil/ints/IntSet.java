package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Size64;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntSet.class */
public interface IntSet extends IntCollection, Set<Integer> {
    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
    /* renamed from: iterator */
    Iterator<Integer> iterator2();

    boolean remove(int i);

    /* JADX WARN: Type inference failed for: r0v1, types: [com.viaversion.viaversion.libs.fastutil.ints.IntIterator] */
    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
    /* renamed from: spliterator */
    default Spliterator<Integer> spliterator2() {
        return IntSpliterators.asSpliterator(iterator2(), Size64.sizeOf(this), 321);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntCollection, java.util.Collection
    @Deprecated
    default boolean remove(Object o) {
        return super.remove(o);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntCollection
    @Deprecated
    default boolean add(Integer o) {
        return super.add(o);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntCollection, java.util.Collection
    @Deprecated
    default boolean contains(Object o) {
        return super.contains(o);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntCollection
    @Deprecated
    default boolean rem(int k) {
        return remove(k);
    }

    /* renamed from: of */
    static IntSet m187of() {
        return IntSets.UNMODIFIABLE_EMPTY_SET;
    }

    /* renamed from: of */
    static IntSet m186of(int e) {
        return IntSets.singleton(e);
    }

    /* renamed from: of */
    static IntSet m185of(int e0, int e1) {
        IntArraySet innerSet = new IntArraySet(2);
        innerSet.add(e0);
        if (!innerSet.add(e1)) {
            throw new IllegalArgumentException("Duplicate element: " + e1);
        }
        return IntSets.unmodifiable(innerSet);
    }

    /* renamed from: of */
    static IntSet m184of(int e0, int e1, int e2) {
        IntArraySet innerSet = new IntArraySet(3);
        innerSet.add(e0);
        if (!innerSet.add(e1)) {
            throw new IllegalArgumentException("Duplicate element: " + e1);
        }
        if (!innerSet.add(e2)) {
            throw new IllegalArgumentException("Duplicate element: " + e2);
        }
        return IntSets.unmodifiable(innerSet);
    }

    /* renamed from: of */
    static IntSet m183of(int... a) {
        IntSet intSet;
        switch (a.length) {
            case 0:
                return m187of();
            case 1:
                return m186of(a[0]);
            case 2:
                return m185of(a[0], a[1]);
            case 3:
                return m184of(a[0], a[1], a[2]);
            default:
                if (a.length <= 4) {
                    intSet = new IntArraySet(a.length);
                } else {
                    intSet = new IntOpenHashSet(a.length);
                }
                IntSet innerSet = intSet;
                for (int element : a) {
                    if (!innerSet.add(element)) {
                        throw new IllegalArgumentException("Duplicate element: " + element);
                    }
                }
                return IntSets.unmodifiable(innerSet);
        }
    }
}
