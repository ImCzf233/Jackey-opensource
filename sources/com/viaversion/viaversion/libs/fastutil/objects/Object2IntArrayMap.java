package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntArrays;
import com.viaversion.viaversion.libs.fastutil.ints.IntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2IntArrayMap.class */
public class Object2IntArrayMap<K> extends AbstractObject2IntMap<K> implements Serializable, Cloneable {
    private static final long serialVersionUID = 1;
    private transient Object[] key;
    private transient int[] value;
    private int size;
    private transient Object2IntMap.FastEntrySet<K> entries;
    private transient ObjectSet<K> keys;
    private transient IntCollection values;

    static /* synthetic */ int access$010(Object2IntArrayMap x0) {
        int i = x0.size;
        x0.size = i - 1;
        return i;
    }

    public Object2IntArrayMap(Object[] key, int[] value) {
        this.key = key;
        this.value = value;
        this.size = key.length;
        if (key.length != value.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
        }
    }

    public Object2IntArrayMap() {
        this.key = ObjectArrays.EMPTY_ARRAY;
        this.value = IntArrays.EMPTY_ARRAY;
    }

    public Object2IntArrayMap(int capacity) {
        this.key = new Object[capacity];
        this.value = new int[capacity];
    }

    public Object2IntArrayMap(Object2IntMap<K> m) {
        this(m.size());
        int i = 0;
        ObjectIterator<Object2IntMap.Entry<K>> it = m.object2IntEntrySet().iterator();
        while (it.hasNext()) {
            Object2IntMap.Entry<K> e = it.next();
            this.key[i] = e.getKey();
            this.value[i] = e.getIntValue();
            i++;
        }
        this.size = i;
    }

    public Object2IntArrayMap(Map<? extends K, ? extends Integer> m) {
        this(m.size());
        int i = 0;
        for (Map.Entry<? extends K, ? extends Integer> e : m.entrySet()) {
            this.key[i] = e.getKey();
            this.value[i] = e.getValue().intValue();
            i++;
        }
        this.size = i;
    }

