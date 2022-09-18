package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2IntMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet;
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
import java.util.Spliterator;
import java.util.function.Consumer;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2IntArrayMap.class */
public class Int2IntArrayMap extends AbstractInt2IntMap implements Serializable, Cloneable {
    private static final long serialVersionUID = 1;
    private transient int[] key;
    private transient int[] value;
    private int size;
    private transient Int2IntMap.FastEntrySet entries;
    private transient IntSet keys;
    private transient IntCollection values;

    static /* synthetic */ int access$010(Int2IntArrayMap x0) {
        int i = x0.size;
        x0.size = i - 1;
        return i;
    }

    public Int2IntArrayMap(int[] key, int[] value) {
        this.key = key;
        this.value = value;
        this.size = key.length;
        if (key.length != value.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
        }
    }

    public Int2IntArrayMap() {
        this.key = IntArrays.EMPTY_ARRAY;
        this.value = IntArrays.EMPTY_ARRAY;
    }

    public Int2IntArrayMap(int capacity) {
        this.key = new int[capacity];
        this.value = new int[capacity];
    }

    public Int2IntArrayMap(Int2IntMap m) {
        this(m.size());
        int i = 0;
        ObjectIterator<Int2IntMap.Entry> it = m.int2IntEntrySet().iterator();
        while (it.hasNext()) {
            Int2IntMap.Entry e = it.next();
            this.key[i] = e.getIntKey();
            this.value[i] = e.getIntValue();
            i++;
        }
        this.size = i;
    }

    public Int2IntArrayMap(Map<? extends Integer, ? extends Integer> m) {
        this(m.size());
        int i = 0;
        for (Map.Entry<? extends Integer, ? extends Integer> e : m.entrySet()) {
            this.key[i] = e.getKey().intValue();
            this.value[i] = e.getValue().intValue();
            i++;
        }
        this.size = i;
    }

