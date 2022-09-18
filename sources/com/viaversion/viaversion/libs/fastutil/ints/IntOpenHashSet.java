package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Hash;
import com.viaversion.viaversion.libs.fastutil.HashCommon;
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

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntOpenHashSet.class */
public class IntOpenHashSet extends AbstractIntSet implements Serializable, Cloneable, Hash {
    private static final long serialVersionUID = 0;
    private static final boolean ASSERTS = false;
    protected transient int[] key;
    protected transient int mask;
    protected transient boolean containsNull;

    /* renamed from: n */
    protected transient int f113n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;

    /* renamed from: f */
    protected final float f114f;

    public IntOpenHashSet(int expected, float f) {
        if (f <= 0.0f || f >= 1.0f) {
            throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1");
        }
        if (expected < 0) {
            throw new IllegalArgumentException("The expected number of elements must be nonnegative");
        }
        this.f114f = f;
        int arraySize = HashCommon.arraySize(expected, f);
        this.f113n = arraySize;
        this.minN = arraySize;
        this.mask = this.f113n - 1;
        this.maxFill = HashCommon.maxFill(this.f113n, f);
        this.key = new int[this.f113n + 1];
    }

    public IntOpenHashSet(int expected) {
        this(expected, 0.75f);
    }

    public IntOpenHashSet() {
        this(16, 0.75f);
    }

    public IntOpenHashSet(Collection<? extends Integer> c, float f) {
        this(c.size(), f);
        addAll(c);
    }

    public IntOpenHashSet(Collection<? extends Integer> c) {
        this(c, 0.75f);
    }

    public IntOpenHashSet(IntCollection c, float f) {
        this(c.size(), f);
        addAll(c);
    }

    public IntOpenHashSet(IntCollection c) {
        this(c, 0.75f);
    }

    public IntOpenHashSet(IntIterator i, float f) {
        this(16, f);
        while (i.hasNext()) {
            add(i.nextInt());
        }
    }

    public IntOpenHashSet(IntIterator i) {
        this(i, 0.75f);
    }

    public IntOpenHashSet(Iterator<?> i, float f) {
        this(IntIterators.asIntIterator(i), f);
    }

    public IntOpenHashSet(Iterator<?> i) {
        this(IntIterators.asIntIterator(i));
    }

    public IntOpenHashSet(int[] a, int offset, int length, float f) {
        this(length < 0 ? 0 : length, f);
        IntArrays.ensureOffsetLength(a, offset, length);
        for (int i = 0; i < length; i++) {
            add(a[offset + i]);
        }
    }

    public IntOpenHashSet(int[] a, int offset, int length) {
        this(a, offset, length, 0.75f);
    }

    public IntOpenHashSet(int[] a, float f) {
        this(a, 0, a.length, f);
    }

    public IntOpenHashSet(int[] a) {
        this(a, 0.75f);
    }

    /* renamed from: of */
    public static IntOpenHashSet m193of() {
        return new IntOpenHashSet();
    }

    /* renamed from: of */
    public static IntOpenHashSet m192of(int e) {
        IntOpenHashSet result = new IntOpenHashSet(1, 0.75f);
        result.add(e);
        return result;
    }

    /* renamed from: of */
    public static IntOpenHashSet m191of(int e0, int e1) {
        IntOpenHashSet result = new IntOpenHashSet(2, 0.75f);
        result.add(e0);
        if (!result.add(e1)) {
            throw new IllegalArgumentException("Duplicate element: " + e1);
        }
        return result;
    }

