package kotlin.collections;

import kotlin.Metadata;
import kotlin.UByteArray;
import kotlin.UIntArray;
import kotlin.ULongArray;
import kotlin.UShortArray;
import kotlin.Unsigned;
import kotlin.UnsignedUtils;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(m51mv = {1, 6, 0}, m52k = 2, m49xi = 48, m54d1 = {"��0\n��\n\u0002\u0010\b\n��\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0010\u001a*\u0010��\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001��¢\u0006\u0004\b\u0006\u0010\u0007\u001a*\u0010��\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001��¢\u0006\u0004\b\t\u0010\n\u001a*\u0010��\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001��¢\u0006\u0004\b\f\u0010\r\u001a*\u0010��\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001��¢\u0006\u0004\b\u000f\u0010\u0010\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001��¢\u0006\u0004\b\u0013\u0010\u0014\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001��¢\u0006\u0004\b\u0015\u0010\u0016\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001��¢\u0006\u0004\b\u0017\u0010\u0018\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001��¢\u0006\u0004\b\u0019\u0010\u001a\u001a*\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001ø\u0001��¢\u0006\u0004\b\u001e\u0010\u0014\u001a*\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001ø\u0001��¢\u0006\u0004\b\u001f\u0010\u0016\u001a*\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001ø\u0001��¢\u0006\u0004\b \u0010\u0018\u001a*\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001ø\u0001��¢\u0006\u0004\b!\u0010\u001a\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\""}, m53d2 = {"partition", "", "array", "Lkotlin/UByteArray;", "left", "right", "partition-4UcCI2c", "([BII)I", "Lkotlin/UIntArray;", "partition-oBK06Vg", "([III)I", "Lkotlin/ULongArray;", "partition--nroSd4", "([JII)I", "Lkotlin/UShortArray;", "partition-Aa5vz7o", "([SII)I", "quickSort", "", "quickSort-4UcCI2c", "([BII)V", "quickSort-oBK06Vg", "([III)V", "quickSort--nroSd4", "([JII)V", "quickSort-Aa5vz7o", "([SII)V", "sortArray", "fromIndex", "toIndex", "sortArray-4UcCI2c", "sortArray-oBK06Vg", "sortArray--nroSd4", "sortArray-Aa5vz7o", "kotlin-stdlib"})
/* renamed from: kotlin.collections.UArraySortingKt */
/* loaded from: Jackey Client b2.jar:kotlin/collections/UArraySortingKt.class */
public final class UArraySorting {
    @Unsigned
    /* renamed from: partition-4UcCI2c */
    private static final int m1673partition4UcCI2c(byte[] array, int left, int right) {
        int i = left;
        int j = right;
        byte pivot = UByteArray.m1280getw2LRezQ(array, (left + right) / 2);
        while (i <= j) {
            while (Intrinsics.compare(UByteArray.m1280getw2LRezQ(array, i) & 255, pivot & 255) < 0) {
                i++;
            }
            while (Intrinsics.compare(UByteArray.m1280getw2LRezQ(array, j) & 255, pivot & 255) > 0) {
                j--;
            }
            if (i <= j) {
                byte tmp = UByteArray.m1280getw2LRezQ(array, i);
                UByteArray.m1281setVurrAj0(array, i, UByteArray.m1280getw2LRezQ(array, j));
                UByteArray.m1281setVurrAj0(array, j, tmp);
                i++;
                j--;
            }
        }
        return i;
    }

    @Unsigned
    /* renamed from: quickSort-4UcCI2c */
    private static final void m1674quickSort4UcCI2c(byte[] array, int left, int right) {
        int index = m1673partition4UcCI2c(array, left, right);
        if (left < index - 1) {
            m1674quickSort4UcCI2c(array, left, index - 1);
        }
        if (index < right) {
            m1674quickSort4UcCI2c(array, index, right);
        }
    }

    @Unsigned
    /* renamed from: partition-Aa5vz7o */
    private static final int m1675partitionAa5vz7o(short[] array, int left, int right) {
        int i = left;
        int j = right;
        short pivot = UShortArray.m1544getMh2AYeg(array, (left + right) / 2);
        while (i <= j) {
            while (Intrinsics.compare(UShortArray.m1544getMh2AYeg(array, i) & 65535, pivot & 65535) < 0) {
                i++;
            }
            while (Intrinsics.compare(UShortArray.m1544getMh2AYeg(array, j) & 65535, pivot & 65535) > 0) {
                j--;
            }
            if (i <= j) {
                short tmp = UShortArray.m1544getMh2AYeg(array, i);
                UShortArray.m1545set01HTLdE(array, i, UShortArray.m1544getMh2AYeg(array, j));
                UShortArray.m1545set01HTLdE(array, j, tmp);
                i++;
                j--;
            }
        }
        return i;
    }

