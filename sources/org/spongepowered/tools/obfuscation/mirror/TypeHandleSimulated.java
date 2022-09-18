package org.spongepowered.tools.obfuscation.mirror;

import java.lang.annotation.Annotation;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.asm.util.SignaturePrinter;

/* loaded from: Jackey Client b2.jar:org/spongepowered/tools/obfuscation/mirror/TypeHandleSimulated.class */
public class TypeHandleSimulated extends TypeHandle {
    private final TypeElement simulatedType;

    public TypeHandleSimulated(String name, TypeMirror type) {
        this(TypeUtils.getPackage(type), name, type);
    }

    public TypeHandleSimulated(PackageElement pkg, String name, TypeMirror type) {
        super(pkg, name);
        this.simulatedType = ((DeclaredType) type).asElement();
    }

    @Override // org.spongepowered.tools.obfuscation.mirror.TypeHandle
    public TypeElement getTargetElement() {
        return this.simulatedType;
    }

    @Override // org.spongepowered.tools.obfuscation.mirror.TypeHandle
    public boolean isPublic() {
        return true;
    }

    @Override // org.spongepowered.tools.obfuscation.mirror.TypeHandle
    public boolean isImaginary() {
        return false;
    }

    @Override // org.spongepowered.tools.obfuscation.mirror.TypeHandle
    public boolean isSimulated() {
        return true;
    }

    @Override // org.spongepowered.tools.obfuscation.mirror.TypeHandle
    public AnnotationHandle getAnnotation(Class<? extends Annotation> annotationClass) {
        return null;
    }

    @Override // org.spongepowered.tools.obfuscation.mirror.TypeHandle
    public TypeHandle getSuperclass() {
        return null;
    }

    @Override // org.spongepowered.tools.obfuscation.mirror.TypeHandle
    public String findDescriptor(MemberInfo memberInfo) {
        if (memberInfo != null) {
            return memberInfo.desc;
        }
        return null;
    }

    @Override // org.spongepowered.tools.obfuscation.mirror.TypeHandle
    public FieldHandle findField(String name, String type, boolean caseSensitive) {
        return new FieldHandle((String) null, name, type);
    }

    @Override // org.spongepowered.tools.obfuscation.mirror.TypeHandle
    public MethodHandle findMethod(String name, String desc, boolean caseSensitive) {
        return new MethodHandle(null, name, desc);
    }

    @Override // org.spongepowered.tools.obfuscation.mirror.TypeHandle
    public MappingMethod getMappingMethod(String name, String desc) {
        String signature = new SignaturePrinter(name, desc).setFullyQualified(true).toDescriptor();
        String rawSignature = TypeUtils.stripGenerics(signature);
        MethodHandle method = findMethodRecursive((TypeHandle) this, name, signature, rawSignature, true);
        return method != null ? method.asMapping(true) : super.getMappingMethod(name, desc);
    }

    private static MethodHandle findMethodRecursive(TypeHandle target, String name, String signature, String rawSignature, boolean matchCase) {
        TypeElement elem = target.getTargetElement();
        if (elem == null) {
            return null;
        }
        MethodHandle method = TypeHandle.findMethod(target, name, signature, rawSignature, matchCase);
        if (method != null) {
            return method;
        }
        for (TypeMirror iface : elem.getInterfaces()) {
            MethodHandle method2 = findMethodRecursive(iface, name, signature, rawSignature, matchCase);
            if (method2 != null) {
                return method2;
            }
        }
        TypeMirror superClass = elem.getSuperclass();
        if (superClass == null || superClass.getKind() == TypeKind.NONE) {
            return null;
        }
        return findMethodRecursive(superClass, name, signature, rawSignature, matchCase);
    }

    private static MethodHandle findMethodRecursive(TypeMirror target, String name, String signature, String rawSignature, boolean matchCase) {
        if (!(target instanceof DeclaredType)) {
            return null;
        }
        TypeElement element = ((DeclaredType) target).asElement();
        return findMethodRecursive(new TypeHandle(element), name, signature, rawSignature, matchCase);
    }
}
