package jdk.nashorn.internal.runtime.arrays;

import java.util.List;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/arrays/JavaListIterator.class */
public class JavaListIterator extends ArrayLikeIterator<Object> {
    protected final List<?> list;
    protected final long length;

    public JavaListIterator(List<?> list, boolean includeUndefined) {
        super(includeUndefined);
        this.list = list;
        this.length = list.size();
    }

    protected boolean indexInArray() {
        return this.index < this.length;
    }

    @Override // java.util.Iterator
    public Object next() {
        return this.list.get((int) bumpIndex());
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator
    public long getLength() {
        return this.length;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return indexInArray();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator, java.util.Iterator
    public void remove() {
        this.list.remove(Long.valueOf(this.index));
    }
}
