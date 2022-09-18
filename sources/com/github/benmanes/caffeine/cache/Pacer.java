package com.github.benmanes.caffeine.cache;

import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/Pacer.class */
public final class Pacer {
    static final long TOLERANCE = Caffeine.ceilingPowerOfTwo(TimeUnit.SECONDS.toNanos(1));
    final Scheduler scheduler;
    long nextFireTime;
    Future<?> future;

    public Pacer(Scheduler scheduler) {
        this.scheduler = (Scheduler) Objects.requireNonNull(scheduler);
    }

    public void schedule(Executor executor, Runnable command, long now, long delay) {
        long scheduleAt = now + delay;
        if (this.future == null) {
            if (this.nextFireTime != 0) {
                return;
            }
        } else if (this.nextFireTime - now > 0) {
            if (maySkip(scheduleAt)) {
                return;
            }
            this.future.cancel(false);
        }
        long actualDelay = calculateSchedule(now, delay, scheduleAt);
        this.future = this.scheduler.schedule(executor, command, actualDelay, TimeUnit.NANOSECONDS);
    }

    boolean maySkip(long scheduleAt) {
        long delta = scheduleAt - this.nextFireTime;
        return delta >= 0 || (-delta) <= TOLERANCE;
    }

    long calculateSchedule(long now, long delay, long scheduleAt) {
        if (delay <= TOLERANCE) {
            this.nextFireTime = now + TOLERANCE;
            return TOLERANCE;
        }
        this.nextFireTime = scheduleAt;
        return delay;
    }
}
