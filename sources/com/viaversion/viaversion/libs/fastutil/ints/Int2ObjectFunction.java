package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Function;
import com.viaversion.viaversion.libs.fastutil.bytes.Byte2IntFunction;
import com.viaversion.viaversion.libs.fastutil.bytes.Byte2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.chars.Char2IntFunction;
import com.viaversion.viaversion.libs.fastutil.chars.Char2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.doubles.Double2IntFunction;
import com.viaversion.viaversion.libs.fastutil.doubles.Double2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.floats.Float2IntFunction;
import com.viaversion.viaversion.libs.fastutil.floats.Float2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.longs.Long2IntFunction;
import com.viaversion.viaversion.libs.fastutil.longs.Long2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2ByteFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2CharFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2DoubleFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2FloatFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2LongFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2ReferenceFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2ShortFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Reference2IntFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Reference2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.shorts.Short2IntFunction;
import com.viaversion.viaversion.libs.fastutil.shorts.Short2ObjectFunction;
import java.util.function.IntFunction;

@FunctionalInterface
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectFunction.class */
public interface Int2ObjectFunction<V> extends Function<Integer, V>, IntFunction<V> {
    V get(int i);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.viaversion.viaversion.libs.fastutil.Function
    @Deprecated
    /* bridge */ /* synthetic */ default Object put(Integer num, Object obj) {
        return put2(num, (Integer) obj);
    }

    @Override // java.util.function.IntFunction
    default V apply(int operand) {
        return get(operand);
    }

    default V put(int key, V value) {
        throw new UnsupportedOperationException();
    }

    default V getOrDefault(int key, V defaultValue) {
        V v = get(key);
        return (v != defaultReturnValue() || containsKey(key)) ? v : defaultValue;
    }

    default V remove(int key) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    /* renamed from: put */
    default V put2(Integer key, V value) {
        int k = key.intValue();
        boolean containsKey = containsKey(k);
        V v = put(k, (int) value);
        if (containsKey) {
            return v;
        }
        return null;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.Function
    @Deprecated
    default V get(Object key) {
        if (key == null) {
            return null;
        }
        int k = ((Integer) key).intValue();
        V v = get(k);
        if (v == defaultReturnValue() && !containsKey(k)) {
            return null;
        }
        return v;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.Function
    @Deprecated
    default V getOrDefault(Object key, V defaultValue) {
        if (key == null) {
            return defaultValue;
        }
        int k = ((Integer) key).intValue();
        V v = get(k);
        return (v != defaultReturnValue() || containsKey(k)) ? v : defaultValue;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.Function
    @Deprecated
    default V remove(Object key) {
        if (key == null) {
            return null;
        }
        int k = ((Integer) key).intValue();
        if (!containsKey(k)) {
            return null;
        }
        return remove(k);
    }

    default boolean containsKey(int key) {
        return true;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.Function
    @Deprecated
    default boolean containsKey(Object key) {
        if (key == null) {
            return false;
        }
        return containsKey(((Integer) key).intValue());
    }

    default void defaultReturnValue(V rv) {
        throw new UnsupportedOperationException();
    }

    default V defaultReturnValue() {
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<T, V> compose(java.util.function.Function<? super T, ? extends Integer> before) {
        return super.compose(before);
    }

    default Int2ByteFunction andThenByte(Object2ByteFunction<V> after) {
        return k -> {
            return after.getByte(get(after));
        };
    }

    default Byte2ObjectFunction<V> composeByte(Byte2IntFunction before) {
        return k -> {
            return get(before.get(before));
        };
    }

    default Int2ShortFunction andThenShort(Object2ShortFunction<V> after) {
        return k -> {
            return after.getShort(get(after));
        };
    }

    default Short2ObjectFunction<V> composeShort(Short2IntFunction before) {
        return k -> {
            return get(before.get(before));
        };
    }

    default Int2IntFunction andThenInt(Object2IntFunction<V> after) {
        return k -> {
            return after.getInt(get(after));
        };
    }

    default Int2ObjectFunction<V> composeInt(Int2IntFunction before) {
        return k -> {
            return get(before.get(before));
        };
    }

    default Int2LongFunction andThenLong(Object2LongFunction<V> after) {
        return k -> {
            return after.getLong(get(after));
        };
    }

    default Long2ObjectFunction<V> composeLong(Long2IntFunction before) {
        return k -> {
            return get(before.get(before));
        };
    }

    default Int2CharFunction andThenChar(Object2CharFunction<V> after) {
        return k -> {
            return after.getChar(get(after));
        };
    }

    default Char2ObjectFunction<V> composeChar(Char2IntFunction before) {
        return k -> {
            return get(before.get(before));
        };
    }

    default Int2FloatFunction andThenFloat(Object2FloatFunction<V> after) {
        return k -> {
            return after.getFloat(get(after));
        };
    }

    default Float2ObjectFunction<V> composeFloat(Float2IntFunction before) {
        return k -> {
            return get(before.get(before));
        };
    }

    default Int2DoubleFunction andThenDouble(Object2DoubleFunction<V> after) {
        return k -> {
            return after.getDouble(get(after));
        };
    }

    default Double2ObjectFunction<V> composeDouble(Double2IntFunction before) {
        return k -> {
            return get(before.get(before));
        };
    }

    default <T> Int2ObjectFunction<T> andThenObject(Object2ObjectFunction<? super V, ? extends T> after) {
        return k -> {
            return after.get(get(after));
        };
    }

    default <T> Object2ObjectFunction<T, V> composeObject(Object2IntFunction<? super T> before) {
        return k -> {
            return get(before.getInt(before));
        };
    }

    default <T> Int2ReferenceFunction<T> andThenReference(Object2ReferenceFunction<? super V, ? extends T> after) {
        return k -> {
            return after.get(get(after));
        };
    }

    default <T> Reference2ObjectFunction<T, V> composeReference(Reference2IntFunction<? super T> before) {
        return k -> {
            return get(before.getInt(before));
        };
    }
}
