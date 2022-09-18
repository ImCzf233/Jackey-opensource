package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Hash;
import com.viaversion.viaversion.libs.fastutil.HashCommon;
import com.viaversion.viaversion.libs.fastutil.Size64;
import com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntListIterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntSortedMap;
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
import java.util.function.IntConsumer;
import java.util.function.ToIntFunction;
import jdk.nashorn.internal.runtime.JSType;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2IntLinkedOpenHashMap.class */
public class Object2IntLinkedOpenHashMap<K> extends AbstractObject2IntSortedMap<K> implements Serializable, Cloneable, Hash {
    private static final long serialVersionUID = 0;
    private static final boolean ASSERTS = false;
    protected transient K[] key;
    protected transient int[] value;
    protected transient int mask;
    protected transient boolean containsNullKey;
    protected transient int first;
    protected transient int last;
    protected transient long[] link;

    /* renamed from: n */
    protected transient int f132n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;

    /* renamed from: f */
    protected final float f133f;
    protected transient Object2IntSortedMap.FastSortedEntrySet<K> entries;
    protected transient ObjectSortedSet<K> keys;
    protected transient IntCollection values;

    public Object2IntLinkedOpenHashMap(int expected, float f) {
        this.first = -1;
        this.last = -1;
        if (f <= 0.0f || f >= 1.0f) {
            throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1");
        }
        if (expected < 0) {
            throw new IllegalArgumentException("The expected number of elements must be nonnegative");
        }
        this.f133f = f;
        int arraySize = HashCommon.arraySize(expected, f);
        this.f132n = arraySize;
        this.minN = arraySize;
        this.mask = this.f132n - 1;
        this.maxFill = HashCommon.maxFill(this.f132n, f);
        this.key = (K[]) new Object[this.f132n + 1];
        this.value = new int[this.f132n + 1];
        this.link = new long[this.f132n + 1];
    }

    public Object2IntLinkedOpenHashMap(int expected) {
        this(expected, 0.75f);
    }

    public Object2IntLinkedOpenHashMap() {
        this(16, 0.75f);
    }

    public Object2IntLinkedOpenHashMap(Map<? extends K, ? extends Integer> m, float f) {
        this(m.size(), f);
        putAll(m);
    }

    public Object2IntLinkedOpenHashMap(Map<? extends K, ? extends Integer> m) {
        this(m, 0.75f);
    }

    public Object2IntLinkedOpenHashMap(Object2IntMap<K> m, float f) {
        this(m.size(), f);
        putAll(m);
    }

    public Object2IntLinkedOpenHashMap(Object2IntMap<K> m) {
        this((Object2IntMap) m, 0.75f);
    }

    public Object2IntLinkedOpenHashMap(K[] k, int[] v, float f) {
        this(k.length, f);
        if (k.length != v.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
        }
        for (int i = 0; i < k.length; i++) {
            put((Object2IntLinkedOpenHashMap<K>) k[i], v[i]);
        }
    }

    public Object2IntLinkedOpenHashMap(K[] k, int[] v) {
        this(k, v, 0.75f);
    }

    private int realSize() {
        return this.containsNullKey ? this.size - 1 : this.size;
    }

    private void ensureCapacity(int capacity) {
        int needed = HashCommon.arraySize(capacity, this.f133f);
        if (needed > this.f132n) {
            rehash(needed);
        }
    }

