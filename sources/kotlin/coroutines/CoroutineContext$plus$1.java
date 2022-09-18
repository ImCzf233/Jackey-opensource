package kotlin.coroutines;

import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: CoroutineContext.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 3, m49xi = 48, m54d1 = {"��\u0010\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\u0010��\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\n¢\u0006\u0002\b\u0005"}, m53d2 = {"<anonymous>", "Lkotlin/coroutines/CoroutineContext;", "acc", "element", "Lkotlin/coroutines/CoroutineContext$Element;", "invoke"})
/* loaded from: Jackey Client b2.jar:kotlin/coroutines/CoroutineContext$plus$1.class */
public final class CoroutineContext$plus$1 extends Lambda implements Function2<CoroutineContext, CoroutineContext.Element, CoroutineContext> {
    public static final CoroutineContext$plus$1 INSTANCE = new CoroutineContext$plus$1();

    CoroutineContext$plus$1() {
        super(2);
    }

    @NotNull
    public final CoroutineContext invoke(@NotNull CoroutineContext acc, @NotNull CoroutineContext.Element element) {
        CombinedContext combinedContext;
        Intrinsics.checkNotNullParameter(acc, "acc");
        Intrinsics.checkNotNullParameter(element, "element");
        CoroutineContext removed = acc.minusKey(element.getKey());
        if (removed == EmptyCoroutineContext.INSTANCE) {
            return element;
        }
        ContinuationInterceptor interceptor = (ContinuationInterceptor) removed.get(ContinuationInterceptor.Key);
        if (interceptor == null) {
            combinedContext = new CombinedContext(removed, element);
        } else {
            CoroutineContext left = removed.minusKey(ContinuationInterceptor.Key);
            combinedContext = left == EmptyCoroutineContext.INSTANCE ? new CombinedContext(element, interceptor) : new CombinedContext(new CombinedContext(left, element), interceptor);
        }
        return combinedContext;
    }
}
