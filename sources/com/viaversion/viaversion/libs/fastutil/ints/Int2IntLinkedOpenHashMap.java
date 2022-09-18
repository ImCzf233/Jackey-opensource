package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Hash;
import com.viaversion.viaversion.libs.fastutil.HashCommon;
import com.viaversion.viaversion.libs.fastutil.Size64;
import com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2IntMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntSortedMap;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSortedSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectListIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import jdk.nashorn.internal.runtime.JSType;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2IntLinkedOpenHashMap.class */
public class Int2IntLinkedOpenHashMap extends AbstractInt2IntSortedMap implements Serializable, Cloneable, Hash {
    private static final long serialVersionUID = 0;
    private static final boolean ASSERTS = false;
    protected transient int[] key;
    protected transient int[] value;
    protected transient int mask;
    protected transient boolean containsNullKey;
    protected transient int first;
    protected transient int last;
    protected transient long[] link;

    /* renamed from: n */
    protected transient int f76n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;

    /* renamed from: f */
    protected final float f77f;
    protected transient Int2IntSortedMap.FastSortedEntrySet entries;
    protected transient IntSortedSet keys;
    protected transient IntCollection values;

    public Int2IntLinkedOpenHashMap(int expected, float f) {
        this.first = -1;
        this.last = -1;
        if (f <= 0.0f || f >= 1.0f) {
            throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1");
        }
        if (expected < 0) {
            throw new IllegalArgumentException("The expected number of elements must be nonnegative");
        }
        this.f77f = f;
        int arraySize = HashCommon.arraySize(expected, f);
        this.f76n = arraySize;
        this.minN = arraySize;
        this.mask = this.f76n - 1;
        this.maxFill = HashCommon.maxFill(this.f76n, f);
        this.key = new int[this.f76n + 1];
        this.value = new int[this.f76n + 1];
        this.link = new long[this.f76n + 1];
    }

    public Int2IntLinkedOpenHashMap(int expected) {
        this(expected, 0.75f);
    }

    public Int2IntLinkedOpenHashMap() {
        this(16, 0.75f);
    }

    public Int2IntLinkedOpenHashMap(Map<? extends Integer, ? extends Integer> m, float f) {
        this(m.size(), f);
        putAll(m);
    }

    public Int2IntLinkedOpenHashMap(Map<? extends Integer, ? extends Integer> m) {
        this(m, 0.75f);
    }

    public Int2IntLinkedOpenHashMap(Int2IntMap m, float f) {
        this(m.size(), f);
        putAll(m);
    }

    public Int2IntLinkedOpenHashMap(Int2IntMap m) {
        this(m, 0.75f);
    }

    public Int2IntLinkedOpenHashMap(int[] k, int[] v, float f) {
        this(k.length, f);
        if (k.length != v.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
        }
        for (int i = 0; i < k.length; i++) {
            put(k[i], v[i]);
        }
    }

    public Int2IntLinkedOpenHashMap(int[] k, int[] v) {
        this(k, v, 0.75f);
    }

    private int realSize() {
        return this.containsNullKey ? this.size - 1 : this.size;
    }

    private void ensureCapacity(int capacity) {
        int needed = HashCommon.arraySize(capacity, this.f77f);
        if (needed > this.f76n) {
            rehash(needed);
        }
    }

