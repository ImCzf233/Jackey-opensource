package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.ints.IntIterators;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.Spliterator;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/AbstractIntList.class */
public abstract class AbstractIntList extends AbstractIntCollection implements IntList, IntStack {
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

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
    public void add(int index, int k) {
        throw new UnsupportedOperationException();
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
    public boolean add(int k) {
        add(size(), k);
        return true;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
    public int removeInt(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
    public int set(int index, int k) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    public boolean addAll(int index, Collection<? extends Integer> c) {
        if (c instanceof IntCollection) {
            return addAll(index, (IntCollection) c);
        }
        ensureIndex(index);
        Iterator<? extends Integer> i = c.iterator();
        boolean retVal = i.hasNext();
        while (i.hasNext()) {
            int i2 = index;
            index++;
            add(i2, i.next().intValue());
        }
        return retVal;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection
    public boolean addAll(Collection<? extends Integer> c) {
        return addAll(size(), c);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
    public Iterator<Integer> iterator() {
        return listIterator2();
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList, java.util.List
    /* renamed from: listIterator */
    public ListIterator<Integer> listIterator2() {
        return listIterator2(0);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList, java.util.List
    /* renamed from: listIterator */
    public ListIterator<Integer> listIterator2(int index) {
        ensureIndex(index);
        return new IntIterators.AbstractIndexBasedListIterator(0, index) { // from class: com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList.1
            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterators.AbstractIndexBasedIterator
            protected final int get(int i) {
                return AbstractIntList.this.getInt(i);
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterators.AbstractIndexBasedListIterator
            protected final void add(int i, int k) {
                AbstractIntList.this.add(i, k);
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterators.AbstractIndexBasedListIterator
            protected final void set(int i, int k) {
                AbstractIntList.this.set(i, k);
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterators.AbstractIndexBasedIterator
            protected final void remove(int i) {
                AbstractIntList.this.removeInt(i);
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterators.AbstractIndexBasedIterator
            protected final int getMaxPos() {
                return AbstractIntList.this.size();
            }
        };
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/AbstractIntList$IndexBasedSpliterator.class */
    public static final class IndexBasedSpliterator extends IntSpliterators.LateBindingSizeIndexBasedSpliterator {

        /* renamed from: l */
        final IntList f73l;

        public IndexBasedSpliterator(IntList l, int pos) {
            super(pos);
            this.f73l = l;
        }

        IndexBasedSpliterator(IntList l, int pos, int maxPos) {
            super(pos, maxPos);
            this.f73l = l;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators.LateBindingSizeIndexBasedSpliterator
        protected final int getMaxPosFromBackingStore() {
            return this.f73l.size();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators.AbstractIndexBasedSpliterator
        protected final int get(int i) {
            return this.f73l.getInt(i);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators.AbstractIndexBasedSpliterator
        public final IndexBasedSpliterator makeForSplit(int pos, int maxPos) {
            return new IndexBasedSpliterator(this.f73l, pos, maxPos);
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
    public boolean contains(int k) {
        return indexOf(k) >= 0;
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.viaversion.viaversion.libs.fastutil.ints.IntListIterator] */
    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
    public int indexOf(int k) {
        ?? listIterator2 = listIterator2();
        while (listIterator2.hasNext()) {
            int e = listIterator2.nextInt();
            if (k == e) {
                return listIterator2.previousIndex();
            }
        }
        return -1;
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.viaversion.viaversion.libs.fastutil.ints.IntListIterator] */
    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
    public int lastIndexOf(int k) {
        ?? listIterator2 = listIterator2(size());
        while (listIterator2.hasPrevious()) {
            int e = listIterator2.previousInt();
            if (k == e) {
                return listIterator2.nextIndex();
            }
        }
        return -1;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
    public void size(int size) {
        int i = size();
        if (size > i) {
            while (true) {
                int i2 = i;
                i++;
                if (i2 < size) {
                    add(0);
                } else {
                    return;
                }
            }
        } else {
            while (true) {
                int i3 = i;
                i--;
                if (i3 != size) {
                    removeInt(i);
                } else {
                    return;
                }
            }
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList, java.util.List
    /* renamed from: subList */
    public List<Integer> subList2(int from, int to) {
        ensureIndex(from);
        ensureIndex(to);
        if (from > to) {
            throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
        }
        if (this instanceof RandomAccess) {
            return new IntRandomAccessSubList(this, from, to);
        }
        return new IntSubList(this, from, to);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterable
    public void forEach(java.util.function.IntConsumer action) {
        if (this instanceof RandomAccess) {
            int max = size();
            for (int i = 0; i < max; i++) {
                action.accept(getInt(i));
            }
            return;
        }
        super.forEach(action);
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.viaversion.viaversion.libs.fastutil.ints.IntListIterator] */
    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
    public void removeElements(int from, int to) {
        ensureIndex(to);
        ?? listIterator2 = listIterator2(from);
        int n = to - from;
        if (n < 0) {
            throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
        }
        while (true) {
            int i = n;
            n--;
            if (i != 0) {
                listIterator2.nextInt();
                listIterator2.remove();
            } else {
                return;
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v5, types: [com.viaversion.viaversion.libs.fastutil.ints.IntListIterator] */
    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
    public void addElements(int index, int[] a, int offset, int length) {
        ensureIndex(index);
        IntArrays.ensureOffsetLength(a, offset, length);
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
            ?? listIterator2 = listIterator2(index);
            while (true) {
                int i4 = length;
                length--;
                if (i4 != 0) {
                    int i5 = offset;
                    offset++;
                    listIterator2.add(a[i5]);
                } else {
                    return;
                }
            }
        }
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
    public void addElements(int index, int[] a) {
        addElements(index, a, 0, a.length);
    }

    /* JADX WARN: Type inference failed for: r0v7, types: [com.viaversion.viaversion.libs.fastutil.ints.IntListIterator] */
    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
    public void getElements(int from, int[] a, int offset, int length) {
        ensureIndex(from);
        IntArrays.ensureOffsetLength(a, offset, length);
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
                    a[i2] = getInt(i3);
                } else {
                    return;
                }
            }
        } else {
            ?? listIterator2 = listIterator2(from);
            while (true) {
                int i4 = length;
                length--;
                if (i4 != 0) {
                    int i5 = offset;
                    offset++;
                    a[i5] = listIterator2.nextInt();
                } else {
                    return;
                }
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v7, types: [com.viaversion.viaversion.libs.fastutil.ints.IntListIterator] */
    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
    public void setElements(int index, int[] a, int offset, int length) {
        ensureIndex(index);
        IntArrays.ensureOffsetLength(a, offset, length);
        if (index + length > size()) {
            throw new IndexOutOfBoundsException("End index (" + (index + length) + ") is greater than list size (" + size() + ")");
        }
        if (this instanceof RandomAccess) {
            for (int i = 0; i < length; i++) {
                set(i + index, a[i + offset]);
            }
            return;
        }
        ?? listIterator2 = listIterator2(index);
        int i2 = 0;
        while (i2 < length) {
            listIterator2.nextInt();
            int i3 = i2;
            i2++;
            listIterator2.set(a[offset + i3]);
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public void clear() {
        removeElements(0, size());
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.viaversion.viaversion.libs.fastutil.ints.IntListIterator, com.viaversion.viaversion.libs.fastutil.ints.IntIterator] */
    @Override // java.util.Collection, java.util.List
    public int hashCode() {
        ?? it = iterator();
        int h = 1;
        int s = size();
        while (true) {
            int i = s;
            s--;
            if (i != 0) {
                int k = it.nextInt();
                h = (31 * h) + k;
            } else {
                return h;
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v21, types: [com.viaversion.viaversion.libs.fastutil.ints.IntListIterator] */
    /* JADX WARN: Type inference failed for: r0v24, types: [com.viaversion.viaversion.libs.fastutil.ints.IntListIterator] */
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
        if (l instanceof IntList) {
            ?? listIterator2 = listIterator2();
            ?? listIterator22 = ((IntList) l).listIterator2();
            do {
                int i = s;
                s--;
                if (i == 0) {
                    return true;
                }
            } while (listIterator2.nextInt() == listIterator22.nextInt());
            return false;
        }
        ListIterator<?> i1 = listIterator2();
        ListIterator<?> i2 = l.listIterator();
        do {
            int i3 = s;
            s--;
            if (i3 == 0) {
                return true;
            }
        } while (Objects.equals(i1.next(), i2.next()));
        return false;
    }

    /* JADX WARN: Type inference failed for: r0v24, types: [com.viaversion.viaversion.libs.fastutil.ints.IntListIterator] */
    /* JADX WARN: Type inference failed for: r0v27, types: [com.viaversion.viaversion.libs.fastutil.ints.IntListIterator] */
    public int compareTo(List<? extends Integer> l) {
        if (l == this) {
            return 0;
        }
        if (l instanceof IntList) {
            ?? listIterator2 = listIterator2();
            ?? listIterator22 = ((IntList) l).listIterator2();
            while (listIterator2.hasNext() && listIterator22.hasNext()) {
                int e1 = listIterator2.nextInt();
                int e2 = listIterator22.nextInt();
                int r = Integer.compare(e1, e2);
                if (r != 0) {
                    return r;
                }
            }
            if (listIterator22.hasNext()) {
                return -1;
            }
            return listIterator2.hasNext() ? 1 : 0;
        }
        ListIterator<? extends Integer> i1 = listIterator2();
        ListIterator<? extends Integer> i2 = l.listIterator();
        while (i1.hasNext() && i2.hasNext()) {
            int r2 = i1.next().compareTo(i2.next());
            if (r2 != 0) {
                return r2;
            }
        }
        if (i2.hasNext()) {
            return -1;
        }
        return i1.hasNext() ? 1 : 0;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntStack
    public void push(int o) {
        add(o);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntStack
    public int popInt() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return removeInt(size() - 1);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntStack
    public int topInt() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return getInt(size() - 1);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntStack
    public int peekInt(int i) {
        return getInt((size() - 1) - i);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
    public boolean rem(int k) {
        int index = indexOf(k);
        if (index == -1) {
            return false;
        }
        removeInt(index);
        return true;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
    public int[] toIntArray() {
        int size = size();
        int[] ret = new int[size];
        getElements(0, ret, 0, size);
        return ret;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
    public int[] toArray(int[] a) {
        int size = size();
        if (a.length < size) {
            a = Arrays.copyOf(a, size);
        }
        getElements(0, a, 0, size);
        return a;
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.viaversion.viaversion.libs.fastutil.ints.IntIterator] */
    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
    public boolean addAll(int index, IntCollection c) {
        ensureIndex(index);
        ?? iterator2 = c.iterator2();
        boolean retVal = iterator2.hasNext();
        while (iterator2.hasNext()) {
            int i = index;
            index++;
            add(i, iterator2.nextInt());
        }
        return retVal;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
    public boolean addAll(IntCollection c) {
        return addAll(size(), c);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
    public final void replaceAll(IntUnaryOperator operator) {
        replaceAll((java.util.function.IntUnaryOperator) operator);
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.viaversion.viaversion.libs.fastutil.ints.IntListIterator, com.viaversion.viaversion.libs.fastutil.ints.IntIterator] */
    @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection
    public String toString() {
        StringBuilder s = new StringBuilder();
        ?? it = iterator();
        int n = size();
        boolean first = true;
        s.append("[");
        while (true) {
            int i = n;
            n--;
            if (i != 0) {
                if (first) {
                    first = false;
                } else {
                    s.append(", ");
                }
                int k = it.nextInt();
                s.append(String.valueOf(k));
            } else {
                s.append("]");
                return s.toString();
            }
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/AbstractIntList$IntSubList.class */
    public static class IntSubList extends AbstractIntList implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;

        /* renamed from: l */
        protected final IntList f74l;
        protected final int from;

        /* renamed from: to */
        protected int f75to;
        static final /* synthetic */ boolean $assertionsDisabled;

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList, java.util.List
        public /* bridge */ /* synthetic */ ListIterator<Integer> listIterator() {
            return super.listIterator2();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, java.lang.Comparable
        public /* bridge */ /* synthetic */ int compareTo(List<? extends Integer> list) {
            return super.compareTo(list);
        }

        static {
            $assertionsDisabled = !AbstractIntList.class.desiredAssertionStatus();
        }

        public IntSubList(IntList l, int from, int to) {
            this.f74l = l;
            this.from = from;
            this.f75to = to;
        }

        public boolean assertRange() {
            if ($assertionsDisabled || this.from <= this.f74l.size()) {
                if (!$assertionsDisabled && this.f75to > this.f74l.size()) {
                    throw new AssertionError();
                }
                if (!$assertionsDisabled && this.f75to < this.from) {
                    throw new AssertionError();
                }
                return true;
            }
            throw new AssertionError();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
        public boolean add(int k) {
            this.f74l.add(this.f75to, k);
            this.f75to++;
            if ($assertionsDisabled || assertRange()) {
                return true;
            }
            throw new AssertionError();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList
        public void add(int index, int k) {
            ensureIndex(index);
            this.f74l.add(this.from + index, k);
            this.f75to++;
            if ($assertionsDisabled || assertRange()) {
                return;
            }
            throw new AssertionError();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, java.util.List
        public boolean addAll(int index, Collection<? extends Integer> c) {
            ensureIndex(index);
            this.f75to += c.size();
            return this.f74l.addAll(this.from + index, c);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
        public int getInt(int index) {
            ensureRestrictedIndex(index);
            return this.f74l.getInt(this.from + index);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList
        public int removeInt(int index) {
            ensureRestrictedIndex(index);
            this.f75to--;
            return this.f74l.removeInt(this.from + index);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList
        public int set(int index, int k) {
            ensureRestrictedIndex(index);
            return this.f74l.set(this.from + index, k);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.f75to - this.from;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList
        public void getElements(int from, int[] a, int offset, int length) {
            ensureIndex(from);
            if (from + length > size()) {
                throw new IndexOutOfBoundsException("End index (" + from + length + ") is greater than list size (" + size() + ")");
            }
            this.f74l.getElements(this.from + from, a, offset, length);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList
        public void removeElements(int from, int to) {
            ensureIndex(from);
            ensureIndex(to);
            this.f74l.removeElements(this.from + from, this.from + to);
            this.f75to -= to - from;
            if ($assertionsDisabled || assertRange()) {
                return;
            }
            throw new AssertionError();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList
        public void addElements(int index, int[] a, int offset, int length) {
            ensureIndex(index);
            this.f74l.addElements(this.from + index, a, offset, length);
            this.f75to += length;
            if ($assertionsDisabled || assertRange()) {
                return;
            }
            throw new AssertionError();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList
        public void setElements(int index, int[] a, int offset, int length) {
            ensureIndex(index);
            this.f74l.setElements(this.from + index, a, offset, length);
            if ($assertionsDisabled || assertRange()) {
                return;
            }
            throw new AssertionError();
        }

        /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/AbstractIntList$IntSubList$RandomAccessIter.class */
        public final class RandomAccessIter extends IntIterators.AbstractIndexBasedListIterator {
            static final /* synthetic */ boolean $assertionsDisabled;

            static {
                $assertionsDisabled = !AbstractIntList.class.desiredAssertionStatus();
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            RandomAccessIter(int pos) {
                super(0, pos);
                IntSubList.this = r5;
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterators.AbstractIndexBasedIterator
            protected final int get(int i) {
                return IntSubList.this.f74l.getInt(IntSubList.this.from + i);
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterators.AbstractIndexBasedListIterator
            protected final void add(int i, int k) {
                IntSubList.this.add(i, k);
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterators.AbstractIndexBasedListIterator
            protected final void set(int i, int k) {
                IntSubList.this.set(i, k);
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterators.AbstractIndexBasedIterator
            protected final void remove(int i) {
                IntSubList.this.removeInt(i);
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterators.AbstractIndexBasedIterator
            protected final int getMaxPos() {
                return IntSubList.this.f75to - IntSubList.this.from;
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterators.AbstractIndexBasedListIterator, com.viaversion.viaversion.libs.fastutil.ints.IntListIterator
            public void add(int k) {
                super.add(k);
                if ($assertionsDisabled || IntSubList.this.assertRange()) {
                    return;
                }
                throw new AssertionError();
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterators.AbstractIndexBasedIterator, java.util.Iterator, com.viaversion.viaversion.libs.fastutil.ints.IntListIterator, java.util.ListIterator
            public void remove() {
                super.remove();
                if ($assertionsDisabled || IntSubList.this.assertRange()) {
                    return;
                }
                throw new AssertionError();
            }
        }

        /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/AbstractIntList$IntSubList$ParentWrappingIter.class */
        public class ParentWrappingIter implements IntListIterator {
            private IntListIterator parent;

            ParentWrappingIter(IntListIterator parent) {
                IntSubList.this = r4;
                this.parent = parent;
            }

            @Override // java.util.ListIterator
            public int nextIndex() {
                return this.parent.nextIndex() - IntSubList.this.from;
            }

            @Override // java.util.ListIterator
            public int previousIndex() {
                return this.parent.previousIndex() - IntSubList.this.from;
            }

            @Override // java.util.Iterator, java.util.ListIterator
            public boolean hasNext() {
                return this.parent.nextIndex() < IntSubList.this.f75to;
            }

            @Override // com.viaversion.viaversion.libs.fastutil.BidirectionalIterator
            public boolean hasPrevious() {
                return this.parent.previousIndex() >= IntSubList.this.from;
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
            public int nextInt() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return this.parent.nextInt();
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntBidirectionalIterator
            public int previousInt() {
                if (!hasPrevious()) {
                    throw new NoSuchElementException();
                }
                return this.parent.previousInt();
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntListIterator
            public void add(int k) {
                this.parent.add(k);
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntListIterator
            public void set(int k) {
                this.parent.set(k);
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntListIterator, java.util.Iterator, java.util.ListIterator
            public void remove() {
                this.parent.remove();
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntBidirectionalIterator, com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator
            public int back(int n) {
                if (n < 0) {
                    throw new IllegalArgumentException("Argument must be nonnegative: " + n);
                }
                int currentPos = this.parent.previousIndex();
                int parentNewPos = currentPos - n;
                if (parentNewPos < IntSubList.this.from - 1) {
                    parentNewPos = IntSubList.this.from - 1;
                }
                int toSkip = parentNewPos - currentPos;
                return this.parent.back(toSkip);
            }

            @Override // com.viaversion.viaversion.libs.fastutil.ints.IntBidirectionalIterator, com.viaversion.viaversion.libs.fastutil.ints.IntIterator, com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator, com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator
            public int skip(int n) {
                if (n < 0) {
                    throw new IllegalArgumentException("Argument must be nonnegative: " + n);
                }
                int currentPos = this.parent.nextIndex();
                int parentNewPos = currentPos + n;
                if (parentNewPos > IntSubList.this.f75to) {
                    parentNewPos = IntSubList.this.f75to;
                }
                int toSkip = parentNewPos - currentPos;
                return this.parent.skip(toSkip);
            }
        }

        /* JADX WARN: Type inference failed for: r3v2, types: [com.viaversion.viaversion.libs.fastutil.ints.IntListIterator] */
        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList, java.util.List
        /* renamed from: listIterator */
        public ListIterator<Integer> listIterator2(int index) {
            ensureIndex(index);
            if (this.f74l instanceof RandomAccess) {
                return new RandomAccessIter(index);
            }
            return new ParentWrappingIter(this.f74l.listIterator2(index + this.from));
        }

        @Override // java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
        /* renamed from: spliterator */
        public Spliterator<Integer> spliterator2() {
            return this.f74l instanceof RandomAccess ? new IndexBasedSpliterator(this.f74l, this.from, this.f75to) : super.spliterator2();
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList, java.util.List
        /* renamed from: subList */
        public List<Integer> subList2(int from, int to) {
            ensureIndex(from);
            ensureIndex(to);
            if (from > to) {
                throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
            }
            return new IntSubList(this, from, to);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
        public boolean rem(int k) {
            int index = indexOf(k);
            if (index == -1) {
                return false;
            }
            this.f75to--;
            this.f74l.removeInt(this.from + index);
            if (!$assertionsDisabled && !assertRange()) {
                throw new AssertionError();
            }
            return true;
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList
        public boolean addAll(int index, IntCollection c) {
            ensureIndex(index);
            return super.addAll(index, c);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.IntList
        public boolean addAll(int index, IntList l) {
            ensureIndex(index);
            return super.addAll(index, l);
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/AbstractIntList$IntRandomAccessSubList.class */
    public static class IntRandomAccessSubList extends IntSubList implements RandomAccess {
        private static final long serialVersionUID = -107070782945191929L;

        public IntRandomAccessSubList(IntList l, int from, int to) {
            super(l, from, to);
        }

        @Override // com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList.IntSubList, com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList, com.viaversion.viaversion.libs.fastutil.ints.IntList, java.util.List
        /* renamed from: subList */
        public List<Integer> subList2(int from, int to) {
            ensureIndex(from);
            ensureIndex(to);
            if (from > to) {
                throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
            }
            return new IntRandomAccessSubList(this, from, to);
        }
    }
}
