package org.spongepowered.tools.obfuscation;

import java.util.Iterator;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.AnnotatedMixinElementHandler;
import org.spongepowered.tools.obfuscation.Mappings;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;

/* loaded from: Jackey Client b2.jar:org/spongepowered/tools/obfuscation/AnnotatedMixinElementHandlerShadow.class */
public class AnnotatedMixinElementHandlerShadow extends AnnotatedMixinElementHandler {

    /* loaded from: Jackey Client b2.jar:org/spongepowered/tools/obfuscation/AnnotatedMixinElementHandlerShadow$AnnotatedElementShadow.class */
    public static abstract class AnnotatedElementShadow<E extends Element, M extends IMapping<M>> extends AnnotatedMixinElementHandler.AnnotatedElement<E> {
        private final boolean shouldRemap;
        private final AnnotatedMixinElementHandler.ShadowElementName name;
        private final IMapping.Type type;

        public abstract M getMapping(TypeHandle typeHandle, String str, String str2);

        public abstract void addMapping(ObfuscationType obfuscationType, IMapping<?> iMapping);

        protected AnnotatedElementShadow(E element, AnnotationHandle annotation, boolean shouldRemap, IMapping.Type type) {
            super(element, annotation);
            this.shouldRemap = shouldRemap;
            this.name = new AnnotatedMixinElementHandler.ShadowElementName(element, annotation);
            this.type = type;
        }

        public boolean shouldRemap() {
            return this.shouldRemap;
        }

        public AnnotatedMixinElementHandler.ShadowElementName getName() {
            return this.name;
        }

        public IMapping.Type getElementType() {
            return this.type;
        }

        public String toString() {
            return getElementType().name().toLowerCase();
        }

        public AnnotatedMixinElementHandler.ShadowElementName setObfuscatedName(IMapping<?> name) {
            return setObfuscatedName(name.getSimpleName());
        }

        public AnnotatedMixinElementHandler.ShadowElementName setObfuscatedName(String name) {
            return getName().setObfuscatedName(name);
        }

        public ObfuscationData<M> getObfuscationData(IObfuscationDataProvider provider, TypeHandle owner) {
            return provider.getObfEntry(getMapping(owner, getName().toString(), getDesc()));
        }
    }

    /* loaded from: Jackey Client b2.jar:org/spongepowered/tools/obfuscation/AnnotatedMixinElementHandlerShadow$AnnotatedElementShadowField.class */
    public class AnnotatedElementShadowField extends AnnotatedElementShadow<VariableElement, MappingField> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnnotatedElementShadowField(VariableElement element, AnnotationHandle annotation, boolean shouldRemap) {
            super(element, annotation, shouldRemap, IMapping.Type.FIELD);
            AnnotatedMixinElementHandlerShadow.this = this$0;
        }

        @Override // org.spongepowered.tools.obfuscation.AnnotatedMixinElementHandlerShadow.AnnotatedElementShadow
        public MappingField getMapping(TypeHandle owner, String name, String desc) {
            return new MappingField(owner.getName(), name, desc);
        }

        @Override // org.spongepowered.tools.obfuscation.AnnotatedMixinElementHandlerShadow.AnnotatedElementShadow
        public void addMapping(ObfuscationType type, IMapping<?> remapped) {
            AnnotatedMixinElementHandlerShadow.this.addFieldMapping(type, setObfuscatedName(remapped), getDesc(), remapped.getDesc());
        }
    }

    /* loaded from: Jackey Client b2.jar:org/spongepowered/tools/obfuscation/AnnotatedMixinElementHandlerShadow$AnnotatedElementShadowMethod.class */
    public class AnnotatedElementShadowMethod extends AnnotatedElementShadow<ExecutableElement, MappingMethod> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnnotatedElementShadowMethod(ExecutableElement element, AnnotationHandle annotation, boolean shouldRemap) {
            super(element, annotation, shouldRemap, IMapping.Type.METHOD);
            AnnotatedMixinElementHandlerShadow.this = this$0;
        }

        @Override // org.spongepowered.tools.obfuscation.AnnotatedMixinElementHandlerShadow.AnnotatedElementShadow
        public MappingMethod getMapping(TypeHandle owner, String name, String desc) {
            return owner.getMappingMethod(name, desc);
        }

        @Override // org.spongepowered.tools.obfuscation.AnnotatedMixinElementHandlerShadow.AnnotatedElementShadow
        public void addMapping(ObfuscationType type, IMapping<?> remapped) {
            AnnotatedMixinElementHandlerShadow.this.addMethodMapping(type, setObfuscatedName(remapped), getDesc(), remapped.getDesc());
        }
    }

    public AnnotatedMixinElementHandlerShadow(IMixinAnnotationProcessor ap, AnnotatedMixin mixin) {
        super(ap, mixin);
    }

    public void registerShadow(AnnotatedElementShadow<?, ?> elem) {
        validateTarget(elem.getElement(), elem.getAnnotation(), elem.getName(), "@Shadow");
        if (!elem.shouldRemap()) {
            return;
        }
        for (TypeHandle target : this.mixin.getTargets()) {
            registerShadowForTarget(elem, target);
        }
    }

    private void registerShadowForTarget(AnnotatedElementShadow<?, ?> elem, TypeHandle target) {
        ObfuscationData<?> obfuscationData = elem.getObfuscationData(this.obf.getDataProvider(), target);
        if (obfuscationData.isEmpty()) {
            String info = this.mixin.isMultiTarget() ? " in target " + target : "";
            if (target.isSimulated()) {
                elem.printMessage(this.f447ap, Diagnostic.Kind.WARNING, "Unable to locate obfuscation mapping" + info + " for @Shadow " + elem);
                return;
            } else {
                elem.printMessage(this.f447ap, Diagnostic.Kind.WARNING, "Unable to locate obfuscation mapping" + info + " for @Shadow " + elem);
                return;
            }
        }
        Iterator<ObfuscationType> it = obfuscationData.iterator();
        while (it.hasNext()) {
            ObfuscationType type = it.next();
            try {
                elem.addMapping(type, (IMapping) obfuscationData.get(type));
            } catch (Mappings.MappingConflictException ex) {
                elem.printMessage(this.f447ap, Diagnostic.Kind.ERROR, "Mapping conflict for @Shadow " + elem + ": " + ex.getNew().getSimpleName() + " for target " + target + " conflicts with existing mapping " + ex.getOld().getSimpleName());
            }
        }
    }
}
