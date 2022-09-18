package jdk.nashorn.internal.runtime.arrays;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.SwitchPoint;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.UnwarrantedOptimismException;
import jdk.nashorn.internal.runtime.linker.NashornCallSiteDescriptor;
import jdk.nashorn.internal.runtime.logging.Logger;

@Logger(name = "arrays")
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/arrays/ContinuousArrayData.class */
public abstract class ContinuousArrayData extends ArrayData {
    protected static final MethodHandle FAST_ACCESS_GUARD = Lookup.f248MH.dropArguments(CompilerConstants.staticCall(MethodHandles.lookup(), ContinuousArrayData.class, "guard", Boolean.TYPE, Class.class, ScriptObject.class).methodHandle(), 2, Integer.TYPE);

    public abstract MethodHandle getElementGetter(Class<?> cls, int i);

    public abstract MethodHandle getElementSetter(Class<?> cls);

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public abstract ContinuousArrayData copy();

    public abstract Class<?> getElementType();

    public abstract Class<?> getBoxedElementType();

    public ContinuousArrayData(long length) {
        super(length);
    }

    public final boolean hasRoomFor(int index) {
        return has(index) || (((long) index) == length() && ensure((long) index) == this);
    }

    public boolean isEmpty() {
        return length() == 0;
    }

    protected final int throwHas(int index) {
        if (!has(index)) {
            throw new ClassCastException();
        }
        return index;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public Type getOptimisticType() {
        return Type.typeFor(getElementType());
    }

    public ContinuousArrayData widest(ContinuousArrayData otherData) {
        Class<?> elementType = getElementType();
        return Type.widest(elementType, otherData.getElementType()) == elementType ? this : otherData;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final MethodHandle getContinuousElementGetter(MethodHandle get, Class<?> returnType, int programPoint) {
        return getContinuousElementGetter(getClass(), get, returnType, programPoint);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final MethodHandle getContinuousElementSetter(MethodHandle set, Class<?> returnType) {
        return getContinuousElementSetter(getClass(), set, returnType);
    }

    public MethodHandle getContinuousElementGetter(Class<? extends ContinuousArrayData> clazz, MethodHandle getHas, Class<?> returnType, int programPoint) {
        boolean isOptimistic = UnwarrantedOptimismException.isValid(programPoint);
        int fti = JSType.getAccessorTypeIndex(getHas.type().returnType());
        int ti = JSType.getAccessorTypeIndex(returnType);
        MethodHandle mh = getHas;
        if (isOptimistic && ti < fti) {
            mh = Lookup.f248MH.insertArguments(ArrayData.THROW_UNWARRANTED.methodHandle(), 1, Integer.valueOf(programPoint));
        }
        MethodHandle mh2 = Lookup.f248MH.asType(mh, mh.type().changeReturnType(returnType).changeParameterType(0, clazz));
        if (!isOptimistic) {
            return Lookup.filterReturnType(mh2, returnType);
        }
        return mh2;
    }

    protected MethodHandle getContinuousElementSetter(Class<? extends ContinuousArrayData> clazz, MethodHandle setHas, Class<?> elementType) {
        return Lookup.f248MH.asType(setHas, setHas.type().changeParameterType(2, elementType).changeParameterType(0, clazz));
    }

    private static final boolean guard(Class<? extends ContinuousArrayData> clazz, ScriptObject sobj) {
        if (sobj != null && sobj.getArray().getClass() == clazz) {
            return true;
        }
        return false;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public GuardedInvocation findFastGetIndexMethod(Class<? extends ArrayData> clazz, CallSiteDescriptor desc, LinkRequest request) {
        MethodType callType = desc.getMethodType();
        Class<?> indexType = callType.parameterType(1);
        Class<?> returnType = callType.returnType();
        if (ContinuousArrayData.class.isAssignableFrom(clazz) && indexType == Integer.TYPE) {
            Object[] args = request.getArguments();
            int index = ((Integer) args[args.length - 1]).intValue();
            if (has(index)) {
                MethodHandle getArray = ScriptObject.GET_ARRAY.methodHandle();
                int programPoint = NashornCallSiteDescriptor.isOptimistic(desc) ? NashornCallSiteDescriptor.getProgramPoint(desc) : -1;
                MethodHandle getElement = getElementGetter(returnType, programPoint);
                if (getElement != null) {
                    MethodHandle getElement2 = Lookup.f248MH.filterArguments(getElement, 0, Lookup.f248MH.asType(getArray, getArray.type().changeReturnType(clazz)));
                    MethodHandle guard = Lookup.f248MH.insertArguments(FAST_ACCESS_GUARD, 0, clazz);
                    return new GuardedInvocation(getElement2, guard, (SwitchPoint) null, ClassCastException.class);
                }
                return null;
            }
            return null;
        }
        return null;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public GuardedInvocation findFastSetIndexMethod(Class<? extends ArrayData> clazz, CallSiteDescriptor desc, LinkRequest request) {
        MethodHandle setElement;
        MethodType callType = desc.getMethodType();
        Class<?> indexType = callType.parameterType(1);
        Class<?> elementType = callType.parameterType(2);
        if (ContinuousArrayData.class.isAssignableFrom(clazz) && indexType == Integer.TYPE) {
            Object[] args = request.getArguments();
            int index = ((Integer) args[args.length - 2]).intValue();
            if (hasRoomFor(index) && (setElement = getElementSetter(elementType)) != null) {
                MethodHandle getArray = ScriptObject.GET_ARRAY.methodHandle();
                MethodHandle setElement2 = Lookup.f248MH.filterArguments(setElement, 0, Lookup.f248MH.asType(getArray, getArray.type().changeReturnType(getClass())));
                MethodHandle guard = Lookup.f248MH.insertArguments(FAST_ACCESS_GUARD, 0, clazz);
                return new GuardedInvocation(setElement2, guard, (SwitchPoint) null, ClassCastException.class);
            }
            return null;
        }
        return null;
    }

    public double fastPush(int arg) {
        throw new ClassCastException(String.valueOf(getClass()));
    }

    public double fastPush(long arg) {
        throw new ClassCastException(String.valueOf(getClass()));
    }

    public double fastPush(double arg) {
        throw new ClassCastException(String.valueOf(getClass()));
    }

    public double fastPush(Object arg) {
        throw new ClassCastException(String.valueOf(getClass()));
    }

    public int fastPopInt() {
        throw new ClassCastException(String.valueOf(getClass()));
    }

    public double fastPopDouble() {
        throw new ClassCastException(String.valueOf(getClass()));
    }

    public Object fastPopObject() {
        throw new ClassCastException(String.valueOf(getClass()));
    }

    public ContinuousArrayData fastConcat(ContinuousArrayData otherData) {
        throw new ClassCastException(String.valueOf(getClass()) + " != " + String.valueOf(otherData.getClass()));
    }
}
