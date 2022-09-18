package jdk.nashorn.internal.runtime.arrays;

import java.lang.invoke.MethodHandle;
import java.nio.Buffer;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.nashorn.internal.lookup.Lookup;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/arrays/TypedArrayData.class */
public abstract class TypedArrayData<T extends Buffer> extends ContinuousArrayData {

    /* renamed from: nb */
    protected final T f275nb;

    protected abstract MethodHandle getGetElem();

    protected abstract MethodHandle getSetElem();

    public TypedArrayData(T nb, int elementLength) {
        super(elementLength);
        this.f275nb = nb;
    }

    public final int getElementLength() {
        return (int) length();
    }

    public boolean isUnsigned() {
        return false;
    }

    public boolean isClamped() {
        return false;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public boolean canDelete(int index, boolean strict) {
        return false;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public boolean canDelete(long longIndex, boolean strict) {
        return false;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData, jdk.nashorn.internal.runtime.arrays.ArrayData
    public TypedArrayData<T> copy() {
        throw new UnsupportedOperationException();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object[] asObjectArray() {
        throw new UnsupportedOperationException();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData shiftLeft(int by) {
        throw new UnsupportedOperationException();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData shiftRight(int by) {
        throw new UnsupportedOperationException();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData ensure(long safeIndex) {
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData shrink(long newLength) {
        throw new UnsupportedOperationException();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public final boolean has(int index) {
        return 0 <= index && ((long) index) < length();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData delete(int index) {
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData delete(long fromIndex, long toIndex) {
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public TypedArrayData<T> convert(Class<?> type) {
        throw new UnsupportedOperationException();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object pop() {
        throw new UnsupportedOperationException();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData slice(long from, long to) {
        throw new UnsupportedOperationException();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public MethodHandle getElementGetter(Class<?> returnType, int programPoint) {
        MethodHandle getter = getContinuousElementGetter(getClass(), getGetElem(), returnType, programPoint);
        if (getter != null) {
            return Lookup.filterReturnType(getter, returnType);
        }
        return getter;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public MethodHandle getElementSetter(Class<?> elementType) {
        return getContinuousElementSetter(getClass(), Lookup.filterArgumentType(getSetElem(), 2, elementType), elementType);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    protected MethodHandle getContinuousElementSetter(Class<? extends ContinuousArrayData> clazz, MethodHandle setHas, Class<?> elementType) {
        MethodHandle mh = Lookup.filterArgumentType(setHas, 2, elementType);
        return Lookup.f248MH.asType(mh, mh.type().changeParameterType(0, clazz));
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData, jdk.nashorn.internal.runtime.arrays.ArrayData
    public GuardedInvocation findFastGetIndexMethod(Class<? extends ArrayData> clazz, CallSiteDescriptor desc, LinkRequest request) {
        GuardedInvocation inv = super.findFastGetIndexMethod(clazz, desc, request);
        if (inv != null) {
            return inv;
        }
        return null;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData, jdk.nashorn.internal.runtime.arrays.ArrayData
    public GuardedInvocation findFastSetIndexMethod(Class<? extends ArrayData> clazz, CallSiteDescriptor desc, LinkRequest request) {
        GuardedInvocation inv = super.findFastSetIndexMethod(clazz, desc, request);
        if (inv != null) {
            return inv;
        }
        return null;
    }
}
