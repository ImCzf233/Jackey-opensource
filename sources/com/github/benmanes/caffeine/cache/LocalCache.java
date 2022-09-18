package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.stats.StatsCounter;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executor;
import java.util.function.BiFunction;
import java.util.function.Function;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/LocalCache.class */
interface LocalCache<K, V> extends ConcurrentMap<K, V> {
    boolean isRecordingStats();

    StatsCounter statsCounter();

    boolean hasRemovalListener();

    RemovalListener<K, V> removalListener();

    void notifyRemoval(K k, V v, RemovalCause removalCause);

    Executor executor();

    boolean hasWriteTime();

    Ticker expirationTicker();

    Ticker statsTicker();

    long estimatedSize();

    V getIfPresent(Object obj, boolean z);

    V getIfPresentQuietly(Object obj, long[] jArr);

    Map<K, V> getAllPresent(Iterable<?> iterable);

    V put(K k, V v, boolean z);

    V compute(K k, BiFunction<? super K, ? super V, ? extends V> biFunction, boolean z, boolean z2, boolean z3);

    V computeIfAbsent(K k, Function<? super K, ? extends V> function, boolean z, boolean z2);

    void cleanUp();

    @Override // java.util.concurrent.ConcurrentMap, java.util.Map
    default V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return compute(key, remappingFunction, false, true, true);
    }

    @Override // java.util.concurrent.ConcurrentMap, java.util.Map
    default V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        return computeIfAbsent(key, mappingFunction, true, true);
    }

    default void invalidateAll(Iterable<?> keys) {
        for (Object key : keys) {
            remove(key);
        }
    }

    default <T, R> Function<? super T, ? extends R> statsAware(Function<? super T, ? extends R> mappingFunction, boolean recordLoad) {
        if (!isRecordingStats()) {
            return mappingFunction;
        }
        return key -> {
            statsCounter().recordMisses(1);
            long startTime = statsTicker().read();
            try {
                Object apply = mappingFunction.apply(recordLoad);
                long loadTime = statsTicker().read() - startTime;
                if (mappingFunction) {
                    if (apply == null) {
                        statsCounter().recordLoadFailure(loadTime);
                    } else {
                        statsCounter().recordLoadSuccess(loadTime);
                    }
                }
                return apply;
            } catch (Error | RuntimeException e) {
                statsCounter().recordLoadFailure(statsTicker().read() - startTime);
                throw e;
            }
        };
    }

    default <T, U, R> BiFunction<? super T, ? super U, ? extends R> statsAware(BiFunction<? super T, ? super U, ? extends R> remappingFunction) {
        return statsAware(remappingFunction, true, true, true);
    }

    default <T, U, R> BiFunction<? super T, ? super U, ? extends R> statsAware(BiFunction<? super T, ? super U, ? extends R> remappingFunction, boolean recordMiss, boolean recordLoad, boolean recordLoadFailure) {
        if (!isRecordingStats()) {
            return remappingFunction;
        }
        return t, u -> {
            if (u == null && recordMiss) {
                statsCounter().recordMisses(1);
            }
            long startTime = statsTicker().read();
            try {
                Object apply = recordMiss.apply(recordLoad, u);
                long loadTime = statsTicker().read() - startTime;
                if (recordLoadFailure) {
                    if (apply == null) {
                        statsCounter().recordLoadFailure(loadTime);
                    } else {
                        statsCounter().recordLoadSuccess(loadTime);
                    }
                }
                return apply;
            } catch (Error | RuntimeException e) {
                if (remappingFunction) {
                    statsCounter().recordLoadFailure(statsTicker().read() - startTime);
                }
                throw e;
            }
        };
    }
}
