package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2IntMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMaps;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntSortedMap;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterable;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSets;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2IntSortedMaps.class */
public final class Int2IntSortedMaps {
    public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();

    private Int2IntSortedMaps() {
    }

    public static Comparator<? super Map.Entry<Integer, ?>> entryComparator(IntComparator comparator) {
        return x, y -> {
            return comparator.compare(((Integer) x.getKey()).intValue(), ((Integer) y.getKey()).intValue());
        };
    }

    public static ObjectBidirectionalIterator<Int2IntMap.Entry> fastIterator(Int2IntSortedMap map) {
        ObjectSortedSet<Int2IntMap.Entry> entries = map.int2IntEntrySet();
        if (entries instanceof Int2IntSortedMap.FastSortedEntrySet) {
            return ((Int2IntSortedMap.FastSortedEntrySet) entries).fastIterator();
        }
        return entries.iterator();
    }

    public static ObjectBidirectionalIterable<Int2IntMap.Entry> fastIterable(Int2IntSortedMap map) {
        ObjectSortedSet<Int2IntMap.Entry> entries = map.int2IntEntrySet();
        if (entries instanceof Int2IntSortedMap.FastSortedEntrySet) {
            Int2IntSortedMap.FastSortedEntrySet fastSortedEntrySet = (Int2IntSortedMap.FastSortedEntrySet) entries;
            Objects.requireNonNull(fastSortedEntrySet);
            return this::fastIterator;
        }
        return entries;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2IntSortedMaps$EmptySortedMap.class */
    public static class EmptySortedMap extends Int2IntMaps.EmptyMap implements Int2IntSortedMap, Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptySortedMap() {
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntSortedMap, java.util.SortedMap
        public IntComparator comparator() {
            return null;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMaps.EmptyMap, com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap
        public ObjectSortedSet<Int2IntMap.Entry> int2IntEntrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
        @Deprecated
        public Set<Map.Entry<Integer, Integer>> entrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMaps.EmptyMap, com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
        public IntSortedSet keySet() {
            return IntSortedSets.EMPTY_SET;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntSortedMap
        public Int2IntSortedMap subMap(int from, int to) {
            return Int2IntSortedMaps.EMPTY_MAP;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntSortedMap
        public Int2IntSortedMap headMap(int to) {
            return Int2IntSortedMaps.EMPTY_MAP;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntSortedMap
        public Int2IntSortedMap tailMap(int from) {
            return Int2IntSortedMaps.EMPTY_MAP;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntSortedMap
        public int firstIntKey() {
            throw new NoSuchElementException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntSortedMap
        public int lastIntKey() {
            throw new NoSuchElementException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntSortedMap
        @Deprecated
        public Int2IntSortedMap headMap(Integer oto) {
            return headMap(oto.intValue());
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntSortedMap
        @Deprecated
        public Int2IntSortedMap tailMap(Integer ofrom) {
            return tailMap(ofrom.intValue());
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntSortedMap
        @Deprecated
        public Int2IntSortedMap subMap(Integer ofrom, Integer oto) {
            return subMap(ofrom.intValue(), oto.intValue());
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntSortedMap, java.util.SortedMap
        @Deprecated
        public Integer firstKey() {
            return Integer.valueOf(firstIntKey());
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntSortedMap, java.util.SortedMap
        @Deprecated
        public Integer lastKey() {
            return Integer.valueOf(lastIntKey());
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2IntSortedMaps$Singleton.class */
    public static class Singleton extends Int2IntMaps.Singleton implements Int2IntSortedMap, Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final IntComparator comparator;

        protected Singleton(int key, int value, IntComparator comparator) {
            super(key, value);
            this.comparator = comparator;
        }

        protected Singleton(int key, int value) {
            this(key, value, null);
        }

        final int compare(int k1, int k2) {
            return this.comparator == null ? Integer.compare(k1, k2) : this.comparator.compare(k1, k2);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntSortedMap, java.util.SortedMap
        public IntComparator comparator() {
            return this.comparator;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMaps.Singleton, com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap
        public ObjectSortedSet<Int2IntMap.Entry> int2IntEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.singleton(new AbstractInt2IntMap.BasicEntry(this.key, this.value), Int2IntSortedMaps.entryComparator(this.comparator));
            }
            return (ObjectSortedSet) this.entries;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMaps.Singleton, com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
        @Deprecated
        public Set<Map.Entry<Integer, Integer>> entrySet() {
            return int2IntEntrySet();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMaps.Singleton, com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
        public IntSortedSet keySet() {
            if (this.keys == null) {
                this.keys = IntSortedSets.singleton(this.key, this.comparator);
            }
            return (IntSortedSet) this.keys;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntSortedMap
        public Int2IntSortedMap subMap(int from, int to) {
            if (compare(from, this.key) <= 0 && compare(this.key, to) < 0) {
                return this;
            }
            return Int2IntSortedMaps.EMPTY_MAP;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntSortedMap
        public Int2IntSortedMap headMap(int to) {
            if (compare(this.key, to) < 0) {
                return this;
            }
            return Int2IntSortedMaps.EMPTY_MAP;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntSortedMap
        public Int2IntSortedMap tailMap(int from) {
            if (compare(from, this.key) <= 0) {
                return this;
            }
            return Int2IntSortedMaps.EMPTY_MAP;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntSortedMap
        public int firstIntKey() {
            return this.key;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntSortedMap
        public int lastIntKey() {
            return this.key;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntSortedMap
        @Deprecated
        public Int2IntSortedMap headMap(Integer oto) {
            return headMap(oto.intValue());
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntSortedMap
        @Deprecated
        public Int2IntSortedMap tailMap(Integer ofrom) {
            return tailMap(ofrom.intValue());
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntSortedMap
        @Deprecated
        public Int2IntSortedMap subMap(Integer ofrom, Integer oto) {
            return subMap(ofrom.intValue(), oto.intValue());
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntSortedMap, java.util.SortedMap
        @Deprecated
        public Integer firstKey() {
            return Integer.valueOf(firstIntKey());
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntSortedMap, java.util.SortedMap
        @Deprecated
        public Integer lastKey() {
            return Integer.valueOf(lastIntKey());
        }
    }

    public static Int2IntSortedMap singleton(Integer key, Integer value) {
        return new Singleton(key.intValue(), value.intValue());
    }

    public static Int2IntSortedMap singleton(Integer key, Integer value, IntComparator comparator) {
        return new Singleton(key.intValue(), value.intValue(), comparator);
    }

    public static Int2IntSortedMap singleton(int key, int value) {
        return new Singleton(key, value);
    }

    public static Int2IntSortedMap singleton(int key, int value, IntComparator comparator) {
        return new Singleton(key, value, comparator);
    }

    public static Int2IntSortedMap synchronize(Int2IntSortedMap m) {
        return new SynchronizedSortedMap(m);
    }

    public static Int2IntSortedMap synchronize(Int2IntSortedMap m, Object sync) {
        return new SynchronizedSortedMap(m, sync);
    }

    public static Int2IntSortedMap unmodifiable(Int2IntSortedMap m) {
        return new UnmodifiableSortedMap(m);
    }
}
