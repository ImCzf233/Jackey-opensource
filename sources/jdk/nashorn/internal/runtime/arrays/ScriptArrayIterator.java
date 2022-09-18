package jdk.nashorn.internal.runtime.arrays;

import jdk.nashorn.internal.runtime.ScriptObject;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/arrays/ScriptArrayIterator.class */
public class ScriptArrayIterator extends ArrayLikeIterator<Object> {
    protected final ScriptObject array;
    protected final long length;

    public ScriptArrayIterator(ScriptObject array, boolean includeUndefined) {
        super(includeUndefined);
        this.array = array;
        this.length = array.getArray().length();
    }

    protected boolean indexInArray() {
        return this.index < this.length;
    }

    @Override // java.util.Iterator
    public Object next() {
        return this.array.get(bumpIndex());
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator
    public long getLength() {
        return this.length;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        if (!this.includeUndefined) {
            while (indexInArray() && !this.array.has(this.index)) {
                bumpIndex();
            }
        }
        return indexInArray();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator, java.util.Iterator
    public void remove() {
        this.array.delete(this.index, false);
    }
}
