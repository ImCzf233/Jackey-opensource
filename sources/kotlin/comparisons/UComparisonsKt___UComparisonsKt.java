package kotlin.comparisons;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.UByte;
import kotlin.UByteArray;
import kotlin.UInt;
import kotlin.UIntArray;
import kotlin.ULong;
import kotlin.ULongArray;
import kotlin.UShort;
import kotlin.UShortArray;
import kotlin.Unsigned;
import kotlin.UnsignedUtils;
import kotlin.WasExperimental;
import kotlin.internal.InlineOnly;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: _UComparisons.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 5, m49xi = 49, m54d1 = {"��B\n��\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0010\u001a\"\u0010��\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0007ø\u0001��¢\u0006\u0004\b\u0004\u0010\u0005\u001a+\u0010��\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u0001H\u0087\bø\u0001��¢\u0006\u0004\b\u0007\u0010\b\u001a&\u0010��\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\n\u0010\t\u001a\u00020\n\"\u00020\u0001H\u0007ø\u0001��¢\u0006\u0004\b\u000b\u0010\f\u001a\"\u0010��\u001a\u00020\r2\u0006\u0010\u0002\u001a\u00020\r2\u0006\u0010\u0003\u001a\u00020\rH\u0007ø\u0001��¢\u0006\u0004\b\u000e\u0010\u000f\u001a+\u0010��\u001a\u00020\r2\u0006\u0010\u0002\u001a\u00020\r2\u0006\u0010\u0003\u001a\u00020\r2\u0006\u0010\u0006\u001a\u00020\rH\u0087\bø\u0001��¢\u0006\u0004\b\u0010\u0010\u0011\u001a&\u0010��\u001a\u00020\r2\u0006\u0010\u0002\u001a\u00020\r2\n\u0010\t\u001a\u00020\u0012\"\u00020\rH\u0007ø\u0001��¢\u0006\u0004\b\u0013\u0010\u0014\u001a\"\u0010��\u001a\u00020\u00152\u0006\u0010\u0002\u001a\u00020\u00152\u0006\u0010\u0003\u001a\u00020\u0015H\u0007ø\u0001��¢\u0006\u0004\b\u0016\u0010\u0017\u001a+\u0010��\u001a\u00020\u00152\u0006\u0010\u0002\u001a\u00020\u00152\u0006\u0010\u0003\u001a\u00020\u00152\u0006\u0010\u0006\u001a\u00020\u0015H\u0087\bø\u0001��¢\u0006\u0004\b\u0018\u0010\u0019\u001a&\u0010��\u001a\u00020\u00152\u0006\u0010\u0002\u001a\u00020\u00152\n\u0010\t\u001a\u00020\u001a\"\u00020\u0015H\u0007ø\u0001��¢\u0006\u0004\b\u001b\u0010\u001c\u001a\"\u0010��\u001a\u00020\u001d2\u0006\u0010\u0002\u001a\u00020\u001d2\u0006\u0010\u0003\u001a\u00020\u001dH\u0007ø\u0001��¢\u0006\u0004\b\u001e\u0010\u001f\u001a+\u0010��\u001a\u00020\u001d2\u0006\u0010\u0002\u001a\u00020\u001d2\u0006\u0010\u0003\u001a\u00020\u001d2\u0006\u0010\u0006\u001a\u00020\u001dH\u0087\bø\u0001��¢\u0006\u0004\b \u0010!\u001a&\u0010��\u001a\u00020\u001d2\u0006\u0010\u0002\u001a\u00020\u001d2\n\u0010\t\u001a\u00020\"\"\u00020\u001dH\u0007ø\u0001��¢\u0006\u0004\b#\u0010$\u001a\"\u0010%\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0007ø\u0001��¢\u0006\u0004\b&\u0010\u0005\u001a+\u0010%\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u0001H\u0087\bø\u0001��¢\u0006\u0004\b'\u0010\b\u001a&\u0010%\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\n\u0010\t\u001a\u00020\n\"\u00020\u0001H\u0007ø\u0001��¢\u0006\u0004\b(\u0010\f\u001a\"\u0010%\u001a\u00020\r2\u0006\u0010\u0002\u001a\u00020\r2\u0006\u0010\u0003\u001a\u00020\rH\u0007ø\u0001��¢\u0006\u0004\b)\u0010\u000f\u001a+\u0010%\u001a\u00020\r2\u0006\u0010\u0002\u001a\u00020\r2\u0006\u0010\u0003\u001a\u00020\r2\u0006\u0010\u0006\u001a\u00020\rH\u0087\bø\u0001��¢\u0006\u0004\b*\u0010\u0011\u001a&\u0010%\u001a\u00020\r2\u0006\u0010\u0002\u001a\u00020\r2\n\u0010\t\u001a\u00020\u0012\"\u00020\rH\u0007ø\u0001��¢\u0006\u0004\b+\u0010\u0014\u001a\"\u0010%\u001a\u00020\u00152\u0006\u0010\u0002\u001a\u00020\u00152\u0006\u0010\u0003\u001a\u00020\u0015H\u0007ø\u0001��¢\u0006\u0004\b,\u0010\u0017\u001a+\u0010%\u001a\u00020\u00152\u0006\u0010\u0002\u001a\u00020\u00152\u0006\u0010\u0003\u001a\u00020\u00152\u0006\u0010\u0006\u001a\u00020\u0015H\u0087\bø\u0001��¢\u0006\u0004\b-\u0010\u0019\u001a&\u0010%\u001a\u00020\u00152\u0006\u0010\u0002\u001a\u00020\u00152\n\u0010\t\u001a\u00020\u001a\"\u00020\u0015H\u0007ø\u0001��¢\u0006\u0004\b.\u0010\u001c\u001a\"\u0010%\u001a\u00020\u001d2\u0006\u0010\u0002\u001a\u00020\u001d2\u0006\u0010\u0003\u001a\u00020\u001dH\u0007ø\u0001��¢\u0006\u0004\b/\u0010\u001f\u001a+\u0010%\u001a\u00020\u001d2\u0006\u0010\u0002\u001a\u00020\u001d2\u0006\u0010\u0003\u001a\u00020\u001d2\u0006\u0010\u0006\u001a\u00020\u001dH\u0087\bø\u0001��¢\u0006\u0004\b0\u0010!\u001a&\u0010%\u001a\u00020\u001d2\u0006\u0010\u0002\u001a\u00020\u001d2\n\u0010\t\u001a\u00020\"\"\u00020\u001dH\u0007ø\u0001��¢\u0006\u0004\b1\u0010$\u0082\u0002\u0004\n\u0002\b\u0019¨\u00062"}, m53d2 = {"maxOf", "Lkotlin/UByte;", "a", "b", "maxOf-Kr8caGY", "(BB)B", "c", "maxOf-b33U2AM", "(BBB)B", "other", "Lkotlin/UByteArray;", "maxOf-Wr6uiD8", "(B[B)B", "Lkotlin/UInt;", "maxOf-J1ME1BU", "(II)I", "maxOf-WZ9TVnA", "(III)I", "Lkotlin/UIntArray;", "maxOf-Md2H83M", "(I[I)I", "Lkotlin/ULong;", "maxOf-eb3DHEI", "(JJ)J", "maxOf-sambcqE", "(JJJ)J", "Lkotlin/ULongArray;", "maxOf-R03FKyM", "(J[J)J", "Lkotlin/UShort;", "maxOf-5PvTz6A", "(SS)S", "maxOf-VKSA0NQ", "(SSS)S", "Lkotlin/UShortArray;", "maxOf-t1qELG4", "(S[S)S", "minOf", "minOf-Kr8caGY", "minOf-b33U2AM", "minOf-Wr6uiD8", "minOf-J1ME1BU", "minOf-WZ9TVnA", "minOf-Md2H83M", "minOf-eb3DHEI", "minOf-sambcqE", "minOf-R03FKyM", "minOf-5PvTz6A", "minOf-VKSA0NQ", "minOf-t1qELG4", "kotlin-stdlib"}, m48xs = "kotlin/comparisons/UComparisonsKt")
/* loaded from: Jackey Client b2.jar:kotlin/comparisons/UComparisonsKt___UComparisonsKt.class */
public class UComparisonsKt___UComparisonsKt {
    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    /* renamed from: maxOf-J1ME1BU */
    public static final int m2394maxOfJ1ME1BU(int a, int b) {
        return UnsignedUtils.uintCompare(a, b) >= 0 ? a : b;
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    /* renamed from: maxOf-eb3DHEI */
    public static final long m2395maxOfeb3DHEI(long a, long b) {
        return UnsignedUtils.ulongCompare(a, b) >= 0 ? a : b;
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    /* renamed from: maxOf-Kr8caGY */
    public static final byte m2396maxOfKr8caGY(byte a, byte b) {
        return Intrinsics.compare(a & 255, b & 255) >= 0 ? a : b;
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    /* renamed from: maxOf-5PvTz6A */
    public static final short m2397maxOf5PvTz6A(short a, short b) {
        return Intrinsics.compare(a & 65535, b & 65535) >= 0 ? a : b;
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    @InlineOnly
    /* renamed from: maxOf-WZ9TVnA */
    private static final int m2398maxOfWZ9TVnA(int a, int b, int c) {
        return _UComparisons.m2394maxOfJ1ME1BU(a, _UComparisons.m2394maxOfJ1ME1BU(b, c));
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    @InlineOnly
    /* renamed from: maxOf-sambcqE */
    private static final long m2399maxOfsambcqE(long a, long b, long c) {
        return _UComparisons.m2395maxOfeb3DHEI(a, _UComparisons.m2395maxOfeb3DHEI(b, c));
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    @InlineOnly
    /* renamed from: maxOf-b33U2AM */
    private static final byte m2400maxOfb33U2AM(byte a, byte b, byte c) {
        return _UComparisons.m2396maxOfKr8caGY(a, _UComparisons.m2396maxOfKr8caGY(b, c));
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    @InlineOnly
    /* renamed from: maxOf-VKSA0NQ */
    private static final short m2401maxOfVKSA0NQ(short a, short b, short c) {
        return _UComparisons.m2397maxOf5PvTz6A(a, _UComparisons.m2397maxOf5PvTz6A(b, c));
    }

    @SinceKotlin(version = "1.4")
    @Unsigned
    /* renamed from: maxOf-Md2H83M */
    public static final int m2402maxOfMd2H83M(int a, @NotNull int... other) {
        Intrinsics.checkNotNullParameter(other, "other");
        int max = a;
        Iterator<UInt> m1362iteratorimpl = UIntArray.m1362iteratorimpl(other);
        while (m1362iteratorimpl.hasNext()) {
            int e = m1362iteratorimpl.next().m1355unboximpl();
            max = _UComparisons.m2394maxOfJ1ME1BU(max, e);
        }
        return max;
    }

    @SinceKotlin(version = "1.4")
    @Unsigned
    /* renamed from: maxOf-R03FKyM */
    public static final long m2403maxOfR03FKyM(long a, @NotNull long... other) {
        Intrinsics.checkNotNullParameter(other, "other");
        long max = a;
        Iterator<ULong> m1441iteratorimpl = ULongArray.m1441iteratorimpl(other);
        while (m1441iteratorimpl.hasNext()) {
            long e = m1441iteratorimpl.next().m1434unboximpl();
            max = _UComparisons.m2395maxOfeb3DHEI(max, e);
        }
        return max;
    }

    @SinceKotlin(version = "1.4")
    @Unsigned
    /* renamed from: maxOf-Wr6uiD8 */
    public static final byte m2404maxOfWr6uiD8(byte a, @NotNull byte... other) {
        Intrinsics.checkNotNullParameter(other, "other");
        byte max = a;
        Iterator<UByte> m1283iteratorimpl = UByteArray.m1283iteratorimpl(other);
        while (m1283iteratorimpl.hasNext()) {
            byte e = m1283iteratorimpl.next().m1276unboximpl();
            max = _UComparisons.m2396maxOfKr8caGY(max, e);
        }
        return max;
    }

    @SinceKotlin(version = "1.4")
    @Unsigned
    /* renamed from: maxOf-t1qELG4 */
    public static final short m2405maxOft1qELG4(short a, @NotNull short... other) {
        Intrinsics.checkNotNullParameter(other, "other");
        short max = a;
        Iterator<UShort> m1547iteratorimpl = UShortArray.m1547iteratorimpl(other);
        while (m1547iteratorimpl.hasNext()) {
            short e = m1547iteratorimpl.next().m1540unboximpl();
            max = _UComparisons.m2397maxOf5PvTz6A(max, e);
        }
        return max;
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    /* renamed from: minOf-J1ME1BU */
    public static final int m2406minOfJ1ME1BU(int a, int b) {
        return UnsignedUtils.uintCompare(a, b) <= 0 ? a : b;
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    /* renamed from: minOf-eb3DHEI */
    public static final long m2407minOfeb3DHEI(long a, long b) {
        return UnsignedUtils.ulongCompare(a, b) <= 0 ? a : b;
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    /* renamed from: minOf-Kr8caGY */
    public static final byte m2408minOfKr8caGY(byte a, byte b) {
        return Intrinsics.compare(a & 255, b & 255) <= 0 ? a : b;
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    /* renamed from: minOf-5PvTz6A */
    public static final short m2409minOf5PvTz6A(short a, short b) {
        return Intrinsics.compare(a & 65535, b & 65535) <= 0 ? a : b;
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    @InlineOnly
    /* renamed from: minOf-WZ9TVnA */
    private static final int m2410minOfWZ9TVnA(int a, int b, int c) {
        return _UComparisons.m2406minOfJ1ME1BU(a, _UComparisons.m2406minOfJ1ME1BU(b, c));
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    @InlineOnly
    /* renamed from: minOf-sambcqE */
    private static final long m2411minOfsambcqE(long a, long b, long c) {
        return _UComparisons.m2407minOfeb3DHEI(a, _UComparisons.m2407minOfeb3DHEI(b, c));
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    @InlineOnly
    /* renamed from: minOf-b33U2AM */
    private static final byte m2412minOfb33U2AM(byte a, byte b, byte c) {
        return _UComparisons.m2408minOfKr8caGY(a, _UComparisons.m2408minOfKr8caGY(b, c));
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    @InlineOnly
    /* renamed from: minOf-VKSA0NQ */
    private static final short m2413minOfVKSA0NQ(short a, short b, short c) {
        return _UComparisons.m2409minOf5PvTz6A(a, _UComparisons.m2409minOf5PvTz6A(b, c));
    }

    @SinceKotlin(version = "1.4")
    @Unsigned
    /* renamed from: minOf-Md2H83M */
    public static final int m2414minOfMd2H83M(int a, @NotNull int... other) {
        Intrinsics.checkNotNullParameter(other, "other");
        int min = a;
        Iterator<UInt> m1362iteratorimpl = UIntArray.m1362iteratorimpl(other);
        while (m1362iteratorimpl.hasNext()) {
            int e = m1362iteratorimpl.next().m1355unboximpl();
            min = _UComparisons.m2406minOfJ1ME1BU(min, e);
        }
        return min;
    }

    @SinceKotlin(version = "1.4")
    @Unsigned
    /* renamed from: minOf-R03FKyM */
    public static final long m2415minOfR03FKyM(long a, @NotNull long... other) {
        Intrinsics.checkNotNullParameter(other, "other");
        long min = a;
        Iterator<ULong> m1441iteratorimpl = ULongArray.m1441iteratorimpl(other);
        while (m1441iteratorimpl.hasNext()) {
            long e = m1441iteratorimpl.next().m1434unboximpl();
            min = _UComparisons.m2407minOfeb3DHEI(min, e);
        }
        return min;
    }

    @SinceKotlin(version = "1.4")
    @Unsigned
    /* renamed from: minOf-Wr6uiD8 */
    public static final byte m2416minOfWr6uiD8(byte a, @NotNull byte... other) {
        Intrinsics.checkNotNullParameter(other, "other");
        byte min = a;
        Iterator<UByte> m1283iteratorimpl = UByteArray.m1283iteratorimpl(other);
        while (m1283iteratorimpl.hasNext()) {
            byte e = m1283iteratorimpl.next().m1276unboximpl();
            min = _UComparisons.m2408minOfKr8caGY(min, e);
        }
        return min;
    }

    @SinceKotlin(version = "1.4")
    @Unsigned
    /* renamed from: minOf-t1qELG4 */
    public static final short m2417minOft1qELG4(short a, @NotNull short... other) {
        Intrinsics.checkNotNullParameter(other, "other");
        short min = a;
        Iterator<UShort> m1547iteratorimpl = UShortArray.m1547iteratorimpl(other);
        while (m1547iteratorimpl.hasNext()) {
            short e = m1547iteratorimpl.next().m1540unboximpl();
            min = _UComparisons.m2409minOf5PvTz6A(min, e);
        }
        return min;
    }
}
