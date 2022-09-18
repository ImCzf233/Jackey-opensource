package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Function;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.ToIntFunction;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2IntFunctions.class */
public final class Object2IntFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Object2IntFunctions() {
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2IntFunctions$EmptyFunction.class */
    public static class EmptyFunction<K> extends AbstractObject2IntFunction<K> implements Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunction
        public int getInt(Object k) {
            return 0;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunction
        public int getOrDefault(Object k, int defaultValue) {
            return defaultValue;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.Function
        public boolean containsKey(Object k) {
            return false;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2IntFunction, com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunction
        public int defaultReturnValue() {
            return 0;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2IntFunction, com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunction
        public void defaultReturnValue(int defRetValue) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.Function, com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
        public int size() {
            return 0;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.Function, com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
        public void clear() {
        }

        public Object clone() {
            return Object2IntFunctions.EMPTY_FUNCTION;
        }

        public int hashCode() {
            return 0;
        }

        public boolean equals(Object o) {
            return (o instanceof Function) && ((Function) o).size() == 0;
        }

        public String toString() {
            return "{}";
        }

        private Object readResolve() {
            return Object2IntFunctions.EMPTY_FUNCTION;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2IntFunctions$Singleton.class */
    public static class Singleton<K> extends AbstractObject2IntFunction<K> implements Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final K key;
        protected final int value;

        public Singleton(K key, int value) {
            this.key = key;
            this.value = value;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.Function
        public boolean containsKey(Object k) {
            return Objects.equals(this.key, k);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunction
        public int getInt(Object k) {
            return Objects.equals(this.key, k) ? this.value : this.defRetValue;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunction
        public int getOrDefault(Object k, int defaultValue) {
            return Objects.equals(this.key, k) ? this.value : defaultValue;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.Function, com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
        public int size() {
            return 1;
        }

        public Object clone() {
            return this;
        }
    }

    public static <K> Object2IntFunction<K> singleton(K key, int value) {
        return new Singleton(key, value);
    }

    public static <K> Object2IntFunction<K> singleton(K key, Integer value) {
        return new Singleton(key, value.intValue());
    }

    public static <K> Object2IntFunction<K> synchronize(Object2IntFunction<K> f) {
        return new SynchronizedFunction(f);
    }

    public static <K> Object2IntFunction<K> synchronize(Object2IntFunction<K> f, Object sync) {
        return new SynchronizedFunction(f, sync);
    }

    public static <K> Object2IntFunction<K> unmodifiable(Object2IntFunction<? extends K> f) {
        return new UnmodifiableFunction(f);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2IntFunctions$PrimitiveFunction.class */
    public static class PrimitiveFunction<K> implements Object2IntFunction<K> {
        protected final java.util.function.Function<? super K, ? extends Integer> function;

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunction, com.viaversion.viaversion.libs.fastutil.Function
        @Deprecated
        public /* bridge */ /* synthetic */ Integer put(Object obj, Integer num) {
            return put((PrimitiveFunction<K>) obj, num);
        }

        protected PrimitiveFunction(java.util.function.Function<? super K, ? extends Integer> function) {
            this.function = function;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.Function
        public boolean containsKey(Object key) {
            return this.function.apply(key) != null;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunction
        public int getInt(Object key) {
            Integer v = this.function.apply(key);
            if (v == null) {
                return defaultReturnValue();
            }
            return v.intValue();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunction
        public int getOrDefault(Object key, int defaultValue) {
            Integer v = this.function.apply(key);
            if (v == null) {
                return defaultValue;
            }
            return v.intValue();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunction, com.viaversion.viaversion.libs.fastutil.Function
        @Deprecated
        public Integer get(Object key) {
            return this.function.apply(key);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunction
        @Deprecated
        public Integer getOrDefault(Object key, Integer defaultValue) {
            Integer v = this.function.apply(key);
            return v == null ? defaultValue : v;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunction
        @Deprecated
        public Integer put(K key, Integer value) {
            throw new UnsupportedOperationException();
        }
    }

    public static <K> Object2IntFunction<K> primitive(java.util.function.Function<? super K, ? extends Integer> f) {
        Objects.requireNonNull(f);
        if (f instanceof Object2IntFunction) {
            return (Object2IntFunction) f;
        }
        if (f instanceof ToIntFunction) {
            return key -> {
                return ((ToIntFunction) f).applyAsInt(key);
            };
        }
        return new PrimitiveFunction(f);
    }
}
