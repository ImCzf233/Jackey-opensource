package org.spongepowered.tools.obfuscation.validation;

import java.util.Collection;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.tools.obfuscation.MixinValidator;
import org.spongepowered.tools.obfuscation.SupportedOptions;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IMixinValidator;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeUtils;

/* loaded from: Jackey Client b2.jar:org/spongepowered/tools/obfuscation/validation/TargetValidator.class */
public class TargetValidator extends MixinValidator {
    public TargetValidator(IMixinAnnotationProcessor ap) {
        super(ap, IMixinValidator.ValidationPass.LATE);
    }

    @Override // org.spongepowered.tools.obfuscation.MixinValidator
    public boolean validate(TypeElement mixin, AnnotationHandle annotation, Collection<TypeHandle> targets) {
        if ("true".equalsIgnoreCase(this.options.getOption(SupportedOptions.DISABLE_TARGET_VALIDATOR))) {
            return true;
        }
        if (mixin.getKind() == ElementKind.INTERFACE) {
            validateInterfaceMixin(mixin, targets);
            return true;
        }
        validateClassMixin(mixin, targets);
        return true;
    }

    private void validateInterfaceMixin(TypeElement mixin, Collection<TypeHandle> targets) {
        boolean containsNonAccessorMethod = false;
        for (Element element : mixin.getEnclosedElements()) {
            if (element.getKind() == ElementKind.METHOD) {
                boolean isAccessor = AnnotationHandle.m3of(element, Accessor.class).exists();
                boolean isInvoker = AnnotationHandle.m3of(element, Invoker.class).exists();
                containsNonAccessorMethod |= !isAccessor && !isInvoker;
            }
        }
        if (!containsNonAccessorMethod) {
            return;
        }
        for (TypeHandle target : targets) {
            TypeElement targetType = target.getElement();
            if (targetType != null && targetType.getKind() != ElementKind.INTERFACE) {
                error("Targetted type '" + target + " of " + mixin + " is not an interface", mixin);
            }
        }
    }

    private void validateClassMixin(TypeElement mixin, Collection<TypeHandle> targets) {
        TypeMirror superClass = mixin.getSuperclass();
        for (TypeHandle target : targets) {
            TypeMirror targetType = target.getType();
            if (targetType != null && !validateSuperClass(targetType, superClass)) {
                error("Superclass " + superClass + " of " + mixin + " was not found in the hierarchy of target class " + targetType, mixin);
            }
        }
    }

    private boolean validateSuperClass(TypeMirror targetType, TypeMirror superClass) {
        if (TypeUtils.isAssignable(this.processingEnv, targetType, superClass)) {
            return true;
        }
        return validateSuperClassRecursive(targetType, superClass);
    }

    private boolean validateSuperClassRecursive(TypeMirror targetType, TypeMirror superClass) {
        if (!(targetType instanceof DeclaredType)) {
            return false;
        }
        if (TypeUtils.isAssignable(this.processingEnv, targetType, superClass)) {
            return true;
        }
        TypeElement targetElement = ((DeclaredType) targetType).asElement();
        TypeMirror targetSuper = targetElement.getSuperclass();
        if (targetSuper.getKind() == TypeKind.NONE) {
            return false;
        }
        if (checkMixinsFor(targetSuper, superClass)) {
            return true;
        }
        return validateSuperClassRecursive(targetSuper, superClass);
    }

    private boolean checkMixinsFor(TypeMirror targetType, TypeMirror superClass) {
        for (TypeMirror mixinType : getMixinsTargeting(targetType)) {
            if (TypeUtils.isAssignable(this.processingEnv, mixinType, superClass)) {
                return true;
            }
        }
        return false;
    }
}
