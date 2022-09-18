package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Hash;
import com.viaversion.viaversion.libs.fastutil.HashCommon;
import com.viaversion.viaversion.libs.fastutil.Size64;
import com.viaversion.viaversion.libs.fastutil.ints.IntCollections;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.stream.IntStream;
import jdk.nashorn.internal.runtime.JSType;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntLinkedOpenHashSet.class */
public class IntLinkedOpenHashSet extends AbstractIntSortedSet implements Serializable, Cloneable, Hash {
    private static final long serialVersionUID = 0;
    private static final boolean ASSERTS = false;
    protected transient int[] key;
    protected transient int mask;
    protected transient boolean containsNull;
    protected transient int first;
    protected transient int last;
    protected transient long[] link;

    /* renamed from: n */
    protected transient int f111n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;

    /* renamed from: f */
    protected final float f112f;
    private static final int SPLITERATOR_CHARACTERISTICS = 337;

    public IntLinkedOpenHashSet(int expected, float f) {
        this.first = -1;
        this.last = -1;
        if (f <= 0.0f || f >= 1.0f) {
            throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1");
        }
        if (expected < 0) {
            throw new IllegalArgumentException("The expected number of elements must be nonnegative");
        }
        this.f112f = f;
        int arraySize = HashCommon.arraySize(expected, f);
        this.f111n = arraySize;
        this.minN = arraySize;
        this.mask = this.f111n - 1;
        this.maxFill = HashCommon.maxFill(this.f111n, f);
        this.key = new int[this.f111n + 1];
        this.link = new long[this.f111n + 1];
    }

    public IntLinkedOpenHashSet(int expected) {
        this(expected, 0.75f);
    }

    public IntLinkedOpenHashSet() {
        this(16, 0.75f);
    }

    public IntLinkedOpenHashSet(Collection<? extends Integer> c, float f) {
        this(c.size(), f);
        addAll(c);
    }

    public IntLinkedOpenHashSet(Collection<? extends Integer> c) {
        this(c, 0.75f);
    }

    public IntLinkedOpenHashSet(IntCollection c, float f) {
        this(c.size(), f);
        addAll(c);
    }

    public IntLinkedOpenHashSet(IntCollection c) {
        this(c, 0.75f);
    }

    public IntLinkedOpenHashSet(IntIterator i, float f) {
        this(16, f);
        while (i.hasNext()) {
            add(i.nextInt());
        }
    }

    public IntLinkedOpenHashSet(IntIterator i) {
        this(i, 0.75f);
    }

    public IntLinkedOpenHashSet(Iterator<?> i, float f) {
        this(IntIterators.asIntIterator(i), f);
    }

    public IntLinkedOpenHashSet(Iterator<?> i) {
        this(IntIterators.asIntIterator(i));
    }

    public IntLinkedOpenHashSet(int[] a, int offset, int length, float f) {
        this(length < 0 ? 0 : length, f);
        IntArrays.ensureOffsetLength(a, offset, length);
        for (int i = 0; i < length; i++) {
            add(a[offset + i]);
        }
    }

    public IntLinkedOpenHashSet(int[] a, int offset, int length) {
        this(a, offset, length, 0.75f);
    }

    public IntLinkedOpenHashSet(int[] a, float f) {
        this(a, 0, a.length, f);
    }

    public IntLinkedOpenHashSet(int[] a) {
        this(a, 0.75f);
    }

    /* renamed from: of */
    public static IntLinkedOpenHashSet m206of() {
        return new IntLinkedOpenHashSet();
    }

    /* renamed from: of */
    public static IntLinkedOpenHashSet m205of(int e) {
        IntLinkedOpenHashSet result = new IntLinkedOpenHashSet(1, 0.75f);
        result.add(e);
        return result;
    }

    /* renamed from: of */
    public static IntLinkedOpenHashSet m204of(int e0, int e1) {
        IntLinkedOpenHashSet result = new IntLinkedOpenHashSet(2, 0.75f);
        result.add(e0);
        if (!result.add(e1)) {
            throw new IllegalArgumentException("Duplicate element: " + e1);
        }
        return result;
    }

