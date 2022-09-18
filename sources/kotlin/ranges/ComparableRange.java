package kotlin.ranges;

import java.lang.Comparable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.Range;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Ranges.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��*\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000f\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n��\n\u0002\u0010��\n��\n\u0002\u0010\b\n��\n\u0002\u0010\u000e\n��\b\u0012\u0018��*\u000e\b��\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u0015\u0012\u0006\u0010\u0004\u001a\u00028��\u0012\u0006\u0010\u0005\u001a\u00028��¢\u0006\u0002\u0010\u0006J\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0096\u0002J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016R\u0016\u0010\u0005\u001a\u00028��X\u0096\u0004¢\u0006\n\n\u0002\u0010\t\u001a\u0004\b\u0007\u0010\bR\u0016\u0010\u0004\u001a\u00028��X\u0096\u0004¢\u0006\n\n\u0002\u0010\t\u001a\u0004\b\n\u0010\b¨\u0006\u0013"}, m53d2 = {"Lkotlin/ranges/ComparableRange;", "T", "", "Lkotlin/ranges/ClosedRange;", "start", "endInclusive", "(Ljava/lang/Comparable;Ljava/lang/Comparable;)V", "getEndInclusive", "()Ljava/lang/Comparable;", "Ljava/lang/Comparable;", "getStart", "equals", "", "other", "", "hashCode", "", "toString", "", "kotlin-stdlib"})
/* loaded from: Jackey Client b2.jar:kotlin/ranges/ComparableRange.class */
class ComparableRange<T extends Comparable<? super T>> implements Range<T> {
    @NotNull
    private final T start;
    @NotNull
    private final T endInclusive;

    public ComparableRange(@NotNull T start, @NotNull T endInclusive) {
        Intrinsics.checkNotNullParameter(start, "start");
        Intrinsics.checkNotNullParameter(endInclusive, "endInclusive");
        this.start = start;
        this.endInclusive = endInclusive;
    }

    @Override // kotlin.ranges.Range
    public boolean contains(@NotNull T t) {
        return Range.DefaultImpls.contains(this, t);
    }

    @Override // kotlin.ranges.Range
    public boolean isEmpty() {
        return Range.DefaultImpls.isEmpty(this);
    }

    @Override // kotlin.ranges.Range
    @NotNull
    public T getStart() {
        return this.start;
    }

    @Override // kotlin.ranges.Range
    @NotNull
    public T getEndInclusive() {
        return this.endInclusive;
    }

    public boolean equals(@Nullable Object other) {
        return (other instanceof ComparableRange) && ((isEmpty() && ((ComparableRange) other).isEmpty()) || (Intrinsics.areEqual(getStart(), ((ComparableRange) other).getStart()) && Intrinsics.areEqual(getEndInclusive(), ((ComparableRange) other).getEndInclusive())));
    }

    public int hashCode() {
        if (isEmpty()) {
            return -1;
        }
        return (31 * getStart().hashCode()) + getEndInclusive().hashCode();
    }

    @NotNull
    public String toString() {
        return getStart() + ".." + getEndInclusive();
    }
}
