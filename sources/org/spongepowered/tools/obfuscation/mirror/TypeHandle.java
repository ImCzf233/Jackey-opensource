package org.spongepowered.tools.obfuscation.mirror;

import com.google.common.collect.ImmutableList;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.mirror.mapping.ResolvableMappingMethod;

/* loaded from: Jackey Client b2.jar:org/spongepowered/tools/obfuscation/mirror/TypeHandle.class */
public class TypeHandle {
    private final String name;
    private final PackageElement pkg;
    private final TypeElement element;
    private TypeReference reference;

    public TypeHandle(PackageElement pkg, String name) {
        this.name = name.replace('.', '/');
        this.pkg = pkg;
        this.element = null;
    }

    public TypeHandle(TypeElement element) {
        this.pkg = TypeUtils.getPackage(element);
        this.name = TypeUtils.getInternalName(element);
        this.element = element;
    }

    public TypeHandle(DeclaredType type) {
        this(type.asElement());
    }

    public final String toString() {
        return this.name.replace('/', '.');
    }

    public final String getName() {
        return this.name;
    }

    public final PackageElement getPackage() {
        return this.pkg;
    }

    public final TypeElement getElement() {
        return this.element;
    }

    public TypeElement getTargetElement() {
        return this.element;
    }

    public AnnotationHandle getAnnotation(Class<? extends Annotation> annotationClass) {
        return AnnotationHandle.m3of(getTargetElement(), annotationClass);
    }

    public final List<? extends Element> getEnclosedElements() {
        return getEnclosedElements(getTargetElement());
    }

    public <T extends Element> List<T> getEnclosedElements(ElementKind... kind) {
        return getEnclosedElements(getTargetElement(), kind);
    }

    public TypeMirror getType() {
        if (getTargetElement() != null) {
            return getTargetElement().asType();
        }
        return null;
    }

    public TypeHandle getSuperclass() {
        DeclaredType superclass;
        TypeElement targetElement = getTargetElement();
        if (targetElement == null || (superclass = targetElement.getSuperclass()) == null || superclass.getKind() == TypeKind.NONE) {
            return null;
        }
        return new TypeHandle(superclass);
    }

    public List<TypeHandle> getInterfaces() {
        if (getTargetElement() == null) {
            return Collections.emptyList();
        }
        ImmutableList.Builder<TypeHandle> list = ImmutableList.builder();
        for (DeclaredType declaredType : getTargetElement().getInterfaces()) {
            list.add(new TypeHandle(declaredType));
        }
        return list.build();
    }

    public boolean isPublic() {
        return getTargetElement() != null && getTargetElement().getModifiers().contains(Modifier.PUBLIC);
    }

    public boolean isImaginary() {
        return getTargetElement() == null;
    }

    public boolean isSimulated() {
        return false;
    }

    public final TypeReference getReference() {
        if (this.reference == null) {
            this.reference = new TypeReference(this);
        }
        return this.reference;
    }

    public MappingMethod getMappingMethod(String name, String desc) {
        return new ResolvableMappingMethod(this, name, desc);
    }

