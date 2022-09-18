package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.ints.IntCollection;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.IntBinaryOperator;
import java.util.function.ToIntFunction;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2IntMap.class */
public interface Object2IntMap<K> extends Object2IntFunction<K>, Map<K, Integer> {
    @Override // com.viaversion.viaversion.libs.fastutil.Function, com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
    int size();

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunction
    void defaultReturnValue(int i);

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunction
    int defaultReturnValue();

    ObjectSet<Entry<K>> object2IntEntrySet();

    ObjectSet<K> keySet();

    IntCollection values();

    @Override // com.viaversion.viaversion.libs.fastutil.Function
    boolean containsKey(Object obj);

    boolean containsValue(int i);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunction, com.viaversion.viaversion.libs.fastutil.Function
    @Deprecated
    /* bridge */ /* synthetic */ default Integer put(Object obj, Integer num) {
        return put((Object2IntMap<K>) obj, num);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Map
    @Deprecated
    /* bridge */ /* synthetic */ default Integer merge(Object obj, Integer num, BiFunction<? super Integer, ? super Integer, ? extends Integer> biFunction) {
        return merge2((Object2IntMap<K>) obj, num, biFunction);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Map
    @Deprecated
    /* bridge */ /* synthetic */ default Integer replace(Object obj, Integer num) {
        return replace2((Object2IntMap<K>) obj, num);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Map
    @Deprecated
    /* bridge */ /* synthetic */ default boolean replace(Object obj, Integer num, Integer num2) {
        return replace2((Object2IntMap<K>) obj, num, num2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Map
    @Deprecated
    /* bridge */ /* synthetic */ default Integer putIfAbsent(Object obj, Integer num) {
        return putIfAbsent2((Object2IntMap<K>) obj, num);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2IntMap$FastEntrySet.class */
    public interface FastEntrySet<K> extends ObjectSet<Entry<K>> {
        ObjectIterator<Entry<K>> fastIterator();

        default void fastForEach(Consumer<? super Entry<K>> consumer) {
            forEach(consumer);
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.Function, com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
    default void clear() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    @Deprecated
    default ObjectSet<Map.Entry<K, Integer>> entrySet() {
        return object2IntEntrySet();
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunction
    @Deprecated
    default Integer put(K key, Integer value) {
        return super.put2((Object2IntMap<K>) key, value);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunction, com.viaversion.viaversion.libs.fastutil.Function
    @Deprecated
    default Integer get(Object key) {
        return super.get(key);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunction, com.viaversion.viaversion.libs.fastutil.Function
    @Deprecated
    default Integer remove(Object key) {
        return super.remove(key);
    }

    @Override // java.util.Map
    @Deprecated
    default boolean containsValue(Object value) {
        if (value == null) {
            return false;
        }
        return containsValue(((Integer) value).intValue());
    }

    @Override // java.util.Map
    default void forEach(BiConsumer<? super K, ? super Integer> consumer) {
        ObjectSet<Entry<K>> entrySet = object2IntEntrySet();
        Consumer<Entry<K>> wrappingConsumer = entry -> {
            consumer.accept(entry.getKey(), Integer.valueOf(entry.getIntValue()));
        };
        if (entrySet instanceof FastEntrySet) {
            ((FastEntrySet) entrySet).fastForEach(wrappingConsumer);
        } else {
            entrySet.forEach(wrappingConsumer);
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunction
    default int getOrDefault(Object key, int defaultValue) {
        int v = getInt(key);
        return (v != defaultReturnValue() || containsKey(key)) ? v : defaultValue;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunction
    @Deprecated
    default Integer getOrDefault(Object key, Integer defaultValue) {
        return (Integer) super.getOrDefault(key, (Object) defaultValue);
    }

    default int putIfAbsent(K key, int value) {
        int v = getInt(key);
        int drv = defaultReturnValue();
        if (v != drv || containsKey(key)) {
            return v;
        }
        put((Object2IntMap<K>) key, value);
        return drv;
    }

    default boolean remove(Object key, int value) {
        int curValue = getInt(key);
        if (curValue == value) {
            if (curValue == defaultReturnValue() && !containsKey(key)) {
                return false;
            }
            removeInt(key);
            return true;
        }
        return false;
    }

    default boolean replace(K key, int oldValue, int newValue) {
        int curValue = getInt(key);
        if (curValue == oldValue) {
            if (curValue == defaultReturnValue() && !containsKey(key)) {
                return false;
            }
            put((Object2IntMap<K>) key, newValue);
            return true;
        }
        return false;
    }

    default int replace(K key, int value) {
        return containsKey(key) ? put((Object2IntMap<K>) key, value) : defaultReturnValue();
    }

    default int computeIfAbsent(K key, ToIntFunction<? super K> mappingFunction) {
        Objects.requireNonNull(mappingFunction);
        int v = getInt(key);
        if (v != defaultReturnValue() || containsKey(key)) {
            return v;
        }
        int newValue = mappingFunction.applyAsInt(key);
        put((Object2IntMap<K>) key, newValue);
        return newValue;
    }

    @Deprecated
    default int computeIntIfAbsent(K key, ToIntFunction<? super K> mappingFunction) {
        return computeIfAbsent((Object2IntMap<K>) key, (ToIntFunction<? super Object2IntMap<K>>) mappingFunction);
    }

    default int computeIfAbsent(K key, Object2IntFunction<? super K> mappingFunction) {
        Objects.requireNonNull(mappingFunction);
        int v = getInt(key);
        int drv = defaultReturnValue();
        if (v != drv || containsKey(key)) {
            return v;
        }
        if (!mappingFunction.containsKey(key)) {
            return drv;
        }
        int newValue = mappingFunction.getInt(key);
        put((Object2IntMap<K>) key, newValue);
        return newValue;
    }

    @Deprecated
    default int computeIntIfAbsentPartial(K key, Object2IntFunction<? super K> mappingFunction) {
        return computeIfAbsent((Object2IntMap<K>) key, (Object2IntFunction<? super Object2IntMap<K>>) mappingFunction);
    }

    default int computeIntIfPresent(K key, BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        int oldValue = getInt(key);
        int drv = defaultReturnValue();
        if (oldValue == drv && !containsKey(key)) {
            return drv;
        }
        Integer newValue = remappingFunction.apply(key, Integer.valueOf(oldValue));
        if (newValue == null) {
            removeInt(key);
            return drv;
        }
        int newVal = newValue.intValue();
        put((Object2IntMap<K>) key, newVal);
        return newVal;
    }

    default int computeInt(K key, BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        int oldValue = getInt(key);
        int drv = defaultReturnValue();
        boolean contained = oldValue != drv || containsKey(key);
        Integer newValue = remappingFunction.apply(key, contained ? Integer.valueOf(oldValue) : null);
        if (newValue == null) {
            if (contained) {
                removeInt(key);
            }
            return drv;
        }
        int newVal = newValue.intValue();
        put((Object2IntMap<K>) key, newVal);
        return newVal;
    }

    default int merge(K key, int value, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
        int newValue;
        Objects.requireNonNull(remappingFunction);
        int oldValue = getInt(key);
        int drv = defaultReturnValue();
        if (oldValue != drv || containsKey(key)) {
            Integer mergedValue = remappingFunction.apply(Integer.valueOf(oldValue), Integer.valueOf(value));
            if (mergedValue == null) {
                removeInt(key);
                return drv;
            }
            newValue = mergedValue.intValue();
        } else {
            newValue = value;
        }
        put((Object2IntMap<K>) key, newValue);
        return newValue;
    }

    default int mergeInt(K key, int value, IntBinaryOperator remappingFunction) {
        int i;
        Objects.requireNonNull(remappingFunction);
        int oldValue = getInt(key);
        int drv = defaultReturnValue();
        if (oldValue != drv || containsKey(key)) {
            i = remappingFunction.applyAsInt(oldValue, value);
        } else {
            i = value;
        }
        int newValue = i;
        put((Object2IntMap<K>) key, newValue);
        return newValue;
    }

    default int mergeInt(K key, int value, com.viaversion.viaversion.libs.fastutil.ints.IntBinaryOperator remappingFunction) {
        return mergeInt((Object2IntMap<K>) key, value, (IntBinaryOperator) remappingFunction);
    }

    @Deprecated
    default int mergeInt(K key, int value, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
        return merge((Object2IntMap<K>) key, value, remappingFunction);
    }

    @Deprecated
    /* renamed from: putIfAbsent */
    default Integer putIfAbsent2(K key, Integer value) {
        return (Integer) super.putIfAbsent((Object2IntMap<K>) key, (K) value);
    }

    @Override // java.util.Map
    @Deprecated
    default boolean remove(Object key, Object value) {
        return super.remove(key, value);
    }

    @Deprecated
    /* renamed from: replace */
    default boolean replace2(K key, Integer oldValue, Integer newValue) {
        return super.replace((Object2IntMap<K>) key, oldValue, newValue);
    }

    @Deprecated
    /* renamed from: replace */
    default Integer replace2(K key, Integer value) {
        return (Integer) super.replace((Object2IntMap<K>) key, (K) value);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Deprecated
    /* renamed from: merge */
    default Integer merge2(K key, Integer value, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
        return (Integer) super.merge((Object2IntMap<K>) key, (K) value, (BiFunction<? super K, ? super K, ? extends K>) remappingFunction);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2IntMap$Entry.class */
    public interface Entry<K> extends Map.Entry<K, Integer> {
        int getIntValue();

        int setValue(int i);

        @Override // java.util.Map.Entry
        @Deprecated
        default Integer getValue() {
            return Integer.valueOf(getIntValue());
        }

        @Deprecated
        default Integer setValue(Integer value) {
            return Integer.valueOf(setValue(value.intValue()));
        }
    }
}
