package kotlin.ranges;

import java.lang.Comparable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u001a\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000f\n\u0002\u0010��\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0004\bf\u0018��*\u000e\b��\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\u00020\u0003J\u0016\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00028��H\u0096\u0002¢\u0006\u0002\u0010\fJ\b\u0010\r\u001a\u00020\nH\u0016R\u0012\u0010\u0004\u001a\u00028��X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00028��X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\u0006¨\u0006\u000e"}, m53d2 = {"Lkotlin/ranges/ClosedRange;", "T", "", "", "endInclusive", "getEndInclusive", "()Ljava/lang/Comparable;", "start", "getStart", "contains", "", "value", "(Ljava/lang/Comparable;)Z", "isEmpty", "kotlin-stdlib"})
/* renamed from: kotlin.ranges.ClosedRange */
/* loaded from: Jackey Client b2.jar:kotlin/ranges/ClosedRange.class */
public interface Range<T extends Comparable<? super T>> {
    @NotNull
    T getStart();

    @NotNull
    T getEndInclusive();

    boolean contains(@NotNull T t);

    boolean isEmpty();

    /* compiled from: Range.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48)
    /* renamed from: kotlin.ranges.ClosedRange$DefaultImpls */
    /* loaded from: Jackey Client b2.jar:kotlin/ranges/ClosedRange$DefaultImpls.class */
    public static final class DefaultImpls {
        public static <T extends Comparable<? super T>> boolean contains(@NotNull Range<T> range, @NotNull T value) {
            Intrinsics.checkNotNullParameter(range, "this");
            Intrinsics.checkNotNullParameter(value, "value");
            return value.compareTo(range.getStart()) >= 0 && value.compareTo(range.getEndInclusive()) <= 0;
        }

        public static <T extends Comparable<? super T>> boolean isEmpty(@NotNull Range<T> range) {
            Intrinsics.checkNotNullParameter(range, "this");
            return range.getStart().compareTo(range.getEndInclusive()) > 0;
        }
    }
}
