package com.github.benmanes.caffeine.cache;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/* compiled from: Scheduler.java */
/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/SystemScheduler.class */
public enum SystemScheduler implements Scheduler {
    INSTANCE;
    
    static final Method delayedExecutor = getDelayedExecutorMethod();

    @Override // com.github.benmanes.caffeine.cache.Scheduler
    public Future<?> schedule(Executor executor, Runnable command, long delay, TimeUnit unit) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(command);
        Objects.requireNonNull(unit);
        try {
            Executor scheduler = (Executor) delayedExecutor.invoke(CompletableFuture.class, Long.valueOf(delay), unit, executor);
            return CompletableFuture.runAsync(command, scheduler);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    static Method getDelayedExecutorMethod() {
        try {
            return CompletableFuture.class.getMethod("delayedExecutor", Long.TYPE, TimeUnit.class, Executor.class);
        } catch (NoSuchMethodException | SecurityException e) {
            return null;
        }
    }

    public static boolean isPresent() {
        return delayedExecutor != null;
    }
}
