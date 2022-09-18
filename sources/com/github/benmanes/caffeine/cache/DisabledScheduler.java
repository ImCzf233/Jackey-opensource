package com.github.benmanes.caffeine.cache;

import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/* compiled from: Scheduler.java */
/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/DisabledScheduler.class */
public enum DisabledScheduler implements Scheduler {
    INSTANCE;

    @Override // com.github.benmanes.caffeine.cache.Scheduler
    public Future<Void> schedule(Executor executor, Runnable command, long delay, TimeUnit unit) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(command);
        Objects.requireNonNull(unit);
        return DisabledFuture.INSTANCE;
    }
}
