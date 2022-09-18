package com.viaversion.viaversion.libs.fastutil.objects;

import java.util.Spliterator;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectSpliterator.class */
public interface ObjectSpliterator<K> extends Spliterator<K> {
    @Override // java.util.Spliterator
    ObjectSpliterator<K> trySplit();

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
}
