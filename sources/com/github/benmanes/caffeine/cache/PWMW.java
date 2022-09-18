package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.AccessOrderDeque;
import java.lang.ref.ReferenceQueue;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/PWMW.class */
final class PWMW<K, V> extends C0017PW<K, V> {
    int queueType;
    int weight;
    int policyWeight;
    Node<K, V> previousInAccessOrder;
    Node<K, V> nextInAccessOrder;

    @Override // com.github.benmanes.caffeine.cache.Node, com.github.benmanes.caffeine.cache.AccessOrderDeque.AccessOrder
    public /* bridge */ /* synthetic */ void setNextInAccessOrder(AccessOrderDeque.AccessOrder accessOrder) {
        setNextInAccessOrder((Node) ((Node) accessOrder));
    }

    @Override // com.github.benmanes.caffeine.cache.Node, com.github.benmanes.caffeine.cache.AccessOrderDeque.AccessOrder
    public /* bridge */ /* synthetic */ void setPreviousInAccessOrder(AccessOrderDeque.AccessOrder accessOrder) {
        setPreviousInAccessOrder((Node) ((Node) accessOrder));
    }

    PWMW() {
    }

    PWMW(K key, ReferenceQueue<K> keyReferenceQueue, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        super(key, keyReferenceQueue, value, valueReferenceQueue, weight, now);
        this.weight = weight;
    }

    PWMW(Object keyReference, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
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

    @Override // com.github.benmanes.caffeine.cache.Node, com.github.benmanes.caffeine.cache.AccessOrderDeque.AccessOrder
    public Node<K, V> getPreviousInAccessOrder() {
        return this.previousInAccessOrder;
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public void setPreviousInAccessOrder(Node<K, V> previousInAccessOrder) {
        this.previousInAccessOrder = previousInAccessOrder;
    }

    @Override // com.github.benmanes.caffeine.cache.Node, com.github.benmanes.caffeine.cache.AccessOrderDeque.AccessOrder
    public Node<K, V> getNextInAccessOrder() {
        return this.nextInAccessOrder;
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public void setNextInAccessOrder(Node<K, V> nextInAccessOrder) {
        this.nextInAccessOrder = nextInAccessOrder;
    }

    @Override // com.github.benmanes.caffeine.cache.C0017PW, com.github.benmanes.caffeine.cache.NodeFactory
    public Node<K, V> newNode(K key, ReferenceQueue<K> keyReferenceQueue, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        return new PWMW(key, keyReferenceQueue, value, valueReferenceQueue, weight, now);
    }

    @Override // com.github.benmanes.caffeine.cache.C0017PW, com.github.benmanes.caffeine.cache.NodeFactory
    public Node<K, V> newNode(Object keyReference, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        return new PWMW(keyReference, value, valueReferenceQueue, weight, now);
    }
}
