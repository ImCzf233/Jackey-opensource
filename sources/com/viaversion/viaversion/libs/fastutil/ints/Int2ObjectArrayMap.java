package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectArrays;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator;
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

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectArrayMap.class */
public class Int2ObjectArrayMap<V> extends AbstractInt2ObjectMap<V> implements Serializable, Cloneable {
    private static final long serialVersionUID = 1;
    private transient int[] key;
    private transient Object[] value;
    private int size;
    private transient Int2ObjectMap.FastEntrySet<V> entries;
    private transient IntSet keys;
    private transient ObjectCollection<V> values;

    static /* synthetic */ int access$010(Int2ObjectArrayMap x0) {
        int i = x0.size;
        x0.size = i - 1;
        return i;
    }

    public Int2ObjectArrayMap(int[] key, Object[] value) {
        this.key = key;
        this.value = value;
        this.size = key.length;
        if (key.length != value.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
        }
    }

    public Int2ObjectArrayMap() {
        this.key = IntArrays.EMPTY_ARRAY;
        this.value = ObjectArrays.EMPTY_ARRAY;
    }

    public Int2ObjectArrayMap(int capacity) {
        this.key = new int[capacity];
        this.value = new Object[capacity];
    }

    public Int2ObjectArrayMap(Int2ObjectMap<V> m) {
        this(m.size());
        int i = 0;
        ObjectIterator<Int2ObjectMap.Entry<V>> it = m.int2ObjectEntrySet().iterator();
        while (it.hasNext()) {
            Int2ObjectMap.Entry<V> e = it.next();
            this.key[i] = e.getIntKey();
            this.value[i] = e.getValue();
            i++;
        }
        this.size = i;
    }

    public Int2ObjectArrayMap(Map<? extends Integer, ? extends V> m) {
        this(m.size());
        int i = 0;
        for (Map.Entry<? extends Integer, ? extends V> e : m.entrySet()) {
            this.key[i] = e.getKey().intValue();
            this.value[i] = e.getValue();
            i++;
        }
        this.size = i;
    }

