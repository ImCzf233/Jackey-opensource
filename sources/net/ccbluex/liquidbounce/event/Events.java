package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;

@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000b\n\u0002\b\t\u0018��2\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\n\u0010\u0007\"\u0004\b\u000b\u0010\t¨\u0006\f"}, m53d2 = {"Lnet/ccbluex/liquidbounce/event/ActionEvent;", "Lnet/ccbluex/liquidbounce/event/Event;", "sprinting", "", "sneaking", "(ZZ)V", "getSneaking", "()Z", "setSneaking", "(Z)V", "getSprinting", "setSprinting", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.event.ActionEvent */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/event/ActionEvent.class */
public final class Events extends Event {
    private boolean sprinting;
    private boolean sneaking;

    public Events(boolean sprinting, boolean sneaking) {
        this.sprinting = sprinting;
        this.sneaking = sneaking;
    }

    public final boolean getSprinting() {
        return this.sprinting;
    }

    public final void setSprinting(boolean z) {
        this.sprinting = z;
    }

    public final boolean getSneaking() {
        return this.sneaking;
    }

    public final void setSneaking(boolean z) {
        this.sneaking = z;
    }
}
