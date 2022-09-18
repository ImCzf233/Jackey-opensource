package com.viaversion.viaversion.libs.fastutil;

import com.viaversion.viaversion.libs.fastutil.ints.IntComparator;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/Arrays.class */
public class Arrays {
    public static final int MAX_ARRAY_SIZE = 2147483639;
    private static final int MERGESORT_NO_REC = 16;
    private static final int QUICKSORT_NO_REC = 16;
    private static final int PARALLEL_QUICKSORT_NO_FORK = 8192;
    private static final int QUICKSORT_MEDIAN_OF_9 = 128;

    private Arrays() {
    }

    public static void ensureFromTo(int arrayLength, int from, int to) {
        if (from < 0) {
            throw new ArrayIndexOutOfBoundsException("Start index (" + from + ") is negative");
        }
        if (from > to) {
            throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
        }
        if (to <= arrayLength) {
            return;
        }
        throw new ArrayIndexOutOfBoundsException("End index (" + to + ") is greater than array length (" + arrayLength + ")");
    }

    public static void ensureOffsetLength(int arrayLength, int offset, int length) {
        if (offset < 0) {
            throw new ArrayIndexOutOfBoundsException("Offset (" + offset + ") is negative");
        }
        if (length < 0) {
            throw new IllegalArgumentException("Length (" + length + ") is negative");
        }
        if (offset + length <= arrayLength) {
            return;
        }
        throw new ArrayIndexOutOfBoundsException("Last index (" + (offset + length) + ") is greater than array length (" + arrayLength + ")");
    }

    private static void inPlaceMerge(int from, int mid, int to, IntComparator comp, Swapper swapper) {
        int secondCut;
        int firstCut;
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
        int first2 = firstCut;
        int last2 = secondCut;
        if (mid != first2 && mid != last2) {
            int first1 = first2;
            int last1 = mid;
            while (true) {
                last1--;
                if (first1 >= last1) {
                    break;
                }
                int i = first1;
                first1++;
                swapper.swap(i, last1);
            }
            int first12 = mid;
            int last12 = last2;
            while (true) {
                last12--;
                if (first12 >= last12) {
                    break;
                }
                int i2 = first12;
                first12++;
                swapper.swap(i2, last12);
            }
            int first13 = first2;
            int last13 = last2;
            while (true) {
                last13--;
                if (first13 >= last13) {
                    break;
                }
                int i3 = first13;
                first13++;
                swapper.swap(i3, last13);
            }
        }
        int mid2 = firstCut + (secondCut - mid);
        inPlaceMerge(from, firstCut, mid2, comp, swapper);
        inPlaceMerge(mid2, secondCut, to, comp, swapper);
    }

    private static int lowerBound(int from, int to, int pos, IntComparator comp) {
        int i = to - from;
        while (true) {
            int len = i;
            if (len > 0) {
                int half = len / 2;
                int middle = from + half;
                if (comp.compare(middle, pos) < 0) {
                    from = middle + 1;
                    i = len - (half + 1);
                } else {
                    i = half;
                }
            } else {
                return from;
            }
        }
    }

    private static int upperBound(int from, int mid, int pos, IntComparator comp) {
        int i = mid - from;
        while (true) {
            int len = i;
            if (len > 0) {
                int half = len / 2;
                int middle = from + half;
                if (comp.compare(pos, middle) < 0) {
                    i = half;
                } else {
                    from = middle + 1;
                    i = len - (half + 1);
                }
            } else {
                return from;
            }
        }
    }

    public static int med3(int a, int b, int c, IntComparator comp) {
        int ab = comp.compare(a, b);
        int ac = comp.compare(a, c);
        int bc = comp.compare(b, c);
        return ab < 0 ? bc < 0 ? b : ac < 0 ? c : a : bc > 0 ? b : ac > 0 ? c : a;
    }

    private static ForkJoinPool getPool() {
        ForkJoinPool current = ForkJoinTask.getPool();
        return current == null ? ForkJoinPool.commonPool() : current;
    }

    public static void mergeSort(int from, int to, IntComparator c, Swapper swapper) {
        int length = to - from;
        if (length < 16) {
            for (int i = from; i < to; i++) {
                for (int j = i; j > from && c.compare(j - 1, j) > 0; j--) {
                    swapper.swap(j, j - 1);
                }
            }
            return;
        }
        int mid = (from + to) >>> 1;
        mergeSort(from, mid, c, swapper);
        mergeSort(mid, to, c, swapper);
        if (c.compare(mid - 1, mid) <= 0) {
            return;
        }
        inPlaceMerge(from, mid, to, c, swapper);
    }

