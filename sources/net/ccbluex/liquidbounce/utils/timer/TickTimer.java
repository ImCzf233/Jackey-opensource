package net.ccbluex.liquidbounce.utils.timer;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/timer/TickTimer.class */
public final class TickTimer {
    public int tick;

    public void update() {
        this.tick++;
    }

    public void reset() {
        this.tick = 0;
    }

    public boolean hasTimePassed(int ticks) {
        return this.tick >= ticks;
    }
}
