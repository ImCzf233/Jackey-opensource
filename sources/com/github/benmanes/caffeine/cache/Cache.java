package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.stats.CacheStats;
import com.google.errorprone.annotations.CompatibleWith;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/Cache.class */
public interface Cache<K, V> {
    V getIfPresent(@CompatibleWith("K") Object obj);

    V get(K k, Function<? super K, ? extends V> function);

    Map<K, V> getAllPresent(Iterable<?> iterable);

    void put(K k, V v);

    void putAll(Map<? extends K, ? extends V> map);

    void invalidate(@CompatibleWith("K") Object obj);

    void invalidateAll(Iterable<?> iterable);

    void invalidateAll();

    long estimatedSize();

    CacheStats stats();

    ConcurrentMap<K, V> asMap();

    void cleanUp();

    Policy<K, V> policy();

    default Map<K, V> getAll(Iterable<? extends K> keys, Function<Iterable<? extends K>, Map<K, V>> mappingFunction) {
        throw new UnsupportedOperationException();
    }
}
