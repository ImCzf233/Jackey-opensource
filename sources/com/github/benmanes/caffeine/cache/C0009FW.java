package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.References;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

/* renamed from: com.github.benmanes.caffeine.cache.FW */
/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/FW.class */
public class C0009FW<K, V> extends Node<K, V> implements NodeFactory<K, V> {
    protected static final long KEY_OFFSET = UnsafeAccess.objectFieldOffset(C0009FW.class, LocalCacheFactory.KEY);
    protected static final long VALUE_OFFSET = UnsafeAccess.objectFieldOffset(C0009FW.class, "value");
    volatile References.WeakKeyReference<K> key;
    volatile References.WeakValueReference<V> value;

    public C0009FW() {
    }

    public C0009FW(K key, ReferenceQueue<K> keyReferenceQueue, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        this(new References.WeakKeyReference(key, keyReferenceQueue), value, valueReferenceQueue, weight, now);
    }

    public C0009FW(Object keyReference, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        UnsafeAccess.UNSAFE.putObject(this, KEY_OFFSET, keyReference);
        UnsafeAccess.UNSAFE.putObject(this, VALUE_OFFSET, new References.WeakValueReference(keyReference, value, valueReferenceQueue));
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public final K getKey() {
        return (K) ((Reference) UnsafeAccess.UNSAFE.getObject(this, KEY_OFFSET)).get();
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public final Object getKeyReference() {
        return UnsafeAccess.UNSAFE.getObject(this, KEY_OFFSET);
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public final V getValue() {
        return (V) ((Reference) UnsafeAccess.UNSAFE.getObject(this, VALUE_OFFSET)).get();
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public final Object getValueReference() {
        return UnsafeAccess.UNSAFE.getObject(this, VALUE_OFFSET);
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public final void setValue(V value, ReferenceQueue<V> referenceQueue) {
        ((Reference) getValueReference()).clear();
        UnsafeAccess.UNSAFE.putObject(this, VALUE_OFFSET, new References.WeakValueReference(getKeyReference(), value, referenceQueue));
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public final boolean containsValue(Object value) {
        return getValue() == value;
    }

    @Override // com.github.benmanes.caffeine.cache.NodeFactory
    public Node<K, V> newNode(K key, ReferenceQueue<K> keyReferenceQueue, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        return new C0009FW(key, keyReferenceQueue, value, valueReferenceQueue, weight, now);
    }

    @Override // com.github.benmanes.caffeine.cache.NodeFactory
    public Node<K, V> newNode(Object keyReference, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        return new C0009FW(keyReference, value, valueReferenceQueue, weight, now);
    }

    @Override // com.github.benmanes.caffeine.cache.NodeFactory
    public Object newLookupKey(Object key) {
        return new References.LookupKeyReference(key);
    }

    @Override // com.github.benmanes.caffeine.cache.NodeFactory
    public Object newReferenceKey(K key, ReferenceQueue<K> referenceQueue) {
        return new References.WeakKeyReference(key, referenceQueue);
    }

    @Override // com.github.benmanes.caffeine.cache.NodeFactory
    public boolean weakValues() {
        return true;
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public final boolean isAlive() {
        Object key = getKeyReference();
        return (key == RETIRED_WEAK_KEY || key == DEAD_WEAK_KEY) ? false : true;
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public final boolean isRetired() {
        return getKeyReference() == RETIRED_WEAK_KEY;
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public final void retire() {
        ((Reference) getKeyReference()).clear();
        ((Reference) getValueReference()).clear();
        UnsafeAccess.UNSAFE.putObject(this, KEY_OFFSET, RETIRED_WEAK_KEY);
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public final boolean isDead() {
        return getKeyReference() == DEAD_WEAK_KEY;
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public final void die() {
        ((Reference) getKeyReference()).clear();
        ((Reference) getValueReference()).clear();
        UnsafeAccess.UNSAFE.putObject(this, KEY_OFFSET, DEAD_WEAK_KEY);
    }
}
