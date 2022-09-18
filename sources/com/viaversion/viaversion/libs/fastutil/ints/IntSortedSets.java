package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.ints.IntSets;
import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntSortedSets.class */
public final class IntSortedSets {
    public static final EmptySet EMPTY_SET = new EmptySet();

    private IntSortedSets() {
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntSortedSets$EmptySet.class */
    public static class EmptySet extends IntSets.EmptySet implements IntSortedSet, Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptySet() {
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet
        public IntBidirectionalIterator iterator(int from) {
            return IntIterators.EMPTY_ITERATOR;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet
        public IntSortedSet subSet(int from, int to) {
            return IntSortedSets.EMPTY_SET;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet
        public IntSortedSet headSet(int from) {
            return IntSortedSets.EMPTY_SET;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet
        public IntSortedSet tailSet(int to) {
            return IntSortedSets.EMPTY_SET;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet
        public int firstInt() {
            throw new NoSuchElementException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet
        public int lastInt() {
            throw new NoSuchElementException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet, java.util.SortedSet
        public IntComparator comparator() {
            return null;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet
        @Deprecated
        public IntSortedSet subSet(Integer from, Integer to) {
            return IntSortedSets.EMPTY_SET;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet
        @Deprecated
        public IntSortedSet headSet(Integer from) {
            return IntSortedSets.EMPTY_SET;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet
        @Deprecated
        public IntSortedSet tailSet(Integer to) {
            return IntSortedSets.EMPTY_SET;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet, java.util.SortedSet
        @Deprecated
        public Integer first() {
            throw new NoSuchElementException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet, java.util.SortedSet
        @Deprecated
        public Integer last() {
            throw new NoSuchElementException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSets.EmptySet
        public Object clone() {
            return IntSortedSets.EMPTY_SET;
        }

        private Object readResolve() {
            return IntSortedSets.EMPTY_SET;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntSortedSets$Singleton.class */
    public static class Singleton extends IntSets.Singleton implements IntSortedSet, Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        final IntComparator comparator;

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSets.Singleton, com.viaversion.viaversion.libs.fastutil.ints.AbstractIntSet, com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
        public /* bridge */ /* synthetic */ Iterator<Integer> iterator() {
            return super.iterator();
        }

        protected Singleton(int element, IntComparator comparator) {
            super(element);
            this.comparator = comparator;
        }

        Singleton(int element) {
            this(element, null);
        }

        final int compare(int k1, int k2) {
            return this.comparator == null ? Integer.compare(k1, k2) : this.comparator.compare(k1, k2);
        }

        /* JADX WARN: Type inference failed for: r0v1, types: [com.viaversion.viaversion.libs.fastutil.ints.IntListIterator, com.viaversion.viaversion.libs.fastutil.ints.IntBidirectionalIterator] */
        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet
        public IntBidirectionalIterator iterator(int from) {
            ?? it = iterator();
            if (compare(this.element, from) <= 0) {
                it.nextInt();
            }
            return it;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet, java.util.SortedSet
        public IntComparator comparator() {
            return this.comparator;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSets.Singleton, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
        /* renamed from: spliterator */
        public Spliterator<Integer> spliterator2() {
            return IntSpliterators.singleton(this.element, this.comparator);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet
        public IntSortedSet subSet(int from, int to) {
            if (compare(from, this.element) <= 0 && compare(this.element, to) < 0) {
                return this;
            }
            return IntSortedSets.EMPTY_SET;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet
        public IntSortedSet headSet(int to) {
            if (compare(this.element, to) < 0) {
                return this;
            }
            return IntSortedSets.EMPTY_SET;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet
        public IntSortedSet tailSet(int from) {
            if (compare(from, this.element) <= 0) {
                return this;
            }
            return IntSortedSets.EMPTY_SET;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet
        public int firstInt() {
            return this.element;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet
        public int lastInt() {
            return this.element;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet
        @Deprecated
        public IntSortedSet subSet(Integer from, Integer to) {
            return subSet(from.intValue(), to.intValue());
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet
        @Deprecated
        public IntSortedSet headSet(Integer to) {
            return headSet(to.intValue());
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet
        @Deprecated
        public IntSortedSet tailSet(Integer from) {
            return tailSet(from.intValue());
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet, java.util.SortedSet
        @Deprecated
        public Integer first() {
            return Integer.valueOf(this.element);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet, java.util.SortedSet
        @Deprecated
        public Integer last() {
            return Integer.valueOf(this.element);
        }
    }

    public static IntSortedSet singleton(int element) {
        return new Singleton(element);
    }

    public static IntSortedSet singleton(int element, IntComparator comparator) {
        return new Singleton(element, comparator);
    }

    public static IntSortedSet singleton(Object element) {
        return new Singleton(((Integer) element).intValue());
    }

    public static IntSortedSet singleton(Object element, IntComparator comparator) {
        return new Singleton(((Integer) element).intValue(), comparator);
    }

    public static IntSortedSet synchronize(IntSortedSet s) {
        return new SynchronizedSortedSet(s);
    }

    public static IntSortedSet synchronize(IntSortedSet s, Object sync) {
        return new SynchronizedSortedSet(s, sync);
    }

    public static IntSortedSet unmodifiable(IntSortedSet s) {
        return new UnmodifiableSortedSet(s);
    }
}
