package kotlin.collections;

import java.util.Enumeration;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(m51mv = {1, 6, 0}, m52k = 5, m49xi = 49, m54d1 = {"��\u000e\n��\n\u0002\u0010(\n��\n\u0002\u0018\u0002\n��\u001a\u001f\u0010��\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b��\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0086\u0002¨\u0006\u0004"}, m53d2 = {"iterator", "", "T", "Ljava/util/Enumeration;", "kotlin-stdlib"}, m48xs = "kotlin/collections/CollectionsKt")
/* renamed from: kotlin.collections.CollectionsKt__IteratorsJVMKt */
/* loaded from: Jackey Client b2.jar:kotlin/collections/CollectionsKt__IteratorsJVMKt.class */
public class IteratorsJVM extends CollectionsKt__IterablesKt {
    @NotNull
    public static final <T> Iterator<T> iterator(@NotNull Enumeration<T> enumeration) {
        Intrinsics.checkNotNullParameter(enumeration, "<this>");
        return new CollectionsKt__IteratorsJVMKt$iterator$1(enumeration);
    }
}