    public Int2IntArrayMap(int[] key, int[] value, int size) {
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

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2IntArrayMap$EntrySet.class */
    public final class EntrySet extends AbstractObjectSet<Int2IntMap.Entry> implements Int2IntMap.FastEntrySet {
        private EntrySet() {
            Int2IntArrayMap.this = r4;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet, com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable
        public ObjectIterator<Int2IntMap.Entry> iterator() {
            return new ObjectIterator<Int2IntMap.Entry>() { // from class: com.viaversion.viaversion.libs.fastutil.ints.Int2IntArrayMap.EntrySet.1
                int curr = -1;
                int next = 0;

                @Override // java.util.Iterator
                public boolean hasNext() {
                    return this.next < Int2IntArrayMap.this.size;
                }

                @Override // java.util.Iterator
                public Int2IntMap.Entry next() {
                    if (hasNext()) {
                        int[] iArr = Int2IntArrayMap.this.key;
                        int i = this.next;
                        this.curr = i;
                        int i2 = iArr[i];
                        int[] iArr2 = Int2IntArrayMap.this.value;
                        int i3 = this.next;
                        this.next = i3 + 1;
                        return new AbstractInt2IntMap.BasicEntry(i2, iArr2[i3]);
                    }
                    throw new NoSuchElementException();
                }

                @Override // java.util.Iterator
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int access$010 = Int2IntArrayMap.access$010(Int2IntArrayMap.this);
                    int i = this.next;
                    this.next = i - 1;
                    int tail = access$010 - i;
                    System.arraycopy(Int2IntArrayMap.this.key, this.next + 1, Int2IntArrayMap.this.key, this.next, tail);
                    System.arraycopy(Int2IntArrayMap.this.value, this.next + 1, Int2IntArrayMap.this.value, this.next, tail);
                }

                @Override // java.util.Iterator
                public void forEachRemaining(Consumer<? super Int2IntMap.Entry> action) {
                    int max = Int2IntArrayMap.this.size;
                    while (this.next < max) {
                        int[] iArr = Int2IntArrayMap.this.key;
                        int i = this.next;
                        this.curr = i;
                        int i2 = iArr[i];
                        int[] iArr2 = Int2IntArrayMap.this.value;
                        int i3 = this.next;
                        this.next = i3 + 1;
                        action.accept(new AbstractInt2IntMap.BasicEntry(i2, iArr2[i3]));
                    }
                }
            };
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap.FastEntrySet
        public ObjectIterator<Int2IntMap.Entry> fastIterator() {
            return new ObjectIterator<Int2IntMap.Entry>() { // from class: com.viaversion.viaversion.libs.fastutil.ints.Int2IntArrayMap.EntrySet.2
                int next = 0;
                int curr = -1;
                final AbstractInt2IntMap.BasicEntry entry = new AbstractInt2IntMap.BasicEntry();

                @Override // java.util.Iterator
                public boolean hasNext() {
                    return this.next < Int2IntArrayMap.this.size;
                }

                @Override // java.util.Iterator
                public Int2IntMap.Entry next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    AbstractInt2IntMap.BasicEntry basicEntry = this.entry;
                    int[] iArr = Int2IntArrayMap.this.key;
                    int i = this.next;
                    this.curr = i;
                    basicEntry.key = iArr[i];
                    AbstractInt2IntMap.BasicEntry basicEntry2 = this.entry;
                    int[] iArr2 = Int2IntArrayMap.this.value;
                    int i2 = this.next;
                    this.next = i2 + 1;
                    basicEntry2.value = iArr2[i2];
                    return this.entry;
                }

                @Override // java.util.Iterator
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    int access$010 = Int2IntArrayMap.access$010(Int2IntArrayMap.this);
                    int i = this.next;
                    this.next = i - 1;
                    int tail = access$010 - i;
                    System.arraycopy(Int2IntArrayMap.this.key, this.next + 1, Int2IntArrayMap.this.key, this.next, tail);
                    System.arraycopy(Int2IntArrayMap.this.value, this.next + 1, Int2IntArrayMap.this.value, this.next, tail);
                }

                @Override // java.util.Iterator
                public void forEachRemaining(Consumer<? super Int2IntMap.Entry> action) {
                    int max = Int2IntArrayMap.this.size;
                    while (this.next < max) {
                        AbstractInt2IntMap.BasicEntry basicEntry = this.entry;
                        int[] iArr = Int2IntArrayMap.this.key;
                        int i = this.next;
                        this.curr = i;
                        basicEntry.key = iArr[i];
                        AbstractInt2IntMap.BasicEntry basicEntry2 = this.entry;
                        int[] iArr2 = Int2IntArrayMap.this.value;
                        int i2 = this.next;
                        this.next = i2 + 1;
                        basicEntry2.value = iArr2[i2];
                        action.accept(this.entry);
                    }
                }
            };
        }

        /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2IntArrayMap$EntrySet$EntrySetSpliterator.class */
        public final class EntrySetSpliterator extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator<Int2IntMap.Entry> implements ObjectSpliterator<Int2IntMap.Entry> {
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
            public final Int2IntMap.Entry get(int location) {
                return new AbstractInt2IntMap.BasicEntry(Int2IntArrayMap.this.key[location], Int2IntArrayMap.this.value[location]);
            }

            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators.AbstractIndexBasedSpliterator
            public final EntrySetSpliterator makeForSplit(int pos, int maxPos) {
                return new EntrySetSpliterator(pos, maxPos);
            }
        }

        @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectSet, java.util.Set
        public ObjectSpliterator<Int2IntMap.Entry> spliterator() {
            return new EntrySetSpliterator(0, Int2IntArrayMap.this.size);
        }

        @Override // java.lang.Iterable
        public void forEach(Consumer<? super Int2IntMap.Entry> action) {
            int max = Int2IntArrayMap.this.size;
            for (int i = 0; i < max; i++) {
                action.accept(new AbstractInt2IntMap.BasicEntry(Int2IntArrayMap.this.key[i], Int2IntArrayMap.this.value[i]));
            }
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap.FastEntrySet
        public void fastForEach(Consumer<? super Int2IntMap.Entry> action) {
            AbstractInt2IntMap.BasicEntry entry = new AbstractInt2IntMap.BasicEntry();
            int max = Int2IntArrayMap.this.size;
            for (int i = 0; i < max; i++) {
                entry.key = Int2IntArrayMap.this.key[i];
                entry.value = Int2IntArrayMap.this.value[i];
                action.accept(entry);
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return Int2IntArrayMap.this.size;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<?, ?> e = (Map.Entry) o;
            if (e.getKey() == null || !(e.getKey() instanceof Integer) || e.getValue() == null || !(e.getValue() instanceof Integer)) {
                return false;
            }
            int k = ((Integer) e.getKey()).intValue();
            return Int2IntArrayMap.this.containsKey(k) && Int2IntArrayMap.this.get(k) == ((Integer) e.getValue()).intValue();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<?, ?> e = (Map.Entry) o;
            if (e.getKey() == null || !(e.getKey() instanceof Integer) || e.getValue() == null || !(e.getValue() instanceof Integer)) {
                return false;
            }
            int k = ((Integer) e.getKey()).intValue();
            int v = ((Integer) e.getValue()).intValue();
            int oldPos = Int2IntArrayMap.this.findKey(k);
            if (oldPos != -1 && v == Int2IntArrayMap.this.value[oldPos]) {
                int tail = (Int2IntArrayMap.this.size - oldPos) - 1;
                System.arraycopy(Int2IntArrayMap.this.key, oldPos + 1, Int2IntArrayMap.this.key, oldPos, tail);
                System.arraycopy(Int2IntArrayMap.this.value, oldPos + 1, Int2IntArrayMap.this.value, oldPos, tail);
                Int2IntArrayMap.access$010(Int2IntArrayMap.this);
                return true;
            }
            return false;
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap
    public Int2IntMap.FastEntrySet int2IntEntrySet() {
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

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction
    public int get(int k) {
        int[] key = this.key;
        int i = this.size;
        do {
            int i2 = i;
            i--;
            if (i2 == 0) {
                return this.defRetValue;
            }
        } while (key[i] != k);
        return this.value[i];
    }

    @Override // com.viaversion.viaversion.libs.fastutil.Function, com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
    public int size() {
        return this.size;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.Function, com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
    public void clear() {
        this.size = 0;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2IntMap, com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction, com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap
    public boolean containsKey(int k) {
        return findKey(k) != -1;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2IntMap, com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap
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

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2IntMap, java.util.Map
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction
    public int put(int k, int v) {
        int oldKey = findKey(k);
        if (oldKey != -1) {
            int oldValue = this.value[oldKey];
            this.value[oldKey] = v;
            return oldValue;
        }
        if (this.size == this.key.length) {
            int[] newKey = new int[this.size == 0 ? 2 : this.size * 2];
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

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction
    public int remove(int k) {
        int oldPos = findKey(k);
        if (oldPos == -1) {
            return this.defRetValue;
        }
        int oldValue = this.value[oldPos];
        int tail = (this.size - oldPos) - 1;
        System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
        System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
        this.size--;
        return oldValue;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2IntArrayMap$KeySet.class */
    public final class KeySet extends AbstractIntSet {
        private KeySet() {
            Int2IntArrayMap.this = r4;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
        public boolean contains(int k) {
            return Int2IntArrayMap.this.findKey(k) != -1;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntSet, com.viaversion.viaversion.libs.fastutil.ints.IntSet
        public boolean remove(int k) {
            int oldPos = Int2IntArrayMap.this.findKey(k);
            if (oldPos != -1) {
                int tail = (Int2IntArrayMap.this.size - oldPos) - 1;
                System.arraycopy(Int2IntArrayMap.this.key, oldPos + 1, Int2IntArrayMap.this.key, oldPos, tail);
                System.arraycopy(Int2IntArrayMap.this.value, oldPos + 1, Int2IntArrayMap.this.value, oldPos, tail);
                Int2IntArrayMap.access$010(Int2IntArrayMap.this);
                return true;
            }
            return false;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntSet, com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
        /* renamed from: iterator */
        public Iterator<Integer> iterator2() {
            return new IntIterator() { // from class: com.viaversion.viaversion.libs.fastutil.ints.Int2IntArrayMap.KeySet.1
                int pos = 0;

                @Override // java.util.Iterator
                public boolean hasNext() {
                    return this.pos < Int2IntArrayMap.this.size;
                }

                @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
                public int nextInt() {
                    if (hasNext()) {
                        int[] iArr = Int2IntArrayMap.this.key;
                        int i = this.pos;
                        this.pos = i + 1;
                        return iArr[i];
                    }
                    throw new NoSuchElementException();
                }

                @Override // java.util.Iterator
                public void remove() {
                    if (this.pos != 0) {
                        int tail = Int2IntArrayMap.this.size - this.pos;
                        System.arraycopy(Int2IntArrayMap.this.key, this.pos, Int2IntArrayMap.this.key, this.pos - 1, tail);
                        System.arraycopy(Int2IntArrayMap.this.value, this.pos, Int2IntArrayMap.this.value, this.pos - 1, tail);
                        Int2IntArrayMap.access$010(Int2IntArrayMap.this);
                        this.pos--;
                        return;
                    }
                    throw new IllegalStateException();
                }

                @Override // java.util.PrimitiveIterator.OfInt
                public void forEachRemaining(java.util.function.IntConsumer action) {
                    int max = Int2IntArrayMap.this.size;
                    while (this.pos < max) {
                        int[] iArr = Int2IntArrayMap.this.key;
                        int i = this.pos;
                        this.pos = i + 1;
                        action.accept(iArr[i]);
                    }
                }
            };
        }

        /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2IntArrayMap$KeySet$KeySetSpliterator.class */
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
                return Int2IntArrayMap.this.key[location];
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators.AbstractIndexBasedSpliterator
            public final KeySetSpliterator makeForSplit(int pos, int maxPos) {
                return new KeySetSpliterator(pos, maxPos);
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators.AbstractIndexBasedSpliterator, java.util.Spliterator.OfInt
            public void forEachRemaining(java.util.function.IntConsumer action) {
                int max = Int2IntArrayMap.this.size;
                while (this.pos < max) {
                    int[] iArr = Int2IntArrayMap.this.key;
                    int i = this.pos;
                    this.pos = i + 1;
                    action.accept(iArr[i]);
                }
            }
        }

        @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
        /* renamed from: spliterator */
        public Spliterator<Integer> spliterator2() {
            return new KeySetSpliterator(0, Int2IntArrayMap.this.size);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterable
        public void forEach(java.util.function.IntConsumer action) {
            int max = Int2IntArrayMap.this.size;
            for (int i = 0; i < max; i++) {
                action.accept(Int2IntArrayMap.this.key[i]);
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return Int2IntArrayMap.this.size;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            Int2IntArrayMap.this.clear();
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2IntMap, com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
    public IntSet keySet() {
        if (this.keys == null) {
            this.keys = new KeySet();
        }
        return this.keys;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2IntArrayMap$ValuesCollection.class */
    public final class ValuesCollection extends AbstractIntCollection {
        private ValuesCollection() {
            Int2IntArrayMap.this = r4;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
        public boolean contains(int v) {
            return Int2IntArrayMap.this.containsValue(v);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
        /* renamed from: iterator */
        public Iterator<Integer> iterator2() {
            return new IntIterator() { // from class: com.viaversion.viaversion.libs.fastutil.ints.Int2IntArrayMap.ValuesCollection.1
                int pos = 0;

                @Override // java.util.Iterator
                public boolean hasNext() {
                    return this.pos < Int2IntArrayMap.this.size;
                }

                @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
                public int nextInt() {
                    if (hasNext()) {
                        int[] iArr = Int2IntArrayMap.this.value;
                        int i = this.pos;
                        this.pos = i + 1;
                        return iArr[i];
                    }
                    throw new NoSuchElementException();
                }

                @Override // java.util.Iterator
                public void remove() {
                    if (this.pos != 0) {
                        int tail = Int2IntArrayMap.this.size - this.pos;
                        System.arraycopy(Int2IntArrayMap.this.key, this.pos, Int2IntArrayMap.this.key, this.pos - 1, tail);
                        System.arraycopy(Int2IntArrayMap.this.value, this.pos, Int2IntArrayMap.this.value, this.pos - 1, tail);
                        Int2IntArrayMap.access$010(Int2IntArrayMap.this);
                        this.pos--;
                        return;
                    }
                    throw new IllegalStateException();
                }

                @Override // java.util.PrimitiveIterator.OfInt
                public void forEachRemaining(java.util.function.IntConsumer action) {
                    int max = Int2IntArrayMap.this.size;
                    while (this.pos < max) {
                        int[] iArr = Int2IntArrayMap.this.value;
                        int i = this.pos;
                        this.pos = i + 1;
                        action.accept(iArr[i]);
                    }
                }
            };
        }

        /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2IntArrayMap$ValuesCollection$ValuesSpliterator.class */
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
                return Int2IntArrayMap.this.value[location];
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators.AbstractIndexBasedSpliterator
            public final ValuesSpliterator makeForSplit(int pos, int maxPos) {
                return new ValuesSpliterator(pos, maxPos);
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators.AbstractIndexBasedSpliterator, java.util.Spliterator.OfInt
            public void forEachRemaining(java.util.function.IntConsumer action) {
                int max = Int2IntArrayMap.this.size;
                while (this.pos < max) {
                    int[] iArr = Int2IntArrayMap.this.value;
                    int i = this.pos;
                    this.pos = i + 1;
                    action.accept(iArr[i]);
                }
            }
        }

        @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
        /* renamed from: spliterator */
        public Spliterator<Integer> spliterator2() {
            return new ValuesSpliterator(0, Int2IntArrayMap.this.size);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterable
        public void forEach(java.util.function.IntConsumer action) {
            int max = Int2IntArrayMap.this.size;
            for (int i = 0; i < max; i++) {
                action.accept(Int2IntArrayMap.this.value[i]);
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            return Int2IntArrayMap.this.size;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public void clear() {
            Int2IntArrayMap.this.clear();
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2IntMap, com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
    public IntCollection values() {
        if (this.values == null) {
            this.values = new ValuesCollection();
        }
        return this.values;
    }

    public Int2IntArrayMap clone() {
        try {
            Int2IntArrayMap c = (Int2IntArrayMap) super.clone();
            c.key = (int[]) this.key.clone();
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
            s.writeInt(this.key[i]);
            s.writeInt(this.value[i]);
        }
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.key = new int[this.size];
        this.value = new int[this.size];
        for (int i = 0; i < this.size; i++) {
            this.key[i] = s.readInt();
            this.value[i] = s.readInt();
        }
    }
}
