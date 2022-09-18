package com.viaversion.viaversion.util;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/util/MathUtil.class */
public final class MathUtil {
    public static int ceilLog2(int i) {
        if (i > 0) {
            return 32 - Integer.numberOfLeadingZeros(i - 1);
        }
        return 0;
    }

    public static int clamp(int i, int min, int max) {
        if (i < min) {
            return min;
        }
        return i > max ? max : i;
    }
}
