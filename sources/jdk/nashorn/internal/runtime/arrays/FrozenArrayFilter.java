package jdk.nashorn.internal.runtime.arrays;

import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.ScriptRuntime;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/arrays/FrozenArrayFilter.class */
public final class FrozenArrayFilter extends SealedArrayFilter {
    public FrozenArrayFilter(ArrayData underlying) {
        super(underlying);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.SealedArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData copy() {
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.SealedArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public PropertyDescriptor getDescriptor(Global global, int index) {
        return global.newDataDescriptor(getObject(index), false, true, false);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int index, int value, boolean strict) {
        if (strict) {
            throw ECMAErrors.typeError("cant.set.property", Integer.toString(index), "frozen array");
        }
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int index, double value, boolean strict) {
        if (strict) {
            throw ECMAErrors.typeError("cant.set.property", Integer.toString(index), "frozen array");
        }
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData set(int index, Object value, boolean strict) {
        if (strict) {
            throw ECMAErrors.typeError("cant.set.property", Integer.toString(index), "frozen array");
        }
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData push(boolean strict, Object... items) {
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayFilter, jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object pop() {
        int len = (int) this.underlying.length();
        return len == 0 ? ScriptRuntime.UNDEFINED : this.underlying.getObject(len - 1);
    }
}
