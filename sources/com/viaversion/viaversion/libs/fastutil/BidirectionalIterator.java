package com.viaversion.viaversion.libs.fastutil;

import java.util.Iterator;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/BidirectionalIterator.class */
public interface BidirectionalIterator<K> extends Iterator<K> {
    K previous();

    boolean hasPrevious();
}
