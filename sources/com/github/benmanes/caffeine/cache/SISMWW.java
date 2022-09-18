package com.github.benmanes.caffeine.cache;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/SISMWW.class */
class SISMWW<K, V> extends SISMW<K, V> {
    final Ticker ticker;
    final WriteOrderDeque<Node<K, V>> writeOrderDeque = new WriteOrderDeque<>();
    volatile long expiresAfterWriteNanos;
    final Pacer pacer;

    public SISMWW(Caffeine<K, V> builder, CacheLoader<? super K, V> cacheLoader, boolean async) {
        super(builder, cacheLoader, async);
        Pacer pacer;
        this.ticker = builder.getTicker();
        this.expiresAfterWriteNanos = builder.getExpiresAfterWriteNanos();
        if (builder.getScheduler() == Scheduler.disabledScheduler()) {
            pacer = null;
        } else {
            pacer = new Pacer(builder.getScheduler());
        }
        this.pacer = pacer;
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache, com.github.benmanes.caffeine.cache.LocalCache
    public final Ticker expirationTicker() {
        return this.ticker;
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache
    protected final WriteOrderDeque<Node<K, V>> writeOrderDeque() {
        return this.writeOrderDeque;
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache
    protected final boolean expiresAfterWrite() {
        return true;
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache
    protected final long expiresAfterWriteNanos() {
        return this.expiresAfterWriteNanos;
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache
    protected final void setExpiresAfterWriteNanos(long expiresAfterWriteNanos) {
        this.expiresAfterWriteNanos = expiresAfterWriteNanos;
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache
    public final Pacer pacer() {
        return this.pacer;
    }
}
