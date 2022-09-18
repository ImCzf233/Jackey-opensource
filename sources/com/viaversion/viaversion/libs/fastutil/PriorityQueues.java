package com.viaversion.viaversion.libs.fastutil;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Comparator;
import java.util.NoSuchElementException;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/PriorityQueues.class */
public class PriorityQueues {
    public static final EmptyPriorityQueue EMPTY_QUEUE = new EmptyPriorityQueue();

    private PriorityQueues() {
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/PriorityQueues$EmptyPriorityQueue.class */
    public static class EmptyPriorityQueue implements PriorityQueue, Serializable {
        private static final long serialVersionUID = 0;

        protected EmptyPriorityQueue() {
        }

        @Override // com.viaversion.viaversion.libs.fastutil.PriorityQueue
        public void enqueue(Object o) {
            throw new UnsupportedOperationException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.PriorityQueue
        public Object dequeue() {
            throw new NoSuchElementException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.PriorityQueue
        public boolean isEmpty() {
            return true;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.PriorityQueue
        public int size() {
            return 0;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.PriorityQueue
        public void clear() {
        }

        @Override // com.viaversion.viaversion.libs.fastutil.PriorityQueue
        public Object first() {
            throw new NoSuchElementException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.PriorityQueue
        public Object last() {
            throw new NoSuchElementException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.PriorityQueue
        public void changed() {
            throw new NoSuchElementException();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.PriorityQueue
        public Comparator<?> comparator() {
            return null;
        }

        public Object clone() {
            return PriorityQueues.EMPTY_QUEUE;
        }

        public int hashCode() {
            return 0;
        }

        public boolean equals(Object o) {
            return (o instanceof PriorityQueue) && ((PriorityQueue) o).isEmpty();
        }

        private Object readResolve() {
            return PriorityQueues.EMPTY_QUEUE;
        }
    }

    public static <K> PriorityQueue<K> emptyQueue() {
        return EMPTY_QUEUE;
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/PriorityQueues$SynchronizedPriorityQueue.class */
    public static class SynchronizedPriorityQueue<K> implements PriorityQueue<K>, Serializable {
        public static final long serialVersionUID = -7046029254386353129L;

        /* renamed from: q */
        protected final PriorityQueue<K> f64q;
        protected final Object sync;

        protected SynchronizedPriorityQueue(PriorityQueue<K> q, Object sync) {
            this.f64q = q;
            this.sync = sync;
        }

        protected SynchronizedPriorityQueue(PriorityQueue<K> q) {
            this.f64q = q;
            this.sync = this;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.PriorityQueue
        public void enqueue(K x) {
            synchronized (this.sync) {
                this.f64q.enqueue(x);
            }
        }

        @Override // com.viaversion.viaversion.libs.fastutil.PriorityQueue
        public K dequeue() {
            K dequeue;
            synchronized (this.sync) {
                dequeue = this.f64q.dequeue();
            }
            return dequeue;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.PriorityQueue
        public K first() {
            K first;
            synchronized (this.sync) {
                first = this.f64q.first();
            }
            return first;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.PriorityQueue
        public K last() {
            K last;
            synchronized (this.sync) {
                last = this.f64q.last();
            }
            return last;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.PriorityQueue
        public boolean isEmpty() {
            boolean isEmpty;
            synchronized (this.sync) {
                isEmpty = this.f64q.isEmpty();
            }
            return isEmpty;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.PriorityQueue
        public int size() {
            int size;
            synchronized (this.sync) {
                size = this.f64q.size();
            }
            return size;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.PriorityQueue
        public void clear() {
            synchronized (this.sync) {
                this.f64q.clear();
            }
        }

        @Override // com.viaversion.viaversion.libs.fastutil.PriorityQueue
        public void changed() {
            synchronized (this.sync) {
                this.f64q.changed();
            }
        }

        @Override // com.viaversion.viaversion.libs.fastutil.PriorityQueue
        public Comparator<? super K> comparator() {
            Comparator<? super K> comparator;
            synchronized (this.sync) {
                comparator = this.f64q.comparator();
            }
            return comparator;
        }

        public String toString() {
            String obj;
            synchronized (this.sync) {
                obj = this.f64q.toString();
            }
            return obj;
        }

        public int hashCode() {
            int hashCode;
            synchronized (this.sync) {
                hashCode = this.f64q.hashCode();
            }
            return hashCode;
        }

        public boolean equals(Object o) {
            boolean equals;
            if (o == this) {
                return true;
            }
            synchronized (this.sync) {
                equals = this.f64q.equals(o);
            }
            return equals;
        }

        private void writeObject(ObjectOutputStream s) throws IOException {
            synchronized (this.sync) {
                s.defaultWriteObject();
            }
        }
    }

    public static <K> PriorityQueue<K> synchronize(PriorityQueue<K> q) {
        return new SynchronizedPriorityQueue(q);
    }

    public static <K> PriorityQueue<K> synchronize(PriorityQueue<K> q, Object sync) {
        return new SynchronizedPriorityQueue(q, sync);
    }
}
