package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMaps;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntSortedMap;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2IntSortedMaps.class */
public final class Object2IntSortedMaps {
    public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();

    private Object2IntSortedMaps() {
    }

    public static <K> Comparator<? super Map.Entry<K, ?>> entryComparator(Comparator<? super K> comparator) {
        return x, y -> {
            return comparator.compare(x.getKey(), y.getKey());
        };
    }

    public static <K> ObjectBidirectionalIterator<Object2IntMap.Entry<K>> fastIterator(Object2IntSortedMap<K> map) {
        ObjectSortedSet<Object2IntMap.Entry<K>> entries = map.object2IntEntrySet();
        if (entries instanceof Object2IntSortedMap.FastSortedEntrySet) {
            return ((Object2IntSortedMap.FastSortedEntrySet) entries).fastIterator();
        }
        return entries.iterator();
    }

    public static <K> ObjectBidirectionalIterable<Object2IntMap.Entry<K>> fastIterable(Object2IntSortedMap<K> map) {
        ObjectSortedSet<Object2IntMap.Entry<K>> entries = map.object2IntEntrySet();
        if (entries instanceof Object2IntSortedMap.FastSortedEntrySet) {
            Object2IntSortedMap.FastSortedEntrySet fastSortedEntrySet = (Object2IntSortedMap.FastSortedEntrySet) entries;
            Objects.requireNonNull(fastSortedEntrySet);
            return this::fastIterator;
        }
        return entries;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2IntSortedMaps$EmptySortedMap.class */
    public static class EmptySortedMap<K> extends Object2IntMaps.EmptyMap<K> implements Object2IntSortedMap<K>, Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptySortedMap() {
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntSortedMap, java.util.SortedMap
        public Comparator<? super K> comparator() {
            return null;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMaps.EmptyMap, com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap
        public ObjectSortedSet<Object2IntMap.Entry<K>> object2IntEntrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap, java.util.Map
        @Deprecated
        public ObjectSortedSet<Map.Entry<K, Integer>> entrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMaps.EmptyMap, com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap, java.util.Map
        public ObjectSortedSet<K> keySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntSortedMap, java.util.SortedMap
        public Object2IntSortedMap<K> subMap(K from, K to) {
            return Object2IntSortedMaps.EMPTY_MAP;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntSortedMap, java.util.SortedMap
        public Object2IntSortedMap<K> headMap(K to) {
            return Object2IntSortedMaps.EMPTY_MAP;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntSortedMap, java.util.SortedMap
        public Object2IntSortedMap<K> tailMap(K from) {
            return Object2IntSortedMaps.EMPTY_MAP;
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

    public static <K> Object2IntSortedMap<K> emptyMap() {
        return EMPTY_MAP;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2IntSortedMaps$Singleton.class */
    public static class Singleton<K> extends Object2IntMaps.Singleton<K> implements Object2IntSortedMap<K>, Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Comparator<? super K> comparator;

        protected Singleton(K key, int value, Comparator<? super K> comparator) {
            super(key, value);
            this.comparator = comparator;
        }

        protected Singleton(K key, int value) {
            this(key, value, null);
        }

        final int compare(K k1, K k2) {
            return this.comparator == null ? ((Comparable) k1).compareTo(k2) : this.comparator.compare(k1, k2);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntSortedMap, java.util.SortedMap
        public Comparator<? super K> comparator() {
            return this.comparator;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMaps.Singleton, com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap
        public ObjectSortedSet<Object2IntMap.Entry<K>> object2IntEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.singleton(new AbstractObject2IntMap.BasicEntry(this.key, this.value), Object2IntSortedMaps.entryComparator(this.comparator));
            }
            return (ObjectSortedSet) this.entries;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMaps.Singleton, com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap, java.util.Map
        @Deprecated
        public ObjectSortedSet<Map.Entry<K, Integer>> entrySet() {
            return object2IntEntrySet();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMaps.Singleton, com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap, java.util.Map
        public ObjectSortedSet<K> keySet() {
            if (this.keys == null) {
                this.keys = ObjectSortedSets.singleton(this.key, this.comparator);
            }
            return (ObjectSortedSet) this.keys;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntSortedMap, java.util.SortedMap
        public Object2IntSortedMap<K> subMap(K from, K to) {
            if (compare(from, this.key) <= 0 && compare(this.key, to) < 0) {
                return this;
            }
            return Object2IntSortedMaps.EMPTY_MAP;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntSortedMap, java.util.SortedMap
        public Object2IntSortedMap<K> headMap(K to) {
            if (compare(this.key, to) < 0) {
                return this;
            }
            return Object2IntSortedMaps.EMPTY_MAP;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntSortedMap, java.util.SortedMap
        public Object2IntSortedMap<K> tailMap(K from) {
            if (compare(from, this.key) <= 0) {
                return this;
            }
            return Object2IntSortedMaps.EMPTY_MAP;
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

    public static <K> Object2IntSortedMap<K> singleton(K key, Integer value) {
        return new Singleton(key, value.intValue());
    }

    public static <K> Object2IntSortedMap<K> singleton(K key, Integer value, Comparator<? super K> comparator) {
        return new Singleton(key, value.intValue(), comparator);
    }

    public static <K> Object2IntSortedMap<K> singleton(K key, int value) {
        return new Singleton(key, value);
    }

    public static <K> Object2IntSortedMap<K> singleton(K key, int value, Comparator<? super K> comparator) {
        return new Singleton(key, value, comparator);
    }

    public static <K> Object2IntSortedMap<K> synchronize(Object2IntSortedMap<K> m) {
        return new SynchronizedSortedMap(m);
    }

    public static <K> Object2IntSortedMap<K> synchronize(Object2IntSortedMap<K> m, Object sync) {
        return new SynchronizedSortedMap(m, sync);
    }

    public static <K> Object2IntSortedMap<K> unmodifiable(Object2IntSortedMap<K> m) {
        return new UnmodifiableSortedMap(m);
    }
}