    private void tryCapacity(long capacity) {
        int needed = (int) Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long) Math.ceil(((float) capacity) / this.f133f))));
        if (needed > this.f132n) {
            rehash(needed);
        }
    }

    public int removeEntry(int pos) {
        int oldValue = this.value[pos];
        this.size--;
        fixPointers(pos);
        shiftKeys(pos);
        if (this.f132n > this.minN && this.size < this.maxFill / 4 && this.f132n > 16) {
            rehash(this.f132n / 2);
        }
        return oldValue;
    }

    public int removeNullEntry() {
        this.containsNullKey = false;
        this.key[this.f132n] = null;
        int oldValue = this.value[this.f132n];
        this.size--;
        fixPointers(this.f132n);
        if (this.f132n > this.minN && this.size < this.maxFill / 4 && this.f132n > 16) {
            rehash(this.f132n / 2);
        }
        return oldValue;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2IntMap, java.util.Map
    public void putAll(Map<? extends K, ? extends Integer> m) {
        if (this.f133f <= 0.5d) {
            ensureCapacity(m.size());
        } else {
            tryCapacity(size() + m.size());
        }
        super.putAll(m);
    }

    private int find(K k) {
        K curr;
        if (k == null) {
            return this.containsNullKey ? this.f132n : -(this.f132n + 1);
        }
        K[] key = this.key;
        int mix = HashCommon.mix(k.hashCode()) & this.mask;
        int pos = mix;
        K curr2 = key[mix];
        if (curr2 == null) {
            return -(pos + 1);
        }
        if (k.equals(curr2)) {
            return pos;
        }
        do {
            int i = (pos + 1) & this.mask;
            pos = i;
            curr = key[i];
            if (curr == null) {
                return -(pos + 1);
            }
        } while (!k.equals(curr));
        return pos;
    }

    private void insert(int pos, K k, int v) {
        if (pos == this.f132n) {
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
            rehash(HashCommon.arraySize(this.size + 1, this.f133f));
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunction
    public int put(K k, int v) {
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

    public int addTo(K k, int incr) {
        int pos;
        K curr;
        if (k == null) {
            if (this.containsNullKey) {
                return addToValue(this.f132n, incr);
            }
            pos = this.f132n;
            this.containsNullKey = true;
        } else {
            K[] key = this.key;
            int mix = HashCommon.mix(k.hashCode()) & this.mask;
            pos = mix;
            K curr2 = key[mix];
            if (curr2 != null) {
                if (curr2.equals(k)) {
                    return addToValue(pos, incr);
                }
                do {
                    int i = (pos + 1) & this.mask;
                    pos = i;
                    curr = key[i];
                    if (curr != null) {
                    }
                } while (!curr.equals(k));
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
            rehash(HashCommon.arraySize(this.size + 1, this.f133f));
        }
        return this.defRetValue;
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
            this.value[last] = this.value[pos];
            fixPointers(pos, last);
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunction
    public int removeInt(Object k) {
        K curr;
        if (k == null) {
            if (this.containsNullKey) {
                return removeNullEntry();
            }
            return this.defRetValue;
        }
        K[] key = this.key;
        int mix = HashCommon.mix(k.hashCode()) & this.mask;
        int pos = mix;
        K curr2 = key[mix];
        if (curr2 == null) {
            return this.defRetValue;
        }
        if (k.equals(curr2)) {
            return removeEntry(pos);
        }
        do {
            int i = (pos + 1) & this.mask;
            pos = i;
            curr = key[i];
            if (curr == null) {
                return this.defRetValue;
            }
        } while (!k.equals(curr));
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
        if (pos == this.f132n) {
            this.containsNullKey = false;
            this.key[this.f132n] = null;
        } else {
            shiftKeys(pos);
        }
        if (this.f132n > this.minN && this.size < this.maxFill / 4 && this.f132n > 16) {
            rehash(this.f132n / 2);
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
        if (pos == this.f132n) {
            this.containsNullKey = false;
            this.key[this.f132n] = null;
        } else {
            shiftKeys(pos);
        }
        if (this.f132n > this.minN && this.size < this.maxFill / 4 && this.f132n > 16) {
            rehash(this.f132n / 2);
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

    public int getAndMoveToFirst(K k) {
        K curr;
        if (k == null) {
            if (this.containsNullKey) {
                moveIndexToFirst(this.f132n);
                return this.value[this.f132n];
            }
            return this.defRetValue;
        }
        K[] key = this.key;
        int mix = HashCommon.mix(k.hashCode()) & this.mask;
        int pos = mix;
        K curr2 = key[mix];
        if (curr2 == null) {
            return this.defRetValue;
        }
        if (k.equals(curr2)) {
            moveIndexToFirst(pos);
            return this.value[pos];
        }
        do {
            int i = (pos + 1) & this.mask;
            pos = i;
            curr = key[i];
            if (curr == null) {
                return this.defRetValue;
            }
        } while (!k.equals(curr));
        moveIndexToFirst(pos);
        return this.value[pos];
    }

    public int getAndMoveToLast(K k) {
        K curr;
        if (k == null) {
            if (this.containsNullKey) {
                moveIndexToLast(this.f132n);
                return this.value[this.f132n];
            }
            return this.defRetValue;
        }
        K[] key = this.key;
        int mix = HashCommon.mix(k.hashCode()) & this.mask;
        int pos = mix;
        K curr2 = key[mix];
        if (curr2 == null) {
            return this.defRetValue;
        }
        if (k.equals(curr2)) {
            moveIndexToLast(pos);
            return this.value[pos];
        }
        do {
            int i = (pos + 1) & this.mask;
            pos = i;
            curr = key[i];
            if (curr == null) {
                return this.defRetValue;
            }
        } while (!k.equals(curr));
        moveIndexToLast(pos);
        return this.value[pos];
    }

    public int putAndMoveToFirst(K k, int v) {
        int pos;
        K curr;
        if (k == null) {
            if (this.containsNullKey) {
                moveIndexToFirst(this.f132n);
                return setValue(this.f132n, v);
            }
            this.containsNullKey = true;
            pos = this.f132n;
        } else {
            K[] key = this.key;
            int mix = HashCommon.mix(k.hashCode()) & this.mask;
            pos = mix;
            K curr2 = key[mix];
            if (curr2 != null) {
                if (curr2.equals(k)) {
                    moveIndexToFirst(pos);
                    return setValue(pos, v);
                }
                do {
                    int i = (pos + 1) & this.mask;
                    pos = i;
                    curr = key[i];
                    if (curr != null) {
                    }
                } while (!curr.equals(k));
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
            rehash(HashCommon.arraySize(this.size, this.f133f));
        }
        return this.defRetValue;
    }

    public int putAndMoveToLast(K k, int v) {
        int pos;
        K curr;
        if (k == null) {
            if (this.containsNullKey) {
                moveIndexToLast(this.f132n);
                return setValue(this.f132n, v);
            }
            this.containsNullKey = true;
            pos = this.f132n;
        } else {
            K[] key = this.key;
            int mix = HashCommon.mix(k.hashCode()) & this.mask;
            pos = mix;
            K curr2 = key[mix];
            if (curr2 != null) {
                if (curr2.equals(k)) {
                    moveIndexToLast(pos);
                    return setValue(pos, v);
                }
                do {
                    int i = (pos + 1) & this.mask;
                    pos = i;
                    curr = key[i];
                    if (curr != null) {
                    }
                } while (!curr.equals(k));
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
            rehash(HashCommon.arraySize(this.size, this.f133f));
        }
        return this.defRetValue;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunction
    public int getInt(Object k) {
        K curr;
        if (k == null) {
            return this.containsNullKey ? this.value[this.f132n] : this.defRetValue;
        }
        K[] key = this.key;
        int mix = HashCommon.mix(k.hashCode()) & this.mask;
        int pos = mix;
        K curr2 = key[mix];
        if (curr2 == null) {
            return this.defRetValue;
        }
        if (k.equals(curr2)) {
            return this.value[pos];
        }
        do {
            int i = (pos + 1) & this.mask;
            pos = i;
            curr = key[i];
            if (curr == null) {
                return this.defRetValue;
            }
        } while (!k.equals(curr));
        return this.value[pos];
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2IntMap, com.viaversion.viaversion.libs.fastutil.Function
    public boolean containsKey(Object k) {
        K curr;
        if (k == null) {
            return this.containsNullKey;
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

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2IntMap, com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap
    public boolean containsValue(int v) {
        int[] value = this.value;
        K[] key = this.key;
        if (this.containsNullKey && value[this.f132n] == v) {
            return true;
        }
        int i = this.f132n;
        while (true) {
            int i2 = i;
            i--;
            if (i2 != 0) {
                if (key[i] != null && value[i] == v) {
                    return true;
                }
            } else {
                return false;
            }
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunction
    public int getOrDefault(Object k, int defaultValue) {
        K curr;
        if (k == null) {
            return this.containsNullKey ? this.value[this.f132n] : defaultValue;
        }
        K[] key = this.key;
        int mix = HashCommon.mix(k.hashCode()) & this.mask;
        int pos = mix;
        K curr2 = key[mix];
        if (curr2 == null) {
            return defaultValue;
        }
        if (k.equals(curr2)) {
            return this.value[pos];
        }
        do {
            int i = (pos + 1) & this.mask;
            pos = i;
            curr = key[i];
            if (curr == null) {
                return defaultValue;
            }
        } while (!k.equals(curr));
        return this.value[pos];
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap
    public int putIfAbsent(K k, int v) {
        int pos = find(k);
        if (pos >= 0) {
            return this.value[pos];
        }
        insert((-pos) - 1, k, v);
        return this.defRetValue;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap
    public boolean remove(Object k, int v) {
        if (k == null) {
            if (this.containsNullKey && v == this.value[this.f132n]) {
                removeNullEntry();
                return true;
            }
            return false;
        }
        K[] key = this.key;
        int mix = HashCommon.mix(k.hashCode()) & this.mask;
        int pos = mix;
        K curr = key[mix];
        if (curr == null) {
            return false;
        }
        if (k.equals(curr) && v == this.value[pos]) {
            removeEntry(pos);
            return true;
        }
        while (true) {
            int i = (pos + 1) & this.mask;
            pos = i;
            K curr2 = key[i];
            if (curr2 == null) {
                return false;
            }
            if (k.equals(curr2) && v == this.value[pos]) {
                removeEntry(pos);
                return true;
            }
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap
    public boolean replace(K k, int oldValue, int v) {
        int pos = find(k);
        if (pos < 0 || oldValue != this.value[pos]) {
            return false;
        }
        this.value[pos] = v;
        return true;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap
    public int replace(K k, int v) {
        int pos = find(k);
        if (pos < 0) {
            return this.defRetValue;
        }
        int oldValue = this.value[pos];
        this.value[pos] = v;
        return oldValue;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap
    public int computeIfAbsent(K k, ToIntFunction<? super K> mappingFunction) {
        Objects.requireNonNull(mappingFunction);
        int pos = find(k);
        if (pos >= 0) {
            return this.value[pos];
        }
        int newValue = mappingFunction.applyAsInt(k);
        insert((-pos) - 1, k, newValue);
        return newValue;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap
    public int computeIfAbsent(K key, Object2IntFunction<? super K> mappingFunction) {
        Objects.requireNonNull(mappingFunction);
        int pos = find(key);
        if (pos >= 0) {
            return this.value[pos];
        }
        if (!mappingFunction.containsKey(key)) {
            return this.defRetValue;
        }
        int newValue = mappingFunction.getInt(key);
        insert((-pos) - 1, key, newValue);
        return newValue;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap
    public int computeIntIfPresent(K k, BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        int pos = find(k);
        if (pos < 0) {
            return this.defRetValue;
        }
        Integer newValue = remappingFunction.apply(k, Integer.valueOf(this.value[pos]));
        if (newValue == null) {
            if (k == null) {
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

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap
    public int computeInt(K k, BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        int pos = find(k);
        Integer newValue = remappingFunction.apply(k, pos >= 0 ? Integer.valueOf(this.value[pos]) : null);
        if (newValue == null) {
            if (pos >= 0) {
                if (k == null) {
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

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap
    public int merge(K k, int v, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        int pos = find(k);
        if (pos < 0) {
            insert((-pos) - 1, k, v);
            return v;
        }
        Integer newValue = remappingFunction.apply(Integer.valueOf(this.value[pos]), Integer.valueOf(v));
        if (newValue == null) {
            if (k == null) {
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
        Arrays.fill(this.key, (Object) null);
        this.last = -1;
        this.first = -1;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.Function, com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap, java.util.Map
    public int size() {
        return this.size;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2IntMap, java.util.Map
    public boolean isEmpty() {
        return this.size == 0;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2IntLinkedOpenHashMap$MapEntry.class */
    public final class MapEntry implements Object2IntMap.Entry<K>, Map.Entry<K, Integer>, ObjectIntPair<K> {
        int index;

        MapEntry(int index) {
            Object2IntLinkedOpenHashMap.this = this$0;
            this.index = index;
        }

        MapEntry() {
            Object2IntLinkedOpenHashMap.this = this$0;
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            return Object2IntLinkedOpenHashMap.this.key[this.index];
        }

        @Override // com.viaversion.viaversion.libs.fastutil.Pair
        public K left() {
            return Object2IntLinkedOpenHashMap.this.key[this.index];
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap.Entry
        public int getIntValue() {
            return Object2IntLinkedOpenHashMap.this.value[this.index];
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectIntPair
        public int rightInt() {
            return Object2IntLinkedOpenHashMap.this.value[this.index];
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap.Entry
        public int setValue(int v) {
            int oldValue = Object2IntLinkedOpenHashMap.this.value[this.index];
            Object2IntLinkedOpenHashMap.this.value[this.index] = v;
            return oldValue;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectIntPair
        public ObjectIntPair<K> right(int v) {
            Object2IntLinkedOpenHashMap.this.value[this.index] = v;
            return this;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap.Entry, java.util.Map.Entry
        @Deprecated
        public Integer getValue() {
            return Integer.valueOf(Object2IntLinkedOpenHashMap.this.value[this.index]);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap.Entry
        @Deprecated
        public Integer setValue(Integer v) {
            return Integer.valueOf(setValue(v.intValue()));
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<K, Integer> e = (Map.Entry) o;
            return Objects.equals(Object2IntLinkedOpenHashMap.this.key[this.index], e.getKey()) && Object2IntLinkedOpenHashMap.this.value[this.index] == e.getValue().intValue();
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            return (Object2IntLinkedOpenHashMap.this.key[this.index] == null ? 0 : Object2IntLinkedOpenHashMap.this.key[this.index].hashCode()) ^ Object2IntLinkedOpenHashMap.this.value[this.index];
        }

        public String toString() {
            return Object2IntLinkedOpenHashMap.this.key[this.index] + "=>" + Object2IntLinkedOpenHashMap.this.value[this.index];
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

    @Override // java.util.SortedMap
    public K firstKey() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.key[this.first];
    }

    @Override // java.util.SortedMap
    public K lastKey() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.key[this.last];
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntSortedMap, java.util.SortedMap
    public Object2IntSortedMap<K> tailMap(K from) {
        throw new UnsupportedOperationException();
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntSortedMap, java.util.SortedMap
    public Object2IntSortedMap<K> headMap(K to) {
        throw new UnsupportedOperationException();
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntSortedMap, java.util.SortedMap
    public Object2IntSortedMap<K> subMap(K from, K to) {
        throw new UnsupportedOperationException();
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntSortedMap, java.util.SortedMap
    public Comparator<? super K> comparator() {
        return null;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2IntLinkedOpenHashMap$MapIterator.class */
    public abstract class MapIterator<ConsumerType> {
        int prev;
        int next;
        int curr;
        int index;

        abstract void acceptOnIndex(ConsumerType consumertype, int i);

        protected MapIterator() {
            Object2IntLinkedOpenHashMap.this = r4;
            this.prev = -1;
            this.next = -1;
            this.curr = -1;
            this.index = -1;
            this.next = r4.first;
            this.index = 0;
        }

        private MapIterator(K from) {
            Object2IntLinkedOpenHashMap.this = r6;
            this.prev = -1;
            this.next = -1;
            this.curr = -1;
            this.index = -1;
            if (from == null) {
                if (r6.containsNullKey) {
                    this.next = (int) r6.link[r6.f132n];
                    this.prev = r6.f132n;
                    return;
                }
                throw new NoSuchElementException("The key " + from + " does not belong to this map.");
            } else if (Objects.equals(r6.key[r6.last], from)) {
                this.prev = r6.last;
                this.index = r6.size;
            } else {
                int mix = HashCommon.mix(from.hashCode());
                int i = r6.mask;
                while (true) {
                    int pos = mix & i;
                    if (r6.key[pos] != null) {
                        if (r6.key[pos].equals(from)) {
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
                this.index = Object2IntLinkedOpenHashMap.this.size;
            } else {
                int pos = Object2IntLinkedOpenHashMap.this.first;
                this.index = 1;
                while (pos != this.prev) {
                    pos = (int) Object2IntLinkedOpenHashMap.this.link[pos];
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
            this.next = (int) Object2IntLinkedOpenHashMap.this.link[this.curr];
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
            this.prev = (int) (Object2IntLinkedOpenHashMap.this.link[this.curr] >>> 32);
            this.next = this.curr;
            if (this.index >= 0) {
                this.index--;
            }
            return this.curr;
        }

        public void forEachRemaining(ConsumerType action) {
            while (hasNext()) {
                this.curr = this.next;
                this.next = (int) Object2IntLinkedOpenHashMap.this.link[this.curr];
                this.prev = this.curr;
                if (this.index >= 0) {
                    this.index++;
                }
                acceptOnIndex(action, this.curr);
            }
        }

        public void remove() {
            K curr;
            ensureIndexKnown();
            if (this.curr == -1) {
                throw new IllegalStateException();
            }
            if (this.curr == this.prev) {
                this.index--;
                this.prev = (int) (Object2IntLinkedOpenHashMap.this.link[this.curr] >>> 32);
            } else {
                this.next = (int) Object2IntLinkedOpenHashMap.this.link[this.curr];
            }
            Object2IntLinkedOpenHashMap.this.size--;
            if (this.prev == -1) {
                Object2IntLinkedOpenHashMap.this.first = this.next;
            } else {
                long[] jArr = Object2IntLinkedOpenHashMap.this.link;
                int i = this.prev;
                jArr[i] = jArr[i] ^ ((Object2IntLinkedOpenHashMap.this.link[this.prev] ^ (this.next & JSType.MAX_UINT)) & JSType.MAX_UINT);
            }
            if (this.next == -1) {
                Object2IntLinkedOpenHashMap.this.last = this.prev;
            } else {
                long[] jArr2 = Object2IntLinkedOpenHashMap.this.link;
                int i2 = this.next;
                jArr2[i2] = jArr2[i2] ^ ((Object2IntLinkedOpenHashMap.this.link[this.next] ^ ((this.prev & JSType.MAX_UINT) << 32)) & (-4294967296L));
            }
            int pos = this.curr;
            this.curr = -1;
            if (pos == Object2IntLinkedOpenHashMap.this.f132n) {
                Object2IntLinkedOpenHashMap.this.containsNullKey = false;
                Object2IntLinkedOpenHashMap.this.key[Object2IntLinkedOpenHashMap.this.f132n] = null;
                return;
            }
            K[] key = Object2IntLinkedOpenHashMap.this.key;
            while (true) {
                int last = pos;
                int i3 = last + 1;
                int i4 = Object2IntLinkedOpenHashMap.this.mask;
                while (true) {
                    pos = i3 & i4;
                    curr = key[pos];
                    if (curr == null) {
                        key[last] = null;
                        return;
                    }
                    int slot = HashCommon.mix(curr.hashCode()) & Object2IntLinkedOpenHashMap.this.mask;
                    if (last > pos) {
                        if (last >= slot && slot > pos) {
                            break;
                        }
                        i3 = pos + 1;
                        i4 = Object2IntLinkedOpenHashMap.this.mask;
                    } else if (last >= slot || slot > pos) {
                        break;
                    } else {
                        i3 = pos + 1;
                        i4 = Object2IntLinkedOpenHashMap.this.mask;
                    }
                }
                key[last] = curr;
                Object2IntLinkedOpenHashMap.this.value[last] = Object2IntLinkedOpenHashMap.this.value[pos];
                if (this.next == pos) {
                    this.next = last;
                }
                if (this.prev == pos) {
                    this.prev = last;
                }
                Object2IntLinkedOpenHashMap.this.fixPointers(pos, last);
            }
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

        public void set(Object2IntMap.Entry<K> ok) {
            throw new UnsupportedOperationException();
        }

        public void add(Object2IntMap.Entry<K> ok) {
            throw new UnsupportedOperationException();
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2IntLinkedOpenHashMap$EntryIterator.class */
    public final class EntryIterator extends Object2IntLinkedOpenHashMap<K>.MapIterator<Consumer<? super Object2IntMap.Entry<K>>> implements ObjectListIterator<Object2IntMap.Entry<K>> {
        private Object2IntLinkedOpenHashMap<K>.MapEntry entry;

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntLinkedOpenHashMap.MapIterator
        /* bridge */ /* synthetic */ void acceptOnIndex(Object obj, int i) {
            acceptOnIndex((Consumer) ((Consumer) obj), i);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectListIterator, java.util.ListIterator
        public /* bridge */ /* synthetic */ void add(Object obj) {
            super.add((Object2IntMap.Entry) ((Object2IntMap.Entry) obj));
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectListIterator, java.util.ListIterator
        public /* bridge */ /* synthetic */ void set(Object obj) {
            super.set((Object2IntMap.Entry) ((Object2IntMap.Entry) obj));
        }

        @Override // java.util.Iterator
        public /* bridge */ /* synthetic */ void forEachRemaining(Consumer consumer) {
            super.forEachRemaining((EntryIterator) consumer);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public EntryIterator() {
            super();
            Object2IntLinkedOpenHashMap.this = r4;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public EntryIterator(K from) {
            super(from);
            Object2IntLinkedOpenHashMap.this = r6;
        }

        final void acceptOnIndex(Consumer<? super Object2IntMap.Entry<K>> action, int index) {
            action.accept(new MapEntry(index));
        }

        @Override // java.util.Iterator, java.util.ListIterator
        public Object2IntLinkedOpenHashMap<K>.MapEntry next() {
            Object2IntLinkedOpenHashMap<K>.MapEntry mapEntry = new MapEntry(nextEntry());
            this.entry = mapEntry;
            return mapEntry;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.BidirectionalIterator, java.util.ListIterator
        public Object2IntLinkedOpenHashMap<K>.MapEntry previous() {
            Object2IntLinkedOpenHashMap<K>.MapEntry mapEntry = new MapEntry(previousEntry());
            this.entry = mapEntry;
            return mapEntry;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntLinkedOpenHashMap.MapIterator, com.viaversion.viaversion.libs.fastutil.objects.ObjectListIterator, java.util.Iterator, java.util.ListIterator
        public void remove() {
            super.remove();
            this.entry.index = -1;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2IntLinkedOpenHashMap$FastEntryIterator.class */
    public final class FastEntryIterator extends Object2IntLinkedOpenHashMap<K>.MapIterator<Consumer<? super Object2IntMap.Entry<K>>> implements ObjectListIterator<Object2IntMap.Entry<K>> {
        final Object2IntLinkedOpenHashMap<K>.MapEntry entry;

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntLinkedOpenHashMap.MapIterator
        /* bridge */ /* synthetic */ void acceptOnIndex(Object obj, int i) {
            acceptOnIndex((Consumer) ((Consumer) obj), i);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectListIterator, java.util.ListIterator
        public /* bridge */ /* synthetic */ void add(Object obj) {
            super.add((Object2IntMap.Entry) ((Object2IntMap.Entry) obj));
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectListIterator, java.util.ListIterator
        public /* bridge */ /* synthetic */ void set(Object obj) {
            super.set((Object2IntMap.Entry) ((Object2IntMap.Entry) obj));
        }

        @Override // java.util.Iterator
        public /* bridge */ /* synthetic */ void forEachRemaining(Consumer consumer) {
            super.forEachRemaining((FastEntryIterator) consumer);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public FastEntryIterator() {
            super();
            Object2IntLinkedOpenHashMap.this = r6;
            this.entry = new MapEntry();
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public FastEntryIterator(K from) {
            super(from);
            Object2IntLinkedOpenHashMap.this = r6;
            this.entry = new MapEntry();
        }

        final void acceptOnIndex(Consumer<? super Object2IntMap.Entry<K>> action, int index) {
            this.entry.index = index;
            action.accept(this.entry);
        }

        @Override // java.util.Iterator, java.util.ListIterator
        public Object2IntLinkedOpenHashMap<K>.MapEntry next() {
            this.entry.index = nextEntry();
            return this.entry;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.BidirectionalIterator, java.util.ListIterator
        public Object2IntLinkedOpenHashMap<K>.MapEntry previous() {
            this.entry.index = previousEntry();
            return this.entry;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2IntLinkedOpenHashMap$MapEntrySet.class */
    public final class MapEntrySet extends AbstractObjectSortedSet<Object2IntMap.Entry<K>> implements Object2IntSortedMap.FastSortedEntrySet<K> {
        private static final int SPLITERATOR_CHARACTERISTICS = 81;

        private MapEntrySet() {
            Object2IntLinkedOpenHashMap.this = r4;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSet
        public /* bridge */ /* synthetic */ ObjectBidirectionalIterator iterator(Object obj) {
            return iterator((Object2IntMap.Entry) ((Object2IntMap.Entry) obj));
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSortedSet, com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet, com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable
        public ObjectBidirectionalIterator<Object2IntMap.Entry<K>> iterator() {
            return new EntryIterator();
        }

        @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectSet, java.util.Set
        public ObjectSpliterator<Object2IntMap.Entry<K>> spliterator() {
            return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(Object2IntLinkedOpenHashMap.this), 81);
        }

        @Override // java.util.SortedSet
        public Comparator<? super Object2IntMap.Entry<K>> comparator() {
            return null;
        }

        public ObjectSortedSet<Object2IntMap.Entry<K>> subSet(Object2IntMap.Entry<K> fromElement, Object2IntMap.Entry<K> toElement) {
            throw new UnsupportedOperationException();
        }

        public ObjectSortedSet<Object2IntMap.Entry<K>> headSet(Object2IntMap.Entry<K> toElement) {
            throw new UnsupportedOperationException();
        }

        public ObjectSortedSet<Object2IntMap.Entry<K>> tailSet(Object2IntMap.Entry<K> fromElement) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.SortedSet
        public Object2IntMap.Entry<K> first() {
            if (Object2IntLinkedOpenHashMap.this.size == 0) {
                throw new NoSuchElementException();
            }
            return new MapEntry(Object2IntLinkedOpenHashMap.this.first);
        }

        @Override // java.util.SortedSet
        public Object2IntMap.Entry<K> last() {
            if (Object2IntLinkedOpenHashMap.this.size == 0) {
                throw new NoSuchElementException();
            }
            return new MapEntry(Object2IntLinkedOpenHashMap.this.last);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object o) {
            K curr;
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<?, ?> e = (Map.Entry) o;
            if (e.getValue() == null || !(e.getValue() instanceof Integer)) {
                return false;
            }
            Object key = e.getKey();
            int v = ((Integer) e.getValue()).intValue();
            if (key == null) {
                return Object2IntLinkedOpenHashMap.this.containsNullKey && Object2IntLinkedOpenHashMap.this.value[Object2IntLinkedOpenHashMap.this.f132n] == v;
            }
            K[] key2 = Object2IntLinkedOpenHashMap.this.key;
            int mix = HashCommon.mix(key.hashCode()) & Object2IntLinkedOpenHashMap.this.mask;
            int pos = mix;
            K curr2 = key2[mix];
            if (curr2 == null) {
                return false;
            }
            if (key.equals(curr2)) {
                return Object2IntLinkedOpenHashMap.this.value[pos] == v;
            }
            do {
                int i = (pos + 1) & Object2IntLinkedOpenHashMap.this.mask;
                pos = i;
                curr = key2[i];
                if (curr == null) {
                    return false;
                }
            } while (!key.equals(curr));
            return Object2IntLinkedOpenHashMap.this.value[pos] == v;
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
            if (key == null) {
                if (Object2IntLinkedOpenHashMap.this.containsNullKey && Object2IntLinkedOpenHashMap.this.value[Object2IntLinkedOpenHashMap.this.f132n] == v) {
                    Object2IntLinkedOpenHashMap.this.removeNullEntry();
                    return true;
                }
                return false;
            }
            K[] key2 = Object2IntLinkedOpenHashMap.this.key;
            int mix = HashCommon.mix(key.hashCode()) & Object2IntLinkedOpenHashMap.this.mask;
            int pos = mix;
            K curr = key2[mix];
            if (curr == null) {
                return false;
            }
            if (curr.equals(key)) {
                if (Object2IntLinkedOpenHashMap.this.value[pos] == v) {
                    Object2IntLinkedOpenHashMap.this.removeEntry(pos);
                    return true;
                }
                return false;
            }
            while (true) {
                int i = (pos + 1) & Object2IntLinkedOpenHashMap.this.mask;
                pos = i;
                K curr2 = key2[i];
                if (curr2 == null) {
                    return false;
                }
                if (curr2.equals(key) && Object2IntLinkedOpenHashMap.this.value[pos] == v) {
                    Object2IntLinkedOpenHashMap.this.removeEntry(pos);
                    return true;
                }
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return Object2IntLinkedOpenHashMap.this.size;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            Object2IntLinkedOpenHashMap.this.clear();
        }

        public ObjectListIterator<Object2IntMap.Entry<K>> iterator(Object2IntMap.Entry<K> from) {
            return new EntryIterator(from.getKey());
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntSortedMap.FastSortedEntrySet, com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap.FastEntrySet
        public ObjectListIterator<Object2IntMap.Entry<K>> fastIterator() {
            return new FastEntryIterator();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntSortedMap.FastSortedEntrySet
        public ObjectListIterator<Object2IntMap.Entry<K>> fastIterator(Object2IntMap.Entry<K> from) {
            return new FastEntryIterator(from.getKey());
        }

        @Override // java.lang.Iterable
        public void forEach(Consumer<? super Object2IntMap.Entry<K>> consumer) {
            int i = Object2IntLinkedOpenHashMap.this.size;
            int next = Object2IntLinkedOpenHashMap.this.first;
            while (true) {
                int i2 = i;
                i--;
                if (i2 != 0) {
                    int curr = next;
                    next = (int) Object2IntLinkedOpenHashMap.this.link[curr];
                    consumer.accept(new AbstractObject2IntMap.BasicEntry(Object2IntLinkedOpenHashMap.this.key[curr], Object2IntLinkedOpenHashMap.this.value[curr]));
                } else {
                    return;
                }
            }
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap.FastEntrySet
        public void fastForEach(Consumer<? super Object2IntMap.Entry<K>> consumer) {
            AbstractObject2IntMap.BasicEntry<K> entry = new AbstractObject2IntMap.BasicEntry<>();
            int i = Object2IntLinkedOpenHashMap.this.size;
            int next = Object2IntLinkedOpenHashMap.this.first;
            while (true) {
                int i2 = i;
                i--;
                if (i2 != 0) {
                    int curr = next;
                    next = (int) Object2IntLinkedOpenHashMap.this.link[curr];
                    entry.key = Object2IntLinkedOpenHashMap.this.key[curr];
                    entry.value = Object2IntLinkedOpenHashMap.this.value[curr];
                    consumer.accept(entry);
                } else {
                    return;
                }
            }
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap
    public Object2IntSortedMap.FastSortedEntrySet<K> object2IntEntrySet() {
        if (this.entries == null) {
            this.entries = new MapEntrySet();
        }
        return this.entries;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2IntLinkedOpenHashMap$KeyIterator.class */
    public final class KeyIterator extends Object2IntLinkedOpenHashMap<K>.MapIterator<Consumer<? super K>> implements ObjectListIterator<K> {
        @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntLinkedOpenHashMap.MapIterator
        /* bridge */ /* synthetic */ void acceptOnIndex(Object obj, int i) {
            acceptOnIndex((Consumer) ((Consumer) obj), i);
        }

        @Override // java.util.Iterator
        public /* bridge */ /* synthetic */ void forEachRemaining(Consumer consumer) {
            super.forEachRemaining((KeyIterator) consumer);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public KeyIterator(K k) {
            super(k);
            Object2IntLinkedOpenHashMap.this = r6;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.BidirectionalIterator, java.util.ListIterator
        public K previous() {
            return Object2IntLinkedOpenHashMap.this.key[previousEntry()];
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public KeyIterator() {
            super();
            Object2IntLinkedOpenHashMap.this = r4;
        }

        final void acceptOnIndex(Consumer<? super K> action, int index) {
            action.accept((Object) Object2IntLinkedOpenHashMap.this.key[index]);
        }

        @Override // java.util.Iterator, java.util.ListIterator
        public K next() {
            return Object2IntLinkedOpenHashMap.this.key[nextEntry()];
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2IntLinkedOpenHashMap$KeySet.class */
    public final class KeySet extends AbstractObjectSortedSet<K> {
        private static final int SPLITERATOR_CHARACTERISTICS = 81;

        private KeySet() {
            Object2IntLinkedOpenHashMap.this = r4;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSet
        public ObjectListIterator<K> iterator(K from) {
            return new KeyIterator(from);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSortedSet, com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet, com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable
        public ObjectListIterator<K> iterator() {
            return new KeyIterator();
        }

        @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectSet, java.util.Set
        public ObjectSpliterator<K> spliterator() {
            return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(Object2IntLinkedOpenHashMap.this), 81);
        }

        @Override // java.lang.Iterable
        public void forEach(Consumer<? super K> consumer) {
            int i = Object2IntLinkedOpenHashMap.this.size;
            int next = Object2IntLinkedOpenHashMap.this.first;
            while (true) {
                int i2 = i;
                i--;
                if (i2 != 0) {
                    int curr = next;
                    next = (int) Object2IntLinkedOpenHashMap.this.link[curr];
                    consumer.accept((Object) Object2IntLinkedOpenHashMap.this.key[curr]);
                } else {
                    return;
                }
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return Object2IntLinkedOpenHashMap.this.size;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object k) {
            return Object2IntLinkedOpenHashMap.this.containsKey(k);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object k) {
            int oldSize = Object2IntLinkedOpenHashMap.this.size;
            Object2IntLinkedOpenHashMap.this.removeInt(k);
            return Object2IntLinkedOpenHashMap.this.size != oldSize;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            Object2IntLinkedOpenHashMap.this.clear();
        }

        @Override // java.util.SortedSet
        public K first() {
            if (Object2IntLinkedOpenHashMap.this.size == 0) {
                throw new NoSuchElementException();
            }
            return Object2IntLinkedOpenHashMap.this.key[Object2IntLinkedOpenHashMap.this.first];
        }

        @Override // java.util.SortedSet
        public K last() {
            if (Object2IntLinkedOpenHashMap.this.size == 0) {
                throw new NoSuchElementException();
            }
            return Object2IntLinkedOpenHashMap.this.key[Object2IntLinkedOpenHashMap.this.last];
        }

        @Override // java.util.SortedSet
        public Comparator<? super K> comparator() {
            return null;
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
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2IntSortedMap, com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2IntMap, com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap, java.util.Map
    public ObjectSortedSet<K> keySet() {
        if (this.keys == null) {
            this.keys = new KeySet();
        }
        return this.keys;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/Object2IntLinkedOpenHashMap$ValueIterator.class */
    public final class ValueIterator extends Object2IntLinkedOpenHashMap<K>.MapIterator<IntConsumer> implements IntListIterator {
        @Override // java.util.PrimitiveIterator.OfInt
        public /* bridge */ /* synthetic */ void forEachRemaining(IntConsumer intConsumer) {
            super.forEachRemaining((ValueIterator) intConsumer);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntBidirectionalIterator
        public int previousInt() {
            return Object2IntLinkedOpenHashMap.this.value[previousEntry()];
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ValueIterator() {
            super();
            Object2IntLinkedOpenHashMap.this = r4;
        }

        public final void acceptOnIndex(IntConsumer action, int index) {
            action.accept(Object2IntLinkedOpenHashMap.this.value[index]);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
        public int nextInt() {
            return Object2IntLinkedOpenHashMap.this.value[nextEntry()];
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2IntSortedMap, com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2IntMap, com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap, java.util.Map
    public IntCollection values() {
        if (this.values == null) {
            this.values = new AbstractIntCollection() { // from class: com.viaversion.viaversion.libs.fastutil.objects.Object2IntLinkedOpenHashMap.1
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
                    return IntSpliterators.asSpliterator(iterator2(), Size64.sizeOf(Object2IntLinkedOpenHashMap.this), 336);
                }

                @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterable
                public void forEach(IntConsumer consumer) {
                    int i = Object2IntLinkedOpenHashMap.this.size;
                    int next = Object2IntLinkedOpenHashMap.this.first;
                    while (true) {
                        int i2 = i;
                        i--;
                        if (i2 != 0) {
                            int curr = next;
                            next = (int) Object2IntLinkedOpenHashMap.this.link[curr];
                            consumer.accept(Object2IntLinkedOpenHashMap.this.value[curr]);
                        } else {
                            return;
                        }
                    }
                }

                @Override // java.util.AbstractCollection, java.util.Collection
                public int size() {
                    return Object2IntLinkedOpenHashMap.this.size;
                }

                @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
                public boolean contains(int v) {
                    return Object2IntLinkedOpenHashMap.this.containsValue(v);
                }

                @Override // java.util.AbstractCollection, java.util.Collection
                public void clear() {
                    Object2IntLinkedOpenHashMap.this.clear();
                }
            };
        }
        return this.values;
    }

    public boolean trim() {
        return trim(this.size);
    }

    public boolean trim(int n) {
        int l = HashCommon.nextPowerOfTwo((int) Math.ceil(n / this.f133f));
        if (l >= this.f132n || this.size > HashCommon.maxFill(l, this.f133f)) {
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
        int[] value = this.value;
        int mask = newN - 1;
        K[] newKey = (K[]) new Object[newN + 1];
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
        this.f132n = newN;
        this.mask = mask;
        this.maxFill = HashCommon.maxFill(this.f132n, this.f133f);
        this.key = newKey;
        this.value = newValue;
    }

    public Object2IntLinkedOpenHashMap<K> clone() {
        try {
            Object2IntLinkedOpenHashMap<K> c = (Object2IntLinkedOpenHashMap) super.clone();
            c.keys = null;
            c.values = null;
            c.entries = null;
            c.containsNullKey = this.containsNullKey;
            c.key = (K[]) ((Object[]) this.key.clone());
            c.value = (int[]) this.value.clone();
            c.link = (long[]) this.link.clone();
            return c;
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2IntMap, java.util.Map
    public int hashCode() {
        int h = 0;
        int j = realSize();
        int i = 0;
        int t = 0;
        while (true) {
            int i2 = j;
            j--;
            if (i2 == 0) {
                break;
            }
            while (this.key[i] == null) {
                i++;
            }
            if (this != this.key[i]) {
                t = this.key[i].hashCode();
            }
            t ^= this.value[i];
            h += t;
            i++;
        }
        if (this.containsNullKey) {
            h += this.value[this.f132n];
        }
        return h;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        K[] key = this.key;
        int[] value = this.value;
        Object2IntLinkedOpenHashMap<K>.EntryIterator i = new EntryIterator();
        s.defaultWriteObject();
        int j = this.size;
        while (true) {
            int i2 = j;
            j--;
            if (i2 != 0) {
                int e = i.nextEntry();
                s.writeObject(key[e]);
                s.writeInt(value[e]);
            } else {
                return;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        int pos;
        s.defaultReadObject();
        this.f132n = HashCommon.arraySize(this.size, this.f133f);
        this.maxFill = HashCommon.maxFill(this.f132n, this.f133f);
        this.mask = this.f132n - 1;
        K[] key = (K[]) new Object[this.f132n + 1];
        this.key = key;
        int[] value = new int[this.f132n + 1];
        this.value = value;
        long[] link = new long[this.f132n + 1];
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
            Object readObject = s.readObject();
            int v = s.readInt();
            if (readObject == null) {
                pos = this.f132n;
                this.containsNullKey = true;
            } else {
                int mix = HashCommon.mix(readObject.hashCode());
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
            key[pos] = readObject;
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
