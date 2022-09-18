package com.viaversion.viaversion.libs.fastutil;

import java.util.Comparator;
import java.util.NoSuchElementException;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/IndirectPriorityQueues.class */
public class IndirectPriorityQueues {
    public static final EmptyIndirectPriorityQueue EMPTY_QUEUE = new EmptyIndirectPriorityQueue();

    private IndirectPriorityQueues() {
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/IndirectPriorityQueues$EmptyIndirectPriorityQueue.class */
    public static class EmptyIndirectPriorityQueue implements IndirectPriorityQueue {
        protected EmptyIndirectPriorityQueue() {
        }

        @Override // com.viaversion.viaversion.libs.fastutil.IndirectPriorityQueue
        public void enqueue(int i) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.IndirectPriorityQueue
        public int dequeue() {
            throw new NoSuchElementException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.IndirectPriorityQueue
        public boolean isEmpty() {
            return true;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.IndirectPriorityQueue
        public int size() {
            return 0;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.IndirectPriorityQueue
        public boolean contains(int index) {
            return false;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.IndirectPriorityQueue
        public void clear() {
        }

        @Override // com.viaversion.viaversion.libs.fastutil.IndirectPriorityQueue
        public int first() {
            throw new NoSuchElementException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.IndirectPriorityQueue
        public int last() {
            throw new NoSuchElementException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.IndirectPriorityQueue
        public void changed() {
            throw new NoSuchElementException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.IndirectPriorityQueue
        public void allChanged() {
        }

        @Override // com.viaversion.viaversion.libs.fastutil.IndirectPriorityQueue
        public Comparator<?> comparator() {
            return null;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.IndirectPriorityQueue
        public void changed(int i) {
            throw new IllegalArgumentException("Index " + i + " is not in the queue");
        }

        @Override // com.viaversion.viaversion.libs.fastutil.IndirectPriorityQueue
        public boolean remove(int i) {
            return false;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.IndirectPriorityQueue
        public int front(int[] a) {
            return 0;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/IndirectPriorityQueues$SynchronizedIndirectPriorityQueue.class */
    public static class SynchronizedIndirectPriorityQueue<K> implements IndirectPriorityQueue<K> {
        public static final long serialVersionUID = -7046029254386353129L;

        /* renamed from: q */
        protected final IndirectPriorityQueue<K> f63q;
        protected final Object sync;

        protected SynchronizedIndirectPriorityQueue(IndirectPriorityQueue<K> q, Object sync) {
            this.f63q = q;
            this.sync = sync;
        }

        protected SynchronizedIndirectPriorityQueue(IndirectPriorityQueue<K> q) {
            this.f63q = q;
            this.sync = this;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.IndirectPriorityQueue
        public void enqueue(int x) {
            synchronized (this.sync) {
                this.f63q.enqueue(x);
            }
        }

        @Override // com.viaversion.viaversion.libs.fastutil.IndirectPriorityQueue
        public int dequeue() {
            int dequeue;
            synchronized (this.sync) {
                dequeue = this.f63q.dequeue();
            }
            return dequeue;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.IndirectPriorityQueue
        public boolean contains(int index) {
            boolean contains;
            synchronized (this.sync) {
                contains = this.f63q.contains(index);
            }
            return contains;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.IndirectPriorityQueue
        public int first() {
            int first;
            synchronized (this.sync) {
                first = this.f63q.first();
            }
            return first;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.IndirectPriorityQueue
        public int last() {
            int last;
            synchronized (this.sync) {
                last = this.f63q.last();
            }
            return last;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.IndirectPriorityQueue
        public boolean isEmpty() {
            boolean isEmpty;
            synchronized (this.sync) {
                isEmpty = this.f63q.isEmpty();
            }
            return isEmpty;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.IndirectPriorityQueue
        public int size() {
            int size;
            synchronized (this.sync) {
                size = this.f63q.size();
            }
            return size;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.IndirectPriorityQueue
        public void clear() {
            synchronized (this.sync) {
                this.f63q.clear();
            }
        }

        @Override // com.viaversion.viaversion.libs.fastutil.IndirectPriorityQueue
        public void changed() {
            synchronized (this.sync) {
                this.f63q.changed();
            }
        }

        @Override // com.viaversion.viaversion.libs.fastutil.IndirectPriorityQueue
        public void allChanged() {
            synchronized (this.sync) {
                this.f63q.allChanged();
            }
        }

        @Override // com.viaversion.viaversion.libs.fastutil.IndirectPriorityQueue
        public void changed(int i) {
            synchronized (this.sync) {
                this.f63q.changed(i);
            }
        }

        @Override // com.viaversion.viaversion.libs.fastutil.IndirectPriorityQueue
        public boolean remove(int i) {
            boolean remove;
            synchronized (this.sync) {
                remove = this.f63q.remove(i);
            }
            return remove;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.IndirectPriorityQueue
        public Comparator<? super K> comparator() {
            Comparator<? super K> comparator;
            synchronized (this.sync) {
                comparator = this.f63q.comparator();
            }
            return comparator;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.IndirectPriorityQueue
        public int front(int[] a) {
            return this.f63q.front(a);
        }
    }

    public static <K> IndirectPriorityQueue<K> synchronize(IndirectPriorityQueue<K> q) {
        return new SynchronizedIndirectPriorityQueue(q);
    }

    public static <K> IndirectPriorityQueue<K> synchronize(IndirectPriorityQueue<K> q, Object sync) {
        return new SynchronizedIndirectPriorityQueue(q, sync);
    }
}
