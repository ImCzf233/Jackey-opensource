package net.ccbluex.liquidbounce.utils.timer;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/timer/MSTimer.class */
public final class MSTimer {
    public long time = -1;

    public boolean hasTimePassed(long MS) {
        return System.currentTimeMillis() >= this.time + MS;
    }

    public long hasTimeLeft(long MS) {
        return (MS + this.time) - System.currentTimeMillis();
    }

    public void reset() {
        this.time = System.currentTimeMillis();
    }
}
