package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.SafeMath;
import com.viaversion.viaversion.libs.fastutil.ints.IntLists;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators;
import java.io.InvalidObjectException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import java.util.stream.IntStream;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntImmutableList.class */
public class IntImmutableList extends IntLists.ImmutableListBase implements IntList, RandomAccess, Cloneable, Serializable {
    private static final long serialVersionUID = 0;
    static final IntImmutableList EMPTY = new IntImmutableList(IntArrays.EMPTY_ARRAY);

    /* renamed from: a */
    private final int[] f103a;

    public IntImmutableList(int[] a) {
        this.f103a = a;
    }

    public IntImmutableList(Collection<? extends Integer> c) {
        this(c.isEmpty() ? IntArrays.EMPTY_ARRAY : IntIterators.unwrap(IntIterators.asIntIterator(c.iterator())));
    }

    /* JADX WARN: Type inference failed for: r1v4, types: [com.viaversion.viaversion.libs.fastutil.ints.IntIterator] */
    public IntImmutableList(IntCollection c) {
        this(c.isEmpty() ? IntArrays.EMPTY_ARRAY : IntIterators.unwrap(c.iterator2()));
    }

    public IntImmutableList(IntList l) {
        this(l.isEmpty() ? IntArrays.EMPTY_ARRAY : new int[l.size()]);
        l.getElements(0, this.f103a, 0, l.size());
    }

    public IntImmutableList(int[] a, int offset, int length) {
        this(length == 0 ? IntArrays.EMPTY_ARRAY : new int[length]);
        System.arraycopy(a, offset, this.f103a, 0, length);
    }

    public IntImmutableList(IntIterator i) {
        this(i.hasNext() ? IntIterators.unwrap(i) : IntArrays.EMPTY_ARRAY);
    }

    /* renamed from: of */
    public static IntImmutableList m213of() {
        return EMPTY;
    }

    /* renamed from: of */
    public static IntImmutableList m212of(int... init) {
        return init.length == 0 ? m213of() : new IntImmutableList(init);
    }

    private static IntImmutableList convertTrustedToImmutableList(IntArrayList arrayList) {
        if (arrayList.isEmpty()) {
            return m213of();
        }
        int[] backingArray = arrayList.elements();
        if (arrayList.size() != backingArray.length) {
            backingArray = Arrays.copyOf(backingArray, arrayList.size());
        }
        return new IntImmutableList(backingArray);
    }

    public static IntImmutableList toList(IntStream stream) {
        return convertTrustedToImmutableList(IntArrayList.toList(stream));
    }

