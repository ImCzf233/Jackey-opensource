package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2IntMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunctions;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSets;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2IntMaps.class */
public final class Int2IntMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Int2IntMaps() {
    }

    public static ObjectIterator<Int2IntMap.Entry> fastIterator(Int2IntMap map) {
        ObjectSet<Int2IntMap.Entry> entries = map.int2IntEntrySet();
        if (entries instanceof Int2IntMap.FastEntrySet) {
            return ((Int2IntMap.FastEntrySet) entries).fastIterator();
        }
        return entries.iterator();
    }

    public static void fastForEach(Int2IntMap map, Consumer<? super Int2IntMap.Entry> consumer) {
        ObjectSet<Int2IntMap.Entry> entries = map.int2IntEntrySet();
        if (entries instanceof Int2IntMap.FastEntrySet) {
            ((Int2IntMap.FastEntrySet) entries).fastForEach(consumer);
        } else {
            entries.forEach(consumer);
        }
    }

    public static ObjectIterable<Int2IntMap.Entry> fastIterable(Int2IntMap map) {
        final ObjectSet<Int2IntMap.Entry> entries = map.int2IntEntrySet();
        return entries instanceof Int2IntMap.FastEntrySet ? new ObjectIterable<Int2IntMap.Entry>() { // from class: com.viaversion.viaversion.libs.fastutil.ints.Int2IntMaps.1
            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection
            public ObjectIterator<Int2IntMap.Entry> iterator() {
                return ((Int2IntMap.FastEntrySet) entries).fastIterator();
            }

            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectSet, java.util.Set
            public ObjectSpliterator<Int2IntMap.Entry> spliterator() {
                return entries.spliterator();
            }

            @Override // java.lang.Iterable
            public void forEach(Consumer<? super Int2IntMap.Entry> consumer) {
                ((Int2IntMap.FastEntrySet) entries).fastForEach(consumer);
            }
        } : entries;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2IntMaps$EmptyMap.class */
    public static class EmptyMap extends Int2IntFunctions.EmptyFunction implements Int2IntMap, Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap
        public boolean containsValue(int v) {
            return false;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction
        @Deprecated
        public Integer getOrDefault(Object key, Integer defaultValue) {
            return defaultValue;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunctions.EmptyFunction, com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction
        public int getOrDefault(int key, int defaultValue) {
            return defaultValue;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
        @Deprecated
        public boolean containsValue(Object ov) {
            return false;
        }

        @Override // java.util.Map
        public void putAll(Map<? extends Integer, ? extends Integer> m) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap
        public ObjectSet<Int2IntMap.Entry> int2IntEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
        public IntSet keySet() {
            return IntSets.EMPTY_SET;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
        public IntCollection values() {
            return IntSets.EMPTY_SET;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
        public void forEach(BiConsumer<? super Integer, ? super Integer> consumer) {
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunctions.EmptyFunction
        public Object clone() {
            return Int2IntMaps.EMPTY_MAP;
        }

        @Override // java.util.Map
        public boolean isEmpty() {
            return true;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunctions.EmptyFunction, java.util.Map
        public int hashCode() {
            return 0;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunctions.EmptyFunction, java.util.Map
        public boolean equals(Object o) {
            if (!(o instanceof Map)) {
                return false;
            }
            return ((Map) o).isEmpty();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunctions.EmptyFunction
        public String toString() {
            return "{}";
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2IntMaps$Singleton.class */
    public static class Singleton extends Int2IntFunctions.Singleton implements Int2IntMap, Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Int2IntMap.Entry> entries;
        protected transient IntSet keys;
        protected transient IntCollection values;

        public Singleton(int key, int value) {
            super(key, value);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap
        public boolean containsValue(int v) {
            return this.value == v;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
        @Deprecated
        public boolean containsValue(Object ov) {
            return ((Integer) ov).intValue() == this.value;
        }

        @Override // java.util.Map
        public void putAll(Map<? extends Integer, ? extends Integer> m) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap
        public ObjectSet<Int2IntMap.Entry> int2IntEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new AbstractInt2IntMap.BasicEntry(this.key, this.value));
            }
            return this.entries;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
        @Deprecated
        /* renamed from: entrySet */
        public Set<Map.Entry<Integer, Integer>> entrySet2() {
            return int2IntEntrySet();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
        public IntSet keySet() {
            if (this.keys == null) {
                this.keys = IntSets.singleton(this.key);
            }
            return this.keys;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
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
            return this.key ^ this.value;
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
            return m.entrySet().iterator().next().equals(entrySet2().iterator().next());
        }

        public String toString() {
            return "{" + this.key + "=>" + this.value + "}";
        }
    }

    public static Int2IntMap singleton(int key, int value) {
        return new Singleton(key, value);
    }

    public static Int2IntMap singleton(Integer key, Integer value) {
        return new Singleton(key.intValue(), value.intValue());
    }

    public static Int2IntMap synchronize(Int2IntMap m) {
        return new SynchronizedMap(m);
    }

    public static Int2IntMap synchronize(Int2IntMap m, Object sync) {
        return new SynchronizedMap(m, sync);
    }

    public static Int2IntMap unmodifiable(Int2IntMap m) {
        return new UnmodifiableMap(m);
    }
}
