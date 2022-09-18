package com.github.benmanes.caffeine.cache;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/SISA.class */
public class SISA<K, V> extends SIS<K, V> {
    final Ticker ticker;
    final AccessOrderDeque<Node<K, V>> accessOrderWindowDeque;
    final Expiry<K, V> expiry;
    final TimerWheel<K, V> timerWheel;
    volatile long expiresAfterAccessNanos;
    final MpscGrowableArrayQueue<Runnable> writeBuffer;
    final Pacer pacer;

    public SISA(Caffeine<K, V> builder, CacheLoader<? super K, V> cacheLoader, boolean async) {
        super(builder, cacheLoader, async);
        AccessOrderDeque<Node<K, V>> accessOrderDeque;
        Pacer pacer;
        this.ticker = builder.getTicker();
        if (builder.evicts() || builder.expiresAfterAccess()) {
            accessOrderDeque = new AccessOrderDeque<>();
        } else {
            accessOrderDeque = null;
        }
        this.accessOrderWindowDeque = accessOrderDeque;
        this.expiry = builder.getExpiry(this.isAsync);
        this.timerWheel = builder.expiresVariable() ? new TimerWheel<>(this) : null;
        this.expiresAfterAccessNanos = builder.getExpiresAfterAccessNanos();
        this.writeBuffer = new MpscGrowableArrayQueue<>(4, WRITE_BUFFER_MAX);
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
    protected final AccessOrderDeque<Node<K, V>> accessOrderWindowDeque() {
        return this.accessOrderWindowDeque;
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
    protected final MpscGrowableArrayQueue<Runnable> writeBuffer() {
        return this.writeBuffer;
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache
    protected final boolean buffersWrites() {
        return true;
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache
    public final Pacer pacer() {
        return this.pacer;
    }
}
