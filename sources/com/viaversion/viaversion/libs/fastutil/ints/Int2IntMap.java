package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2IntMap.class */
public interface Int2IntMap extends Int2IntFunction, Map<Integer, Integer> {
    @Override // java.util.Map
    int size();

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction
    void defaultReturnValue(int i);

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction
    int defaultReturnValue();

    ObjectSet<Entry> int2IntEntrySet();

    IntSet keySet();

    IntCollection values();

    @Override // 
    boolean containsKey(int i);

    boolean containsValue(int i);

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2IntMap$FastEntrySet.class */
    public interface FastEntrySet extends ObjectSet<Entry> {
        ObjectIterator<Entry> fastIterator();

        default void fastForEach(Consumer<? super Entry> consumer) {
            forEach(consumer);
        }
    }

    @Override // java.util.Map
    default void clear() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    @Deprecated
    /* renamed from: entrySet */
    default Set<Map.Entry<Integer, Integer>> entrySet2() {
        return int2IntEntrySet();
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction
    @Deprecated
    default Integer put(Integer key, Integer value) {
        return super.put(key, value);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction, com.viaversion.viaversion.libs.fastutil.Function
    @Deprecated
    default Integer get(Object key) {
        return super.get(key);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction, com.viaversion.viaversion.libs.fastutil.Function
    @Deprecated
    default Integer remove(Object key) {
        return super.remove(key);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction, com.viaversion.viaversion.libs.fastutil.Function
    @Deprecated
    default boolean containsKey(Object key) {
        return super.containsKey(key);
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
    default void forEach(BiConsumer<? super Integer, ? super Integer> consumer) {
        ObjectSet<Entry> entrySet = int2IntEntrySet();
        Consumer<Entry> wrappingConsumer = entry -> {
            consumer.accept(Integer.valueOf(entry.getIntKey()), Integer.valueOf(entry.getIntValue()));
        };
        if (entrySet instanceof FastEntrySet) {
            ((FastEntrySet) entrySet).fastForEach(wrappingConsumer);
        } else {
            entrySet.forEach(wrappingConsumer);
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction
    default int getOrDefault(int key, int defaultValue) {
        int v = get(key);
        return (v != defaultReturnValue() || containsKey(key)) ? v : defaultValue;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction
    @Deprecated
    default Integer getOrDefault(Object key, Integer defaultValue) {
        return (Integer) super.getOrDefault(key, (Object) defaultValue);
    }

    default int putIfAbsent(int key, int value) {
        int v = get(key);
        int drv = defaultReturnValue();
        if (v != drv || containsKey(key)) {
            return v;
        }
        put(key, value);
        return drv;
    }

    default boolean remove(int key, int value) {
        int curValue = get(key);
        if (curValue == value) {
            if (curValue == defaultReturnValue() && !containsKey(key)) {
                return false;
            }
            remove(key);
            return true;
        }
        return false;
    }

    default boolean replace(int key, int oldValue, int newValue) {
        int curValue = get(key);
        if (curValue == oldValue) {
            if (curValue == defaultReturnValue() && !containsKey(key)) {
                return false;
            }
            put(key, newValue);
            return true;
        }
        return false;
    }

    default int replace(int key, int value) {
        return containsKey(key) ? put(key, value) : defaultReturnValue();
    }

    default int computeIfAbsent(int key, java.util.function.IntUnaryOperator mappingFunction) {
        Objects.requireNonNull(mappingFunction);
        int v = get(key);
        if (v != defaultReturnValue() || containsKey(key)) {
            return v;
        }
        int newValue = mappingFunction.applyAsInt(key);
        put(key, newValue);
        return newValue;
    }

    default int computeIfAbsentNullable(int key, IntFunction<? extends Integer> mappingFunction) {
        Objects.requireNonNull(mappingFunction);
        int v = get(key);
        int drv = defaultReturnValue();
        if (v != drv || containsKey(key)) {
            return v;
        }
        Integer mappedValue = mappingFunction.apply(key);
        if (mappedValue == null) {
            return drv;
        }
        int newValue = mappedValue.intValue();
        put(key, newValue);
        return newValue;
    }

    default int computeIfAbsent(int key, Int2IntFunction mappingFunction) {
        Objects.requireNonNull(mappingFunction);
        int v = get(key);
        int drv = defaultReturnValue();
        if (v != drv || containsKey(key)) {
            return v;
        }
        if (!mappingFunction.containsKey(key)) {
            return drv;
        }
        int newValue = mappingFunction.get(key);
        put(key, newValue);
        return newValue;
    }

    @Deprecated
    default int computeIfAbsentPartial(int key, Int2IntFunction mappingFunction) {
        return computeIfAbsent(key, mappingFunction);
    }

    default int computeIfPresent(int key, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        int oldValue = get(key);
        int drv = defaultReturnValue();
        if (oldValue == drv && !containsKey(key)) {
            return drv;
        }
        Integer newValue = remappingFunction.apply(Integer.valueOf(key), Integer.valueOf(oldValue));
        if (newValue == null) {
            remove(key);
            return drv;
        }
        int newVal = newValue.intValue();
        put(key, newVal);
        return newVal;
    }

    default int compute(int key, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        int oldValue = get(key);
        int drv = defaultReturnValue();
        boolean contained = oldValue != drv || containsKey(key);
        Integer newValue = remappingFunction.apply(Integer.valueOf(key), contained ? Integer.valueOf(oldValue) : null);
        if (newValue == null) {
            if (contained) {
                remove(key);
            }
            return drv;
        }
        int newVal = newValue.intValue();
        put(key, newVal);
        return newVal;
    }

    default int merge(int key, int value, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
        int newValue;
        Objects.requireNonNull(remappingFunction);
        int oldValue = get(key);
        int drv = defaultReturnValue();
        if (oldValue != drv || containsKey(key)) {
            Integer mergedValue = remappingFunction.apply(Integer.valueOf(oldValue), Integer.valueOf(value));
            if (mergedValue == null) {
                remove(key);
                return drv;
            }
            newValue = mergedValue.intValue();
        } else {
            newValue = value;
        }
        put(key, newValue);
        return newValue;
    }

    default int mergeInt(int key, int value, java.util.function.IntBinaryOperator remappingFunction) {
        int i;
        Objects.requireNonNull(remappingFunction);
        int oldValue = get(key);
        int drv = defaultReturnValue();
        if (oldValue != drv || containsKey(key)) {
            i = remappingFunction.applyAsInt(oldValue, value);
        } else {
            i = value;
        }
        int newValue = i;
        put(key, newValue);
        return newValue;
    }

    default int mergeInt(int key, int value, IntBinaryOperator remappingFunction) {
        return mergeInt(key, value, (java.util.function.IntBinaryOperator) remappingFunction);
    }

    @Deprecated
    default Integer putIfAbsent(Integer key, Integer value) {
        return (Integer) super.putIfAbsent((Int2IntMap) key, value);
    }

    @Override // java.util.Map
    @Deprecated
    default boolean remove(Object key, Object value) {
        return super.remove(key, value);
    }

    @Deprecated
    default boolean replace(Integer key, Integer oldValue, Integer newValue) {
        return super.replace((Int2IntMap) key, oldValue, newValue);
    }

    @Deprecated
    default Integer replace(Integer key, Integer value) {
        return (Integer) super.replace((Int2IntMap) key, value);
    }

    @Deprecated
    default Integer computeIfAbsent(Integer key, Function<? super Integer, ? extends Integer> mappingFunction) {
        return (Integer) super.computeIfAbsent((Int2IntMap) key, (Function<? super Int2IntMap, ? extends Object>) mappingFunction);
    }

    @Deprecated
    default Integer computeIfPresent(Integer key, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
        return (Integer) super.computeIfPresent((Int2IntMap) key, (BiFunction<? super Int2IntMap, ? super Object, ? extends Object>) remappingFunction);
    }

    @Deprecated
    default Integer compute(Integer key, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
        return (Integer) super.compute((Int2IntMap) key, (BiFunction<? super Int2IntMap, ? super Object, ? extends Object>) remappingFunction);
    }

    @Deprecated
    default Integer merge(Integer key, Integer value, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
        return (Integer) super.merge((Int2IntMap) key, value, remappingFunction);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2IntMap$Entry.class */
    public interface Entry extends Map.Entry<Integer, Integer> {
        int getIntKey();

        int getIntValue();

        int setValue(int i);

        @Override // java.util.Map.Entry
        @Deprecated
        default Integer getKey() {
            return Integer.valueOf(getIntKey());
        }

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
