package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntBidirectionalIterator.class */
public interface IntBidirectionalIterator extends IntIterator, ObjectBidirectionalIterator<Integer> {
    int previousInt();

    @Override // com.viaversion.viaversion.libs.fastutil.BidirectionalIterator, java.util.ListIterator
    @Deprecated
    default Integer previous() {
        return Integer.valueOf(previousInt());
    }

    default int back(int n) {
        int i = n;
        while (true) {
            int i2 = i;
            i--;
            if (i2 == 0 || !hasPrevious()) {
                break;
            }
            previousInt();
        }
        return (n - i) - 1;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterator, com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator
    default int skip(int n) {
        return super.skip(n);
    }
}