    /* renamed from: of */
    public static IntLinkedOpenHashSet m203of(int e0, int e1, int e2) {
        IntLinkedOpenHashSet result = new IntLinkedOpenHashSet(3, 0.75f);
        result.add(e0);
        if (!result.add(e1)) {
            throw new IllegalArgumentException("Duplicate element: " + e1);
        }
        if (!result.add(e2)) {
            throw new IllegalArgumentException("Duplicate element: " + e2);
        }
        return result;
    }

    /* renamed from: of */
    public static IntLinkedOpenHashSet m202of(int... a) {
        IntLinkedOpenHashSet result = new IntLinkedOpenHashSet(a.length, 0.75f);
        for (int element : a) {
            if (!result.add(element)) {
                throw new IllegalArgumentException("Duplicate element " + element);
            }
        }
        return result;
    }

    public static IntLinkedOpenHashSet toSet(IntStream stream) {
        return (IntLinkedOpenHashSet) stream.collect(IntLinkedOpenHashSet::new, (v0, v1) -> {
            v0.add(v1);
        }, (v0, v1) -> {
            v0.addAll(v1);
        });
    }

    public static IntLinkedOpenHashSet toSetWithExpectedSize(IntStream stream, int expectedSize) {
        if (expectedSize <= 16) {
            return toSet(stream);
        }
        return (IntLinkedOpenHashSet) stream.collect(new IntCollections.SizeDecreasingSupplier(expectedSize, size -> {
            if (size <= 16) {
                return new IntLinkedOpenHashSet();
            }
            return new IntLinkedOpenHashSet(size);
        }), (v0, v1) -> {
            v0.add(v1);
        }, (v0, v1) -> {
            v0.addAll(v1);
        });
    }

    private int realSize() {
        return this.containsNull ? this.size - 1 : this.size;
    }

    private void ensureCapacity(int capacity) {
        int needed = HashCommon.arraySize(capacity, this.f112f);
        if (needed > this.f111n) {
            rehash(needed);
        }
    }

