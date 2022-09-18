package jdk.nashorn.internal.runtime.arrays;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptRuntime;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/arrays/IntArrayData.class */
public final class IntArrayData extends ContinuousArrayData implements IntElements {
    private int[] array;
    private static final MethodHandle HAS_GET_ELEM;
    private static final MethodHandle SET_ELEM;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !IntArrayData.class.desiredAssertionStatus();
        HAS_GET_ELEM = CompilerConstants.specialCall(MethodHandles.lookup(), IntArrayData.class, "getElem", Integer.TYPE, Integer.TYPE).methodHandle();
        SET_ELEM = CompilerConstants.specialCall(MethodHandles.lookup(), IntArrayData.class, "setElem", Void.TYPE, Integer.TYPE, Integer.TYPE).methodHandle();
    }

    public IntArrayData() {
        this(new int[32], 0);
    }

    public IntArrayData(int length) {
        super(length);
        this.array = new int[ArrayData.nextSize(length)];
    }

    public IntArrayData(int[] array, int length) {
        super(length);
        if ($assertionsDisabled || array == null || array.length >= length) {
            this.array = array;
            return;
        }
        throw new AssertionError();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public final Class<?> getElementType() {
        return Integer.TYPE;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public final Class<?> getBoxedElementType() {
        return Integer.class;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.AnyElements
    public final int getElementWeight() {
        return 1;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public final ContinuousArrayData widest(ContinuousArrayData otherData) {
        return otherData;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object[] asObjectArray() {
        return toObjectArray(true);
    }

    private int getElem(int index) {
        if (has(index)) {
            return this.array[index];
        }
        throw new ClassCastException();
    }

    private void setElem(int index, int elem) {
        if (hasRoomFor(index)) {
            this.array[index] = elem;
            return;
        }
        throw new ClassCastException();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public MethodHandle getElementGetter(Class<?> returnType, int programPoint) {
        return getContinuousElementGetter(HAS_GET_ELEM, returnType, programPoint);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public MethodHandle getElementSetter(Class<?> elementType) {
        if (elementType == Integer.TYPE) {
            return getContinuousElementSetter(SET_ELEM, elementType);
        }
        return null;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData, jdk.nashorn.internal.runtime.arrays.ArrayData
    public IntArrayData copy() {
        return new IntArrayData((int[]) this.array.clone(), (int) length());
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object asArrayOfType(Class<?> componentType) {
        if (componentType == Integer.TYPE) {
            int len = (int) length();
            return this.array.length == len ? (int[]) this.array.clone() : Arrays.copyOf(this.array, len);
        }
        return super.asArrayOfType(componentType);
    }

    private Object[] toObjectArray(boolean trim) {
        if ($assertionsDisabled || length() <= this.array.length) {
            int len = (int) length();
            Object[] oarray = new Object[trim ? len : this.array.length];
            for (int index = 0; index < len; index++) {
                oarray[index] = Integer.valueOf(this.array[index]);
            }
            return oarray;
        }
        throw new AssertionError("length exceeds internal array size");
    }

    private double[] toDoubleArray() {
        if ($assertionsDisabled || length() <= this.array.length) {
            int len = (int) length();
            double[] darray = new double[this.array.length];
            for (int index = 0; index < len; index++) {
                darray[index] = this.array[index];
            }
            return darray;
        }
        throw new AssertionError("length exceeds internal array size");
    }

    private NumberArrayData convertToDouble() {
        return new NumberArrayData(toDoubleArray(), (int) length());
    }

    private ObjectArrayData convertToObject() {
        return new ObjectArrayData(toObjectArray(false), (int) length());
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData convert(Class<?> type) {
        if (type == Integer.class || type == Byte.class || type == Short.class) {
            return this;
        }
        if (type == Double.class || type == Float.class) {
            return convertToDouble();
        }
        return convertToObject();
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
        Arrays.fill(this.array, (int) newLength, this.array.length, 0);
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int index, Object value, boolean strict) {
        if (JSType.isRepresentableAsInt(value)) {
            return set(index, JSType.toInt32(value), strict);
        }
        if (value == ScriptRuntime.UNDEFINED) {
            return new UndefinedArrayFilter(this).set(index, value, strict);
        }
        ArrayData newData = convert(value == null ? Object.class : value.getClass());
        return newData.set(index, value, strict);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int index, int value, boolean strict) {
        this.array[index] = value;
        setLength(Math.max(index + 1, length()));
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int index, double value, boolean strict) {
        if (JSType.isRepresentableAsInt(value)) {
            this.array[index] = (int) value;
            setLength(Math.max(index + 1, length()));
            return this;
        }
        return convert(Double.class).set(index, value, strict);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public int getInt(int index) {
        return this.array[index];
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public int getIntOptimistic(int index, int programPoint) {
        return this.array[index];
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public double getDouble(int index) {
        return this.array[index];
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public double getDoubleOptimistic(int index, int programPoint) {
        return this.array[index];
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object getObject(int index) {
        return Integer.valueOf(this.array[index]);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public boolean has(int index) {
        return 0 <= index && ((long) index) < length();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData delete(int index) {
        return new DeletedRangeArrayFilter(this, index, index);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData delete(long fromIndex, long toIndex) {
        return new DeletedRangeArrayFilter(this, fromIndex, toIndex);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object pop() {
        int len = (int) length();
        if (len == 0) {
            return ScriptRuntime.UNDEFINED;
        }
        int newLength = len - 1;
        int elem = this.array[newLength];
        this.array[newLength] = 0;
        setLength(newLength);
        return Integer.valueOf(elem);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData slice(long from, long to) {
        return new IntArrayData(Arrays.copyOfRange(this.array, (int) from, (int) to), (int) (to - (from < 0 ? from + length() : from)));
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData fastSplice(int start, int removed, int added) throws UnsupportedOperationException {
        int[] newArray;
        long oldLength = length();
        long newLength = (oldLength - removed) + added;
        if (newLength > 131072 && newLength > this.array.length) {
            throw new UnsupportedOperationException();
        }
        ArrayData returnValue = removed == 0 ? EMPTY_ARRAY : new IntArrayData(Arrays.copyOfRange(this.array, start, start + removed), removed);
        if (newLength != oldLength) {
            if (newLength > this.array.length) {
                newArray = new int[ArrayData.nextSize((int) newLength)];
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
    public double fastPush(int arg) {
        int len = (int) length();
        if (len == this.array.length) {
            this.array = Arrays.copyOf(this.array, nextSize(len));
        }
        this.array[len] = arg;
        return increaseLength();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public int fastPopInt() {
        if (length() == 0) {
            throw new ClassCastException();
        }
        int newLength = (int) decreaseLength();
        int elem = this.array[newLength];
        this.array[newLength] = 0;
        return elem;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public double fastPopDouble() {
        return fastPopInt();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public Object fastPopObject() {
        return Integer.valueOf(fastPopInt());
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public ContinuousArrayData fastConcat(ContinuousArrayData otherData) {
        int otherLength = (int) otherData.length();
        int thisLength = (int) length();
        if ($assertionsDisabled || (otherLength > 0 && thisLength > 0)) {
            int[] otherArray = ((IntArrayData) otherData).array;
            int newLength = otherLength + thisLength;
            int[] newArray = new int[ArrayData.alignUp(newLength)];
            System.arraycopy(this.array, 0, newArray, 0, thisLength);
            System.arraycopy(otherArray, 0, newArray, thisLength, otherLength);
            return new IntArrayData(newArray, newLength);
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
