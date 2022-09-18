package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.LocalAsyncCache;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/LocalAsyncLoadingCache.class */
public abstract class LocalAsyncLoadingCache<K, V> implements LocalAsyncCache<K, V>, AsyncLoadingCache<K, V> {
    static final Logger logger = Logger.getLogger(LocalAsyncLoadingCache.class.getName());
    final boolean canBulkLoad;
    final AsyncCacheLoader<K, V> loader;
    LoadingCacheView<K, V> cacheView;

    /* JADX WARN: Multi-variable type inference failed */
    public LocalAsyncLoadingCache(AsyncCacheLoader<? super K, V> loader) {
        this.loader = loader;
        this.canBulkLoad = canBulkLoad(loader);
    }

    private static boolean canBulkLoad(AsyncCacheLoader<?, ?> loader) {
        try {
            Class<?> defaultLoaderClass = AsyncCacheLoader.class;
            if (loader instanceof CacheLoader) {
                defaultLoaderClass = CacheLoader.class;
                Method classLoadAll = loader.getClass().getMethod("loadAll", Iterable.class);
                Method defaultLoadAll = CacheLoader.class.getMethod("loadAll", Iterable.class);
                if (!classLoadAll.equals(defaultLoadAll)) {
                    return true;
                }
            }
            Method classAsyncLoadAll = loader.getClass().getMethod("asyncLoadAll", Iterable.class, Executor.class);
            Method defaultAsyncLoadAll = defaultLoaderClass.getMethod("asyncLoadAll", Iterable.class, Executor.class);
            return !classAsyncLoadAll.equals(defaultAsyncLoadAll);
        } catch (NoSuchMethodException | SecurityException e) {
            logger.log(Level.WARNING, "Cannot determine if CacheLoader can bulk load", (Throwable) e);
            return false;
        }
    }

    @Override // com.github.benmanes.caffeine.cache.AsyncLoadingCache
    public CompletableFuture<V> get(K key) {
        AsyncCacheLoader<K, V> asyncCacheLoader = this.loader;
        Objects.requireNonNull(asyncCacheLoader);
        return get((LocalAsyncLoadingCache<K, V>) key, (BiFunction<? super LocalAsyncLoadingCache<K, V>, Executor, CompletableFuture<V>>) this::asyncLoad);
    }

    @Override // com.github.benmanes.caffeine.cache.AsyncLoadingCache
    public CompletableFuture<Map<K, V>> getAll(Iterable<? extends K> keys) {
        if (this.canBulkLoad) {
            AsyncCacheLoader<K, V> asyncCacheLoader = this.loader;
            Objects.requireNonNull(asyncCacheLoader);
            return getAll(keys, this::asyncLoadAll);
        }
        Map<K, CompletableFuture<V>> result = new LinkedHashMap<>();
        Function<? super K, ? extends CompletableFuture<V>> function = this::get;
        for (K key : keys) {
            CompletableFuture<V> future = result.computeIfAbsent(key, function);
            Objects.requireNonNull(future);
        }
        return composeResult(result);
    }

    @Override // com.github.benmanes.caffeine.cache.AsyncCache
    public LoadingCache<K, V> synchronous() {
        if (this.cacheView == null) {
            LoadingCacheView<K, V> loadingCacheView = new LoadingCacheView<>(this);
            this.cacheView = loadingCacheView;
            return loadingCacheView;
        }
        return this.cacheView;
    }

    /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/LocalAsyncLoadingCache$LoadingCacheView.class */
    public static final class LoadingCacheView<K, V> extends LocalAsyncCache.AbstractCacheView<K, V> implements LoadingCache<K, V> {
        private static final long serialVersionUID = 1;
        final LocalAsyncLoadingCache<K, V> asyncCache;

        LoadingCacheView(LocalAsyncLoadingCache<K, V> asyncCache) {
            this.asyncCache = (LocalAsyncLoadingCache) Objects.requireNonNull(asyncCache);
        }

        @Override // com.github.benmanes.caffeine.cache.LocalAsyncCache.AbstractCacheView
        public LocalAsyncLoadingCache<K, V> asyncCache() {
            return this.asyncCache;
        }

        @Override // com.github.benmanes.caffeine.cache.LoadingCache
        public V get(K key) {
            return (V) resolve(this.asyncCache.get(key));
        }

        @Override // com.github.benmanes.caffeine.cache.LoadingCache
        public Map<K, V> getAll(Iterable<? extends K> keys) {
            return (Map) resolve(this.asyncCache.getAll(keys));
        }

        @Override // com.github.benmanes.caffeine.cache.LoadingCache
        public void refresh(K key) {
            Objects.requireNonNull(key);
            long[] writeTime = new long[1];
            CompletableFuture<V> oldValueFuture = this.asyncCache.cache().getIfPresentQuietly(key, writeTime);
            if (oldValueFuture == null || (oldValueFuture.isDone() && oldValueFuture.isCompletedExceptionally())) {
                LocalAsyncLoadingCache<K, V> localAsyncLoadingCache = this.asyncCache;
                AsyncCacheLoader<K, V> asyncCacheLoader = this.asyncCache.loader;
                Objects.requireNonNull(asyncCacheLoader);
                localAsyncLoadingCache.get(key, this::asyncLoad, false);
            } else if (!oldValueFuture.isDone()) {
            } else {
                oldValueFuture.thenAccept(oldValue -> {
                    CompletableFuture<V> completableFuture;
                    long now = this.asyncCache.cache().statsTicker().read();
                    if (writeTime == null) {
                        completableFuture = this.asyncCache.loader.asyncLoad(key, this.asyncCache.cache().executor());
                    } else {
                        completableFuture = this.asyncCache.loader.asyncReload(key, writeTime, this.asyncCache.cache().executor());
                    }
                    CompletableFuture<V> refreshFuture = completableFuture;
                    refreshFuture.whenComplete(newValue, error -> {
                        long loadTime = this.asyncCache.cache().statsTicker().read() - now;
                        if (error != null) {
                            this.asyncCache.cache().statsCounter().recordLoadFailure(loadTime);
                            LocalAsyncLoadingCache.logger.log(Level.WARNING, "Exception thrown during refresh", error);
                            return;
                        }
                        boolean[] discard = new boolean[1];
                        this.asyncCache.cache().compute(now, k, currentValue -> {
                            if (currentValue == null) {
                                if (newValue != null) {
                                    return oldValueFuture;
                                }
                                return null;
                            }
                            if (currentValue == key) {
                                long expectedWriteTime = refreshFuture[0];
                                if (this.asyncCache.cache().hasWriteTime()) {
                                    this.asyncCache.cache().getIfPresentQuietly(key, refreshFuture);
                                }
                                if (refreshFuture[0] == expectedWriteTime) {
                                    if (newValue != null) {
                                        return oldValueFuture;
                                    }
                                    return null;
                                }
                            }
                            now[0] = true;
                            return currentValue;
                        }, false, false, true);
                        if (discard[0] && this.asyncCache.cache().hasRemovalListener()) {
                            this.asyncCache.cache().notifyRemoval(now, key, RemovalCause.REPLACED);
                        }
                        if (oldValueFuture == null) {
                            this.asyncCache.cache().statsCounter().recordLoadFailure(loadTime);
                        } else {
                            this.asyncCache.cache().statsCounter().recordLoadSuccess(loadTime);
                        }
                    });
                });
            }
        }
    }
}
