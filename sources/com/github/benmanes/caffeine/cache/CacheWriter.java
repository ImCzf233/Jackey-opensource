package com.github.benmanes.caffeine.cache;

@Deprecated
/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/CacheWriter.class */
public interface CacheWriter<K, V> {
    void write(K k, V v);

    void delete(K k, V v, RemovalCause removalCause);

    static <K, V> CacheWriter<K, V> disabledWriter() {
        CacheWriter<K, V> writer = DisabledWriter.INSTANCE;
        return writer;
    }
}
