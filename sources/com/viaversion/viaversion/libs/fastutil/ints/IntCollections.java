package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.objects.ObjectArrays;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntCollections.class */
public final class IntCollections {
    private IntCollections() {
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntCollections$EmptyCollection.class */
    public static abstract class EmptyCollection extends AbstractIntCollection {
        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
        public boolean contains(int k) {
            return false;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public Object[] toArray() {
            return ObjectArrays.EMPTY_ARRAY;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public <T> T[] toArray(T[] array) {
            if (array.length > 0) {
                array[0] = null;
            }
            return array;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
        public Iterator<Integer> iterator() {
            return IntIterators.EMPTY_ITERATOR;
        }

        @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
        /* renamed from: spliterator */
        public Spliterator<Integer> spliterator2() {
            return IntSpliterators.EMPTY_SPLITERATOR;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            return 0;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public void clear() {
        }

        @Override // java.util.Collection
        public int hashCode() {
            return 0;
        }

        @Override // java.util.Collection
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof Collection)) {
                return false;
            }
            return ((Collection) o).isEmpty();
        }

        @Override // java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntIterable
        @Deprecated
        public void forEach(Consumer<? super Integer> action) {
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection
        public boolean containsAll(Collection<?> c) {
            return c.isEmpty();
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
            Objects.requireNonNull(filter);
            return false;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
        public int[] toIntArray() {
            return IntArrays.EMPTY_ARRAY;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
        @Deprecated
        public int[] toIntArray(int[] a) {
            return a;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterable
        public void forEach(java.util.function.IntConsumer action) {
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
        public boolean containsAll(IntCollection c) {
            return c.isEmpty();
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
            Objects.requireNonNull(filter);
            return false;
        }
    }

    public static IntCollection synchronize(IntCollection c) {
        return new SynchronizedCollection(c);
    }

    public static IntCollection synchronize(IntCollection c, Object sync) {
        return new SynchronizedCollection(c, sync);
    }

    public static IntCollection unmodifiable(IntCollection c) {
        return new UnmodifiableCollection(c);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntCollections$IterableCollection.class */
    public static class IterableCollection extends AbstractIntCollection implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final IntIterable iterable;

        protected IterableCollection(IntIterable iterable) {
            this.iterable = (IntIterable) Objects.requireNonNull(iterable);
        }

        /* JADX WARN: Type inference failed for: r0v2, types: [com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator] */
        /* JADX WARN: Type inference failed for: r0v8, types: [com.viaversion.viaversion.libs.fastutil.ints.IntIterator] */
        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            long size = this.iterable.spliterator2().getExactSizeIfKnown();
            if (size >= 0) {
                return (int) Math.min(2147483647L, size);
            }
            int c = 0;
            ?? iterator2 = iterator2();
            while (iterator2.hasNext()) {
                iterator2.nextInt();
                c++;
            }
            return c;
        }

        /* JADX WARN: Type inference failed for: r0v2, types: [com.viaversion.viaversion.libs.fastutil.ints.IntIterator] */
        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean isEmpty() {
            return !this.iterable.iterator2().hasNext();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
        /* renamed from: iterator */
        public Iterator<Integer> iterator2() {
            return this.iterable.iterator2();
        }

        @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
        /* renamed from: spliterator */
        public Spliterator<Integer> spliterator2() {
            return this.iterable.spliterator2();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable
        public IntIterator intIterator() {
            return this.iterable.intIterator();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable
        public IntSpliterator intSpliterator() {
            return this.iterable.intSpliterator();
        }
    }

    public static IntCollection asCollection(IntIterable iterable) {
        if (iterable instanceof IntCollection) {
            return (IntCollection) iterable;
        }
        return new IterableCollection(iterable);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntCollections$SizeDecreasingSupplier.class */
    public static class SizeDecreasingSupplier<C extends IntCollection> implements Supplier<C> {
        static final int RECOMMENDED_MIN_SIZE = 8;
        final AtomicInteger suppliedCount = new AtomicInteger(0);
        final int expectedFinalSize;
        final IntFunction<C> builder;

        public SizeDecreasingSupplier(int expectedFinalSize, IntFunction<C> builder) {
            this.expectedFinalSize = expectedFinalSize;
            this.builder = builder;
        }

        @Override // java.util.function.Supplier
        public C get() {
            int expectedNeededNextSize = 1 + ((this.expectedFinalSize - 1) / this.suppliedCount.incrementAndGet());
            if (expectedNeededNextSize < 0) {
                expectedNeededNextSize = 8;
            }
            return this.builder.apply(expectedNeededNextSize);
        }
    }
}
