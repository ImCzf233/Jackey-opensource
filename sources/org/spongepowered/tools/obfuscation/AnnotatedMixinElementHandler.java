package org.spongepowered.tools.obfuscation;

import java.util.Iterator;
import java.util.List;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.asm.util.ConstraintParser;
import org.spongepowered.asm.util.throwables.ConstraintViolationException;
import org.spongepowered.asm.util.throwables.InvalidConstraintException;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IObfuscationManager;
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.FieldHandle;
import org.spongepowered.tools.obfuscation.mirror.MethodHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeUtils;
import org.spongepowered.tools.obfuscation.mirror.Visibility;

/* loaded from: Jackey Client b2.jar:org/spongepowered/tools/obfuscation/AnnotatedMixinElementHandler.class */
public abstract class AnnotatedMixinElementHandler {
    protected final AnnotatedMixin mixin;
    protected final String classRef;

    /* renamed from: ap */
    protected final IMixinAnnotationProcessor f447ap;
    protected final IObfuscationManager obf;
    private IMappingConsumer mappings;

    /* loaded from: Jackey Client b2.jar:org/spongepowered/tools/obfuscation/AnnotatedMixinElementHandler$AnnotatedElement.class */
    public static abstract class AnnotatedElement<E extends Element> {
        protected final E element;
        protected final AnnotationHandle annotation;
        private final String desc;

        public AnnotatedElement(E element, AnnotationHandle annotation) {
            this.element = element;
            this.annotation = annotation;
            this.desc = TypeUtils.getDescriptor(element);
        }

        public E getElement() {
            return this.element;
        }

        public AnnotationHandle getAnnotation() {
            return this.annotation;
        }

        public String getSimpleName() {
            return getElement().getSimpleName().toString();
        }

        public String getDesc() {
            return this.desc;
        }

        public final void printMessage(Messager messager, Diagnostic.Kind kind, CharSequence msg) {
            messager.printMessage(kind, msg, this.element, this.annotation.asMirror());
        }
    }

    /* loaded from: Jackey Client b2.jar:org/spongepowered/tools/obfuscation/AnnotatedMixinElementHandler$AliasedElementName.class */
    public static class AliasedElementName {
        protected final String originalName;
        private final List<String> aliases;
        private boolean caseSensitive;

        public AliasedElementName(Element element, AnnotationHandle annotation) {
            this.originalName = element.getSimpleName().toString();
            this.aliases = annotation.getList("aliases");
        }

        public AliasedElementName setCaseSensitive(boolean caseSensitive) {
            this.caseSensitive = caseSensitive;
            return this;
        }

        public boolean isCaseSensitive() {
            return this.caseSensitive;
        }

        public boolean hasAliases() {
            return this.aliases.size() > 0;
        }

        public List<String> getAliases() {
            return this.aliases;
        }

        public String elementName() {
            return this.originalName;
        }

        public String baseName() {
            return this.originalName;
        }

        public boolean hasPrefix() {
            return false;
        }
    }

    /* loaded from: Jackey Client b2.jar:org/spongepowered/tools/obfuscation/AnnotatedMixinElementHandler$ShadowElementName.class */
    public static class ShadowElementName extends AliasedElementName {
        private final boolean hasPrefix;
        private final String prefix;
        private final String baseName;
        private String obfuscated;

        public ShadowElementName(Element element, AnnotationHandle shadow) {
            super(element, shadow);
            this.prefix = (String) shadow.getValue("prefix", "shadow$");
            boolean hasPrefix = false;
            String name = this.originalName;
            if (name.startsWith(this.prefix)) {
                hasPrefix = true;
                name = name.substring(this.prefix.length());
            }
            this.hasPrefix = hasPrefix;
            String str = name;
            this.baseName = str;
            this.obfuscated = str;
        }

        public String toString() {
            return this.baseName;
        }

        @Override // org.spongepowered.tools.obfuscation.AnnotatedMixinElementHandler.AliasedElementName
        public String baseName() {
            return this.baseName;
        }

        public ShadowElementName setObfuscatedName(IMapping<?> name) {
            this.obfuscated = name.getName();
            return this;
        }

        public ShadowElementName setObfuscatedName(String name) {
            this.obfuscated = name;
            return this;
        }

