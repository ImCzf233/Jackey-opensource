package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.function.BinaryOperator;

@FunctionalInterface
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntBinaryOperator.class */
public interface IntBinaryOperator extends BinaryOperator<Integer>, java.util.function.IntBinaryOperator {
    int apply(int i, int i2);

    @Override // java.util.function.IntBinaryOperator
    @Deprecated
    default int applyAsInt(int x, int y) {
        return apply(x, y);
    }

    @Deprecated
    default Integer apply(Integer x, Integer y) {
        return Integer.valueOf(apply(x.intValue(), y.intValue()));
    }
}
