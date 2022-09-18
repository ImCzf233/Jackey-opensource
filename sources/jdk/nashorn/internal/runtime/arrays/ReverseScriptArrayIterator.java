package jdk.nashorn.internal.runtime.arrays;

import jdk.nashorn.internal.runtime.ScriptObject;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/arrays/ReverseScriptArrayIterator.class */
public final class ReverseScriptArrayIterator extends ScriptArrayIterator {
    public ReverseScriptArrayIterator(ScriptObject array, boolean includeUndefined) {
        super(array, includeUndefined);
        this.index = array.getArray().length() - 1;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator
    public boolean isReverse() {
        return true;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ScriptArrayIterator
    protected boolean indexInArray() {
        return this.index >= 0;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator
    public long bumpIndex() {
        long j = this.index;
        this.index = j - 1;
        return j;
    }
}
