package com.viaversion.viaversion.libs.fastutil.objects;

import java.util.Iterator;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectIterator.class */
public interface ObjectIterator<K> extends Iterator<K> {
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
            next();
        }
        return (n - i) - 1;
    }
}
