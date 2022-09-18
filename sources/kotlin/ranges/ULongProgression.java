package kotlin.ranges;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.ULong;
import kotlin.Unsigned;
import kotlin.UnsignedUtils;
import kotlin.WasExperimental;
import kotlin.internal.UProgressionUtil;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.markers.KMarkers;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ULongRange.kt */
@SinceKotlin(version = "1.5")
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��:\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\t\n\u0002\u0010\u000b\n��\n\u0002\u0010��\n��\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010(\n��\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0017\u0018�� \u001a2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u001aB\"\b��\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0006ø\u0001��¢\u0006\u0002\u0010\u0007J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0096\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u0010H\u0016J\u0012\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00020\u0017H\u0086\u0002ø\u0001��J\b\u0010\u0018\u001a\u00020\u0019H\u0016R\u0019\u0010\b\u001a\u00020\u0002ø\u0001��ø\u0001\u0001¢\u0006\n\n\u0002\u0010\u000b\u001a\u0004\b\t\u0010\nR\u0019\u0010\f\u001a\u00020\u0002ø\u0001��ø\u0001\u0001¢\u0006\n\n\u0002\u0010\u000b\u001a\u0004\b\r\u0010\nR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n��\u001a\u0004\b\u000e\u0010\nø\u0001��\u0082\u0002\b\n\u0002\b\u0019\n\u0002\b!¨\u0006\u001b"}, m53d2 = {"Lkotlin/ranges/ULongProgression;", "", "Lkotlin/ULong;", "start", "endInclusive", "step", "", "(JJJLkotlin/jvm/internal/DefaultConstructorMarker;)V", "first", "getFirst-s-VKNKU", "()J", "J", "last", "getLast-s-VKNKU", "getStep", "equals", "", "other", "", "hashCode", "", "isEmpty", "iterator", "", "toString", "", "Companion", "kotlin-stdlib"})
@WasExperimental(markerClass = {Unsigned.class})
/* loaded from: Jackey Client b2.jar:kotlin/ranges/ULongProgression.class */
public class ULongProgression implements Iterable<ULong>, KMarkers {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private final long first;
    private final long last;
    private final long step;

    public /* synthetic */ ULongProgression(long start, long endInclusive, long step, DefaultConstructorMarker $constructor_marker) {
        this(start, endInclusive, step);
    }

    private ULongProgression(long start, long endInclusive, long step) {
        if (step == 0) {
            throw new IllegalArgumentException("Step must be non-zero.");
        }
        if (step == Long.MIN_VALUE) {
            throw new IllegalArgumentException("Step must be greater than Long.MIN_VALUE to avoid overflow on negation.");
        }
        this.first = start;
        this.last = UProgressionUtil.m2436getProgressionLastElement7ftBX0g(start, endInclusive, step);
        this.step = step;
    }

    /* renamed from: getFirst-s-VKNKU */
    public final long m2495getFirstsVKNKU() {
        return this.first;
    }

    /* renamed from: getLast-s-VKNKU */
    public final long m2496getLastsVKNKU() {
        return this.last;
    }

    public final long getStep() {
        return this.step;
    }

    @Override // java.lang.Iterable
    @NotNull
    public final Iterator<ULong> iterator() {
        return new ULongProgressionIterator(m2495getFirstsVKNKU(), m2496getLastsVKNKU(), this.step, null);
    }

    public boolean isEmpty() {
        return this.step > 0 ? UnsignedUtils.ulongCompare(m2495getFirstsVKNKU(), m2496getLastsVKNKU()) > 0 : UnsignedUtils.ulongCompare(m2495getFirstsVKNKU(), m2496getLastsVKNKU()) < 0;
    }

    public boolean equals(@Nullable Object other) {
        return (other instanceof ULongProgression) && ((isEmpty() && ((ULongProgression) other).isEmpty()) || (m2495getFirstsVKNKU() == ((ULongProgression) other).m2495getFirstsVKNKU() && m2496getLastsVKNKU() == ((ULongProgression) other).m2496getLastsVKNKU() && this.step == ((ULongProgression) other).step));
    }

    public int hashCode() {
        if (isEmpty()) {
            return -1;
        }
        return (31 * ((31 * ((int) ULong.m1432constructorimpl(m2495getFirstsVKNKU() ^ ULong.m1432constructorimpl(m2495getFirstsVKNKU() >>> 32)))) + ((int) ULong.m1432constructorimpl(m2496getLastsVKNKU() ^ ULong.m1432constructorimpl(m2496getLastsVKNKU() >>> 32))))) + ((int) (this.step ^ (this.step >>> 32)));
    }

    @NotNull
    public String toString() {
        return this.step > 0 ? ((Object) ULong.m1429toStringimpl(m2495getFirstsVKNKU())) + ".." + ((Object) ULong.m1429toStringimpl(m2496getLastsVKNKU())) + " step " + this.step : ((Object) ULong.m1429toStringimpl(m2495getFirstsVKNKU())) + " downTo " + ((Object) ULong.m1429toStringimpl(m2496getLastsVKNKU())) + " step " + (-this.step);
    }

    /* compiled from: ULongRange.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\"\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\b\u0086\u0003\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J(\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tø\u0001��¢\u0006\u0004\b\n\u0010\u000b\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\f"}, m53d2 = {"Lkotlin/ranges/ULongProgression$Companion;", "", "()V", "fromClosedRange", "Lkotlin/ranges/ULongProgression;", "rangeStart", "Lkotlin/ULong;", "rangeEnd", "step", "", "fromClosedRange-7ftBX0g", "(JJJ)Lkotlin/ranges/ULongProgression;", "kotlin-stdlib"})
    /* loaded from: Jackey Client b2.jar:kotlin/ranges/ULongProgression$Companion.class */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        private Companion() {
        }

        @NotNull
        /* renamed from: fromClosedRange-7ftBX0g */
        public final ULongProgression m2498fromClosedRange7ftBX0g(long rangeStart, long rangeEnd, long step) {
            return new ULongProgression(rangeStart, rangeEnd, step, null);
        }
    }
}
