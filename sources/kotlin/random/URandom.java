package kotlin.random;

import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.regexp.joni.constants.AsmConstants;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.UByteArray;
import kotlin.UInt;
import kotlin.ULong;
import kotlin.Unsigned;
import kotlin.UnsignedUtils;
import kotlin.WasExperimental;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.UIntRange;
import kotlin.ranges.ULongRange;
import org.jetbrains.annotations.NotNull;

@Metadata(m51mv = {1, 6, 0}, m52k = 2, m49xi = 48, m54d1 = {"��:\n��\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\b\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\"\u0010��\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H��ø\u0001��¢\u0006\u0004\b\u0005\u0010\u0006\u001a\"\u0010\u0007\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\bH��ø\u0001��¢\u0006\u0004\b\t\u0010\n\u001a\u001c\u0010\u000b\u001a\u00020\f*\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0007ø\u0001��¢\u0006\u0002\u0010\u0010\u001a\u001e\u0010\u000b\u001a\u00020\f*\u00020\r2\u0006\u0010\u0011\u001a\u00020\fH\u0007ø\u0001��¢\u0006\u0004\b\u0012\u0010\u0013\u001a2\u0010\u000b\u001a\u00020\f*\u00020\r2\u0006\u0010\u0011\u001a\u00020\f2\b\b\u0002\u0010\u0014\u001a\u00020\u000f2\b\b\u0002\u0010\u0015\u001a\u00020\u000fH\u0007ø\u0001��¢\u0006\u0004\b\u0016\u0010\u0017\u001a\u0014\u0010\u0018\u001a\u00020\u0003*\u00020\rH\u0007ø\u0001��¢\u0006\u0002\u0010\u0019\u001a\u001e\u0010\u0018\u001a\u00020\u0003*\u00020\r2\u0006\u0010\u0004\u001a\u00020\u0003H\u0007ø\u0001��¢\u0006\u0004\b\u001a\u0010\u001b\u001a&\u0010\u0018\u001a\u00020\u0003*\u00020\r2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\u0007ø\u0001��¢\u0006\u0004\b\u001c\u0010\u001d\u001a\u001c\u0010\u0018\u001a\u00020\u0003*\u00020\r2\u0006\u0010\u001e\u001a\u00020\u001fH\u0007ø\u0001��¢\u0006\u0002\u0010 \u001a\u0014\u0010!\u001a\u00020\b*\u00020\rH\u0007ø\u0001��¢\u0006\u0002\u0010\"\u001a\u001e\u0010!\u001a\u00020\b*\u00020\r2\u0006\u0010\u0004\u001a\u00020\bH\u0007ø\u0001��¢\u0006\u0004\b#\u0010$\u001a&\u0010!\u001a\u00020\b*\u00020\r2\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\bH\u0007ø\u0001��¢\u0006\u0004\b%\u0010&\u001a\u001c\u0010!\u001a\u00020\b*\u00020\r2\u0006\u0010\u001e\u001a\u00020'H\u0007ø\u0001��¢\u0006\u0002\u0010(\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006)"}, m53d2 = {"checkUIntRangeBounds", "", "from", "Lkotlin/UInt;", "until", "checkUIntRangeBounds-J1ME1BU", "(II)V", "checkULongRangeBounds", "Lkotlin/ULong;", "checkULongRangeBounds-eb3DHEI", "(JJ)V", "nextUBytes", "Lkotlin/UByteArray;", "Lkotlin/random/Random;", "size", "", "(Lkotlin/random/Random;I)[B", "array", "nextUBytes-EVgfTAA", "(Lkotlin/random/Random;[B)[B", "fromIndex", "toIndex", "nextUBytes-Wvrt4B4", "(Lkotlin/random/Random;[BII)[B", "nextUInt", "(Lkotlin/random/Random;)I", "nextUInt-qCasIEU", "(Lkotlin/random/Random;I)I", "nextUInt-a8DCA5k", "(Lkotlin/random/Random;II)I", AsmConstants.CODERANGE, "Lkotlin/ranges/UIntRange;", "(Lkotlin/random/Random;Lkotlin/ranges/UIntRange;)I", "nextULong", "(Lkotlin/random/Random;)J", "nextULong-V1Xi4fY", "(Lkotlin/random/Random;J)J", "nextULong-jmpaW-c", "(Lkotlin/random/Random;JJ)J", "Lkotlin/ranges/ULongRange;", "(Lkotlin/random/Random;Lkotlin/ranges/ULongRange;)J", "kotlin-stdlib"})
/* renamed from: kotlin.random.URandomKt */
/* loaded from: Jackey Client b2.jar:kotlin/random/URandomKt.class */
public final class URandom {
    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    public static final int nextUInt(@NotNull Random $this$nextUInt) {
        Intrinsics.checkNotNullParameter($this$nextUInt, "<this>");
        return UInt.m1353constructorimpl($this$nextUInt.nextInt());
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    /* renamed from: nextUInt-qCasIEU */
    public static final int m2471nextUIntqCasIEU(@NotNull Random nextUInt, int until) {
        Intrinsics.checkNotNullParameter(nextUInt, "$this$nextUInt");
        return m2472nextUInta8DCA5k(nextUInt, 0, until);
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    /* renamed from: nextUInt-a8DCA5k */
    public static final int m2472nextUInta8DCA5k(@NotNull Random nextUInt, int from, int until) {
        Intrinsics.checkNotNullParameter(nextUInt, "$this$nextUInt");
        m2478checkUIntRangeBoundsJ1ME1BU(from, until);
        int signedFrom = from ^ Integer.MIN_VALUE;
        int signedUntil = until ^ Integer.MIN_VALUE;
        int signedResult = nextUInt.nextInt(signedFrom, signedUntil) ^ Integer.MIN_VALUE;
        return UInt.m1353constructorimpl(signedResult);
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    public static final int nextUInt(@NotNull Random $this$nextUInt, @NotNull UIntRange range) {
        Intrinsics.checkNotNullParameter($this$nextUInt, "<this>");
        Intrinsics.checkNotNullParameter(range, "range");
        if (range.isEmpty()) {
            throw new IllegalArgumentException(Intrinsics.stringPlus("Cannot get random in empty range: ", range));
        }
        if (UnsignedUtils.uintCompare(range.m2488getLastpVg5ArA(), -1) < 0) {
            return m2472nextUInta8DCA5k($this$nextUInt, range.m2487getFirstpVg5ArA(), UInt.m1353constructorimpl(range.m2488getLastpVg5ArA() + 1));
        }
        if (UnsignedUtils.uintCompare(range.m2487getFirstpVg5ArA(), 0) > 0) {
            return UInt.m1353constructorimpl(m2472nextUInta8DCA5k($this$nextUInt, UInt.m1353constructorimpl(range.m2487getFirstpVg5ArA() - 1), range.m2488getLastpVg5ArA()) + 1);
        }
        return nextUInt($this$nextUInt);
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    public static final long nextULong(@NotNull Random $this$nextULong) {
        Intrinsics.checkNotNullParameter($this$nextULong, "<this>");
        return ULong.m1432constructorimpl($this$nextULong.nextLong());
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    /* renamed from: nextULong-V1Xi4fY */
    public static final long m2473nextULongV1Xi4fY(@NotNull Random nextULong, long until) {
        Intrinsics.checkNotNullParameter(nextULong, "$this$nextULong");
        return m2474nextULongjmpaWc(nextULong, 0L, until);
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    /* renamed from: nextULong-jmpaW-c */
    public static final long m2474nextULongjmpaWc(@NotNull Random nextULong, long from, long until) {
        Intrinsics.checkNotNullParameter(nextULong, "$this$nextULong");
        m2479checkULongRangeBoundseb3DHEI(from, until);
        long signedFrom = from ^ Long.MIN_VALUE;
        long signedUntil = until ^ Long.MIN_VALUE;
        long signedResult = nextULong.nextLong(signedFrom, signedUntil) ^ Long.MIN_VALUE;
        return ULong.m1432constructorimpl(signedResult);
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    public static final long nextULong(@NotNull Random $this$nextULong, @NotNull ULongRange range) {
        Intrinsics.checkNotNullParameter($this$nextULong, "<this>");
        Intrinsics.checkNotNullParameter(range, "range");
        if (range.isEmpty()) {
            throw new IllegalArgumentException(Intrinsics.stringPlus("Cannot get random in empty range: ", range));
        }
        if (UnsignedUtils.ulongCompare(range.m2496getLastsVKNKU(), -1L) < 0) {
            return m2474nextULongjmpaWc($this$nextULong, range.m2495getFirstsVKNKU(), ULong.m1432constructorimpl(range.m2496getLastsVKNKU() + ULong.m1432constructorimpl(1 & JSType.MAX_UINT)));
        }
        if (UnsignedUtils.ulongCompare(range.m2495getFirstsVKNKU(), 0L) > 0) {
            return ULong.m1432constructorimpl(m2474nextULongjmpaWc($this$nextULong, ULong.m1432constructorimpl(range.m2495getFirstsVKNKU() - ULong.m1432constructorimpl(1 & JSType.MAX_UINT)), range.m2496getLastsVKNKU()) + ULong.m1432constructorimpl(1 & JSType.MAX_UINT));
        }
        return nextULong($this$nextULong);
    }

    @SinceKotlin(version = "1.3")
    @Unsigned
    @NotNull
    /* renamed from: nextUBytes-EVgfTAA */
    public static final byte[] m2475nextUBytesEVgfTAA(@NotNull Random nextUBytes, @NotNull byte[] array) {
        Intrinsics.checkNotNullParameter(nextUBytes, "$this$nextUBytes");
        Intrinsics.checkNotNullParameter(array, "array");
        nextUBytes.nextBytes(array);
        return array;
    }

    @SinceKotlin(version = "1.3")
    @Unsigned
    @NotNull
    public static final byte[] nextUBytes(@NotNull Random $this$nextUBytes, int size) {
        Intrinsics.checkNotNullParameter($this$nextUBytes, "<this>");
        return UByteArray.m1292constructorimpl($this$nextUBytes.nextBytes(size));
    }

    /* renamed from: nextUBytes-Wvrt4B4$default */
    public static /* synthetic */ byte[] m2477nextUBytesWvrt4B4$default(Random random, byte[] bArr, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = UByteArray.m1282getSizeimpl(bArr);
        }
        return m2476nextUBytesWvrt4B4(random, bArr, i, i2);
    }

    @SinceKotlin(version = "1.3")
    @Unsigned
    @NotNull
    /* renamed from: nextUBytes-Wvrt4B4 */
    public static final byte[] m2476nextUBytesWvrt4B4(@NotNull Random nextUBytes, @NotNull byte[] array, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter(nextUBytes, "$this$nextUBytes");
        Intrinsics.checkNotNullParameter(array, "array");
        nextUBytes.nextBytes(array, fromIndex, toIndex);
        return array;
    }

    /* renamed from: checkUIntRangeBounds-J1ME1BU */
    public static final void m2478checkUIntRangeBoundsJ1ME1BU(int from, int until) {
        if (!(UnsignedUtils.uintCompare(until, from) > 0)) {
            throw new IllegalArgumentException(RandomKt.boundsErrorMessage(UInt.m1354boximpl(from), UInt.m1354boximpl(until)).toString());
        }
    }

    /* renamed from: checkULongRangeBounds-eb3DHEI */
    public static final void m2479checkULongRangeBoundseb3DHEI(long from, long until) {
        if (!(UnsignedUtils.ulongCompare(until, from) > 0)) {
            throw new IllegalArgumentException(RandomKt.boundsErrorMessage(ULong.m1433boximpl(from), ULong.m1433boximpl(until)).toString());
        }
    }
}