    private void tryCapacity(long capacity) {
        int needed = (int) Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long) Math.ceil(((float) capacity) / this.f112f))));
        if (needed > this.f111n) {
            rehash(needed);
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
    public boolean addAll(IntCollection c) {
        if (this.f112f <= 0.5d) {
            ensureCapacity(c.size());
        } else {
            tryCapacity(size() + c.size());
        }
        return super.addAll(c);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection
    public boolean addAll(Collection<? extends Integer> c) {
        if (this.f112f <= 0.5d) {
            ensureCapacity(c.size());
        } else {
            tryCapacity(size() + c.size());
        }
        return super.addAll(c);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
    public boolean add(int k) {
        int pos;
        int curr;
        if (k == 0) {
            if (this.containsNull) {
                return false;
            }
            pos = this.f111n;
            this.containsNull = true;
        } else {
            int[] key = this.key;
            int mix = HashCommon.mix(k) & this.mask;
            pos = mix;
            int curr2 = key[mix];
            if (curr2 != 0) {
                if (curr2 == k) {
                    return false;
                }
                do {
                    int i = (pos + 1) & this.mask;
                    pos = i;
                    curr = key[i];
                    if (curr != 0) {
                    }
                } while (curr != k);
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
            rehash(HashCommon.arraySize(this.size + 1, this.f112f));
            return true;
        }
        return true;
    }

    protected final void shiftKeys(int pos) {
        int curr;
        int[] key = this.key;
        while (true) {
            int last = pos;
            int i = last + 1;
            int i2 = this.mask;
            while (true) {
                pos = i & i2;
                curr = key[pos];
                if (curr == 0) {
                    key[last] = 0;
                    return;
                }
                int slot = HashCommon.mix(curr) & this.mask;
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
        if (this.f111n > this.minN && this.size < this.maxFill / 4 && this.f111n > 16) {
            rehash(this.f111n / 2);
            return true;
        }
        return true;
    }

    private boolean removeNullEntry() {
        this.containsNull = false;
        this.key[this.f111n] = 0;
        this.size--;
        fixPointers(this.f111n);
        if (this.f111n > this.minN && this.size < this.maxFill / 4 && this.f111n > 16) {
            rehash(this.f111n / 2);
            return true;
        }
        return true;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntSet, com.viaversion.viaversion.libs.fastutil.ints.IntSet
    public boolean remove(int k) {
        int curr;
        if (k == 0) {
            if (this.containsNull) {
                return removeNullEntry();
            }
            return false;
        }
        int[] key = this.key;
        int mix = HashCommon.mix(k) & this.mask;
        int pos = mix;
        int curr2 = key[mix];
        if (curr2 == 0) {
            return false;
        }
        if (k == curr2) {
            return removeEntry(pos);
        }
        do {
            int i = (pos + 1) & this.mask;
            pos = i;
            curr = key[i];
            if (curr == 0) {
                return false;
            }
        } while (k != curr);
        return removeEntry(pos);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
    public boolean contains(int k) {
        int curr;
        if (k == 0) {
            return this.containsNull;
        }
        int[] key = this.key;
        int mix = HashCommon.mix(k) & this.mask;
        int pos = mix;
        int curr2 = key[mix];
        if (curr2 == 0) {
            return false;
        }
        if (k == curr2) {
            return true;
        }
        do {
            int i = (pos + 1) & this.mask;
            pos = i;
            curr = key[i];
            if (curr == 0) {
                return false;
            }
        } while (k != curr);
        return true;
    }

    public int removeFirstInt() {
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
        int k = this.key[pos];
        this.size--;
        if (k == 0) {
            this.containsNull = false;
            this.key[this.f111n] = 0;
        } else {
            shiftKeys(pos);
        }
        if (this.f111n > this.minN && this.size < this.maxFill / 4 && this.f111n > 16) {
            rehash(this.f111n / 2);
        }
        return k;
    }

    public int removeLastInt() {
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
        int k = this.key[pos];
        this.size--;
        if (k == 0) {
            this.containsNull = false;
            this.key[this.f111n] = 0;
        } else {
            shiftKeys(pos);
        }
        if (this.f111n > this.minN && this.size < this.maxFill / 4 && this.f111n > 16) {
            rehash(this.f111n / 2);
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

    public boolean addAndMoveToFirst(int k) {
        int pos;
        if (k == 0) {
            if (this.containsNull) {
                moveIndexToFirst(this.f111n);
                return false;
            }
            this.containsNull = true;
            pos = this.f111n;
        } else {
            int[] key = this.key;
            int mix = HashCommon.mix(k);
            int i = this.mask;
            while (true) {
                pos = mix & i;
                if (key[pos] == 0) {
                    break;
                } else if (k == key[pos]) {
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
            rehash(HashCommon.arraySize(this.size, this.f112f));
            return true;
        }
        return true;
    }

    public boolean addAndMoveToLast(int k) {
        int pos;
        if (k == 0) {
            if (this.containsNull) {
                moveIndexToLast(this.f111n);
                return false;
            }
            this.containsNull = true;
            pos = this.f111n;
        } else {
            int[] key = this.key;
            int mix = HashCommon.mix(k);
            int i = this.mask;
            while (true) {
                pos = mix & i;
                if (key[pos] == 0) {
                    break;
                } else if (k == key[pos]) {
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
            rehash(HashCommon.arraySize(this.size, this.f112f));
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
        Arrays.fill(this.key, 0);
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

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet
    public int firstInt() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.key[this.first];
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet
    public int lastInt() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.key[this.last];
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet
    public IntSortedSet tailSet(int from) {
        throw new UnsupportedOperationException();
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet
    public IntSortedSet headSet(int to) {
        throw new UnsupportedOperationException();
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet
    public IntSortedSet subSet(int from, int to) {
        throw new UnsupportedOperationException();
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet, java.util.SortedSet
    public IntComparator comparator() {
        return null;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntLinkedOpenHashSet$SetIterator.class */
    public final class SetIterator implements IntListIterator {
        int prev;
        int next;
        int curr;
        int index;

        SetIterator() {
            IntLinkedOpenHashSet.this = r4;
            this.prev = -1;
            this.next = -1;
            this.curr = -1;
            this.index = -1;
            this.next = r4.first;
            this.index = 0;
        }

        SetIterator(int from) {
            IntLinkedOpenHashSet.this = r6;
            this.prev = -1;
            this.next = -1;
            this.curr = -1;
            this.index = -1;
            if (from == 0) {
                if (r6.containsNull) {
                    this.next = (int) r6.link[r6.f111n];
                    this.prev = r6.f111n;
                    return;
                }
                throw new NoSuchElementException("The key " + from + " does not belong to this set.");
            } else if (r6.key[r6.last] == from) {
                this.prev = r6.last;
                this.index = r6.size;
            } else {
                int[] key = r6.key;
                int mix = HashCommon.mix(from);
                int i = r6.mask;
                while (true) {
                    int pos = mix & i;
                    if (key[pos] != 0) {
                        if (key[pos] == from) {
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

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
        public int nextInt() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            this.curr = this.next;
            this.next = (int) IntLinkedOpenHashSet.this.link[this.curr];
            this.prev = this.curr;
            if (this.index >= 0) {
                this.index++;
            }
            return IntLinkedOpenHashSet.this.key[this.curr];
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntBidirectionalIterator
        public int previousInt() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            this.curr = this.prev;
            this.prev = (int) (IntLinkedOpenHashSet.this.link[this.curr] >>> 32);
            this.next = this.curr;
            if (this.index >= 0) {
                this.index--;
            }
            return IntLinkedOpenHashSet.this.key[this.curr];
        }

        @Override // java.util.PrimitiveIterator.OfInt
        public void forEachRemaining(java.util.function.IntConsumer action) {
            int[] key = IntLinkedOpenHashSet.this.key;
            long[] link = IntLinkedOpenHashSet.this.link;
            while (this.next != -1) {
                this.curr = this.next;
                this.next = (int) link[this.curr];
                this.prev = this.curr;
                if (this.index >= 0) {
                    this.index++;
                }
                action.accept(key[this.curr]);
            }
        }

        private final void ensureIndexKnown() {
            if (this.index >= 0) {
                return;
            }
            if (this.prev == -1) {
                this.index = 0;
            } else if (this.next == -1) {
                this.index = IntLinkedOpenHashSet.this.size;
            } else {
                int pos = IntLinkedOpenHashSet.this.first;
                this.index = 1;
                while (pos != this.prev) {
                    pos = (int) IntLinkedOpenHashSet.this.link[pos];
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

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntListIterator, java.util.Iterator, java.util.ListIterator
        public void remove() {
            int curr;
            ensureIndexKnown();
            if (this.curr == -1) {
                throw new IllegalStateException();
            }
            if (this.curr == this.prev) {
                this.index--;
                this.prev = (int) (IntLinkedOpenHashSet.this.link[this.curr] >>> 32);
            } else {
                this.next = (int) IntLinkedOpenHashSet.this.link[this.curr];
            }
            IntLinkedOpenHashSet.this.size--;
            if (this.prev == -1) {
                IntLinkedOpenHashSet.this.first = this.next;
            } else {
                long[] jArr = IntLinkedOpenHashSet.this.link;
                int i = this.prev;
                jArr[i] = jArr[i] ^ ((IntLinkedOpenHashSet.this.link[this.prev] ^ (this.next & JSType.MAX_UINT)) & JSType.MAX_UINT);
            }
            if (this.next == -1) {
                IntLinkedOpenHashSet.this.last = this.prev;
            } else {
                long[] jArr2 = IntLinkedOpenHashSet.this.link;
                int i2 = this.next;
                jArr2[i2] = jArr2[i2] ^ ((IntLinkedOpenHashSet.this.link[this.next] ^ ((this.prev & JSType.MAX_UINT) << 32)) & (-4294967296L));
            }
            int pos = this.curr;
            this.curr = -1;
            if (pos == IntLinkedOpenHashSet.this.f111n) {
                IntLinkedOpenHashSet.this.containsNull = false;
                IntLinkedOpenHashSet.this.key[IntLinkedOpenHashSet.this.f111n] = 0;
                return;
            }
            int[] key = IntLinkedOpenHashSet.this.key;
            while (true) {
                int last = pos;
                int i3 = last + 1;
                int i4 = IntLinkedOpenHashSet.this.mask;
                while (true) {
                    pos = i3 & i4;
                    curr = key[pos];
                    if (curr == 0) {
                        key[last] = 0;
                        return;
                    }
                    int slot = HashCommon.mix(curr) & IntLinkedOpenHashSet.this.mask;
                    if (last > pos) {
                        if (last >= slot && slot > pos) {
                            break;
                        }
                        i3 = pos + 1;
                        i4 = IntLinkedOpenHashSet.this.mask;
                    } else if (last >= slot || slot > pos) {
                        break;
                    } else {
                        i3 = pos + 1;
                        i4 = IntLinkedOpenHashSet.this.mask;
                    }
                }
                key[last] = curr;
                if (this.next == pos) {
                    this.next = last;
                }
                if (this.prev == pos) {
                    this.prev = last;
                }
                IntLinkedOpenHashSet.this.fixPointers(pos, last);
            }
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet
    public IntListIterator iterator(int from) {
        return new SetIterator(from);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntSortedSet, com.viaversion.viaversion.libs.fastutil.ints.AbstractIntSet, com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
    public Iterator<Integer> iterator() {
        return new SetIterator();
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.viaversion.viaversion.libs.fastutil.ints.IntListIterator, com.viaversion.viaversion.libs.fastutil.ints.IntIterator] */
    @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
    /* renamed from: spliterator */
    public Spliterator<Integer> spliterator2() {
        return IntSpliterators.asSpliterator(iterator(), Size64.sizeOf(this), 337);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterable
    public void forEach(java.util.function.IntConsumer action) {
        int next = this.first;
        while (next != -1) {
            int curr = next;
            next = (int) this.link[curr];
            action.accept(this.key[curr]);
        }
    }

    public boolean trim() {
        return trim(this.size);
    }

    public boolean trim(int n) {
        int l = HashCommon.nextPowerOfTwo((int) Math.ceil(n / this.f112f));
        if (l >= this.f111n || this.size > HashCommon.maxFill(l, this.f112f)) {
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
        int[] key = this.key;
        int mask = newN - 1;
        int[] newKey = new int[newN + 1];
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
            if (key[i] == 0) {
                pos = newN;
            } else {
                int mix = HashCommon.mix(key[i]);
                while (true) {
                    pos = mix & mask;
                    if (newKey[pos] == 0) {
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
        this.f111n = newN;
        this.mask = mask;
        this.maxFill = HashCommon.maxFill(this.f111n, this.f112f);
        this.key = newKey;
    }

    public IntLinkedOpenHashSet clone() {
        try {
            IntLinkedOpenHashSet c = (IntLinkedOpenHashSet) super.clone();
            c.key = (int[]) this.key.clone();
            c.containsNull = this.containsNull;
            c.link = (long[]) this.link.clone();
            return c;
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntSet, java.util.Collection, java.util.Set
    public int hashCode() {
        int h = 0;
        int j = realSize();
        int i = 0;
        while (true) {
            int i2 = j;
            j--;
            if (i2 != 0) {
                while (this.key[i] == 0) {
                    i++;
                }
                h += this.key[i];
                i++;
            } else {
                return h;
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.viaversion.viaversion.libs.fastutil.ints.IntListIterator, com.viaversion.viaversion.libs.fastutil.ints.IntIterator] */
    private void writeObject(ObjectOutputStream s) throws IOException {
        ?? it = iterator();
        s.defaultWriteObject();
        int j = this.size;
        while (true) {
            int i = j;
            j--;
            if (i != 0) {
                s.writeInt(it.nextInt());
            } else {
                return;
            }
        }
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        int pos;
        int i;
        s.defaultReadObject();
        this.f111n = HashCommon.arraySize(this.size, this.f112f);
        this.maxFill = HashCommon.maxFill(this.f111n, this.f112f);
        this.mask = this.f111n - 1;
        int[] key = new int[this.f111n + 1];
        this.key = key;
        long[] link = new long[this.f111n + 1];
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
            int k = s.readInt();
            if (k == 0) {
                pos = this.f111n;
                this.containsNull = true;
            } else {
                int mix = HashCommon.mix(k) & this.mask;
                pos = mix;
                if (key[mix] != 0) {
                    do {
                        i = (pos + 1) & this.mask;
                        pos = i;
                    } while (key[i] != 0);
                }
            }
            key[pos] = k;
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
