package com.viaversion.viaversion.libs.fastutil.ints;

import java.io.Serializable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/ints/AbstractInt2IntFunction.class */
public abstract class AbstractInt2IntFunction implements Int2IntFunction, Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected int defRetValue;

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction
    public void defaultReturnValue(int rv) {
        this.defRetValue = rv;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction
    public int defaultReturnValue() {
        return this.defRetValue;
    }
}
