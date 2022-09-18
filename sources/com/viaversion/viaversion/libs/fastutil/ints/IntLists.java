package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.ints.IntCollections;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.RandomAccess;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntLists.class */
public final class IntLists {
    public static final EmptyList EMPTY_LIST = new EmptyList();

    private IntLists() {
    }

    public static IntList shuffle(IntList l, Random random) {
        int i = l.size();
        while (true) {
            int i2 = i;
            i--;
            if (i2 != 0) {
                int p = random.nextInt(i + 1);
                int t = l.getInt(i);
                l.set(i, l.getInt(p));
                l.set(p, t);
            } else {
                return l;
            }
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntLists$EmptyList.class */
    public static class EmptyList extends IntCollections.EmptyCollection implements IntList, RandomAccess, Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyList() {
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
        public int getInt(int i) {
            throw new IndexOutOfBoundsException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
        public boolean rem(int k) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
        public int removeInt(int i) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
        public void add(int index, int k) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
        public int set(int index, int k) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
        public int indexOf(int k) {
            return -1;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
        public int lastIndexOf(int k) {
            return -1;
        }

        @Override // java.util.List
        public boolean addAll(int i, Collection<? extends Integer> c) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList, java.util.List
        @Deprecated
        public void replaceAll(UnaryOperator<Integer> operator) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
        public void replaceAll(java.util.function.IntUnaryOperator operator) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
        public boolean addAll(IntList c) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
        public boolean addAll(int i, IntCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
        public boolean addAll(int i, IntList c) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
        @Deprecated
        public void add(int index, Integer k) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList, java.util.List
        @Deprecated
        public Integer get(int index) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
        @Deprecated
        public boolean add(Integer k) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
        @Deprecated
        public Integer set(int index, Integer k) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList, java.util.List
        @Deprecated
        public Integer remove(int k) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList, java.util.List
        @Deprecated
        public int indexOf(Object k) {
            return -1;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList, java.util.List
        @Deprecated
        public int lastIndexOf(Object k) {
            return -1;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
        public void sort(IntComparator comparator) {
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
        public void unstableSort(IntComparator comparator) {
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList, java.util.List
        @Deprecated
        public void sort(Comparator<? super Integer> comparator) {
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
        @Deprecated
        public void unstableSort(Comparator<? super Integer> comparator) {
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList, java.util.List
        /* renamed from: listIterator */
        public ListIterator<Integer> listIterator2() {
            return IntIterators.EMPTY_ITERATOR;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntCollections.EmptyCollection, com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
        public Iterator<Integer> iterator() {
            return IntIterators.EMPTY_ITERATOR;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList, java.util.List
        /* renamed from: listIterator */
        public ListIterator<Integer> listIterator2(int i) {
            if (i == 0) {
                return IntIterators.EMPTY_ITERATOR;
            }
            throw new IndexOutOfBoundsException(String.valueOf(i));
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList, java.util.List
        /* renamed from: subList */
        public List<Integer> subList2(int from, int to) {
            if (from == 0 && to == 0) {
                return this;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
        public void getElements(int from, int[] a, int offset, int length) {
            if (from == 0 && length == 0 && offset >= 0 && offset <= a.length) {
                return;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
        public void removeElements(int from, int to) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
        public void addElements(int index, int[] a, int offset, int length) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
        public void addElements(int index, int[] a) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
        public void setElements(int[] a) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
        public void setElements(int index, int[] a) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
        public void setElements(int index, int[] a, int offset, int length) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
        public void size(int s) {
            throw new UnsupportedOperationException();
        }

        public int compareTo(List<? extends Integer> o) {
            return (o != this && !o.isEmpty()) ? -1 : 0;
        }

        public Object clone() {
            return IntLists.EMPTY_LIST;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntCollections.EmptyCollection, java.util.Collection
        public int hashCode() {
            return 1;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntCollections.EmptyCollection, java.util.Collection
        public boolean equals(Object o) {
            return (o instanceof List) && ((List) o).isEmpty();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection
        public String toString() {
            return "[]";
        }

        private Object readResolve() {
            return IntLists.EMPTY_LIST;
        }
    }

    public static IntList emptyList() {
        return EMPTY_LIST;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntLists$Singleton.class */
    public static class Singleton extends AbstractIntList implements RandomAccess, Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        private final int element;

        protected Singleton(int element) {
            this.element = element;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
        public int getInt(int i) {
            if (i == 0) {
                return this.element;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
        public boolean rem(int k) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList
        public int removeInt(int i) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
        public boolean contains(int k) {
            return k == this.element;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList
        public int indexOf(int k) {
            return k == this.element ? 0 : -1;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
        public int[] toIntArray() {
            return new int[]{this.element};
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList, java.util.List
        /* renamed from: listIterator */
        public ListIterator<Integer> listIterator2() {
            return IntIterators.singleton(this.element);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
        public Iterator<Integer> iterator() {
            return listIterator2();
        }

        @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
        /* renamed from: spliterator */
        public Spliterator<Integer> spliterator2() {
            return IntSpliterators.singleton(this.element);
        }

        /* JADX WARN: Type inference failed for: r0v4, types: [java.util.ListIterator<java.lang.Integer>, com.viaversion.viaversion.libs.fastutil.ints.IntListIterator] */
        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList, java.util.List
        /* renamed from: listIterator */
        public ListIterator<Integer> listIterator2(int i) {
            if (i > 1 || i < 0) {
                throw new IndexOutOfBoundsException();
            }
            ?? listIterator2 = listIterator2();
            if (i == 1) {
                listIterator2.nextInt();
            }
            return listIterator2;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList, java.util.List
        /* renamed from: subList */
        public List<Integer> subList2(int from, int to) {
            ensureIndex(from);
            ensureIndex(to);
            if (from > to) {
                throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
            }
            if (from != 0 || to != 1) {
                return IntLists.EMPTY_LIST;
            }
            return this;
        }

        @Override // java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntIterable
        @Deprecated
        public void forEach(Consumer<? super Integer> action) {
            action.accept(Integer.valueOf(this.element));
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, java.util.List
        public boolean addAll(int i, Collection<? extends Integer> c) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection
        public boolean addAll(Collection<? extends Integer> c) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection
        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection
        public boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Collection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
        @Deprecated
        public boolean removeIf(Predicate<? super Integer> filter) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList, java.util.List
        @Deprecated
        public void replaceAll(UnaryOperator<Integer> operator) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
        public void replaceAll(java.util.function.IntUnaryOperator operator) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntIterable
        public void forEach(java.util.function.IntConsumer action) {
            action.accept(this.element);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
        public boolean addAll(IntList c) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
        public boolean addAll(int i, IntList c) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList
        public boolean addAll(int i, IntCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
        public boolean addAll(IntCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
        public boolean removeAll(IntCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
        public boolean retainAll(IntCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntCollection
        public boolean removeIf(java.util.function.IntPredicate filter) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        @Deprecated
        public Object[] toArray() {
            return new Object[]{Integer.valueOf(this.element)};
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
        public void sort(IntComparator comparator) {
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
        public void unstableSort(IntComparator comparator) {
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList, java.util.List
        @Deprecated
        public void sort(Comparator<? super Integer> comparator) {
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
        @Deprecated
        public void unstableSort(Comparator<? super Integer> comparator) {
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList
        public void getElements(int from, int[] a, int offset, int length) {
            if (offset < 0) {
                throw new ArrayIndexOutOfBoundsException("Offset (" + offset + ") is negative");
            }
            if (offset + length > a.length) {
                throw new ArrayIndexOutOfBoundsException("End index (" + (offset + length) + ") is greater than array length (" + a.length + ")");
            }
            if (from + length > size()) {
                throw new IndexOutOfBoundsException("End index (" + (from + length) + ") is greater than list size (" + size() + ")");
            }
            if (length <= 0) {
                return;
            }
            a[offset] = this.element;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList
        public void removeElements(int from, int to) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList
        public void addElements(int index, int[] a) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList
        public void addElements(int index, int[] a, int offset, int length) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
        public void setElements(int[] a) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
        public void setElements(int index, int[] a) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList
        public void setElements(int index, int[] a, int offset, int length) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return 1;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList
        public void size(int size) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, java.util.AbstractCollection, java.util.Collection, java.util.List
        public void clear() {
            throw new UnsupportedOperationException();
        }

        public Object clone() {
            return this;
        }
    }

    public static IntList singleton(int element) {
        return new Singleton(element);
    }

    public static IntList singleton(Object element) {
        return new Singleton(((Integer) element).intValue());
    }

    public static IntList synchronize(IntList l) {
        return l instanceof RandomAccess ? new SynchronizedRandomAccessList(l) : new SynchronizedList(l);
    }

    public static IntList synchronize(IntList l, Object sync) {
        return l instanceof RandomAccess ? new SynchronizedRandomAccessList(l, sync) : new SynchronizedList(l, sync);
    }

    public static IntList unmodifiable(IntList l) {
        return l instanceof RandomAccess ? new UnmodifiableRandomAccessList(l) : new UnmodifiableList(l);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntLists$ImmutableListBase.class */
    public static abstract class ImmutableListBase extends AbstractIntList implements IntList {
        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList
        @Deprecated
        public final void add(int index, int k) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
        @Deprecated
        public final boolean add(int k) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection
        @Deprecated
        public final boolean addAll(Collection<? extends Integer> c) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, java.util.List
        @Deprecated
        public final boolean addAll(int index, Collection<? extends Integer> c) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList
        @Deprecated
        public final int removeInt(int index) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
        @Deprecated
        public final boolean rem(int k) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection
        @Deprecated
        public final boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection
        @Deprecated
        public final boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Collection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
        @Deprecated
        public final boolean removeIf(Predicate<? super Integer> c) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntCollection
        @Deprecated
        public final boolean removeIf(java.util.function.IntPredicate c) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList, java.util.List
        @Deprecated
        public final void replaceAll(UnaryOperator<Integer> operator) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
        @Deprecated
        public final void replaceAll(java.util.function.IntUnaryOperator operator) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
        @Deprecated
        public final void add(int index, Integer k) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
        @Deprecated
        public final boolean add(Integer k) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList, java.util.List
        @Deprecated
        public final Integer remove(int index) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
        @Deprecated
        public final boolean remove(Object k) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
        @Deprecated
        public final Integer set(int index, Integer k) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
        @Deprecated
        public final boolean addAll(IntCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
        @Deprecated
        public final boolean addAll(IntList c) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList
        @Deprecated
        public final boolean addAll(int index, IntCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
        @Deprecated
        public final boolean addAll(int index, IntList c) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
        @Deprecated
        public final boolean removeAll(IntCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
        @Deprecated
        public final boolean retainAll(IntCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList
        @Deprecated
        public final int set(int index, int k) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, java.util.AbstractCollection, java.util.Collection, java.util.List
        @Deprecated
        public final void clear() {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList
        @Deprecated
        public final void size(int size) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList
        @Deprecated
        public final void removeElements(int from, int to) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList
        @Deprecated
        public final void addElements(int index, int[] a, int offset, int length) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList
        @Deprecated
        public final void setElements(int index, int[] a, int offset, int length) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
        @Deprecated
        public final void sort(IntComparator comp) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
        @Deprecated
        public final void unstableSort(IntComparator comp) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList, java.util.List
        @Deprecated
        public final void sort(Comparator<? super Integer> comparator) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
        @Deprecated
        public final void unstableSort(Comparator<? super Integer> comparator) {
            throw new UnsupportedOperationException();
        }
    }
}
