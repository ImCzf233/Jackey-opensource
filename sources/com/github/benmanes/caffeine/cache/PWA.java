package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.AccessOrderDeque;
import java.lang.ref.ReferenceQueue;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/PWA.class */
public class PWA<K, V> extends C0017PW<K, V> {
    protected static final long ACCESS_TIME_OFFSET = UnsafeAccess.objectFieldOffset(PWA.class, LocalCacheFactory.ACCESS_TIME);
    volatile long accessTime;
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

    public PWA() {
    }

    public PWA(K key, ReferenceQueue<K> keyReferenceQueue, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        super(key, keyReferenceQueue, value, valueReferenceQueue, weight, now);
        UnsafeAccess.UNSAFE.putLong(this, ACCESS_TIME_OFFSET, now);
    }

    public PWA(Object keyReference, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        super(keyReference, value, valueReferenceQueue, weight, now);
        UnsafeAccess.UNSAFE.putLong(this, ACCESS_TIME_OFFSET, now);
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public Node<K, V> getPreviousInVariableOrder() {
        return this.previousInAccessOrder;
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public void setPreviousInVariableOrder(Node<K, V> previousInAccessOrder) {
        this.previousInAccessOrder = previousInAccessOrder;
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public Node<K, V> getNextInVariableOrder() {
        return this.nextInAccessOrder;
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public void setNextInVariableOrder(Node<K, V> nextInAccessOrder) {
        this.nextInAccessOrder = nextInAccessOrder;
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public long getVariableTime() {
        return UnsafeAccess.UNSAFE.getLong(this, ACCESS_TIME_OFFSET);
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public void setVariableTime(long accessTime) {
        UnsafeAccess.UNSAFE.putLong(this, ACCESS_TIME_OFFSET, accessTime);
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public boolean casVariableTime(long expect, long update) {
        return this.accessTime == expect && UnsafeAccess.UNSAFE.compareAndSwapLong(this, ACCESS_TIME_OFFSET, expect, update);
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public final long getAccessTime() {
        return UnsafeAccess.UNSAFE.getLong(this, ACCESS_TIME_OFFSET);
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public final void setAccessTime(long accessTime) {
        UnsafeAccess.UNSAFE.putLong(this, ACCESS_TIME_OFFSET, accessTime);
    }

    @Override // com.github.benmanes.caffeine.cache.Node, com.github.benmanes.caffeine.cache.AccessOrderDeque.AccessOrder
    public final Node<K, V> getPreviousInAccessOrder() {
        return this.previousInAccessOrder;
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public final void setPreviousInAccessOrder(Node<K, V> previousInAccessOrder) {
        this.previousInAccessOrder = previousInAccessOrder;
    }

    @Override // com.github.benmanes.caffeine.cache.Node, com.github.benmanes.caffeine.cache.AccessOrderDeque.AccessOrder
    public final Node<K, V> getNextInAccessOrder() {
        return this.nextInAccessOrder;
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public final void setNextInAccessOrder(Node<K, V> nextInAccessOrder) {
        this.nextInAccessOrder = nextInAccessOrder;
    }

    @Override // com.github.benmanes.caffeine.cache.C0017PW, com.github.benmanes.caffeine.cache.NodeFactory
    public Node<K, V> newNode(K key, ReferenceQueue<K> keyReferenceQueue, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        return new PWA(key, keyReferenceQueue, value, valueReferenceQueue, weight, now);
    }

    @Override // com.github.benmanes.caffeine.cache.C0017PW, com.github.benmanes.caffeine.cache.NodeFactory
    public Node<K, V> newNode(Object keyReference, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        return new PWA(keyReference, value, valueReferenceQueue, weight, now);
    }
}
