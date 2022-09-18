package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.stats.CacheStats;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/LocalManualCache.class */
interface LocalManualCache<K, V> extends Cache<K, V> {
    LocalCache<K, V> cache();

    @Override // com.github.benmanes.caffeine.cache.Cache
    default long estimatedSize() {
        return cache().estimatedSize();
    }

    @Override // com.github.benmanes.caffeine.cache.Cache
    default void cleanUp() {
        cache().cleanUp();
    }

    @Override // com.github.benmanes.caffeine.cache.Cache
    default V getIfPresent(Object key) {
        return cache().getIfPresent(key, true);
    }

    @Override // com.github.benmanes.caffeine.cache.Cache
    default V get(K key, Function<? super K, ? extends V> mappingFunction) {
        return cache().computeIfAbsent(key, mappingFunction);
    }

    @Override // com.github.benmanes.caffeine.cache.Cache
    default Map<K, V> getAllPresent(Iterable<?> keys) {
        return cache().getAllPresent(keys);
    }

    @Override // com.github.benmanes.caffeine.cache.Cache
    default Map<K, V> getAll(Iterable<? extends K> keys, Function<Iterable<? extends K>, Map<K, V>> mappingFunction) {
        Objects.requireNonNull(mappingFunction);
        Set<K> keysToLoad = new LinkedHashSet<>();
        Map<K, V> found = cache().getAllPresent(keys);
        Map<K, V> result = new LinkedHashMap<>(found.size());
        for (K key : keys) {
            V value = found.get(key);
            if (value == null) {
                keysToLoad.add(key);
            }
            result.put(key, value);
        }
        if (keysToLoad.isEmpty()) {
            return found;
        }
        bulkLoad(keysToLoad, result, mappingFunction);
        return Collections.unmodifiableMap(result);
    }

    default void bulkLoad(Set<K> keysToLoad, Map<K, V> result, Function<Iterable<? extends K>, Map<K, V>> mappingFunction) {
        boolean success = false;
        long startTime = cache().statsTicker().read();
        try {
            try {
                Map<K, V> loaded = mappingFunction.apply(keysToLoad);
                loaded.forEach(key, value -> {
                    cache().put(key, value, false);
                });
                for (K key2 : keysToLoad) {
                    V value2 = loaded.get(key2);
                    if (value2 == null) {
                        result.remove(key2);
                    } else {
                        result.put(key2, value2);
                    }
                }
                success = !loaded.isEmpty();
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e2) {
                throw new CompletionException(e2);
            }
        } finally {
            long loadTime = cache().statsTicker().read() - startTime;
            if (success) {
                cache().statsCounter().recordLoadSuccess(loadTime);
            } else {
                cache().statsCounter().recordLoadFailure(loadTime);
            }
        }
    }

    @Override // com.github.benmanes.caffeine.cache.Cache
    default void put(K key, V value) {
        cache().put(key, value);
    }

    @Override // com.github.benmanes.caffeine.cache.Cache
    default void putAll(Map<? extends K, ? extends V> map) {
        cache().putAll(map);
    }

    @Override // com.github.benmanes.caffeine.cache.Cache
    default void invalidate(Object key) {
        cache().remove(key);
    }

    @Override // com.github.benmanes.caffeine.cache.Cache
    default void invalidateAll(Iterable<?> keys) {
        cache().invalidateAll(keys);
    }

    @Override // com.github.benmanes.caffeine.cache.Cache
    default void invalidateAll() {
        cache().clear();
    }

    @Override // com.github.benmanes.caffeine.cache.Cache
    default CacheStats stats() {
        return cache().statsCounter().snapshot();
    }

    @Override // com.github.benmanes.caffeine.cache.Cache
    default ConcurrentMap<K, V> asMap() {
        return cache();
    }
}
