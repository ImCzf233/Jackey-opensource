package com.github.benmanes.caffeine.cache;

import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/* compiled from: Scheduler.java */
/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/GuardedScheduler.class */
public final class GuardedScheduler implements Scheduler, Serializable {
    static final Logger logger = Logger.getLogger(GuardedScheduler.class.getName());
    static final long serialVersionUID = 1;
    final Scheduler delegate;

    public GuardedScheduler(Scheduler delegate) {
        this.delegate = (Scheduler) Objects.requireNonNull(delegate);
    }

    @Override // com.github.benmanes.caffeine.cache.Scheduler
    public Future<?> schedule(Executor executor, Runnable command, long delay, TimeUnit unit) {
        try {
            Future<?> future = this.delegate.schedule(executor, command, delay, unit);
            return future == null ? DisabledFuture.INSTANCE : future;
        } catch (Throwable t) {
            logger.log(Level.WARNING, "Exception thrown by scheduler; discarded task", t);
            return DisabledFuture.INSTANCE;
        }
    }
}
