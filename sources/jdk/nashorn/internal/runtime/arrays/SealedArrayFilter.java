package jdk.nashorn.internal.runtime.arrays;

import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.PropertyDescriptor;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/arrays/SealedArrayFilter.class */
public class SealedArrayFilter extends ArrayFilter {
    public SealedArrayFilter(ArrayData underlying) {
        super(underlying);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData copy() {
        return new SealedArrayFilter(this.underlying.copy());
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData slice(long from, long to) {
        return getUnderlying().slice(from, to);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public boolean canDelete(int index, boolean strict) {
        return canDelete(ArrayIndex.toLongIndex(index), strict);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public boolean canDelete(long longIndex, boolean strict) {
        if (strict) {
            throw ECMAErrors.typeError("cant.delete.property", Long.toString(longIndex), "sealed array");
        }
        return false;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public PropertyDescriptor getDescriptor(Global global, int index) {
        return global.newDataDescriptor(getObject(index), false, true, true);
    }
}
