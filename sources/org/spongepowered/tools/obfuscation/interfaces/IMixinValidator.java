package org.spongepowered.tools.obfuscation.interfaces;

import java.util.Collection;
import javax.lang.model.element.TypeElement;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;

/* loaded from: Jackey Client b2.jar:org/spongepowered/tools/obfuscation/interfaces/IMixinValidator.class */
public interface IMixinValidator {

    /* loaded from: Jackey Client b2.jar:org/spongepowered/tools/obfuscation/interfaces/IMixinValidator$ValidationPass.class */
    public enum ValidationPass {
        EARLY,
        LATE,
        FINAL
    }

    boolean validate(ValidationPass validationPass, TypeElement typeElement, AnnotationHandle annotationHandle, Collection<TypeHandle> collection);
}
