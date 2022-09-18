package org.spongepowered.asm.mixin.injection.struct;

import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.invoke.ModifyArgInjector;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
import org.spongepowered.asm.util.Annotations;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/injection/struct/ModifyArgInjectionInfo.class */
public class ModifyArgInjectionInfo extends InjectionInfo {
    public ModifyArgInjectionInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation) {
        super(mixin, method, annotation);
    }

    @Override // org.spongepowered.asm.mixin.injection.struct.InjectionInfo
    protected Injector parseInjector(AnnotationNode injectAnnotation) {
        int index = ((Integer) Annotations.getValue(injectAnnotation, "index", -1)).intValue();
        return new ModifyArgInjector(this, index);
    }

    @Override // org.spongepowered.asm.mixin.injection.struct.InjectionInfo
    protected String getDescription() {
        return "Argument modifier method";
    }
}
