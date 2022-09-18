package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntIterable.class */
public interface IntIterable extends Iterable<Integer> {
    @Override // java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
    /* renamed from: iterator */
    Iterator<Integer> iterator2();

    /* JADX WARN: Type inference failed for: r0v1, types: [com.viaversion.viaversion.libs.fastutil.ints.IntIterator] */
    default IntIterator intIterator() {
        return iterator2();
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.viaversion.viaversion.libs.fastutil.ints.IntIterator] */
    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
    /* renamed from: spliterator */
    default Spliterator<Integer> spliterator2() {
        return IntSpliterators.asSpliteratorUnknownSize(iterator2(), 0);
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator] */
    default IntSpliterator intSpliterator() {
        return spliterator2();
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.viaversion.viaversion.libs.fastutil.ints.IntIterator] */
    default void forEach(java.util.function.IntConsumer action) {
        Objects.requireNonNull(action);
        iterator2().forEachRemaining(action);
    }

    default void forEach(IntConsumer action) {
        forEach((java.util.function.IntConsumer) action);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterable
    @Deprecated
    default void forEach(Consumer<? super Integer> action) {
        java.util.function.IntConsumer intConsumer;
        Objects.requireNonNull(action);
        if (action instanceof java.util.function.IntConsumer) {
            intConsumer = (java.util.function.IntConsumer) action;
        } else {
            Objects.requireNonNull(action);
            intConsumer = (v1) -> {
                r1.accept(v1);
            };
        }
        forEach(intConsumer);
    }
}
