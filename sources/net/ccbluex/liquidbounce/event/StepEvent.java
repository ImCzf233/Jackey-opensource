package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;

/* compiled from: Events.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0007\n\u0002\b\u0005\u0018��2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\u0004¨\u0006\b"}, m53d2 = {"Lnet/ccbluex/liquidbounce/event/StepEvent;", "Lnet/ccbluex/liquidbounce/event/Event;", "stepHeight", "", "(F)V", "getStepHeight", "()F", "setStepHeight", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/event/StepEvent.class */
public final class StepEvent extends Event {
    private float stepHeight;

    public StepEvent(float stepHeight) {
        this.stepHeight = stepHeight;
    }

    public final float getStepHeight() {
        return this.stepHeight;
    }

    public final void setStepHeight(float f) {
        this.stepHeight = f;
    }
}
