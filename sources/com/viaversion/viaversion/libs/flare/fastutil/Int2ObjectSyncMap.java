package com.viaversion.viaversion.libs.flare.fastutil;

import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSet;
import java.util.function.BiFunction;
import java.util.function.IntFunction;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/flare/fastutil/Int2ObjectSyncMap.class */
public interface Int2ObjectSyncMap<V> extends Int2ObjectMap<V> {

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/flare/fastutil/Int2ObjectSyncMap$ExpungingEntry.class */
    public interface ExpungingEntry<V> {
        boolean exists();

        V get();

        V getOr(final V other);

        InsertionResult<V> setIfAbsent(final V value);

        InsertionResult<V> computeIfAbsent(final int key, final IntFunction<? extends V> function);

        InsertionResult<V> computeIfAbsentPrimitive(final int key, final Int2ObjectFunction<? extends V> function);

        InsertionResult<V> computeIfPresent(final int key, final BiFunction<? super Integer, ? super V, ? extends V> remappingFunction);

        InsertionResult<V> compute(final int key, final BiFunction<? super Integer, ? super V, ? extends V> remappingFunction);

        void set(final V value);

        boolean replace(final Object compare, final V value);

        V clear();

        boolean trySet(final V value);

        V tryReplace(final V value);

        boolean tryExpunge();

        boolean tryUnexpungeAndSet(final V value);

        boolean tryUnexpungeAndCompute(final int key, final IntFunction<? extends V> function);

        boolean tryUnexpungeAndComputePrimitive(final int key, final Int2ObjectFunction<? extends V> function);

        boolean tryUnexpungeAndCompute(final int key, final BiFunction<? super Integer, ? super V, ? extends V> remappingFunction);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/flare/fastutil/Int2ObjectSyncMap$InsertionResult.class */
    public interface InsertionResult<V> {
        byte operation();

        V previous();

        V current();
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap
    ObjectSet<Int2ObjectMap.Entry<V>> int2ObjectEntrySet();

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap, com.viaversion.viaversion.libs.fastutil.Function, com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
    int size();

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap, com.viaversion.viaversion.libs.fastutil.Function, com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
    void clear();

    static <V> Int2ObjectSyncMap<V> hashmap() {
        return m147of(Int2ObjectOpenHashMap::new, 16);
    }

    static <V> Int2ObjectSyncMap<V> hashmap(final int initialCapacity) {
        return m147of(Int2ObjectOpenHashMap::new, initialCapacity);
    }

    static IntSet hashset() {
        return setOf(Int2ObjectOpenHashMap::new, 16);
    }

    static IntSet hashset(final int initialCapacity) {
        return setOf(Int2ObjectOpenHashMap::new, initialCapacity);
    }

    /* renamed from: of */
    static <V> Int2ObjectSyncMap<V> m147of(final IntFunction<Int2ObjectMap<ExpungingEntry<V>>> function, final int initialCapacity) {
        return new Int2ObjectSyncMapImpl(function, initialCapacity);
    }

    static IntSet setOf(final IntFunction<Int2ObjectMap<ExpungingEntry<Boolean>>> function, final int initialCapacity) {
        return new Int2ObjectSyncMapSet(new Int2ObjectSyncMapImpl(function, initialCapacity));
    }
}
