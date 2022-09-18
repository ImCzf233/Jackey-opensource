package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Arrays;
import com.viaversion.viaversion.libs.fastutil.Hash;
import java.io.Serializable;
import java.util.Random;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntArrays.class */
public final class IntArrays {
    private static final int QUICKSORT_NO_REC = 16;
    private static final int PARALLEL_QUICKSORT_NO_FORK = 8192;
    private static final int QUICKSORT_MEDIAN_OF_9 = 128;
    private static final int MERGESORT_NO_REC = 16;
    private static final int DIGIT_BITS = 8;
    private static final int DIGIT_MASK = 255;
    private static final int DIGITS_PER_ELEMENT = 4;
    private static final int RADIXSORT_NO_REC = 1024;
    private static final int PARALLEL_RADIXSORT_NO_FORK = 1024;
    static final int RADIX_SORT_MIN_THRESHOLD = 2000;
    public static final int[] EMPTY_ARRAY = new int[0];
    public static final int[] DEFAULT_EMPTY_ARRAY = new int[0];
    protected static final Segment POISON_PILL = new Segment(-1, -1, -1);
    public static final Hash.Strategy<int[]> HASH_STRATEGY = new ArrayHashStrategy();

    private IntArrays() {
    }

    public static int[] forceCapacity(int[] array, int length, int preserve) {
        int[] t = new int[length];
        System.arraycopy(array, 0, t, 0, preserve);
        return t;
    }

    public static int[] ensureCapacity(int[] array, int length) {
        return ensureCapacity(array, length, array.length);
    }

    public static int[] ensureCapacity(int[] array, int length, int preserve) {
        return length > array.length ? forceCapacity(array, length, preserve) : array;
    }

    public static int[] grow(int[] array, int length) {
        return grow(array, length, array.length);
    }

    public static int[] grow(int[] array, int length, int preserve) {
        if (length > array.length) {
            int newLength = (int) Math.max(Math.min(array.length + (array.length >> 1), 2147483639L), length);
            int[] t = new int[newLength];
            System.arraycopy(array, 0, t, 0, preserve);
            return t;
        }
        return array;
    }

    public static int[] trim(int[] array, int length) {
        if (length >= array.length) {
            return array;
        }
        int[] t = length == 0 ? EMPTY_ARRAY : new int[length];
        System.arraycopy(array, 0, t, 0, length);
        return t;
    }

    public static int[] setLength(int[] array, int length) {
        if (length == array.length) {
            return array;
        }
        if (length < array.length) {
            return trim(array, length);
        }
        return ensureCapacity(array, length);
    }

    public static int[] copy(int[] array, int offset, int length) {
        ensureOffsetLength(array, offset, length);
        int[] a = length == 0 ? EMPTY_ARRAY : new int[length];
        System.arraycopy(array, offset, a, 0, length);
        return a;
    }

    public static int[] copy(int[] array) {
        return (int[]) array.clone();
    }

    @Deprecated
    public static void fill(int[] array, int value) {
        int i = array.length;
        while (true) {
            int i2 = i;
            i--;
            if (i2 != 0) {
                array[i] = value;
            } else {
                return;
            }
        }
    }

    @Deprecated
    public static void fill(int[] array, int from, int to, int value) {
        ensureFromTo(array, from, to);
        if (from != 0) {
            for (int i = from; i < to; i++) {
                array[i] = value;
            }
            return;
        }
        while (true) {
            int i2 = to;
            to--;
            if (i2 != 0) {
                array[to] = value;
            } else {
                return;
            }
        }
    }

    @Deprecated
    public static boolean equals(int[] a1, int[] a2) {
        int i = a1.length;
        if (i != a2.length) {
            return false;
        }
        do {
            int i2 = i;
            i--;
            if (i2 == 0) {
                return true;
            }
        } while (a1[i] == a2[i]);
        return false;
    }

    public static void ensureFromTo(int[] a, int from, int to) {
        Arrays.ensureFromTo(a.length, from, to);
    }

    public static void ensureOffsetLength(int[] a, int offset, int length) {
        Arrays.ensureOffsetLength(a.length, offset, length);
    }

    public static void ensureSameLength(int[] a, int[] b) {
        if (a.length != b.length) {
            throw new IllegalArgumentException("Array size mismatch: " + a.length + " != " + b.length);
        }
    }

    private static ForkJoinPool getPool() {
        ForkJoinPool current = ForkJoinTask.getPool();
        return current == null ? ForkJoinPool.commonPool() : current;
    }

    public static void swap(int[] x, int a, int b) {
        int t = x[a];
        x[a] = x[b];
        x[b] = t;
    }

    public static void swap(int[] x, int a, int b, int n) {
        int i = 0;
        while (i < n) {
            swap(x, a, b);
            i++;
            a++;
            b++;
        }
    }

    public static int med3(int[] x, int a, int b, int c, IntComparator comp) {
        int ab = comp.compare(x[a], x[b]);
        int ac = comp.compare(x[a], x[c]);
        int bc = comp.compare(x[b], x[c]);
        return ab < 0 ? bc < 0 ? b : ac < 0 ? c : a : bc > 0 ? b : ac > 0 ? c : a;
    }

    private static void selectionSort(int[] a, int from, int to, IntComparator comp) {
        for (int i = from; i < to - 1; i++) {
            int m = i;
            for (int j = i + 1; j < to; j++) {
                if (comp.compare(a[j], a[m]) < 0) {
                    m = j;
                }
            }
            if (m != i) {
                int u = a[i];
                a[i] = a[m];
                a[m] = u;
            }
        }
    }

    private static void insertionSort(int[] a, int from, int to, IntComparator comp) {
        int i = from;
        while (true) {
            i++;
            if (i < to) {
                int t = a[i];
                int j = i;
                int i2 = a[j - 1];
                while (true) {
                    int u = i2;
                    if (comp.compare(t, u) < 0) {
                        a[j] = u;
                        if (from != j - 1) {
                            j--;
                            i2 = a[j - 1];
                        } else {
                            j--;
                            break;
                        }
                    }
                }
                a[j] = t;
            } else {
                return;
            }
        }
    }

    public static void quickSort(int[] x, int from, int to, IntComparator comp) {
        int comparison;
        int comparison2;
        int len = to - from;
        if (len < 16) {
            selectionSort(x, from, to, comp);
            return;
        }
        int m = from + (len / 2);
        int l = from;
        int n = to - 1;
        if (len > 128) {
            int s = len / 8;
            l = med3(x, l, l + s, l + (2 * s), comp);
            m = med3(x, m - s, m, m + s, comp);
            n = med3(x, n - (2 * s), n - s, n, comp);
        }
        int v = x[med3(x, l, m, n, comp)];
        int a = from;
        int b = a;
        int c = to - 1;
        int d = c;
        while (true) {
            if (b <= c && (comparison2 = comp.compare(x[b], v)) <= 0) {
                if (comparison2 == 0) {
                    int i = a;
                    a++;
                    swap(x, i, b);
                }
                b++;
            } else {
                while (c >= b && (comparison = comp.compare(x[c], v)) >= 0) {
                    if (comparison == 0) {
                        int i2 = d;
                        d--;
                        swap(x, c, i2);
                    }
                    c--;
                }
                if (b > c) {
                    break;
                }
                int i3 = b;
                b++;
                int i4 = c;
                c--;
                swap(x, i3, i4);
            }
        }
        int s2 = Math.min(a - from, b - a);
        swap(x, from, b - s2, s2);
        int s3 = Math.min(d - c, (to - d) - 1);
        swap(x, b, to - s3, s3);
        int s4 = b - a;
        if (s4 > 1) {
            quickSort(x, from, from + s4, comp);
        }
        int s5 = d - c;
        if (s5 > 1) {
            quickSort(x, to - s5, to, comp);
        }
    }

