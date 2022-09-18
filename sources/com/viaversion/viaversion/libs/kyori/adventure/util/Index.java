package com.viaversion.viaversion.libs.kyori.adventure.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.IntFunction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/kyori/adventure/util/Index.class */
public final class Index<K, V> {
    private final Map<K, V> keyToValue;
    private final Map<V, K> valueToKey;

    private Index(final Map<K, V> keyToValue, final Map<V, K> valueToKey) {
        this.keyToValue = keyToValue;
        this.valueToKey = valueToKey;
    }

    @NotNull
    public static <K, V extends Enum<V>> Index<K, V> create(final Class<V> type, @NotNull final Function<? super V, ? extends K> keyFunction) {
        return create(type, keyFunction, (Enum[]) type.getEnumConstants());
    }

    @SafeVarargs
    @NotNull
    public static Index create(final Class type, @NotNull final Function keyFunction, @NotNull final Enum... values) {
        return create(values, length -> {
            return new EnumMap(type);
        }, keyFunction);
    }

    @SafeVarargs
    @NotNull
    public static <K, V> Index<K, V> create(@NotNull final Function<? super V, ? extends K> keyFunction, @NotNull final V... values) {
        return create(values, HashMap::new, keyFunction);
    }

    @NotNull
    public static <K, V> Index<K, V> create(@NotNull final Function<? super V, ? extends K> keyFunction, @NotNull final List<V> constants) {
        return create(constants, HashMap::new, keyFunction);
    }

    @NotNull
    private static <K, V> Index<K, V> create(final V[] values, final IntFunction<Map<V, K>> valueToKeyFactory, @NotNull final Function<? super V, ? extends K> keyFunction) {
        return create(Arrays.asList(values), valueToKeyFactory, keyFunction);
    }

    @NotNull
    private static <K, V> Index<K, V> create(final List<V> values, final IntFunction<Map<V, K>> valueToKeyFactory, @NotNull final Function<? super V, ? extends K> keyFunction) {
        int length = values.size();
        Map<K, V> keyToValue = new HashMap<>(length);
        Map<V, K> valueToKey = valueToKeyFactory.apply(length);
        for (int i = 0; i < length; i++) {
            V value = values.get(i);
            K key = keyFunction.apply(value);
            if (keyToValue.putIfAbsent(key, value) != null) {
                throw new IllegalStateException(String.format("Key %s already mapped to value %s", key, keyToValue.get(key)));
            }
            if (valueToKey.putIfAbsent(value, key) != null) {
                throw new IllegalStateException(String.format("Value %s already mapped to key %s", value, valueToKey.get(value)));
            }
        }
        return new Index<>(Collections.unmodifiableMap(keyToValue), Collections.unmodifiableMap(valueToKey));
    }

    @NotNull
    public Set<K> keys() {
        return Collections.unmodifiableSet(this.keyToValue.keySet());
    }

    @Nullable
    public K key(@NotNull final V value) {
        return this.valueToKey.get(value);
    }

    @NotNull
    public Set<V> values() {
        return Collections.unmodifiableSet(this.valueToKey.keySet());
    }

    @Nullable
    public V value(@NotNull final K key) {
        return this.keyToValue.get(key);
    }
}
