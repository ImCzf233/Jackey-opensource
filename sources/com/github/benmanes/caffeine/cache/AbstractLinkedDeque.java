package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.LinkedDeque;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.NoSuchElementException;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/AbstractLinkedDeque.class */
public abstract class AbstractLinkedDeque<E> extends AbstractCollection<E> implements LinkedDeque<E> {
    E first;
    E last;

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Deque
    public abstract boolean contains(Object obj);

    void linkFirst(E e) {
        E f = this.first;
        this.first = e;
        if (f == null) {
            this.last = e;
            return;
        }
        setPrevious(f, e);
        setNext(e, f);
    }

    void linkLast(E e) {
        E l = this.last;
        this.last = e;
        if (l == null) {
            this.first = e;
            return;
        }
        setNext(l, e);
        setPrevious(e, l);
    }

    E unlinkFirst() {
        E f = this.first;
        E next = getNext(f);
        setNext(f, null);
        this.first = next;
        if (next == null) {
            this.last = null;
        } else {
            setPrevious(next, null);
        }
        return f;
    }

    E unlinkLast() {
        E l = this.last;
        E prev = getPrevious(l);
        setPrevious(l, null);
        this.last = prev;
        if (prev == null) {
            this.first = null;
        } else {
            setNext(prev, null);
        }
        return l;
    }

    public void unlink(E e) {
        E prev = getPrevious(e);
        E next = getNext(e);
        if (prev == null) {
            this.first = next;
        } else {
            setNext(prev, next);
            setPrevious(e, null);
        }
        if (next == null) {
            this.last = prev;
            return;
        }
        setPrevious(next, prev);
        setNext(e, null);
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean isEmpty() {
        return this.first == null;
    }

    void checkNotEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Deque
    public int size() {
        int size = 0;
        E e = this.first;
        while (true) {
            E e2 = e;
            if (e2 != null) {
                size++;
                e = getNext(e2);
            } else {
                return size;
            }
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public void clear() {
        E e = this.first;
        while (true) {
            E e2 = e;
            if (e2 != null) {
                E next = getNext(e2);
                setPrevious(e2, null);
                setNext(e2, null);
                e = next;
            } else {
                this.last = null;
                this.first = null;
                return;
            }
        }
    }

    @Override // com.github.benmanes.caffeine.cache.LinkedDeque
    public boolean isFirst(E e) {
        return e != null && e == this.first;
    }

    @Override // com.github.benmanes.caffeine.cache.LinkedDeque
    public boolean isLast(E e) {
        return e != null && e == this.last;
    }

    @Override // com.github.benmanes.caffeine.cache.LinkedDeque
    public void moveToFront(E e) {
        if (e != this.first) {
            unlink(e);
            linkFirst(e);
        }
    }

    @Override // com.github.benmanes.caffeine.cache.LinkedDeque
    public void moveToBack(E e) {
        if (e != this.last) {
            unlink(e);
            linkLast(e);
        }
    }

    @Override // java.util.Deque, java.util.Queue
    public E peek() {
        return peekFirst();
    }

    @Override // java.util.Deque
    public E peekFirst() {
        return this.first;
    }

    @Override // java.util.Deque
    public E peekLast() {
        return this.last;
    }

    @Override // java.util.Deque
    public E getFirst() {
        checkNotEmpty();
        return peekFirst();
    }

    @Override // java.util.Deque
    public E getLast() {
        checkNotEmpty();
        return peekLast();
    }

    @Override // java.util.Deque, java.util.Queue
    public E element() {
        return getFirst();
    }

    @Override // java.util.Deque, java.util.Queue
    public boolean offer(E e) {
        return offerLast(e);
    }

    @Override // java.util.Deque
    public boolean offerFirst(E e) {
        if (contains(e)) {
            return false;
        }
        linkFirst(e);
        return true;
    }

    @Override // java.util.Deque
    public boolean offerLast(E e) {
        if (contains(e)) {
            return false;
        }
        linkLast(e);
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Deque, java.util.Queue
    public boolean add(E e) {
        return offerLast(e);
    }

    @Override // java.util.Deque
    public void addFirst(E e) {
        if (!offerFirst(e)) {
            throw new IllegalArgumentException();
        }
    }

    @Override // java.util.Deque
    public void addLast(E e) {
        if (!offerLast(e)) {
            throw new IllegalArgumentException();
        }
    }

    @Override // java.util.Deque, java.util.Queue
    public E poll() {
        return pollFirst();
    }

    @Override // java.util.Deque
    public E pollFirst() {
        if (isEmpty()) {
            return null;
        }
        return unlinkFirst();
    }

    @Override // java.util.Deque
    public E pollLast() {
        if (isEmpty()) {
            return null;
        }
        return unlinkLast();
    }

    @Override // java.util.Deque, java.util.Queue
    public E remove() {
        return removeFirst();
    }

    @Override // java.util.Deque
    public E removeFirst() {
        checkNotEmpty();
        return pollFirst();
    }

    @Override // java.util.Deque
    public boolean removeFirstOccurrence(Object o) {
        return remove(o);
    }

    @Override // java.util.Deque
    public E removeLast() {
        checkNotEmpty();
        return pollLast();
    }

    @Override // java.util.Deque
    public boolean removeLastOccurrence(Object o) {
        return remove(o);
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (Object o : c) {
            modified |= remove(o);
        }
        return modified;
    }

    @Override // java.util.Deque
    public void push(E e) {
        addFirst(e);
    }

    @Override // java.util.Deque
    public E pop() {
        return removeFirst();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.github.benmanes.caffeine.cache.LinkedDeque, java.util.Deque
    public LinkedDeque.PeekingIterator<E> iterator() {
        return new AbstractLinkedDeque<E>.AbstractLinkedIterator(this.first) { // from class: com.github.benmanes.caffeine.cache.AbstractLinkedDeque.1
            @Override // com.github.benmanes.caffeine.cache.AbstractLinkedDeque.AbstractLinkedIterator
            E computeNext() {
                return AbstractLinkedDeque.this.getNext(this.cursor);
            }
        };
    }

    @Override // com.github.benmanes.caffeine.cache.LinkedDeque, java.util.Deque
    public LinkedDeque.PeekingIterator<E> descendingIterator() {
        return new AbstractLinkedDeque<E>.AbstractLinkedIterator(this.last) { // from class: com.github.benmanes.caffeine.cache.AbstractLinkedDeque.2
            @Override // com.github.benmanes.caffeine.cache.AbstractLinkedDeque.AbstractLinkedIterator
            E computeNext() {
                return AbstractLinkedDeque.this.getPrevious(this.cursor);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: Jackey Client b2.jar:com/github/benmanes/caffeine/cache/AbstractLinkedDeque$AbstractLinkedIterator.class */
    public abstract class AbstractLinkedIterator implements LinkedDeque.PeekingIterator<E> {
        E previous;
        E cursor;

        abstract E computeNext();

        AbstractLinkedIterator(E start) {
            AbstractLinkedDeque.this = this$0;
            this.cursor = start;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.cursor != null;
        }

        @Override // com.github.benmanes.caffeine.cache.LinkedDeque.PeekingIterator
        public E peek() {
            return this.cursor;
        }

        @Override // java.util.Iterator
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            this.previous = this.cursor;
            this.cursor = (E) computeNext();
            return this.previous;
        }

        @Override // java.util.Iterator
        public void remove() {
            if (this.previous == null) {
                throw new IllegalStateException();
            }
            AbstractLinkedDeque.this.remove(this.previous);
            this.previous = null;
        }
    }
}
