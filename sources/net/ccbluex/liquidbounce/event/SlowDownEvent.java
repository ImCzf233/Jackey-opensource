package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;

/* compiled from: Events.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0007\n\u0002\b\t\u0018��2\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\n\u0010\u0007\"\u0004\b\u000b\u0010\t¨\u0006\f"}, m53d2 = {"Lnet/ccbluex/liquidbounce/event/SlowDownEvent;", "Lnet/ccbluex/liquidbounce/event/Event;", "strafe", "", "forward", "(FF)V", "getForward", "()F", "setForward", "(F)V", "getStrafe", "setStrafe", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/event/SlowDownEvent.class */
public final class SlowDownEvent extends Event {
    private float strafe;
    private float forward;

    public SlowDownEvent(float strafe, float forward) {
        this.strafe = strafe;
        this.forward = forward;
    }

    public final float getStrafe() {
        return this.strafe;
    }

    public final void setStrafe(float f) {
        this.strafe = f;
    }

    public final float getForward() {
        return this.forward;
    }

    public final void setForward(float f) {
        this.forward = f;
    }
}
