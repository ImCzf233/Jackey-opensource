package com.github.benmanes.caffeine.cache;

import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@FunctionalInterface
/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/Scheduler.class */
public interface Scheduler {
    Future<?> schedule(Executor executor, Runnable runnable, long j, TimeUnit timeUnit);

    static Scheduler disabledScheduler() {
        return DisabledScheduler.INSTANCE;
    }

    static Scheduler systemScheduler() {
        return SystemScheduler.isPresent() ? SystemScheduler.INSTANCE : disabledScheduler();
    }

    static Scheduler forScheduledExecutorService(ScheduledExecutorService scheduledExecutorService) {
        return new ExecutorServiceScheduler(scheduledExecutorService);
    }

    static Scheduler guardedScheduler(Scheduler scheduler) {
        return scheduler instanceof GuardedScheduler ? scheduler : new GuardedScheduler(scheduler);
    }
}
