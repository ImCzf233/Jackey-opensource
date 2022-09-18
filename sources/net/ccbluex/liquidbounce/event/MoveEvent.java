package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;

/* compiled from: Events.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018��2\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006J\u0006\u0010\u0014\u001a\u00020\u0015J\u0006\u0010\u0016\u001a\u00020\u0015R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u0007\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u0010\u0010\r\"\u0004\b\u0011\u0010\u000fR\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u0012\u0010\r\"\u0004\b\u0013\u0010\u000f¨\u0006\u0017"}, m53d2 = {"Lnet/ccbluex/liquidbounce/event/MoveEvent;", "Lnet/ccbluex/liquidbounce/event/CancellableEvent;", "x", "", "y", "z", "(DDD)V", "isSafeWalk", "", "()Z", "setSafeWalk", "(Z)V", "getX", "()D", "setX", "(D)V", "getY", "setY", "getZ", "setZ", "zero", "", "zeroXZ", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/event/MoveEvent.class */
public final class MoveEvent extends CancellableEvent {

    /* renamed from: x */
    private double f328x;

    /* renamed from: y */
    private double f329y;

    /* renamed from: z */
    private double f330z;
    private boolean isSafeWalk;

    public MoveEvent(double x, double y, double z) {
        this.f328x = x;
        this.f329y = y;
        this.f330z = z;
    }

    public final double getX() {
        return this.f328x;
    }

    public final void setX(double d) {
        this.f328x = d;
    }

    public final double getY() {
        return this.f329y;
    }

    public final void setY(double d) {
        this.f329y = d;
    }

    public final double getZ() {
        return this.f330z;
    }

    public final void setZ(double d) {
        this.f330z = d;
    }

    public final boolean isSafeWalk() {
        return this.isSafeWalk;
    }

    public final void setSafeWalk(boolean z) {
        this.isSafeWalk = z;
    }

    public final void zero() {
        this.f328x = 0.0d;
        this.f329y = 0.0d;
        this.f330z = 0.0d;
    }

    public final void zeroXZ() {
        this.f328x = 0.0d;
        this.f330z = 0.0d;
    }
}
