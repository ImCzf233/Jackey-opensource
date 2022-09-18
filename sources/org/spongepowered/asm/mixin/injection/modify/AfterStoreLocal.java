package org.spongepowered.asm.mixin.injection.modify;

import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;

@InjectionPoint.AtCode("STORE")
/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/injection/modify/AfterStoreLocal.class */
public class AfterStoreLocal extends BeforeLoadLocal {
    public AfterStoreLocal(InjectionPointData data) {
        super(data, 54, true);
    }
}
