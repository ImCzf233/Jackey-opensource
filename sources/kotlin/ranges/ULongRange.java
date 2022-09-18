package kotlin.ranges;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.ULong;
import kotlin.Unsigned;
import kotlin.UnsignedUtils;
import kotlin.WasExperimental;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ULongRange.kt */
@SinceKotlin(version = "1.5")
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��2\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010��\n��\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0007\u0018�� \u00172\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002:\u0001\u0017B\u0018\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003ø\u0001��¢\u0006\u0002\u0010\u0006J\u001b\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0003H\u0096\u0002ø\u0001��¢\u0006\u0004\b\r\u0010\u000eJ\u0013\u0010\u000f\u001a\u00020\u000b2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0096\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u000bH\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0016R\u001a\u0010\u0005\u001a\u00020\u00038VX\u0096\u0004ø\u0001��ø\u0001\u0001¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\u0004\u001a\u00020\u00038VX\u0096\u0004ø\u0001��ø\u0001\u0001¢\u0006\u0006\u001a\u0004\b\t\u0010\bø\u0001��\u0082\u0002\b\n\u0002\b\u0019\n\u0002\b!¨\u0006\u0018"}, m53d2 = {"Lkotlin/ranges/ULongRange;", "Lkotlin/ranges/ULongProgression;", "Lkotlin/ranges/ClosedRange;", "Lkotlin/ULong;", "start", "endInclusive", "(JJLkotlin/jvm/internal/DefaultConstructorMarker;)V", "getEndInclusive-s-VKNKU", "()J", "getStart-s-VKNKU", "contains", "", "value", "contains-VKZWuLQ", "(J)Z", "equals", "other", "", "hashCode", "", "isEmpty", "toString", "", "Companion", "kotlin-stdlib"})
@WasExperimental(markerClass = {Unsigned.class})
/* loaded from: Jackey Client b2.jar:kotlin/ranges/ULongRange.class */
public final class ULongRange extends ULongProgression implements Range<ULong> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private static final ULongRange EMPTY = new ULongRange(-1, 0, null);

    public /* synthetic */ ULongRange(long start, long endInclusive, DefaultConstructorMarker $constructor_marker) {
        this(start, endInclusive);
    }

    @Override // kotlin.ranges.Range
    public /* bridge */ /* synthetic */ ULong getStart() {
        return ULong.m1433boximpl(m2499getStartsVKNKU());
    }

    @Override // kotlin.ranges.Range
    public /* bridge */ /* synthetic */ ULong getEndInclusive() {
        return ULong.m1433boximpl(m2500getEndInclusivesVKNKU());
    }

    @Override // kotlin.ranges.Range
    public /* bridge */ /* synthetic */ boolean contains(ULong uLong) {
        return m2501containsVKZWuLQ(uLong.m1434unboximpl());
    }

    private ULongRange(long start, long endInclusive) {
        super(start, endInclusive, 1L, null);
    }

    /* renamed from: getStart-s-VKNKU */
    public long m2499getStartsVKNKU() {
        return m2495getFirstsVKNKU();
    }

    /* renamed from: getEndInclusive-s-VKNKU */
    public long m2500getEndInclusivesVKNKU() {
        return m2496getLastsVKNKU();
    }

    /* renamed from: contains-VKZWuLQ */
    public boolean m2501containsVKZWuLQ(long value) {
        return UnsignedUtils.ulongCompare(m2495getFirstsVKNKU(), value) <= 0 && UnsignedUtils.ulongCompare(value, m2496getLastsVKNKU()) <= 0;
    }

    @Override // kotlin.ranges.ULongProgression, kotlin.ranges.Range
    public boolean isEmpty() {
        return UnsignedUtils.ulongCompare(m2495getFirstsVKNKU(), m2496getLastsVKNKU()) > 0;
    }

    @Override // kotlin.ranges.ULongProgression
    public boolean equals(@Nullable Object other) {
        return (other instanceof ULongRange) && ((isEmpty() && ((ULongRange) other).isEmpty()) || (m2495getFirstsVKNKU() == ((ULongRange) other).m2495getFirstsVKNKU() && m2496getLastsVKNKU() == ((ULongRange) other).m2496getLastsVKNKU()));
    }

    @Override // kotlin.ranges.ULongProgression
    public int hashCode() {
        if (isEmpty()) {
            return -1;
        }
        return (31 * ((int) ULong.m1432constructorimpl(m2495getFirstsVKNKU() ^ ULong.m1432constructorimpl(m2495getFirstsVKNKU() >>> 32)))) + ((int) ULong.m1432constructorimpl(m2496getLastsVKNKU() ^ ULong.m1432constructorimpl(m2496getLastsVKNKU() >>> 32)));
    }

    @Override // kotlin.ranges.ULongProgression
    @NotNull
    public String toString() {
        return ((Object) ULong.m1429toStringimpl(m2495getFirstsVKNKU())) + ".." + ((Object) ULong.m1429toStringimpl(m2496getLastsVKNKU()));
    }

    /* compiled from: ULongRange.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0014\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n��\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, m53d2 = {"Lkotlin/ranges/ULongRange$Companion;", "", "()V", "EMPTY", "Lkotlin/ranges/ULongRange;", "getEMPTY", "()Lkotlin/ranges/ULongRange;", "kotlin-stdlib"})
    /* loaded from: Jackey Client b2.jar:kotlin/ranges/ULongRange$Companion.class */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        private Companion() {
        }

        @NotNull
        public final ULongRange getEMPTY() {
            return ULongRange.EMPTY;
        }
    }
}
