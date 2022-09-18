package kotlin.reflect;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m51mv = {1, 6, 0}, m52k = 2, m49xi = 48, m54d1 = {"��\u000e\n��\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u001f\u0010��\u001a\u0004\u0018\u00010\u0001*\u0006\u0012\u0002\b\u00030\u00028À\u0002X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0005"}, m53d2 = {"qualifiedOrSimpleName", "", "Lkotlin/reflect/KClass;", "getQualifiedOrSimpleName", "(Lkotlin/reflect/KClass;)Ljava/lang/String;", "kotlin-stdlib"})
/* renamed from: kotlin.reflect.KClassesImplKt */
/* loaded from: Jackey Client b2.jar:kotlin/reflect/KClassesImplKt.class */
public final class KClassesImpl {
    @Nullable
    public static final String getQualifiedOrSimpleName(@NotNull KClass<?> kClass) {
        Intrinsics.checkNotNullParameter(kClass, "<this>");
        return kClass.getQualifiedName();
    }
}
