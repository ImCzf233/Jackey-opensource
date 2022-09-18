package com.viaversion.viaversion.libs.fastutil;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/BigListIterator.class */
public interface BigListIterator<K> extends BidirectionalIterator<K> {
    long nextIndex();

    long previousIndex();

    default void set(K e) {
        throw new UnsupportedOperationException();
    }

    default void add(K e) {
        throw new UnsupportedOperationException();
    }
}
