package com.github.benmanes.caffeine.cache;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/MpscGrowableArrayQueue.class */
final class MpscGrowableArrayQueue<E> extends MpscChunkedArrayQueue<E> {
    public MpscGrowableArrayQueue(int initialCapacity, int maxCapacity) {
        super(initialCapacity, maxCapacity);
    }

    @Override // com.github.benmanes.caffeine.cache.MpscChunkedArrayQueue, com.github.benmanes.caffeine.cache.BaseMpscLinkedArrayQueue
    protected int getNextBufferSize(E[] buffer) {
        long maxSize = this.maxQueueCapacity / 2;
        if (buffer.length > maxSize) {
            throw new IllegalStateException();
        }
        int newSize = 2 * (buffer.length - 1);
        return newSize + 1;
    }

    @Override // com.github.benmanes.caffeine.cache.MpscChunkedArrayQueue, com.github.benmanes.caffeine.cache.BaseMpscLinkedArrayQueue
    protected long getCurrentBufferCapacity(long mask) {
        return mask + 2 == this.maxQueueCapacity ? this.maxQueueCapacity : mask;
    }
}
