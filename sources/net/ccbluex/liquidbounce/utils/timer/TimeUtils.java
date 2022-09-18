package net.ccbluex.liquidbounce.utils.timer;

import net.ccbluex.liquidbounce.utils.misc.RandomUtils;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/timer/TimeUtils.class */
public final class TimeUtils {
    public static long randomDelay(int minDelay, int maxDelay) {
        return RandomUtils.nextInt(minDelay, maxDelay);
    }

    public static long randomClickDelay(int minCPS, int maxCPS) {
        return (long) ((Math.random() * (((1000 / minCPS) - (1000 / maxCPS)) + 1)) + (1000 / maxCPS));
    }
}