    private void tryCapacity(long capacity) {
        int needed = (int) Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long) Math.ceil(((float) capacity) / this.f77f))));
        if (needed > this.f76n) {
            rehash(needed);
        }
    }

    public int removeEntry(int pos) {
        int oldValue = this.value[pos];
        this.size--;
        fixPointers(pos);
        shiftKeys(pos);
        if (this.f76n > this.minN && this.size < this.maxFill / 4 && this.f76n > 16) {
            rehash(this.f76n / 2);
        }
        return oldValue;
    }

    public int removeNullEntry() {
        this.containsNullKey = false;
        int oldValue = this.value[this.f76n];
        this.size--;
        fixPointers(this.f76n);
        if (this.f76n > this.minN && this.size < this.maxFill / 4 && this.f76n > 16) {
            rehash(this.f76n / 2);
        }
        return oldValue;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2IntMap, java.util.Map
    public void putAll(Map<? extends Integer, ? extends Integer> m) {
        if (this.f77f <= 0.5d) {
            ensureCapacity(m.size());
        } else {
            tryCapacity(size() + m.size());
        }
        super.putAll(m);
    }

    private int find(int k) {
        int curr;
        if (k == 0) {
            return this.containsNullKey ? this.f76n : -(this.f76n + 1);
        }
        int[] key = this.key;
        int mix = HashCommon.mix(k) & this.mask;
        int pos = mix;
        int curr2 = key[mix];
        if (curr2 == 0) {
            return -(pos + 1);
        }
        if (k == curr2) {
            return pos;
        }
        do {
            int i = (pos + 1) & this.mask;
            pos = i;
            curr = key[i];
            if (curr == 0) {
                return -(pos + 1);
            }
        } while (k != curr);
        return pos;
    }

    private void insert(int pos, int k, int v) {
        if (pos == this.f76n) {
            this.containsNullKey = true;
        }
        this.key[pos] = k;
        this.value[pos] = v;
        if (this.size == 0) {
            this.last = pos;
            this.first = pos;
            this.link[pos] = -1;
        } else {
            long[] jArr = this.link;
            int i = this.last;
            jArr[i] = jArr[i] ^ ((this.link[this.last] ^ (pos & JSType.MAX_UINT)) & JSType.MAX_UINT);
            this.link[pos] = ((this.last & JSType.MAX_UINT) << 32) | JSType.MAX_UINT;
            this.last = pos;
        }
        int i2 = this.size;
        this.size = i2 + 1;
        if (i2 >= this.maxFill) {
            rehash(HashCommon.arraySize(this.size + 1, this.f77f));
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction
    public int put(int k, int v) {
        int pos = find(k);
        if (pos < 0) {
            insert((-pos) - 1, k, v);
            return this.defRetValue;
        }
        int oldValue = this.value[pos];
        this.value[pos] = v;
        return oldValue;
    }

    private int addToValue(int pos, int incr) {
        int oldValue = this.value[pos];
        this.value[pos] = oldValue + incr;
        return oldValue;
    }

    public int addTo(int k, int incr) {
        int pos;
        int curr;
        if (k == 0) {
            if (this.containsNullKey) {
                return addToValue(this.f76n, incr);
            }
            pos = this.f76n;
            this.containsNullKey = true;
        } else {
            int[] key = this.key;
            int mix = HashCommon.mix(k) & this.mask;
            pos = mix;
            int curr2 = key[mix];
            if (curr2 != 0) {
                if (curr2 == k) {
                    return addToValue(pos, incr);
                }
                do {
                    int i = (pos + 1) & this.mask;
                    pos = i;
                    curr = key[i];
                    if (curr != 0) {
                    }
                } while (curr != k);
                return addToValue(pos, incr);
            }
        }
        this.key[pos] = k;
        this.value[pos] = this.defRetValue + incr;
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
            rehash(HashCommon.arraySize(this.size + 1, this.f77f));
        }
        return this.defRetValue;
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
            this.value[last] = this.value[pos];
            fixPointers(pos, last);
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction
    public int remove(int k) {
        int curr;
        if (k == 0) {
            if (this.containsNullKey) {
                return removeNullEntry();
            }
            return this.defRetValue;
        }
        int[] key = this.key;
        int mix = HashCommon.mix(k) & this.mask;
        int pos = mix;
        int curr2 = key[mix];
        if (curr2 == 0) {
            return this.defRetValue;
        }
        if (k == curr2) {
            return removeEntry(pos);
        }
        do {
            int i = (pos + 1) & this.mask;
            pos = i;
            curr = key[i];
            if (curr == 0) {
                return this.defRetValue;
            }
        } while (k != curr);
        return removeEntry(pos);
    }

    private int setValue(int pos, int v) {
        int oldValue = this.value[pos];
        this.value[pos] = v;
        return oldValue;
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
        this.size--;
        int v = this.value[pos];
        if (pos == this.f76n) {
            this.containsNullKey = false;
        } else {
            shiftKeys(pos);
        }
        if (this.f76n > this.minN && this.size < this.maxFill / 4 && this.f76n > 16) {
            rehash(this.f76n / 2);
        }
        return v;
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
        this.size--;
        int v = this.value[pos];
        if (pos == this.f76n) {
            this.containsNullKey = false;
        } else {
            shiftKeys(pos);
        }
        if (this.f76n > this.minN && this.size < this.maxFill / 4 && this.f76n > 16) {
            rehash(this.f76n / 2);
        }
        return v;
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

    public int getAndMoveToFirst(int k) {
        int curr;
        if (k == 0) {
            if (this.containsNullKey) {
                moveIndexToFirst(this.f76n);
                return this.value[this.f76n];
            }
            return this.defRetValue;
        }
        int[] key = this.key;
        int mix = HashCommon.mix(k) & this.mask;
        int pos = mix;
        int curr2 = key[mix];
        if (curr2 == 0) {
            return this.defRetValue;
        }
        if (k == curr2) {
            moveIndexToFirst(pos);
            return this.value[pos];
        }
        do {
            int i = (pos + 1) & this.mask;
            pos = i;
            curr = key[i];
            if (curr == 0) {
                return this.defRetValue;
            }
        } while (k != curr);
        moveIndexToFirst(pos);
        return this.value[pos];
    }

    public int getAndMoveToLast(int k) {
        int curr;
        if (k == 0) {
            if (this.containsNullKey) {
                moveIndexToLast(this.f76n);
                return this.value[this.f76n];
            }
            return this.defRetValue;
        }
        int[] key = this.key;
        int mix = HashCommon.mix(k) & this.mask;
        int pos = mix;
        int curr2 = key[mix];
        if (curr2 == 0) {
            return this.defRetValue;
        }
        if (k == curr2) {
            moveIndexToLast(pos);
            return this.value[pos];
        }
        do {
            int i = (pos + 1) & this.mask;
            pos = i;
            curr = key[i];
            if (curr == 0) {
                return this.defRetValue;
            }
        } while (k != curr);
        moveIndexToLast(pos);
        return this.value[pos];
    }

    public int putAndMoveToFirst(int k, int v) {
        int pos;
        int curr;
        if (k == 0) {
            if (this.containsNullKey) {
                moveIndexToFirst(this.f76n);
                return setValue(this.f76n, v);
            }
            this.containsNullKey = true;
            pos = this.f76n;
        } else {
            int[] key = this.key;
            int mix = HashCommon.mix(k) & this.mask;
            pos = mix;
            int curr2 = key[mix];
            if (curr2 != 0) {
                if (curr2 == k) {
                    moveIndexToFirst(pos);
                    return setValue(pos, v);
                }
                do {
                    int i = (pos + 1) & this.mask;
                    pos = i;
                    curr = key[i];
                    if (curr != 0) {
                    }
                } while (curr != k);
                moveIndexToFirst(pos);
                return setValue(pos, v);
            }
        }
        this.key[pos] = k;
        this.value[pos] = v;
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
            rehash(HashCommon.arraySize(this.size, this.f77f));
        }
        return this.defRetValue;
    }

    public int putAndMoveToLast(int k, int v) {
        int pos;
        int curr;
        if (k == 0) {
            if (this.containsNullKey) {
                moveIndexToLast(this.f76n);
                return setValue(this.f76n, v);
            }
            this.containsNullKey = true;
            pos = this.f76n;
        } else {
            int[] key = this.key;
            int mix = HashCommon.mix(k) & this.mask;
            pos = mix;
            int curr2 = key[mix];
            if (curr2 != 0) {
                if (curr2 == k) {
                    moveIndexToLast(pos);
                    return setValue(pos, v);
                }
                do {
                    int i = (pos + 1) & this.mask;
                    pos = i;
                    curr = key[i];
                    if (curr != 0) {
                    }
                } while (curr != k);
                moveIndexToLast(pos);
                return setValue(pos, v);
            }
        }
        this.key[pos] = k;
        this.value[pos] = v;
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
            rehash(HashCommon.arraySize(this.size, this.f77f));
        }
        return this.defRetValue;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction
    public int get(int k) {
        int curr;
        if (k == 0) {
            return this.containsNullKey ? this.value[this.f76n] : this.defRetValue;
        }
        int[] key = this.key;
        int mix = HashCommon.mix(k) & this.mask;
        int pos = mix;
        int curr2 = key[mix];
        if (curr2 == 0) {
            return this.defRetValue;
        }
        if (k == curr2) {
            return this.value[pos];
        }
        do {
            int i = (pos + 1) & this.mask;
            pos = i;
            curr = key[i];
            if (curr == 0) {
                return this.defRetValue;
            }
        } while (k != curr);
        return this.value[pos];
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2IntMap, com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction, com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap
    public boolean containsKey(int k) {
        int curr;
        if (k == 0) {
            return this.containsNullKey;
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

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2IntMap, com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap
    public boolean containsValue(int v) {
        int[] value = this.value;
        int[] key = this.key;
        if (this.containsNullKey && value[this.f76n] == v) {
            return true;
        }
        int i = this.f76n;
        while (true) {
            int i2 = i;
            i--;
            if (i2 != 0) {
                if (key[i] != 0 && value[i] == v) {
                    return true;
                }
            } else {
                return false;
            }
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction
    public int getOrDefault(int k, int defaultValue) {
        int curr;
        if (k == 0) {
            return this.containsNullKey ? this.value[this.f76n] : defaultValue;
        }
        int[] key = this.key;
        int mix = HashCommon.mix(k) & this.mask;
        int pos = mix;
        int curr2 = key[mix];
        if (curr2 == 0) {
            return defaultValue;
        }
        if (k == curr2) {
            return this.value[pos];
        }
        do {
            int i = (pos + 1) & this.mask;
            pos = i;
            curr = key[i];
            if (curr == 0) {
                return defaultValue;
            }
        } while (k != curr);
        return this.value[pos];
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap
    public int putIfAbsent(int k, int v) {
        int pos = find(k);
        if (pos >= 0) {
            return this.value[pos];
        }
        insert((-pos) - 1, k, v);
        return this.defRetValue;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap
    public boolean remove(int k, int v) {
        if (k == 0) {
            if (this.containsNullKey && v == this.value[this.f76n]) {
                removeNullEntry();
                return true;
            }
            return false;
        }
        int[] key = this.key;
        int mix = HashCommon.mix(k) & this.mask;
        int pos = mix;
        int curr = key[mix];
        if (curr == 0) {
            return false;
        }
        if (k == curr && v == this.value[pos]) {
            removeEntry(pos);
            return true;
        }
        while (true) {
            int i = (pos + 1) & this.mask;
            pos = i;
            int curr2 = key[i];
            if (curr2 == 0) {
                return false;
            }
            if (k == curr2 && v == this.value[pos]) {
                removeEntry(pos);
                return true;
            }
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap
    public boolean replace(int k, int oldValue, int v) {
        int pos = find(k);
        if (pos < 0 || oldValue != this.value[pos]) {
            return false;
        }
        this.value[pos] = v;
        return true;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap
    public int replace(int k, int v) {
        int pos = find(k);
        if (pos < 0) {
            return this.defRetValue;
        }
        int oldValue = this.value[pos];
        this.value[pos] = v;
        return oldValue;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap
    public int computeIfAbsent(int k, java.util.function.IntUnaryOperator mappingFunction) {
        Objects.requireNonNull(mappingFunction);
        int pos = find(k);
        if (pos >= 0) {
            return this.value[pos];
        }
        int newValue = mappingFunction.applyAsInt(k);
        insert((-pos) - 1, k, newValue);
        return newValue;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap
    public int computeIfAbsent(int key, Int2IntFunction mappingFunction) {
        Objects.requireNonNull(mappingFunction);
        int pos = find(key);
        if (pos >= 0) {
            return this.value[pos];
        }
        if (!mappingFunction.containsKey(key)) {
            return this.defRetValue;
        }
        int newValue = mappingFunction.get(key);
        insert((-pos) - 1, key, newValue);
        return newValue;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap
    public int computeIfAbsentNullable(int k, IntFunction<? extends Integer> mappingFunction) {
        Objects.requireNonNull(mappingFunction);
        int pos = find(k);
        if (pos >= 0) {
            return this.value[pos];
        }
        Integer newValue = mappingFunction.apply(k);
        if (newValue == null) {
            return this.defRetValue;
        }
        int v = newValue.intValue();
        insert((-pos) - 1, k, v);
        return v;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap
    public int computeIfPresent(int k, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        int pos = find(k);
        if (pos < 0) {
            return this.defRetValue;
        }
        Integer newValue = remappingFunction.apply(Integer.valueOf(k), Integer.valueOf(this.value[pos]));
        if (newValue == null) {
            if (k == 0) {
                removeNullEntry();
            } else {
                removeEntry(pos);
            }
            return this.defRetValue;
        }
        int[] iArr = this.value;
        int intValue = newValue.intValue();
        iArr[pos] = intValue;
        return intValue;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap
    public int compute(int k, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        int pos = find(k);
        Integer newValue = remappingFunction.apply(Integer.valueOf(k), pos >= 0 ? Integer.valueOf(this.value[pos]) : null);
        if (newValue == null) {
            if (pos >= 0) {
                if (k == 0) {
                    removeNullEntry();
                } else {
                    removeEntry(pos);
                }
            }
            return this.defRetValue;
        }
        int newVal = newValue.intValue();
        if (pos < 0) {
            insert((-pos) - 1, k, newVal);
            return newVal;
        }
        this.value[pos] = newVal;
        return newVal;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap
    public int merge(int k, int v, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        int pos = find(k);
        if (pos < 0) {
            insert((-pos) - 1, k, v);
            return v;
        }
        Integer newValue = remappingFunction.apply(Integer.valueOf(this.value[pos]), Integer.valueOf(v));
        if (newValue == null) {
            if (k == 0) {
                removeNullEntry();
            } else {
                removeEntry(pos);
            }
            return this.defRetValue;
        }
        int[] iArr = this.value;
        int intValue = newValue.intValue();
        iArr[pos] = intValue;
        return intValue;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.Function, com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
    public void clear() {
        if (this.size == 0) {
            return;
        }
        this.size = 0;
        this.containsNullKey = false;
        Arrays.fill(this.key, 0);
        this.last = -1;
        this.first = -1;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.Function, com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
    public int size() {
        return this.size;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2IntMap, java.util.Map
    public boolean isEmpty() {
        return this.size == 0;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2IntLinkedOpenHashMap$MapEntry.class */
    public final class MapEntry implements Int2IntMap.Entry, Map.Entry<Integer, Integer>, IntIntPair {
        int index;

        MapEntry(int index) {
            Int2IntLinkedOpenHashMap.this = this$0;
            this.index = index;
        }

        MapEntry() {
            Int2IntLinkedOpenHashMap.this = this$0;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap.Entry
        public int getIntKey() {
            return Int2IntLinkedOpenHashMap.this.key[this.index];
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIntPair
        public int leftInt() {
            return Int2IntLinkedOpenHashMap.this.key[this.index];
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap.Entry
        public int getIntValue() {
            return Int2IntLinkedOpenHashMap.this.value[this.index];
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIntPair
        public int rightInt() {
            return Int2IntLinkedOpenHashMap.this.value[this.index];
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap.Entry
        public int setValue(int v) {
            int oldValue = Int2IntLinkedOpenHashMap.this.value[this.index];
            Int2IntLinkedOpenHashMap.this.value[this.index] = v;
            return oldValue;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIntPair
        public IntIntPair right(int v) {
            Int2IntLinkedOpenHashMap.this.value[this.index] = v;
            return this;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap.Entry, java.util.Map.Entry
        @Deprecated
        public Integer getKey() {
            return Integer.valueOf(Int2IntLinkedOpenHashMap.this.key[this.index]);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap.Entry, java.util.Map.Entry
        @Deprecated
        public Integer getValue() {
            return Integer.valueOf(Int2IntLinkedOpenHashMap.this.value[this.index]);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap.Entry
        @Deprecated
        public Integer setValue(Integer v) {
            return Integer.valueOf(setValue(v.intValue()));
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<Integer, Integer> e = (Map.Entry) o;
            return Int2IntLinkedOpenHashMap.this.key[this.index] == e.getKey().intValue() && Int2IntLinkedOpenHashMap.this.value[this.index] == e.getValue().intValue();
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            return Int2IntLinkedOpenHashMap.this.key[this.index] ^ Int2IntLinkedOpenHashMap.this.value[this.index];
        }

        public String toString() {
            return Int2IntLinkedOpenHashMap.this.key[this.index] + "=>" + Int2IntLinkedOpenHashMap.this.value[this.index];
        }
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

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntSortedMap
    public int firstIntKey() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.key[this.first];
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntSortedMap
    public int lastIntKey() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.key[this.last];
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntSortedMap
    public Int2IntSortedMap tailMap(int from) {
        throw new UnsupportedOperationException();
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntSortedMap
    public Int2IntSortedMap headMap(int to) {
        throw new UnsupportedOperationException();
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntSortedMap
    public Int2IntSortedMap subMap(int from, int to) {
        throw new UnsupportedOperationException();
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntSortedMap, java.util.SortedMap
    public IntComparator comparator() {
        return null;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2IntLinkedOpenHashMap$MapIterator.class */
    public abstract class MapIterator<ConsumerType> {
        int prev;
        int next;
        int curr;
        int index;

        abstract void acceptOnIndex(ConsumerType consumertype, int i);

        protected MapIterator() {
            Int2IntLinkedOpenHashMap.this = r4;
            this.prev = -1;
            this.next = -1;
            this.curr = -1;
            this.index = -1;
            this.next = r4.first;
            this.index = 0;
        }

        private MapIterator(int from) {
            Int2IntLinkedOpenHashMap.this = r6;
            this.prev = -1;
            this.next = -1;
            this.curr = -1;
            this.index = -1;
            if (from == 0) {
                if (r6.containsNullKey) {
                    this.next = (int) r6.link[r6.f76n];
                    this.prev = r6.f76n;
                    return;
                }
                throw new NoSuchElementException("The key " + from + " does not belong to this map.");
            } else if (r6.key[r6.last] == from) {
                this.prev = r6.last;
                this.index = r6.size;
            } else {
                int mix = HashCommon.mix(from);
                int i = r6.mask;
                while (true) {
                    int pos = mix & i;
                    if (r6.key[pos] != 0) {
                        if (r6.key[pos] == from) {
                            this.next = (int) r6.link[pos];
                            this.prev = pos;
                            return;
                        }
                        mix = pos + 1;
                        i = r6.mask;
                    } else {
                        throw new NoSuchElementException("The key " + from + " does not belong to this map.");
                    }
                }
            }
        }

        public boolean hasNext() {
            return this.next != -1;
        }

        public boolean hasPrevious() {
            return this.prev != -1;
        }

        private final void ensureIndexKnown() {
            if (this.index >= 0) {
                return;
            }
            if (this.prev == -1) {
                this.index = 0;
            } else if (this.next == -1) {
                this.index = Int2IntLinkedOpenHashMap.this.size;
            } else {
                int pos = Int2IntLinkedOpenHashMap.this.first;
                this.index = 1;
                while (pos != this.prev) {
                    pos = (int) Int2IntLinkedOpenHashMap.this.link[pos];
                    this.index++;
                }
            }
        }

        public int nextIndex() {
            ensureIndexKnown();
            return this.index;
        }

        public int previousIndex() {
            ensureIndexKnown();
            return this.index - 1;
        }

        public int nextEntry() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            this.curr = this.next;
            this.next = (int) Int2IntLinkedOpenHashMap.this.link[this.curr];
            this.prev = this.curr;
            if (this.index >= 0) {
                this.index++;
            }
            return this.curr;
        }

        public int previousEntry() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            this.curr = this.prev;
            this.prev = (int) (Int2IntLinkedOpenHashMap.this.link[this.curr] >>> 32);
            this.next = this.curr;
            if (this.index >= 0) {
                this.index--;
            }
            return this.curr;
        }

        public void forEachRemaining(ConsumerType action) {
            while (hasNext()) {
                this.curr = this.next;
                this.next = (int) Int2IntLinkedOpenHashMap.this.link[this.curr];
                this.prev = this.curr;
                if (this.index >= 0) {
                    this.index++;
                }
                acceptOnIndex(action, this.curr);
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:38:0x0162, code lost:
            r0[r0] = r0;
            com.viaversion.viaversion.libs.fastutil.ints.Int2IntLinkedOpenHashMap.this.value[r0] = com.viaversion.viaversion.libs.fastutil.ints.Int2IntLinkedOpenHashMap.this.value[r14];
         */
        /* JADX WARN: Code restructure failed: missing block: B:39:0x017f, code lost:
            if (r11.next != r14) goto L41;
         */
        /* JADX WARN: Code restructure failed: missing block: B:40:0x0182, code lost:
            r11.next = r0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:42:0x018c, code lost:
            if (r11.prev != r14) goto L48;
         */
        /* JADX WARN: Code restructure failed: missing block: B:43:0x018f, code lost:
            r11.prev = r0;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void remove() {
            /*
                Method dump skipped, instructions count: 417
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.viaversion.viaversion.libs.fastutil.ints.Int2IntLinkedOpenHashMap.MapIterator.remove():void");
        }

        public int skip(int n) {
            int i = n;
            while (true) {
                int i2 = i;
                i--;
                if (i2 == 0 || !hasNext()) {
                    break;
                }
                nextEntry();
            }
            return (n - i) - 1;
        }

        public int back(int n) {
            int i = n;
            while (true) {
                int i2 = i;
                i--;
                if (i2 == 0 || !hasPrevious()) {
                    break;
                }
                previousEntry();
            }
            return (n - i) - 1;
        }

        public void set(Int2IntMap.Entry ok) {
            throw new UnsupportedOperationException();
        }

        public void add(Int2IntMap.Entry ok) {
            throw new UnsupportedOperationException();
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2IntLinkedOpenHashMap$EntryIterator.class */
    public final class EntryIterator extends MapIterator<Consumer<? super Int2IntMap.Entry>> implements ObjectListIterator<Int2IntMap.Entry> {
        private MapEntry entry;

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectListIterator, java.util.ListIterator
        public /* bridge */ /* synthetic */ void add(Object obj) {
            super.add((Int2IntMap.Entry) obj);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectListIterator, java.util.ListIterator
        public /* bridge */ /* synthetic */ void set(Object obj) {
            super.set((Int2IntMap.Entry) obj);
        }

        @Override // java.util.Iterator
        public /* bridge */ /* synthetic */ void forEachRemaining(Consumer consumer) {
            super.forEachRemaining((EntryIterator) consumer);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public EntryIterator() {
            super();
            Int2IntLinkedOpenHashMap.this = r4;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public EntryIterator(int from) {
            super(from);
            Int2IntLinkedOpenHashMap.this = r6;
        }

        public final void acceptOnIndex(Consumer<? super Int2IntMap.Entry> action, int index) {
            action.accept(new MapEntry(index));
        }

        @Override // java.util.Iterator, java.util.ListIterator
        public MapEntry next() {
            MapEntry mapEntry = new MapEntry(nextEntry());
            this.entry = mapEntry;
            return mapEntry;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.BidirectionalIterator, java.util.ListIterator
        public MapEntry previous() {
            MapEntry mapEntry = new MapEntry(previousEntry());
            this.entry = mapEntry;
            return mapEntry;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntLinkedOpenHashMap.MapIterator, com.viaversion.viaversion.libs.fastutil.objects.ObjectListIterator, java.util.Iterator, java.util.ListIterator
        public void remove() {
            super.remove();
            this.entry.index = -1;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2IntLinkedOpenHashMap$FastEntryIterator.class */
    public final class FastEntryIterator extends MapIterator<Consumer<? super Int2IntMap.Entry>> implements ObjectListIterator<Int2IntMap.Entry> {
        final MapEntry entry;

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectListIterator, java.util.ListIterator
        public /* bridge */ /* synthetic */ void add(Object obj) {
            super.add((Int2IntMap.Entry) obj);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectListIterator, java.util.ListIterator
        public /* bridge */ /* synthetic */ void set(Object obj) {
            super.set((Int2IntMap.Entry) obj);
        }

        @Override // java.util.Iterator
        public /* bridge */ /* synthetic */ void forEachRemaining(Consumer consumer) {
            super.forEachRemaining((FastEntryIterator) consumer);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public FastEntryIterator() {
            super();
            Int2IntLinkedOpenHashMap.this = r6;
            this.entry = new MapEntry();
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public FastEntryIterator(int from) {
            super(from);
            Int2IntLinkedOpenHashMap.this = r6;
            this.entry = new MapEntry();
        }

        public final void acceptOnIndex(Consumer<? super Int2IntMap.Entry> action, int index) {
            this.entry.index = index;
            action.accept(this.entry);
        }

        @Override // java.util.Iterator, java.util.ListIterator
        public MapEntry next() {
            this.entry.index = nextEntry();
            return this.entry;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.BidirectionalIterator, java.util.ListIterator
        public MapEntry previous() {
            this.entry.index = previousEntry();
            return this.entry;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2IntLinkedOpenHashMap$MapEntrySet.class */
    public final class MapEntrySet extends AbstractObjectSortedSet<Int2IntMap.Entry> implements Int2IntSortedMap.FastSortedEntrySet {
        private static final int SPLITERATOR_CHARACTERISTICS = 81;

        private MapEntrySet() {
            Int2IntLinkedOpenHashMap.this = r4;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSortedSet, com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet, com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable
        public ObjectBidirectionalIterator<Int2IntMap.Entry> iterator() {
            return new EntryIterator();
        }

        @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectSet, java.util.Set
        public ObjectSpliterator<Int2IntMap.Entry> spliterator() {
            return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(Int2IntLinkedOpenHashMap.this), 81);
        }

        @Override // java.util.SortedSet
        public Comparator<? super Int2IntMap.Entry> comparator() {
            return null;
        }

        public ObjectSortedSet<Int2IntMap.Entry> subSet(Int2IntMap.Entry fromElement, Int2IntMap.Entry toElement) {
            throw new UnsupportedOperationException();
        }

        public ObjectSortedSet<Int2IntMap.Entry> headSet(Int2IntMap.Entry toElement) {
            throw new UnsupportedOperationException();
        }

        public ObjectSortedSet<Int2IntMap.Entry> tailSet(Int2IntMap.Entry fromElement) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.SortedSet
        public Int2IntMap.Entry first() {
            if (Int2IntLinkedOpenHashMap.this.size == 0) {
                throw new NoSuchElementException();
            }
            return new MapEntry(Int2IntLinkedOpenHashMap.this.first);
        }

        @Override // java.util.SortedSet
        public Int2IntMap.Entry last() {
            if (Int2IntLinkedOpenHashMap.this.size == 0) {
                throw new NoSuchElementException();
            }
            return new MapEntry(Int2IntLinkedOpenHashMap.this.last);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object o) {
            int curr;
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<?, ?> e = (Map.Entry) o;
            if (e.getKey() == null || !(e.getKey() instanceof Integer) || e.getValue() == null || !(e.getValue() instanceof Integer)) {
                return false;
            }
            int k = ((Integer) e.getKey()).intValue();
            int v = ((Integer) e.getValue()).intValue();
            if (k == 0) {
                return Int2IntLinkedOpenHashMap.this.containsNullKey && Int2IntLinkedOpenHashMap.this.value[Int2IntLinkedOpenHashMap.this.f76n] == v;
            }
            int[] key = Int2IntLinkedOpenHashMap.this.key;
            int mix = HashCommon.mix(k) & Int2IntLinkedOpenHashMap.this.mask;
            int pos = mix;
            int curr2 = key[mix];
            if (curr2 == 0) {
                return false;
            }
            if (k == curr2) {
                return Int2IntLinkedOpenHashMap.this.value[pos] == v;
            }
            do {
                int i = (pos + 1) & Int2IntLinkedOpenHashMap.this.mask;
                pos = i;
                curr = key[i];
                if (curr == 0) {
                    return false;
                }
            } while (k != curr);
            return Int2IntLinkedOpenHashMap.this.value[pos] == v;
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
            if (k == 0) {
                if (Int2IntLinkedOpenHashMap.this.containsNullKey && Int2IntLinkedOpenHashMap.this.value[Int2IntLinkedOpenHashMap.this.f76n] == v) {
                    Int2IntLinkedOpenHashMap.this.removeNullEntry();
                    return true;
                }
                return false;
            }
            int[] key = Int2IntLinkedOpenHashMap.this.key;
            int mix = HashCommon.mix(k) & Int2IntLinkedOpenHashMap.this.mask;
            int pos = mix;
            int curr = key[mix];
            if (curr == 0) {
                return false;
            }
            if (curr == k) {
                if (Int2IntLinkedOpenHashMap.this.value[pos] == v) {
                    Int2IntLinkedOpenHashMap.this.removeEntry(pos);
                    return true;
                }
                return false;
            }
            while (true) {
                int i = (pos + 1) & Int2IntLinkedOpenHashMap.this.mask;
                pos = i;
                int curr2 = key[i];
                if (curr2 == 0) {
                    return false;
                }
                if (curr2 == k && Int2IntLinkedOpenHashMap.this.value[pos] == v) {
                    Int2IntLinkedOpenHashMap.this.removeEntry(pos);
                    return true;
                }
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return Int2IntLinkedOpenHashMap.this.size;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            Int2IntLinkedOpenHashMap.this.clear();
        }

        public ObjectListIterator<Int2IntMap.Entry> iterator(Int2IntMap.Entry from) {
            return new EntryIterator(from.getIntKey());
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntSortedMap.FastSortedEntrySet, com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap.FastEntrySet
        public ObjectListIterator<Int2IntMap.Entry> fastIterator() {
            return new FastEntryIterator();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntSortedMap.FastSortedEntrySet
        public ObjectListIterator<Int2IntMap.Entry> fastIterator(Int2IntMap.Entry from) {
            return new FastEntryIterator(from.getIntKey());
        }

        @Override // java.lang.Iterable
        public void forEach(Consumer<? super Int2IntMap.Entry> consumer) {
            int i = Int2IntLinkedOpenHashMap.this.size;
            int next = Int2IntLinkedOpenHashMap.this.first;
            while (true) {
                int i2 = i;
                i--;
                if (i2 != 0) {
                    int curr = next;
                    next = (int) Int2IntLinkedOpenHashMap.this.link[curr];
                    consumer.accept(new AbstractInt2IntMap.BasicEntry(Int2IntLinkedOpenHashMap.this.key[curr], Int2IntLinkedOpenHashMap.this.value[curr]));
                } else {
                    return;
                }
            }
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap.FastEntrySet
        public void fastForEach(Consumer<? super Int2IntMap.Entry> consumer) {
            AbstractInt2IntMap.BasicEntry entry = new AbstractInt2IntMap.BasicEntry();
            int i = Int2IntLinkedOpenHashMap.this.size;
            int next = Int2IntLinkedOpenHashMap.this.first;
            while (true) {
                int i2 = i;
                i--;
                if (i2 != 0) {
                    int curr = next;
                    next = (int) Int2IntLinkedOpenHashMap.this.link[curr];
                    entry.key = Int2IntLinkedOpenHashMap.this.key[curr];
                    entry.value = Int2IntLinkedOpenHashMap.this.value[curr];
                    consumer.accept(entry);
                } else {
                    return;
                }
            }
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap
    public Int2IntSortedMap.FastSortedEntrySet int2IntEntrySet() {
        if (this.entries == null) {
            this.entries = new MapEntrySet();
        }
        return this.entries;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2IntLinkedOpenHashMap$KeyIterator.class */
    public final class KeyIterator extends MapIterator<java.util.function.IntConsumer> implements IntListIterator {
        @Override // java.util.PrimitiveIterator.OfInt
        public /* bridge */ /* synthetic */ void forEachRemaining(java.util.function.IntConsumer intConsumer) {
            super.forEachRemaining((KeyIterator) intConsumer);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public KeyIterator(int k) {
            super(k);
            Int2IntLinkedOpenHashMap.this = r6;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntBidirectionalIterator
        public int previousInt() {
            return Int2IntLinkedOpenHashMap.this.key[previousEntry()];
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public KeyIterator() {
            super();
            Int2IntLinkedOpenHashMap.this = r4;
        }

        public final void acceptOnIndex(java.util.function.IntConsumer action, int index) {
            action.accept(Int2IntLinkedOpenHashMap.this.key[index]);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
        public int nextInt() {
            return Int2IntLinkedOpenHashMap.this.key[nextEntry()];
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2IntLinkedOpenHashMap$KeySet.class */
    public final class KeySet extends AbstractIntSortedSet {
        private static final int SPLITERATOR_CHARACTERISTICS = 337;

        private KeySet() {
            Int2IntLinkedOpenHashMap.this = r4;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet
        public IntListIterator iterator(int from) {
            return new KeyIterator(from);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntSortedSet, com.viaversion.viaversion.libs.fastutil.ints.AbstractIntSet, com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
        public Iterator<Integer> iterator() {
            return new KeyIterator();
        }

        /* JADX WARN: Type inference failed for: r0v1, types: [com.viaversion.viaversion.libs.fastutil.ints.IntListIterator, com.viaversion.viaversion.libs.fastutil.ints.IntIterator] */
        @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
        /* renamed from: spliterator */
        public Spliterator<Integer> spliterator2() {
            return IntSpliterators.asSpliterator(iterator(), Size64.sizeOf(Int2IntLinkedOpenHashMap.this), 337);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterable
        public void forEach(java.util.function.IntConsumer consumer) {
            int i = Int2IntLinkedOpenHashMap.this.size;
            int next = Int2IntLinkedOpenHashMap.this.first;
            while (true) {
                int i2 = i;
                i--;
                if (i2 != 0) {
                    int curr = next;
                    next = (int) Int2IntLinkedOpenHashMap.this.link[curr];
                    consumer.accept(Int2IntLinkedOpenHashMap.this.key[curr]);
                } else {
                    return;
                }
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return Int2IntLinkedOpenHashMap.this.size;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
        public boolean contains(int k) {
            return Int2IntLinkedOpenHashMap.this.containsKey(k);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntSet, com.viaversion.viaversion.libs.fastutil.ints.IntSet
        public boolean remove(int k) {
            int oldSize = Int2IntLinkedOpenHashMap.this.size;
            Int2IntLinkedOpenHashMap.this.remove(k);
            return Int2IntLinkedOpenHashMap.this.size != oldSize;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            Int2IntLinkedOpenHashMap.this.clear();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet
        public int firstInt() {
            if (Int2IntLinkedOpenHashMap.this.size == 0) {
                throw new NoSuchElementException();
            }
            return Int2IntLinkedOpenHashMap.this.key[Int2IntLinkedOpenHashMap.this.first];
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet
        public int lastInt() {
            if (Int2IntLinkedOpenHashMap.this.size == 0) {
                throw new NoSuchElementException();
            }
            return Int2IntLinkedOpenHashMap.this.key[Int2IntLinkedOpenHashMap.this.last];
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet, java.util.SortedSet
        public IntComparator comparator() {
            return null;
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
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2IntSortedMap, com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2IntMap, com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
    public IntSortedSet keySet() {
        if (this.keys == null) {
            this.keys = new KeySet();
        }
        return this.keys;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/Int2IntLinkedOpenHashMap$ValueIterator.class */
    public final class ValueIterator extends MapIterator<java.util.function.IntConsumer> implements IntListIterator {
        @Override // java.util.PrimitiveIterator.OfInt
        public /* bridge */ /* synthetic */ void forEachRemaining(java.util.function.IntConsumer intConsumer) {
            super.forEachRemaining((ValueIterator) intConsumer);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntBidirectionalIterator
        public int previousInt() {
            return Int2IntLinkedOpenHashMap.this.value[previousEntry()];
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ValueIterator() {
            super();
            Int2IntLinkedOpenHashMap.this = r4;
        }

        public final void acceptOnIndex(java.util.function.IntConsumer action, int index) {
            action.accept(Int2IntLinkedOpenHashMap.this.value[index]);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
        public int nextInt() {
            return Int2IntLinkedOpenHashMap.this.value[nextEntry()];
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2IntSortedMap, com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2IntMap, com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
    public IntCollection values() {
        if (this.values == null) {
            this.values = new AbstractIntCollection() { // from class: com.viaversion.viaversion.libs.fastutil.ints.Int2IntLinkedOpenHashMap.1
                private static final int SPLITERATOR_CHARACTERISTICS = 336;

                @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
                /* renamed from: iterator */
                public Iterator<Integer> iterator2() {
                    return new ValueIterator();
                }

                /* JADX WARN: Type inference failed for: r0v1, types: [com.viaversion.viaversion.libs.fastutil.ints.IntIterator] */
                @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
                /* renamed from: spliterator */
                public Spliterator<Integer> spliterator2() {
                    return IntSpliterators.asSpliterator(iterator2(), Size64.sizeOf(Int2IntLinkedOpenHashMap.this), 336);
                }

                @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterable
                public void forEach(java.util.function.IntConsumer consumer) {
                    int i = Int2IntLinkedOpenHashMap.this.size;
                    int next = Int2IntLinkedOpenHashMap.this.first;
                    while (true) {
                        int i2 = i;
                        i--;
                        if (i2 != 0) {
                            int curr = next;
                            next = (int) Int2IntLinkedOpenHashMap.this.link[curr];
                            consumer.accept(Int2IntLinkedOpenHashMap.this.value[curr]);
                        } else {
                            return;
                        }
                    }
                }

                @Override // java.util.AbstractCollection, java.util.Collection
                public int size() {
                    return Int2IntLinkedOpenHashMap.this.size;
                }

                @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
                public boolean contains(int v) {
                    return Int2IntLinkedOpenHashMap.this.containsValue(v);
                }

                @Override // java.util.AbstractCollection, java.util.Collection
                public void clear() {
                    Int2IntLinkedOpenHashMap.this.clear();
                }
            };
        }
        return this.values;
    }

    public boolean trim() {
        return trim(this.size);
    }

    public boolean trim(int n) {
        int l = HashCommon.nextPowerOfTwo((int) Math.ceil(n / this.f77f));
        if (l >= this.f76n || this.size > HashCommon.maxFill(l, this.f77f)) {
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
        int[] value = this.value;
        int mask = newN - 1;
        int[] newKey = new int[newN + 1];
        int[] newValue = new int[newN + 1];
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
            newValue[pos] = value[i];
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
        this.f76n = newN;
        this.mask = mask;
        this.maxFill = HashCommon.maxFill(this.f76n, this.f77f);
        this.key = newKey;
        this.value = newValue;
    }

    public Int2IntLinkedOpenHashMap clone() {
        try {
            Int2IntLinkedOpenHashMap c = (Int2IntLinkedOpenHashMap) super.clone();
            c.keys = null;
            c.values = null;
            c.entries = null;
            c.containsNullKey = this.containsNullKey;
            c.key = (int[]) this.key.clone();
            c.value = (int[]) this.value.clone();
            c.link = (long[]) this.link.clone();
            return c;
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2IntMap, java.util.Map
    public int hashCode() {
        int h = 0;
        int j = realSize();
        int i = 0;
        while (true) {
            int i2 = j;
            j--;
            if (i2 == 0) {
                break;
            }
            while (this.key[i] == 0) {
                i++;
            }
            int t = this.key[i];
            h += t ^ this.value[i];
            i++;
        }
        if (this.containsNullKey) {
            h += this.value[this.f76n];
        }
        return h;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        int[] key = this.key;
        int[] value = this.value;
        EntryIterator i = new EntryIterator();
        s.defaultWriteObject();
        int j = this.size;
        while (true) {
            int i2 = j;
            j--;
            if (i2 != 0) {
                int e = i.nextEntry();
                s.writeInt(key[e]);
                s.writeInt(value[e]);
            } else {
                return;
            }
        }
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        int pos;
        s.defaultReadObject();
        this.f76n = HashCommon.arraySize(this.size, this.f77f);
        this.maxFill = HashCommon.maxFill(this.f76n, this.f77f);
        this.mask = this.f76n - 1;
        int[] key = new int[this.f76n + 1];
        this.key = key;
        int[] value = new int[this.f76n + 1];
        this.value = value;
        long[] link = new long[this.f76n + 1];
        this.link = link;
        int prev = -1;
        this.last = -1;
        this.first = -1;
        int i = this.size;
        while (true) {
            int i2 = i;
            i--;
            if (i2 == 0) {
                break;
            }
            int k = s.readInt();
            int v = s.readInt();
            if (k == 0) {
                pos = this.f76n;
                this.containsNullKey = true;
            } else {
                int mix = HashCommon.mix(k);
                int i3 = this.mask;
                while (true) {
                    pos = mix & i3;
                    if (key[pos] == 0) {
                        break;
                    }
                    mix = pos + 1;
                    i3 = this.mask;
                }
            }
            key[pos] = k;
            value[pos] = v;
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
