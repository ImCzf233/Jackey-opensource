package com.github.benmanes.caffeine.cache;

import java.lang.ref.ReferenceQueue;
import java.util.Objects;

/* renamed from: com.github.benmanes.caffeine.cache.PS */
/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/PS.class */
public class C0016PS<K, V> extends Node<K, V> implements NodeFactory<K, V> {
    protected static final long KEY_OFFSET = UnsafeAccess.objectFieldOffset(C0016PS.class, LocalCacheFactory.KEY);
    protected static final long VALUE_OFFSET = UnsafeAccess.objectFieldOffset(C0016PS.class, "value");
    volatile K key;
    volatile V value;

    public C0016PS() {
    }

    public C0016PS(K key, ReferenceQueue<K> keyReferenceQueue, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        this(key, value, valueReferenceQueue, weight, now);
    }

    public C0016PS(Object keyReference, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        UnsafeAccess.UNSAFE.putObject(this, KEY_OFFSET, keyReference);
        UnsafeAccess.UNSAFE.putObject(this, VALUE_OFFSET, value);
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public final K getKey() {
        return (K) UnsafeAccess.UNSAFE.getObject(this, KEY_OFFSET);
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public final Object getKeyReference() {
        return UnsafeAccess.UNSAFE.getObject(this, KEY_OFFSET);
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public final V getValue() {
        return (V) UnsafeAccess.UNSAFE.getObject(this, VALUE_OFFSET);
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public final Object getValueReference() {
        return UnsafeAccess.UNSAFE.getObject(this, VALUE_OFFSET);
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public final void setValue(V value, ReferenceQueue<V> referenceQueue) {
        UnsafeAccess.UNSAFE.putObject(this, VALUE_OFFSET, value);
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public final boolean containsValue(Object value) {
        return Objects.equals(value, getValue());
    }

    @Override // com.github.benmanes.caffeine.cache.NodeFactory
    public Node<K, V> newNode(K key, ReferenceQueue<K> keyReferenceQueue, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        return new C0016PS(key, keyReferenceQueue, value, valueReferenceQueue, weight, now);
    }

    @Override // com.github.benmanes.caffeine.cache.NodeFactory
    public Node<K, V> newNode(Object keyReference, V value, ReferenceQueue<V> valueReferenceQueue, int weight, long now) {
        return new C0016PS(keyReference, value, valueReferenceQueue, weight, now);
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public final boolean isAlive() {
        Object key = getKeyReference();
        return (key == RETIRED_STRONG_KEY || key == DEAD_STRONG_KEY) ? false : true;
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public final boolean isRetired() {
        return getKeyReference() == RETIRED_STRONG_KEY;
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public final void retire() {
        UnsafeAccess.UNSAFE.putObject(this, KEY_OFFSET, RETIRED_STRONG_KEY);
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public final boolean isDead() {
        return getKeyReference() == DEAD_STRONG_KEY;
    }

    @Override // com.github.benmanes.caffeine.cache.Node
    public final void die() {
        UnsafeAccess.UNSAFE.putObject(this, VALUE_OFFSET, (Object) null);
        UnsafeAccess.UNSAFE.putObject(this, KEY_OFFSET, DEAD_STRONG_KEY);
    }
}
