package jdk.nashorn.internal.runtime.arrays;

import java.util.NoSuchElementException;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptObject;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/arrays/ScriptObjectIterator.class */
public class ScriptObjectIterator extends ArrayLikeIterator<Object> {
    protected final ScriptObject obj;
    private final long length;

    public ScriptObjectIterator(ScriptObject obj, boolean includeUndefined) {
        super(includeUndefined);
        this.obj = obj;
        this.length = JSType.toUint32(obj.getLength());
        this.index = 0L;
    }

    protected boolean indexInArray() {
        return this.index < this.length;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator
    public long getLength() {
        return this.length;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        if (this.length == 0) {
            return false;
        }
        while (indexInArray() && !this.obj.has(this.index) && !this.includeUndefined) {
            bumpIndex();
        }
        return indexInArray();
    }

    @Override // java.util.Iterator
    public Object next() {
        if (indexInArray()) {
            return this.obj.get(bumpIndex());
        }
        throw new NoSuchElementException();
    }
}
