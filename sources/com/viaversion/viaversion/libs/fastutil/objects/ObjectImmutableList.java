package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.SafeMath;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollections;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectLists;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators;
import java.io.InvalidObjectException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.function.Consumer;
import java.util.stream.Collector;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectImmutableList.class */
public class ObjectImmutableList<K> extends ObjectLists.ImmutableListBase<K> implements ObjectList<K>, RandomAccess, Cloneable, Serializable {
    private static final long serialVersionUID = 0;

    /* renamed from: a */
    private final K[] f155a;
    static final ObjectImmutableList EMPTY = new ObjectImmutableList(ObjectArrays.EMPTY_ARRAY);
    private static final Collector<Object, ?, ObjectImmutableList<Object>> TO_LIST_COLLECTOR = Collector.of(ObjectArrayList::new, (v0, v1) -> {
        v0.add(v1);
    }, (v0, v1) -> {
        return v0.combine(v1);
    }, ObjectImmutableList::convertTrustedToImmutableList, new Collector.Characteristics[0]);

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, java.lang.Comparable
    public /* bridge */ /* synthetic */ int compareTo(Object obj) {
        return compareTo((List) ((List) obj));
    }

    private static final <K> K[] emptyArray() {
        return (K[]) ObjectArrays.EMPTY_ARRAY;
    }

    public ObjectImmutableList(K[] a) {
        this.f155a = a;
    }

    public ObjectImmutableList(Collection<? extends K> c) {
        this(c.isEmpty() ? emptyArray() : ObjectIterators.unwrap(c.iterator()));
    }

    public ObjectImmutableList(ObjectCollection<? extends K> c) {
        this(c.isEmpty() ? emptyArray() : ObjectIterators.unwrap(c.iterator()));
    }

    public ObjectImmutableList(ObjectList<? extends K> l) {
        this(l.isEmpty() ? emptyArray() : new Object[l.size()]);
        l.getElements(0, this.f155a, 0, l.size());
    }

    public ObjectImmutableList(K[] a, int offset, int length) {
        this(length == 0 ? emptyArray() : new Object[length]);
        System.arraycopy(a, offset, this.f155a, 0, length);
    }

    public ObjectImmutableList(ObjectIterator<? extends K> i) {
        this(i.hasNext() ? ObjectIterators.unwrap(i) : emptyArray());
    }

    /* renamed from: of */
    public static <K> ObjectImmutableList<K> m176of() {
        return EMPTY;
    }

