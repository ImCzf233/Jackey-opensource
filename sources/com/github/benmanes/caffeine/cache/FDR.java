package com.github.benmanes.caffeine.cache;

import java.lang.ref.ReferenceQueue;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/FDR.class */
public class FDR<K, V> extends C0007FD<K, V> {
    protected static final long WRITE_TIME_OFFSET = UnsafeAccess.objectFieldOffset(FDR.class, LocalCacheFactory.WRITE_TIME);
    volatile long writeTime;

    public FDR() {
    }

    public FDR(K key, ReferenceQueue<K> keyReferenceQueue, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        super(key, keyReferenceQueue, value, valueReferenceQueue, weight, now);
        UnsafeAccess.UNSAFE.putLong(this, WRITE_TIME_OFFSET, now);
    }

    public FDR(Object keyReference, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        super(keyReference, value, valueReferenceQueue, weight, now);
        UnsafeAccess.UNSAFE.putLong(this, WRITE_TIME_OFFSET, now);
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public final long getWriteTime() {
        return UnsafeAccess.UNSAFE.getLong(this, WRITE_TIME_OFFSET);
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public final void setWriteTime(long writeTime) {
        UnsafeAccess.UNSAFE.putLong(this, WRITE_TIME_OFFSET, writeTime);
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public final boolean casWriteTime(long expect, long update) {
        return this.writeTime == expect && UnsafeAccess.UNSAFE.compareAndSwapLong(this, WRITE_TIME_OFFSET, expect, update);
    }

    @Override // com.github.benmanes.caffeine.cache.C0007FD, com.github.benmanes.caffeine.cache.NodeFactory
    public Node<K, V> newNode(K key, ReferenceQueue<K> keyReferenceQueue, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        return new FDR(key, keyReferenceQueue, value, valueReferenceQueue, weight, now);
    }

    @Override // com.github.benmanes.caffeine.cache.C0007FD, com.github.benmanes.caffeine.cache.NodeFactory
    public Node<K, V> newNode(Object keyReference, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        return new FDR(keyReference, value, valueReferenceQueue, weight, now);
    }
}
