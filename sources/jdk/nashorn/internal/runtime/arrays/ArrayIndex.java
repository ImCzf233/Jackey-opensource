package jdk.nashorn.internal.runtime.arrays;

import jdk.nashorn.internal.runtime.ConsString;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptObject;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/arrays/ArrayIndex.class */
public final class ArrayIndex {
    private static final int INVALID_ARRAY_INDEX = -1;
    private static final long MAX_ARRAY_INDEX = 4294967294L;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !ArrayIndex.class.desiredAssertionStatus();
    }

    private ArrayIndex() {
    }

    private static long fromString(String key) {
        long value = 0;
        int length = key.length();
        if (length != 0) {
            if (length > 1 && key.charAt(0) == '0') {
                return -1L;
            }
            for (int i = 0; i < length; i++) {
                char digit = key.charAt(i);
                if (digit < '0' || digit > '9') {
                    return -1L;
                }
                value = ((value * 10) + digit) - 48;
                if (value > MAX_ARRAY_INDEX) {
                    return -1L;
                }
            }
            return value;
        }
        return -1L;
    }

    public static int getArrayIndex(Object key) {
        if (key instanceof Integer) {
            return getArrayIndex(((Integer) key).intValue());
        }
        if (key instanceof Double) {
            return getArrayIndex(((Double) key).doubleValue());
        }
        if (key instanceof String) {
            return (int) fromString((String) key);
        }
        if (key instanceof Long) {
            return getArrayIndex(((Long) key).longValue());
        }
        if (key instanceof ConsString) {
            return (int) fromString(key.toString());
        }
        if (!$assertionsDisabled && (key instanceof ScriptObject)) {
            throw new AssertionError();
        }
        return -1;
    }

    public static int getArrayIndex(int key) {
        if (key >= 0) {
            return key;
        }
        return -1;
    }

    public static int getArrayIndex(long key) {
        if (key >= 0 && key <= MAX_ARRAY_INDEX) {
            return (int) key;
        }
        return -1;
    }

    public static int getArrayIndex(double key) {
        if (JSType.isRepresentableAsInt(key)) {
            return getArrayIndex((int) key);
        }
        if (JSType.isRepresentableAsLong(key)) {
            return getArrayIndex((long) key);
        }
        return -1;
    }

    public static int getArrayIndex(String key) {
        return (int) fromString(key);
    }

    public static boolean isValidArrayIndex(int index) {
        return index != -1;
    }

    public static long toLongIndex(int index) {
        return JSType.toUint32(index);
    }

    public static String toKey(int index) {
        return Long.toString(JSType.toUint32(index));
    }
}
