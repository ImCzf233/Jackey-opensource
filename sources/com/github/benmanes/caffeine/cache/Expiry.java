package com.github.benmanes.caffeine.cache;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/Expiry.class */
public interface Expiry<K, V> {
    long expireAfterCreate(K k, V v, long j);

    long expireAfterUpdate(K k, V v, long j, long j2);

    long expireAfterRead(K k, V v, long j, long j2);
}