    public String findDescriptor(MemberInfo memberInfo) {
        String desc = memberInfo.desc;
        if (desc == null) {
            Iterator it = getEnclosedElements(ElementKind.METHOD).iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                ExecutableElement method = (ExecutableElement) it.next();
                if (method.getSimpleName().toString().equals(memberInfo.name)) {
                    desc = TypeUtils.getDescriptor(method);
                    break;
                }
            }
        }
        return desc;
    }

    public final FieldHandle findField(VariableElement element) {
        return findField(element, true);
    }

    public final FieldHandle findField(VariableElement element, boolean caseSensitive) {
        return findField(element.getSimpleName().toString(), TypeUtils.getTypeName(element.asType()), caseSensitive);
    }

    public final FieldHandle findField(String name, String type) {
        return findField(name, type, true);
    }

    public FieldHandle findField(String name, String type, boolean caseSensitive) {
        String rawType = TypeUtils.stripGenerics(type);
        for (VariableElement field : getEnclosedElements(ElementKind.FIELD)) {
            if (compareElement(field, name, type, caseSensitive)) {
                return new FieldHandle(getTargetElement(), field);
            }
            if (compareElement(field, name, rawType, caseSensitive)) {
                return new FieldHandle(getTargetElement(), field, true);
            }
        }
        return null;
    }

    public final MethodHandle findMethod(ExecutableElement element) {
        return findMethod(element, true);
    }

    public final MethodHandle findMethod(ExecutableElement element, boolean caseSensitive) {
        return findMethod(element.getSimpleName().toString(), TypeUtils.getJavaSignature((Element) element), caseSensitive);
    }

    public final MethodHandle findMethod(String name, String signature) {
        return findMethod(name, signature, true);
    }

    public MethodHandle findMethod(String name, String signature, boolean matchCase) {
        String rawSignature = TypeUtils.stripGenerics(signature);
        return findMethod(this, name, signature, rawSignature, matchCase);
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x0028  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static org.spongepowered.tools.obfuscation.mirror.MethodHandle findMethod(org.spongepowered.tools.obfuscation.mirror.TypeHandle r6, java.lang.String r7, java.lang.String r8, java.lang.String r9, boolean r10) {
        /*
            r0 = r6
            javax.lang.model.element.TypeElement r0 = r0.getTargetElement()
            r1 = 2
            javax.lang.model.element.ElementKind[] r1 = new javax.lang.model.element.ElementKind[r1]
            r2 = r1
            r3 = 0
            javax.lang.model.element.ElementKind r4 = javax.lang.model.element.ElementKind.CONSTRUCTOR
            r2[r3] = r4
            r2 = r1
            r3 = 1
            javax.lang.model.element.ElementKind r4 = javax.lang.model.element.ElementKind.METHOD
            r2[r3] = r4
            java.util.List r0 = getEnclosedElements(r0, r1)
            java.util.Iterator r0 = r0.iterator()
            r11 = r0
        L1e:
            r0 = r11
            boolean r0 = r0.hasNext()
            if (r0 == 0) goto L5a
            r0 = r11
            java.lang.Object r0 = r0.next()
            javax.lang.model.element.ExecutableElement r0 = (javax.lang.model.element.ExecutableElement) r0
            r12 = r0
            r0 = r12
            r1 = r7
            r2 = r8
            r3 = r10
            boolean r0 = compareElement(r0, r1, r2, r3)
            if (r0 != 0) goto L4c
            r0 = r12
            r1 = r7
            r2 = r9
            r3 = r10
            boolean r0 = compareElement(r0, r1, r2, r3)
            if (r0 == 0) goto L57
        L4c:
            org.spongepowered.tools.obfuscation.mirror.MethodHandle r0 = new org.spongepowered.tools.obfuscation.mirror.MethodHandle
            r1 = r0
            r2 = r6
            r3 = r12
            r1.<init>(r2, r3)
            return r0
        L57:
            goto L1e
        L5a:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongepowered.tools.obfuscation.mirror.TypeHandle.findMethod(org.spongepowered.tools.obfuscation.mirror.TypeHandle, java.lang.String, java.lang.String, java.lang.String, boolean):org.spongepowered.tools.obfuscation.mirror.MethodHandle");
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0048, code lost:
        if (r5.equals(r0) != false) goto L14;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected static boolean compareElement(javax.lang.model.element.Element r3, java.lang.String r4, java.lang.String r5, boolean r6) {
        /*
            r0 = r3
            javax.lang.model.element.Name r0 = r0.getSimpleName()     // Catch: java.lang.NullPointerException -> L51
            java.lang.String r0 = r0.toString()     // Catch: java.lang.NullPointerException -> L51
            r7 = r0
            r0 = r3
            java.lang.String r0 = org.spongepowered.tools.obfuscation.mirror.TypeUtils.getJavaSignature(r0)     // Catch: java.lang.NullPointerException -> L51
            r8 = r0
            r0 = r8
            java.lang.String r0 = org.spongepowered.tools.obfuscation.mirror.TypeUtils.stripGenerics(r0)     // Catch: java.lang.NullPointerException -> L51
            r9 = r0
            r0 = r6
            if (r0 == 0) goto L25
            r0 = r4
            r1 = r7
            boolean r0 = r0.equals(r1)     // Catch: java.lang.NullPointerException -> L51
            goto L2b
        L25:
            r0 = r4
            r1 = r7
            boolean r0 = r0.equalsIgnoreCase(r1)     // Catch: java.lang.NullPointerException -> L51
        L2b:
            r10 = r0
            r0 = r10
            if (r0 == 0) goto L4f
            r0 = r5
            int r0 = r0.length()     // Catch: java.lang.NullPointerException -> L51
            if (r0 == 0) goto L4b
            r0 = r5
            r1 = r8
            boolean r0 = r0.equals(r1)     // Catch: java.lang.NullPointerException -> L51
            if (r0 != 0) goto L4b
            r0 = r5
            r1 = r9
            boolean r0 = r0.equals(r1)     // Catch: java.lang.NullPointerException -> L51
            if (r0 == 0) goto L4f
        L4b:
            r0 = 1
            goto L50
        L4f:
            r0 = 0
        L50:
            return r0
        L51:
            r7 = move-exception
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongepowered.tools.obfuscation.mirror.TypeHandle.compareElement(javax.lang.model.element.Element, java.lang.String, java.lang.String, boolean):boolean");
    }

    protected static <T extends Element> List<T> getEnclosedElements(TypeElement targetElement, ElementKind... kind) {
        if (kind == null || kind.length < 1) {
            return getEnclosedElements(targetElement);
        }
        if (targetElement == null) {
            return Collections.emptyList();
        }
        ImmutableList.Builder<T> list = ImmutableList.builder();
        for (Element elem : targetElement.getEnclosedElements()) {
            int length = kind.length;
            int i = 0;
            while (true) {
                if (i < length) {
                    ElementKind ek = kind[i];
                    if (elem.getKind() != ek) {
                        i++;
                    } else {
                        list.add(elem);
                        break;
                    }
                }
            }
        }
        return list.build();
    }

    protected static List<? extends Element> getEnclosedElements(TypeElement targetElement) {
        return targetElement != null ? targetElement.getEnclosedElements() : Collections.emptyList();
    }
}
