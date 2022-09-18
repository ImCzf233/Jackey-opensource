package com.github.benmanes.caffeine.cache;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentMap;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/AsyncLoadingCache.class */
public interface AsyncLoadingCache<K, V> extends AsyncCache<K, V> {
    CompletableFuture<V> get(K k);

    CompletableFuture<Map<K, V>> getAll(Iterable<? extends K> iterable);

    @Override // com.github.benmanes.caffeine.cache.AsyncCache
    LoadingCache<K, V> synchronous();

    @Override // com.github.benmanes.caffeine.cache.AsyncCache
    default ConcurrentMap<K, CompletableFuture<V>> asMap() {
        throw new UnsupportedOperationException();
    }
}
