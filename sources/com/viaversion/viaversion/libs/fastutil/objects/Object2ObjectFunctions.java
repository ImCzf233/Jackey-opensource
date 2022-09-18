package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Function;
import java.io.Serializable;
import java.util.Objects;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2ObjectFunctions.class */
public final class Object2ObjectFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Object2ObjectFunctions() {
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2ObjectFunctions$EmptyFunction.class */
    public static class EmptyFunction<K, V> extends AbstractObject2ObjectFunction<K, V> implements Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectFunction, com.viaversion.viaversion.libs.fastutil.Function
        public V get(Object k) {
            return null;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectFunction, com.viaversion.viaversion.libs.fastutil.Function
        public V getOrDefault(Object k, V defaultValue) {
            return defaultValue;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.Function
        public boolean containsKey(Object k) {
            return false;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2ObjectFunction, com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectFunction
        public V defaultReturnValue() {
            return null;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2ObjectFunction, com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectFunction
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
            return Object2ObjectFunctions.EMPTY_FUNCTION;
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
            return Object2ObjectFunctions.EMPTY_FUNCTION;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2ObjectFunctions$Singleton.class */
    public static class Singleton<K, V> extends AbstractObject2ObjectFunction<K, V> implements Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final K key;
        protected final V value;

        public Singleton(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.Function
        public boolean containsKey(Object k) {
            return Objects.equals(this.key, k);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectFunction, com.viaversion.viaversion.libs.fastutil.Function
        public V get(Object k) {
            return Objects.equals(this.key, k) ? this.value : this.defRetValue;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectFunction, com.viaversion.viaversion.libs.fastutil.Function
        public V getOrDefault(Object k, V defaultValue) {
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

    public static <K, V> Object2ObjectFunction<K, V> singleton(K key, V value) {
        return new Singleton(key, value);
    }

    public static <K, V> Object2ObjectFunction<K, V> synchronize(Object2ObjectFunction<K, V> f) {
        return new SynchronizedFunction(f);
    }

    public static <K, V> Object2ObjectFunction<K, V> synchronize(Object2ObjectFunction<K, V> f, Object sync) {
        return new SynchronizedFunction(f, sync);
    }

    public static <K, V> Object2ObjectFunction<K, V> unmodifiable(Object2ObjectFunction<? extends K, ? extends V> f) {
        return new UnmodifiableFunction(f);
    }
}
