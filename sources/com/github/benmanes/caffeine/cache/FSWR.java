package com.github.benmanes.caffeine.cache;

import java.lang.ref.ReferenceQueue;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/FSWR.class */
public class FSWR<K, V> extends FSW<K, V> {
    public FSWR() {
    }

    public FSWR(K key, ReferenceQueue<K> keyReferenceQueue, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        super(key, keyReferenceQueue, value, valueReferenceQueue, weight, now);
    }

    public FSWR(Object keyReference, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        super(keyReference, value, valueReferenceQueue, weight, now);
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public final boolean casWriteTime(long expect, long update) {
        return this.writeTime == expect && UnsafeAccess.UNSAFE.compareAndSwapLong(this, WRITE_TIME_OFFSET, expect, update);
    }

    @Override // com.github.benmanes.caffeine.cache.FSW, com.github.benmanes.caffeine.cache.C0008FS, com.github.benmanes.caffeine.cache.NodeFactory
    public Node<K, V> newNode(K key, ReferenceQueue<K> keyReferenceQueue, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        return new FSWR(key, keyReferenceQueue, value, valueReferenceQueue, weight, now);
    }

    @Override // com.github.benmanes.caffeine.cache.FSW, com.github.benmanes.caffeine.cache.C0008FS, com.github.benmanes.caffeine.cache.NodeFactory
    public Node<K, V> newNode(Object keyReference, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        return new FSWR(keyReference, value, valueReferenceQueue, weight, now);
    }
}
