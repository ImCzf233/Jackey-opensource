package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Hash;
import com.viaversion.viaversion.libs.fastutil.HashCommon;
import com.viaversion.viaversion.libs.fastutil.Size64;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollections;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collector;
import jdk.nashorn.internal.runtime.JSType;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectLinkedOpenHashSet.class */
public class ObjectLinkedOpenHashSet<K> extends AbstractObjectSortedSet<K> implements Serializable, Cloneable, Hash {
    private static final long serialVersionUID = 0;
    private static final boolean ASSERTS = false;
    protected transient K[] key;
    protected transient int mask;
    protected transient boolean containsNull;
    protected transient int first;
    protected transient int last;
    protected transient long[] link;

    /* renamed from: n */
    protected transient int f161n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;

    /* renamed from: f */
    protected final float f162f;
    private static final Collector<Object, ?, ObjectLinkedOpenHashSet<Object>> TO_SET_COLLECTOR = Collector.of(ObjectLinkedOpenHashSet::new, (v0, v1) -> {
        v0.add(v1);
    }, (v0, v1) -> {
        return v0.combine(v1);
    }, new Collector.Characteristics[0]);
    private static final int SPLITERATOR_CHARACTERISTICS = 81;

    public ObjectLinkedOpenHashSet(int expected, float f) {
        this.first = -1;
        this.last = -1;
        if (f <= 0.0f || f >= 1.0f) {
            throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1");
        }
        if (expected < 0) {
            throw new IllegalArgumentException("The expected number of elements must be nonnegative");
        }
        this.f162f = f;
        int arraySize = HashCommon.arraySize(expected, f);
        this.f161n = arraySize;
        this.minN = arraySize;
        this.mask = this.f161n - 1;
        this.maxFill = HashCommon.maxFill(this.f161n, f);
        this.key = (K[]) new Object[this.f161n + 1];
        this.link = new long[this.f161n + 1];
    }

    public ObjectLinkedOpenHashSet(int expected) {
        this(expected, 0.75f);
    }

    public ObjectLinkedOpenHashSet() {
        this(16, 0.75f);
    }

    public ObjectLinkedOpenHashSet(Collection<? extends K> c, float f) {
        this(c.size(), f);
        addAll(c);
    }

    public ObjectLinkedOpenHashSet(Collection<? extends K> c) {
        this(c, 0.75f);
    }

    public ObjectLinkedOpenHashSet(ObjectCollection<? extends K> c, float f) {
        this(c.size(), f);
        addAll(c);
    }

    public ObjectLinkedOpenHashSet(ObjectCollection<? extends K> c) {
        this((ObjectCollection) c, 0.75f);
    }

    public ObjectLinkedOpenHashSet(Iterator<? extends K> i, float f) {
        this(16, f);
        while (i.hasNext()) {
            add(i.next());
        }
    }

    public ObjectLinkedOpenHashSet(Iterator<? extends K> i) {
        this(i, 0.75f);
    }

    public ObjectLinkedOpenHashSet(K[] a, int offset, int length, float f) {
        this(length < 0 ? 0 : length, f);
        ObjectArrays.ensureOffsetLength(a, offset, length);
        for (int i = 0; i < length; i++) {
            add(a[offset + i]);
        }
    }

    public ObjectLinkedOpenHashSet(K[] a, int offset, int length) {
        this(a, offset, length, 0.75f);
    }

    public ObjectLinkedOpenHashSet(K[] a, float f) {
        this(a, 0, a.length, f);
    }

    public ObjectLinkedOpenHashSet(K[] a) {
        this(a, 0.75f);
    }

    /* renamed from: of */
    public static <K> ObjectLinkedOpenHashSet<K> m171of() {
        return new ObjectLinkedOpenHashSet<>();
    }

    /* renamed from: of */
    public static <K> ObjectLinkedOpenHashSet<K> m170of(K e) {
        ObjectLinkedOpenHashSet<K> result = new ObjectLinkedOpenHashSet<>(1, 0.75f);
        result.add(e);
        return result;
    }

    /* renamed from: of */
    public static <K> ObjectLinkedOpenHashSet<K> m169of(K e0, K e1) {
        ObjectLinkedOpenHashSet<K> result = new ObjectLinkedOpenHashSet<>(2, 0.75f);
        result.add(e0);
        if (!result.add(e1)) {
            throw new IllegalArgumentException("Duplicate element: " + e1);
        }
        return result;
    }

