package org.spongepowered.tools.obfuscation;

import java.util.Iterator;
import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.DeclaredType;
import javax.tools.Diagnostic;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.MethodHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeUtils;

/* loaded from: Jackey Client b2.jar:org/spongepowered/tools/obfuscation/AnnotatedMixinElementHandlerSoftImplements.class */
public class AnnotatedMixinElementHandlerSoftImplements extends AnnotatedMixinElementHandler {
    public AnnotatedMixinElementHandlerSoftImplements(IMixinAnnotationProcessor ap, AnnotatedMixin mixin) {
        super(ap, mixin);
    }

    public void process(AnnotationHandle implementsAnnotation) {
        if (!this.mixin.remap()) {
            return;
        }
        List<AnnotationHandle> interfaces = implementsAnnotation.getAnnotationList("value");
        if (interfaces.size() < 1) {
            this.f447ap.printMessage(Diagnostic.Kind.WARNING, "Empty @Implements annotation", this.mixin.getMixin(), implementsAnnotation.asMirror());
            return;
        }
        for (AnnotationHandle interfaceAnnotation : interfaces) {
            Interface.Remap remap = (Interface.Remap) interfaceAnnotation.getValue("remap", Interface.Remap.ALL);
            if (remap != Interface.Remap.NONE) {
                try {
                    TypeHandle iface = new TypeHandle((DeclaredType) interfaceAnnotation.getValue("iface"));
                    String prefix = (String) interfaceAnnotation.getValue("prefix");
                    processSoftImplements(remap, iface, prefix);
                } catch (Exception ex) {
                    this.f447ap.printMessage(Diagnostic.Kind.ERROR, "Unexpected error: " + ex.getClass().getName() + ": " + ex.getMessage(), this.mixin.getMixin(), interfaceAnnotation.asMirror());
                }
            }
        }
    }

    private void processSoftImplements(Interface.Remap remap, TypeHandle iface, String prefix) {
        for (ExecutableElement method : iface.getEnclosedElements(ElementKind.METHOD)) {
            processMethod(remap, iface, prefix, method);
        }
        for (TypeHandle superInterface : iface.getInterfaces()) {
            processSoftImplements(remap, superInterface, prefix);
        }
    }

    private void processMethod(Interface.Remap remap, TypeHandle iface, String prefix, ExecutableElement method) {
        MethodHandle prefixedMixinMethod;
        MethodHandle mixinMethod;
        String name = method.getSimpleName().toString();
        String sig = TypeUtils.getJavaSignature((Element) method);
        String desc = TypeUtils.getDescriptor(method);
        if (remap != Interface.Remap.ONLY_PREFIXED && (mixinMethod = this.mixin.getHandle().findMethod(name, sig)) != null) {
            addInterfaceMethodMapping(remap, iface, null, mixinMethod, name, desc);
        }
        if (prefix != null && (prefixedMixinMethod = this.mixin.getHandle().findMethod(prefix + name, sig)) != null) {
            addInterfaceMethodMapping(remap, iface, prefix, prefixedMixinMethod, name, desc);
        }
    }

    private void addInterfaceMethodMapping(Interface.Remap remap, TypeHandle iface, String prefix, MethodHandle method, String name, String desc) {
        MappingMethod mapping = new MappingMethod(iface.getName(), name, desc);
        ObfuscationData<MappingMethod> obfData = this.obf.getDataProvider().getObfMethod(mapping);
        if (obfData.isEmpty()) {
            if (remap.forceRemap()) {
                this.f447ap.printMessage(Diagnostic.Kind.ERROR, "No obfuscation mapping for soft-implementing method", method.getElement());
                return;
            }
            return;
        }
        addMethodMappings(method.getName(), desc, applyPrefix(obfData, prefix));
    }

    private ObfuscationData<MappingMethod> applyPrefix(ObfuscationData<MappingMethod> data, String prefix) {
        if (prefix == null) {
            return data;
        }
        ObfuscationData<MappingMethod> prefixed = new ObfuscationData<>();
        Iterator<ObfuscationType> it = data.iterator();
        while (it.hasNext()) {
            ObfuscationType type = it.next();
            MappingMethod mapping = data.get(type);
            prefixed.put(type, mapping.addPrefix(prefix));
        }
        return prefixed;
    }
}
