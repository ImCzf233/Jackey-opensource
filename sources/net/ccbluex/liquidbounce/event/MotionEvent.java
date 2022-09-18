package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: Events.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0017\u0018��2\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bR\u001a\u0010\f\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\b\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u001e\u0010\u001b\"\u0004\b\u001f\u0010\u001dR\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b \u0010\u0017\"\u0004\b!\u0010\u0019R\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\"\u0010\u001b\"\u0004\b#\u0010\u001d¨\u0006$"}, m53d2 = {"Lnet/ccbluex/liquidbounce/event/MotionEvent;", "Lnet/ccbluex/liquidbounce/event/Event;", "x", "", "y", "z", "yaw", "", "pitch", "onGround", "", "(DDDFFZ)V", "eventState", "Lnet/ccbluex/liquidbounce/event/EventState;", "getEventState", "()Lnet/ccbluex/liquidbounce/event/EventState;", "setEventState", "(Lnet/ccbluex/liquidbounce/event/EventState;)V", "getOnGround", "()Z", "setOnGround", "(Z)V", "getPitch", "()F", "setPitch", "(F)V", "getX", "()D", "setX", "(D)V", "getY", "setY", "getYaw", "setYaw", "getZ", "setZ", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/event/MotionEvent.class */
public final class MotionEvent extends Event {

    /* renamed from: x */
    private double f325x;

    /* renamed from: y */
    private double f326y;

    /* renamed from: z */
    private double f327z;
    private float yaw;
    private float pitch;
    private boolean onGround;
    @NotNull
    private EventState eventState = EventState.PRE;

    public MotionEvent(double x, double y, double z, float yaw, float pitch, boolean onGround) {
        this.f325x = x;
        this.f326y = y;
        this.f327z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }

    public final double getX() {
        return this.f325x;
    }

    public final void setX(double d) {
        this.f325x = d;
    }

    public final double getY() {
        return this.f326y;
    }

    public final void setY(double d) {
        this.f326y = d;
    }

    public final double getZ() {
        return this.f327z;
    }

    public final void setZ(double d) {
        this.f327z = d;
    }

    public final float getYaw() {
        return this.yaw;
    }

    public final void setYaw(float f) {
        this.yaw = f;
    }

    public final float getPitch() {
        return this.pitch;
    }

    public final void setPitch(float f) {
        this.pitch = f;
    }

    public final boolean getOnGround() {
        return this.onGround;
    }

    public final void setOnGround(boolean z) {
        this.onGround = z;
    }

    @NotNull
    public final EventState getEventState() {
        return this.eventState;
    }

    public final void setEventState(@NotNull EventState eventState) {
        Intrinsics.checkNotNullParameter(eventState, "<set-?>");
        this.eventState = eventState;
    }
}
