package kotlin.time;

import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.SinceKotlin;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: DurationUnitJvm.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 5, m49xi = 49, m54d1 = {"�� \n��\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a \u0010��\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0001\u001a \u0010��\u001a\u00020\u00062\u0006\u0010\u0002\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0001\u001a \u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0002\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0001\u001a\f\u0010\b\u001a\u00020\u0004*\u00020\tH\u0007\u001a\f\u0010\n\u001a\u00020\t*\u00020\u0004H\u0007¨\u0006\u000b"}, m53d2 = {"convertDurationUnit", "", "value", "sourceUnit", "Lkotlin/time/DurationUnit;", "targetUnit", "", "convertDurationUnitOverflow", "toDurationUnit", "Ljava/util/concurrent/TimeUnit;", "toTimeUnit", "kotlin-stdlib"}, m48xs = "kotlin/time/DurationUnitKt")
/* loaded from: Jackey Client b2.jar:kotlin/time/DurationUnitKt__DurationUnitJvmKt.class */
public class DurationUnitKt__DurationUnitJvmKt {

    /* compiled from: DurationUnitJvm.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48)
    /* loaded from: Jackey Client b2.jar:kotlin/time/DurationUnitKt__DurationUnitJvmKt$WhenMappings.class */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[TimeUnit.values().length];
            iArr[TimeUnit.NANOSECONDS.ordinal()] = 1;
            iArr[TimeUnit.MICROSECONDS.ordinal()] = 2;
            iArr[TimeUnit.MILLISECONDS.ordinal()] = 3;
            iArr[TimeUnit.SECONDS.ordinal()] = 4;
            iArr[TimeUnit.MINUTES.ordinal()] = 5;
            iArr[TimeUnit.HOURS.ordinal()] = 6;
            iArr[TimeUnit.DAYS.ordinal()] = 7;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @SinceKotlin(version = "1.6")
    @ExperimentalTime
    @NotNull
    public static final TimeUnit toTimeUnit(@NotNull DurationUnitJvm $this$toTimeUnit) {
        Intrinsics.checkNotNullParameter($this$toTimeUnit, "<this>");
        return $this$toTimeUnit.getTimeUnit$kotlin_stdlib();
    }

    @SinceKotlin(version = "1.6")
    @ExperimentalTime
    @NotNull
    public static final DurationUnitJvm toDurationUnit(@NotNull TimeUnit $this$toDurationUnit) {
        Intrinsics.checkNotNullParameter($this$toDurationUnit, "<this>");
        switch (WhenMappings.$EnumSwitchMapping$0[$this$toDurationUnit.ordinal()]) {
            case 1:
                return DurationUnitJvm.NANOSECONDS;
            case 2:
                return DurationUnitJvm.MICROSECONDS;
            case 3:
                return DurationUnitJvm.MILLISECONDS;
            case 4:
                return DurationUnitJvm.SECONDS;
            case 5:
                return DurationUnitJvm.MINUTES;
            case 6:
                return DurationUnitJvm.HOURS;
            case 7:
                return DurationUnitJvm.DAYS;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    @SinceKotlin(version = "1.3")
    public static final double convertDurationUnit(double value, @NotNull DurationUnitJvm sourceUnit, @NotNull DurationUnitJvm targetUnit) {
        Intrinsics.checkNotNullParameter(sourceUnit, "sourceUnit");
        Intrinsics.checkNotNullParameter(targetUnit, "targetUnit");
        long sourceInTargets = targetUnit.getTimeUnit$kotlin_stdlib().convert(1L, sourceUnit.getTimeUnit$kotlin_stdlib());
        if (sourceInTargets > 0) {
            return value * sourceInTargets;
        }
        long otherInThis = sourceUnit.getTimeUnit$kotlin_stdlib().convert(1L, targetUnit.getTimeUnit$kotlin_stdlib());
        return value / otherInThis;
    }

    @SinceKotlin(version = "1.5")
    public static final long convertDurationUnitOverflow(long value, @NotNull DurationUnitJvm sourceUnit, @NotNull DurationUnitJvm targetUnit) {
        Intrinsics.checkNotNullParameter(sourceUnit, "sourceUnit");
        Intrinsics.checkNotNullParameter(targetUnit, "targetUnit");
        return targetUnit.getTimeUnit$kotlin_stdlib().convert(value, sourceUnit.getTimeUnit$kotlin_stdlib());
    }

    @SinceKotlin(version = "1.5")
    public static final long convertDurationUnit(long value, @NotNull DurationUnitJvm sourceUnit, @NotNull DurationUnitJvm targetUnit) {
        Intrinsics.checkNotNullParameter(sourceUnit, "sourceUnit");
        Intrinsics.checkNotNullParameter(targetUnit, "targetUnit");
        return targetUnit.getTimeUnit$kotlin_stdlib().convert(value, sourceUnit.getTimeUnit$kotlin_stdlib());
    }
}
