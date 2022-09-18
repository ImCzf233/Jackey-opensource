package kotlin.internal;

import jdk.nashorn.internal.runtime.regexp.joni.constants.AsmConstants;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.UInt;
import kotlin.ULong;
import kotlin.UnsignedUtils;

@Metadata(m51mv = {1, 6, 0}, m52k = 2, m49xi = 48, m54d1 = {"�� \n��\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n��\n\u0002\u0010\t\n\u0002\b\u0002\u001a*\u0010��\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u0001H\u0002ø\u0001��¢\u0006\u0004\b\u0005\u0010\u0006\u001a*\u0010��\u001a\u00020\u00072\u0006\u0010\u0002\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u0007H\u0002ø\u0001��¢\u0006\u0004\b\b\u0010\t\u001a*\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u000eH\u0001ø\u0001��¢\u0006\u0004\b\u000f\u0010\u0006\u001a*\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u0010H\u0001ø\u0001��¢\u0006\u0004\b\u0011\u0010\t\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0012"}, m53d2 = {"differenceModulo", "Lkotlin/UInt;", "a", "b", "c", "differenceModulo-WZ9TVnA", "(III)I", "Lkotlin/ULong;", "differenceModulo-sambcqE", "(JJJ)J", "getProgressionLastElement", "start", AsmConstants.END, "step", "", "getProgressionLastElement-Nkh28Cs", "", "getProgressionLastElement-7ftBX0g", "kotlin-stdlib"})
/* renamed from: kotlin.internal.UProgressionUtilKt */
/* loaded from: Jackey Client b2.jar:kotlin/internal/UProgressionUtilKt.class */
public final class UProgressionUtil {
    /* renamed from: differenceModulo-WZ9TVnA */
    private static final int m2433differenceModuloWZ9TVnA(int a, int b, int c) {
        int ac = UnsignedUtils.m1564uintRemainderJ1ME1BU(a, c);
        int bc = UnsignedUtils.m1564uintRemainderJ1ME1BU(b, c);
        return UnsignedUtils.uintCompare(ac, bc) >= 0 ? UInt.m1353constructorimpl(ac - bc) : UInt.m1353constructorimpl(UInt.m1353constructorimpl(ac - bc) + c);
    }

    /* renamed from: differenceModulo-sambcqE */
    private static final long m2434differenceModulosambcqE(long a, long b, long c) {
        long ac = UnsignedUtils.m1566ulongRemaindereb3DHEI(a, c);
        long bc = UnsignedUtils.m1566ulongRemaindereb3DHEI(b, c);
        return UnsignedUtils.ulongCompare(ac, bc) >= 0 ? ULong.m1432constructorimpl(ac - bc) : ULong.m1432constructorimpl(ULong.m1432constructorimpl(ac - bc) + c);
    }

    @SinceKotlin(version = "1.3")
    @PublishedApi
    /* renamed from: getProgressionLastElement-Nkh28Cs */
    public static final int m2435getProgressionLastElementNkh28Cs(int start, int end, int step) {
        if (step > 0) {
            return UnsignedUtils.uintCompare(start, end) >= 0 ? end : UInt.m1353constructorimpl(end - m2433differenceModuloWZ9TVnA(end, start, UInt.m1353constructorimpl(step)));
        } else if (step >= 0) {
            throw new IllegalArgumentException("Step is zero.");
        } else {
            return UnsignedUtils.uintCompare(start, end) <= 0 ? end : UInt.m1353constructorimpl(end + m2433differenceModuloWZ9TVnA(start, end, UInt.m1353constructorimpl(-step)));
        }
    }

    @SinceKotlin(version = "1.3")
    @PublishedApi
    /* renamed from: getProgressionLastElement-7ftBX0g */
    public static final long m2436getProgressionLastElement7ftBX0g(long start, long end, long step) {
        if (step > 0) {
            return UnsignedUtils.ulongCompare(start, end) >= 0 ? end : ULong.m1432constructorimpl(end - m2434differenceModulosambcqE(end, start, ULong.m1432constructorimpl(step)));
        } else if (step >= 0) {
            throw new IllegalArgumentException("Step is zero.");
        } else {
            return UnsignedUtils.ulongCompare(start, end) <= 0 ? end : ULong.m1432constructorimpl(end + m2434differenceModulosambcqE(start, end, ULong.m1432constructorimpl(-step)));
        }
    }
}
