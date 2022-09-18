package org.spongepowered.asm.mixin.injection.code;

import org.spongepowered.asm.mixin.injection.IInjectionPointContext;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/injection/code/ISliceContext.class */
public interface ISliceContext extends IInjectionPointContext {
    MethodSlice getSlice(String str);
}
