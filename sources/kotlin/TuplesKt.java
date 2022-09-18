package kotlin;

import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: Tuples.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 2, m49xi = 48, m54d1 = {"��\u0016\n��\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n��\n\u0002\u0018\u0002\n��\u001a2\u0010��\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b��\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0002H\u00022\u0006\u0010\u0004\u001a\u0002H\u0003H\u0086\u0004¢\u0006\u0002\u0010\u0005\u001a\"\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\b0\u0007\"\u0004\b��\u0010\b*\u000e\u0012\u0004\u0012\u0002H\b\u0012\u0004\u0012\u0002H\b0\u0001\u001a(\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\b0\u0007\"\u0004\b��\u0010\b*\u0014\u0012\u0004\u0012\u0002H\b\u0012\u0004\u0012\u0002H\b\u0012\u0004\u0012\u0002H\b0\t¨\u0006\n"}, m53d2 = {"to", "Lkotlin/Pair;", "A", "B", "that", "(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;", "toList", "", "T", "Lkotlin/Triple;", "kotlin-stdlib"})
@JvmName(name = "TuplesKt")
/* loaded from: Jackey Client b2.jar:kotlin/TuplesKt.class */
public final class TuplesKt {
    @NotNull
    /* renamed from: to */
    public static final <A, B> Tuples<A, B> m46to(A a, B b) {
        return new Tuples<>(a, b);
    }

    @NotNull
    public static final <T> List<T> toList(@NotNull Tuples<? extends T, ? extends T> tuples) {
        Intrinsics.checkNotNullParameter(tuples, "<this>");
        return CollectionsKt.listOf(tuples.getFirst(), tuples.getSecond());
    }

    @NotNull
    public static final <T> List<T> toList(@NotNull Triple<? extends T, ? extends T, ? extends T> triple) {
        Intrinsics.checkNotNullParameter(triple, "<this>");
        return CollectionsKt.listOf(triple.getFirst(), triple.getSecond(), triple.getThird());
    }
}
