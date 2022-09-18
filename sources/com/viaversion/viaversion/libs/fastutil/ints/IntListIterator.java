package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.ListIterator;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntListIterator.class */
public interface IntListIterator extends IntBidirectionalIterator, ListIterator<Integer> {
    default void set(int k) {
        throw new UnsupportedOperationException();
    }

    default void add(int k) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Iterator, java.util.ListIterator
    default void remove() {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    default void set(Integer k) {
        set(k.intValue());
    }

    @Deprecated
    default void add(Integer k) {
        add(k.intValue());
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt, java.util.Iterator
    @Deprecated
    default Integer next() {
        return super.next();
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntBidirectionalIterator, com.viaversion.viaversion.libs.fastutil.BidirectionalIterator, java.util.ListIterator
    @Deprecated
    default Integer previous() {
        return super.previous();
    }
}
