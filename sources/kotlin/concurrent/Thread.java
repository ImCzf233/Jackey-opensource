package kotlin.concurrent;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m51mv = {1, 6, 0}, m52k = 2, m49xi = 48, m54d1 = {"��:\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000e\n��\n\u0002\u0010\b\n��\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010��\n\u0002\u0018\u0002\n\u0002\b\u0003\u001aJ\u0010��\u001a\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\b\u0002\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f\u001a3\u0010\u000e\u001a\u0002H\u000f\"\b\b��\u0010\u000f*\u00020\u0010*\b\u0012\u0004\u0012\u0002H\u000f0\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\u000f0\fH\u0087\bø\u0001��¢\u0006\u0002\u0010\u0013\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0014"}, m53d2 = {"thread", "Ljava/lang/Thread;", "start", "", "isDaemon", "contextClassLoader", "Ljava/lang/ClassLoader;", "name", "", "priority", "", "block", "Lkotlin/Function0;", "", "getOrSet", "T", "", "Ljava/lang/ThreadLocal;", "default", "(Ljava/lang/ThreadLocal;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "kotlin-stdlib"})
@JvmName(name = "ThreadsKt")
/* renamed from: kotlin.concurrent.ThreadsKt */
/* loaded from: Jackey Client b2.jar:kotlin/concurrent/ThreadsKt.class */
public final class Thread {
    public static /* synthetic */ java.lang.Thread thread$default(boolean z, boolean z2, ClassLoader classLoader, String str, int i, Functions functions, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z = true;
        }
        if ((i2 & 2) != 0) {
            z2 = false;
        }
        if ((i2 & 4) != 0) {
            classLoader = null;
        }
        if ((i2 & 8) != 0) {
            str = null;
        }
        if ((i2 & 16) != 0) {
            i = -1;
        }
        return thread(z, z2, classLoader, str, i, functions);
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [kotlin.concurrent.ThreadsKt$thread$thread$1] */
    @NotNull
    public static final java.lang.Thread thread(boolean start, boolean isDaemon, @Nullable ClassLoader contextClassLoader, @Nullable String name, int priority, @NotNull final Functions<Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        ?? r0 = new java.lang.Thread() { // from class: kotlin.concurrent.ThreadsKt$thread$thread$1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                block.invoke();
            }
        };
        if (isDaemon) {
            r0.setDaemon(true);
        }
        if (priority > 0) {
            r0.setPriority(priority);
        }
        if (name != null) {
            r0.setName(name);
        }
        if (contextClassLoader != null) {
            r0.setContextClassLoader(contextClassLoader);
        }
        if (start) {
            r0.start();
        }
        return (java.lang.Thread) r0;
    }

    @InlineOnly
    private static final <T> T getOrSet(ThreadLocal<T> threadLocal, Functions<? extends T> functions) {
        Intrinsics.checkNotNullParameter(threadLocal, "<this>");
        Intrinsics.checkNotNullParameter(functions, "default");
        T t = threadLocal.get();
        if (t == null) {
            T invoke = functions.invoke();
            threadLocal.set(invoke);
            return invoke;
        }
        return t;
    }
}
