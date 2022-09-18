package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;

/* compiled from: Events.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0007\n\u0002\b\u0004\u0018��2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n��\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, m53d2 = {"Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "Lnet/ccbluex/liquidbounce/event/Event;", "partialTicks", "", "(F)V", "getPartialTicks", "()F", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/event/Render2DEvent.class */
public final class Render2DEvent extends Event {
    private final float partialTicks;

    public Render2DEvent(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public final float getPartialTicks() {
        return this.partialTicks;
    }
}
