package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Hash;
import com.viaversion.viaversion.libs.fastutil.HashCommon;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollections;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.stream.Collector;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectOpenHashSet.class */
public class ObjectOpenHashSet<K> extends AbstractObjectSet<K> implements Serializable, Cloneable, Hash {
    private static final long serialVersionUID = 0;
    private static final boolean ASSERTS = false;
    protected transient K[] key;
    protected transient int mask;
    protected transient boolean containsNull;

    /* renamed from: n */
    protected transient int f163n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;

    /* renamed from: f */
    protected final float f164f;
    private static final Collector<Object, ?, ObjectOpenHashSet<Object>> TO_SET_COLLECTOR = Collector.of(ObjectOpenHashSet::new, (v0, v1) -> {
        v0.add(v1);
    }, (v0, v1) -> {
        return v0.combine(v1);
    }, Collector.Characteristics.UNORDERED);

    public ObjectOpenHashSet(int expected, float f) {
        if (f <= 0.0f || f >= 1.0f) {
            throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1");
        }
        if (expected < 0) {
            throw new IllegalArgumentException("The expected number of elements must be nonnegative");
        }
        this.f164f = f;
        int arraySize = HashCommon.arraySize(expected, f);
        this.f163n = arraySize;
        this.minN = arraySize;
        this.mask = this.f163n - 1;
        this.maxFill = HashCommon.maxFill(this.f163n, f);
        this.key = (K[]) new Object[this.f163n + 1];
    }

    public ObjectOpenHashSet(int expected) {
        this(expected, 0.75f);
    }

    public ObjectOpenHashSet() {
        this(16, 0.75f);
    }

    public ObjectOpenHashSet(Collection<? extends K> c, float f) {
        this(c.size(), f);
        addAll(c);
    }

    public ObjectOpenHashSet(Collection<? extends K> c) {
        this(c, 0.75f);
    }

    public ObjectOpenHashSet(ObjectCollection<? extends K> c, float f) {
        this(c.size(), f);
        addAll(c);
    }

    public ObjectOpenHashSet(ObjectCollection<? extends K> c) {
        this((ObjectCollection) c, 0.75f);
    }

    public ObjectOpenHashSet(Iterator<? extends K> i, float f) {
        this(16, f);
        while (i.hasNext()) {
            add(i.next());
        }
    }

    public ObjectOpenHashSet(Iterator<? extends K> i) {
        this(i, 0.75f);
    }

    public ObjectOpenHashSet(K[] a, int offset, int length, float f) {
        this(length < 0 ? 0 : length, f);
        ObjectArrays.ensureOffsetLength(a, offset, length);
        for (int i = 0; i < length; i++) {
            add(a[offset + i]);
        }
    }

    public ObjectOpenHashSet(K[] a, int offset, int length) {
        this(a, offset, length, 0.75f);
    }

    public ObjectOpenHashSet(K[] a, float f) {
        this(a, 0, a.length, f);
    }

    public ObjectOpenHashSet(K[] a) {
        this(a, 0.75f);
    }

    /* renamed from: of */
    public static <K> ObjectOpenHashSet<K> m158of() {
        return new ObjectOpenHashSet<>();
    }

    /* renamed from: of */
    public static <K> ObjectOpenHashSet<K> m157of(K e) {
        ObjectOpenHashSet<K> result = new ObjectOpenHashSet<>(1, 0.75f);
        result.add(e);
        return result;
    }

    /* renamed from: of */
    public static <K> ObjectOpenHashSet<K> m156of(K e0, K e1) {
        ObjectOpenHashSet<K> result = new ObjectOpenHashSet<>(2, 0.75f);
        result.add(e0);
        if (!result.add(e1)) {
            throw new IllegalArgumentException("Duplicate element: " + e1);
        }
        return result;
    }

