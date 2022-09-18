package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunctions;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSets;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator;
import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectMaps.class */
public final class Int2ObjectMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Int2ObjectMaps() {
    }

    public static <V> ObjectIterator<Int2ObjectMap.Entry<V>> fastIterator(Int2ObjectMap<V> map) {
        ObjectSet<Int2ObjectMap.Entry<V>> entries = map.int2ObjectEntrySet();
        if (entries instanceof Int2ObjectMap.FastEntrySet) {
            return ((Int2ObjectMap.FastEntrySet) entries).fastIterator();
        }
        return entries.iterator();
    }

    public static <V> void fastForEach(Int2ObjectMap<V> map, Consumer<? super Int2ObjectMap.Entry<V>> consumer) {
        ObjectSet<Int2ObjectMap.Entry<V>> entries = map.int2ObjectEntrySet();
        if (entries instanceof Int2ObjectMap.FastEntrySet) {
            ((Int2ObjectMap.FastEntrySet) entries).fastForEach(consumer);
        } else {
            entries.forEach(consumer);
        }
    }

    public static <V> ObjectIterable<Int2ObjectMap.Entry<V>> fastIterable(Int2ObjectMap<V> map) {
        final ObjectSet<Int2ObjectMap.Entry<V>> entries = map.int2ObjectEntrySet();
        return entries instanceof Int2ObjectMap.FastEntrySet ? new ObjectIterable<Int2ObjectMap.Entry<V>>() { // from class: com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMaps.1
            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection
            public ObjectIterator<Int2ObjectMap.Entry<V>> iterator() {
                return ((Int2ObjectMap.FastEntrySet) entries).fastIterator();
            }

            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectSet, java.util.Set
            public ObjectSpliterator<Int2ObjectMap.Entry<V>> spliterator() {
                return entries.spliterator();
            }

            @Override // java.lang.Iterable
            public void forEach(Consumer<? super Int2ObjectMap.Entry<V>> consumer) {
                ((Int2ObjectMap.FastEntrySet) entries).fastForEach(consumer);
            }
        } : entries;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectMaps$EmptyMap.class */
    public static class EmptyMap<V> extends Int2ObjectFunctions.EmptyFunction<V> implements Int2ObjectMap<V>, Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        @Override // java.util.Map
        public boolean containsValue(Object v) {
            return false;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction, com.viaversion.viaversion.libs.fastutil.Function
        @Deprecated
        public V getOrDefault(Object key, V defaultValue) {
            return defaultValue;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunctions.EmptyFunction, com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction
        public V getOrDefault(int key, V defaultValue) {
            return defaultValue;
        }

        @Override // java.util.Map
        public void putAll(Map<? extends Integer, ? extends V> m) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap
        public ObjectSet<Int2ObjectMap.Entry<V>> int2ObjectEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap, java.util.Map
        public IntSet keySet() {
            return IntSets.EMPTY_SET;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap, java.util.Map
        public ObjectCollection<V> values() {
            return ObjectSets.EMPTY_SET;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap, java.util.Map
        public void forEach(BiConsumer<? super Integer, ? super V> consumer) {
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunctions.EmptyFunction
        public Object clone() {
            return Int2ObjectMaps.EMPTY_MAP;
        }

        @Override // java.util.Map
        public boolean isEmpty() {
            return true;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunctions.EmptyFunction, java.util.Map
        public int hashCode() {
            return 0;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunctions.EmptyFunction, java.util.Map
        public boolean equals(Object o) {
            if (!(o instanceof Map)) {
                return false;
            }
            return ((Map) o).isEmpty();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunctions.EmptyFunction
        public String toString() {
            return "{}";
        }
    }

    public static <V> Int2ObjectMap<V> emptyMap() {
        return EMPTY_MAP;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectMaps$Singleton.class */
    public static class Singleton<V> extends Int2ObjectFunctions.Singleton<V> implements Int2ObjectMap<V>, Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Int2ObjectMap.Entry<V>> entries;
        protected transient IntSet keys;
        protected transient ObjectCollection<V> values;

        public Singleton(int key, V value) {
            super(key, value);
        }

        @Override // java.util.Map
        public boolean containsValue(Object v) {
            return Objects.equals(this.value, v);
        }

        @Override // java.util.Map
        public void putAll(Map<? extends Integer, ? extends V> m) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap
        public ObjectSet<Int2ObjectMap.Entry<V>> int2ObjectEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new AbstractInt2ObjectMap.BasicEntry(this.key, this.value));
            }
            return this.entries;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap, java.util.Map
        @Deprecated
        public ObjectSet<Map.Entry<Integer, V>> entrySet() {
            return int2ObjectEntrySet();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap, java.util.Map
        public IntSet keySet() {
            if (this.keys == null) {
                this.keys = IntSets.singleton(this.key);
            }
            return this.keys;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap, java.util.Map
        public ObjectCollection<V> values() {
            if (this.values == null) {
                this.values = ObjectSets.singleton(this.value);
            }
            return this.values;
        }

        @Override // java.util.Map
        public boolean isEmpty() {
            return false;
        }

        @Override // java.util.Map
        public int hashCode() {
            return this.key ^ (this.value == null ? 0 : this.value.hashCode());
        }

        @Override // java.util.Map
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof Map)) {
                return false;
            }
            Map<?, ?> m = (Map) o;
            if (m.size() != 1) {
                return false;
            }
            return m.entrySet().iterator().next().equals(entrySet().iterator().next());
        }

        public String toString() {
            return "{" + this.key + "=>" + this.value + "}";
        }
    }

    public static <V> Int2ObjectMap<V> singleton(int key, V value) {
        return new Singleton(key, value);
    }

    public static <V> Int2ObjectMap<V> singleton(Integer key, V value) {
        return new Singleton(key.intValue(), value);
    }

    public static <V> Int2ObjectMap<V> synchronize(Int2ObjectMap<V> m) {
        return new SynchronizedMap(m);
    }

    public static <V> Int2ObjectMap<V> synchronize(Int2ObjectMap<V> m, Object sync) {
        return new SynchronizedMap(m, sync);
    }

    public static <V> Int2ObjectMap<V> unmodifiable(Int2ObjectMap<? extends V> m) {
        return new UnmodifiableMap(m);
    }
}
