package jdk.nashorn.internal.runtime.arrays;

import java.lang.reflect.Array;
import jdk.nashorn.internal.runtime.ScriptRuntime;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/arrays/DeletedRangeArrayFilter.class */
public final class DeletedRangeArrayFilter extends ArrayFilter {

    /* renamed from: lo */
    private long f273lo;

    /* renamed from: hi */
    private long f274hi;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !DeletedRangeArrayFilter.class.desiredAssertionStatus();
    }

    public DeletedRangeArrayFilter(ArrayData underlying, long lo, long hi) {
        super(maybeSparse(underlying, hi));
        this.f273lo = lo;
        this.f274hi = hi;
    }

    private static ArrayData maybeSparse(ArrayData underlying, long hi) {
        if (hi < 131072 || (underlying instanceof SparseArrayData)) {
            return underlying;
        }
        return new SparseArrayData(underlying, underlying.length());
    }

    private boolean isEmpty() {
        return this.f273lo > this.f274hi;
    }

    private boolean isDeleted(int index) {
        long longIndex = ArrayIndex.toLongIndex(index);
        return this.f273lo <= longIndex && longIndex <= this.f274hi;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData copy() {
        return new DeletedRangeArrayFilter(this.underlying.copy(), this.f273lo, this.f274hi);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object[] asObjectArray() {
        Object[] value = super.asObjectArray();
        if (this.f273lo < 2147483647L) {
            int end = (int) Math.min(this.f274hi + 1, 2147483647L);
            for (int i = (int) this.f273lo; i < end; i++) {
                value[i] = ScriptRuntime.UNDEFINED;
            }
        }
        return value;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object asArrayOfType(Class<?> componentType) {
        Object value = super.asArrayOfType(componentType);
        Object undefValue = convertUndefinedValue(componentType);
        if (this.f273lo < 2147483647L) {
            int end = (int) Math.min(this.f274hi + 1, 2147483647L);
            for (int i = (int) this.f273lo; i < end; i++) {
                Array.set(value, i, undefValue);
            }
        }
        return value;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData ensure(long safeIndex) {
        if (safeIndex >= 131072 && safeIndex >= length()) {
            return new SparseArrayData(this, safeIndex + 1);
        }
        return super.ensure(safeIndex);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData shiftLeft(int by) {
        super.shiftLeft(by);
        this.f273lo = Math.max(0L, this.f273lo - by);
        this.f274hi = Math.max(-1L, this.f274hi - by);
        return isEmpty() ? getUnderlying() : this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData shiftRight(int by) {
        super.shiftRight(by);
        long len = length();
        this.f273lo = Math.min(len, this.f273lo + by);
        this.f274hi = Math.min(len - 1, this.f274hi + by);
        return isEmpty() ? getUnderlying() : this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData shrink(long newLength) {
        super.shrink(newLength);
        this.f273lo = Math.min(newLength, this.f273lo);
        this.f274hi = Math.min(newLength - 1, this.f274hi);
        return isEmpty() ? getUnderlying() : this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int index, Object value, boolean strict) {
        long longIndex = ArrayIndex.toLongIndex(index);
        if (longIndex < this.f273lo || longIndex > this.f274hi) {
            return super.set(index, value, strict);
        }
        if (longIndex > this.f273lo && longIndex < this.f274hi) {
            return getDeletedArrayFilter().set(index, value, strict);
        }
        if (longIndex == this.f273lo) {
            this.f273lo++;
        } else if (!$assertionsDisabled && longIndex != this.f274hi) {
            throw new AssertionError();
        } else {
            this.f274hi--;
        }
        return isEmpty() ? getUnderlying().set(index, value, strict) : super.set(index, value, strict);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int index, int value, boolean strict) {
        long longIndex = ArrayIndex.toLongIndex(index);
        if (longIndex < this.f273lo || longIndex > this.f274hi) {
            return super.set(index, value, strict);
        }
        if (longIndex > this.f273lo && longIndex < this.f274hi) {
            return getDeletedArrayFilter().set(index, value, strict);
        }
        if (longIndex == this.f273lo) {
            this.f273lo++;
        } else if (!$assertionsDisabled && longIndex != this.f274hi) {
            throw new AssertionError();
        } else {
            this.f274hi--;
        }
        return isEmpty() ? getUnderlying().set(index, value, strict) : super.set(index, value, strict);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int index, double value, boolean strict) {
        long longIndex = ArrayIndex.toLongIndex(index);
        if (longIndex < this.f273lo || longIndex > this.f274hi) {
            return super.set(index, value, strict);
        }
        if (longIndex > this.f273lo && longIndex < this.f274hi) {
            return getDeletedArrayFilter().set(index, value, strict);
        }
        if (longIndex == this.f273lo) {
            this.f273lo++;
        } else if (!$assertionsDisabled && longIndex != this.f274hi) {
            throw new AssertionError();
        } else {
            this.f274hi--;
        }
        return isEmpty() ? getUnderlying().set(index, value, strict) : super.set(index, value, strict);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public boolean has(int index) {
        return super.has(index) && !isDeleted(index);
    }

    private ArrayData getDeletedArrayFilter() {
        ArrayData deleteFilter = new DeletedArrayFilter(getUnderlying());
        deleteFilter.delete(this.f273lo, this.f274hi);
        return deleteFilter;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData delete(int index) {
        long longIndex = ArrayIndex.toLongIndex(index);
        this.underlying.setEmpty(index);
        if (longIndex + 1 == this.f273lo) {
            this.f273lo = longIndex;
        } else if (longIndex - 1 == this.f274hi) {
            this.f274hi = longIndex;
        } else if (longIndex < this.f273lo || this.f274hi < longIndex) {
            return getDeletedArrayFilter().delete(index);
        }
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData delete(long fromIndex, long toIndex) {
        if (fromIndex > this.f274hi + 1 || toIndex < this.f273lo - 1) {
            return getDeletedArrayFilter().delete(fromIndex, toIndex);
        }
        this.f273lo = Math.min(fromIndex, this.f273lo);
        this.f274hi = Math.max(toIndex, this.f274hi);
        this.underlying.setEmpty(this.f273lo, this.f274hi);
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object pop() {
        int index = ((int) length()) - 1;
        if (super.has(index)) {
            boolean isDeleted = isDeleted(index);
            Object value = super.pop();
            this.f273lo = Math.min(index + 1, this.f273lo);
            this.f274hi = Math.min(index, this.f274hi);
            return isDeleted ? ScriptRuntime.UNDEFINED : value;
        }
        return super.pop();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData slice(long from, long to) {
        return new DeletedRangeArrayFilter(this.underlying.slice(from, to), Math.max(0L, this.f273lo - from), Math.max(0L, this.f274hi - from));
    }
}
