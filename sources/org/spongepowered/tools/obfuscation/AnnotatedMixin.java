package org.spongepowered.tools.obfuscation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.processing.Messager;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.tools.Diagnostic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.tools.obfuscation.AnnotatedMixinElementHandlerAccessor;
import org.spongepowered.tools.obfuscation.AnnotatedMixinElementHandlerInjector;
import org.spongepowered.tools.obfuscation.AnnotatedMixinElementHandlerOverwrite;
import org.spongepowered.tools.obfuscation.AnnotatedMixinElementHandlerShadow;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IMixinValidator;
import org.spongepowered.tools.obfuscation.interfaces.IObfuscationManager;
import org.spongepowered.tools.obfuscation.interfaces.ITypeHandleProvider;
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeUtils;
import org.spongepowered.tools.obfuscation.struct.InjectorRemap;

/* loaded from: Jackey Client b2.jar:org/spongepowered/tools/obfuscation/AnnotatedMixin.class */
public class AnnotatedMixin {
    private final AnnotationHandle annotation;
    private final Messager messager;
    private final ITypeHandleProvider typeProvider;
    private final IObfuscationManager obf;
    private final IMappingConsumer mappings;
    private final TypeElement mixin;
    private final List<ExecutableElement> methods;
    private final TypeHandle handle;
    private final String classRef;
    private final boolean remap;
    private final boolean virtual;
    private final AnnotatedMixinElementHandlerOverwrite overwrites;
    private final AnnotatedMixinElementHandlerShadow shadows;
    private final AnnotatedMixinElementHandlerInjector injectors;
    private final AnnotatedMixinElementHandlerAccessor accessors;
    private final AnnotatedMixinElementHandlerSoftImplements softImplements;
    private final List<TypeHandle> targets = new ArrayList();
    private boolean validated = false;
    private final TypeHandle primaryTarget = initTargets();

    public AnnotatedMixin(IMixinAnnotationProcessor ap, TypeElement type) {
        this.typeProvider = ap.getTypeProvider();
        this.obf = ap.getObfuscationManager();
        this.mappings = this.obf.createMappingConsumer();
        this.messager = ap;
        this.mixin = type;
        this.handle = new TypeHandle(type);
        this.methods = new ArrayList(this.handle.getEnclosedElements(ElementKind.METHOD));
        this.virtual = this.handle.getAnnotation(Pseudo.class).exists();
        this.annotation = this.handle.getAnnotation(Mixin.class);
        this.classRef = TypeUtils.getInternalName(type);
        this.remap = this.annotation.getBoolean("remap", true) && this.targets.size() > 0;
        this.overwrites = new AnnotatedMixinElementHandlerOverwrite(ap, this);
        this.shadows = new AnnotatedMixinElementHandlerShadow(ap, this);
        this.injectors = new AnnotatedMixinElementHandlerInjector(ap, this);
        this.accessors = new AnnotatedMixinElementHandlerAccessor(ap, this);
        this.softImplements = new AnnotatedMixinElementHandlerSoftImplements(ap, this);
    }

    public AnnotatedMixin runValidators(IMixinValidator.ValidationPass pass, Collection<IMixinValidator> validators) {
        for (IMixinValidator validator : validators) {
            if (!validator.validate(pass, this.mixin, this.annotation, this.targets)) {
                break;
            }
        }
        if (pass == IMixinValidator.ValidationPass.FINAL && !this.validated) {
            this.validated = true;
            runFinalValidation();
        }
        return this;
    }

    private TypeHandle initTargets() {
        TypeHandle primaryTarget = null;
        try {
            for (DeclaredType declaredType : this.annotation.getList()) {
                TypeHandle type = new TypeHandle(declaredType);
                if (!this.targets.contains(type)) {
                    addTarget(type);
                    if (primaryTarget == null) {
                        primaryTarget = type;
                    }
                }
            }
        } catch (Exception ex) {
            printMessage(Diagnostic.Kind.WARNING, "Error processing public targets: " + ex.getClass().getName() + ": " + ex.getMessage(), this);
        }
        try {
            for (String privateTarget : this.annotation.getList("targets")) {
                TypeHandle type2 = this.typeProvider.getTypeHandle(privateTarget);
                if (!this.targets.contains(type2)) {
                    if (this.virtual) {
                        type2 = this.typeProvider.getSimulatedHandle(privateTarget, this.mixin.asType());
                    } else if (type2 == null) {
                        printMessage(Diagnostic.Kind.ERROR, "Mixin target " + privateTarget + " could not be found", this);
                        return null;
                    } else if (type2.isPublic()) {
                        printMessage(Diagnostic.Kind.WARNING, "Mixin target " + privateTarget + " is public and must be specified in value", this);
                        return null;
                    }
                    addSoftTarget(type2, privateTarget);
                    if (primaryTarget == null) {
                        primaryTarget = type2;
                    }
                }
            }
        } catch (Exception ex2) {
            printMessage(Diagnostic.Kind.WARNING, "Error processing private targets: " + ex2.getClass().getName() + ": " + ex2.getMessage(), this);
        }
        if (primaryTarget == null) {
            printMessage(Diagnostic.Kind.ERROR, "Mixin has no targets", this);
        }
        return primaryTarget;
    }

