package com.github.benmanes.caffeine.cache;

import java.lang.ref.ReferenceQueue;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/FWAWR.class */
class FWAWR<K, V> extends FWAW<K, V> {
    public FWAWR() {
    }

    public FWAWR(K key, ReferenceQueue<K> keyReferenceQueue, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        super(key, keyReferenceQueue, value, valueReferenceQueue, weight, now);
    }

    public FWAWR(Object keyReference, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        super(keyReference, value, valueReferenceQueue, weight, now);
    }

    @Override // com.github.benmanes.caffeine.cache.FWAW, com.github.benmanes.caffeine.cache.FWA, com.github.benmanes.caffeine.cache.Node
    public Node<K, V> getPreviousInVariableOrder() {
        return this.previousInWriteOrder;
    }

    @Override // com.github.benmanes.caffeine.cache.FWAW, com.github.benmanes.caffeine.cache.FWA, com.github.benmanes.caffeine.cache.Node
    public void setPreviousInVariableOrder(Node<K, V> previousInWriteOrder) {
        this.previousInWriteOrder = previousInWriteOrder;
    }

    @Override // com.github.benmanes.caffeine.cache.FWAW, com.github.benmanes.caffeine.cache.FWA, com.github.benmanes.caffeine.cache.Node
    public Node<K, V> getNextInVariableOrder() {
        return this.nextInWriteOrder;
    }

    @Override // com.github.benmanes.caffeine.cache.FWAW, com.github.benmanes.caffeine.cache.FWA, com.github.benmanes.caffeine.cache.Node
    public void setNextInVariableOrder(Node<K, V> nextInWriteOrder) {
        this.nextInWriteOrder = nextInWriteOrder;
    }

    @Override // com.github.benmanes.caffeine.cache.FWAW, com.github.benmanes.caffeine.cache.FWA, com.github.benmanes.caffeine.cache.Node
    public long getVariableTime() {
        return UnsafeAccess.UNSAFE.getLong(this, ACCESS_TIME_OFFSET);
    }

    @Override // com.github.benmanes.caffeine.cache.FWAW, com.github.benmanes.caffeine.cache.FWA, com.github.benmanes.caffeine.cache.Node
    public void setVariableTime(long accessTime) {
        UnsafeAccess.UNSAFE.putLong(this, ACCESS_TIME_OFFSET, accessTime);
    }

    @Override // com.github.benmanes.caffeine.cache.FWAW, com.github.benmanes.caffeine.cache.FWA, com.github.benmanes.caffeine.cache.Node
    public boolean casVariableTime(long expect, long update) {
        return this.accessTime == expect && UnsafeAccess.UNSAFE.compareAndSwapLong(this, ACCESS_TIME_OFFSET, expect, update);
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public final boolean casWriteTime(long expect, long update) {
        return this.writeTime == expect && UnsafeAccess.UNSAFE.compareAndSwapLong(this, WRITE_TIME_OFFSET, expect, update);
    }

    @Override // com.github.benmanes.caffeine.cache.FWAW, com.github.benmanes.caffeine.cache.FWA, com.github.benmanes.caffeine.cache.C0009FW, com.github.benmanes.caffeine.cache.NodeFactory
    public Node<K, V> newNode(K key, ReferenceQueue<K> keyReferenceQueue, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        return new FWAWR(key, keyReferenceQueue, value, valueReferenceQueue, weight, now);
    }

    @Override // com.github.benmanes.caffeine.cache.FWAW, com.github.benmanes.caffeine.cache.FWA, com.github.benmanes.caffeine.cache.C0009FW, com.github.benmanes.caffeine.cache.NodeFactory
    public Node<K, V> newNode(Object keyReference, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        return new FWAWR(keyReference, value, valueReferenceQueue, weight, now);
    }
}
