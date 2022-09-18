package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.SafeMath;
import com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList;
import com.viaversion.viaversion.libs.fastutil.ints.IntCollections;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterators;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import java.util.stream.IntStream;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntArrayList.class */
public class IntArrayList extends AbstractIntList implements RandomAccess, Cloneable, Serializable {
    private static final long serialVersionUID = -7046029254386353130L;
    public static final int DEFAULT_INITIAL_CAPACITY = 10;

    /* renamed from: a */
    protected transient int[] f92a;
    protected int size;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !IntArrayList.class.desiredAssertionStatus();
    }

    private static final int[] copyArraySafe(int[] a, int length) {
        if (length == 0) {
            return IntArrays.EMPTY_ARRAY;
        }
        return Arrays.copyOf(a, length);
    }

    private static final int[] copyArrayFromSafe(IntArrayList l) {
        return copyArraySafe(l.f92a, l.size);
    }

    protected IntArrayList(int[] a, boolean wrapped) {
        this.f92a = a;
    }

    private void initArrayFromCapacity(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Initial capacity (" + capacity + ") is negative");
        }
        if (capacity == 0) {
            this.f92a = IntArrays.EMPTY_ARRAY;
        } else {
            this.f92a = new int[capacity];
        }
    }

    public IntArrayList(int capacity) {
        initArrayFromCapacity(capacity);
    }

    public IntArrayList() {
        this.f92a = IntArrays.DEFAULT_EMPTY_ARRAY;
    }

    public IntArrayList(Collection<? extends Integer> c) {
        if (c instanceof IntArrayList) {
            this.f92a = copyArrayFromSafe((IntArrayList) c);
            this.size = this.f92a.length;
            return;
        }
        initArrayFromCapacity(c.size());
        if (c instanceof IntList) {
            int[] iArr = this.f92a;
            int size = c.size();
            this.size = size;
            ((IntList) c).getElements(0, iArr, 0, size);
            return;
        }
        this.size = IntIterators.unwrap(IntIterators.asIntIterator(c.iterator()), this.f92a);
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.viaversion.viaversion.libs.fastutil.ints.IntIterator] */
    public IntArrayList(IntCollection c) {
        if (c instanceof IntArrayList) {
            this.f92a = copyArrayFromSafe((IntArrayList) c);
            this.size = this.f92a.length;
            return;
        }
        initArrayFromCapacity(c.size());
        if (c instanceof IntList) {
            int[] iArr = this.f92a;
            int size = c.size();
            this.size = size;
            ((IntList) c).getElements(0, iArr, 0, size);
            return;
        }
        this.size = IntIterators.unwrap((IntIterator) c.iterator2(), this.f92a);
    }

    public IntArrayList(IntList l) {
        if (l instanceof IntArrayList) {
            this.f92a = copyArrayFromSafe((IntArrayList) l);
            this.size = this.f92a.length;
            return;
        }
        initArrayFromCapacity(l.size());
        int[] iArr = this.f92a;
        int size = l.size();
        this.size = size;
        l.getElements(0, iArr, 0, size);
    }

    public IntArrayList(int[] a) {
        this(a, 0, a.length);
    }

    public IntArrayList(int[] a, int offset, int length) {
        this(length);
        System.arraycopy(a, offset, this.f92a, 0, length);
        this.size = length;
    }

    public IntArrayList(Iterator<? extends Integer> i) {
        this();
        while (i.hasNext()) {
            add(i.next().intValue());
        }
    }

    public IntArrayList(IntIterator i) {
        this();
        while (i.hasNext()) {
            add(i.nextInt());
        }
    }

    public int[] elements() {
        return this.f92a;
    }

    public static IntArrayList wrap(int[] a, int length) {
        if (length > a.length) {
            throw new IllegalArgumentException("The specified length (" + length + ") is greater than the array size (" + a.length + ")");
        }
        IntArrayList l = new IntArrayList(a, true);
        l.size = length;
        return l;
    }

    public static IntArrayList wrap(int[] a) {
        return wrap(a, a.length);
    }

    /* renamed from: of */
    public static IntArrayList m218of() {
        return new IntArrayList();
    }

    /* renamed from: of */
    public static IntArrayList m217of(int... init) {
        return wrap(init);
    }

    public static IntArrayList toList(IntStream stream) {
        return (IntArrayList) stream.collect(IntArrayList::new, (v0, v1) -> {
            v0.add(v1);
        }, (v0, v1) -> {
            v0.addAll(v1);
        });
    }

    public static IntArrayList toListWithExpectedSize(IntStream stream, int expectedSize) {
        if (expectedSize <= 10) {
            return toList(stream);
        }
        return (IntArrayList) stream.collect(new IntCollections.SizeDecreasingSupplier(expectedSize, size -> {
            return size <= 10 ? new IntArrayList() : new IntArrayList(size);
        }), (v0, v1) -> {
            v0.add(v1);
        }, (v0, v1) -> {
            v0.addAll(v1);
        });
    }

    public void ensureCapacity(int capacity) {
        if (capacity > this.f92a.length) {
            if (this.f92a == IntArrays.DEFAULT_EMPTY_ARRAY && capacity <= 10) {
                return;
            }
            this.f92a = IntArrays.ensureCapacity(this.f92a, capacity, this.size);
            if (!$assertionsDisabled && this.size > this.f92a.length) {
                throw new AssertionError();
            }
        }
    }

    private void grow(int capacity) {
        if (capacity <= this.f92a.length) {
            return;
        }
        if (this.f92a != IntArrays.DEFAULT_EMPTY_ARRAY) {
            capacity = (int) Math.max(Math.min(this.f92a.length + (this.f92a.length >> 1), 2147483639L), capacity);
        } else if (capacity < 10) {
            capacity = 10;
        }
        this.f92a = IntArrays.forceCapacity(this.f92a, capacity, this.size);
        if (!$assertionsDisabled && this.size > this.f92a.length) {
            throw new AssertionError();
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList
    public void add(int index, int k) {
        ensureIndex(index);
        grow(this.size + 1);
        if (index != this.size) {
            System.arraycopy(this.f92a, index, this.f92a, index + 1, this.size - index);
        }
        this.f92a[index] = k;
        this.size++;
        if ($assertionsDisabled || this.size <= this.f92a.length) {
            return;
        }
        throw new AssertionError();
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
    public boolean add(int k) {
        grow(this.size + 1);
        int[] iArr = this.f92a;
        int i = this.size;
        this.size = i + 1;
        iArr[i] = k;
        if ($assertionsDisabled || this.size <= this.f92a.length) {
            return true;
        }
        throw new AssertionError();
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
    public int getInt(int index) {
        if (index >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
        }
        return this.f92a[index];
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList
    public int indexOf(int k) {
        for (int i = 0; i < this.size; i++) {
            if (k == this.f92a[i]) {
                return i;
            }
        }
        return -1;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList
    public int lastIndexOf(int k) {
        int i = this.size;
        do {
            int i2 = i;
            i--;
            if (i2 == 0) {
                return -1;
            }
        } while (k != this.f92a[i]);
        return i;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList
    public int removeInt(int index) {
        if (index >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
        }
        int old = this.f92a[index];
        this.size--;
        if (index != this.size) {
            System.arraycopy(this.f92a, index + 1, this.f92a, index, this.size - index);
        }
        if (!$assertionsDisabled && this.size > this.f92a.length) {
            throw new AssertionError();
        }
        return old;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
    public boolean rem(int k) {
        int index = indexOf(k);
        if (index == -1) {
            return false;
        }
        removeInt(index);
        if (!$assertionsDisabled && this.size > this.f92a.length) {
            throw new AssertionError();
        }
        return true;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList
    public int set(int index, int k) {
        if (index >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
        }
        int old = this.f92a[index];
        this.f92a[index] = k;
        return old;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public void clear() {
        this.size = 0;
        if ($assertionsDisabled || this.size <= this.f92a.length) {
            return;
        }
        throw new AssertionError();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return this.size;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList
    public void size(int size) {
        if (size > this.f92a.length) {
            this.f92a = IntArrays.forceCapacity(this.f92a, size, this.size);
        }
        if (size > this.size) {
            Arrays.fill(this.f92a, this.size, size, 0);
        }
        this.size = size;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List, com.viaversion.viaversion.libs.fastutil.Stack
    public boolean isEmpty() {
        return this.size == 0;
    }

    public void trim() {
        trim(0);
    }

    public void trim(int n) {
        if (n >= this.f92a.length || this.size == this.f92a.length) {
            return;
        }
        int[] t = new int[Math.max(n, this.size)];
        System.arraycopy(this.f92a, 0, t, 0, this.size);
        this.f92a = t;
        if (!$assertionsDisabled && this.size > this.f92a.length) {
            throw new AssertionError();
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntArrayList$SubList.class */
    public class SubList extends AbstractIntList.IntRandomAccessSubList {
        private static final long serialVersionUID = -3185226345314976296L;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        protected SubList(int from, int to) {
            super(r6, from, to);
            IntArrayList.this = r6;
        }

        private int[] getParentArray() {
            return IntArrayList.this.f92a;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList.IntSubList, com.viaversion.viaversion.libs.fastutil.ints.IntList
        public int getInt(int i) {
            ensureRestrictedIndex(i);
            return IntArrayList.this.f92a[i + this.from];
        }

        /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntArrayList$SubList$SubListIterator.class */
        public final class SubListIterator extends IntIterators.AbstractIndexBasedListIterator {
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            SubListIterator(int index) {
                super(0, index);
                SubList.this = r5;
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterators.AbstractIndexBasedIterator
            protected final int get(int i) {
                return IntArrayList.this.f92a[SubList.this.from + i];
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterators.AbstractIndexBasedListIterator
            protected final void add(int i, int k) {
                SubList.this.add(i, k);
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterators.AbstractIndexBasedListIterator
            protected final void set(int i, int k) {
                SubList.this.set(i, k);
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterators.AbstractIndexBasedIterator
            protected final void remove(int i) {
                SubList.this.removeInt(i);
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterators.AbstractIndexBasedIterator
            protected final int getMaxPos() {
                return SubList.this.f75to - SubList.this.from;
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterators.AbstractIndexBasedIterator, com.viaversion.viaversion.libs.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
            public int nextInt() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                int[] iArr = IntArrayList.this.f92a;
                int i = SubList.this.from;
                int i2 = this.pos;
                this.pos = i2 + 1;
                this.lastReturned = i2;
                return iArr[i + i2];
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterators.AbstractIndexBasedListIterator, com.viaversion.viaversion.libs.fastutil.ints.IntBidirectionalIterator
            public int previousInt() {
                if (!hasPrevious()) {
                    throw new NoSuchElementException();
                }
                int[] iArr = IntArrayList.this.f92a;
                int i = SubList.this.from;
                int i2 = this.pos - 1;
                this.pos = i2;
                this.lastReturned = i2;
                return iArr[i + i2];
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterators.AbstractIndexBasedIterator, java.util.PrimitiveIterator.OfInt
            public void forEachRemaining(java.util.function.IntConsumer action) {
                int max = SubList.this.f75to - SubList.this.from;
                while (this.pos < max) {
                    int[] iArr = IntArrayList.this.f92a;
                    int i = SubList.this.from;
                    int i2 = this.pos;
                    this.pos = i2 + 1;
                    this.lastReturned = i2;
                    action.accept(iArr[i + i2]);
                }
            }
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList.IntSubList, com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList, java.util.List
        /* renamed from: listIterator */
        public ListIterator<Integer> listIterator2(int index) {
            return new SubListIterator(index);
        }

        /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntArrayList$SubList$SubListSpliterator.class */
        public final class SubListSpliterator extends IntSpliterators.LateBindingSizeIndexBasedSpliterator {
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            SubListSpliterator() {
                super(r4.from);
                SubList.this = r4;
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            private SubListSpliterator(int pos, int maxPos) {
                super(pos, maxPos);
                SubList.this = r5;
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators.LateBindingSizeIndexBasedSpliterator
            protected final int getMaxPosFromBackingStore() {
                return SubList.this.f75to;
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators.AbstractIndexBasedSpliterator
            protected final int get(int i) {
                return IntArrayList.this.f92a[i];
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators.AbstractIndexBasedSpliterator
            public final SubListSpliterator makeForSplit(int pos, int maxPos) {
                return new SubListSpliterator(pos, maxPos);
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators.AbstractIndexBasedSpliterator, java.util.Spliterator.OfInt
            public boolean tryAdvance(java.util.function.IntConsumer action) {
                if (this.pos >= getMaxPos()) {
                    return false;
                }
                int[] iArr = IntArrayList.this.f92a;
                int i = this.pos;
                this.pos = i + 1;
                action.accept(iArr[i]);
                return true;
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators.AbstractIndexBasedSpliterator, java.util.Spliterator.OfInt
            public void forEachRemaining(java.util.function.IntConsumer action) {
                int max = getMaxPos();
                while (this.pos < max) {
                    int[] iArr = IntArrayList.this.f92a;
                    int i = this.pos;
                    this.pos = i + 1;
                    action.accept(iArr[i]);
                }
            }
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList.IntSubList, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
        /* renamed from: spliterator */
        public java.util.Spliterator<Integer> spliterator2() {
            return new SubListSpliterator();
        }

        boolean contentsEquals(int[] otherA, int otherAFrom, int otherATo) {
            if (IntArrayList.this.f92a == otherA && this.from == otherAFrom && this.f75to == otherATo) {
                return true;
            }
            if (otherATo - otherAFrom != size()) {
                return false;
            }
            int pos = this.from;
            int otherPos = otherAFrom;
            while (pos < this.f75to) {
                int i = pos;
                pos++;
                int i2 = otherPos;
                otherPos++;
                if (IntArrayList.this.f92a[i] != otherA[i2]) {
                    return false;
                }
            }
            return true;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, java.util.Collection, java.util.List
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (o == null || !(o instanceof List)) {
                return false;
            }
            if (o instanceof IntArrayList) {
                IntArrayList other = (IntArrayList) o;
                return contentsEquals(other.f92a, 0, other.size());
            } else if (o instanceof SubList) {
                SubList other2 = (SubList) o;
                return contentsEquals(other2.getParentArray(), other2.from, other2.f75to);
            } else {
                return super.equals(o);
            }
        }

        int contentsCompareTo(int[] otherA, int otherAFrom, int otherATo) {
            if (IntArrayList.this.f92a == otherA && this.from == otherAFrom && this.f75to == otherATo) {
                return 0;
            }
            int i = this.from;
            int j = otherAFrom;
            while (i < this.f75to && i < otherATo) {
                int e1 = IntArrayList.this.f92a[i];
                int e2 = otherA[j];
                int r = Integer.compare(e1, e2);
                if (r == 0) {
                    i++;
                    j++;
                } else {
                    return r;
                }
            }
            if (i < otherATo) {
                return -1;
            }
            return i < this.f75to ? 1 : 0;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList
        public int compareTo(List<? extends Integer> l) {
            if (l instanceof IntArrayList) {
                IntArrayList other = (IntArrayList) l;
                return contentsCompareTo(other.f92a, 0, other.size());
            } else if (l instanceof SubList) {
                SubList other2 = (SubList) l;
                return contentsCompareTo(other2.getParentArray(), other2.from, other2.f75to);
            } else {
                return super.compareTo(l);
            }
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList, java.util.List
    /* renamed from: subList */
    public List<Integer> subList2(int from, int to) {
        if (from == 0 && to == size()) {
            return this;
        }
        ensureIndex(from);
        ensureIndex(to);
        if (from > to) {
            throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
        }
        return new SubList(from, to);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList
    public void getElements(int from, int[] a, int offset, int length) {
        IntArrays.ensureOffsetLength(a, offset, length);
        System.arraycopy(this.f92a, from, a, offset, length);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList
    public void removeElements(int from, int to) {
        com.viaversion.viaversion.libs.fastutil.Arrays.ensureFromTo(this.size, from, to);
        System.arraycopy(this.f92a, to, this.f92a, from, this.size - to);
        this.size -= to - from;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList
    public void addElements(int index, int[] a, int offset, int length) {
        ensureIndex(index);
        IntArrays.ensureOffsetLength(a, offset, length);
        grow(this.size + length);
        System.arraycopy(this.f92a, index, this.f92a, index + length, this.size - index);
        System.arraycopy(a, offset, this.f92a, index, length);
        this.size += length;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList
    public void setElements(int index, int[] a, int offset, int length) {
        ensureIndex(index);
        IntArrays.ensureOffsetLength(a, offset, length);
        if (index + length > this.size) {
            throw new IndexOutOfBoundsException("End index (" + (index + length) + ") is greater than list size (" + this.size + ")");
        }
        System.arraycopy(a, offset, this.f92a, index, length);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntIterable
    public void forEach(java.util.function.IntConsumer action) {
        for (int i = 0; i < this.size; i++) {
            action.accept(this.f92a[i]);
        }
    }

    /* JADX WARN: Type inference failed for: r0v10, types: [com.viaversion.viaversion.libs.fastutil.ints.IntIterator] */
    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList
    public boolean addAll(int index, IntCollection c) {
        if (c instanceof IntList) {
            return addAll(index, (IntList) c);
        }
        ensureIndex(index);
        int n = c.size();
        if (n == 0) {
            return false;
        }
        grow(this.size + n);
        System.arraycopy(this.f92a, index, this.f92a, index + n, this.size - index);
        ?? iterator2 = c.iterator2();
        this.size += n;
        while (true) {
            int i = n;
            n--;
            if (i == 0) {
                break;
            }
            int i2 = index;
            index++;
            this.f92a[i2] = iterator2.nextInt();
        }
        if (!$assertionsDisabled && this.size > this.f92a.length) {
            throw new AssertionError();
        }
        return true;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
    public boolean addAll(int index, IntList l) {
        ensureIndex(index);
        int n = l.size();
        if (n == 0) {
            return false;
        }
        grow(this.size + n);
        System.arraycopy(this.f92a, index, this.f92a, index + n, this.size - index);
        l.getElements(0, this.f92a, index, n);
        this.size += n;
        if (!$assertionsDisabled && this.size > this.f92a.length) {
            throw new AssertionError();
        }
        return true;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
    public boolean removeAll(IntCollection c) {
        int[] a = this.f92a;
        int j = 0;
        for (int i = 0; i < this.size; i++) {
            if (!c.contains(a[i])) {
                int i2 = j;
                j++;
                a[i2] = a[i];
            }
        }
        boolean modified = this.size != j;
        this.size = j;
        return modified;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
    public int[] toArray(int[] a) {
        if (a == null || a.length < this.size) {
            a = Arrays.copyOf(a, this.size);
        }
        System.arraycopy(this.f92a, 0, a, 0, this.size);
        return a;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList, java.util.List
    /* renamed from: listIterator */
    public ListIterator<Integer> listIterator2(final int index) {
        ensureIndex(index);
        return new IntListIterator() { // from class: com.viaversion.viaversion.libs.fastutil.ints.IntArrayList.1
            int pos;
            int last = -1;

            {
                IntArrayList.this = this;
                this.pos = index;
            }

            @Override // java.util.Iterator, java.util.ListIterator
            public boolean hasNext() {
                return this.pos < IntArrayList.this.size;
            }

            @Override // com.viaversion.viaversion.libs.fastutil.BidirectionalIterator
            public boolean hasPrevious() {
                return this.pos > 0;
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
            public int nextInt() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                int[] iArr = IntArrayList.this.f92a;
                int i = this.pos;
                this.pos = i + 1;
                this.last = i;
                return iArr[i];
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntBidirectionalIterator
            public int previousInt() {
                if (!hasPrevious()) {
                    throw new NoSuchElementException();
                }
                int[] iArr = IntArrayList.this.f92a;
                int i = this.pos - 1;
                this.pos = i;
                this.last = i;
                return iArr[i];
            }

            @Override // java.util.ListIterator
            public int nextIndex() {
                return this.pos;
            }

            @Override // java.util.ListIterator
            public int previousIndex() {
                return this.pos - 1;
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntListIterator
            public void add(int k) {
                IntArrayList intArrayList = IntArrayList.this;
                int i = this.pos;
                this.pos = i + 1;
                intArrayList.add(i, k);
                this.last = -1;
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntListIterator
            public void set(int k) {
                if (this.last == -1) {
                    throw new IllegalStateException();
                }
                IntArrayList.this.set(this.last, k);
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntListIterator, java.util.Iterator, java.util.ListIterator
            public void remove() {
                if (this.last == -1) {
                    throw new IllegalStateException();
                }
                IntArrayList.this.removeInt(this.last);
                if (this.last < this.pos) {
                    this.pos--;
                }
                this.last = -1;
            }

            @Override // java.util.PrimitiveIterator.OfInt
            public void forEachRemaining(java.util.function.IntConsumer action) {
                while (this.pos < IntArrayList.this.size) {
                    int[] iArr = IntArrayList.this.f92a;
                    int i = this.pos;
                    this.pos = i + 1;
                    this.last = i;
                    action.accept(iArr[i]);
                }
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntBidirectionalIterator, com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator
            public int back(int n) {
                if (n < 0) {
                    throw new IllegalArgumentException("Argument must be nonnegative: " + n);
                }
                int remaining = IntArrayList.this.size - this.pos;
                if (n < remaining) {
                    this.pos -= n;
                } else {
                    n = remaining;
                    this.pos = 0;
                }
                this.last = this.pos;
                return n;
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntBidirectionalIterator, com.viaversion.viaversion.libs.fastutil.ints.IntIterator, com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator
            public int skip(int n) {
                if (n < 0) {
                    throw new IllegalArgumentException("Argument must be nonnegative: " + n);
                }
                int remaining = IntArrayList.this.size - this.pos;
                if (n < remaining) {
                    this.pos += n;
                } else {
                    n = remaining;
                    this.pos = IntArrayList.this.size;
                }
                this.last = this.pos - 1;
                return n;
            }
        };
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntArrayList$Spliterator.class */
    public final class Spliterator implements IntSpliterator {
        boolean hasSplit;
        int pos;
        int max;
        static final /* synthetic */ boolean $assertionsDisabled;

        static {
            $assertionsDisabled = !IntArrayList.class.desiredAssertionStatus();
        }

        public Spliterator(IntArrayList intArrayList) {
            this(0, intArrayList.size, false);
        }

        private Spliterator(int pos, int max, boolean hasSplit) {
            IntArrayList.this = r6;
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
            return this.hasSplit ? this.max : IntArrayList.this.size;
        }

        @Override // java.util.Spliterator
        public int characteristics() {
            return IntSpliterators.LIST_SPLITERATOR_CHARACTERISTICS;
        }

        @Override // java.util.Spliterator
        public long estimateSize() {
            return getWorkingMax() - this.pos;
        }

        @Override // java.util.Spliterator.OfInt
        public boolean tryAdvance(java.util.function.IntConsumer action) {
            if (this.pos >= getWorkingMax()) {
                return false;
            }
            int[] iArr = IntArrayList.this.f92a;
            int i = this.pos;
            this.pos = i + 1;
            action.accept(iArr[i]);
            return true;
        }

        @Override // java.util.Spliterator.OfInt
        public void forEachRemaining(java.util.function.IntConsumer action) {
            int max = getWorkingMax();
            while (this.pos < max) {
                action.accept(IntArrayList.this.f92a[this.pos]);
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

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
    public void sort(IntComparator comp) {
        if (comp == null) {
            IntArrays.stableSort(this.f92a, 0, this.size);
        } else {
            IntArrays.stableSort(this.f92a, 0, this.size, comp);
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
    public void unstableSort(IntComparator comp) {
        if (comp == null) {
            IntArrays.unstableSort(this.f92a, 0, this.size);
        } else {
            IntArrays.unstableSort(this.f92a, 0, this.size, comp);
        }
    }

    public IntArrayList clone() {
        IntArrayList cloned;
        if (getClass() == IntArrayList.class) {
            cloned = new IntArrayList(copyArraySafe(this.f92a, this.size), false);
            cloned.size = this.size;
        } else {
            try {
                cloned = (IntArrayList) super.clone();
                cloned.f92a = copyArraySafe(this.f92a, this.size);
            } catch (CloneNotSupportedException err) {
                throw new InternalError(err);
            }
        }
        return cloned;
    }

    public boolean equals(IntArrayList l) {
        if (l == this) {
            return true;
        }
        int s = size();
        if (s != l.size()) {
            return false;
        }
        int[] a1 = this.f92a;
        int[] a2 = l.f92a;
        if (a1 == a2 && s == l.size()) {
            return true;
        }
        do {
            int i = s;
            s--;
            if (i == 0) {
                return true;
            }
        } while (a1[s] == a2[s]);
        return false;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, java.util.Collection, java.util.List
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || !(o instanceof List)) {
            return false;
        }
        if (o instanceof IntArrayList) {
            return equals((IntArrayList) o);
        }
        if (o instanceof SubList) {
            return ((SubList) o).equals(this);
        }
        return super.equals(o);
    }

    public int compareTo(IntArrayList l) {
        int s1 = size();
        int s2 = l.size();
        int[] a1 = this.f92a;
        int[] a2 = l.f92a;
        if (a1 == a2 && s1 == s2) {
            return 0;
        }
        int i = 0;
        while (i < s1 && i < s2) {
            int e1 = a1[i];
            int e2 = a2[i];
            int r = Integer.compare(e1, e2);
            if (r == 0) {
                i++;
            } else {
                return r;
            }
        }
        if (i < s2) {
            return -1;
        }
        return i < s1 ? 1 : 0;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList
    public int compareTo(List<? extends Integer> l) {
        if (l instanceof IntArrayList) {
            return compareTo((IntArrayList) l);
        }
        if (l instanceof SubList) {
            return -((SubList) l).compareTo((List<? extends Integer>) this);
        }
        return super.compareTo(l);
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        for (int i = 0; i < this.size; i++) {
            s.writeInt(this.f92a[i]);
        }
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.f92a = new int[this.size];
        for (int i = 0; i < this.size; i++) {
            this.f92a[i] = s.readInt();
        }
    }
}
