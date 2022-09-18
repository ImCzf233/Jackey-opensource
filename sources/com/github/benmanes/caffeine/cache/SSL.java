package com.github.benmanes.caffeine.cache;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/SSL.class */
public class SSL<K, V> extends C0024SS<K, V> {
    final RemovalListener<K, V> removalListener;

    public SSL(Caffeine<K, V> builder, CacheLoader<? super K, V> cacheLoader, boolean async) {
        super(builder, cacheLoader, async);
        this.removalListener = (RemovalListener<K, V>) builder.getRemovalListener(async);
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache, com.github.benmanes.caffeine.cache.LocalCache
    public final RemovalListener<K, V> removalListener() {
        return this.removalListener;
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache, com.github.benmanes.caffeine.cache.LocalCache
    public final boolean hasRemovalListener() {
        return true;
    }
}
