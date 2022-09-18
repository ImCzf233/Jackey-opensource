package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.BidirectionalIterator;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectBidirectionalIterator.class */
public interface ObjectBidirectionalIterator<K> extends ObjectIterator<K>, BidirectionalIterator<K> {
    default int back(int n) {
        int i = n;
        while (true) {
            int i2 = i;
            i--;
            if (i2 == 0 || !hasPrevious()) {
                break;
            }
            previous();
        }
        return (n - i) - 1;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator
    default int skip(int n) {
        return super.skip(n);
    }
}
