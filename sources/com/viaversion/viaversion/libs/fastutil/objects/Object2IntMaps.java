package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.ints.IntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntSets;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunctions;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import java.io.Serializable;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2IntMaps.class */
public final class Object2IntMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Object2IntMaps() {
    }

    public static <K> ObjectIterator<Object2IntMap.Entry<K>> fastIterator(Object2IntMap<K> map) {
        ObjectSet<Object2IntMap.Entry<K>> entries = map.object2IntEntrySet();
        if (entries instanceof Object2IntMap.FastEntrySet) {
            return ((Object2IntMap.FastEntrySet) entries).fastIterator();
        }
        return entries.iterator();
    }

    public static <K> void fastForEach(Object2IntMap<K> map, Consumer<? super Object2IntMap.Entry<K>> consumer) {
        ObjectSet<Object2IntMap.Entry<K>> entries = map.object2IntEntrySet();
        if (entries instanceof Object2IntMap.FastEntrySet) {
            ((Object2IntMap.FastEntrySet) entries).fastForEach(consumer);
        } else {
            entries.forEach(consumer);
        }
    }

    public static <K> ObjectIterable<Object2IntMap.Entry<K>> fastIterable(Object2IntMap<K> map) {
        final ObjectSet<Object2IntMap.Entry<K>> entries = map.object2IntEntrySet();
        return entries instanceof Object2IntMap.FastEntrySet ? new ObjectIterable<Object2IntMap.Entry<K>>() { // from class: com.viaversion.viaversion.libs.fastutil.objects.Object2IntMaps.1
            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection
            public ObjectIterator<Object2IntMap.Entry<K>> iterator() {
                return ((Object2IntMap.FastEntrySet) entries).fastIterator();
            }

            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectSet, java.util.Set
            public ObjectSpliterator<Object2IntMap.Entry<K>> spliterator() {
                return entries.spliterator();
            }

            @Override // java.lang.Iterable
            public void forEach(Consumer<? super Object2IntMap.Entry<K>> consumer) {
                ((Object2IntMap.FastEntrySet) entries).fastForEach(consumer);
            }
        } : entries;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2IntMaps$EmptyMap.class */
    public static class EmptyMap<K> extends Object2IntFunctions.EmptyFunction<K> implements Object2IntMap<K>, Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap
        public boolean containsValue(int v) {
            return false;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunction
        @Deprecated
        public Integer getOrDefault(Object key, Integer defaultValue) {
            return defaultValue;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunctions.EmptyFunction, com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunction
        public int getOrDefault(Object key, int defaultValue) {
            return defaultValue;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap, java.util.Map
        @Deprecated
        public boolean containsValue(Object ov) {
            return false;
        }

        @Override // java.util.Map
        public void putAll(Map<? extends K, ? extends Integer> m) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap
        public ObjectSet<Object2IntMap.Entry<K>> object2IntEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap, java.util.Map
        public ObjectSet<K> keySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap, java.util.Map
        public IntCollection values() {
            return IntSets.EMPTY_SET;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap, java.util.Map
        public void forEach(BiConsumer<? super K, ? super Integer> consumer) {
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunctions.EmptyFunction
        public Object clone() {
            return Object2IntMaps.EMPTY_MAP;
        }

        @Override // java.util.Map
        public boolean isEmpty() {
            return true;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunctions.EmptyFunction, java.util.Map
        public int hashCode() {
            return 0;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunctions.EmptyFunction, java.util.Map
        public boolean equals(Object o) {
            if (!(o instanceof Map)) {
                return false;
            }
            return ((Map) o).isEmpty();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunctions.EmptyFunction
        public String toString() {
            return "{}";
        }
    }

    public static <K> Object2IntMap<K> emptyMap() {
        return EMPTY_MAP;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2IntMaps$Singleton.class */
    public static class Singleton<K> extends Object2IntFunctions.Singleton<K> implements Object2IntMap<K>, Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Object2IntMap.Entry<K>> entries;
        protected transient ObjectSet<K> keys;
        protected transient IntCollection values;

        public Singleton(K key, int value) {
            super(key, value);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap
        public boolean containsValue(int v) {
            return this.value == v;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap, java.util.Map
        @Deprecated
        public boolean containsValue(Object ov) {
            return ((Integer) ov).intValue() == this.value;
        }

        @Override // java.util.Map
        public void putAll(Map<? extends K, ? extends Integer> m) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap
        public ObjectSet<Object2IntMap.Entry<K>> object2IntEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new AbstractObject2IntMap.BasicEntry(this.key, this.value));
            }
            return this.entries;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap, java.util.Map
        @Deprecated
        public ObjectSet<Map.Entry<K, Integer>> entrySet() {
            return object2IntEntrySet();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap, java.util.Map
        public ObjectSet<K> keySet() {
            if (this.keys == null) {
                this.keys = ObjectSets.singleton(this.key);
            }
            return this.keys;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap, java.util.Map
        public IntCollection values() {
            if (this.values == null) {
                this.values = IntSets.singleton(this.value);
            }
            return this.values;
        }

        @Override // java.util.Map
        public boolean isEmpty() {
            return false;
        }

        @Override // java.util.Map
        public int hashCode() {
            return (this.key == null ? 0 : this.key.hashCode()) ^ this.value;
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

    public static <K> Object2IntMap<K> singleton(K key, int value) {
        return new Singleton(key, value);
    }

    public static <K> Object2IntMap<K> singleton(K key, Integer value) {
        return new Singleton(key, value.intValue());
    }

    public static <K> Object2IntMap<K> synchronize(Object2IntMap<K> m) {
        return new SynchronizedMap(m);
    }

    public static <K> Object2IntMap<K> synchronize(Object2IntMap<K> m, Object sync) {
        return new SynchronizedMap(m, sync);
    }

    public static <K> Object2IntMap<K> unmodifiable(Object2IntMap<? extends K> m) {
        return new UnmodifiableMap(m);
    }
}