    /* renamed from: of */
    public static <K> ObjectLinkedOpenHashSet<K> m168of(K e0, K e1, K e2) {
        ObjectLinkedOpenHashSet<K> result = new ObjectLinkedOpenHashSet<>(3, 0.75f);
        result.add(e0);
        if (!result.add(e1)) {
            throw new IllegalArgumentException("Duplicate element: " + e1);
        }
        if (!result.add(e2)) {
            throw new IllegalArgumentException("Duplicate element: " + e2);
        }
        return result;
    }

    @SafeVarargs
    /* renamed from: of */
    public static <K> ObjectLinkedOpenHashSet<K> m167of(K... a) {
        ObjectLinkedOpenHashSet<K> result = new ObjectLinkedOpenHashSet<>(a.length, 0.75f);
        for (K element : a) {
            if (!result.add(element)) {
                throw new IllegalArgumentException("Duplicate element " + element);
            }
        }
        return result;
    }

    private ObjectLinkedOpenHashSet<K> combine(ObjectLinkedOpenHashSet<? extends K> toAddFrom) {
        addAll(toAddFrom);
        return this;
    }

    public static <K> Collector<K, ?, ObjectLinkedOpenHashSet<K>> toSet() {
        return (Collector<K, ?, ObjectLinkedOpenHashSet<K>>) TO_SET_COLLECTOR;
    }

    public static <K> Collector<K, ?, ObjectLinkedOpenHashSet<K>> toSetWithExpectedSize(int expectedSize) {
        if (expectedSize <= 16) {
            return toSet();
        }
        return Collector.of(new ObjectCollections.SizeDecreasingSupplier(expectedSize, size -> {
            if (size <= 16) {
                return new ObjectLinkedOpenHashSet();
            }
            return new ObjectLinkedOpenHashSet(size);
        }), (v0, v1) -> {
            v0.add(v1);
        }, (v0, v1) -> {
            return v0.combine(v1);
        }, new Collector.Characteristics[0]);
    }

    private int realSize() {
        return this.containsNull ? this.size - 1 : this.size;
    }

    private void ensureCapacity(int capacity) {
        int needed = HashCommon.arraySize(capacity, this.f162f);
        if (needed > this.f161n) {
            rehash(needed);
        }
    }

