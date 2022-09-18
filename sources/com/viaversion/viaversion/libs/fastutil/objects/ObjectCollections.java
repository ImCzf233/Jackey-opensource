package com.viaversion.viaversion.libs.fastutil.objects;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectCollections.class */
public final class ObjectCollections {
    private ObjectCollections() {
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectCollections$EmptyCollection.class */
    public static abstract class EmptyCollection<K> extends AbstractObjectCollection<K> {
        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean contains(Object k) {
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

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable
        public ObjectBidirectionalIterator<K> iterator() {
            return ObjectIterators.EMPTY_ITERATOR;
        }

        @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectSet, java.util.Set
        public ObjectSpliterator<K> spliterator() {
            return ObjectSpliterators.EMPTY_SPLITERATOR;
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

        @Override // java.lang.Iterable
        public void forEach(Consumer<? super K> action) {
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean containsAll(Collection<?> c) {
            return c.isEmpty();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean addAll(Collection<? extends K> c) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Collection
        public boolean removeIf(Predicate<? super K> filter) {
            Objects.requireNonNull(filter);
            return false;
        }
    }

    public static <K> ObjectCollection<K> synchronize(ObjectCollection<K> c) {
        return new SynchronizedCollection(c);
    }

    public static <K> ObjectCollection<K> synchronize(ObjectCollection<K> c, Object sync) {
        return new SynchronizedCollection(c, sync);
    }

    public static <K> ObjectCollection<K> unmodifiable(ObjectCollection<? extends K> c) {
        return new UnmodifiableCollection(c);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectCollections$IterableCollection.class */
    public static class IterableCollection<K> extends AbstractObjectCollection<K> implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ObjectIterable<K> iterable;

        protected IterableCollection(ObjectIterable<K> iterable) {
            this.iterable = (ObjectIterable) Objects.requireNonNull(iterable);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            long size = this.iterable.spliterator().getExactSizeIfKnown();
            if (size >= 0) {
                return (int) Math.min(2147483647L, size);
            }
            int c = 0;
            ObjectIterator<K> iterator = iterator();
            while (iterator.hasNext()) {
                iterator.next();
                c++;
            }
            return c;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean isEmpty() {
            return !this.iterable.iterator().hasNext();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable
        public ObjectIterator<K> iterator() {
            return this.iterable.iterator();
        }

        @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectSet, java.util.Set
        public ObjectSpliterator<K> spliterator() {
            return this.iterable.spliterator();
        }
    }

    public static <K> ObjectCollection<K> asCollection(ObjectIterable<K> iterable) {
        if (iterable instanceof ObjectCollection) {
            return (ObjectCollection) iterable;
        }
        return new IterableCollection(iterable);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectCollections$SizeDecreasingSupplier.class */
    static class SizeDecreasingSupplier<K, C extends ObjectCollection<K>> implements Supplier<C> {
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
