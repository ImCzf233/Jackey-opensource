package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Function;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.IntFunction;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectFunctions.class */
public final class Int2ObjectFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Int2ObjectFunctions() {
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectFunctions$EmptyFunction.class */
    public static class EmptyFunction<V> extends AbstractInt2ObjectFunction<V> implements Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction
        public V get(int k) {
            return null;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction
        public V getOrDefault(int k, V defaultValue) {
            return defaultValue;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction, com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap
        public boolean containsKey(int k) {
            return false;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2ObjectFunction, com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction
        public V defaultReturnValue() {
            return null;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2ObjectFunction, com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction
        public void defaultReturnValue(V defRetValue) {
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
            return Int2ObjectFunctions.EMPTY_FUNCTION;
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
            return Int2ObjectFunctions.EMPTY_FUNCTION;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectFunctions$Singleton.class */
    public static class Singleton<V> extends AbstractInt2ObjectFunction<V> implements Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final int key;
        protected final V value;

        public Singleton(int key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction, com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap
        public boolean containsKey(int k) {
            return this.key == k;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction
        public V get(int k) {
            return this.key == k ? this.value : this.defRetValue;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction
        public V getOrDefault(int k, V defaultValue) {
            return this.key == k ? this.value : defaultValue;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.Function, com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
        public int size() {
            return 1;
        }

        public Object clone() {
            return this;
        }
    }

    public static <V> Int2ObjectFunction<V> singleton(int key, V value) {
        return new Singleton(key, value);
    }

    public static <V> Int2ObjectFunction<V> singleton(Integer key, V value) {
        return new Singleton(key.intValue(), value);
    }

    public static <V> Int2ObjectFunction<V> synchronize(Int2ObjectFunction<V> f) {
        return new SynchronizedFunction(f);
    }

    public static <V> Int2ObjectFunction<V> synchronize(Int2ObjectFunction<V> f, Object sync) {
        return new SynchronizedFunction(f, sync);
    }

    public static <V> Int2ObjectFunction<V> unmodifiable(Int2ObjectFunction<? extends V> f) {
        return new UnmodifiableFunction(f);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectFunctions$PrimitiveFunction.class */
    public static class PrimitiveFunction<V> implements Int2ObjectFunction<V> {
        protected final java.util.function.Function<? super Integer, ? extends V> function;

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction, com.viaversion.viaversion.libs.fastutil.Function
        @Deprecated
        public /* bridge */ /* synthetic */ Object put(Integer num, Object obj) {
            return put(num, (Integer) obj);
        }

        protected PrimitiveFunction(java.util.function.Function<? super Integer, ? extends V> function) {
            this.function = function;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction, com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap
        public boolean containsKey(int key) {
            return this.function.apply(Integer.valueOf(key)) != null;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction, com.viaversion.viaversion.libs.fastutil.Function
        @Deprecated
        public boolean containsKey(Object key) {
            return (key == null || this.function.apply((Integer) key) == null) ? false : true;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction
        public V get(int key) {
            V v = this.function.apply(Integer.valueOf(key));
            if (v == null) {
                return null;
            }
            return v;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction
        public V getOrDefault(int key, V defaultValue) {
            V v = this.function.apply(Integer.valueOf(key));
            if (v == null) {
                return defaultValue;
            }
            return v;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction, com.viaversion.viaversion.libs.fastutil.Function
        @Deprecated
        public V get(Object key) {
            if (key == null) {
                return null;
            }
            return this.function.apply((Integer) key);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction, com.viaversion.viaversion.libs.fastutil.Function
        @Deprecated
        public V getOrDefault(Object key, V defaultValue) {
            if (key == null) {
                return defaultValue;
            }
            V v = this.function.apply((Integer) key);
            return v == null ? defaultValue : v;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction
        @Deprecated
        public V put(Integer key, V value) {
            throw new UnsupportedOperationException();
        }
    }

    public static <V> Int2ObjectFunction<V> primitive(java.util.function.Function<? super Integer, ? extends V> f) {
        Objects.requireNonNull(f);
        if (f instanceof Int2ObjectFunction) {
            return (Int2ObjectFunction) f;
        }
        if (f instanceof IntFunction) {
            IntFunction intFunction = (IntFunction) f;
            Objects.requireNonNull(intFunction);
            return this::apply;
        }
        return new PrimitiveFunction(f);
    }
}
