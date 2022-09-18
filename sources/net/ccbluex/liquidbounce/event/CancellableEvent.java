package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;

/* compiled from: Event.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n��\b\u0016\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0007\u001a\u00020\bR\u001e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@BX\u0086\u000e¢\u0006\b\n��\u001a\u0004\b\u0005\u0010\u0006¨\u0006\t"}, m53d2 = {"Lnet/ccbluex/liquidbounce/event/CancellableEvent;", "Lnet/ccbluex/liquidbounce/event/Event;", "()V", "<set-?>", "", "isCancelled", "()Z", "cancelEvent", "", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/event/CancellableEvent.class */
public class CancellableEvent extends Event {
    private boolean isCancelled;

    public final boolean isCancelled() {
        return this.isCancelled;
    }

    public final void cancelEvent() {
        this.isCancelled = true;
    }
}
