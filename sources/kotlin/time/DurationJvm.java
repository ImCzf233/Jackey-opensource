package kotlin.time;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(m51mv = {1, 6, 0}, m52k = 2, m49xi = 48, m54d1 = {"��.\n��\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n��\n\u0002\u0010\u000e\n��\n\u0002\u0010\u0006\n\u0002\b\u0002\u001a\u0010\u0010\t\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u000bH\u0002\u001a\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\n\u001a\u00020\u000bH��\u001a\u0018\u0010\u0010\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\n\u001a\u00020\u000bH��\"\u0014\u0010��\u001a\u00020\u0001X\u0080\u0004¢\u0006\b\n��\u001a\u0004\b\u0002\u0010\u0003\"\u001c\u0010\u0004\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u0005X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\b¨\u0006\u0011"}, m53d2 = {"durationAssertionsEnabled", "", "getDurationAssertionsEnabled", "()Z", "precisionFormats", "", "Ljava/lang/ThreadLocal;", "Ljava/text/DecimalFormat;", "[Ljava/lang/ThreadLocal;", "createFormatForDecimals", "decimals", "", "formatToExactDecimals", "", "value", "", "formatUpToDecimals", "kotlin-stdlib"})
/* renamed from: kotlin.time.DurationJvmKt */
/* loaded from: Jackey Client b2.jar:kotlin/time/DurationJvmKt.class */
public final class DurationJvm {
    private static final boolean durationAssertionsEnabled = Duration.class.desiredAssertionStatus();
    @NotNull
    private static final ThreadLocal<DecimalFormat>[] precisionFormats;

    public static final boolean getDurationAssertionsEnabled() {
        return durationAssertionsEnabled;
    }

    static {
        ThreadLocal<DecimalFormat>[] threadLocalArr = new ThreadLocal[4];
        for (int i = 0; i < 4; i++) {
            threadLocalArr[i] = new ThreadLocal<>();
        }
        precisionFormats = threadLocalArr;
    }

    private static final DecimalFormat createFormatForDecimals(int decimals) {
        DecimalFormat $this$createFormatForDecimals_u24lambda_u2d0 = new DecimalFormat("0");
        if (decimals > 0) {
            $this$createFormatForDecimals_u24lambda_u2d0.setMinimumFractionDigits(decimals);
        }
        $this$createFormatForDecimals_u24lambda_u2d0.setRoundingMode(RoundingMode.HALF_UP);
        return $this$createFormatForDecimals_u24lambda_u2d0;
    }

    @NotNull
    public static final String formatToExactDecimals(double value, int decimals) {
        DecimalFormat decimalFormat;
        DecimalFormat decimalFormat2;
        if (decimals < precisionFormats.length) {
            ThreadLocal<DecimalFormat> threadLocal = precisionFormats[decimals];
            DecimalFormat decimalFormat3 = threadLocal.get();
            if (decimalFormat3 == null) {
                DecimalFormat createFormatForDecimals = createFormatForDecimals(decimals);
                threadLocal.set(createFormatForDecimals);
                decimalFormat2 = createFormatForDecimals;
            } else {
                decimalFormat2 = decimalFormat3;
            }
            decimalFormat = decimalFormat2;
        } else {
            decimalFormat = createFormatForDecimals(decimals);
        }
        DecimalFormat format = decimalFormat;
        String format2 = format.format(value);
        Intrinsics.checkNotNullExpressionValue(format2, "format.format(value)");
        return format2;
    }

    @NotNull
    public static final String formatUpToDecimals(double value, int decimals) {
        DecimalFormat $this$formatUpToDecimals_u24lambda_u2d2 = createFormatForDecimals(0);
        $this$formatUpToDecimals_u24lambda_u2d2.setMaximumFractionDigits(decimals);
        String format = $this$formatUpToDecimals_u24lambda_u2d2.format(value);
        Intrinsics.checkNotNullExpressionValue(format, "createFormatForDecimals(… }\n        .format(value)");
        return format;
    }
}
