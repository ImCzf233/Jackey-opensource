package kotlin.time;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: DurationUnit.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 5, m49xi = 49, m54d1 = {"��\u001c\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\f\n��\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n��\u001a\u0018\u0010��\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0001\u001a\u0010\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\bH\u0001\u001a\f\u0010\u0007\u001a\u00020\b*\u00020\u0001H\u0001¨\u0006\t"}, m53d2 = {"durationUnitByIsoChar", "Lkotlin/time/DurationUnit;", "isoChar", "", "isTimeComponent", "", "durationUnitByShortName", "shortName", "", "kotlin-stdlib"}, m48xs = "kotlin/time/DurationUnitKt")
/* loaded from: Jackey Client b2.jar:kotlin/time/DurationUnitKt__DurationUnitKt.class */
class DurationUnitKt__DurationUnitKt extends DurationUnitKt__DurationUnitJvmKt {

    /* compiled from: DurationUnit.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48)
    /* loaded from: Jackey Client b2.jar:kotlin/time/DurationUnitKt__DurationUnitKt$WhenMappings.class */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[DurationUnitJvm.values().length];
            iArr[DurationUnitJvm.NANOSECONDS.ordinal()] = 1;
            iArr[DurationUnitJvm.MICROSECONDS.ordinal()] = 2;
            iArr[DurationUnitJvm.MILLISECONDS.ordinal()] = 3;
            iArr[DurationUnitJvm.SECONDS.ordinal()] = 4;
            iArr[DurationUnitJvm.MINUTES.ordinal()] = 5;
            iArr[DurationUnitJvm.HOURS.ordinal()] = 6;
            iArr[DurationUnitJvm.DAYS.ordinal()] = 7;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @SinceKotlin(version = "1.3")
    @NotNull
    public static final String shortName(@NotNull DurationUnitJvm $this$shortName) {
        Intrinsics.checkNotNullParameter($this$shortName, "<this>");
        switch (WhenMappings.$EnumSwitchMapping$0[$this$shortName.ordinal()]) {
            case 1:
                return "ns";
            case 2:
                return "us";
            case 3:
                return "ms";
            case 4:
                return "s";
            case 5:
                return "m";
            case 6:
                return "h";
            case 7:
                return "d";
            default:
                throw new IllegalStateException(Intrinsics.stringPlus("Unknown unit: ", $this$shortName).toString());
        }
    }

    @SinceKotlin(version = "1.5")
    @NotNull
    public static final DurationUnitJvm durationUnitByShortName(@NotNull String shortName) {
        Intrinsics.checkNotNullParameter(shortName, "shortName");
        switch (shortName.hashCode()) {
            case 100:
                if (shortName.equals("d")) {
                    return DurationUnitJvm.DAYS;
                }
                break;
            case 104:
                if (shortName.equals("h")) {
                    return DurationUnitJvm.HOURS;
                }
                break;
            case 109:
                if (shortName.equals("m")) {
                    return DurationUnitJvm.MINUTES;
                }
                break;
            case 115:
                if (shortName.equals("s")) {
                    return DurationUnitJvm.SECONDS;
                }
                break;
            case 3494:
                if (shortName.equals("ms")) {
                    return DurationUnitJvm.MILLISECONDS;
                }
                break;
            case 3525:
                if (shortName.equals("ns")) {
                    return DurationUnitJvm.NANOSECONDS;
                }
                break;
            case 3742:
                if (shortName.equals("us")) {
                    return DurationUnitJvm.MICROSECONDS;
                }
                break;
        }
        throw new IllegalArgumentException(Intrinsics.stringPlus("Unknown duration unit short name: ", shortName));
    }

    @SinceKotlin(version = "1.5")
    @NotNull
    public static final DurationUnitJvm durationUnitByIsoChar(char isoChar, boolean isTimeComponent) {
        if (!isTimeComponent) {
            if (isoChar != 'D') {
                throw new IllegalArgumentException(Intrinsics.stringPlus("Invalid or unsupported duration ISO non-time unit: ", Character.valueOf(isoChar)));
            }
            return DurationUnitJvm.DAYS;
        } else if (isoChar == 'H') {
            return DurationUnitJvm.HOURS;
        } else {
            if (isoChar == 'M') {
                return DurationUnitJvm.MINUTES;
            }
            if (isoChar != 'S') {
                throw new IllegalArgumentException(Intrinsics.stringPlus("Invalid duration ISO time unit: ", Character.valueOf(isoChar)));
            }
            return DurationUnitJvm.SECONDS;
        }
    }
}
