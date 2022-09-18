package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Arrays;
import com.viaversion.viaversion.libs.fastutil.Hash;
import com.viaversion.viaversion.libs.fastutil.ints.IntArrays;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Comparator;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectArrays.class */
public final class ObjectArrays {
    private static final int QUICKSORT_NO_REC = 16;
    private static final int PARALLEL_QUICKSORT_NO_FORK = 8192;
    private static final int QUICKSORT_MEDIAN_OF_9 = 128;
    private static final int MERGESORT_NO_REC = 16;
    public static final Object[] EMPTY_ARRAY = new Object[0];
    public static final Object[] DEFAULT_EMPTY_ARRAY = new Object[0];
    public static final Hash.Strategy HASH_STRATEGY = new ArrayHashStrategy();

    private ObjectArrays() {
    }

    private static <K> K[] newArray(K[] prototype, int length) {
        Class<?> klass = prototype.getClass();
        if (klass == Object[].class) {
            return length == 0 ? (K[]) EMPTY_ARRAY : (K[]) new Object[length];
        }
        return (K[]) ((Object[]) Array.newInstance(klass.getComponentType(), length));
    }

    public static <K> K[] forceCapacity(K[] array, int length, int preserve) {
        K[] t = (K[]) newArray(array, length);
        System.arraycopy(array, 0, t, 0, preserve);
        return t;
    }

    public static <K> K[] ensureCapacity(K[] array, int length) {
        return (K[]) ensureCapacity(array, length, array.length);
    }

    public static <K> K[] ensureCapacity(K[] array, int length, int preserve) {
        return length > array.length ? (K[]) forceCapacity(array, length, preserve) : array;
    }

    public static <K> K[] grow(K[] array, int length) {
        return (K[]) grow(array, length, array.length);
    }

    public static <K> K[] grow(K[] array, int length, int preserve) {
        if (length > array.length) {
            int newLength = (int) Math.max(Math.min(array.length + (array.length >> 1), 2147483639L), length);
            K[] t = (K[]) newArray(array, newLength);
            System.arraycopy(array, 0, t, 0, preserve);
            return t;
        }
        return array;
    }

    public static <K> K[] trim(K[] array, int length) {
        if (length >= array.length) {
            return array;
        }
        K[] t = (K[]) newArray(array, length);
        System.arraycopy(array, 0, t, 0, length);
        return t;
    }

    public static <K> K[] setLength(K[] array, int length) {
        if (length == array.length) {
            return array;
        }
        if (length < array.length) {
            return (K[]) trim(array, length);
        }
        return (K[]) ensureCapacity(array, length);
    }

    public static <K> K[] copy(K[] array, int offset, int length) {
        ensureOffsetLength(array, offset, length);
        K[] a = (K[]) newArray(array, length);
        System.arraycopy(array, offset, a, 0, length);
        return a;
    }

    public static <K> K[] copy(K[] array) {
        return (K[]) ((Object[]) array.clone());
    }

