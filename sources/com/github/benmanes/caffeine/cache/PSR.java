package com.github.benmanes.caffeine.cache;

import java.lang.ref.ReferenceQueue;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/PSR.class */
class PSR<K, V> extends C0016PS<K, V> {
    protected static final long WRITE_TIME_OFFSET = UnsafeAccess.objectFieldOffset(PSR.class, LocalCacheFactory.WRITE_TIME);
    volatile long writeTime;

    public PSR() {
    }

    public PSR(K key, ReferenceQueue<K> keyReferenceQueue, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        super(key, keyReferenceQueue, value, valueReferenceQueue, weight, now);
        UnsafeAccess.UNSAFE.putLong(this, WRITE_TIME_OFFSET, now);
    }

    public PSR(Object keyReference, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
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

    @Override // com.github.benmanes.caffeine.cache.C0016PS, com.github.benmanes.caffeine.cache.NodeFactory
    public Node<K, V> newNode(K key, ReferenceQueue<K> keyReferenceQueue, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        return new PSR(key, keyReferenceQueue, value, valueReferenceQueue, weight, now);
    }

    @Override // com.github.benmanes.caffeine.cache.C0016PS, com.github.benmanes.caffeine.cache.NodeFactory
    public Node<K, V> newNode(Object keyReference, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        return new PSR(keyReference, value, valueReferenceQueue, weight, now);
    }
}
