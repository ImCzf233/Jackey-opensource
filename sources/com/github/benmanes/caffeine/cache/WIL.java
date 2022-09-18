package com.github.benmanes.caffeine.cache;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/WIL.class */
public class WIL<K, V> extends C0025WI<K, V> {
    final RemovalListener<K, V> removalListener;

    public WIL(Caffeine<K, V> builder, CacheLoader<? super K, V> cacheLoader, boolean async) {
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