    private void printMessage(Diagnostic.Kind kind, CharSequence msg, AnnotatedMixin mixin) {
        this.messager.printMessage(kind, msg, this.mixin, this.annotation.asMirror());
    }

    private void addSoftTarget(TypeHandle type, String reference) {
        ObfuscationData<String> obfClassData = this.obf.getDataProvider().getObfClass(type);
        if (!obfClassData.isEmpty()) {
            this.obf.getReferenceManager().addClassMapping(this.classRef, reference, obfClassData);
        }
        addTarget(type);
    }

    private void addTarget(TypeHandle type) {
        this.targets.add(type);
    }

    public String toString() {
        return this.mixin.getSimpleName().toString();
    }

    public AnnotationHandle getAnnotation() {
        return this.annotation;
    }

    public TypeElement getMixin() {
        return this.mixin;
    }

    public TypeHandle getHandle() {
        return this.handle;
    }

    public String getClassRef() {
        return this.classRef;
    }

    public boolean isInterface() {
        return this.mixin.getKind() == ElementKind.INTERFACE;
    }

    @Deprecated
    public TypeHandle getPrimaryTarget() {
        return this.primaryTarget;
    }

    public List<TypeHandle> getTargets() {
        return this.targets;
    }

    public boolean isMultiTarget() {
        return this.targets.size() > 1;
    }

    public boolean remap() {
        return this.remap;
    }

    public IMappingConsumer getMappings() {
        return this.mappings;
    }

    private void runFinalValidation() {
        for (ExecutableElement method : this.methods) {
            this.overwrites.registerMerge(method);
        }
    }

    public void registerOverwrite(ExecutableElement method, AnnotationHandle overwrite, boolean shouldRemap) {
        this.methods.remove(method);
        this.overwrites.registerOverwrite(new AnnotatedMixinElementHandlerOverwrite.AnnotatedElementOverwrite(method, overwrite, shouldRemap));
    }

    public void registerShadow(VariableElement field, AnnotationHandle shadow, boolean shouldRemap) {
        AnnotatedMixinElementHandlerShadow annotatedMixinElementHandlerShadow = this.shadows;
        AnnotatedMixinElementHandlerShadow annotatedMixinElementHandlerShadow2 = this.shadows;
        annotatedMixinElementHandlerShadow2.getClass();
        annotatedMixinElementHandlerShadow.registerShadow(new AnnotatedMixinElementHandlerShadow.AnnotatedElementShadowField(field, shadow, shouldRemap));
    }

    public void registerShadow(ExecutableElement method, AnnotationHandle shadow, boolean shouldRemap) {
        this.methods.remove(method);
        AnnotatedMixinElementHandlerShadow annotatedMixinElementHandlerShadow = this.shadows;
        AnnotatedMixinElementHandlerShadow annotatedMixinElementHandlerShadow2 = this.shadows;
        annotatedMixinElementHandlerShadow2.getClass();
        annotatedMixinElementHandlerShadow.registerShadow(new AnnotatedMixinElementHandlerShadow.AnnotatedElementShadowMethod(method, shadow, shouldRemap));
    }

    public void registerInjector(ExecutableElement method, AnnotationHandle inject, InjectorRemap remap) {
        this.methods.remove(method);
        this.injectors.registerInjector(new AnnotatedMixinElementHandlerInjector.AnnotatedElementInjector(method, inject, remap));
        List<AnnotationHandle> ats = inject.getAnnotationList("at");
        for (AnnotationHandle at : ats) {
            registerInjectionPoint(method, inject, at, remap, "@At(%s)");
        }
        List<AnnotationHandle> slices = inject.getAnnotationList("slice");
        for (AnnotationHandle slice : slices) {
            String id = (String) slice.getValue("id", "");
            AnnotationHandle from = slice.getAnnotation("from");
            if (from != null) {
                registerInjectionPoint(method, inject, from, remap, "@Slice[" + id + "](from=@At(%s))");
            }
            AnnotationHandle to = slice.getAnnotation("to");
            if (to != null) {
                registerInjectionPoint(method, inject, to, remap, "@Slice[" + id + "](to=@At(%s))");
            }
        }
    }

    public void registerInjectionPoint(ExecutableElement element, AnnotationHandle inject, AnnotationHandle at, InjectorRemap remap, String format) {
        this.injectors.registerInjectionPoint(new AnnotatedMixinElementHandlerInjector.AnnotatedElementInjectionPoint(element, inject, at, remap), format);
    }

    public void registerAccessor(ExecutableElement element, AnnotationHandle accessor, boolean shouldRemap) {
        this.methods.remove(element);
        this.accessors.registerAccessor(new AnnotatedMixinElementHandlerAccessor.AnnotatedElementAccessor(element, accessor, shouldRemap));
    }

    public void registerInvoker(ExecutableElement element, AnnotationHandle invoker, boolean shouldRemap) {
        this.methods.remove(element);
        this.accessors.registerAccessor(new AnnotatedMixinElementHandlerAccessor.AnnotatedElementInvoker(element, invoker, shouldRemap));
    }

    public void registerSoftImplements(AnnotationHandle implementsAnnotation) {
        this.softImplements.process(implementsAnnotation);
    }
}
