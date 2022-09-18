package com.viaversion.viaversion.libs.fastutil.ints;

import java.io.Serializable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/AbstractInt2ObjectFunction.class */
public abstract class AbstractInt2ObjectFunction<V> implements Int2ObjectFunction<V>, Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected V defRetValue;

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction
    public void defaultReturnValue(V rv) {
        this.defRetValue = rv;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction
    public V defaultReturnValue() {
        return this.defRetValue;
    }
}
