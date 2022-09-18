package com.github.benmanes.caffeine.cache;

@FunctionalInterface
/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/RemovalListener.class */
public interface RemovalListener<K, V> {
    void onRemoval(K k, V v, RemovalCause removalCause);
}
