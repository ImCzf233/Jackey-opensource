package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2ObjectArrayMap.class */
public class Object2ObjectArrayMap<K, V> extends AbstractObject2ObjectMap<K, V> implements Serializable, Cloneable {
    private static final long serialVersionUID = 1;
    private transient Object[] key;
    private transient Object[] value;
    private int size;
    private transient Object2ObjectMap.FastEntrySet<K, V> entries;
    private transient ObjectSet<K> keys;
    private transient ObjectCollection<V> values;

    static /* synthetic */ int access$010(Object2ObjectArrayMap x0) {
        int i = x0.size;
        x0.size = i - 1;
        return i;
    }

    public Object2ObjectArrayMap(Object[] key, Object[] value) {
        this.key = key;
        this.value = value;
        this.size = key.length;
        if (key.length != value.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
        }
    }

    public Object2ObjectArrayMap() {
        this.key = ObjectArrays.EMPTY_ARRAY;
        this.value = ObjectArrays.EMPTY_ARRAY;
    }

    public Object2ObjectArrayMap(int capacity) {
        this.key = new Object[capacity];
        this.value = new Object[capacity];
    }

    public Object2ObjectArrayMap(Object2ObjectMap<K, V> m) {
        this(m.size());
        int i = 0;
        ObjectIterator<Object2ObjectMap.Entry<K, V>> it = m.object2ObjectEntrySet().iterator();
        while (it.hasNext()) {
            Object2ObjectMap.Entry<K, V> e = it.next();
            this.key[i] = e.getKey();
            this.value[i] = e.getValue();
            i++;
        }
        this.size = i;
    }

