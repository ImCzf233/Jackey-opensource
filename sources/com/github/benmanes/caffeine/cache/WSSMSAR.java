package com.github.benmanes.caffeine.cache;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/WSSMSAR.class */
final class WSSMSAR<K, V> extends WSSMSA<K, V> {
    volatile long refreshAfterWriteNanos;

    WSSMSAR(Caffeine<K, V> builder, CacheLoader<? super K, V> cacheLoader, boolean async) {
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
