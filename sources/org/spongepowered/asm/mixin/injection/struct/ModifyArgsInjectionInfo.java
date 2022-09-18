package org.spongepowered.asm.mixin.injection.struct;

import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.invoke.ModifyArgsInjector;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/injection/struct/ModifyArgsInjectionInfo.class */
public class ModifyArgsInjectionInfo extends InjectionInfo {
    public ModifyArgsInjectionInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation) {
        super(mixin, method, annotation);
    }

    @Override // org.spongepowered.asm.mixin.injection.struct.InjectionInfo
    protected Injector parseInjector(AnnotationNode injectAnnotation) {
        return new ModifyArgsInjector(this);
    }

    @Override // org.spongepowered.asm.mixin.injection.struct.InjectionInfo
    protected String getDescription() {
        return "Multi-argument modifier method";
    }
}