    public Object2IntArrayMap(Object[] key, int[] value, int size) {
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

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2IntArrayMap$EntrySet.class */
    public final class EntrySet extends AbstractObjectSet<Object2IntMap.Entry<K>> implements Object2IntMap.FastEntrySet<K> {
        private EntrySet() {
            Object2IntArrayMap.this = r4;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet, com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable
        public ObjectIterator<Object2IntMap.Entry<K>> iterator() {
            return new ObjectIterator<Object2IntMap.Entry<K>>() { // from class: com.viaversion.viaversion.libs.fastutil.objects.Object2IntArrayMap.EntrySet.1
                int curr = -1;
                int next = 0;

                @Override // java.util.Iterator
                public boolean hasNext() {
                    return this.next < Object2IntArrayMap.this.size;
                }

                @Override // java.util.Iterator
                public Object2IntMap.Entry<K> next() {
                    if (hasNext()) {
                        Object[] objArr = Object2IntArrayMap.this.key;
                        int i = this.next;
                        this.curr = i;
                        Object obj = objArr[i];
                        int[] iArr = Object2IntArrayMap.this.value;
                        int i2 = this.next;
                        this.next = i2 + 1;
                        return new AbstractObject2IntMap.BasicEntry(obj, iArr[i2]);
                    }
                    throw new NoSuchElementException();
                }

                @Override // java.util.Iterator
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int access$010 = Object2IntArrayMap.access$010(Object2IntArrayMap.this);
                    int i = this.next;
                    this.next = i - 1;
                    int tail = access$010 - i;
                    System.arraycopy(Object2IntArrayMap.this.key, this.next + 1, Object2IntArrayMap.this.key, this.next, tail);
                    System.arraycopy(Object2IntArrayMap.this.value, this.next + 1, Object2IntArrayMap.this.value, this.next, tail);
                    Object2IntArrayMap.this.key[Object2IntArrayMap.this.size] = null;
                }

                @Override // java.util.Iterator
                public void forEachRemaining(Consumer<? super Object2IntMap.Entry<K>> action) {
                    int max = Object2IntArrayMap.this.size;
                    while (this.next < max) {
                        Object[] objArr = Object2IntArrayMap.this.key;
                        int i = this.next;
                        this.curr = i;
                        Object obj = objArr[i];
                        int[] iArr = Object2IntArrayMap.this.value;
                        int i2 = this.next;
                        this.next = i2 + 1;
                        action.accept(new AbstractObject2IntMap.BasicEntry(obj, iArr[i2]));
                    }
                }
            };
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap.FastEntrySet
        public ObjectIterator<Object2IntMap.Entry<K>> fastIterator() {
            return new ObjectIterator<Object2IntMap.Entry<K>>() { // from class: com.viaversion.viaversion.libs.fastutil.objects.Object2IntArrayMap.EntrySet.2
                int next = 0;
                int curr = -1;
                final AbstractObject2IntMap.BasicEntry<K> entry = new AbstractObject2IntMap.BasicEntry<>();

                @Override // java.util.Iterator
                public boolean hasNext() {
                    return this.next < Object2IntArrayMap.this.size;
                }

                @Override // java.util.Iterator
                public Object2IntMap.Entry<K> next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    AbstractObject2IntMap.BasicEntry<K> basicEntry = this.entry;
                    Object[] objArr = Object2IntArrayMap.this.key;
                    int i = this.next;
                    this.curr = i;
                    basicEntry.key = (K) objArr[i];
                    AbstractObject2IntMap.BasicEntry<K> basicEntry2 = this.entry;
                    int[] iArr = Object2IntArrayMap.this.value;
                    int i2 = this.next;
                    this.next = i2 + 1;
                    basicEntry2.value = iArr[i2];
                    return this.entry;
                }

                @Override // java.util.Iterator
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int access$010 = Object2IntArrayMap.access$010(Object2IntArrayMap.this);
                    int i = this.next;
                    this.next = i - 1;
                    int tail = access$010 - i;
                    System.arraycopy(Object2IntArrayMap.this.key, this.next + 1, Object2IntArrayMap.this.key, this.next, tail);
                    System.arraycopy(Object2IntArrayMap.this.value, this.next + 1, Object2IntArrayMap.this.value, this.next, tail);
                    Object2IntArrayMap.this.key[Object2IntArrayMap.this.size] = null;
                }

                @Override // java.util.Iterator
                public void forEachRemaining(Consumer<? super Object2IntMap.Entry<K>> action) {
                    int max = Object2IntArrayMap.this.size;
                    while (this.next < max) {
                        AbstractObject2IntMap.BasicEntry<K> basicEntry = this.entry;
                        Object[] objArr = Object2IntArrayMap.this.key;
                        int i = this.next;
                        this.curr = i;
                        basicEntry.key = (K) objArr[i];
                        AbstractObject2IntMap.BasicEntry<K> basicEntry2 = this.entry;
                        int[] iArr = Object2IntArrayMap.this.value;
                        int i2 = this.next;
                        this.next = i2 + 1;
                        basicEntry2.value = iArr[i2];
                        action.accept(this.entry);
                    }
                }
            };
        }

        /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2IntArrayMap$EntrySet$EntrySetSpliterator.class */
        public final class EntrySetSpliterator extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator<Object2IntMap.Entry<K>> implements ObjectSpliterator<Object2IntMap.Entry<K>> {
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
            public final Object2IntMap.Entry<K> get(int location) {
                return new AbstractObject2IntMap.BasicEntry(Object2IntArrayMap.this.key[location], Object2IntArrayMap.this.value[location]);
            }

            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators.AbstractIndexBasedSpliterator
            public final Object2IntArrayMap<K>.EntrySet.EntrySetSpliterator makeForSplit(int pos, int maxPos) {
                return new EntrySetSpliterator(pos, maxPos);
            }
        }

        @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectSet, java.util.Set
        public ObjectSpliterator<Object2IntMap.Entry<K>> spliterator() {
            return new EntrySetSpliterator(0, Object2IntArrayMap.this.size);
        }

        @Override // java.lang.Iterable
        public void forEach(Consumer<? super Object2IntMap.Entry<K>> action) {
            int max = Object2IntArrayMap.this.size;
            for (int i = 0; i < max; i++) {
                action.accept(new AbstractObject2IntMap.BasicEntry(Object2IntArrayMap.this.key[i], Object2IntArrayMap.this.value[i]));
            }
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap.FastEntrySet
        public void fastForEach(Consumer<? super Object2IntMap.Entry<K>> action) {
            AbstractObject2IntMap.BasicEntry<K> entry = new AbstractObject2IntMap.BasicEntry<>();
            int max = Object2IntArrayMap.this.size;
            for (int i = 0; i < max; i++) {
                entry.key = (K) Object2IntArrayMap.this.key[i];
                entry.value = Object2IntArrayMap.this.value[i];
                action.accept(entry);
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return Object2IntArrayMap.this.size;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<?, ?> e = (Map.Entry) o;
            if (e.getValue() == null || !(e.getValue() instanceof Integer)) {
                return false;
            }
            Object key = e.getKey();
            return Object2IntArrayMap.this.containsKey(key) && Object2IntArrayMap.this.getInt(key) == ((Integer) e.getValue()).intValue();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<?, ?> e = (Map.Entry) o;
            if (e.getValue() == null || !(e.getValue() instanceof Integer)) {
                return false;
            }
            Object key = e.getKey();
            int v = ((Integer) e.getValue()).intValue();
            int oldPos = Object2IntArrayMap.this.findKey(key);
            if (oldPos != -1 && v == Object2IntArrayMap.this.value[oldPos]) {
                int tail = (Object2IntArrayMap.this.size - oldPos) - 1;
                System.arraycopy(Object2IntArrayMap.this.key, oldPos + 1, Object2IntArrayMap.this.key, oldPos, tail);
                System.arraycopy(Object2IntArrayMap.this.value, oldPos + 1, Object2IntArrayMap.this.value, oldPos, tail);
                Object2IntArrayMap.access$010(Object2IntArrayMap.this);
                Object2IntArrayMap.this.key[Object2IntArrayMap.this.size] = null;
                return true;
            }
            return false;
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap
    public Object2IntMap.FastEntrySet<K> object2IntEntrySet() {
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

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunction
    public int getInt(Object k) {
        Object[] key = this.key;
        int i = this.size;
        do {
            int i2 = i;
            i--;
            if (i2 == 0) {
                return this.defRetValue;
            }
        } while (!Objects.equals(key[i], k));
        return this.value[i];
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
            } else {
                this.size = 0;
                return;
            }
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2IntMap, com.viaversion.viaversion.libs.fastutil.Function
    public boolean containsKey(Object k) {
        return findKey(k) != -1;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2IntMap, com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap
    public boolean containsValue(int v) {
        int i = this.size;
        do {
            int i2 = i;
            i--;
            if (i2 == 0) {
                return false;
            }
        } while (this.value[i] != v);
        return true;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2IntMap, java.util.Map
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunction
    public int put(K k, int v) {
        int oldKey = findKey(k);
        if (oldKey != -1) {
            int oldValue = this.value[oldKey];
            this.value[oldKey] = v;
            return oldValue;
        }
        if (this.size == this.key.length) {
            Object[] newKey = new Object[this.size == 0 ? 2 : this.size * 2];
            int[] newValue = new int[this.size == 0 ? 2 : this.size * 2];
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

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunction
    public int removeInt(Object k) {
        int oldPos = findKey(k);
        if (oldPos == -1) {
            return this.defRetValue;
        }
        int oldValue = this.value[oldPos];
        int tail = (this.size - oldPos) - 1;
        System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
        System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
        this.size--;
        this.key[this.size] = null;
        return oldValue;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2IntArrayMap$KeySet.class */
    public final class KeySet extends AbstractObjectSet<K> {
        private KeySet() {
            Object2IntArrayMap.this = r4;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object k) {
            return Object2IntArrayMap.this.findKey(k) != -1;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object k) {
            int oldPos = Object2IntArrayMap.this.findKey(k);
            if (oldPos != -1) {
                int tail = (Object2IntArrayMap.this.size - oldPos) - 1;
                System.arraycopy(Object2IntArrayMap.this.key, oldPos + 1, Object2IntArrayMap.this.key, oldPos, tail);
                System.arraycopy(Object2IntArrayMap.this.value, oldPos + 1, Object2IntArrayMap.this.value, oldPos, tail);
                Object2IntArrayMap.access$010(Object2IntArrayMap.this);
                Object2IntArrayMap.this.key[Object2IntArrayMap.this.size] = null;
                return true;
            }
            return false;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet, com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable
        public ObjectIterator<K> iterator() {
            return new ObjectIterator<K>() { // from class: com.viaversion.viaversion.libs.fastutil.objects.Object2IntArrayMap.KeySet.1
                int pos = 0;

                @Override // java.util.Iterator
                public boolean hasNext() {
                    return this.pos < Object2IntArrayMap.this.size;
                }

                @Override // java.util.Iterator
                public K next() {
                    if (hasNext()) {
                        Object[] objArr = Object2IntArrayMap.this.key;
                        int i = this.pos;
                        this.pos = i + 1;
                        return (K) objArr[i];
                    }
                    throw new NoSuchElementException();
                }

                @Override // java.util.Iterator
                public void remove() {
                    if (this.pos != 0) {
                        int tail = Object2IntArrayMap.this.size - this.pos;
                        System.arraycopy(Object2IntArrayMap.this.key, this.pos, Object2IntArrayMap.this.key, this.pos - 1, tail);
                        System.arraycopy(Object2IntArrayMap.this.value, this.pos, Object2IntArrayMap.this.value, this.pos - 1, tail);
                        Object2IntArrayMap.access$010(Object2IntArrayMap.this);
                        this.pos--;
                        Object2IntArrayMap.this.key[Object2IntArrayMap.this.size] = null;
                        return;
                    }
                    throw new IllegalStateException();
                }

                @Override // java.util.Iterator
                public void forEachRemaining(Consumer<? super K> action) {
                    int max = Object2IntArrayMap.this.size;
                    while (this.pos < max) {
                        Object[] objArr = Object2IntArrayMap.this.key;
                        int i = this.pos;
                        this.pos = i + 1;
                        action.accept(objArr[i]);
                    }
                }
            };
        }

        /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2IntArrayMap$KeySet$KeySetSpliterator.class */
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
                return (K) Object2IntArrayMap.this.key[location];
            }

            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators.AbstractIndexBasedSpliterator
            public final Object2IntArrayMap<K>.KeySet.KeySetSpliterator makeForSplit(int pos, int maxPos) {
                return new KeySetSpliterator(pos, maxPos);
            }

            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators.AbstractIndexBasedSpliterator, java.util.Spliterator
            public void forEachRemaining(Consumer<? super K> action) {
                int max = Object2IntArrayMap.this.size;
                while (this.pos < max) {
                    Object[] objArr = Object2IntArrayMap.this.key;
                    int i = this.pos;
                    this.pos = i + 1;
                    action.accept(objArr[i]);
                }
            }
        }

        @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectSet, java.util.Set
        public ObjectSpliterator<K> spliterator() {
            return new KeySetSpliterator(0, Object2IntArrayMap.this.size);
        }

        @Override // java.lang.Iterable
        public void forEach(Consumer<? super K> action) {
            int max = Object2IntArrayMap.this.size;
            for (int i = 0; i < max; i++) {
                action.accept(Object2IntArrayMap.this.key[i]);
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return Object2IntArrayMap.this.size;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            Object2IntArrayMap.this.clear();
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2IntMap, com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap, java.util.Map
    public ObjectSet<K> keySet() {
        if (this.keys == null) {
            this.keys = new KeySet();
        }
        return this.keys;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2IntArrayMap$ValuesCollection.class */
    public final class ValuesCollection extends AbstractIntCollection {
        private ValuesCollection() {
            Object2IntArrayMap.this = r4;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
        public boolean contains(int v) {
            return Object2IntArrayMap.this.containsValue(v);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
        /* renamed from: iterator */
        public Iterator<Integer> iterator2() {
            return new IntIterator() { // from class: com.viaversion.viaversion.libs.fastutil.objects.Object2IntArrayMap.ValuesCollection.1
                int pos = 0;

                @Override // java.util.Iterator
                public boolean hasNext() {
                    return this.pos < Object2IntArrayMap.this.size;
                }

                @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
                public int nextInt() {
                    if (hasNext()) {
                        int[] iArr = Object2IntArrayMap.this.value;
                        int i = this.pos;
                        this.pos = i + 1;
                        return iArr[i];
                    }
                    throw new NoSuchElementException();
                }

                @Override // java.util.Iterator
                public void remove() {
                    if (this.pos != 0) {
                        int tail = Object2IntArrayMap.this.size - this.pos;
                        System.arraycopy(Object2IntArrayMap.this.key, this.pos, Object2IntArrayMap.this.key, this.pos - 1, tail);
                        System.arraycopy(Object2IntArrayMap.this.value, this.pos, Object2IntArrayMap.this.value, this.pos - 1, tail);
                        Object2IntArrayMap.access$010(Object2IntArrayMap.this);
                        this.pos--;
                        Object2IntArrayMap.this.key[Object2IntArrayMap.this.size] = null;
                        return;
                    }
                    throw new IllegalStateException();
                }

                @Override // java.util.PrimitiveIterator.OfInt
                public void forEachRemaining(IntConsumer action) {
                    int max = Object2IntArrayMap.this.size;
                    while (this.pos < max) {
                        int[] iArr = Object2IntArrayMap.this.value;
                        int i = this.pos;
                        this.pos = i + 1;
                        action.accept(iArr[i]);
                    }
                }
            };
        }

        /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2IntArrayMap$ValuesCollection$ValuesSpliterator.class */
        public final class ValuesSpliterator extends IntSpliterators.EarlyBindingSizeIndexBasedSpliterator implements IntSpliterator {
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            ValuesSpliterator(int pos, int maxPos) {
                super(pos, maxPos);
                ValuesCollection.this = this$1;
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators.AbstractIndexBasedSpliterator, java.util.Spliterator
            public int characteristics() {
                return IntSpliterators.LIST_SPLITERATOR_CHARACTERISTICS;
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators.AbstractIndexBasedSpliterator
            protected final int get(int location) {
                return Object2IntArrayMap.this.value[location];
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators.AbstractIndexBasedSpliterator
            public final Object2IntArrayMap<K>.ValuesCollection.ValuesSpliterator makeForSplit(int pos, int maxPos) {
                return new ValuesSpliterator(pos, maxPos);
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators.AbstractIndexBasedSpliterator, java.util.Spliterator.OfInt
            public void forEachRemaining(IntConsumer action) {
                int max = Object2IntArrayMap.this.size;
                while (this.pos < max) {
                    int[] iArr = Object2IntArrayMap.this.value;
                    int i = this.pos;
                    this.pos = i + 1;
                    action.accept(iArr[i]);
                }
            }
        }

        @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
        /* renamed from: spliterator */
        public Spliterator<Integer> spliterator2() {
            return new ValuesSpliterator(0, Object2IntArrayMap.this.size);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterable
        public void forEach(IntConsumer action) {
            int max = Object2IntArrayMap.this.size;
            for (int i = 0; i < max; i++) {
                action.accept(Object2IntArrayMap.this.value[i]);
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            return Object2IntArrayMap.this.size;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public void clear() {
            Object2IntArrayMap.this.clear();
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2IntMap, com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap, java.util.Map
    public IntCollection values() {
        if (this.values == null) {
            this.values = new ValuesCollection();
        }
        return this.values;
    }

    public Object2IntArrayMap<K> clone() {
        try {
            Object2IntArrayMap<K> c = (Object2IntArrayMap) super.clone();
            c.key = (Object[]) this.key.clone();
            c.value = (int[]) this.value.clone();
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
            s.writeInt(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.key = new Object[this.size];
        this.value = new int[this.size];
        for (int i = 0; i < this.size; i++) {
            this.key[i] = s.readObject();
            this.value[i] = s.readInt();
        }
    }
}
