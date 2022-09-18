package org.spongepowered.tools.obfuscation;

import java.lang.annotation.Annotation;
import java.util.Set;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeUtils;

@SupportedAnnotationTypes({"org.spongepowered.asm.mixin.injection.Inject", "org.spongepowered.asm.mixin.injection.ModifyArg", "org.spongepowered.asm.mixin.injection.ModifyArgs", "org.spongepowered.asm.mixin.injection.Redirect", "org.spongepowered.asm.mixin.injection.At"})
/* loaded from: Jackey Client b2.jar:org/spongepowered/tools/obfuscation/MixinObfuscationProcessorInjection.class */
public class MixinObfuscationProcessorInjection extends MixinObfuscationProcessor {
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) {
            postProcess(roundEnv);
            return true;
        }
        processMixins(roundEnv);
        processInjectors(roundEnv, Inject.class);
        processInjectors(roundEnv, ModifyArg.class);
        processInjectors(roundEnv, ModifyArgs.class);
        processInjectors(roundEnv, Redirect.class);
        processInjectors(roundEnv, ModifyVariable.class);
        processInjectors(roundEnv, ModifyConstant.class);
        postProcess(roundEnv);
        return true;
    }

    @Override // org.spongepowered.tools.obfuscation.MixinObfuscationProcessor
    public void postProcess(RoundEnvironment roundEnv) {
        super.postProcess(roundEnv);
        try {
            this.mixins.writeReferences();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void processInjectors(RoundEnvironment roundEnv, Class<? extends Annotation> injectorClass) {
        for (ExecutableElement executableElement : roundEnv.getElementsAnnotatedWith(injectorClass)) {
            TypeElement enclosingElement = executableElement.getEnclosingElement();
            if (!(enclosingElement instanceof TypeElement)) {
                throw new IllegalStateException("@" + injectorClass.getSimpleName() + " element has unexpected parent with type " + TypeUtils.getElementType(enclosingElement));
            }
            AnnotationHandle inject = AnnotationHandle.m3of(executableElement, injectorClass);
            if (executableElement.getKind() == ElementKind.METHOD) {
                this.mixins.registerInjector(enclosingElement, executableElement, inject);
            } else {
                this.mixins.printMessage(Diagnostic.Kind.WARNING, "Found an @" + injectorClass.getSimpleName() + " annotation on an element which is not a method: " + executableElement.toString());
            }
        }
    }
}
