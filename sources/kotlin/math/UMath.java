package kotlin.math;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.Unsigned;
import kotlin.WasExperimental;
import kotlin.comparisons._UComparisons;
import kotlin.internal.InlineOnly;

@Metadata(m51mv = {1, 6, 0}, m52k = 2, m49xi = 48, m54d1 = {"��\u0012\n��\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a#\u0010��\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0087\bø\u0001��¢\u0006\u0004\b\u0004\u0010\u0005\u001a#\u0010��\u001a\u00020\u00062\u0006\u0010\u0002\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u0006H\u0087\bø\u0001��¢\u0006\u0004\b\u0007\u0010\b\u001a#\u0010\t\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0087\bø\u0001��¢\u0006\u0004\b\n\u0010\u0005\u001a#\u0010\t\u001a\u00020\u00062\u0006\u0010\u0002\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u0006H\u0087\bø\u0001��¢\u0006\u0004\b\u000b\u0010\b\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\f"}, m53d2 = {"max", "Lkotlin/UInt;", "a", "b", "max-J1ME1BU", "(II)I", "Lkotlin/ULong;", "max-eb3DHEI", "(JJ)J", "min", "min-J1ME1BU", "min-eb3DHEI", "kotlin-stdlib"})
/* renamed from: kotlin.math.UMathKt */
/* loaded from: Jackey Client b2.jar:kotlin/math/UMathKt.class */
public final class UMath {
    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    @InlineOnly
    /* renamed from: min-J1ME1BU */
    private static final int m2462minJ1ME1BU(int a, int b) {
        return _UComparisons.m2406minOfJ1ME1BU(a, b);
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    @InlineOnly
    /* renamed from: min-eb3DHEI */
    private static final long m2463mineb3DHEI(long a, long b) {
        return _UComparisons.m2407minOfeb3DHEI(a, b);
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    @InlineOnly
    /* renamed from: max-J1ME1BU */
    private static final int m2464maxJ1ME1BU(int a, int b) {
        return _UComparisons.m2394maxOfJ1ME1BU(a, b);
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    @InlineOnly
    /* renamed from: max-eb3DHEI */
    private static final long m2465maxeb3DHEI(long a, long b) {
        return _UComparisons.m2395maxOfeb3DHEI(a, b);
    }
}
