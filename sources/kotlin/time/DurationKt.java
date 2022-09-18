package kotlin.time;

import java.util.Collection;
import java.util.Iterator;
import kotlin.Annotations;
import kotlin.DeprecatedSinceKotlin;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.SinceKotlin;
import kotlin.WasExperimental;
import kotlin.collections.IntIterator;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.LongCompanionObject;
import kotlin.math.MathKt;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Marker;
import org.spongepowered.asm.util.ClassSignature;

/* compiled from: Duration.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 2, m49xi = 48, m54d1 = {"��>\n��\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\b\n��\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\b*\n\u0002\u0010\u000e\n��\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\f\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a \u0010#\u001a\u00020\u00072\u0006\u0010$\u001a\u00020\u00012\u0006\u0010%\u001a\u00020\u0005H\u0002ø\u0001��¢\u0006\u0002\u0010&\u001a\u0018\u0010'\u001a\u00020\u00072\u0006\u0010(\u001a\u00020\u0001H\u0002ø\u0001��¢\u0006\u0002\u0010\u0010\u001a\u0018\u0010)\u001a\u00020\u00072\u0006\u0010*\u001a\u00020\u0001H\u0002ø\u0001��¢\u0006\u0002\u0010\u0010\u001a\u0018\u0010+\u001a\u00020\u00072\u0006\u0010,\u001a\u00020\u0001H\u0002ø\u0001��¢\u0006\u0002\u0010\u0010\u001a\u0018\u0010-\u001a\u00020\u00072\u0006\u0010.\u001a\u00020\u0001H\u0002ø\u0001��¢\u0006\u0002\u0010\u0010\u001a\u0010\u0010/\u001a\u00020\u00012\u0006\u0010*\u001a\u00020\u0001H\u0002\u001a\u0010\u00100\u001a\u00020\u00012\u0006\u0010.\u001a\u00020\u0001H\u0002\u001a \u00101\u001a\u00020\u00072\u0006\u00102\u001a\u0002032\u0006\u00104\u001a\u000205H\u0002ø\u0001��¢\u0006\u0002\u00106\u001a\u0010\u00107\u001a\u00020\u00012\u0006\u00102\u001a\u000203H\u0002\u001a)\u00108\u001a\u00020\u0005*\u0002032\u0006\u00109\u001a\u00020\u00052\u0012\u0010:\u001a\u000e\u0012\u0004\u0012\u00020<\u0012\u0004\u0012\u0002050;H\u0082\b\u001a)\u0010=\u001a\u000203*\u0002032\u0006\u00109\u001a\u00020\u00052\u0012\u0010:\u001a\u000e\u0012\u0004\u0012\u00020<\u0012\u0004\u0012\u0002050;H\u0082\b\u001a\u001f\u0010>\u001a\u00020\u0007*\u00020\b2\u0006\u0010?\u001a\u00020\u0007H\u0087\nø\u0001��¢\u0006\u0004\b@\u0010A\u001a\u001f\u0010>\u001a\u00020\u0007*\u00020\u00052\u0006\u0010?\u001a\u00020\u0007H\u0087\nø\u0001��¢\u0006\u0004\bB\u0010C\u001a\u001c\u0010D\u001a\u00020\u0007*\u00020\b2\u0006\u0010E\u001a\u00020FH\u0007ø\u0001��¢\u0006\u0002\u0010G\u001a\u001c\u0010D\u001a\u00020\u0007*\u00020\u00052\u0006\u0010E\u001a\u00020FH\u0007ø\u0001��¢\u0006\u0002\u0010H\u001a\u001c\u0010D\u001a\u00020\u0007*\u00020\u00012\u0006\u0010E\u001a\u00020FH\u0007ø\u0001��¢\u0006\u0002\u0010I\"\u000e\u0010��\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n��\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n��\"\u000e\u0010\u0003\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n��\"\u000e\u0010\u0004\u001a\u00020\u0005X\u0080T¢\u0006\u0002\n��\"!\u0010\u0006\u001a\u00020\u0007*\u00020\b8FX\u0087\u0004ø\u0001��¢\u0006\f\u0012\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\f\"!\u0010\u0006\u001a\u00020\u0007*\u00020\u00058FX\u0087\u0004ø\u0001��¢\u0006\f\u0012\u0004\b\t\u0010\r\u001a\u0004\b\u000b\u0010\u000e\"!\u0010\u0006\u001a\u00020\u0007*\u00020\u00018FX\u0087\u0004ø\u0001��¢\u0006\f\u0012\u0004\b\t\u0010\u000f\u001a\u0004\b\u000b\u0010\u0010\"!\u0010\u0011\u001a\u00020\u0007*\u00020\b8FX\u0087\u0004ø\u0001��¢\u0006\f\u0012\u0004\b\u0012\u0010\n\u001a\u0004\b\u0013\u0010\f\"!\u0010\u0011\u001a\u00020\u0007*\u00020\u00058FX\u0087\u0004ø\u0001��¢\u0006\f\u0012\u0004\b\u0012\u0010\r\u001a\u0004\b\u0013\u0010\u000e\"!\u0010\u0011\u001a\u00020\u0007*\u00020\u00018FX\u0087\u0004ø\u0001��¢\u0006\f\u0012\u0004\b\u0012\u0010\u000f\u001a\u0004\b\u0013\u0010\u0010\"!\u0010\u0014\u001a\u00020\u0007*\u00020\b8FX\u0087\u0004ø\u0001��¢\u0006\f\u0012\u0004\b\u0015\u0010\n\u001a\u0004\b\u0016\u0010\f\"!\u0010\u0014\u001a\u00020\u0007*\u00020\u00058FX\u0087\u0004ø\u0001��¢\u0006\f\u0012\u0004\b\u0015\u0010\r\u001a\u0004\b\u0016\u0010\u000e\"!\u0010\u0014\u001a\u00020\u0007*\u00020\u00018FX\u0087\u0004ø\u0001��¢\u0006\f\u0012\u0004\b\u0015\u0010\u000f\u001a\u0004\b\u0016\u0010\u0010\"!\u0010\u0017\u001a\u00020\u0007*\u00020\b8FX\u0087\u0004ø\u0001��¢\u0006\f\u0012\u0004\b\u0018\u0010\n\u001a\u0004\b\u0019\u0010\f\"!\u0010\u0017\u001a\u00020\u0007*\u00020\u00058FX\u0087\u0004ø\u0001��¢\u0006\f\u0012\u0004\b\u0018\u0010\r\u001a\u0004\b\u0019\u0010\u000e\"!\u0010\u0017\u001a\u00020\u0007*\u00020\u00018FX\u0087\u0004ø\u0001��¢\u0006\f\u0012\u0004\b\u0018\u0010\u000f\u001a\u0004\b\u0019\u0010\u0010\"!\u0010\u001a\u001a\u00020\u0007*\u00020\b8FX\u0087\u0004ø\u0001��¢\u0006\f\u0012\u0004\b\u001b\u0010\n\u001a\u0004\b\u001c\u0010\f\"!\u0010\u001a\u001a\u00020\u0007*\u00020\u00058FX\u0087\u0004ø\u0001��¢\u0006\f\u0012\u0004\b\u001b\u0010\r\u001a\u0004\b\u001c\u0010\u000e\"!\u0010\u001a\u001a\u00020\u0007*\u00020\u00018FX\u0087\u0004ø\u0001��¢\u0006\f\u0012\u0004\b\u001b\u0010\u000f\u001a\u0004\b\u001c\u0010\u0010\"!\u0010\u001d\u001a\u00020\u0007*\u00020\b8FX\u0087\u0004ø\u0001��¢\u0006\f\u0012\u0004\b\u001e\u0010\n\u001a\u0004\b\u001f\u0010\f\"!\u0010\u001d\u001a\u00020\u0007*\u00020\u00058FX\u0087\u0004ø\u0001��¢\u0006\f\u0012\u0004\b\u001e\u0010\r\u001a\u0004\b\u001f\u0010\u000e\"!\u0010\u001d\u001a\u00020\u0007*\u00020\u00018FX\u0087\u0004ø\u0001��¢\u0006\f\u0012\u0004\b\u001e\u0010\u000f\u001a\u0004\b\u001f\u0010\u0010\"!\u0010 \u001a\u00020\u0007*\u00020\b8FX\u0087\u0004ø\u0001��¢\u0006\f\u0012\u0004\b!\u0010\n\u001a\u0004\b\"\u0010\f\"!\u0010 \u001a\u00020\u0007*\u00020\u00058FX\u0087\u0004ø\u0001��¢\u0006\f\u0012\u0004\b!\u0010\r\u001a\u0004\b\"\u0010\u000e\"!\u0010 \u001a\u00020\u0007*\u00020\u00018FX\u0087\u0004ø\u0001��¢\u0006\f\u0012\u0004\b!\u0010\u000f\u001a\u0004\b\"\u0010\u0010\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006J"}, m53d2 = {"MAX_MILLIS", "", "MAX_NANOS", "MAX_NANOS_IN_MILLIS", "NANOS_IN_MILLIS", "", "days", "Lkotlin/time/Duration;", "", "getDays$annotations", "(D)V", "getDays", "(D)J", "(I)V", "(I)J", "(J)V", "(J)J", "hours", "getHours$annotations", "getHours", "microseconds", "getMicroseconds$annotations", "getMicroseconds", "milliseconds", "getMilliseconds$annotations", "getMilliseconds", "minutes", "getMinutes$annotations", "getMinutes", "nanoseconds", "getNanoseconds$annotations", "getNanoseconds", "seconds", "getSeconds$annotations", "getSeconds", "durationOf", "normalValue", "unitDiscriminator", "(JI)J", "durationOfMillis", "normalMillis", "durationOfMillisNormalized", "millis", "durationOfNanos", "normalNanos", "durationOfNanosNormalized", "nanos", "millisToNanos", "nanosToMillis", "parseDuration", "value", "", "strictIso", "", "(Ljava/lang/String;Z)J", "parseOverLongIsoComponent", "skipWhile", "startIndex", "predicate", "Lkotlin/Function1;", "", "substringWhile", "times", "duration", "times-kIfJnKk", "(DJ)J", "times-mvk6XK0", "(IJ)J", "toDuration", "unit", "Lkotlin/time/DurationUnit;", "(DLkotlin/time/DurationUnit;)J", "(ILkotlin/time/DurationUnit;)J", "(JLkotlin/time/DurationUnit;)J", "kotlin-stdlib"})
/* loaded from: Jackey Client b2.jar:kotlin/time/DurationKt.class */
public final class DurationKt {
    public static final int NANOS_IN_MILLIS = 1000000;
    public static final long MAX_NANOS = 4611686018426999999L;
    public static final long MAX_MILLIS = 4611686018427387903L;
    private static final long MAX_NANOS_IN_MILLIS = 4611686018426L;

