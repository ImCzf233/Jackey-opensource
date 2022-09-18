package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.SafeMath;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntArraySet.class */
public class IntArraySet extends AbstractIntSet implements Serializable, Cloneable {
    private static final long serialVersionUID = 1;

    /* renamed from: a */
    private transient int[] f93a;
    private int size;

    static /* synthetic */ int access$010(IntArraySet x0) {
        int i = x0.size;
        x0.size = i - 1;
        return i;
    }

    public IntArraySet(int[] a) {
        this.f93a = a;
        this.size = a.length;
    }

    public IntArraySet() {
        this.f93a = IntArrays.EMPTY_ARRAY;
    }

    public IntArraySet(int capacity) {
        this.f93a = new int[capacity];
    }

    public IntArraySet(IntCollection c) {
        this(c.size());
        addAll(c);
    }

    public IntArraySet(Collection<? extends Integer> c) {
        this(c.size());
        addAll(c);
    }

    public IntArraySet(IntSet c) {
        this(c.size());
        int i = 0;
        Iterator<Integer> iterator2 = c.iterator2();
        while (iterator2.hasNext()) {
            int x = iterator2.next().intValue();
            this.f93a[i] = x;
            i++;
        }
        this.size = i;
    }

    public IntArraySet(Set<? extends Integer> c) {
        this(c.size());
        int i = 0;
        for (Integer x : c) {
            this.f93a[i] = x.intValue();
            i++;
        }
        this.size = i;
    }

    public IntArraySet(int[] a, int size) {
        this.f93a = a;
        this.size = size;
        if (size > a.length) {
            throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the array size (" + a.length + ")");
        }
    }

    /* renamed from: of */
    public static IntArraySet m216of() {
        return ofUnchecked();
    }

    /* renamed from: of */
    public static IntArraySet m215of(int e) {
        return ofUnchecked(e);
    }

    /* renamed from: of */
    public static IntArraySet m214of(int... a) {
        if (a.length == 2) {
            if (a[0] == a[1]) {
                throw new IllegalArgumentException("Duplicate element: " + a[1]);
            }
        } else if (a.length > 2) {
            IntOpenHashSet.m189of(a);
        }
        return ofUnchecked(a);
    }

    public static IntArraySet ofUnchecked() {
        return new IntArraySet();
    }

    public static IntArraySet ofUnchecked(int... a) {
        return new IntArraySet(a);
    }

