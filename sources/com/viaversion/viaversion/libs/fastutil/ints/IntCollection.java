package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Size64;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntCollection.class */
public interface IntCollection extends Collection<Integer>, IntIterable {
    @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
    /* renamed from: iterator */
    Iterator<Integer> iterator2();

    boolean add(int i);

    boolean contains(int i);

    boolean rem(int i);

    int[] toIntArray();

    int[] toArray(int[] iArr);

    boolean addAll(IntCollection intCollection);

    boolean containsAll(IntCollection intCollection);

    boolean removeAll(IntCollection intCollection);

    boolean retainAll(IntCollection intCollection);

    /* JADX WARN: Type inference failed for: r0v1, types: [com.viaversion.viaversion.libs.fastutil.ints.IntIterator] */
    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterable
    default IntIterator intIterator() {
        return iterator2();
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.viaversion.viaversion.libs.fastutil.ints.IntIterator] */
    @Override // java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
    /* renamed from: spliterator */
    default Spliterator<Integer> spliterator2() {
        return IntSpliterators.asSpliterator(iterator2(), Size64.sizeOf(this), 320);
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator] */
    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterable
    default IntSpliterator intSpliterator() {
        return spliterator2();
    }

    @Deprecated
    default boolean add(Integer key) {
        return add(key.intValue());
    }

    @Override // java.util.Collection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
    @Deprecated
    default boolean contains(Object key) {
        if (key == null) {
            return false;
        }
        return contains(((Integer) key).intValue());
    }

    @Override // java.util.Collection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
    @Deprecated
    default boolean remove(Object key) {
        if (key == null) {
            return false;
        }
        return rem(((Integer) key).intValue());
    }

    @Deprecated
    default int[] toIntArray(int[] a) {
        return toArray(a);
    }

    @Override // java.util.Collection
    @Deprecated
    default boolean removeIf(Predicate<? super Integer> filter) {
        java.util.function.IntPredicate intPredicate;
        if (filter instanceof java.util.function.IntPredicate) {
            intPredicate = (java.util.function.IntPredicate) filter;
        } else {
            intPredicate = key -> {
                return filter.test(Integer.valueOf(key));
            };
        }
        return removeIf(intPredicate);
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [com.viaversion.viaversion.libs.fastutil.ints.IntIterator] */
    default boolean removeIf(java.util.function.IntPredicate filter) {
        Objects.requireNonNull(filter);
        boolean removed = false;
        ?? iterator2 = iterator2();
        while (iterator2.hasNext()) {
            if (filter.test(iterator2.nextInt())) {
                iterator2.remove();
                removed = true;
            }
        }
        return removed;
    }

    default boolean removeIf(IntPredicate filter) {
        return removeIf((java.util.function.IntPredicate) filter);
    }

    @Override // java.util.Collection
    @Deprecated
    default Stream<Integer> stream() {
        return super.stream();
    }

    default IntStream intStream() {
        return StreamSupport.intStream(intSpliterator(), false);
    }

    @Override // java.util.Collection
    @Deprecated
    default Stream<Integer> parallelStream() {
        return super.parallelStream();
    }

    default IntStream intParallelStream() {
        return StreamSupport.intStream(intSpliterator(), true);
    }
}
