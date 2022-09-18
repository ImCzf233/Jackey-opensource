package kotlin.coroutines.jvm.internal;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import org.jetbrains.annotations.Nullable;

/* compiled from: CoroutineStackFrame.kt */
@SinceKotlin(version = "1.3")
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0012\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0004\n\u0002\u0018\u0002\n��\bg\u0018��2\u00020\u0001J\n\u0010\u0005\u001a\u0004\u0018\u00010\u0006H&R\u0014\u0010\u0002\u001a\u0004\u0018\u00010��X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0007"}, m53d2 = {"Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "", "callerFrame", "getCallerFrame", "()Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "getStackTraceElement", "Ljava/lang/StackTraceElement;", "kotlin-stdlib"})
/* loaded from: Jackey Client b2.jar:kotlin/coroutines/jvm/internal/CoroutineStackFrame.class */
public interface CoroutineStackFrame {
    @Nullable
    CoroutineStackFrame getCallerFrame();

    @Nullable
    StackTraceElement getStackTraceElement();
}
