package kotlin.concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import kotlin.Metadata;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(m51mv = {1, 6, 0}, m52k = 2, m49xi = 48, m54d1 = {"��\u001a\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a6\u0010��\u001a\u0002H\u0001\"\u0004\b��\u0010\u0001*\u00020\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0004H\u0087\bø\u0001��\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0002\u0010\u0005\u001a6\u0010\u0006\u001a\u0002H\u0001\"\u0004\b��\u0010\u0001*\u00020\u00072\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0004H\u0087\bø\u0001��\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0002\u0010\b\u001a6\u0010\t\u001a\u0002H\u0001\"\u0004\b��\u0010\u0001*\u00020\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0004H\u0087\bø\u0001��\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0002\u0010\u0005\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\n"}, m53d2 = {"read", "T", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "action", "Lkotlin/Function0;", "(Ljava/util/concurrent/locks/ReentrantReadWriteLock;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withLock", "Ljava/util/concurrent/locks/Lock;", "(Ljava/util/concurrent/locks/Lock;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "write", "kotlin-stdlib"})
@JvmName(name = "LocksKt")
/* renamed from: kotlin.concurrent.LocksKt */
/* loaded from: Jackey Client b2.jar:kotlin/concurrent/LocksKt.class */
public final class Locks {
    @InlineOnly
    private static final <T> T withLock(Lock $this$withLock, Functions<? extends T> action) {
        Intrinsics.checkNotNullParameter($this$withLock, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        $this$withLock.lock();
        try {
            T invoke = action.invoke();
            InlineMarker.finallyStart(1);
            $this$withLock.unlock();
            InlineMarker.finallyEnd(1);
            return invoke;
        } catch (Throwable th) {
            InlineMarker.finallyStart(1);
            $this$withLock.unlock();
            InlineMarker.finallyEnd(1);
            throw th;
        }
    }

    @InlineOnly
    private static final <T> T read(ReentrantReadWriteLock $this$read, Functions<? extends T> action) {
        Intrinsics.checkNotNullParameter($this$read, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        ReentrantReadWriteLock.ReadLock rl = $this$read.readLock();
        rl.lock();
        try {
            T invoke = action.invoke();
            InlineMarker.finallyStart(1);
            rl.unlock();
            InlineMarker.finallyEnd(1);
            return invoke;
        } catch (Throwable th) {
            InlineMarker.finallyStart(1);
            rl.unlock();
            InlineMarker.finallyEnd(1);
            throw th;
        }
    }

    @InlineOnly
    private static final <T> T write(ReentrantReadWriteLock $this$write, Functions<? extends T> action) {
        Intrinsics.checkNotNullParameter($this$write, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        ReentrantReadWriteLock.ReadLock rl = $this$write.readLock();
        int readCount = $this$write.getWriteHoldCount() == 0 ? $this$write.getReadHoldCount() : 0;
        int i = 0;
        while (i < readCount) {
            i++;
            rl.unlock();
        }
        ReentrantReadWriteLock.WriteLock wl = $this$write.writeLock();
        wl.lock();
        try {
            T invoke = action.invoke();
            InlineMarker.finallyStart(1);
            int i2 = 0;
            while (i2 < readCount) {
                i2++;
                rl.lock();
            }
            wl.unlock();
            InlineMarker.finallyEnd(1);
            return invoke;
        } catch (Throwable th) {
            InlineMarker.finallyStart(1);
            int i3 = 0;
            while (i3 < readCount) {
                i3++;
                rl.lock();
            }
            wl.unlock();
            InlineMarker.finallyEnd(1);
            throw th;
        }
    }
}