    public static void quickSort(int[] x, IntComparator comp) {
        quickSort(x, 0, x.length, comp);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntArrays$ForkJoinQuickSortComp.class */
    public static class ForkJoinQuickSortComp extends RecursiveAction {
        private static final long serialVersionUID = 1;
        private final int from;

        /* renamed from: to */
        private final int f99to;

        /* renamed from: x */
        private final int[] f100x;
        private final IntComparator comp;

        public ForkJoinQuickSortComp(int[] x, int from, int to, IntComparator comp) {
            this.from = from;
            this.f99to = to;
            this.f100x = x;
            this.comp = comp;
        }

        @Override // java.util.concurrent.RecursiveAction
        protected void compute() {
            int comparison;
            int comparison2;
            int[] x = this.f100x;
            int len = this.f99to - this.from;
            if (len < 8192) {
                IntArrays.quickSort(x, this.from, this.f99to, this.comp);
                return;
            }
            int m = this.from + (len / 2);
            int l = this.from;
            int n = this.f99to - 1;
            int s = len / 8;
            int v = x[IntArrays.med3(x, IntArrays.med3(x, l, l + s, l + (2 * s), this.comp), IntArrays.med3(x, m - s, m, m + s, this.comp), IntArrays.med3(x, n - (2 * s), n - s, n, this.comp), this.comp)];
            int a = this.from;
            int b = a;
            int c = this.f99to - 1;
            int d = c;
            while (true) {
                if (b <= c && (comparison2 = this.comp.compare(x[b], v)) <= 0) {
                    if (comparison2 == 0) {
                        int i = a;
                        a++;
                        IntArrays.swap(x, i, b);
                    }
                    b++;
                } else {
                    while (c >= b && (comparison = this.comp.compare(x[c], v)) >= 0) {
                        if (comparison == 0) {
                            int i2 = d;
                            d--;
                            IntArrays.swap(x, c, i2);
                        }
                        c--;
                    }
                    if (b > c) {
                        break;
                    }
                    int i3 = b;
                    b++;
                    int i4 = c;
                    c--;
                    IntArrays.swap(x, i3, i4);
                }
            }
            int s2 = Math.min(a - this.from, b - a);
            IntArrays.swap(x, this.from, b - s2, s2);
            int s3 = Math.min(d - c, (this.f99to - d) - 1);
            IntArrays.swap(x, b, this.f99to - s3, s3);
            int s4 = b - a;
            int t = d - c;
            if (s4 > 1 && t > 1) {
                invokeAll(new ForkJoinQuickSortComp(x, this.from, this.from + s4, this.comp), new ForkJoinQuickSortComp(x, this.f99to - t, this.f99to, this.comp));
            } else if (s4 > 1) {
                invokeAll(new ForkJoinTask[]{new ForkJoinQuickSortComp(x, this.from, this.from + s4, this.comp)});
            } else {
                invokeAll(new ForkJoinTask[]{new ForkJoinQuickSortComp(x, this.f99to - t, this.f99to, this.comp)});
            }
        }
    }

    public static void parallelQuickSort(int[] x, int from, int to, IntComparator comp) {
        ForkJoinPool pool = getPool();
        if (to - from < 8192 || pool.getParallelism() == 1) {
            quickSort(x, from, to, comp);
        } else {
            pool.invoke(new ForkJoinQuickSortComp(x, from, to, comp));
        }
    }

    public static void parallelQuickSort(int[] x, IntComparator comp) {
        parallelQuickSort(x, 0, x.length, comp);
    }

    public static int med3(int[] x, int a, int b, int c) {
        int ab = Integer.compare(x[a], x[b]);
        int ac = Integer.compare(x[a], x[c]);
        int bc = Integer.compare(x[b], x[c]);
        return ab < 0 ? bc < 0 ? b : ac < 0 ? c : a : bc > 0 ? b : ac > 0 ? c : a;
    }

    private static void selectionSort(int[] a, int from, int to) {
        for (int i = from; i < to - 1; i++) {
            int m = i;
            for (int j = i + 1; j < to; j++) {
                if (a[j] < a[m]) {
                    m = j;
                }
            }
            if (m != i) {
                int u = a[i];
                a[i] = a[m];
                a[m] = u;
            }
        }
    }

    private static void insertionSort(int[] a, int from, int to) {
        int i = from;
        while (true) {
            i++;
            if (i < to) {
                int t = a[i];
                int j = i;
                int i2 = a[j - 1];
                while (true) {
                    int u = i2;
                    if (t < u) {
                        a[j] = u;
                        if (from != j - 1) {
                            j--;
                            i2 = a[j - 1];
                        } else {
                            j--;
                            break;
                        }
                    }
                }
                a[j] = t;
            } else {
                return;
            }
        }
    }

    public static void quickSort(int[] x, int from, int to) {
        int comparison;
        int comparison2;
        int len = to - from;
        if (len < 16) {
            selectionSort(x, from, to);
            return;
        }
        int m = from + (len / 2);
        int l = from;
        int n = to - 1;
        if (len > 128) {
            int s = len / 8;
            l = med3(x, l, l + s, l + (2 * s));
            m = med3(x, m - s, m, m + s);
            n = med3(x, n - (2 * s), n - s, n);
        }
        int v = x[med3(x, l, m, n)];
        int a = from;
        int b = a;
        int c = to - 1;
        int d = c;
        while (true) {
            if (b <= c && (comparison2 = Integer.compare(x[b], v)) <= 0) {
                if (comparison2 == 0) {
                    int i = a;
                    a++;
                    swap(x, i, b);
                }
                b++;
            } else {
                while (c >= b && (comparison = Integer.compare(x[c], v)) >= 0) {
                    if (comparison == 0) {
                        int i2 = d;
                        d--;
                        swap(x, c, i2);
                    }
                    c--;
                }
                if (b > c) {
                    break;
                }
                int i3 = b;
                b++;
                int i4 = c;
                c--;
                swap(x, i3, i4);
            }
        }
        int s2 = Math.min(a - from, b - a);
        swap(x, from, b - s2, s2);
        int s3 = Math.min(d - c, (to - d) - 1);
        swap(x, b, to - s3, s3);
        int s4 = b - a;
        if (s4 > 1) {
            quickSort(x, from, from + s4);
        }
        int s5 = d - c;
        if (s5 > 1) {
            quickSort(x, to - s5, to);
        }
    }

    public static void quickSort(int[] x) {
        quickSort(x, 0, x.length);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntArrays$ForkJoinQuickSort.class */
    public static class ForkJoinQuickSort extends RecursiveAction {
        private static final long serialVersionUID = 1;
        private final int from;

        /* renamed from: to */
        private final int f94to;

        /* renamed from: x */
        private final int[] f95x;

        public ForkJoinQuickSort(int[] x, int from, int to) {
            this.from = from;
            this.f94to = to;
            this.f95x = x;
        }

        @Override // java.util.concurrent.RecursiveAction
        protected void compute() {
            int comparison;
            int comparison2;
            int[] x = this.f95x;
            int len = this.f94to - this.from;
            if (len < 8192) {
                IntArrays.quickSort(x, this.from, this.f94to);
                return;
            }
            int m = this.from + (len / 2);
            int l = this.from;
            int n = this.f94to - 1;
            int s = len / 8;
            int v = x[IntArrays.med3(x, IntArrays.med3(x, l, l + s, l + (2 * s)), IntArrays.med3(x, m - s, m, m + s), IntArrays.med3(x, n - (2 * s), n - s, n))];
            int a = this.from;
            int b = a;
            int c = this.f94to - 1;
            int d = c;
            while (true) {
                if (b <= c && (comparison2 = Integer.compare(x[b], v)) <= 0) {
                    if (comparison2 == 0) {
                        int i = a;
                        a++;
                        IntArrays.swap(x, i, b);
                    }
                    b++;
                } else {
                    while (c >= b && (comparison = Integer.compare(x[c], v)) >= 0) {
                        if (comparison == 0) {
                            int i2 = d;
                            d--;
                            IntArrays.swap(x, c, i2);
                        }
                        c--;
                    }
                    if (b > c) {
                        break;
                    }
                    int i3 = b;
                    b++;
                    int i4 = c;
                    c--;
                    IntArrays.swap(x, i3, i4);
                }
            }
            int s2 = Math.min(a - this.from, b - a);
            IntArrays.swap(x, this.from, b - s2, s2);
            int s3 = Math.min(d - c, (this.f94to - d) - 1);
            IntArrays.swap(x, b, this.f94to - s3, s3);
            int s4 = b - a;
            int t = d - c;
            if (s4 > 1 && t > 1) {
                invokeAll(new ForkJoinQuickSort(x, this.from, this.from + s4), new ForkJoinQuickSort(x, this.f94to - t, this.f94to));
            } else if (s4 > 1) {
                invokeAll(new ForkJoinTask[]{new ForkJoinQuickSort(x, this.from, this.from + s4)});
            } else {
                invokeAll(new ForkJoinTask[]{new ForkJoinQuickSort(x, this.f94to - t, this.f94to)});
            }
        }
    }

    public static void parallelQuickSort(int[] x, int from, int to) {
        ForkJoinPool pool = getPool();
        if (to - from < 8192 || pool.getParallelism() == 1) {
            quickSort(x, from, to);
        } else {
            pool.invoke(new ForkJoinQuickSort(x, from, to));
        }
    }

    public static void parallelQuickSort(int[] x) {
        parallelQuickSort(x, 0, x.length);
    }

    public static int med3Indirect(int[] perm, int[] x, int a, int b, int c) {
        int aa = x[perm[a]];
        int bb = x[perm[b]];
        int cc = x[perm[c]];
        int ab = Integer.compare(aa, bb);
        int ac = Integer.compare(aa, cc);
        int bc = Integer.compare(bb, cc);
        return ab < 0 ? bc < 0 ? b : ac < 0 ? c : a : bc > 0 ? b : ac > 0 ? c : a;
    }

    private static void insertionSortIndirect(int[] perm, int[] a, int from, int to) {
        int i = from;
        while (true) {
            i++;
            if (i < to) {
                int t = perm[i];
                int j = i;
                int i2 = perm[j - 1];
                while (true) {
                    int u = i2;
                    if (a[t] < a[u]) {
                        perm[j] = u;
                        if (from != j - 1) {
                            j--;
                            i2 = perm[j - 1];
                        } else {
                            j--;
                            break;
                        }
                    }
                }
                perm[j] = t;
            } else {
                return;
            }
        }
    }

    public static void quickSortIndirect(int[] perm, int[] x, int from, int to) {
        int comparison;
        int comparison2;
        int len = to - from;
        if (len < 16) {
            insertionSortIndirect(perm, x, from, to);
            return;
        }
        int m = from + (len / 2);
        int l = from;
        int n = to - 1;
        if (len > 128) {
            int s = len / 8;
            l = med3Indirect(perm, x, l, l + s, l + (2 * s));
            m = med3Indirect(perm, x, m - s, m, m + s);
            n = med3Indirect(perm, x, n - (2 * s), n - s, n);
        }
        int v = x[perm[med3Indirect(perm, x, l, m, n)]];
        int a = from;
        int b = a;
        int c = to - 1;
        int d = c;
        while (true) {
            if (b <= c && (comparison2 = Integer.compare(x[perm[b]], v)) <= 0) {
                if (comparison2 == 0) {
                    int i = a;
                    a++;
                    swap(perm, i, b);
                }
                b++;
            } else {
                while (c >= b && (comparison = Integer.compare(x[perm[c]], v)) >= 0) {
                    if (comparison == 0) {
                        int i2 = d;
                        d--;
                        swap(perm, c, i2);
                    }
                    c--;
                }
                if (b > c) {
                    break;
                }
                int i3 = b;
                b++;
                int i4 = c;
                c--;
                swap(perm, i3, i4);
            }
        }
        int s2 = Math.min(a - from, b - a);
        swap(perm, from, b - s2, s2);
        int s3 = Math.min(d - c, (to - d) - 1);
        swap(perm, b, to - s3, s3);
        int s4 = b - a;
        if (s4 > 1) {
            quickSortIndirect(perm, x, from, from + s4);
        }
        int s5 = d - c;
        if (s5 > 1) {
            quickSortIndirect(perm, x, to - s5, to);
        }
    }

    public static void quickSortIndirect(int[] perm, int[] x) {
        quickSortIndirect(perm, x, 0, x.length);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntArrays$ForkJoinQuickSortIndirect.class */
    public static class ForkJoinQuickSortIndirect extends RecursiveAction {
        private static final long serialVersionUID = 1;
        private final int from;

        /* renamed from: to */
        private final int f101to;
        private final int[] perm;

        /* renamed from: x */
        private final int[] f102x;

        public ForkJoinQuickSortIndirect(int[] perm, int[] x, int from, int to) {
            this.from = from;
            this.f101to = to;
            this.f102x = x;
            this.perm = perm;
        }

        @Override // java.util.concurrent.RecursiveAction
        protected void compute() {
            int comparison;
            int comparison2;
            int[] x = this.f102x;
            int len = this.f101to - this.from;
            if (len < 8192) {
                IntArrays.quickSortIndirect(this.perm, x, this.from, this.f101to);
                return;
            }
            int m = this.from + (len / 2);
            int l = this.from;
            int n = this.f101to - 1;
            int s = len / 8;
            int v = x[this.perm[IntArrays.med3Indirect(this.perm, x, IntArrays.med3Indirect(this.perm, x, l, l + s, l + (2 * s)), IntArrays.med3Indirect(this.perm, x, m - s, m, m + s), IntArrays.med3Indirect(this.perm, x, n - (2 * s), n - s, n))]];
            int a = this.from;
            int b = a;
            int c = this.f101to - 1;
            int d = c;
            while (true) {
                if (b <= c && (comparison2 = Integer.compare(x[this.perm[b]], v)) <= 0) {
                    if (comparison2 == 0) {
                        int i = a;
                        a++;
                        IntArrays.swap(this.perm, i, b);
                    }
                    b++;
                } else {
                    while (c >= b && (comparison = Integer.compare(x[this.perm[c]], v)) >= 0) {
                        if (comparison == 0) {
                            int i2 = d;
                            d--;
                            IntArrays.swap(this.perm, c, i2);
                        }
                        c--;
                    }
                    if (b > c) {
                        break;
                    }
                    int i3 = b;
                    b++;
                    int i4 = c;
                    c--;
                    IntArrays.swap(this.perm, i3, i4);
                }
            }
            int s2 = Math.min(a - this.from, b - a);
            IntArrays.swap(this.perm, this.from, b - s2, s2);
            int s3 = Math.min(d - c, (this.f101to - d) - 1);
            IntArrays.swap(this.perm, b, this.f101to - s3, s3);
            int s4 = b - a;
            int t = d - c;
            if (s4 > 1 && t > 1) {
                invokeAll(new ForkJoinQuickSortIndirect(this.perm, x, this.from, this.from + s4), new ForkJoinQuickSortIndirect(this.perm, x, this.f101to - t, this.f101to));
            } else if (s4 > 1) {
                invokeAll(new ForkJoinTask[]{new ForkJoinQuickSortIndirect(this.perm, x, this.from, this.from + s4)});
            } else {
                invokeAll(new ForkJoinTask[]{new ForkJoinQuickSortIndirect(this.perm, x, this.f101to - t, this.f101to)});
            }
        }
    }

    public static void parallelQuickSortIndirect(int[] perm, int[] x, int from, int to) {
        ForkJoinPool pool = getPool();
        if (to - from < 8192 || pool.getParallelism() == 1) {
            quickSortIndirect(perm, x, from, to);
        } else {
            pool.invoke(new ForkJoinQuickSortIndirect(perm, x, from, to));
        }
    }

    public static void parallelQuickSortIndirect(int[] perm, int[] x) {
        parallelQuickSortIndirect(perm, x, 0, x.length);
    }

    public static void stabilize(int[] perm, int[] x, int from, int to) {
        int curr = from;
        for (int i = from + 1; i < to; i++) {
            if (x[perm[i]] != x[perm[curr]]) {
                if (i - curr > 1) {
                    parallelQuickSort(perm, curr, i);
                }
                curr = i;
            }
        }
        if (to - curr > 1) {
            parallelQuickSort(perm, curr, to);
        }
    }

    public static void stabilize(int[] perm, int[] x) {
        stabilize(perm, x, 0, perm.length);
    }

    public static int med3(int[] x, int[] y, int a, int b, int c) {
        int t = Integer.compare(x[a], x[b]);
        int ab = t == 0 ? Integer.compare(y[a], y[b]) : t;
        int t2 = Integer.compare(x[a], x[c]);
        int ac = t2 == 0 ? Integer.compare(y[a], y[c]) : t2;
        int t3 = Integer.compare(x[b], x[c]);
        int bc = t3 == 0 ? Integer.compare(y[b], y[c]) : t3;
        return ab < 0 ? bc < 0 ? b : ac < 0 ? c : a : bc > 0 ? b : ac > 0 ? c : a;
    }

    public static void swap(int[] x, int[] y, int a, int b) {
        int t = x[a];
        int u = y[a];
        x[a] = x[b];
        y[a] = y[b];
        x[b] = t;
        y[b] = u;
    }

    public static void swap(int[] x, int[] y, int a, int b, int n) {
        int i = 0;
        while (i < n) {
            swap(x, y, a, b);
            i++;
            a++;
            b++;
        }
    }

    private static void selectionSort(int[] a, int[] b, int from, int to) {
        for (int i = from; i < to - 1; i++) {
            int m = i;
            for (int j = i + 1; j < to; j++) {
                int u = Integer.compare(a[j], a[m]);
                if (u < 0 || (u == 0 && b[j] < b[m])) {
                    m = j;
                }
            }
            if (m != i) {
                int t = a[i];
                a[i] = a[m];
                a[m] = t;
                int t2 = b[i];
                b[i] = b[m];
                b[m] = t2;
            }
        }
    }

    public static void quickSort(int[] x, int[] y, int from, int to) {
        int i;
        int i2;
        int len = to - from;
        if (len < 16) {
            selectionSort(x, y, from, to);
            return;
        }
        int m = from + (len / 2);
        int l = from;
        int n = to - 1;
        if (len > 128) {
            int s = len / 8;
            l = med3(x, y, l, l + s, l + (2 * s));
            m = med3(x, y, m - s, m, m + s);
            n = med3(x, y, n - (2 * s), n - s, n);
        }
        int m2 = med3(x, y, l, m, n);
        int v = x[m2];
        int w = y[m2];
        int a = from;
        int b = a;
        int c = to - 1;
        int d = c;
        while (true) {
            if (b <= c) {
                int t = Integer.compare(x[b], v);
                if (t == 0) {
                    i2 = Integer.compare(y[b], w);
                } else {
                    i2 = t;
                }
                int comparison = i2;
                if (i2 <= 0) {
                    if (comparison == 0) {
                        int i3 = a;
                        a++;
                        swap(x, y, i3, b);
                    }
                    b++;
                }
            }
            while (c >= b) {
                int t2 = Integer.compare(x[c], v);
                if (t2 == 0) {
                    i = Integer.compare(y[c], w);
                } else {
                    i = t2;
                }
                int comparison2 = i;
                if (i < 0) {
                    break;
                }
                if (comparison2 == 0) {
                    int i4 = d;
                    d--;
                    swap(x, y, c, i4);
                }
                c--;
            }
            if (b > c) {
                break;
            }
            int i5 = b;
            b++;
            int i6 = c;
            c--;
            swap(x, y, i5, i6);
        }
        int s2 = Math.min(a - from, b - a);
        swap(x, y, from, b - s2, s2);
        int s3 = Math.min(d - c, (to - d) - 1);
        swap(x, y, b, to - s3, s3);
        int s4 = b - a;
        if (s4 > 1) {
            quickSort(x, y, from, from + s4);
        }
        int s5 = d - c;
        if (s5 > 1) {
            quickSort(x, y, to - s5, to);
        }
    }

    public static void quickSort(int[] x, int[] y) {
        ensureSameLength(x, y);
        quickSort(x, y, 0, x.length);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntArrays$ForkJoinQuickSort2.class */
    public static class ForkJoinQuickSort2 extends RecursiveAction {
        private static final long serialVersionUID = 1;
        private final int from;

        /* renamed from: to */
        private final int f96to;

        /* renamed from: x */
        private final int[] f97x;

        /* renamed from: y */
        private final int[] f98y;

        public ForkJoinQuickSort2(int[] x, int[] y, int from, int to) {
            this.from = from;
            this.f96to = to;
            this.f97x = x;
            this.f98y = y;
        }

        @Override // java.util.concurrent.RecursiveAction
        protected void compute() {
            int i;
            int i2;
            int[] x = this.f97x;
            int[] y = this.f98y;
            int len = this.f96to - this.from;
            if (len < 8192) {
                IntArrays.quickSort(x, y, this.from, this.f96to);
                return;
            }
            int m = this.from + (len / 2);
            int l = this.from;
            int n = this.f96to - 1;
            int s = len / 8;
            int m2 = IntArrays.med3(x, y, IntArrays.med3(x, y, l, l + s, l + (2 * s)), IntArrays.med3(x, y, m - s, m, m + s), IntArrays.med3(x, y, n - (2 * s), n - s, n));
            int v = x[m2];
            int w = y[m2];
            int a = this.from;
            int b = a;
            int c = this.f96to - 1;
            int d = c;
            while (true) {
                if (b <= c) {
                    int t = Integer.compare(x[b], v);
                    if (t == 0) {
                        i2 = Integer.compare(y[b], w);
                    } else {
                        i2 = t;
                    }
                    int comparison = i2;
                    if (i2 <= 0) {
                        if (comparison == 0) {
                            int i3 = a;
                            a++;
                            IntArrays.swap(x, y, i3, b);
                        }
                        b++;
                    }
                }
                while (c >= b) {
                    int t2 = Integer.compare(x[c], v);
                    if (t2 == 0) {
                        i = Integer.compare(y[c], w);
                    } else {
                        i = t2;
                    }
                    int comparison2 = i;
                    if (i < 0) {
                        break;
                    }
                    if (comparison2 == 0) {
                        int i4 = d;
                        d--;
                        IntArrays.swap(x, y, c, i4);
                    }
                    c--;
                }
                if (b > c) {
                    break;
                }
                int i5 = b;
                b++;
                int i6 = c;
                c--;
                IntArrays.swap(x, y, i5, i6);
            }
            int s2 = Math.min(a - this.from, b - a);
            IntArrays.swap(x, y, this.from, b - s2, s2);
            int s3 = Math.min(d - c, (this.f96to - d) - 1);
            IntArrays.swap(x, y, b, this.f96to - s3, s3);
            int s4 = b - a;
            int t3 = d - c;
            if (s4 > 1 && t3 > 1) {
                invokeAll(new ForkJoinQuickSort2(x, y, this.from, this.from + s4), new ForkJoinQuickSort2(x, y, this.f96to - t3, this.f96to));
            } else if (s4 > 1) {
                invokeAll(new ForkJoinTask[]{new ForkJoinQuickSort2(x, y, this.from, this.from + s4)});
            } else {
                invokeAll(new ForkJoinTask[]{new ForkJoinQuickSort2(x, y, this.f96to - t3, this.f96to)});
            }
        }
    }

    public static void parallelQuickSort(int[] x, int[] y, int from, int to) {
        ForkJoinPool pool = getPool();
        if (to - from < 8192 || pool.getParallelism() == 1) {
            quickSort(x, y, from, to);
        } else {
            pool.invoke(new ForkJoinQuickSort2(x, y, from, to));
        }
    }

    public static void parallelQuickSort(int[] x, int[] y) {
        ensureSameLength(x, y);
        parallelQuickSort(x, y, 0, x.length);
    }

    public static void unstableSort(int[] a, int from, int to) {
        if (to - from >= RADIX_SORT_MIN_THRESHOLD) {
            radixSort(a, from, to);
        } else {
            quickSort(a, from, to);
        }
    }

    public static void unstableSort(int[] a) {
        unstableSort(a, 0, a.length);
    }

    public static void unstableSort(int[] a, int from, int to, IntComparator comp) {
        quickSort(a, from, to, comp);
    }

    public static void unstableSort(int[] a, IntComparator comp) {
        unstableSort(a, 0, a.length, comp);
    }

    public static void mergeSort(int[] a, int from, int to, int[] supp) {
        int len = to - from;
        if (len < 16) {
            insertionSort(a, from, to);
            return;
        }
        if (supp == null) {
            supp = java.util.Arrays.copyOf(a, to);
        }
        int mid = (from + to) >>> 1;
        mergeSort(supp, from, mid, a);
        mergeSort(supp, mid, to, a);
        if (supp[mid - 1] <= supp[mid]) {
            System.arraycopy(supp, from, a, from, len);
            return;
        }
        int p = from;
        int q = mid;
        for (int i = from; i < to; i++) {
            if (q >= to || (p < mid && supp[p] <= supp[q])) {
                int i2 = p;
                p++;
                a[i] = supp[i2];
            } else {
                int i3 = q;
                q++;
                a[i] = supp[i3];
            }
        }
    }

    public static void mergeSort(int[] a, int from, int to) {
        mergeSort(a, from, to, (int[]) null);
    }

    public static void mergeSort(int[] a) {
        mergeSort(a, 0, a.length);
    }

    public static void mergeSort(int[] a, int from, int to, IntComparator comp, int[] supp) {
        int len = to - from;
        if (len < 16) {
            insertionSort(a, from, to, comp);
            return;
        }
        if (supp == null) {
            supp = java.util.Arrays.copyOf(a, to);
        }
        int mid = (from + to) >>> 1;
        mergeSort(supp, from, mid, comp, a);
        mergeSort(supp, mid, to, comp, a);
        if (comp.compare(supp[mid - 1], supp[mid]) <= 0) {
            System.arraycopy(supp, from, a, from, len);
            return;
        }
        int p = from;
        int q = mid;
        for (int i = from; i < to; i++) {
            if (q >= to || (p < mid && comp.compare(supp[p], supp[q]) <= 0)) {
                int i2 = p;
                p++;
                a[i] = supp[i2];
            } else {
                int i3 = q;
                q++;
                a[i] = supp[i3];
            }
        }
    }

    public static void mergeSort(int[] a, int from, int to, IntComparator comp) {
        mergeSort(a, from, to, comp, null);
    }

    public static void mergeSort(int[] a, IntComparator comp) {
        mergeSort(a, 0, a.length, comp);
    }

    public static void stableSort(int[] a, int from, int to) {
        unstableSort(a, from, to);
    }

    public static void stableSort(int[] a) {
        stableSort(a, 0, a.length);
    }

    public static void stableSort(int[] a, int from, int to, IntComparator comp) {
        mergeSort(a, from, to, comp);
    }

    public static void stableSort(int[] a, IntComparator comp) {
        stableSort(a, 0, a.length, comp);
    }

    public static int binarySearch(int[] a, int from, int to, int key) {
        int to2 = to - 1;
        while (from <= to2) {
            int mid = (from + to2) >>> 1;
            int midVal = a[mid];
            if (midVal < key) {
                from = mid + 1;
            } else if (midVal > key) {
                to2 = mid - 1;
            } else {
                return mid;
            }
        }
        return -(from + 1);
    }

    public static int binarySearch(int[] a, int key) {
        return binarySearch(a, 0, a.length, key);
    }

    public static int binarySearch(int[] a, int from, int to, int key, IntComparator c) {
        int to2 = to - 1;
        while (from <= to2) {
            int mid = (from + to2) >>> 1;
            int midVal = a[mid];
            int cmp = c.compare(midVal, key);
            if (cmp < 0) {
                from = mid + 1;
            } else if (cmp > 0) {
                to2 = mid - 1;
            } else {
                return mid;
            }
        }
        return -(from + 1);
    }

    public static int binarySearch(int[] a, int key, IntComparator c) {
        return binarySearch(a, 0, a.length, key, c);
    }

    public static void radixSort(int[] a) {
        radixSort(a, 0, a.length);
    }

    public static void radixSort(int[] a, int from, int to) {
        if (to - from < 1024) {
            quickSort(a, from, to);
            return;
        }
        int[] offsetStack = new int[766];
        int[] lengthStack = new int[766];
        int[] levelStack = new int[766];
        offsetStack[0] = from;
        lengthStack[0] = to - from;
        int stackPos = 0 + 1;
        levelStack[0] = 0;
        int[] count = new int[256];
        int[] pos = new int[256];
        while (stackPos > 0) {
            stackPos--;
            int first = offsetStack[stackPos];
            int length = lengthStack[stackPos];
            int level = levelStack[stackPos];
            int signMask = level % 4 == 0 ? 128 : 0;
            int shift = (3 - (level % 4)) * 8;
            int i = first + length;
            while (true) {
                int i2 = i;
                i--;
                if (i2 == first) {
                    break;
                }
                int i3 = ((a[i] >>> shift) & 255) ^ signMask;
                count[i3] = count[i3] + 1;
            }
            int lastUsed = -1;
            int p = first;
            for (int i4 = 0; i4 < 256; i4++) {
                if (count[i4] != 0) {
                    lastUsed = i4;
                }
                int i5 = p + count[i4];
                p = i5;
                pos[i4] = i5;
            }
            int end = (first + length) - count[lastUsed];
            int i6 = first;
            while (i6 <= end) {
                int t = a[i6];
                int c = ((t >>> shift) & 255) ^ signMask;
                if (i6 < end) {
                    while (true) {
                        int i7 = c;
                        int d = pos[i7] - 1;
                        pos[i7] = d;
                        if (d <= i6) {
                            break;
                        }
                        int z = t;
                        t = a[d];
                        a[d] = z;
                        c = ((t >>> shift) & 255) ^ signMask;
                    }
                    a[i6] = t;
                }
                if (level < 3 && count[c] > 1) {
                    if (count[c] < 1024) {
                        quickSort(a, i6, i6 + count[c]);
                    } else {
                        offsetStack[stackPos] = i6;
                        lengthStack[stackPos] = count[c];
                        int i8 = stackPos;
                        stackPos++;
                        levelStack[i8] = level + 1;
                    }
                }
                i6 += count[c];
                count[c] = 0;
            }
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntArrays$Segment.class */
    public static final class Segment {
        protected final int offset;
        protected final int length;
        protected final int level;

        protected Segment(int offset, int length, int level) {
            this.offset = offset;
            this.length = length;
            this.level = level;
        }

        public String toString() {
            return "Segment [offset=" + this.offset + ", length=" + this.length + ", level=" + this.level + "]";
        }
    }

    public static void parallelRadixSort(int[] a, int from, int to) {
        ForkJoinPool pool = getPool();
        if (to - from < 1024 || pool.getParallelism() == 1) {
            quickSort(a, from, to);
            return;
        }
        LinkedBlockingQueue<Segment> queue = new LinkedBlockingQueue<>();
        queue.add(new Segment(from, to - from, 0));
        AtomicInteger queueSize = new AtomicInteger(1);
        int numberOfThreads = pool.getParallelism();
        ExecutorCompletionService<Void> executorCompletionService = new ExecutorCompletionService<>(pool);
        int j = numberOfThreads;
        while (true) {
            int i = j;
            j--;
            if (i == 0) {
                break;
            }
            executorCompletionService.submit(() -> {
                int[] count = new int[256];
                int[] pos = new int[256];
                while (true) {
                    if (queueSize.get() == 0) {
                        int i2 = numberOfThreads;
                        while (true) {
                            int i3 = i2;
                            i2--;
                            if (i3 == 0) {
                                break;
                            }
                            queue.add(POISON_PILL);
                        }
                    }
                    Segment segment = (Segment) queue.take();
                    if (segment == POISON_PILL) {
                        return null;
                    }
                    int first = segment.offset;
                    int length = segment.length;
                    int level = segment.level;
                    int signMask = level % 4 == 0 ? 128 : 0;
                    int shift = (3 - (level % 4)) * 8;
                    int i4 = first + length;
                    while (true) {
                        int i5 = i4;
                        i4--;
                        if (i5 == first) {
                            break;
                        }
                        int i6 = ((a[i4] >>> shift) & 255) ^ signMask;
                        count[i6] = count[i6] + 1;
                    }
                    int lastUsed = -1;
                    int p = first;
                    for (int i7 = 0; i7 < 256; i7++) {
                        if (count[i7] != 0) {
                            lastUsed = i7;
                        }
                        int i8 = p + count[i7];
                        p = i8;
                        pos[i7] = i8;
                    }
                    int end = (first + length) - count[lastUsed];
                    int i9 = first;
                    while (i9 <= end) {
                        int t = a[i9];
                        int c = ((t >>> shift) & 255) ^ signMask;
                        if (i9 < end) {
                            while (true) {
                                int i10 = c;
                                int d = pos[i10] - 1;
                                pos[i10] = d;
                                if (d <= i9) {
                                    break;
                                }
                                int z = t;
                                t = a[d];
                                a[d] = z;
                                c = ((t >>> shift) & 255) ^ signMask;
                            }
                            a[i9] = t;
                        }
                        if (level < 3 && count[c] > 1) {
                            if (count[c] < 1024) {
                                quickSort(a, i9, i9 + count[c]);
                            } else {
                                queueSize.incrementAndGet();
                                queue.add(new Segment(i9, count[c], level + 1));
                            }
                        }
                        i9 += count[c];
                        count[c] = 0;
                    }
                    queueSize.decrementAndGet();
                }
            });
        }
        Throwable problem = null;
        int i2 = numberOfThreads;
        while (true) {
            int i3 = i2;
            i2--;
            if (i3 == 0) {
                break;
            }
            try {
                executorCompletionService.take().get();
            } catch (Exception e) {
                problem = e.getCause();
            }
        }
        if (problem != null) {
            if (!(problem instanceof RuntimeException)) {
                throw new RuntimeException(problem);
            }
        }
    }

    public static void parallelRadixSort(int[] a) {
        parallelRadixSort(a, 0, a.length);
    }

    public static void radixSortIndirect(int[] perm, int[] a, boolean stable) {
        radixSortIndirect(perm, a, 0, perm.length, stable);
    }

    public static void radixSortIndirect(int[] perm, int[] a, int from, int to, boolean stable) {
        if (to - from < 1024) {
            insertionSortIndirect(perm, a, from, to);
            return;
        }
        int[] offsetStack = new int[766];
        int[] lengthStack = new int[766];
        int[] levelStack = new int[766];
        offsetStack[0] = from;
        lengthStack[0] = to - from;
        int stackPos = 0 + 1;
        levelStack[0] = 0;
        int[] count = new int[256];
        int[] pos = new int[256];
        int[] support = stable ? new int[perm.length] : null;
        while (stackPos > 0) {
            stackPos--;
            int first = offsetStack[stackPos];
            int length = lengthStack[stackPos];
            int level = levelStack[stackPos];
            int signMask = level % 4 == 0 ? 128 : 0;
            int shift = (3 - (level % 4)) * 8;
            int i = first + length;
            while (true) {
                int i2 = i;
                i--;
                if (i2 == first) {
                    break;
                }
                int i3 = ((a[perm[i]] >>> shift) & 255) ^ signMask;
                count[i3] = count[i3] + 1;
            }
            int lastUsed = -1;
            int p = stable ? 0 : first;
            for (int i4 = 0; i4 < 256; i4++) {
                if (count[i4] != 0) {
                    lastUsed = i4;
                }
                int i5 = p + count[i4];
                p = i5;
                pos[i4] = i5;
            }
            if (stable) {
                int i6 = first + length;
                while (true) {
                    int i7 = i6;
                    i6--;
                    if (i7 == first) {
                        break;
                    }
                    int i8 = ((a[perm[i6]] >>> shift) & 255) ^ signMask;
                    int i9 = pos[i8] - 1;
                    pos[i8] = i9;
                    support[i9] = perm[i6];
                }
                System.arraycopy(support, 0, perm, first, length);
                int p2 = first;
                for (int i10 = 0; i10 <= lastUsed; i10++) {
                    if (level < 3 && count[i10] > 1) {
                        if (count[i10] < 1024) {
                            insertionSortIndirect(perm, a, p2, p2 + count[i10]);
                        } else {
                            offsetStack[stackPos] = p2;
                            lengthStack[stackPos] = count[i10];
                            int i11 = stackPos;
                            stackPos++;
                            levelStack[i11] = level + 1;
                        }
                    }
                    p2 += count[i10];
                }
                java.util.Arrays.fill(count, 0);
            } else {
                int end = (first + length) - count[lastUsed];
                int i12 = first;
                while (i12 <= end) {
                    int t = perm[i12];
                    int c = ((a[t] >>> shift) & 255) ^ signMask;
                    if (i12 < end) {
                        while (true) {
                            int i13 = c;
                            int d = pos[i13] - 1;
                            pos[i13] = d;
                            if (d <= i12) {
                                break;
                            }
                            int z = t;
                            t = perm[d];
                            perm[d] = z;
                            c = ((a[t] >>> shift) & 255) ^ signMask;
                        }
                        perm[i12] = t;
                    }
                    if (level < 3 && count[c] > 1) {
                        if (count[c] < 1024) {
                            insertionSortIndirect(perm, a, i12, i12 + count[c]);
                        } else {
                            offsetStack[stackPos] = i12;
                            lengthStack[stackPos] = count[c];
                            int i14 = stackPos;
                            stackPos++;
                            levelStack[i14] = level + 1;
                        }
                    }
                    i12 += count[c];
                    count[c] = 0;
                }
            }
        }
    }

    public static void parallelRadixSortIndirect(int[] perm, int[] a, int from, int to, boolean stable) {
        ForkJoinPool pool = getPool();
        if (to - from < 1024 || pool.getParallelism() == 1) {
            radixSortIndirect(perm, a, from, to, stable);
            return;
        }
        LinkedBlockingQueue<Segment> queue = new LinkedBlockingQueue<>();
        queue.add(new Segment(from, to - from, 0));
        AtomicInteger queueSize = new AtomicInteger(1);
        int numberOfThreads = pool.getParallelism();
        ExecutorCompletionService<Void> executorCompletionService = new ExecutorCompletionService<>(pool);
        int[] support = stable ? new int[perm.length] : null;
        int j = numberOfThreads;
        while (true) {
            int i = j;
            j--;
            if (i == 0) {
                break;
            }
            executorCompletionService.submit(() -> {
                int[] count = new int[256];
                int[] pos = new int[256];
                while (true) {
                    if (queueSize.get() == 0) {
                        int i2 = numberOfThreads;
                        while (true) {
                            int i3 = i2;
                            i2--;
                            if (i3 == 0) {
                                break;
                            }
                            queue.add(POISON_PILL);
                        }
                    }
                    Segment segment = (Segment) queue.take();
                    if (segment == POISON_PILL) {
                        return null;
                    }
                    int first = segment.offset;
                    int length = segment.length;
                    int level = segment.level;
                    int signMask = level % 4 == 0 ? 128 : 0;
                    int shift = (3 - (level % 4)) * 8;
                    int i4 = first + length;
                    while (true) {
                        int i5 = i4;
                        i4--;
                        if (i5 == first) {
                            break;
                        }
                        int i6 = ((a[perm[i4]] >>> shift) & 255) ^ signMask;
                        count[i6] = count[i6] + 1;
                    }
                    int lastUsed = -1;
                    int p = first;
                    for (int i7 = 0; i7 < 256; i7++) {
                        if (count[i7] != 0) {
                            lastUsed = i7;
                        }
                        int i8 = p + count[i7];
                        p = i8;
                        pos[i7] = i8;
                    }
                    if (stable) {
                        int i9 = first + length;
                        while (true) {
                            int i10 = i9;
                            i9--;
                            if (i10 == first) {
                                break;
                            }
                            int i11 = ((a[perm[i9]] >>> shift) & 255) ^ signMask;
                            int i12 = pos[i11] - 1;
                            pos[i11] = i12;
                            support[i12] = perm[i9];
                        }
                        System.arraycopy(support, first, perm, first, length);
                        int p2 = first;
                        for (int i13 = 0; i13 <= lastUsed; i13++) {
                            if (level < 3 && count[i13] > 1) {
                                if (count[i13] < 1024) {
                                    radixSortIndirect(perm, a, p2, p2 + count[i13], stable);
                                } else {
                                    queueSize.incrementAndGet();
                                    queue.add(new Segment(p2, count[i13], level + 1));
                                }
                            }
                            p2 += count[i13];
                        }
                        java.util.Arrays.fill(count, 0);
                    } else {
                        int end = (first + length) - count[lastUsed];
                        int i14 = first;
                        while (i14 <= end) {
                            int t = perm[i14];
                            int c = ((a[t] >>> shift) & 255) ^ signMask;
                            if (i14 < end) {
                                while (true) {
                                    int i15 = c;
                                    int d = pos[i15] - 1;
                                    pos[i15] = d;
                                    if (d <= i14) {
                                        break;
                                    }
                                    int z = t;
                                    t = perm[d];
                                    perm[d] = z;
                                    c = ((a[t] >>> shift) & 255) ^ signMask;
                                }
                                perm[i14] = t;
                            }
                            if (level < 3 && count[c] > 1) {
                                if (count[c] < 1024) {
                                    radixSortIndirect(perm, a, i14, i14 + count[c], stable);
                                } else {
                                    queueSize.incrementAndGet();
                                    queue.add(new Segment(i14, count[c], level + 1));
                                }
                            }
                            i14 += count[c];
                            count[c] = 0;
                        }
                    }
                    queueSize.decrementAndGet();
                }
            });
        }
        Throwable problem = null;
        int i2 = numberOfThreads;
        while (true) {
            int i3 = i2;
            i2--;
            if (i3 == 0) {
                break;
            }
            try {
                executorCompletionService.take().get();
            } catch (Exception e) {
                problem = e.getCause();
            }
        }
        if (problem != null) {
            if (!(problem instanceof RuntimeException)) {
                throw new RuntimeException(problem);
            }
        }
    }

    public static void parallelRadixSortIndirect(int[] perm, int[] a, boolean stable) {
        parallelRadixSortIndirect(perm, a, 0, a.length, stable);
    }

    public static void radixSort(int[] a, int[] b) {
        ensureSameLength(a, b);
        radixSort(a, b, 0, a.length);
    }

    public static void radixSort(int[] a, int[] b, int from, int to) {
        if (to - from < 1024) {
            selectionSort(a, b, from, to);
            return;
        }
        int[] offsetStack = new int[1786];
        int[] lengthStack = new int[1786];
        int[] levelStack = new int[1786];
        offsetStack[0] = from;
        lengthStack[0] = to - from;
        int stackPos = 0 + 1;
        levelStack[0] = 0;
        int[] count = new int[256];
        int[] pos = new int[256];
        while (stackPos > 0) {
            stackPos--;
            int first = offsetStack[stackPos];
            int length = lengthStack[stackPos];
            int level = levelStack[stackPos];
            int signMask = level % 4 == 0 ? 128 : 0;
            int[] k = level < 4 ? a : b;
            int shift = (3 - (level % 4)) * 8;
            int i = first + length;
            while (true) {
                int i2 = i;
                i--;
                if (i2 == first) {
                    break;
                }
                int i3 = ((k[i] >>> shift) & 255) ^ signMask;
                count[i3] = count[i3] + 1;
            }
            int lastUsed = -1;
            int p = first;
            for (int i4 = 0; i4 < 256; i4++) {
                if (count[i4] != 0) {
                    lastUsed = i4;
                }
                int i5 = p + count[i4];
                p = i5;
                pos[i4] = i5;
            }
            int end = (first + length) - count[lastUsed];
            int i6 = first;
            while (i6 <= end) {
                int t = a[i6];
                int u = b[i6];
                int c = ((k[i6] >>> shift) & 255) ^ signMask;
                if (i6 < end) {
                    while (true) {
                        int i7 = c;
                        int d = pos[i7] - 1;
                        pos[i7] = d;
                        if (d <= i6) {
                            break;
                        }
                        c = ((k[d] >>> shift) & 255) ^ signMask;
                        int z = t;
                        t = a[d];
                        a[d] = z;
                        int z2 = u;
                        u = b[d];
                        b[d] = z2;
                    }
                    a[i6] = t;
                    b[i6] = u;
                }
                if (level < 7 && count[c] > 1) {
                    if (count[c] < 1024) {
                        selectionSort(a, b, i6, i6 + count[c]);
                    } else {
                        offsetStack[stackPos] = i6;
                        lengthStack[stackPos] = count[c];
                        int i8 = stackPos;
                        stackPos++;
                        levelStack[i8] = level + 1;
                    }
                }
                i6 += count[c];
                count[c] = 0;
            }
        }
    }

    public static void parallelRadixSort(int[] a, int[] b, int from, int to) {
        ForkJoinPool pool = getPool();
        if (to - from < 1024 || pool.getParallelism() == 1) {
            quickSort(a, b, from, to);
        } else if (a.length != b.length) {
            throw new IllegalArgumentException("Array size mismatch.");
        } else {
            LinkedBlockingQueue<Segment> queue = new LinkedBlockingQueue<>();
            queue.add(new Segment(from, to - from, 0));
            AtomicInteger queueSize = new AtomicInteger(1);
            int numberOfThreads = pool.getParallelism();
            ExecutorCompletionService<Void> executorCompletionService = new ExecutorCompletionService<>(pool);
            int j = numberOfThreads;
            while (true) {
                int i = j;
                j--;
                if (i == 0) {
                    break;
                }
                executorCompletionService.submit(() -> {
                    int[] count = new int[256];
                    int[] pos = new int[256];
                    while (true) {
                        if (queueSize.get() == 0) {
                            int i2 = numberOfThreads;
                            while (true) {
                                int i3 = i2;
                                i2--;
                                if (i3 == 0) {
                                    break;
                                }
                                queue.add(POISON_PILL);
                            }
                        }
                        Segment segment = (Segment) queue.take();
                        if (segment == POISON_PILL) {
                            return null;
                        }
                        int first = segment.offset;
                        int length = segment.length;
                        int level = segment.level;
                        int signMask = level % 4 == 0 ? 128 : 0;
                        int[] k = level < 4 ? a : b;
                        int shift = (3 - (level % 4)) * 8;
                        int i4 = first + length;
                        while (true) {
                            int i5 = i4;
                            i4--;
                            if (i5 == first) {
                                break;
                            }
                            int i6 = ((k[i4] >>> shift) & 255) ^ signMask;
                            count[i6] = count[i6] + 1;
                        }
                        int lastUsed = -1;
                        int p = first;
                        for (int i7 = 0; i7 < 256; i7++) {
                            if (count[i7] != 0) {
                                lastUsed = i7;
                            }
                            int i8 = p + count[i7];
                            p = i8;
                            pos[i7] = i8;
                        }
                        int end = (first + length) - count[lastUsed];
                        int i9 = first;
                        while (i9 <= end) {
                            int t = a[i9];
                            int u = b[i9];
                            int c = ((k[i9] >>> shift) & 255) ^ signMask;
                            if (i9 < end) {
                                while (true) {
                                    int i10 = c;
                                    int d = pos[i10] - 1;
                                    pos[i10] = d;
                                    if (d <= i9) {
                                        break;
                                    }
                                    c = ((k[d] >>> shift) & 255) ^ signMask;
                                    int z = t;
                                    int w = u;
                                    t = a[d];
                                    u = b[d];
                                    a[d] = z;
                                    b[d] = w;
                                }
                                a[i9] = t;
                                b[i9] = u;
                            }
                            if (level < 7 && count[c] > 1) {
                                if (count[c] < 1024) {
                                    quickSort(a, b, i9, i9 + count[c]);
                                } else {
                                    queueSize.incrementAndGet();
                                    queue.add(new Segment(i9, count[c], level + 1));
                                }
                            }
                            i9 += count[c];
                            count[c] = 0;
                        }
                        queueSize.decrementAndGet();
                    }
                });
            }
            Throwable problem = null;
            int i2 = numberOfThreads;
            while (true) {
                int i3 = i2;
                i2--;
                if (i3 == 0) {
                    break;
                }
                try {
                    executorCompletionService.take().get();
                } catch (Exception e) {
                    problem = e.getCause();
                }
            }
            if (problem != null) {
                if (!(problem instanceof RuntimeException)) {
                    throw new RuntimeException(problem);
                }
            }
        }
    }

    public static void parallelRadixSort(int[] a, int[] b) {
        ensureSameLength(a, b);
        parallelRadixSort(a, b, 0, a.length);
    }

    private static void insertionSortIndirect(int[] perm, int[] a, int[] b, int from, int to) {
        int i = from;
        while (true) {
            i++;
            if (i < to) {
                int t = perm[i];
                int j = i;
                int i2 = perm[j - 1];
                while (true) {
                    int u = i2;
                    if (a[t] < a[u] || (a[t] == a[u] && b[t] < b[u])) {
                        perm[j] = u;
                        if (from != j - 1) {
                            j--;
                            i2 = perm[j - 1];
                        } else {
                            j--;
                            break;
                        }
                    }
                }
                perm[j] = t;
            } else {
                return;
            }
        }
    }

    public static void radixSortIndirect(int[] perm, int[] a, int[] b, boolean stable) {
        ensureSameLength(a, b);
        radixSortIndirect(perm, a, b, 0, a.length, stable);
    }

    public static void radixSortIndirect(int[] perm, int[] a, int[] b, int from, int to, boolean stable) {
        if (to - from < 1024) {
            insertionSortIndirect(perm, a, b, from, to);
            return;
        }
        int[] offsetStack = new int[1786];
        int[] lengthStack = new int[1786];
        int[] levelStack = new int[1786];
        offsetStack[0] = from;
        lengthStack[0] = to - from;
        int stackPos = 0 + 1;
        levelStack[0] = 0;
        int[] count = new int[256];
        int[] pos = new int[256];
        int[] support = stable ? new int[perm.length] : null;
        while (stackPos > 0) {
            stackPos--;
            int first = offsetStack[stackPos];
            int length = lengthStack[stackPos];
            int level = levelStack[stackPos];
            int signMask = level % 4 == 0 ? 128 : 0;
            int[] k = level < 4 ? a : b;
            int shift = (3 - (level % 4)) * 8;
            int i = first + length;
            while (true) {
                int i2 = i;
                i--;
                if (i2 == first) {
                    break;
                }
                int i3 = ((k[perm[i]] >>> shift) & 255) ^ signMask;
                count[i3] = count[i3] + 1;
            }
            int lastUsed = -1;
            int p = stable ? 0 : first;
            for (int i4 = 0; i4 < 256; i4++) {
                if (count[i4] != 0) {
                    lastUsed = i4;
                }
                int i5 = p + count[i4];
                p = i5;
                pos[i4] = i5;
            }
            if (stable) {
                int i6 = first + length;
                while (true) {
                    int i7 = i6;
                    i6--;
                    if (i7 == first) {
                        break;
                    }
                    int i8 = ((k[perm[i6]] >>> shift) & 255) ^ signMask;
                    int i9 = pos[i8] - 1;
                    pos[i8] = i9;
                    support[i9] = perm[i6];
                }
                System.arraycopy(support, 0, perm, first, length);
                int p2 = first;
                for (int i10 = 0; i10 < 256; i10++) {
                    if (level < 7 && count[i10] > 1) {
                        if (count[i10] < 1024) {
                            insertionSortIndirect(perm, a, b, p2, p2 + count[i10]);
                        } else {
                            offsetStack[stackPos] = p2;
                            lengthStack[stackPos] = count[i10];
                            int i11 = stackPos;
                            stackPos++;
                            levelStack[i11] = level + 1;
                        }
                    }
                    p2 += count[i10];
                }
                java.util.Arrays.fill(count, 0);
            } else {
                int end = (first + length) - count[lastUsed];
                int i12 = first;
                while (i12 <= end) {
                    int t = perm[i12];
                    int c = ((k[t] >>> shift) & 255) ^ signMask;
                    if (i12 < end) {
                        while (true) {
                            int i13 = c;
                            int d = pos[i13] - 1;
                            pos[i13] = d;
                            if (d <= i12) {
                                break;
                            }
                            int z = t;
                            t = perm[d];
                            perm[d] = z;
                            c = ((k[t] >>> shift) & 255) ^ signMask;
                        }
                        perm[i12] = t;
                    }
                    if (level < 7 && count[c] > 1) {
                        if (count[c] < 1024) {
                            insertionSortIndirect(perm, a, b, i12, i12 + count[c]);
                        } else {
                            offsetStack[stackPos] = i12;
                            lengthStack[stackPos] = count[c];
                            int i14 = stackPos;
                            stackPos++;
                            levelStack[i14] = level + 1;
                        }
                    }
                    i12 += count[c];
                    count[c] = 0;
                }
            }
        }
    }

    private static void selectionSort(int[][] a, int from, int to, int level) {
        int layers = a.length;
        int firstLayer = level / 4;
        for (int i = from; i < to - 1; i++) {
            int m = i;
            for (int j = i + 1; j < to; j++) {
                int p = firstLayer;
                while (true) {
                    if (p >= layers) {
                        break;
                    } else if (a[p][j] < a[p][m]) {
                        m = j;
                        break;
                    } else if (a[p][j] > a[p][m]) {
                        break;
                    } else {
                        p++;
                    }
                }
            }
            if (m != i) {
                int p2 = layers;
                while (true) {
                    int i2 = p2;
                    p2--;
                    if (i2 != 0) {
                        int u = a[p2][i];
                        a[p2][i] = a[p2][m];
                        a[p2][m] = u;
                    }
                }
            }
        }
    }

    public static void radixSort(int[][] a) {
        radixSort(a, 0, a[0].length);
    }

    /* JADX WARN: Code restructure failed: missing block: B:39:0x018d, code lost:
        if (r27 < r0) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0190, code lost:
        r1 = r28;
        r2 = r0[r1] - 1;
        r0[r1] = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x019f, code lost:
        if (r2 <= r27) goto L73;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x01a2, code lost:
        r28 = ((r0[r2] >>> r0) & 255) ^ r22;
        r30 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x01b6, code lost:
        r0 = r30;
        r30 = r30 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x01bb, code lost:
        if (r0 == 0) goto L74;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x01be, code lost:
        r0 = r0[r30];
        r0[r30] = r6[r30][r2];
        r6[r30][r2] = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x01e0, code lost:
        r30 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x01e3, code lost:
        r0 = r30;
        r30 = r30 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x01e8, code lost:
        if (r0 == 0) goto L75;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x01eb, code lost:
        r6[r30][r27] = r0[r30];
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x01fe, code lost:
        if (r0 >= r0) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0207, code lost:
        if (r0[r28] <= 1) goto L70;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0212, code lost:
        if (r0[r28] >= 1024) goto L57;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0215, code lost:
        selectionSort(r6, r27, r27 + r0[r28], r0 + 1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x022a, code lost:
        r0[r12] = r27;
        r0[r12] = r0[r28];
        r1 = r12;
        r12 = r12 + 1;
        r0[r1] = r0 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0247, code lost:
        r27 = r27 + r0[r28];
        r0[r28] = 0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void radixSort(int[][] r6, int r7, int r8) {
        /*
            Method dump skipped, instructions count: 606
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.viaversion.viaversion.libs.fastutil.ints.IntArrays.radixSort(int[][], int, int):void");
    }

    public static int[] shuffle(int[] a, int from, int to, Random random) {
        int i = to - from;
        while (true) {
            int i2 = i;
            i--;
            if (i2 != 0) {
                int p = random.nextInt(i + 1);
                int t = a[from + i];
                a[from + i] = a[from + p];
                a[from + p] = t;
            } else {
                return a;
            }
        }
    }

    public static int[] shuffle(int[] a, Random random) {
        int i = a.length;
        while (true) {
            int i2 = i;
            i--;
            if (i2 != 0) {
                int p = random.nextInt(i + 1);
                int t = a[i];
                a[i] = a[p];
                a[p] = t;
            } else {
                return a;
            }
        }
    }

    public static int[] reverse(int[] a) {
        int length = a.length;
        int i = length / 2;
        while (true) {
            int i2 = i;
            i--;
            if (i2 != 0) {
                int t = a[(length - i) - 1];
                a[(length - i) - 1] = a[i];
                a[i] = t;
            } else {
                return a;
            }
        }
    }

    public static int[] reverse(int[] a, int from, int to) {
        int length = to - from;
        int i = length / 2;
        while (true) {
            int i2 = i;
            i--;
            if (i2 != 0) {
                int t = a[((from + length) - i) - 1];
                a[((from + length) - i) - 1] = a[from + i];
                a[from + i] = t;
            } else {
                return a;
            }
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntArrays$ArrayHashStrategy.class */
    private static final class ArrayHashStrategy implements Hash.Strategy<int[]>, Serializable {
        private static final long serialVersionUID = -7046029254386353129L;

        private ArrayHashStrategy() {
        }

        public int hashCode(int[] o) {
            return java.util.Arrays.hashCode(o);
        }

        public boolean equals(int[] a, int[] b) {
            return java.util.Arrays.equals(a, b);
        }
    }
}
