package jdk.nashorn.internal.runtime.arrays;

import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptObject;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/arrays/ReverseScriptObjectIterator.class */
public final class ReverseScriptObjectIterator extends ScriptObjectIterator {
    public ReverseScriptObjectIterator(ScriptObject obj, boolean includeUndefined) {
        super(obj, includeUndefined);
        this.index = JSType.toUint32(obj.getLength()) - 1;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator
    public boolean isReverse() {
        return true;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ScriptObjectIterator
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