    public Object2ObjectArrayMap(Map<? extends K, ? extends V> m) {
        this(m.size());
        int i = 0;
        for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
            this.key[i] = e.getKey();
            this.value[i] = e.getValue();
            i++;
        }
        this.size = i;
    }

    public Object2ObjectArrayMap(Object[] key, Object[] value, int size) {
        this.key = key;
        this.value = value;
        this.size = size;
        if (key.length != value.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
        }
        if (size > key.length) {
            throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")");
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2ObjectArrayMap$EntrySet.class */
    public final class EntrySet extends AbstractObjectSet<Object2ObjectMap.Entry<K, V>> implements Object2ObjectMap.FastEntrySet<K, V> {
        private EntrySet() {
            Object2ObjectArrayMap.this = r4;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet, com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable
        public ObjectIterator<Object2ObjectMap.Entry<K, V>> iterator() {
            return new ObjectIterator<Object2ObjectMap.Entry<K, V>>() { // from class: com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectArrayMap.EntrySet.1
                int curr = -1;
                int next = 0;

                @Override // java.util.Iterator
                public boolean hasNext() {
                    return this.next < Object2ObjectArrayMap.this.size;
                }

                @Override // java.util.Iterator
                public Object2ObjectMap.Entry<K, V> next() {
                    if (hasNext()) {
                        Object[] objArr = Object2ObjectArrayMap.this.key;
                        int i = this.next;
                        this.curr = i;
                        Object obj = objArr[i];
                        Object[] objArr2 = Object2ObjectArrayMap.this.value;
                        int i2 = this.next;
                        this.next = i2 + 1;
                        return new AbstractObject2ObjectMap.BasicEntry(obj, objArr2[i2]);
                    }
                    throw new NoSuchElementException();
                }

                @Override // java.util.Iterator
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int access$010 = Object2ObjectArrayMap.access$010(Object2ObjectArrayMap.this);
                    int i = this.next;
                    this.next = i - 1;
                    int tail = access$010 - i;
                    System.arraycopy(Object2ObjectArrayMap.this.key, this.next + 1, Object2ObjectArrayMap.this.key, this.next, tail);
                    System.arraycopy(Object2ObjectArrayMap.this.value, this.next + 1, Object2ObjectArrayMap.this.value, this.next, tail);
                    Object2ObjectArrayMap.this.key[Object2ObjectArrayMap.this.size] = null;
                    Object2ObjectArrayMap.this.value[Object2ObjectArrayMap.this.size] = null;
                }

                @Override // java.util.Iterator
                public void forEachRemaining(Consumer<? super Object2ObjectMap.Entry<K, V>> action) {
                    int max = Object2ObjectArrayMap.this.size;
                    while (this.next < max) {
                        Object[] objArr = Object2ObjectArrayMap.this.key;
                        int i = this.next;
                        this.curr = i;
                        Object obj = objArr[i];
                        Object[] objArr2 = Object2ObjectArrayMap.this.value;
                        int i2 = this.next;
                        this.next = i2 + 1;
                        action.accept(new AbstractObject2ObjectMap.BasicEntry(obj, objArr2[i2]));
                    }
                }
            };
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap.FastEntrySet
        public ObjectIterator<Object2ObjectMap.Entry<K, V>> fastIterator() {
            return new ObjectIterator<Object2ObjectMap.Entry<K, V>>() { // from class: com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectArrayMap.EntrySet.2
                int next = 0;
                int curr = -1;
                final AbstractObject2ObjectMap.BasicEntry<K, V> entry = new AbstractObject2ObjectMap.BasicEntry<>();

                @Override // java.util.Iterator
                public boolean hasNext() {
                    return this.next < Object2ObjectArrayMap.this.size;
                }

                @Override // java.util.Iterator
                public Object2ObjectMap.Entry<K, V> next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    AbstractObject2ObjectMap.BasicEntry<K, V> basicEntry = this.entry;
                    Object[] objArr = Object2ObjectArrayMap.this.key;
                    int i = this.next;
                    this.curr = i;
                    basicEntry.key = (K) objArr[i];
                    AbstractObject2ObjectMap.BasicEntry<K, V> basicEntry2 = this.entry;
                    Object[] objArr2 = Object2ObjectArrayMap.this.value;
                    int i2 = this.next;
                    this.next = i2 + 1;
                    basicEntry2.value = (V) objArr2[i2];
                    return this.entry;
                }

                @Override // java.util.Iterator
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int access$010 = Object2ObjectArrayMap.access$010(Object2ObjectArrayMap.this);
                    int i = this.next;
                    this.next = i - 1;
                    int tail = access$010 - i;
                    System.arraycopy(Object2ObjectArrayMap.this.key, this.next + 1, Object2ObjectArrayMap.this.key, this.next, tail);
                    System.arraycopy(Object2ObjectArrayMap.this.value, this.next + 1, Object2ObjectArrayMap.this.value, this.next, tail);
                    Object2ObjectArrayMap.this.key[Object2ObjectArrayMap.this.size] = null;
                    Object2ObjectArrayMap.this.value[Object2ObjectArrayMap.this.size] = null;
                }

                @Override // java.util.Iterator
                public void forEachRemaining(Consumer<? super Object2ObjectMap.Entry<K, V>> action) {
                    int max = Object2ObjectArrayMap.this.size;
                    while (this.next < max) {
                        AbstractObject2ObjectMap.BasicEntry<K, V> basicEntry = this.entry;
                        Object[] objArr = Object2ObjectArrayMap.this.key;
                        int i = this.next;
                        this.curr = i;
                        basicEntry.key = (K) objArr[i];
                        AbstractObject2ObjectMap.BasicEntry<K, V> basicEntry2 = this.entry;
                        Object[] objArr2 = Object2ObjectArrayMap.this.value;
                        int i2 = this.next;
                        this.next = i2 + 1;
                        basicEntry2.value = (V) objArr2[i2];
                        action.accept(this.entry);
                    }
                }
            };
        }

        /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2ObjectArrayMap$EntrySet$EntrySetSpliterator.class */
        public final class EntrySetSpliterator extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator<Object2ObjectMap.Entry<K, V>> implements ObjectSpliterator<Object2ObjectMap.Entry<K, V>> {
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            EntrySetSpliterator(int pos, int maxPos) {
                super(pos, maxPos);
                EntrySet.this = this$1;
            }

            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators.AbstractIndexBasedSpliterator, java.util.Spliterator
            public int characteristics() {
                return 16465;
            }

            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators.AbstractIndexBasedSpliterator
            public final Object2ObjectMap.Entry<K, V> get(int location) {
                return new AbstractObject2ObjectMap.BasicEntry(Object2ObjectArrayMap.this.key[location], Object2ObjectArrayMap.this.value[location]);
            }

            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators.AbstractIndexBasedSpliterator
            public final Object2ObjectArrayMap<K, V>.EntrySet.EntrySetSpliterator makeForSplit(int pos, int maxPos) {
                return new EntrySetSpliterator(pos, maxPos);
            }
        }

        @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectSet, java.util.Set
        public ObjectSpliterator<Object2ObjectMap.Entry<K, V>> spliterator() {
            return new EntrySetSpliterator(0, Object2ObjectArrayMap.this.size);
        }

        @Override // java.lang.Iterable
        public void forEach(Consumer<? super Object2ObjectMap.Entry<K, V>> action) {
            int max = Object2ObjectArrayMap.this.size;
            for (int i = 0; i < max; i++) {
                action.accept(new AbstractObject2ObjectMap.BasicEntry(Object2ObjectArrayMap.this.key[i], Object2ObjectArrayMap.this.value[i]));
            }
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap.FastEntrySet
        public void fastForEach(Consumer<? super Object2ObjectMap.Entry<K, V>> action) {
            AbstractObject2ObjectMap.BasicEntry<K, V> entry = new AbstractObject2ObjectMap.BasicEntry<>();
            int max = Object2ObjectArrayMap.this.size;
            for (int i = 0; i < max; i++) {
                entry.key = (K) Object2ObjectArrayMap.this.key[i];
                entry.value = (V) Object2ObjectArrayMap.this.value[i];
                action.accept(entry);
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return Object2ObjectArrayMap.this.size;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<?, ?> e = (Map.Entry) o;
            Object key = e.getKey();
            return Object2ObjectArrayMap.this.containsKey(key) && Objects.equals(Object2ObjectArrayMap.this.get(key), e.getValue());
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<?, ?> e = (Map.Entry) o;
            Object key = e.getKey();
            Object value = e.getValue();
            int oldPos = Object2ObjectArrayMap.this.findKey(key);
            if (oldPos != -1 && Objects.equals(value, Object2ObjectArrayMap.this.value[oldPos])) {
                int tail = (Object2ObjectArrayMap.this.size - oldPos) - 1;
                System.arraycopy(Object2ObjectArrayMap.this.key, oldPos + 1, Object2ObjectArrayMap.this.key, oldPos, tail);
                System.arraycopy(Object2ObjectArrayMap.this.value, oldPos + 1, Object2ObjectArrayMap.this.value, oldPos, tail);
                Object2ObjectArrayMap.access$010(Object2ObjectArrayMap.this);
                Object2ObjectArrayMap.this.key[Object2ObjectArrayMap.this.size] = null;
                Object2ObjectArrayMap.this.value[Object2ObjectArrayMap.this.size] = null;
                return true;
            }
            return false;
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap
    public Object2ObjectMap.FastEntrySet<K, V> object2ObjectEntrySet() {
        if (this.entries == null) {
            this.entries = new EntrySet();
        }
        return this.entries;
    }

    public int findKey(Object k) {
        Object[] key = this.key;
        int i = this.size;
        do {
            int i2 = i;
            i--;
            if (i2 == 0) {
                return -1;
            }
        } while (!Objects.equals(key[i], k));
        return i;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectFunction, com.viaversion.viaversion.libs.fastutil.Function
    public V get(Object k) {
        Object[] key = this.key;
        int i = this.size;
        do {
            int i2 = i;
            i--;
            if (i2 == 0) {
                return this.defRetValue;
            }
        } while (!Objects.equals(key[i], k));
        return (V) this.value[i];
    }

    @Override // com.viaversion.viaversion.libs.fastutil.Function, com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
    public int size() {
        return this.size;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.Function, com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
    public void clear() {
        int i = this.size;
        while (true) {
            int i2 = i;
            i--;
            if (i2 != 0) {
                this.key[i] = null;
                this.value[i] = null;
            } else {
                this.size = 0;
                return;
            }
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2ObjectMap, com.viaversion.viaversion.libs.fastutil.Function
    public boolean containsKey(Object k) {
        return findKey(k) != -1;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2ObjectMap, java.util.Map
    public boolean containsValue(Object v) {
        int i = this.size;
        do {
            int i2 = i;
            i--;
            if (i2 == 0) {
                return false;
            }
        } while (!Objects.equals(this.value[i], v));
        return true;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2ObjectMap, java.util.Map
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectFunction, com.viaversion.viaversion.libs.fastutil.Function
    public V put(K k, V v) {
        int oldKey = findKey(k);
        if (oldKey != -1) {
            V oldValue = (V) this.value[oldKey];
            this.value[oldKey] = v;
            return oldValue;
        }
        if (this.size == this.key.length) {
            Object[] newKey = new Object[this.size == 0 ? 2 : this.size * 2];
            Object[] newValue = new Object[this.size == 0 ? 2 : this.size * 2];
            int i = this.size;
            while (true) {
                int i2 = i;
                i--;
                if (i2 == 0) {
                    break;
                }
                newKey[i] = this.key[i];
                newValue[i] = this.value[i];
            }
            this.key = newKey;
            this.value = newValue;
        }
        this.key[this.size] = k;
        this.value[this.size] = v;
        this.size++;
        return this.defRetValue;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectFunction, com.viaversion.viaversion.libs.fastutil.Function
    public V remove(Object k) {
        int oldPos = findKey(k);
        if (oldPos == -1) {
            return this.defRetValue;
        }
        V oldValue = (V) this.value[oldPos];
        int tail = (this.size - oldPos) - 1;
        System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
        System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
        this.size--;
        this.key[this.size] = null;
        this.value[this.size] = null;
        return oldValue;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2ObjectArrayMap$KeySet.class */
    public final class KeySet extends AbstractObjectSet<K> {
        private KeySet() {
            Object2ObjectArrayMap.this = r4;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object k) {
            return Object2ObjectArrayMap.this.findKey(k) != -1;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object k) {
            int oldPos = Object2ObjectArrayMap.this.findKey(k);
            if (oldPos != -1) {
                int tail = (Object2ObjectArrayMap.this.size - oldPos) - 1;
                System.arraycopy(Object2ObjectArrayMap.this.key, oldPos + 1, Object2ObjectArrayMap.this.key, oldPos, tail);
                System.arraycopy(Object2ObjectArrayMap.this.value, oldPos + 1, Object2ObjectArrayMap.this.value, oldPos, tail);
                Object2ObjectArrayMap.access$010(Object2ObjectArrayMap.this);
                Object2ObjectArrayMap.this.key[Object2ObjectArrayMap.this.size] = null;
                Object2ObjectArrayMap.this.value[Object2ObjectArrayMap.this.size] = null;
                return true;
            }
            return false;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet, com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable
        public ObjectIterator<K> iterator() {
            return new ObjectIterator<K>() { // from class: com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectArrayMap.KeySet.1
                int pos = 0;

                @Override // java.util.Iterator
                public boolean hasNext() {
                    return this.pos < Object2ObjectArrayMap.this.size;
                }

                @Override // java.util.Iterator
                public K next() {
                    if (hasNext()) {
                        Object[] objArr = Object2ObjectArrayMap.this.key;
                        int i = this.pos;
                        this.pos = i + 1;
                        return (K) objArr[i];
                    }
                    throw new NoSuchElementException();
                }

                @Override // java.util.Iterator
                public void remove() {
                    if (this.pos != 0) {
                        int tail = Object2ObjectArrayMap.this.size - this.pos;
                        System.arraycopy(Object2ObjectArrayMap.this.key, this.pos, Object2ObjectArrayMap.this.key, this.pos - 1, tail);
                        System.arraycopy(Object2ObjectArrayMap.this.value, this.pos, Object2ObjectArrayMap.this.value, this.pos - 1, tail);
                        Object2ObjectArrayMap.access$010(Object2ObjectArrayMap.this);
                        this.pos--;
                        Object2ObjectArrayMap.this.key[Object2ObjectArrayMap.this.size] = null;
                        Object2ObjectArrayMap.this.value[Object2ObjectArrayMap.this.size] = null;
                        return;
                    }
                    throw new IllegalStateException();
                }

                @Override // java.util.Iterator
                public void forEachRemaining(Consumer<? super K> action) {
                    int max = Object2ObjectArrayMap.this.size;
                    while (this.pos < max) {
                        Object[] objArr = Object2ObjectArrayMap.this.key;
                        int i = this.pos;
                        this.pos = i + 1;
                        action.accept(objArr[i]);
                    }
                }
            };
        }

        /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2ObjectArrayMap$KeySet$KeySetSpliterator.class */
        public final class KeySetSpliterator extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator<K> implements ObjectSpliterator<K> {
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            KeySetSpliterator(int pos, int maxPos) {
                super(pos, maxPos);
                KeySet.this = this$1;
            }

            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators.AbstractIndexBasedSpliterator, java.util.Spliterator
            public int characteristics() {
                return 16465;
            }

            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators.AbstractIndexBasedSpliterator
            protected final K get(int location) {
                return (K) Object2ObjectArrayMap.this.key[location];
            }

            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators.AbstractIndexBasedSpliterator
            public final Object2ObjectArrayMap<K, V>.KeySet.KeySetSpliterator makeForSplit(int pos, int maxPos) {
                return new KeySetSpliterator(pos, maxPos);
            }

            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators.AbstractIndexBasedSpliterator, java.util.Spliterator
            public void forEachRemaining(Consumer<? super K> action) {
                int max = Object2ObjectArrayMap.this.size;
                while (this.pos < max) {
                    Object[] objArr = Object2ObjectArrayMap.this.key;
                    int i = this.pos;
                    this.pos = i + 1;
                    action.accept(objArr[i]);
                }
            }
        }

        @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectSet, java.util.Set
        public ObjectSpliterator<K> spliterator() {
            return new KeySetSpliterator(0, Object2ObjectArrayMap.this.size);
        }

        @Override // java.lang.Iterable
        public void forEach(Consumer<? super K> action) {
            int max = Object2ObjectArrayMap.this.size;
            for (int i = 0; i < max; i++) {
                action.accept(Object2ObjectArrayMap.this.key[i]);
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return Object2ObjectArrayMap.this.size;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            Object2ObjectArrayMap.this.clear();
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2ObjectMap, com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap, java.util.Map
    public ObjectSet<K> keySet() {
        if (this.keys == null) {
            this.keys = new KeySet();
        }
        return this.keys;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2ObjectArrayMap$ValuesCollection.class */
    public final class ValuesCollection extends AbstractObjectCollection<V> {
        private ValuesCollection() {
            Object2ObjectArrayMap.this = r4;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean contains(Object v) {
            return Object2ObjectArrayMap.this.containsValue(v);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable
        public ObjectIterator<V> iterator() {
            return new ObjectIterator<V>() { // from class: com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectArrayMap.ValuesCollection.1
                int pos = 0;

                @Override // java.util.Iterator
                public boolean hasNext() {
                    return this.pos < Object2ObjectArrayMap.this.size;
                }

                @Override // java.util.Iterator
                public V next() {
                    if (hasNext()) {
                        Object[] objArr = Object2ObjectArrayMap.this.value;
                        int i = this.pos;
                        this.pos = i + 1;
                        return (V) objArr[i];
                    }
                    throw new NoSuchElementException();
                }

                @Override // java.util.Iterator
                public void remove() {
                    if (this.pos != 0) {
                        int tail = Object2ObjectArrayMap.this.size - this.pos;
                        System.arraycopy(Object2ObjectArrayMap.this.key, this.pos, Object2ObjectArrayMap.this.key, this.pos - 1, tail);
                        System.arraycopy(Object2ObjectArrayMap.this.value, this.pos, Object2ObjectArrayMap.this.value, this.pos - 1, tail);
                        Object2ObjectArrayMap.access$010(Object2ObjectArrayMap.this);
                        this.pos--;
                        Object2ObjectArrayMap.this.key[Object2ObjectArrayMap.this.size] = null;
                        Object2ObjectArrayMap.this.value[Object2ObjectArrayMap.this.size] = null;
                        return;
                    }
                    throw new IllegalStateException();
                }

                @Override // java.util.Iterator
                public void forEachRemaining(Consumer<? super V> action) {
                    int max = Object2ObjectArrayMap.this.size;
                    while (this.pos < max) {
                        Object[] objArr = Object2ObjectArrayMap.this.value;
                        int i = this.pos;
                        this.pos = i + 1;
                        action.accept(objArr[i]);
                    }
                }
            };
        }

        /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2ObjectArrayMap$ValuesCollection$ValuesSpliterator.class */
        public final class ValuesSpliterator extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator<V> implements ObjectSpliterator<V> {
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            ValuesSpliterator(int pos, int maxPos) {
                super(pos, maxPos);
                ValuesCollection.this = this$1;
            }

            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators.AbstractIndexBasedSpliterator, java.util.Spliterator
            public int characteristics() {
                return ObjectSpliterators.LIST_SPLITERATOR_CHARACTERISTICS;
            }

            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators.AbstractIndexBasedSpliterator
            protected final V get(int location) {
                return (V) Object2ObjectArrayMap.this.value[location];
            }

            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators.AbstractIndexBasedSpliterator
            public final Object2ObjectArrayMap<K, V>.ValuesCollection.ValuesSpliterator makeForSplit(int pos, int maxPos) {
                return new ValuesSpliterator(pos, maxPos);
            }

            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators.AbstractIndexBasedSpliterator, java.util.Spliterator
            public void forEachRemaining(Consumer<? super V> action) {
                int max = Object2ObjectArrayMap.this.size;
                while (this.pos < max) {
                    Object[] objArr = Object2ObjectArrayMap.this.value;
                    int i = this.pos;
                    this.pos = i + 1;
                    action.accept(objArr[i]);
                }
            }
        }

        @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectSet, java.util.Set
        public ObjectSpliterator<V> spliterator() {
            return new ValuesSpliterator(0, Object2ObjectArrayMap.this.size);
        }

        @Override // java.lang.Iterable
        public void forEach(Consumer<? super V> action) {
            int max = Object2ObjectArrayMap.this.size;
            for (int i = 0; i < max; i++) {
                action.accept(Object2ObjectArrayMap.this.value[i]);
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            return Object2ObjectArrayMap.this.size;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public void clear() {
            Object2ObjectArrayMap.this.clear();
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2ObjectMap, com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectMap, java.util.Map
    public ObjectCollection<V> values() {
        if (this.values == null) {
            this.values = new ValuesCollection();
        }
        return this.values;
    }

    public Object2ObjectArrayMap<K, V> clone() {
        try {
            Object2ObjectArrayMap<K, V> c = (Object2ObjectArrayMap) super.clone();
            c.key = (Object[]) this.key.clone();
            c.value = (Object[]) this.value.clone();
            c.entries = null;
            c.keys = null;
            c.values = null;
            return c;
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        int max = this.size;
        for (int i = 0; i < max; i++) {
            s.writeObject(this.key[i]);
            s.writeObject(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.key = new Object[this.size];
        this.value = new Object[this.size];
        for (int i = 0; i < this.size; i++) {
            this.key[i] = s.readObject();
            this.value[i] = s.readObject();
        }
    }
}
