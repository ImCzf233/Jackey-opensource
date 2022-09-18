package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSet;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.IntFunction;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectMap.class */
public interface Int2ObjectMap<V> extends Int2ObjectFunction<V>, Map<Integer, V> {
    @Override // com.viaversion.viaversion.libs.fastutil.Function, com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
    int size();

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction
    void defaultReturnValue(V v);

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction
    V defaultReturnValue();

    ObjectSet<Entry<V>> int2ObjectEntrySet();

    IntSet keySet();

    ObjectCollection<V> values();

    @Override // 
    boolean containsKey(int i);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction, com.viaversion.viaversion.libs.fastutil.Function
    @Deprecated
    /* bridge */ /* synthetic */ default Object put(Integer num, Object obj) {
        return put(num, (Integer) obj);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectMap$FastEntrySet.class */
    public interface FastEntrySet<V> extends ObjectSet<Entry<V>> {
        ObjectIterator<Entry<V>> fastIterator();

        default void fastForEach(Consumer<? super Entry<V>> consumer) {
            forEach(consumer);
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.Function, com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
    default void clear() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    @Deprecated
    default ObjectSet<Map.Entry<Integer, V>> entrySet() {
        return int2ObjectEntrySet();
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction
    @Deprecated
    default V put(Integer key, V value) {
        return (V) super.put2(key, (Integer) value);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction, com.viaversion.viaversion.libs.fastutil.Function
    @Deprecated
    default V get(Object key) {
        return (V) super.get(key);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction, com.viaversion.viaversion.libs.fastutil.Function
    @Deprecated
    default V remove(Object key) {
        return (V) super.remove(key);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction, com.viaversion.viaversion.libs.fastutil.Function
    @Deprecated
    default boolean containsKey(Object key) {
        return super.containsKey(key);
    }

    @Override // java.util.Map
    default void forEach(BiConsumer<? super Integer, ? super V> consumer) {
        ObjectSet<Entry<V>> entrySet = int2ObjectEntrySet();
        Consumer<Entry<V>> wrappingConsumer = entry -> {
            consumer.accept(Integer.valueOf(entry.getIntKey()), entry.getValue());
        };
        if (entrySet instanceof FastEntrySet) {
            ((FastEntrySet) entrySet).fastForEach(wrappingConsumer);
        } else {
            entrySet.forEach(wrappingConsumer);
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction
    default V getOrDefault(int key, V defaultValue) {
        V v = get(key);
        return (v != defaultReturnValue() || containsKey(key)) ? v : defaultValue;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction, com.viaversion.viaversion.libs.fastutil.Function
    @Deprecated
    default V getOrDefault(Object key, V defaultValue) {
        return (V) super.getOrDefault(key, defaultValue);
    }

    default V putIfAbsent(int key, V value) {
        V v = get(key);
        V drv = defaultReturnValue();
        if (v != drv || containsKey(key)) {
            return v;
        }
        put(key, (int) value);
        return drv;
    }

    default boolean remove(int key, Object value) {
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

    default boolean replace(int key, V oldValue, V newValue) {
        V curValue = get(key);
        if (Objects.equals(curValue, oldValue)) {
            if (curValue == defaultReturnValue() && !containsKey(key)) {
                return false;
            }
            put(key, (int) newValue);
            return true;
        }
        return false;
    }

    default V replace(int key, V value) {
        return containsKey(key) ? put(key, (int) value) : defaultReturnValue();
    }

    default V computeIfAbsent(int key, IntFunction<? extends V> mappingFunction) {
        Objects.requireNonNull(mappingFunction);
        V v = get(key);
        if (v != defaultReturnValue() || containsKey(key)) {
            return v;
        }
        V newValue = mappingFunction.apply(key);
        put(key, (int) newValue);
        return newValue;
    }

    default V computeIfAbsent(int key, Int2ObjectFunction<? extends V> mappingFunction) {
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
        put(key, (int) newValue);
        return newValue;
    }

    @Deprecated
    default V computeIfAbsentPartial(int key, Int2ObjectFunction<? extends V> mappingFunction) {
        return computeIfAbsent(key, (Int2ObjectFunction) mappingFunction);
    }

    default V computeIfPresent(int key, BiFunction<? super Integer, ? super V, ? extends V> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        Object obj = (V) get(key);
        V drv = defaultReturnValue();
        if (obj == drv && !containsKey(key)) {
            return drv;
        }
        V newValue = remappingFunction.apply(Integer.valueOf(key), obj);
        if (newValue == null) {
            remove(key);
            return drv;
        }
        put(key, (int) newValue);
        return newValue;
    }

    default V compute(int key, BiFunction<? super Integer, ? super V, ? extends V> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        V oldValue = get(key);
        V drv = defaultReturnValue();
        boolean contained = oldValue != drv || containsKey(key);
        V newValue = remappingFunction.apply(Integer.valueOf(key), contained ? (Object) oldValue : (Object) null);
        if (newValue == null) {
            if (contained) {
                remove(key);
            }
            return drv;
        }
        put(key, (int) newValue);
        return newValue;
    }

    default V merge(int key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
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
        put(key, (int) newValue);
        return newValue;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectMap$Entry.class */
    public interface Entry<V> extends Map.Entry<Integer, V> {
        int getIntKey();

        @Override // java.util.Map.Entry
        @Deprecated
        default Integer getKey() {
            return Integer.valueOf(getIntKey());
        }
    }
}
