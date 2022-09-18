package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMaps;
import com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectSortedMap;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2ObjectSortedMaps.class */
public final class Object2ObjectSortedMaps {
    public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();

    private Object2ObjectSortedMaps() {
    }

    public static <K> Comparator<? super Map.Entry<K, ?>> entryComparator(Comparator<? super K> comparator) {
        return x, y -> {
            return comparator.compare(x.getKey(), y.getKey());
        };
    }

    public static <K, V> ObjectBidirectionalIterator<Object2ObjectMap.Entry<K, V>> fastIterator(Object2ObjectSortedMap<K, V> map) {
        ObjectSortedSet<Object2ObjectMap.Entry<K, V>> entries = map.object2ObjectEntrySet();
        if (entries instanceof Object2ObjectSortedMap.FastSortedEntrySet) {
            return ((Object2ObjectSortedMap.FastSortedEntrySet) entries).fastIterator();
        }
        return entries.iterator();
    }

    public static <K, V> ObjectBidirectionalIterable<Object2ObjectMap.Entry<K, V>> fastIterable(Object2ObjectSortedMap<K, V> map) {
        ObjectSortedSet<Object2ObjectMap.Entry<K, V>> entries = map.object2ObjectEntrySet();
        if (entries instanceof Object2ObjectSortedMap.FastSortedEntrySet) {
            Object2ObjectSortedMap.FastSortedEntrySet fastSortedEntrySet = (Object2ObjectSortedMap.FastSortedEntrySet) entries;
            Objects.requireNonNull(fastSortedEntrySet);
            return this::fastIterator;
        }
        return entries;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2ObjectSortedMaps$EmptySortedMap.class */
    public static class EmptySortedMap<K, V> extends Object2ObjectMaps.EmptyMap<K, V> implements Object2ObjectSortedMap<K, V>, Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptySortedMap() {
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectSortedMap, java.util.SortedMap
        public Comparator<? super K> comparator() {
            return null;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMaps.EmptyMap, com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap
        public ObjectSortedSet<Object2ObjectMap.Entry<K, V>> object2ObjectEntrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap, java.util.Map
        public ObjectSortedSet<Map.Entry<K, V>> entrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMaps.EmptyMap, com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap, java.util.Map
        public ObjectSortedSet<K> keySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectSortedMap, java.util.SortedMap
        public Object2ObjectSortedMap<K, V> subMap(K from, K to) {
            return Object2ObjectSortedMaps.EMPTY_MAP;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectSortedMap, java.util.SortedMap
        public Object2ObjectSortedMap<K, V> headMap(K to) {
            return Object2ObjectSortedMaps.EMPTY_MAP;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectSortedMap, java.util.SortedMap
        public Object2ObjectSortedMap<K, V> tailMap(K from) {
            return Object2ObjectSortedMaps.EMPTY_MAP;
        }

        @Override // java.util.SortedMap
        public K firstKey() {
            throw new NoSuchElementException();
        }

        @Override // java.util.SortedMap
        public K lastKey() {
            throw new NoSuchElementException();
        }
    }

    public static <K, V> Object2ObjectSortedMap<K, V> emptyMap() {
        return EMPTY_MAP;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2ObjectSortedMaps$Singleton.class */
    public static class Singleton<K, V> extends Object2ObjectMaps.Singleton<K, V> implements Object2ObjectSortedMap<K, V>, Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Comparator<? super K> comparator;

        protected Singleton(K key, V value, Comparator<? super K> comparator) {
            super(key, value);
            this.comparator = comparator;
        }

        protected Singleton(K key, V value) {
            this(key, value, null);
        }

        final int compare(K k1, K k2) {
            return this.comparator == null ? ((Comparable) k1).compareTo(k2) : this.comparator.compare(k1, k2);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectSortedMap, java.util.SortedMap
        public Comparator<? super K> comparator() {
            return this.comparator;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMaps.Singleton, com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap
        public ObjectSortedSet<Object2ObjectMap.Entry<K, V>> object2ObjectEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.singleton(new AbstractObject2ObjectMap.BasicEntry(this.key, this.value), Object2ObjectSortedMaps.entryComparator(this.comparator));
            }
            return (ObjectSortedSet) this.entries;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMaps.Singleton, com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap, java.util.Map
        public ObjectSortedSet<Map.Entry<K, V>> entrySet() {
            return object2ObjectEntrySet();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMaps.Singleton, com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap, java.util.Map
        public ObjectSortedSet<K> keySet() {
            if (this.keys == null) {
                this.keys = ObjectSortedSets.singleton(this.key, this.comparator);
            }
            return (ObjectSortedSet) this.keys;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectSortedMap, java.util.SortedMap
        public Object2ObjectSortedMap<K, V> subMap(K from, K to) {
            if (compare(from, this.key) <= 0 && compare(this.key, to) < 0) {
                return this;
            }
            return Object2ObjectSortedMaps.EMPTY_MAP;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectSortedMap, java.util.SortedMap
        public Object2ObjectSortedMap<K, V> headMap(K to) {
            if (compare(this.key, to) < 0) {
                return this;
            }
            return Object2ObjectSortedMaps.EMPTY_MAP;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectSortedMap, java.util.SortedMap
        public Object2ObjectSortedMap<K, V> tailMap(K from) {
            if (compare(from, this.key) <= 0) {
                return this;
            }
            return Object2ObjectSortedMaps.EMPTY_MAP;
        }

        @Override // java.util.SortedMap
        public K firstKey() {
            return this.key;
        }

        @Override // java.util.SortedMap
        public K lastKey() {
            return this.key;
        }
    }

    public static <K, V> Object2ObjectSortedMap<K, V> singleton(K key, V value) {
        return new Singleton(key, value);
    }

    public static <K, V> Object2ObjectSortedMap<K, V> singleton(K key, V value, Comparator<? super K> comparator) {
        return new Singleton(key, value, comparator);
    }

    public static <K, V> Object2ObjectSortedMap<K, V> synchronize(Object2ObjectSortedMap<K, V> m) {
        return new SynchronizedSortedMap(m);
    }

    public static <K, V> Object2ObjectSortedMap<K, V> synchronize(Object2ObjectSortedMap<K, V> m, Object sync) {
        return new SynchronizedSortedMap(m, sync);
    }

    public static <K, V> Object2ObjectSortedMap<K, V> unmodifiable(Object2ObjectSortedMap<K, ? extends V> m) {
        return new UnmodifiableSortedMap(m);
    }
}
