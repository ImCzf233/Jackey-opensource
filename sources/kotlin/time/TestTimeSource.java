package kotlin.time;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.internal.LongCompanionObject;

/* compiled from: TimeSources.kt */
@SinceKotlin(version = "1.3")
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"�� \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n��\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0007\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002ø\u0001��¢\u0006\u0004\b\t\u0010\nJ\u001b\u0010\u000b\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0086\u0002ø\u0001��¢\u0006\u0004\b\f\u0010\nJ\b\u0010\r\u001a\u00020\u0004H\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n��\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u000e"}, m53d2 = {"Lkotlin/time/TestTimeSource;", "Lkotlin/time/AbstractLongTimeSource;", "()V", "reading", "", "overflow", "", "duration", "Lkotlin/time/Duration;", "overflow-LRDsOJo", "(J)V", "plusAssign", "plusAssign-LRDsOJo", "read", "kotlin-stdlib"})
@ExperimentalTime
/* loaded from: Jackey Client b2.jar:kotlin/time/TestTimeSource.class */
public final class TestTimeSource extends AbstractLongTimeSource {
    private long reading;

    public TestTimeSource() {
        super(DurationUnitJvm.NANOSECONDS);
    }

    @Override // kotlin.time.AbstractLongTimeSource
    protected long read() {
        return this.reading;
    }

    /* renamed from: plusAssign-LRDsOJo */
    public final void m2735plusAssignLRDsOJo(long duration) {
        long j;
        long longDelta = Duration.m2628toLongimpl(duration, getUnit());
        if (longDelta != Long.MIN_VALUE && longDelta != LongCompanionObject.MAX_VALUE) {
            long newReading = this.reading + longDelta;
            if ((this.reading ^ longDelta) >= 0 && (this.reading ^ newReading) < 0) {
                m2736overflowLRDsOJo(duration);
            }
            j = newReading;
        } else {
            double delta = Duration.m2627toDoubleimpl(duration, getUnit());
            double newReading2 = this.reading + delta;
            if (newReading2 > 9.223372036854776E18d || newReading2 < -9.223372036854776E18d) {
                m2736overflowLRDsOJo(duration);
            }
            j = (long) newReading2;
        }
        this.reading = j;
    }

    /* renamed from: overflow-LRDsOJo */
    private final void m2736overflowLRDsOJo(long duration) {
        throw new IllegalStateException("TestTimeSource will overflow if its reading " + this.reading + "ns is advanced by " + ((Object) Duration.m2646toStringimpl(duration)) + '.');
    }
}