        @Override // org.spongepowered.tools.obfuscation.AnnotatedMixinElementHandler.AliasedElementName
        public boolean hasPrefix() {
            return this.hasPrefix;
        }

        public String prefix() {
            return this.hasPrefix ? this.prefix : "";
        }

        public String name() {
            return prefix(this.baseName);
        }

        public String obfuscated() {
            return prefix(this.obfuscated);
        }

        public String prefix(String name) {
            return this.hasPrefix ? this.prefix + name : name;
        }
    }

    public AnnotatedMixinElementHandler(IMixinAnnotationProcessor ap, AnnotatedMixin mixin) {
        this.f447ap = ap;
        this.mixin = mixin;
        this.classRef = mixin.getClassRef();
        this.obf = ap.getObfuscationManager();
    }

    private IMappingConsumer getMappings() {
        if (this.mappings == null) {
            IMappingConsumer mappingConsumer = this.mixin.getMappings();
            if (mappingConsumer instanceof Mappings) {
                this.mappings = ((Mappings) mappingConsumer).asUnique();
            } else {
                this.mappings = mappingConsumer;
            }
        }
        return this.mappings;
    }

    protected final void addFieldMappings(String mcpName, String mcpSignature, ObfuscationData<MappingField> obfData) {
        Iterator<ObfuscationType> it = obfData.iterator();
        while (it.hasNext()) {
            ObfuscationType type = it.next();
            MappingField obfField = obfData.get(type);
            addFieldMapping(type, mcpName, obfField.getSimpleName(), mcpSignature, obfField.getDesc());
        }
    }

    public final void addFieldMapping(ObfuscationType type, ShadowElementName name, String mcpSignature, String obfSignature) {
        addFieldMapping(type, name.name(), name.obfuscated(), mcpSignature, obfSignature);
    }

    protected final void addFieldMapping(ObfuscationType type, String mcpName, String obfName, String mcpSignature, String obfSignature) {
        MappingField from = new MappingField(this.classRef, mcpName, mcpSignature);
        MappingField to = new MappingField(this.classRef, obfName, obfSignature);
        getMappings().addFieldMapping(type, from, to);
    }

    public final void addMethodMappings(String mcpName, String mcpSignature, ObfuscationData<MappingMethod> obfData) {
        Iterator<ObfuscationType> it = obfData.iterator();
        while (it.hasNext()) {
            ObfuscationType type = it.next();
            MappingMethod obfMethod = obfData.get(type);
            addMethodMapping(type, mcpName, obfMethod.getSimpleName(), mcpSignature, obfMethod.getDesc());
        }
    }

    public final void addMethodMapping(ObfuscationType type, ShadowElementName name, String mcpSignature, String obfSignature) {
        addMethodMapping(type, name.name(), name.obfuscated(), mcpSignature, obfSignature);
    }

    protected final void addMethodMapping(ObfuscationType type, String mcpName, String obfName, String mcpSignature, String obfSignature) {
        MappingMethod from = new MappingMethod(this.classRef, mcpName, mcpSignature);
        MappingMethod to = new MappingMethod(this.classRef, obfName, obfSignature);
        getMappings().addMethodMapping(type, from, to);
    }

    public final void checkConstraints(ExecutableElement method, AnnotationHandle annotation) {
        try {
            ConstraintParser.Constraint constraint = ConstraintParser.parse((String) annotation.getValue("constraints"));
            try {
                constraint.check(this.f447ap.getTokenProvider());
            } catch (ConstraintViolationException ex) {
                this.f447ap.printMessage(Diagnostic.Kind.ERROR, ex.getMessage(), method, annotation.asMirror());
            }
        } catch (InvalidConstraintException ex2) {
            this.f447ap.printMessage(Diagnostic.Kind.WARNING, ex2.getMessage(), method, annotation.asMirror());
        }
    }

    public final void validateTarget(Element element, AnnotationHandle annotation, AliasedElementName name, String type) {
        if (element instanceof ExecutableElement) {
            validateTargetMethod((ExecutableElement) element, annotation, name, type, false, false);
        } else if (element instanceof VariableElement) {
            validateTargetField((VariableElement) element, annotation, name, type);
        }
    }

