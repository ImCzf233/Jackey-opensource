package kotlin;

import kotlin.internal.InlineOnly;
import kotlin.jvm.internal.Intrinsics;

@Metadata(m51mv = {1, 6, 0}, m52k = 2, m49xi = 48, m54d1 = {"��\u0010\n��\n\u0002\u0010\b\n��\n\u0002\u0010\u000f\n\u0002\b\u0003\u001a&\u0010��\u001a\u00020\u0001\"\u0004\b��\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\u0004\u001a\u0002H\u0002H\u0087\f¢\u0006\u0002\u0010\u0005¨\u0006\u0006"}, m53d2 = {"compareTo", "", "T", "", "other", "(Ljava/lang/Comparable;Ljava/lang/Object;)I", "kotlin-stdlib"})
/* renamed from: kotlin.CompareToKt */
/* loaded from: Jackey Client b2.jar:kotlin/CompareToKt.class */
public final class compareTo {
    /* JADX WARN: Multi-variable type inference failed */
    @SinceKotlin(version = "1.6")
    @InlineOnly
    private static final <T> int compareTo(Comparable<? super T> comparable, T t) {
        Intrinsics.checkNotNullParameter(comparable, "<this>");
        return comparable.compareTo(t);
    }
}
