package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMaps;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectSortedMap;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterable;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSets;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectSortedMaps.class */
public final class Int2ObjectSortedMaps {
    public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();

    private Int2ObjectSortedMaps() {
    }

    public static Comparator<? super Map.Entry<Integer, ?>> entryComparator(IntComparator comparator) {
        return x, y -> {
            return comparator.compare(((Integer) x.getKey()).intValue(), ((Integer) y.getKey()).intValue());
        };
    }

    public static <V> ObjectBidirectionalIterator<Int2ObjectMap.Entry<V>> fastIterator(Int2ObjectSortedMap<V> map) {
        ObjectSortedSet<Int2ObjectMap.Entry<V>> entries = map.int2ObjectEntrySet();
        if (entries instanceof Int2ObjectSortedMap.FastSortedEntrySet) {
            return ((Int2ObjectSortedMap.FastSortedEntrySet) entries).fastIterator();
        }
        return entries.iterator();
    }

    public static <V> ObjectBidirectionalIterable<Int2ObjectMap.Entry<V>> fastIterable(Int2ObjectSortedMap<V> map) {
        ObjectSortedSet<Int2ObjectMap.Entry<V>> entries = map.int2ObjectEntrySet();
        if (entries instanceof Int2ObjectSortedMap.FastSortedEntrySet) {
            Int2ObjectSortedMap.FastSortedEntrySet fastSortedEntrySet = (Int2ObjectSortedMap.FastSortedEntrySet) entries;
            Objects.requireNonNull(fastSortedEntrySet);
            return this::fastIterator;
        }
        return entries;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectSortedMaps$EmptySortedMap.class */
    public static class EmptySortedMap<V> extends Int2ObjectMaps.EmptyMap<V> implements Int2ObjectSortedMap<V>, Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptySortedMap() {
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectSortedMap, java.util.SortedMap
        public IntComparator comparator() {
            return null;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMaps.EmptyMap, com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap
        public ObjectSortedSet<Int2ObjectMap.Entry<V>> int2ObjectEntrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap, java.util.Map
        @Deprecated
        public ObjectSortedSet<Map.Entry<Integer, V>> entrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMaps.EmptyMap, com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap, java.util.Map
        public IntSortedSet keySet() {
            return IntSortedSets.EMPTY_SET;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectSortedMap
        public Int2ObjectSortedMap<V> subMap(int from, int to) {
            return Int2ObjectSortedMaps.EMPTY_MAP;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectSortedMap
        public Int2ObjectSortedMap<V> headMap(int to) {
            return Int2ObjectSortedMaps.EMPTY_MAP;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectSortedMap
        public Int2ObjectSortedMap<V> tailMap(int from) {
            return Int2ObjectSortedMaps.EMPTY_MAP;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectSortedMap
        public int firstIntKey() {
            throw new NoSuchElementException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectSortedMap
        public int lastIntKey() {
            throw new NoSuchElementException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectSortedMap
        @Deprecated
        public Int2ObjectSortedMap<V> headMap(Integer oto) {
            return headMap(oto.intValue());
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectSortedMap
        @Deprecated
        public Int2ObjectSortedMap<V> tailMap(Integer ofrom) {
            return tailMap(ofrom.intValue());
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectSortedMap
        @Deprecated
        public Int2ObjectSortedMap<V> subMap(Integer ofrom, Integer oto) {
            return subMap(ofrom.intValue(), oto.intValue());
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectSortedMap, java.util.SortedMap
        @Deprecated
        public Integer firstKey() {
            return Integer.valueOf(firstIntKey());
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectSortedMap, java.util.SortedMap
        @Deprecated
        public Integer lastKey() {
            return Integer.valueOf(lastIntKey());
        }
    }

    public static <V> Int2ObjectSortedMap<V> emptyMap() {
        return EMPTY_MAP;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectSortedMaps$Singleton.class */
    public static class Singleton<V> extends Int2ObjectMaps.Singleton<V> implements Int2ObjectSortedMap<V>, Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final IntComparator comparator;

        protected Singleton(int key, V value, IntComparator comparator) {
            super(key, value);
            this.comparator = comparator;
        }

        protected Singleton(int key, V value) {
            this(key, value, null);
        }

        final int compare(int k1, int k2) {
            return this.comparator == null ? Integer.compare(k1, k2) : this.comparator.compare(k1, k2);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectSortedMap, java.util.SortedMap
        public IntComparator comparator() {
            return this.comparator;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMaps.Singleton, com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap
        public ObjectSortedSet<Int2ObjectMap.Entry<V>> int2ObjectEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.singleton(new AbstractInt2ObjectMap.BasicEntry(this.key, this.value), Int2ObjectSortedMaps.entryComparator(this.comparator));
            }
            return (ObjectSortedSet) this.entries;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMaps.Singleton, com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap, java.util.Map
        @Deprecated
        public ObjectSortedSet<Map.Entry<Integer, V>> entrySet() {
            return int2ObjectEntrySet();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMaps.Singleton, com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap, java.util.Map
        public IntSortedSet keySet() {
            if (this.keys == null) {
                this.keys = IntSortedSets.singleton(this.key, this.comparator);
            }
            return (IntSortedSet) this.keys;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectSortedMap
        public Int2ObjectSortedMap<V> subMap(int from, int to) {
            if (compare(from, this.key) <= 0 && compare(this.key, to) < 0) {
                return this;
            }
            return Int2ObjectSortedMaps.EMPTY_MAP;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectSortedMap
        public Int2ObjectSortedMap<V> headMap(int to) {
            if (compare(this.key, to) < 0) {
                return this;
            }
            return Int2ObjectSortedMaps.EMPTY_MAP;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectSortedMap
        public Int2ObjectSortedMap<V> tailMap(int from) {
            if (compare(from, this.key) <= 0) {
                return this;
            }
            return Int2ObjectSortedMaps.EMPTY_MAP;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectSortedMap
        public int firstIntKey() {
            return this.key;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectSortedMap
        public int lastIntKey() {
            return this.key;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectSortedMap
        @Deprecated
        public Int2ObjectSortedMap<V> headMap(Integer oto) {
            return headMap(oto.intValue());
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectSortedMap
        @Deprecated
        public Int2ObjectSortedMap<V> tailMap(Integer ofrom) {
            return tailMap(ofrom.intValue());
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectSortedMap
        @Deprecated
        public Int2ObjectSortedMap<V> subMap(Integer ofrom, Integer oto) {
            return subMap(ofrom.intValue(), oto.intValue());
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectSortedMap, java.util.SortedMap
        @Deprecated
        public Integer firstKey() {
            return Integer.valueOf(firstIntKey());
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectSortedMap, java.util.SortedMap
        @Deprecated
        public Integer lastKey() {
            return Integer.valueOf(lastIntKey());
        }
    }

    public static <V> Int2ObjectSortedMap<V> singleton(Integer key, V value) {
        return new Singleton(key.intValue(), value);
    }

    public static <V> Int2ObjectSortedMap<V> singleton(Integer key, V value, IntComparator comparator) {
        return new Singleton(key.intValue(), value, comparator);
    }

    public static <V> Int2ObjectSortedMap<V> singleton(int key, V value) {
        return new Singleton(key, value);
    }

    public static <V> Int2ObjectSortedMap<V> singleton(int key, V value, IntComparator comparator) {
        return new Singleton(key, value, comparator);
    }

    public static <V> Int2ObjectSortedMap<V> synchronize(Int2ObjectSortedMap<V> m) {
        return new SynchronizedSortedMap(m);
    }

    public static <V> Int2ObjectSortedMap<V> synchronize(Int2ObjectSortedMap<V> m, Object sync) {
        return new SynchronizedSortedMap(m, sync);
    }

    public static <V> Int2ObjectSortedMap<V> unmodifiable(Int2ObjectSortedMap<? extends V> m) {
        return new UnmodifiableSortedMap(m);
    }
}