    /* renamed from: of */
    public static <K> ObjectOpenHashSet<K> m155of(K e0, K e1, K e2) {
        ObjectOpenHashSet<K> result = new ObjectOpenHashSet<>(3, 0.75f);
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
    public static <K> ObjectOpenHashSet<K> m154of(K... a) {
        ObjectOpenHashSet<K> result = new ObjectOpenHashSet<>(a.length, 0.75f);
        for (K element : a) {
            if (!result.add(element)) {
                throw new IllegalArgumentException("Duplicate element " + element);
            }
        }
        return result;
    }

    private ObjectOpenHashSet<K> combine(ObjectOpenHashSet<? extends K> toAddFrom) {
        addAll(toAddFrom);
        return this;
    }

    public static <K> Collector<K, ?, ObjectOpenHashSet<K>> toSet() {
        return (Collector<K, ?, ObjectOpenHashSet<K>>) TO_SET_COLLECTOR;
    }

    public static <K> Collector<K, ?, ObjectOpenHashSet<K>> toSetWithExpectedSize(int expectedSize) {
        return expectedSize <= 16 ? toSet() : Collector.of(new ObjectCollections.SizeDecreasingSupplier(expectedSize, size -> {
            if (size <= 16) {
                return new ObjectOpenHashSet();
            }
            return new ObjectOpenHashSet(size);
        }), (v0, v1) -> {
            v0.add(v1);
        }, (v0, v1) -> {
            return v0.combine(v1);
        }, Collector.Characteristics.UNORDERED);
    }

    public int realSize() {
        return this.containsNull ? this.size - 1 : this.size;
    }

    private void ensureCapacity(int capacity) {
        int needed = HashCommon.arraySize(capacity, this.f164f);
        if (needed > this.f163n) {
            rehash(needed);
        }
    }

    private void tryCapacity(long capacity) {
        int needed = (int) Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long) Math.ceil(((float) capacity) / this.f164f))));
        if (needed > this.f163n) {
            rehash(needed);
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean addAll(Collection<? extends K> c) {
        if (this.f164f <= 0.5d) {
            ensureCapacity(c.size());
        } else {
            tryCapacity(size() + c.size());
        }
        return super.addAll(c);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean add(K k) {
        K curr;
        if (k == null) {
            if (this.containsNull) {
                return false;
            }
            this.containsNull = true;
        } else {
            K[] key = this.key;
            int mix = HashCommon.mix(k.hashCode()) & this.mask;
            int pos = mix;
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
        int i2 = this.size;
        this.size = i2 + 1;
        if (i2 >= this.maxFill) {
            rehash(HashCommon.arraySize(this.size + 1, this.f164f));
            return true;
        }
        return true;
    }

    public K addOrGet(K k) {
        K curr;
        if (k == null) {
            if (this.containsNull) {
                return this.key[this.f163n];
            }
            this.containsNull = true;
        } else {
            K[] key = this.key;
            int mix = HashCommon.mix(k.hashCode()) & this.mask;
            int pos = mix;
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
        int i2 = this.size;
        this.size = i2 + 1;
        if (i2 >= this.maxFill) {
            rehash(HashCommon.arraySize(this.size + 1, this.f164f));
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
        }
    }

    private boolean removeEntry(int pos) {
        this.size--;
        shiftKeys(pos);
        if (this.f163n > this.minN && this.size < this.maxFill / 4 && this.f163n > 16) {
            rehash(this.f163n / 2);
            return true;
        }
        return true;
    }

    private boolean removeNullEntry() {
        this.containsNull = false;
        this.key[this.f163n] = null;
        this.size--;
        if (this.f163n > this.minN && this.size < this.maxFill / 4 && this.f163n > 16) {
            rehash(this.f163n / 2);
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
            return this.key[this.f163n];
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

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public void clear() {
        if (this.size == 0) {
            return;
        }
        this.size = 0;
        this.containsNull = false;
        Arrays.fill(this.key, (Object) null);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        return this.size;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean isEmpty() {
        return this.size == 0;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectOpenHashSet$SetIterator.class */
    public final class SetIterator implements ObjectIterator<K> {
        int pos;
        int last;

        /* renamed from: c */
        int f165c;
        boolean mustReturnNull;
        ObjectArrayList<K> wrapped;

        private SetIterator() {
            ObjectOpenHashSet.this = r4;
            this.pos = ObjectOpenHashSet.this.f163n;
            this.last = -1;
            this.f165c = ObjectOpenHashSet.this.size;
            this.mustReturnNull = ObjectOpenHashSet.this.containsNull;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.f165c != 0;
        }

        @Override // java.util.Iterator
        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            this.f165c--;
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                this.last = ObjectOpenHashSet.this.f163n;
                return ObjectOpenHashSet.this.key[ObjectOpenHashSet.this.f163n];
            }
            K[] key = ObjectOpenHashSet.this.key;
            do {
                int i = this.pos - 1;
                this.pos = i;
                if (i < 0) {
                    this.last = Integer.MIN_VALUE;
                    return this.wrapped.get((-this.pos) - 1);
                }
            } while (key[this.pos] == null);
            int i2 = this.pos;
            this.last = i2;
            return key[i2];
        }

        private final void shiftKeys(int pos) {
            K curr;
            K[] key = ObjectOpenHashSet.this.key;
            while (true) {
                int last = pos;
                int i = last + 1;
                int i2 = ObjectOpenHashSet.this.mask;
                while (true) {
                    pos = i & i2;
                    curr = key[pos];
                    if (curr == null) {
                        key[last] = null;
                        return;
                    }
                    int slot = HashCommon.mix(curr.hashCode()) & ObjectOpenHashSet.this.mask;
                    if (last > pos) {
                        if (last >= slot && slot > pos) {
                            break;
                        }
                        i = pos + 1;
                        i2 = ObjectOpenHashSet.this.mask;
                    } else if (last >= slot || slot > pos) {
                        break;
                    } else {
                        i = pos + 1;
                        i2 = ObjectOpenHashSet.this.mask;
                    }
                }
                if (pos < last) {
                    if (this.wrapped == null) {
                        this.wrapped = new ObjectArrayList<>(2);
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
            if (this.last == ObjectOpenHashSet.this.f163n) {
                ObjectOpenHashSet.this.containsNull = false;
                ObjectOpenHashSet.this.key[ObjectOpenHashSet.this.f163n] = null;
            } else if (this.pos >= 0) {
                shiftKeys(this.last);
            } else {
                ObjectOpenHashSet.this.remove(this.wrapped.set((-this.pos) - 1, null));
                this.last = -1;
                return;
            }
            ObjectOpenHashSet.this.size--;
            this.last = -1;
        }

        @Override // java.util.Iterator
        public void forEachRemaining(Consumer<? super K> action) {
            K[] key = ObjectOpenHashSet.this.key;
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                this.last = ObjectOpenHashSet.this.f163n;
                action.accept((Object) key[ObjectOpenHashSet.this.f163n]);
                this.f165c--;
            }
            while (this.f165c != 0) {
                int i = this.pos - 1;
                this.pos = i;
                if (i < 0) {
                    this.last = Integer.MIN_VALUE;
                    action.accept((K) this.wrapped.get((-this.pos) - 1));
                    this.f165c--;
                } else if (key[this.pos] != null) {
                    int i2 = this.pos;
                    this.last = i2;
                    action.accept((Object) key[i2]);
                    this.f165c--;
                }
            }
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet, com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable
    public ObjectIterator<K> iterator() {
        return new SetIterator();
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectOpenHashSet$SetSpliterator.class */
    public final class SetSpliterator implements ObjectSpliterator<K> {
        private static final int POST_SPLIT_CHARACTERISTICS = 1;
        int pos;
        int max;

        /* renamed from: c */
        int f166c;
        boolean mustReturnNull;
        boolean hasSplit;

        SetSpliterator() {
            ObjectOpenHashSet.this = r4;
            this.pos = 0;
            this.max = ObjectOpenHashSet.this.f163n;
            this.f166c = 0;
            this.mustReturnNull = ObjectOpenHashSet.this.containsNull;
            this.hasSplit = false;
        }

        SetSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
            ObjectOpenHashSet.this = r4;
            this.pos = 0;
            this.max = ObjectOpenHashSet.this.f163n;
            this.f166c = 0;
            this.mustReturnNull = ObjectOpenHashSet.this.containsNull;
            this.hasSplit = false;
            this.pos = pos;
            this.max = max;
            this.mustReturnNull = mustReturnNull;
            this.hasSplit = hasSplit;
        }

        @Override // java.util.Spliterator
        public boolean tryAdvance(Consumer<? super K> action) {
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                this.f166c++;
                action.accept((Object) ObjectOpenHashSet.this.key[ObjectOpenHashSet.this.f163n]);
                return true;
            }
            K[] key = ObjectOpenHashSet.this.key;
            while (this.pos < this.max) {
                if (key[this.pos] != null) {
                    this.f166c++;
                    int i = this.pos;
                    this.pos = i + 1;
                    action.accept((Object) key[i]);
                    return true;
                }
                this.pos++;
            }
            return false;
        }

        @Override // java.util.Spliterator
        public void forEachRemaining(Consumer<? super K> action) {
            K[] key = ObjectOpenHashSet.this.key;
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                action.accept((Object) key[ObjectOpenHashSet.this.f163n]);
                this.f166c++;
            }
            while (this.pos < this.max) {
                if (key[this.pos] != null) {
                    action.accept((Object) key[this.pos]);
                    this.f166c++;
                }
                this.pos++;
            }
        }

        @Override // java.util.Spliterator
        public int characteristics() {
            return this.hasSplit ? 1 : 65;
        }

        @Override // java.util.Spliterator
        public long estimateSize() {
            if (!this.hasSplit) {
                return ObjectOpenHashSet.this.size - this.f166c;
            }
            return Math.min(ObjectOpenHashSet.this.size - this.f166c, ((long) ((ObjectOpenHashSet.this.realSize() / ObjectOpenHashSet.this.f163n) * (this.max - this.pos))) + (this.mustReturnNull ? 1 : 0));
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator, java.util.Spliterator
        public ObjectOpenHashSet<K>.SetSpliterator trySplit() {
            int retLen;
            if (this.pos >= this.max - 1 || (retLen = (this.max - this.pos) >> 1) <= 1) {
                return null;
            }
            int myNewPos = this.pos + retLen;
            int retPos = this.pos;
            ObjectOpenHashSet<K>.SetSpliterator split = new SetSpliterator(retPos, myNewPos, this.mustReturnNull, true);
            this.pos = myNewPos;
            this.mustReturnNull = false;
            this.hasSplit = true;
            return split;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator
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
            K[] key = ObjectOpenHashSet.this.key;
            while (this.pos < this.max && n > 0) {
                int i = this.pos;
                this.pos = i + 1;
                if (key[i] != null) {
                    skipped++;
                    n--;
                }
            }
            return skipped;
        }
    }

    @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectSet, java.util.Set
    public ObjectSpliterator<K> spliterator() {
        return new SetSpliterator();
    }

    @Override // java.lang.Iterable
    public void forEach(Consumer<? super K> action) {
        if (this.containsNull) {
            action.accept((Object) this.key[this.f163n]);
        }
        K[] key = this.key;
        int pos = this.f163n;
        while (true) {
            int i = pos;
            pos--;
            if (i != 0) {
                if (key[pos] != null) {
                    action.accept((Object) key[pos]);
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
        int l = HashCommon.nextPowerOfTwo((int) Math.ceil(n / this.f164f));
        if (l >= this.f163n || this.size > HashCommon.maxFill(l, this.f164f)) {
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
        K[] key = this.key;
        int mask = newN - 1;
        K[] newKey = (K[]) new Object[newN + 1];
        int i2 = this.f163n;
        int j = realSize();
        while (true) {
            int i3 = j;
            j--;
            if (i3 != 0) {
                do {
                    i2--;
                } while (key[i2] == null);
                int mix = HashCommon.mix(key[i2].hashCode()) & mask;
                int pos = mix;
                if (newKey[mix] != null) {
                    do {
                        i = (pos + 1) & mask;
                        pos = i;
                    } while (newKey[i] != null);
                }
                newKey[pos] = key[i2];
            } else {
                this.f163n = newN;
                this.mask = mask;
                this.maxFill = HashCommon.maxFill(this.f163n, this.f164f);
                this.key = newKey;
                return;
            }
        }
    }

    public ObjectOpenHashSet<K> clone() {
        try {
            ObjectOpenHashSet<K> c = (ObjectOpenHashSet) super.clone();
            c.key = (K[]) ((Object[]) this.key.clone());
            c.containsNull = this.containsNull;
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
        this.f163n = HashCommon.arraySize(this.size, this.f164f);
        this.maxFill = HashCommon.maxFill(this.f163n, this.f164f);
        this.mask = this.f163n - 1;
        K[] key = (K[]) new Object[this.f163n + 1];
        this.key = key;
        int i2 = this.size;
        while (true) {
            int i3 = i2;
            i2--;
            if (i3 != 0) {
                Object readObject = s.readObject();
                if (readObject == null) {
                    pos = this.f163n;
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
            } else {
                return;
            }
        }
    }

    private void checkTable() {
    }
}