    public Int2ObjectArrayMap(int[] key, Object[] value, int size) {
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

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectArrayMap$EntrySet.class */
    public final class EntrySet extends AbstractObjectSet<Int2ObjectMap.Entry<V>> implements Int2ObjectMap.FastEntrySet<V> {
        private EntrySet() {
            Int2ObjectArrayMap.this = r4;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet, com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable
        public ObjectIterator<Int2ObjectMap.Entry<V>> iterator() {
            return new ObjectIterator<Int2ObjectMap.Entry<V>>() { // from class: com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectArrayMap.EntrySet.1
                int curr = -1;
                int next = 0;

                @Override // java.util.Iterator
                public boolean hasNext() {
                    return this.next < Int2ObjectArrayMap.this.size;
                }

                @Override // java.util.Iterator
                public Int2ObjectMap.Entry<V> next() {
                    if (hasNext()) {
                        int[] iArr = Int2ObjectArrayMap.this.key;
                        int i = this.next;
                        this.curr = i;
                        int i2 = iArr[i];
                        Object[] objArr = Int2ObjectArrayMap.this.value;
                        int i3 = this.next;
                        this.next = i3 + 1;
                        return new AbstractInt2ObjectMap.BasicEntry(i2, objArr[i3]);
                    }
                    throw new NoSuchElementException();
                }

                @Override // java.util.Iterator
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int access$010 = Int2ObjectArrayMap.access$010(Int2ObjectArrayMap.this);
                    int i = this.next;
                    this.next = i - 1;
                    int tail = access$010 - i;
                    System.arraycopy(Int2ObjectArrayMap.this.key, this.next + 1, Int2ObjectArrayMap.this.key, this.next, tail);
                    System.arraycopy(Int2ObjectArrayMap.this.value, this.next + 1, Int2ObjectArrayMap.this.value, this.next, tail);
                    Int2ObjectArrayMap.this.value[Int2ObjectArrayMap.this.size] = null;
                }

                @Override // java.util.Iterator
                public void forEachRemaining(Consumer<? super Int2ObjectMap.Entry<V>> action) {
                    int max = Int2ObjectArrayMap.this.size;
                    while (this.next < max) {
                        int[] iArr = Int2ObjectArrayMap.this.key;
                        int i = this.next;
                        this.curr = i;
                        int i2 = iArr[i];
                        Object[] objArr = Int2ObjectArrayMap.this.value;
                        int i3 = this.next;
                        this.next = i3 + 1;
                        action.accept(new AbstractInt2ObjectMap.BasicEntry(i2, objArr[i3]));
                    }
                }
            };
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap.FastEntrySet
        public ObjectIterator<Int2ObjectMap.Entry<V>> fastIterator() {
            return new ObjectIterator<Int2ObjectMap.Entry<V>>() { // from class: com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectArrayMap.EntrySet.2
                int next = 0;
                int curr = -1;
                final AbstractInt2ObjectMap.BasicEntry<V> entry = new AbstractInt2ObjectMap.BasicEntry<>();

                @Override // java.util.Iterator
                public boolean hasNext() {
                    return this.next < Int2ObjectArrayMap.this.size;
                }

                @Override // java.util.Iterator
                public Int2ObjectMap.Entry<V> next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    AbstractInt2ObjectMap.BasicEntry<V> basicEntry = this.entry;
                    int[] iArr = Int2ObjectArrayMap.this.key;
                    int i = this.next;
                    this.curr = i;
                    basicEntry.key = iArr[i];
                    AbstractInt2ObjectMap.BasicEntry<V> basicEntry2 = this.entry;
                    Object[] objArr = Int2ObjectArrayMap.this.value;
                    int i2 = this.next;
                    this.next = i2 + 1;
                    basicEntry2.value = (V) objArr[i2];
                    return this.entry;
                }

                @Override // java.util.Iterator
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int access$010 = Int2ObjectArrayMap.access$010(Int2ObjectArrayMap.this);
                    int i = this.next;
                    this.next = i - 1;
                    int tail = access$010 - i;
                    System.arraycopy(Int2ObjectArrayMap.this.key, this.next + 1, Int2ObjectArrayMap.this.key, this.next, tail);
                    System.arraycopy(Int2ObjectArrayMap.this.value, this.next + 1, Int2ObjectArrayMap.this.value, this.next, tail);
                    Int2ObjectArrayMap.this.value[Int2ObjectArrayMap.this.size] = null;
                }

                @Override // java.util.Iterator
                public void forEachRemaining(Consumer<? super Int2ObjectMap.Entry<V>> action) {
                    int max = Int2ObjectArrayMap.this.size;
                    while (this.next < max) {
                        AbstractInt2ObjectMap.BasicEntry<V> basicEntry = this.entry;
                        int[] iArr = Int2ObjectArrayMap.this.key;
                        int i = this.next;
                        this.curr = i;
                        basicEntry.key = iArr[i];
                        AbstractInt2ObjectMap.BasicEntry<V> basicEntry2 = this.entry;
                        Object[] objArr = Int2ObjectArrayMap.this.value;
                        int i2 = this.next;
                        this.next = i2 + 1;
                        basicEntry2.value = (V) objArr[i2];
                        action.accept(this.entry);
                    }
                }
            };
        }

        /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectArrayMap$EntrySet$EntrySetSpliterator.class */
        public final class EntrySetSpliterator extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator<Int2ObjectMap.Entry<V>> implements ObjectSpliterator<Int2ObjectMap.Entry<V>> {
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
            public final Int2ObjectMap.Entry<V> get(int location) {
                return new AbstractInt2ObjectMap.BasicEntry(Int2ObjectArrayMap.this.key[location], Int2ObjectArrayMap.this.value[location]);
            }

            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators.AbstractIndexBasedSpliterator
            public final Int2ObjectArrayMap<V>.EntrySet.EntrySetSpliterator makeForSplit(int pos, int maxPos) {
                return new EntrySetSpliterator(pos, maxPos);
            }
        }

        @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectSet, java.util.Set
        public ObjectSpliterator<Int2ObjectMap.Entry<V>> spliterator() {
            return new EntrySetSpliterator(0, Int2ObjectArrayMap.this.size);
        }

        @Override // java.lang.Iterable
        public void forEach(Consumer<? super Int2ObjectMap.Entry<V>> action) {
            int max = Int2ObjectArrayMap.this.size;
            for (int i = 0; i < max; i++) {
                action.accept(new AbstractInt2ObjectMap.BasicEntry(Int2ObjectArrayMap.this.key[i], Int2ObjectArrayMap.this.value[i]));
            }
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap.FastEntrySet
        public void fastForEach(Consumer<? super Int2ObjectMap.Entry<V>> action) {
            AbstractInt2ObjectMap.BasicEntry<V> entry = new AbstractInt2ObjectMap.BasicEntry<>();
            int max = Int2ObjectArrayMap.this.size;
            for (int i = 0; i < max; i++) {
                entry.key = Int2ObjectArrayMap.this.key[i];
                entry.value = (V) Int2ObjectArrayMap.this.value[i];
                action.accept(entry);
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return Int2ObjectArrayMap.this.size;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<?, ?> e = (Map.Entry) o;
            if (e.getKey() == null || !(e.getKey() instanceof Integer)) {
                return false;
            }
            int k = ((Integer) e.getKey()).intValue();
            return Int2ObjectArrayMap.this.containsKey(k) && Objects.equals(Int2ObjectArrayMap.this.get(k), e.getValue());
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<?, ?> e = (Map.Entry) o;
            if (e.getKey() == null || !(e.getKey() instanceof Integer)) {
                return false;
            }
            int k = ((Integer) e.getKey()).intValue();
            Object value = e.getValue();
            int oldPos = Int2ObjectArrayMap.this.findKey(k);
            if (oldPos != -1 && Objects.equals(value, Int2ObjectArrayMap.this.value[oldPos])) {
                int tail = (Int2ObjectArrayMap.this.size - oldPos) - 1;
                System.arraycopy(Int2ObjectArrayMap.this.key, oldPos + 1, Int2ObjectArrayMap.this.key, oldPos, tail);
                System.arraycopy(Int2ObjectArrayMap.this.value, oldPos + 1, Int2ObjectArrayMap.this.value, oldPos, tail);
                Int2ObjectArrayMap.access$010(Int2ObjectArrayMap.this);
                Int2ObjectArrayMap.this.value[Int2ObjectArrayMap.this.size] = null;
                return true;
            }
            return false;
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap
    public Int2ObjectMap.FastEntrySet<V> int2ObjectEntrySet() {
        if (this.entries == null) {
            this.entries = new EntrySet();
        }
        return this.entries;
    }

    public int findKey(int k) {
        int[] key = this.key;
        int i = this.size;
        do {
            int i2 = i;
            i--;
            if (i2 == 0) {
                return -1;
            }
        } while (key[i] != k);
        return i;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction
    public V get(int k) {
        int[] key = this.key;
        int i = this.size;
        do {
            int i2 = i;
            i--;
            if (i2 == 0) {
                return this.defRetValue;
            }
        } while (key[i] != k);
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
                this.value[i] = null;
            } else {
                this.size = 0;
                return;
            }
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2ObjectMap, com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction, com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap
    public boolean containsKey(int k) {
        return findKey(k) != -1;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2ObjectMap, java.util.Map
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

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2ObjectMap, java.util.Map
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction
    public V put(int k, V v) {
        int oldKey = findKey(k);
        if (oldKey != -1) {
            V oldValue = (V) this.value[oldKey];
            this.value[oldKey] = v;
            return oldValue;
        }
        if (this.size == this.key.length) {
            int[] newKey = new int[this.size == 0 ? 2 : this.size * 2];
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

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction
    public V remove(int k) {
        int oldPos = findKey(k);
        if (oldPos == -1) {
            return this.defRetValue;
        }
        V oldValue = (V) this.value[oldPos];
        int tail = (this.size - oldPos) - 1;
        System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
        System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
        this.size--;
        this.value[this.size] = null;
        return oldValue;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectArrayMap$KeySet.class */
    public final class KeySet extends AbstractIntSet {
        private KeySet() {
            Int2ObjectArrayMap.this = r4;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
        public boolean contains(int k) {
            return Int2ObjectArrayMap.this.findKey(k) != -1;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntSet, com.viaversion.viaversion.libs.fastutil.ints.IntSet
        public boolean remove(int k) {
            int oldPos = Int2ObjectArrayMap.this.findKey(k);
            if (oldPos != -1) {
                int tail = (Int2ObjectArrayMap.this.size - oldPos) - 1;
                System.arraycopy(Int2ObjectArrayMap.this.key, oldPos + 1, Int2ObjectArrayMap.this.key, oldPos, tail);
                System.arraycopy(Int2ObjectArrayMap.this.value, oldPos + 1, Int2ObjectArrayMap.this.value, oldPos, tail);
                Int2ObjectArrayMap.access$010(Int2ObjectArrayMap.this);
                Int2ObjectArrayMap.this.value[Int2ObjectArrayMap.this.size] = null;
                return true;
            }
            return false;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntSet, com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
        /* renamed from: iterator */
        public Iterator<Integer> iterator2() {
            return new IntIterator() { // from class: com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectArrayMap.KeySet.1
                int pos = 0;

                @Override // java.util.Iterator
                public boolean hasNext() {
                    return this.pos < Int2ObjectArrayMap.this.size;
                }

                @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
                public int nextInt() {
                    if (hasNext()) {
                        int[] iArr = Int2ObjectArrayMap.this.key;
                        int i = this.pos;
                        this.pos = i + 1;
                        return iArr[i];
                    }
                    throw new NoSuchElementException();
                }

                @Override // java.util.Iterator
                public void remove() {
                    if (this.pos != 0) {
                        int tail = Int2ObjectArrayMap.this.size - this.pos;
                        System.arraycopy(Int2ObjectArrayMap.this.key, this.pos, Int2ObjectArrayMap.this.key, this.pos - 1, tail);
                        System.arraycopy(Int2ObjectArrayMap.this.value, this.pos, Int2ObjectArrayMap.this.value, this.pos - 1, tail);
                        Int2ObjectArrayMap.access$010(Int2ObjectArrayMap.this);
                        this.pos--;
                        Int2ObjectArrayMap.this.value[Int2ObjectArrayMap.this.size] = null;
                        return;
                    }
                    throw new IllegalStateException();
                }

                @Override // java.util.PrimitiveIterator.OfInt
                public void forEachRemaining(java.util.function.IntConsumer action) {
                    int max = Int2ObjectArrayMap.this.size;
                    while (this.pos < max) {
                        int[] iArr = Int2ObjectArrayMap.this.key;
                        int i = this.pos;
                        this.pos = i + 1;
                        action.accept(iArr[i]);
                    }
                }
            };
        }

        /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectArrayMap$KeySet$KeySetSpliterator.class */
        public final class KeySetSpliterator extends IntSpliterators.EarlyBindingSizeIndexBasedSpliterator implements IntSpliterator {
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            KeySetSpliterator(int pos, int maxPos) {
                super(pos, maxPos);
                KeySet.this = this$1;
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators.AbstractIndexBasedSpliterator, java.util.Spliterator
            public int characteristics() {
                return 16721;
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators.AbstractIndexBasedSpliterator
            protected final int get(int location) {
                return Int2ObjectArrayMap.this.key[location];
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators.AbstractIndexBasedSpliterator
            public final Int2ObjectArrayMap<V>.KeySet.KeySetSpliterator makeForSplit(int pos, int maxPos) {
                return new KeySetSpliterator(pos, maxPos);
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators.AbstractIndexBasedSpliterator, java.util.Spliterator.OfInt
            public void forEachRemaining(java.util.function.IntConsumer action) {
                int max = Int2ObjectArrayMap.this.size;
                while (this.pos < max) {
                    int[] iArr = Int2ObjectArrayMap.this.key;
                    int i = this.pos;
                    this.pos = i + 1;
                    action.accept(iArr[i]);
                }
            }
        }

        @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
        /* renamed from: spliterator */
        public Spliterator<Integer> spliterator2() {
            return new KeySetSpliterator(0, Int2ObjectArrayMap.this.size);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterable
        public void forEach(java.util.function.IntConsumer action) {
            int max = Int2ObjectArrayMap.this.size;
            for (int i = 0; i < max; i++) {
                action.accept(Int2ObjectArrayMap.this.key[i]);
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return Int2ObjectArrayMap.this.size;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            Int2ObjectArrayMap.this.clear();
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2ObjectMap, com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap, java.util.Map
    public IntSet keySet() {
        if (this.keys == null) {
            this.keys = new KeySet();
        }
        return this.keys;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectArrayMap$ValuesCollection.class */
    public final class ValuesCollection extends AbstractObjectCollection<V> {
        private ValuesCollection() {
            Int2ObjectArrayMap.this = r4;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean contains(Object v) {
            return Int2ObjectArrayMap.this.containsValue(v);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable
        public ObjectIterator<V> iterator() {
            return new ObjectIterator<V>() { // from class: com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectArrayMap.ValuesCollection.1
                int pos = 0;

                @Override // java.util.Iterator
                public boolean hasNext() {
                    return this.pos < Int2ObjectArrayMap.this.size;
                }

                @Override // java.util.Iterator
                public V next() {
                    if (hasNext()) {
                        Object[] objArr = Int2ObjectArrayMap.this.value;
                        int i = this.pos;
                        this.pos = i + 1;
                        return (V) objArr[i];
                    }
                    throw new NoSuchElementException();
                }

                @Override // java.util.Iterator
                public void remove() {
                    if (this.pos != 0) {
                        int tail = Int2ObjectArrayMap.this.size - this.pos;
                        System.arraycopy(Int2ObjectArrayMap.this.key, this.pos, Int2ObjectArrayMap.this.key, this.pos - 1, tail);
                        System.arraycopy(Int2ObjectArrayMap.this.value, this.pos, Int2ObjectArrayMap.this.value, this.pos - 1, tail);
                        Int2ObjectArrayMap.access$010(Int2ObjectArrayMap.this);
                        this.pos--;
                        Int2ObjectArrayMap.this.value[Int2ObjectArrayMap.this.size] = null;
                        return;
                    }
                    throw new IllegalStateException();
                }

                @Override // java.util.Iterator
                public void forEachRemaining(Consumer<? super V> action) {
                    int max = Int2ObjectArrayMap.this.size;
                    while (this.pos < max) {
                        Object[] objArr = Int2ObjectArrayMap.this.value;
                        int i = this.pos;
                        this.pos = i + 1;
                        action.accept(objArr[i]);
                    }
                }
            };
        }

        /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectArrayMap$ValuesCollection$ValuesSpliterator.class */
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
                return (V) Int2ObjectArrayMap.this.value[location];
            }

            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators.AbstractIndexBasedSpliterator
            public final Int2ObjectArrayMap<V>.ValuesCollection.ValuesSpliterator makeForSplit(int pos, int maxPos) {
                return new ValuesSpliterator(pos, maxPos);
            }

            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators.AbstractIndexBasedSpliterator, java.util.Spliterator
            public void forEachRemaining(Consumer<? super V> action) {
                int max = Int2ObjectArrayMap.this.size;
                while (this.pos < max) {
                    Object[] objArr = Int2ObjectArrayMap.this.value;
                    int i = this.pos;
                    this.pos = i + 1;
                    action.accept(objArr[i]);
                }
            }
        }

        @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectSet, java.util.Set
        public ObjectSpliterator<V> spliterator() {
            return new ValuesSpliterator(0, Int2ObjectArrayMap.this.size);
        }

        @Override // java.lang.Iterable
        public void forEach(Consumer<? super V> action) {
            int max = Int2ObjectArrayMap.this.size;
            for (int i = 0; i < max; i++) {
                action.accept(Int2ObjectArrayMap.this.value[i]);
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            return Int2ObjectArrayMap.this.size;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public void clear() {
            Int2ObjectArrayMap.this.clear();
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2ObjectMap, com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap, java.util.Map
    public ObjectCollection<V> values() {
        if (this.values == null) {
            this.values = new ValuesCollection();
        }
        return this.values;
    }

    public Int2ObjectArrayMap<V> clone() {
        try {
            Int2ObjectArrayMap<V> c = (Int2ObjectArrayMap) super.clone();
            c.key = (int[]) this.key.clone();
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
            s.writeInt(this.key[i]);
            s.writeObject(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.key = new int[this.size];
        this.value = new Object[this.size];
        for (int i = 0; i < this.size; i++) {
            this.key[i] = s.readInt();
            this.value[i] = s.readObject();
        }
    }
}
