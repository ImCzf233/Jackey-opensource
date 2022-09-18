package org.spongepowered.tools.obfuscation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
import org.spongepowered.asm.mixin.injection.struct.InvalidMemberDescriptorException;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.AnnotatedMixinElementHandler;
import org.spongepowered.tools.obfuscation.ReferenceManager;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IReferenceManager;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
import org.spongepowered.tools.obfuscation.struct.InjectorRemap;

/* loaded from: Jackey Client b2.jar:org/spongepowered/tools/obfuscation/AnnotatedMixinElementHandlerInjector.class */
public class AnnotatedMixinElementHandlerInjector extends AnnotatedMixinElementHandler {

    /* loaded from: Jackey Client b2.jar:org/spongepowered/tools/obfuscation/AnnotatedMixinElementHandlerInjector$AnnotatedElementInjector.class */
    public static class AnnotatedElementInjector extends AnnotatedMixinElementHandler.AnnotatedElement<ExecutableElement> {
        private final InjectorRemap state;

        public AnnotatedElementInjector(ExecutableElement element, AnnotationHandle annotation, InjectorRemap shouldRemap) {
            super(element, annotation);
            this.state = shouldRemap;
        }

        public boolean shouldRemap() {
            return this.state.shouldRemap();
        }

        public boolean hasCoerceArgument() {
            if (!this.annotation.toString().equals("@Inject")) {
                return false;
            }
            Iterator it = this.element.getParameters().iterator();
            if (it.hasNext()) {
                VariableElement param = (VariableElement) it.next();
                return AnnotationHandle.m3of(param, Coerce.class).exists();
            }
            return false;
        }

        public void addMessage(Diagnostic.Kind kind, CharSequence msg, Element element, AnnotationHandle annotation) {
            this.state.addMessage(kind, msg, element, annotation);
        }

        public String toString() {
            return getAnnotation().toString();
        }
    }

    /* loaded from: Jackey Client b2.jar:org/spongepowered/tools/obfuscation/AnnotatedMixinElementHandlerInjector$AnnotatedElementInjectionPoint.class */
    public static class AnnotatedElementInjectionPoint extends AnnotatedMixinElementHandler.AnnotatedElement<ExecutableElement> {

        /* renamed from: at */
        private final AnnotationHandle f449at;
        private Map<String, String> args;
        private final InjectorRemap state;

        public AnnotatedElementInjectionPoint(ExecutableElement element, AnnotationHandle inject, AnnotationHandle at, InjectorRemap state) {
            super(element, inject);
            this.f449at = at;
            this.state = state;
        }

        public boolean shouldRemap() {
            return this.f449at.getBoolean("remap", this.state.shouldRemap());
        }

        public AnnotationHandle getAt() {
            return this.f449at;
        }

        public String getAtArg(String key) {
            if (this.args == null) {
                this.args = new HashMap();
                for (String arg : this.f449at.getList("args")) {
                    if (arg != null) {
                        int eqPos = arg.indexOf(61);
                        if (eqPos > -1) {
                            this.args.put(arg.substring(0, eqPos), arg.substring(eqPos + 1));
                        } else {
                            this.args.put(arg, "");
                        }
                    }
                }
            }
            return this.args.get(key);
        }

        public void notifyRemapped() {
            this.state.notifyRemapped();
        }
    }

    public AnnotatedMixinElementHandlerInjector(IMixinAnnotationProcessor ap, AnnotatedMixin mixin) {
        super(ap, mixin);
    }

    public void registerInjector(AnnotatedElementInjector elem) {
        if (this.mixin.isInterface()) {
            this.f447ap.printMessage(Diagnostic.Kind.ERROR, "Injector in interface is unsupported", elem.getElement());
        }
        for (String reference : elem.getAnnotation().getList("method")) {
            MemberInfo targetMember = MemberInfo.parse(reference);
            if (targetMember.name != null) {
                try {
                    targetMember.validate();
                } catch (InvalidMemberDescriptorException ex) {
                    elem.printMessage(this.f447ap, Diagnostic.Kind.ERROR, ex.getMessage());
                }
                if (targetMember.desc != null) {
                    validateReferencedTarget(elem.getElement(), elem.getAnnotation(), targetMember, elem.toString());
                }
                if (elem.shouldRemap()) {
                    for (TypeHandle target : this.mixin.getTargets()) {
                        if (!registerInjector(elem, reference, targetMember, target)) {
                            break;
                        }
                    }
                }
            }
        }
    }

