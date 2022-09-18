package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.BigArrays;
import java.io.Serializable;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import kotlin.jvm.internal.LongCompanionObject;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectIterators.class */
public final class ObjectIterators {
    public static final EmptyIterator EMPTY_ITERATOR = new EmptyIterator();

    private ObjectIterators() {
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectIterators$EmptyIterator.class */
    public static class EmptyIterator<K> implements ObjectListIterator<K>, Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyIterator() {
        }

        @Override // java.util.Iterator, java.util.ListIterator
        public boolean hasNext() {
            return false;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.BidirectionalIterator
        public boolean hasPrevious() {
            return false;
        }

        @Override // java.util.Iterator, java.util.ListIterator
        public K next() {
            throw new NoSuchElementException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.BidirectionalIterator, java.util.ListIterator
        public K previous() {
            throw new NoSuchElementException();
        }

        @Override // java.util.ListIterator
        public int nextIndex() {
            return 0;
        }

        @Override // java.util.ListIterator
        public int previousIndex() {
            return -1;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator
        public int skip(int n) {
            return 0;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator
        public int back(int n) {
            return 0;
        }

        @Override // java.util.Iterator
        public void forEachRemaining(Consumer<? super K> action) {
        }

        public Object clone() {
            return ObjectIterators.EMPTY_ITERATOR;
        }

        private Object readResolve() {
            return ObjectIterators.EMPTY_ITERATOR;
        }
    }

    public static <K> ObjectIterator<K> emptyIterator() {
        return EMPTY_ITERATOR;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectIterators$SingletonIterator.class */
    public static class SingletonIterator<K> implements ObjectListIterator<K> {
        private final K element;
        private byte curr;

        public SingletonIterator(K element) {
            this.element = element;
        }

        @Override // java.util.Iterator, java.util.ListIterator
        public boolean hasNext() {
            return this.curr == 0;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.BidirectionalIterator
        public boolean hasPrevious() {
            return this.curr == 1;
        }

        @Override // java.util.Iterator, java.util.ListIterator
        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            this.curr = (byte) 1;
            return this.element;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.BidirectionalIterator, java.util.ListIterator
        public K previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            this.curr = (byte) 0;
            return this.element;
        }

        @Override // java.util.Iterator
        public void forEachRemaining(Consumer<? super K> action) {
            Objects.requireNonNull(action);
            if (this.curr == 0) {
                action.accept((K) this.element);
                this.curr = (byte) 1;
            }
        }

        @Override // java.util.ListIterator
        public int nextIndex() {
            return this.curr;
        }

        @Override // java.util.ListIterator
        public int previousIndex() {
            return this.curr - 1;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator
        public int back(int n) {
            if (n < 0) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            }
            if (n == 0 || this.curr < 1) {
                return 0;
            }
            this.curr = (byte) 1;
            return 1;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator
        public int skip(int n) {
            if (n < 0) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            }
            if (n == 0 || this.curr > 0) {
                return 0;
            }
            this.curr = (byte) 0;
            return 1;
        }
    }

    public static <K> ObjectListIterator<K> singleton(K element) {
        return new SingletonIterator(element);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectIterators$ArrayIterator.class */
    private static class ArrayIterator<K> implements ObjectListIterator<K> {
        private final K[] array;
        private final int offset;
        private final int length;
        private int curr;

        public ArrayIterator(K[] array, int offset, int length) {
            this.array = array;
            this.offset = offset;
            this.length = length;
        }

        @Override // java.util.Iterator, java.util.ListIterator
        public boolean hasNext() {
            return this.curr < this.length;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.BidirectionalIterator
        public boolean hasPrevious() {
            return this.curr > 0;
        }

        @Override // java.util.Iterator, java.util.ListIterator
        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            K[] kArr = this.array;
            int i = this.offset;
            int i2 = this.curr;
            this.curr = i2 + 1;
            return kArr[i + i2];
        }

        @Override // com.viaversion.viaversion.libs.fastutil.BidirectionalIterator, java.util.ListIterator
        public K previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            K[] kArr = this.array;
            int i = this.offset;
            int i2 = this.curr - 1;
            this.curr = i2;
            return kArr[i + i2];
        }

        @Override // java.util.Iterator
        public void forEachRemaining(Consumer<? super K> action) {
            Objects.requireNonNull(action);
            while (this.curr < this.length) {
                action.accept((Object) this.array[this.offset + this.curr]);
                this.curr++;
            }
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator
        public int skip(int n) {
            if (n < 0) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            }
            if (n <= this.length - this.curr) {
                this.curr += n;
                return n;
            }
            int n2 = this.length - this.curr;
            this.curr = this.length;
            return n2;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator
        public int back(int n) {
            if (n < 0) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            }
            if (n <= this.curr) {
                this.curr -= n;
                return n;
            }
            int n2 = this.curr;
            this.curr = 0;
            return n2;
        }

        @Override // java.util.ListIterator
        public int nextIndex() {
            return this.curr;
        }

        @Override // java.util.ListIterator
        public int previousIndex() {
            return this.curr - 1;
        }
    }

    public static <K> ObjectListIterator<K> wrap(K[] array, int offset, int length) {
        ObjectArrays.ensureOffsetLength(array, offset, length);
        return new ArrayIterator(array, offset, length);
    }

    public static <K> ObjectListIterator<K> wrap(K[] array) {
        return new ArrayIterator(array, 0, array.length);
    }

    public static <K> int unwrap(Iterator<? extends K> i, K[] array, int offset, int max) {
        if (max < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative");
        }
        if (offset < 0 || offset + max > array.length) {
            throw new IllegalArgumentException();
        }
        int j = max;
        while (true) {
            int i2 = j;
            j--;
            if (i2 == 0 || !i.hasNext()) {
                break;
            }
            int i3 = offset;
            offset++;
            array[i3] = i.next();
        }
        return (max - j) - 1;
    }

    public static <K> int unwrap(Iterator<? extends K> i, K[] array) {
        return unwrap(i, array, 0, array.length);
    }

    public static <K> K[] unwrap(Iterator<? extends K> i, int max) {
        if (max < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative");
        }
        Object[] objArr = new Object[16];
        int j = 0;
        while (true) {
            int i2 = max;
            max--;
            if (i2 == 0 || !i.hasNext()) {
                break;
            }
            if (j == objArr.length) {
                objArr = ObjectArrays.grow(objArr, j + 1);
            }
            int i3 = j;
            j++;
            objArr[i3] = i.next();
        }
        return (K[]) ObjectArrays.trim(objArr, j);
    }

    public static <K> K[] unwrap(Iterator<? extends K> i) {
        return (K[]) unwrap(i, Integer.MAX_VALUE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <K> long unwrap(Iterator<? extends K> i, K[][] array, long offset, long max) {
        if (max < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative");
        }
        if (offset < 0 || offset + max > BigArrays.length(array)) {
            throw new IllegalArgumentException();
        }
        long j = max;
        while (true) {
            long j2 = j;
            j = j2 - 1;
            if (j2 == 0 || !i.hasNext()) {
                break;
            }
            offset++;
            BigArrays.set((Object[][]) array, (long) array, (Object) i.next());
        }
        return (max - j) - 1;
    }

    public static <K> long unwrap(Iterator<? extends K> i, K[][] array) {
        return unwrap(i, array, 0L, BigArrays.length(array));
    }

    public static <K> int unwrap(Iterator<K> i, ObjectCollection<? super K> c, int max) {
        if (max < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative");
        }
        int j = max;
        while (true) {
            int i2 = j;
            j--;
            if (i2 == 0 || !i.hasNext()) {
                break;
            }
            c.add(i.next());
        }
        return (max - j) - 1;
    }

    public static <K> K[][] unwrapBig(Iterator<? extends K> i, long max) {
        if (max < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative");
        }
        Object[][] newBigArray = ObjectBigArrays.newBigArray(16L);
        long j = 0;
        while (true) {
            long j2 = max;
            max = j2 - 1;
            if (j2 == 0 || !i.hasNext()) {
                break;
            }
            if (j == BigArrays.length(newBigArray)) {
                newBigArray = BigArrays.grow(newBigArray, j + 1);
            }
            long j3 = j;
            j = j3 + 1;
            BigArrays.set(newBigArray, j3, i.next());
        }
        return (K[][]) BigArrays.trim(newBigArray, j);
    }

    public static <K> K[][] unwrapBig(Iterator<? extends K> i) {
        return (K[][]) unwrapBig(i, LongCompanionObject.MAX_VALUE);
    }

    public static <K> long unwrap(Iterator<K> i, ObjectCollection<? super K> c) {
        long j = 0;
        while (true) {
            long n = j;
            if (i.hasNext()) {
                c.add(i.next());
                j = n + 1;
            } else {
                return n;
            }
        }
    }

    public static <K> int pour(Iterator<K> i, ObjectCollection<? super K> s, int max) {
        if (max < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative");
        }
        int j = max;
        while (true) {
            int i2 = j;
            j--;
            if (i2 == 0 || !i.hasNext()) {
                break;
            }
            s.add(i.next());
        }
        return (max - j) - 1;
    }

    public static <K> int pour(Iterator<K> i, ObjectCollection<? super K> s) {
        return pour(i, s, Integer.MAX_VALUE);
    }

    public static <K> ObjectList<K> pour(Iterator<K> i, int max) {
        ObjectArrayList<K> l = new ObjectArrayList<>();
        pour(i, l, max);
        l.trim();
        return l;
    }

    public static <K> ObjectList<K> pour(Iterator<K> i) {
        return pour(i, Integer.MAX_VALUE);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectIterators$IteratorWrapper.class */
    private static class IteratorWrapper<K> implements ObjectIterator<K> {

        /* renamed from: i */
        final Iterator<K> f159i;

        public IteratorWrapper(Iterator<K> i) {
            this.f159i = i;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.f159i.hasNext();
        }

        @Override // java.util.Iterator
        public void remove() {
            this.f159i.remove();
        }

        @Override // java.util.Iterator
        public K next() {
            return this.f159i.next();
        }

        @Override // java.util.Iterator
        public void forEachRemaining(Consumer<? super K> action) {
            this.f159i.forEachRemaining(action);
        }
    }

    public static <K> ObjectIterator<K> asObjectIterator(Iterator<K> i) {
        if (i instanceof ObjectIterator) {
            return (ObjectIterator) i;
        }
        return new IteratorWrapper(i);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectIterators$ListIteratorWrapper.class */
    private static class ListIteratorWrapper<K> implements ObjectListIterator<K> {

        /* renamed from: i */
        final ListIterator<K> f160i;

        public ListIteratorWrapper(ListIterator<K> i) {
            this.f160i = i;
        }

        @Override // java.util.Iterator, java.util.ListIterator
        public boolean hasNext() {
            return this.f160i.hasNext();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.BidirectionalIterator
        public boolean hasPrevious() {
            return this.f160i.hasPrevious();
        }

        @Override // java.util.ListIterator
        public int nextIndex() {
            return this.f160i.nextIndex();
        }

        @Override // java.util.ListIterator
        public int previousIndex() {
            return this.f160i.previousIndex();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectListIterator, java.util.ListIterator
        public void set(K k) {
            this.f160i.set(k);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectListIterator, java.util.ListIterator
        public void add(K k) {
            this.f160i.add(k);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectListIterator, java.util.Iterator, java.util.ListIterator
        public void remove() {
            this.f160i.remove();
        }

        @Override // java.util.Iterator, java.util.ListIterator
        public K next() {
            return this.f160i.next();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.BidirectionalIterator, java.util.ListIterator
        public K previous() {
            return this.f160i.previous();
        }

        @Override // java.util.Iterator
        public void forEachRemaining(Consumer<? super K> action) {
            this.f160i.forEachRemaining(action);
        }
    }

    public static <K> ObjectListIterator<K> asObjectIterator(ListIterator<K> i) {
        if (i instanceof ObjectListIterator) {
            return (ObjectListIterator) i;
        }
        return new ListIteratorWrapper(i);
    }

    public static <K> boolean any(Iterator<K> iterator, Predicate<? super K> predicate) {
        return indexOf(iterator, predicate) != -1;
    }

    public static <K> boolean all(Iterator<K> iterator, Predicate<? super K> predicate) {
        Objects.requireNonNull(predicate);
        while (iterator.hasNext()) {
            if (!predicate.test(iterator.next())) {
                return false;
            }
        }
        return true;
    }

    public static <K> int indexOf(Iterator<K> iterator, Predicate<? super K> predicate) {
        Objects.requireNonNull(predicate);
        int i = 0;
        while (iterator.hasNext()) {
            if (!predicate.test(iterator.next())) {
                i++;
            } else {
                return i;
            }
        }
        return -1;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectIterators$AbstractIndexBasedIterator.class */
    public static abstract class AbstractIndexBasedIterator<K> extends AbstractObjectIterator<K> {
        protected final int minPos;
        protected int pos;
        protected int lastReturned;

        protected abstract K get(int i);

        protected abstract void remove(int i);

        protected abstract int getMaxPos();

        protected AbstractIndexBasedIterator(int minPos, int initialPos) {
            this.minPos = minPos;
            this.pos = initialPos;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.pos < getMaxPos();
        }

        @Override // java.util.Iterator, java.util.ListIterator
        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int i = this.pos;
            this.pos = i + 1;
            this.lastReturned = i;
            return get(i);
        }

        @Override // java.util.Iterator, com.viaversion.viaversion.libs.fastutil.objects.ObjectListIterator, java.util.ListIterator
        public void remove() {
            if (this.lastReturned == -1) {
                throw new IllegalStateException();
            }
            remove(this.lastReturned);
            if (this.lastReturned < this.pos) {
                this.pos--;
            }
            this.lastReturned = -1;
        }

        @Override // java.util.Iterator
        public void forEachRemaining(Consumer<? super K> action) {
            while (this.pos < getMaxPos()) {
                int i = this.pos;
                this.pos = i + 1;
                this.lastReturned = i;
                action.accept(get(i));
            }
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator
        public int skip(int n) {
            if (n < 0) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            }
            int max = getMaxPos();
            int remaining = max - this.pos;
            if (n < remaining) {
                this.pos += n;
            } else {
                n = remaining;
                this.pos = max;
            }
            this.lastReturned = this.pos - 1;
            return n;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectIterators$AbstractIndexBasedListIterator.class */
    public static abstract class AbstractIndexBasedListIterator<K> extends AbstractIndexBasedIterator<K> implements ObjectListIterator<K> {
        protected abstract void add(int i, K k);

        protected abstract void set(int i, K k);

        public AbstractIndexBasedListIterator(int minPos, int initialPos) {
            super(minPos, initialPos);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.BidirectionalIterator
        public boolean hasPrevious() {
            return this.pos > this.minPos;
        }

        public K previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            int i = this.pos - 1;
            this.pos = i;
            this.lastReturned = i;
            return get(i);
        }

        @Override // java.util.ListIterator
        public int nextIndex() {
            return this.pos;
        }

        @Override // java.util.ListIterator
        public int previousIndex() {
            return this.pos - 1;
        }

        public void add(K k) {
            int i = this.pos;
            this.pos = i + 1;
            add(i, k);
            this.lastReturned = -1;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectListIterator, java.util.ListIterator
        public void set(K k) {
            if (this.lastReturned == -1) {
                throw new IllegalStateException();
            }
            set(this.lastReturned, k);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator
        public int back(int n) {
            if (n < 0) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            }
            int remaining = this.pos - this.minPos;
            if (n < remaining) {
                this.pos -= n;
            } else {
                n = remaining;
                this.pos = this.minPos;
            }
            this.lastReturned = this.pos;
            return n;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectIterators$IteratorConcatenator.class */
    public static class IteratorConcatenator<K> implements ObjectIterator<K> {

        /* renamed from: a */
        final ObjectIterator<? extends K>[] f158a;
        int offset;
        int length;
        int lastOffset = -1;

        public IteratorConcatenator(ObjectIterator<? extends K>[] a, int offset, int length) {
            this.f158a = a;
            this.offset = offset;
            this.length = length;
            advance();
        }

        private void advance() {
            while (this.length != 0 && !this.f158a[this.offset].hasNext()) {
                this.length--;
                this.offset++;
            }
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.length > 0;
        }

        @Override // java.util.Iterator
        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            ObjectIterator<? extends K>[] objectIteratorArr = this.f158a;
            int i = this.offset;
            this.lastOffset = i;
            K next = objectIteratorArr[i].next();
            advance();
            return next;
        }

        @Override // java.util.Iterator
        public void forEachRemaining(Consumer<? super K> action) {
            while (this.length > 0) {
                ObjectIterator<? extends K>[] objectIteratorArr = this.f158a;
                int i = this.offset;
                this.lastOffset = i;
                objectIteratorArr[i].forEachRemaining(action);
                advance();
            }
        }

        @Override // java.util.Iterator
        public void remove() {
            if (this.lastOffset == -1) {
                throw new IllegalStateException();
            }
            this.f158a[this.lastOffset].remove();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator
        public int skip(int n) {
            if (n < 0) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            }
            this.lastOffset = -1;
            int skipped = 0;
            while (skipped < n && this.length != 0) {
                skipped += this.f158a[this.offset].skip(n - skipped);
                if (this.f158a[this.offset].hasNext()) {
                    break;
                }
                this.length--;
                this.offset++;
            }
            return skipped;
        }
    }

    @SafeVarargs
    public static <K> ObjectIterator<K> concat(ObjectIterator<? extends K>... a) {
        return concat(a, 0, a.length);
    }

    public static <K> ObjectIterator<K> concat(ObjectIterator<? extends K>[] a, int offset, int length) {
        return new IteratorConcatenator(a, offset, length);
    }

    public static <K> ObjectIterator<K> unmodifiable(ObjectIterator<? extends K> i) {
        return new UnmodifiableIterator(i);
    }

    public static <K> ObjectBidirectionalIterator<K> unmodifiable(ObjectBidirectionalIterator<? extends K> i) {
        return new UnmodifiableBidirectionalIterator(i);
    }

    public static <K> ObjectListIterator<K> unmodifiable(ObjectListIterator<? extends K> i) {
        return new UnmodifiableListIterator(i);
    }
}
