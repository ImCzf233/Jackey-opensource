package jdk.nashorn.internal.runtime.arrays;

import java.lang.reflect.Array;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/arrays/JavaArrayIterator.class */
public class JavaArrayIterator extends ArrayLikeIterator<Object> {
    protected final Object array;
    protected final long length;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !JavaArrayIterator.class.desiredAssertionStatus();
    }

    public JavaArrayIterator(Object array, boolean includeUndefined) {
        super(includeUndefined);
        if ($assertionsDisabled || array.getClass().isArray()) {
            this.array = array;
            this.length = Array.getLength(array);
            return;
        }
        throw new AssertionError("expecting Java array object");
    }

    protected boolean indexInArray() {
        return this.index < this.length;
    }

    @Override // java.util.Iterator
    public Object next() {
        return Array.get(this.array, (int) bumpIndex());
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
        throw new UnsupportedOperationException("remove");
    }
}
