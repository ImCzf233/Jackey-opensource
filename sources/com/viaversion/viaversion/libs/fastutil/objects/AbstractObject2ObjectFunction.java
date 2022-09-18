package com.viaversion.viaversion.libs.fastutil.objects;

import java.io.Serializable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/fastutil/objects/AbstractObject2ObjectFunction.class */
public abstract class AbstractObject2ObjectFunction<K, V> implements Object2ObjectFunction<K, V>, Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected V defRetValue;

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectFunction
    public void defaultReturnValue(V rv) {
        this.defRetValue = rv;
    }

    @Override // com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectFunction
    public V defaultReturnValue() {
        return this.defRetValue;
    }
}
