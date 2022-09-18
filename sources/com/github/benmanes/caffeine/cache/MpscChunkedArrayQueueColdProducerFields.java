package com.github.benmanes.caffeine.cache;

/* compiled from: MpscGrowableArrayQueue.java */
/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/MpscChunkedArrayQueueColdProducerFields.class */
abstract class MpscChunkedArrayQueueColdProducerFields<E> extends BaseMpscLinkedArrayQueue<E> {
    protected final long maxQueueCapacity;

    public MpscChunkedArrayQueueColdProducerFields(int initialCapacity, int maxCapacity) {
        super(initialCapacity);
        if (maxCapacity < 4) {
            throw new IllegalArgumentException("Max capacity must be 4 or more");
        }
        if (Caffeine.ceilingPowerOfTwo(initialCapacity) >= Caffeine.ceilingPowerOfTwo(maxCapacity)) {
            throw new IllegalArgumentException("Initial capacity cannot exceed maximum capacity(both rounded up to a power of 2)");
        }
        this.maxQueueCapacity = Caffeine.ceilingPowerOfTwo(maxCapacity) << 1;
    }
}
