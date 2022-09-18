package com.github.benmanes.caffeine.cache;

import java.lang.ref.ReferenceQueue;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/FDAWRMW.class */
final class FDAWRMW<K, V> extends FDAWR<K, V> {
    int queueType;
    int weight;
    int policyWeight;

    FDAWRMW() {
    }

    FDAWRMW(K key, ReferenceQueue<K> keyReferenceQueue, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        super(key, keyReferenceQueue, value, valueReferenceQueue, weight, now);
        this.weight = weight;
    }

    FDAWRMW(Object keyReference, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        super(keyReference, value, valueReferenceQueue, weight, now);
        this.weight = weight;
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public int getQueueType() {
        return this.queueType;
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public void setQueueType(int queueType) {
        this.queueType = queueType;
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public int getWeight() {
        return this.weight;
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public int getPolicyWeight() {
        return this.policyWeight;
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public void setPolicyWeight(int policyWeight) {
        this.policyWeight = policyWeight;
    }

    @Override // com.github.benmanes.caffeine.cache.FDAWR, com.github.benmanes.caffeine.cache.FDAW, com.github.benmanes.caffeine.cache.FDA, com.github.benmanes.caffeine.cache.C0007FD, com.github.benmanes.caffeine.cache.NodeFactory
    public Node<K, V> newNode(K key, ReferenceQueue<K> keyReferenceQueue, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        return new FDAWRMW(key, keyReferenceQueue, value, valueReferenceQueue, weight, now);
    }

    @Override // com.github.benmanes.caffeine.cache.FDAWR, com.github.benmanes.caffeine.cache.FDAW, com.github.benmanes.caffeine.cache.FDA, com.github.benmanes.caffeine.cache.C0007FD, com.github.benmanes.caffeine.cache.NodeFactory
    public Node<K, V> newNode(Object keyReference, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        return new FDAWRMW(keyReference, value, valueReferenceQueue, weight, now);
    }
}
