package jdk.nashorn.internal.runtime.regexp.joni;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/regexp/joni/MinMaxLen.class */
final class MinMaxLen {
    int min;
    int max;
    private static final short[] distValues = {1000, 500, 333, 250, 200, 167, 143, 125, 111, 100, 91, 83, 77, 71, 67, 63, 59, 56, 53, 50, 48, 45, 43, 42, 40, 38, 37, 36, 34, 33, 32, 31, 30, 29, 29, 28, 27, 26, 26, 25, 24, 24, 23, 23, 22, 22, 21, 21, 20, 20, 20, 19, 19, 19, 18, 18, 18, 17, 17, 17, 16, 16, 16, 16, 15, 15, 15, 15, 14, 14, 14, 14, 14, 14, 13, 13, 13, 13, 13, 13, 12, 12, 12, 12, 12, 12, 11, 11, 11, 11, 11, 11, 11, 11, 11, 10, 10, 10, 10, 10};
    static final int INFINITE_DISTANCE = Integer.MAX_VALUE;

    public MinMaxLen() {
    }

    MinMaxLen(int min, int max) {
        this.min = min;
        this.max = max;
    }

    int distanceValue() {
        if (this.max == Integer.MAX_VALUE) {
            return 0;
        }
        int d = this.max - this.min;
        if (d >= distValues.length) {
            return 1;
        }
        return distValues[d];
    }

    public int compareDistanceValue(MinMaxLen other, int v1p, int v2p) {
        int v1;
        int v2;
        if (v2p <= 0) {
            return -1;
        }
        if (v1p <= 0 || (v2 = v2p * other.distanceValue()) > (v1 = v1p * distanceValue())) {
            return 1;
        }
        if (v2 < v1) {
            return -1;
        }
        if (other.min < this.min) {
            return 1;
        }
        if (other.min > this.min) {
            return -1;
        }
        return 0;
    }

    public boolean equal(MinMaxLen other) {
        return this.min == other.min && this.max == other.max;
    }

    public void set(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public void clear() {
        this.max = 0;
        this.min = 0;
    }

    public void copy(MinMaxLen other) {
        this.min = other.min;
        this.max = other.max;
    }

    public void add(MinMaxLen other) {
        this.min = distanceAdd(this.min, other.min);
        this.max = distanceAdd(this.max, other.max);
    }

    void addLength(int len) {
        this.min = distanceAdd(this.min, len);
        this.max = distanceAdd(this.max, len);
    }

    public void altMerge(MinMaxLen other) {
        if (this.min > other.min) {
            this.min = other.min;
        }
        if (this.max < other.max) {
            this.max = other.max;
        }
    }

    public static int distanceAdd(int d1, int d2) {
        if (d1 != Integer.MAX_VALUE && d2 != Integer.MAX_VALUE && d1 <= Integer.MAX_VALUE - d2) {
            return d1 + d2;
        }
        return Integer.MAX_VALUE;
    }

    public static int distanceMultiply(int d, int m) {
        if (m == 0) {
            return 0;
        }
        if (d < Integer.MAX_VALUE / m) {
            return d * m;
        }
        return Integer.MAX_VALUE;
    }

    public static String distanceRangeToString(int a, int b) {
        String s;
        String s2 = (a == Integer.MAX_VALUE ? "inf" : "(" + a + ")") + "-";
        if (b == Integer.MAX_VALUE) {
            s = s2 + "inf";
        } else {
            s = s2 + "(" + b + ")";
        }
        return s;
    }
}
