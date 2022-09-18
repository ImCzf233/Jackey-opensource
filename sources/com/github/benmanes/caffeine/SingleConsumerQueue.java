package com.github.benmanes.caffeine;

import com.github.benmanes.caffeine.SCQHeader;
import com.github.benmanes.caffeine.base.UnsafeAccess;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

@Deprecated
/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/SingleConsumerQueue.class */
public final class SingleConsumerQueue<E> extends SCQHeader.HeadAndTailRef<E> implements Queue<E>, Serializable {
    static final int NCPU = Runtime.getRuntime().availableProcessors();
    static final int ARENA_LENGTH = ceilingPowerOfTwo((NCPU + 1) / 2);
    static final int ARENA_MASK = ARENA_LENGTH - 1;
    static final Function<?, ?> OPTIMISIC = Node::new;
    static final int SPINS;
    static final long PROBE;
    final AtomicReference<Node<E>>[] arena = new AtomicReference[ARENA_LENGTH];
    final Function<E, Node<E>> factory;
    static final long serialVersionUID = 1;

    static {
        SPINS = NCPU == 1 ? 0 : 2000;
        PROBE = UnsafeAccess.objectFieldOffset(Thread.class, "threadLocalRandomProbe");
    }

    static int ceilingPowerOfTwo(int x) {
        return 1 << (-Integer.numberOfLeadingZeros(x - 1));
    }

    private SingleConsumerQueue(Function<E, Node<E>> factory) {
        for (int i = 0; i < ARENA_LENGTH; i++) {
            this.arena[i] = new AtomicReference<>();
        }
        Node<E> node = new Node<>(null);
        this.factory = factory;
        lazySetTail(node);
        this.head = node;
    }

    public static <E> SingleConsumerQueue<E> optimistic() {
        return new SingleConsumerQueue<>(OPTIMISIC);
    }