    private void tryCapacity(long capacity) {
        int needed = (int) Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long) Math.ceil(((float) capacity) / this.f162f))));
        if (needed > this.f161n) {
            rehash(needed);
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean addAll(Collection<? extends K> c) {
        if (this.f162f <= 0.5d) {
            ensureCapacity(c.size());
        } else {
            tryCapacity(size() + c.size());
        }
        return super.addAll(c);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean add(K k) {
        int pos;
        K curr;
        if (k == null) {
            if (this.containsNull) {
                return false;
            }
            pos = this.f161n;
            this.containsNull = true;
        } else {
            K[] key = this.key;
            int mix = HashCommon.mix(k.hashCode()) & this.mask;
            pos = mix;
            K curr2 = key[mix];
            if (curr2 != null) {
                if (curr2.equals(k)) {
                    return false;
                }
                do {
                    int i = (pos + 1) & this.mask;
                    pos = i;
                    curr = key[i];
                    if (curr != null) {
                    }
                } while (!curr.equals(k));
                return false;
            }
            key[pos] = k;
        }
        if (this.size == 0) {
            int i2 = pos;
            this.last = i2;
            this.first = i2;
            this.link[pos] = -1;
        } else {
            long[] jArr = this.link;
            int i3 = this.last;
            jArr[i3] = jArr[i3] ^ ((this.link[this.last] ^ (pos & JSType.MAX_UINT)) & JSType.MAX_UINT);
            this.link[pos] = ((this.last & JSType.MAX_UINT) << 32) | JSType.MAX_UINT;
            this.last = pos;
        }
        int i4 = this.size;
        this.size = i4 + 1;
        if (i4 >= this.maxFill) {
            rehash(HashCommon.arraySize(this.size + 1, this.f162f));
            return true;
        }
        return true;
    }

    public K addOrGet(K k) {
        int pos;
        K curr;
        if (k == null) {
            if (this.containsNull) {
                return this.key[this.f161n];
            }
            pos = this.f161n;
            this.containsNull = true;
        } else {
            K[] key = this.key;
            int mix = HashCommon.mix(k.hashCode()) & this.mask;
            pos = mix;
            K curr2 = key[mix];
            if (curr2 != null) {
                if (curr2.equals(k)) {
                    return curr2;
                }
                do {
                    int i = (pos + 1) & this.mask;
                    pos = i;
                    curr = key[i];
                    if (curr != null) {
                    }
                } while (!curr.equals(k));
                return curr;
            }
            key[pos] = k;
        }
        if (this.size == 0) {
            int i2 = pos;
            this.last = i2;
            this.first = i2;
            this.link[pos] = -1;
        } else {
            long[] jArr = this.link;
            int i3 = this.last;
            jArr[i3] = jArr[i3] ^ ((this.link[this.last] ^ (pos & JSType.MAX_UINT)) & JSType.MAX_UINT);
            this.link[pos] = ((this.last & JSType.MAX_UINT) << 32) | JSType.MAX_UINT;
            this.last = pos;
        }
        int i4 = this.size;
        this.size = i4 + 1;
        if (i4 >= this.maxFill) {
            rehash(HashCommon.arraySize(this.size + 1, this.f162f));
        }
        return k;
    }

    protected final void shiftKeys(int pos) {
        K curr;
        K[] key = this.key;
        while (true) {
            int last = pos;
            int i = last + 1;
            int i2 = this.mask;
            while (true) {
                pos = i & i2;
                curr = key[pos];
                if (curr == null) {
                    key[last] = null;
                    return;
                }
                int slot = HashCommon.mix(curr.hashCode()) & this.mask;
                if (last > pos) {
                    if (last >= slot && slot > pos) {
                        break;
                    }
                    i = pos + 1;
                    i2 = this.mask;
                } else if (last < slot && slot <= pos) {
                    i = pos + 1;
                    i2 = this.mask;
                }
            }
            key[last] = curr;
            fixPointers(pos, last);
        }
    }

    private boolean removeEntry(int pos) {
        this.size--;
        fixPointers(pos);
        shiftKeys(pos);
        if (this.f161n > this.minN && this.size < this.maxFill / 4 && this.f161n > 16) {
            rehash(this.f161n / 2);
            return true;
        }
        return true;
    }

    private boolean removeNullEntry() {
        this.containsNull = false;
        this.key[this.f161n] = null;
        this.size--;
        fixPointers(this.f161n);
        if (this.f161n > this.minN && this.size < this.maxFill / 4 && this.f161n > 16) {
            rehash(this.f161n / 2);
            return true;
        }
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean remove(Object k) {
        K curr;
        if (k == null) {
            if (this.containsNull) {
                return removeNullEntry();
            }
            return false;
        }
        K[] key = this.key;
        int mix = HashCommon.mix(k.hashCode()) & this.mask;
        int pos = mix;
        K curr2 = key[mix];
        if (curr2 == null) {
            return false;
        }
        if (k.equals(curr2)) {
            return removeEntry(pos);
        }
        do {
            int i = (pos + 1) & this.mask;
            pos = i;
            curr = key[i];
            if (curr == null) {
                return false;
            }
        } while (!k.equals(curr));
        return removeEntry(pos);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean contains(Object k) {
        K curr;
        if (k == null) {
            return this.containsNull;
        }
        K[] key = this.key;
        int mix = HashCommon.mix(k.hashCode()) & this.mask;
        int pos = mix;
        K curr2 = key[mix];
        if (curr2 == null) {
            return false;
        }
        if (k.equals(curr2)) {
            return true;
        }
        do {
            int i = (pos + 1) & this.mask;
            pos = i;
            curr = key[i];
            if (curr == null) {
                return false;
            }
        } while (!k.equals(curr));
        return true;
    }

    public K get(Object k) {
        K curr;
        if (k == null) {
            return this.key[this.f161n];
        }
        K[] key = this.key;
        int mix = HashCommon.mix(k.hashCode()) & this.mask;
        int pos = mix;
        K curr2 = key[mix];
        if (curr2 == null) {
            return null;
        }
        if (k.equals(curr2)) {
            return curr2;
        }
        do {
            int i = (pos + 1) & this.mask;
            pos = i;
            curr = key[i];
            if (curr == null) {
                return null;
            }
        } while (!k.equals(curr));
        return curr;
    }

    public K removeFirst() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        int pos = this.first;
        this.first = (int) this.link[pos];
        if (0 <= this.first) {
            long[] jArr = this.link;
            int i = this.first;
            jArr[i] = jArr[i] | (-4294967296L);
        }
        K k = this.key[pos];
        this.size--;
        if (k == null) {
            this.containsNull = false;
            this.key[this.f161n] = null;
        } else {
            shiftKeys(pos);
        }
        if (this.f161n > this.minN && this.size < this.maxFill / 4 && this.f161n > 16) {
            rehash(this.f161n / 2);
        }
        return k;
    }

    public K removeLast() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        int pos = this.last;
        this.last = (int) (this.link[pos] >>> 32);
        if (0 <= this.last) {
            long[] jArr = this.link;
            int i = this.last;
            jArr[i] = jArr[i] | JSType.MAX_UINT;
        }
        K k = this.key[pos];
        this.size--;
        if (k == null) {
            this.containsNull = false;
            this.key[this.f161n] = null;
        } else {
            shiftKeys(pos);
        }
        if (this.f161n > this.minN && this.size < this.maxFill / 4 && this.f161n > 16) {
            rehash(this.f161n / 2);
        }
        return k;
    }

    private void moveIndexToFirst(int i) {
        if (this.size == 1 || this.first == i) {
            return;
        }
        if (this.last == i) {
            this.last = (int) (this.link[i] >>> 32);
            long[] jArr = this.link;
            int i2 = this.last;
            jArr[i2] = jArr[i2] | JSType.MAX_UINT;
        } else {
            long linki = this.link[i];
            int prev = (int) (linki >>> 32);
            int next = (int) linki;
            long[] jArr2 = this.link;
            jArr2[prev] = jArr2[prev] ^ ((this.link[prev] ^ (linki & JSType.MAX_UINT)) & JSType.MAX_UINT);
            long[] jArr3 = this.link;
            jArr3[next] = jArr3[next] ^ ((this.link[next] ^ (linki & (-4294967296L))) & (-4294967296L));
        }
        long[] jArr4 = this.link;
        int i3 = this.first;
        jArr4[i3] = jArr4[i3] ^ ((this.link[this.first] ^ ((i & JSType.MAX_UINT) << 32)) & (-4294967296L));
        this.link[i] = (-4294967296L) | (this.first & JSType.MAX_UINT);
        this.first = i;
    }

    private void moveIndexToLast(int i) {
        if (this.size == 1 || this.last == i) {
            return;
        }
        if (this.first == i) {
            this.first = (int) this.link[i];
            long[] jArr = this.link;
            int i2 = this.first;
            jArr[i2] = jArr[i2] | (-4294967296L);
        } else {
            long linki = this.link[i];
            int prev = (int) (linki >>> 32);
            int next = (int) linki;
            long[] jArr2 = this.link;
            jArr2[prev] = jArr2[prev] ^ ((this.link[prev] ^ (linki & JSType.MAX_UINT)) & JSType.MAX_UINT);
            long[] jArr3 = this.link;
            jArr3[next] = jArr3[next] ^ ((this.link[next] ^ (linki & (-4294967296L))) & (-4294967296L));
        }
        long[] jArr4 = this.link;
        int i3 = this.last;
        jArr4[i3] = jArr4[i3] ^ ((this.link[this.last] ^ (i & JSType.MAX_UINT)) & JSType.MAX_UINT);
        this.link[i] = ((this.last & JSType.MAX_UINT) << 32) | JSType.MAX_UINT;
        this.last = i;
    }

    public boolean addAndMoveToFirst(K k) {
        int pos;
        if (k == null) {
            if (this.containsNull) {
                moveIndexToFirst(this.f161n);
                return false;
            }
            this.containsNull = true;
            pos = this.f161n;
        } else {
            K[] key = this.key;
            int mix = HashCommon.mix(k.hashCode());
            int i = this.mask;
            while (true) {
                pos = mix & i;
                if (key[pos] == null) {
                    break;
                } else if (k.equals(key[pos])) {
                    moveIndexToFirst(pos);
                    return false;
                } else {
                    mix = pos + 1;
                    i = this.mask;
                }
            }
        }
        this.key[pos] = k;
        if (this.size == 0) {
            int i2 = pos;
            this.last = i2;
            this.first = i2;
            this.link[pos] = -1;
        } else {
            long[] jArr = this.link;
            int i3 = this.first;
            jArr[i3] = jArr[i3] ^ ((this.link[this.first] ^ ((pos & JSType.MAX_UINT) << 32)) & (-4294967296L));
            this.link[pos] = (-4294967296L) | (this.first & JSType.MAX_UINT);
            this.first = pos;
        }
        int i4 = this.size;
        this.size = i4 + 1;
        if (i4 >= this.maxFill) {
            rehash(HashCommon.arraySize(this.size, this.f162f));
            return true;
        }
        return true;
    }

    public boolean addAndMoveToLast(K k) {
        int pos;
        if (k == null) {
            if (this.containsNull) {
                moveIndexToLast(this.f161n);
                return false;
            }
            this.containsNull = true;
            pos = this.f161n;
        } else {
            K[] key = this.key;
            int mix = HashCommon.mix(k.hashCode());
            int i = this.mask;
            while (true) {
                pos = mix & i;
                if (key[pos] == null) {
                    break;
                } else if (k.equals(key[pos])) {
                    moveIndexToLast(pos);
                    return false;
                } else {
                    mix = pos + 1;
                    i = this.mask;
                }
            }
        }
        this.key[pos] = k;
        if (this.size == 0) {
            int i2 = pos;
            this.last = i2;
            this.first = i2;
            this.link[pos] = -1;
        } else {
            long[] jArr = this.link;
            int i3 = this.last;
            jArr[i3] = jArr[i3] ^ ((this.link[this.last] ^ (pos & JSType.MAX_UINT)) & JSType.MAX_UINT);
            this.link[pos] = ((this.last & JSType.MAX_UINT) << 32) | JSType.MAX_UINT;
            this.last = pos;
        }
        int i4 = this.size;
        this.size = i4 + 1;
        if (i4 >= this.maxFill) {
            rehash(HashCommon.arraySize(this.size, this.f162f));
            return true;
        }
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public void clear() {
        if (this.size == 0) {
            return;
        }
        this.size = 0;
        this.containsNull = false;
        Arrays.fill(this.key, (Object) null);
        this.last = -1;
        this.first = -1;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        return this.size;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean isEmpty() {
        return this.size == 0;
    }

    protected void fixPointers(int i) {
        if (this.size == 0) {
            this.last = -1;
            this.first = -1;
        } else if (this.first == i) {
            this.first = (int) this.link[i];
            if (0 <= this.first) {
                long[] jArr = this.link;
                int i2 = this.first;
                jArr[i2] = jArr[i2] | (-4294967296L);
            }
        } else if (this.last == i) {
            this.last = (int) (this.link[i] >>> 32);
            if (0 <= this.last) {
                long[] jArr2 = this.link;
                int i3 = this.last;
                jArr2[i3] = jArr2[i3] | JSType.MAX_UINT;
            }
        } else {
            long linki = this.link[i];
            int prev = (int) (linki >>> 32);
            int next = (int) linki;
            long[] jArr3 = this.link;
            jArr3[prev] = jArr3[prev] ^ ((this.link[prev] ^ (linki & JSType.MAX_UINT)) & JSType.MAX_UINT);
            long[] jArr4 = this.link;
            jArr4[next] = jArr4[next] ^ ((this.link[next] ^ (linki & (-4294967296L))) & (-4294967296L));
        }
    }

    protected void fixPointers(int s, int d) {
        if (this.size == 1) {
            this.last = d;
            this.first = d;
            this.link[d] = -1;
        } else if (this.first == s) {
            this.first = d;
            long[] jArr = this.link;
            int i = (int) this.link[s];
            jArr[i] = jArr[i] ^ ((this.link[(int) this.link[s]] ^ ((d & JSType.MAX_UINT) << 32)) & (-4294967296L));
            this.link[d] = this.link[s];
        } else if (this.last == s) {
            this.last = d;
            long[] jArr2 = this.link;
            int i2 = (int) (this.link[s] >>> 32);
            jArr2[i2] = jArr2[i2] ^ ((this.link[(int) (this.link[s] >>> 32)] ^ (d & JSType.MAX_UINT)) & JSType.MAX_UINT);
            this.link[d] = this.link[s];
        } else {
            long links = this.link[s];
            int prev = (int) (links >>> 32);
            int next = (int) links;
            long[] jArr3 = this.link;
            jArr3[prev] = jArr3[prev] ^ ((this.link[prev] ^ (d & JSType.MAX_UINT)) & JSType.MAX_UINT);
            long[] jArr4 = this.link;
            jArr4[next] = jArr4[next] ^ ((this.link[next] ^ ((d & JSType.MAX_UINT) << 32)) & (-4294967296L));
            this.link[d] = links;
        }
    }

    @Override // java.util.SortedSet
    public K first() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.key[this.first];
    }

    @Override // java.util.SortedSet
    public K last() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.key[this.last];
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSet, java.util.SortedSet
    public ObjectSortedSet<K> tailSet(K from) {
        throw new UnsupportedOperationException();
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSet, java.util.SortedSet
    public ObjectSortedSet<K> headSet(K to) {
        throw new UnsupportedOperationException();
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSet, java.util.SortedSet
    public ObjectSortedSet<K> subSet(K from, K to) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.SortedSet
    public Comparator<? super K> comparator() {
        return null;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectLinkedOpenHashSet$SetIterator.class */
    public final class SetIterator implements ObjectListIterator<K> {
        int prev;
        int next;
        int curr;
        int index;

        SetIterator() {
            ObjectLinkedOpenHashSet.this = r4;
            this.prev = -1;
            this.next = -1;
            this.curr = -1;
            this.index = -1;
            this.next = r4.first;
            this.index = 0;
        }

        SetIterator(K from) {
            ObjectLinkedOpenHashSet.this = r6;
            this.prev = -1;
            this.next = -1;
            this.curr = -1;
            this.index = -1;
            if (from == null) {
                if (r6.containsNull) {
                    this.next = (int) r6.link[r6.f161n];
                    this.prev = r6.f161n;
                    return;
                }
                throw new NoSuchElementException("The key " + from + " does not belong to this set.");
            } else if (Objects.equals(r6.key[r6.last], from)) {
                this.prev = r6.last;
                this.index = r6.size;
            } else {
                K[] key = r6.key;
                int mix = HashCommon.mix(from.hashCode());
                int i = r6.mask;
                while (true) {
                    int pos = mix & i;
                    if (key[pos] != null) {
                        if (key[pos].equals(from)) {
                            this.next = (int) r6.link[pos];
                            this.prev = pos;
                            return;
                        }
                        mix = pos + 1;
                        i = r6.mask;
                    } else {
                        throw new NoSuchElementException("The key " + from + " does not belong to this set.");
                    }
                }
            }
        }

        @Override // java.util.Iterator, java.util.ListIterator
        public boolean hasNext() {
            return this.next != -1;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.BidirectionalIterator
        public boolean hasPrevious() {
            return this.prev != -1;
        }

        @Override // java.util.Iterator, java.util.ListIterator
        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            this.curr = this.next;
            this.next = (int) ObjectLinkedOpenHashSet.this.link[this.curr];
            this.prev = this.curr;
            if (this.index >= 0) {
                this.index++;
            }
            return ObjectLinkedOpenHashSet.this.key[this.curr];
        }

        @Override // com.viaversion.viaversion.libs.fastutil.BidirectionalIterator, java.util.ListIterator
        public K previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            this.curr = this.prev;
            this.prev = (int) (ObjectLinkedOpenHashSet.this.link[this.curr] >>> 32);
            this.next = this.curr;
            if (this.index >= 0) {
                this.index--;
            }
            return ObjectLinkedOpenHashSet.this.key[this.curr];
        }

        @Override // java.util.Iterator
        public void forEachRemaining(Consumer<? super K> action) {
            K[] key = ObjectLinkedOpenHashSet.this.key;
            long[] link = ObjectLinkedOpenHashSet.this.link;
            while (this.next != -1) {
                this.curr = this.next;
                this.next = (int) link[this.curr];
                this.prev = this.curr;
                if (this.index >= 0) {
                    this.index++;
                }
                action.accept((Object) key[this.curr]);
            }
        }

        private final void ensureIndexKnown() {
            if (this.index >= 0) {
                return;
            }
            if (this.prev == -1) {
                this.index = 0;
            } else if (this.next == -1) {
                this.index = ObjectLinkedOpenHashSet.this.size;
            } else {
                int pos = ObjectLinkedOpenHashSet.this.first;
                this.index = 1;
                while (pos != this.prev) {
                    pos = (int) ObjectLinkedOpenHashSet.this.link[pos];
                    this.index++;
                }
            }
        }

        @Override // java.util.ListIterator
        public int nextIndex() {
            ensureIndexKnown();
            return this.index;
        }

        @Override // java.util.ListIterator
        public int previousIndex() {
            ensureIndexKnown();
            return this.index - 1;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectListIterator, java.util.Iterator, java.util.ListIterator
        public void remove() {
            K curr;
            ensureIndexKnown();
            if (this.curr == -1) {
                throw new IllegalStateException();
            }
            if (this.curr == this.prev) {
                this.index--;
                this.prev = (int) (ObjectLinkedOpenHashSet.this.link[this.curr] >>> 32);
            } else {
                this.next = (int) ObjectLinkedOpenHashSet.this.link[this.curr];
            }
            ObjectLinkedOpenHashSet.this.size--;
            if (this.prev == -1) {
                ObjectLinkedOpenHashSet.this.first = this.next;
            } else {
                long[] jArr = ObjectLinkedOpenHashSet.this.link;
                int i = this.prev;
                jArr[i] = jArr[i] ^ ((ObjectLinkedOpenHashSet.this.link[this.prev] ^ (this.next & JSType.MAX_UINT)) & JSType.MAX_UINT);
            }
            if (this.next == -1) {
                ObjectLinkedOpenHashSet.this.last = this.prev;
            } else {
                long[] jArr2 = ObjectLinkedOpenHashSet.this.link;
                int i2 = this.next;
                jArr2[i2] = jArr2[i2] ^ ((ObjectLinkedOpenHashSet.this.link[this.next] ^ ((this.prev & JSType.MAX_UINT) << 32)) & (-4294967296L));
            }
            int pos = this.curr;
            this.curr = -1;
            if (pos == ObjectLinkedOpenHashSet.this.f161n) {
                ObjectLinkedOpenHashSet.this.containsNull = false;
                ObjectLinkedOpenHashSet.this.key[ObjectLinkedOpenHashSet.this.f161n] = null;
                return;
            }
            K[] key = ObjectLinkedOpenHashSet.this.key;
            while (true) {
                int last = pos;
                int i3 = last + 1;
                int i4 = ObjectLinkedOpenHashSet.this.mask;
                while (true) {
                    pos = i3 & i4;
                    curr = key[pos];
                    if (curr == null) {
                        key[last] = null;
                        return;
                    }
                    int slot = HashCommon.mix(curr.hashCode()) & ObjectLinkedOpenHashSet.this.mask;
                    if (last > pos) {
                        if (last >= slot && slot > pos) {
                            break;
                        }
                        i3 = pos + 1;
                        i4 = ObjectLinkedOpenHashSet.this.mask;
                    } else if (last >= slot || slot > pos) {
                        break;
                    } else {
                        i3 = pos + 1;
                        i4 = ObjectLinkedOpenHashSet.this.mask;
                    }
                }
                key[last] = curr;
                if (this.next == pos) {
                    this.next = last;
                }
                if (this.prev == pos) {
                    this.prev = last;
                }
                ObjectLinkedOpenHashSet.this.fixPointers(pos, last);
            }
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSet
    public ObjectListIterator<K> iterator(K from) {
        return new SetIterator(from);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSortedSet, com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet, com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable
    public ObjectListIterator<K> iterator() {
        return new SetIterator();
    }

    @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectSet, java.util.Set
    public ObjectSpliterator<K> spliterator() {
        return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(this), 81);
    }

    @Override // java.lang.Iterable
    public void forEach(Consumer<? super K> action) {
        int next = this.first;
        while (next != -1) {
            int curr = next;
            next = (int) this.link[curr];
            action.accept((Object) this.key[curr]);
        }
    }

    public boolean trim() {
        return trim(this.size);
    }

    public boolean trim(int n) {
        int l = HashCommon.nextPowerOfTwo((int) Math.ceil(n / this.f162f));
        if (l >= this.f161n || this.size > HashCommon.maxFill(l, this.f162f)) {
            return true;
        }
        try {
            rehash(l);
            return true;
        } catch (OutOfMemoryError e) {
            return false;
        }
    }

    protected void rehash(int newN) {
        int pos;
        K[] key = this.key;
        int mask = newN - 1;
        K[] newKey = (K[]) new Object[newN + 1];
        int i = this.first;
        int prev = -1;
        int newPrev = -1;
        long[] link = this.link;
        long[] newLink = new long[newN + 1];
        this.first = -1;
        int j = this.size;
        while (true) {
            int i2 = j;
            j--;
            if (i2 == 0) {
                break;
            }
            if (key[i] == null) {
                pos = newN;
            } else {
                int mix = HashCommon.mix(key[i].hashCode());
                while (true) {
                    pos = mix & mask;
                    if (newKey[pos] == null) {
                        break;
                    }
                    mix = pos + 1;
                }
            }
            newKey[pos] = key[i];
            if (prev != -1) {
                int i3 = newPrev;
                newLink[i3] = newLink[i3] ^ ((newLink[newPrev] ^ (pos & JSType.MAX_UINT)) & JSType.MAX_UINT);
                int i4 = pos;
                newLink[i4] = newLink[i4] ^ ((newLink[pos] ^ ((newPrev & JSType.MAX_UINT) << 32)) & (-4294967296L));
                newPrev = pos;
            } else {
                int i5 = pos;
                this.first = i5;
                newPrev = i5;
                newLink[pos] = -1;
            }
            int t = i;
            i = (int) link[i];
            prev = t;
        }
        this.link = newLink;
        this.last = newPrev;
        if (newPrev != -1) {
            int i6 = newPrev;
            newLink[i6] = newLink[i6] | JSType.MAX_UINT;
        }
        this.f161n = newN;
        this.mask = mask;
        this.maxFill = HashCommon.maxFill(this.f161n, this.f162f);
        this.key = newKey;
    }

    public ObjectLinkedOpenHashSet<K> clone() {
        try {
            ObjectLinkedOpenHashSet<K> c = (ObjectLinkedOpenHashSet) super.clone();
            c.key = (K[]) ((Object[]) this.key.clone());
            c.containsNull = this.containsNull;
            c.link = (long[]) this.link.clone();
            return c;
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet, java.util.Collection, java.util.Set
    public int hashCode() {
        int h = 0;
        int j = realSize();
        int i = 0;
        while (true) {
            int i2 = j;
            j--;
            if (i2 != 0) {
                while (this.key[i] == null) {
                    i++;
                }
                if (this != this.key[i]) {
                    h += this.key[i].hashCode();
                }
                i++;
            } else {
                return h;
            }
        }
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        ObjectIterator<K> i = iterator();
        s.defaultWriteObject();
        int j = this.size;
        while (true) {
            int i2 = j;
            j--;
            if (i2 != 0) {
                s.writeObject(i.next());
            } else {
                return;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        int pos;
        int i;
        s.defaultReadObject();
        this.f161n = HashCommon.arraySize(this.size, this.f162f);
        this.maxFill = HashCommon.maxFill(this.f161n, this.f162f);
        this.mask = this.f161n - 1;
        K[] key = (K[]) new Object[this.f161n + 1];
        this.key = key;
        long[] link = new long[this.f161n + 1];
        this.link = link;
        int prev = -1;
        this.last = -1;
        this.first = -1;
        int i2 = this.size;
        while (true) {
            int i3 = i2;
            i2--;
            if (i3 == 0) {
                break;
            }
            Object readObject = s.readObject();
            if (readObject == null) {
                pos = this.f161n;
                this.containsNull = true;
            } else {
                int mix = HashCommon.mix(readObject.hashCode()) & this.mask;
                pos = mix;
                if (key[mix] != 0) {
                    do {
                        i = (pos + 1) & this.mask;
                        pos = i;
                    } while (key[i] != 0);
                }
            }
            key[pos] = readObject;
            if (this.first != -1) {
                int i4 = prev;
                link[i4] = link[i4] ^ ((link[prev] ^ (pos & JSType.MAX_UINT)) & JSType.MAX_UINT);
                int i5 = pos;
                link[i5] = link[i5] ^ ((link[pos] ^ ((prev & JSType.MAX_UINT) << 32)) & (-4294967296L));
                prev = pos;
            } else {
                int i6 = pos;
                this.first = i6;
                prev = i6;
                int i7 = pos;
                link[i7] = link[i7] | (-4294967296L);
            }
        }
        this.last = prev;
        if (prev != -1) {
            int i8 = prev;
            link[i8] = link[i8] | JSType.MAX_UINT;
        }
    }

    private void checkTable() {
    }
}
