package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.AccessOrderDeque;
import java.lang.ref.ReferenceQueue;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/FWWRMS.class */
final class FWWRMS<K, V> extends FWWR<K, V> {
    int queueType;
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

    FWWRMS() {
    }

    FWWRMS(K key, ReferenceQueue<K> keyReferenceQueue, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        super(key, keyReferenceQueue, value, valueReferenceQueue, weight, now);
    }

    FWWRMS(Object keyReference, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
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

    @Override // com.github.benmanes.caffeine.cache.FWWR, com.github.benmanes.caffeine.cache.FWW, com.github.benmanes.caffeine.cache.C0009FW, com.github.benmanes.caffeine.cache.NodeFactory
    public Node<K, V> newNode(K key, ReferenceQueue<K> keyReferenceQueue, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        return new FWWRMS(key, keyReferenceQueue, value, valueReferenceQueue, weight, now);
    }

    @Override // com.github.benmanes.caffeine.cache.FWWR, com.github.benmanes.caffeine.cache.FWW, com.github.benmanes.caffeine.cache.C0009FW, com.github.benmanes.caffeine.cache.NodeFactory
    public Node<K, V> newNode(Object keyReference, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        return new FWWRMS(keyReference, value, valueReferenceQueue, weight, now);
    }
}
