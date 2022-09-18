package com.github.benmanes.caffeine.cache;

import java.lang.ref.ReferenceQueue;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: com.github.benmanes.caffeine.cache.SI */
/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/SI.class */
public class C0023SI<K, V> extends BoundedLocalCache<K, V> {
    final ReferenceQueue<V> valueReferenceQueue = new ReferenceQueue<>();

    public C0023SI(Caffeine<K, V> builder, CacheLoader<? super K, V> cacheLoader, boolean async) {
        super(builder, cacheLoader, async);
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
