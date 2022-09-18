package jdk.nashorn.internal.runtime.arrays;

import java.nio.ByteBuffer;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.ScriptRuntime;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/arrays/ByteBufferArrayData.class */
public final class ByteBufferArrayData extends ArrayData {
    private final ByteBuffer buf;

    ByteBufferArrayData(int length) {
        super(length);
        this.buf = ByteBuffer.allocateDirect(length);
    }

    public ByteBufferArrayData(ByteBuffer buf) {
        super(buf.capacity());
        this.buf = buf;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public PropertyDescriptor getDescriptor(Global global, int index) {
        return global.newDataDescriptor(getObject(index), false, true, true);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData copy() {
        throw unsupported("copy");
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object[] asObjectArray() {
        throw unsupported("asObjectArray");
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public void setLength(long length) {
        throw new UnsupportedOperationException("setLength");
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData shiftLeft(int by) {
        throw unsupported("shiftLeft");
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData shiftRight(int by) {
        throw unsupported("shiftRight");
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData ensure(long safeIndex) {
        if (safeIndex < this.buf.capacity()) {
            return this;
        }
        throw unsupported("ensure");
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData shrink(long newLength) {
        throw unsupported("shrink");
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int index, Object value, boolean strict) {
        if (value instanceof Number) {
            this.buf.put(index, ((Number) value).byteValue());
            return this;
        }
        throw ECMAErrors.typeError("not.a.number", ScriptRuntime.safeToString(value));
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int index, int value, boolean strict) {
        this.buf.put(index, (byte) value);
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int index, double value, boolean strict) {
        this.buf.put(index, (byte) value);
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public int getInt(int index) {
        return 255 & this.buf.get(index);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public double getDouble(int index) {
        return 255 & this.buf.get(index);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object getObject(int index) {
        return Integer.valueOf(255 & this.buf.get(index));
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public boolean has(int index) {
        return index > -1 && index < this.buf.capacity();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public boolean canDelete(int index, boolean strict) {
        return false;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public boolean canDelete(long longIndex, boolean strict) {
        return false;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData delete(int index) {
        throw unsupported("delete");
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData delete(long fromIndex, long toIndex) {
        throw unsupported("delete");
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData push(boolean strict, Object... items) {
        throw unsupported("push");
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object pop() {
        throw unsupported("pop");
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData slice(long from, long to) {
        throw unsupported("slice");
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData convert(Class<?> type) {
        throw unsupported("convert");
    }

    private static UnsupportedOperationException unsupported(String method) {
        return new UnsupportedOperationException(method);
    }
}