    private boolean registerInjector(AnnotatedElementInjector elem, String reference, MemberInfo targetMember, TypeHandle target) {
        String desc = target.findDescriptor(targetMember);
        if (desc == null) {
            Diagnostic.Kind error = this.mixin.isMultiTarget() ? Diagnostic.Kind.ERROR : Diagnostic.Kind.WARNING;
            if (target.isSimulated()) {
                elem.printMessage(this.f447ap, Diagnostic.Kind.NOTE, elem + " target '" + reference + "' in @Pseudo mixin will not be obfuscated");
                return true;
            } else if (target.isImaginary()) {
                elem.printMessage(this.f447ap, error, elem + " target requires method signature because enclosing type information for " + target + " is unavailable");
                return true;
            } else if (!targetMember.isInitialiser()) {
                elem.printMessage(this.f447ap, error, "Unable to determine signature for " + elem + " target method");
                return true;
            } else {
                return true;
            }
        }
        String targetName = elem + " target " + targetMember.name;
        MappingMethod targetMethod = target.getMappingMethod(targetMember.name, desc);
        ObfuscationData<MappingMethod> obfData = this.obf.getDataProvider().getObfMethod(targetMethod);
        if (obfData.isEmpty()) {
            if (target.isSimulated()) {
                obfData = this.obf.getDataProvider().getRemappedMethod(targetMethod);
            } else if (targetMember.isClassInitialiser()) {
                return true;
            } else {
                elem.addMessage(targetMember.isConstructor() ? Diagnostic.Kind.WARNING : Diagnostic.Kind.ERROR, "No obfuscation mapping for " + targetName, elem.getElement(), elem.getAnnotation());
                return false;
            }
        }
        IReferenceManager refMap = this.obf.getReferenceManager();
        try {
            if ((targetMember.owner == null && this.mixin.isMultiTarget()) || target.isSimulated()) {
                obfData = AnnotatedMixinElementHandler.stripOwnerData(obfData);
            }
            refMap.addMethodMapping(this.classRef, reference, obfData);
            return true;
        } catch (ReferenceManager.ReferenceConflictException ex) {
            String conflictType = this.mixin.isMultiTarget() ? "Multi-target" : "Target";
            if (elem.hasCoerceArgument() && targetMember.owner == null && targetMember.desc == null) {
                MemberInfo oldMember = MemberInfo.parse(ex.getOld());
                MemberInfo newMember = MemberInfo.parse(ex.getNew());
                if (oldMember.name.equals(newMember.name)) {
                    ObfuscationData<MappingMethod> obfData2 = AnnotatedMixinElementHandler.stripDescriptors(obfData);
                    refMap.setAllowConflicts(true);
                    refMap.addMethodMapping(this.classRef, reference, obfData2);
                    refMap.setAllowConflicts(false);
                    elem.printMessage(this.f447ap, Diagnostic.Kind.WARNING, "Coerced " + conflictType + " reference has conflicting descriptors for " + targetName + ": Storing bare references " + obfData2.values() + " in refMap");
                    return true;
                }
            }
            elem.printMessage(this.f447ap, Diagnostic.Kind.ERROR, conflictType + " reference conflict for " + targetName + ": " + reference + " -> " + ex.getNew() + " previously defined as " + ex.getOld());
            return true;
        }
    }

    public void registerInjectionPoint(AnnotatedElementInjectionPoint elem, String format) {
        if (this.mixin.isInterface()) {
            this.f447ap.printMessage(Diagnostic.Kind.ERROR, "Injector in interface is unsupported", elem.getElement());
        }
        if (!elem.shouldRemap()) {
            return;
        }
        String type = InjectionPointData.parseType((String) elem.getAt().getValue("value"));
        String target = (String) elem.getAt().getValue("target");
        if ("NEW".equals(type)) {
            remapNewTarget(String.format(format, type + ".<target>"), target, elem);
            remapNewTarget(String.format(format, type + ".args[class]"), elem.getAtArg("class"), elem);
            return;
        }
        remapReference(String.format(format, type + ".<target>"), target, elem);
    }

