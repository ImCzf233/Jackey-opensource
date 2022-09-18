package com.github.benmanes.caffeine.cache;

import java.lang.ref.ReferenceQueue;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/PWWR.class */
public class PWWR<K, V> extends PWW<K, V> {
    public PWWR() {
    }

    public PWWR(K key, ReferenceQueue<K> keyReferenceQueue, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        super(key, keyReferenceQueue, value, valueReferenceQueue, weight, now);
    }

    public PWWR(Object keyReference, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        super(keyReference, value, valueReferenceQueue, weight, now);
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public final boolean casWriteTime(long expect, long update) {
        return this.writeTime == expect && UnsafeAccess.UNSAFE.compareAndSwapLong(this, WRITE_TIME_OFFSET, expect, update);
    }

    @Override // com.github.benmanes.caffeine.cache.PWW, com.github.benmanes.caffeine.cache.C0017PW, com.github.benmanes.caffeine.cache.NodeFactory
    public Node<K, V> newNode(K key, ReferenceQueue<K> keyReferenceQueue, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        return new PWWR(key, keyReferenceQueue, value, valueReferenceQueue, weight, now);
    }

    @Override // com.github.benmanes.caffeine.cache.PWW, com.github.benmanes.caffeine.cache.C0017PW, com.github.benmanes.caffeine.cache.NodeFactory
    public Node<K, V> newNode(Object keyReference, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        return new PWWR(keyReference, value, valueReferenceQueue, weight, now);
    }
}