    public static <E> SingleConsumerQueue<E> linearizable() {
        return new SingleConsumerQueue<>(LinearizableNode::new);
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean isEmpty() {
        return this.head == this.tail;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public int size() {
        Node<E> node;
        Node<E> cursor = this.head;
        Node<E> t = this.tail;
        int size = 0;
        while (cursor != t && size != Integer.MAX_VALUE) {
            Node<E> next = cursor.getNextRelaxed();
            if (next == null) {
                do {
                    node = cursor.next;
                    next = node;
                } while (node == null);
            }
            cursor = next;
            size++;
        }
        return size;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean contains(Object o) {
        if (o == null) {
            return false;
        }
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            if (o.equals(it.next())) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.Queue
    public E peek() {
        Node<E> node;
        Node<E> h = this.head;
        Node<E> t = this.tail;
        if (h == t) {
            return null;
        }
        Node<E> next = h.getNextRelaxed();
        if (next == null) {
            do {
                node = h.next;
                next = node;
            } while (node == null);
            return next.value;
        }
        return next.value;
    }

    @Override // java.util.Queue
    public boolean offer(E e) {
        Objects.requireNonNull(e);
        Node<E> node = this.factory.apply(e);
        append(node, node);
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x003d  */
    @Override // java.util.Queue
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public E poll() {
        /*
            r3 = this;
            r0 = r3
            com.github.benmanes.caffeine.SingleConsumerQueue$Node<E> r0 = r0.head
            r4 = r0
            r0 = r4
            com.github.benmanes.caffeine.SingleConsumerQueue$Node r0 = r0.getNextRelaxed()
            r5 = r0
            r0 = r5
            if (r0 != 0) goto L24
            r0 = r4
            r1 = r3
            com.github.benmanes.caffeine.SingleConsumerQueue$Node<E> r1 = r1.tail
            if (r0 != r1) goto L18
            r0 = 0
            return r0
        L18:
            r0 = r4
            com.github.benmanes.caffeine.SingleConsumerQueue$Node<E> r0 = r0.next
            r1 = r0
            r5 = r1
            if (r0 != 0) goto L24
            goto L18
        L24:
            r0 = r5
            E r0 = r0.value
            r6 = r0
            r0 = r5
            r1 = 0
            r0.value = r1
            r0 = r3
            r1 = r5
            r0.head = r1
            r0 = r3
            java.util.function.Function<E, com.github.benmanes.caffeine.SingleConsumerQueue$Node<E>> r0 = r0.factory
            java.util.function.Function<?, ?> r1 = com.github.benmanes.caffeine.SingleConsumerQueue.OPTIMISIC
            if (r0 != r1) goto L42
            r0 = r4
            r1 = 0
            r0.next = r1
        L42:
            r0 = r6
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.github.benmanes.caffeine.SingleConsumerQueue.poll():java.lang.Object");
    }

    @Override // java.util.AbstractQueue, java.util.AbstractCollection, java.util.Collection, java.util.Queue
    public boolean add(E e) {
        return offer(e);
    }

    @Override // java.util.AbstractQueue, java.util.AbstractCollection, java.util.Collection
    public boolean addAll(Collection<? extends E> c) {
        Objects.requireNonNull(c);
        Node<E> first = null;
        Node<E> last = null;
        for (E e : c) {
            Objects.requireNonNull(e);
            if (first == null) {
                first = this.factory.apply(e);
                last = first;
            } else {
                Node<E> newLast = new Node<>(e);
                last.lazySetNext(newLast);
                last = newLast;
            }
        }
        if (first == null) {
            return false;
        }
        append(first, last);
        return true;
    }

    void append(Node<E> first, Node<E> last) {
        Node<E> next;
        while (true) {
            Node<E> t = this.tail;
            if (casTail(t, last)) {
                t.lazySetNext(first);
                if (this.factory == OPTIMISIC) {
                    return;
                }
                while (true) {
                    first.complete();
                    if (first == last || (next = first.getNextRelaxed()) == null) {
                        return;
                    }
                    if (next.value == null) {
                        first.next = null;
                    }
                    first = next;
                }
            } else {
                Node<E> node = transferOrCombine(first, last);
                if (node == null) {
                    first.await();
                    return;
                } else if (node != first) {
                    last = node;
                }
            }
        }
    }

    Node<E> transferOrCombine(Node<E> first, Node<E> last) {
        int index = index();
        AtomicReference<Node<E>> slot = this.arena[index];
        while (true) {
            Node<E> found = slot.get();
            if (found == null) {
                if (slot.compareAndSet(null, first)) {
                    for (int spin = 0; spin < SPINS; spin++) {
                        if (slot.get() != first) {
                            return null;
                        }
                    }
                    if (!slot.compareAndSet(first, null)) {
                        return null;
                    }
                    return first;
                }
            } else if (slot.compareAndSet(found, null)) {
                last.lazySetNext(found);
                Node<E> last2 = findLast(found);
                for (int i = 1; i < ARENA_LENGTH; i++) {
                    AtomicReference<Node<E>> slot2 = this.arena[(i + index) & ARENA_MASK];
                    Node<E> found2 = slot2.get();
                    if (found2 != null && slot2.compareAndSet(found2, null)) {
                        last2.lazySetNext(found2);
                        last2 = findLast(found2);
                    }
                }
                return last2;
            }
        }
    }

    static int index() {
        int probe = UnsafeAccess.UNSAFE.getInt(Thread.currentThread(), PROBE);
        if (probe == 0) {
            ThreadLocalRandom.current();
            probe = UnsafeAccess.UNSAFE.getInt(Thread.currentThread(), PROBE);
        }
        return probe & ARENA_MASK;
    }

    static <E> Node<E> findLast(Node<E> node) {
        while (true) {
            Node<E> next = node.getNextRelaxed();
            if (next != null) {
                node = next;
            } else {
                return node;
            }
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public Iterator<E> iterator() {
        return new Iterator<E>() { // from class: com.github.benmanes.caffeine.SingleConsumerQueue.1
            Node<E> prev;

            /* renamed from: t */
            Node<E> f3t;
            Node<E> cursor;
            boolean failOnRemoval = true;

            {
                SingleConsumerQueue.this = this;
                this.f3t = SingleConsumerQueue.this.tail;
                this.cursor = SingleConsumerQueue.this.head;
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.cursor != this.f3t;
            }

            @Override // java.util.Iterator
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                advance();
                this.failOnRemoval = false;
                return this.cursor.value;
            }

            private void advance() {
                if (this.prev == null || !this.failOnRemoval) {
                    this.prev = this.cursor;
                }
                this.cursor = awaitNext();
            }

            @Override // java.util.Iterator
            public void remove() {
                if (this.failOnRemoval) {
                    throw new IllegalStateException();
                }
                this.failOnRemoval = true;
                this.cursor.value = null;
                if (this.f3t == this.cursor) {
                    this.prev.lazySetNext(null);
                    if (SingleConsumerQueue.this.casTail(this.f3t, this.prev)) {
                        return;
                    }
                }
                this.prev.lazySetNext(awaitNext());
            }

            Node<E> awaitNext() {
                if (this.cursor.getNextRelaxed() == null) {
                    do {
                    } while (this.cursor.next == null);
                    return this.cursor.getNextRelaxed();
                }
                return this.cursor.getNextRelaxed();
            }
        };
    }

    Object writeReplace() {
        return new SerializationProxy(this);
    }

    private void readObject(ObjectInputStream stream) throws InvalidObjectException {
        throw new InvalidObjectException("Proxy required");
    }

    /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/SingleConsumerQueue$SerializationProxy.class */
    static final class SerializationProxy<E> implements Serializable {
        final boolean linearizable;
        final List<E> elements;
        static final long serialVersionUID = 1;

        SerializationProxy(SingleConsumerQueue<E> queue) {
            this.linearizable = queue.factory.apply(null) instanceof LinearizableNode;
            this.elements = new ArrayList(queue);
        }

        Object readResolve() {
            SingleConsumerQueue<E> queue = this.linearizable ? SingleConsumerQueue.linearizable() : SingleConsumerQueue.optimistic();
            queue.addAll(this.elements);
            return queue;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/SingleConsumerQueue$Node.class */
    public static class Node<E> {
        static final long NEXT_OFFSET = UnsafeAccess.objectFieldOffset(Node.class, "next");
        E value;
        volatile Node<E> next;

        Node(E value) {
            this.value = value;
        }

        Node<E> getNextRelaxed() {
            return (Node) UnsafeAccess.UNSAFE.getObject(this, NEXT_OFFSET);
        }

        void lazySetNext(Node<E> newNext) {
            UnsafeAccess.UNSAFE.putOrderedObject(this, NEXT_OFFSET, newNext);
        }

        void complete() {
        }

        void await() {
        }

        boolean isDone() {
            return true;
        }

        public String toString() {
            return getClass().getSimpleName() + "[" + this.value + "]";
        }
    }

    /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/SingleConsumerQueue$LinearizableNode.class */
    public static final class LinearizableNode<E> extends Node<E> {
        volatile boolean done;

        LinearizableNode(E value) {
            super(value);
        }

        @Override // com.github.benmanes.caffeine.SingleConsumerQueue.Node
        void complete() {
            this.done = true;
        }

        @Override // com.github.benmanes.caffeine.SingleConsumerQueue.Node
        void await() {
            do {
            } while (!this.done);
        }

        @Override // com.github.benmanes.caffeine.SingleConsumerQueue.Node
        boolean isDone() {
            return this.done;
        }
    }
}
