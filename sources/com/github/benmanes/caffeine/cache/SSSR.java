package com.github.benmanes.caffeine.cache;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/SSSR.class */
final class SSSR<K, V> extends SSS<K, V> {
    final Ticker ticker;
    volatile long refreshAfterWriteNanos;
    final MpscGrowableArrayQueue<Runnable> writeBuffer = new MpscGrowableArrayQueue<>(4, WRITE_BUFFER_MAX);

    SSSR(Caffeine<K, V> builder, CacheLoader<? super K, V> cacheLoader, boolean async) {
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

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache
    protected MpscGrowableArrayQueue<Runnable> writeBuffer() {
        return this.writeBuffer;
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache
    protected boolean buffersWrites() {
        return true;
    }
}
