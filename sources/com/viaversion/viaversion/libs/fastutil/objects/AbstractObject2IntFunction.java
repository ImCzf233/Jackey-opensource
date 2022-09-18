package com.viaversion.viaversion.libs.fastutil.objects;

import java.io.Serializable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/AbstractObject2IntFunction.class */
public abstract class AbstractObject2IntFunction<K> implements Object2IntFunction<K>, Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected int defRetValue;

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunction
    public void defaultReturnValue(int rv) {
        this.defRetValue = rv;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunction
    public int defaultReturnValue() {
        return this.defRetValue;
    }
}
