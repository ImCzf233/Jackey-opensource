package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectFunctions;
import com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap;
import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2ObjectMaps.class */
public final class Object2ObjectMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Object2ObjectMaps() {
    }

    public static <K, V> ObjectIterator<Object2ObjectMap.Entry<K, V>> fastIterator(Object2ObjectMap<K, V> map) {
        ObjectSet<Object2ObjectMap.Entry<K, V>> entries = map.object2ObjectEntrySet();
        if (entries instanceof Object2ObjectMap.FastEntrySet) {
            return ((Object2ObjectMap.FastEntrySet) entries).fastIterator();
        }
        return entries.iterator();
    }

    public static <K, V> void fastForEach(Object2ObjectMap<K, V> map, Consumer<? super Object2ObjectMap.Entry<K, V>> consumer) {
        ObjectSet<Object2ObjectMap.Entry<K, V>> entries = map.object2ObjectEntrySet();
        if (entries instanceof Object2ObjectMap.FastEntrySet) {
            ((Object2ObjectMap.FastEntrySet) entries).fastForEach(consumer);
        } else {
            entries.forEach(consumer);
        }
    }

    public static <K, V> ObjectIterable<Object2ObjectMap.Entry<K, V>> fastIterable(Object2ObjectMap<K, V> map) {
        final ObjectSet<Object2ObjectMap.Entry<K, V>> entries = map.object2ObjectEntrySet();
        return entries instanceof Object2ObjectMap.FastEntrySet ? new ObjectIterable<Object2ObjectMap.Entry<K, V>>() { // from class: com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMaps.1
            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection
            public ObjectIterator<Object2ObjectMap.Entry<K, V>> iterator() {
                return ((Object2ObjectMap.FastEntrySet) entries).fastIterator();
            }

            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectSet, java.util.Set
            public ObjectSpliterator<Object2ObjectMap.Entry<K, V>> spliterator() {
                return entries.spliterator();
            }

            @Override // java.lang.Iterable
            public void forEach(Consumer<? super Object2ObjectMap.Entry<K, V>> consumer) {
                ((Object2ObjectMap.FastEntrySet) entries).fastForEach(consumer);
            }
        } : entries;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2ObjectMaps$EmptyMap.class */
    public static class EmptyMap<K, V> extends Object2ObjectFunctions.EmptyFunction<K, V> implements Object2ObjectMap<K, V>, Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        @Override // java.util.Map
        public boolean containsValue(Object v) {
            return false;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectFunctions.EmptyFunction, com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectFunction, com.viaversion.viaversion.libs.fastutil.Function
        public V getOrDefault(Object key, V defaultValue) {
            return defaultValue;
        }

        @Override // java.util.Map
        public void putAll(Map<? extends K, ? extends V> m) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap
        public ObjectSet<Object2ObjectMap.Entry<K, V>> object2ObjectEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap, java.util.Map
        public ObjectSet<K> keySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap, java.util.Map
        public ObjectCollection<V> values() {
            return ObjectSets.EMPTY_SET;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap, java.util.Map
        public void forEach(BiConsumer<? super K, ? super V> consumer) {
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectFunctions.EmptyFunction
        public Object clone() {
            return Object2ObjectMaps.EMPTY_MAP;
        }

        @Override // java.util.Map
        public boolean isEmpty() {
            return true;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectFunctions.EmptyFunction, java.util.Map
        public int hashCode() {
            return 0;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectFunctions.EmptyFunction, java.util.Map
        public boolean equals(Object o) {
            if (!(o instanceof Map)) {
                return false;
            }
            return ((Map) o).isEmpty();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectFunctions.EmptyFunction
        public String toString() {
            return "{}";
        }
    }

    public static <K, V> Object2ObjectMap<K, V> emptyMap() {
        return EMPTY_MAP;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2ObjectMaps$Singleton.class */
    public static class Singleton<K, V> extends Object2ObjectFunctions.Singleton<K, V> implements Object2ObjectMap<K, V>, Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Object2ObjectMap.Entry<K, V>> entries;
        protected transient ObjectSet<K> keys;
        protected transient ObjectCollection<V> values;

        public Singleton(K key, V value) {
            super(key, value);
        }

        @Override // java.util.Map
        public boolean containsValue(Object v) {
            return Objects.equals(this.value, v);
        }

        @Override // java.util.Map
        public void putAll(Map<? extends K, ? extends V> m) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap
        public ObjectSet<Object2ObjectMap.Entry<K, V>> object2ObjectEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new AbstractObject2ObjectMap.BasicEntry(this.key, this.value));
            }
            return this.entries;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap, java.util.Map
        public ObjectSet<Map.Entry<K, V>> entrySet() {
            return object2ObjectEntrySet();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap, java.util.Map
        public ObjectSet<K> keySet() {
            if (this.keys == null) {
                this.keys = ObjectSets.singleton(this.key);
            }
            return this.keys;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap, java.util.Map
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
            return (this.key == null ? 0 : this.key.hashCode()) ^ (this.value == null ? 0 : this.value.hashCode());
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

    public static <K, V> Object2ObjectMap<K, V> singleton(K key, V value) {
        return new Singleton(key, value);
    }

    public static <K, V> Object2ObjectMap<K, V> synchronize(Object2ObjectMap<K, V> m) {
        return new SynchronizedMap(m);
    }

    public static <K, V> Object2ObjectMap<K, V> synchronize(Object2ObjectMap<K, V> m, Object sync) {
        return new SynchronizedMap(m, sync);
    }

    public static <K, V> Object2ObjectMap<K, V> unmodifiable(Object2ObjectMap<? extends K, ? extends V> m) {
        return new UnmodifiableMap(m);
    }
}
