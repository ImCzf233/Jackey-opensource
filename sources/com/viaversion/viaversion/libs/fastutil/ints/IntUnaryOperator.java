package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.function.UnaryOperator;

@FunctionalInterface
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntUnaryOperator.class */
public interface IntUnaryOperator extends UnaryOperator<Integer>, java.util.function.IntUnaryOperator {
    int apply(int i);

    static IntUnaryOperator identity() {
        return i -> {
            return i;
        };
    }

    static IntUnaryOperator negation() {
        return i -> {
            return -i;
        };
    }

    @Override // java.util.function.IntUnaryOperator
    @Deprecated
    default int applyAsInt(int x) {
        return apply(x);
    }

    @Deprecated
    default Integer apply(Integer x) {
        return Integer.valueOf(apply(x.intValue()));
    }
}
