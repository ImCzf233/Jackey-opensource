package kotlin;

import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: Access modifiers changed from: package-private */
@Metadata(m51mv = {1, 6, 0}, m52k = 5, m49xi = 49, m54d1 = {"��\u0018\n��\n\u0002\u0010\u0002\n��\n\u0002\u0010\u000b\n��\n\u0002\u0018\u0002\n\u0002\u0010��\n��\u001a\u0011\u0010��\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0087\b\u001a\"\u0010��\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0087\bø\u0001��\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0007"}, m53d2 = {"assert", "", "value", "", "lazyMessage", "Lkotlin/Function0;", "", "kotlin-stdlib"}, m48xs = "kotlin/PreconditionsKt")
/* renamed from: kotlin.PreconditionsKt__AssertionsJVMKt */
/* loaded from: Jackey Client b2.jar:kotlin/PreconditionsKt__AssertionsJVMKt.class */
public class AssertionsJVM {
    @InlineOnly
    /* renamed from: assert */
    private static final void m1204assert(boolean value) {
        if (_Assertions.ENABLED && !value) {
            throw new AssertionError("Assertion failed");
        }
    }

    @InlineOnly
    /* renamed from: assert */
    private static final void m1205assert(boolean value, Functions<? extends Object> lazyMessage) {
        Intrinsics.checkNotNullParameter(lazyMessage, "lazyMessage");
        if (_Assertions.ENABLED && !value) {
            Object message = lazyMessage.invoke();
            throw new AssertionError(message);
        }
    }
}
