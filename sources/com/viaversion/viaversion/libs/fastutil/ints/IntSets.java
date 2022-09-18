package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.ints.IntCollections;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntSets.class */
public final class IntSets {
    static final int ARRAY_SET_CUTOFF = 4;
    public static final EmptySet EMPTY_SET = new EmptySet();
    static final IntSet UNMODIFIABLE_EMPTY_SET = unmodifiable(new IntArraySet(IntArrays.EMPTY_ARRAY));

    private IntSets() {
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntSets$EmptySet.class */
    public static class EmptySet extends IntCollections.EmptyCollection implements IntSet, Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSet
        public boolean remove(int ok) {
            throw new UnsupportedOperationException();
        }

        public Object clone() {
            return IntSets.EMPTY_SET;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntCollections.EmptyCollection, java.util.Collection
        public boolean equals(Object o) {
            return (o instanceof Set) && ((Set) o).isEmpty();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
        @Deprecated
        public boolean rem(int k) {
            return super.rem(k);
        }

        private Object readResolve() {
            return IntSets.EMPTY_SET;
        }
    }

    public static IntSet emptySet() {
        return EMPTY_SET;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntSets$Singleton.class */
    public static class Singleton extends AbstractIntSet implements Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final int element;

        public Singleton(int element) {
            this.element = element;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
        public boolean contains(int k) {
            return k == this.element;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntSet, com.viaversion.viaversion.libs.fastutil.ints.IntSet
        public boolean remove(int k) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntSet, com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
        public Iterator<Integer> iterator() {
            return IntIterators.singleton(this.element);
        }

        @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
        /* renamed from: spliterator */
        public Spliterator<Integer> spliterator2() {
            return IntSpliterators.singleton(this.element);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return 1;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
        public int[] toIntArray() {
            return new int[]{this.element};
        }

        @Override // java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntIterable
        @Deprecated
        public void forEach(Consumer<? super Integer> action) {
            action.accept(Integer.valueOf(this.element));
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection
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

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterable
        public void forEach(java.util.function.IntConsumer action) {
            action.accept(this.element);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
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

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        @Deprecated
        public Object[] toArray() {
            return new Object[]{Integer.valueOf(this.element)};
        }

        public Object clone() {
            return this;
        }
    }

    public static IntSet singleton(int element) {
        return new Singleton(element);
    }

    public static IntSet singleton(Integer element) {
        return new Singleton(element.intValue());
    }

    public static IntSet synchronize(IntSet s) {
        return new SynchronizedSet(s);
    }

    public static IntSet synchronize(IntSet s, Object sync) {
        return new SynchronizedSet(s, sync);
    }

    public static IntSet unmodifiable(IntSet s) {
        return new UnmodifiableSet(s);
    }

    public static IntSet fromTo(final int from, final int to) {
        return new AbstractIntSet() { // from class: com.viaversion.viaversion.libs.fastutil.ints.IntSets.1
            @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
            public boolean contains(int x) {
                return x >= from && x < to;
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntSet, com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
            /* renamed from: iterator */
            public Iterator<Integer> iterator2() {
                return IntIterators.fromTo(from, to);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                long size = to - from;
                if (size < 0 || size > 2147483647L) {
                    return Integer.MAX_VALUE;
                }
                return (int) size;
            }
        };
    }

    public static IntSet from(final int from) {
        return new AbstractIntSet() { // from class: com.viaversion.viaversion.libs.fastutil.ints.IntSets.2
            @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
            public boolean contains(int x) {
                return x >= from;
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntSet, com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
            /* renamed from: iterator */
            public Iterator<Integer> iterator2() {
                return IntIterators.concat(IntIterators.fromTo(from, Integer.MAX_VALUE), IntSets.singleton(Integer.MAX_VALUE).iterator2());
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                long size = (2147483647L - from) + 1;
                if (size < 0 || size > 2147483647L) {
                    return Integer.MAX_VALUE;
                }
                return (int) size;
            }
        };
    }

    /* renamed from: to */
    public static IntSet m182to(final int to) {
        return new AbstractIntSet() { // from class: com.viaversion.viaversion.libs.fastutil.ints.IntSets.3
            @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
            public boolean contains(int x) {
                return x < to;
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntSet, com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
            /* renamed from: iterator */
            public Iterator<Integer> iterator2() {
                return IntIterators.fromTo(Integer.MIN_VALUE, to);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                long size = to - (-2147483648L);
                if (size < 0 || size > 2147483647L) {
                    return Integer.MAX_VALUE;
                }
                return (int) size;
            }
        };
    }
}
