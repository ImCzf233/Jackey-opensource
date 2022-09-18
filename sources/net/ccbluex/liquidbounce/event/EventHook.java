package net.ccbluex.liquidbounce.event;

import java.lang.reflect.Method;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: Listenable.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��.\n\u0002\u0018\u0002\n\u0002\u0010��\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0006\b��\u0018��2\u00020\u0001B\u001f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bB%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n��\u001a\u0004\b\f\u0010\rR\u0011\u0010\u000e\u001a\u00020\u000f¢\u0006\b\n��\u001a\u0004\b\u000e\u0010\u0010R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n��\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n��\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u0015"}, m53d2 = {"Lnet/ccbluex/liquidbounce/event/EventHook;", "", "eventClass", "Lnet/ccbluex/liquidbounce/event/Listenable;", "method", "Ljava/lang/reflect/Method;", "eventTarget", "Lnet/ccbluex/liquidbounce/event/EventTarget;", "(Lnet/ccbluex/liquidbounce/event/Listenable;Ljava/lang/reflect/Method;Lnet/ccbluex/liquidbounce/event/EventTarget;)V", "priority", "", "(Lnet/ccbluex/liquidbounce/event/Listenable;Ljava/lang/reflect/Method;ILnet/ccbluex/liquidbounce/event/EventTarget;)V", "getEventClass", "()Lnet/ccbluex/liquidbounce/event/Listenable;", "isIgnoreCondition", "", "()Z", "getMethod", "()Ljava/lang/reflect/Method;", "getPriority", "()I", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/event/EventHook.class */
public final class EventHook {
    @NotNull
    private final Listenable eventClass;
    @NotNull
    private final Method method;
    private final int priority;
    private final boolean isIgnoreCondition;

    public EventHook(@NotNull Listenable eventClass, @NotNull Method method, int priority, @NotNull EventTarget eventTarget) {
        Intrinsics.checkNotNullParameter(eventClass, "eventClass");
        Intrinsics.checkNotNullParameter(method, "method");
        Intrinsics.checkNotNullParameter(eventTarget, "eventTarget");
        this.eventClass = eventClass;
        this.method = method;
        this.priority = priority;
        this.isIgnoreCondition = eventTarget.ignoreCondition();
    }

    @NotNull
    public final Listenable getEventClass() {
        return this.eventClass;
    }

    @NotNull
    public final Method getMethod() {
        return this.method;
    }

    public final int getPriority() {
        return this.priority;
    }

    public final boolean isIgnoreCondition() {
        return this.isIgnoreCondition;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public EventHook(@NotNull Listenable eventClass, @NotNull Method method, @NotNull EventTarget eventTarget) {
        this(eventClass, method, 0, eventTarget);
        Intrinsics.checkNotNullParameter(eventClass, "eventClass");
        Intrinsics.checkNotNullParameter(method, "method");
        Intrinsics.checkNotNullParameter(eventTarget, "eventTarget");
    }
}
