package com.github.benmanes.caffeine.cache;

import java.lang.reflect.Field;
import java.util.Iterator;
import kotlin.jvm.internal.LongCompanionObject;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: MpscGrowableArrayQueue.java */
/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/BaseMpscLinkedArrayQueue.class */
public abstract class BaseMpscLinkedArrayQueue<E> extends BaseMpscLinkedArrayQueueColdProducerFields<E> {
    private static final long P_INDEX_OFFSET;
    private static final long C_INDEX_OFFSET;
    private static final long P_LIMIT_OFFSET;
    private static final Object JUMP;

    protected abstract long availableInQueue(long j, long j2);

    public abstract int capacity();

    protected abstract int getNextBufferSize(E[] eArr);

    protected abstract long getCurrentBufferCapacity(long j);

    static {
        try {
            Field iField = BaseMpscLinkedArrayQueueProducerFields.class.getDeclaredField("producerIndex");
            P_INDEX_OFFSET = UnsafeAccess.UNSAFE.objectFieldOffset(iField);
            try {
                Field iField2 = BaseMpscLinkedArrayQueueConsumerFields.class.getDeclaredField("consumerIndex");
                C_INDEX_OFFSET = UnsafeAccess.UNSAFE.objectFieldOffset(iField2);
                try {
                    Field iField3 = BaseMpscLinkedArrayQueueColdProducerFields.class.getDeclaredField("producerLimit");
                    P_LIMIT_OFFSET = UnsafeAccess.UNSAFE.objectFieldOffset(iField3);
                    JUMP = new Object();
                } catch (NoSuchFieldException e) {
                    throw new RuntimeException(e);
                }
            } catch (NoSuchFieldException e2) {
                throw new RuntimeException(e2);
            }
        } catch (NoSuchFieldException e3) {
            throw new RuntimeException(e3);
        }
    }

