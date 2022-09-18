package com.github.benmanes.caffeine.cache;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/SSLSMWR.class */
final class SSLSMWR<K, V> extends SSLSMW<K, V> {
    final Ticker ticker;
    volatile long refreshAfterWriteNanos;

    SSLSMWR(Caffeine<K, V> builder, CacheLoader<? super K, V> cacheLoader, boolean async) {
        super(builder, cacheLoader, async);
        this.ticker = builder.getTicker();
        this.refreshAfterWriteNanos = builder.getRefreshAfterWriteNanos();
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache, com.github.benmanes.caffeine.cache.LocalCache
    public Ticker expirationTicker() {
        return this.ticker;
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
