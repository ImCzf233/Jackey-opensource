package jdk.nashorn.internal.runtime.arrays;

import java.lang.reflect.Array;
import jdk.nashorn.internal.runtime.BitVector;
import jdk.nashorn.internal.runtime.ScriptRuntime;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/arrays/DeletedArrayFilter.class */
public final class DeletedArrayFilter extends ArrayFilter {
    private final BitVector deleted;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !DeletedArrayFilter.class.desiredAssertionStatus();
    }

    public DeletedArrayFilter(ArrayData underlying) {
        super(underlying);
        this.deleted = new BitVector(underlying.length());
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData copy() {
        DeletedArrayFilter copy = new DeletedArrayFilter(this.underlying.copy());
        copy.getDeleted().copy(this.deleted);
        return copy;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object[] asObjectArray() {
        Object[] value = super.asObjectArray();
        for (int i = 0; i < value.length; i++) {
            if (this.deleted.isSet(i)) {
                value[i] = ScriptRuntime.UNDEFINED;
            }
        }
        return value;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object asArrayOfType(Class<?> componentType) {
        Object value = super.asArrayOfType(componentType);
        Object undefValue = convertUndefinedValue(componentType);
        int l = Array.getLength(value);
        for (int i = 0; i < l; i++) {
            if (this.deleted.isSet(i)) {
                Array.set(value, i, undefValue);
            }
        }
        return value;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData shiftLeft(int by) {
        super.shiftLeft(by);
        this.deleted.shiftLeft(by, length());
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData shiftRight(int by) {
        super.shiftRight(by);
        this.deleted.shiftRight(by, length());
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData ensure(long safeIndex) {
        if (safeIndex >= 131072 && safeIndex >= length()) {
            return new SparseArrayData(this, safeIndex + 1);
        }
        super.ensure(safeIndex);
        this.deleted.resize(length());
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData shrink(long newLength) {
        super.shrink(newLength);
        this.deleted.resize(length());
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int index, Object value, boolean strict) {
        this.deleted.clear(ArrayIndex.toLongIndex(index));
        return super.set(index, value, strict);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int index, int value, boolean strict) {
        this.deleted.clear(ArrayIndex.toLongIndex(index));
        return super.set(index, value, strict);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int index, double value, boolean strict) {
        this.deleted.clear(ArrayIndex.toLongIndex(index));
        return super.set(index, value, strict);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public boolean has(int index) {
        return super.has(index) && this.deleted.isClear(ArrayIndex.toLongIndex(index));
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData delete(int index) {
        long longIndex = ArrayIndex.toLongIndex(index);
        if ($assertionsDisabled || (longIndex >= 0 && longIndex < length())) {
            this.deleted.set(longIndex);
            this.underlying.setEmpty(index);
            return this;
        }
        throw new AssertionError();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData delete(long fromIndex, long toIndex) {
        if ($assertionsDisabled || (fromIndex >= 0 && fromIndex <= toIndex && toIndex < length())) {
            this.deleted.setRange(fromIndex, toIndex + 1);
            this.underlying.setEmpty(fromIndex, toIndex);
            return this;
        }
        throw new AssertionError();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object pop() {
        long index = length() - 1;
        if (super.has((int) index)) {
            boolean isDeleted = this.deleted.isSet(index);
            Object value = super.pop();
            return isDeleted ? ScriptRuntime.UNDEFINED : value;
        }
        return super.pop();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData slice(long from, long to) {
        ArrayData newArray = this.underlying.slice(from, to);
        DeletedArrayFilter newFilter = new DeletedArrayFilter(newArray);
        newFilter.getDeleted().copy(this.deleted);
        newFilter.getDeleted().shiftLeft(from, newFilter.length());
        return newFilter;
    }

    private BitVector getDeleted() {
        return this.deleted;
    }
}
