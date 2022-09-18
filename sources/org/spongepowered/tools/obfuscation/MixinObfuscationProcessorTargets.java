package org.spongepowered.tools.obfuscation;

import java.util.Set;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeUtils;

@SupportedAnnotationTypes({"org.spongepowered.asm.mixin.Mixin", "org.spongepowered.asm.mixin.Shadow", "org.spongepowered.asm.mixin.Overwrite", "org.spongepowered.asm.mixin.gen.Accessor", "org.spongepowered.asm.mixin.Implements"})
/* loaded from: Jackey Client b2.jar:org/spongepowered/tools/obfuscation/MixinObfuscationProcessorTargets.class */
public class MixinObfuscationProcessorTargets extends MixinObfuscationProcessor {
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) {
            postProcess(roundEnv);
            return true;
        }
        processMixins(roundEnv);
        processShadows(roundEnv);
        processOverwrites(roundEnv);
        processAccessors(roundEnv);
        processInvokers(roundEnv);
        processImplements(roundEnv);
        postProcess(roundEnv);
        return true;
    }

    @Override // org.spongepowered.tools.obfuscation.MixinObfuscationProcessor
    public void postProcess(RoundEnvironment roundEnv) {
        super.postProcess(roundEnv);
        try {
            this.mixins.writeReferences();
            this.mixins.writeMappings();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void processShadows(RoundEnvironment roundEnv) {
        for (Element elem : roundEnv.getElementsAnnotatedWith(Shadow.class)) {
            TypeElement enclosingElement = elem.getEnclosingElement();
            if (!(enclosingElement instanceof TypeElement)) {
                this.mixins.printMessage(Diagnostic.Kind.ERROR, "Unexpected parent with type " + TypeUtils.getElementType(enclosingElement), elem);
            } else {
                AnnotationHandle shadow = AnnotationHandle.m3of(elem, Shadow.class);
                if (elem.getKind() == ElementKind.FIELD) {
                    this.mixins.registerShadow(enclosingElement, (VariableElement) elem, shadow);
                } else if (elem.getKind() == ElementKind.METHOD) {
                    this.mixins.registerShadow(enclosingElement, (ExecutableElement) elem, shadow);
                } else {
                    this.mixins.printMessage(Diagnostic.Kind.ERROR, "Element is not a method or field", elem);
                }
            }
        }
    }

    private void processOverwrites(RoundEnvironment roundEnv) {
        for (Element elem : roundEnv.getElementsAnnotatedWith(Overwrite.class)) {
            TypeElement enclosingElement = elem.getEnclosingElement();
            if (!(enclosingElement instanceof TypeElement)) {
                this.mixins.printMessage(Diagnostic.Kind.ERROR, "Unexpected parent with type " + TypeUtils.getElementType(enclosingElement), elem);
            } else if (elem.getKind() == ElementKind.METHOD) {
                this.mixins.registerOverwrite(enclosingElement, (ExecutableElement) elem);
            } else {
                this.mixins.printMessage(Diagnostic.Kind.ERROR, "Element is not a method", elem);
            }
        }
    }

    private void processAccessors(RoundEnvironment roundEnv) {
        for (Element elem : roundEnv.getElementsAnnotatedWith(Accessor.class)) {
            TypeElement enclosingElement = elem.getEnclosingElement();
            if (!(enclosingElement instanceof TypeElement)) {
                this.mixins.printMessage(Diagnostic.Kind.ERROR, "Unexpected parent with type " + TypeUtils.getElementType(enclosingElement), elem);
            } else if (elem.getKind() == ElementKind.METHOD) {
                this.mixins.registerAccessor(enclosingElement, (ExecutableElement) elem);
            } else {
                this.mixins.printMessage(Diagnostic.Kind.ERROR, "Element is not a method", elem);
            }
        }
    }

    private void processInvokers(RoundEnvironment roundEnv) {
        for (Element elem : roundEnv.getElementsAnnotatedWith(Invoker.class)) {
            TypeElement enclosingElement = elem.getEnclosingElement();
            if (!(enclosingElement instanceof TypeElement)) {
                this.mixins.printMessage(Diagnostic.Kind.ERROR, "Unexpected parent with type " + TypeUtils.getElementType(enclosingElement), elem);
            } else if (elem.getKind() == ElementKind.METHOD) {
                this.mixins.registerInvoker(enclosingElement, (ExecutableElement) elem);
            } else {
                this.mixins.printMessage(Diagnostic.Kind.ERROR, "Element is not a method", elem);
            }
        }
    }

    private void processImplements(RoundEnvironment roundEnv) {
        for (Element elem : roundEnv.getElementsAnnotatedWith(Implements.class)) {
            if (elem.getKind() == ElementKind.CLASS || elem.getKind() == ElementKind.INTERFACE) {
                AnnotationHandle implementsAnnotation = AnnotationHandle.m3of(elem, Implements.class);
                this.mixins.registerSoftImplements((TypeElement) elem, implementsAnnotation);
            } else {
                this.mixins.printMessage(Diagnostic.Kind.ERROR, "Found an @Implements annotation on an element which is not a class or interface", elem);
            }
        }
    }
}
