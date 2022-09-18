package com.github.benmanes.caffeine.cache;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@FunctionalInterface
/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/AsyncCacheLoader.class */
public interface AsyncCacheLoader<K, V> {
    CompletableFuture<V> asyncLoad(K k, Executor executor);

    default CompletableFuture<Map<K, V>> asyncLoadAll(Iterable<? extends K> keys, Executor executor) {
        throw new UnsupportedOperationException();
    }

    default CompletableFuture<V> asyncReload(K key, V oldValue, Executor executor) {
        return asyncLoad(key, executor);
    }
}
