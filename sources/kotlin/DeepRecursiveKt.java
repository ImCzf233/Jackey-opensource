package kotlin;

import kotlin.Result;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.util.Constants;

/* compiled from: DeepRecursive.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 2, m49xi = 48, m54d1 = {"��,\n��\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\u001a2\u0010\u0006\u001a\u0002H\u0007\"\u0004\b��\u0010\b\"\u0004\b\u0001\u0010\u0007*\u000e\u0012\u0004\u0012\u0002H\b\u0012\u0004\u0012\u0002H\u00070\t2\u0006\u0010\n\u001a\u0002H\bH\u0087\u0002¢\u0006\u0002\u0010\u000b\"!\u0010��\u001a\b\u0012\u0004\u0012\u00020\u00020\u00018\u0002X\u0083\u0004ø\u0001��¢\u0006\n\n\u0002\u0010\u0005\u0012\u0004\b\u0003\u0010\u0004*v\b\u0003\u0010\f\"5\b\u0001\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u000f\u0012\u0006\u0012\u0004\u0018\u00010\u00020\r¢\u0006\u0002\b\u001025\b\u0001\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u000f\u0012\u0006\u0012\u0004\u0018\u00010\u00020\r¢\u0006\u0002\b\u0010B\u0002\b\u0011\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0012"}, m53d2 = {"UNDEFINED_RESULT", "Lkotlin/Result;", "", "getUNDEFINED_RESULT$annotations", "()V", Constants.OBJECT, "invoke", "R", "T", "Lkotlin/DeepRecursiveFunction;", "value", "(Lkotlin/DeepRecursiveFunction;Ljava/lang/Object;)Ljava/lang/Object;", "DeepRecursiveFunctionBlock", "Lkotlin/Function3;", "Lkotlin/DeepRecursiveScope;", "Lkotlin/coroutines/Continuation;", "Lkotlin/ExtensionFunctionType;", "Lkotlin/ExperimentalStdlibApi;", "kotlin-stdlib"})
/* loaded from: Jackey Client b2.jar:kotlin/DeepRecursiveKt.class */
public final class DeepRecursiveKt {
    @NotNull
    private static final Object UNDEFINED_RESULT = Result.m1214constructorimpl(IntrinsicsKt.getCOROUTINE_SUSPENDED());

    private static /* synthetic */ void getUNDEFINED_RESULT$annotations() {
    }

    @ExperimentalStdlibApi
    private static /* synthetic */ void DeepRecursiveFunctionBlock$annotations() {
    }

    @SinceKotlin(version = "1.4")
    @ExperimentalStdlibApi
    public static final <T, R> R invoke(@NotNull DeepRecursive<T, R> deepRecursive, T t) {
        Intrinsics.checkNotNullParameter(deepRecursive, "<this>");
        return (R) new DeepRecursiveScopeImpl(deepRecursive.getBlock$kotlin_stdlib(), t).runCallLoop();
    }

    static {
        Result.Companion companion = Result.Companion;
    }
}
