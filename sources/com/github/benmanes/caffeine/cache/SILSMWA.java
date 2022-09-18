package com.github.benmanes.caffeine.cache;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/SILSMWA.class */
public class SILSMWA<K, V> extends SILSMW<K, V> {
    final Ticker ticker;
    final Expiry<K, V> expiry;
    final TimerWheel<K, V> timerWheel;
    volatile long expiresAfterAccessNanos;
    final Pacer pacer;

    public SILSMWA(Caffeine<K, V> builder, CacheLoader<? super K, V> cacheLoader, boolean async) {
        super(builder, cacheLoader, async);
        Pacer pacer;
        this.ticker = builder.getTicker();
        this.expiry = builder.getExpiry(this.isAsync);
        this.timerWheel = builder.expiresVariable() ? new TimerWheel<>(this) : null;
        this.expiresAfterAccessNanos = builder.getExpiresAfterAccessNanos();
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
    protected final boolean expiresVariable() {
        return this.timerWheel != null;
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache
    protected final Expiry<K, V> expiry() {
        return this.expiry;
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache
    protected final TimerWheel<K, V> timerWheel() {
        return this.timerWheel;
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache
    protected final boolean expiresAfterAccess() {
        return this.timerWheel == null;
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache
    protected final long expiresAfterAccessNanos() {
        return this.expiresAfterAccessNanos;
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache
    protected final void setExpiresAfterAccessNanos(long expiresAfterAccessNanos) {
        this.expiresAfterAccessNanos = expiresAfterAccessNanos;
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache
    public final Pacer pacer() {
        return this.pacer;
    }
}
