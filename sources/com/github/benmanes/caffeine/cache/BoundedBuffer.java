package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.BBHeader;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.function.Consumer;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/BoundedBuffer.class */
final class BoundedBuffer<E> extends StripedBuffer<E> {
    static final int BUFFER_SIZE = 16;
    static final int MASK = 15;

    @Override // com.github.benmanes.caffeine.cache.StripedBuffer
    protected Buffer<E> create(E e) {
        return new RingBuffer(e);
    }

    /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/BoundedBuffer$RingBuffer.class */
    static final class RingBuffer<E> extends BBHeader.ReadAndWriteCounterRef implements Buffer<E> {
        final AtomicReferenceArray<E> buffer = new AtomicReferenceArray<>(16);

        public RingBuffer(E e) {
            this.buffer.lazySet(0, e);
        }

        @Override // com.github.benmanes.caffeine.cache.Buffer
        public int offer(E e) {
            long head = this.readCounter;
            long tail = relaxedWriteCounter();
            long size = tail - head;
            if (size >= 16) {
                return 1;
            }
            if (casWriteCounter(tail, tail + 1)) {
                int index = (int) (tail & 15);
                this.buffer.lazySet(index, e);
                return 0;
            }
            return -1;
        }

        @Override // com.github.benmanes.caffeine.cache.Buffer
        public void drainTo(Consumer<E> consumer) {
            long head = this.readCounter;
            long tail = relaxedWriteCounter();
            long size = tail - head;
            if (size == 0) {
                return;
            }
            do {
                int index = (int) (head & 15);
                E e = this.buffer.get(index);
                if (e == null) {
                    break;
                }
                this.buffer.lazySet(index, null);
                consumer.accept(e);
                head++;
            } while (head != tail);
            lazySetReadCounter(head);
        }

        @Override // com.github.benmanes.caffeine.cache.Buffer
        public int reads() {
            return (int) this.readCounter;
        }

        @Override // com.github.benmanes.caffeine.cache.Buffer
        public int writes() {
            return (int) this.writeCounter;
        }
    }
}
