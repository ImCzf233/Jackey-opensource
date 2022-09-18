package com.github.benmanes.caffeine.cache;

/* compiled from: CacheWriter.java */
/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/DisabledWriter.class */
public enum DisabledWriter implements CacheWriter<Object, Object> {
    INSTANCE;

    @Override // com.github.benmanes.caffeine.cache.CacheWriter
    public void write(Object key, Object value) {
    }

    @Override // com.github.benmanes.caffeine.cache.CacheWriter
    public void delete(Object key, Object value, RemovalCause cause) {
    }
}
