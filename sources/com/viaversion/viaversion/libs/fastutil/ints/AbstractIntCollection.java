package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/AbstractIntCollection.class */
public abstract class AbstractIntCollection extends AbstractCollection<Integer> implements IntCollection {
    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.viaversion.viaversion.libs.fastutil.ints.IntCollection, com.viaversion.viaversion.libs.fastutil.ints.IntIterable, com.viaversion.viaversion.libs.fastutil.ints.IntSet, java.util.Set
    /* renamed from: iterator */
    public abstract Iterator<Integer> iterator2();

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntCollection
    public boolean add(int k) {
        throw new UnsupportedOperationException();
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.viaversion.viaversion.libs.fastutil.ints.IntIterator] */
    public boolean contains(int k) {
        ?? iterator2 = iterator2();
        while (iterator2.hasNext()) {
            if (k == iterator2.nextInt()) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.viaversion.viaversion.libs.fastutil.ints.IntIterator] */
    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntCollection
    public boolean rem(int k) {
        ?? iterator2 = iterator2();
        while (iterator2.hasNext()) {
            if (k == iterator2.nextInt()) {
                iterator2.remove();
                return true;
            }
        }
        return false;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntCollection
    @Deprecated
    public boolean add(Integer key) {
        return super.add(key);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
    @Deprecated
    public boolean contains(Object key) {
        return super.contains(key);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, com.viaversion.viaversion.libs.fastutil.ints.IntCollection
    @Deprecated
    public boolean remove(Object key) {
        return super.remove(key);
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [com.viaversion.viaversion.libs.fastutil.ints.IntIterator] */
    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntCollection
    public int[] toArray(int[] a) {
        int size = size();
        if (a == null) {
            a = new int[size];
        } else if (a.length < size) {
            a = Arrays.copyOf(a, size);
        }
        IntIterators.unwrap((IntIterator) iterator2(), a);
        return a;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntCollection
    public int[] toIntArray() {
        return toArray((int[]) null);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntCollection
    @Deprecated
    public int[] toIntArray(int[] a) {
        return toArray(a);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntIterable
    public final void forEach(IntConsumer action) {
        super.forEach(action);
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntCollection
    public final boolean removeIf(IntPredicate filter) {
        return super.removeIf(filter);
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.viaversion.viaversion.libs.fastutil.ints.IntIterator] */
    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntCollection
    public boolean addAll(IntCollection c) {
        boolean retVal = false;
        ?? iterator2 = c.iterator2();
        while (iterator2.hasNext()) {
            if (add(iterator2.nextInt())) {
                retVal = true;
            }
        }
        return retVal;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean addAll(Collection<? extends Integer> c) {
        if (c instanceof IntCollection) {
            return addAll((IntCollection) c);
        }
        return super.addAll(c);
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.viaversion.viaversion.libs.fastutil.ints.IntIterator] */
    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntCollection
    public boolean containsAll(IntCollection c) {
        ?? iterator2 = c.iterator2();
        while (iterator2.hasNext()) {
            if (!contains(iterator2.nextInt())) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean containsAll(Collection<?> c) {
        if (c instanceof IntCollection) {
            return containsAll((IntCollection) c);
        }
        return super.containsAll(c);
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.viaversion.viaversion.libs.fastutil.ints.IntIterator] */
    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntCollection
    public boolean removeAll(IntCollection c) {
        boolean retVal = false;
        ?? iterator2 = c.iterator2();
        while (iterator2.hasNext()) {
            if (rem(iterator2.nextInt())) {
                retVal = true;
            }
        }
        return retVal;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean removeAll(Collection<?> c) {
        if (c instanceof IntCollection) {
            return removeAll((IntCollection) c);
        }
        return super.removeAll(c);
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.viaversion.viaversion.libs.fastutil.ints.IntIterator] */
    @Override // com.viaversion.viaversion.libs.fastutil.ints.IntCollection
    public boolean retainAll(IntCollection c) {
        boolean retVal = false;
        ?? iterator2 = iterator2();
        while (iterator2.hasNext()) {
            if (!c.contains(iterator2.nextInt())) {
                iterator2.remove();
                retVal = true;
            }
        }
        return retVal;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean retainAll(Collection<?> c) {
        if (c instanceof IntCollection) {
            return retainAll((IntCollection) c);
        }
        return super.retainAll(c);
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.viaversion.viaversion.libs.fastutil.ints.IntIterator] */
    @Override // java.util.AbstractCollection
    public String toString() {
        StringBuilder s = new StringBuilder();
        ?? iterator2 = iterator2();
        int n = size();
        boolean first = true;
        s.append("{");
        while (true) {
            int i = n;
            n--;
            if (i != 0) {
                if (first) {
                    first = false;
                } else {
                    s.append(", ");
                }
                int k = iterator2.nextInt();
                s.append(String.valueOf(k));
            } else {
                s.append("}");
                return s.toString();
            }
        }
    }
}