    @SafeVarargs
    /* renamed from: of */
    public static <K> ObjectImmutableList<K> m175of(K... init) {
        return init.length == 0 ? m176of() : new ObjectImmutableList<>(init);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v8, types: [java.lang.Object[]] */
    private static <K> ObjectImmutableList<K> convertTrustedToImmutableList(ObjectArrayList<K> arrayList) {
        if (arrayList.isEmpty()) {
            return m176of();
        }
        K[] backingArray = arrayList.elements();
        if (arrayList.size() != backingArray.length) {
            backingArray = Arrays.copyOf(backingArray, arrayList.size());
        }
        return new ObjectImmutableList<>(backingArray);
    }

    public static <K> Collector<K, ?, ObjectImmutableList<K>> toList() {
        return (Collector<K, ?, ObjectImmutableList<K>>) TO_LIST_COLLECTOR;
    }

    public static <K> Collector<K, ?, ObjectImmutableList<K>> toListWithExpectedSize(int expectedSize) {
        if (expectedSize <= 10) {
            return toList();
        }
        return Collector.of(new ObjectCollections.SizeDecreasingSupplier(expectedSize, size -> {
            if (size <= 10) {
                return new ObjectArrayList();
            }
            return new ObjectArrayList(size);
        }), (v0, v1) -> {
            v0.add(v1);
        }, (v0, v1) -> {
            return v0.combine(v1);
        }, ObjectImmutableList::convertTrustedToImmutableList, new Collector.Characteristics[0]);
    }

    @Override // java.util.List
    public K get(int index) {
        if (index >= this.f155a.length) {
            throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.f155a.length + ")");
        }
        return this.f155a[index];
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, java.util.List
    public int indexOf(Object k) {
        int size = this.f155a.length;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(k, this.f155a[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, java.util.List
    public int lastIndexOf(Object k) {
        int i = this.f155a.length;
        do {
            int i2 = i;
            i--;
            if (i2 == 0) {
                return -1;
            }
        } while (!Objects.equals(k, this.f155a[i]));
        return i;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return this.f155a.length;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List, com.viaversion.viaversion.libs.fastutil.Stack
    public boolean isEmpty() {
        return this.f155a.length == 0;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, com.viaversion.viaversion.libs.fastutil.objects.ObjectList
    public void getElements(int from, Object[] a, int offset, int length) {
        ObjectArrays.ensureOffsetLength(a, offset, length);
        System.arraycopy(this.f155a, from, a, offset, length);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, java.lang.Iterable
    public void forEach(Consumer<? super K> action) {
        for (int i = 0; i < this.f155a.length; i++) {
            action.accept((Object) this.f155a[i]);
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public Object[] toArray() {
        if (this.f155a.getClass().equals(Object[].class)) {
            return (Object[]) this.f155a.clone();
        }
        return Arrays.copyOf(this.f155a, this.f155a.length, Object[].class);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v13, types: [java.lang.Object[]] */
    /* JADX WARN: Type inference failed for: r0v16, types: [java.lang.Object[]] */
    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public <K> K[] toArray(K[] a) {
        if (a == null) {
            a = new Object[size()];
        } else if (a.length < size()) {
            a = (Object[]) Array.newInstance(a.getClass().getComponentType(), size());
        }
        System.arraycopy(this.f155a, 0, a, 0, size());
        if (a.length > size()) {
            a[size()] = null;
        }
        return a;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, com.viaversion.viaversion.libs.fastutil.objects.ObjectList, java.util.List
    public ObjectListIterator<K> listIterator(final int index) {
        ensureIndex(index);
        return new ObjectListIterator<K>() { // from class: com.viaversion.viaversion.libs.fastutil.objects.ObjectImmutableList.1
            int pos;

            {
                ObjectImmutableList.this = this;
                this.pos = index;
            }

            @Override // java.util.Iterator, java.util.ListIterator
            public boolean hasNext() {
                return this.pos < ObjectImmutableList.this.f155a.length;
            }

            @Override // com.viaversion.viaversion.libs.fastutil.BidirectionalIterator
            public boolean hasPrevious() {
                return this.pos > 0;
            }

            @Override // java.util.Iterator, java.util.ListIterator
            public K next() {
                if (hasNext()) {
                    Object[] objArr = ObjectImmutableList.this.f155a;
                    int i = this.pos;
                    this.pos = i + 1;
                    return (K) objArr[i];
                }
                throw new NoSuchElementException();
            }

            @Override // com.viaversion.viaversion.libs.fastutil.BidirectionalIterator, java.util.ListIterator
            public K previous() {
                if (hasPrevious()) {
                    Object[] objArr = ObjectImmutableList.this.f155a;
                    int i = this.pos - 1;
                    this.pos = i;
                    return (K) objArr[i];
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

            @Override // java.util.Iterator
            public void forEachRemaining(Consumer<? super K> action) {
                while (this.pos < ObjectImmutableList.this.f155a.length) {
                    Object[] objArr = ObjectImmutableList.this.f155a;
                    int i = this.pos;
                    this.pos = i + 1;
                    action.accept(objArr[i]);
                }
            }

            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectListIterator, java.util.ListIterator
            public void add(K k) {
                throw new UnsupportedOperationException();
            }

            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectListIterator, java.util.ListIterator
            public void set(K k) {
                throw new UnsupportedOperationException();
            }

            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectListIterator, java.util.Iterator, java.util.ListIterator
            public void remove() {
                throw new UnsupportedOperationException();
            }

            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator
            public int back(int n) {
                if (n >= 0) {
                    int remaining = ObjectImmutableList.this.f155a.length - this.pos;
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

            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator
            public int skip(int n) {
                if (n >= 0) {
                    int remaining = ObjectImmutableList.this.f155a.length - this.pos;
                    if (n < remaining) {
                        this.pos += n;
                    } else {
                        n = remaining;
                        this.pos = ObjectImmutableList.this.f155a.length;
                    }
                    return n;
                }
                throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            }
        };
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectImmutableList$Spliterator.class */
    public final class Spliterator implements ObjectSpliterator<K> {
        int pos;
        int max;
        static final /* synthetic */ boolean $assertionsDisabled;

        static {
            $assertionsDisabled = !ObjectImmutableList.class.desiredAssertionStatus();
        }

        public Spliterator(ObjectImmutableList objectImmutableList) {
            this(0, objectImmutableList.f155a.length);
        }

        private Spliterator(int pos, int max) {
            ObjectImmutableList.this = r6;
            if ($assertionsDisabled || pos <= max) {
                this.pos = pos;
                this.max = max;
                return;
            }
            throw new AssertionError("pos " + pos + " must be <= max " + max);
        }

        @Override // java.util.Spliterator
        public int characteristics() {
            return 17488;
        }

        @Override // java.util.Spliterator
        public long estimateSize() {
            return this.max - this.pos;
        }

        @Override // java.util.Spliterator
        public boolean tryAdvance(Consumer<? super K> action) {
            if (this.pos < this.max) {
                Object[] objArr = ObjectImmutableList.this.f155a;
                int i = this.pos;
                this.pos = i + 1;
                action.accept(objArr[i]);
                return true;
            }
            return false;
        }

        @Override // java.util.Spliterator
        public void forEachRemaining(Consumer<? super K> action) {
            while (this.pos < this.max) {
                action.accept(ObjectImmutableList.this.f155a[this.pos]);
                this.pos++;
            }
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator
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

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator, java.util.Spliterator
        public ObjectSpliterator<K> trySplit() {
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

    @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectSet, java.util.Set
    public ObjectSpliterator<K> spliterator() {
        return new Spliterator(this);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectImmutableList$ImmutableSubList.class */
    public static final class ImmutableSubList<K> extends ObjectLists.ImmutableListBase<K> implements RandomAccess, Serializable {
        private static final long serialVersionUID = 7054639518438982401L;
        final ObjectImmutableList<K> innerList;
        final int from;

        /* renamed from: to */
        final int f156to;

        /* renamed from: a */
        final transient K[] f157a;

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, java.lang.Comparable
        public /* bridge */ /* synthetic */ int compareTo(Object obj) {
            return compareTo((List) ((List) obj));
        }

        ImmutableSubList(ObjectImmutableList<K> innerList, int from, int to) {
            this.innerList = innerList;
            this.from = from;
            this.f156to = to;
            this.f157a = (K[]) ((ObjectImmutableList) innerList).f155a;
        }

        @Override // java.util.List
        public K get(int index) {
            ensureRestrictedIndex(index);
            return this.f157a[index + this.from];
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, java.util.List
        public int indexOf(Object k) {
            for (int i = this.from; i < this.f156to; i++) {
                if (Objects.equals(k, this.f157a[i])) {
                    return i - this.from;
                }
            }
            return -1;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, java.util.List
        public int lastIndexOf(Object k) {
            int i = this.f156to;
            do {
                int i2 = i;
                i--;
                if (i2 == this.from) {
                    return -1;
                }
            } while (!Objects.equals(k, this.f157a[i]));
            return i - this.from;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.f156to - this.from;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List, com.viaversion.viaversion.libs.fastutil.Stack
        public boolean isEmpty() {
            return this.f156to <= this.from;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, com.viaversion.viaversion.libs.fastutil.objects.ObjectList
        public void getElements(int fromSublistIndex, Object[] a, int offset, int length) {
            ObjectArrays.ensureOffsetLength(a, offset, length);
            ensureRestrictedIndex(fromSublistIndex);
            if (this.from + length > this.f156to) {
                throw new IndexOutOfBoundsException("Final index " + (this.from + length) + " (startingIndex: " + this.from + " + length: " + length + ") is greater then list length " + size());
            }
            System.arraycopy(this.f157a, fromSublistIndex + this.from, a, offset, length);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, java.lang.Iterable
        public void forEach(Consumer<? super K> action) {
            for (int i = this.from; i < this.f156to; i++) {
                action.accept((Object) this.f157a[i]);
            }
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, java.util.AbstractCollection, java.util.Collection, java.util.List
        public Object[] toArray() {
            return Arrays.copyOfRange(this.f157a, this.from, this.f156to, Object[].class);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v15, types: [java.lang.Object[]] */
        /* JADX WARN: Type inference failed for: r0v17, types: [java.lang.Object[]] */
        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, java.util.AbstractCollection, java.util.Collection, java.util.List
        public <K> K[] toArray(K[] a) {
            int size = size();
            if (a == null) {
                a = new Object[size];
            } else if (a.length < size) {
                a = (Object[]) Array.newInstance(a.getClass().getComponentType(), size);
            }
            System.arraycopy(this.f157a, this.from, a, 0, size);
            if (a.length > size) {
                a[size] = null;
            }
            return a;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, com.viaversion.viaversion.libs.fastutil.objects.ObjectList, java.util.List
        public ObjectListIterator<K> listIterator(final int index) {
            ensureIndex(index);
            return new ObjectListIterator<K>() { // from class: com.viaversion.viaversion.libs.fastutil.objects.ObjectImmutableList.ImmutableSubList.1
                int pos;

                {
                    ImmutableSubList.this = this;
                    this.pos = index;
                }

                @Override // java.util.Iterator, java.util.ListIterator
                public boolean hasNext() {
                    return this.pos < ImmutableSubList.this.f156to;
                }

                @Override // com.viaversion.viaversion.libs.fastutil.BidirectionalIterator
                public boolean hasPrevious() {
                    return this.pos > ImmutableSubList.this.from;
                }

                @Override // java.util.Iterator, java.util.ListIterator
                public K next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    K[] kArr = ImmutableSubList.this.f157a;
                    int i = this.pos;
                    this.pos = i + 1;
                    return kArr[i + ImmutableSubList.this.from];
                }

                @Override // com.viaversion.viaversion.libs.fastutil.BidirectionalIterator, java.util.ListIterator
                public K previous() {
                    if (!hasPrevious()) {
                        throw new NoSuchElementException();
                    }
                    K[] kArr = ImmutableSubList.this.f157a;
                    int i = this.pos - 1;
                    this.pos = i;
                    return kArr[i + ImmutableSubList.this.from];
                }

                @Override // java.util.ListIterator
                public int nextIndex() {
                    return this.pos;
                }

                @Override // java.util.ListIterator
                public int previousIndex() {
                    return this.pos - 1;
                }

                @Override // java.util.Iterator
                public void forEachRemaining(Consumer<? super K> action) {
                    while (this.pos < ImmutableSubList.this.f156to) {
                        int i = this.pos;
                        this.pos = i + 1;
                        action.accept((Object) ImmutableSubList.this.f157a[i + ImmutableSubList.this.from]);
                    }
                }

                @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectListIterator, java.util.ListIterator
                public void add(K k) {
                    throw new UnsupportedOperationException();
                }

                @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectListIterator, java.util.ListIterator
                public void set(K k) {
                    throw new UnsupportedOperationException();
                }

                @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectListIterator, java.util.Iterator, java.util.ListIterator
                public void remove() {
                    throw new UnsupportedOperationException();
                }

                @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator
                public int back(int n) {
                    if (n < 0) {
                        throw new IllegalArgumentException("Argument must be nonnegative: " + n);
                    }
                    int remaining = ImmutableSubList.this.f156to - this.pos;
                    if (n < remaining) {
                        this.pos -= n;
                    } else {
                        n = remaining;
                        this.pos = 0;
                    }
                    return n;
                }

                @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator
                public int skip(int n) {
                    if (n < 0) {
                        throw new IllegalArgumentException("Argument must be nonnegative: " + n);
                    }
                    int remaining = ImmutableSubList.this.f156to - this.pos;
                    if (n < remaining) {
                        this.pos += n;
                    } else {
                        n = remaining;
                        this.pos = ImmutableSubList.this.f156to;
                    }
                    return n;
                }
            };
        }

        /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectImmutableList$ImmutableSubList$SubListSpliterator.class */
        public final class SubListSpliterator extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator<K> {
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            SubListSpliterator() {
                super(r5.from, r5.f156to);
                ImmutableSubList.this = r5;
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            private SubListSpliterator(int pos, int maxPos) {
                super(pos, maxPos);
                ImmutableSubList.this = r5;
            }

            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators.AbstractIndexBasedSpliterator
            protected final K get(int i) {
                return ImmutableSubList.this.f157a[i];
            }

            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators.AbstractIndexBasedSpliterator
            public final ImmutableSubList<K>.SubListSpliterator makeForSplit(int pos, int maxPos) {
                return new SubListSpliterator(pos, maxPos);
            }

            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators.AbstractIndexBasedSpliterator, java.util.Spliterator
            public boolean tryAdvance(Consumer<? super K> action) {
                if (this.pos >= this.maxPos) {
                    return false;
                }
                int i = this.pos;
                this.pos = i + 1;
                action.accept((Object) ImmutableSubList.this.f157a[i]);
                return true;
            }

            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators.AbstractIndexBasedSpliterator, java.util.Spliterator
            public void forEachRemaining(Consumer<? super K> action) {
                int max = this.maxPos;
                while (this.pos < max) {
                    int i = this.pos;
                    this.pos = i + 1;
                    action.accept((Object) ImmutableSubList.this.f157a[i]);
                }
            }

            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators.AbstractIndexBasedSpliterator, java.util.Spliterator
            public int characteristics() {
                return 17488;
            }
        }

        @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectSet, java.util.Set
        public ObjectSpliterator<K> spliterator() {
            return new SubListSpliterator();
        }

        boolean contentsEquals(K[] otherA, int otherAFrom, int otherATo) {
            if (this.f157a == otherA && this.from == otherAFrom && this.f156to == otherATo) {
                return true;
            }
            if (otherATo - otherAFrom != size()) {
                return false;
            }
            int pos = this.from;
            int otherPos = otherAFrom;
            while (pos < this.f156to) {
                int i = pos;
                pos++;
                int i2 = otherPos;
                otherPos++;
                if (!Objects.equals(this.f157a[i], otherA[i2])) {
                    return false;
                }
            }
            return true;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, java.util.Collection, java.util.List
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (o == null || !(o instanceof List)) {
                return false;
            }
            if (o instanceof ObjectImmutableList) {
                ObjectImmutableList<K> other = (ObjectImmutableList) o;
                return contentsEquals(((ObjectImmutableList) other).f155a, 0, other.size());
            } else if (o instanceof ImmutableSubList) {
                ImmutableSubList<K> other2 = (ImmutableSubList) o;
                return contentsEquals(other2.f157a, other2.from, other2.f156to);
            } else {
                return super.equals(o);
            }
        }

        int contentsCompareTo(K[] otherA, int otherAFrom, int otherATo) {
            int i = this.from;
            int j = otherAFrom;
            while (i < this.f156to && i < otherATo) {
                K e1 = this.f157a[i];
                K e2 = otherA[j];
                int r = ((Comparable) e1).compareTo(e2);
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
            return i < this.f156to ? 1 : 0;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList
        public int compareTo(List<? extends K> l) {
            if (l instanceof ObjectImmutableList) {
                ObjectImmutableList<K> other = (ObjectImmutableList) l;
                return contentsCompareTo(((ObjectImmutableList) other).f155a, 0, other.size());
            } else if (l instanceof ImmutableSubList) {
                ImmutableSubList<K> other2 = (ImmutableSubList) l;
                return contentsCompareTo(other2.f157a, other2.from, other2.f156to);
            } else {
                return super.compareTo((List) l);
            }
        }

        private Object readResolve() throws ObjectStreamException {
            try {
                return this.innerList.subList(this.from, this.f156to);
            } catch (IllegalArgumentException | IndexOutOfBoundsException ex) {
                throw ((InvalidObjectException) new InvalidObjectException(ex.getMessage()).initCause(ex));
            }
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, com.viaversion.viaversion.libs.fastutil.objects.ObjectList, java.util.List
        public ObjectList<K> subList(int from, int to) {
            ensureIndex(from);
            ensureIndex(to);
            if (from == to) {
                return ObjectImmutableList.EMPTY;
            }
            if (from > to) {
                throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
            }
            return new ImmutableSubList(this.innerList, from + this.from, to + this.from);
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, com.viaversion.viaversion.libs.fastutil.objects.ObjectList, java.util.List
    public ObjectList<K> subList(int from, int to) {
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

    public ObjectImmutableList<K> clone() {
        return this;
    }

    public boolean equals(ObjectImmutableList<K> l) {
        if (l == this || this.f155a == l.f155a) {
            return true;
        }
        int s = size();
        if (s != l.size()) {
            return false;
        }
        K[] a1 = this.f155a;
        K[] a2 = l.f155a;
        return Arrays.equals(a1, a2);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, java.util.Collection, java.util.List
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || !(o instanceof List)) {
            return false;
        }
        if (o instanceof ObjectImmutableList) {
            return equals((ObjectImmutableList) ((ObjectImmutableList) o));
        }
        if (o instanceof ImmutableSubList) {
            return ((ImmutableSubList) o).equals(this);
        }
        return super.equals(o);
    }

    public int compareTo(ObjectImmutableList<? extends K> l) {
        int s1 = size();
        int s2 = l.size();
        K[] a1 = this.f155a;
        Object[] objArr = l.f155a;
        int i = 0;
        while (i < s1 && i < s2) {
            K e1 = a1[i];
            int r = ((Comparable) e1).compareTo(objArr[i]);
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

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList
    public int compareTo(List<? extends K> l) {
        if (l instanceof ObjectImmutableList) {
            return compareTo((ObjectImmutableList) ((ObjectImmutableList) l));
        }
        if (l instanceof ImmutableSubList) {
            ImmutableSubList<K> other = (ImmutableSubList) l;
            return -other.compareTo((List) this);
        }
        return super.compareTo((List) l);
    }
}
