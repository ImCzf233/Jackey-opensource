package org.spongepowered.tools.obfuscation;

import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import org.spongepowered.asm.mixin.Mixin;

/* loaded from: Jackey Client b2.jar:org/spongepowered/tools/obfuscation/MixinObfuscationProcessor.class */
public abstract class MixinObfuscationProcessor extends AbstractProcessor {
    protected AnnotatedMixins mixins;

    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.mixins = AnnotatedMixins.getMixinsForEnvironment(processingEnv);
    }

    public void processMixins(RoundEnvironment roundEnv) {
        this.mixins.onPassStarted();
        for (Element elem : roundEnv.getElementsAnnotatedWith(Mixin.class)) {
            if (elem.getKind() == ElementKind.CLASS || elem.getKind() == ElementKind.INTERFACE) {
                this.mixins.registerMixin((TypeElement) elem);
            } else {
                this.mixins.printMessage(Diagnostic.Kind.ERROR, "Found an @Mixin annotation on an element which is not a class or interface", elem);
            }
        }
    }

    public void postProcess(RoundEnvironment roundEnv) {
        this.mixins.onPassCompleted(roundEnv);
    }

    public SourceVersion getSupportedSourceVersion() {
        try {
            return SourceVersion.valueOf("RELEASE_8");
        } catch (IllegalArgumentException e) {
            return super.getSupportedSourceVersion();
        }
    }

    public Set<String> getSupportedOptions() {
        return SupportedOptions.getAllOptions();
    }
}
