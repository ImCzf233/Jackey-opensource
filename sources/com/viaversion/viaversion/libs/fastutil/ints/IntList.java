package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Size64;
import com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.Spliterator;
import java.util.function.UnaryOperator;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntList.class */
public interface IntList extends List<Integer>, Comparable<List<? extends Integer>>, IntCollection {
    @Override // java.util.List, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
    Iterator<Integer> iterator();

    @Override // java.util.List
    /* renamed from: listIterator */
    ListIterator<Integer> listIterator2();

    @Override // java.util.List
    /* renamed from: listIterator */
    ListIterator<Integer> listIterator2(int i);

    @Override // java.util.List
    /* renamed from: subList */
    List<Integer> subList2(int i, int i2);

    void size(int i);

    void getElements(int i, int[] iArr, int i2, int i3);

    void removeElements(int i, int i2);

    void addElements(int i, int[] iArr);

    void addElements(int i, int[] iArr, int i2, int i3);

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntCollection
    boolean add(int i);

    void add(int i, int i2);

    boolean addAll(int i, IntCollection intCollection);

    int set(int i, int i2);

    int getInt(int i);

    int indexOf(int i);

    int lastIndexOf(int i);

    int removeInt(int i);

    /* JADX WARN: Type inference failed for: r0v3, types: [com.viaversion.viaversion.libs.fastutil.ints.IntListIterator, com.viaversion.viaversion.libs.fastutil.ints.IntIterator] */
    @Override // java.util.List, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
    /* renamed from: spliterator */
    default Spliterator<Integer> spliterator2() {
        if (this instanceof RandomAccess) {
            return new AbstractIntList.IndexBasedSpliterator(this, 0);
        }
        return IntSpliterators.asSpliterator(iterator(), Size64.sizeOf(this), IntSpliterators.LIST_SPLITERATOR_CHARACTERISTICS);
    }

    default void setElements(int[] a) {
        setElements(0, a);
    }

    default void setElements(int index, int[] a) {
        setElements(index, a, 0, a.length);
    }

    /* JADX WARN: Type inference failed for: r0v6, types: [com.viaversion.viaversion.libs.fastutil.ints.IntListIterator] */
    default void setElements(int index, int[] a, int offset, int length) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index (" + index + ") is negative");
        }
        if (index > size()) {
            throw new IndexOutOfBoundsException("Index (" + index + ") is greater than list size (" + size() + ")");
        }
        IntArrays.ensureOffsetLength(a, offset, length);
        if (index + length > size()) {
            throw new IndexOutOfBoundsException("End index (" + (index + length) + ") is greater than list size (" + size() + ")");
        }
        ?? listIterator2 = listIterator2(index);
        int i = 0;
        while (i < length) {
            listIterator2.nextInt();
            int i2 = i;
            i++;
            listIterator2.set(a[offset + i2]);
        }
    }

    @Deprecated
    default void add(int index, Integer key) {
        add(index, key.intValue());
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.viaversion.viaversion.libs.fastutil.ints.IntListIterator] */
    default void replaceAll(java.util.function.IntUnaryOperator operator) {
        ?? listIterator2 = listIterator2();
        while (listIterator2.hasNext()) {
            listIterator2.set(operator.applyAsInt(listIterator2.nextInt()));
        }
    }

    default void replaceAll(IntUnaryOperator operator) {
        replaceAll((java.util.function.IntUnaryOperator) operator);
    }

    @Override // java.util.List
    @Deprecated
    default void replaceAll(UnaryOperator<Integer> operator) {
        java.util.function.IntUnaryOperator intUnaryOperator;
        Objects.requireNonNull(operator);
        if (operator instanceof java.util.function.IntUnaryOperator) {
            intUnaryOperator = (java.util.function.IntUnaryOperator) operator;
        } else {
            Objects.requireNonNull(operator);
            intUnaryOperator = (v1) -> {
                return r1.apply(v1);
            };
        }
        replaceAll(intUnaryOperator);
    }

    @Override // java.util.List, java.util.Collection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
    @Deprecated
    default boolean contains(Object key) {
        return super.contains(key);
    }

    @Override // java.util.List
    @Deprecated
    default Integer get(int index) {
        return Integer.valueOf(getInt(index));
    }

    @Override // java.util.List
    @Deprecated
    default int indexOf(Object o) {
        return indexOf(((Integer) o).intValue());
    }

    @Override // java.util.List
    @Deprecated
    default int lastIndexOf(Object o) {
        return lastIndexOf(((Integer) o).intValue());
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntCollection
    @Deprecated
    default boolean add(Integer k) {
        return add(k.intValue());
    }

    @Override // java.util.List, java.util.Collection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
    @Deprecated
    default boolean remove(Object key) {
        return super.remove(key);
    }

    @Override // java.util.List
    @Deprecated
    default Integer remove(int index) {
        return Integer.valueOf(removeInt(index));
    }

    @Deprecated
    default Integer set(int index, Integer k) {
        return Integer.valueOf(set(index, k.intValue()));
    }

    default boolean addAll(int index, IntList l) {
        return addAll(index, (IntCollection) l);
    }

    default boolean addAll(IntList l) {
        return addAll(size(), l);
    }

    /* renamed from: of */
    static IntList m201of() {
        return IntImmutableList.m213of();
    }

    /* renamed from: of */
    static IntList m200of(int e) {
        return IntLists.singleton(e);
    }

    /* renamed from: of */
    static IntList m199of(int e0, int e1) {
        return IntImmutableList.m212of(e0, e1);
    }

    /* renamed from: of */
    static IntList m198of(int e0, int e1, int e2) {
        return IntImmutableList.m212of(e0, e1, e2);
    }

    /* renamed from: of */
    static IntList m197of(int... a) {
        switch (a.length) {
            case 0:
                return m201of();
            case 1:
                return m200of(a[0]);
            default:
                return IntImmutableList.m212of(a);
        }
    }

    @Override // java.util.List
    @Deprecated
    default void sort(Comparator<? super Integer> comparator) {
        sort(IntComparators.asIntComparator(comparator));
    }

    default void sort(IntComparator comparator) {
        if (comparator == null) {
            unstableSort(comparator);
            return;
        }
        int[] elements = toIntArray();
        IntArrays.stableSort(elements, comparator);
        setElements(elements);
    }

    @Deprecated
    default void unstableSort(Comparator<? super Integer> comparator) {
        unstableSort(IntComparators.asIntComparator(comparator));
    }

    default void unstableSort(IntComparator comparator) {
        int[] elements = toIntArray();
        if (comparator == null) {
            IntArrays.unstableSort(elements);
        } else {
            IntArrays.unstableSort(elements, comparator);
        }
        setElements(elements);
    }
}
