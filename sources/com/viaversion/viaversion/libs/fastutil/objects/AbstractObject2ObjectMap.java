package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Size64;
import com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/AbstractObject2ObjectMap.class */
public abstract class AbstractObject2ObjectMap<K, V> extends AbstractObject2ObjectFunction<K, V> implements Object2ObjectMap<K, V>, Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    @Override // com.viaversion.viaversion.libs.fastutil.Function
    public boolean containsKey(Object k) {
        ObjectIterator<Object2ObjectMap.Entry<K, V>> i = object2ObjectEntrySet().iterator();
        while (i.hasNext()) {
            if (i.next().getKey() == k) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.Map
    public boolean containsValue(Object v) {
        ObjectIterator<Object2ObjectMap.Entry<K, V>> i = object2ObjectEntrySet().iterator();
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

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/AbstractObject2ObjectMap$BasicEntry.class */
    public static class BasicEntry<K, V> implements Object2ObjectMap.Entry<K, V> {
        protected K key;
        protected V value;

        public BasicEntry() {
        }

        public BasicEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override // java.util.Map.Entry
        public K getKey() {
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
            if (o instanceof Object2ObjectMap.Entry) {
                Object2ObjectMap.Entry<K, V> e = (Object2ObjectMap.Entry) o;
                return Objects.equals(this.key, e.getKey()) && Objects.equals(this.value, e.getValue());
            }
            Map.Entry<?, ?> e2 = (Map.Entry) o;
            Object key = e2.getKey();
            Object value = e2.getValue();
            return Objects.equals(this.key, key) && Objects.equals(this.value, value);
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            return (this.key == null ? 0 : this.key.hashCode()) ^ (this.value == null ? 0 : this.value.hashCode());
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/AbstractObject2ObjectMap$BasicEntrySet.class */
    public static abstract class BasicEntrySet<K, V> extends AbstractObjectSet<Object2ObjectMap.Entry<K, V>> {
        protected final Object2ObjectMap<K, V> map;

        public BasicEntrySet(Object2ObjectMap<K, V> map) {
            this.map = map;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            if (o instanceof Object2ObjectMap.Entry) {
                Object2ObjectMap.Entry<K, V> e = (Object2ObjectMap.Entry) o;
                K k = e.getKey();
                return this.map.containsKey(k) && Objects.equals(this.map.get(k), e.getValue());
            }
            Map.Entry<?, ?> e2 = (Map.Entry) o;
            Object k2 = e2.getKey();
            Object value = e2.getValue();
            return this.map.containsKey(k2) && Objects.equals(this.map.get(k2), value);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            if (o instanceof Object2ObjectMap.Entry) {
                Object2ObjectMap.Entry<K, V> e = (Object2ObjectMap.Entry) o;
                return this.map.remove(e.getKey(), e.getValue());
            }
            Map.Entry<?, ?> e2 = (Map.Entry) o;
            Object k = e2.getKey();
            Object v = e2.getValue();
            return this.map.remove(k, v);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return this.map.size();
        }

        @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectSet, java.util.Set
        public ObjectSpliterator<Object2ObjectMap.Entry<K, V>> spliterator() {
            return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(this.map), 65);
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap, java.util.Map
    public ObjectSet<K> keySet() {
        return new AbstractObjectSet<K>() { // from class: com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2ObjectMap.1
            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(Object k) {
                return AbstractObject2ObjectMap.this.containsKey(k);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return AbstractObject2ObjectMap.this.size();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public void clear() {
                AbstractObject2ObjectMap.this.clear();
            }

            @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet, com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable
            public ObjectIterator<K> iterator() {
                return new ObjectIterator<K>() { // from class: com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2ObjectMap.1.1

                    /* renamed from: i */
                    private final ObjectIterator<Object2ObjectMap.Entry<K, V>> f125i;

                    {
                        C04641.this = this;
                        this.f125i = Object2ObjectMaps.fastIterator(AbstractObject2ObjectMap.this);
                    }

                    @Override // java.util.Iterator
                    public K next() {
                        return this.f125i.next().getKey();
                    }

                    @Override // java.util.Iterator
                    public boolean hasNext() {
                        return this.f125i.hasNext();
                    }

                    @Override // java.util.Iterator
                    public void remove() {
                        this.f125i.remove();
                    }

                    @Override // java.util.Iterator
                    public void forEachRemaining(Consumer<? super K> action) {
                        this.f125i.forEachRemaining(entry -> {
                            action.accept(entry.getKey());
                        });
                    }
                };
            }

            @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectSet, java.util.Set
            public ObjectSpliterator<K> spliterator() {
                return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractObject2ObjectMap.this), 65);
            }
        };
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap, java.util.Map
    public ObjectCollection<V> values() {
        return new AbstractObjectCollection<V>() { // from class: com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2ObjectMap.2
            @Override // java.util.AbstractCollection, java.util.Collection
            public boolean contains(Object k) {
                return AbstractObject2ObjectMap.this.containsValue(k);
            }

            @Override // java.util.AbstractCollection, java.util.Collection
            public int size() {
                return AbstractObject2ObjectMap.this.size();
            }

            @Override // java.util.AbstractCollection, java.util.Collection
            public void clear() {
                AbstractObject2ObjectMap.this.clear();
            }

            @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable
            public ObjectIterator<V> iterator() {
                return new ObjectIterator<V>() { // from class: com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2ObjectMap.2.1

                    /* renamed from: i */
                    private final ObjectIterator<Object2ObjectMap.Entry<K, V>> f126i;

                    {
                        C04662.this = this;
                        this.f126i = Object2ObjectMaps.fastIterator(AbstractObject2ObjectMap.this);
                    }

                    @Override // java.util.Iterator
                    public V next() {
                        return this.f126i.next().getValue();
                    }

                    @Override // java.util.Iterator
                    public boolean hasNext() {
                        return this.f126i.hasNext();
                    }

                    @Override // java.util.Iterator
                    public void remove() {
                        this.f126i.remove();
                    }

                    @Override // java.util.Iterator
                    public void forEachRemaining(Consumer<? super V> action) {
                        this.f126i.forEachRemaining(entry -> {
                            action.accept(entry.getValue());
                        });
                    }
                };
            }

            @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectSet, java.util.Set
            public ObjectSpliterator<V> spliterator() {
                return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractObject2ObjectMap.this), 64);
            }
        };
    }

    @Override // java.util.Map
    public void putAll(Map<? extends K, ? extends V> m) {
        if (m instanceof Object2ObjectMap) {
            ObjectIterator<Object2ObjectMap.Entry<K, V>> i = Object2ObjectMaps.fastIterator((Object2ObjectMap) m);
            while (i.hasNext()) {
                Object2ObjectMap.Entry<? extends K, ? extends V> e = (Object2ObjectMap.Entry) i.next();
                put(e.getKey(), e.getValue());
            }
            return;
        }
        int n = m.size();
        Iterator<? extends Map.Entry<? extends K, ? extends V>> i2 = m.entrySet().iterator();
        while (true) {
            int i3 = n;
            n--;
            if (i3 != 0) {
                Map.Entry<? extends K, ? extends V> e2 = i2.next();
                put(e2.getKey(), e2.getValue());
            } else {
                return;
            }
        }
    }

    @Override // java.util.Map
    public int hashCode() {
        int h = 0;
        int n = size();
        ObjectIterator<Object2ObjectMap.Entry<K, V>> i = Object2ObjectMaps.fastIterator(this);
        while (true) {
            int i2 = n;
            n--;
            if (i2 != 0) {
                h += ((Object2ObjectMap.Entry) i.next()).hashCode();
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
        return object2ObjectEntrySet().containsAll(m.entrySet());
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        ObjectIterator<Object2ObjectMap.Entry<K, V>> i = Object2ObjectMaps.fastIterator(this);
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
                Object2ObjectMap.Entry<K, V> e = i.next();
                if (this == e.getKey()) {
                    s.append("(this map)");
                } else {
                    s.append(String.valueOf(e.getKey()));
                }
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
