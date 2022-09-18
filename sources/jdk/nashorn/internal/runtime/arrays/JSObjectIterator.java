package jdk.nashorn.internal.runtime.arrays;

import java.util.NoSuchElementException;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.internal.runtime.JSType;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/arrays/JSObjectIterator.class */
public class JSObjectIterator extends ArrayLikeIterator<Object> {
    protected final JSObject obj;
    private final long length;

    public JSObjectIterator(JSObject obj, boolean includeUndefined) {
        super(includeUndefined);
        this.obj = obj;
        this.length = JSType.toUint32(obj.hasMember("length") ? obj.getMember("length") : 0);
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
        while (indexInArray() && !this.obj.hasSlot((int) this.index) && !this.includeUndefined) {
            bumpIndex();
        }
        return indexInArray();
    }

    @Override // java.util.Iterator
    public Object next() {
        if (indexInArray()) {
            return this.obj.getSlot((int) bumpIndex());
        }
        throw new NoSuchElementException();
    }
}
