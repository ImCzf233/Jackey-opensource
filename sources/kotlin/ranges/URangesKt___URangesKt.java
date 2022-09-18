package kotlin.ranges;

import java.util.NoSuchElementException;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.regexp.joni.constants.AsmConstants;
import kotlin.ExperimentalStdlibApi;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.UByte;
import kotlin.UInt;
import kotlin.ULong;
import kotlin.UShort;
import kotlin.Unsigned;
import kotlin.UnsignedUtils;
import kotlin.WasExperimental;
import kotlin.internal.InlineOnly;
import kotlin.jvm.internal.CharCompanionObject;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.random.URandom;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: _URanges.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 5, m49xi = 49, m54d1 = {"��b\n��\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\u0010\t\n\u0002\b\n\u001a\u001e\u0010��\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0007ø\u0001��¢\u0006\u0004\b\u0003\u0010\u0004\u001a\u001e\u0010��\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0002\u001a\u00020\u0005H\u0007ø\u0001��¢\u0006\u0004\b\u0006\u0010\u0007\u001a\u001e\u0010��\u001a\u00020\b*\u00020\b2\u0006\u0010\u0002\u001a\u00020\bH\u0007ø\u0001��¢\u0006\u0004\b\t\u0010\n\u001a\u001e\u0010��\u001a\u00020\u000b*\u00020\u000b2\u0006\u0010\u0002\u001a\u00020\u000bH\u0007ø\u0001��¢\u0006\u0004\b\f\u0010\r\u001a\u001e\u0010\u000e\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0001H\u0007ø\u0001��¢\u0006\u0004\b\u0010\u0010\u0004\u001a\u001e\u0010\u000e\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0005H\u0007ø\u0001��¢\u0006\u0004\b\u0011\u0010\u0007\u001a\u001e\u0010\u000e\u001a\u00020\b*\u00020\b2\u0006\u0010\u000f\u001a\u00020\bH\u0007ø\u0001��¢\u0006\u0004\b\u0012\u0010\n\u001a\u001e\u0010\u000e\u001a\u00020\u000b*\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u000bH\u0007ø\u0001��¢\u0006\u0004\b\u0013\u0010\r\u001a&\u0010\u0014\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0001H\u0007ø\u0001��¢\u0006\u0004\b\u0015\u0010\u0016\u001a&\u0010\u0014\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0002\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0005H\u0007ø\u0001��¢\u0006\u0004\b\u0017\u0010\u0018\u001a$\u0010\u0014\u001a\u00020\u0005*\u00020\u00052\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00050\u001aH\u0007ø\u0001��¢\u0006\u0004\b\u001b\u0010\u001c\u001a&\u0010\u0014\u001a\u00020\b*\u00020\b2\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\bH\u0007ø\u0001��¢\u0006\u0004\b\u001d\u0010\u001e\u001a$\u0010\u0014\u001a\u00020\b*\u00020\b2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\b0\u001aH\u0007ø\u0001��¢\u0006\u0004\b\u001f\u0010 \u001a&\u0010\u0014\u001a\u00020\u000b*\u00020\u000b2\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u000bH\u0007ø\u0001��¢\u0006\u0004\b!\u0010\"\u001a\u001f\u0010#\u001a\u00020$*\u00020%2\u0006\u0010&\u001a\u00020\u0001H\u0087\u0002ø\u0001��¢\u0006\u0004\b'\u0010(\u001a\u001f\u0010#\u001a\u00020$*\u00020%2\b\u0010)\u001a\u0004\u0018\u00010\u0005H\u0087\nø\u0001��¢\u0006\u0002\b*\u001a\u001f\u0010#\u001a\u00020$*\u00020%2\u0006\u0010&\u001a\u00020\bH\u0087\u0002ø\u0001��¢\u0006\u0004\b+\u0010,\u001a\u001f\u0010#\u001a\u00020$*\u00020%2\u0006\u0010&\u001a\u00020\u000bH\u0087\u0002ø\u0001��¢\u0006\u0004\b-\u0010.\u001a\u001f\u0010#\u001a\u00020$*\u00020/2\u0006\u0010&\u001a\u00020\u0001H\u0087\u0002ø\u0001��¢\u0006\u0004\b0\u00101\u001a\u001f\u0010#\u001a\u00020$*\u00020/2\u0006\u0010&\u001a\u00020\u0005H\u0087\u0002ø\u0001��¢\u0006\u0004\b2\u00103\u001a\u001f\u0010#\u001a\u00020$*\u00020/2\b\u0010)\u001a\u0004\u0018\u00010\bH\u0087\nø\u0001��¢\u0006\u0002\b4\u001a\u001f\u0010#\u001a\u00020$*\u00020/2\u0006\u0010&\u001a\u00020\u000bH\u0087\u0002ø\u0001��¢\u0006\u0004\b5\u00106\u001a\u001f\u00107\u001a\u000208*\u00020\u00012\u0006\u00109\u001a\u00020\u0001H\u0087\u0004ø\u0001��¢\u0006\u0004\b:\u0010;\u001a\u001f\u00107\u001a\u000208*\u00020\u00052\u0006\u00109\u001a\u00020\u0005H\u0087\u0004ø\u0001��¢\u0006\u0004\b<\u0010=\u001a\u001f\u00107\u001a\u00020>*\u00020\b2\u0006\u00109\u001a\u00020\bH\u0087\u0004ø\u0001��¢\u0006\u0004\b?\u0010@\u001a\u001f\u00107\u001a\u000208*\u00020\u000b2\u0006\u00109\u001a\u00020\u000bH\u0087\u0004ø\u0001��¢\u0006\u0004\bA\u0010B\u001a\u0015\u0010C\u001a\u00020\u0005*\u00020%H\u0087\bø\u0001��¢\u0006\u0002\u0010D\u001a\u001c\u0010C\u001a\u00020\u0005*\u00020%2\u0006\u0010C\u001a\u00020EH\u0007ø\u0001��¢\u0006\u0002\u0010F\u001a\u0015\u0010C\u001a\u00020\b*\u00020/H\u0087\bø\u0001��¢\u0006\u0002\u0010G\u001a\u001c\u0010C\u001a\u00020\b*\u00020/2\u0006\u0010C\u001a\u00020EH\u0007ø\u0001��¢\u0006\u0002\u0010H\u001a\u0012\u0010I\u001a\u0004\u0018\u00010\u0005*\u00020%H\u0087\bø\u0001��\u001a\u0019\u0010I\u001a\u0004\u0018\u00010\u0005*\u00020%2\u0006\u0010C\u001a\u00020EH\u0007ø\u0001��\u001a\u0012\u0010I\u001a\u0004\u0018\u00010\b*\u00020/H\u0087\bø\u0001��\u001a\u0019\u0010I\u001a\u0004\u0018\u00010\b*\u00020/2\u0006\u0010C\u001a\u00020EH\u0007ø\u0001��\u001a\f\u0010J\u001a\u000208*\u000208H\u0007\u001a\f\u0010J\u001a\u00020>*\u00020>H\u0007\u001a\u0015\u0010K\u001a\u000208*\u0002082\u0006\u0010K\u001a\u00020LH\u0087\u0004\u001a\u0015\u0010K\u001a\u00020>*\u00020>2\u0006\u0010K\u001a\u00020MH\u0087\u0004\u001a\u001f\u0010N\u001a\u00020%*\u00020\u00012\u0006\u00109\u001a\u00020\u0001H\u0087\u0004ø\u0001��¢\u0006\u0004\bO\u0010P\u001a\u001f\u0010N\u001a\u00020%*\u00020\u00052\u0006\u00109\u001a\u00020\u0005H\u0087\u0004ø\u0001��¢\u0006\u0004\bQ\u0010R\u001a\u001f\u0010N\u001a\u00020/*\u00020\b2\u0006\u00109\u001a\u00020\bH\u0087\u0004ø\u0001��¢\u0006\u0004\bS\u0010T\u001a\u001f\u0010N\u001a\u00020%*\u00020\u000b2\u0006\u00109\u001a\u00020\u000bH\u0087\u0004ø\u0001��¢\u0006\u0004\bU\u0010V\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006W"}, m53d2 = {"coerceAtLeast", "Lkotlin/UByte;", "minimumValue", "coerceAtLeast-Kr8caGY", "(BB)B", "Lkotlin/UInt;", "coerceAtLeast-J1ME1BU", "(II)I", "Lkotlin/ULong;", "coerceAtLeast-eb3DHEI", "(JJ)J", "Lkotlin/UShort;", "coerceAtLeast-5PvTz6A", "(SS)S", "coerceAtMost", "maximumValue", "coerceAtMost-Kr8caGY", "coerceAtMost-J1ME1BU", "coerceAtMost-eb3DHEI", "coerceAtMost-5PvTz6A", "coerceIn", "coerceIn-b33U2AM", "(BBB)B", "coerceIn-WZ9TVnA", "(III)I", AsmConstants.CODERANGE, "Lkotlin/ranges/ClosedRange;", "coerceIn-wuiCnnA", "(ILkotlin/ranges/ClosedRange;)I", "coerceIn-sambcqE", "(JJJ)J", "coerceIn-JPwROB0", "(JLkotlin/ranges/ClosedRange;)J", "coerceIn-VKSA0NQ", "(SSS)S", "contains", "", "Lkotlin/ranges/UIntRange;", "value", "contains-68kG9v0", "(Lkotlin/ranges/UIntRange;B)Z", "element", "contains-biwQdVI", "contains-fz5IDCE", "(Lkotlin/ranges/UIntRange;J)Z", "contains-ZsK3CEQ", "(Lkotlin/ranges/UIntRange;S)Z", "Lkotlin/ranges/ULongRange;", "contains-ULb-yJY", "(Lkotlin/ranges/ULongRange;B)Z", "contains-Gab390E", "(Lkotlin/ranges/ULongRange;I)Z", "contains-GYNo2lE", "contains-uhHAxoY", "(Lkotlin/ranges/ULongRange;S)Z", "downTo", "Lkotlin/ranges/UIntProgression;", "to", "downTo-Kr8caGY", "(BB)Lkotlin/ranges/UIntProgression;", "downTo-J1ME1BU", "(II)Lkotlin/ranges/UIntProgression;", "Lkotlin/ranges/ULongProgression;", "downTo-eb3DHEI", "(JJ)Lkotlin/ranges/ULongProgression;", "downTo-5PvTz6A", "(SS)Lkotlin/ranges/UIntProgression;", "random", "(Lkotlin/ranges/UIntRange;)I", "Lkotlin/random/Random;", "(Lkotlin/ranges/UIntRange;Lkotlin/random/Random;)I", "(Lkotlin/ranges/ULongRange;)J", "(Lkotlin/ranges/ULongRange;Lkotlin/random/Random;)J", "randomOrNull", "reversed", "step", "", "", "until", "until-Kr8caGY", "(BB)Lkotlin/ranges/UIntRange;", "until-J1ME1BU", "(II)Lkotlin/ranges/UIntRange;", "until-eb3DHEI", "(JJ)Lkotlin/ranges/ULongRange;", "until-5PvTz6A", "(SS)Lkotlin/ranges/UIntRange;", "kotlin-stdlib"}, m48xs = "kotlin/ranges/URangesKt")
/* loaded from: Jackey Client b2.jar:kotlin/ranges/URangesKt___URangesKt.class */
class URangesKt___URangesKt {
    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    @InlineOnly
    private static final int random(UIntRange $this$random) {
        Intrinsics.checkNotNullParameter($this$random, "<this>");
        return _URanges.random($this$random, Random.Default);
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    @InlineOnly
    private static final long random(ULongRange $this$random) {
        Intrinsics.checkNotNullParameter($this$random, "<this>");
        return _URanges.random($this$random, Random.Default);
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    public static final int random(@NotNull UIntRange $this$random, @NotNull Random random) {
        Intrinsics.checkNotNullParameter($this$random, "<this>");
        Intrinsics.checkNotNullParameter(random, "random");
        try {
            return URandom.nextUInt(random, $this$random);
        } catch (IllegalArgumentException e) {
            throw new NoSuchElementException(e.getMessage());
        }
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    public static final long random(@NotNull ULongRange $this$random, @NotNull Random random) {
        Intrinsics.checkNotNullParameter($this$random, "<this>");
        Intrinsics.checkNotNullParameter(random, "random");
        try {
            return URandom.nextULong(random, $this$random);
        } catch (IllegalArgumentException e) {
            throw new NoSuchElementException(e.getMessage());
        }
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {ExperimentalStdlibApi.class, Unsigned.class})
    @InlineOnly
    private static final UInt randomOrNull(UIntRange $this$randomOrNull) {
        Intrinsics.checkNotNullParameter($this$randomOrNull, "<this>");
        return _URanges.randomOrNull($this$randomOrNull, Random.Default);
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {ExperimentalStdlibApi.class, Unsigned.class})
    @InlineOnly
    private static final ULong randomOrNull(ULongRange $this$randomOrNull) {
        Intrinsics.checkNotNullParameter($this$randomOrNull, "<this>");
        return _URanges.randomOrNull($this$randomOrNull, Random.Default);
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {ExperimentalStdlibApi.class, Unsigned.class})
    @Nullable
    public static final UInt randomOrNull(@NotNull UIntRange $this$randomOrNull, @NotNull Random random) {
        Intrinsics.checkNotNullParameter($this$randomOrNull, "<this>");
        Intrinsics.checkNotNullParameter(random, "random");
        if ($this$randomOrNull.isEmpty()) {
            return null;
        }
        return UInt.m1354boximpl(URandom.nextUInt(random, $this$randomOrNull));
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {ExperimentalStdlibApi.class, Unsigned.class})
    @Nullable
    public static final ULong randomOrNull(@NotNull ULongRange $this$randomOrNull, @NotNull Random random) {
        Intrinsics.checkNotNullParameter($this$randomOrNull, "<this>");
        Intrinsics.checkNotNullParameter(random, "random");
        if ($this$randomOrNull.isEmpty()) {
            return null;
        }
        return ULong.m1433boximpl(URandom.nextULong(random, $this$randomOrNull));
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    @InlineOnly
    /* renamed from: contains-biwQdVI */
    private static final boolean m2503containsbiwQdVI(UIntRange contains, UInt element) {
        Intrinsics.checkNotNullParameter(contains, "$this$contains");
        return element != null && contains.m2493containsWZ4Q5Ns(element.m1355unboximpl());
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    @InlineOnly
    /* renamed from: contains-GYNo2lE */
    private static final boolean m2504containsGYNo2lE(ULongRange contains, ULong element) {
        Intrinsics.checkNotNullParameter(contains, "$this$contains");
        return element != null && contains.m2501containsVKZWuLQ(element.m1434unboximpl());
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    /* renamed from: contains-68kG9v0 */
    public static final boolean m2505contains68kG9v0(@NotNull UIntRange contains, byte value) {
        Intrinsics.checkNotNullParameter(contains, "$this$contains");
        return contains.m2493containsWZ4Q5Ns(UInt.m1353constructorimpl(value & 255));
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    /* renamed from: contains-ULb-yJY */
    public static final boolean m2506containsULbyJY(@NotNull ULongRange contains, byte value) {
        Intrinsics.checkNotNullParameter(contains, "$this$contains");
        return contains.m2501containsVKZWuLQ(ULong.m1432constructorimpl(value & 255));
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    /* renamed from: contains-Gab390E */
    public static final boolean m2507containsGab390E(@NotNull ULongRange contains, int value) {
        Intrinsics.checkNotNullParameter(contains, "$this$contains");
        return contains.m2501containsVKZWuLQ(ULong.m1432constructorimpl(value & JSType.MAX_UINT));
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    /* renamed from: contains-fz5IDCE */
    public static final boolean m2508containsfz5IDCE(@NotNull UIntRange contains, long value) {
        Intrinsics.checkNotNullParameter(contains, "$this$contains");
        return ULong.m1432constructorimpl(value >>> 32) == 0 && contains.m2493containsWZ4Q5Ns(UInt.m1353constructorimpl((int) value));
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    /* renamed from: contains-ZsK3CEQ */
    public static final boolean m2509containsZsK3CEQ(@NotNull UIntRange contains, short value) {
        Intrinsics.checkNotNullParameter(contains, "$this$contains");
        return contains.m2493containsWZ4Q5Ns(UInt.m1353constructorimpl(value & 65535));
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    /* renamed from: contains-uhHAxoY */
    public static final boolean m2510containsuhHAxoY(@NotNull ULongRange contains, short value) {
        Intrinsics.checkNotNullParameter(contains, "$this$contains");
        return contains.m2501containsVKZWuLQ(ULong.m1432constructorimpl(value & 65535));
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    @NotNull
    /* renamed from: downTo-Kr8caGY */
    public static final UIntProgression m2511downToKr8caGY(byte $this$downTo, byte to) {
        return UIntProgression.Companion.m2490fromClosedRangeNkh28Cs(UInt.m1353constructorimpl($this$downTo & 255), UInt.m1353constructorimpl(to & 255), -1);
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    @NotNull
    /* renamed from: downTo-J1ME1BU */
    public static final UIntProgression m2512downToJ1ME1BU(int $this$downTo, int to) {
        return UIntProgression.Companion.m2490fromClosedRangeNkh28Cs($this$downTo, to, -1);
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    @NotNull
    /* renamed from: downTo-eb3DHEI */
    public static final ULongProgression m2513downToeb3DHEI(long $this$downTo, long to) {
        return ULongProgression.Companion.m2498fromClosedRange7ftBX0g($this$downTo, to, -1L);
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    @NotNull
    /* renamed from: downTo-5PvTz6A */
    public static final UIntProgression m2514downTo5PvTz6A(short $this$downTo, short to) {
        return UIntProgression.Companion.m2490fromClosedRangeNkh28Cs(UInt.m1353constructorimpl($this$downTo & 65535), UInt.m1353constructorimpl(to & 65535), -1);
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    @NotNull
    public static final UIntProgression reversed(@NotNull UIntProgression $this$reversed) {
        Intrinsics.checkNotNullParameter($this$reversed, "<this>");
        return UIntProgression.Companion.m2490fromClosedRangeNkh28Cs($this$reversed.m2488getLastpVg5ArA(), $this$reversed.m2487getFirstpVg5ArA(), -$this$reversed.getStep());
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    @NotNull
    public static final ULongProgression reversed(@NotNull ULongProgression $this$reversed) {
        Intrinsics.checkNotNullParameter($this$reversed, "<this>");
        return ULongProgression.Companion.m2498fromClosedRange7ftBX0g($this$reversed.m2496getLastsVKNKU(), $this$reversed.m2495getFirstsVKNKU(), -$this$reversed.getStep());
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    @NotNull
    public static final UIntProgression step(@NotNull UIntProgression $this$step, int step) {
        Intrinsics.checkNotNullParameter($this$step, "<this>");
        RangesKt.checkStepIsPositive(step > 0, Integer.valueOf(step));
        return UIntProgression.Companion.m2490fromClosedRangeNkh28Cs($this$step.m2487getFirstpVg5ArA(), $this$step.m2488getLastpVg5ArA(), $this$step.getStep() > 0 ? step : -step);
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    @NotNull
    public static final ULongProgression step(@NotNull ULongProgression $this$step, long step) {
        Intrinsics.checkNotNullParameter($this$step, "<this>");
        RangesKt.checkStepIsPositive(step > 0, Long.valueOf(step));
        return ULongProgression.Companion.m2498fromClosedRange7ftBX0g($this$step.m2495getFirstsVKNKU(), $this$step.m2496getLastsVKNKU(), $this$step.getStep() > 0 ? step : -step);
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    @NotNull
    /* renamed from: until-Kr8caGY */
    public static final UIntRange m2515untilKr8caGY(byte $this$until, byte to) {
        return Intrinsics.compare(to & 255, 0 & 255) <= 0 ? UIntRange.Companion.getEMPTY() : new UIntRange(UInt.m1353constructorimpl($this$until & 255), UInt.m1353constructorimpl(UInt.m1353constructorimpl(to & 255) - 1), null);
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    @NotNull
    /* renamed from: until-J1ME1BU */
    public static final UIntRange m2516untilJ1ME1BU(int $this$until, int to) {
        return UnsignedUtils.uintCompare(to, 0) <= 0 ? UIntRange.Companion.getEMPTY() : new UIntRange($this$until, UInt.m1353constructorimpl(to - 1), null);
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    @NotNull
    /* renamed from: until-eb3DHEI */
    public static final ULongRange m2517untileb3DHEI(long $this$until, long to) {
        return UnsignedUtils.ulongCompare(to, 0L) <= 0 ? ULongRange.Companion.getEMPTY() : new ULongRange($this$until, ULong.m1432constructorimpl(to - ULong.m1432constructorimpl(1 & JSType.MAX_UINT)), null);
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    @NotNull
    /* renamed from: until-5PvTz6A */
    public static final UIntRange m2518until5PvTz6A(short $this$until, short to) {
        return Intrinsics.compare(to & 65535, 0 & CharCompanionObject.MAX_VALUE) <= 0 ? UIntRange.Companion.getEMPTY() : new UIntRange(UInt.m1353constructorimpl($this$until & 65535), UInt.m1353constructorimpl(UInt.m1353constructorimpl(to & 65535) - 1), null);
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    /* renamed from: coerceAtLeast-J1ME1BU */
    public static final int m2519coerceAtLeastJ1ME1BU(int $this$coerceAtLeast, int minimumValue) {
        return UnsignedUtils.uintCompare($this$coerceAtLeast, minimumValue) < 0 ? minimumValue : $this$coerceAtLeast;
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    /* renamed from: coerceAtLeast-eb3DHEI */
    public static final long m2520coerceAtLeasteb3DHEI(long $this$coerceAtLeast, long minimumValue) {
        return UnsignedUtils.ulongCompare($this$coerceAtLeast, minimumValue) < 0 ? minimumValue : $this$coerceAtLeast;
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    /* renamed from: coerceAtLeast-Kr8caGY */
    public static final byte m2521coerceAtLeastKr8caGY(byte $this$coerceAtLeast, byte minimumValue) {
        return Intrinsics.compare($this$coerceAtLeast & 255, minimumValue & 255) < 0 ? minimumValue : $this$coerceAtLeast;
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    /* renamed from: coerceAtLeast-5PvTz6A */
    public static final short m2522coerceAtLeast5PvTz6A(short $this$coerceAtLeast, short minimumValue) {
        return Intrinsics.compare($this$coerceAtLeast & 65535, minimumValue & 65535) < 0 ? minimumValue : $this$coerceAtLeast;
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    /* renamed from: coerceAtMost-J1ME1BU */
    public static final int m2523coerceAtMostJ1ME1BU(int $this$coerceAtMost, int maximumValue) {
        return UnsignedUtils.uintCompare($this$coerceAtMost, maximumValue) > 0 ? maximumValue : $this$coerceAtMost;
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    /* renamed from: coerceAtMost-eb3DHEI */
    public static final long m2524coerceAtMosteb3DHEI(long $this$coerceAtMost, long maximumValue) {
        return UnsignedUtils.ulongCompare($this$coerceAtMost, maximumValue) > 0 ? maximumValue : $this$coerceAtMost;
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    /* renamed from: coerceAtMost-Kr8caGY */
    public static final byte m2525coerceAtMostKr8caGY(byte $this$coerceAtMost, byte maximumValue) {
        return Intrinsics.compare($this$coerceAtMost & 255, maximumValue & 255) > 0 ? maximumValue : $this$coerceAtMost;
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    /* renamed from: coerceAtMost-5PvTz6A */
    public static final short m2526coerceAtMost5PvTz6A(short $this$coerceAtMost, short maximumValue) {
        return Intrinsics.compare($this$coerceAtMost & 65535, maximumValue & 65535) > 0 ? maximumValue : $this$coerceAtMost;
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    /* renamed from: coerceIn-WZ9TVnA */
    public static final int m2527coerceInWZ9TVnA(int $this$coerceIn, int minimumValue, int maximumValue) {
        if (UnsignedUtils.uintCompare(minimumValue, maximumValue) > 0) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + ((Object) UInt.m1350toStringimpl(maximumValue)) + " is less than minimum " + ((Object) UInt.m1350toStringimpl(minimumValue)) + '.');
        }
        return UnsignedUtils.uintCompare($this$coerceIn, minimumValue) < 0 ? minimumValue : UnsignedUtils.uintCompare($this$coerceIn, maximumValue) > 0 ? maximumValue : $this$coerceIn;
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    /* renamed from: coerceIn-sambcqE */
    public static final long m2528coerceInsambcqE(long $this$coerceIn, long minimumValue, long maximumValue) {
        if (UnsignedUtils.ulongCompare(minimumValue, maximumValue) > 0) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + ((Object) ULong.m1429toStringimpl(maximumValue)) + " is less than minimum " + ((Object) ULong.m1429toStringimpl(minimumValue)) + '.');
        }
        return UnsignedUtils.ulongCompare($this$coerceIn, minimumValue) < 0 ? minimumValue : UnsignedUtils.ulongCompare($this$coerceIn, maximumValue) > 0 ? maximumValue : $this$coerceIn;
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    /* renamed from: coerceIn-b33U2AM */
    public static final byte m2529coerceInb33U2AM(byte $this$coerceIn, byte minimumValue, byte maximumValue) {
        if (Intrinsics.compare(minimumValue & 255, maximumValue & 255) > 0) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + ((Object) UByte.m1271toStringimpl(maximumValue)) + " is less than minimum " + ((Object) UByte.m1271toStringimpl(minimumValue)) + '.');
        }
        return Intrinsics.compare($this$coerceIn & 255, minimumValue & 255) < 0 ? minimumValue : Intrinsics.compare($this$coerceIn & 255, maximumValue & 255) > 0 ? maximumValue : $this$coerceIn;
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    /* renamed from: coerceIn-VKSA0NQ */
    public static final short m2530coerceInVKSA0NQ(short $this$coerceIn, short minimumValue, short maximumValue) {
        if (Intrinsics.compare(minimumValue & 65535, maximumValue & 65535) > 0) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + ((Object) UShort.m1535toStringimpl(maximumValue)) + " is less than minimum " + ((Object) UShort.m1535toStringimpl(minimumValue)) + '.');
        }
        return Intrinsics.compare($this$coerceIn & 65535, minimumValue & 65535) < 0 ? minimumValue : Intrinsics.compare($this$coerceIn & 65535, maximumValue & 65535) > 0 ? maximumValue : $this$coerceIn;
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    /* renamed from: coerceIn-wuiCnnA */
    public static final int m2531coerceInwuiCnnA(int $this$coerceIn, @NotNull Range<UInt> range) {
        Intrinsics.checkNotNullParameter(range, "range");
        if (range instanceof ClosedFloatingPointRange) {
            return ((UInt) RangesKt.coerceIn(UInt.m1354boximpl($this$coerceIn), (ClosedFloatingPointRange<UInt>) range)).m1355unboximpl();
        }
        if (range.isEmpty()) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: " + range + '.');
        }
        return UnsignedUtils.uintCompare($this$coerceIn, range.getStart().m1355unboximpl()) < 0 ? range.getStart().m1355unboximpl() : UnsignedUtils.uintCompare($this$coerceIn, range.getEndInclusive().m1355unboximpl()) > 0 ? range.getEndInclusive().m1355unboximpl() : $this$coerceIn;
    }

    @SinceKotlin(version = "1.5")
    @WasExperimental(markerClass = {Unsigned.class})
    /* renamed from: coerceIn-JPwROB0 */
    public static final long m2532coerceInJPwROB0(long $this$coerceIn, @NotNull Range<ULong> range) {
        Intrinsics.checkNotNullParameter(range, "range");
        if (range instanceof ClosedFloatingPointRange) {
            return ((ULong) RangesKt.coerceIn(ULong.m1433boximpl($this$coerceIn), (ClosedFloatingPointRange<ULong>) range)).m1434unboximpl();
        }
        if (range.isEmpty()) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: " + range + '.');
        }
        return UnsignedUtils.ulongCompare($this$coerceIn, range.getStart().m1434unboximpl()) < 0 ? range.getStart().m1434unboximpl() : UnsignedUtils.ulongCompare($this$coerceIn, range.getEndInclusive().m1434unboximpl()) > 0 ? range.getEndInclusive().m1434unboximpl() : $this$coerceIn;
    }
}
