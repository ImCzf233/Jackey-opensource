package com.github.benmanes.caffeine.cache;

import java.util.Comparator;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/LinkedDeque.class */
interface LinkedDeque<E> extends Deque<E> {
    boolean isFirst(E e);

    boolean isLast(E e);

    void moveToFront(E e);

    void moveToBack(E e);

    E getPrevious(E e);

    void setPrevious(E e, E e2);

    E getNext(E e);

    void setNext(E e, E e2);

    @Override // java.util.Collection, java.lang.Iterable, com.github.benmanes.caffeine.cache.LinkedDeque, java.util.Deque
    PeekingIterator<E> iterator();

    @Override // java.util.Deque
    PeekingIterator<E> descendingIterator();

    /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/LinkedDeque$PeekingIterator.class */
    public interface PeekingIterator<E> extends Iterator<E> {
        E peek();

        static <E> PeekingIterator<E> concat(PeekingIterator<E> first, final PeekingIterator<E> second) {
            return new PeekingIterator<E>() { // from class: com.github.benmanes.caffeine.cache.LinkedDeque.PeekingIterator.1
                @Override // java.util.Iterator
                public boolean hasNext() {
                    return PeekingIterator.this.hasNext() || second.hasNext();
                }

                @Override // java.util.Iterator
                public E next() {
                    if (PeekingIterator.this.hasNext()) {
                        return PeekingIterator.this.next();
                    }
                    if (second.hasNext()) {
                        return second.next();
                    }
                    throw new NoSuchElementException();
                }

                @Override // com.github.benmanes.caffeine.cache.LinkedDeque.PeekingIterator
                public E peek() {
                    return PeekingIterator.this.hasNext() ? (E) PeekingIterator.this.peek() : (E) second.peek();
                }
            };
        }

        static <E> PeekingIterator<E> comparing(PeekingIterator<E> first, final PeekingIterator<E> second, final Comparator<E> comparator) {
            return new PeekingIterator<E>() { // from class: com.github.benmanes.caffeine.cache.LinkedDeque.PeekingIterator.2
                @Override // java.util.Iterator
                public boolean hasNext() {
                    return PeekingIterator.this.hasNext() || second.hasNext();
                }

                @Override // java.util.Iterator
                public E next() {
                    if (!PeekingIterator.this.hasNext()) {
                        return second.next();
                    }
                    if (!second.hasNext()) {
                        return PeekingIterator.this.next();
                    }
                    boolean greaterOrEqual = comparator.compare(PeekingIterator.this.peek(), second.peek()) >= 0;
                    return greaterOrEqual ? PeekingIterator.this.next() : second.next();
                }

                @Override // com.github.benmanes.caffeine.cache.LinkedDeque.PeekingIterator
                public E peek() {
                    if (!PeekingIterator.this.hasNext()) {
                        return (E) second.peek();
                    }
                    if (!second.hasNext()) {
                        return (E) PeekingIterator.this.peek();
                    }
                    boolean greaterOrEqual = comparator.compare(PeekingIterator.this.peek(), second.peek()) >= 0;
                    return greaterOrEqual ? (E) PeekingIterator.this.peek() : (E) second.peek();
                }
            };
        }
    }
}