    public BaseMpscLinkedArrayQueue(int initialCapacity) {
        if (initialCapacity < 2) {
            throw new IllegalArgumentException("Initial capacity must be 2 or more");
        }
        int p2capacity = Caffeine.ceilingPowerOfTwo(initialCapacity);
        long mask = (p2capacity - 1) << 1;
        E[] buffer = (E[]) allocate(p2capacity + 1);
        this.producerBuffer = buffer;
        this.producerMask = mask;
        this.consumerBuffer = buffer;
        this.consumerMask = mask;
        soProducerLimit(mask);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public final Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode());
    }

    @Override // java.util.Queue
    public boolean offer(E e) {
        if (null == e) {
            throw new NullPointerException();
        }
        while (true) {
            long producerLimit = lvProducerLimit();
            long pIndex = lvProducerIndex();
            if ((pIndex & 1) != 1) {
                long mask = this.producerMask;
                E[] buffer = this.producerBuffer;
                if (producerLimit <= pIndex) {
                    int result = offerSlowPath(mask, pIndex, producerLimit);
                    switch (result) {
                        case 2:
                            return false;
                        case 3:
                            resize(mask, buffer, pIndex, e);
                            return true;
                    }
                }
                if (casProducerIndex(pIndex, pIndex + 2)) {
                    long offset = modifiedCalcElementOffset(pIndex, mask);
                    UnsafeRefArrayAccess.soElement(buffer, offset, e);
                    return true;
                }
            }
        }
    }

    private int offerSlowPath(long mask, long pIndex, long producerLimit) {
        long cIndex = lvConsumerIndex();
        long bufferCapacity = getCurrentBufferCapacity(mask);
        int result = 0;
        if (cIndex + bufferCapacity > pIndex) {
            if (!casProducerLimit(producerLimit, cIndex + bufferCapacity)) {
                result = 1;
            }
        } else if (availableInQueue(pIndex, cIndex) <= 0) {
            result = 2;
        } else if (casProducerIndex(pIndex, pIndex + 1)) {
            result = 3;
        } else {
            result = 1;
        }
        return result;
    }

    private static long modifiedCalcElementOffset(long index, long mask) {
        return UnsafeRefArrayAccess.REF_ARRAY_BASE + ((index & mask) << (UnsafeRefArrayAccess.REF_ELEMENT_SHIFT - 1));
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0059  */
    @Override // java.util.Queue
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public E poll() {
        /*
            r6 = this;
            r0 = r6
            E[] r0 = r0.consumerBuffer
            r7 = r0
            r0 = r6
            long r0 = r0.consumerIndex
            r8 = r0
            r0 = r6
            long r0 = r0.consumerMask
            r10 = r0
            r0 = r8
            r1 = r10
            long r0 = modifiedCalcElementOffset(r0, r1)
            r12 = r0
            r0 = r7
            r1 = r12
            java.lang.Object r0 = com.github.benmanes.caffeine.cache.UnsafeRefArrayAccess.lvElement(r0, r1)
            r14 = r0
            r0 = r14
            if (r0 != 0) goto L40
            r0 = r8
            r1 = r6
            long r1 = r1.lvProducerIndex()
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r0 == 0) goto L3e
        L2e:
            r0 = r7
            r1 = r12
            java.lang.Object r0 = com.github.benmanes.caffeine.cache.UnsafeRefArrayAccess.lvElement(r0, r1)
            r14 = r0
            r0 = r14
            if (r0 == 0) goto L2e
            goto L40
        L3e:
            r0 = 0
            return r0
        L40:
            r0 = r14
            java.lang.Object r1 = com.github.benmanes.caffeine.cache.BaseMpscLinkedArrayQueue.JUMP
            if (r0 != r1) goto L59
            r0 = r6
            r1 = r7
            r2 = r10
            java.lang.Object[] r0 = r0.getNextBuffer(r1, r2)
            r15 = r0
            r0 = r6
            r1 = r15
            r2 = r8
            java.lang.Object r0 = r0.newBufferPoll(r1, r2)
            return r0
        L59:
            r0 = r7
            r1 = r12
            r2 = 0
            com.github.benmanes.caffeine.cache.UnsafeRefArrayAccess.soElement(r0, r1, r2)
            r0 = r6
            r1 = r8
            r2 = 2
            long r1 = r1 + r2
            r0.soConsumerIndex(r1)
            r0 = r14
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.github.benmanes.caffeine.cache.BaseMpscLinkedArrayQueue.poll():java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0052  */
    @Override // java.util.Queue
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public E peek() {
        /*
            r6 = this;
            r0 = r6
            E[] r0 = r0.consumerBuffer
            r7 = r0
            r0 = r6
            long r0 = r0.consumerIndex
            r8 = r0
            r0 = r6
            long r0 = r0.consumerMask
            r10 = r0
            r0 = r8
            r1 = r10
            long r0 = modifiedCalcElementOffset(r0, r1)
            r12 = r0
            r0 = r7
            r1 = r12
            java.lang.Object r0 = com.github.benmanes.caffeine.cache.UnsafeRefArrayAccess.lvElement(r0, r1)
            r14 = r0
            r0 = r14
            if (r0 != 0) goto L3d
            r0 = r8
            r1 = r6
            long r1 = r1.lvProducerIndex()
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r0 == 0) goto L3d
        L2e:
            r0 = r7
            r1 = r12
            java.lang.Object r0 = com.github.benmanes.caffeine.cache.UnsafeRefArrayAccess.lvElement(r0, r1)
            r1 = r0
            r14 = r1
            if (r0 != 0) goto L3d
            goto L2e
        L3d:
            r0 = r14
            java.lang.Object r1 = com.github.benmanes.caffeine.cache.BaseMpscLinkedArrayQueue.JUMP
            if (r0 != r1) goto L52
            r0 = r6
            r1 = r6
            r2 = r7
            r3 = r10
            java.lang.Object[] r1 = r1.getNextBuffer(r2, r3)
            r2 = r8
            java.lang.Object r0 = r0.newBufferPeek(r1, r2)
            return r0
        L52:
            r0 = r14
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.github.benmanes.caffeine.cache.BaseMpscLinkedArrayQueue.peek():java.lang.Object");
    }

    private E[] getNextBuffer(E[] buffer, long mask) {
        long nextArrayOffset = nextArrayOffset(mask);
        E[] nextBuffer = (E[]) ((Object[]) UnsafeRefArrayAccess.lvElement(buffer, nextArrayOffset));
        UnsafeRefArrayAccess.soElement(buffer, nextArrayOffset, null);
        return nextBuffer;
    }

    private static long nextArrayOffset(long mask) {
        return modifiedCalcElementOffset(mask + 2, LongCompanionObject.MAX_VALUE);
    }

    private E newBufferPoll(E[] nextBuffer, long index) {
        long offsetInNew = newBufferAndOffset(nextBuffer, index);
        E n = (E) UnsafeRefArrayAccess.lvElement(nextBuffer, offsetInNew);
        if (n == null) {
            throw new IllegalStateException("new buffer must have at least one element");
        }
        UnsafeRefArrayAccess.soElement(nextBuffer, offsetInNew, null);
        soConsumerIndex(index + 2);
        return n;
    }

    private E newBufferPeek(E[] nextBuffer, long index) {
        long offsetInNew = newBufferAndOffset(nextBuffer, index);
        E n = (E) UnsafeRefArrayAccess.lvElement(nextBuffer, offsetInNew);
        if (null == n) {
            throw new IllegalStateException("new buffer must have at least one element");
        }
        return n;
    }

    private long newBufferAndOffset(E[] nextBuffer, long index) {
        this.consumerBuffer = nextBuffer;
        this.consumerMask = (nextBuffer.length - 2) << 1;
        long offsetInNew = modifiedCalcElementOffset(index, this.consumerMask);
        return offsetInNew;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final int size() {
        long before;
        long currentProducerIndex;
        long after = lvConsumerIndex();
        do {
            before = after;
            currentProducerIndex = lvProducerIndex();
            after = lvConsumerIndex();
        } while (before != after);
        long size = (currentProducerIndex - after) >> 1;
        if (size > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) size;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final boolean isEmpty() {
        return lvConsumerIndex() == lvProducerIndex();
    }

    private long lvProducerIndex() {
        return UnsafeAccess.UNSAFE.getLongVolatile(this, P_INDEX_OFFSET);
    }

    private long lvConsumerIndex() {
        return UnsafeAccess.UNSAFE.getLongVolatile(this, C_INDEX_OFFSET);
    }

    private void soProducerIndex(long v) {
        UnsafeAccess.UNSAFE.putOrderedLong(this, P_INDEX_OFFSET, v);
    }

    private boolean casProducerIndex(long expect, long newValue) {
        return UnsafeAccess.UNSAFE.compareAndSwapLong(this, P_INDEX_OFFSET, expect, newValue);
    }

    private void soConsumerIndex(long v) {
        UnsafeAccess.UNSAFE.putOrderedLong(this, C_INDEX_OFFSET, v);
    }

    private long lvProducerLimit() {
        return this.producerLimit;
    }

    private boolean casProducerLimit(long expect, long newValue) {
        return UnsafeAccess.UNSAFE.compareAndSwapLong(this, P_LIMIT_OFFSET, expect, newValue);
    }

    private void soProducerLimit(long v) {
        UnsafeAccess.UNSAFE.putOrderedLong(this, P_LIMIT_OFFSET, v);
    }

    public long currentProducerIndex() {
        return lvProducerIndex() / 2;
    }

    public long currentConsumerIndex() {
        return lvConsumerIndex() / 2;
    }

    public boolean relaxedOffer(E e) {
        return offer(e);
    }

    public E relaxedPoll() {
        E[] buffer = this.consumerBuffer;
        long index = this.consumerIndex;
        long mask = this.consumerMask;
        long offset = modifiedCalcElementOffset(index, mask);
        E e = (E) UnsafeRefArrayAccess.lvElement(buffer, offset);
        if (e == null) {
            return null;
        }
        if (e == JUMP) {
            E[] nextBuffer = getNextBuffer(buffer, mask);
            return newBufferPoll(nextBuffer, index);
        }
        UnsafeRefArrayAccess.soElement(buffer, offset, null);
        soConsumerIndex(index + 2);
        return e;
    }

    public E relaxedPeek() {
        E[] buffer = this.consumerBuffer;
        long index = this.consumerIndex;
        long mask = this.consumerMask;
        long offset = modifiedCalcElementOffset(index, mask);
        E e = (E) UnsafeRefArrayAccess.lvElement(buffer, offset);
        if (e == JUMP) {
            return newBufferPeek(getNextBuffer(buffer, mask), index);
        }
        return e;
    }

    private void resize(long oldMask, E[] oldBuffer, long pIndex, E e) {
        int newBufferLength = getNextBufferSize(oldBuffer);
        E[] newBuffer = (E[]) allocate(newBufferLength);
        this.producerBuffer = newBuffer;
        int newMask = (newBufferLength - 2) << 1;
        this.producerMask = newMask;
        long offsetInOld = modifiedCalcElementOffset(pIndex, oldMask);
        long offsetInNew = modifiedCalcElementOffset(pIndex, newMask);
        UnsafeRefArrayAccess.soElement(newBuffer, offsetInNew, e);
        UnsafeRefArrayAccess.soElement(oldBuffer, nextArrayOffset(oldMask), newBuffer);
        long cIndex = lvConsumerIndex();
        long availableInQueue = availableInQueue(pIndex, cIndex);
        if (availableInQueue <= 0) {
            throw new IllegalStateException();
        }
        soProducerLimit(pIndex + Math.min(newMask, availableInQueue));
        soProducerIndex(pIndex + 2);
        UnsafeRefArrayAccess.soElement(oldBuffer, offsetInOld, JUMP);
    }

    public static <E> E[] allocate(int capacity) {
        return (E[]) new Object[capacity];
    }
}
