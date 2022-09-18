package org.spongepowered.asm.mixin.injection.struct;

import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.modify.LocalVariableDiscriminator;
import org.spongepowered.asm.mixin.injection.modify.ModifyVariableInjector;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/injection/struct/ModifyVariableInjectionInfo.class */
public class ModifyVariableInjectionInfo extends InjectionInfo {
    public ModifyVariableInjectionInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation) {
        super(mixin, method, annotation);
    }

    @Override // org.spongepowered.asm.mixin.injection.struct.InjectionInfo
    protected Injector parseInjector(AnnotationNode injectAnnotation) {
        return new ModifyVariableInjector(this, LocalVariableDiscriminator.parse(injectAnnotation));
    }

    @Override // org.spongepowered.asm.mixin.injection.struct.InjectionInfo
    protected String getDescription() {
        return "Variable modifier method";
    }
}