    @Unsigned
    /* renamed from: quickSort-Aa5vz7o */
    private static final void m1676quickSortAa5vz7o(short[] array, int left, int right) {
        int index = m1675partitionAa5vz7o(array, left, right);
        if (left < index - 1) {
            m1676quickSortAa5vz7o(array, left, index - 1);
        }
        if (index < right) {
            m1676quickSortAa5vz7o(array, index, right);
        }
    }

    @Unsigned
    /* renamed from: partition-oBK06Vg */
    private static final int m1677partitionoBK06Vg(int[] array, int left, int right) {
        int i = left;
        int j = right;
        int pivot = UIntArray.m1359getpVg5ArA(array, (left + right) / 2);
        while (i <= j) {
            while (UnsignedUtils.uintCompare(UIntArray.m1359getpVg5ArA(array, i), pivot) < 0) {
                i++;
            }
            while (UnsignedUtils.uintCompare(UIntArray.m1359getpVg5ArA(array, j), pivot) > 0) {
                j--;
            }
            if (i <= j) {
                int tmp = UIntArray.m1359getpVg5ArA(array, i);
                UIntArray.m1360setVXSXFK8(array, i, UIntArray.m1359getpVg5ArA(array, j));
                UIntArray.m1360setVXSXFK8(array, j, tmp);
                i++;
                j--;
            }
        }
        return i;
    }

    @Unsigned
    /* renamed from: quickSort-oBK06Vg */
    private static final void m1678quickSortoBK06Vg(int[] array, int left, int right) {
        int index = m1677partitionoBK06Vg(array, left, right);
        if (left < index - 1) {
            m1678quickSortoBK06Vg(array, left, index - 1);
        }
        if (index < right) {
            m1678quickSortoBK06Vg(array, index, right);
        }
    }

    @Unsigned
    /* renamed from: partition--nroSd4 */
    private static final int m1679partitionnroSd4(long[] array, int left, int right) {
        int i = left;
        int j = right;
        long pivot = ULongArray.m1438getsVKNKU(array, (left + right) / 2);
        while (i <= j) {
            while (UnsignedUtils.ulongCompare(ULongArray.m1438getsVKNKU(array, i), pivot) < 0) {
                i++;
            }
            while (UnsignedUtils.ulongCompare(ULongArray.m1438getsVKNKU(array, j), pivot) > 0) {
                j--;
            }
            if (i <= j) {
                long tmp = ULongArray.m1438getsVKNKU(array, i);
                ULongArray.m1439setk8EXiF4(array, i, ULongArray.m1438getsVKNKU(array, j));
                ULongArray.m1439setk8EXiF4(array, j, tmp);
                i++;
                j--;
            }
        }
        return i;
    }

    @Unsigned
    /* renamed from: quickSort--nroSd4 */
    private static final void m1680quickSortnroSd4(long[] array, int left, int right) {
        int index = m1679partitionnroSd4(array, left, right);
        if (left < index - 1) {
            m1680quickSortnroSd4(array, left, index - 1);
        }
        if (index < right) {
            m1680quickSortnroSd4(array, index, right);
        }
    }

    @Unsigned
    /* renamed from: sortArray-4UcCI2c */
    public static final void m1681sortArray4UcCI2c(@NotNull byte[] array, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter(array, "array");
        m1674quickSort4UcCI2c(array, fromIndex, toIndex - 1);
    }

    @Unsigned
    /* renamed from: sortArray-Aa5vz7o */
    public static final void m1682sortArrayAa5vz7o(@NotNull short[] array, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter(array, "array");
        m1676quickSortAa5vz7o(array, fromIndex, toIndex - 1);
    }

    @Unsigned
    /* renamed from: sortArray-oBK06Vg */
    public static final void m1683sortArrayoBK06Vg(@NotNull int[] array, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter(array, "array");
        m1678quickSortoBK06Vg(array, fromIndex, toIndex - 1);
    }

    @Unsigned
    /* renamed from: sortArray--nroSd4 */
    public static final void m1684sortArraynroSd4(@NotNull long[] array, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter(array, "array");
        m1680quickSortnroSd4(array, fromIndex, toIndex - 1);
    }
}
