package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Function;
import com.viaversion.viaversion.libs.fastutil.bytes.Byte2IntFunction;
import com.viaversion.viaversion.libs.fastutil.bytes.Byte2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.chars.Char2IntFunction;
import com.viaversion.viaversion.libs.fastutil.chars.Char2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.doubles.Double2IntFunction;
import com.viaversion.viaversion.libs.fastutil.doubles.Double2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.floats.Float2IntFunction;
import com.viaversion.viaversion.libs.fastutil.floats.Float2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ByteFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2CharFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2DoubleFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2FloatFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2LongFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ReferenceFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ShortFunction;
import com.viaversion.viaversion.libs.fastutil.longs.Long2IntFunction;
import com.viaversion.viaversion.libs.fastutil.longs.Long2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.shorts.Short2IntFunction;
import com.viaversion.viaversion.libs.fastutil.shorts.Short2ObjectFunction;
import java.util.function.ToIntFunction;

@FunctionalInterface
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2IntFunction.class */
public interface Object2IntFunction<K> extends Function<K, Integer>, ToIntFunction<K> {
    int getInt(Object obj);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.viaversion.viaversion.libs.fastutil.Function
    @Deprecated
    /* bridge */ /* synthetic */ default Integer put(Object obj, Integer num) {
        return put2((Object2IntFunction<K>) obj, num);
    }

    @Override // java.util.function.ToIntFunction
    default int applyAsInt(K operand) {
        return getInt(operand);
    }

    default int put(K key, int value) {
        throw new UnsupportedOperationException();
    }

    default int getOrDefault(Object key, int defaultValue) {
        int v = getInt(key);
        return (v != defaultReturnValue() || containsKey(key)) ? v : defaultValue;
    }

    default int removeInt(Object key) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    /* renamed from: put */
    default Integer put2(K key, Integer value) {
        boolean containsKey = containsKey(key);
        int v = put((Object2IntFunction<K>) key, value.intValue());
        if (containsKey) {
            return Integer.valueOf(v);
        }
        return null;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.Function
    @Deprecated
    default Integer get(Object key) {
        int v = getInt(key);
        if (v != defaultReturnValue() || containsKey(key)) {
            return Integer.valueOf(v);
        }
        return null;
    }

    @Deprecated
    default Integer getOrDefault(Object key, Integer defaultValue) {
        int v = getInt(key);
        return (v != defaultReturnValue() || containsKey(key)) ? Integer.valueOf(v) : defaultValue;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.Function
    @Deprecated
    default Integer remove(Object key) {
        if (containsKey(key)) {
            return Integer.valueOf(removeInt(key));
        }
        return null;
    }

    default void defaultReturnValue(int rv) {
        throw new UnsupportedOperationException();
    }

    default int defaultReturnValue() {
        return 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<K, T> andThen(java.util.function.Function<? super Integer, ? extends T> after) {
        return super.andThen(after);
    }

    default Object2ByteFunction<K> andThenByte(Int2ByteFunction after) {
        return k -> {
            return after.get(getInt(after));
        };
    }

    default Byte2IntFunction composeByte(Byte2ObjectFunction<K> before) {
        return k -> {
            return getInt(before.get(before));
        };
    }

    default Object2ShortFunction<K> andThenShort(Int2ShortFunction after) {
        return k -> {
            return after.get(getInt(after));
        };
    }

    default Short2IntFunction composeShort(Short2ObjectFunction<K> before) {
        return k -> {
            return getInt(before.get(before));
        };
    }

    default Object2IntFunction<K> andThenInt(Int2IntFunction after) {
        return k -> {
            return after.get(getInt(after));
        };
    }

    default Int2IntFunction composeInt(Int2ObjectFunction<K> before) {
        return k -> {
            return getInt(before.get(before));
        };
    }

    default Object2LongFunction<K> andThenLong(Int2LongFunction after) {
        return k -> {
            return after.get(getInt(after));
        };
    }

    default Long2IntFunction composeLong(Long2ObjectFunction<K> before) {
        return k -> {
            return getInt(before.get(before));
        };
    }

    default Object2CharFunction<K> andThenChar(Int2CharFunction after) {
        return k -> {
            return after.get(getInt(after));
        };
    }

    default Char2IntFunction composeChar(Char2ObjectFunction<K> before) {
        return k -> {
            return getInt(before.get(before));
        };
    }

    default Object2FloatFunction<K> andThenFloat(Int2FloatFunction after) {
        return k -> {
            return after.get(getInt(after));
        };
    }

    default Float2IntFunction composeFloat(Float2ObjectFunction<K> before) {
        return k -> {
            return getInt(before.get(before));
        };
    }

    default Object2DoubleFunction<K> andThenDouble(Int2DoubleFunction after) {
        return k -> {
            return after.get(getInt(after));
        };
    }

    default Double2IntFunction composeDouble(Double2ObjectFunction<K> before) {
        return k -> {
            return getInt(before.get(before));
        };
    }

    default <T> Object2ObjectFunction<K, T> andThenObject(Int2ObjectFunction<? extends T> after) {
        return k -> {
            return after.get(getInt(after));
        };
    }

    default <T> Object2IntFunction<T> composeObject(Object2ObjectFunction<? super T, ? extends K> before) {
        return k -> {
            return getInt(before.get(before));
        };
    }

    default <T> Object2ReferenceFunction<K, T> andThenReference(Int2ReferenceFunction<? extends T> after) {
        return k -> {
            return after.get(getInt(after));
        };
    }

    default <T> Reference2IntFunction<T> composeReference(Reference2ObjectFunction<? super T, ? extends K> before) {
        return k -> {
            return getInt(before.get(before));
        };
    }
}