    public static IntImmutableList toListWithExpectedSize(IntStream stream, int expectedSize) {
        return convertTrustedToImmutableList(IntArrayList.toListWithExpectedSize(stream, expectedSize));
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
    public int getInt(int index) {
        if (index >= this.f103a.length) {
            throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.f103a.length + ")");
        }
        return this.f103a[index];
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList
    public int indexOf(int k) {
        int size = this.f103a.length;
        for (int i = 0; i < size; i++) {
            if (k == this.f103a[i]) {
                return i;
            }
        }
        return -1;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList
    public int lastIndexOf(int k) {
        int i = this.f103a.length;
        do {
            int i2 = i;
            i--;
            if (i2 == 0) {
                return -1;
            }
        } while (k != this.f103a[i]);
        return i;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return this.f103a.length;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List, com.viaversion.viaversion.libs.fastutil.Stack
    public boolean isEmpty() {
        return this.f103a.length == 0;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList
    public void getElements(int from, int[] a, int offset, int length) {
        IntArrays.ensureOffsetLength(a, offset, length);
        System.arraycopy(this.f103a, from, a, offset, length);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntIterable
    public void forEach(java.util.function.IntConsumer action) {
        for (int i = 0; i < this.f103a.length; i++) {
            action.accept(this.f103a[i]);
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
    public int[] toIntArray() {
        return (int[]) this.f103a.clone();
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
    public int[] toArray(int[] a) {
        if (a == null || a.length < size()) {
            a = new int[this.f103a.length];
        }
        System.arraycopy(this.f103a, 0, a, 0, a.length);
        return a;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList, java.util.List
    /* renamed from: listIterator */
    public ListIterator<Integer> listIterator2(final int index) {
        ensureIndex(index);
        return new IntListIterator() { // from class: com.viaversion.viaversion.libs.fastutil.ints.IntImmutableList.1
            int pos;

            {
                IntImmutableList.this = this;
                this.pos = index;
            }

            @Override // java.util.Iterator, java.util.ListIterator
            public boolean hasNext() {
                return this.pos < IntImmutableList.this.f103a.length;
            }

            @Override // com.viaversion.viaversion.libs.fastutil.BidirectionalIterator
            public boolean hasPrevious() {
                return this.pos > 0;
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
            public int nextInt() {
                if (hasNext()) {
                    int[] iArr = IntImmutableList.this.f103a;
                    int i = this.pos;
                    this.pos = i + 1;
                    return iArr[i];
                }
                throw new NoSuchElementException();
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntBidirectionalIterator
            public int previousInt() {
                if (hasPrevious()) {
                    int[] iArr = IntImmutableList.this.f103a;
                    int i = this.pos - 1;
                    this.pos = i;
                    return iArr[i];
                }
                throw new NoSuchElementException();
            }

            @Override // java.util.ListIterator
            public int nextIndex() {
                return this.pos;
            }

            @Override // java.util.ListIterator
            public int previousIndex() {
                return this.pos - 1;
            }

            @Override // java.util.PrimitiveIterator.OfInt
            public void forEachRemaining(java.util.function.IntConsumer action) {
                while (this.pos < IntImmutableList.this.f103a.length) {
                    int[] iArr = IntImmutableList.this.f103a;
                    int i = this.pos;
                    this.pos = i + 1;
                    action.accept(iArr[i]);
                }
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntListIterator
            public void add(int k) {
                throw new UnsupportedOperationException();
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntListIterator
            public void set(int k) {
                throw new UnsupportedOperationException();
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntListIterator, java.util.Iterator, java.util.ListIterator
            public void remove() {
                throw new UnsupportedOperationException();
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntBidirectionalIterator, com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator
            public int back(int n) {
                if (n >= 0) {
                    int remaining = IntImmutableList.this.f103a.length - this.pos;
                    if (n < remaining) {
                        this.pos -= n;
                    } else {
                        n = remaining;
                        this.pos = 0;
                    }
                    return n;
                }
                throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntBidirectionalIterator, com.viaversion.viaversion.libs.fastutil.ints.IntIterator, com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator
            public int skip(int n) {
                if (n >= 0) {
                    int remaining = IntImmutableList.this.f103a.length - this.pos;
                    if (n < remaining) {
                        this.pos += n;
                    } else {
                        n = remaining;
                        this.pos = IntImmutableList.this.f103a.length;
                    }
                    return n;
                }
                throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            }
        };
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntImmutableList$Spliterator.class */
    public final class Spliterator implements IntSpliterator {
        int pos;
        int max;
        static final /* synthetic */ boolean $assertionsDisabled;

        static {
            $assertionsDisabled = !IntImmutableList.class.desiredAssertionStatus();
        }

        public Spliterator(IntImmutableList intImmutableList) {
            this(0, intImmutableList.f103a.length);
        }

        private Spliterator(int pos, int max) {
            IntImmutableList.this = r6;
            if ($assertionsDisabled || pos <= max) {
                this.pos = pos;
                this.max = max;
                return;
            }
            throw new AssertionError("pos " + pos + " must be <= max " + max);
        }

        @Override // java.util.Spliterator
        public int characteristics() {
            return 17744;
        }

        @Override // java.util.Spliterator
        public long estimateSize() {
            return this.max - this.pos;
        }

        @Override // java.util.Spliterator.OfInt
        public boolean tryAdvance(java.util.function.IntConsumer action) {
            if (this.pos < this.max) {
                int[] iArr = IntImmutableList.this.f103a;
                int i = this.pos;
                this.pos = i + 1;
                action.accept(iArr[i]);
                return true;
            }
            return false;
        }

        @Override // java.util.Spliterator.OfInt
        public void forEachRemaining(java.util.function.IntConsumer action) {
            while (this.pos < this.max) {
                action.accept(IntImmutableList.this.f103a[this.pos]);
                this.pos++;
            }
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator
        public long skip(long n) {
            if (n < 0) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            }
            if (this.pos >= this.max) {
                return 0L;
            }
            int remaining = this.max - this.pos;
            if (n < remaining) {
                this.pos = SafeMath.safeLongToInt(this.pos + n);
                return n;
            }
            long n2 = remaining;
            this.pos = this.max;
            return n2;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator, java.util.Spliterator.OfInt, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public IntSpliterator trySplit() {
            int retLen = (this.max - this.pos) >> 1;
            if (retLen <= 1) {
                return null;
            }
            int myNewPos = this.pos + retLen;
            int oldPos = this.pos;
            this.pos = myNewPos;
            return new Spliterator(oldPos, myNewPos);
        }
    }

    @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
    /* renamed from: spliterator */
    public java.util.Spliterator<Integer> spliterator2() {
        return new Spliterator(this);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntImmutableList$ImmutableSubList.class */
    public static final class ImmutableSubList extends IntLists.ImmutableListBase implements RandomAccess, Serializable {
        private static final long serialVersionUID = 7054639518438982401L;
        final IntImmutableList innerList;
        final int from;

        /* renamed from: to */
        final int f104to;

        /* renamed from: a */
        final transient int[] f105a;

        ImmutableSubList(IntImmutableList innerList, int from, int to) {
            this.innerList = innerList;
            this.from = from;
            this.f104to = to;
            this.f105a = innerList.f103a;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
        public int getInt(int index) {
            ensureRestrictedIndex(index);
            return this.f105a[index + this.from];
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList
        public int indexOf(int k) {
            for (int i = this.from; i < this.f104to; i++) {
                if (k == this.f105a[i]) {
                    return i - this.from;
                }
            }
            return -1;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList
        public int lastIndexOf(int k) {
            int i = this.f104to;
            do {
                int i2 = i;
                i--;
                if (i2 == this.from) {
                    return -1;
                }
            } while (k != this.f105a[i]);
            return i - this.from;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.f104to - this.from;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List, com.viaversion.viaversion.libs.fastutil.Stack
        public boolean isEmpty() {
            return this.f104to <= this.from;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList
        public void getElements(int fromSublistIndex, int[] a, int offset, int length) {
            IntArrays.ensureOffsetLength(a, offset, length);
            ensureRestrictedIndex(fromSublistIndex);
            if (this.from + length > this.f104to) {
                throw new IndexOutOfBoundsException("Final index " + (this.from + length) + " (startingIndex: " + this.from + " + length: " + length + ") is greater then list length " + size());
            }
            System.arraycopy(this.f105a, fromSublistIndex + this.from, a, offset, length);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntIterable
        public void forEach(java.util.function.IntConsumer action) {
            for (int i = this.from; i < this.f104to; i++) {
                action.accept(this.f105a[i]);
            }
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
        public int[] toIntArray() {
            return Arrays.copyOfRange(this.f105a, this.from, this.f104to);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
        public int[] toArray(int[] a) {
            if (a == null || a.length < size()) {
                a = new int[size()];
            }
            System.arraycopy(this.f105a, this.from, a, 0, size());
            return a;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList, java.util.List
        /* renamed from: listIterator */
        public ListIterator<Integer> listIterator2(final int index) {
            ensureIndex(index);
            return new IntListIterator() { // from class: com.viaversion.viaversion.libs.fastutil.ints.IntImmutableList.ImmutableSubList.1
                int pos;

                {
                    ImmutableSubList.this = this;
                    this.pos = index;
                }

                @Override // java.util.Iterator, java.util.ListIterator
                public boolean hasNext() {
                    return this.pos < ImmutableSubList.this.f104to;
                }

                @Override // com.viaversion.viaversion.libs.fastutil.BidirectionalIterator
                public boolean hasPrevious() {
                    return this.pos > ImmutableSubList.this.from;
                }

                @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
                public int nextInt() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    int[] iArr = ImmutableSubList.this.f105a;
                    int i = this.pos;
                    this.pos = i + 1;
                    return iArr[i + ImmutableSubList.this.from];
                }

                @Override // com.viaversion.viaversion.libs.fastutil.ints.IntBidirectionalIterator
                public int previousInt() {
                    if (!hasPrevious()) {
                        throw new NoSuchElementException();
                    }
                    int[] iArr = ImmutableSubList.this.f105a;
                    int i = this.pos - 1;
                    this.pos = i;
                    return iArr[i + ImmutableSubList.this.from];
                }

                @Override // java.util.ListIterator
                public int nextIndex() {
                    return this.pos;
                }

                @Override // java.util.ListIterator
                public int previousIndex() {
                    return this.pos - 1;
                }

                @Override // java.util.PrimitiveIterator.OfInt
                public void forEachRemaining(java.util.function.IntConsumer action) {
                    while (this.pos < ImmutableSubList.this.f104to) {
                        int[] iArr = ImmutableSubList.this.f105a;
                        int i = this.pos;
                        this.pos = i + 1;
                        action.accept(iArr[i + ImmutableSubList.this.from]);
                    }
                }

                @Override // com.viaversion.viaversion.libs.fastutil.ints.IntListIterator
                public void add(int k) {
                    throw new UnsupportedOperationException();
                }

                @Override // com.viaversion.viaversion.libs.fastutil.ints.IntListIterator
                public void set(int k) {
                    throw new UnsupportedOperationException();
                }

                @Override // com.viaversion.viaversion.libs.fastutil.ints.IntListIterator, java.util.Iterator, java.util.ListIterator
                public void remove() {
                    throw new UnsupportedOperationException();
                }

                @Override // com.viaversion.viaversion.libs.fastutil.ints.IntBidirectionalIterator, com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator
                public int back(int n) {
                    if (n < 0) {
                        throw new IllegalArgumentException("Argument must be nonnegative: " + n);
                    }
                    int remaining = ImmutableSubList.this.f104to - this.pos;
                    if (n < remaining) {
                        this.pos -= n;
                    } else {
                        n = remaining;
                        this.pos = 0;
                    }
                    return n;
                }

                @Override // com.viaversion.viaversion.libs.fastutil.ints.IntBidirectionalIterator, com.viaversion.viaversion.libs.fastutil.ints.IntIterator, com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator
                public int skip(int n) {
                    if (n < 0) {
                        throw new IllegalArgumentException("Argument must be nonnegative: " + n);
                    }
                    int remaining = ImmutableSubList.this.f104to - this.pos;
                    if (n < remaining) {
                        this.pos += n;
                    } else {
                        n = remaining;
                        this.pos = ImmutableSubList.this.f104to;
                    }
                    return n;
                }
            };
        }

        /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntImmutableList$ImmutableSubList$SubListSpliterator.class */
        public final class SubListSpliterator extends IntSpliterators.EarlyBindingSizeIndexBasedSpliterator {
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            SubListSpliterator() {
                super(r5.from, r5.f104to);
                ImmutableSubList.this = r5;
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            private SubListSpliterator(int pos, int maxPos) {
                super(pos, maxPos);
                ImmutableSubList.this = r5;
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators.AbstractIndexBasedSpliterator
            protected final int get(int i) {
                return ImmutableSubList.this.f105a[i];
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators.AbstractIndexBasedSpliterator
            public final SubListSpliterator makeForSplit(int pos, int maxPos) {
                return new SubListSpliterator(pos, maxPos);
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators.AbstractIndexBasedSpliterator, java.util.Spliterator.OfInt
            public boolean tryAdvance(java.util.function.IntConsumer action) {
                if (this.pos >= this.maxPos) {
                    return false;
                }
                int[] iArr = ImmutableSubList.this.f105a;
                int i = this.pos;
                this.pos = i + 1;
                action.accept(iArr[i]);
                return true;
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators.AbstractIndexBasedSpliterator, java.util.Spliterator.OfInt
            public void forEachRemaining(java.util.function.IntConsumer action) {
                int max = this.maxPos;
                while (this.pos < max) {
                    int[] iArr = ImmutableSubList.this.f105a;
                    int i = this.pos;
                    this.pos = i + 1;
                    action.accept(iArr[i]);
                }
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators.AbstractIndexBasedSpliterator, java.util.Spliterator
            public int characteristics() {
                return 17744;
            }
        }

        @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
        /* renamed from: spliterator */
        public java.util.Spliterator<Integer> spliterator2() {
            return new SubListSpliterator();
        }

        boolean contentsEquals(int[] otherA, int otherAFrom, int otherATo) {
            if (this.f105a == otherA && this.from == otherAFrom && this.f104to == otherATo) {
                return true;
            }
            if (otherATo - otherAFrom != size()) {
                return false;
            }
            int pos = this.from;
            int otherPos = otherAFrom;
            while (pos < this.f104to) {
                int i = pos;
                pos++;
                int i2 = otherPos;
                otherPos++;
                if (this.f105a[i] != otherA[i2]) {
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
            if (o instanceof IntImmutableList) {
                IntImmutableList other = (IntImmutableList) o;
                return contentsEquals(other.f103a, 0, other.size());
            } else if (o instanceof ImmutableSubList) {
                ImmutableSubList other2 = (ImmutableSubList) o;
                return contentsEquals(other2.f105a, other2.from, other2.f104to);
            } else {
                return super.equals(o);
            }
        }

        int contentsCompareTo(int[] otherA, int otherAFrom, int otherATo) {
            if (this.f105a == otherA && this.from == otherAFrom && this.f104to == otherATo) {
                return 0;
            }
            int i = this.from;
            int j = otherAFrom;
            while (i < this.f104to && i < otherATo) {
                int e1 = this.f105a[i];
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
            return i < this.f104to ? 1 : 0;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList
        public int compareTo(List<? extends Integer> l) {
            if (l instanceof IntImmutableList) {
                IntImmutableList other = (IntImmutableList) l;
                return contentsCompareTo(other.f103a, 0, other.size());
            } else if (l instanceof ImmutableSubList) {
                ImmutableSubList other2 = (ImmutableSubList) l;
                return contentsCompareTo(other2.f105a, other2.from, other2.f104to);
            } else {
                return super.compareTo(l);
            }
        }

        private Object readResolve() throws ObjectStreamException {
            try {
                return this.innerList.subList2(this.from, this.f104to);
            } catch (IllegalArgumentException | IndexOutOfBoundsException ex) {
                throw ((InvalidObjectException) new InvalidObjectException(ex.getMessage()).initCause(ex));
            }
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList, java.util.List
        /* renamed from: subList */
        public List<Integer> subList2(int from, int to) {
            ensureIndex(from);
            ensureIndex(to);
            if (from == to) {
                return IntImmutableList.EMPTY;
            }
            if (from > to) {
                throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
            }
            return new ImmutableSubList(this.innerList, from + this.from, to + this.from);
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
        if (from == to) {
            return EMPTY;
        }
        if (from > to) {
            throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
        }
        return new ImmutableSubList(this, from, to);
    }

    public IntImmutableList clone() {
        return this;
    }

    public boolean equals(IntImmutableList l) {
        if (l == this || this.f103a == l.f103a) {
            return true;
        }
        int s = size();
        if (s != l.size()) {
            return false;
        }
        int[] a1 = this.f103a;
        int[] a2 = l.f103a;
        return Arrays.equals(a1, a2);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, java.util.Collection, java.util.List
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || !(o instanceof List)) {
            return false;
        }
        if (o instanceof IntImmutableList) {
            return equals((IntImmutableList) o);
        }
        if (o instanceof ImmutableSubList) {
            return ((ImmutableSubList) o).equals(this);
        }
        return super.equals(o);
    }

    public int compareTo(IntImmutableList l) {
        if (this.f103a == l.f103a) {
            return 0;
        }
        int s1 = size();
        int s2 = l.size();
        int[] a1 = this.f103a;
        int[] a2 = l.f103a;
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
        if (l instanceof IntImmutableList) {
            return compareTo((IntImmutableList) l);
        }
        if (l instanceof ImmutableSubList) {
            ImmutableSubList other = (ImmutableSubList) l;
            return -other.compareTo((List<? extends Integer>) this);
        }
        return super.compareTo(l);
    }
}
