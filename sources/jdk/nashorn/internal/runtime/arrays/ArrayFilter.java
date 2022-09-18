package jdk.nashorn.internal.runtime.arrays;

import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.Undefined;
import jdk.nashorn.internal.runtime.linker.Bootstrap;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/arrays/ArrayFilter.class */
public abstract class ArrayFilter extends ArrayData {
    protected ArrayData underlying;

    public ArrayFilter(ArrayData underlying) {
        super(underlying.length());
        this.underlying = underlying;
    }

    public ArrayData getUnderlying() {
        return this.underlying;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public void setLength(long length) {
        super.setLength(length);
        this.underlying.setLength(length);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object[] asObjectArray() {
        return this.underlying.asObjectArray();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object asArrayOfType(Class<?> componentType) {
        return this.underlying.asArrayOfType(componentType);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData shiftLeft(int by) {
        this.underlying.shiftLeft(by);
        setLength(this.underlying.length());
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData shiftRight(int by) {
        this.underlying = this.underlying.shiftRight(by);
        setLength(this.underlying.length());
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData ensure(long safeIndex) {
        this.underlying = this.underlying.ensure(safeIndex);
        setLength(this.underlying.length());
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData shrink(long newLength) {
        this.underlying = this.underlying.shrink(newLength);
        setLength(this.underlying.length());
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int index, Object value, boolean strict) {
        this.underlying = this.underlying.set(index, value, strict);
        setLength(this.underlying.length());
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int index, int value, boolean strict) {
        this.underlying = this.underlying.set(index, value, strict);
        setLength(this.underlying.length());
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int index, double value, boolean strict) {
        this.underlying = this.underlying.set(index, value, strict);
        setLength(this.underlying.length());
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData setEmpty(int index) {
        this.underlying.setEmpty(index);
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData setEmpty(long lo, long hi) {
        this.underlying.setEmpty(lo, hi);
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public Type getOptimisticType() {
        return this.underlying.getOptimisticType();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public int getInt(int index) {
        return this.underlying.getInt(index);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public int getIntOptimistic(int index, int programPoint) {
        return this.underlying.getIntOptimistic(index, programPoint);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public double getDouble(int index) {
        return this.underlying.getDouble(index);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public double getDoubleOptimistic(int index, int programPoint) {
        return this.underlying.getDoubleOptimistic(index, programPoint);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object getObject(int index) {
        return this.underlying.getObject(index);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public boolean has(int index) {
        return this.underlying.has(index);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData delete(int index) {
        this.underlying = this.underlying.delete(index);
        setLength(this.underlying.length());
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData delete(long from, long to) {
        this.underlying = this.underlying.delete(from, to);
        setLength(this.underlying.length());
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData convert(Class<?> type) {
        this.underlying = this.underlying.convert(type);
        setLength(this.underlying.length());
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object pop() {
        Object value = this.underlying.pop();
        setLength(this.underlying.length());
        return value;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public long nextIndex(long index) {
        return this.underlying.nextIndex(index);
    }

    public static Object convertUndefinedValue(Class<?> targetType) {
        return invoke(Bootstrap.getLinkerServices().getTypeConverter(Undefined.class, targetType), ScriptRuntime.UNDEFINED);
    }
}
