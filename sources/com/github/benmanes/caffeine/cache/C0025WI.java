package com.github.benmanes.caffeine.cache;

import java.lang.ref.ReferenceQueue;

/* renamed from: com.github.benmanes.caffeine.cache.WI */
/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/WI.class */
public class C0025WI<K, V> extends BoundedLocalCache<K, V> {
    final ReferenceQueue<K> keyReferenceQueue = new ReferenceQueue<>();
    final ReferenceQueue<V> valueReferenceQueue = new ReferenceQueue<>();

    public C0025WI(Caffeine<K, V> builder, CacheLoader<? super K, V> cacheLoader, boolean async) {
        super(builder, cacheLoader, async);
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache
    protected final ReferenceQueue<K> keyReferenceQueue() {
        return this.keyReferenceQueue;
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache
    protected final boolean collectKeys() {
        return true;
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache
    protected final ReferenceQueue<V> valueReferenceQueue() {
        return this.valueReferenceQueue;
    }

    @Override // com.github.benmanes.caffeine.cache.BoundedLocalCache
    protected final boolean collectValues() {
        return true;
    }
}
