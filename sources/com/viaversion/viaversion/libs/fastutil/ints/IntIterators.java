package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.BigArrays;
import com.viaversion.viaversion.libs.fastutil.bytes.ByteIterator;
import com.viaversion.viaversion.libs.fastutil.chars.CharIterator;
import com.viaversion.viaversion.libs.fastutil.shorts.ShortIterator;
import java.io.Serializable;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.PrimitiveIterator;
import java.util.function.Consumer;
import kotlin.jvm.internal.LongCompanionObject;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntIterators.class */
public final class IntIterators {
    public static final EmptyIterator EMPTY_ITERATOR = new EmptyIterator();

    private IntIterators() {
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntIterators$EmptyIterator.class */
    public static class EmptyIterator implements IntListIterator, Serializable, Cloneable {
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

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
        public int nextInt() {
            throw new NoSuchElementException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntBidirectionalIterator
        public int previousInt() {
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

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntBidirectionalIterator, com.viaversion.viaversion.libs.fastutil.ints.IntIterator, com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator
        public int skip(int n) {
            return 0;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntBidirectionalIterator, com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator
        public int back(int n) {
            return 0;
        }

        @Override // java.util.PrimitiveIterator.OfInt
        public void forEachRemaining(java.util.function.IntConsumer action) {
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt, java.util.Iterator
        @Deprecated
        public void forEachRemaining(Consumer<? super Integer> action) {
        }

        public Object clone() {
            return IntIterators.EMPTY_ITERATOR;
        }

        private Object readResolve() {
            return IntIterators.EMPTY_ITERATOR;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntIterators$SingletonIterator.class */
    public static class SingletonIterator implements IntListIterator {
        private final int element;
        private byte curr;

        public SingletonIterator(int element) {
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

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
        public int nextInt() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            this.curr = (byte) 1;
            return this.element;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntBidirectionalIterator
        public int previousInt() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            this.curr = (byte) 0;
            return this.element;
        }

        @Override // java.util.PrimitiveIterator.OfInt
        public void forEachRemaining(java.util.function.IntConsumer action) {
            Objects.requireNonNull(action);
            if (this.curr == 0) {
                action.accept(this.element);
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

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntBidirectionalIterator, com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator
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

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntBidirectionalIterator, com.viaversion.viaversion.libs.fastutil.ints.IntIterator, com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator
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

    public static IntListIterator singleton(int element) {
        return new SingletonIterator(element);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntIterators$ArrayIterator.class */
    private static class ArrayIterator implements IntListIterator {
        private final int[] array;
        private final int offset;
        private final int length;
        private int curr;

        public ArrayIterator(int[] array, int offset, int length) {
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

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
        public int nextInt() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int[] iArr = this.array;
            int i = this.offset;
            int i2 = this.curr;
            this.curr = i2 + 1;
            return iArr[i + i2];
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntBidirectionalIterator
        public int previousInt() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            int[] iArr = this.array;
            int i = this.offset;
            int i2 = this.curr - 1;
            this.curr = i2;
            return iArr[i + i2];
        }

        @Override // java.util.PrimitiveIterator.OfInt
        public void forEachRemaining(java.util.function.IntConsumer action) {
            Objects.requireNonNull(action);
            while (this.curr < this.length) {
                action.accept(this.array[this.offset + this.curr]);
                this.curr++;
            }
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntBidirectionalIterator, com.viaversion.viaversion.libs.fastutil.ints.IntIterator, com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator
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

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntBidirectionalIterator, com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator
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

    public static IntListIterator wrap(int[] array, int offset, int length) {
        IntArrays.ensureOffsetLength(array, offset, length);
        return new ArrayIterator(array, offset, length);
    }

    public static IntListIterator wrap(int[] array) {
        return new ArrayIterator(array, 0, array.length);
    }

    public static int unwrap(IntIterator i, int[] array, int offset, int max) {
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
            array[i3] = i.nextInt();
        }
        return (max - j) - 1;
    }

    public static int unwrap(IntIterator i, int[] array) {
        return unwrap(i, array, 0, array.length);
    }

    public static int[] unwrap(IntIterator i, int max) {
        if (max < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative");
        }
        int[] array = new int[16];
        int j = 0;
        while (true) {
            int i2 = max;
            max--;
            if (i2 == 0 || !i.hasNext()) {
                break;
            }
            if (j == array.length) {
                array = IntArrays.grow(array, j + 1);
            }
            int i3 = j;
            j++;
            array[i3] = i.nextInt();
        }
        return IntArrays.trim(array, j);
    }

    public static int[] unwrap(IntIterator i) {
        return unwrap(i, Integer.MAX_VALUE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static long unwrap(IntIterator i, int[][] array, long offset, long max) {
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
            BigArrays.set(array, (long) array, i.nextInt());
        }
        return (max - j) - 1;
    }

    public static long unwrap(IntIterator i, int[][] array) {
        return unwrap(i, array, 0L, BigArrays.length(array));
    }

    public static int unwrap(IntIterator i, IntCollection c, int max) {
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
            c.add(i.nextInt());
        }
        return (max - j) - 1;
    }

    public static int[][] unwrapBig(IntIterator i, long max) {
        if (max < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative");
        }
        int[][] array = IntBigArrays.newBigArray(16L);
        long j = 0;
        while (true) {
            long j2 = max;
            max = j2 - 1;
            if (j2 == 0 || !i.hasNext()) {
                break;
            }
            if (j == BigArrays.length(array)) {
                array = BigArrays.grow(array, j + 1);
            }
            long j3 = j;
            j = j3 + 1;
            BigArrays.set(array, j3, i.nextInt());
        }
        return BigArrays.trim(array, j);
    }

    public static int[][] unwrapBig(IntIterator i) {
        return unwrapBig(i, LongCompanionObject.MAX_VALUE);
    }

    public static long unwrap(IntIterator i, IntCollection c) {
        long j = 0;
        while (true) {
            long n = j;
            if (i.hasNext()) {
                c.add(i.nextInt());
                j = n + 1;
            } else {
                return n;
            }
        }
    }

    public static int pour(IntIterator i, IntCollection s, int max) {
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
            s.add(i.nextInt());
        }
        return (max - j) - 1;
    }

    public static int pour(IntIterator i, IntCollection s) {
        return pour(i, s, Integer.MAX_VALUE);
    }

    public static IntList pour(IntIterator i, int max) {
        IntArrayList l = new IntArrayList();
        pour(i, l, max);
        l.trim();
        return l;
    }

    public static IntList pour(IntIterator i) {
        return pour(i, Integer.MAX_VALUE);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntIterators$IteratorWrapper.class */
    public static class IteratorWrapper implements IntIterator {

        /* renamed from: i */
        final Iterator<Integer> f108i;

        public IteratorWrapper(Iterator<Integer> i) {
            this.f108i = i;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.f108i.hasNext();
        }

        @Override // java.util.Iterator
        public void remove() {
            this.f108i.remove();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
        public int nextInt() {
            return this.f108i.next().intValue();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterator
        public void forEachRemaining(IntConsumer action) {
            this.f108i.forEachRemaining(action);
        }

        @Override // java.util.PrimitiveIterator.OfInt
        public void forEachRemaining(java.util.function.IntConsumer action) {
            Consumer<? super Integer> consumer;
            Objects.requireNonNull(action);
            Iterator<Integer> it = this.f108i;
            if (action instanceof Consumer) {
                consumer = (Consumer) action;
            } else {
                Objects.requireNonNull(action);
                consumer = (v1) -> {
                    r1.accept(v1);
                };
            }
            it.forEachRemaining(consumer);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt, java.util.Iterator
        @Deprecated
        public void forEachRemaining(Consumer<? super Integer> action) {
            this.f108i.forEachRemaining(action);
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntIterators$PrimitiveIteratorWrapper.class */
    public static class PrimitiveIteratorWrapper implements IntIterator {

        /* renamed from: i */
        final PrimitiveIterator.OfInt f110i;

        public PrimitiveIteratorWrapper(PrimitiveIterator.OfInt i) {
            this.f110i = i;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.f110i.hasNext();
        }

        @Override // java.util.Iterator
        public void remove() {
            this.f110i.remove();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
        public int nextInt() {
            return this.f110i.nextInt();
        }

        @Override // java.util.PrimitiveIterator.OfInt
        public void forEachRemaining(java.util.function.IntConsumer action) {
            this.f110i.forEachRemaining(action);
        }
    }

    public static IntIterator asIntIterator(Iterator i) {
        if (i instanceof IntIterator) {
            return (IntIterator) i;
        }
        if (i instanceof PrimitiveIterator.OfInt) {
            return new PrimitiveIteratorWrapper((PrimitiveIterator.OfInt) i);
        }
        return new IteratorWrapper(i);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntIterators$ListIteratorWrapper.class */
    private static class ListIteratorWrapper implements IntListIterator {

        /* renamed from: i */
        final ListIterator<Integer> f109i;

        public ListIteratorWrapper(ListIterator<Integer> i) {
            this.f109i = i;
        }

        @Override // java.util.Iterator, java.util.ListIterator
        public boolean hasNext() {
            return this.f109i.hasNext();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.BidirectionalIterator
        public boolean hasPrevious() {
            return this.f109i.hasPrevious();
        }

        @Override // java.util.ListIterator
        public int nextIndex() {
            return this.f109i.nextIndex();
        }

        @Override // java.util.ListIterator
        public int previousIndex() {
            return this.f109i.previousIndex();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntListIterator
        public void set(int k) {
            this.f109i.set(Integer.valueOf(k));
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntListIterator
        public void add(int k) {
            this.f109i.add(Integer.valueOf(k));
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntListIterator, java.util.Iterator, java.util.ListIterator
        public void remove() {
            this.f109i.remove();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
        public int nextInt() {
            return this.f109i.next().intValue();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntBidirectionalIterator
        public int previousInt() {
            return this.f109i.previous().intValue();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterator
        public void forEachRemaining(IntConsumer action) {
            this.f109i.forEachRemaining(action);
        }

        @Override // java.util.PrimitiveIterator.OfInt
        public void forEachRemaining(java.util.function.IntConsumer action) {
            Consumer<? super Integer> consumer;
            Objects.requireNonNull(action);
            ListIterator<Integer> listIterator = this.f109i;
            if (action instanceof Consumer) {
                consumer = (Consumer) action;
            } else {
                Objects.requireNonNull(action);
                consumer = (v1) -> {
                    r1.accept(v1);
                };
            }
            listIterator.forEachRemaining(consumer);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt, java.util.Iterator
        @Deprecated
        public void forEachRemaining(Consumer<? super Integer> action) {
            this.f109i.forEachRemaining(action);
        }
    }

    public static IntListIterator asIntIterator(ListIterator i) {
        if (i instanceof IntListIterator) {
            return (IntListIterator) i;
        }
        return new ListIteratorWrapper(i);
    }

    public static boolean any(IntIterator iterator, java.util.function.IntPredicate predicate) {
        return indexOf(iterator, predicate) != -1;
    }

    public static boolean all(IntIterator iterator, java.util.function.IntPredicate predicate) {
        Objects.requireNonNull(predicate);
        while (iterator.hasNext()) {
            if (!predicate.test(iterator.nextInt())) {
                return false;
            }
        }
        return true;
    }

    public static int indexOf(IntIterator iterator, java.util.function.IntPredicate predicate) {
        Objects.requireNonNull(predicate);
        int i = 0;
        while (iterator.hasNext()) {
            if (!predicate.test(iterator.nextInt())) {
                i++;
            } else {
                return i;
            }
        }
        return -1;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntIterators$AbstractIndexBasedIterator.class */
    public static abstract class AbstractIndexBasedIterator extends AbstractIntIterator {
        protected final int minPos;
        protected int pos;
        protected int lastReturned;

        protected abstract int get(int i);

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

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
        public int nextInt() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int i = this.pos;
            this.pos = i + 1;
            this.lastReturned = i;
            return get(i);
        }

        @Override // java.util.Iterator, com.viaversion.viaversion.libs.fastutil.ints.IntListIterator, java.util.ListIterator
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

        @Override // java.util.PrimitiveIterator.OfInt
        public void forEachRemaining(java.util.function.IntConsumer action) {
            while (this.pos < getMaxPos()) {
                int i = this.pos;
                this.pos = i + 1;
                this.lastReturned = i;
                action.accept(get(i));
            }
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterator, com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator
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

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntIterators$AbstractIndexBasedListIterator.class */
    public static abstract class AbstractIndexBasedListIterator extends AbstractIndexBasedIterator implements IntListIterator {
        protected abstract void add(int i, int i2);

        protected abstract void set(int i, int i2);

        public AbstractIndexBasedListIterator(int minPos, int initialPos) {
            super(minPos, initialPos);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.BidirectionalIterator
        public boolean hasPrevious() {
            return this.pos > this.minPos;
        }

        public int previousInt() {
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

        public void add(int k) {
            int i = this.pos;
            this.pos = i + 1;
            add(i, k);
            this.lastReturned = -1;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntListIterator
        public void set(int k) {
            if (this.lastReturned == -1) {
                throw new IllegalStateException();
            }
            set(this.lastReturned, k);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntBidirectionalIterator, com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator
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

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntIterators$IntervalIterator.class */
    public static class IntervalIterator implements IntListIterator {
        private final int from;

        /* renamed from: to */
        private final int f106to;
        int curr;

        public IntervalIterator(int from, int to) {
            this.curr = from;
            this.from = from;
            this.f106to = to;
        }

        @Override // java.util.Iterator, java.util.ListIterator
        public boolean hasNext() {
            return this.curr < this.f106to;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.BidirectionalIterator
        public boolean hasPrevious() {
            return this.curr > this.from;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
        public int nextInt() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int i = this.curr;
            this.curr = i + 1;
            return i;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntBidirectionalIterator
        public int previousInt() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            int i = this.curr - 1;
            this.curr = i;
            return i;
        }

        @Override // java.util.PrimitiveIterator.OfInt
        public void forEachRemaining(java.util.function.IntConsumer action) {
            Objects.requireNonNull(action);
            while (this.curr < this.f106to) {
                action.accept(this.curr);
                this.curr++;
            }
        }

        @Override // java.util.ListIterator
        public int nextIndex() {
            return this.curr - this.from;
        }

        @Override // java.util.ListIterator
        public int previousIndex() {
            return (this.curr - this.from) - 1;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntBidirectionalIterator, com.viaversion.viaversion.libs.fastutil.ints.IntIterator, com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator
        public int skip(int n) {
            if (n < 0) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            }
            if (this.curr + n <= this.f106to) {
                this.curr += n;
                return n;
            }
            int n2 = this.f106to - this.curr;
            this.curr = this.f106to;
            return n2;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntBidirectionalIterator, com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator
        public int back(int n) {
            if (this.curr - n >= this.from) {
                this.curr -= n;
                return n;
            }
            int n2 = this.curr - this.from;
            this.curr = this.from;
            return n2;
        }
    }

    public static IntListIterator fromTo(int from, int to) {
        return new IntervalIterator(from, to);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntIterators$IteratorConcatenator.class */
    public static class IteratorConcatenator implements IntIterator {

        /* renamed from: a */
        final IntIterator[] f107a;
        int offset;
        int length;
        int lastOffset = -1;

        public IteratorConcatenator(IntIterator[] a, int offset, int length) {
            this.f107a = a;
            this.offset = offset;
            this.length = length;
            advance();
        }

        private void advance() {
            while (this.length != 0 && !this.f107a[this.offset].hasNext()) {
                this.length--;
                this.offset++;
            }
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.length > 0;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
        public int nextInt() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            IntIterator[] intIteratorArr = this.f107a;
            int i = this.offset;
            this.lastOffset = i;
            int next = intIteratorArr[i].nextInt();
            advance();
            return next;
        }

        @Override // java.util.PrimitiveIterator.OfInt
        public void forEachRemaining(java.util.function.IntConsumer action) {
            while (this.length > 0) {
                IntIterator[] intIteratorArr = this.f107a;
                int i = this.offset;
                this.lastOffset = i;
                intIteratorArr[i].forEachRemaining(action);
                advance();
            }
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt, java.util.Iterator
        @Deprecated
        public void forEachRemaining(Consumer<? super Integer> action) {
            while (this.length > 0) {
                IntIterator[] intIteratorArr = this.f107a;
                int i = this.offset;
                this.lastOffset = i;
                intIteratorArr[i].forEachRemaining(action);
                advance();
            }
        }

        @Override // java.util.Iterator
        public void remove() {
            if (this.lastOffset == -1) {
                throw new IllegalStateException();
            }
            this.f107a[this.lastOffset].remove();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterator, com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator
        public int skip(int n) {
            if (n < 0) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            }
            this.lastOffset = -1;
            int skipped = 0;
            while (skipped < n && this.length != 0) {
                skipped += this.f107a[this.offset].skip(n - skipped);
                if (this.f107a[this.offset].hasNext()) {
                    break;
                }
                this.length--;
                this.offset++;
            }
            return skipped;
        }
    }

    public static IntIterator concat(IntIterator... a) {
        return concat(a, 0, a.length);
    }

    public static IntIterator concat(IntIterator[] a, int offset, int length) {
        return new IteratorConcatenator(a, offset, length);
    }

    public static IntIterator unmodifiable(IntIterator i) {
        return new UnmodifiableIterator(i);
    }

    public static IntBidirectionalIterator unmodifiable(IntBidirectionalIterator i) {
        return new UnmodifiableBidirectionalIterator(i);
    }

    public static IntListIterator unmodifiable(IntListIterator i) {
        return new UnmodifiableListIterator(i);
    }

    public static IntIterator wrap(ByteIterator iterator) {
        return new ByteIteratorWrapper(iterator);
    }

    public static IntIterator wrap(ShortIterator iterator) {
        return new ShortIteratorWrapper(iterator);
    }

    public static IntIterator wrap(CharIterator iterator) {
        return new CharIteratorWrapper(iterator);
    }
}
