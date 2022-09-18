package jdk.nashorn.internal.runtime.arrays;

import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.internal.runtime.JSType;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/arrays/ReverseJSObjectIterator.class */
public final class ReverseJSObjectIterator extends JSObjectIterator {
    public ReverseJSObjectIterator(JSObject obj, boolean includeUndefined) {
        super(obj, includeUndefined);
        this.index = JSType.toUint32(obj.hasMember("length") ? obj.getMember("length") : 0) - 1;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator
    public boolean isReverse() {
        return true;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.JSObjectIterator
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
