package jdk.nashorn.internal.runtime.arrays;

import java.lang.reflect.Array;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/arrays/ReverseJavaArrayIterator.class */
public final class ReverseJavaArrayIterator extends JavaArrayIterator {
    public ReverseJavaArrayIterator(Object array, boolean includeUndefined) {
        super(array, includeUndefined);
        this.index = Array.getLength(array) - 1;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator
    public boolean isReverse() {
        return true;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.JavaArrayIterator
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
