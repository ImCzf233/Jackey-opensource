package com.viaversion.viaversion.libs.fastutil.objects;

import java.util.ListIterator;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectListIterator.class */
public interface ObjectListIterator<K> extends ObjectBidirectionalIterator<K>, ListIterator<K> {
    default void set(K k) {
        throw new UnsupportedOperationException();
    }

    default void add(K k) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Iterator, java.util.ListIterator
    default void remove() {
        throw new UnsupportedOperationException();
    }
}
