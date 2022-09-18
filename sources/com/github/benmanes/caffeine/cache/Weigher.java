package com.github.benmanes.caffeine.cache;

@FunctionalInterface
/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/Weigher.class */
public interface Weigher<K, V> {
    int weigh(K k, V v);

    static <K, V> Weigher<K, V> singletonWeigher() {
        Weigher<K, V> self = SingletonWeigher.INSTANCE;
        return self;
    }

    static <K, V> Weigher<K, V> boundedWeigher(Weigher<K, V> delegate) {
        return new BoundedWeigher(delegate);
    }
}