    @SinceKotlin(version = "1.3")
    @Annotations(message = "Use 'Int.nanoseconds' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "this.nanoseconds", imports = {"kotlin.time.Duration.Companion.nanoseconds"}))
    @DeprecatedSinceKotlin(warningSince = "1.5")
    @ExperimentalTime
    public static /* synthetic */ void getNanoseconds$annotations(int i) {
    }

    @SinceKotlin(version = "1.3")
    @Annotations(message = "Use 'Long.nanoseconds' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "this.nanoseconds", imports = {"kotlin.time.Duration.Companion.nanoseconds"}))
    @DeprecatedSinceKotlin(warningSince = "1.5")
    @ExperimentalTime
    public static /* synthetic */ void getNanoseconds$annotations(long j) {
    }

    @SinceKotlin(version = "1.3")
    @Annotations(message = "Use 'Double.nanoseconds' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "this.nanoseconds", imports = {"kotlin.time.Duration.Companion.nanoseconds"}))
    @DeprecatedSinceKotlin(warningSince = "1.5")
    @ExperimentalTime
    public static /* synthetic */ void getNanoseconds$annotations(double d) {
    }

    @SinceKotlin(version = "1.3")
    @Annotations(message = "Use 'Int.microseconds' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "this.microseconds", imports = {"kotlin.time.Duration.Companion.microseconds"}))
    @DeprecatedSinceKotlin(warningSince = "1.5")
    @ExperimentalTime
    public static /* synthetic */ void getMicroseconds$annotations(int i) {
    }

    @SinceKotlin(version = "1.3")
    @Annotations(message = "Use 'Long.microseconds' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "this.microseconds", imports = {"kotlin.time.Duration.Companion.microseconds"}))
    @DeprecatedSinceKotlin(warningSince = "1.5")
    @ExperimentalTime
    public static /* synthetic */ void getMicroseconds$annotations(long j) {
    }

    @SinceKotlin(version = "1.3")
    @Annotations(message = "Use 'Double.microseconds' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "this.microseconds", imports = {"kotlin.time.Duration.Companion.microseconds"}))
    @DeprecatedSinceKotlin(warningSince = "1.5")
    @ExperimentalTime
    public static /* synthetic */ void getMicroseconds$annotations(double d) {
    }

    @SinceKotlin(version = "1.3")
    @Annotations(message = "Use 'Int.milliseconds' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "this.milliseconds", imports = {"kotlin.time.Duration.Companion.milliseconds"}))
    @DeprecatedSinceKotlin(warningSince = "1.5")
    @ExperimentalTime
    public static /* synthetic */ void getMilliseconds$annotations(int i) {
    }

    @SinceKotlin(version = "1.3")
    @Annotations(message = "Use 'Long.milliseconds' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "this.milliseconds", imports = {"kotlin.time.Duration.Companion.milliseconds"}))
    @DeprecatedSinceKotlin(warningSince = "1.5")
    @ExperimentalTime
    public static /* synthetic */ void getMilliseconds$annotations(long j) {
    }

    @SinceKotlin(version = "1.3")
    @Annotations(message = "Use 'Double.milliseconds' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "this.milliseconds", imports = {"kotlin.time.Duration.Companion.milliseconds"}))
    @DeprecatedSinceKotlin(warningSince = "1.5")
    @ExperimentalTime
    public static /* synthetic */ void getMilliseconds$annotations(double d) {
    }

    @SinceKotlin(version = "1.3")
    @Annotations(message = "Use 'Int.seconds' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "this.seconds", imports = {"kotlin.time.Duration.Companion.seconds"}))
    @DeprecatedSinceKotlin(warningSince = "1.5")
    @ExperimentalTime
    public static /* synthetic */ void getSeconds$annotations(int i) {
    }

    @SinceKotlin(version = "1.3")
    @Annotations(message = "Use 'Long.seconds' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "this.seconds", imports = {"kotlin.time.Duration.Companion.seconds"}))
    @DeprecatedSinceKotlin(warningSince = "1.5")
    @ExperimentalTime
    public static /* synthetic */ void getSeconds$annotations(long j) {
    }

    @SinceKotlin(version = "1.3")
    @Annotations(message = "Use 'Double.seconds' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "this.seconds", imports = {"kotlin.time.Duration.Companion.seconds"}))
    @DeprecatedSinceKotlin(warningSince = "1.5")
    @ExperimentalTime
    public static /* synthetic */ void getSeconds$annotations(double d) {
    }

    @SinceKotlin(version = "1.3")
    @Annotations(message = "Use 'Int.minutes' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "this.minutes", imports = {"kotlin.time.Duration.Companion.minutes"}))
    @DeprecatedSinceKotlin(warningSince = "1.5")
    @ExperimentalTime
    public static /* synthetic */ void getMinutes$annotations(int i) {
    }

    @SinceKotlin(version = "1.3")
    @Annotations(message = "Use 'Long.minutes' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "this.minutes", imports = {"kotlin.time.Duration.Companion.minutes"}))
    @DeprecatedSinceKotlin(warningSince = "1.5")
    @ExperimentalTime
    public static /* synthetic */ void getMinutes$annotations(long j) {
    }

    @SinceKotlin(version = "1.3")
    @Annotations(message = "Use 'Double.minutes' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "this.minutes", imports = {"kotlin.time.Duration.Companion.minutes"}))
    @DeprecatedSinceKotlin(warningSince = "1.5")
    @ExperimentalTime
    public static /* synthetic */ void getMinutes$annotations(double d) {
    }

    @SinceKotlin(version = "1.3")
    @Annotations(message = "Use 'Int.hours' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "this.hours", imports = {"kotlin.time.Duration.Companion.hours"}))
    @DeprecatedSinceKotlin(warningSince = "1.5")
    @ExperimentalTime
    public static /* synthetic */ void getHours$annotations(int i) {
    }

    @SinceKotlin(version = "1.3")
    @Annotations(message = "Use 'Long.hours' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "this.hours", imports = {"kotlin.time.Duration.Companion.hours"}))
    @DeprecatedSinceKotlin(warningSince = "1.5")
    @ExperimentalTime
    public static /* synthetic */ void getHours$annotations(long j) {
    }

    @SinceKotlin(version = "1.3")
    @Annotations(message = "Use 'Double.hours' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "this.hours", imports = {"kotlin.time.Duration.Companion.hours"}))
    @DeprecatedSinceKotlin(warningSince = "1.5")
    @ExperimentalTime
    public static /* synthetic */ void getHours$annotations(double d) {
    }

    @SinceKotlin(version = "1.3")
    @Annotations(message = "Use 'Int.days' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "this.days", imports = {"kotlin.time.Duration.Companion.days"}))
    @DeprecatedSinceKotlin(warningSince = "1.5")
    @ExperimentalTime
    public static /* synthetic */ void getDays$annotations(int i) {
    }

    @SinceKotlin(version = "1.3")
    @Annotations(message = "Use 'Long.days' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "this.days", imports = {"kotlin.time.Duration.Companion.days"}))
    @DeprecatedSinceKotlin(warningSince = "1.5")
    @ExperimentalTime
    public static /* synthetic */ void getDays$annotations(long j) {
    }

    @SinceKotlin(version = "1.3")
    @Annotations(message = "Use 'Double.days' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "this.days", imports = {"kotlin.time.Duration.Companion.days"}))
    @DeprecatedSinceKotlin(warningSince = "1.5")
    @ExperimentalTime
    public static /* synthetic */ void getDays$annotations(double d) {
    }

    @SinceKotlin(version = "1.6")
    @WasExperimental(markerClass = {ExperimentalTime.class})
    public static final long toDuration(int $this$toDuration, @NotNull DurationUnitJvm unit) {
        Intrinsics.checkNotNullParameter(unit, "unit");
        if (unit.compareTo(DurationUnitJvm.SECONDS) <= 0) {
            return durationOfNanos(DurationUnitKt.convertDurationUnitOverflow($this$toDuration, unit, DurationUnitJvm.NANOSECONDS));
        }
        return toDuration($this$toDuration, unit);
    }

    @SinceKotlin(version = "1.6")
    @WasExperimental(markerClass = {ExperimentalTime.class})
    public static final long toDuration(long $this$toDuration, @NotNull DurationUnitJvm unit) {
        Intrinsics.checkNotNullParameter(unit, "unit");
        long maxNsInUnit = DurationUnitKt.convertDurationUnitOverflow(MAX_NANOS, DurationUnitJvm.NANOSECONDS, unit);
        if ((-maxNsInUnit) <= $this$toDuration ? $this$toDuration <= maxNsInUnit : false) {
            return durationOfNanos(DurationUnitKt.convertDurationUnitOverflow($this$toDuration, unit, DurationUnitJvm.NANOSECONDS));
        }
        long millis = DurationUnitKt.convertDurationUnit($this$toDuration, unit, DurationUnitJvm.MILLISECONDS);
        return durationOfMillis(RangesKt.coerceIn(millis, -4611686018427387903L, (long) MAX_MILLIS));
    }

    @SinceKotlin(version = "1.6")
    @WasExperimental(markerClass = {ExperimentalTime.class})
    public static final long toDuration(double $this$toDuration, @NotNull DurationUnitJvm unit) {
        Intrinsics.checkNotNullParameter(unit, "unit");
        double valueInNs = DurationUnitKt.convertDurationUnit($this$toDuration, unit, DurationUnitJvm.NANOSECONDS);
        if (!(!Double.isNaN(valueInNs))) {
            throw new IllegalArgumentException("Duration value cannot be NaN.".toString());
        }
        long nanos = MathKt.roundToLong(valueInNs);
        if (-4611686018426999999L <= nanos ? nanos < 4611686018427000000L : false) {
            return durationOfNanos(nanos);
        }
        long millis = MathKt.roundToLong(DurationUnitKt.convertDurationUnit($this$toDuration, unit, DurationUnitJvm.MILLISECONDS));
        return durationOfMillisNormalized(millis);
    }

    public static final long getNanoseconds(int $this$nanoseconds) {
        return toDuration($this$nanoseconds, DurationUnitJvm.NANOSECONDS);
    }

    public static final long getNanoseconds(long $this$nanoseconds) {
        return toDuration($this$nanoseconds, DurationUnitJvm.NANOSECONDS);
    }

    public static final long getNanoseconds(double $this$nanoseconds) {
        return toDuration($this$nanoseconds, DurationUnitJvm.NANOSECONDS);
    }

    public static final long getMicroseconds(int $this$microseconds) {
        return toDuration($this$microseconds, DurationUnitJvm.MICROSECONDS);
    }

    public static final long getMicroseconds(long $this$microseconds) {
        return toDuration($this$microseconds, DurationUnitJvm.MICROSECONDS);
    }

    public static final long getMicroseconds(double $this$microseconds) {
        return toDuration($this$microseconds, DurationUnitJvm.MICROSECONDS);
    }

    public static final long getMilliseconds(int $this$milliseconds) {
        return toDuration($this$milliseconds, DurationUnitJvm.MILLISECONDS);
    }

    public static final long getMilliseconds(long $this$milliseconds) {
        return toDuration($this$milliseconds, DurationUnitJvm.MILLISECONDS);
    }

    public static final long getMilliseconds(double $this$milliseconds) {
        return toDuration($this$milliseconds, DurationUnitJvm.MILLISECONDS);
    }

    public static final long getSeconds(int $this$seconds) {
        return toDuration($this$seconds, DurationUnitJvm.SECONDS);
    }

    public static final long getSeconds(long $this$seconds) {
        return toDuration($this$seconds, DurationUnitJvm.SECONDS);
    }

    public static final long getSeconds(double $this$seconds) {
        return toDuration($this$seconds, DurationUnitJvm.SECONDS);
    }

    public static final long getMinutes(int $this$minutes) {
        return toDuration($this$minutes, DurationUnitJvm.MINUTES);
    }

    public static final long getMinutes(long $this$minutes) {
        return toDuration($this$minutes, DurationUnitJvm.MINUTES);
    }

    public static final long getMinutes(double $this$minutes) {
        return toDuration($this$minutes, DurationUnitJvm.MINUTES);
    }

    public static final long getHours(int $this$hours) {
        return toDuration($this$hours, DurationUnitJvm.HOURS);
    }

    public static final long getHours(long $this$hours) {
        return toDuration($this$hours, DurationUnitJvm.HOURS);
    }

    public static final long getHours(double $this$hours) {
        return toDuration($this$hours, DurationUnitJvm.HOURS);
    }

    public static final long getDays(int $this$days) {
        return toDuration($this$days, DurationUnitJvm.DAYS);
    }

    public static final long getDays(long $this$days) {
        return toDuration($this$days, DurationUnitJvm.DAYS);
    }

    public static final long getDays(double $this$days) {
        return toDuration($this$days, DurationUnitJvm.DAYS);
    }

    @SinceKotlin(version = "1.6")
    @WasExperimental(markerClass = {ExperimentalTime.class})
    @InlineOnly
    /* renamed from: times-mvk6XK0 */
    private static final long m2729timesmvk6XK0(int $this$times, long duration) {
        return Duration.m2607timesUwyO8pc(duration, $this$times);
    }

    @SinceKotlin(version = "1.6")
    @WasExperimental(markerClass = {ExperimentalTime.class})
    @InlineOnly
    /* renamed from: times-kIfJnKk */
    private static final long m2730timeskIfJnKk(double $this$times, long duration) {
        return Duration.m2608timesUwyO8pc(duration, $this$times);
    }

    public static final long parseDuration(String value, boolean strictIso) {
        int i$iv$iv;
        int i$iv$iv2;
        int i$iv;
        int i$iv$iv3;
        int length = value.length();
        if (length == 0) {
            throw new IllegalArgumentException("The string is empty");
        }
        int index = 0;
        long result = Duration.Companion.m2658getZEROUwyO8pc();
        char charAt = value.charAt(0);
        if (charAt == '+' ? true : charAt == '-') {
            index = 0 + 1;
        }
        boolean hasSign = index > 0;
        boolean isNegative = hasSign && StringsKt.startsWith$default((CharSequence) value, '-', false, 2, (Object) null);
        if (length <= index) {
            throw new IllegalArgumentException("No components");
        }
        if (value.charAt(index) == 'P') {
            int index2 = index + 1;
            if (index2 == length) {
                throw new IllegalArgumentException();
            }
            boolean isTimeComponent = false;
            DurationUnitJvm prevUnit = null;
            while (index2 < length) {
                if (value.charAt(index2) == 'T') {
                    if (!isTimeComponent) {
                        index2++;
                        if (index2 != length) {
                            isTimeComponent = true;
                        }
                    }
                    throw new IllegalArgumentException();
                }
                int i = index2;
                while (true) {
                    i$iv$iv3 = i;
                    if (i$iv$iv3 < value.length()) {
                        char it = value.charAt(i$iv$iv3);
                        if (!(('0' <= it ? it < ':' : false) || StringsKt.contains$default((CharSequence) "+-.", it, false, 2, (Object) null))) {
                            break;
                        }
                        i = i$iv$iv3 + 1;
                    } else {
                        break;
                    }
                }
                String component = value.substring(index2, i$iv$iv3);
                Intrinsics.checkNotNullExpressionValue(component, "this as java.lang.String…ing(startIndex, endIndex)");
                if (component.length() == 0) {
                    throw new IllegalArgumentException();
                }
                int index3 = index2 + component.length();
                String str = value;
                if (index3 < 0 || index3 > StringsKt.getLastIndex(str)) {
                    throw new IllegalArgumentException(Intrinsics.stringPlus("Missing unit for value ", component));
                }
                char unitChar = str.charAt(index3);
                index2 = index3 + 1;
                DurationUnitJvm unit = DurationUnitKt.durationUnitByIsoChar(unitChar, isTimeComponent);
                if (prevUnit != null && prevUnit.compareTo(unit) <= 0) {
                    throw new IllegalArgumentException("Unexpected order of duration components");
                }
                prevUnit = unit;
                int dotIndex = StringsKt.indexOf$default((CharSequence) component, '.', 0, false, 6, (Object) null);
                if (unit != DurationUnitJvm.SECONDS || dotIndex <= 0) {
                    result = Duration.m2604plusLRDsOJo(result, toDuration(parseOverLongIsoComponent(component), unit));
                } else {
                    String whole = component.substring(0, dotIndex);
                    Intrinsics.checkNotNullExpressionValue(whole, "this as java.lang.String…ing(startIndex, endIndex)");
                    long result2 = Duration.m2604plusLRDsOJo(result, toDuration(parseOverLongIsoComponent(whole), unit));
                    String substring = component.substring(dotIndex);
                    Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String).substring(startIndex)");
                    result = Duration.m2604plusLRDsOJo(result2, toDuration(Double.parseDouble(substring), unit));
                }
            }
        } else if (strictIso) {
            throw new IllegalArgumentException();
        } else {
            if (StringsKt.regionMatches(value, index, "Infinity", 0, Math.max(length - index, "Infinity".length()), true)) {
                result = Duration.Companion.m2659getINFINITEUwyO8pc();
            } else {
                DurationUnitJvm prevUnit2 = null;
                boolean afterFirst = false;
                boolean allowSpaces = !hasSign;
                if (hasSign && value.charAt(index) == '(' && StringsKt.last(value) == ')') {
                    allowSpaces = true;
                    index++;
                    length--;
                    if (index == length) {
                        throw new IllegalArgumentException("No components");
                    }
                }
                while (index < length) {
                    if (afterFirst && allowSpaces) {
                        int i2 = index;
                        while (true) {
                            i$iv = i2;
                            if (i$iv < value.length()) {
                                if (!(value.charAt(i$iv) == ' ')) {
                                    break;
                                }
                                i2 = i$iv + 1;
                            } else {
                                break;
                            }
                        }
                        index = i$iv;
                    }
                    afterFirst = true;
                    int i3 = index;
                    while (true) {
                        i$iv$iv = i3;
                        if (i$iv$iv < value.length()) {
                            char it2 = value.charAt(i$iv$iv);
                            if (!(('0' <= it2 ? it2 < ':' : false) || it2 == '.')) {
                                break;
                            }
                            i3 = i$iv$iv + 1;
                        } else {
                            break;
                        }
                    }
                    String component2 = value.substring(index, i$iv$iv);
                    Intrinsics.checkNotNullExpressionValue(component2, "this as java.lang.String…ing(startIndex, endIndex)");
                    if (component2.length() == 0) {
                        throw new IllegalArgumentException();
                    }
                    int index4 = index + component2.length();
                    int i4 = index4;
                    while (true) {
                        i$iv$iv2 = i4;
                        if (i$iv$iv2 < value.length()) {
                            char it3 = value.charAt(i$iv$iv2);
                            if (!('a' <= it3 ? it3 < '{' : false)) {
                                break;
                            }
                            i4 = i$iv$iv2 + 1;
                        } else {
                            break;
                        }
                    }
                    String unitName = value.substring(index4, i$iv$iv2);
                    Intrinsics.checkNotNullExpressionValue(unitName, "this as java.lang.String…ing(startIndex, endIndex)");
                    index = index4 + unitName.length();
                    DurationUnitJvm unit2 = DurationUnitKt.durationUnitByShortName(unitName);
                    if (prevUnit2 != null && prevUnit2.compareTo(unit2) <= 0) {
                        throw new IllegalArgumentException("Unexpected order of duration components");
                    }
                    prevUnit2 = unit2;
                    int dotIndex2 = StringsKt.indexOf$default((CharSequence) component2, '.', 0, false, 6, (Object) null);
                    if (dotIndex2 > 0) {
                        String whole2 = component2.substring(0, dotIndex2);
                        Intrinsics.checkNotNullExpressionValue(whole2, "this as java.lang.String…ing(startIndex, endIndex)");
                        long result3 = Duration.m2604plusLRDsOJo(result, toDuration(Long.parseLong(whole2), unit2));
                        String substring2 = component2.substring(dotIndex2);
                        Intrinsics.checkNotNullExpressionValue(substring2, "this as java.lang.String).substring(startIndex)");
                        result = Duration.m2604plusLRDsOJo(result3, toDuration(Double.parseDouble(substring2), unit2));
                        if (index < length) {
                            throw new IllegalArgumentException("Fractional component must be last");
                        }
                    } else {
                        result = Duration.m2604plusLRDsOJo(result, toDuration(Long.parseLong(component2), unit2));
                    }
                }
            }
        }
        return isNegative ? Duration.m2603unaryMinusUwyO8pc(result) : result;
    }

    private static final long parseOverLongIsoComponent(String value) {
        boolean z;
        boolean z2;
        int length = value.length();
        int startIndex = 0;
        if (length > 0 && StringsKt.contains$default((CharSequence) ClassSignature.IToken.WILDCARDS, value.charAt(0), false, 2, (Object) null)) {
            startIndex = 0 + 1;
        }
        if (length - startIndex > 16) {
            Iterable $this$all$iv = new IntRange(startIndex, StringsKt.getLastIndex(value));
            if (!($this$all$iv instanceof Collection) || !((Collection) $this$all$iv).isEmpty()) {
                Iterator<Integer> it = $this$all$iv.iterator();
                while (true) {
                    if (it.hasNext()) {
                        int element$iv = ((IntIterator) it).nextInt();
                        char charAt = value.charAt(element$iv);
                        if ('0' > charAt) {
                            z2 = false;
                            continue;
                        } else if (charAt < ':') {
                            z2 = true;
                            continue;
                        } else {
                            z2 = false;
                            continue;
                        }
                        if (!z2) {
                            z = false;
                            break;
                        }
                    } else {
                        z = true;
                        break;
                    }
                }
            } else {
                z = true;
            }
            if (z) {
                if (value.charAt(0) != '-') {
                    return LongCompanionObject.MAX_VALUE;
                }
                return Long.MIN_VALUE;
            }
        }
        return StringsKt.startsWith$default(value, Marker.ANY_NON_NULL_MARKER, false, 2, (Object) null) ? Long.parseLong(StringsKt.drop(value, 1)) : Long.parseLong(value);
    }

    private static final String substringWhile(String $this$substringWhile, int startIndex, Function1<? super Character, Boolean> function1) {
        int i$iv;
        int i = startIndex;
        while (true) {
            i$iv = i;
            if (i$iv >= $this$substringWhile.length() || !function1.invoke(Character.valueOf($this$substringWhile.charAt(i$iv))).booleanValue()) {
                break;
            }
            i = i$iv + 1;
        }
        String substring = $this$substringWhile.substring(startIndex, i$iv);
        Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String…ing(startIndex, endIndex)");
        return substring;
    }

    private static final int skipWhile(String $this$skipWhile, int startIndex, Function1<? super Character, Boolean> function1) {
        int i;
        int i2 = startIndex;
        while (true) {
            i = i2;
            if (i >= $this$skipWhile.length() || !function1.invoke(Character.valueOf($this$skipWhile.charAt(i))).booleanValue()) {
                break;
            }
            i2 = i + 1;
        }
        return i;
    }

    public static final long nanosToMillis(long nanos) {
        return nanos / ((long) NANOS_IN_MILLIS);
    }

    public static final long millisToNanos(long millis) {
        return millis * ((long) NANOS_IN_MILLIS);
    }

    public static final long durationOfNanos(long normalNanos) {
        return Duration.m2653constructorimpl(normalNanos << 1);
    }

    public static final long durationOfMillis(long normalMillis) {
        return Duration.m2653constructorimpl((normalMillis << 1) + 1);
    }

    public static final long durationOf(long normalValue, int unitDiscriminator) {
        return Duration.m2653constructorimpl((normalValue << 1) + unitDiscriminator);
    }

    public static final long durationOfNanosNormalized(long nanos) {
        if (-4611686018426999999L <= nanos ? nanos < 4611686018427000000L : false) {
            return durationOfNanos(nanos);
        }
        return durationOfMillis(nanosToMillis(nanos));
    }

    public static final long durationOfMillisNormalized(long millis) {
        if (-4611686018426L <= millis ? millis < 4611686018427L : false) {
            return durationOfNanos(millisToNanos(millis));
        }
        return durationOfMillis(RangesKt.coerceIn(millis, -4611686018427387903L, (long) MAX_MILLIS));
    }
}
