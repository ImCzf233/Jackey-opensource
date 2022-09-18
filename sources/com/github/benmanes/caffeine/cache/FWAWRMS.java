package com.github.benmanes.caffeine.cache;

import java.lang.ref.ReferenceQueue;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/FWAWRMS.class */
final class FWAWRMS<K, V> extends FWAWR<K, V> {
    int queueType;

    FWAWRMS() {
    }

    FWAWRMS(K key, ReferenceQueue<K> keyReferenceQueue, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        super(key, keyReferenceQueue, value, valueReferenceQueue, weight, now);
    }

    FWAWRMS(Object keyReference, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
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

    @Override // com.github.benmanes.caffeine.cache.FWAWR, com.github.benmanes.caffeine.cache.FWAW, com.github.benmanes.caffeine.cache.FWA, com.github.benmanes.caffeine.cache.C0009FW, com.github.benmanes.caffeine.cache.NodeFactory
    public Node<K, V> newNode(K key, ReferenceQueue<K> keyReferenceQueue, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        return new FWAWRMS(key, keyReferenceQueue, value, valueReferenceQueue, weight, now);
    }

    @Override // com.github.benmanes.caffeine.cache.FWAWR, com.github.benmanes.caffeine.cache.FWAW, com.github.benmanes.caffeine.cache.FWA, com.github.benmanes.caffeine.cache.C0009FW, com.github.benmanes.caffeine.cache.NodeFactory
    public Node<K, V> newNode(Object keyReference, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        return new FWAWRMS(keyReference, value, valueReferenceQueue, weight, now);
    }
}
