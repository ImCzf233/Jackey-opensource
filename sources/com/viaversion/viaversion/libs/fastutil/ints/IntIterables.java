package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.Iterator;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntIterables.class */
public final class IntIterables {
    private IntIterables() {
    }

    public static long size(IntIterable iterable) {
        long c = 0;
        Iterator<Integer> iterator2 = iterable.iterator2();
        while (iterator2.hasNext()) {
            iterator2.next().intValue();
            c++;
        }
        return c;
    }
}
