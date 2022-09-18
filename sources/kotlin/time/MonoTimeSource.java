package kotlin.time;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import org.jetbrains.annotations.NotNull;

@SinceKotlin(version = "1.3")
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n��\n\u0002\u0010\u000e\n��\bÁ\u0002\u0018��2\u00020\u00012\u00020\u0002B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H\u0014J\b\u0010\u0006\u001a\u00020\u0007H\u0016¨\u0006\b"}, m53d2 = {"Lkotlin/time/MonotonicTimeSource;", "Lkotlin/time/AbstractLongTimeSource;", "Lkotlin/time/TimeSource;", "()V", "read", "", "toString", "", "kotlin-stdlib"})
@ExperimentalTime
/* renamed from: kotlin.time.MonotonicTimeSource */
/* loaded from: Jackey Client b2.jar:kotlin/time/MonotonicTimeSource.class */
public final class MonoTimeSource extends AbstractLongTimeSource implements TimeSource {
    @NotNull
    public static final MonoTimeSource INSTANCE = new MonoTimeSource();

    private MonoTimeSource() {
        super(DurationUnitJvm.NANOSECONDS);
    }

    @Override // kotlin.time.AbstractLongTimeSource
    protected long read() {
        return System.nanoTime();
    }

    @NotNull
    public String toString() {
        return "TimeSource(System.nanoTime())";
    }
}