    protected static void swap(Swapper swapper, int a, int b, int n) {
        int i = 0;
        while (i < n) {
            swapper.swap(a, b);
            i++;
            a++;
            b++;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/Arrays$ForkJoinGenericQuickSort.class */
    protected static class ForkJoinGenericQuickSort extends RecursiveAction {
        private static final long serialVersionUID = 1;
        private final int from;

        /* renamed from: to */
        private final int f62to;
        private final IntComparator comp;
        private final Swapper swapper;

        public ForkJoinGenericQuickSort(int from, int to, IntComparator comp, Swapper swapper) {
            this.from = from;
            this.f62to = to;
            this.comp = comp;
            this.swapper = swapper;
        }

        @Override // java.util.concurrent.RecursiveAction
        protected void compute() {
            int comparison;
            int comparison2;
            int len = this.f62to - this.from;
            if (len < 8192) {
                Arrays.quickSort(this.from, this.f62to, this.comp, this.swapper);
                return;
            }
            int m = this.from + (len / 2);
            int l = this.from;
            int n = this.f62to - 1;
            int s = len / 8;
            int m2 = Arrays.med3(Arrays.med3(l, l + s, l + (2 * s), this.comp), Arrays.med3(m - s, m, m + s, this.comp), Arrays.med3(n - (2 * s), n - s, n, this.comp), this.comp);
            int a = this.from;
            int b = a;
            int c = this.f62to - 1;
            int d = c;
            while (true) {
                if (b <= c && (comparison2 = this.comp.compare(b, m2)) <= 0) {
                    if (comparison2 == 0) {
                        if (a == m2) {
                            m2 = b;
                        } else if (b == m2) {
                            m2 = a;
                        }
                        int i = a;
                        a++;
                        this.swapper.swap(i, b);
                    }
                    b++;
                } else {
                    while (c >= b && (comparison = this.comp.compare(c, m2)) >= 0) {
                        if (comparison == 0) {
                            if (c == m2) {
                                m2 = d;
                            } else if (d == m2) {
                                m2 = c;
                            }
                            int i2 = d;
                            d--;
                            this.swapper.swap(c, i2);
                        }
                        c--;
                    }
                    if (b > c) {
                        break;
                    }
                    if (b == m2) {
                        m2 = d;
                    } else if (c == m2) {
                        m2 = c;
                    }
                    int i3 = b;
                    b++;
                    int i4 = c;
                    c--;
                    this.swapper.swap(i3, i4);
                }
            }
            int s2 = Math.min(a - this.from, b - a);
            Arrays.swap(this.swapper, this.from, b - s2, s2);
            int s3 = Math.min(d - c, (this.f62to - d) - 1);
            Arrays.swap(this.swapper, b, this.f62to - s3, s3);
            int s4 = b - a;
            int t = d - c;
            if (s4 <= 1 || t <= 1) {
                if (s4 > 1) {
                    invokeAll(new ForkJoinTask[]{new ForkJoinGenericQuickSort(this.from, this.from + s4, this.comp, this.swapper)});
                    return;
                } else {
                    invokeAll(new ForkJoinTask[]{new ForkJoinGenericQuickSort(this.f62to - t, this.f62to, this.comp, this.swapper)});
                    return;
                }
            }
            invokeAll(new ForkJoinGenericQuickSort(this.from, this.from + s4, this.comp, this.swapper), new ForkJoinGenericQuickSort(this.f62to - t, this.f62to, this.comp, this.swapper));
        }
    }

    public static void parallelQuickSort(int from, int to, IntComparator comp, Swapper swapper) {
        ForkJoinPool pool = getPool();
        if (to - from >= 8192 && pool.getParallelism() != 1) {
            pool.invoke(new ForkJoinGenericQuickSort(from, to, comp, swapper));
        } else {
            quickSort(from, to, comp, swapper);
        }
    }

    public static void quickSort(int from, int to, IntComparator comp, Swapper swapper) {
        int comparison;
        int comparison2;
        int len = to - from;
        if (len < 16) {
            for (int i = from; i < to; i++) {
                for (int j = i; j > from && comp.compare(j - 1, j) > 0; j--) {
                    swapper.swap(j, j - 1);
                }
            }
            return;
        }
        int m = from + (len / 2);
        int l = from;
        int n = to - 1;
        if (len > 128) {
            int s = len / 8;
            l = med3(l, l + s, l + (2 * s), comp);
            m = med3(m - s, m, m + s, comp);
            n = med3(n - (2 * s), n - s, n, comp);
        }
        int m2 = med3(l, m, n, comp);
        int a = from;
        int b = a;
        int c = to - 1;
        int d = c;
        while (true) {
            if (b <= c && (comparison2 = comp.compare(b, m2)) <= 0) {
                if (comparison2 == 0) {
                    if (a == m2) {
                        m2 = b;
                    } else if (b == m2) {
                        m2 = a;
                    }
                    int i2 = a;
                    a++;
                    swapper.swap(i2, b);
                }
                b++;
            } else {
                while (c >= b && (comparison = comp.compare(c, m2)) >= 0) {
                    if (comparison == 0) {
                        if (c == m2) {
                            m2 = d;
                        } else if (d == m2) {
                            m2 = c;
                        }
                        int i3 = d;
                        d--;
                        swapper.swap(c, i3);
                    }
                    c--;
                }
                if (b > c) {
                    break;
                }
                if (b == m2) {
                    m2 = d;
                } else if (c == m2) {
                    m2 = c;
                }
                int i4 = b;
                b++;
                int i5 = c;
                c--;
                swapper.swap(i4, i5);
            }
        }
        int s2 = Math.min(a - from, b - a);
        swap(swapper, from, b - s2, s2);
        int s3 = Math.min(d - c, (to - d) - 1);
        swap(swapper, b, to - s3, s3);
        int s4 = b - a;
        if (s4 > 1) {
            quickSort(from, from + s4, comp, swapper);
        }
        int s5 = d - c;
        if (s5 > 1) {
            quickSort(to - s5, to, comp, swapper);
        }
    }
}
