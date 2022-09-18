package com.viaversion.viaversion.libs.fastutil;

import java.util.Comparator;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/PriorityQueue.class */
public interface PriorityQueue<K> {
    void enqueue(K k);

    K dequeue();

    int size();

    void clear();

    K first();

    Comparator<? super K> comparator();

    default boolean isEmpty() {
        return size() == 0;
    }

    default K last() {
        throw new UnsupportedOperationException();
    }

    default void changed() {
        throw new UnsupportedOperationException();
    }
}