    @Deprecated
    public static <K> void fill(K[] array, K value) {
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
    public static <K> void fill(K[] array, int from, int to, K value) {
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
    public static <K> boolean equals(K[] a1, K[] a2) {
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
        } while (Objects.equals(a1[i], a2[i]));
        return false;
    }

    public static <K> void ensureFromTo(K[] a, int from, int to) {
        Arrays.ensureFromTo(a.length, from, to);
    }

    public static <K> void ensureOffsetLength(K[] a, int offset, int length) {
        Arrays.ensureOffsetLength(a.length, offset, length);
    }

    public static <K> void ensureSameLength(K[] a, K[] b) {
        if (a.length != b.length) {
            throw new IllegalArgumentException("Array size mismatch: " + a.length + " != " + b.length);
        }
    }

    private static ForkJoinPool getPool() {
        ForkJoinPool current = ForkJoinTask.getPool();
        return current == null ? ForkJoinPool.commonPool() : current;
    }

    public static <K> void swap(K[] x, int a, int b) {
        K t = x[a];
        x[a] = x[b];
        x[b] = t;
    }

    public static <K> void swap(K[] x, int a, int b, int n) {
        int i = 0;
        while (i < n) {
            swap(x, a, b);
            i++;
            a++;
            b++;
        }
    }

    public static <K> int med3(K[] x, int a, int b, int c, Comparator<K> comp) {
        int ab = comp.compare(x[a], x[b]);
        int ac = comp.compare(x[a], x[c]);
        int bc = comp.compare(x[b], x[c]);
        return ab < 0 ? bc < 0 ? b : ac < 0 ? c : a : bc > 0 ? b : ac > 0 ? c : a;
    }

    private static <K> void selectionSort(K[] a, int from, int to, Comparator<K> comp) {
        for (int i = from; i < to - 1; i++) {
            int m = i;
            for (int j = i + 1; j < to; j++) {
                if (comp.compare(a[j], a[m]) < 0) {
                    m = j;
                }
            }
            if (m != i) {
                K u = a[i];
                a[i] = a[m];
                a[m] = u;
            }
        }
    }

    private static <K> void insertionSort(K[] a, int from, int to, Comparator<K> comp) {
        int i = from;
        while (true) {
            i++;
            if (i < to) {
                K t = a[i];
                int j = i;
                K k = a[j - 1];
                while (true) {
                    K u = k;
                    if (comp.compare(t, u) < 0) {
                        a[j] = u;
                        if (from != j - 1) {
                            j--;
                            k = a[j - 1];
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

    public static <K> void quickSort(K[] x, int from, int to, Comparator<K> comp) {
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
        K v = x[med3(x, l, m, n, comp)];
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

    public static <K> void quickSort(K[] x, Comparator<K> comp) {
        quickSort(x, 0, x.length, comp);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectArrays$ForkJoinQuickSortComp.class */
    public static class ForkJoinQuickSortComp<K> extends RecursiveAction {
        private static final long serialVersionUID = 1;
        private final int from;

        /* renamed from: to */
        private final int f151to;

        /* renamed from: x */
        private final K[] f152x;
        private final Comparator<K> comp;

        public ForkJoinQuickSortComp(K[] x, int from, int to, Comparator<K> comp) {
            this.from = from;
            this.f151to = to;
            this.f152x = x;
            this.comp = comp;
        }

        @Override // java.util.concurrent.RecursiveAction
        protected void compute() {
            int comparison;
            int comparison2;
            K[] x = this.f152x;
            int len = this.f151to - this.from;
            if (len < 8192) {
                ObjectArrays.quickSort(x, this.from, this.f151to, this.comp);
                return;
            }
            int m = this.from + (len / 2);
            int l = this.from;
            int n = this.f151to - 1;
            int s = len / 8;
            K v = x[ObjectArrays.med3(x, ObjectArrays.med3(x, l, l + s, l + (2 * s), this.comp), ObjectArrays.med3(x, m - s, m, m + s, this.comp), ObjectArrays.med3(x, n - (2 * s), n - s, n, this.comp), this.comp)];
            int a = this.from;
            int b = a;
            int c = this.f151to - 1;
            int d = c;
            while (true) {
                if (b <= c && (comparison2 = this.comp.compare(x[b], v)) <= 0) {
                    if (comparison2 == 0) {
                        int i = a;
                        a++;
                        ObjectArrays.swap(x, i, b);
                    }
                    b++;
                } else {
                    while (c >= b && (comparison = this.comp.compare(x[c], v)) >= 0) {
                        if (comparison == 0) {
                            int i2 = d;
                            d--;
                            ObjectArrays.swap(x, c, i2);
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
                    ObjectArrays.swap(x, i3, i4);
                }
            }
            int s2 = Math.min(a - this.from, b - a);
            ObjectArrays.swap(x, this.from, b - s2, s2);
            int s3 = Math.min(d - c, (this.f151to - d) - 1);
            ObjectArrays.swap(x, b, this.f151to - s3, s3);
            int s4 = b - a;
            int t = d - c;
            if (s4 > 1 && t > 1) {
                invokeAll(new ForkJoinQuickSortComp(x, this.from, this.from + s4, this.comp), new ForkJoinQuickSortComp(x, this.f151to - t, this.f151to, this.comp));
            } else if (s4 > 1) {
                invokeAll(new ForkJoinTask[]{new ForkJoinQuickSortComp(x, this.from, this.from + s4, this.comp)});
            } else {
                invokeAll(new ForkJoinTask[]{new ForkJoinQuickSortComp(x, this.f151to - t, this.f151to, this.comp)});
            }
        }
    }

    public static <K> void parallelQuickSort(K[] x, int from, int to, Comparator<K> comp) {
        ForkJoinPool pool = getPool();
        if (to - from < 8192 || pool.getParallelism() == 1) {
            quickSort(x, from, to, comp);
        } else {
            pool.invoke(new ForkJoinQuickSortComp(x, from, to, comp));
        }
    }

    public static <K> void parallelQuickSort(K[] x, Comparator<K> comp) {
        parallelQuickSort(x, 0, x.length, comp);
    }

    public static <K> int med3(K[] x, int a, int b, int c) {
        int ab = ((Comparable) x[a]).compareTo(x[b]);
        int ac = ((Comparable) x[a]).compareTo(x[c]);
        int bc = ((Comparable) x[b]).compareTo(x[c]);
        return ab < 0 ? bc < 0 ? b : ac < 0 ? c : a : bc > 0 ? b : ac > 0 ? c : a;
    }

    private static <K> void selectionSort(K[] a, int from, int to) {
        for (int i = from; i < to - 1; i++) {
            int m = i;
            for (int j = i + 1; j < to; j++) {
                if (((Comparable) a[j]).compareTo(a[m]) < 0) {
                    m = j;
                }
            }
            if (m != i) {
                K u = a[i];
                a[i] = a[m];
                a[m] = u;
            }
        }
    }

    private static <K> void insertionSort(K[] a, int from, int to) {
        int i = from;
        while (true) {
            i++;
            if (i < to) {
                K t = a[i];
                int j = i;
                K k = a[j - 1];
                while (true) {
                    K u = k;
                    if (((Comparable) t).compareTo(u) < 0) {
                        a[j] = u;
                        if (from != j - 1) {
                            j--;
                            k = a[j - 1];
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

    public static <K> void quickSort(K[] x, int from, int to) {
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
        K v = x[med3(x, l, m, n)];
        int a = from;
        int b = a;
        int c = to - 1;
        int d = c;
        while (true) {
            if (b <= c && (comparison2 = ((Comparable) x[b]).compareTo(v)) <= 0) {
                if (comparison2 == 0) {
                    int i = a;
                    a++;
                    swap(x, i, b);
                }
                b++;
            } else {
                while (c >= b && (comparison = ((Comparable) x[c]).compareTo(v)) >= 0) {
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

    public static <K> void quickSort(K[] x) {
        quickSort(x, 0, x.length);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectArrays$ForkJoinQuickSort.class */
    public static class ForkJoinQuickSort<K> extends RecursiveAction {
        private static final long serialVersionUID = 1;
        private final int from;

        /* renamed from: to */
        private final int f146to;

        /* renamed from: x */
        private final K[] f147x;

        public ForkJoinQuickSort(K[] x, int from, int to) {
            this.from = from;
            this.f146to = to;
            this.f147x = x;
        }

        @Override // java.util.concurrent.RecursiveAction
        protected void compute() {
            int comparison;
            int comparison2;
            K[] x = this.f147x;
            int len = this.f146to - this.from;
            if (len < 8192) {
                ObjectArrays.quickSort(x, this.from, this.f146to);
                return;
            }
            int m = this.from + (len / 2);
            int l = this.from;
            int n = this.f146to - 1;
            int s = len / 8;
            K v = x[ObjectArrays.med3(x, ObjectArrays.med3(x, l, l + s, l + (2 * s)), ObjectArrays.med3(x, m - s, m, m + s), ObjectArrays.med3(x, n - (2 * s), n - s, n))];
            int a = this.from;
            int b = a;
            int c = this.f146to - 1;
            int d = c;
            while (true) {
                if (b <= c && (comparison2 = ((Comparable) x[b]).compareTo(v)) <= 0) {
                    if (comparison2 == 0) {
                        int i = a;
                        a++;
                        ObjectArrays.swap(x, i, b);
                    }
                    b++;
                } else {
                    while (c >= b && (comparison = ((Comparable) x[c]).compareTo(v)) >= 0) {
                        if (comparison == 0) {
                            int i2 = d;
                            d--;
                            ObjectArrays.swap(x, c, i2);
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
                    ObjectArrays.swap(x, i3, i4);
                }
            }
            int s2 = Math.min(a - this.from, b - a);
            ObjectArrays.swap(x, this.from, b - s2, s2);
            int s3 = Math.min(d - c, (this.f146to - d) - 1);
            ObjectArrays.swap(x, b, this.f146to - s3, s3);
            int s4 = b - a;
            int t = d - c;
            if (s4 > 1 && t > 1) {
                invokeAll(new ForkJoinQuickSort(x, this.from, this.from + s4), new ForkJoinQuickSort(x, this.f146to - t, this.f146to));
            } else if (s4 > 1) {
                invokeAll(new ForkJoinTask[]{new ForkJoinQuickSort(x, this.from, this.from + s4)});
            } else {
                invokeAll(new ForkJoinTask[]{new ForkJoinQuickSort(x, this.f146to - t, this.f146to)});
            }
        }
    }

    public static <K> void parallelQuickSort(K[] x, int from, int to) {
        ForkJoinPool pool = getPool();
        if (to - from < 8192 || pool.getParallelism() == 1) {
            quickSort(x, from, to);
        } else {
            pool.invoke(new ForkJoinQuickSort(x, from, to));
        }
    }

    public static <K> void parallelQuickSort(K[] x) {
        parallelQuickSort(x, 0, x.length);
    }

    public static <K> int med3Indirect(int[] perm, K[] x, int a, int b, int c) {
        K aa = x[perm[a]];
        K bb = x[perm[b]];
        K cc = x[perm[c]];
        int ab = ((Comparable) aa).compareTo(bb);
        int ac = ((Comparable) aa).compareTo(cc);
        int bc = ((Comparable) bb).compareTo(cc);
        return ab < 0 ? bc < 0 ? b : ac < 0 ? c : a : bc > 0 ? b : ac > 0 ? c : a;
    }

    private static <K> void insertionSortIndirect(int[] perm, K[] a, int from, int to) {
        int i = from;
        while (true) {
            i++;
            if (i < to) {
                int t = perm[i];
                int j = i;
                int i2 = perm[j - 1];
                while (true) {
                    int u = i2;
                    if (((Comparable) a[t]).compareTo(a[u]) < 0) {
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

    public static <K> void quickSortIndirect(int[] perm, K[] x, int from, int to) {
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
        K v = x[perm[med3Indirect(perm, x, l, m, n)]];
        int a = from;
        int b = a;
        int c = to - 1;
        int d = c;
        while (true) {
            if (b <= c && (comparison2 = ((Comparable) x[perm[b]]).compareTo(v)) <= 0) {
                if (comparison2 == 0) {
                    int i = a;
                    a++;
                    IntArrays.swap(perm, i, b);
                }
                b++;
            } else {
                while (c >= b && (comparison = ((Comparable) x[perm[c]]).compareTo(v)) >= 0) {
                    if (comparison == 0) {
                        int i2 = d;
                        d--;
                        IntArrays.swap(perm, c, i2);
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
                IntArrays.swap(perm, i3, i4);
            }
        }
        int s2 = Math.min(a - from, b - a);
        IntArrays.swap(perm, from, b - s2, s2);
        int s3 = Math.min(d - c, (to - d) - 1);
        IntArrays.swap(perm, b, to - s3, s3);
        int s4 = b - a;
        if (s4 > 1) {
            quickSortIndirect(perm, x, from, from + s4);
        }
        int s5 = d - c;
        if (s5 > 1) {
            quickSortIndirect(perm, x, to - s5, to);
        }
    }

    public static <K> void quickSortIndirect(int[] perm, K[] x) {
        quickSortIndirect(perm, x, 0, x.length);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectArrays$ForkJoinQuickSortIndirect.class */
    public static class ForkJoinQuickSortIndirect<K> extends RecursiveAction {
        private static final long serialVersionUID = 1;
        private final int from;

        /* renamed from: to */
        private final int f153to;
        private final int[] perm;

        /* renamed from: x */
        private final K[] f154x;

        public ForkJoinQuickSortIndirect(int[] perm, K[] x, int from, int to) {
            this.from = from;
            this.f153to = to;
            this.f154x = x;
            this.perm = perm;
        }

        @Override // java.util.concurrent.RecursiveAction
        protected void compute() {
            int comparison;
            int comparison2;
            K[] x = this.f154x;
            int len = this.f153to - this.from;
            if (len < 8192) {
                ObjectArrays.quickSortIndirect(this.perm, x, this.from, this.f153to);
                return;
            }
            int m = this.from + (len / 2);
            int l = this.from;
            int n = this.f153to - 1;
            int s = len / 8;
            K v = x[this.perm[ObjectArrays.med3Indirect(this.perm, x, ObjectArrays.med3Indirect(this.perm, x, l, l + s, l + (2 * s)), ObjectArrays.med3Indirect(this.perm, x, m - s, m, m + s), ObjectArrays.med3Indirect(this.perm, x, n - (2 * s), n - s, n))]];
            int a = this.from;
            int b = a;
            int c = this.f153to - 1;
            int d = c;
            while (true) {
                if (b <= c && (comparison2 = ((Comparable) x[this.perm[b]]).compareTo(v)) <= 0) {
                    if (comparison2 == 0) {
                        int i = a;
                        a++;
                        IntArrays.swap(this.perm, i, b);
                    }
                    b++;
                } else {
                    while (c >= b && (comparison = ((Comparable) x[this.perm[c]]).compareTo(v)) >= 0) {
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
            int s3 = Math.min(d - c, (this.f153to - d) - 1);
            IntArrays.swap(this.perm, b, this.f153to - s3, s3);
            int s4 = b - a;
            int t = d - c;
            if (s4 > 1 && t > 1) {
                invokeAll(new ForkJoinQuickSortIndirect(this.perm, x, this.from, this.from + s4), new ForkJoinQuickSortIndirect(this.perm, x, this.f153to - t, this.f153to));
            } else if (s4 > 1) {
                invokeAll(new ForkJoinTask[]{new ForkJoinQuickSortIndirect(this.perm, x, this.from, this.from + s4)});
            } else {
                invokeAll(new ForkJoinTask[]{new ForkJoinQuickSortIndirect(this.perm, x, this.f153to - t, this.f153to)});
            }
        }
    }

    public static <K> void parallelQuickSortIndirect(int[] perm, K[] x, int from, int to) {
        ForkJoinPool pool = getPool();
        if (to - from < 8192 || pool.getParallelism() == 1) {
            quickSortIndirect(perm, x, from, to);
        } else {
            pool.invoke(new ForkJoinQuickSortIndirect(perm, x, from, to));
        }
    }

    public static <K> void parallelQuickSortIndirect(int[] perm, K[] x) {
        parallelQuickSortIndirect(perm, x, 0, x.length);
    }

    public static <K> void stabilize(int[] perm, K[] x, int from, int to) {
        int curr = from;
        for (int i = from + 1; i < to; i++) {
            if (x[perm[i]] != x[perm[curr]]) {
                if (i - curr > 1) {
                    IntArrays.parallelQuickSort(perm, curr, i);
                }
                curr = i;
            }
        }
        if (to - curr > 1) {
            IntArrays.parallelQuickSort(perm, curr, to);
        }
    }

    public static <K> void stabilize(int[] perm, K[] x) {
        stabilize(perm, x, 0, perm.length);
    }

    public static <K> int med3(K[] x, K[] y, int a, int b, int c) {
        int i;
        int i2;
        int i3;
        int t = ((Comparable) x[a]).compareTo(x[b]);
        if (t == 0) {
            i = ((Comparable) y[a]).compareTo(y[b]);
        } else {
            i = t;
        }
        int ab = i;
        int t2 = ((Comparable) x[a]).compareTo(x[c]);
        if (t2 == 0) {
            i2 = ((Comparable) y[a]).compareTo(y[c]);
        } else {
            i2 = t2;
        }
        int ac = i2;
        int t3 = ((Comparable) x[b]).compareTo(x[c]);
        if (t3 == 0) {
            i3 = ((Comparable) y[b]).compareTo(y[c]);
        } else {
            i3 = t3;
        }
        int bc = i3;
        return ab < 0 ? bc < 0 ? b : ac < 0 ? c : a : bc > 0 ? b : ac > 0 ? c : a;
    }

    public static <K> void swap(K[] x, K[] y, int a, int b) {
        K t = x[a];
        K u = y[a];
        x[a] = x[b];
        y[a] = y[b];
        x[b] = t;
        y[b] = u;
    }

    public static <K> void swap(K[] x, K[] y, int a, int b, int n) {
        int i = 0;
        while (i < n) {
            swap(x, y, a, b);
            i++;
            a++;
            b++;
        }
    }

    private static <K> void selectionSort(K[] a, K[] b, int from, int to) {
        for (int i = from; i < to - 1; i++) {
            int m = i;
            for (int j = i + 1; j < to; j++) {
                int u = ((Comparable) a[j]).compareTo(a[m]);
                if (u < 0 || (u == 0 && ((Comparable) b[j]).compareTo(b[m]) < 0)) {
                    m = j;
                }
            }
            if (m != i) {
                K t = a[i];
                a[i] = a[m];
                a[m] = t;
                K t2 = b[i];
                b[i] = b[m];
                b[m] = t2;
            }
        }
    }

    public static <K> void quickSort(K[] x, K[] y, int from, int to) {
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
        K v = x[m2];
        K w = y[m2];
        int a = from;
        int b = a;
        int c = to - 1;
        int d = c;
        while (true) {
            if (b <= c) {
                int t = ((Comparable) x[b]).compareTo(v);
                if (t == 0) {
                    i2 = ((Comparable) y[b]).compareTo(w);
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
                int t2 = ((Comparable) x[c]).compareTo(v);
                if (t2 == 0) {
                    i = ((Comparable) y[c]).compareTo(w);
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

    public static <K> void quickSort(K[] x, K[] y) {
        ensureSameLength(x, y);
        quickSort(x, y, 0, x.length);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectArrays$ForkJoinQuickSort2.class */
    public static class ForkJoinQuickSort2<K> extends RecursiveAction {
        private static final long serialVersionUID = 1;
        private final int from;

        /* renamed from: to */
        private final int f148to;

        /* renamed from: x */
        private final K[] f149x;

        /* renamed from: y */
        private final K[] f150y;

        public ForkJoinQuickSort2(K[] x, K[] y, int from, int to) {
            this.from = from;
            this.f148to = to;
            this.f149x = x;
            this.f150y = y;
        }

        @Override // java.util.concurrent.RecursiveAction
        protected void compute() {
            int i;
            int i2;
            K[] x = this.f149x;
            K[] y = this.f150y;
            int len = this.f148to - this.from;
            if (len < 8192) {
                ObjectArrays.quickSort(x, y, this.from, this.f148to);
                return;
            }
            int m = this.from + (len / 2);
            int l = this.from;
            int n = this.f148to - 1;
            int s = len / 8;
            int m2 = ObjectArrays.med3(x, y, ObjectArrays.med3(x, y, l, l + s, l + (2 * s)), ObjectArrays.med3(x, y, m - s, m, m + s), ObjectArrays.med3(x, y, n - (2 * s), n - s, n));
            K v = x[m2];
            K w = y[m2];
            int a = this.from;
            int b = a;
            int c = this.f148to - 1;
            int d = c;
            while (true) {
                if (b <= c) {
                    int t = ((Comparable) x[b]).compareTo(v);
                    if (t == 0) {
                        i2 = ((Comparable) y[b]).compareTo(w);
                    } else {
                        i2 = t;
                    }
                    int comparison = i2;
                    if (i2 <= 0) {
                        if (comparison == 0) {
                            int i3 = a;
                            a++;
                            ObjectArrays.swap(x, y, i3, b);
                        }
                        b++;
                    }
                }
                while (c >= b) {
                    int t2 = ((Comparable) x[c]).compareTo(v);
                    if (t2 == 0) {
                        i = ((Comparable) y[c]).compareTo(w);
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
                        ObjectArrays.swap(x, y, c, i4);
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
                ObjectArrays.swap(x, y, i5, i6);
            }
            int s2 = Math.min(a - this.from, b - a);
            ObjectArrays.swap(x, y, this.from, b - s2, s2);
            int s3 = Math.min(d - c, (this.f148to - d) - 1);
            ObjectArrays.swap(x, y, b, this.f148to - s3, s3);
            int s4 = b - a;
            int t3 = d - c;
            if (s4 > 1 && t3 > 1) {
                invokeAll(new ForkJoinQuickSort2(x, y, this.from, this.from + s4), new ForkJoinQuickSort2(x, y, this.f148to - t3, this.f148to));
            } else if (s4 > 1) {
                invokeAll(new ForkJoinTask[]{new ForkJoinQuickSort2(x, y, this.from, this.from + s4)});
            } else {
                invokeAll(new ForkJoinTask[]{new ForkJoinQuickSort2(x, y, this.f148to - t3, this.f148to)});
            }
        }
    }

    public static <K> void parallelQuickSort(K[] x, K[] y, int from, int to) {
        ForkJoinPool pool = getPool();
        if (to - from < 8192 || pool.getParallelism() == 1) {
            quickSort(x, y, from, to);
        } else {
            pool.invoke(new ForkJoinQuickSort2(x, y, from, to));
        }
    }

    public static <K> void parallelQuickSort(K[] x, K[] y) {
        ensureSameLength(x, y);
        parallelQuickSort(x, y, 0, x.length);
    }

    public static <K> void unstableSort(K[] a, int from, int to) {
        quickSort(a, from, to);
    }

    public static <K> void unstableSort(K[] a) {
        unstableSort(a, 0, a.length);
    }

    public static <K> void unstableSort(K[] a, int from, int to, Comparator<K> comp) {
        quickSort(a, from, to, comp);
    }

    public static <K> void unstableSort(K[] a, Comparator<K> comp) {
        unstableSort(a, 0, a.length, comp);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v27, types: [java.lang.Object[]] */
    public static <K> void mergeSort(K[] a, int from, int to, K[] supp) {
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
        if (((Comparable) supp[mid - 1]).compareTo(supp[mid]) <= 0) {
            System.arraycopy(supp, from, a, from, len);
            return;
        }
        int p = from;
        int q = mid;
        for (int i = from; i < to; i++) {
            if (q >= to || (p < mid && ((Comparable) supp[p]).compareTo(supp[q]) <= 0)) {
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

    public static <K> void mergeSort(K[] a, int from, int to) {
        mergeSort(a, from, to, (Object[]) null);
    }

    public static <K> void mergeSort(K[] a) {
        mergeSort(a, 0, a.length);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v23, types: [java.lang.Object[]] */
    public static <K> void mergeSort(K[] a, int from, int to, Comparator<K> comp, K[] supp) {
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

    public static <K> void mergeSort(K[] a, int from, int to, Comparator<K> comp) {
        mergeSort(a, from, to, comp, null);
    }

    public static <K> void mergeSort(K[] a, Comparator<K> comp) {
        mergeSort(a, 0, a.length, comp);
    }

    public static <K> void stableSort(K[] a, int from, int to) {
        java.util.Arrays.sort(a, from, to);
    }

    public static <K> void stableSort(K[] a) {
        stableSort(a, 0, a.length);
    }

    public static <K> void stableSort(K[] a, int from, int to, Comparator<K> comp) {
        java.util.Arrays.sort(a, from, to, comp);
    }

    public static <K> void stableSort(K[] a, Comparator<K> comp) {
        stableSort(a, 0, a.length, comp);
    }

    public static <K> int binarySearch(K[] a, int from, int to, K key) {
        int to2 = to - 1;
        while (from <= to2) {
            int mid = (from + to2) >>> 1;
            K midVal = a[mid];
            int cmp = ((Comparable) midVal).compareTo(key);
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

    public static <K> int binarySearch(K[] a, K key) {
        return binarySearch(a, 0, a.length, key);
    }

    public static <K> int binarySearch(K[] a, int from, int to, K key, Comparator<K> c) {
        int to2 = to - 1;
        while (from <= to2) {
            int mid = (from + to2) >>> 1;
            K midVal = a[mid];
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

    public static <K> int binarySearch(K[] a, K key, Comparator<K> c) {
        return binarySearch(a, 0, a.length, key, c);
    }

    public static <K> K[] shuffle(K[] a, int from, int to, Random random) {
        int i = to - from;
        while (true) {
            int i2 = i;
            i--;
            if (i2 != 0) {
                int p = random.nextInt(i + 1);
                K t = a[from + i];
                a[from + i] = a[from + p];
                a[from + p] = t;
            } else {
                return a;
            }
        }
    }

    public static <K> K[] shuffle(K[] a, Random random) {
        int i = a.length;
        while (true) {
            int i2 = i;
            i--;
            if (i2 != 0) {
                int p = random.nextInt(i + 1);
                K t = a[i];
                a[i] = a[p];
                a[p] = t;
            } else {
                return a;
            }
        }
    }

    public static <K> K[] reverse(K[] a) {
        int length = a.length;
        int i = length / 2;
        while (true) {
            int i2 = i;
            i--;
            if (i2 != 0) {
                K t = a[(length - i) - 1];
                a[(length - i) - 1] = a[i];
                a[i] = t;
            } else {
                return a;
            }
        }
    }

    public static <K> K[] reverse(K[] a, int from, int to) {
        int length = to - from;
        int i = length / 2;
        while (true) {
            int i2 = i;
            i--;
            if (i2 != 0) {
                K t = a[((from + length) - i) - 1];
                a[((from + length) - i) - 1] = a[from + i];
                a[from + i] = t;
            } else {
                return a;
            }
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectArrays$ArrayHashStrategy.class */
    private static final class ArrayHashStrategy<K> implements Hash.Strategy<K[]>, Serializable {
        private static final long serialVersionUID = -7046029254386353129L;

        private ArrayHashStrategy() {
        }

        public int hashCode(K[] o) {
            return java.util.Arrays.hashCode(o);
        }

        public boolean equals(K[] a, K[] b) {
            return java.util.Arrays.equals(a, b);
        }
    }
}
