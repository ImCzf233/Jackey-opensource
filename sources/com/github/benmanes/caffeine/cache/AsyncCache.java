package com.github.benmanes.caffeine.cache;

import com.google.errorprone.annotations.CompatibleWith;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executor;
import java.util.function.BiFunction;
import java.util.function.Function;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/AsyncCache.class */
public interface AsyncCache<K, V> {
    CompletableFuture<V> getIfPresent(@CompatibleWith("K") Object obj);

    CompletableFuture<V> get(K k, Function<? super K, ? extends V> function);

    CompletableFuture<V> get(K k, BiFunction<? super K, Executor, CompletableFuture<V>> biFunction);

    void put(K k, CompletableFuture<V> completableFuture);

    ConcurrentMap<K, CompletableFuture<V>> asMap();

    Cache<K, V> synchronous();

    default CompletableFuture<Map<K, V>> getAll(Iterable<? extends K> keys, Function<Iterable<? extends K>, Map<K, V>> mappingFunction) {
        throw new UnsupportedOperationException();
    }

    default CompletableFuture<Map<K, V>> getAll(Iterable<? extends K> keys, BiFunction<Iterable<? extends K>, Executor, CompletableFuture<Map<K, V>>> mappingFunction) {
        throw new UnsupportedOperationException();
    }
}
