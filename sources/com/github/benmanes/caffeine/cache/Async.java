package com.github.benmanes.caffeine.cache;

import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;
import kotlin.time.DurationKt;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/Async.class */
public final class Async {
    static final long ASYNC_EXPIRY = 6917529027641081854L;

    private Async() {
    }

    public static boolean isReady(CompletableFuture<?> future) {
        return future != null && future.isDone() && !future.isCompletedExceptionally() && future.join() != null;
    }

    public static <V> V getIfReady(CompletableFuture<V> future) {
        if (isReady(future)) {
            return future.join();
        }
        return null;
    }

    public static <V> V getWhenSuccessful(CompletableFuture<V> future) {
        if (future == null) {
            return null;
        }
        try {
            return future.join();
        } catch (CancellationException | CompletionException e) {
            return null;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/Async$AsyncRemovalListener.class */
    public static final class AsyncRemovalListener<K, V> implements RemovalListener<K, CompletableFuture<V>>, Serializable {
        private static final long serialVersionUID = 1;
        final RemovalListener<K, V> delegate;
        final Executor executor;

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.github.benmanes.caffeine.cache.RemovalListener
        public /* bridge */ /* synthetic */ void onRemoval(Object obj, Object obj2, RemovalCause removalCause) {
            onRemoval((AsyncRemovalListener<K, V>) obj, (CompletableFuture) ((CompletableFuture) obj2), removalCause);
        }

        public AsyncRemovalListener(RemovalListener<K, V> delegate, Executor executor) {
            this.delegate = (RemovalListener) Objects.requireNonNull(delegate);
            this.executor = (Executor) Objects.requireNonNull(executor);
        }

        public void onRemoval(K key, CompletableFuture<V> future, RemovalCause cause) {
            if (future != null) {
                future.thenAcceptAsync(value -> {
                    if (cause != null) {
                        this.delegate.onRemoval(key, cause, key);
                    }
                }, this.executor);
            }
        }

        Object writeReplace() {
            return this.delegate;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/Async$AsyncWeigher.class */
    public static final class AsyncWeigher<K, V> implements Weigher<K, CompletableFuture<V>>, Serializable {
        private static final long serialVersionUID = 1;
        final Weigher<K, V> delegate;

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.github.benmanes.caffeine.cache.Weigher
        public /* bridge */ /* synthetic */ int weigh(Object obj, Object obj2) {
            return weigh((AsyncWeigher<K, V>) obj, (CompletableFuture) ((CompletableFuture) obj2));
        }

        public AsyncWeigher(Weigher<K, V> delegate) {
            this.delegate = (Weigher) Objects.requireNonNull(delegate);
        }

        public int weigh(K key, CompletableFuture<V> future) {
            if (Async.isReady(future)) {
                return this.delegate.weigh(key, future.join());
            }
            return 0;
        }

        Object writeReplace() {
            return this.delegate;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/Async$AsyncExpiry.class */
    public static final class AsyncExpiry<K, V> implements Expiry<K, CompletableFuture<V>>, Serializable {
        private static final long serialVersionUID = 1;
        final Expiry<K, V> delegate;

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.github.benmanes.caffeine.cache.Expiry
        public /* bridge */ /* synthetic */ long expireAfterRead(Object obj, Object obj2, long j, long j2) {
            return expireAfterRead((AsyncExpiry<K, V>) obj, (CompletableFuture) ((CompletableFuture) obj2), j, j2);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.github.benmanes.caffeine.cache.Expiry
        public /* bridge */ /* synthetic */ long expireAfterUpdate(Object obj, Object obj2, long j, long j2) {
            return expireAfterUpdate((AsyncExpiry<K, V>) obj, (CompletableFuture) ((CompletableFuture) obj2), j, j2);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.github.benmanes.caffeine.cache.Expiry
        public /* bridge */ /* synthetic */ long expireAfterCreate(Object obj, Object obj2, long j) {
            return expireAfterCreate((AsyncExpiry<K, V>) obj, (CompletableFuture) ((CompletableFuture) obj2), j);
        }

        public AsyncExpiry(Expiry<K, V> delegate) {
            this.delegate = (Expiry) Objects.requireNonNull(delegate);
        }

        public long expireAfterCreate(K key, CompletableFuture<V> future, long currentTime) {
            if (Async.isReady(future)) {
                long duration = this.delegate.expireAfterCreate(key, future.join(), currentTime);
                return Math.min(duration, (long) DurationKt.MAX_MILLIS);
            }
            return Async.ASYNC_EXPIRY;
        }

        public long expireAfterUpdate(K key, CompletableFuture<V> future, long currentTime, long currentDuration) {
            long j;
            if (Async.isReady(future)) {
                if (currentDuration > DurationKt.MAX_MILLIS) {
                    j = this.delegate.expireAfterCreate(key, future.join(), currentTime);
                } else {
                    j = this.delegate.expireAfterUpdate(key, future.join(), currentTime, currentDuration);
                }
                long duration = j;
                return Math.min(duration, (long) DurationKt.MAX_MILLIS);
            }
            return Async.ASYNC_EXPIRY;
        }

        public long expireAfterRead(K key, CompletableFuture<V> future, long currentTime, long currentDuration) {
            if (Async.isReady(future)) {
                long duration = this.delegate.expireAfterRead(key, future.join(), currentTime, currentDuration);
                return Math.min(duration, (long) DurationKt.MAX_MILLIS);
            }
            return Async.ASYNC_EXPIRY;
        }

        Object writeReplace() {
            return this.delegate;
        }
    }
}
