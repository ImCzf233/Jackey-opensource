package kotlin;

import jdk.nashorn.internal.runtime.JSType;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(m51mv = {1, 6, 0}, m52k = 2, m49xi = 48, m54d1 = {"��0\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\t\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a\u0018\u0010��\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0001ø\u0001��¢\u0006\u0002\u0010\u0004\u001a\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0002\u001a\u00020\u0003H\u0001ø\u0001��¢\u0006\u0002\u0010\u0007\u001a\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\tH\u0001\u001a\"\u0010\f\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\u0001H\u0001ø\u0001��¢\u0006\u0004\b\r\u0010\u000e\u001a\"\u0010\u000f\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\u0001H\u0001ø\u0001��¢\u0006\u0004\b\u0010\u0010\u000e\u001a\u0010\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0002\u001a\u00020\tH\u0001\u001a\u0018\u0010\u0012\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00132\u0006\u0010\u000b\u001a\u00020\u0013H\u0001\u001a\"\u0010\u0014\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0006H\u0001ø\u0001��¢\u0006\u0004\b\u0015\u0010\u0016\u001a\"\u0010\u0017\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0006H\u0001ø\u0001��¢\u0006\u0004\b\u0018\u0010\u0016\u001a\u0010\u0010\u0019\u001a\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u0013H\u0001\u001a\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0002\u001a\u00020\u0013H��\u001a\u0018\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0002\u001a\u00020\u00132\u0006\u0010\u001c\u001a\u00020\tH��\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u001d"}, m53d2 = {"doubleToUInt", "Lkotlin/UInt;", "v", "", "(D)I", "doubleToULong", "Lkotlin/ULong;", "(D)J", "uintCompare", "", "v1", "v2", "uintDivide", "uintDivide-J1ME1BU", "(II)I", "uintRemainder", "uintRemainder-J1ME1BU", "uintToDouble", "ulongCompare", "", "ulongDivide", "ulongDivide-eb3DHEI", "(JJ)J", "ulongRemainder", "ulongRemainder-eb3DHEI", "ulongToDouble", "ulongToString", "", "base", "kotlin-stdlib"})
@JvmName(name = "UnsignedKt")
/* renamed from: kotlin.UnsignedKt */
/* loaded from: Jackey Client b2.jar:kotlin/UnsignedKt.class */
public final class UnsignedUtils {
    @PublishedApi
    public static final int uintCompare(int v1, int v2) {
        return Intrinsics.compare(v1 ^ Integer.MIN_VALUE, v2 ^ Integer.MIN_VALUE);
    }

    @PublishedApi
    public static final int ulongCompare(long v1, long v2) {
        return Intrinsics.compare(v1 ^ Long.MIN_VALUE, v2 ^ Long.MIN_VALUE);
    }

    @PublishedApi
    /* renamed from: uintDivide-J1ME1BU */
    public static final int m1563uintDivideJ1ME1BU(int v1, int v2) {
        return UInt.m1353constructorimpl((int) ((v1 & JSType.MAX_UINT) / (v2 & JSType.MAX_UINT)));
    }

    @PublishedApi
    /* renamed from: uintRemainder-J1ME1BU */
    public static final int m1564uintRemainderJ1ME1BU(int v1, int v2) {
        return UInt.m1353constructorimpl((int) ((v1 & JSType.MAX_UINT) % (v2 & JSType.MAX_UINT)));
    }

    @PublishedApi
    /* renamed from: ulongDivide-eb3DHEI */
    public static final long m1565ulongDivideeb3DHEI(long v1, long v2) {
        if (v2 < 0) {
            return ulongCompare(v1, v2) < 0 ? ULong.m1432constructorimpl(0L) : ULong.m1432constructorimpl(1L);
        } else if (v1 < 0) {
            long quotient = ((v1 >>> 1) / v2) << 1;
            long rem = v1 - (quotient * v2);
            return ULong.m1432constructorimpl(quotient + (ulongCompare(ULong.m1432constructorimpl(rem), ULong.m1432constructorimpl(v2)) >= 0 ? 1 : 0));
        } else {
            return ULong.m1432constructorimpl(v1 / v2);
        }
    }

    @PublishedApi
    /* renamed from: ulongRemainder-eb3DHEI */
    public static final long m1566ulongRemaindereb3DHEI(long v1, long v2) {
        if (v2 < 0) {
            if (ulongCompare(v1, v2) < 0) {
                return v1;
            }
            return ULong.m1432constructorimpl(v1 - v2);
        } else if (v1 < 0) {
            long quotient = ((v1 >>> 1) / v2) << 1;
            long rem = v1 - (quotient * v2);
            return ULong.m1432constructorimpl(rem - (ulongCompare(ULong.m1432constructorimpl(rem), ULong.m1432constructorimpl(v2)) >= 0 ? v2 : 0L));
        } else {
            return ULong.m1432constructorimpl(v1 % v2);
        }
    }

    @PublishedApi
    public static final int doubleToUInt(double v) {
        if (!Double.isNaN(v) && v > uintToDouble(0)) {
            if (v >= uintToDouble(-1)) {
                return -1;
            }
            return v <= 2.147483647E9d ? UInt.m1353constructorimpl((int) v) : UInt.m1353constructorimpl(UInt.m1353constructorimpl((int) (v - Integer.MAX_VALUE)) + UInt.m1353constructorimpl(Integer.MAX_VALUE));
        }
        return 0;
    }

    @PublishedApi
    public static final long doubleToULong(double v) {
        if (!Double.isNaN(v) && v > ulongToDouble(0L)) {
            if (v >= ulongToDouble(-1L)) {
                return -1L;
            }
            return v < 9.223372036854776E18d ? ULong.m1432constructorimpl((long) v) : ULong.m1432constructorimpl(ULong.m1432constructorimpl((long) (v - 9.223372036854776E18d)) - Long.MIN_VALUE);
        }
        return 0L;
    }

    @PublishedApi
    public static final double uintToDouble(int v) {
        return (v & Integer.MAX_VALUE) + (((v >>> 31) << 30) * 2);
    }

    @PublishedApi
    public static final double ulongToDouble(long v) {
        return ((v >>> 11) * 2048) + (v & 2047);
    }

    @NotNull
    public static final String ulongToString(long v) {
        return ulongToString(v, 10);
    }

    @NotNull
    public static final String ulongToString(long v, int base) {
        if (v >= 0) {
            String l = Long.toString(v, CharsKt.checkRadix(base));
            Intrinsics.checkNotNullExpressionValue(l, "toString(this, checkRadix(radix))");
            return l;
        }
        long quotient = ((v >>> 1) / base) << 1;
        long rem = v - (quotient * base);
        if (rem >= base) {
            rem -= base;
            quotient++;
        }
        String l2 = Long.toString(quotient, CharsKt.checkRadix(base));
        Intrinsics.checkNotNullExpressionValue(l2, "toString(this, checkRadix(radix))");
        String l3 = Long.toString(rem, CharsKt.checkRadix(base));
        Intrinsics.checkNotNullExpressionValue(l3, "toString(this, checkRadix(radix))");
        return Intrinsics.stringPlus(l2, l3);
    }
}
