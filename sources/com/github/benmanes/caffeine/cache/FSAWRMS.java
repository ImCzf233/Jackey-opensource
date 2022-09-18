package com.github.benmanes.caffeine.cache;

import java.lang.ref.ReferenceQueue;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/FSAWRMS.class */
final class FSAWRMS<K, V> extends FSAWR<K, V> {
    int queueType;

    FSAWRMS() {
    }

    FSAWRMS(K key, ReferenceQueue<K> keyReferenceQueue, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        super(key, keyReferenceQueue, value, valueReferenceQueue, weight, now);
    }

    FSAWRMS(Object keyReference, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        super(keyReference, value, valueReferenceQueue, weight, now);
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public int getQueueType() {
        return this.queueType;
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public void setQueueType(int queueType) {
        this.queueType = queueType;
    }

    @Override // com.github.benmanes.caffeine.cache.FSAWR, com.github.benmanes.caffeine.cache.FSAW, com.github.benmanes.caffeine.cache.FSA, com.github.benmanes.caffeine.cache.C0008FS, com.github.benmanes.caffeine.cache.NodeFactory
    public Node<K, V> newNode(K key, ReferenceQueue<K> keyReferenceQueue, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        return new FSAWRMS(key, keyReferenceQueue, value, valueReferenceQueue, weight, now);
    }

    @Override // com.github.benmanes.caffeine.cache.FSAWR, com.github.benmanes.caffeine.cache.FSAW, com.github.benmanes.caffeine.cache.FSA, com.github.benmanes.caffeine.cache.C0008FS, com.github.benmanes.caffeine.cache.NodeFactory
    public Node<K, V> newNode(Object keyReference, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        return new FSAWRMS(keyReference, value, valueReferenceQueue, weight, now);
    }
}
