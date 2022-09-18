package com.viaversion.viaversion.libs.fastutil;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/SafeMath.class */
public final class SafeMath {
    private SafeMath() {
    }

    public static char safeIntToChar(int value) {
        if (value < 0 || 65535 < value) {
            throw new IllegalArgumentException(value + " can't be represented as char");
        }
        return (char) value;
    }

    public static byte safeIntToByte(int value) {
        if (value < -128 || 127 < value) {
            throw new IllegalArgumentException(value + " can't be represented as byte (out of range)");
        }
        return (byte) value;
    }

    public static short safeIntToShort(int value) {
        if (value < -32768 || 32767 < value) {
            throw new IllegalArgumentException(value + " can't be represented as short (out of range)");
        }
        return (short) value;
    }

    public static char safeLongToChar(long value) {
        if (value < 0 || 65535 < value) {
            throw new IllegalArgumentException(value + " can't be represented as int (out of range)");
        }
        return (char) value;
    }

    public static byte safeLongToByte(long value) {
        if (value < -128 || 127 < value) {
            throw new IllegalArgumentException(value + " can't be represented as int (out of range)");
        }
        return (byte) value;
    }

    public static short safeLongToShort(long value) {
        if (value < -32768 || 32767 < value) {
            throw new IllegalArgumentException(value + " can't be represented as int (out of range)");
        }
        return (short) value;
    }

    public static int safeLongToInt(long value) {
        if (value < -2147483648L || 2147483647L < value) {
            throw new IllegalArgumentException(value + " can't be represented as int (out of range)");
        }
        return (int) value;
    }

    public static float safeDoubleToFloat(double value) {
        if (Double.isNaN(value)) {
            return Float.NaN;
        }
        if (Double.isInfinite(value)) {
            return value < 0.0d ? Float.NEGATIVE_INFINITY : Float.POSITIVE_INFINITY;
        } else if (value < -3.4028234663852886E38d || 3.4028234663852886E38d < value) {
            throw new IllegalArgumentException(value + " can't be represented as float (out of range)");
        } else {
            float floatValue = (float) value;
            if (floatValue == value) {
                return floatValue;
            }
            throw new IllegalArgumentException(value + " can't be represented as float (imprecise)");
        }
    }
}
