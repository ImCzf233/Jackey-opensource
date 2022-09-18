package org.spongepowered.asm.mixin.injection;

import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.refmap.IMixinContext;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/injection/IInjectionPointContext.class */
public interface IInjectionPointContext {
    IMixinContext getContext();

    MethodNode getMethod();

    AnnotationNode getAnnotation();
}
