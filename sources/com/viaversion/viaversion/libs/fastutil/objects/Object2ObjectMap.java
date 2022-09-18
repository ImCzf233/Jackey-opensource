package com.viaversion.viaversion.libs.fastutil.objects;

import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2ObjectMap.class */
public interface Object2ObjectMap<K, V> extends Object2ObjectFunction<K, V>, Map<K, V> {

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2ObjectMap$Entry.class */
    public interface Entry<K, V> extends Map.Entry<K, V> {
    }

    @Override // com.viaversion.viaversion.libs.fastutil.Function, com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
    int size();

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectFunction
    void defaultReturnValue(V v);

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectFunction
    V defaultReturnValue();

    ObjectSet<Entry<K, V>> object2ObjectEntrySet();

    ObjectSet<K> keySet();

    ObjectCollection<V> values();

    @Override // com.viaversion.viaversion.libs.fastutil.Function
    boolean containsKey(Object obj);

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2ObjectMap$FastEntrySet.class */
    public interface FastEntrySet<K, V> extends ObjectSet<Entry<K, V>> {
        ObjectIterator<Entry<K, V>> fastIterator();

        default void fastForEach(Consumer<? super Entry<K, V>> consumer) {
            forEach(consumer);
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.Function, com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
    default void clear() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    default ObjectSet<Map.Entry<K, V>> entrySet() {
        return object2ObjectEntrySet();
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectFunction, com.viaversion.viaversion.libs.fastutil.Function
    default V put(K key, V value) {
        return (V) super.put(key, value);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectFunction, com.viaversion.viaversion.libs.fastutil.Function
    default V remove(Object key) {
        return (V) super.remove(key);
    }

    @Override // java.util.Map
    default void forEach(BiConsumer<? super K, ? super V> consumer) {
        ObjectSet<Entry<K, V>> entrySet = object2ObjectEntrySet();
        Consumer<Entry<K, V>> wrappingConsumer = entry -> {
            consumer.accept(entry.getKey(), entry.getValue());
        };
        if (entrySet instanceof FastEntrySet) {
            ((FastEntrySet) entrySet).fastForEach(wrappingConsumer);
        } else {
            entrySet.forEach(wrappingConsumer);
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectFunction, com.viaversion.viaversion.libs.fastutil.Function
    default V getOrDefault(Object key, V defaultValue) {
        V v = get(key);
        return (v != defaultReturnValue() || containsKey(key)) ? v : defaultValue;
    }

    default V putIfAbsent(K key, V value) {
        V v = get(key);
        V drv = defaultReturnValue();
        if (v != drv || containsKey(key)) {
            return v;
        }
        put(key, value);
        return drv;
    }

    default boolean remove(Object key, Object value) {
        V curValue = get(key);
        if (Objects.equals(curValue, value)) {
            if (curValue == defaultReturnValue() && !containsKey(key)) {
                return false;
            }
            remove(key);
            return true;
        }
        return false;
    }

    default boolean replace(K key, V oldValue, V newValue) {
        V curValue = get(key);
        if (Objects.equals(curValue, oldValue)) {
            if (curValue == defaultReturnValue() && !containsKey(key)) {
                return false;
            }
            put(key, newValue);
            return true;
        }
        return false;
    }

    default V replace(K key, V value) {
        return containsKey(key) ? put(key, value) : defaultReturnValue();
    }

    default V computeIfAbsent(K key, Object2ObjectFunction<? super K, ? extends V> mappingFunction) {
        Objects.requireNonNull(mappingFunction);
        V v = get(key);
        V drv = defaultReturnValue();
        if (v != drv || containsKey(key)) {
            return v;
        }
        if (!mappingFunction.containsKey(key)) {
            return drv;
        }
        V newValue = mappingFunction.get(key);
        put(key, newValue);
        return newValue;
    }

    @Deprecated
    default V computeObjectIfAbsentPartial(K key, Object2ObjectFunction<? super K, ? extends V> mappingFunction) {
        return computeIfAbsent((Object2ObjectMap<K, V>) key, (Object2ObjectFunction<? super Object2ObjectMap<K, V>, ? extends V>) mappingFunction);
    }

    default V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        Object obj = (V) get(key);
        V drv = defaultReturnValue();
        if (obj == drv && !containsKey(key)) {
            return drv;
        }
        V newValue = remappingFunction.apply(key, obj);
        if (newValue == null) {
            remove(key);
            return drv;
        }
        put(key, newValue);
        return newValue;
    }

    default V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        V oldValue = get(key);
        V drv = defaultReturnValue();
        boolean contained = oldValue != drv || containsKey(key);
        V newValue = remappingFunction.apply(key, contained ? (Object) oldValue : (Object) null);
        if (newValue == null) {
            if (contained) {
                remove(key);
            }
            return drv;
        }
        put(key, newValue);
        return newValue;
    }

    default V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        V newValue;
        Objects.requireNonNull(remappingFunction);
        Objects.requireNonNull(value);
        V oldValue = get(key);
        V drv = defaultReturnValue();
        if (oldValue != drv || containsKey(key)) {
            V mergedValue = remappingFunction.apply(oldValue, value);
            if (mergedValue == null) {
                remove(key);
                return drv;
            }
            newValue = mergedValue;
        } else {
            newValue = value;
        }
        put(key, newValue);
        return newValue;
    }
}
