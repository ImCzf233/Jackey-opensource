package kotlin.time;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.util.Constants;

/* compiled from: measureTime.kt */
@SinceKotlin(version = "1.3")
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��*\n\u0002\u0018\u0002\n��\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n��\n\u0002\u0010\u000e\n��\b\u0087\b\u0018��*\u0004\b��\u0010\u00012\u00020\u0002B\u0018\u0012\u0006\u0010\u0003\u001a\u00028��\u0012\u0006\u0010\u0004\u001a\u00020\u0005ø\u0001��¢\u0006\u0002\u0010\u0006J\u000e\u0010\r\u001a\u00028��HÆ\u0003¢\u0006\u0002\u0010\u000bJ\u0016\u0010\u000e\u001a\u00020\u0005HÆ\u0003ø\u0001��ø\u0001\u0001¢\u0006\u0004\b\u000f\u0010\bJ-\u0010\u0010\u001a\b\u0012\u0004\u0012\u00028��0��2\b\b\u0002\u0010\u0003\u001a\u00028��2\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001ø\u0001��¢\u0006\u0004\b\u0011\u0010\u0012J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0002HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001R\u0019\u0010\u0004\u001a\u00020\u0005ø\u0001��ø\u0001\u0001¢\u0006\n\n\u0002\u0010\t\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0003\u001a\u00028��¢\u0006\n\n\u0002\u0010\f\u001a\u0004\b\n\u0010\u000b\u0082\u0002\b\n\u0002\b\u0019\n\u0002\b!¨\u0006\u001a"}, m53d2 = {"Lkotlin/time/TimedValue;", "T", "", "value", "duration", "Lkotlin/time/Duration;", "(Ljava/lang/Object;JLkotlin/jvm/internal/DefaultConstructorMarker;)V", "getDuration-UwyO8pc", "()J", "J", "getValue", "()Ljava/lang/Object;", Constants.OBJECT, "component1", "component2", "component2-UwyO8pc", "copy", "copy-RFiDyg4", "(Ljava/lang/Object;J)Lkotlin/time/TimedValue;", "equals", "", "other", "hashCode", "", "toString", "", "kotlin-stdlib"})
@ExperimentalTime
/* loaded from: Jackey Client b2.jar:kotlin/time/TimedValue.class */
public final class TimedValue<T> {
    private final T value;
    private final long duration;

    public final T component1() {
        return this.value;
    }

    /* renamed from: component2-UwyO8pc */
    public final long m2742component2UwyO8pc() {
        return this.duration;
    }

    @NotNull
    /* renamed from: copy-RFiDyg4 */
    public final TimedValue<T> m2743copyRFiDyg4(T t, long duration) {
        return new TimedValue<>(t, duration, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: copy-RFiDyg4$default */
    public static /* synthetic */ TimedValue m2744copyRFiDyg4$default(TimedValue timedValue, Object obj, long j, int i, Object obj2) {
        T t = obj;
        if ((i & 1) != 0) {
            t = timedValue.value;
        }
        if ((i & 2) != 0) {
            j = timedValue.duration;
        }
        return timedValue.m2743copyRFiDyg4(t, j);
    }

    @NotNull
    public String toString() {
        return "TimedValue(value=" + this.value + ", duration=" + ((Object) Duration.m2646toStringimpl(this.duration)) + ')';
    }

    public int hashCode() {
        int result = this.value == null ? 0 : this.value.hashCode();
        return (result * 31) + Duration.m2651hashCodeimpl(this.duration);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TimedValue)) {
            return false;
        }
        TimedValue timedValue = (TimedValue) other;
        return Intrinsics.areEqual(this.value, timedValue.value) && Duration.m2656equalsimpl0(this.duration, timedValue.duration);
    }

    public /* synthetic */ TimedValue(Object value, long duration, DefaultConstructorMarker $constructor_marker) {
        this(value, duration);
    }

    private TimedValue(T t, long duration) {
        this.value = t;
        this.duration = duration;
    }

    public final T getValue() {
        return this.value;
    }

    /* renamed from: getDuration-UwyO8pc */
    public final long m2741getDurationUwyO8pc() {
        return this.duration;
    }
}
