package kotlin.time;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import org.jetbrains.annotations.NotNull;

/* compiled from: TimeSource.kt */
@SinceKotlin(version = "1.3")
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0012\n\u0002\u0018\u0002\n\u0002\u0010��\n��\n\u0002\u0018\u0002\n\u0002\b\u0003\bg\u0018�� \u00042\u00020\u0001:\u0002\u0004\u0005J\b\u0010\u0002\u001a\u00020\u0003H&¨\u0006\u0006"}, m53d2 = {"Lkotlin/time/TimeSource;", "", "markNow", "Lkotlin/time/TimeMark;", "Companion", "Monotonic", "kotlin-stdlib"})
@ExperimentalTime
/* loaded from: Jackey Client b2.jar:kotlin/time/TimeSource.class */
public interface TimeSource {
    @NotNull
    public static final Companion Companion = Companion.$$INSTANCE;

    @NotNull
    TimeMark markNow();

    /* compiled from: TimeSource.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000e\n��\bÆ\u0002\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\t\u0010\u0003\u001a\u00020\u0004H\u0096\u0001J\b\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, m53d2 = {"Lkotlin/time/TimeSource$Monotonic;", "Lkotlin/time/TimeSource;", "()V", "markNow", "Lkotlin/time/TimeMark;", "toString", "", "kotlin-stdlib"})
    /* loaded from: Jackey Client b2.jar:kotlin/time/TimeSource$Monotonic.class */
    public static final class Monotonic implements TimeSource {
        @NotNull
        public static final Monotonic INSTANCE = new Monotonic();
        private final /* synthetic */ MonoTimeSource $$delegate_0 = MonoTimeSource.INSTANCE;

        @Override // kotlin.time.TimeSource
        @NotNull
        public TimeMark markNow() {
            return this.$$delegate_0.markNow();
        }

        private Monotonic() {
        }

        @NotNull
        public String toString() {
            return MonoTimeSource.INSTANCE.toString();
        }
    }

    /* compiled from: TimeSource.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\f\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\b\u0086\u0003\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, m53d2 = {"Lkotlin/time/TimeSource$Companion;", "", "()V", "kotlin-stdlib"})
    /* loaded from: Jackey Client b2.jar:kotlin/time/TimeSource$Companion.class */
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }
    }
}
