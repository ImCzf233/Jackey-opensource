package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Function;
import java.io.Serializable;
import java.util.Objects;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2IntFunctions.class */
public final class Int2IntFunctions {
    public static final EmptyFunction EMPTY_FUNCTION = new EmptyFunction();

    private Int2IntFunctions() {
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2IntFunctions$EmptyFunction.class */
    public static class EmptyFunction extends AbstractInt2IntFunction implements Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction
        public int get(int k) {
            return 0;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction
        public int getOrDefault(int k, int defaultValue) {
            return defaultValue;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction, com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap
        public boolean containsKey(int k) {
            return false;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2IntFunction, com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction
        public int defaultReturnValue() {
            return 0;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2IntFunction, com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction
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
            return Int2IntFunctions.EMPTY_FUNCTION;
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
            return Int2IntFunctions.EMPTY_FUNCTION;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2IntFunctions$Singleton.class */
    public static class Singleton extends AbstractInt2IntFunction implements Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final int key;
        protected final int value;

        public Singleton(int key, int value) {
            this.key = key;
            this.value = value;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction, com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap
        public boolean containsKey(int k) {
            return this.key == k;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction
        public int get(int k) {
            return this.key == k ? this.value : this.defRetValue;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction
        public int getOrDefault(int k, int defaultValue) {
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

    public static Int2IntFunction singleton(int key, int value) {
        return new Singleton(key, value);
    }

    public static Int2IntFunction singleton(Integer key, Integer value) {
        return new Singleton(key.intValue(), value.intValue());
    }

    public static Int2IntFunction synchronize(Int2IntFunction f) {
        return new SynchronizedFunction(f);
    }

    public static Int2IntFunction synchronize(Int2IntFunction f, Object sync) {
        return new SynchronizedFunction(f, sync);
    }

    public static Int2IntFunction unmodifiable(Int2IntFunction f) {
        return new UnmodifiableFunction(f);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2IntFunctions$PrimitiveFunction.class */
    public static class PrimitiveFunction implements Int2IntFunction {
        protected final java.util.function.Function<? super Integer, ? extends Integer> function;

        protected PrimitiveFunction(java.util.function.Function<? super Integer, ? extends Integer> function) {
            this.function = function;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction, com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap
        public boolean containsKey(int key) {
            return this.function.apply(Integer.valueOf(key)) != null;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction, com.viaversion.viaversion.libs.fastutil.Function
        @Deprecated
        public boolean containsKey(Object key) {
            return (key == null || this.function.apply((Integer) key) == null) ? false : true;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction
        public int get(int key) {
            Integer v = this.function.apply(Integer.valueOf(key));
            if (v == null) {
                return defaultReturnValue();
            }
            return v.intValue();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction
        public int getOrDefault(int key, int defaultValue) {
            Integer v = this.function.apply(Integer.valueOf(key));
            if (v == null) {
                return defaultValue;
            }
            return v.intValue();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction, com.viaversion.viaversion.libs.fastutil.Function
        @Deprecated
        public Integer get(Object key) {
            if (key == null) {
                return null;
            }
            return this.function.apply((Integer) key);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction
        @Deprecated
        public Integer getOrDefault(Object key, Integer defaultValue) {
            if (key == null) {
                return defaultValue;
            }
            Integer v = this.function.apply((Integer) key);
            return v == null ? defaultValue : v;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction
        @Deprecated
        public Integer put(Integer key, Integer value) {
            throw new UnsupportedOperationException();
        }
    }

    public static Int2IntFunction primitive(java.util.function.Function<? super Integer, ? extends Integer> f) {
        Objects.requireNonNull(f);
        if (f instanceof Int2IntFunction) {
            return (Int2IntFunction) f;
        }
        if (f instanceof java.util.function.IntUnaryOperator) {
            java.util.function.IntUnaryOperator intUnaryOperator = (java.util.function.IntUnaryOperator) f;
            Objects.requireNonNull(intUnaryOperator);
            return this::applyAsInt;
        }
        return new PrimitiveFunction(f);
    }
}
