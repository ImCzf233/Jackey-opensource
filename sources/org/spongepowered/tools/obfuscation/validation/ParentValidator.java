package org.spongepowered.tools.obfuscation.validation;

import java.util.Collection;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import org.spongepowered.tools.obfuscation.MixinValidator;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IMixinValidator;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;

/* loaded from: Jackey Client b2.jar:org/spongepowered/tools/obfuscation/validation/ParentValidator.class */
public class ParentValidator extends MixinValidator {
    public ParentValidator(IMixinAnnotationProcessor ap) {
        super(ap, IMixinValidator.ValidationPass.EARLY);
    }

    @Override // org.spongepowered.tools.obfuscation.MixinValidator
    public boolean validate(TypeElement mixin, AnnotationHandle annotation, Collection<TypeHandle> targets) {
        if (mixin.getEnclosingElement().getKind() != ElementKind.PACKAGE && !mixin.getModifiers().contains(Modifier.STATIC)) {
            error("Inner class mixin must be declared static", mixin);
            return true;
        }
        return true;
    }
}
