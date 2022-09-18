package org.spongepowered.tools.obfuscation;

import java.lang.reflect.Method;
import javax.lang.model.element.ExecutableElement;
import javax.tools.Diagnostic;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.AnnotatedMixinElementHandler;
import org.spongepowered.tools.obfuscation.Mappings;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;

/* loaded from: Jackey Client b2.jar:org/spongepowered/tools/obfuscation/AnnotatedMixinElementHandlerOverwrite.class */
public class AnnotatedMixinElementHandlerOverwrite extends AnnotatedMixinElementHandler {

    /* loaded from: Jackey Client b2.jar:org/spongepowered/tools/obfuscation/AnnotatedMixinElementHandlerOverwrite$AnnotatedElementOverwrite.class */
    public static class AnnotatedElementOverwrite extends AnnotatedMixinElementHandler.AnnotatedElement<ExecutableElement> {
        private final boolean shouldRemap;

        public AnnotatedElementOverwrite(ExecutableElement element, AnnotationHandle annotation, boolean shouldRemap) {
            super(element, annotation);
            this.shouldRemap = shouldRemap;
        }

        public boolean shouldRemap() {
            return this.shouldRemap;
        }
    }

    public AnnotatedMixinElementHandlerOverwrite(IMixinAnnotationProcessor ap, AnnotatedMixin mixin) {
        super(ap, mixin);
    }

    public void registerMerge(ExecutableElement method) {
        validateTargetMethod(method, null, new AnnotatedMixinElementHandler.AliasedElementName(method, AnnotationHandle.MISSING), "overwrite", true, true);
    }

    public void registerOverwrite(AnnotatedElementOverwrite elem) {
        AnnotatedMixinElementHandler.AliasedElementName name = new AnnotatedMixinElementHandler.AliasedElementName(elem.getElement(), elem.getAnnotation());
        validateTargetMethod(elem.getElement(), elem.getAnnotation(), name, "@Overwrite", true, false);
        checkConstraints(elem.getElement(), elem.getAnnotation());
        if (elem.shouldRemap()) {
            for (TypeHandle target : this.mixin.getTargets()) {
                if (!registerOverwriteForTarget(elem, target)) {
                    return;
                }
            }
        }
        if (!"true".equalsIgnoreCase(this.f447ap.getOption(SupportedOptions.DISABLE_OVERWRITE_CHECKER))) {
            Diagnostic.Kind overwriteErrorKind = "error".equalsIgnoreCase(this.f447ap.getOption(SupportedOptions.OVERWRITE_ERROR_LEVEL)) ? Diagnostic.Kind.ERROR : Diagnostic.Kind.WARNING;
            String javadoc = this.f447ap.getJavadocProvider().getJavadoc(elem.getElement());
            if (javadoc == null) {
                this.f447ap.printMessage(overwriteErrorKind, "@Overwrite is missing javadoc comment", elem.getElement());
                return;
            }
            if (!javadoc.toLowerCase().contains("@author")) {
                this.f447ap.printMessage(overwriteErrorKind, "@Overwrite is missing an @author tag", elem.getElement());
            }
            if (!javadoc.toLowerCase().contains("@reason")) {
                this.f447ap.printMessage(overwriteErrorKind, "@Overwrite is missing an @reason tag", elem.getElement());
            }
        }
    }

    private boolean registerOverwriteForTarget(AnnotatedElementOverwrite elem, TypeHandle target) {
        MappingMethod targetMethod = target.getMappingMethod(elem.getSimpleName(), elem.getDesc());
        ObfuscationData<MappingMethod> obfData = this.obf.getDataProvider().getObfMethod(targetMethod);
        if (obfData.isEmpty()) {
            Diagnostic.Kind error = Diagnostic.Kind.ERROR;
            try {
                Method md = elem.getElement().getClass().getMethod("isStatic", new Class[0]);
                if (((Boolean) md.invoke(elem.getElement(), new Object[0])).booleanValue()) {
                    error = Diagnostic.Kind.WARNING;
                }
            } catch (Exception e) {
            }
            this.f447ap.printMessage(error, "No obfuscation mapping for @Overwrite method", elem.getElement());
            return false;
        }
        try {
            addMethodMappings(elem.getSimpleName(), elem.getDesc(), obfData);
            return true;
        } catch (Mappings.MappingConflictException ex) {
            elem.printMessage(this.f447ap, Diagnostic.Kind.ERROR, "Mapping conflict for @Overwrite method: " + ex.getNew().getSimpleName() + " for target " + target + " conflicts with existing mapping " + ex.getOld().getSimpleName());
            return false;
        }
    }
}
