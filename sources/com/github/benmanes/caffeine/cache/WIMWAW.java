package com.github.benmanes.caffeine.cache;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/WIMWAW.class */
class WIMWAW<K, V> extends WIMWA<K, V> {
    final WriteOrderDeque<Node<K, V>> writeOrderDeque = new WriteOrderDeque<>();
    volatile long expiresAfterWriteNanos;

    public WIMWAW(Caffeine<K, V> builder, CacheLoader<? super K, V> cacheLoader, boolean async) {
        super(builder, cacheLoader, async);
        this.expiresAfterWriteNanos = builder.getExpiresAfterWriteNanos();
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
}
