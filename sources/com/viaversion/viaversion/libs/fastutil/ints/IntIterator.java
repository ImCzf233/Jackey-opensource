package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.Objects;
import java.util.PrimitiveIterator;
import java.util.function.Consumer;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntIterator.class */
public interface IntIterator extends PrimitiveIterator.OfInt {
    @Override // java.util.PrimitiveIterator.OfInt
    int nextInt();

    @Override // java.util.PrimitiveIterator.OfInt, java.util.Iterator
    @Deprecated
    default Integer next() {
        return Integer.valueOf(nextInt());
    }

    default void forEachRemaining(IntConsumer action) {
        forEachRemaining((java.util.function.IntConsumer) action);
    }

    @Override // java.util.PrimitiveIterator.OfInt, java.util.Iterator
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

    default int skip(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Argument must be nonnegative: " + n);
        }
        int i = n;
        while (true) {
            int i2 = i;
            i--;
            if (i2 == 0 || !hasNext()) {
                break;
            }
            nextInt();
        }
        return (n - i) - 1;
    }
}
