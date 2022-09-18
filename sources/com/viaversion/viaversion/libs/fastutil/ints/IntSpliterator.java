package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntSpliterator.class */
public interface IntSpliterator extends Spliterator.OfInt {
    @Override // java.util.Spliterator.OfInt, java.util.Spliterator.OfPrimitive, java.util.Spliterator
    IntSpliterator trySplit();

    @Override // java.util.Spliterator.OfInt, java.util.Spliterator
    @Deprecated
    default boolean tryAdvance(Consumer<? super Integer> action) {
        java.util.function.IntConsumer intConsumer;
        if (action instanceof java.util.function.IntConsumer) {
            intConsumer = (java.util.function.IntConsumer) action;
        } else {
            Objects.requireNonNull(action);
            intConsumer = (v1) -> {
                r1.accept(v1);
            };
        }
        return tryAdvance(intConsumer);
    }

    default boolean tryAdvance(IntConsumer action) {
        return tryAdvance((java.util.function.IntConsumer) action);
    }

    @Override // java.util.Spliterator.OfInt, java.util.Spliterator
    @Deprecated
    default void forEachRemaining(Consumer<? super Integer> action) {
        java.util.function.IntConsumer intConsumer;
        if (action instanceof java.util.function.IntConsumer) {
            intConsumer = (java.util.function.IntConsumer) action;
        } else {
            Objects.requireNonNull(action);
            intConsumer = (v1) -> {
                r1.accept(v1);
            };
        }
        forEachRemaining(intConsumer);
    }

    default void forEachRemaining(IntConsumer action) {
        forEachRemaining((java.util.function.IntConsumer) action);
    }

    default long skip(long n) {
        if (n < 0) {
            throw new IllegalArgumentException("Argument must be nonnegative: " + n);
        }
        long i = n;
        do {
            long j = i;
            i = j - 1;
            if (j == 0) {
                break;
            }
        } while (tryAdvance(unused -> {
        }));
        return (n - i) - 1;
    }

    @Override // java.util.Spliterator
    default IntComparator getComparator() {
        throw new IllegalStateException();
    }
}
