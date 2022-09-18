package com.github.benmanes.caffeine.cache;

import java.util.Map;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/LoadingCache.class */
public interface LoadingCache<K, V> extends Cache<K, V> {
    V get(K k);

    Map<K, V> getAll(Iterable<? extends K> iterable);

    void refresh(K k);
}
