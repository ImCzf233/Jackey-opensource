package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.SafeMath;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import kotlin.jvm.internal.LongCompanionObject;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectSpliterators.class */
public final class ObjectSpliterators {
    static final int BASE_SPLITERATOR_CHARACTERISTICS = 0;
    public static final int COLLECTION_SPLITERATOR_CHARACTERISTICS = 64;
    public static final int LIST_SPLITERATOR_CHARACTERISTICS = 16464;
    public static final int SET_SPLITERATOR_CHARACTERISTICS = 65;
    private static final int SORTED_CHARACTERISTICS = 20;
    public static final int SORTED_SET_SPLITERATOR_CHARACTERISTICS = 85;
    public static final EmptySpliterator EMPTY_SPLITERATOR = new EmptySpliterator();

    private ObjectSpliterators() {
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectSpliterators$EmptySpliterator.class */
    public static class EmptySpliterator<K> implements ObjectSpliterator<K>, Serializable, Cloneable {
        private static final long serialVersionUID = 8379247926738230492L;
        private static final int CHARACTERISTICS = 16448;

        protected EmptySpliterator() {
        }

        @Override // java.util.Spliterator
        public boolean tryAdvance(Consumer<? super K> action) {
            return false;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator, java.util.Spliterator
        public ObjectSpliterator<K> trySplit() {
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

        @Override // java.util.Spliterator
        public void forEachRemaining(Consumer<? super K> action) {
        }

        public Object clone() {
            return ObjectSpliterators.EMPTY_SPLITERATOR;
        }

        private Object readResolve() {
            return ObjectSpliterators.EMPTY_SPLITERATOR;
        }
    }

    public static <K> ObjectSpliterator<K> emptySpliterator() {
        return EMPTY_SPLITERATOR;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectSpliterators$SingletonSpliterator.class */
    public static class SingletonSpliterator<K> implements ObjectSpliterator<K> {
        private final K element;
        private final Comparator<? super K> comparator;
        private boolean consumed;
        private static final int CHARACTERISTICS = 17493;

        public SingletonSpliterator(K element) {
            this(element, null);
        }

        public SingletonSpliterator(K element, Comparator<? super K> comparator) {
            this.consumed = false;
            this.element = element;
            this.comparator = comparator;
        }

        @Override // java.util.Spliterator
        public boolean tryAdvance(Consumer<? super K> action) {
            Objects.requireNonNull(action);
            if (this.consumed) {
                return false;
            }
            this.consumed = true;
            action.accept((K) this.element);
            return true;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator, java.util.Spliterator
        public ObjectSpliterator<K> trySplit() {
            return null;
        }

        @Override // java.util.Spliterator
        public long estimateSize() {
            return this.consumed ? 0L : 1L;
        }

        @Override // java.util.Spliterator
        public int characteristics() {
            return CHARACTERISTICS | (this.element != null ? 256 : 0);
        }

        @Override // java.util.Spliterator
        public void forEachRemaining(Consumer<? super K> action) {
            Objects.requireNonNull(action);
            if (!this.consumed) {
                this.consumed = true;
                action.accept((K) this.element);
            }
        }

        @Override // java.util.Spliterator
        public Comparator<? super K> getComparator() {
            return this.comparator;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator
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

    public static <K> ObjectSpliterator<K> singleton(K element) {
        return new SingletonSpliterator(element);
    }

    public static <K> ObjectSpliterator<K> singleton(K element, Comparator<? super K> comparator) {
        return new SingletonSpliterator(element, comparator);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectSpliterators$ArraySpliterator.class */
    public static class ArraySpliterator<K> implements ObjectSpliterator<K> {
        private static final int BASE_CHARACTERISTICS = 16464;
        final K[] array;
        private final int offset;
        private int length;
        private int curr;
        final int characteristics;

        public ArraySpliterator(K[] array, int offset, int length, int additionalCharacteristics) {
            this.array = array;
            this.offset = offset;
            this.length = length;
            this.characteristics = 16464 | additionalCharacteristics;
        }

        @Override // java.util.Spliterator
        public boolean tryAdvance(Consumer<? super K> action) {
            if (this.curr >= this.length) {
                return false;
            }
            Objects.requireNonNull(action);
            int i = this.offset;
            int i2 = this.curr;
            this.curr = i2 + 1;
            action.accept((Object) this.array[i + i2]);
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

        protected ArraySpliterator<K> makeForSplit(int newOffset, int newLength) {
            return new ArraySpliterator<>(this.array, newOffset, newLength, this.characteristics);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator, java.util.Spliterator
        public ObjectSpliterator<K> trySplit() {
            int retLength = (this.length - this.curr) >> 1;
            if (retLength <= 1) {
                return null;
            }
            int myNewCurr = this.curr + retLength;
            int retOffset = this.offset + this.curr;
            this.curr = myNewCurr;
            return makeForSplit(retOffset, retLength);
        }

        @Override // java.util.Spliterator
        public void forEachRemaining(Consumer<? super K> action) {
            Objects.requireNonNull(action);
            while (this.curr < this.length) {
                action.accept((Object) this.array[this.offset + this.curr]);
                this.curr++;
            }
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator
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

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectSpliterators$ArraySpliteratorWithComparator.class */
    public static class ArraySpliteratorWithComparator<K> extends ArraySpliterator<K> {
        private final Comparator<? super K> comparator;

        public ArraySpliteratorWithComparator(K[] array, int offset, int length, int additionalCharacteristics, Comparator<? super K> comparator) {
            super(array, offset, length, additionalCharacteristics | 20);
            this.comparator = comparator;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators.ArraySpliterator
        public ArraySpliteratorWithComparator<K> makeForSplit(int newOffset, int newLength) {
            return new ArraySpliteratorWithComparator<>(this.array, newOffset, newLength, this.characteristics, this.comparator);
        }

        @Override // java.util.Spliterator
        public Comparator<? super K> getComparator() {
            return this.comparator;
        }
    }

    public static <K> ObjectSpliterator<K> wrap(K[] array, int offset, int length) {
        ObjectArrays.ensureOffsetLength(array, offset, length);
        return new ArraySpliterator(array, offset, length, 0);
    }

    public static <K> ObjectSpliterator<K> wrap(K[] array) {
        return new ArraySpliterator(array, 0, array.length, 0);
    }

    public static <K> ObjectSpliterator<K> wrap(K[] array, int offset, int length, int additionalCharacteristics) {
        ObjectArrays.ensureOffsetLength(array, offset, length);
        return new ArraySpliterator(array, offset, length, additionalCharacteristics);
    }

    public static <K> ObjectSpliterator<K> wrapPreSorted(K[] array, int offset, int length, int additionalCharacteristics, Comparator<? super K> comparator) {
        ObjectArrays.ensureOffsetLength(array, offset, length);
        return new ArraySpliteratorWithComparator(array, offset, length, additionalCharacteristics, comparator);
    }

    public static <K> ObjectSpliterator<K> wrapPreSorted(K[] array, int offset, int length, Comparator<? super K> comparator) {
        return wrapPreSorted(array, offset, length, 0, comparator);
    }

    public static <K> ObjectSpliterator<K> wrapPreSorted(K[] array, Comparator<? super K> comparator) {
        return wrapPreSorted(array, 0, array.length, comparator);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectSpliterators$SpliteratorWrapper.class */
    public static class SpliteratorWrapper<K> implements ObjectSpliterator<K> {

        /* renamed from: i */
        final Spliterator<K> f168i;

        public SpliteratorWrapper(Spliterator<K> i) {
            this.f168i = i;
        }

        @Override // java.util.Spliterator
        public boolean tryAdvance(Consumer<? super K> action) {
            return this.f168i.tryAdvance(action);
        }

        @Override // java.util.Spliterator
        public void forEachRemaining(Consumer<? super K> action) {
            this.f168i.forEachRemaining(action);
        }

        @Override // java.util.Spliterator
        public long estimateSize() {
            return this.f168i.estimateSize();
        }

        @Override // java.util.Spliterator
        public int characteristics() {
            return this.f168i.characteristics();
        }

        @Override // java.util.Spliterator
        public Comparator<? super K> getComparator() {
            return ObjectComparators.asObjectComparator(this.f168i.getComparator());
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator, java.util.Spliterator
        public ObjectSpliterator<K> trySplit() {
            Spliterator<K> innerSplit = this.f168i.trySplit();
            if (innerSplit == null) {
                return null;
            }
            return new SpliteratorWrapper(innerSplit);
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectSpliterators$SpliteratorWrapperWithComparator.class */
    private static class SpliteratorWrapperWithComparator<K> extends SpliteratorWrapper<K> {
        final Comparator<? super K> comparator;

        public SpliteratorWrapperWithComparator(Spliterator<K> i, Comparator<? super K> comparator) {
            super(i);
            this.comparator = comparator;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators.SpliteratorWrapper, java.util.Spliterator
        public Comparator<? super K> getComparator() {
            return this.comparator;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators.SpliteratorWrapper, com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator, java.util.Spliterator
        public ObjectSpliterator<K> trySplit() {
            Spliterator<K> innerSplit = this.f168i.trySplit();
            if (innerSplit == null) {
                return null;
            }
            return new SpliteratorWrapperWithComparator(innerSplit, this.comparator);
        }
    }

    public static <K> ObjectSpliterator<K> asObjectSpliterator(Spliterator<K> i) {
        if (i instanceof ObjectSpliterator) {
            return (ObjectSpliterator) i;
        }
        return new SpliteratorWrapper(i);
    }

    public static <K> ObjectSpliterator<K> asObjectSpliterator(Spliterator<K> i, Comparator<? super K> comparatorOverride) {
        if (i instanceof ObjectSpliterator) {
            throw new IllegalArgumentException("Cannot override comparator on instance that is already a " + ObjectSpliterator.class.getSimpleName());
        }
        return new SpliteratorWrapperWithComparator(i, comparatorOverride);
    }

    public static <K> void onEachMatching(Spliterator<K> spliterator, Predicate<? super K> predicate, Consumer<? super K> action) {
        Objects.requireNonNull(predicate);
        Objects.requireNonNull(action);
        spliterator.forEachRemaining(value -> {
            if (predicate.test(value)) {
                action.accept(value);
            }
        });
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectSpliterators$AbstractIndexBasedSpliterator.class */
    public static abstract class AbstractIndexBasedSpliterator<K> extends AbstractObjectSpliterator<K> {
        protected int pos;

        protected abstract K get(int i);

        protected abstract int getMaxPos();

        protected abstract ObjectSpliterator<K> makeForSplit(int i, int i2);

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
            return ObjectSpliterators.LIST_SPLITERATOR_CHARACTERISTICS;
        }

        @Override // java.util.Spliterator
        public long estimateSize() {
            return getMaxPos() - this.pos;
        }

        @Override // java.util.Spliterator
        public boolean tryAdvance(Consumer<? super K> action) {
            if (this.pos >= getMaxPos()) {
                return false;
            }
            int i = this.pos;
            this.pos = i + 1;
            action.accept(get(i));
            return true;
        }

        @Override // java.util.Spliterator
        public void forEachRemaining(Consumer<? super K> action) {
            int max = getMaxPos();
            while (this.pos < max) {
                action.accept(get(this.pos));
                this.pos++;
            }
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator
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

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator, java.util.Spliterator
        public ObjectSpliterator<K> trySplit() {
            int max = getMaxPos();
            int splitPoint = computeSplitPoint();
            if (splitPoint == this.pos || splitPoint == max) {
                return null;
            }
            splitPointCheck(splitPoint, max);
            int oldPos = this.pos;
            ObjectSpliterator<K> maybeSplit = makeForSplit(oldPos, splitPoint);
            if (maybeSplit != null) {
                this.pos = splitPoint;
            }
            return maybeSplit;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectSpliterators$EarlyBindingSizeIndexBasedSpliterator.class */
    public static abstract class EarlyBindingSizeIndexBasedSpliterator<K> extends AbstractIndexBasedSpliterator<K> {
        protected final int maxPos;

        public EarlyBindingSizeIndexBasedSpliterator(int initialPos, int maxPos) {
            super(initialPos);
            this.maxPos = maxPos;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators.AbstractIndexBasedSpliterator
        protected final int getMaxPos() {
            return this.maxPos;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectSpliterators$LateBindingSizeIndexBasedSpliterator.class */
    public static abstract class LateBindingSizeIndexBasedSpliterator<K> extends AbstractIndexBasedSpliterator<K> {
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

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators.AbstractIndexBasedSpliterator
        public final int getMaxPos() {
            return this.maxPosFixed ? this.maxPos : getMaxPosFromBackingStore();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators.AbstractIndexBasedSpliterator, com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator, java.util.Spliterator
        public ObjectSpliterator<K> trySplit() {
            ObjectSpliterator<K> maybeSplit = super.trySplit();
            if (!this.maxPosFixed && maybeSplit != null) {
                this.maxPos = getMaxPosFromBackingStore();
                this.maxPosFixed = true;
            }
            return maybeSplit;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectSpliterators$SpliteratorConcatenator.class */
    public static class SpliteratorConcatenator<K> implements ObjectSpliterator<K> {
        private static final int EMPTY_CHARACTERISTICS = 16448;
        private static final int CHARACTERISTICS_NOT_SUPPORTED_WHILE_MULTIPLE = 5;

        /* renamed from: a */
        final ObjectSpliterator<? extends K>[] f167a;
        int offset;
        int length;
        long remainingEstimatedExceptCurrent;
        int characteristics;

        public SpliteratorConcatenator(ObjectSpliterator<? extends K>[] a, int offset, int length) {
            this.remainingEstimatedExceptCurrent = LongCompanionObject.MAX_VALUE;
            this.characteristics = 0;
            this.f167a = a;
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
                com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator<? extends K>[] r0 = r0.f167a
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
            throw new UnsupportedOperationException("Method not decompiled: com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators.SpliteratorConcatenator.recomputeRemaining():long");
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
                current &= this.f167a[i].characteristics();
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

        @Override // java.util.Spliterator
        public boolean tryAdvance(Consumer<? super K> action) {
            boolean any = false;
            while (true) {
                if (this.length <= 0) {
                    break;
                } else if (this.f167a[this.offset].tryAdvance(action)) {
                    any = true;
                    break;
                } else {
                    advanceNextSpliterator();
                }
            }
            return any;
        }

        @Override // java.util.Spliterator
        public void forEachRemaining(Consumer<? super K> action) {
            while (this.length > 0) {
                this.f167a[this.offset].forEachRemaining(action);
                advanceNextSpliterator();
            }
        }

        @Override // java.util.Spliterator
        public long estimateSize() {
            if (this.length <= 0) {
                return 0L;
            }
            long est = this.f167a[this.offset].estimateSize() + this.remainingEstimatedExceptCurrent;
            if (est < 0) {
                return LongCompanionObject.MAX_VALUE;
            }
            return est;
        }

        @Override // java.util.Spliterator
        public int characteristics() {
            return this.characteristics;
        }

        @Override // java.util.Spliterator
        public Comparator<? super K> getComparator() {
            if (this.length == 1 && (this.characteristics & 4) != 0) {
                return (Comparator<? super Object>) this.f167a[this.offset].getComparator();
            }
            throw new IllegalStateException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator, java.util.Spliterator
        public ObjectSpliterator<K> trySplit() {
            switch (this.length) {
                case 0:
                    return null;
                case 1:
                    ObjectSpliterator objectSpliterator = (ObjectSpliterator<? extends K>) this.f167a[this.offset].trySplit();
                    this.characteristics = this.f167a[this.offset].characteristics();
                    return objectSpliterator;
                case 2:
                    int i = this.offset;
                    this.offset = i + 1;
                    ObjectSpliterator<K> split = (ObjectSpliterator<K>) this.f167a[i];
                    this.length--;
                    this.characteristics = this.f167a[this.offset].characteristics();
                    this.remainingEstimatedExceptCurrent = 0L;
                    return split;
                default:
                    int mid = this.length >> 1;
                    int ret_offset = this.offset;
                    int new_offset = this.offset + mid;
                    int new_length = this.length - mid;
                    this.offset = new_offset;
                    this.length = new_length;
                    this.remainingEstimatedExceptCurrent = recomputeRemaining();
                    this.characteristics = computeCharacteristics();
                    return new SpliteratorConcatenator(this.f167a, ret_offset, mid);
            }
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator
        public long skip(long n) {
            long skipped = 0;
            if (this.length <= 0) {
                return 0L;
            }
            while (skipped < n && this.length >= 0) {
                long curSkipped = this.f167a[this.offset].skip(n - skipped);
                skipped += curSkipped;
                if (skipped < n) {
                    advanceNextSpliterator();
                }
            }
            return skipped;
        }
    }

    @SafeVarargs
    public static <K> ObjectSpliterator<K> concat(ObjectSpliterator<? extends K>... a) {
        return concat(a, 0, a.length);
    }

    public static <K> ObjectSpliterator<K> concat(ObjectSpliterator<? extends K>[] a, int offset, int length) {
        return new SpliteratorConcatenator(a, offset, length);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectSpliterators$SpliteratorFromIterator.class */
    public static class SpliteratorFromIterator<K> implements ObjectSpliterator<K> {
        private static final int BATCH_INCREMENT_SIZE = 1024;
        private static final int BATCH_MAX_SIZE = 33554432;
        private final ObjectIterator<? extends K> iter;
        final int characteristics;
        private final boolean knownSize;
        private long size;
        private int nextBatchSize;
        private ObjectSpliterator<K> delegate;

        SpliteratorFromIterator(ObjectIterator<? extends K> iter, int characteristics) {
            this.size = LongCompanionObject.MAX_VALUE;
            this.nextBatchSize = 1024;
            this.delegate = null;
            this.iter = iter;
            this.characteristics = 0 | characteristics;
            this.knownSize = false;
        }

        SpliteratorFromIterator(ObjectIterator<? extends K> iter, long size, int additionalCharacteristics) {
            this.size = LongCompanionObject.MAX_VALUE;
            this.nextBatchSize = 1024;
            this.delegate = null;
            this.iter = iter;
            this.knownSize = true;
            this.size = size;
            if ((additionalCharacteristics & 4096) != 0) {
                this.characteristics = 0 | additionalCharacteristics;
            } else {
                this.characteristics = 16448 | additionalCharacteristics;
            }
        }

        @Override // java.util.Spliterator
        public boolean tryAdvance(Consumer<? super K> action) {
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
                action.accept((K) this.iter.next());
                return true;
            }
        }

        @Override // java.util.Spliterator
        public void forEachRemaining(Consumer<? super K> action) {
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

        protected ObjectSpliterator<K> makeForSplit(K[] batch, int len) {
            return ObjectSpliterators.wrap(batch, 0, len, this.characteristics);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v7, types: [java.lang.Object[]] */
        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator, java.util.Spliterator
        public ObjectSpliterator<K> trySplit() {
            if (!this.iter.hasNext()) {
                return null;
            }
            int batchSizeEst = (!this.knownSize || this.size <= 0) ? this.nextBatchSize : (int) Math.min(this.nextBatchSize, this.size);
            Object[] batch = new Object[batchSizeEst];
            int actualSeen = 0;
            while (actualSeen < batchSizeEst && this.iter.hasNext()) {
                int i = actualSeen;
                actualSeen++;
                batch[i] = this.iter.next();
                this.size--;
            }
            if (batchSizeEst < this.nextBatchSize && this.iter.hasNext()) {
                batch = Arrays.copyOf(batch, this.nextBatchSize);
                while (this.iter.hasNext() && actualSeen < this.nextBatchSize) {
                    int i2 = actualSeen;
                    actualSeen++;
                    batch[i2] = this.iter.next();
                    this.size--;
                }
            }
            this.nextBatchSize = Math.min(33554432, this.nextBatchSize + 1024);
            ObjectSpliterator<K> split = makeForSplit(batch, actualSeen);
            if (!this.iter.hasNext()) {
                this.delegate = split;
                return split.trySplit();
            }
            return split;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator
        public long skip(long n) {
            long skippedSoFar;
            if (n < 0) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            }
            if (this.iter instanceof ObjectBigListIterator) {
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
    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectSpliterators$SpliteratorFromIteratorWithComparator.class */
    public static class SpliteratorFromIteratorWithComparator<K> extends SpliteratorFromIterator<K> {
        private final Comparator<? super K> comparator;

        SpliteratorFromIteratorWithComparator(ObjectIterator<? extends K> iter, int additionalCharacteristics, Comparator<? super K> comparator) {
            super(iter, additionalCharacteristics | 20);
            this.comparator = comparator;
        }

        SpliteratorFromIteratorWithComparator(ObjectIterator<? extends K> iter, long size, int additionalCharacteristics, Comparator<? super K> comparator) {
            super(iter, size, additionalCharacteristics | 20);
            this.comparator = comparator;
        }

        @Override // java.util.Spliterator
        public Comparator<? super K> getComparator() {
            return this.comparator;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators.SpliteratorFromIterator
        protected ObjectSpliterator<K> makeForSplit(K[] array, int len) {
            return ObjectSpliterators.wrapPreSorted(array, 0, len, this.characteristics, this.comparator);
        }
    }

    public static <K> ObjectSpliterator<K> asSpliterator(ObjectIterator<? extends K> iter, long size, int additionalCharacterisitcs) {
        return new SpliteratorFromIterator(iter, size, additionalCharacterisitcs);
    }

    public static <K> ObjectSpliterator<K> asSpliteratorFromSorted(ObjectIterator<? extends K> iter, long size, int additionalCharacterisitcs, Comparator<? super K> comparator) {
        return new SpliteratorFromIteratorWithComparator(iter, size, additionalCharacterisitcs, comparator);
    }

    public static <K> ObjectSpliterator<K> asSpliteratorUnknownSize(ObjectIterator<? extends K> iter, int characterisitcs) {
        return new SpliteratorFromIterator(iter, characterisitcs);
    }

    public static <K> ObjectSpliterator<K> asSpliteratorFromSortedUnknownSize(ObjectIterator<? extends K> iter, int additionalCharacterisitcs, Comparator<? super K> comparator) {
        return new SpliteratorFromIteratorWithComparator(iter, additionalCharacterisitcs, comparator);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/ObjectSpliterators$IteratorFromSpliterator.class */
    private static final class IteratorFromSpliterator<K> implements ObjectIterator<K>, Consumer<K> {
        private final ObjectSpliterator<? extends K> spliterator;
        private K holder = null;
        private boolean hasPeeked = false;

        IteratorFromSpliterator(ObjectSpliterator<? extends K> spliterator) {
            this.spliterator = spliterator;
        }

        @Override // java.util.function.Consumer
        public void accept(K item) {
            this.holder = item;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            if (this.hasPeeked) {
                return true;
            }
            boolean hadElement = this.spliterator.tryAdvance(this);
            if (!hadElement) {
                return false;
            }
            this.hasPeeked = true;
            return true;
        }

        @Override // java.util.Iterator
        public K next() {
            if (this.hasPeeked) {
                this.hasPeeked = false;
                return this.holder;
            }
            boolean hadElement = this.spliterator.tryAdvance(this);
            if (!hadElement) {
                throw new NoSuchElementException();
            }
            return this.holder;
        }

        @Override // java.util.Iterator
        public void forEachRemaining(Consumer<? super K> action) {
            if (this.hasPeeked) {
                this.hasPeeked = false;
                action.accept((K) this.holder);
            }
            this.spliterator.forEachRemaining(action);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator
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

    public static <K> ObjectIterator<K> asIterator(ObjectSpliterator<? extends K> spliterator) {
        return new IteratorFromSpliterator(spliterator);
    }
}
