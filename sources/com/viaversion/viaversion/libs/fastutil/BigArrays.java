package com.viaversion.viaversion.libs.fastutil;

import com.viaversion.viaversion.libs.fastutil.booleans.BooleanArrays;
import com.viaversion.viaversion.libs.fastutil.booleans.BooleanBigArrays;
import com.viaversion.viaversion.libs.fastutil.bytes.ByteArrays;
import com.viaversion.viaversion.libs.fastutil.bytes.ByteBigArrays;
import com.viaversion.viaversion.libs.fastutil.chars.CharArrays;
import com.viaversion.viaversion.libs.fastutil.chars.CharBigArrays;
import com.viaversion.viaversion.libs.fastutil.doubles.DoubleArrays;
import com.viaversion.viaversion.libs.fastutil.doubles.DoubleBigArrays;
import com.viaversion.viaversion.libs.fastutil.floats.FloatArrays;
import com.viaversion.viaversion.libs.fastutil.floats.FloatBigArrays;
import com.viaversion.viaversion.libs.fastutil.ints.IntArrays;
import com.viaversion.viaversion.libs.fastutil.ints.IntBigArrays;
import com.viaversion.viaversion.libs.fastutil.longs.LongArrays;
import com.viaversion.viaversion.libs.fastutil.longs.LongBigArrays;
import com.viaversion.viaversion.libs.fastutil.longs.LongComparator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectArrays;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectBigArrays;
import com.viaversion.viaversion.libs.fastutil.shorts.ShortArrays;
import com.viaversion.viaversion.libs.fastutil.shorts.ShortBigArrays;
import java.lang.reflect.Array;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicLongArray;
import kotlin.jvm.internal.LongCompanionObject;
import org.apache.log4j.spi.Configurator;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/BigArrays.class */
public class BigArrays {
    public static final int SEGMENT_SHIFT = 27;
    public static final int SEGMENT_SIZE = 134217728;
    public static final int SEGMENT_MASK = 134217727;
    private static final int SMALL = 7;
    private static final int MEDIUM = 40;

    protected BigArrays() {
    }

    public static int segment(long index) {
        return (int) (index >>> 27);
    }

    public static int displacement(long index) {
        return (int) (index & 134217727);
    }

    public static long start(int segment) {
        return segment << 27;
    }

    public static long nearestSegmentStart(long index, long min, long max) {
        long lower = start(segment(index));
        long upper = start(segment(index) + 1);
        if (upper >= max) {
            if (lower < min) {
                return index;
            }
            return lower;
        } else if (lower < min) {
            return upper;
        } else {
            long mid = lower + ((upper - lower) >> 1);
            return index <= mid ? lower : upper;
        }
    }

    public static long index(int segment, int displacement) {
        return start(segment) + displacement;
    }

    public static void ensureFromTo(long bigArrayLength, long from, long to) {
        if (from < 0) {
            throw new ArrayIndexOutOfBoundsException("Start index (" + from + ") is negative");
        }
        if (from > to) {
            throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
        }
        if (to > bigArrayLength) {
            throw new ArrayIndexOutOfBoundsException("End index (" + to + ") is greater than big-array length (" + bigArrayLength + ")");
        }
    }

    public static void ensureOffsetLength(long bigArrayLength, long offset, long length) {
        if (offset < 0) {
            throw new ArrayIndexOutOfBoundsException("Offset (" + offset + ") is negative");
        }
        if (length < 0) {
            throw new IllegalArgumentException("Length (" + length + ") is negative");
        }
        if (offset + length > bigArrayLength) {
            throw new ArrayIndexOutOfBoundsException("Last index (" + (offset + length) + ") is greater than big-array length (" + bigArrayLength + ")");
        }
    }

