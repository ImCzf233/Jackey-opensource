package kotlin.coroutines.cancellation;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.internal.InlineOnly;

@Metadata(m51mv = {1, 6, 0}, m52k = 2, m49xi = 48, m54d1 = {"��\u001e\n��\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000e\n��\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a!\u0010��\u001a\u00060\u0001j\u0002`\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0087\b\u001a\u0017\u0010��\u001a\u00060\u0001j\u0002`\u00022\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0087\b*\u001a\b\u0007\u0010��\"\u00020\u00012\u00020\u0001B\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t¨\u0006\n"}, m53d2 = {"CancellationException", "Ljava/util/concurrent/CancellationException;", "Lkotlin/coroutines/cancellation/CancellationException;", "message", "", "cause", "", "Lkotlin/SinceKotlin;", "version", "1.4", "kotlin-stdlib"})
/* renamed from: kotlin.coroutines.cancellation.CancellationExceptionKt */
/* loaded from: Jackey Client b2.jar:kotlin/coroutines/cancellation/CancellationExceptionKt.class */
public final class CancellationException {
    @SinceKotlin(version = "1.4")
    public static /* synthetic */ void CancellationException$annotations() {
    }

    @SinceKotlin(version = "1.4")
    @InlineOnly
    private static final java.util.concurrent.CancellationException CancellationException(String message, Throwable cause) {
        java.util.concurrent.CancellationException it = new java.util.concurrent.CancellationException(message);
        it.initCause(cause);
        return it;
    }

    @SinceKotlin(version = "1.4")
    @InlineOnly
    private static final java.util.concurrent.CancellationException CancellationException(Throwable cause) {
        java.util.concurrent.CancellationException it = new java.util.concurrent.CancellationException(cause == null ? null : cause.toString());
        it.initCause(cause);
        return it;
    }
}
