package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.WriteOrderDeque;
import java.lang.ref.ReferenceQueue;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/FDAW.class */
class FDAW<K, V> extends FDA<K, V> {
    protected static final long WRITE_TIME_OFFSET = UnsafeAccess.objectFieldOffset(FDAW.class, LocalCacheFactory.WRITE_TIME);
    volatile long writeTime;
    Node<K, V> previousInWriteOrder;
    Node<K, V> nextInWriteOrder;

    @Override // com.github.benmanes.caffeine.cache.Node, com.github.benmanes.caffeine.cache.WriteOrderDeque.WriteOrder
    public /* bridge */ /* synthetic */ void setNextInWriteOrder(WriteOrderDeque.WriteOrder writeOrder) {
        setNextInWriteOrder((Node) ((Node) writeOrder));
    }

    @Override // com.github.benmanes.caffeine.cache.Node, com.github.benmanes.caffeine.cache.WriteOrderDeque.WriteOrder
    public /* bridge */ /* synthetic */ void setPreviousInWriteOrder(WriteOrderDeque.WriteOrder writeOrder) {
        setPreviousInWriteOrder((Node) ((Node) writeOrder));
    }

    public FDAW() {
    }

    public FDAW(K key, ReferenceQueue<K> keyReferenceQueue, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        super(key, keyReferenceQueue, value, valueReferenceQueue, weight, now);
        UnsafeAccess.UNSAFE.putLong(this, WRITE_TIME_OFFSET, now);
    }

    public FDAW(Object keyReference, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        super(keyReference, value, valueReferenceQueue, weight, now);
        UnsafeAccess.UNSAFE.putLong(this, WRITE_TIME_OFFSET, now);
    }

    @Override // com.github.benmanes.caffeine.cache.FDA, com.github.benmanes.caffeine.cache.Node
    public Node<K, V> getPreviousInVariableOrder() {
        return this.previousInWriteOrder;
    }

    @Override // com.github.benmanes.caffeine.cache.FDA, com.github.benmanes.caffeine.cache.Node
    public void setPreviousInVariableOrder(Node<K, V> previousInWriteOrder) {
        this.previousInWriteOrder = previousInWriteOrder;
    }

    @Override // com.github.benmanes.caffeine.cache.FDA, com.github.benmanes.caffeine.cache.Node
    public Node<K, V> getNextInVariableOrder() {
        return this.nextInWriteOrder;
    }

    @Override // com.github.benmanes.caffeine.cache.FDA, com.github.benmanes.caffeine.cache.Node
    public void setNextInVariableOrder(Node<K, V> nextInWriteOrder) {
        this.nextInWriteOrder = nextInWriteOrder;
    }

    @Override // com.github.benmanes.caffeine.cache.FDA, com.github.benmanes.caffeine.cache.Node
    public long getVariableTime() {
        return UnsafeAccess.UNSAFE.getLong(this, WRITE_TIME_OFFSET);
    }

    @Override // com.github.benmanes.caffeine.cache.FDA, com.github.benmanes.caffeine.cache.Node
    public void setVariableTime(long writeTime) {
        UnsafeAccess.UNSAFE.putLong(this, WRITE_TIME_OFFSET, writeTime);
    }

    @Override // com.github.benmanes.caffeine.cache.FDA, com.github.benmanes.caffeine.cache.Node
    public boolean casVariableTime(long expect, long update) {
        return this.writeTime == expect && UnsafeAccess.UNSAFE.compareAndSwapLong(this, WRITE_TIME_OFFSET, expect, update);
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public final long getWriteTime() {
        return UnsafeAccess.UNSAFE.getLong(this, WRITE_TIME_OFFSET);
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public final void setWriteTime(long writeTime) {
        UnsafeAccess.UNSAFE.putLong(this, WRITE_TIME_OFFSET, writeTime);
    }

    @Override // com.github.benmanes.caffeine.cache.Node, com.github.benmanes.caffeine.cache.WriteOrderDeque.WriteOrder
    public final Node<K, V> getPreviousInWriteOrder() {
        return this.previousInWriteOrder;
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public final void setPreviousInWriteOrder(Node<K, V> previousInWriteOrder) {
        this.previousInWriteOrder = previousInWriteOrder;
    }

    @Override // com.github.benmanes.caffeine.cache.Node, com.github.benmanes.caffeine.cache.WriteOrderDeque.WriteOrder
    public final Node<K, V> getNextInWriteOrder() {
        return this.nextInWriteOrder;
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public final void setNextInWriteOrder(Node<K, V> nextInWriteOrder) {
        this.nextInWriteOrder = nextInWriteOrder;
    }

    @Override // com.github.benmanes.caffeine.cache.FDA, com.github.benmanes.caffeine.cache.C0007FD, com.github.benmanes.caffeine.cache.NodeFactory
    public Node<K, V> newNode(K key, ReferenceQueue<K> keyReferenceQueue, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        return new FDAW(key, keyReferenceQueue, value, valueReferenceQueue, weight, now);
    }

    @Override // com.github.benmanes.caffeine.cache.FDA, com.github.benmanes.caffeine.cache.C0007FD, com.github.benmanes.caffeine.cache.NodeFactory
    public Node<K, V> newNode(Object keyReference, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        return new FDAW(keyReference, value, valueReferenceQueue, weight, now);
    }
}
