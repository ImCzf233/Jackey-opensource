package com.viaversion.viaversion.libs.fastutil;

@FunctionalInterface
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/Function.class */
public interface Function<K, V> extends java.util.function.Function<K, V> {
    V get(Object obj);

    @Override // java.util.function.Function
    default V apply(K key) {
        return get(key);
    }

    default V put(K key, V value) {
        throw new UnsupportedOperationException();
    }

    default V getOrDefault(Object key, V defaultValue) {
        V value = get(key);
        return (value != null || containsKey(key)) ? value : defaultValue;
    }

    default boolean containsKey(Object key) {
        return true;
    }

    default V remove(Object key) {
        throw new UnsupportedOperationException();
    }

    default int size() {
        return -1;
    }

    default void clear() {
        throw new UnsupportedOperationException();
    }
}
