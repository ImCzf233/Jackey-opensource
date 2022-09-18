package com.github.benmanes.caffeine.cache;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: MpscGrowableArrayQueue.java */
/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/BaseMpscLinkedArrayQueueConsumerFields.class */
public abstract class BaseMpscLinkedArrayQueueConsumerFields<E> extends BaseMpscLinkedArrayQueuePad2<E> {
    protected long consumerMask;
    protected E[] consumerBuffer;
    protected long consumerIndex;
}
