package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Size64;
import com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntBinaryOperator;
import com.viaversion.viaversion.libs.fastutil.ints.IntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/AbstractObject2IntMap.class */
public abstract class AbstractObject2IntMap<K> extends AbstractObject2IntFunction<K> implements Object2IntMap<K>, Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    @Override // com.viaversion.viaversion.libs.fastutil.Function
    public boolean containsKey(Object k) {
        ObjectIterator<Object2IntMap.Entry<K>> i = object2IntEntrySet().iterator();
        while (i.hasNext()) {
            if (i.next().getKey() == k) {
                return true;
            }
        }
        return false;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap
    public boolean containsValue(int v) {
        ObjectIterator<Object2IntMap.Entry<K>> i = object2IntEntrySet().iterator();
        while (i.hasNext()) {
            if (i.next().getIntValue() == v) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap
    public final int mergeInt(K key, int value, IntBinaryOperator remappingFunction) {
        return mergeInt((AbstractObject2IntMap<K>) key, value, (java.util.function.IntBinaryOperator) remappingFunction);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/AbstractObject2IntMap$BasicEntry.class */
    public static class BasicEntry<K> implements Object2IntMap.Entry<K> {
        protected K key;
        protected int value;

        public BasicEntry() {
        }

        public BasicEntry(K key, Integer value) {
            this.key = key;
            this.value = value.intValue();
        }

        public BasicEntry(K key, int value) {
            this.key = key;
            this.value = value;
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            return this.key;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap.Entry
        public int getIntValue() {
            return this.value;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap.Entry
        public int setValue(int value) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            if (o instanceof Object2IntMap.Entry) {
                Object2IntMap.Entry<K> e = (Object2IntMap.Entry) o;
                return Objects.equals(this.key, e.getKey()) && this.value == e.getIntValue();
            }
            Map.Entry<?, ?> e2 = (Map.Entry) o;
            Object key = e2.getKey();
            Object value = e2.getValue();
            return value != null && (value instanceof Integer) && Objects.equals(this.key, key) && this.value == ((Integer) value).intValue();
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            return (this.key == null ? 0 : this.key.hashCode()) ^ this.value;
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/AbstractObject2IntMap$BasicEntrySet.class */
    public static abstract class BasicEntrySet<K> extends AbstractObjectSet<Object2IntMap.Entry<K>> {
        protected final Object2IntMap<K> map;

        public BasicEntrySet(Object2IntMap<K> map) {
            this.map = map;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            if (o instanceof Object2IntMap.Entry) {
                Object2IntMap.Entry<K> e = (Object2IntMap.Entry) o;
                K k = e.getKey();
                return this.map.containsKey(k) && this.map.getInt(k) == e.getIntValue();
            }
            Map.Entry<?, ?> e2 = (Map.Entry) o;
            Object k2 = e2.getKey();
            Object value = e2.getValue();
            return value != null && (value instanceof Integer) && this.map.containsKey(k2) && this.map.getInt(k2) == ((Integer) value).intValue();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            if (o instanceof Object2IntMap.Entry) {
                Object2IntMap.Entry<K> e = (Object2IntMap.Entry) o;
                return this.map.remove(e.getKey(), e.getIntValue());
            }
            Map.Entry<?, ?> e2 = (Map.Entry) o;
            Object k = e2.getKey();
            Object value = e2.getValue();
            if (value == null || !(value instanceof Integer)) {
                return false;
            }
            int v = ((Integer) value).intValue();
            return this.map.remove(k, v);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return this.map.size();
        }

        @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectSet, java.util.Set
        public ObjectSpliterator<Object2IntMap.Entry<K>> spliterator() {
            return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(this.map), 65);
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap, java.util.Map
    public ObjectSet<K> keySet() {
        return new AbstractObjectSet<K>() { // from class: com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2IntMap.1
            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(Object k) {
                return AbstractObject2IntMap.this.containsKey(k);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return AbstractObject2IntMap.this.size();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public void clear() {
                AbstractObject2IntMap.this.clear();
            }

            @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet, com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable
            public ObjectIterator<K> iterator() {
                return new ObjectIterator<K>() { // from class: com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2IntMap.1.1

                    /* renamed from: i */
                    private final ObjectIterator<Object2IntMap.Entry<K>> f121i;

                    {
                        C04601.this = this;
                        this.f121i = Object2IntMaps.fastIterator(AbstractObject2IntMap.this);
                    }

                    @Override // java.util.Iterator
                    public K next() {
                        return this.f121i.next().getKey();
                    }

                    @Override // java.util.Iterator
                    public boolean hasNext() {
                        return this.f121i.hasNext();
                    }

                    @Override // java.util.Iterator
                    public void remove() {
                        this.f121i.remove();
                    }

                    @Override // java.util.Iterator
                    public void forEachRemaining(Consumer<? super K> action) {
                        this.f121i.forEachRemaining(entry -> {
                            action.accept(entry.getKey());
                        });
                    }
                };
            }

            @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectSet, java.util.Set
            public ObjectSpliterator<K> spliterator() {
                return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractObject2IntMap.this), 65);
            }
        };
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap, java.util.Map
    public IntCollection values() {
        return new AbstractIntCollection() { // from class: com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2IntMap.2
            @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
            public boolean contains(int k) {
                return AbstractObject2IntMap.this.containsValue(k);
            }

            @Override // java.util.AbstractCollection, java.util.Collection
            public int size() {
                return AbstractObject2IntMap.this.size();
            }

            @Override // java.util.AbstractCollection, java.util.Collection
            public void clear() {
                AbstractObject2IntMap.this.clear();
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
            /* renamed from: iterator */
            public Iterator<Integer> iterator2() {
                return new IntIterator() { // from class: com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2IntMap.2.1

                    /* renamed from: i */
                    private final ObjectIterator<Object2IntMap.Entry<K>> f122i;

                    {
                        C04622.this = this;
                        this.f122i = Object2IntMaps.fastIterator(AbstractObject2IntMap.this);
                    }

                    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
                    public int nextInt() {
                        return this.f122i.next().getIntValue();
                    }

                    @Override // java.util.Iterator
                    public boolean hasNext() {
                        return this.f122i.hasNext();
                    }

                    @Override // java.util.Iterator
                    public void remove() {
                        this.f122i.remove();
                    }

                    @Override // java.util.PrimitiveIterator.OfInt
                    public void forEachRemaining(IntConsumer action) {
                        this.f122i.forEachRemaining(entry -> {
                            action.accept(entry.getIntValue());
                        });
                    }
                };
            }

            /* JADX WARN: Type inference failed for: r0v1, types: [com.viaversion.viaversion.libs.fastutil.ints.IntIterator] */
            @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
            /* renamed from: spliterator */
            public Spliterator<Integer> spliterator2() {
                return IntSpliterators.asSpliterator(iterator2(), Size64.sizeOf(AbstractObject2IntMap.this), 320);
            }
        };
    }

    @Override // java.util.Map
    public void putAll(Map<? extends K, ? extends Integer> m) {
        if (m instanceof Object2IntMap) {
            ObjectIterator<Object2IntMap.Entry<K>> i = Object2IntMaps.fastIterator((Object2IntMap) m);
            while (i.hasNext()) {
                Object2IntMap.Entry<? extends K> e = (Object2IntMap.Entry) i.next();
                put((AbstractObject2IntMap<K>) e.getKey(), e.getIntValue());
            }
            return;
        }
        int n = m.size();
        Iterator<? extends Map.Entry<? extends K, ? extends Integer>> i2 = m.entrySet().iterator();
        while (true) {
            int i3 = n;
            n--;
            if (i3 != 0) {
                Map.Entry<? extends K, ? extends Integer> e2 = i2.next();
                put2((AbstractObject2IntMap<K>) e2.getKey(), e2.getValue());
            } else {
                return;
            }
        }
    }

    @Override // java.util.Map
    public int hashCode() {
        int h = 0;
        int n = size();
        ObjectIterator<Object2IntMap.Entry<K>> i = Object2IntMaps.fastIterator(this);
        while (true) {
            int i2 = n;
            n--;
            if (i2 != 0) {
                h += ((Object2IntMap.Entry) i.next()).hashCode();
            } else {
                return h;
            }
        }
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
        if (m.size() != size()) {
            return false;
        }
        return object2IntEntrySet().containsAll(m.entrySet());
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        ObjectIterator<Object2IntMap.Entry<K>> i = Object2IntMaps.fastIterator(this);
        int n = size();
        boolean first = true;
        s.append("{");
        while (true) {
            int i2 = n;
            n--;
            if (i2 != 0) {
                if (first) {
                    first = false;
                } else {
                    s.append(", ");
                }
                Object2IntMap.Entry<K> e = i.next();
                if (this == e.getKey()) {
                    s.append("(this map)");
                } else {
                    s.append(String.valueOf(e.getKey()));
                }
                s.append("=>");
                s.append(String.valueOf(e.getIntValue()));
            } else {
                s.append("}");
                return s.toString();
            }
        }
    }
}
