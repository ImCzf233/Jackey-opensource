package kotlin.concurrent;

import java.util.TimerTask;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: Timer.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 176, m54d1 = {"��\u0011\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0002\n��*\u0001��\b\n\u0018��2\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016¨\u0006\u0004"}, m53d2 = {"kotlin/concurrent/TimersKt$timerTask$1", "Ljava/util/TimerTask;", "run", "", "kotlin-stdlib"})
/* loaded from: Jackey Client b2.jar:kotlin/concurrent/TimersKt$timerTask$1.class */
public final class TimersKt$timerTask$1 extends TimerTask {
    final /* synthetic */ Function1<TimerTask, Unit> $action;

    /* JADX WARN: Multi-variable type inference failed */
    public TimersKt$timerTask$1(Function1<? super TimerTask, Unit> function1) {
        this.$action = function1;
    }

    @Override // java.util.TimerTask, java.lang.Runnable
    public void run() {
        this.$action.invoke(this);
    }
}
