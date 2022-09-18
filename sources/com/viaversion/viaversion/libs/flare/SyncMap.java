package com.viaversion.viaversion.libs.flare;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntFunction;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/flare/SyncMap.class */
public interface SyncMap<K, V> extends ConcurrentMap<K, V> {

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/flare/SyncMap$ExpungingEntry.class */
    public interface ExpungingEntry<V> {
        boolean exists();

        V get();

        V getOr(final V other);

        InsertionResult<V> setIfAbsent(final V value);

        <K> InsertionResult<V> computeIfAbsent(final K key, final Function<? super K, ? extends V> function);

        <K> InsertionResult<V> computeIfPresent(final K key, final BiFunction<? super K, ? super V, ? extends V> remappingFunction);

        <K> InsertionResult<V> compute(final K key, final BiFunction<? super K, ? super V, ? extends V> remappingFunction);

        void set(final V value);

        boolean replace(final Object compare, final V value);

        V clear();

        boolean trySet(final V value);

        V tryReplace(final V value);

        boolean tryExpunge();

        boolean tryUnexpungeAndSet(final V value);

        <K> boolean tryUnexpungeAndCompute(final K key, final Function<? super K, ? extends V> function);

        <K> boolean tryUnexpungeAndCompute(final K key, final BiFunction<? super K, ? super V, ? extends V> remappingFunction);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/flare/SyncMap$InsertionResult.class */
    public interface InsertionResult<V> {
        byte operation();

        V previous();

        V current();
    }

    @Override // java.util.Map
    Set<Map.Entry<K, V>> entrySet();

    @Override // java.util.Map
    int size();

    @Override // java.util.Map
    void clear();

    static <K, V> SyncMap<K, V> hashmap() {
        return m148of(HashMap::new, 16);
    }

    static <K, V> SyncMap<K, V> hashmap(final int initialCapacity) {
        return m148of(HashMap::new, initialCapacity);
    }

    static <K> Set<K> hashset() {
        return setOf(HashMap::new, 16);
    }

    static <K> Set<K> hashset(final int initialCapacity) {
        return setOf(HashMap::new, initialCapacity);
    }

    /* renamed from: of */
    static <K, V> SyncMap<K, V> m148of(final IntFunction<Map<K, ExpungingEntry<V>>> function, final int initialCapacity) {
        return new SyncMapImpl(function, initialCapacity);
    }

    static <K> Set<K> setOf(final IntFunction<Map<K, ExpungingEntry<Boolean>>> function, final int initialCapacity) {
        return Collections.newSetFromMap(new SyncMapImpl(function, initialCapacity));
    }
}