    protected final void remapNewTarget(String subject, String reference, AnnotatedElementInjectionPoint elem) {
        if (reference == null) {
            return;
        }
        MemberInfo member = MemberInfo.parse(reference);
        String target = member.toCtorType();
        if (target != null) {
            String desc = member.toCtorDesc();
            MappingMethod m = new MappingMethod(target, ".", desc != null ? desc : "()V");
            ObfuscationData<MappingMethod> remapped = this.obf.getDataProvider().getRemappedMethod(m);
            if (remapped.isEmpty()) {
                this.f447ap.printMessage(Diagnostic.Kind.WARNING, "Cannot find class mapping for " + subject + " '" + target + "'", elem.getElement(), elem.getAnnotation().asMirror());
                return;
            }
            ObfuscationData<String> mappings = new ObfuscationData<>();
            Iterator<ObfuscationType> it = remapped.iterator();
            while (it.hasNext()) {
                ObfuscationType type = it.next();
                MappingMethod mapping = remapped.get(type);
                if (desc == null) {
                    mappings.put(type, mapping.getOwner());
                } else {
                    mappings.put(type, mapping.getDesc().replace(")V", ")L" + mapping.getOwner() + ";"));
                }
            }
            this.obf.getReferenceManager().addClassMapping(this.classRef, reference, mappings);
        }
        elem.notifyRemapped();
    }

    protected final void remapReference(String subject, String reference, AnnotatedElementInjectionPoint elem) {
        if (reference == null) {
            return;
        }
        AnnotationMirror errorsOn = (this.f447ap.getCompilerEnvironment() == IMixinAnnotationProcessor.CompilerEnvironment.JDT ? elem.getAt() : elem.getAnnotation()).asMirror();
        MemberInfo targetMember = MemberInfo.parse(reference);
        if (!targetMember.isFullyQualified()) {
            String missing = targetMember.owner == null ? targetMember.desc == null ? "owner and signature" : "owner" : "signature";
            this.f447ap.printMessage(Diagnostic.Kind.ERROR, subject + " is not fully qualified, missing " + missing, elem.getElement(), errorsOn);
            return;
        }
        try {
            targetMember.validate();
        } catch (InvalidMemberDescriptorException ex) {
            this.f447ap.printMessage(Diagnostic.Kind.ERROR, ex.getMessage(), elem.getElement(), errorsOn);
        }
        try {
            if (targetMember.isField()) {
                ObfuscationData<MappingField> obfFieldData = this.obf.getDataProvider().getObfFieldRecursive(targetMember);
                if (obfFieldData.isEmpty()) {
                    this.f447ap.printMessage(Diagnostic.Kind.WARNING, "Cannot find field mapping for " + subject + " '" + reference + "'", elem.getElement(), errorsOn);
                    return;
                }
                this.obf.getReferenceManager().addFieldMapping(this.classRef, reference, targetMember, obfFieldData);
            } else {
                ObfuscationData<MappingMethod> obfMethodData = this.obf.getDataProvider().getObfMethodRecursive(targetMember);
                if (obfMethodData.isEmpty() && (targetMember.owner == null || !targetMember.owner.startsWith("java/lang/"))) {
                    this.f447ap.printMessage(Diagnostic.Kind.WARNING, "Cannot find method mapping for " + subject + " '" + reference + "'", elem.getElement(), errorsOn);
                    return;
                }
                this.obf.getReferenceManager().addMethodMapping(this.classRef, reference, targetMember, obfMethodData);
            }
            elem.notifyRemapped();
        } catch (ReferenceManager.ReferenceConflictException ex2) {
            this.f447ap.printMessage(Diagnostic.Kind.ERROR, "Unexpected reference conflict for " + subject + ": " + reference + " -> " + ex2.getNew() + " previously defined as " + ex2.getOld(), elem.getElement(), errorsOn);
        }
    }
}
