package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Size64;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/AbstractInt2ObjectMap.class */
public abstract class AbstractInt2ObjectMap<V> extends AbstractInt2ObjectFunction<V> implements Int2ObjectMap<V>, Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction, com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap
    public boolean containsKey(int k) {
        ObjectIterator<Int2ObjectMap.Entry<V>> i = int2ObjectEntrySet().iterator();
        while (i.hasNext()) {
            if (i.next().getIntKey() == k) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.Map
    public boolean containsValue(Object v) {
        ObjectIterator<Int2ObjectMap.Entry<V>> i = int2ObjectEntrySet().iterator();
        while (i.hasNext()) {
            if (i.next().getValue() == v) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return size() == 0;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/AbstractInt2ObjectMap$BasicEntry.class */
    public static class BasicEntry<V> implements Int2ObjectMap.Entry<V> {
        protected int key;
        protected V value;

        public BasicEntry() {
        }

        public BasicEntry(Integer key, V value) {
            this.key = key.intValue();
            this.value = value;
        }

        public BasicEntry(int key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap.Entry
        public int getIntKey() {
            return this.key;
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            return this.value;
        }

        @Override // java.util.Map.Entry
        public V setValue(V value) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            if (o instanceof Int2ObjectMap.Entry) {
                Int2ObjectMap.Entry<V> e = (Int2ObjectMap.Entry) o;
                return this.key == e.getIntKey() && Objects.equals(this.value, e.getValue());
            }
            Map.Entry<?, ?> e2 = (Map.Entry) o;
            Object key = e2.getKey();
            if (key == null || !(key instanceof Integer)) {
                return false;
            }
            Object value = e2.getValue();
            return this.key == ((Integer) key).intValue() && Objects.equals(this.value, value);
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            return this.key ^ (this.value == null ? 0 : this.value.hashCode());
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/AbstractInt2ObjectMap$BasicEntrySet.class */
    public static abstract class BasicEntrySet<V> extends AbstractObjectSet<Int2ObjectMap.Entry<V>> {
        protected final Int2ObjectMap<V> map;

        public BasicEntrySet(Int2ObjectMap<V> map) {
            this.map = map;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            if (o instanceof Int2ObjectMap.Entry) {
                Int2ObjectMap.Entry<V> e = (Int2ObjectMap.Entry) o;
                int k = e.getIntKey();
                return this.map.containsKey(k) && Objects.equals(this.map.get(k), e.getValue());
            }
            Map.Entry<?, ?> e2 = (Map.Entry) o;
            Object key = e2.getKey();
            if (key == null || !(key instanceof Integer)) {
                return false;
            }
            int k2 = ((Integer) key).intValue();
            Object value = e2.getValue();
            return this.map.containsKey(k2) && Objects.equals(this.map.get(k2), value);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            if (o instanceof Int2ObjectMap.Entry) {
                Int2ObjectMap.Entry<V> e = (Int2ObjectMap.Entry) o;
                return this.map.remove(e.getIntKey(), e.getValue());
            }
            Map.Entry<?, ?> e2 = (Map.Entry) o;
            Object key = e2.getKey();
            if (key == null || !(key instanceof Integer)) {
                return false;
            }
            int k = ((Integer) key).intValue();
            Object v = e2.getValue();
            return this.map.remove(k, v);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return this.map.size();
        }

        @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectSet, java.util.Set
        public ObjectSpliterator<Int2ObjectMap.Entry<V>> spliterator() {
            return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(this.map), 65);
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap, java.util.Map
    public IntSet keySet() {
        return new AbstractIntSet() { // from class: com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2ObjectMap.1
            @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
            public boolean contains(int k) {
                return AbstractInt2ObjectMap.this.containsKey(k);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return AbstractInt2ObjectMap.this.size();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public void clear() {
                AbstractInt2ObjectMap.this.clear();
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntSet, com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
            /* renamed from: iterator */
            public Iterator<Integer> iterator2() {
                return new IntIterator() { // from class: com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2ObjectMap.1.1

                    /* renamed from: i */
                    private final ObjectIterator<Int2ObjectMap.Entry<V>> f69i;

                    {
                        C04281.this = this;
                        this.f69i = Int2ObjectMaps.fastIterator(AbstractInt2ObjectMap.this);
                    }

                    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
                    public int nextInt() {
                        return this.f69i.next().getIntKey();
                    }

                    @Override // java.util.Iterator
                    public boolean hasNext() {
                        return this.f69i.hasNext();
                    }

                    @Override // java.util.Iterator
                    public void remove() {
                        this.f69i.remove();
                    }

                    @Override // java.util.PrimitiveIterator.OfInt
                    public void forEachRemaining(java.util.function.IntConsumer action) {
                        this.f69i.forEachRemaining(entry -> {
                            action.accept(entry.getIntKey());
                        });
                    }
                };
            }

            /* JADX WARN: Type inference failed for: r0v1, types: [com.viaversion.viaversion.libs.fastutil.ints.IntIterator] */
            @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
            /* renamed from: spliterator */
            public Spliterator<Integer> spliterator2() {
                return IntSpliterators.asSpliterator(iterator2(), Size64.sizeOf(AbstractInt2ObjectMap.this), 321);
            }
        };
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap, java.util.Map
    public ObjectCollection<V> values() {
        return new AbstractObjectCollection<V>() { // from class: com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2ObjectMap.2
            @Override // java.util.AbstractCollection, java.util.Collection
            public boolean contains(Object k) {
                return AbstractInt2ObjectMap.this.containsValue(k);
            }

            @Override // java.util.AbstractCollection, java.util.Collection
            public int size() {
                return AbstractInt2ObjectMap.this.size();
            }

            @Override // java.util.AbstractCollection, java.util.Collection
            public void clear() {
                AbstractInt2ObjectMap.this.clear();
            }

            @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable
            public ObjectIterator<V> iterator() {
                return new ObjectIterator<V>() { // from class: com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2ObjectMap.2.1

                    /* renamed from: i */
                    private final ObjectIterator<Int2ObjectMap.Entry<V>> f70i;

                    {
                        C04302.this = this;
                        this.f70i = Int2ObjectMaps.fastIterator(AbstractInt2ObjectMap.this);
                    }

                    @Override // java.util.Iterator
                    public V next() {
                        return this.f70i.next().getValue();
                    }

                    @Override // java.util.Iterator
                    public boolean hasNext() {
                        return this.f70i.hasNext();
                    }

                    @Override // java.util.Iterator
                    public void remove() {
                        this.f70i.remove();
                    }

                    @Override // java.util.Iterator
                    public void forEachRemaining(Consumer<? super V> action) {
                        this.f70i.forEachRemaining(entry -> {
                            action.accept(entry.getValue());
                        });
                    }
                };
            }

            @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectSet, java.util.Set
            public ObjectSpliterator<V> spliterator() {
                return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractInt2ObjectMap.this), 64);
            }
        };
    }

    @Override // java.util.Map
    public void putAll(Map<? extends Integer, ? extends V> m) {
        if (m instanceof Int2ObjectMap) {
            ObjectIterator<Int2ObjectMap.Entry<V>> i = Int2ObjectMaps.fastIterator((Int2ObjectMap) m);
            while (i.hasNext()) {
                Int2ObjectMap.Entry<? extends V> e = (Int2ObjectMap.Entry) i.next();
                put(e.getIntKey(), (int) e.getValue());
            }
            return;
        }
        int n = m.size();
        Iterator<? extends Map.Entry<? extends Integer, ? extends V>> i2 = m.entrySet().iterator();
        while (true) {
            int i3 = n;
            n--;
            if (i3 != 0) {
                Map.Entry<? extends Integer, ? extends V> e2 = i2.next();
                put2(e2.getKey(), (Integer) e2.getValue());
            } else {
                return;
            }
        }
    }

    @Override // java.util.Map
    public int hashCode() {
        int h = 0;
        int n = size();
        ObjectIterator<Int2ObjectMap.Entry<V>> i = Int2ObjectMaps.fastIterator(this);
        while (true) {
            int i2 = n;
            n--;
            if (i2 != 0) {
                h += ((Int2ObjectMap.Entry) i.next()).hashCode();
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
        return int2ObjectEntrySet().containsAll(m.entrySet());
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        ObjectIterator<Int2ObjectMap.Entry<V>> i = Int2ObjectMaps.fastIterator(this);
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
                Int2ObjectMap.Entry<V> e = i.next();
                s.append(String.valueOf(e.getIntKey()));
                s.append("=>");
                if (this == e.getValue()) {
                    s.append("(this map)");
                } else {
                    s.append(String.valueOf(e.getValue()));
                }
            } else {
                s.append("}");
                return s.toString();
            }
        }
    }
}
