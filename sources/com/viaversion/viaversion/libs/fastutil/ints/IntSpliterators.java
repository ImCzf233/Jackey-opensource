package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.SafeMath;
import com.viaversion.viaversion.libs.fastutil.bytes.ByteSpliterator;
import com.viaversion.viaversion.libs.fastutil.chars.CharSpliterator;
import com.viaversion.viaversion.libs.fastutil.shorts.ShortSpliterator;
import java.io.Serializable;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;
import kotlin.jvm.internal.LongCompanionObject;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntSpliterators.class */
public final class IntSpliterators {
    static final int BASE_SPLITERATOR_CHARACTERISTICS = 256;
    public static final int COLLECTION_SPLITERATOR_CHARACTERISTICS = 320;
    public static final int LIST_SPLITERATOR_CHARACTERISTICS = 16720;
    public static final int SET_SPLITERATOR_CHARACTERISTICS = 321;
    private static final int SORTED_CHARACTERISTICS = 20;
    public static final int SORTED_SET_SPLITERATOR_CHARACTERISTICS = 341;
    public static final EmptySpliterator EMPTY_SPLITERATOR = new EmptySpliterator();

    private IntSpliterators() {
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntSpliterators$EmptySpliterator.class */
    public static class EmptySpliterator implements IntSpliterator, Serializable, Cloneable {
        private static final long serialVersionUID = 8379247926738230492L;
        private static final int CHARACTERISTICS = 16448;

        protected EmptySpliterator() {
        }

        @Override // java.util.Spliterator.OfInt
        public boolean tryAdvance(java.util.function.IntConsumer action) {
            return false;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator, java.util.Spliterator.OfInt, java.util.Spliterator
        @Deprecated
        public boolean tryAdvance(Consumer<? super Integer> action) {
            return false;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator, java.util.Spliterator.OfInt, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public IntSpliterator trySplit() {
            return null;
        }

        @Override // java.util.Spliterator
        public long estimateSize() {
            return 0L;
        }

        @Override // java.util.Spliterator
        public int characteristics() {
            return CHARACTERISTICS;
        }

        @Override // java.util.Spliterator.OfInt
        public void forEachRemaining(java.util.function.IntConsumer action) {
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator, java.util.Spliterator.OfInt, java.util.Spliterator
        @Deprecated
        public void forEachRemaining(Consumer<? super Integer> action) {
        }

        public Object clone() {
            return IntSpliterators.EMPTY_SPLITERATOR;
        }

        private Object readResolve() {
            return IntSpliterators.EMPTY_SPLITERATOR;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntSpliterators$SingletonSpliterator.class */
    public static class SingletonSpliterator implements IntSpliterator {
        private final int element;
        private final IntComparator comparator;
        private boolean consumed;
        private static final int CHARACTERISTICS = 17749;

        public SingletonSpliterator(int element) {
            this(element, null);
        }

        public SingletonSpliterator(int element, IntComparator comparator) {
            this.consumed = false;
            this.element = element;
            this.comparator = comparator;
        }

        @Override // java.util.Spliterator.OfInt
        public boolean tryAdvance(java.util.function.IntConsumer action) {
            Objects.requireNonNull(action);
            if (this.consumed) {
                return false;
            }
            this.consumed = true;
            action.accept(this.element);
            return true;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator, java.util.Spliterator.OfInt, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public IntSpliterator trySplit() {
            return null;
        }

        @Override // java.util.Spliterator
        public long estimateSize() {
            return this.consumed ? 0L : 1L;
        }

        @Override // java.util.Spliterator
        public int characteristics() {
            return CHARACTERISTICS;
        }

        @Override // java.util.Spliterator.OfInt
        public void forEachRemaining(java.util.function.IntConsumer action) {
            Objects.requireNonNull(action);
            if (!this.consumed) {
                this.consumed = true;
                action.accept(this.element);
            }
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator, java.util.Spliterator
        public IntComparator getComparator() {
            return this.comparator;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator
        public long skip(long n) {
            if (n < 0) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            }
            if (n == 0 || this.consumed) {
                return 0L;
            }
            this.consumed = true;
            return 1L;
        }
    }

    public static IntSpliterator singleton(int element) {
        return new SingletonSpliterator(element);
    }

    public static IntSpliterator singleton(int element, IntComparator comparator) {
        return new SingletonSpliterator(element, comparator);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntSpliterators$ArraySpliterator.class */
    public static class ArraySpliterator implements IntSpliterator {
        private static final int BASE_CHARACTERISTICS = 16720;
        final int[] array;
        private final int offset;
        private int length;
        private int curr;
        final int characteristics;

        public ArraySpliterator(int[] array, int offset, int length, int additionalCharacteristics) {
            this.array = array;
            this.offset = offset;
            this.length = length;
            this.characteristics = 16720 | additionalCharacteristics;
        }

        @Override // java.util.Spliterator.OfInt
        public boolean tryAdvance(java.util.function.IntConsumer action) {
            if (this.curr >= this.length) {
                return false;
            }
            Objects.requireNonNull(action);
            int[] iArr = this.array;
            int i = this.offset;
            int i2 = this.curr;
            this.curr = i2 + 1;
            action.accept(iArr[i + i2]);
            return true;
        }

        @Override // java.util.Spliterator
        public long estimateSize() {
            return this.length - this.curr;
        }

        @Override // java.util.Spliterator
        public int characteristics() {
            return this.characteristics;
        }

        protected ArraySpliterator makeForSplit(int newOffset, int newLength) {
            return new ArraySpliterator(this.array, newOffset, newLength, this.characteristics);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator, java.util.Spliterator.OfInt, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public IntSpliterator trySplit() {
            int retLength = (this.length - this.curr) >> 1;
            if (retLength <= 1) {
                return null;
            }
            int myNewCurr = this.curr + retLength;
            int retOffset = this.offset + this.curr;
            this.curr = myNewCurr;
            return makeForSplit(retOffset, retLength);
        }

        @Override // java.util.Spliterator.OfInt
        public void forEachRemaining(java.util.function.IntConsumer action) {
            Objects.requireNonNull(action);
            while (this.curr < this.length) {
                action.accept(this.array[this.offset + this.curr]);
                this.curr++;
            }
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator
        public long skip(long n) {
            if (n < 0) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            }
            if (this.curr >= this.length) {
                return 0L;
            }
            int remaining = this.length - this.curr;
            if (n < remaining) {
                this.curr = SafeMath.safeLongToInt(this.curr + n);
                return n;
            }
            long n2 = remaining;
            this.curr = this.length;
            return n2;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntSpliterators$ArraySpliteratorWithComparator.class */
    public static class ArraySpliteratorWithComparator extends ArraySpliterator {
        private final IntComparator comparator;

        public ArraySpliteratorWithComparator(int[] array, int offset, int length, int additionalCharacteristics, IntComparator comparator) {
            super(array, offset, length, additionalCharacteristics | 20);
            this.comparator = comparator;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators.ArraySpliterator
        public ArraySpliteratorWithComparator makeForSplit(int newOffset, int newLength) {
            return new ArraySpliteratorWithComparator(this.array, newOffset, newLength, this.characteristics, this.comparator);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator, java.util.Spliterator
        public IntComparator getComparator() {
            return this.comparator;
        }
    }

    public static IntSpliterator wrap(int[] array, int offset, int length) {
        IntArrays.ensureOffsetLength(array, offset, length);
        return new ArraySpliterator(array, offset, length, 0);
    }

    public static IntSpliterator wrap(int[] array) {
        return new ArraySpliterator(array, 0, array.length, 0);
    }

    public static IntSpliterator wrap(int[] array, int offset, int length, int additionalCharacteristics) {
        IntArrays.ensureOffsetLength(array, offset, length);
        return new ArraySpliterator(array, offset, length, additionalCharacteristics);
    }

    public static IntSpliterator wrapPreSorted(int[] array, int offset, int length, int additionalCharacteristics, IntComparator comparator) {
        IntArrays.ensureOffsetLength(array, offset, length);
        return new ArraySpliteratorWithComparator(array, offset, length, additionalCharacteristics, comparator);
    }

    public static IntSpliterator wrapPreSorted(int[] array, int offset, int length, IntComparator comparator) {
        return wrapPreSorted(array, offset, length, 0, comparator);
    }

    public static IntSpliterator wrapPreSorted(int[] array, IntComparator comparator) {
        return wrapPreSorted(array, 0, array.length, comparator);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntSpliterators$SpliteratorWrapper.class */
    public static class SpliteratorWrapper implements IntSpliterator {

        /* renamed from: i */
        final Spliterator<Integer> f120i;

        public SpliteratorWrapper(Spliterator<Integer> i) {
            this.f120i = i;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator
        public boolean tryAdvance(IntConsumer action) {
            return this.f120i.tryAdvance(action);
        }

        @Override // java.util.Spliterator.OfInt
        public boolean tryAdvance(java.util.function.IntConsumer action) {
            Consumer<? super Integer> consumer;
            Objects.requireNonNull(action);
            Spliterator<Integer> spliterator = this.f120i;
            if (action instanceof Consumer) {
                consumer = (Consumer) action;
            } else {
                Objects.requireNonNull(action);
                consumer = (v1) -> {
                    r1.accept(v1);
                };
            }
            return spliterator.tryAdvance(consumer);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator, java.util.Spliterator.OfInt, java.util.Spliterator
        @Deprecated
        public boolean tryAdvance(Consumer<? super Integer> action) {
            return this.f120i.tryAdvance(action);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator
        public void forEachRemaining(IntConsumer action) {
            this.f120i.forEachRemaining(action);
        }

        @Override // java.util.Spliterator.OfInt
        public void forEachRemaining(java.util.function.IntConsumer action) {
            Consumer<? super Integer> consumer;
            Objects.requireNonNull(action);
            Spliterator<Integer> spliterator = this.f120i;
            if (action instanceof Consumer) {
                consumer = (Consumer) action;
            } else {
                Objects.requireNonNull(action);
                consumer = (v1) -> {
                    r1.accept(v1);
                };
            }
            spliterator.forEachRemaining(consumer);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator, java.util.Spliterator.OfInt, java.util.Spliterator
        @Deprecated
        public void forEachRemaining(Consumer<? super Integer> action) {
            this.f120i.forEachRemaining(action);
        }

        @Override // java.util.Spliterator
        public long estimateSize() {
            return this.f120i.estimateSize();
        }

        @Override // java.util.Spliterator
        public int characteristics() {
            return this.f120i.characteristics();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator, java.util.Spliterator
        public IntComparator getComparator() {
            return IntComparators.asIntComparator(this.f120i.getComparator());
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator, java.util.Spliterator.OfInt, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public IntSpliterator trySplit() {
            Spliterator<Integer> innerSplit = this.f120i.trySplit();
            if (innerSplit == null) {
                return null;
            }
            return new SpliteratorWrapper(innerSplit);
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntSpliterators$SpliteratorWrapperWithComparator.class */
    private static class SpliteratorWrapperWithComparator extends SpliteratorWrapper {
        final IntComparator comparator;

        public SpliteratorWrapperWithComparator(Spliterator<Integer> i, IntComparator comparator) {
            super(i);
            this.comparator = comparator;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators.SpliteratorWrapper, com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator, java.util.Spliterator
        public IntComparator getComparator() {
            return this.comparator;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators.SpliteratorWrapper, com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator, java.util.Spliterator.OfInt, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public IntSpliterator trySplit() {
            Spliterator<Integer> innerSplit = this.f120i.trySplit();
            if (innerSplit == null) {
                return null;
            }
            return new SpliteratorWrapperWithComparator(innerSplit, this.comparator);
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntSpliterators$PrimitiveSpliteratorWrapper.class */
    public static class PrimitiveSpliteratorWrapper implements IntSpliterator {

        /* renamed from: i */
        final Spliterator.OfInt f118i;

        public PrimitiveSpliteratorWrapper(Spliterator.OfInt i) {
            this.f118i = i;
        }

        @Override // java.util.Spliterator.OfInt
        public boolean tryAdvance(java.util.function.IntConsumer action) {
            return this.f118i.tryAdvance(action);
        }

        @Override // java.util.Spliterator.OfInt
        public void forEachRemaining(java.util.function.IntConsumer action) {
            this.f118i.forEachRemaining(action);
        }

        @Override // java.util.Spliterator
        public long estimateSize() {
            return this.f118i.estimateSize();
        }

        @Override // java.util.Spliterator
        public int characteristics() {
            return this.f118i.characteristics();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator, java.util.Spliterator
        public IntComparator getComparator() {
            return IntComparators.asIntComparator(this.f118i.getComparator());
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator, java.util.Spliterator.OfInt, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public IntSpliterator trySplit() {
            Spliterator.OfInt innerSplit = this.f118i.trySplit();
            if (innerSplit == null) {
                return null;
            }
            return new PrimitiveSpliteratorWrapper(innerSplit);
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntSpliterators$PrimitiveSpliteratorWrapperWithComparator.class */
    private static class PrimitiveSpliteratorWrapperWithComparator extends PrimitiveSpliteratorWrapper {
        final IntComparator comparator;

        public PrimitiveSpliteratorWrapperWithComparator(Spliterator.OfInt i, IntComparator comparator) {
            super(i);
            this.comparator = comparator;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators.PrimitiveSpliteratorWrapper, com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator, java.util.Spliterator
        public IntComparator getComparator() {
            return this.comparator;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators.PrimitiveSpliteratorWrapper, com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator, java.util.Spliterator.OfInt, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public IntSpliterator trySplit() {
            Spliterator.OfInt innerSplit = this.f118i.trySplit();
            if (innerSplit == null) {
                return null;
            }
            return new PrimitiveSpliteratorWrapperWithComparator(innerSplit, this.comparator);
        }
    }

    public static IntSpliterator asIntSpliterator(Spliterator i) {
        if (i instanceof IntSpliterator) {
            return (IntSpliterator) i;
        }
        if (i instanceof Spliterator.OfInt) {
            return new PrimitiveSpliteratorWrapper((Spliterator.OfInt) i);
        }
        return new SpliteratorWrapper(i);
    }

    public static IntSpliterator asIntSpliterator(Spliterator i, IntComparator comparatorOverride) {
        if (i instanceof IntSpliterator) {
            throw new IllegalArgumentException("Cannot override comparator on instance that is already a " + IntSpliterator.class.getSimpleName());
        }
        if (i instanceof Spliterator.OfInt) {
            return new PrimitiveSpliteratorWrapperWithComparator((Spliterator.OfInt) i, comparatorOverride);
        }
        return new SpliteratorWrapperWithComparator(i, comparatorOverride);
    }

    public static void onEachMatching(IntSpliterator spliterator, java.util.function.IntPredicate predicate, java.util.function.IntConsumer action) {
        Objects.requireNonNull(predicate);
        Objects.requireNonNull(action);
        spliterator.forEachRemaining(value -> {
            if (predicate.test(value)) {
                action.accept(value);
            }
        });
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntSpliterators$AbstractIndexBasedSpliterator.class */
    public static abstract class AbstractIndexBasedSpliterator extends AbstractIntSpliterator {
        protected int pos;

        protected abstract int get(int i);

        protected abstract int getMaxPos();

        protected abstract IntSpliterator makeForSplit(int i, int i2);

        protected AbstractIndexBasedSpliterator(int initialPos) {
            this.pos = initialPos;
        }

        protected int computeSplitPoint() {
            return this.pos + ((getMaxPos() - this.pos) / 2);
        }

        private void splitPointCheck(int splitPoint, int observedMax) {
            if (splitPoint < this.pos || splitPoint > observedMax) {
                throw new IndexOutOfBoundsException("splitPoint " + splitPoint + " outside of range of current position " + this.pos + " and range end " + observedMax);
            }
        }

        @Override // java.util.Spliterator
        public int characteristics() {
            return IntSpliterators.LIST_SPLITERATOR_CHARACTERISTICS;
        }

        @Override // java.util.Spliterator
        public long estimateSize() {
            return getMaxPos() - this.pos;
        }

        @Override // java.util.Spliterator.OfInt
        public boolean tryAdvance(java.util.function.IntConsumer action) {
            if (this.pos >= getMaxPos()) {
                return false;
            }
            int i = this.pos;
            this.pos = i + 1;
            action.accept(get(i));
            return true;
        }

        @Override // java.util.Spliterator.OfInt
        public void forEachRemaining(java.util.function.IntConsumer action) {
            int max = getMaxPos();
            while (this.pos < max) {
                action.accept(get(this.pos));
                this.pos++;
            }
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator
        public long skip(long n) {
            if (n < 0) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            }
            int max = getMaxPos();
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
            int max = getMaxPos();
            int splitPoint = computeSplitPoint();
            if (splitPoint == this.pos || splitPoint == max) {
                return null;
            }
            splitPointCheck(splitPoint, max);
            int oldPos = this.pos;
            IntSpliterator maybeSplit = makeForSplit(oldPos, splitPoint);
            if (maybeSplit != null) {
                this.pos = splitPoint;
            }
            return maybeSplit;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntSpliterators$EarlyBindingSizeIndexBasedSpliterator.class */
    public static abstract class EarlyBindingSizeIndexBasedSpliterator extends AbstractIndexBasedSpliterator {
        protected final int maxPos;

        public EarlyBindingSizeIndexBasedSpliterator(int initialPos, int maxPos) {
            super(initialPos);
            this.maxPos = maxPos;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators.AbstractIndexBasedSpliterator
        protected final int getMaxPos() {
            return this.maxPos;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntSpliterators$LateBindingSizeIndexBasedSpliterator.class */
    public static abstract class LateBindingSizeIndexBasedSpliterator extends AbstractIndexBasedSpliterator {
        protected int maxPos;
        private boolean maxPosFixed;

        protected abstract int getMaxPosFromBackingStore();

        public LateBindingSizeIndexBasedSpliterator(int initialPos) {
            super(initialPos);
            this.maxPos = -1;
            this.maxPosFixed = false;
        }

        public LateBindingSizeIndexBasedSpliterator(int initialPos, int fixedMaxPos) {
            super(initialPos);
            this.maxPos = -1;
            this.maxPos = fixedMaxPos;
            this.maxPosFixed = true;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators.AbstractIndexBasedSpliterator
        public final int getMaxPos() {
            return this.maxPosFixed ? this.maxPos : getMaxPosFromBackingStore();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators.AbstractIndexBasedSpliterator, com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator, java.util.Spliterator.OfInt, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public IntSpliterator trySplit() {
            IntSpliterator maybeSplit = super.trySplit();
            if (!this.maxPosFixed && maybeSplit != null) {
                this.maxPos = getMaxPosFromBackingStore();
                this.maxPosFixed = true;
            }
            return maybeSplit;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntSpliterators$IntervalSpliterator.class */
    private static class IntervalSpliterator implements IntSpliterator {
        private static final int DONT_SPLIT_THRESHOLD = 2;
        private static final int CHARACTERISTICS = 17749;
        private int curr;

        /* renamed from: to */
        private int f117to;

        public IntervalSpliterator(int from, int to) {
            this.curr = from;
            this.f117to = to;
        }

        @Override // java.util.Spliterator.OfInt
        public boolean tryAdvance(java.util.function.IntConsumer action) {
            if (this.curr >= this.f117to) {
                return false;
            }
            int i = this.curr;
            this.curr = i + 1;
            action.accept(i);
            return true;
        }

        @Override // java.util.Spliterator.OfInt
        public void forEachRemaining(java.util.function.IntConsumer action) {
            Objects.requireNonNull(action);
            while (this.curr < this.f117to) {
                action.accept(this.curr);
                this.curr++;
            }
        }

        @Override // java.util.Spliterator
        public long estimateSize() {
            return this.f117to - this.curr;
        }

        @Override // java.util.Spliterator
        public int characteristics() {
            return CHARACTERISTICS;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator, java.util.Spliterator
        public IntComparator getComparator() {
            return null;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator, java.util.Spliterator.OfInt, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public IntSpliterator trySplit() {
            long remaining = this.f117to - this.curr;
            int mid = (int) (this.curr + (remaining >> 1));
            if (remaining >= 0 && remaining <= 2) {
                return null;
            }
            int old_curr = this.curr;
            this.curr = mid;
            return new IntervalSpliterator(old_curr, mid);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator
        public long skip(long n) {
            if (n < 0) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            }
            if (this.curr >= this.f117to) {
                return 0L;
            }
            long newCurr = this.curr + n;
            if (newCurr <= this.f117to && newCurr >= this.curr) {
                this.curr = SafeMath.safeLongToInt(newCurr);
                return n;
            }
            long n2 = this.f117to - this.curr;
            this.curr = this.f117to;
            return n2;
        }
    }

    public static IntSpliterator fromTo(int from, int to) {
        return new IntervalSpliterator(from, to);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntSpliterators$SpliteratorConcatenator.class */
    public static class SpliteratorConcatenator implements IntSpliterator {
        private static final int EMPTY_CHARACTERISTICS = 16448;
        private static final int CHARACTERISTICS_NOT_SUPPORTED_WHILE_MULTIPLE = 5;

        /* renamed from: a */
        final IntSpliterator[] f119a;
        int offset;
        int length;
        long remainingEstimatedExceptCurrent;
        int characteristics;

        public SpliteratorConcatenator(IntSpliterator[] a, int offset, int length) {
            this.remainingEstimatedExceptCurrent = LongCompanionObject.MAX_VALUE;
            this.characteristics = 0;
            this.f119a = a;
            this.offset = offset;
            this.length = length;
            this.remainingEstimatedExceptCurrent = recomputeRemaining();
            this.characteristics = computeCharacteristics();
        }

        /* JADX WARN: Removed duplicated region for block: B:5:0x0014  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private long recomputeRemaining() {
            /*
                r5 = this;
                r0 = r5
                int r0 = r0.length
                r1 = 1
                int r0 = r0 - r1
                r6 = r0
                r0 = r5
                int r0 = r0.offset
                r1 = 1
                int r0 = r0 + r1
                r7 = r0
                r0 = 0
                r8 = r0
            L10:
                r0 = r6
                if (r0 <= 0) goto L4e
                r0 = r5
                com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator[] r0 = r0.f119a
                r1 = r7
                int r7 = r7 + 1
                r0 = r0[r1]
                long r0 = r0.estimateSize()
                r10 = r0
                int r6 = r6 + (-1)
                r0 = r10
                r1 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
                int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
                if (r0 != 0) goto L34
                r0 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
                return r0
            L34:
                r0 = r8
                r1 = r10
                long r0 = r0 + r1
                r8 = r0
                r0 = r8
                r1 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
                int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
                if (r0 == 0) goto L47
                r0 = r8
                r1 = 0
                int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
                if (r0 >= 0) goto L4b
            L47:
                r0 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
                return r0
            L4b:
                goto L10
            L4e:
                r0 = r8
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators.SpliteratorConcatenator.recomputeRemaining():long");
        }

        private int computeCharacteristics() {
            if (this.length <= 0) {
                return EMPTY_CHARACTERISTICS;
            }
            int current = -1;
            int curLength = this.length;
            int curOffset = this.offset;
            if (curLength > 1) {
                current = (-1) & (-6);
            }
            while (curLength > 0) {
                int i = curOffset;
                curOffset++;
                current &= this.f119a[i].characteristics();
                curLength--;
            }
            return current;
        }

        private void advanceNextSpliterator() {
            if (this.length <= 0) {
                throw new AssertionError("advanceNextSpliterator() called with none remaining");
            }
            this.offset++;
            this.length--;
            this.remainingEstimatedExceptCurrent = recomputeRemaining();
        }

        @Override // java.util.Spliterator.OfInt
        public boolean tryAdvance(java.util.function.IntConsumer action) {
            boolean any = false;
            while (true) {
                if (this.length <= 0) {
                    break;
                } else if (this.f119a[this.offset].tryAdvance(action)) {
                    any = true;
                    break;
                } else {
                    advanceNextSpliterator();
                }
            }
            return any;
        }

        @Override // java.util.Spliterator.OfInt
        public void forEachRemaining(java.util.function.IntConsumer action) {
            while (this.length > 0) {
                this.f119a[this.offset].forEachRemaining(action);
                advanceNextSpliterator();
            }
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator, java.util.Spliterator.OfInt, java.util.Spliterator
        @Deprecated
        public void forEachRemaining(Consumer<? super Integer> action) {
            while (this.length > 0) {
                this.f119a[this.offset].forEachRemaining(action);
                advanceNextSpliterator();
            }
        }

        @Override // java.util.Spliterator
        public long estimateSize() {
            if (this.length <= 0) {
                return 0L;
            }
            long est = this.f119a[this.offset].estimateSize() + this.remainingEstimatedExceptCurrent;
            if (est < 0) {
                return LongCompanionObject.MAX_VALUE;
            }
            return est;
        }

        @Override // java.util.Spliterator
        public int characteristics() {
            return this.characteristics;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator, java.util.Spliterator
        public IntComparator getComparator() {
            if (this.length == 1 && (this.characteristics & 4) != 0) {
                return this.f119a[this.offset].getComparator();
            }
            throw new IllegalStateException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator, java.util.Spliterator.OfInt, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public IntSpliterator trySplit() {
            switch (this.length) {
                case 0:
                    return null;
                case 1:
                    IntSpliterator split = this.f119a[this.offset].trySplit();
                    this.characteristics = this.f119a[this.offset].characteristics();
                    return split;
                case 2:
                    IntSpliterator[] intSpliteratorArr = this.f119a;
                    int i = this.offset;
                    this.offset = i + 1;
                    IntSpliterator split2 = intSpliteratorArr[i];
                    this.length--;
                    this.characteristics = this.f119a[this.offset].characteristics();
                    this.remainingEstimatedExceptCurrent = 0L;
                    return split2;
                default:
                    int mid = this.length >> 1;
                    int ret_offset = this.offset;
                    int new_offset = this.offset + mid;
                    int new_length = this.length - mid;
                    this.offset = new_offset;
                    this.length = new_length;
                    this.remainingEstimatedExceptCurrent = recomputeRemaining();
                    this.characteristics = computeCharacteristics();
                    return new SpliteratorConcatenator(this.f119a, ret_offset, mid);
            }
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator
        public long skip(long n) {
            long skipped = 0;
            if (this.length <= 0) {
                return 0L;
            }
            while (skipped < n && this.length >= 0) {
                long curSkipped = this.f119a[this.offset].skip(n - skipped);
                skipped += curSkipped;
                if (skipped < n) {
                    advanceNextSpliterator();
                }
            }
            return skipped;
        }
    }

    public static IntSpliterator concat(IntSpliterator... a) {
        return concat(a, 0, a.length);
    }

    public static IntSpliterator concat(IntSpliterator[] a, int offset, int length) {
        return new SpliteratorConcatenator(a, offset, length);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntSpliterators$SpliteratorFromIterator.class */
    public static class SpliteratorFromIterator implements IntSpliterator {
        private static final int BATCH_INCREMENT_SIZE = 1024;
        private static final int BATCH_MAX_SIZE = 33554432;
        private final IntIterator iter;
        final int characteristics;
        private final boolean knownSize;
        private long size;
        private int nextBatchSize;
        private IntSpliterator delegate;

        SpliteratorFromIterator(IntIterator iter, int characteristics) {
            this.size = LongCompanionObject.MAX_VALUE;
            this.nextBatchSize = 1024;
            this.delegate = null;
            this.iter = iter;
            this.characteristics = 256 | characteristics;
            this.knownSize = false;
        }

        SpliteratorFromIterator(IntIterator iter, long size, int additionalCharacteristics) {
            this.size = LongCompanionObject.MAX_VALUE;
            this.nextBatchSize = 1024;
            this.delegate = null;
            this.iter = iter;
            this.knownSize = true;
            this.size = size;
            if ((additionalCharacteristics & 4096) != 0) {
                this.characteristics = 256 | additionalCharacteristics;
            } else {
                this.characteristics = 16704 | additionalCharacteristics;
            }
        }

        @Override // java.util.Spliterator.OfInt
        public boolean tryAdvance(java.util.function.IntConsumer action) {
            if (this.delegate != null) {
                boolean hadRemaining = this.delegate.tryAdvance(action);
                if (!hadRemaining) {
                    this.delegate = null;
                }
                return hadRemaining;
            } else if (!this.iter.hasNext()) {
                return false;
            } else {
                this.size--;
                action.accept(this.iter.nextInt());
                return true;
            }
        }

        @Override // java.util.Spliterator.OfInt
        public void forEachRemaining(java.util.function.IntConsumer action) {
            if (this.delegate != null) {
                this.delegate.forEachRemaining(action);
                this.delegate = null;
            }
            this.iter.forEachRemaining(action);
            this.size = 0L;
        }

        @Override // java.util.Spliterator
        public long estimateSize() {
            if (this.delegate != null) {
                return this.delegate.estimateSize();
            }
            if (!this.iter.hasNext()) {
                return 0L;
            }
            return (!this.knownSize || this.size < 0) ? LongCompanionObject.MAX_VALUE : this.size;
        }

        @Override // java.util.Spliterator
        public int characteristics() {
            return this.characteristics;
        }

        protected IntSpliterator makeForSplit(int[] batch, int len) {
            return IntSpliterators.wrap(batch, 0, len, this.characteristics);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator, java.util.Spliterator.OfInt, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public IntSpliterator trySplit() {
            if (!this.iter.hasNext()) {
                return null;
            }
            int batchSizeEst = (!this.knownSize || this.size <= 0) ? this.nextBatchSize : (int) Math.min(this.nextBatchSize, this.size);
            int[] batch = new int[batchSizeEst];
            int actualSeen = 0;
            while (actualSeen < batchSizeEst && this.iter.hasNext()) {
                int i = actualSeen;
                actualSeen++;
                batch[i] = this.iter.nextInt();
                this.size--;
            }
            if (batchSizeEst < this.nextBatchSize && this.iter.hasNext()) {
                batch = Arrays.copyOf(batch, this.nextBatchSize);
                while (this.iter.hasNext() && actualSeen < this.nextBatchSize) {
                    int i2 = actualSeen;
                    actualSeen++;
                    batch[i2] = this.iter.nextInt();
                    this.size--;
                }
            }
            this.nextBatchSize = Math.min(33554432, this.nextBatchSize + 1024);
            IntSpliterator split = makeForSplit(batch, actualSeen);
            if (!this.iter.hasNext()) {
                this.delegate = split;
                return split.trySplit();
            }
            return split;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator
        public long skip(long n) {
            long skippedSoFar;
            if (n < 0) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            }
            if (this.iter instanceof IntBigListIterator) {
                long skipped = this.iter.skip(n);
                this.size -= skipped;
                return skipped;
            }
            long j = 0;
            while (true) {
                skippedSoFar = j;
                if (skippedSoFar >= n || !this.iter.hasNext()) {
                    break;
                }
                int skipped2 = this.iter.skip(SafeMath.safeLongToInt(Math.min(n, 2147483647L)));
                this.size -= skipped2;
                j = skippedSoFar + skipped2;
            }
            return skippedSoFar;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntSpliterators$SpliteratorFromIteratorWithComparator.class */
    public static class SpliteratorFromIteratorWithComparator extends SpliteratorFromIterator {
        private final IntComparator comparator;

        SpliteratorFromIteratorWithComparator(IntIterator iter, int additionalCharacteristics, IntComparator comparator) {
            super(iter, additionalCharacteristics | 20);
            this.comparator = comparator;
        }

        SpliteratorFromIteratorWithComparator(IntIterator iter, long size, int additionalCharacteristics, IntComparator comparator) {
            super(iter, size, additionalCharacteristics | 20);
            this.comparator = comparator;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator, java.util.Spliterator
        public IntComparator getComparator() {
            return this.comparator;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators.SpliteratorFromIterator
        protected IntSpliterator makeForSplit(int[] array, int len) {
            return IntSpliterators.wrapPreSorted(array, 0, len, this.characteristics, this.comparator);
        }
    }

    public static IntSpliterator asSpliterator(IntIterator iter, long size, int additionalCharacterisitcs) {
        return new SpliteratorFromIterator(iter, size, additionalCharacterisitcs);
    }

    public static IntSpliterator asSpliteratorFromSorted(IntIterator iter, long size, int additionalCharacterisitcs, IntComparator comparator) {
        return new SpliteratorFromIteratorWithComparator(iter, size, additionalCharacterisitcs, comparator);
    }

    public static IntSpliterator asSpliteratorUnknownSize(IntIterator iter, int characterisitcs) {
        return new SpliteratorFromIterator(iter, characterisitcs);
    }

    public static IntSpliterator asSpliteratorFromSortedUnknownSize(IntIterator iter, int additionalCharacterisitcs, IntComparator comparator) {
        return new SpliteratorFromIteratorWithComparator(iter, additionalCharacterisitcs, comparator);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/IntSpliterators$IteratorFromSpliterator.class */
    private static final class IteratorFromSpliterator implements IntIterator, IntConsumer {
        private final IntSpliterator spliterator;
        private int holder = 0;
        private boolean hasPeeked = false;

        IteratorFromSpliterator(IntSpliterator spliterator) {
            this.spliterator = spliterator;
        }

        @Override // java.util.function.IntConsumer
        public void accept(int item) {
            this.holder = item;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            if (this.hasPeeked) {
                return true;
            }
            boolean hadElement = this.spliterator.tryAdvance((IntConsumer) this);
            if (!hadElement) {
                return false;
            }
            this.hasPeeked = true;
            return true;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
        public int nextInt() {
            if (this.hasPeeked) {
                this.hasPeeked = false;
                return this.holder;
            }
            boolean hadElement = this.spliterator.tryAdvance((IntConsumer) this);
            if (!hadElement) {
                throw new NoSuchElementException();
            }
            return this.holder;
        }

        @Override // java.util.PrimitiveIterator.OfInt
        public void forEachRemaining(java.util.function.IntConsumer action) {
            if (this.hasPeeked) {
                this.hasPeeked = false;
                action.accept(this.holder);
            }
            this.spliterator.forEachRemaining(action);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterator, com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator
        public int skip(int n) {
            if (n < 0) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            }
            int skipped = 0;
            if (this.hasPeeked) {
                this.hasPeeked = false;
                this.spliterator.skip(1L);
                skipped = 0 + 1;
                n--;
            }
            if (n > 0) {
                skipped += SafeMath.safeLongToInt(this.spliterator.skip(n));
            }
            return skipped;
        }
    }

    public static IntIterator asIterator(IntSpliterator spliterator) {
        return new IteratorFromSpliterator(spliterator);
    }

    public static IntSpliterator wrap(ByteSpliterator spliterator) {
        return new ByteSpliteratorWrapper(spliterator);
    }

    public static IntSpliterator wrap(ShortSpliterator spliterator) {
        return new ShortSpliteratorWrapper(spliterator);
    }

    public static IntSpliterator wrap(CharSpliterator spliterator) {
        return new CharSpliteratorWrapper(spliterator);
    }
}
