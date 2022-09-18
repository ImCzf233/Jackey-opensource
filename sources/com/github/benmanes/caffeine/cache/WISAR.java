package com.github.benmanes.caffeine.cache;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/WISAR.class */
final class WISAR<K, V> extends WISA<K, V> {
    volatile long refreshAfterWriteNanos;

    WISAR(Caffeine<K, V> builder, CacheLoader<? super K, V> cacheLoader, boolean async) {
        super(builder, cacheLoader, async);
        this.refreshAfterWriteNanos = builder.getRefreshAfterWriteNanos();
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache
    protected boolean refreshAfterWrite() {
        return true;
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache
    protected long refreshAfterWriteNanos() {
        return this.refreshAfterWriteNanos;
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache
    protected void setRefreshAfterWriteNanos(long refreshAfterWriteNanos) {
        this.refreshAfterWriteNanos = refreshAfterWriteNanos;
    }
}