    public static void ensureLength(long bigArrayLength) {
        if (bigArrayLength < 0) {
            throw new IllegalArgumentException("Negative big-array size: " + bigArrayLength);
        }
        if (bigArrayLength >= 288230376017494016L) {
            throw new IllegalArgumentException("Big-array size too big: " + bigArrayLength);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static void inPlaceMerge(long from, long mid, long to, LongComparator comp, BigSwapper swapper) {
        long secondCut;
        long firstCut;
        if (from >= mid || mid >= to) {
            return;
        }
        if (to - from == 2) {
            if (comp.compare(mid, from) < 0) {
                swapper.swap(from, mid);
                return;
            }
            return;
        }
        if (mid - from > to - mid) {
            firstCut = from + ((mid - from) / 2);
            secondCut = lowerBound(mid, to, firstCut, comp);
        } else {
            secondCut = mid + ((to - mid) / 2);
            firstCut = upperBound(from, mid, secondCut, comp);
        }
        long first2 = firstCut;
        long last2 = secondCut;
        if (mid != first2 && mid != last2) {
            long first1 = first2;
            long last1 = mid;
            while (true) {
                long j = first1;
                last1 = j;
                if (j >= last1 - 1) {
                    break;
                }
                first1++;
                swapper.swap(swapper, last1);
            }
            long first12 = mid;
            long last12 = last2;
            while (true) {
                long j2 = last12 - 1;
                last12 = j2;
                if (first12 >= j2) {
                    break;
                }
                long j3 = first12;
                first12 = j3 + 1;
                swapper.swap(j3, last12);
            }
            long first13 = first2;
            long last13 = last2;
            while (true) {
                long j4 = last13 - 1;
                last13 = j4;
                if (first13 >= j4) {
                    break;
                }
                long j5 = first13;
                first13 = j5 + 1;
                swapper.swap(j5, last13);
            }
        }
        long mid2 = firstCut + (secondCut - mid);
        inPlaceMerge(from, firstCut, mid2, comp, swapper);
        inPlaceMerge(mid2, secondCut, to, comp, swapper);
    }

    private static long lowerBound(long mid, long to, long firstCut, LongComparator comp) {
        long j = to - mid;
        while (true) {
            long len = j;
            if (len > 0) {
                long half = len / 2;
                long middle = mid + half;
                if (comp.compare(middle, firstCut) < 0) {
                    mid = middle + 1;
                    j = len - (half + 1);
                } else {
                    j = half;
                }
            } else {
                return mid;
            }
        }
    }

    private static long med3(long a, long b, long c, LongComparator comp) {
        int ab = comp.compare(a, b);
        int ac = comp.compare(a, c);
        int bc = comp.compare(b, c);
        return ab < 0 ? bc < 0 ? b : ac < 0 ? c : a : bc > 0 ? b : ac > 0 ? c : a;
    }

    public static void mergeSort(long from, long to, LongComparator comp, BigSwapper swapper) {
        long length = to - from;
        if (length < 7) {
            long j = from;
            while (true) {
                long i = j;
                if (i < to) {
                    long j2 = i;
                    while (true) {
                        long j3 = j2;
                        if (j3 > from && comp.compare(j3 - 1, j3) > 0) {
                            swapper.swap(j3, j3 - 1);
                            j2 = j3 - 1;
                        }
                    }
                    j = i + 1;
                } else {
                    return;
                }
            }
        } else {
            long mid = (from + to) >>> 1;
            mergeSort(from, mid, comp, swapper);
            mergeSort(mid, to, comp, swapper);
            if (comp.compare(mid - 1, mid) <= 0) {
                return;
            }
            inPlaceMerge(from, mid, to, comp, swapper);
        }
    }

    public static void quickSort(long from, long to, LongComparator comp, BigSwapper swapper) {
        int comparison;
        int comparison2;
        long len = to - from;
        if (len < 7) {
            long j = from;
            while (true) {
                long i = j;
                if (i < to) {
                    long j2 = i;
                    while (true) {
                        long j3 = j2;
                        if (j3 > from && comp.compare(j3 - 1, j3) > 0) {
                            swapper.swap(j3, j3 - 1);
                            j2 = j3 - 1;
                        }
                    }
                    j = i + 1;
                } else {
                    return;
                }
            }
        } else {
            long m = from + (len / 2);
            if (len > 7) {
                long l = from;
                long n = to - 1;
                if (len > 40) {
                    long s = len / 8;
                    l = med3(l, l + s, l + (2 * s), comp);
                    m = med3(m - s, m, m + s, comp);
                    n = med3(n - (2 * s), n - s, n, comp);
                }
                m = med3(l, m, n, comp);
            }
            long a = from;
            long b = a;
            long c = to - 1;
            long d = c;
            while (true) {
                if (b <= c && (comparison2 = comp.compare(b, m)) <= 0) {
                    if (comparison2 == 0) {
                        if (a == m) {
                            m = b;
                        } else if (b == m) {
                            m = a;
                        }
                        long j4 = a;
                        a = j4 + 1;
                        swapper.swap(j4, b);
                    }
                    b++;
                } else {
                    while (c >= b && (comparison = comp.compare(c, m)) >= 0) {
                        if (comparison == 0) {
                            if (c == m) {
                                m = d;
                            } else if (d == m) {
                                m = c;
                            }
                            long j5 = d;
                            d = j5 - 1;
                            swapper.swap(j5, j5);
                        }
                        c--;
                    }
                    if (b > c) {
                        break;
                    }
                    if (b == m) {
                        m = d;
                    } else if (c == m) {
                        m = c;
                    }
                    long j6 = b;
                    b = j6 + 1;
                    long j7 = c;
                    c = j7 - 1;
                    swapper.swap(j6, j7);
                }
            }
            long n2 = from + len;
            long s2 = Math.min(a - from, b - a);
            vecSwap(swapper, from, b - s2, s2);
            long s3 = Math.min(d - c, (n2 - d) - 1);
            long s4 = n2 - s3;
            vecSwap(swapper, b, s4, s3);
            if (b - a > 1) {
                quickSort(from, from + s4, comp, swapper);
            }
            long s5 = d - c;
            if (s5 > 1) {
                quickSort(n2 - s5, n2, comp, swapper);
            }
        }
    }

    private static long upperBound(long from, long mid, long secondCut, LongComparator comp) {
        long j = mid - from;
        while (true) {
            long len = j;
            if (len > 0) {
                long half = len / 2;
                long middle = from + half;
                if (comp.compare(secondCut, middle) < 0) {
                    j = half;
                } else {
                    from = middle + 1;
                    j = len - (half + 1);
                }
            } else {
                return from;
            }
        }
    }

    private static void vecSwap(BigSwapper swapper, long from, long l, long s) {
        int i = 0;
        while (i < s) {
            swapper.swap(from, l);
            i++;
            from++;
            l++;
        }
    }

    public static byte get(byte[][] array, long index) {
        return array[segment(index)][displacement(index)];
    }

    public static void set(byte[][] array, long index, byte value) {
        array[segment(index)][displacement(index)] = value;
    }

    public static void swap(byte[][] array, long first, long second) {
        byte t = array[segment(first)][displacement(first)];
        array[segment(first)][displacement(first)] = array[segment(second)][displacement(second)];
        array[segment(second)][displacement(second)] = t;
    }

    public static byte[][] reverse(byte[][] a) {
        long length = length(a);
        long i = length / 2;
        while (true) {
            long j = i;
            i = j - 1;
            if (j != 0) {
                swap(a, i, (length - i) - 1);
            } else {
                return a;
            }
        }
    }

    public static void add(byte[][] array, long index, byte incr) {
        byte[] bArr = array[segment(index)];
        int displacement = displacement(index);
        bArr[displacement] = (byte) (bArr[displacement] + incr);
    }

    public static void mul(byte[][] array, long index, byte factor) {
        byte[] bArr = array[segment(index)];
        int displacement = displacement(index);
        bArr[displacement] = (byte) (bArr[displacement] * factor);
    }

    public static void incr(byte[][] array, long index) {
        byte[] bArr = array[segment(index)];
        int displacement = displacement(index);
        bArr[displacement] = (byte) (bArr[displacement] + 1);
    }

    public static void decr(byte[][] array, long index) {
        byte[] bArr = array[segment(index)];
        int displacement = displacement(index);
        bArr[displacement] = (byte) (bArr[displacement] - 1);
    }

    public static long length(byte[][] array) {
        int length = array.length;
        if (length == 0) {
            return 0L;
        }
        return start(length - 1) + array[length - 1].length;
    }

    public static void copy(byte[][] srcArray, long srcPos, byte[][] destArray, long destPos, long length) {
        if (destPos <= srcPos) {
            int srcSegment = segment(srcPos);
            int destSegment = segment(destPos);
            int srcDispl = displacement(srcPos);
            int destDispl = displacement(destPos);
            while (length > 0) {
                int l = (int) Math.min(length, Math.min(srcArray[srcSegment].length - srcDispl, destArray[destSegment].length - destDispl));
                if (l == 0) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                System.arraycopy(srcArray[srcSegment], srcDispl, destArray[destSegment], destDispl, l);
                int i = srcDispl + l;
                srcDispl = i;
                if (i == 134217728) {
                    srcDispl = 0;
                    srcSegment++;
                }
                int i2 = destDispl + l;
                destDispl = i2;
                if (i2 == 134217728) {
                    destDispl = 0;
                    destSegment++;
                }
                length -= l;
            }
            return;
        }
        int srcSegment2 = segment(srcPos + length);
        int destSegment2 = segment(destPos + length);
        int srcDispl2 = displacement(srcPos + length);
        int destDispl2 = displacement(destPos + length);
        while (length > 0) {
            if (srcDispl2 == 0) {
                srcDispl2 = 134217728;
                srcSegment2--;
            }
            if (destDispl2 == 0) {
                destDispl2 = 134217728;
                destSegment2--;
            }
            int l2 = (int) Math.min(length, Math.min(srcDispl2, destDispl2));
            if (l2 == 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            System.arraycopy(srcArray[srcSegment2], srcDispl2 - l2, destArray[destSegment2], destDispl2 - l2, l2);
            srcDispl2 -= l2;
            destDispl2 -= l2;
            length -= l2;
        }
    }

    public static void copyFromBig(byte[][] srcArray, long srcPos, byte[] destArray, int destPos, int length) {
        int srcSegment = segment(srcPos);
        int srcDispl = displacement(srcPos);
        while (length > 0) {
            int l = Math.min(srcArray[srcSegment].length - srcDispl, length);
            if (l == 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            System.arraycopy(srcArray[srcSegment], srcDispl, destArray, destPos, l);
            int i = srcDispl + l;
            srcDispl = i;
            if (i == 134217728) {
                srcDispl = 0;
                srcSegment++;
            }
            destPos += l;
            length -= l;
        }
    }

    public static void copyToBig(byte[] srcArray, int srcPos, byte[][] destArray, long destPos, long length) {
        int destSegment = segment(destPos);
        int destDispl = displacement(destPos);
        while (length > 0) {
            int l = (int) Math.min(destArray[destSegment].length - destDispl, length);
            if (l == 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            System.arraycopy(srcArray, srcPos, destArray[destSegment], destDispl, l);
            int i = destDispl + l;
            destDispl = i;
            if (i == 134217728) {
                destDispl = 0;
                destSegment++;
            }
            srcPos += l;
            length -= l;
        }
    }

    /* JADX WARN: Type inference failed for: r0v13, types: [byte[], byte[][]] */
    public static byte[][] wrap(byte[] array) {
        if (array.length == 0) {
            return ByteBigArrays.EMPTY_BIG_ARRAY;
        }
        if (array.length <= 134217728) {
            return new byte[]{array};
        }
        byte[][] bigArray = ByteBigArrays.newBigArray(array.length);
        for (int i = 0; i < bigArray.length; i++) {
            System.arraycopy(array, (int) start(i), bigArray[i], 0, bigArray[i].length);
        }
        return bigArray;
    }

    public static byte[][] ensureCapacity(byte[][] array, long length) {
        return ensureCapacity(array, length, length(array));
    }

    public static byte[][] forceCapacity(byte[][] array, long length, long preserve) {
        ensureLength(length);
        int valid = array.length - ((array.length == 0 || (array.length > 0 && array[array.length - 1].length == 134217728)) ? 0 : 1);
        int baseLength = (int) ((length + 134217727) >>> 27);
        byte[][] base = (byte[][]) java.util.Arrays.copyOf(array, baseLength);
        int residual = (int) (length & 134217727);
        if (residual != 0) {
            for (int i = valid; i < baseLength - 1; i++) {
                base[i] = new byte[134217728];
            }
            base[baseLength - 1] = new byte[residual];
        } else {
            for (int i2 = valid; i2 < baseLength; i2++) {
                base[i2] = new byte[134217728];
            }
        }
        if (preserve - (valid * 134217728) > 0) {
            copy(array, valid * 134217728, base, valid * 134217728, preserve - (valid * 134217728));
        }
        return base;
    }

    public static byte[][] ensureCapacity(byte[][] array, long length, long preserve) {
        return length > length(array) ? forceCapacity(array, length, preserve) : array;
    }

    public static byte[][] grow(byte[][] array, long length) {
        long oldLength = length(array);
        return length > oldLength ? grow(array, length, oldLength) : array;
    }

    public static byte[][] grow(byte[][] array, long length, long preserve) {
        long oldLength = length(array);
        if (length > oldLength) {
            return ensureCapacity(array, Math.max(oldLength + (oldLength >> 1), length), preserve);
        }
        return array;
    }

    public static byte[][] trim(byte[][] array, long length) {
        ensureLength(length);
        long oldLength = length(array);
        if (length >= oldLength) {
            return array;
        }
        int baseLength = (int) ((length + 134217727) >>> 27);
        byte[][] base = (byte[][]) java.util.Arrays.copyOf(array, baseLength);
        int residual = (int) (length & 134217727);
        if (residual != 0) {
            base[baseLength - 1] = ByteArrays.trim(base[baseLength - 1], residual);
        }
        return base;
    }

    public static byte[][] setLength(byte[][] array, long length) {
        long oldLength = length(array);
        if (length == oldLength) {
            return array;
        }
        if (length < oldLength) {
            return trim(array, length);
        }
        return ensureCapacity(array, length);
    }

    public static byte[][] copy(byte[][] array, long offset, long length) {
        ensureOffsetLength(array, offset, length);
        byte[][] a = ByteBigArrays.newBigArray(length);
        copy(array, offset, a, 0L, length);
        return a;
    }

    public static byte[][] copy(byte[][] array) {
        byte[][] base = (byte[][]) array.clone();
        int i = base.length;
        while (true) {
            int i2 = i;
            i--;
            if (i2 != 0) {
                base[i] = (byte[]) array[i].clone();
            } else {
                return base;
            }
        }
    }

    public static void fill(byte[][] array, byte value) {
        int i = array.length;
        while (true) {
            int i2 = i;
            i--;
            if (i2 != 0) {
                java.util.Arrays.fill(array[i], value);
            } else {
                return;
            }
        }
    }

    public static void fill(byte[][] array, long from, long to, byte value) {
        long length = length(array);
        ensureFromTo(length, from, to);
        if (length == 0) {
            return;
        }
        int fromSegment = segment(from);
        int toSegment = segment(to);
        int fromDispl = displacement(from);
        int toDispl = displacement(to);
        if (fromSegment == toSegment) {
            java.util.Arrays.fill(array[fromSegment], fromDispl, toDispl, value);
            return;
        }
        if (toDispl != 0) {
            java.util.Arrays.fill(array[toSegment], 0, toDispl, value);
        }
        while (true) {
            toSegment--;
            if (toSegment > fromSegment) {
                java.util.Arrays.fill(array[toSegment], value);
            } else {
                java.util.Arrays.fill(array[fromSegment], fromDispl, 134217728, value);
                return;
            }
        }
    }

    public static boolean equals(byte[][] a1, byte[][] a2) {
        if (length(a1) != length(a2)) {
            return false;
        }
        int i = a1.length;
        while (true) {
            int i2 = i;
            i--;
            if (i2 != 0) {
                byte[] t = a1[i];
                byte[] u = a2[i];
                int j = t.length;
                while (true) {
                    int i3 = j;
                    j--;
                    if (i3 != 0) {
                        if (t[j] != u[j]) {
                            return false;
                        }
                    }
                }
            } else {
                return true;
            }
        }
    }

    public static String toString(byte[][] a) {
        if (a == null) {
            return Configurator.NULL;
        }
        long last = length(a) - 1;
        if (last == -1) {
            return "[]";
        }
        StringBuilder b = new StringBuilder();
        b.append('[');
        long j = 0;
        while (true) {
            long i = j;
            b.append(String.valueOf((int) get(a, i)));
            if (i == last) {
                return b.append(']').toString();
            }
            b.append(", ");
            j = i + 1;
        }
    }

    public static void ensureFromTo(byte[][] a, long from, long to) {
        ensureFromTo(length(a), from, to);
    }

    public static void ensureOffsetLength(byte[][] a, long offset, long length) {
        ensureOffsetLength(length(a), offset, length);
    }

    public static void ensureSameLength(byte[][] a, byte[][] b) {
        if (length(a) != length(b)) {
            throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b));
        }
    }

    public static byte[][] shuffle(byte[][] a, long from, long to, Random random) {
        long i = to - from;
        while (true) {
            long j = i;
            i = j - 1;
            if (j != 0) {
                long p = (random.nextLong() & LongCompanionObject.MAX_VALUE) % (i + 1);
                byte t = get(a, from + i);
                set(a, from + i, get(a, from + p));
                set(a, from + p, t);
            } else {
                return a;
            }
        }
    }

    public static byte[][] shuffle(byte[][] a, Random random) {
        long i = length(a);
        while (true) {
            long j = i;
            i = j - 1;
            if (j != 0) {
                long p = (random.nextLong() & LongCompanionObject.MAX_VALUE) % (i + 1);
                byte t = get(a, i);
                set(a, i, get(a, p));
                set(a, p, t);
            } else {
                return a;
            }
        }
    }

    public static int get(int[][] array, long index) {
        return array[segment(index)][displacement(index)];
    }

    public static void set(int[][] array, long index, int value) {
        array[segment(index)][displacement(index)] = value;
    }

    public static long length(AtomicIntegerArray[] array) {
        int length = array.length;
        if (length == 0) {
            return 0L;
        }
        return start(length - 1) + array[length - 1].length();
    }

    public static int get(AtomicIntegerArray[] array, long index) {
        return array[segment(index)].get(displacement(index));
    }

    public static void set(AtomicIntegerArray[] array, long index, int value) {
        array[segment(index)].set(displacement(index), value);
    }

    public static int getAndSet(AtomicIntegerArray[] array, long index, int value) {
        return array[segment(index)].getAndSet(displacement(index), value);
    }

    public static int getAndAdd(AtomicIntegerArray[] array, long index, int value) {
        return array[segment(index)].getAndAdd(displacement(index), value);
    }

    public static int addAndGet(AtomicIntegerArray[] array, long index, int value) {
        return array[segment(index)].addAndGet(displacement(index), value);
    }

    public static int getAndIncrement(AtomicIntegerArray[] array, long index) {
        return array[segment(index)].getAndDecrement(displacement(index));
    }

    public static int incrementAndGet(AtomicIntegerArray[] array, long index) {
        return array[segment(index)].incrementAndGet(displacement(index));
    }

    public static int getAndDecrement(AtomicIntegerArray[] array, long index) {
        return array[segment(index)].getAndDecrement(displacement(index));
    }

    public static int decrementAndGet(AtomicIntegerArray[] array, long index) {
        return array[segment(index)].decrementAndGet(displacement(index));
    }

    public static boolean compareAndSet(AtomicIntegerArray[] array, long index, int expected, int value) {
        return array[segment(index)].compareAndSet(displacement(index), expected, value);
    }

    public static void swap(int[][] array, long first, long second) {
        int t = array[segment(first)][displacement(first)];
        array[segment(first)][displacement(first)] = array[segment(second)][displacement(second)];
        array[segment(second)][displacement(second)] = t;
    }

    public static int[][] reverse(int[][] a) {
        long length = length(a);
        long i = length / 2;
        while (true) {
            long j = i;
            i = j - 1;
            if (j != 0) {
                swap(a, i, (length - i) - 1);
            } else {
                return a;
            }
        }
    }

    public static void add(int[][] array, long index, int incr) {
        int[] iArr = array[segment(index)];
        int displacement = displacement(index);
        iArr[displacement] = iArr[displacement] + incr;
    }

    public static void mul(int[][] array, long index, int factor) {
        int[] iArr = array[segment(index)];
        int displacement = displacement(index);
        iArr[displacement] = iArr[displacement] * factor;
    }

    public static void incr(int[][] array, long index) {
        int[] iArr = array[segment(index)];
        int displacement = displacement(index);
        iArr[displacement] = iArr[displacement] + 1;
    }

    public static void decr(int[][] array, long index) {
        int[] iArr = array[segment(index)];
        int displacement = displacement(index);
        iArr[displacement] = iArr[displacement] - 1;
    }

    public static long length(int[][] array) {
        int length = array.length;
        if (length == 0) {
            return 0L;
        }
        return start(length - 1) + array[length - 1].length;
    }

    public static void copy(int[][] srcArray, long srcPos, int[][] destArray, long destPos, long length) {
        if (destPos <= srcPos) {
            int srcSegment = segment(srcPos);
            int destSegment = segment(destPos);
            int srcDispl = displacement(srcPos);
            int destDispl = displacement(destPos);
            while (length > 0) {
                int l = (int) Math.min(length, Math.min(srcArray[srcSegment].length - srcDispl, destArray[destSegment].length - destDispl));
                if (l == 0) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                System.arraycopy(srcArray[srcSegment], srcDispl, destArray[destSegment], destDispl, l);
                int i = srcDispl + l;
                srcDispl = i;
                if (i == 134217728) {
                    srcDispl = 0;
                    srcSegment++;
                }
                int i2 = destDispl + l;
                destDispl = i2;
                if (i2 == 134217728) {
                    destDispl = 0;
                    destSegment++;
                }
                length -= l;
            }
            return;
        }
        int srcSegment2 = segment(srcPos + length);
        int destSegment2 = segment(destPos + length);
        int srcDispl2 = displacement(srcPos + length);
        int destDispl2 = displacement(destPos + length);
        while (length > 0) {
            if (srcDispl2 == 0) {
                srcDispl2 = 134217728;
                srcSegment2--;
            }
            if (destDispl2 == 0) {
                destDispl2 = 134217728;
                destSegment2--;
            }
            int l2 = (int) Math.min(length, Math.min(srcDispl2, destDispl2));
            if (l2 == 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            System.arraycopy(srcArray[srcSegment2], srcDispl2 - l2, destArray[destSegment2], destDispl2 - l2, l2);
            srcDispl2 -= l2;
            destDispl2 -= l2;
            length -= l2;
        }
    }

    public static void copyFromBig(int[][] srcArray, long srcPos, int[] destArray, int destPos, int length) {
        int srcSegment = segment(srcPos);
        int srcDispl = displacement(srcPos);
        while (length > 0) {
            int l = Math.min(srcArray[srcSegment].length - srcDispl, length);
            if (l == 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            System.arraycopy(srcArray[srcSegment], srcDispl, destArray, destPos, l);
            int i = srcDispl + l;
            srcDispl = i;
            if (i == 134217728) {
                srcDispl = 0;
                srcSegment++;
            }
            destPos += l;
            length -= l;
        }
    }

    public static void copyToBig(int[] srcArray, int srcPos, int[][] destArray, long destPos, long length) {
        int destSegment = segment(destPos);
        int destDispl = displacement(destPos);
        while (length > 0) {
            int l = (int) Math.min(destArray[destSegment].length - destDispl, length);
            if (l == 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            System.arraycopy(srcArray, srcPos, destArray[destSegment], destDispl, l);
            int i = destDispl + l;
            destDispl = i;
            if (i == 134217728) {
                destDispl = 0;
                destSegment++;
            }
            srcPos += l;
            length -= l;
        }
    }

    /* JADX WARN: Type inference failed for: r0v13, types: [int[], int[][]] */
    public static int[][] wrap(int[] array) {
        if (array.length == 0) {
            return IntBigArrays.EMPTY_BIG_ARRAY;
        }
        if (array.length <= 134217728) {
            return new int[]{array};
        }
        int[][] bigArray = IntBigArrays.newBigArray(array.length);
        for (int i = 0; i < bigArray.length; i++) {
            System.arraycopy(array, (int) start(i), bigArray[i], 0, bigArray[i].length);
        }
        return bigArray;
    }

    public static int[][] ensureCapacity(int[][] array, long length) {
        return ensureCapacity(array, length, length(array));
    }

    public static int[][] forceCapacity(int[][] array, long length, long preserve) {
        ensureLength(length);
        int valid = array.length - ((array.length == 0 || (array.length > 0 && array[array.length - 1].length == 134217728)) ? 0 : 1);
        int baseLength = (int) ((length + 134217727) >>> 27);
        int[][] base = (int[][]) java.util.Arrays.copyOf(array, baseLength);
        int residual = (int) (length & 134217727);
        if (residual != 0) {
            for (int i = valid; i < baseLength - 1; i++) {
                base[i] = new int[134217728];
            }
            base[baseLength - 1] = new int[residual];
        } else {
            for (int i2 = valid; i2 < baseLength; i2++) {
                base[i2] = new int[134217728];
            }
        }
        if (preserve - (valid * 134217728) > 0) {
            copy(array, valid * 134217728, base, valid * 134217728, preserve - (valid * 134217728));
        }
        return base;
    }

    public static int[][] ensureCapacity(int[][] array, long length, long preserve) {
        return length > length(array) ? forceCapacity(array, length, preserve) : array;
    }

    public static int[][] grow(int[][] array, long length) {
        long oldLength = length(array);
        return length > oldLength ? grow(array, length, oldLength) : array;
    }

    public static int[][] grow(int[][] array, long length, long preserve) {
        long oldLength = length(array);
        if (length > oldLength) {
            return ensureCapacity(array, Math.max(oldLength + (oldLength >> 1), length), preserve);
        }
        return array;
    }

    public static int[][] trim(int[][] array, long length) {
        ensureLength(length);
        long oldLength = length(array);
        if (length >= oldLength) {
            return array;
        }
        int baseLength = (int) ((length + 134217727) >>> 27);
        int[][] base = (int[][]) java.util.Arrays.copyOf(array, baseLength);
        int residual = (int) (length & 134217727);
        if (residual != 0) {
            base[baseLength - 1] = IntArrays.trim(base[baseLength - 1], residual);
        }
        return base;
    }

    public static int[][] setLength(int[][] array, long length) {
        long oldLength = length(array);
        if (length == oldLength) {
            return array;
        }
        if (length < oldLength) {
            return trim(array, length);
        }
        return ensureCapacity(array, length);
    }

    public static int[][] copy(int[][] array, long offset, long length) {
        ensureOffsetLength(array, offset, length);
        int[][] a = IntBigArrays.newBigArray(length);
        copy(array, offset, a, 0L, length);
        return a;
    }

    public static int[][] copy(int[][] array) {
        int[][] base = (int[][]) array.clone();
        int i = base.length;
        while (true) {
            int i2 = i;
            i--;
            if (i2 != 0) {
                base[i] = (int[]) array[i].clone();
            } else {
                return base;
            }
        }
    }

    public static void fill(int[][] array, int value) {
        int i = array.length;
        while (true) {
            int i2 = i;
            i--;
            if (i2 != 0) {
                java.util.Arrays.fill(array[i], value);
            } else {
                return;
            }
        }
    }

    public static void fill(int[][] array, long from, long to, int value) {
        long length = length(array);
        ensureFromTo(length, from, to);
        if (length == 0) {
            return;
        }
        int fromSegment = segment(from);
        int toSegment = segment(to);
        int fromDispl = displacement(from);
        int toDispl = displacement(to);
        if (fromSegment == toSegment) {
            java.util.Arrays.fill(array[fromSegment], fromDispl, toDispl, value);
            return;
        }
        if (toDispl != 0) {
            java.util.Arrays.fill(array[toSegment], 0, toDispl, value);
        }
        while (true) {
            toSegment--;
            if (toSegment > fromSegment) {
                java.util.Arrays.fill(array[toSegment], value);
            } else {
                java.util.Arrays.fill(array[fromSegment], fromDispl, 134217728, value);
                return;
            }
        }
    }

    public static boolean equals(int[][] a1, int[][] a2) {
        if (length(a1) != length(a2)) {
            return false;
        }
        int i = a1.length;
        while (true) {
            int i2 = i;
            i--;
            if (i2 != 0) {
                int[] t = a1[i];
                int[] u = a2[i];
                int j = t.length;
                while (true) {
                    int i3 = j;
                    j--;
                    if (i3 != 0) {
                        if (t[j] != u[j]) {
                            return false;
                        }
                    }
                }
            } else {
                return true;
            }
        }
    }

    public static String toString(int[][] a) {
        if (a == null) {
            return Configurator.NULL;
        }
        long last = length(a) - 1;
        if (last == -1) {
            return "[]";
        }
        StringBuilder b = new StringBuilder();
        b.append('[');
        long j = 0;
        while (true) {
            long i = j;
            b.append(String.valueOf(get(a, i)));
            if (i == last) {
                return b.append(']').toString();
            }
            b.append(", ");
            j = i + 1;
        }
    }

    public static void ensureFromTo(int[][] a, long from, long to) {
        ensureFromTo(length(a), from, to);
    }

    public static void ensureOffsetLength(int[][] a, long offset, long length) {
        ensureOffsetLength(length(a), offset, length);
    }

    public static void ensureSameLength(int[][] a, int[][] b) {
        if (length(a) != length(b)) {
            throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b));
        }
    }

    public static int[][] shuffle(int[][] a, long from, long to, Random random) {
        long i = to - from;
        while (true) {
            long j = i;
            i = j - 1;
            if (j != 0) {
                long p = (random.nextLong() & LongCompanionObject.MAX_VALUE) % (i + 1);
                int t = get(a, from + i);
                set(a, from + i, get(a, from + p));
                set(a, from + p, t);
            } else {
                return a;
            }
        }
    }

    public static int[][] shuffle(int[][] a, Random random) {
        long i = length(a);
        while (true) {
            long j = i;
            i = j - 1;
            if (j != 0) {
                long p = (random.nextLong() & LongCompanionObject.MAX_VALUE) % (i + 1);
                int t = get(a, i);
                set(a, i, get(a, p));
                set(a, p, t);
            } else {
                return a;
            }
        }
    }

    public static long get(long[][] array, long index) {
        return array[segment(index)][displacement(index)];
    }

    public static void set(long[][] array, long index, long value) {
        array[segment(index)][displacement(index)] = value;
    }

    public static long length(AtomicLongArray[] array) {
        int length = array.length;
        if (length == 0) {
            return 0L;
        }
        return start(length - 1) + array[length - 1].length();
    }

    public static long get(AtomicLongArray[] array, long index) {
        return array[segment(index)].get(displacement(index));
    }

    public static void set(AtomicLongArray[] array, long index, long value) {
        array[segment(index)].set(displacement(index), value);
    }

    public static long getAndSet(AtomicLongArray[] array, long index, long value) {
        return array[segment(index)].getAndSet(displacement(index), value);
    }

    public static long getAndAdd(AtomicLongArray[] array, long index, long value) {
        return array[segment(index)].getAndAdd(displacement(index), value);
    }

    public static long addAndGet(AtomicLongArray[] array, long index, long value) {
        return array[segment(index)].addAndGet(displacement(index), value);
    }

    public static long getAndIncrement(AtomicLongArray[] array, long index) {
        return array[segment(index)].getAndDecrement(displacement(index));
    }

    public static long incrementAndGet(AtomicLongArray[] array, long index) {
        return array[segment(index)].incrementAndGet(displacement(index));
    }

    public static long getAndDecrement(AtomicLongArray[] array, long index) {
        return array[segment(index)].getAndDecrement(displacement(index));
    }

    public static long decrementAndGet(AtomicLongArray[] array, long index) {
        return array[segment(index)].decrementAndGet(displacement(index));
    }

    public static boolean compareAndSet(AtomicLongArray[] array, long index, long expected, long value) {
        return array[segment(index)].compareAndSet(displacement(index), expected, value);
    }

    public static void swap(long[][] array, long first, long second) {
        long t = array[segment(first)][displacement(first)];
        array[segment(first)][displacement(first)] = array[segment(second)][displacement(second)];
        array[segment(second)][displacement(second)] = t;
    }

    public static long[][] reverse(long[][] a) {
        long length = length(a);
        long i = length / 2;
        while (true) {
            long j = i;
            i = j - 1;
            if (j != 0) {
                swap(a, i, (length - i) - 1);
            } else {
                return a;
            }
        }
    }

    public static void add(long[][] array, long index, long incr) {
        long[] jArr = array[segment(index)];
        int displacement = displacement(index);
        jArr[displacement] = jArr[displacement] + incr;
    }

    public static void mul(long[][] array, long index, long factor) {
        long[] jArr = array[segment(index)];
        int displacement = displacement(index);
        jArr[displacement] = jArr[displacement] * factor;
    }

    public static void incr(long[][] array, long index) {
        long[] jArr = array[segment(index)];
        int displacement = displacement(index);
        jArr[displacement] = jArr[displacement] + 1;
    }

    public static void decr(long[][] array, long index) {
        long[] jArr = array[segment(index)];
        int displacement = displacement(index);
        jArr[displacement] = jArr[displacement] - 1;
    }

    public static long length(long[][] array) {
        int length = array.length;
        if (length == 0) {
            return 0L;
        }
        return start(length - 1) + array[length - 1].length;
    }

    public static void copy(long[][] srcArray, long srcPos, long[][] destArray, long destPos, long length) {
        if (destPos <= srcPos) {
            int srcSegment = segment(srcPos);
            int destSegment = segment(destPos);
            int srcDispl = displacement(srcPos);
            int destDispl = displacement(destPos);
            while (length > 0) {
                int l = (int) Math.min(length, Math.min(srcArray[srcSegment].length - srcDispl, destArray[destSegment].length - destDispl));
                if (l == 0) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                System.arraycopy(srcArray[srcSegment], srcDispl, destArray[destSegment], destDispl, l);
                int i = srcDispl + l;
                srcDispl = i;
                if (i == 134217728) {
                    srcDispl = 0;
                    srcSegment++;
                }
                int i2 = destDispl + l;
                destDispl = i2;
                if (i2 == 134217728) {
                    destDispl = 0;
                    destSegment++;
                }
                length -= l;
            }
            return;
        }
        int srcSegment2 = segment(srcPos + length);
        int destSegment2 = segment(destPos + length);
        int srcDispl2 = displacement(srcPos + length);
        int destDispl2 = displacement(destPos + length);
        while (length > 0) {
            if (srcDispl2 == 0) {
                srcDispl2 = 134217728;
                srcSegment2--;
            }
            if (destDispl2 == 0) {
                destDispl2 = 134217728;
                destSegment2--;
            }
            int l2 = (int) Math.min(length, Math.min(srcDispl2, destDispl2));
            if (l2 == 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            System.arraycopy(srcArray[srcSegment2], srcDispl2 - l2, destArray[destSegment2], destDispl2 - l2, l2);
            srcDispl2 -= l2;
            destDispl2 -= l2;
            length -= l2;
        }
    }

    public static void copyFromBig(long[][] srcArray, long srcPos, long[] destArray, int destPos, int length) {
        int srcSegment = segment(srcPos);
        int srcDispl = displacement(srcPos);
        while (length > 0) {
            int l = Math.min(srcArray[srcSegment].length - srcDispl, length);
            if (l == 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            System.arraycopy(srcArray[srcSegment], srcDispl, destArray, destPos, l);
            int i = srcDispl + l;
            srcDispl = i;
            if (i == 134217728) {
                srcDispl = 0;
                srcSegment++;
            }
            destPos += l;
            length -= l;
        }
    }

    public static void copyToBig(long[] srcArray, int srcPos, long[][] destArray, long destPos, long length) {
        int destSegment = segment(destPos);
        int destDispl = displacement(destPos);
        while (length > 0) {
            int l = (int) Math.min(destArray[destSegment].length - destDispl, length);
            if (l == 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            System.arraycopy(srcArray, srcPos, destArray[destSegment], destDispl, l);
            int i = destDispl + l;
            destDispl = i;
            if (i == 134217728) {
                destDispl = 0;
                destSegment++;
            }
            srcPos += l;
            length -= l;
        }
    }

    /* JADX WARN: Type inference failed for: r0v13, types: [long[], long[][]] */
    public static long[][] wrap(long[] array) {
        if (array.length == 0) {
            return LongBigArrays.EMPTY_BIG_ARRAY;
        }
        if (array.length <= 134217728) {
            return new long[]{array};
        }
        long[][] bigArray = LongBigArrays.newBigArray(array.length);
        for (int i = 0; i < bigArray.length; i++) {
            System.arraycopy(array, (int) start(i), bigArray[i], 0, bigArray[i].length);
        }
        return bigArray;
    }

    public static long[][] ensureCapacity(long[][] array, long length) {
        return ensureCapacity(array, length, length(array));
    }

    public static long[][] forceCapacity(long[][] array, long length, long preserve) {
        ensureLength(length);
        int valid = array.length - ((array.length == 0 || (array.length > 0 && array[array.length - 1].length == 134217728)) ? 0 : 1);
        int baseLength = (int) ((length + 134217727) >>> 27);
        long[][] base = (long[][]) java.util.Arrays.copyOf(array, baseLength);
        int residual = (int) (length & 134217727);
        if (residual != 0) {
            for (int i = valid; i < baseLength - 1; i++) {
                base[i] = new long[134217728];
            }
            base[baseLength - 1] = new long[residual];
        } else {
            for (int i2 = valid; i2 < baseLength; i2++) {
                base[i2] = new long[134217728];
            }
        }
        if (preserve - (valid * 134217728) > 0) {
            copy(array, valid * 134217728, base, valid * 134217728, preserve - (valid * 134217728));
        }
        return base;
    }

    public static long[][] ensureCapacity(long[][] array, long length, long preserve) {
        return length > length(array) ? forceCapacity(array, length, preserve) : array;
    }

    public static long[][] grow(long[][] array, long length) {
        long oldLength = length(array);
        return length > oldLength ? grow(array, length, oldLength) : array;
    }

    public static long[][] grow(long[][] array, long length, long preserve) {
        long oldLength = length(array);
        if (length > oldLength) {
            return ensureCapacity(array, Math.max(oldLength + (oldLength >> 1), length), preserve);
        }
        return array;
    }

    public static long[][] trim(long[][] array, long length) {
        ensureLength(length);
        long oldLength = length(array);
        if (length >= oldLength) {
            return array;
        }
        int baseLength = (int) ((length + 134217727) >>> 27);
        long[][] base = (long[][]) java.util.Arrays.copyOf(array, baseLength);
        int residual = (int) (length & 134217727);
        if (residual != 0) {
            base[baseLength - 1] = LongArrays.trim(base[baseLength - 1], residual);
        }
        return base;
    }

    public static long[][] setLength(long[][] array, long length) {
        long oldLength = length(array);
        if (length == oldLength) {
            return array;
        }
        if (length < oldLength) {
            return trim(array, length);
        }
        return ensureCapacity(array, length);
    }

    public static long[][] copy(long[][] array, long offset, long length) {
        ensureOffsetLength(array, offset, length);
        long[][] a = LongBigArrays.newBigArray(length);
        copy(array, offset, a, 0L, length);
        return a;
    }

    public static long[][] copy(long[][] array) {
        long[][] base = (long[][]) array.clone();
        int i = base.length;
        while (true) {
            int i2 = i;
            i--;
            if (i2 != 0) {
                base[i] = (long[]) array[i].clone();
            } else {
                return base;
            }
        }
    }

    public static void fill(long[][] array, long value) {
        int i = array.length;
        while (true) {
            int i2 = i;
            i--;
            if (i2 != 0) {
                java.util.Arrays.fill(array[i], value);
            } else {
                return;
            }
        }
    }

    public static void fill(long[][] array, long from, long to, long value) {
        long length = length(array);
        ensureFromTo(length, from, to);
        if (length == 0) {
            return;
        }
        int fromSegment = segment(from);
        int toSegment = segment(to);
        int fromDispl = displacement(from);
        int toDispl = displacement(to);
        if (fromSegment == toSegment) {
            java.util.Arrays.fill(array[fromSegment], fromDispl, toDispl, value);
            return;
        }
        if (toDispl != 0) {
            java.util.Arrays.fill(array[toSegment], 0, toDispl, value);
        }
        while (true) {
            toSegment--;
            if (toSegment > fromSegment) {
                java.util.Arrays.fill(array[toSegment], value);
            } else {
                java.util.Arrays.fill(array[fromSegment], fromDispl, 134217728, value);
                return;
            }
        }
    }

    public static boolean equals(long[][] a1, long[][] a2) {
        if (length(a1) != length(a2)) {
            return false;
        }
        int i = a1.length;
        while (true) {
            int i2 = i;
            i--;
            if (i2 != 0) {
                long[] t = a1[i];
                long[] u = a2[i];
                int j = t.length;
                while (true) {
                    int i3 = j;
                    j--;
                    if (i3 != 0) {
                        if (t[j] != u[j]) {
                            return false;
                        }
                    }
                }
            } else {
                return true;
            }
        }
    }

    public static String toString(long[][] a) {
        if (a == null) {
            return Configurator.NULL;
        }
        long last = length(a) - 1;
        if (last == -1) {
            return "[]";
        }
        StringBuilder b = new StringBuilder();
        b.append('[');
        long j = 0;
        while (true) {
            long i = j;
            b.append(String.valueOf(get(a, i)));
            if (i == last) {
                return b.append(']').toString();
            }
            b.append(", ");
            j = i + 1;
        }
    }

    public static void ensureFromTo(long[][] a, long from, long to) {
        ensureFromTo(length(a), from, to);
    }

    public static void ensureOffsetLength(long[][] a, long offset, long length) {
        ensureOffsetLength(length(a), offset, length);
    }

    public static void ensureSameLength(long[][] a, long[][] b) {
        if (length(a) != length(b)) {
            throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b));
        }
    }

    public static long[][] shuffle(long[][] a, long from, long to, Random random) {
        long i = to - from;
        while (true) {
            long j = i;
            i = j - 1;
            if (j != 0) {
                long p = (random.nextLong() & LongCompanionObject.MAX_VALUE) % (i + 1);
                long t = get(a, from + i);
                set(a, from + i, get(a, from + p));
                set(a, from + p, t);
            } else {
                return a;
            }
        }
    }

    public static long[][] shuffle(long[][] a, Random random) {
        long i = length(a);
        while (true) {
            long j = i;
            i = j - 1;
            if (j != 0) {
                long p = (random.nextLong() & LongCompanionObject.MAX_VALUE) % (i + 1);
                long t = get(a, i);
                set(a, i, get(a, p));
                set(a, p, t);
            } else {
                return a;
            }
        }
    }

    public static double get(double[][] array, long index) {
        return array[segment(index)][displacement(index)];
    }

    public static void set(double[][] array, long index, double value) {
        array[segment(index)][displacement(index)] = value;
    }

    public static void swap(double[][] array, long first, long second) {
        double t = array[segment(first)][displacement(first)];
        array[segment(first)][displacement(first)] = array[segment(second)][displacement(second)];
        array[segment(second)][displacement(second)] = t;
    }

    public static double[][] reverse(double[][] a) {
        long length = length(a);
        long i = length / 2;
        while (true) {
            long j = i;
            i = j - 1;
            if (j != 0) {
                swap(a, i, (length - i) - 1);
            } else {
                return a;
            }
        }
    }

    public static void add(double[][] array, long index, double incr) {
        double[] dArr = array[segment(index)];
        int displacement = displacement(index);
        dArr[displacement] = dArr[displacement] + incr;
    }

    public static void mul(double[][] array, long index, double factor) {
        double[] dArr = array[segment(index)];
        int displacement = displacement(index);
        dArr[displacement] = dArr[displacement] * factor;
    }

    public static void incr(double[][] array, long index) {
        double[] dArr = array[segment(index)];
        int displacement = displacement(index);
        dArr[displacement] = dArr[displacement] + 1.0d;
    }

    public static void decr(double[][] array, long index) {
        double[] dArr = array[segment(index)];
        int displacement = displacement(index);
        dArr[displacement] = dArr[displacement] - 1.0d;
    }

    public static long length(double[][] array) {
        int length = array.length;
        if (length == 0) {
            return 0L;
        }
        return start(length - 1) + array[length - 1].length;
    }

    public static void copy(double[][] srcArray, long srcPos, double[][] destArray, long destPos, long length) {
        if (destPos <= srcPos) {
            int srcSegment = segment(srcPos);
            int destSegment = segment(destPos);
            int srcDispl = displacement(srcPos);
            int destDispl = displacement(destPos);
            while (length > 0) {
                int l = (int) Math.min(length, Math.min(srcArray[srcSegment].length - srcDispl, destArray[destSegment].length - destDispl));
                if (l == 0) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                System.arraycopy(srcArray[srcSegment], srcDispl, destArray[destSegment], destDispl, l);
                int i = srcDispl + l;
                srcDispl = i;
                if (i == 134217728) {
                    srcDispl = 0;
                    srcSegment++;
                }
                int i2 = destDispl + l;
                destDispl = i2;
                if (i2 == 134217728) {
                    destDispl = 0;
                    destSegment++;
                }
                length -= l;
            }
            return;
        }
        int srcSegment2 = segment(srcPos + length);
        int destSegment2 = segment(destPos + length);
        int srcDispl2 = displacement(srcPos + length);
        int destDispl2 = displacement(destPos + length);
        while (length > 0) {
            if (srcDispl2 == 0) {
                srcDispl2 = 134217728;
                srcSegment2--;
            }
            if (destDispl2 == 0) {
                destDispl2 = 134217728;
                destSegment2--;
            }
            int l2 = (int) Math.min(length, Math.min(srcDispl2, destDispl2));
            if (l2 == 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            System.arraycopy(srcArray[srcSegment2], srcDispl2 - l2, destArray[destSegment2], destDispl2 - l2, l2);
            srcDispl2 -= l2;
            destDispl2 -= l2;
            length -= l2;
        }
    }

    public static void copyFromBig(double[][] srcArray, long srcPos, double[] destArray, int destPos, int length) {
        int srcSegment = segment(srcPos);
        int srcDispl = displacement(srcPos);
        while (length > 0) {
            int l = Math.min(srcArray[srcSegment].length - srcDispl, length);
            if (l == 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            System.arraycopy(srcArray[srcSegment], srcDispl, destArray, destPos, l);
            int i = srcDispl + l;
            srcDispl = i;
            if (i == 134217728) {
                srcDispl = 0;
                srcSegment++;
            }
            destPos += l;
            length -= l;
        }
    }

    public static void copyToBig(double[] srcArray, int srcPos, double[][] destArray, long destPos, long length) {
        int destSegment = segment(destPos);
        int destDispl = displacement(destPos);
        while (length > 0) {
            int l = (int) Math.min(destArray[destSegment].length - destDispl, length);
            if (l == 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            System.arraycopy(srcArray, srcPos, destArray[destSegment], destDispl, l);
            int i = destDispl + l;
            destDispl = i;
            if (i == 134217728) {
                destDispl = 0;
                destSegment++;
            }
            srcPos += l;
            length -= l;
        }
    }

    /* JADX WARN: Type inference failed for: r0v13, types: [double[], double[][]] */
    public static double[][] wrap(double[] array) {
        if (array.length == 0) {
            return DoubleBigArrays.EMPTY_BIG_ARRAY;
        }
        if (array.length <= 134217728) {
            return new double[]{array};
        }
        double[][] bigArray = DoubleBigArrays.newBigArray(array.length);
        for (int i = 0; i < bigArray.length; i++) {
            System.arraycopy(array, (int) start(i), bigArray[i], 0, bigArray[i].length);
        }
        return bigArray;
    }

    public static double[][] ensureCapacity(double[][] array, long length) {
        return ensureCapacity(array, length, length(array));
    }

    public static double[][] forceCapacity(double[][] array, long length, long preserve) {
        ensureLength(length);
        int valid = array.length - ((array.length == 0 || (array.length > 0 && array[array.length - 1].length == 134217728)) ? 0 : 1);
        int baseLength = (int) ((length + 134217727) >>> 27);
        double[][] base = (double[][]) java.util.Arrays.copyOf(array, baseLength);
        int residual = (int) (length & 134217727);
        if (residual != 0) {
            for (int i = valid; i < baseLength - 1; i++) {
                base[i] = new double[134217728];
            }
            base[baseLength - 1] = new double[residual];
        } else {
            for (int i2 = valid; i2 < baseLength; i2++) {
                base[i2] = new double[134217728];
            }
        }
        if (preserve - (valid * 134217728) > 0) {
            copy(array, valid * 134217728, base, valid * 134217728, preserve - (valid * 134217728));
        }
        return base;
    }

    public static double[][] ensureCapacity(double[][] array, long length, long preserve) {
        return length > length(array) ? forceCapacity(array, length, preserve) : array;
    }

    public static double[][] grow(double[][] array, long length) {
        long oldLength = length(array);
        return length > oldLength ? grow(array, length, oldLength) : array;
    }

    public static double[][] grow(double[][] array, long length, long preserve) {
        long oldLength = length(array);
        if (length > oldLength) {
            return ensureCapacity(array, Math.max(oldLength + (oldLength >> 1), length), preserve);
        }
        return array;
    }

    public static double[][] trim(double[][] array, long length) {
        ensureLength(length);
        long oldLength = length(array);
        if (length >= oldLength) {
            return array;
        }
        int baseLength = (int) ((length + 134217727) >>> 27);
        double[][] base = (double[][]) java.util.Arrays.copyOf(array, baseLength);
        int residual = (int) (length & 134217727);
        if (residual != 0) {
            base[baseLength - 1] = DoubleArrays.trim(base[baseLength - 1], residual);
        }
        return base;
    }

    public static double[][] setLength(double[][] array, long length) {
        long oldLength = length(array);
        if (length == oldLength) {
            return array;
        }
        if (length < oldLength) {
            return trim(array, length);
        }
        return ensureCapacity(array, length);
    }

    public static double[][] copy(double[][] array, long offset, long length) {
        ensureOffsetLength(array, offset, length);
        double[][] a = DoubleBigArrays.newBigArray(length);
        copy(array, offset, a, 0L, length);
        return a;
    }

    public static double[][] copy(double[][] array) {
        double[][] base = (double[][]) array.clone();
        int i = base.length;
        while (true) {
            int i2 = i;
            i--;
            if (i2 != 0) {
                base[i] = (double[]) array[i].clone();
            } else {
                return base;
            }
        }
    }

    public static void fill(double[][] array, double value) {
        int i = array.length;
        while (true) {
            int i2 = i;
            i--;
            if (i2 != 0) {
                java.util.Arrays.fill(array[i], value);
            } else {
                return;
            }
        }
    }

    public static void fill(double[][] array, long from, long to, double value) {
        long length = length(array);
        ensureFromTo(length, from, to);
        if (length == 0) {
            return;
        }
        int fromSegment = segment(from);
        int toSegment = segment(to);
        int fromDispl = displacement(from);
        int toDispl = displacement(to);
        if (fromSegment == toSegment) {
            java.util.Arrays.fill(array[fromSegment], fromDispl, toDispl, value);
            return;
        }
        if (toDispl != 0) {
            java.util.Arrays.fill(array[toSegment], 0, toDispl, value);
        }
        while (true) {
            toSegment--;
            if (toSegment > fromSegment) {
                java.util.Arrays.fill(array[toSegment], value);
            } else {
                java.util.Arrays.fill(array[fromSegment], fromDispl, 134217728, value);
                return;
            }
        }
    }

    public static boolean equals(double[][] a1, double[][] a2) {
        if (length(a1) != length(a2)) {
            return false;
        }
        int i = a1.length;
        while (true) {
            int i2 = i;
            i--;
            if (i2 != 0) {
                double[] t = a1[i];
                double[] u = a2[i];
                int j = t.length;
                while (true) {
                    int i3 = j;
                    j--;
                    if (i3 != 0) {
                        if (Double.doubleToLongBits(t[j]) != Double.doubleToLongBits(u[j])) {
                            return false;
                        }
                    }
                }
            } else {
                return true;
            }
        }
    }

    public static String toString(double[][] a) {
        if (a == null) {
            return Configurator.NULL;
        }
        long last = length(a) - 1;
        if (last == -1) {
            return "[]";
        }
        StringBuilder b = new StringBuilder();
        b.append('[');
        long j = 0;
        while (true) {
            long i = j;
            b.append(String.valueOf(get(a, i)));
            if (i == last) {
                return b.append(']').toString();
            }
            b.append(", ");
            j = i + 1;
        }
    }

    public static void ensureFromTo(double[][] a, long from, long to) {
        ensureFromTo(length(a), from, to);
    }

    public static void ensureOffsetLength(double[][] a, long offset, long length) {
        ensureOffsetLength(length(a), offset, length);
    }

    public static void ensureSameLength(double[][] a, double[][] b) {
        if (length(a) != length(b)) {
            throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b));
        }
    }

    public static double[][] shuffle(double[][] a, long from, long to, Random random) {
        long i = to - from;
        while (true) {
            long j = i;
            i = j - 1;
            if (j != 0) {
                long p = (random.nextLong() & LongCompanionObject.MAX_VALUE) % (i + 1);
                double t = get(a, from + i);
                set(a, from + i, get(a, from + p));
                set(a, from + p, t);
            } else {
                return a;
            }
        }
    }

    public static double[][] shuffle(double[][] a, Random random) {
        long i = length(a);
        while (true) {
            long j = i;
            i = j - 1;
            if (j != 0) {
                long p = (random.nextLong() & LongCompanionObject.MAX_VALUE) % (i + 1);
                double t = get(a, i);
                set(a, i, get(a, p));
                set(a, p, t);
            } else {
                return a;
            }
        }
    }

    public static boolean get(boolean[][] array, long index) {
        return array[segment(index)][displacement(index)];
    }

    public static void set(boolean[][] array, long index, boolean value) {
        array[segment(index)][displacement(index)] = value;
    }

    public static void swap(boolean[][] array, long first, long second) {
        boolean t = array[segment(first)][displacement(first)];
        array[segment(first)][displacement(first)] = array[segment(second)][displacement(second)];
        array[segment(second)][displacement(second)] = t;
    }

    public static boolean[][] reverse(boolean[][] a) {
        long length = length(a);
        long i = length / 2;
        while (true) {
            long j = i;
            i = j - 1;
            if (j != 0) {
                swap(a, i, (length - i) - 1);
            } else {
                return a;
            }
        }
    }

    public static long length(boolean[][] array) {
        int length = array.length;
        if (length == 0) {
            return 0L;
        }
        return start(length - 1) + array[length - 1].length;
    }

    public static void copy(boolean[][] srcArray, long srcPos, boolean[][] destArray, long destPos, long length) {
        if (destPos <= srcPos) {
            int srcSegment = segment(srcPos);
            int destSegment = segment(destPos);
            int srcDispl = displacement(srcPos);
            int destDispl = displacement(destPos);
            while (length > 0) {
                int l = (int) Math.min(length, Math.min(srcArray[srcSegment].length - srcDispl, destArray[destSegment].length - destDispl));
                if (l == 0) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                System.arraycopy(srcArray[srcSegment], srcDispl, destArray[destSegment], destDispl, l);
                int i = srcDispl + l;
                srcDispl = i;
                if (i == 134217728) {
                    srcDispl = 0;
                    srcSegment++;
                }
                int i2 = destDispl + l;
                destDispl = i2;
                if (i2 == 134217728) {
                    destDispl = 0;
                    destSegment++;
                }
                length -= l;
            }
            return;
        }
        int srcSegment2 = segment(srcPos + length);
        int destSegment2 = segment(destPos + length);
        int srcDispl2 = displacement(srcPos + length);
        int destDispl2 = displacement(destPos + length);
        while (length > 0) {
            if (srcDispl2 == 0) {
                srcDispl2 = 134217728;
                srcSegment2--;
            }
            if (destDispl2 == 0) {
                destDispl2 = 134217728;
                destSegment2--;
            }
            int l2 = (int) Math.min(length, Math.min(srcDispl2, destDispl2));
            if (l2 == 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            System.arraycopy(srcArray[srcSegment2], srcDispl2 - l2, destArray[destSegment2], destDispl2 - l2, l2);
            srcDispl2 -= l2;
            destDispl2 -= l2;
            length -= l2;
        }
    }

    public static void copyFromBig(boolean[][] srcArray, long srcPos, boolean[] destArray, int destPos, int length) {
        int srcSegment = segment(srcPos);
        int srcDispl = displacement(srcPos);
        while (length > 0) {
            int l = Math.min(srcArray[srcSegment].length - srcDispl, length);
            if (l == 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            System.arraycopy(srcArray[srcSegment], srcDispl, destArray, destPos, l);
            int i = srcDispl + l;
            srcDispl = i;
            if (i == 134217728) {
                srcDispl = 0;
                srcSegment++;
            }
            destPos += l;
            length -= l;
        }
    }

    public static void copyToBig(boolean[] srcArray, int srcPos, boolean[][] destArray, long destPos, long length) {
        int destSegment = segment(destPos);
        int destDispl = displacement(destPos);
        while (length > 0) {
            int l = (int) Math.min(destArray[destSegment].length - destDispl, length);
            if (l == 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            System.arraycopy(srcArray, srcPos, destArray[destSegment], destDispl, l);
            int i = destDispl + l;
            destDispl = i;
            if (i == 134217728) {
                destDispl = 0;
                destSegment++;
            }
            srcPos += l;
            length -= l;
        }
    }

    /* JADX WARN: Type inference failed for: r0v13, types: [boolean[], boolean[][]] */
    public static boolean[][] wrap(boolean[] array) {
        if (array.length == 0) {
            return BooleanBigArrays.EMPTY_BIG_ARRAY;
        }
        if (array.length <= 134217728) {
            return new boolean[]{array};
        }
        boolean[][] bigArray = BooleanBigArrays.newBigArray(array.length);
        for (int i = 0; i < bigArray.length; i++) {
            System.arraycopy(array, (int) start(i), bigArray[i], 0, bigArray[i].length);
        }
        return bigArray;
    }

    public static boolean[][] ensureCapacity(boolean[][] array, long length) {
        return ensureCapacity(array, length, length(array));
    }

    public static boolean[][] forceCapacity(boolean[][] array, long length, long preserve) {
        ensureLength(length);
        int valid = array.length - ((array.length == 0 || (array.length > 0 && array[array.length - 1].length == 134217728)) ? 0 : 1);
        int baseLength = (int) ((length + 134217727) >>> 27);
        boolean[][] base = (boolean[][]) java.util.Arrays.copyOf(array, baseLength);
        int residual = (int) (length & 134217727);
        if (residual != 0) {
            for (int i = valid; i < baseLength - 1; i++) {
                base[i] = new boolean[134217728];
            }
            base[baseLength - 1] = new boolean[residual];
        } else {
            for (int i2 = valid; i2 < baseLength; i2++) {
                base[i2] = new boolean[134217728];
            }
        }
        if (preserve - (valid * 134217728) > 0) {
            copy(array, valid * 134217728, base, valid * 134217728, preserve - (valid * 134217728));
        }
        return base;
    }

    public static boolean[][] ensureCapacity(boolean[][] array, long length, long preserve) {
        return length > length(array) ? forceCapacity(array, length, preserve) : array;
    }

    public static boolean[][] grow(boolean[][] array, long length) {
        long oldLength = length(array);
        return length > oldLength ? grow(array, length, oldLength) : array;
    }

    public static boolean[][] grow(boolean[][] array, long length, long preserve) {
        long oldLength = length(array);
        if (length > oldLength) {
            return ensureCapacity(array, Math.max(oldLength + (oldLength >> 1), length), preserve);
        }
        return array;
    }

    public static boolean[][] trim(boolean[][] array, long length) {
        ensureLength(length);
        long oldLength = length(array);
        if (length >= oldLength) {
            return array;
        }
        int baseLength = (int) ((length + 134217727) >>> 27);
        boolean[][] base = (boolean[][]) java.util.Arrays.copyOf(array, baseLength);
        int residual = (int) (length & 134217727);
        if (residual != 0) {
            base[baseLength - 1] = BooleanArrays.trim(base[baseLength - 1], residual);
        }
        return base;
    }

    public static boolean[][] setLength(boolean[][] array, long length) {
        long oldLength = length(array);
        if (length == oldLength) {
            return array;
        }
        if (length < oldLength) {
            return trim(array, length);
        }
        return ensureCapacity(array, length);
    }

    public static boolean[][] copy(boolean[][] array, long offset, long length) {
        ensureOffsetLength(array, offset, length);
        boolean[][] a = BooleanBigArrays.newBigArray(length);
        copy(array, offset, a, 0L, length);
        return a;
    }

    public static boolean[][] copy(boolean[][] array) {
        boolean[][] base = (boolean[][]) array.clone();
        int i = base.length;
        while (true) {
            int i2 = i;
            i--;
            if (i2 != 0) {
                base[i] = (boolean[]) array[i].clone();
            } else {
                return base;
            }
        }
    }

    public static void fill(boolean[][] array, boolean value) {
        int i = array.length;
        while (true) {
            int i2 = i;
            i--;
            if (i2 != 0) {
                java.util.Arrays.fill(array[i], value);
            } else {
                return;
            }
        }
    }

    public static void fill(boolean[][] array, long from, long to, boolean value) {
        long length = length(array);
        ensureFromTo(length, from, to);
        if (length == 0) {
            return;
        }
        int fromSegment = segment(from);
        int toSegment = segment(to);
        int fromDispl = displacement(from);
        int toDispl = displacement(to);
        if (fromSegment == toSegment) {
            java.util.Arrays.fill(array[fromSegment], fromDispl, toDispl, value);
            return;
        }
        if (toDispl != 0) {
            java.util.Arrays.fill(array[toSegment], 0, toDispl, value);
        }
        while (true) {
            toSegment--;
            if (toSegment > fromSegment) {
                java.util.Arrays.fill(array[toSegment], value);
            } else {
                java.util.Arrays.fill(array[fromSegment], fromDispl, 134217728, value);
                return;
            }
        }
    }

    public static boolean equals(boolean[][] a1, boolean[][] a2) {
        if (length(a1) != length(a2)) {
            return false;
        }
        int i = a1.length;
        while (true) {
            int i2 = i;
            i--;
            if (i2 != 0) {
                boolean[] t = a1[i];
                boolean[] u = a2[i];
                int j = t.length;
                while (true) {
                    int i3 = j;
                    j--;
                    if (i3 != 0) {
                        if (t[j] != u[j]) {
                            return false;
                        }
                    }
                }
            } else {
                return true;
            }
        }
    }

    public static String toString(boolean[][] a) {
        if (a == null) {
            return Configurator.NULL;
        }
        long last = length(a) - 1;
        if (last == -1) {
            return "[]";
        }
        StringBuilder b = new StringBuilder();
        b.append('[');
        long j = 0;
        while (true) {
            long i = j;
            b.append(String.valueOf(get(a, i)));
            if (i == last) {
                return b.append(']').toString();
            }
            b.append(", ");
            j = i + 1;
        }
    }

    public static void ensureFromTo(boolean[][] a, long from, long to) {
        ensureFromTo(length(a), from, to);
    }

    public static void ensureOffsetLength(boolean[][] a, long offset, long length) {
        ensureOffsetLength(length(a), offset, length);
    }

    public static void ensureSameLength(boolean[][] a, boolean[][] b) {
        if (length(a) != length(b)) {
            throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b));
        }
    }

    public static boolean[][] shuffle(boolean[][] a, long from, long to, Random random) {
        long i = to - from;
        while (true) {
            long j = i;
            i = j - 1;
            if (j != 0) {
                long p = (random.nextLong() & LongCompanionObject.MAX_VALUE) % (i + 1);
                boolean t = get(a, from + i);
                set(a, from + i, get(a, from + p));
                set(a, from + p, t);
            } else {
                return a;
            }
        }
    }

    public static boolean[][] shuffle(boolean[][] a, Random random) {
        long i = length(a);
        while (true) {
            long j = i;
            i = j - 1;
            if (j != 0) {
                long p = (random.nextLong() & LongCompanionObject.MAX_VALUE) % (i + 1);
                boolean t = get(a, i);
                set(a, i, get(a, p));
                set(a, p, t);
            } else {
                return a;
            }
        }
    }

    public static short get(short[][] array, long index) {
        return array[segment(index)][displacement(index)];
    }

    public static void set(short[][] array, long index, short value) {
        array[segment(index)][displacement(index)] = value;
    }

    public static void swap(short[][] array, long first, long second) {
        short t = array[segment(first)][displacement(first)];
        array[segment(first)][displacement(first)] = array[segment(second)][displacement(second)];
        array[segment(second)][displacement(second)] = t;
    }

    public static short[][] reverse(short[][] a) {
        long length = length(a);
        long i = length / 2;
        while (true) {
            long j = i;
            i = j - 1;
            if (j != 0) {
                swap(a, i, (length - i) - 1);
            } else {
                return a;
            }
        }
    }

    public static void add(short[][] array, long index, short incr) {
        short[] sArr = array[segment(index)];
        int displacement = displacement(index);
        sArr[displacement] = (short) (sArr[displacement] + incr);
    }

    public static void mul(short[][] array, long index, short factor) {
        short[] sArr = array[segment(index)];
        int displacement = displacement(index);
        sArr[displacement] = (short) (sArr[displacement] * factor);
    }

    public static void incr(short[][] array, long index) {
        short[] sArr = array[segment(index)];
        int displacement = displacement(index);
        sArr[displacement] = (short) (sArr[displacement] + 1);
    }

    public static void decr(short[][] array, long index) {
        short[] sArr = array[segment(index)];
        int displacement = displacement(index);
        sArr[displacement] = (short) (sArr[displacement] - 1);
    }

    public static long length(short[][] array) {
        int length = array.length;
        if (length == 0) {
            return 0L;
        }
        return start(length - 1) + array[length - 1].length;
    }

    public static void copy(short[][] srcArray, long srcPos, short[][] destArray, long destPos, long length) {
        if (destPos <= srcPos) {
            int srcSegment = segment(srcPos);
            int destSegment = segment(destPos);
            int srcDispl = displacement(srcPos);
            int destDispl = displacement(destPos);
            while (length > 0) {
                int l = (int) Math.min(length, Math.min(srcArray[srcSegment].length - srcDispl, destArray[destSegment].length - destDispl));
                if (l == 0) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                System.arraycopy(srcArray[srcSegment], srcDispl, destArray[destSegment], destDispl, l);
                int i = srcDispl + l;
                srcDispl = i;
                if (i == 134217728) {
                    srcDispl = 0;
                    srcSegment++;
                }
                int i2 = destDispl + l;
                destDispl = i2;
                if (i2 == 134217728) {
                    destDispl = 0;
                    destSegment++;
                }
                length -= l;
            }
            return;
        }
        int srcSegment2 = segment(srcPos + length);
        int destSegment2 = segment(destPos + length);
        int srcDispl2 = displacement(srcPos + length);
        int destDispl2 = displacement(destPos + length);
        while (length > 0) {
            if (srcDispl2 == 0) {
                srcDispl2 = 134217728;
                srcSegment2--;
            }
            if (destDispl2 == 0) {
                destDispl2 = 134217728;
                destSegment2--;
            }
            int l2 = (int) Math.min(length, Math.min(srcDispl2, destDispl2));
            if (l2 == 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            System.arraycopy(srcArray[srcSegment2], srcDispl2 - l2, destArray[destSegment2], destDispl2 - l2, l2);
            srcDispl2 -= l2;
            destDispl2 -= l2;
            length -= l2;
        }
    }

    public static void copyFromBig(short[][] srcArray, long srcPos, short[] destArray, int destPos, int length) {
        int srcSegment = segment(srcPos);
        int srcDispl = displacement(srcPos);
        while (length > 0) {
            int l = Math.min(srcArray[srcSegment].length - srcDispl, length);
            if (l == 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            System.arraycopy(srcArray[srcSegment], srcDispl, destArray, destPos, l);
            int i = srcDispl + l;
            srcDispl = i;
            if (i == 134217728) {
                srcDispl = 0;
                srcSegment++;
            }
            destPos += l;
            length -= l;
        }
    }

    public static void copyToBig(short[] srcArray, int srcPos, short[][] destArray, long destPos, long length) {
        int destSegment = segment(destPos);
        int destDispl = displacement(destPos);
        while (length > 0) {
            int l = (int) Math.min(destArray[destSegment].length - destDispl, length);
            if (l == 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            System.arraycopy(srcArray, srcPos, destArray[destSegment], destDispl, l);
            int i = destDispl + l;
            destDispl = i;
            if (i == 134217728) {
                destDispl = 0;
                destSegment++;
            }
            srcPos += l;
            length -= l;
        }
    }

    /* JADX WARN: Type inference failed for: r0v13, types: [short[], short[][]] */
    public static short[][] wrap(short[] array) {
        if (array.length == 0) {
            return ShortBigArrays.EMPTY_BIG_ARRAY;
        }
        if (array.length <= 134217728) {
            return new short[]{array};
        }
        short[][] bigArray = ShortBigArrays.newBigArray(array.length);
        for (int i = 0; i < bigArray.length; i++) {
            System.arraycopy(array, (int) start(i), bigArray[i], 0, bigArray[i].length);
        }
        return bigArray;
    }

    public static short[][] ensureCapacity(short[][] array, long length) {
        return ensureCapacity(array, length, length(array));
    }

    public static short[][] forceCapacity(short[][] array, long length, long preserve) {
        ensureLength(length);
        int valid = array.length - ((array.length == 0 || (array.length > 0 && array[array.length - 1].length == 134217728)) ? 0 : 1);
        int baseLength = (int) ((length + 134217727) >>> 27);
        short[][] base = (short[][]) java.util.Arrays.copyOf(array, baseLength);
        int residual = (int) (length & 134217727);
        if (residual != 0) {
            for (int i = valid; i < baseLength - 1; i++) {
                base[i] = new short[134217728];
            }
            base[baseLength - 1] = new short[residual];
        } else {
            for (int i2 = valid; i2 < baseLength; i2++) {
                base[i2] = new short[134217728];
            }
        }
        if (preserve - (valid * 134217728) > 0) {
            copy(array, valid * 134217728, base, valid * 134217728, preserve - (valid * 134217728));
        }
        return base;
    }

    public static short[][] ensureCapacity(short[][] array, long length, long preserve) {
        return length > length(array) ? forceCapacity(array, length, preserve) : array;
    }

    public static short[][] grow(short[][] array, long length) {
        long oldLength = length(array);
        return length > oldLength ? grow(array, length, oldLength) : array;
    }

    public static short[][] grow(short[][] array, long length, long preserve) {
        long oldLength = length(array);
        if (length > oldLength) {
            return ensureCapacity(array, Math.max(oldLength + (oldLength >> 1), length), preserve);
        }
        return array;
    }

    public static short[][] trim(short[][] array, long length) {
        ensureLength(length);
        long oldLength = length(array);
        if (length >= oldLength) {
            return array;
        }
        int baseLength = (int) ((length + 134217727) >>> 27);
        short[][] base = (short[][]) java.util.Arrays.copyOf(array, baseLength);
        int residual = (int) (length & 134217727);
        if (residual != 0) {
            base[baseLength - 1] = ShortArrays.trim(base[baseLength - 1], residual);
        }
        return base;
    }

    public static short[][] setLength(short[][] array, long length) {
        long oldLength = length(array);
        if (length == oldLength) {
            return array;
        }
        if (length < oldLength) {
            return trim(array, length);
        }
        return ensureCapacity(array, length);
    }

    public static short[][] copy(short[][] array, long offset, long length) {
        ensureOffsetLength(array, offset, length);
        short[][] a = ShortBigArrays.newBigArray(length);
        copy(array, offset, a, 0L, length);
        return a;
    }

    public static short[][] copy(short[][] array) {
        short[][] base = (short[][]) array.clone();
        int i = base.length;
        while (true) {
            int i2 = i;
            i--;
            if (i2 != 0) {
                base[i] = (short[]) array[i].clone();
            } else {
                return base;
            }
        }
    }

    public static void fill(short[][] array, short value) {
        int i = array.length;
        while (true) {
            int i2 = i;
            i--;
            if (i2 != 0) {
                java.util.Arrays.fill(array[i], value);
            } else {
                return;
            }
        }
    }

    public static void fill(short[][] array, long from, long to, short value) {
        long length = length(array);
        ensureFromTo(length, from, to);
        if (length == 0) {
            return;
        }
        int fromSegment = segment(from);
        int toSegment = segment(to);
        int fromDispl = displacement(from);
        int toDispl = displacement(to);
        if (fromSegment == toSegment) {
            java.util.Arrays.fill(array[fromSegment], fromDispl, toDispl, value);
            return;
        }
        if (toDispl != 0) {
            java.util.Arrays.fill(array[toSegment], 0, toDispl, value);
        }
        while (true) {
            toSegment--;
            if (toSegment > fromSegment) {
                java.util.Arrays.fill(array[toSegment], value);
            } else {
                java.util.Arrays.fill(array[fromSegment], fromDispl, 134217728, value);
                return;
            }
        }
    }

    public static boolean equals(short[][] a1, short[][] a2) {
        if (length(a1) != length(a2)) {
            return false;
        }
        int i = a1.length;
        while (true) {
            int i2 = i;
            i--;
            if (i2 != 0) {
                short[] t = a1[i];
                short[] u = a2[i];
                int j = t.length;
                while (true) {
                    int i3 = j;
                    j--;
                    if (i3 != 0) {
                        if (t[j] != u[j]) {
                            return false;
                        }
                    }
                }
            } else {
                return true;
            }
        }
    }

    public static String toString(short[][] a) {
        if (a == null) {
            return Configurator.NULL;
        }
        long last = length(a) - 1;
        if (last == -1) {
            return "[]";
        }
        StringBuilder b = new StringBuilder();
        b.append('[');
        long j = 0;
        while (true) {
            long i = j;
            b.append(String.valueOf((int) get(a, i)));
            if (i == last) {
                return b.append(']').toString();
            }
            b.append(", ");
            j = i + 1;
        }
    }

    public static void ensureFromTo(short[][] a, long from, long to) {
        ensureFromTo(length(a), from, to);
    }

    public static void ensureOffsetLength(short[][] a, long offset, long length) {
        ensureOffsetLength(length(a), offset, length);
    }

    public static void ensureSameLength(short[][] a, short[][] b) {
        if (length(a) != length(b)) {
            throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b));
        }
    }

    public static short[][] shuffle(short[][] a, long from, long to, Random random) {
        long i = to - from;
        while (true) {
            long j = i;
            i = j - 1;
            if (j != 0) {
                long p = (random.nextLong() & LongCompanionObject.MAX_VALUE) % (i + 1);
                short t = get(a, from + i);
                set(a, from + i, get(a, from + p));
                set(a, from + p, t);
            } else {
                return a;
            }
        }
    }

    public static short[][] shuffle(short[][] a, Random random) {
        long i = length(a);
        while (true) {
            long j = i;
            i = j - 1;
            if (j != 0) {
                long p = (random.nextLong() & LongCompanionObject.MAX_VALUE) % (i + 1);
                short t = get(a, i);
                set(a, i, get(a, p));
                set(a, p, t);
            } else {
                return a;
            }
        }
    }

    public static char get(char[][] array, long index) {
        return array[segment(index)][displacement(index)];
    }

    public static void set(char[][] array, long index, char value) {
        array[segment(index)][displacement(index)] = value;
    }

    public static void swap(char[][] array, long first, long second) {
        char t = array[segment(first)][displacement(first)];
        array[segment(first)][displacement(first)] = array[segment(second)][displacement(second)];
        array[segment(second)][displacement(second)] = t;
    }

    public static char[][] reverse(char[][] a) {
        long length = length(a);
        long i = length / 2;
        while (true) {
            long j = i;
            i = j - 1;
            if (j != 0) {
                swap(a, i, (length - i) - 1);
            } else {
                return a;
            }
        }
    }

    public static void add(char[][] array, long index, char incr) {
        char[] cArr = array[segment(index)];
        int displacement = displacement(index);
        cArr[displacement] = (char) (cArr[displacement] + incr);
    }

    public static void mul(char[][] array, long index, char factor) {
        char[] cArr = array[segment(index)];
        int displacement = displacement(index);
        cArr[displacement] = (char) (cArr[displacement] * factor);
    }

    public static void incr(char[][] array, long index) {
        char[] cArr = array[segment(index)];
        int displacement = displacement(index);
        cArr[displacement] = (char) (cArr[displacement] + 1);
    }

    public static void decr(char[][] array, long index) {
        char[] cArr = array[segment(index)];
        int displacement = displacement(index);
        cArr[displacement] = (char) (cArr[displacement] - 1);
    }

    public static long length(char[][] array) {
        int length = array.length;
        if (length == 0) {
            return 0L;
        }
        return start(length - 1) + array[length - 1].length;
    }

    public static void copy(char[][] srcArray, long srcPos, char[][] destArray, long destPos, long length) {
        if (destPos <= srcPos) {
            int srcSegment = segment(srcPos);
            int destSegment = segment(destPos);
            int srcDispl = displacement(srcPos);
            int destDispl = displacement(destPos);
            while (length > 0) {
                int l = (int) Math.min(length, Math.min(srcArray[srcSegment].length - srcDispl, destArray[destSegment].length - destDispl));
                if (l == 0) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                System.arraycopy(srcArray[srcSegment], srcDispl, destArray[destSegment], destDispl, l);
                int i = srcDispl + l;
                srcDispl = i;
                if (i == 134217728) {
                    srcDispl = 0;
                    srcSegment++;
                }
                int i2 = destDispl + l;
                destDispl = i2;
                if (i2 == 134217728) {
                    destDispl = 0;
                    destSegment++;
                }
                length -= l;
            }
            return;
        }
        int srcSegment2 = segment(srcPos + length);
        int destSegment2 = segment(destPos + length);
        int srcDispl2 = displacement(srcPos + length);
        int destDispl2 = displacement(destPos + length);
        while (length > 0) {
            if (srcDispl2 == 0) {
                srcDispl2 = 134217728;
                srcSegment2--;
            }
            if (destDispl2 == 0) {
                destDispl2 = 134217728;
                destSegment2--;
            }
            int l2 = (int) Math.min(length, Math.min(srcDispl2, destDispl2));
            if (l2 == 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            System.arraycopy(srcArray[srcSegment2], srcDispl2 - l2, destArray[destSegment2], destDispl2 - l2, l2);
            srcDispl2 -= l2;
            destDispl2 -= l2;
            length -= l2;
        }
    }

    public static void copyFromBig(char[][] srcArray, long srcPos, char[] destArray, int destPos, int length) {
        int srcSegment = segment(srcPos);
        int srcDispl = displacement(srcPos);
        while (length > 0) {
            int l = Math.min(srcArray[srcSegment].length - srcDispl, length);
            if (l == 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            System.arraycopy(srcArray[srcSegment], srcDispl, destArray, destPos, l);
            int i = srcDispl + l;
            srcDispl = i;
            if (i == 134217728) {
                srcDispl = 0;
                srcSegment++;
            }
            destPos += l;
            length -= l;
        }
    }

    public static void copyToBig(char[] srcArray, int srcPos, char[][] destArray, long destPos, long length) {
        int destSegment = segment(destPos);
        int destDispl = displacement(destPos);
        while (length > 0) {
            int l = (int) Math.min(destArray[destSegment].length - destDispl, length);
            if (l == 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            System.arraycopy(srcArray, srcPos, destArray[destSegment], destDispl, l);
            int i = destDispl + l;
            destDispl = i;
            if (i == 134217728) {
                destDispl = 0;
                destSegment++;
            }
            srcPos += l;
            length -= l;
        }
    }

    /* JADX WARN: Type inference failed for: r0v13, types: [char[], char[][]] */
    public static char[][] wrap(char[] array) {
        if (array.length == 0) {
            return CharBigArrays.EMPTY_BIG_ARRAY;
        }
        if (array.length <= 134217728) {
            return new char[]{array};
        }
        char[][] bigArray = CharBigArrays.newBigArray(array.length);
        for (int i = 0; i < bigArray.length; i++) {
            System.arraycopy(array, (int) start(i), bigArray[i], 0, bigArray[i].length);
        }
        return bigArray;
    }

    public static char[][] ensureCapacity(char[][] array, long length) {
        return ensureCapacity(array, length, length(array));
    }

    public static char[][] forceCapacity(char[][] array, long length, long preserve) {
        ensureLength(length);
        int valid = array.length - ((array.length == 0 || (array.length > 0 && array[array.length - 1].length == 134217728)) ? 0 : 1);
        int baseLength = (int) ((length + 134217727) >>> 27);
        char[][] base = (char[][]) java.util.Arrays.copyOf(array, baseLength);
        int residual = (int) (length & 134217727);
        if (residual != 0) {
            for (int i = valid; i < baseLength - 1; i++) {
                base[i] = new char[134217728];
            }
            base[baseLength - 1] = new char[residual];
        } else {
            for (int i2 = valid; i2 < baseLength; i2++) {
                base[i2] = new char[134217728];
            }
        }
        if (preserve - (valid * 134217728) > 0) {
            copy(array, valid * 134217728, base, valid * 134217728, preserve - (valid * 134217728));
        }
        return base;
    }

    public static char[][] ensureCapacity(char[][] array, long length, long preserve) {
        return length > length(array) ? forceCapacity(array, length, preserve) : array;
    }

    public static char[][] grow(char[][] array, long length) {
        long oldLength = length(array);
        return length > oldLength ? grow(array, length, oldLength) : array;
    }

    public static char[][] grow(char[][] array, long length, long preserve) {
        long oldLength = length(array);
        if (length > oldLength) {
            return ensureCapacity(array, Math.max(oldLength + (oldLength >> 1), length), preserve);
        }
        return array;
    }

    public static char[][] trim(char[][] array, long length) {
        ensureLength(length);
        long oldLength = length(array);
        if (length >= oldLength) {
            return array;
        }
        int baseLength = (int) ((length + 134217727) >>> 27);
        char[][] base = (char[][]) java.util.Arrays.copyOf(array, baseLength);
        int residual = (int) (length & 134217727);
        if (residual != 0) {
            base[baseLength - 1] = CharArrays.trim(base[baseLength - 1], residual);
        }
        return base;
    }

    public static char[][] setLength(char[][] array, long length) {
        long oldLength = length(array);
        if (length == oldLength) {
            return array;
        }
        if (length < oldLength) {
            return trim(array, length);
        }
        return ensureCapacity(array, length);
    }

    public static char[][] copy(char[][] array, long offset, long length) {
        ensureOffsetLength(array, offset, length);
        char[][] a = CharBigArrays.newBigArray(length);
        copy(array, offset, a, 0L, length);
        return a;
    }

    public static char[][] copy(char[][] array) {
        char[][] base = (char[][]) array.clone();
        int i = base.length;
        while (true) {
            int i2 = i;
            i--;
            if (i2 != 0) {
                base[i] = (char[]) array[i].clone();
            } else {
                return base;
            }
        }
    }

    public static void fill(char[][] array, char value) {
        int i = array.length;
        while (true) {
            int i2 = i;
            i--;
            if (i2 != 0) {
                java.util.Arrays.fill(array[i], value);
            } else {
                return;
            }
        }
    }

    public static void fill(char[][] array, long from, long to, char value) {
        long length = length(array);
        ensureFromTo(length, from, to);
        if (length == 0) {
            return;
        }
        int fromSegment = segment(from);
        int toSegment = segment(to);
        int fromDispl = displacement(from);
        int toDispl = displacement(to);
        if (fromSegment == toSegment) {
            java.util.Arrays.fill(array[fromSegment], fromDispl, toDispl, value);
            return;
        }
        if (toDispl != 0) {
            java.util.Arrays.fill(array[toSegment], 0, toDispl, value);
        }
        while (true) {
            toSegment--;
            if (toSegment > fromSegment) {
                java.util.Arrays.fill(array[toSegment], value);
            } else {
                java.util.Arrays.fill(array[fromSegment], fromDispl, 134217728, value);
                return;
            }
        }
    }

    public static boolean equals(char[][] a1, char[][] a2) {
        if (length(a1) != length(a2)) {
            return false;
        }
        int i = a1.length;
        while (true) {
            int i2 = i;
            i--;
            if (i2 != 0) {
                char[] t = a1[i];
                char[] u = a2[i];
                int j = t.length;
                while (true) {
                    int i3 = j;
                    j--;
                    if (i3 != 0) {
                        if (t[j] != u[j]) {
                            return false;
                        }
                    }
                }
            } else {
                return true;
            }
        }
    }

    public static String toString(char[][] a) {
        if (a == null) {
            return Configurator.NULL;
        }
        long last = length(a) - 1;
        if (last == -1) {
            return "[]";
        }
        StringBuilder b = new StringBuilder();
        b.append('[');
        long j = 0;
        while (true) {
            long i = j;
            b.append(String.valueOf(get(a, i)));
            if (i == last) {
                return b.append(']').toString();
            }
            b.append(", ");
            j = i + 1;
        }
    }

    public static void ensureFromTo(char[][] a, long from, long to) {
        ensureFromTo(length(a), from, to);
    }

    public static void ensureOffsetLength(char[][] a, long offset, long length) {
        ensureOffsetLength(length(a), offset, length);
    }

    public static void ensureSameLength(char[][] a, char[][] b) {
        if (length(a) != length(b)) {
            throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b));
        }
    }

    public static char[][] shuffle(char[][] a, long from, long to, Random random) {
        long i = to - from;
        while (true) {
            long j = i;
            i = j - 1;
            if (j != 0) {
                long p = (random.nextLong() & LongCompanionObject.MAX_VALUE) % (i + 1);
                char t = get(a, from + i);
                set(a, from + i, get(a, from + p));
                set(a, from + p, t);
            } else {
                return a;
            }
        }
    }

    public static char[][] shuffle(char[][] a, Random random) {
        long i = length(a);
        while (true) {
            long j = i;
            i = j - 1;
            if (j != 0) {
                long p = (random.nextLong() & LongCompanionObject.MAX_VALUE) % (i + 1);
                char t = get(a, i);
                set(a, i, get(a, p));
                set(a, p, t);
            } else {
                return a;
            }
        }
    }

    public static float get(float[][] array, long index) {
        return array[segment(index)][displacement(index)];
    }

    public static void set(float[][] array, long index, float value) {
        array[segment(index)][displacement(index)] = value;
    }

    public static void swap(float[][] array, long first, long second) {
        float t = array[segment(first)][displacement(first)];
        array[segment(first)][displacement(first)] = array[segment(second)][displacement(second)];
        array[segment(second)][displacement(second)] = t;
    }

    public static float[][] reverse(float[][] a) {
        long length = length(a);
        long i = length / 2;
        while (true) {
            long j = i;
            i = j - 1;
            if (j != 0) {
                swap(a, i, (length - i) - 1);
            } else {
                return a;
            }
        }
    }

    public static void add(float[][] array, long index, float incr) {
        float[] fArr = array[segment(index)];
        int displacement = displacement(index);
        fArr[displacement] = fArr[displacement] + incr;
    }

    public static void mul(float[][] array, long index, float factor) {
        float[] fArr = array[segment(index)];
        int displacement = displacement(index);
        fArr[displacement] = fArr[displacement] * factor;
    }

    public static void incr(float[][] array, long index) {
        float[] fArr = array[segment(index)];
        int displacement = displacement(index);
        fArr[displacement] = fArr[displacement] + 1.0f;
    }

    public static void decr(float[][] array, long index) {
        float[] fArr = array[segment(index)];
        int displacement = displacement(index);
        fArr[displacement] = fArr[displacement] - 1.0f;
    }

    public static long length(float[][] array) {
        int length = array.length;
        if (length == 0) {
            return 0L;
        }
        return start(length - 1) + array[length - 1].length;
    }

    public static void copy(float[][] srcArray, long srcPos, float[][] destArray, long destPos, long length) {
        if (destPos <= srcPos) {
            int srcSegment = segment(srcPos);
            int destSegment = segment(destPos);
            int srcDispl = displacement(srcPos);
            int destDispl = displacement(destPos);
            while (length > 0) {
                int l = (int) Math.min(length, Math.min(srcArray[srcSegment].length - srcDispl, destArray[destSegment].length - destDispl));
                if (l == 0) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                System.arraycopy(srcArray[srcSegment], srcDispl, destArray[destSegment], destDispl, l);
                int i = srcDispl + l;
                srcDispl = i;
                if (i == 134217728) {
                    srcDispl = 0;
                    srcSegment++;
                }
                int i2 = destDispl + l;
                destDispl = i2;
                if (i2 == 134217728) {
                    destDispl = 0;
                    destSegment++;
                }
                length -= l;
            }
            return;
        }
        int srcSegment2 = segment(srcPos + length);
        int destSegment2 = segment(destPos + length);
        int srcDispl2 = displacement(srcPos + length);
        int destDispl2 = displacement(destPos + length);
        while (length > 0) {
            if (srcDispl2 == 0) {
                srcDispl2 = 134217728;
                srcSegment2--;
            }
            if (destDispl2 == 0) {
                destDispl2 = 134217728;
                destSegment2--;
            }
            int l2 = (int) Math.min(length, Math.min(srcDispl2, destDispl2));
            if (l2 == 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            System.arraycopy(srcArray[srcSegment2], srcDispl2 - l2, destArray[destSegment2], destDispl2 - l2, l2);
            srcDispl2 -= l2;
            destDispl2 -= l2;
            length -= l2;
        }
    }

    public static void copyFromBig(float[][] srcArray, long srcPos, float[] destArray, int destPos, int length) {
        int srcSegment = segment(srcPos);
        int srcDispl = displacement(srcPos);
        while (length > 0) {
            int l = Math.min(srcArray[srcSegment].length - srcDispl, length);
            if (l == 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            System.arraycopy(srcArray[srcSegment], srcDispl, destArray, destPos, l);
            int i = srcDispl + l;
            srcDispl = i;
            if (i == 134217728) {
                srcDispl = 0;
                srcSegment++;
            }
            destPos += l;
            length -= l;
        }
    }

    public static void copyToBig(float[] srcArray, int srcPos, float[][] destArray, long destPos, long length) {
        int destSegment = segment(destPos);
        int destDispl = displacement(destPos);
        while (length > 0) {
            int l = (int) Math.min(destArray[destSegment].length - destDispl, length);
            if (l == 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            System.arraycopy(srcArray, srcPos, destArray[destSegment], destDispl, l);
            int i = destDispl + l;
            destDispl = i;
            if (i == 134217728) {
                destDispl = 0;
                destSegment++;
            }
            srcPos += l;
            length -= l;
        }
    }

    /* JADX WARN: Type inference failed for: r0v13, types: [float[], float[][]] */
    public static float[][] wrap(float[] array) {
        if (array.length == 0) {
            return FloatBigArrays.EMPTY_BIG_ARRAY;
        }
        if (array.length <= 134217728) {
            return new float[]{array};
        }
        float[][] bigArray = FloatBigArrays.newBigArray(array.length);
        for (int i = 0; i < bigArray.length; i++) {
            System.arraycopy(array, (int) start(i), bigArray[i], 0, bigArray[i].length);
        }
        return bigArray;
    }

    public static float[][] ensureCapacity(float[][] array, long length) {
        return ensureCapacity(array, length, length(array));
    }

    public static float[][] forceCapacity(float[][] array, long length, long preserve) {
        ensureLength(length);
        int valid = array.length - ((array.length == 0 || (array.length > 0 && array[array.length - 1].length == 134217728)) ? 0 : 1);
        int baseLength = (int) ((length + 134217727) >>> 27);
        float[][] base = (float[][]) java.util.Arrays.copyOf(array, baseLength);
        int residual = (int) (length & 134217727);
        if (residual != 0) {
            for (int i = valid; i < baseLength - 1; i++) {
                base[i] = new float[134217728];
            }
            base[baseLength - 1] = new float[residual];
        } else {
            for (int i2 = valid; i2 < baseLength; i2++) {
                base[i2] = new float[134217728];
            }
        }
        if (preserve - (valid * 134217728) > 0) {
            copy(array, valid * 134217728, base, valid * 134217728, preserve - (valid * 134217728));
        }
        return base;
    }

    public static float[][] ensureCapacity(float[][] array, long length, long preserve) {
        return length > length(array) ? forceCapacity(array, length, preserve) : array;
    }

    public static float[][] grow(float[][] array, long length) {
        long oldLength = length(array);
        return length > oldLength ? grow(array, length, oldLength) : array;
    }

    public static float[][] grow(float[][] array, long length, long preserve) {
        long oldLength = length(array);
        if (length > oldLength) {
            return ensureCapacity(array, Math.max(oldLength + (oldLength >> 1), length), preserve);
        }
        return array;
    }

    public static float[][] trim(float[][] array, long length) {
        ensureLength(length);
        long oldLength = length(array);
        if (length >= oldLength) {
            return array;
        }
        int baseLength = (int) ((length + 134217727) >>> 27);
        float[][] base = (float[][]) java.util.Arrays.copyOf(array, baseLength);
        int residual = (int) (length & 134217727);
        if (residual != 0) {
            base[baseLength - 1] = FloatArrays.trim(base[baseLength - 1], residual);
        }
        return base;
    }

    public static float[][] setLength(float[][] array, long length) {
        long oldLength = length(array);
        if (length == oldLength) {
            return array;
        }
        if (length < oldLength) {
            return trim(array, length);
        }
        return ensureCapacity(array, length);
    }

    public static float[][] copy(float[][] array, long offset, long length) {
        ensureOffsetLength(array, offset, length);
        float[][] a = FloatBigArrays.newBigArray(length);
        copy(array, offset, a, 0L, length);
        return a;
    }

    public static float[][] copy(float[][] array) {
        float[][] base = (float[][]) array.clone();
        int i = base.length;
        while (true) {
            int i2 = i;
            i--;
            if (i2 != 0) {
                base[i] = (float[]) array[i].clone();
            } else {
                return base;
            }
        }
    }

    public static void fill(float[][] array, float value) {
        int i = array.length;
        while (true) {
            int i2 = i;
            i--;
            if (i2 != 0) {
                java.util.Arrays.fill(array[i], value);
            } else {
                return;
            }
        }
    }

    public static void fill(float[][] array, long from, long to, float value) {
        long length = length(array);
        ensureFromTo(length, from, to);
        if (length == 0) {
            return;
        }
        int fromSegment = segment(from);
        int toSegment = segment(to);
        int fromDispl = displacement(from);
        int toDispl = displacement(to);
        if (fromSegment == toSegment) {
            java.util.Arrays.fill(array[fromSegment], fromDispl, toDispl, value);
            return;
        }
        if (toDispl != 0) {
            java.util.Arrays.fill(array[toSegment], 0, toDispl, value);
        }
        while (true) {
            toSegment--;
            if (toSegment > fromSegment) {
                java.util.Arrays.fill(array[toSegment], value);
            } else {
                java.util.Arrays.fill(array[fromSegment], fromDispl, 134217728, value);
                return;
            }
        }
    }

    public static boolean equals(float[][] a1, float[][] a2) {
        if (length(a1) != length(a2)) {
            return false;
        }
        int i = a1.length;
        while (true) {
            int i2 = i;
            i--;
            if (i2 != 0) {
                float[] t = a1[i];
                float[] u = a2[i];
                int j = t.length;
                while (true) {
                    int i3 = j;
                    j--;
                    if (i3 != 0) {
                        if (Float.floatToIntBits(t[j]) != Float.floatToIntBits(u[j])) {
                            return false;
                        }
                    }
                }
            } else {
                return true;
            }
        }
    }

    public static String toString(float[][] a) {
        if (a == null) {
            return Configurator.NULL;
        }
        long last = length(a) - 1;
        if (last == -1) {
            return "[]";
        }
        StringBuilder b = new StringBuilder();
        b.append('[');
        long j = 0;
        while (true) {
            long i = j;
            b.append(String.valueOf(get(a, i)));
            if (i == last) {
                return b.append(']').toString();
            }
            b.append(", ");
            j = i + 1;
        }
    }

    public static void ensureFromTo(float[][] a, long from, long to) {
        ensureFromTo(length(a), from, to);
    }

    public static void ensureOffsetLength(float[][] a, long offset, long length) {
        ensureOffsetLength(length(a), offset, length);
    }

    public static void ensureSameLength(float[][] a, float[][] b) {
        if (length(a) != length(b)) {
            throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b));
        }
    }

    public static float[][] shuffle(float[][] a, long from, long to, Random random) {
        long i = to - from;
        while (true) {
            long j = i;
            i = j - 1;
            if (j != 0) {
                long p = (random.nextLong() & LongCompanionObject.MAX_VALUE) % (i + 1);
                float t = get(a, from + i);
                set(a, from + i, get(a, from + p));
                set(a, from + p, t);
            } else {
                return a;
            }
        }
    }

    public static float[][] shuffle(float[][] a, Random random) {
        long i = length(a);
        while (true) {
            long j = i;
            i = j - 1;
            if (j != 0) {
                long p = (random.nextLong() & LongCompanionObject.MAX_VALUE) % (i + 1);
                float t = get(a, i);
                set(a, i, get(a, p));
                set(a, p, t);
            } else {
                return a;
            }
        }
    }

    public static <K> K get(K[][] array, long index) {
        return array[segment(index)][displacement(index)];
    }

    public static <K> void set(K[][] array, long index, K value) {
        array[segment(index)][displacement(index)] = value;
    }

    public static <K> void swap(K[][] array, long first, long second) {
        K t = array[segment(first)][displacement(first)];
        array[segment(first)][displacement(first)] = array[segment(second)][displacement(second)];
        array[segment(second)][displacement(second)] = t;
    }

    public static <K> K[][] reverse(K[][] a) {
        long length = length(a);
        long i = length / 2;
        while (true) {
            long j = i;
            i = j - 1;
            if (j != 0) {
                swap(a, i, (length - i) - 1);
            } else {
                return a;
            }
        }
    }

    public static <K> long length(K[][] array) {
        int length = array.length;
        if (length == 0) {
            return 0L;
        }
        return start(length - 1) + array[length - 1].length;
    }

    public static <K> void copy(K[][] srcArray, long srcPos, K[][] destArray, long destPos, long length) {
        if (destPos <= srcPos) {
            int srcSegment = segment(srcPos);
            int destSegment = segment(destPos);
            int srcDispl = displacement(srcPos);
            int destDispl = displacement(destPos);
            while (length > 0) {
                int l = (int) Math.min(length, Math.min(srcArray[srcSegment].length - srcDispl, destArray[destSegment].length - destDispl));
                if (l == 0) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                System.arraycopy(srcArray[srcSegment], srcDispl, destArray[destSegment], destDispl, l);
                int i = srcDispl + l;
                srcDispl = i;
                if (i == 134217728) {
                    srcDispl = 0;
                    srcSegment++;
                }
                int i2 = destDispl + l;
                destDispl = i2;
                if (i2 == 134217728) {
                    destDispl = 0;
                    destSegment++;
                }
                length -= l;
            }
            return;
        }
        int srcSegment2 = segment(srcPos + length);
        int destSegment2 = segment(destPos + length);
        int srcDispl2 = displacement(srcPos + length);
        int destDispl2 = displacement(destPos + length);
        while (length > 0) {
            if (srcDispl2 == 0) {
                srcDispl2 = 134217728;
                srcSegment2--;
            }
            if (destDispl2 == 0) {
                destDispl2 = 134217728;
                destSegment2--;
            }
            int l2 = (int) Math.min(length, Math.min(srcDispl2, destDispl2));
            if (l2 == 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            System.arraycopy(srcArray[srcSegment2], srcDispl2 - l2, destArray[destSegment2], destDispl2 - l2, l2);
            srcDispl2 -= l2;
            destDispl2 -= l2;
            length -= l2;
        }
    }

    public static <K> void copyFromBig(K[][] srcArray, long srcPos, K[] destArray, int destPos, int length) {
        int srcSegment = segment(srcPos);
        int srcDispl = displacement(srcPos);
        while (length > 0) {
            int l = Math.min(srcArray[srcSegment].length - srcDispl, length);
            if (l == 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            System.arraycopy(srcArray[srcSegment], srcDispl, destArray, destPos, l);
            int i = srcDispl + l;
            srcDispl = i;
            if (i == 134217728) {
                srcDispl = 0;
                srcSegment++;
            }
            destPos += l;
            length -= l;
        }
    }

    public static <K> void copyToBig(K[] srcArray, int srcPos, K[][] destArray, long destPos, long length) {
        int destSegment = segment(destPos);
        int destDispl = displacement(destPos);
        while (length > 0) {
            int l = (int) Math.min(destArray[destSegment].length - destDispl, length);
            if (l == 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            System.arraycopy(srcArray, srcPos, destArray[destSegment], destDispl, l);
            int i = destDispl + l;
            destDispl = i;
            if (i == 134217728) {
                destDispl = 0;
                destSegment++;
            }
            srcPos += l;
            length -= l;
        }
    }

    public static <K> K[][] wrap(K[] array) {
        if (array.length == 0 && array.getClass() == Object[].class) {
            return (K[][]) ObjectBigArrays.EMPTY_BIG_ARRAY;
        }
        if (array.length <= 134217728) {
            K[][] bigArray = (K[][]) ((Object[][]) Array.newInstance(array.getClass(), 1));
            bigArray[0] = array;
            return bigArray;
        }
        K[][] bigArray2 = (K[][]) ObjectBigArrays.newBigArray(array.getClass(), array.length);
        for (int i = 0; i < bigArray2.length; i++) {
            System.arraycopy(array, (int) start(i), bigArray2[i], 0, bigArray2[i].length);
        }
        return bigArray2;
    }

    public static <K> K[][] ensureCapacity(K[][] array, long length) {
        return (K[][]) ensureCapacity(array, length, length(array));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <K> K[][] forceCapacity(K[][] array, long length, long preserve) {
        ensureLength(length);
        int valid = array.length - ((array.length == 0 || (array.length > 0 && array[array.length - 1].length == 134217728)) ? 0 : 1);
        int baseLength = (int) ((length + 134217727) >>> 27);
        K[][] base = (K[][]) ((Object[][]) java.util.Arrays.copyOf(array, baseLength));
        Class<?> componentType = array.getClass().getComponentType();
        int residual = (int) (length & 134217727);
        if (residual != 0) {
            for (int i = valid; i < baseLength - 1; i++) {
                base[i] = (Object[]) Array.newInstance(componentType.getComponentType(), 134217728);
            }
            base[baseLength - 1] = (Object[]) Array.newInstance(componentType.getComponentType(), residual);
        } else {
            for (int i2 = valid; i2 < baseLength; i2++) {
                base[i2] = (Object[]) Array.newInstance(componentType.getComponentType(), 134217728);
            }
        }
        if (preserve - (valid * 134217728) > 0) {
            copy(array, valid * 134217728, base, valid * 134217728, preserve - (valid * 134217728));
        }
        return base;
    }

    public static <K> K[][] ensureCapacity(K[][] array, long length, long preserve) {
        return length > length(array) ? (K[][]) forceCapacity(array, length, preserve) : array;
    }

    public static <K> K[][] grow(K[][] array, long length) {
        long oldLength = length(array);
        return length > oldLength ? (K[][]) grow(array, length, oldLength) : array;
    }

    public static <K> K[][] grow(K[][] array, long length, long preserve) {
        long oldLength = length(array);
        if (length > oldLength) {
            return (K[][]) ensureCapacity(array, Math.max(oldLength + (oldLength >> 1), length), preserve);
        }
        return array;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <K> K[][] trim(K[][] array, long length) {
        ensureLength(length);
        long oldLength = length(array);
        if (length >= oldLength) {
            return array;
        }
        int baseLength = (int) ((length + 134217727) >>> 27);
        K[][] base = (K[][]) ((Object[][]) java.util.Arrays.copyOf(array, baseLength));
        int residual = (int) (length & 134217727);
        if (residual != 0) {
            base[baseLength - 1] = ObjectArrays.trim(base[baseLength - 1], residual);
        }
        return base;
    }

    public static <K> K[][] setLength(K[][] array, long length) {
        long oldLength = length(array);
        if (length == oldLength) {
            return array;
        }
        if (length < oldLength) {
            return (K[][]) trim(array, length);
        }
        return (K[][]) ensureCapacity(array, length);
    }

    public static <K> K[][] copy(K[][] array, long offset, long length) {
        ensureOffsetLength(array, offset, length);
        K[][] a = (K[][]) ObjectBigArrays.newBigArray(array, length);
        copy(array, offset, a, 0L, length);
        return a;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <K> K[][] copy(K[][] array) {
        K[][] base = (K[][]) ((Object[][]) array.clone());
        int i = base.length;
        while (true) {
            int i2 = i;
            i--;
            if (i2 != 0) {
                base[i] = (Object[]) array[i].clone();
            } else {
                return base;
            }
        }
    }

    public static <K> void fill(K[][] array, K value) {
        int i = array.length;
        while (true) {
            int i2 = i;
            i--;
            if (i2 != 0) {
                java.util.Arrays.fill(array[i], value);
            } else {
                return;
            }
        }
    }

    public static <K> void fill(K[][] array, long from, long to, K value) {
        long length = length(array);
        ensureFromTo(length, from, to);
        if (length == 0) {
            return;
        }
        int fromSegment = segment(from);
        int toSegment = segment(to);
        int fromDispl = displacement(from);
        int toDispl = displacement(to);
        if (fromSegment == toSegment) {
            java.util.Arrays.fill(array[fromSegment], fromDispl, toDispl, value);
            return;
        }
        if (toDispl != 0) {
            java.util.Arrays.fill(array[toSegment], 0, toDispl, value);
        }
        while (true) {
            toSegment--;
            if (toSegment > fromSegment) {
                java.util.Arrays.fill(array[toSegment], value);
            } else {
                java.util.Arrays.fill(array[fromSegment], fromDispl, 134217728, value);
                return;
            }
        }
    }

    public static <K> boolean equals(K[][] a1, K[][] a2) {
        if (length(a1) != length(a2)) {
            return false;
        }
        int i = a1.length;
        while (true) {
            int i2 = i;
            i--;
            if (i2 != 0) {
                K[] t = a1[i];
                K[] u = a2[i];
                int j = t.length;
                while (true) {
                    int i3 = j;
                    j--;
                    if (i3 != 0) {
                        if (!Objects.equals(t[j], u[j])) {
                            return false;
                        }
                    }
                }
            } else {
                return true;
            }
        }
    }

    public static <K> String toString(K[][] a) {
        if (a == null) {
            return Configurator.NULL;
        }
        long last = length(a) - 1;
        if (last == -1) {
            return "[]";
        }
        StringBuilder b = new StringBuilder();
        b.append('[');
        long j = 0;
        while (true) {
            long i = j;
            b.append(String.valueOf(get(a, i)));
            if (i == last) {
                return b.append(']').toString();
            }
            b.append(", ");
            j = i + 1;
        }
    }

    public static <K> void ensureFromTo(K[][] a, long from, long to) {
        ensureFromTo(length(a), from, to);
    }

    public static <K> void ensureOffsetLength(K[][] a, long offset, long length) {
        ensureOffsetLength(length(a), offset, length);
    }

    public static <K> void ensureSameLength(K[][] a, K[][] b) {
        if (length(a) != length(b)) {
            throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b));
        }
    }

    public static <K> K[][] shuffle(K[][] a, long from, long to, Random random) {
        long i = to - from;
        while (true) {
            long j = i;
            i = j - 1;
            if (j != 0) {
                long p = (random.nextLong() & LongCompanionObject.MAX_VALUE) % (i + 1);
                Object obj = get(a, from + i);
                set(a, from + i, get(a, from + p));
                set(a, from + p, obj);
            } else {
                return a;
            }
        }
    }

    public static <K> K[][] shuffle(K[][] a, Random random) {
        long i = length(a);
        while (true) {
            long j = i;
            i = j - 1;
            if (j != 0) {
                long p = (random.nextLong() & LongCompanionObject.MAX_VALUE) % (i + 1);
                Object obj = get(a, i);
                set(a, i, get(a, p));
                set(a, p, obj);
            } else {
                return a;
            }
        }
    }

    public static void main(String[] arg) {
        int[][] a = IntBigArrays.newBigArray(1 << Integer.parseInt(arg[0]));
        int k = 10;
        while (true) {
            int i = k;
            k--;
            if (i != 0) {
                long start = -System.currentTimeMillis();
                long x = 0;
                long i2 = length(a);
                while (true) {
                    long j = i2;
                    i2 = j - 1;
                    if (j == 0) {
                        break;
                    }
                    x ^= i2 ^ get(a, i2);
                }
                if (x == 0) {
                    System.err.println();
                }
                System.out.println("Single loop: " + (start + System.currentTimeMillis()) + "ms");
                long start2 = -System.currentTimeMillis();
                long y = 0;
                int i3 = a.length;
                while (true) {
                    int i4 = i3;
                    i3--;
                    if (i4 == 0) {
                        break;
                    }
                    int[] t = a[i3];
                    int d = t.length;
                    while (true) {
                        int i5 = d;
                        d--;
                        if (i5 != 0) {
                            y ^= t[d] ^ index(i3, d);
                        }
                    }
                }
                if (y == 0) {
                    System.err.println();
                }
                if (x != y) {
                    throw new AssertionError();
                }
                System.out.println("Double loop: " + (start2 + System.currentTimeMillis()) + "ms");
                long j2 = length(a);
                int i6 = a.length;
                while (true) {
                    int i7 = i6;
                    i6--;
                    if (i7 == 0) {
                        break;
                    }
                    int[] t2 = a[i6];
                    int d2 = t2.length;
                    while (true) {
                        int i8 = d2;
                        d2--;
                        if (i8 != 0) {
                            long j3 = y;
                            j2 = j3;
                            y = j3 ^ (t2[d2] ^ (j2 - 1));
                        }
                    }
                }
                if (0 == 0) {
                    System.err.println();
                }
                if (x != 0) {
                    throw new AssertionError();
                }
                System.out.println("Double loop (with additional index): " + (start2 + System.currentTimeMillis()) + "ms");
            } else {
                return;
            }
        }
    }
}
