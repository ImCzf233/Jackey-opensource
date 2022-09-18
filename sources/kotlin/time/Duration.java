package kotlin.time;

import kotlin.Annotations;
import kotlin.DeprecatedSinceKotlin;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.ReplaceWith;
import kotlin.SinceKotlin;
import kotlin.WasExperimental;
import kotlin.comparisons.ComparisonsKt;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmInline;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.LongCompanionObject;
import kotlin.math.MathKt;
import kotlin.ranges.LongRange;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Duration.kt */
@SinceKotlin(version = "1.6")
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��n\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n��\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b-\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0010\u000b\n\u0002\u0010��\n\u0002\b\u001b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\u0010\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\b\u0087@\u0018�� ¤\u00012\b\u0012\u0004\u0012\u00020��0\u0001:\u0002¤\u0001B\u0014\b��\u0012\u0006\u0010\u0002\u001a\u00020\u0003ø\u0001��¢\u0006\u0004\b\u0004\u0010\u0005J%\u0010D\u001a\u00020��2\u0006\u0010E\u001a\u00020\u00032\u0006\u0010F\u001a\u00020\u0003H\u0002ø\u0001��ø\u0001\u0001¢\u0006\u0004\bG\u0010HJ\u001b\u0010I\u001a\u00020\t2\u0006\u0010J\u001a\u00020��H\u0096\u0002ø\u0001��¢\u0006\u0004\bK\u0010LJ\u001e\u0010M\u001a\u00020��2\u0006\u0010N\u001a\u00020\u000fH\u0086\u0002ø\u0001��ø\u0001\u0001¢\u0006\u0004\bO\u0010PJ\u001e\u0010M\u001a\u00020��2\u0006\u0010N\u001a\u00020\tH\u0086\u0002ø\u0001��ø\u0001\u0001¢\u0006\u0004\bO\u0010QJ\u001b\u0010M\u001a\u00020\u000f2\u0006\u0010J\u001a\u00020��H\u0086\u0002ø\u0001��¢\u0006\u0004\bR\u0010SJ\u001a\u0010T\u001a\u00020U2\b\u0010J\u001a\u0004\u0018\u00010VHÖ\u0003¢\u0006\u0004\bW\u0010XJ\u0010\u0010Y\u001a\u00020\tHÖ\u0001¢\u0006\u0004\bZ\u0010\rJ\r\u0010[\u001a\u00020U¢\u0006\u0004\b\\\u0010]J\u000f\u0010^\u001a\u00020UH\u0002¢\u0006\u0004\b_\u0010]J\u000f\u0010`\u001a\u00020UH\u0002¢\u0006\u0004\ba\u0010]J\r\u0010b\u001a\u00020U¢\u0006\u0004\bc\u0010]J\r\u0010d\u001a\u00020U¢\u0006\u0004\be\u0010]J\r\u0010f\u001a\u00020U¢\u0006\u0004\bg\u0010]J\u001b\u0010h\u001a\u00020��2\u0006\u0010J\u001a\u00020��H\u0086\u0002ø\u0001��¢\u0006\u0004\bi\u0010jJ\u001b\u0010k\u001a\u00020��2\u0006\u0010J\u001a\u00020��H\u0086\u0002ø\u0001��¢\u0006\u0004\bl\u0010jJ\u001e\u0010m\u001a\u00020��2\u0006\u0010N\u001a\u00020\u000fH\u0086\u0002ø\u0001��ø\u0001\u0001¢\u0006\u0004\bn\u0010PJ\u001e\u0010m\u001a\u00020��2\u0006\u0010N\u001a\u00020\tH\u0086\u0002ø\u0001��ø\u0001\u0001¢\u0006\u0004\bn\u0010QJ\u009d\u0001\u0010o\u001a\u0002Hp\"\u0004\b��\u0010p2u\u0010q\u001aq\u0012\u0013\u0012\u00110\u0003¢\u0006\f\bs\u0012\b\bt\u0012\u0004\b\b(u\u0012\u0013\u0012\u00110\t¢\u0006\f\bs\u0012\b\bt\u0012\u0004\b\b(v\u0012\u0013\u0012\u00110\t¢\u0006\f\bs\u0012\b\bt\u0012\u0004\b\b(w\u0012\u0013\u0012\u00110\t¢\u0006\f\bs\u0012\b\bt\u0012\u0004\b\b(x\u0012\u0013\u0012\u00110\t¢\u0006\f\bs\u0012\b\bt\u0012\u0004\b\b(y\u0012\u0004\u0012\u0002Hp0rH\u0086\bø\u0001\u0002\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0004\bz\u0010{J\u0088\u0001\u0010o\u001a\u0002Hp\"\u0004\b��\u0010p2`\u0010q\u001a\\\u0012\u0013\u0012\u00110\u0003¢\u0006\f\bs\u0012\b\bt\u0012\u0004\b\b(v\u0012\u0013\u0012\u00110\t¢\u0006\f\bs\u0012\b\bt\u0012\u0004\b\b(w\u0012\u0013\u0012\u00110\t¢\u0006\f\bs\u0012\b\bt\u0012\u0004\b\b(x\u0012\u0013\u0012\u00110\t¢\u0006\f\bs\u0012\b\bt\u0012\u0004\b\b(y\u0012\u0004\u0012\u0002Hp0|H\u0086\bø\u0001\u0002\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0004\bz\u0010}Js\u0010o\u001a\u0002Hp\"\u0004\b��\u0010p2K\u0010q\u001aG\u0012\u0013\u0012\u00110\u0003¢\u0006\f\bs\u0012\b\bt\u0012\u0004\b\b(w\u0012\u0013\u0012\u00110\t¢\u0006\f\bs\u0012\b\bt\u0012\u0004\b\b(x\u0012\u0013\u0012\u00110\t¢\u0006\f\bs\u0012\b\bt\u0012\u0004\b\b(y\u0012\u0004\u0012\u0002Hp0~H\u0086\bø\u0001\u0002\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0004\bz\u0010\u007fJ`\u0010o\u001a\u0002Hp\"\u0004\b��\u0010p27\u0010q\u001a3\u0012\u0013\u0012\u00110\u0003¢\u0006\f\bs\u0012\b\bt\u0012\u0004\b\b(x\u0012\u0013\u0012\u00110\t¢\u0006\f\bs\u0012\b\bt\u0012\u0004\b\b(y\u0012\u0004\u0012\u0002Hp0\u0080\u0001H\u0086\bø\u0001\u0002\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0005\bz\u0010\u0081\u0001J\u0019\u0010\u0082\u0001\u001a\u00020\u000f2\u0007\u0010\u0083\u0001\u001a\u00020=¢\u0006\u0006\b\u0084\u0001\u0010\u0085\u0001J\u0019\u0010\u0086\u0001\u001a\u00020\t2\u0007\u0010\u0083\u0001\u001a\u00020=¢\u0006\u0006\b\u0087\u0001\u0010\u0088\u0001J\u0011\u0010\u0089\u0001\u001a\u00030\u008a\u0001¢\u0006\u0006\b\u008b\u0001\u0010\u008c\u0001J\u0019\u0010\u008d\u0001\u001a\u00020\u00032\u0007\u0010\u0083\u0001\u001a\u00020=¢\u0006\u0006\b\u008e\u0001\u0010\u008f\u0001J\u0011\u0010\u0090\u0001\u001a\u00020\u0003H\u0007¢\u0006\u0005\b\u0091\u0001\u0010\u0005J\u0011\u0010\u0092\u0001\u001a\u00020\u0003H\u0007¢\u0006\u0005\b\u0093\u0001\u0010\u0005J\u0013\u0010\u0094\u0001\u001a\u00030\u008a\u0001H\u0016¢\u0006\u0006\b\u0095\u0001\u0010\u008c\u0001J%\u0010\u0094\u0001\u001a\u00030\u008a\u00012\u0007\u0010\u0083\u0001\u001a\u00020=2\t\b\u0002\u0010\u0096\u0001\u001a\u00020\t¢\u0006\u0006\b\u0095\u0001\u0010\u0097\u0001J\u0018\u0010\u0098\u0001\u001a\u00020��H\u0086\u0002ø\u0001��ø\u0001\u0001¢\u0006\u0005\b\u0099\u0001\u0010\u0005JK\u0010\u009a\u0001\u001a\u00030\u009b\u0001*\b0\u009c\u0001j\u0003`\u009d\u00012\u0007\u0010\u009e\u0001\u001a\u00020\t2\u0007\u0010\u009f\u0001\u001a\u00020\t2\u0007\u0010 \u0001\u001a\u00020\t2\b\u0010\u0083\u0001\u001a\u00030\u008a\u00012\u0007\u0010¡\u0001\u001a\u00020UH\u0002¢\u0006\u0006\b¢\u0001\u0010£\u0001R\u0017\u0010\u0006\u001a\u00020��8Fø\u0001��ø\u0001\u0001¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005R\u001a\u0010\b\u001a\u00020\t8@X\u0081\u0004¢\u0006\f\u0012\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\u000f8FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0010\u0010\u000b\u001a\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0013\u001a\u00020\u000f8FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0014\u0010\u000b\u001a\u0004\b\u0015\u0010\u0012R\u001a\u0010\u0016\u001a\u00020\u000f8FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0017\u0010\u000b\u001a\u0004\b\u0018\u0010\u0012R\u001a\u0010\u0019\u001a\u00020\u000f8FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u001a\u0010\u000b\u001a\u0004\b\u001b\u0010\u0012R\u001a\u0010\u001c\u001a\u00020\u000f8FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u001d\u0010\u000b\u001a\u0004\b\u001e\u0010\u0012R\u001a\u0010\u001f\u001a\u00020\u000f8FX\u0087\u0004¢\u0006\f\u0012\u0004\b \u0010\u000b\u001a\u0004\b!\u0010\u0012R\u001a\u0010\"\u001a\u00020\u000f8FX\u0087\u0004¢\u0006\f\u0012\u0004\b#\u0010\u000b\u001a\u0004\b$\u0010\u0012R\u0011\u0010%\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b&\u0010\u0005R\u0011\u0010'\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b(\u0010\u0005R\u0011\u0010)\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b*\u0010\u0005R\u0011\u0010+\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b,\u0010\u0005R\u0011\u0010-\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b.\u0010\u0005R\u0011\u0010/\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b0\u0010\u0005R\u0011\u00101\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b2\u0010\u0005R\u001a\u00103\u001a\u00020\t8@X\u0081\u0004¢\u0006\f\u0012\u0004\b4\u0010\u000b\u001a\u0004\b5\u0010\rR\u001a\u00106\u001a\u00020\t8@X\u0081\u0004¢\u0006\f\u0012\u0004\b7\u0010\u000b\u001a\u0004\b8\u0010\rR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n��R\u001a\u00109\u001a\u00020\t8@X\u0081\u0004¢\u0006\f\u0012\u0004\b:\u0010\u000b\u001a\u0004\b;\u0010\rR\u0014\u0010<\u001a\u00020=8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b>\u0010?R\u0015\u0010@\u001a\u00020\t8Â\u0002X\u0082\u0004¢\u0006\u0006\u001a\u0004\bA\u0010\rR\u0014\u0010B\u001a\u00020\u00038BX\u0082\u0004¢\u0006\u0006\u001a\u0004\bC\u0010\u0005\u0088\u0001\u0002\u0092\u0001\u00020\u0003ø\u0001��\u0082\u0002\u000f\n\u0002\b\u0019\n\u0002\b!\n\u0005\b\u009920\u0001¨\u0006¥\u0001"}, m53d2 = {"Lkotlin/time/Duration;", "", "rawValue", "", "constructor-impl", "(J)J", "absoluteValue", "getAbsoluteValue-UwyO8pc", "hoursComponent", "", "getHoursComponent$annotations", "()V", "getHoursComponent-impl", "(J)I", "inDays", "", "getInDays$annotations", "getInDays-impl", "(J)D", "inHours", "getInHours$annotations", "getInHours-impl", "inMicroseconds", "getInMicroseconds$annotations", "getInMicroseconds-impl", "inMilliseconds", "getInMilliseconds$annotations", "getInMilliseconds-impl", "inMinutes", "getInMinutes$annotations", "getInMinutes-impl", "inNanoseconds", "getInNanoseconds$annotations", "getInNanoseconds-impl", "inSeconds", "getInSeconds$annotations", "getInSeconds-impl", "inWholeDays", "getInWholeDays-impl", "inWholeHours", "getInWholeHours-impl", "inWholeMicroseconds", "getInWholeMicroseconds-impl", "inWholeMilliseconds", "getInWholeMilliseconds-impl", "inWholeMinutes", "getInWholeMinutes-impl", "inWholeNanoseconds", "getInWholeNanoseconds-impl", "inWholeSeconds", "getInWholeSeconds-impl", "minutesComponent", "getMinutesComponent$annotations", "getMinutesComponent-impl", "nanosecondsComponent", "getNanosecondsComponent$annotations", "getNanosecondsComponent-impl", "secondsComponent", "getSecondsComponent$annotations", "getSecondsComponent-impl", "storageUnit", "Lkotlin/time/DurationUnit;", "getStorageUnit-impl", "(J)Lkotlin/time/DurationUnit;", "unitDiscriminator", "getUnitDiscriminator-impl", "value", "getValue-impl", "addValuesMixedRanges", "thisMillis", "otherNanos", "addValuesMixedRanges-UwyO8pc", "(JJJ)J", "compareTo", "other", "compareTo-LRDsOJo", "(JJ)I", "div", "scale", "div-UwyO8pc", "(JD)J", "(JI)J", "div-LRDsOJo", "(JJ)D", "equals", "", "", "equals-impl", "(JLjava/lang/Object;)Z", "hashCode", "hashCode-impl", "isFinite", "isFinite-impl", "(J)Z", "isInMillis", "isInMillis-impl", "isInNanos", "isInNanos-impl", "isInfinite", "isInfinite-impl", "isNegative", "isNegative-impl", "isPositive", "isPositive-impl", "minus", "minus-LRDsOJo", "(JJ)J", "plus", "plus-LRDsOJo", "times", "times-UwyO8pc", "toComponents", "T", "action", "Lkotlin/Function5;", "Lkotlin/ParameterName;", "name", "days", "hours", "minutes", "seconds", "nanoseconds", "toComponents-impl", "(JLkotlin/jvm/functions/Function5;)Ljava/lang/Object;", "Lkotlin/Function4;", "(JLkotlin/jvm/functions/Function4;)Ljava/lang/Object;", "Lkotlin/Function3;", "(JLkotlin/jvm/functions/Function3;)Ljava/lang/Object;", "Lkotlin/Function2;", "(JLkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "toDouble", "unit", "toDouble-impl", "(JLkotlin/time/DurationUnit;)D", "toInt", "toInt-impl", "(JLkotlin/time/DurationUnit;)I", "toIsoString", "", "toIsoString-impl", "(J)Ljava/lang/String;", "toLong", "toLong-impl", "(JLkotlin/time/DurationUnit;)J", "toLongMilliseconds", "toLongMilliseconds-impl", "toLongNanoseconds", "toLongNanoseconds-impl", "toString", "toString-impl", "decimals", "(JLkotlin/time/DurationUnit;I)Ljava/lang/String;", "unaryMinus", "unaryMinus-UwyO8pc", "appendFractional", "", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "whole", "fractional", "fractionalSize", "isoZeroes", "appendFractional-impl", "(JLjava/lang/StringBuilder;IIILjava/lang/String;Z)V", "Companion", "kotlin-stdlib"})
@JvmInline
@WasExperimental(markerClass = {ExperimentalTime.class})
/* loaded from: Jackey Client b2.jar:kotlin/time/Duration.class */
public final class Duration implements Comparable<Duration> {
    private final long rawValue;
    @NotNull
    public static final Companion Companion = new Companion(null);
    private static final long ZERO = m2653constructorimpl(0);
    private static final long INFINITE = DurationKt.access$durationOfMillis(DurationKt.MAX_MILLIS);
    private static final long NEG_INFINITE = DurationKt.access$durationOfMillis(-4611686018427387903L);

    @PublishedApi
    public static /* synthetic */ void getHoursComponent$annotations() {
    }

    @PublishedApi
    public static /* synthetic */ void getMinutesComponent$annotations() {
    }

    @PublishedApi
    public static /* synthetic */ void getSecondsComponent$annotations() {
    }

    @PublishedApi
    public static /* synthetic */ void getNanosecondsComponent$annotations() {
    }

    @Annotations(message = "Use inWholeDays property instead or convert toDouble(DAYS) if a double value is required.", replaceWith = @ReplaceWith(expression = "toDouble(DurationUnit.DAYS)", imports = {}))
    @ExperimentalTime
    public static /* synthetic */ void getInDays$annotations() {
    }

    @Annotations(message = "Use inWholeHours property instead or convert toDouble(HOURS) if a double value is required.", replaceWith = @ReplaceWith(expression = "toDouble(DurationUnit.HOURS)", imports = {}))
    @ExperimentalTime
    public static /* synthetic */ void getInHours$annotations() {
    }

    @Annotations(message = "Use inWholeMinutes property instead or convert toDouble(MINUTES) if a double value is required.", replaceWith = @ReplaceWith(expression = "toDouble(DurationUnit.MINUTES)", imports = {}))
    @ExperimentalTime
    public static /* synthetic */ void getInMinutes$annotations() {
    }

    @Annotations(message = "Use inWholeSeconds property instead or convert toDouble(SECONDS) if a double value is required.", replaceWith = @ReplaceWith(expression = "toDouble(DurationUnit.SECONDS)", imports = {}))
    @ExperimentalTime
    public static /* synthetic */ void getInSeconds$annotations() {
    }

    @Annotations(message = "Use inWholeMilliseconds property instead or convert toDouble(MILLISECONDS) if a double value is required.", replaceWith = @ReplaceWith(expression = "toDouble(DurationUnit.MILLISECONDS)", imports = {}))
    @ExperimentalTime
    public static /* synthetic */ void getInMilliseconds$annotations() {
    }

    @Annotations(message = "Use inWholeMicroseconds property instead or convert toDouble(MICROSECONDS) if a double value is required.", replaceWith = @ReplaceWith(expression = "toDouble(DurationUnit.MICROSECONDS)", imports = {}))
    @ExperimentalTime
    public static /* synthetic */ void getInMicroseconds$annotations() {
    }

    @Annotations(message = "Use inWholeNanoseconds property instead or convert toDouble(NANOSECONDS) if a double value is required.", replaceWith = @ReplaceWith(expression = "toDouble(DurationUnit.NANOSECONDS)", imports = {}))
    @ExperimentalTime
    public static /* synthetic */ void getInNanoseconds$annotations() {
    }

    /* renamed from: hashCode-impl */
    public static int m2651hashCodeimpl(long arg0) {
        return (int) (arg0 ^ (arg0 >>> 32));
    }

    public int hashCode() {
        return m2651hashCodeimpl(this.rawValue);
    }

    /* renamed from: equals-impl */
    public static boolean m2652equalsimpl(long arg0, Object other) {
        return (other instanceof Duration) && arg0 == ((Duration) other).m2655unboximpl();
    }

    public boolean equals(Object other) {
        return m2652equalsimpl(this.rawValue, other);
    }

    /* renamed from: box-impl */
    public static final /* synthetic */ Duration m2654boximpl(long v) {
        return new Duration(v);
    }

    /* renamed from: unbox-impl */
    public final /* synthetic */ long m2655unboximpl() {
        return this.rawValue;
    }

    /* renamed from: equals-impl0 */
    public static final boolean m2656equalsimpl0(long p1, long p2) {
        return p1 == p2;
    }

    @Override // java.lang.Comparable
    public /* bridge */ /* synthetic */ int compareTo(Duration duration) {
        return m2618compareToLRDsOJo(duration.m2655unboximpl());
    }

    private /* synthetic */ Duration(long rawValue) {
        this.rawValue = rawValue;
    }

    /* renamed from: getValue-impl */
    private static final long m2598getValueimpl(long arg0) {
        return arg0 >> 1;
    }

    /* renamed from: getUnitDiscriminator-impl */
    private static final int m2599getUnitDiscriminatorimpl(long arg0) {
        return ((int) arg0) & 1;
    }

    /* renamed from: isInNanos-impl */
    private static final boolean m2600isInNanosimpl(long arg0) {
        return (((int) arg0) & 1) == 0;
    }

    /* renamed from: isInMillis-impl */
    private static final boolean m2601isInMillisimpl(long arg0) {
        return (((int) arg0) & 1) == 1;
    }

    /* renamed from: getStorageUnit-impl */
    private static final DurationUnitJvm m2602getStorageUnitimpl(long arg0) {
        return m2600isInNanosimpl(arg0) ? DurationUnitJvm.NANOSECONDS : DurationUnitJvm.MILLISECONDS;
    }

    /* renamed from: constructor-impl */
    public static long m2653constructorimpl(long rawValue) {
        if (DurationJvm.getDurationAssertionsEnabled()) {
            if (m2600isInNanosimpl(rawValue)) {
                long m2598getValueimpl = m2598getValueimpl(rawValue);
                if (!(-4611686018426999999L <= m2598getValueimpl ? m2598getValueimpl < 4611686018427000000L : false)) {
                    throw new AssertionError(m2598getValueimpl(rawValue) + " ns is out of nanoseconds range");
                }
            } else {
                long m2598getValueimpl2 = m2598getValueimpl(rawValue);
                if (!(-4611686018427387903L <= m2598getValueimpl2 ? m2598getValueimpl2 < 4611686018427387904L : false)) {
                    throw new AssertionError(m2598getValueimpl(rawValue) + " ms is out of milliseconds range");
                }
                long m2598getValueimpl3 = m2598getValueimpl(rawValue);
                if (-4611686018426L <= m2598getValueimpl3 ? m2598getValueimpl3 < 4611686018427L : false) {
                    throw new AssertionError(m2598getValueimpl(rawValue) + " ms is denormalized");
                }
            }
        }
        return rawValue;
    }

    /* compiled from: Duration.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��<\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0017\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\n\b\u0086\u0003\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010*\u001a\u00020\r2\u0006\u0010+\u001a\u00020\r2\u0006\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020-H\u0007J\u001d\u0010\f\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\rH\u0007ø\u0001��ø\u0001\u0001¢\u0006\u0004\b/\u0010\u0011J\u001d\u0010\f\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\u0012H\u0007ø\u0001��ø\u0001\u0001¢\u0006\u0004\b/\u0010\u0014J\u001d\u0010\f\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\u0015H\u0007ø\u0001��ø\u0001\u0001¢\u0006\u0004\b/\u0010\u0017J\u001d\u0010\u0018\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\rH\u0007ø\u0001��ø\u0001\u0001¢\u0006\u0004\b0\u0010\u0011J\u001d\u0010\u0018\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\u0012H\u0007ø\u0001��ø\u0001\u0001¢\u0006\u0004\b0\u0010\u0014J\u001d\u0010\u0018\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\u0015H\u0007ø\u0001��ø\u0001\u0001¢\u0006\u0004\b0\u0010\u0017J\u001d\u0010\u001b\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\rH\u0007ø\u0001��ø\u0001\u0001¢\u0006\u0004\b1\u0010\u0011J\u001d\u0010\u001b\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\u0012H\u0007ø\u0001��ø\u0001\u0001¢\u0006\u0004\b1\u0010\u0014J\u001d\u0010\u001b\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\u0015H\u0007ø\u0001��ø\u0001\u0001¢\u0006\u0004\b1\u0010\u0017J\u001d\u0010\u001e\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\rH\u0007ø\u0001��ø\u0001\u0001¢\u0006\u0004\b2\u0010\u0011J\u001d\u0010\u001e\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\u0012H\u0007ø\u0001��ø\u0001\u0001¢\u0006\u0004\b2\u0010\u0014J\u001d\u0010\u001e\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\u0015H\u0007ø\u0001��ø\u0001\u0001¢\u0006\u0004\b2\u0010\u0017J\u001d\u0010!\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\rH\u0007ø\u0001��ø\u0001\u0001¢\u0006\u0004\b3\u0010\u0011J\u001d\u0010!\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\u0012H\u0007ø\u0001��ø\u0001\u0001¢\u0006\u0004\b3\u0010\u0014J\u001d\u0010!\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\u0015H\u0007ø\u0001��ø\u0001\u0001¢\u0006\u0004\b3\u0010\u0017J\u001d\u0010$\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\rH\u0007ø\u0001��ø\u0001\u0001¢\u0006\u0004\b4\u0010\u0011J\u001d\u0010$\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\u0012H\u0007ø\u0001��ø\u0001\u0001¢\u0006\u0004\b4\u0010\u0014J\u001d\u0010$\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\u0015H\u0007ø\u0001��ø\u0001\u0001¢\u0006\u0004\b4\u0010\u0017J\u001b\u00105\u001a\u00020\u00042\u0006\u0010+\u001a\u000206ø\u0001��ø\u0001\u0001¢\u0006\u0004\b7\u00108J\u001b\u00109\u001a\u00020\u00042\u0006\u0010+\u001a\u000206ø\u0001��ø\u0001\u0001¢\u0006\u0004\b:\u00108J\u001b\u0010;\u001a\u0004\u0018\u00010\u00042\u0006\u0010+\u001a\u000206ø\u0001��ø\u0001\u0001¢\u0006\u0002\b<J\u001b\u0010=\u001a\u0004\u0018\u00010\u00042\u0006\u0010+\u001a\u000206ø\u0001��ø\u0001\u0001¢\u0006\u0002\b>J\u001d\u0010'\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\rH\u0007ø\u0001��ø\u0001\u0001¢\u0006\u0004\b?\u0010\u0011J\u001d\u0010'\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\u0012H\u0007ø\u0001��ø\u0001\u0001¢\u0006\u0004\b?\u0010\u0014J\u001d\u0010'\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\u0015H\u0007ø\u0001��ø\u0001\u0001¢\u0006\u0004\b?\u0010\u0017R\u0019\u0010\u0003\u001a\u00020\u0004ø\u0001��ø\u0001\u0001¢\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\u0005\u0010\u0006R\u001c\u0010\b\u001a\u00020\u0004X\u0080\u0004ø\u0001��ø\u0001\u0001¢\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\t\u0010\u0006R\u0019\u0010\n\u001a\u00020\u0004ø\u0001��ø\u0001\u0001¢\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\u000b\u0010\u0006R%\u0010\f\u001a\u00020\u0004*\u00020\r8Æ\u0002X\u0087\u0004ø\u0001��ø\u0001\u0001¢\u0006\f\u0012\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011R%\u0010\f\u001a\u00020\u0004*\u00020\u00128Æ\u0002X\u0087\u0004ø\u0001��ø\u0001\u0001¢\u0006\f\u0012\u0004\b\u000e\u0010\u0013\u001a\u0004\b\u0010\u0010\u0014R%\u0010\f\u001a\u00020\u0004*\u00020\u00158Æ\u0002X\u0087\u0004ø\u0001��ø\u0001\u0001¢\u0006\f\u0012\u0004\b\u000e\u0010\u0016\u001a\u0004\b\u0010\u0010\u0017R%\u0010\u0018\u001a\u00020\u0004*\u00020\r8Æ\u0002X\u0087\u0004ø\u0001��ø\u0001\u0001¢\u0006\f\u0012\u0004\b\u0019\u0010\u000f\u001a\u0004\b\u001a\u0010\u0011R%\u0010\u0018\u001a\u00020\u0004*\u00020\u00128Æ\u0002X\u0087\u0004ø\u0001��ø\u0001\u0001¢\u0006\f\u0012\u0004\b\u0019\u0010\u0013\u001a\u0004\b\u001a\u0010\u0014R%\u0010\u0018\u001a\u00020\u0004*\u00020\u00158Æ\u0002X\u0087\u0004ø\u0001��ø\u0001\u0001¢\u0006\f\u0012\u0004\b\u0019\u0010\u0016\u001a\u0004\b\u001a\u0010\u0017R%\u0010\u001b\u001a\u00020\u0004*\u00020\r8Æ\u0002X\u0087\u0004ø\u0001��ø\u0001\u0001¢\u0006\f\u0012\u0004\b\u001c\u0010\u000f\u001a\u0004\b\u001d\u0010\u0011R%\u0010\u001b\u001a\u00020\u0004*\u00020\u00128Æ\u0002X\u0087\u0004ø\u0001��ø\u0001\u0001¢\u0006\f\u0012\u0004\b\u001c\u0010\u0013\u001a\u0004\b\u001d\u0010\u0014R%\u0010\u001b\u001a\u00020\u0004*\u00020\u00158Æ\u0002X\u0087\u0004ø\u0001��ø\u0001\u0001¢\u0006\f\u0012\u0004\b\u001c\u0010\u0016\u001a\u0004\b\u001d\u0010\u0017R%\u0010\u001e\u001a\u00020\u0004*\u00020\r8Æ\u0002X\u0087\u0004ø\u0001��ø\u0001\u0001¢\u0006\f\u0012\u0004\b\u001f\u0010\u000f\u001a\u0004\b \u0010\u0011R%\u0010\u001e\u001a\u00020\u0004*\u00020\u00128Æ\u0002X\u0087\u0004ø\u0001��ø\u0001\u0001¢\u0006\f\u0012\u0004\b\u001f\u0010\u0013\u001a\u0004\b \u0010\u0014R%\u0010\u001e\u001a\u00020\u0004*\u00020\u00158Æ\u0002X\u0087\u0004ø\u0001��ø\u0001\u0001¢\u0006\f\u0012\u0004\b\u001f\u0010\u0016\u001a\u0004\b \u0010\u0017R%\u0010!\u001a\u00020\u0004*\u00020\r8Æ\u0002X\u0087\u0004ø\u0001��ø\u0001\u0001¢\u0006\f\u0012\u0004\b\"\u0010\u000f\u001a\u0004\b#\u0010\u0011R%\u0010!\u001a\u00020\u0004*\u00020\u00128Æ\u0002X\u0087\u0004ø\u0001��ø\u0001\u0001¢\u0006\f\u0012\u0004\b\"\u0010\u0013\u001a\u0004\b#\u0010\u0014R%\u0010!\u001a\u00020\u0004*\u00020\u00158Æ\u0002X\u0087\u0004ø\u0001��ø\u0001\u0001¢\u0006\f\u0012\u0004\b\"\u0010\u0016\u001a\u0004\b#\u0010\u0017R%\u0010$\u001a\u00020\u0004*\u00020\r8Æ\u0002X\u0087\u0004ø\u0001��ø\u0001\u0001¢\u0006\f\u0012\u0004\b%\u0010\u000f\u001a\u0004\b&\u0010\u0011R%\u0010$\u001a\u00020\u0004*\u00020\u00128Æ\u0002X\u0087\u0004ø\u0001��ø\u0001\u0001¢\u0006\f\u0012\u0004\b%\u0010\u0013\u001a\u0004\b&\u0010\u0014R%\u0010$\u001a\u00020\u0004*\u00020\u00158Æ\u0002X\u0087\u0004ø\u0001��ø\u0001\u0001¢\u0006\f\u0012\u0004\b%\u0010\u0016\u001a\u0004\b&\u0010\u0017R%\u0010'\u001a\u00020\u0004*\u00020\r8Æ\u0002X\u0087\u0004ø\u0001��ø\u0001\u0001¢\u0006\f\u0012\u0004\b(\u0010\u000f\u001a\u0004\b)\u0010\u0011R%\u0010'\u001a\u00020\u0004*\u00020\u00128Æ\u0002X\u0087\u0004ø\u0001��ø\u0001\u0001¢\u0006\f\u0012\u0004\b(\u0010\u0013\u001a\u0004\b)\u0010\u0014R%\u0010'\u001a\u00020\u0004*\u00020\u00158Æ\u0002X\u0087\u0004ø\u0001��ø\u0001\u0001¢\u0006\f\u0012\u0004\b(\u0010\u0016\u001a\u0004\b)\u0010\u0017\u0082\u0002\b\n\u0002\b\u0019\n\u0002\b!¨\u0006@"}, m53d2 = {"Lkotlin/time/Duration$Companion;", "", "()V", "INFINITE", "Lkotlin/time/Duration;", "getINFINITE-UwyO8pc", "()J", "J", "NEG_INFINITE", "getNEG_INFINITE-UwyO8pc$kotlin_stdlib", "ZERO", "getZERO-UwyO8pc", "days", "", "getDays-UwyO8pc$annotations", "(D)V", "getDays-UwyO8pc", "(D)J", "", "(I)V", "(I)J", "", "(J)V", "(J)J", "hours", "getHours-UwyO8pc$annotations", "getHours-UwyO8pc", "microseconds", "getMicroseconds-UwyO8pc$annotations", "getMicroseconds-UwyO8pc", "milliseconds", "getMilliseconds-UwyO8pc$annotations", "getMilliseconds-UwyO8pc", "minutes", "getMinutes-UwyO8pc$annotations", "getMinutes-UwyO8pc", "nanoseconds", "getNanoseconds-UwyO8pc$annotations", "getNanoseconds-UwyO8pc", "seconds", "getSeconds-UwyO8pc$annotations", "getSeconds-UwyO8pc", "convert", "value", "sourceUnit", "Lkotlin/time/DurationUnit;", "targetUnit", "days-UwyO8pc", "hours-UwyO8pc", "microseconds-UwyO8pc", "milliseconds-UwyO8pc", "minutes-UwyO8pc", "nanoseconds-UwyO8pc", "parse", "", "parse-UwyO8pc", "(Ljava/lang/String;)J", "parseIsoString", "parseIsoString-UwyO8pc", "parseIsoStringOrNull", "parseIsoStringOrNull-FghU774", "parseOrNull", "parseOrNull-FghU774", "seconds-UwyO8pc", "kotlin-stdlib"})
    /* loaded from: Jackey Client b2.jar:kotlin/time/Duration$Companion.class */
    public static final class Companion {
        @InlineOnly
        /* renamed from: getNanoseconds-UwyO8pc$annotations */
        public static /* synthetic */ void m2662getNanosecondsUwyO8pc$annotations(int i) {
        }

        @InlineOnly
        /* renamed from: getNanoseconds-UwyO8pc$annotations */
        public static /* synthetic */ void m2664getNanosecondsUwyO8pc$annotations(long j) {
        }

        @InlineOnly
        /* renamed from: getNanoseconds-UwyO8pc$annotations */
        public static /* synthetic */ void m2666getNanosecondsUwyO8pc$annotations(double d) {
        }

        @InlineOnly
        /* renamed from: getMicroseconds-UwyO8pc$annotations */
        public static /* synthetic */ void m2668getMicrosecondsUwyO8pc$annotations(int i) {
        }

        @InlineOnly
        /* renamed from: getMicroseconds-UwyO8pc$annotations */
        public static /* synthetic */ void m2670getMicrosecondsUwyO8pc$annotations(long j) {
        }

        @InlineOnly
        /* renamed from: getMicroseconds-UwyO8pc$annotations */
        public static /* synthetic */ void m2672getMicrosecondsUwyO8pc$annotations(double d) {
        }

        @InlineOnly
        /* renamed from: getMilliseconds-UwyO8pc$annotations */
        public static /* synthetic */ void m2674getMillisecondsUwyO8pc$annotations(int i) {
        }

        @InlineOnly
        /* renamed from: getMilliseconds-UwyO8pc$annotations */
        public static /* synthetic */ void m2676getMillisecondsUwyO8pc$annotations(long j) {
        }

        @InlineOnly
        /* renamed from: getMilliseconds-UwyO8pc$annotations */
        public static /* synthetic */ void m2678getMillisecondsUwyO8pc$annotations(double d) {
        }

        @InlineOnly
        /* renamed from: getSeconds-UwyO8pc$annotations */
        public static /* synthetic */ void m2680getSecondsUwyO8pc$annotations(int i) {
        }

        @InlineOnly
        /* renamed from: getSeconds-UwyO8pc$annotations */
        public static /* synthetic */ void m2682getSecondsUwyO8pc$annotations(long j) {
        }

        @InlineOnly
        /* renamed from: getSeconds-UwyO8pc$annotations */
        public static /* synthetic */ void m2684getSecondsUwyO8pc$annotations(double d) {
        }

        @InlineOnly
        /* renamed from: getMinutes-UwyO8pc$annotations */
        public static /* synthetic */ void m2686getMinutesUwyO8pc$annotations(int i) {
        }

        @InlineOnly
        /* renamed from: getMinutes-UwyO8pc$annotations */
        public static /* synthetic */ void m2688getMinutesUwyO8pc$annotations(long j) {
        }

        @InlineOnly
        /* renamed from: getMinutes-UwyO8pc$annotations */
        public static /* synthetic */ void m2690getMinutesUwyO8pc$annotations(double d) {
        }

        @InlineOnly
        /* renamed from: getHours-UwyO8pc$annotations */
        public static /* synthetic */ void m2692getHoursUwyO8pc$annotations(int i) {
        }

        @InlineOnly
        /* renamed from: getHours-UwyO8pc$annotations */
        public static /* synthetic */ void m2694getHoursUwyO8pc$annotations(long j) {
        }

        @InlineOnly
        /* renamed from: getHours-UwyO8pc$annotations */
        public static /* synthetic */ void m2696getHoursUwyO8pc$annotations(double d) {
        }

        @InlineOnly
        /* renamed from: getDays-UwyO8pc$annotations */
        public static /* synthetic */ void m2698getDaysUwyO8pc$annotations(int i) {
        }

        @InlineOnly
        /* renamed from: getDays-UwyO8pc$annotations */
        public static /* synthetic */ void m2700getDaysUwyO8pc$annotations(long j) {
        }

        @InlineOnly
        /* renamed from: getDays-UwyO8pc$annotations */
        public static /* synthetic */ void m2702getDaysUwyO8pc$annotations(double d) {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        private Companion() {
        }

        /* renamed from: getZERO-UwyO8pc */
        public final long m2658getZEROUwyO8pc() {
            return Duration.ZERO;
        }

        /* renamed from: getINFINITE-UwyO8pc */
        public final long m2659getINFINITEUwyO8pc() {
            return Duration.INFINITE;
        }

        /* renamed from: getNEG_INFINITE-UwyO8pc$kotlin_stdlib */
        public final long m2660getNEG_INFINITEUwyO8pc$kotlin_stdlib() {
            return Duration.NEG_INFINITE;
        }

        @ExperimentalTime
        public final double convert(double value, @NotNull DurationUnitJvm sourceUnit, @NotNull DurationUnitJvm targetUnit) {
            Intrinsics.checkNotNullParameter(sourceUnit, "sourceUnit");
            Intrinsics.checkNotNullParameter(targetUnit, "targetUnit");
            return DurationUnitKt.convertDurationUnit(value, sourceUnit, targetUnit);
        }

        /* renamed from: getNanoseconds-UwyO8pc */
        private final long m2661getNanosecondsUwyO8pc(int $this$nanoseconds) {
            return DurationKt.toDuration($this$nanoseconds, DurationUnitJvm.NANOSECONDS);
        }

        /* renamed from: getNanoseconds-UwyO8pc */
        private final long m2663getNanosecondsUwyO8pc(long $this$nanoseconds) {
            return DurationKt.toDuration($this$nanoseconds, DurationUnitJvm.NANOSECONDS);
        }

        /* renamed from: getNanoseconds-UwyO8pc */
        private final long m2665getNanosecondsUwyO8pc(double $this$nanoseconds) {
            return DurationKt.toDuration($this$nanoseconds, DurationUnitJvm.NANOSECONDS);
        }

        /* renamed from: getMicroseconds-UwyO8pc */
        private final long m2667getMicrosecondsUwyO8pc(int $this$microseconds) {
            return DurationKt.toDuration($this$microseconds, DurationUnitJvm.MICROSECONDS);
        }

        /* renamed from: getMicroseconds-UwyO8pc */
        private final long m2669getMicrosecondsUwyO8pc(long $this$microseconds) {
            return DurationKt.toDuration($this$microseconds, DurationUnitJvm.MICROSECONDS);
        }

        /* renamed from: getMicroseconds-UwyO8pc */
        private final long m2671getMicrosecondsUwyO8pc(double $this$microseconds) {
            return DurationKt.toDuration($this$microseconds, DurationUnitJvm.MICROSECONDS);
        }

        /* renamed from: getMilliseconds-UwyO8pc */
        private final long m2673getMillisecondsUwyO8pc(int $this$milliseconds) {
            return DurationKt.toDuration($this$milliseconds, DurationUnitJvm.MILLISECONDS);
        }

        /* renamed from: getMilliseconds-UwyO8pc */
        private final long m2675getMillisecondsUwyO8pc(long $this$milliseconds) {
            return DurationKt.toDuration($this$milliseconds, DurationUnitJvm.MILLISECONDS);
        }

        /* renamed from: getMilliseconds-UwyO8pc */
        private final long m2677getMillisecondsUwyO8pc(double $this$milliseconds) {
            return DurationKt.toDuration($this$milliseconds, DurationUnitJvm.MILLISECONDS);
        }

        /* renamed from: getSeconds-UwyO8pc */
        private final long m2679getSecondsUwyO8pc(int $this$seconds) {
            return DurationKt.toDuration($this$seconds, DurationUnitJvm.SECONDS);
        }

        /* renamed from: getSeconds-UwyO8pc */
        private final long m2681getSecondsUwyO8pc(long $this$seconds) {
            return DurationKt.toDuration($this$seconds, DurationUnitJvm.SECONDS);
        }

        /* renamed from: getSeconds-UwyO8pc */
        private final long m2683getSecondsUwyO8pc(double $this$seconds) {
            return DurationKt.toDuration($this$seconds, DurationUnitJvm.SECONDS);
        }

        /* renamed from: getMinutes-UwyO8pc */
        private final long m2685getMinutesUwyO8pc(int $this$minutes) {
            return DurationKt.toDuration($this$minutes, DurationUnitJvm.MINUTES);
        }

        /* renamed from: getMinutes-UwyO8pc */
        private final long m2687getMinutesUwyO8pc(long $this$minutes) {
            return DurationKt.toDuration($this$minutes, DurationUnitJvm.MINUTES);
        }

        /* renamed from: getMinutes-UwyO8pc */
        private final long m2689getMinutesUwyO8pc(double $this$minutes) {
            return DurationKt.toDuration($this$minutes, DurationUnitJvm.MINUTES);
        }

        /* renamed from: getHours-UwyO8pc */
        private final long m2691getHoursUwyO8pc(int $this$hours) {
            return DurationKt.toDuration($this$hours, DurationUnitJvm.HOURS);
        }

        /* renamed from: getHours-UwyO8pc */
        private final long m2693getHoursUwyO8pc(long $this$hours) {
            return DurationKt.toDuration($this$hours, DurationUnitJvm.HOURS);
        }

        /* renamed from: getHours-UwyO8pc */
        private final long m2695getHoursUwyO8pc(double $this$hours) {
            return DurationKt.toDuration($this$hours, DurationUnitJvm.HOURS);
        }

        /* renamed from: getDays-UwyO8pc */
        private final long m2697getDaysUwyO8pc(int $this$days) {
            return DurationKt.toDuration($this$days, DurationUnitJvm.DAYS);
        }

        /* renamed from: getDays-UwyO8pc */
        private final long m2699getDaysUwyO8pc(long $this$days) {
            return DurationKt.toDuration($this$days, DurationUnitJvm.DAYS);
        }

        /* renamed from: getDays-UwyO8pc */
        private final long m2701getDaysUwyO8pc(double $this$days) {
            return DurationKt.toDuration($this$days, DurationUnitJvm.DAYS);
        }

        @SinceKotlin(version = "1.5")
        @Annotations(message = "Use 'Int.nanoseconds' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "value.nanoseconds", imports = {"kotlin.time.Duration.Companion.nanoseconds"}))
        @DeprecatedSinceKotlin(warningSince = "1.6")
        @ExperimentalTime
        /* renamed from: nanoseconds-UwyO8pc */
        public final long m2703nanosecondsUwyO8pc(int value) {
            return DurationKt.toDuration(value, DurationUnitJvm.NANOSECONDS);
        }

        @SinceKotlin(version = "1.5")
        @Annotations(message = "Use 'Long.nanoseconds' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "value.nanoseconds", imports = {"kotlin.time.Duration.Companion.nanoseconds"}))
        @DeprecatedSinceKotlin(warningSince = "1.6")
        @ExperimentalTime
        /* renamed from: nanoseconds-UwyO8pc */
        public final long m2704nanosecondsUwyO8pc(long value) {
            return DurationKt.toDuration(value, DurationUnitJvm.NANOSECONDS);
        }

        @SinceKotlin(version = "1.5")
        @Annotations(message = "Use 'Double.nanoseconds' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "value.nanoseconds", imports = {"kotlin.time.Duration.Companion.nanoseconds"}))
        @DeprecatedSinceKotlin(warningSince = "1.6")
        @ExperimentalTime
        /* renamed from: nanoseconds-UwyO8pc */
        public final long m2705nanosecondsUwyO8pc(double value) {
            return DurationKt.toDuration(value, DurationUnitJvm.NANOSECONDS);
        }

        @SinceKotlin(version = "1.5")
        @Annotations(message = "Use 'Int.microseconds' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "value.microseconds", imports = {"kotlin.time.Duration.Companion.microseconds"}))
        @DeprecatedSinceKotlin(warningSince = "1.6")
        @ExperimentalTime
        /* renamed from: microseconds-UwyO8pc */
        public final long m2706microsecondsUwyO8pc(int value) {
            return DurationKt.toDuration(value, DurationUnitJvm.MICROSECONDS);
        }

        @SinceKotlin(version = "1.5")
        @Annotations(message = "Use 'Long.microseconds' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "value.microseconds", imports = {"kotlin.time.Duration.Companion.microseconds"}))
        @DeprecatedSinceKotlin(warningSince = "1.6")
        @ExperimentalTime
        /* renamed from: microseconds-UwyO8pc */
        public final long m2707microsecondsUwyO8pc(long value) {
            return DurationKt.toDuration(value, DurationUnitJvm.MICROSECONDS);
        }

        @SinceKotlin(version = "1.5")
        @Annotations(message = "Use 'Double.microseconds' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "value.microseconds", imports = {"kotlin.time.Duration.Companion.microseconds"}))
        @DeprecatedSinceKotlin(warningSince = "1.6")
        @ExperimentalTime
        /* renamed from: microseconds-UwyO8pc */
        public final long m2708microsecondsUwyO8pc(double value) {
            return DurationKt.toDuration(value, DurationUnitJvm.MICROSECONDS);
        }

        @SinceKotlin(version = "1.5")
        @Annotations(message = "Use 'Int.milliseconds' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "value.milliseconds", imports = {"kotlin.time.Duration.Companion.milliseconds"}))
        @DeprecatedSinceKotlin(warningSince = "1.6")
        @ExperimentalTime
        /* renamed from: milliseconds-UwyO8pc */
        public final long m2709millisecondsUwyO8pc(int value) {
            return DurationKt.toDuration(value, DurationUnitJvm.MILLISECONDS);
        }

        @SinceKotlin(version = "1.5")
        @Annotations(message = "Use 'Long.milliseconds' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "value.milliseconds", imports = {"kotlin.time.Duration.Companion.milliseconds"}))
        @DeprecatedSinceKotlin(warningSince = "1.6")
        @ExperimentalTime
        /* renamed from: milliseconds-UwyO8pc */
        public final long m2710millisecondsUwyO8pc(long value) {
            return DurationKt.toDuration(value, DurationUnitJvm.MILLISECONDS);
        }

        @SinceKotlin(version = "1.5")
        @Annotations(message = "Use 'Double.milliseconds' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "value.milliseconds", imports = {"kotlin.time.Duration.Companion.milliseconds"}))
        @DeprecatedSinceKotlin(warningSince = "1.6")
        @ExperimentalTime
        /* renamed from: milliseconds-UwyO8pc */
        public final long m2711millisecondsUwyO8pc(double value) {
            return DurationKt.toDuration(value, DurationUnitJvm.MILLISECONDS);
        }

        @SinceKotlin(version = "1.5")
        @Annotations(message = "Use 'Int.seconds' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "value.seconds", imports = {"kotlin.time.Duration.Companion.seconds"}))
        @DeprecatedSinceKotlin(warningSince = "1.6")
        @ExperimentalTime
        /* renamed from: seconds-UwyO8pc */
        public final long m2712secondsUwyO8pc(int value) {
            return DurationKt.toDuration(value, DurationUnitJvm.SECONDS);
        }

        @SinceKotlin(version = "1.5")
        @Annotations(message = "Use 'Long.seconds' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "value.seconds", imports = {"kotlin.time.Duration.Companion.seconds"}))
        @DeprecatedSinceKotlin(warningSince = "1.6")
        @ExperimentalTime
        /* renamed from: seconds-UwyO8pc */
        public final long m2713secondsUwyO8pc(long value) {
            return DurationKt.toDuration(value, DurationUnitJvm.SECONDS);
        }

        @SinceKotlin(version = "1.5")
        @Annotations(message = "Use 'Double.seconds' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "value.seconds", imports = {"kotlin.time.Duration.Companion.seconds"}))
        @DeprecatedSinceKotlin(warningSince = "1.6")
        @ExperimentalTime
        /* renamed from: seconds-UwyO8pc */
        public final long m2714secondsUwyO8pc(double value) {
            return DurationKt.toDuration(value, DurationUnitJvm.SECONDS);
        }

        @SinceKotlin(version = "1.5")
        @Annotations(message = "Use 'Int.minutes' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "value.minutes", imports = {"kotlin.time.Duration.Companion.minutes"}))
        @DeprecatedSinceKotlin(warningSince = "1.6")
        @ExperimentalTime
        /* renamed from: minutes-UwyO8pc */
        public final long m2715minutesUwyO8pc(int value) {
            return DurationKt.toDuration(value, DurationUnitJvm.MINUTES);
        }

        @SinceKotlin(version = "1.5")
        @Annotations(message = "Use 'Long.minutes' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "value.minutes", imports = {"kotlin.time.Duration.Companion.minutes"}))
        @DeprecatedSinceKotlin(warningSince = "1.6")
        @ExperimentalTime
        /* renamed from: minutes-UwyO8pc */
        public final long m2716minutesUwyO8pc(long value) {
            return DurationKt.toDuration(value, DurationUnitJvm.MINUTES);
        }

        @SinceKotlin(version = "1.5")
        @Annotations(message = "Use 'Double.minutes' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "value.minutes", imports = {"kotlin.time.Duration.Companion.minutes"}))
        @DeprecatedSinceKotlin(warningSince = "1.6")
        @ExperimentalTime
        /* renamed from: minutes-UwyO8pc */
        public final long m2717minutesUwyO8pc(double value) {
            return DurationKt.toDuration(value, DurationUnitJvm.MINUTES);
        }

        @SinceKotlin(version = "1.5")
        @Annotations(message = "Use 'Int.hours' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "value.hours", imports = {"kotlin.time.Duration.Companion.hours"}))
        @DeprecatedSinceKotlin(warningSince = "1.6")
        @ExperimentalTime
        /* renamed from: hours-UwyO8pc */
        public final long m2718hoursUwyO8pc(int value) {
            return DurationKt.toDuration(value, DurationUnitJvm.HOURS);
        }

        @SinceKotlin(version = "1.5")
        @Annotations(message = "Use 'Long.hours' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "value.hours", imports = {"kotlin.time.Duration.Companion.hours"}))
        @DeprecatedSinceKotlin(warningSince = "1.6")
        @ExperimentalTime
        /* renamed from: hours-UwyO8pc */
        public final long m2719hoursUwyO8pc(long value) {
            return DurationKt.toDuration(value, DurationUnitJvm.HOURS);
        }

        @SinceKotlin(version = "1.5")
        @Annotations(message = "Use 'Double.hours' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "value.hours", imports = {"kotlin.time.Duration.Companion.hours"}))
        @DeprecatedSinceKotlin(warningSince = "1.6")
        @ExperimentalTime
        /* renamed from: hours-UwyO8pc */
        public final long m2720hoursUwyO8pc(double value) {
            return DurationKt.toDuration(value, DurationUnitJvm.HOURS);
        }

        @SinceKotlin(version = "1.5")
        @Annotations(message = "Use 'Int.days' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "value.days", imports = {"kotlin.time.Duration.Companion.days"}))
        @DeprecatedSinceKotlin(warningSince = "1.6")
        @ExperimentalTime
        /* renamed from: days-UwyO8pc */
        public final long m2721daysUwyO8pc(int value) {
            return DurationKt.toDuration(value, DurationUnitJvm.DAYS);
        }

        @SinceKotlin(version = "1.5")
        @Annotations(message = "Use 'Long.days' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "value.days", imports = {"kotlin.time.Duration.Companion.days"}))
        @DeprecatedSinceKotlin(warningSince = "1.6")
        @ExperimentalTime
        /* renamed from: days-UwyO8pc */
        public final long m2722daysUwyO8pc(long value) {
            return DurationKt.toDuration(value, DurationUnitJvm.DAYS);
        }

        @SinceKotlin(version = "1.5")
        @Annotations(message = "Use 'Double.days' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "value.days", imports = {"kotlin.time.Duration.Companion.days"}))
        @DeprecatedSinceKotlin(warningSince = "1.6")
        @ExperimentalTime
        /* renamed from: days-UwyO8pc */
        public final long m2723daysUwyO8pc(double value) {
            return DurationKt.toDuration(value, DurationUnitJvm.DAYS);
        }

        /* renamed from: parse-UwyO8pc */
        public final long m2724parseUwyO8pc(@NotNull String value) {
            long parseDuration;
            Intrinsics.checkNotNullParameter(value, "value");
            try {
                parseDuration = DurationKt.parseDuration(value, false);
                return parseDuration;
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid duration string format: '" + value + "'.", e);
            }
        }

        /* renamed from: parseIsoString-UwyO8pc */
        public final long m2725parseIsoStringUwyO8pc(@NotNull String value) {
            long parseDuration;
            Intrinsics.checkNotNullParameter(value, "value");
            try {
                parseDuration = DurationKt.parseDuration(value, true);
                return parseDuration;
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid ISO duration string format: '" + value + "'.", e);
            }
        }

        @Nullable
        /* renamed from: parseOrNull-FghU774 */
        public final Duration m2726parseOrNullFghU774(@NotNull String value) {
            Duration duration;
            long parseDuration;
            Intrinsics.checkNotNullParameter(value, "value");
            try {
                parseDuration = DurationKt.parseDuration(value, false);
                duration = Duration.m2654boximpl(parseDuration);
            } catch (IllegalArgumentException e) {
                duration = null;
            }
            return duration;
        }

        @Nullable
        /* renamed from: parseIsoStringOrNull-FghU774 */
        public final Duration m2727parseIsoStringOrNullFghU774(@NotNull String value) {
            Duration duration;
            long parseDuration;
            Intrinsics.checkNotNullParameter(value, "value");
            try {
                parseDuration = DurationKt.parseDuration(value, true);
                duration = Duration.m2654boximpl(parseDuration);
            } catch (IllegalArgumentException e) {
                duration = null;
            }
            return duration;
        }
    }

    /* renamed from: unaryMinus-UwyO8pc */
    public static final long m2603unaryMinusUwyO8pc(long arg0) {
        long durationOf;
        durationOf = DurationKt.durationOf(-m2598getValueimpl(arg0), ((int) arg0) & 1);
        return durationOf;
    }

    /* renamed from: plus-LRDsOJo */
    public static final long m2604plusLRDsOJo(long arg0, long other) {
        long durationOfMillisNormalized;
        long durationOfNanosNormalized;
        if (m2614isInfiniteimpl(arg0)) {
            if (m2615isFiniteimpl(other) || (arg0 ^ other) >= 0) {
                return arg0;
            }
            throw new IllegalArgumentException("Summing infinite durations of different signs yields an undefined result.");
        } else if (m2614isInfiniteimpl(other)) {
            return other;
        } else {
            if ((((int) arg0) & 1) == (((int) other) & 1)) {
                long result = m2598getValueimpl(arg0) + m2598getValueimpl(other);
                if (m2600isInNanosimpl(arg0)) {
                    durationOfNanosNormalized = DurationKt.durationOfNanosNormalized(result);
                    return durationOfNanosNormalized;
                }
                durationOfMillisNormalized = DurationKt.durationOfMillisNormalized(result);
                return durationOfMillisNormalized;
            } else if (m2601isInMillisimpl(arg0)) {
                return m2605addValuesMixedRangesUwyO8pc(arg0, m2598getValueimpl(arg0), m2598getValueimpl(other));
            } else {
                return m2605addValuesMixedRangesUwyO8pc(arg0, m2598getValueimpl(other), m2598getValueimpl(arg0));
            }
        }
    }

    /* renamed from: addValuesMixedRanges-UwyO8pc */
    private static final long m2605addValuesMixedRangesUwyO8pc(long arg0, long thisMillis, long otherNanos) {
        long otherMillis;
        long durationOfMillis;
        long millisToNanos;
        long millisToNanos2;
        long durationOfNanos;
        otherMillis = DurationKt.nanosToMillis(otherNanos);
        long resultMillis = thisMillis + otherMillis;
        if (-4611686018426L <= resultMillis ? resultMillis < 4611686018427L : false) {
            millisToNanos = DurationKt.millisToNanos(otherMillis);
            long otherNanoRemainder = otherNanos - millisToNanos;
            millisToNanos2 = DurationKt.millisToNanos(resultMillis);
            durationOfNanos = DurationKt.durationOfNanos(millisToNanos2 + otherNanoRemainder);
            return durationOfNanos;
        }
        durationOfMillis = DurationKt.durationOfMillis(RangesKt.coerceIn(resultMillis, -4611686018427387903L, (long) DurationKt.MAX_MILLIS));
        return durationOfMillis;
    }

    /* renamed from: minus-LRDsOJo */
    public static final long m2606minusLRDsOJo(long arg0, long other) {
        return m2604plusLRDsOJo(arg0, m2603unaryMinusUwyO8pc(other));
    }

    /* renamed from: times-UwyO8pc */
    public static final long m2607timesUwyO8pc(long arg0, int scale) {
        long durationOfMillis;
        long millis;
        long millisToNanos;
        long nanosToMillis;
        long durationOfMillis2;
        long durationOfNanosNormalized;
        long durationOfNanos;
        if (m2614isInfiniteimpl(arg0)) {
            if (scale == 0) {
                throw new IllegalArgumentException("Multiplying infinite duration by zero yields an undefined result.");
            }
            return scale > 0 ? arg0 : m2603unaryMinusUwyO8pc(arg0);
        } else if (scale == 0) {
            return ZERO;
        } else {
            long value = m2598getValueimpl(arg0);
            long result = value * scale;
            if (m2600isInNanosimpl(arg0)) {
                if (value <= 2147483647L ? -2147483647L <= value : false) {
                    durationOfNanos = DurationKt.durationOfNanos(result);
                    return durationOfNanos;
                } else if (result / scale == value) {
                    durationOfNanosNormalized = DurationKt.durationOfNanosNormalized(result);
                    return durationOfNanosNormalized;
                } else {
                    millis = DurationKt.nanosToMillis(value);
                    millisToNanos = DurationKt.millisToNanos(millis);
                    long remNanos = value - millisToNanos;
                    long resultMillis = millis * scale;
                    nanosToMillis = DurationKt.nanosToMillis(remNanos * scale);
                    long totalMillis = resultMillis + nanosToMillis;
                    if (resultMillis / scale != millis || (totalMillis ^ resultMillis) < 0) {
                        return MathKt.getSign(value) * MathKt.getSign(scale) > 0 ? INFINITE : NEG_INFINITE;
                    }
                    durationOfMillis2 = DurationKt.durationOfMillis(RangesKt.coerceIn(totalMillis, new LongRange(-4611686018427387903L, DurationKt.MAX_MILLIS)));
                    return durationOfMillis2;
                }
            } else if (result / scale != value) {
                return MathKt.getSign(value) * MathKt.getSign(scale) > 0 ? INFINITE : NEG_INFINITE;
            } else {
                durationOfMillis = DurationKt.durationOfMillis(RangesKt.coerceIn(result, new LongRange(-4611686018427387903L, DurationKt.MAX_MILLIS)));
                return durationOfMillis;
            }
        }
    }

    /* renamed from: times-UwyO8pc */
    public static final long m2608timesUwyO8pc(long arg0, double scale) {
        int intScale = MathKt.roundToInt(scale);
        if (((double) intScale) == scale) {
            return m2607timesUwyO8pc(arg0, intScale);
        }
        DurationUnitJvm unit = m2602getStorageUnitimpl(arg0);
        double result = m2627toDoubleimpl(arg0, unit) * scale;
        return DurationKt.toDuration(result, unit);
    }

    /* renamed from: div-UwyO8pc */
    public static final long m2609divUwyO8pc(long arg0, int scale) {
        long durationOfMillis;
        long millisToNanos;
        long millisToNanos2;
        long durationOfNanos;
        long durationOfNanos2;
        if (scale == 0) {
            if (m2613isPositiveimpl(arg0)) {
                return INFINITE;
            }
            if (!m2612isNegativeimpl(arg0)) {
                throw new IllegalArgumentException("Dividing zero duration by zero yields an undefined result.");
            }
            return NEG_INFINITE;
        } else if (m2600isInNanosimpl(arg0)) {
            durationOfNanos2 = DurationKt.durationOfNanos(m2598getValueimpl(arg0) / scale);
            return durationOfNanos2;
        } else if (m2614isInfiniteimpl(arg0)) {
            return m2607timesUwyO8pc(arg0, MathKt.getSign(scale));
        } else {
            long result = m2598getValueimpl(arg0) / scale;
            if (-4611686018426L <= result ? result < 4611686018427L : false) {
                millisToNanos = DurationKt.millisToNanos(m2598getValueimpl(arg0) - (result * scale));
                long rem = millisToNanos / scale;
                millisToNanos2 = DurationKt.millisToNanos(result);
                durationOfNanos = DurationKt.durationOfNanos(millisToNanos2 + rem);
                return durationOfNanos;
            }
            durationOfMillis = DurationKt.durationOfMillis(result);
            return durationOfMillis;
        }
    }

    /* renamed from: div-UwyO8pc */
    public static final long m2610divUwyO8pc(long arg0, double scale) {
        int intScale = MathKt.roundToInt(scale);
        if ((((double) intScale) == scale) && intScale != 0) {
            return m2609divUwyO8pc(arg0, intScale);
        }
        DurationUnitJvm unit = m2602getStorageUnitimpl(arg0);
        double result = m2627toDoubleimpl(arg0, unit) / scale;
        return DurationKt.toDuration(result, unit);
    }

    /* renamed from: div-LRDsOJo */
    public static final double m2611divLRDsOJo(long arg0, long other) {
        DurationUnitJvm coarserUnit = (DurationUnitJvm) ComparisonsKt.maxOf(m2602getStorageUnitimpl(arg0), m2602getStorageUnitimpl(other));
        return m2627toDoubleimpl(arg0, coarserUnit) / m2627toDoubleimpl(other, coarserUnit);
    }

    /* renamed from: isNegative-impl */
    public static final boolean m2612isNegativeimpl(long arg0) {
        return arg0 < 0;
    }

    /* renamed from: isPositive-impl */
    public static final boolean m2613isPositiveimpl(long arg0) {
        return arg0 > 0;
    }

    /* renamed from: isInfinite-impl */
    public static final boolean m2614isInfiniteimpl(long arg0) {
        return arg0 == INFINITE || arg0 == NEG_INFINITE;
    }

    /* renamed from: isFinite-impl */
    public static final boolean m2615isFiniteimpl(long arg0) {
        return !m2614isInfiniteimpl(arg0);
    }

    /* renamed from: getAbsoluteValue-UwyO8pc */
    public static final long m2616getAbsoluteValueUwyO8pc(long arg0) {
        return m2612isNegativeimpl(arg0) ? m2603unaryMinusUwyO8pc(arg0) : arg0;
    }

    /* renamed from: compareTo-LRDsOJo */
    public int m2618compareToLRDsOJo(long other) {
        return m2617compareToLRDsOJo(this.rawValue, other);
    }

    /* renamed from: compareTo-LRDsOJo */
    public static int m2617compareToLRDsOJo(long arg0, long other) {
        long compareBits = arg0 ^ other;
        if (compareBits < 0 || (((int) compareBits) & 1) == 0) {
            return Intrinsics.compare(arg0, other);
        }
        int r = (((int) arg0) & 1) - (((int) other) & 1);
        return m2612isNegativeimpl(arg0) ? -r : r;
    }

    /* renamed from: toComponents-impl */
    public static final <T> T m2619toComponentsimpl(long arg0, @NotNull Function5<? super Long, ? super Integer, ? super Integer, ? super Integer, ? super Integer, ? extends T> action) {
        Intrinsics.checkNotNullParameter(action, "action");
        return action.invoke(Long.valueOf(m2637getInWholeDaysimpl(arg0)), Integer.valueOf(m2623getHoursComponentimpl(arg0)), Integer.valueOf(m2624getMinutesComponentimpl(arg0)), Integer.valueOf(m2625getSecondsComponentimpl(arg0)), Integer.valueOf(m2626getNanosecondsComponentimpl(arg0)));
    }

    /* renamed from: toComponents-impl */
    public static final <T> T m2620toComponentsimpl(long arg0, @NotNull Function4<? super Long, ? super Integer, ? super Integer, ? super Integer, ? extends T> action) {
        Intrinsics.checkNotNullParameter(action, "action");
        return action.invoke(Long.valueOf(m2638getInWholeHoursimpl(arg0)), Integer.valueOf(m2624getMinutesComponentimpl(arg0)), Integer.valueOf(m2625getSecondsComponentimpl(arg0)), Integer.valueOf(m2626getNanosecondsComponentimpl(arg0)));
    }

    /* renamed from: toComponents-impl */
    public static final <T> T m2621toComponentsimpl(long arg0, @NotNull Function3<? super Long, ? super Integer, ? super Integer, ? extends T> action) {
        Intrinsics.checkNotNullParameter(action, "action");
        return action.invoke(Long.valueOf(m2639getInWholeMinutesimpl(arg0)), Integer.valueOf(m2625getSecondsComponentimpl(arg0)), Integer.valueOf(m2626getNanosecondsComponentimpl(arg0)));
    }

    /* renamed from: toComponents-impl */
    public static final <T> T m2622toComponentsimpl(long arg0, @NotNull Function2<? super Long, ? super Integer, ? extends T> action) {
        Intrinsics.checkNotNullParameter(action, "action");
        return action.invoke(Long.valueOf(m2640getInWholeSecondsimpl(arg0)), Integer.valueOf(m2626getNanosecondsComponentimpl(arg0)));
    }

    /* renamed from: getHoursComponent-impl */
    public static final int m2623getHoursComponentimpl(long arg0) {
        if (m2614isInfiniteimpl(arg0)) {
            return 0;
        }
        return (int) (m2638getInWholeHoursimpl(arg0) % 24);
    }

    /* renamed from: getMinutesComponent-impl */
    public static final int m2624getMinutesComponentimpl(long arg0) {
        if (m2614isInfiniteimpl(arg0)) {
            return 0;
        }
        return (int) (m2639getInWholeMinutesimpl(arg0) % 60);
    }

    /* renamed from: getSecondsComponent-impl */
    public static final int m2625getSecondsComponentimpl(long arg0) {
        if (m2614isInfiniteimpl(arg0)) {
            return 0;
        }
        return (int) (m2640getInWholeSecondsimpl(arg0) % 60);
    }

    /* renamed from: getNanosecondsComponent-impl */
    public static final int m2626getNanosecondsComponentimpl(long arg0) {
        long millisToNanos;
        if (m2614isInfiniteimpl(arg0)) {
            return 0;
        }
        if (m2601isInMillisimpl(arg0)) {
            millisToNanos = DurationKt.millisToNanos(m2598getValueimpl(arg0) % 1000);
            return (int) millisToNanos;
        }
        return (int) (m2598getValueimpl(arg0) % 1000000000);
    }

    /* renamed from: toDouble-impl */
    public static final double m2627toDoubleimpl(long arg0, @NotNull DurationUnitJvm unit) {
        Intrinsics.checkNotNullParameter(unit, "unit");
        if (arg0 == INFINITE) {
            return Double.POSITIVE_INFINITY;
        }
        if (arg0 != NEG_INFINITE) {
            return DurationUnitKt.convertDurationUnit(m2598getValueimpl(arg0), m2602getStorageUnitimpl(arg0), unit);
        }
        return Double.NEGATIVE_INFINITY;
    }

    /* renamed from: toLong-impl */
    public static final long m2628toLongimpl(long arg0, @NotNull DurationUnitJvm unit) {
        Intrinsics.checkNotNullParameter(unit, "unit");
        if (arg0 == INFINITE) {
            return LongCompanionObject.MAX_VALUE;
        }
        if (arg0 != NEG_INFINITE) {
            return DurationUnitKt.convertDurationUnit(m2598getValueimpl(arg0), m2602getStorageUnitimpl(arg0), unit);
        }
        return Long.MIN_VALUE;
    }

    /* renamed from: toInt-impl */
    public static final int m2629toIntimpl(long arg0, @NotNull DurationUnitJvm unit) {
        Intrinsics.checkNotNullParameter(unit, "unit");
        return (int) RangesKt.coerceIn(m2628toLongimpl(arg0, unit), -2147483648L, 2147483647L);
    }

    /* renamed from: getInDays-impl */
    public static final double m2630getInDaysimpl(long arg0) {
        return m2627toDoubleimpl(arg0, DurationUnitJvm.DAYS);
    }

    /* renamed from: getInHours-impl */
    public static final double m2631getInHoursimpl(long arg0) {
        return m2627toDoubleimpl(arg0, DurationUnitJvm.HOURS);
    }

    /* renamed from: getInMinutes-impl */
    public static final double m2632getInMinutesimpl(long arg0) {
        return m2627toDoubleimpl(arg0, DurationUnitJvm.MINUTES);
    }

    /* renamed from: getInSeconds-impl */
    public static final double m2633getInSecondsimpl(long arg0) {
        return m2627toDoubleimpl(arg0, DurationUnitJvm.SECONDS);
    }

    /* renamed from: getInMilliseconds-impl */
    public static final double m2634getInMillisecondsimpl(long arg0) {
        return m2627toDoubleimpl(arg0, DurationUnitJvm.MILLISECONDS);
    }

    /* renamed from: getInMicroseconds-impl */
    public static final double m2635getInMicrosecondsimpl(long arg0) {
        return m2627toDoubleimpl(arg0, DurationUnitJvm.MICROSECONDS);
    }

    /* renamed from: getInNanoseconds-impl */
    public static final double m2636getInNanosecondsimpl(long arg0) {
        return m2627toDoubleimpl(arg0, DurationUnitJvm.NANOSECONDS);
    }

    /* renamed from: getInWholeDays-impl */
    public static final long m2637getInWholeDaysimpl(long arg0) {
        return m2628toLongimpl(arg0, DurationUnitJvm.DAYS);
    }

    /* renamed from: getInWholeHours-impl */
    public static final long m2638getInWholeHoursimpl(long arg0) {
        return m2628toLongimpl(arg0, DurationUnitJvm.HOURS);
    }

    /* renamed from: getInWholeMinutes-impl */
    public static final long m2639getInWholeMinutesimpl(long arg0) {
        return m2628toLongimpl(arg0, DurationUnitJvm.MINUTES);
    }

    /* renamed from: getInWholeSeconds-impl */
    public static final long m2640getInWholeSecondsimpl(long arg0) {
        return m2628toLongimpl(arg0, DurationUnitJvm.SECONDS);
    }

    /* renamed from: getInWholeMilliseconds-impl */
    public static final long m2641getInWholeMillisecondsimpl(long arg0) {
        return (!m2601isInMillisimpl(arg0) || !m2615isFiniteimpl(arg0)) ? m2628toLongimpl(arg0, DurationUnitJvm.MILLISECONDS) : m2598getValueimpl(arg0);
    }

    /* renamed from: getInWholeMicroseconds-impl */
    public static final long m2642getInWholeMicrosecondsimpl(long arg0) {
        return m2628toLongimpl(arg0, DurationUnitJvm.MICROSECONDS);
    }

    /* renamed from: getInWholeNanoseconds-impl */
    public static final long m2643getInWholeNanosecondsimpl(long arg0) {
        long millisToNanos;
        long value = m2598getValueimpl(arg0);
        if (m2600isInNanosimpl(arg0)) {
            return value;
        }
        if (value > 9223372036854L) {
            return LongCompanionObject.MAX_VALUE;
        }
        if (value < -9223372036854L) {
            return Long.MIN_VALUE;
        }
        millisToNanos = DurationKt.millisToNanos(value);
        return millisToNanos;
    }

    @Annotations(message = "Use inWholeNanoseconds property instead.", replaceWith = @ReplaceWith(expression = "this.inWholeNanoseconds", imports = {}))
    @ExperimentalTime
    /* renamed from: toLongNanoseconds-impl */
    public static final long m2644toLongNanosecondsimpl(long arg0) {
        return m2643getInWholeNanosecondsimpl(arg0);
    }

    @Annotations(message = "Use inWholeMilliseconds property instead.", replaceWith = @ReplaceWith(expression = "this.inWholeMilliseconds", imports = {}))
    @ExperimentalTime
    /* renamed from: toLongMilliseconds-impl */
    public static final long m2645toLongMillisecondsimpl(long arg0) {
        return m2641getInWholeMillisecondsimpl(arg0);
    }

    @NotNull
    /* renamed from: toString-impl */
    public static String m2646toStringimpl(long arg0) {
        if (arg0 == 0) {
            return "0s";
        }
        if (arg0 == INFINITE) {
            return "Infinity";
        }
        if (arg0 == NEG_INFINITE) {
            return "-Infinity";
        }
        boolean isNegative = m2612isNegativeimpl(arg0);
        StringBuilder $this$toString_impl_u24lambda_u2d5 = new StringBuilder();
        if (isNegative) {
            $this$toString_impl_u24lambda_u2d5.append('-');
        }
        long arg0$iv = m2616getAbsoluteValueUwyO8pc(arg0);
        long days = m2637getInWholeDaysimpl(arg0$iv);
        int hours = m2623getHoursComponentimpl(arg0$iv);
        int minutes = m2624getMinutesComponentimpl(arg0$iv);
        int seconds = m2625getSecondsComponentimpl(arg0$iv);
        int nanoseconds = m2626getNanosecondsComponentimpl(arg0$iv);
        boolean hasDays = days != 0;
        boolean hasHours = hours != 0;
        boolean hasMinutes = minutes != 0;
        boolean hasSeconds = (seconds == 0 && nanoseconds == 0) ? false : true;
        int components = 0;
        if (hasDays) {
            $this$toString_impl_u24lambda_u2d5.append(days).append('d');
            components = 0 + 1;
        }
        if (hasHours || (hasDays && (hasMinutes || hasSeconds))) {
            int i = components;
            components = i + 1;
            if (i > 0) {
                $this$toString_impl_u24lambda_u2d5.append(' ');
            }
            $this$toString_impl_u24lambda_u2d5.append(hours).append('h');
        }
        if (hasMinutes || (hasSeconds && (hasHours || hasDays))) {
            int i2 = components;
            components = i2 + 1;
            if (i2 > 0) {
                $this$toString_impl_u24lambda_u2d5.append(' ');
            }
            $this$toString_impl_u24lambda_u2d5.append(minutes).append('m');
        }
        if (hasSeconds) {
            int i3 = components;
            components = i3 + 1;
            if (i3 > 0) {
                $this$toString_impl_u24lambda_u2d5.append(' ');
            }
            if (seconds != 0 || hasDays || hasHours || hasMinutes) {
                m2647appendFractionalimpl(arg0, $this$toString_impl_u24lambda_u2d5, seconds, nanoseconds, 9, "s", false);
            } else if (nanoseconds >= 1000000) {
                m2647appendFractionalimpl(arg0, $this$toString_impl_u24lambda_u2d5, nanoseconds / DurationKt.NANOS_IN_MILLIS, nanoseconds % DurationKt.NANOS_IN_MILLIS, 6, "ms", false);
            } else if (nanoseconds >= 1000) {
                m2647appendFractionalimpl(arg0, $this$toString_impl_u24lambda_u2d5, nanoseconds / 1000, nanoseconds % 1000, 3, "us", false);
            } else {
                $this$toString_impl_u24lambda_u2d5.append(nanoseconds).append("ns");
            }
        }
        if (isNegative && components > 1) {
            $this$toString_impl_u24lambda_u2d5.insert(1, '(').append(')');
        }
        String sb = $this$toString_impl_u24lambda_u2d5.toString();
        Intrinsics.checkNotNullExpressionValue(sb, "StringBuilder().apply(builderAction).toString()");
        return sb;
    }

    @NotNull
    public String toString() {
        return m2646toStringimpl(this.rawValue);
    }

    /* renamed from: appendFractional-impl */
    private static final void m2647appendFractionalimpl(long arg0, StringBuilder $this$appendFractional, int whole, int fractional, int fractionalSize, String unit, boolean isoZeroes) {
        int i;
        $this$appendFractional.append(whole);
        if (fractional != 0) {
            $this$appendFractional.append('.');
            CharSequence fracString = StringsKt.padStart(String.valueOf(fractional), fractionalSize, '0');
            CharSequence $this$indexOfLast$iv = fracString;
            int length = $this$indexOfLast$iv.length() - 1;
            if (0 <= length) {
                do {
                    int index$iv = length;
                    length--;
                    char it = $this$indexOfLast$iv.charAt(index$iv);
                    if (it != '0') {
                        i = index$iv;
                        break;
                    }
                } while (0 <= length);
                i = -1;
            } else {
                i = -1;
            }
            int nonZeroDigits = i + 1;
            if (isoZeroes || nonZeroDigits >= 3) {
                Intrinsics.checkNotNullExpressionValue($this$appendFractional.append(fracString, 0, ((nonZeroDigits + 2) / 3) * 3), "this.append(value, startIndex, endIndex)");
            } else {
                Intrinsics.checkNotNullExpressionValue($this$appendFractional.append(fracString, 0, nonZeroDigits), "this.append(value, startIndex, endIndex)");
            }
        }
        $this$appendFractional.append(unit);
    }

    /* renamed from: toString-impl$default */
    public static /* synthetic */ String m2649toStringimpl$default(long j, DurationUnitJvm durationUnitJvm, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 0;
        }
        return m2648toStringimpl(j, durationUnitJvm, i);
    }

    @NotNull
    /* renamed from: toString-impl */
    public static final String m2648toStringimpl(long arg0, @NotNull DurationUnitJvm unit, int decimals) {
        Intrinsics.checkNotNullParameter(unit, "unit");
        if (!(decimals >= 0)) {
            throw new IllegalArgumentException(Intrinsics.stringPlus("decimals must be not negative, but was ", Integer.valueOf(decimals)).toString());
        }
        double number = m2627toDoubleimpl(arg0, unit);
        return Double.isInfinite(number) ? String.valueOf(number) : Intrinsics.stringPlus(DurationJvm.formatToExactDecimals(number, RangesKt.coerceAtMost(decimals, 12)), DurationUnitKt.shortName(unit));
    }

    @NotNull
    /* renamed from: toIsoString-impl */
    public static final String m2650toIsoStringimpl(long arg0) {
        StringBuilder $this$toIsoString_impl_u24lambda_u2d9 = new StringBuilder();
        if (m2612isNegativeimpl(arg0)) {
            $this$toIsoString_impl_u24lambda_u2d9.append('-');
        }
        $this$toIsoString_impl_u24lambda_u2d9.append("PT");
        long arg0$iv = m2616getAbsoluteValueUwyO8pc(arg0);
        long hours = m2638getInWholeHoursimpl(arg0$iv);
        int minutes = m2624getMinutesComponentimpl(arg0$iv);
        int seconds = m2625getSecondsComponentimpl(arg0$iv);
        int nanoseconds = m2626getNanosecondsComponentimpl(arg0$iv);
        long hours2 = hours;
        if (m2614isInfiniteimpl(arg0)) {
            hours2 = 9999999999999L;
        }
        boolean hasHours = hours2 != 0;
        boolean hasSeconds = (seconds == 0 && nanoseconds == 0) ? false : true;
        boolean hasMinutes = minutes != 0 || (hasSeconds && hasHours);
        if (hasHours) {
            $this$toIsoString_impl_u24lambda_u2d9.append(hours2).append('H');
        }
        if (hasMinutes) {
            $this$toIsoString_impl_u24lambda_u2d9.append(minutes).append('M');
        }
        if (hasSeconds || (!hasHours && !hasMinutes)) {
            m2647appendFractionalimpl(arg0, $this$toIsoString_impl_u24lambda_u2d9, seconds, nanoseconds, 9, "S", true);
        }
        String sb = $this$toIsoString_impl_u24lambda_u2d9.toString();
        Intrinsics.checkNotNullExpressionValue(sb, "StringBuilder().apply(builderAction).toString()");
        return sb;
    }
}