    /* renamed from: of */
    public static IntOpenHashSet m190of(int e0, int e1, int e2) {
        IntOpenHashSet result = new IntOpenHashSet(3, 0.75f);
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
    public static IntOpenHashSet m189of(int... a) {
        IntOpenHashSet result = new IntOpenHashSet(a.length, 0.75f);
        for (int element : a) {
            if (!result.add(element)) {
                throw new IllegalArgumentException("Duplicate element " + element);
            }
        }
        return result;
    }

    public static IntOpenHashSet toSet(IntStream stream) {
        return (IntOpenHashSet) stream.collect(IntOpenHashSet::new, (v0, v1) -> {
            v0.add(v1);
        }, (v0, v1) -> {
            v0.addAll(v1);
        });
    }

    public static IntOpenHashSet toSetWithExpectedSize(IntStream stream, int expectedSize) {
        if (expectedSize <= 16) {
            return toSet(stream);
        }
        return (IntOpenHashSet) stream.collect(new IntCollections.SizeDecreasingSupplier(expectedSize, size -> {
            return size <= 16 ? new IntOpenHashSet() : new IntOpenHashSet(size);
        }), (v0, v1) -> {
            v0.add(v1);
        }, (v0, v1) -> {
            v0.addAll(v1);
        });
    }

    public int realSize() {
        return this.containsNull ? this.size - 1 : this.size;
    }

    private void ensureCapacity(int capacity) {
        int needed = HashCommon.arraySize(capacity, this.f114f);
        if (needed > this.f113n) {
            rehash(needed);
        }
    }

    private void tryCapacity(long capacity) {
        int needed = (int) Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long) Math.ceil(((float) capacity) / this.f114f))));
        if (needed > this.f113n) {
            rehash(needed);
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
    public boolean addAll(IntCollection c) {
        if (this.f114f <= 0.5d) {
            ensureCapacity(c.size());
        } else {
            tryCapacity(size() + c.size());
        }
        return super.addAll(c);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection
    public boolean addAll(Collection<? extends Integer> c) {
        if (this.f114f <= 0.5d) {
            ensureCapacity(c.size());
        } else {
            tryCapacity(size() + c.size());
        }
        return super.addAll(c);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
    public boolean add(int k) {
        int curr;
        if (k == 0) {
            if (this.containsNull) {
                return false;
            }
            this.containsNull = true;
        } else {
            int[] key = this.key;
            int mix = HashCommon.mix(k) & this.mask;
            int pos = mix;
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
        int i2 = this.size;
        this.size = i2 + 1;
        if (i2 >= this.maxFill) {
            rehash(HashCommon.arraySize(this.size + 1, this.f114f));
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
        }
    }

    private boolean removeEntry(int pos) {
        this.size--;
        shiftKeys(pos);
        if (this.f113n > this.minN && this.size < this.maxFill / 4 && this.f113n > 16) {
            rehash(this.f113n / 2);
            return true;
        }
        return true;
    }

    private boolean removeNullEntry() {
        this.containsNull = false;
        this.key[this.f113n] = 0;
        this.size--;
        if (this.f113n > this.minN && this.size < this.maxFill / 4 && this.f113n > 16) {
            rehash(this.f113n / 2);
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

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public void clear() {
        if (this.size == 0) {
            return;
        }
        this.size = 0;
        this.containsNull = false;
        Arrays.fill(this.key, 0);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        return this.size;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean isEmpty() {
        return this.size == 0;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntOpenHashSet$SetIterator.class */
    public final class SetIterator implements IntIterator {
        int pos;
        int last;

        /* renamed from: c */
        int f115c;
        boolean mustReturnNull;
        IntArrayList wrapped;

        private SetIterator() {
            IntOpenHashSet.this = r4;
            this.pos = IntOpenHashSet.this.f113n;
            this.last = -1;
            this.f115c = IntOpenHashSet.this.size;
            this.mustReturnNull = IntOpenHashSet.this.containsNull;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.f115c != 0;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
        public int nextInt() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            this.f115c--;
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                this.last = IntOpenHashSet.this.f113n;
                return IntOpenHashSet.this.key[IntOpenHashSet.this.f113n];
            }
            int[] key = IntOpenHashSet.this.key;
            do {
                int i = this.pos - 1;
                this.pos = i;
                if (i < 0) {
                    this.last = Integer.MIN_VALUE;
                    return this.wrapped.getInt((-this.pos) - 1);
                }
            } while (key[this.pos] == 0);
            int i2 = this.pos;
            this.last = i2;
            return key[i2];
        }

        private final void shiftKeys(int pos) {
            int curr;
            int[] key = IntOpenHashSet.this.key;
            while (true) {
                int last = pos;
                int i = last + 1;
                int i2 = IntOpenHashSet.this.mask;
                while (true) {
                    pos = i & i2;
                    curr = key[pos];
                    if (curr == 0) {
                        key[last] = 0;
                        return;
                    }
                    int slot = HashCommon.mix(curr) & IntOpenHashSet.this.mask;
                    if (last > pos) {
                        if (last >= slot && slot > pos) {
                            break;
                        }
                        i = pos + 1;
                        i2 = IntOpenHashSet.this.mask;
                    } else if (last >= slot || slot > pos) {
                        break;
                    } else {
                        i = pos + 1;
                        i2 = IntOpenHashSet.this.mask;
                    }
                }
                if (pos < last) {
                    if (this.wrapped == null) {
                        this.wrapped = new IntArrayList(2);
                    }
                    this.wrapped.add(key[pos]);
                }
                key[last] = curr;
            }
        }

        @Override // java.util.Iterator
        public void remove() {
            if (this.last == -1) {
                throw new IllegalStateException();
            }
            if (this.last == IntOpenHashSet.this.f113n) {
                IntOpenHashSet.this.containsNull = false;
                IntOpenHashSet.this.key[IntOpenHashSet.this.f113n] = 0;
            } else if (this.pos >= 0) {
                shiftKeys(this.last);
            } else {
                IntOpenHashSet.this.remove(this.wrapped.getInt((-this.pos) - 1));
                this.last = -1;
                return;
            }
            IntOpenHashSet.this.size--;
            this.last = -1;
        }

        @Override // java.util.PrimitiveIterator.OfInt
        public void forEachRemaining(java.util.function.IntConsumer action) {
            int[] key = IntOpenHashSet.this.key;
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                this.last = IntOpenHashSet.this.f113n;
                action.accept(key[IntOpenHashSet.this.f113n]);
                this.f115c--;
            }
            while (this.f115c != 0) {
                int i = this.pos - 1;
                this.pos = i;
                if (i < 0) {
                    this.last = Integer.MIN_VALUE;
                    action.accept(this.wrapped.getInt((-this.pos) - 1));
                    this.f115c--;
                } else if (key[this.pos] != 0) {
                    int i2 = this.pos;
                    this.last = i2;
                    action.accept(key[i2]);
                    this.f115c--;
                }
            }
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntSet, com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
    /* renamed from: iterator */
    public Iterator<Integer> iterator2() {
        return new SetIterator();
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntOpenHashSet$SetSpliterator.class */
    public final class SetSpliterator implements IntSpliterator {
        private static final int POST_SPLIT_CHARACTERISTICS = 257;
        int pos;
        int max;

        /* renamed from: c */
        int f116c;
        boolean mustReturnNull;
        boolean hasSplit;

        SetSpliterator() {
            IntOpenHashSet.this = r4;
            this.pos = 0;
            this.max = IntOpenHashSet.this.f113n;
            this.f116c = 0;
            this.mustReturnNull = IntOpenHashSet.this.containsNull;
            this.hasSplit = false;
        }

        SetSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
            IntOpenHashSet.this = r4;
            this.pos = 0;
            this.max = IntOpenHashSet.this.f113n;
            this.f116c = 0;
            this.mustReturnNull = IntOpenHashSet.this.containsNull;
            this.hasSplit = false;
            this.pos = pos;
            this.max = max;
            this.mustReturnNull = mustReturnNull;
            this.hasSplit = hasSplit;
        }

        @Override // java.util.Spliterator.OfInt
        public boolean tryAdvance(java.util.function.IntConsumer action) {
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                this.f116c++;
                action.accept(IntOpenHashSet.this.key[IntOpenHashSet.this.f113n]);
                return true;
            }
            int[] key = IntOpenHashSet.this.key;
            while (this.pos < this.max) {
                if (key[this.pos] != 0) {
                    this.f116c++;
                    int i = this.pos;
                    this.pos = i + 1;
                    action.accept(key[i]);
                    return true;
                }
                this.pos++;
            }
            return false;
        }

        @Override // java.util.Spliterator.OfInt
        public void forEachRemaining(java.util.function.IntConsumer action) {
            int[] key = IntOpenHashSet.this.key;
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                action.accept(key[IntOpenHashSet.this.f113n]);
                this.f116c++;
            }
            while (this.pos < this.max) {
                if (key[this.pos] != 0) {
                    action.accept(key[this.pos]);
                    this.f116c++;
                }
                this.pos++;
            }
        }

        @Override // java.util.Spliterator
        public int characteristics() {
            if (this.hasSplit) {
                return POST_SPLIT_CHARACTERISTICS;
            }
            return 321;
        }

        @Override // java.util.Spliterator
        public long estimateSize() {
            if (!this.hasSplit) {
                return IntOpenHashSet.this.size - this.f116c;
            }
            return Math.min(IntOpenHashSet.this.size - this.f116c, ((long) ((IntOpenHashSet.this.realSize() / IntOpenHashSet.this.f113n) * (this.max - this.pos))) + (this.mustReturnNull ? 1 : 0));
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator, java.util.Spliterator.OfInt, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public SetSpliterator trySplit() {
            int retLen;
            if (this.pos >= this.max - 1 || (retLen = (this.max - this.pos) >> 1) <= 1) {
                return null;
            }
            int myNewPos = this.pos + retLen;
            int retPos = this.pos;
            SetSpliterator split = new SetSpliterator(retPos, myNewPos, this.mustReturnNull, true);
            this.pos = myNewPos;
            this.mustReturnNull = false;
            this.hasSplit = true;
            return split;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator
        public long skip(long n) {
            if (n < 0) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            }
            if (n == 0) {
                return 0L;
            }
            long skipped = 0;
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                skipped = 0 + 1;
                n--;
            }
            int[] key = IntOpenHashSet.this.key;
            while (this.pos < this.max && n > 0) {
                int i = this.pos;
                this.pos = i + 1;
                if (key[i] != 0) {
                    skipped++;
                    n--;
                }
            }
            return skipped;
        }
    }

    @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
    /* renamed from: spliterator */
    public Spliterator<Integer> spliterator2() {
        return new SetSpliterator();
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterable
    public void forEach(java.util.function.IntConsumer action) {
        if (this.containsNull) {
            action.accept(this.key[this.f113n]);
        }
        int[] key = this.key;
        int pos = this.f113n;
        while (true) {
            int i = pos;
            pos--;
            if (i != 0) {
                if (key[pos] != 0) {
                    action.accept(key[pos]);
                }
            } else {
                return;
            }
        }
    }

    public boolean trim() {
        return trim(this.size);
    }

    public boolean trim(int n) {
        int l = HashCommon.nextPowerOfTwo((int) Math.ceil(n / this.f114f));
        if (l >= this.f113n || this.size > HashCommon.maxFill(l, this.f114f)) {
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
        int i;
        int[] key = this.key;
        int mask = newN - 1;
        int[] newKey = new int[newN + 1];
        int i2 = this.f113n;
        int j = realSize();
        while (true) {
            int i3 = j;
            j--;
            if (i3 != 0) {
                do {
                    i2--;
                } while (key[i2] == 0);
                int mix = HashCommon.mix(key[i2]) & mask;
                int pos = mix;
                if (newKey[mix] != 0) {
                    do {
                        i = (pos + 1) & mask;
                        pos = i;
                    } while (newKey[i] != 0);
                }
                newKey[pos] = key[i2];
            } else {
                this.f113n = newN;
                this.mask = mask;
                this.maxFill = HashCommon.maxFill(this.f113n, this.f114f);
                this.key = newKey;
                return;
            }
        }
    }

    public IntOpenHashSet clone() {
        try {
            IntOpenHashSet c = (IntOpenHashSet) super.clone();
            c.key = (int[]) this.key.clone();
            c.containsNull = this.containsNull;
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

    /* JADX WARN: Type inference failed for: r0v1, types: [com.viaversion.viaversion.libs.fastutil.ints.IntIterator] */
    private void writeObject(ObjectOutputStream s) throws IOException {
        ?? iterator2 = iterator2();
        s.defaultWriteObject();
        int j = this.size;
        while (true) {
            int i = j;
            j--;
            if (i != 0) {
                s.writeInt(iterator2.nextInt());
            } else {
                return;
            }
        }
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        int pos;
        int i;
        s.defaultReadObject();
        this.f113n = HashCommon.arraySize(this.size, this.f114f);
        this.maxFill = HashCommon.maxFill(this.f113n, this.f114f);
        this.mask = this.f113n - 1;
        int[] key = new int[this.f113n + 1];
        this.key = key;
        int i2 = this.size;
        while (true) {
            int i3 = i2;
            i2--;
            if (i3 != 0) {
                int k = s.readInt();
                if (k == 0) {
                    pos = this.f113n;
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
            } else {
                return;
            }
        }
    }

    private void checkTable() {
    }
}
