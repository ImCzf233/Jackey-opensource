package jdk.nashorn.internal.runtime.arrays;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptRuntime;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/arrays/ObjectArrayData.class */
public final class ObjectArrayData extends ContinuousArrayData implements AnyElements {
    private Object[] array;
    private static final MethodHandle HAS_GET_ELEM;
    private static final MethodHandle SET_ELEM;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !ObjectArrayData.class.desiredAssertionStatus();
        HAS_GET_ELEM = CompilerConstants.specialCall(MethodHandles.lookup(), ObjectArrayData.class, "getElem", Object.class, Integer.TYPE).methodHandle();
        SET_ELEM = CompilerConstants.specialCall(MethodHandles.lookup(), ObjectArrayData.class, "setElem", Void.TYPE, Integer.TYPE, Object.class).methodHandle();
    }

    public ObjectArrayData(Object[] array, int length) {
        super(length);
        if ($assertionsDisabled || array.length >= length) {
            this.array = array;
            return;
        }
        throw new AssertionError();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public final Class<?> getElementType() {
        return Object.class;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public final Class<?> getBoxedElementType() {
        return getElementType();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.AnyElements
    public final int getElementWeight() {
        return 4;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public final ContinuousArrayData widest(ContinuousArrayData otherData) {
        return otherData instanceof NumericElements ? this : otherData;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ObjectArrayData copy() {
        return new ObjectArrayData((Object[]) this.array.clone(), (int) length());
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object[] asObjectArray() {
        return ((long) this.array.length) == length() ? (Object[]) this.array.clone() : asObjectArrayCopy();
    }

    private Object[] asObjectArrayCopy() {
        long len = length();
        if ($assertionsDisabled || len <= 2147483647L) {
            Object[] copy = new Object[(int) len];
            System.arraycopy(this.array, 0, copy, 0, (int) len);
            return copy;
        }
        throw new AssertionError();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ObjectArrayData convert(Class<?> type) {
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData shiftLeft(int by) {
        if (by >= length()) {
            shrink(0L);
        } else {
            System.arraycopy(this.array, by, this.array, 0, this.array.length - by);
        }
        setLength(Math.max(0L, length() - by));
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData shiftRight(int by) {
        ArrayData newData = ensure((by + length()) - 1);
        if (newData != this) {
            newData.shiftRight(by);
            return newData;
        }
        System.arraycopy(this.array, 0, this.array, by, this.array.length - by);
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData ensure(long safeIndex) {
        if (safeIndex >= 131072) {
            return new SparseArrayData(this, safeIndex + 1);
        }
        int alen = this.array.length;
        if (safeIndex >= alen) {
            int newLength = ArrayData.nextSize((int) safeIndex);
            this.array = Arrays.copyOf(this.array, newLength);
        }
        if (safeIndex >= length()) {
            setLength(safeIndex + 1);
        }
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData shrink(long newLength) {
        Arrays.fill(this.array, (int) newLength, this.array.length, ScriptRuntime.UNDEFINED);
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int index, Object value, boolean strict) {
        this.array[index] = value;
        setLength(Math.max(index + 1, length()));
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int index, int value, boolean strict) {
        this.array[index] = Integer.valueOf(value);
        setLength(Math.max(index + 1, length()));
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int index, double value, boolean strict) {
        this.array[index] = Double.valueOf(value);
        setLength(Math.max(index + 1, length()));
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData setEmpty(int index) {
        this.array[index] = ScriptRuntime.EMPTY;
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData setEmpty(long lo, long hi) {
        Arrays.fill(this.array, (int) Math.max(lo, 0L), (int) Math.min(hi + 1, 2147483647L), ScriptRuntime.EMPTY);
        return this;
    }

    private Object getElem(int index) {
        if (has(index)) {
            return this.array[index];
        }
        throw new ClassCastException();
    }

    private void setElem(int index, Object elem) {
        if (hasRoomFor(index)) {
            this.array[index] = elem;
            return;
        }
        throw new ClassCastException();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public MethodHandle getElementGetter(Class<?> returnType, int programPoint) {
        if (returnType.isPrimitive()) {
            return null;
        }
        return getContinuousElementGetter(HAS_GET_ELEM, returnType, programPoint);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public MethodHandle getElementSetter(Class<?> elementType) {
        return getContinuousElementSetter(SET_ELEM, Object.class);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public int getInt(int index) {
        return JSType.toInt32(this.array[index]);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public double getDouble(int index) {
        return JSType.toNumber(this.array[index]);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object getObject(int index) {
        return this.array[index];
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public boolean has(int index) {
        return 0 <= index && ((long) index) < length();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData delete(int index) {
        setEmpty(index);
        return new DeletedRangeArrayFilter(this, index, index);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData delete(long fromIndex, long toIndex) {
        setEmpty(fromIndex, toIndex);
        return new DeletedRangeArrayFilter(this, fromIndex, toIndex);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public double fastPush(int arg) {
        return fastPush(Integer.valueOf(arg));
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public double fastPush(long arg) {
        return fastPush(Long.valueOf(arg));
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public double fastPush(double arg) {
        return fastPush(Double.valueOf(arg));
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public double fastPush(Object arg) {
        int len = (int) length();
        if (len == this.array.length) {
            this.array = Arrays.copyOf(this.array, nextSize(len));
        }
        this.array[len] = arg;
        return increaseLength();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public Object fastPopObject() {
        if (length() == 0) {
            return ScriptRuntime.UNDEFINED;
        }
        int newLength = (int) decreaseLength();
        Object elem = this.array[newLength];
        this.array[newLength] = ScriptRuntime.EMPTY;
        return elem;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object pop() {
        if (length() == 0) {
            return ScriptRuntime.UNDEFINED;
        }
        int newLength = ((int) length()) - 1;
        Object elem = this.array[newLength];
        setEmpty(newLength);
        setLength(newLength);
        return elem;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData slice(long from, long to) {
        long start = from < 0 ? from + length() : from;
        long newLength = to - start;
        return new ObjectArrayData(Arrays.copyOfRange(this.array, (int) from, (int) to), (int) newLength);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData push(boolean strict, Object item) {
        long len = length();
        ArrayData newData = ensure(len);
        if (newData == this) {
            this.array[(int) len] = item;
            return this;
        }
        return newData.set((int) len, item, strict);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData fastSplice(int start, int removed, int added) throws UnsupportedOperationException {
        Object[] newArray;
        long oldLength = length();
        long newLength = (oldLength - removed) + added;
        if (newLength > 131072 && newLength > this.array.length) {
            throw new UnsupportedOperationException();
        }
        ArrayData returnValue = removed == 0 ? EMPTY_ARRAY : new ObjectArrayData(Arrays.copyOfRange(this.array, start, start + removed), removed);
        if (newLength != oldLength) {
            if (newLength > this.array.length) {
                newArray = new Object[ArrayData.nextSize((int) newLength)];
                System.arraycopy(this.array, 0, newArray, 0, start);
            } else {
                newArray = this.array;
            }
            System.arraycopy(this.array, start + removed, newArray, start + added, (int) ((oldLength - start) - removed));
            this.array = newArray;
            setLength(newLength);
        }
        return returnValue;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public ContinuousArrayData fastConcat(ContinuousArrayData otherData) {
        int otherLength = (int) otherData.length();
        int thisLength = (int) length();
        if ($assertionsDisabled || (otherLength > 0 && thisLength > 0)) {
            Object[] otherArray = ((ObjectArrayData) otherData).array;
            int newLength = otherLength + thisLength;
            Object[] newArray = new Object[ArrayData.alignUp(newLength)];
            System.arraycopy(this.array, 0, newArray, 0, thisLength);
            System.arraycopy(otherArray, 0, newArray, thisLength, otherLength);
            return new ObjectArrayData(newArray, newLength);
        }
        throw new AssertionError();
    }

    public String toString() {
        if ($assertionsDisabled || length() <= this.array.length) {
            return getClass().getSimpleName() + ':' + Arrays.toString(Arrays.copyOf(this.array, (int) length()));
        }
        throw new AssertionError(length() + " > " + this.array.length);
    }
}