    public final void validateTargetMethod(ExecutableElement method, AnnotationHandle annotation, AliasedElementName name, String type, boolean overwrite, boolean merge) {
        String signature = TypeUtils.getJavaSignature((Element) method);
        for (TypeHandle target : this.mixin.getTargets()) {
            if (!target.isImaginary()) {
                MethodHandle targetMethod = target.findMethod(method);
                if (targetMethod == null && name.hasPrefix()) {
                    targetMethod = target.findMethod(name.baseName(), signature);
                }
                if (targetMethod == null && name.hasAliases()) {
                    for (String alias : name.getAliases()) {
                        MethodHandle findMethod = target.findMethod(alias, signature);
                        targetMethod = findMethod;
                        if (findMethod != null) {
                            break;
                        }
                    }
                }
                if (targetMethod != null) {
                    if (overwrite) {
                        validateMethodVisibility(method, annotation, type, target, targetMethod);
                    }
                } else if (!merge) {
                    printMessage(Diagnostic.Kind.WARNING, "Cannot find target for " + type + " method in " + target, method, annotation);
                }
            }
        }
    }

    private void validateMethodVisibility(ExecutableElement method, AnnotationHandle annotation, String type, TypeHandle target, MethodHandle targetMethod) {
        Visibility visTarget = targetMethod.getVisibility();
        if (visTarget == null) {
            return;
        }
        Visibility visMethod = TypeUtils.getVisibility(method);
        String visibility = "visibility of " + visTarget + " method in " + target;
        if (visTarget.ordinal() > visMethod.ordinal()) {
            printMessage(Diagnostic.Kind.WARNING, visMethod + " " + type + " method cannot reduce " + visibility, method, annotation);
        } else if (visTarget == Visibility.PRIVATE && visMethod.ordinal() > visTarget.ordinal()) {
            printMessage(Diagnostic.Kind.WARNING, visMethod + " " + type + " method will upgrade " + visibility, method, annotation);
        }
    }

    protected final void validateTargetField(VariableElement field, AnnotationHandle annotation, AliasedElementName name, String type) {
        String fieldType = field.asType().toString();
        for (TypeHandle target : this.mixin.getTargets()) {
            if (!target.isImaginary()) {
                FieldHandle targetField = target.findField(field);
                if (targetField == null) {
                    List<String> aliases = name.getAliases();
                    for (String alias : aliases) {
                        FieldHandle findField = target.findField(alias, fieldType);
                        targetField = findField;
                        if (findField != null) {
                            break;
                        }
                    }
                    if (targetField == null) {
                        this.f447ap.printMessage(Diagnostic.Kind.WARNING, "Cannot find target for " + type + " field in " + target, field, annotation.asMirror());
                    }
                }
            }
        }
    }

    public final void validateReferencedTarget(ExecutableElement method, AnnotationHandle inject, MemberInfo reference, String type) {
        String signature = reference.toDescriptor();
        for (TypeHandle target : this.mixin.getTargets()) {
            if (!target.isImaginary()) {
                MethodHandle targetMethod = target.findMethod(reference.name, signature);
                if (targetMethod == null) {
                    this.f447ap.printMessage(Diagnostic.Kind.WARNING, "Cannot find target method for " + type + " in " + target, method, inject.asMirror());
                }
            }
        }
    }

    private void printMessage(Diagnostic.Kind kind, String msg, Element e, AnnotationHandle annotation) {
        if (annotation == null) {
            this.f447ap.printMessage(kind, msg, e);
        } else {
            this.f447ap.printMessage(kind, msg, e, annotation.asMirror());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T extends IMapping<T>> ObfuscationData<T> stripOwnerData(ObfuscationData<T> data) {
        ObfuscationData<T> stripped = (ObfuscationData<T>) new ObfuscationData();
        Iterator<ObfuscationType> it = data.iterator();
        while (it.hasNext()) {
            ObfuscationType type = it.next();
            T mapping = data.get(type);
            stripped.put(type, mapping.move(null));
        }
        return stripped;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T extends IMapping<T>> ObfuscationData<T> stripDescriptors(ObfuscationData<T> data) {
        ObfuscationData<T> stripped = (ObfuscationData<T>) new ObfuscationData();
        Iterator<ObfuscationType> it = data.iterator();
        while (it.hasNext()) {
            ObfuscationType type = it.next();
            T mapping = data.get(type);
            stripped.put(type, mapping.transform(null));
        }
        return stripped;
    }
}
