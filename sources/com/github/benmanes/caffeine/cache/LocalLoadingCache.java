package com.github.benmanes.caffeine.cache;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/LocalLoadingCache.class */
public interface LocalLoadingCache<K, V> extends LocalManualCache<K, V>, LoadingCache<K, V> {
    public static final Logger logger = Logger.getLogger(LocalLoadingCache.class.getName());

    CacheLoader<? super K, V> cacheLoader();

    Function<K, V> mappingFunction();

    Function<Iterable<? extends K>, Map<K, V>> bulkMappingFunction();

    @Override // com.github.benmanes.caffeine.cache.LoadingCache
    default V get(K key) {
        return cache().computeIfAbsent(key, mappingFunction());
    }

    @Override // com.github.benmanes.caffeine.cache.LoadingCache
    default Map<K, V> getAll(Iterable<? extends K> keys) {
        Function<Iterable<? extends K>, Map<K, V>> mappingFunction = bulkMappingFunction();
        if (mappingFunction == null) {
            return loadSequentially(keys);
        }
        return getAll(keys, mappingFunction);
    }

    /* JADX WARN: Multi-variable type inference failed */
    default Map<K, V> loadSequentially(Iterable<? extends K> keys) {
        Set<K> uniqueKeys = new LinkedHashSet<>();
        for (K key : keys) {
            uniqueKeys.add(key);
        }
        int count = 0;
        Map<K, V> result = new LinkedHashMap<>(uniqueKeys.size());
        try {
            for (K key2 : uniqueKeys) {
                count++;
                V value = get(key2);
                if (value != null) {
                    result.put(key2, value);
                }
            }
            return Collections.unmodifiableMap(result);
        } catch (Throwable t) {
            cache().statsCounter().recordMisses(uniqueKeys.size() - count);
            throw t;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.github.benmanes.caffeine.cache.LoadingCache
    default void refresh(K key) {
        CompletableFuture<V> completableFuture;
        Objects.requireNonNull(key);
        long[] writeTime = new long[1];
        long startTime = cache().statsTicker().read();
        V oldValue = cache().getIfPresentQuietly(key, writeTime);
        if (oldValue == null) {
            completableFuture = cacheLoader().asyncLoad(key, cache().executor());
        } else {
            completableFuture = cacheLoader().asyncReload(key, oldValue, cache().executor());
        }
        CompletableFuture<V> refreshFuture = completableFuture;
        refreshFuture.whenComplete(newValue, error -> {
            long loadTime = cache().statsTicker().read() - startTime;
            if (error != null) {
                logger.log(Level.WARNING, "Exception thrown during refresh", error);
                cache().statsCounter().recordLoadFailure(loadTime);
                return;
            }
            boolean[] discard = new boolean[1];
            cache().compute(startTime, k, currentValue -> {
                if (currentValue == null) {
                    return newValue;
                }
                if (currentValue == writeTime) {
                    long expectedWriteTime = key[0];
                    if (cache().hasWriteTime()) {
                        cache().getIfPresentQuietly(oldValue, key);
                    }
                    if (key[0] == expectedWriteTime) {
                        return newValue;
                    }
                }
                startTime[0] = true;
                return currentValue;
            }, false, false, true);
            if (discard[0] && cache().hasRemovalListener()) {
                cache().notifyRemoval(startTime, writeTime, RemovalCause.REPLACED);
            }
            if (writeTime == 0) {
                cache().statsCounter().recordLoadFailure(loadTime);
            } else {
                cache().statsCounter().recordLoadSuccess(loadTime);
            }
        });
    }

    static <K, V> Function<K, V> newMappingFunction(CacheLoader<? super K, V> cacheLoader) {
        return key -> {
            try {
                return cacheLoader.load(key);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new CompletionException(e);
            } catch (RuntimeException e2) {
                throw e2;
            } catch (Exception e3) {
                throw new CompletionException(e3);
            }
        };
    }

    static <K, V> Function<Iterable<? extends K>, Map<K, V>> newBulkMappingFunction(CacheLoader<? super K, V> cacheLoader) {
        if (!hasLoadAll(cacheLoader)) {
            return null;
        }
        return keysToLoad -> {
            try {
                Map<K, V> loaded = cacheLoader.loadAll(keysToLoad);
                return loaded;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new CompletionException(e);
            } catch (RuntimeException e2) {
                throw e2;
            } catch (Exception e3) {
                throw new CompletionException(e3);
            }
        };
    }

    static boolean hasLoadAll(CacheLoader<?, ?> loader) {
        try {
            Method classLoadAll = loader.getClass().getMethod("loadAll", Iterable.class);
            Method defaultLoadAll = CacheLoader.class.getMethod("loadAll", Iterable.class);
            return !classLoadAll.equals(defaultLoadAll);
        } catch (NoSuchMethodException | SecurityException e) {
            logger.log(Level.WARNING, "Cannot determine if CacheLoader can bulk load", (Throwable) e);
            return false;
        }
    }
}