    private int findKey(int o) {
        int i = this.size;
        do {
            int i2 = i;
            i--;
            if (i2 == 0) {
                return -1;
            }
        } while (this.f93a[i] != o);
        return i;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntSet, com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
    /* renamed from: iterator */
    public Iterator<Integer> iterator2() {
        return new IntIterator() { // from class: com.viaversion.viaversion.libs.fastutil.ints.IntArraySet.1
            int next = 0;

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.next < IntArraySet.this.size;
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
            public int nextInt() {
                if (hasNext()) {
                    int[] iArr = IntArraySet.this.f93a;
                    int i = this.next;
                    this.next = i + 1;
                    return iArr[i];
                }
                throw new NoSuchElementException();
            }

            @Override // java.util.Iterator
            public void remove() {
                int access$010 = IntArraySet.access$010(IntArraySet.this);
                int i = this.next;
                this.next = i - 1;
                int tail = access$010 - i;
                System.arraycopy(IntArraySet.this.f93a, this.next + 1, IntArraySet.this.f93a, this.next, tail);
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterator, com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator
            public int skip(int n) {
                if (n >= 0) {
                    int remaining = IntArraySet.this.size - this.next;
                    if (n < remaining) {
                        this.next += n;
                        return n;
                    }
                    this.next = IntArraySet.this.size;
                    return remaining;
                }
                throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            }
        };
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntArraySet$Spliterator.class */
    public final class Spliterator implements IntSpliterator {
        boolean hasSplit;
        int pos;
        int max;
        static final /* synthetic */ boolean $assertionsDisabled;

        static {
            $assertionsDisabled = !IntArraySet.class.desiredAssertionStatus();
        }

        public Spliterator(IntArraySet intArraySet) {
            this(0, intArraySet.size, false);
        }

        private Spliterator(int pos, int max, boolean hasSplit) {
            IntArraySet.this = r6;
            this.hasSplit = false;
            if ($assertionsDisabled || pos <= max) {
                this.pos = pos;
                this.max = max;
                this.hasSplit = hasSplit;
                return;
            }
            throw new AssertionError("pos " + pos + " must be <= max " + max);
        }

        private int getWorkingMax() {
            return this.hasSplit ? this.max : IntArraySet.this.size;
        }

        @Override // java.util.Spliterator
        public int characteristics() {
            return 16721;
        }

        @Override // java.util.Spliterator
        public long estimateSize() {
            return getWorkingMax() - this.pos;
        }

        @Override // java.util.Spliterator.OfInt
        public boolean tryAdvance(java.util.function.IntConsumer action) {
            if (this.pos < getWorkingMax()) {
                int[] iArr = IntArraySet.this.f93a;
                int i = this.pos;
                this.pos = i + 1;
                action.accept(iArr[i]);
                return true;
            }
            return false;
        }

        @Override // java.util.Spliterator.OfInt
        public void forEachRemaining(java.util.function.IntConsumer action) {
            int max = getWorkingMax();
            while (this.pos < max) {
                action.accept(IntArraySet.this.f93a[this.pos]);
                this.pos++;
            }
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator
        public long skip(long n) {
            if (n < 0) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            }
            int max = getWorkingMax();
            if (this.pos >= max) {
                return 0L;
            }
            int remaining = max - this.pos;
            if (n < remaining) {
                this.pos = SafeMath.safeLongToInt(this.pos + n);
                return n;
            }
            long n2 = remaining;
            this.pos = max;
            return n2;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator, java.util.Spliterator.OfInt, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public IntSpliterator trySplit() {
            int max = getWorkingMax();
            int retLen = (max - this.pos) >> 1;
            if (retLen <= 1) {
                return null;
            }
            this.max = max;
            int myNewPos = this.pos + retLen;
            int oldPos = this.pos;
            this.pos = myNewPos;
            this.hasSplit = true;
            return new Spliterator(oldPos, myNewPos, true);
        }
    }

    @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
    /* renamed from: spliterator */
    public java.util.Spliterator<Integer> spliterator2() {
        return new Spliterator(this);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
    public boolean contains(int k) {
        return findKey(k) != -1;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        return this.size;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntSet, com.viaversion.viaversion.libs.fastutil.ints.IntSet
    public boolean remove(int k) {
        int pos = findKey(k);
        if (pos == -1) {
            return false;
        }
        int tail = (this.size - pos) - 1;
        for (int i = 0; i < tail; i++) {
            this.f93a[pos + i] = this.f93a[pos + i + 1];
        }
        this.size--;
        return true;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
    public boolean add(int k) {
        int pos = findKey(k);
        if (pos != -1) {
            return false;
        }
        if (this.size == this.f93a.length) {
            int[] b = new int[this.size == 0 ? 2 : this.size * 2];
            int i = this.size;
            while (true) {
                int i2 = i;
                i--;
                if (i2 == 0) {
                    break;
                }
                b[i] = this.f93a[i];
            }
            this.f93a = b;
        }
        int[] iArr = this.f93a;
        int i3 = this.size;
        this.size = i3 + 1;
        iArr[i3] = k;
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public void clear() {
        this.size = 0;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
    public int[] toIntArray() {
        return Arrays.copyOf(this.f93a, this.size);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
    public int[] toArray(int[] a) {
        if (a == null || a.length < this.size) {
            a = new int[this.size];
        }
        System.arraycopy(this.f93a, 0, a, 0, this.size);
        return a;
    }

    public IntArraySet clone() {
        try {
            IntArraySet c = (IntArraySet) super.clone();
            c.f93a = (int[]) this.f93a.clone();
            return c;
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        for (int i = 0; i < this.size; i++) {
            s.writeInt(this.f93a[i]);
        }
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.f93a = new int[this.size];
        for (int i = 0; i < this.size; i++) {
            this.f93a[i] = s.readInt();
        }
    }
}
