package com.github.benmanes.caffeine.cache;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/SIL.class */
class SIL<K, V> extends C0023SI<K, V> {
    final RemovalListener<K, V> removalListener;

    public SIL(Caffeine<K, V> builder, CacheLoader<? super K, V> cacheLoader, boolean async) {
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
