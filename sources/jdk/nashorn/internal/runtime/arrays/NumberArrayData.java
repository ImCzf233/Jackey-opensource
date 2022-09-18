package jdk.nashorn.internal.runtime.arrays;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import jdk.internal.dynalink.support.TypeUtilities;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptRuntime;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/arrays/NumberArrayData.class */
public final class NumberArrayData extends ContinuousArrayData implements NumericElements {
    private double[] array;
    private static final MethodHandle HAS_GET_ELEM;
    private static final MethodHandle SET_ELEM;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !NumberArrayData.class.desiredAssertionStatus();
        HAS_GET_ELEM = CompilerConstants.specialCall(MethodHandles.lookup(), NumberArrayData.class, "getElem", Double.TYPE, Integer.TYPE).methodHandle();
        SET_ELEM = CompilerConstants.specialCall(MethodHandles.lookup(), NumberArrayData.class, "setElem", Void.TYPE, Integer.TYPE, Double.TYPE).methodHandle();
    }

    public NumberArrayData(double[] array, int length) {
        super(length);
        if ($assertionsDisabled || array.length >= length) {
            this.array = array;
            return;
        }
        throw new AssertionError();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public final Class<?> getElementType() {
        return Double.TYPE;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public final Class<?> getBoxedElementType() {
        return Double.class;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.AnyElements
    public final int getElementWeight() {
        return 3;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public final ContinuousArrayData widest(ContinuousArrayData otherData) {
        return otherData instanceof IntOrLongElements ? this : otherData;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData, jdk.nashorn.internal.runtime.arrays.ArrayData
    public NumberArrayData copy() {
        return new NumberArrayData((double[]) this.array.clone(), (int) length());
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object[] asObjectArray() {
        return toObjectArray(true);
    }

    private Object[] toObjectArray(boolean trim) {
        if ($assertionsDisabled || length() <= this.array.length) {
            int len = (int) length();
            Object[] oarray = new Object[trim ? len : this.array.length];
            for (int index = 0; index < len; index++) {
                oarray[index] = Double.valueOf(this.array[index]);
            }
            return oarray;
        }
        throw new AssertionError("length exceeds internal array size");
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object asArrayOfType(Class<?> componentType) {
        if (componentType == Double.TYPE) {
            int len = (int) length();
            return this.array.length == len ? (double[]) this.array.clone() : Arrays.copyOf(this.array, len);
        }
        return super.asArrayOfType(componentType);
    }

    private static boolean canWiden(Class<?> type) {
        return (!TypeUtilities.isWrapperType(type) || type == Boolean.class || type == Character.class) ? false : true;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ContinuousArrayData convert(Class<?> type) {
        if (!canWiden(type)) {
            int len = (int) length();
            return new ObjectArrayData(toObjectArray(false), len);
        }
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
        Arrays.fill(this.array, (int) newLength, this.array.length, 0.0d);
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int index, Object value, boolean strict) {
        if ((value instanceof Double) || (value != null && canWiden(value.getClass()))) {
            return set(index, ((Number) value).doubleValue(), strict);
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
        this.array[index] = value;
        setLength(Math.max(index + 1, length()));
        return this;
    }

    private double getElem(int index) {
        if (has(index)) {
            return this.array[index];
        }
        throw new ClassCastException();
    }

    private void setElem(int index, double elem) {
        if (hasRoomFor(index)) {
            this.array[index] = elem;
            return;
        }
        throw new ClassCastException();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public MethodHandle getElementGetter(Class<?> returnType, int programPoint) {
        if (returnType == Integer.TYPE) {
            return null;
        }
        return getContinuousElementGetter(HAS_GET_ELEM, returnType, programPoint);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public MethodHandle getElementSetter(Class<?> elementType) {
        if (elementType.isPrimitive()) {
            return getContinuousElementSetter(Lookup.f248MH.asType(SET_ELEM, SET_ELEM.type().changeParameterType(2, elementType)), elementType);
        }
        return null;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public int getInt(int index) {
        return JSType.toInt32(this.array[index]);
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
        return Double.valueOf(this.array[index]);
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
        double elem = this.array[newLength];
        this.array[newLength] = 0.0d;
        setLength(newLength);
        return Double.valueOf(elem);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData slice(long from, long to) {
        long start = from < 0 ? from + length() : from;
        long newLength = to - start;
        return new NumberArrayData(Arrays.copyOfRange(this.array, (int) from, (int) to), (int) newLength);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData fastSplice(int start, int removed, int added) throws UnsupportedOperationException {
        double[] newArray;
        long oldLength = length();
        long newLength = (oldLength - removed) + added;
        if (newLength > 131072 && newLength > this.array.length) {
            throw new UnsupportedOperationException();
        }
        ArrayData returnValue = removed == 0 ? EMPTY_ARRAY : new NumberArrayData(Arrays.copyOfRange(this.array, start, start + removed), removed);
        if (newLength != oldLength) {
            if (newLength > this.array.length) {
                newArray = new double[ArrayData.nextSize((int) newLength)];
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
        return fastPush(arg);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public double fastPush(long arg) {
        return fastPush(arg);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public double fastPush(double arg) {
        int len = (int) length();
        if (len == this.array.length) {
            this.array = Arrays.copyOf(this.array, nextSize(len));
        }
        this.array[len] = arg;
        return increaseLength();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public double fastPopDouble() {
        if (length() == 0) {
            throw new ClassCastException();
        }
        int newLength = (int) decreaseLength();
        double elem = this.array[newLength];
        this.array[newLength] = 0.0d;
        return elem;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public Object fastPopObject() {
        return Double.valueOf(fastPopDouble());
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public ContinuousArrayData fastConcat(ContinuousArrayData otherData) {
        int otherLength = (int) otherData.length();
        int thisLength = (int) length();
        if ($assertionsDisabled || (otherLength > 0 && thisLength > 0)) {
            double[] otherArray = ((NumberArrayData) otherData).array;
            int newLength = otherLength + thisLength;
            double[] newArray = new double[ArrayData.alignUp(newLength)];
            System.arraycopy(this.array, 0, newArray, 0, thisLength);
            System.arraycopy(otherArray, 0, newArray, thisLength, otherLength);
            return new NumberArrayData(newArray, newLength);
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
