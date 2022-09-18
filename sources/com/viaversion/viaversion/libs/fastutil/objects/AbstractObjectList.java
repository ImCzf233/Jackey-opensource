package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Stack;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterators;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.function.Consumer;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/AbstractObjectList.class */
public abstract class AbstractObjectList<K> extends AbstractObjectCollection<K> implements ObjectList<K>, Stack<K> {
    @Override // java.lang.Comparable
    public /* bridge */ /* synthetic */ int compareTo(Object obj) {
        return compareTo((List) ((List) obj));
    }

    public void ensureIndex(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index (" + index + ") is negative");
        }
        if (index > size()) {
            throw new IndexOutOfBoundsException("Index (" + index + ") is greater than list size (" + size() + ")");
        }
    }

    public void ensureRestrictedIndex(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index (" + index + ") is negative");
        }
        if (index >= size()) {
            throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + size() + ")");
        }
    }

    @Override // java.util.List
    public void add(int index, K k) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean add(K k) {
        add(size(), k);
        return true;
    }

    @Override // java.util.List
    public K remove(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    public K set(int index, K k) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    public boolean addAll(int index, Collection<? extends K> c) {
        ensureIndex(index);
        Iterator<? extends K> i = c.iterator();
        boolean retVal = i.hasNext();
        while (i.hasNext()) {
            int i2 = index;
            index++;
            add(i2, i.next());
        }
        return retVal;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean addAll(Collection<? extends K> c) {
        return addAll(size(), c);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable
    public ObjectListIterator<K> iterator() {
        return listIterator();
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectList, java.util.List
    public ObjectListIterator<K> listIterator() {
        return listIterator(0);
    }

    public ObjectListIterator<K> listIterator(int index) {
        ensureIndex(index);
        return new ObjectIterators.AbstractIndexBasedListIterator<K>(0, index) { // from class: com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList.1
            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectIterators.AbstractIndexBasedIterator
            protected final K get(int i) {
                return (K) AbstractObjectList.this.get(i);
            }

            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectIterators.AbstractIndexBasedListIterator
            protected final void add(int i, K k) {
                AbstractObjectList.this.add(i, k);
            }

            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectIterators.AbstractIndexBasedListIterator
            protected final void set(int i, K k) {
                AbstractObjectList.this.set(i, k);
            }

            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectIterators.AbstractIndexBasedIterator
            protected final void remove(int i) {
                AbstractObjectList.this.remove(i);
            }

            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectIterators.AbstractIndexBasedIterator
            protected final int getMaxPos() {
                return AbstractObjectList.this.size();
            }
        };
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/AbstractObjectList$IndexBasedSpliterator.class */
    public static final class IndexBasedSpliterator<K> extends ObjectSpliterators.LateBindingSizeIndexBasedSpliterator<K> {

        /* renamed from: l */
        final ObjectList<K> f129l;

        public IndexBasedSpliterator(ObjectList<K> l, int pos) {
            super(pos);
            this.f129l = l;
        }

        IndexBasedSpliterator(ObjectList<K> l, int pos, int maxPos) {
            super(pos, maxPos);
            this.f129l = l;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators.LateBindingSizeIndexBasedSpliterator
        protected final int getMaxPosFromBackingStore() {
            return this.f129l.size();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators.AbstractIndexBasedSpliterator
        protected final K get(int i) {
            return this.f129l.get(i);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators.AbstractIndexBasedSpliterator
        public final IndexBasedSpliterator<K> makeForSplit(int pos, int maxPos) {
            return new IndexBasedSpliterator<>(this.f129l, pos, maxPos);
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean contains(Object k) {
        return indexOf(k) >= 0;
    }

    @Override // java.util.List
    public int indexOf(Object k) {
        ObjectListIterator<K> i = listIterator();
        while (i.hasNext()) {
            K e = i.next();
            if (Objects.equals(k, e)) {
                return i.previousIndex();
            }
        }
        return -1;
    }

    @Override // java.util.List
    public int lastIndexOf(Object k) {
        ObjectListIterator<K> i = listIterator(size());
        while (i.hasPrevious()) {
            K e = i.previous();
            if (Objects.equals(k, e)) {
                return i.nextIndex();
            }
        }
        return -1;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectList
    public void size(int size) {
        int i = size();
        if (size > i) {
            while (true) {
                int i2 = i;
                i++;
                if (i2 < size) {
                    add(null);
                } else {
                    return;
                }
            }
        } else {
            while (true) {
                int i3 = i;
                i--;
                if (i3 != size) {
                    remove(i);
                } else {
                    return;
                }
            }
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectList, java.util.List
    public ObjectList<K> subList(int from, int to) {
        ensureIndex(from);
        ensureIndex(to);
        if (from > to) {
            throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
        }
        if (this instanceof RandomAccess) {
            return new ObjectRandomAccessSubList(this, from, to);
        }
        return new ObjectSubList(this, from, to);
    }

    @Override // java.lang.Iterable
    public void forEach(Consumer<? super K> action) {
        if (this instanceof RandomAccess) {
            int max = size();
            for (int i = 0; i < max; i++) {
                action.accept((Object) get(i));
            }
            return;
        }
        super.forEach(action);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectList
    public void removeElements(int from, int to) {
        ensureIndex(to);
        ObjectListIterator<K> i = listIterator(from);
        int n = to - from;
        if (n < 0) {
            throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
        }
        while (true) {
            int i2 = n;
            n--;
            if (i2 != 0) {
                i.next();
                i.remove();
            } else {
                return;
            }
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectList
    public void addElements(int index, K[] a, int offset, int length) {
        ensureIndex(index);
        ObjectArrays.ensureOffsetLength(a, offset, length);
        if (this instanceof RandomAccess) {
            while (true) {
                int i = length;
                length--;
                if (i != 0) {
                    int i2 = index;
                    index++;
                    int i3 = offset;
                    offset++;
                    add(i2, a[i3]);
                } else {
                    return;
                }
            }
        } else {
            ObjectListIterator<K> iter = listIterator(index);
            while (true) {
                int i4 = length;
                length--;
                if (i4 != 0) {
                    int i5 = offset;
                    offset++;
                    iter.add(a[i5]);
                } else {
                    return;
                }
            }
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectList
    public void addElements(int index, K[] a) {
        addElements(index, a, 0, a.length);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectList
    public void getElements(int from, Object[] a, int offset, int length) {
        ensureIndex(from);
        ObjectArrays.ensureOffsetLength(a, offset, length);
        if (from + length > size()) {
            throw new IndexOutOfBoundsException("End index (" + (from + length) + ") is greater than list size (" + size() + ")");
        }
        if (this instanceof RandomAccess) {
            int current = from;
            while (true) {
                int i = length;
                length--;
                if (i != 0) {
                    int i2 = offset;
                    offset++;
                    int i3 = current;
                    current++;
                    a[i2] = get(i3);
                } else {
                    return;
                }
            }
        } else {
            ObjectListIterator<K> i4 = listIterator(from);
            while (true) {
                int i5 = length;
                length--;
                if (i5 != 0) {
                    int i6 = offset;
                    offset++;
                    a[i6] = i4.next();
                } else {
                    return;
                }
            }
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectList
    public void setElements(int index, K[] a, int offset, int length) {
        ensureIndex(index);
        ObjectArrays.ensureOffsetLength(a, offset, length);
        if (index + length > size()) {
            throw new IndexOutOfBoundsException("End index (" + (index + length) + ") is greater than list size (" + size() + ")");
        }
        if (this instanceof RandomAccess) {
            for (int i = 0; i < length; i++) {
                set(i + index, a[i + offset]);
            }
            return;
        }
        ObjectListIterator<K> iter = listIterator(index);
        int i2 = 0;
        while (i2 < length) {
            iter.next();
            int i3 = i2;
            i2++;
            iter.set(a[offset + i3]);
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public void clear() {
        removeElements(0, size());
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public Object[] toArray() {
        int size = size();
        Object[] ret = new Object[size];
        getElements(0, ret, 0, size);
        return ret;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v10, types: [java.lang.Object[]] */
    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public <T> T[] toArray(T[] a) {
        int size = size();
        if (a.length < size) {
            a = Arrays.copyOf(a, size);
        }
        getElements(0, a, 0, size);
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    @Override // java.util.Collection, java.util.List
    public int hashCode() {
        ObjectIterator<K> i = iterator();
        int h = 1;
        int s = size();
        while (true) {
            int i2 = s;
            s--;
            if (i2 != 0) {
                K k = i.next();
                h = (31 * h) + (k == null ? 0 : k.hashCode());
            } else {
                return h;
            }
        }
    }

    @Override // java.util.Collection, java.util.List
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof List)) {
            return false;
        }
        List<?> l = (List) o;
        int s = size();
        if (s != l.size()) {
            return false;
        }
        ListIterator<?> i1 = listIterator();
        ListIterator<?> i2 = l.listIterator();
        do {
            int i = s;
            s--;
            if (i == 0) {
                return true;
            }
        } while (Objects.equals(i1.next(), i2.next()));
        return false;
    }

    public int compareTo(List<? extends K> l) {
        if (l == this) {
            return 0;
        }
        if (l instanceof ObjectList) {
            ObjectListIterator<K> i1 = listIterator();
            ObjectListIterator<K> i2 = ((ObjectList) l).listIterator();
            while (i1.hasNext() && i2.hasNext()) {
                K e1 = i1.next();
                K e2 = i2.next();
                int r = ((Comparable) e1).compareTo(e2);
                if (r != 0) {
                    return r;
                }
            }
            if (i2.hasNext()) {
                return -1;
            }
            return i1.hasNext() ? 1 : 0;
        }
        ListIterator<? extends K> i12 = listIterator();
        ListIterator<? extends K> i22 = l.listIterator();
        while (i12.hasNext() && i22.hasNext()) {
            int r2 = ((Comparable) i12.next()).compareTo(i22.next());
            if (r2 != 0) {
                return r2;
            }
        }
        if (i22.hasNext()) {
            return -1;
        }
        return i12.hasNext() ? 1 : 0;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.Stack
    public void push(K o) {
        add(o);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.Stack
    public K pop() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return remove(size() - 1);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.Stack
    public K top() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return (K) get(size() - 1);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.Stack
    public K peek(int i) {
        return (K) get((size() - 1) - i);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection
    public String toString() {
        StringBuilder s = new StringBuilder();
        ObjectIterator<K> i = iterator();
        int n = size();
        boolean first = true;
        s.append("[");
        while (true) {
            int i2 = n;
            n--;
            if (i2 != 0) {
                if (first) {
                    first = false;
                } else {
                    s.append(", ");
                }
                K k = i.next();
                if (this == k) {
                    s.append("(this list)");
                } else {
                    s.append(String.valueOf(k));
                }
            } else {
                s.append("]");
                return s.toString();
            }
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/AbstractObjectList$ObjectSubList.class */
    public static class ObjectSubList<K> extends AbstractObjectList<K> implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;

        /* renamed from: l */
        protected final ObjectList<K> f130l;
        protected final int from;

        /* renamed from: to */
        protected int f131to;
        static final /* synthetic */ boolean $assertionsDisabled;

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, com.viaversion.viaversion.libs.fastutil.objects.ObjectList, java.util.List
        public /* bridge */ /* synthetic */ ListIterator listIterator() {
            return super.listIterator();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, java.lang.Comparable
        public /* bridge */ /* synthetic */ int compareTo(Object obj) {
            return super.compareTo((List) ((List) obj));
        }

        static {
            $assertionsDisabled = !AbstractObjectList.class.desiredAssertionStatus();
        }

        public ObjectSubList(ObjectList<K> l, int from, int to) {
            this.f130l = l;
            this.from = from;
            this.f131to = to;
        }

        public boolean assertRange() {
            if ($assertionsDisabled || this.from <= this.f130l.size()) {
                if (!$assertionsDisabled && this.f131to > this.f130l.size()) {
                    throw new AssertionError();
                }
                if (!$assertionsDisabled && this.f131to < this.from) {
                    throw new AssertionError();
                }
                return true;
            }
            throw new AssertionError();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, java.util.AbstractCollection, java.util.Collection, java.util.List
        public boolean add(K k) {
            this.f130l.add(this.f131to, k);
            this.f131to++;
            if ($assertionsDisabled || assertRange()) {
                return true;
            }
            throw new AssertionError();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, java.util.List
        public void add(int index, K k) {
            ensureIndex(index);
            this.f130l.add(this.from + index, k);
            this.f131to++;
            if ($assertionsDisabled || assertRange()) {
                return;
            }
            throw new AssertionError();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, java.util.List
        public boolean addAll(int index, Collection<? extends K> c) {
            ensureIndex(index);
            this.f131to += c.size();
            return this.f130l.addAll(this.from + index, c);
        }

        @Override // java.util.List
        public K get(int index) {
            ensureRestrictedIndex(index);
            return this.f130l.get(this.from + index);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, java.util.List
        public K remove(int index) {
            ensureRestrictedIndex(index);
            this.f131to--;
            return this.f130l.remove(this.from + index);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, java.util.List
        public K set(int index, K k) {
            ensureRestrictedIndex(index);
            return this.f130l.set(this.from + index, k);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.f131to - this.from;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, com.viaversion.viaversion.libs.fastutil.objects.ObjectList
        public void getElements(int from, Object[] a, int offset, int length) {
            ensureIndex(from);
            if (from + length > size()) {
                throw new IndexOutOfBoundsException("End index (" + from + length + ") is greater than list size (" + size() + ")");
            }
            this.f130l.getElements(this.from + from, a, offset, length);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, com.viaversion.viaversion.libs.fastutil.objects.ObjectList
        public void removeElements(int from, int to) {
            ensureIndex(from);
            ensureIndex(to);
            this.f130l.removeElements(this.from + from, this.from + to);
            this.f131to -= to - from;
            if ($assertionsDisabled || assertRange()) {
                return;
            }
            throw new AssertionError();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, com.viaversion.viaversion.libs.fastutil.objects.ObjectList
        public void addElements(int index, K[] a, int offset, int length) {
            ensureIndex(index);
            this.f130l.addElements(this.from + index, a, offset, length);
            this.f131to += length;
            if ($assertionsDisabled || assertRange()) {
                return;
            }
            throw new AssertionError();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, com.viaversion.viaversion.libs.fastutil.objects.ObjectList
        public void setElements(int index, K[] a, int offset, int length) {
            ensureIndex(index);
            this.f130l.setElements(this.from + index, a, offset, length);
            if ($assertionsDisabled || assertRange()) {
                return;
            }
            throw new AssertionError();
        }

        /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/AbstractObjectList$ObjectSubList$RandomAccessIter.class */
        public final class RandomAccessIter extends ObjectIterators.AbstractIndexBasedListIterator<K> {
            static final /* synthetic */ boolean $assertionsDisabled;

            static {
                $assertionsDisabled = !AbstractObjectList.class.desiredAssertionStatus();
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            RandomAccessIter(int pos) {
                super(0, pos);
                ObjectSubList.this = r5;
            }

            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectIterators.AbstractIndexBasedIterator
            protected final K get(int i) {
                return ObjectSubList.this.f130l.get(ObjectSubList.this.from + i);
            }

            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectIterators.AbstractIndexBasedListIterator
            protected final void add(int i, K k) {
                ObjectSubList.this.add(i, k);
            }

            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectIterators.AbstractIndexBasedListIterator
            protected final void set(int i, K k) {
                ObjectSubList.this.set(i, k);
            }

            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectIterators.AbstractIndexBasedIterator
            protected final void remove(int i) {
                ObjectSubList.this.remove(i);
            }

            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectIterators.AbstractIndexBasedIterator
            protected final int getMaxPos() {
                return ObjectSubList.this.f131to - ObjectSubList.this.from;
            }

            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectIterators.AbstractIndexBasedListIterator, com.viaversion.viaversion.libs.fastutil.objects.ObjectListIterator, java.util.ListIterator
            public void add(K k) {
                super.add(k);
                if ($assertionsDisabled || ObjectSubList.this.assertRange()) {
                    return;
                }
                throw new AssertionError();
            }

            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectIterators.AbstractIndexBasedIterator, java.util.Iterator, com.viaversion.viaversion.libs.fastutil.objects.ObjectListIterator, java.util.ListIterator
            public void remove() {
                super.remove();
                if ($assertionsDisabled || ObjectSubList.this.assertRange()) {
                    return;
                }
                throw new AssertionError();
            }
        }

        /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/AbstractObjectList$ObjectSubList$ParentWrappingIter.class */
        public class ParentWrappingIter implements ObjectListIterator<K> {
            private ObjectListIterator<K> parent;

            ParentWrappingIter(ObjectListIterator<K> parent) {
                ObjectSubList.this = r4;
                this.parent = parent;
            }

            @Override // java.util.ListIterator
            public int nextIndex() {
                return this.parent.nextIndex() - ObjectSubList.this.from;
            }

            @Override // java.util.ListIterator
            public int previousIndex() {
                return this.parent.previousIndex() - ObjectSubList.this.from;
            }

            @Override // java.util.Iterator, java.util.ListIterator
            public boolean hasNext() {
                return this.parent.nextIndex() < ObjectSubList.this.f131to;
            }

            @Override // com.viaversion.viaversion.libs.fastutil.BidirectionalIterator
            public boolean hasPrevious() {
                return this.parent.previousIndex() >= ObjectSubList.this.from;
            }

            @Override // java.util.Iterator, java.util.ListIterator
            public K next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return this.parent.next();
            }

            @Override // com.viaversion.viaversion.libs.fastutil.BidirectionalIterator, java.util.ListIterator
            public K previous() {
                if (!hasPrevious()) {
                    throw new NoSuchElementException();
                }
                return this.parent.previous();
            }

            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectListIterator, java.util.ListIterator
            public void add(K k) {
                this.parent.add(k);
            }

            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectListIterator, java.util.ListIterator
            public void set(K k) {
                this.parent.set(k);
            }

            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectListIterator, java.util.Iterator, java.util.ListIterator
            public void remove() {
                this.parent.remove();
            }

            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator
            public int back(int n) {
                if (n < 0) {
                    throw new IllegalArgumentException("Argument must be nonnegative: " + n);
                }
                int currentPos = this.parent.previousIndex();
                int parentNewPos = currentPos - n;
                if (parentNewPos < ObjectSubList.this.from - 1) {
                    parentNewPos = ObjectSubList.this.from - 1;
                }
                int toSkip = parentNewPos - currentPos;
                return this.parent.back(toSkip);
            }

            @Override // com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator
            public int skip(int n) {
                if (n < 0) {
                    throw new IllegalArgumentException("Argument must be nonnegative: " + n);
                }
                int currentPos = this.parent.nextIndex();
                int parentNewPos = currentPos + n;
                if (parentNewPos > ObjectSubList.this.f131to) {
                    parentNewPos = ObjectSubList.this.f131to;
                }
                int toSkip = parentNewPos - currentPos;
                return this.parent.skip(toSkip);
            }
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, com.viaversion.viaversion.libs.fastutil.objects.ObjectList, java.util.List
        public ObjectListIterator<K> listIterator(int index) {
            ensureIndex(index);
            if (this.f130l instanceof RandomAccess) {
                return new RandomAccessIter(index);
            }
            return new ParentWrappingIter(this.f130l.listIterator(index + this.from));
        }

        @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable, com.viaversion.viaversion.libs.fastutil.objects.ObjectSet, java.util.Set
        public ObjectSpliterator<K> spliterator() {
            return this.f130l instanceof RandomAccess ? new IndexBasedSpliterator(this.f130l, this.from, this.f131to) : super.spliterator();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, com.viaversion.viaversion.libs.fastutil.objects.ObjectList, java.util.List
        public ObjectList<K> subList(int from, int to) {
            ensureIndex(from);
            ensureIndex(to);
            if (from > to) {
                throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
            }
            return new ObjectSubList(this, from, to);
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/AbstractObjectList$ObjectRandomAccessSubList.class */
    public static class ObjectRandomAccessSubList<K> extends ObjectSubList<K> implements RandomAccess {
        private static final long serialVersionUID = -107070782945191929L;

        public ObjectRandomAccessSubList(ObjectList<K> l, int from, int to) {
            super(l, from, to);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList.ObjectSubList, com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList, com.viaversion.viaversion.libs.fastutil.objects.ObjectList, java.util.List
        public ObjectList<K> subList(int from, int to) {
            ensureIndex(from);
            ensureIndex(to);
            if (from > to) {
                throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
            }
            return new ObjectRandomAccessSubList(this, from, to);
        }
    }
}
