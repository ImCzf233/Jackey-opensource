package kotlin.reflect;

import kotlin.ExperimentalStdlibApi;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.WasExperimental;
import kotlin.internal.LowPriorityInOverloadResolution;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: KClasses.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 2, m49xi = 48, m54d1 = {"��\u0010\n\u0002\b\u0002\n\u0002\u0010��\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a+\u0010��\u001a\u0002H\u0001\"\b\b��\u0010\u0001*\u00020\u0002*\b\u0012\u0004\u0012\u0002H\u00010\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0002H\u0007¢\u0006\u0002\u0010\u0005\u001a-\u0010\u0006\u001a\u0004\u0018\u0001H\u0001\"\b\b��\u0010\u0001*\u00020\u0002*\b\u0012\u0004\u0012\u0002H\u00010\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0002H\u0007¢\u0006\u0002\u0010\u0005¨\u0006\u0007"}, m53d2 = {"cast", "T", "", "Lkotlin/reflect/KClass;", "value", "(Lkotlin/reflect/KClass;Ljava/lang/Object;)Ljava/lang/Object;", "safeCast", "kotlin-stdlib"})
@JvmName(name = "KClasses")
/* loaded from: Jackey Client b2.jar:kotlin/reflect/KClasses.class */
public final class KClasses {
    /* JADX WARN: Multi-variable type inference failed */
    @SinceKotlin(version = "1.4")
    @NotNull
    @LowPriorityInOverloadResolution
    @WasExperimental(markerClass = {ExperimentalStdlibApi.class})
    public static final <T> T cast(@NotNull KClass<T> kClass, @Nullable Object value) {
        Intrinsics.checkNotNullParameter(kClass, "<this>");
        if (!kClass.isInstance(value)) {
            throw new ClassCastException(Intrinsics.stringPlus("Value cannot be cast to ", kClass.getQualifiedName()));
        }
        if (value != 0) {
            return value;
        }
        throw new NullPointerException("null cannot be cast to non-null type T of kotlin.reflect.KClasses.cast");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @SinceKotlin(version = "1.4")
    @Nullable
    @LowPriorityInOverloadResolution
    @WasExperimental(markerClass = {ExperimentalStdlibApi.class})
    public static final <T> T safeCast(@NotNull KClass<T> kClass, @Nullable Object value) {
        Intrinsics.checkNotNullParameter(kClass, "<this>");
        if (kClass.isInstance(value)) {
            if (value != 0) {
                return value;
            }
            throw new NullPointerException("null cannot be cast to non-null type T of kotlin.reflect.KClasses.safeCast");
        }
        return null;
    }
}
