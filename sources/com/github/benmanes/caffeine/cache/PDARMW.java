package com.github.benmanes.caffeine.cache;

import java.lang.ref.ReferenceQueue;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/PDARMW.class */
final class PDARMW<K, V> extends PDAR<K, V> {
    int queueType;
    int weight;
    int policyWeight;

    PDARMW() {
    }

    PDARMW(K key, ReferenceQueue<K> keyReferenceQueue, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        super(key, keyReferenceQueue, value, valueReferenceQueue, weight, now);
        this.weight = weight;
    }

    PDARMW(Object keyReference, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
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

    @Override // com.github.benmanes.caffeine.cache.PDAR, com.github.benmanes.caffeine.cache.PDA, com.github.benmanes.caffeine.cache.C0015PD, com.github.benmanes.caffeine.cache.NodeFactory
    public Node<K, V> newNode(K key, ReferenceQueue<K> keyReferenceQueue, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        return new PDARMW(key, keyReferenceQueue, value, valueReferenceQueue, weight, now);
    }

    @Override // com.github.benmanes.caffeine.cache.PDAR, com.github.benmanes.caffeine.cache.PDA, com.github.benmanes.caffeine.cache.C0015PD, com.github.benmanes.caffeine.cache.NodeFactory
    public Node<K, V> newNode(Object keyReference, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        return new PDARMW(keyReference, value, valueReferenceQueue, weight, now);
    }
}
