package jdk.nashorn.internal.runtime.arrays;

import java.util.List;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/arrays/ReverseJavaListIterator.class */
public final class ReverseJavaListIterator extends JavaListIterator {
    public ReverseJavaListIterator(List<?> list, boolean includeUndefined) {
        super(list, includeUndefined);
        this.index = list.size() - 1;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator
    public boolean isReverse() {
        return true;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.JavaListIterator
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
