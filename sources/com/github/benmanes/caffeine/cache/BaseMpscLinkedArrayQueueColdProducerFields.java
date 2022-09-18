package com.github.benmanes.caffeine.cache;

/* compiled from: MpscGrowableArrayQueue.java */
/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/BaseMpscLinkedArrayQueueColdProducerFields.class */
abstract class BaseMpscLinkedArrayQueueColdProducerFields<E> extends BaseMpscLinkedArrayQueuePad3<E> {
    protected volatile long producerLimit;
    protected long producerMask;
    protected E[] producerBuffer;
}
