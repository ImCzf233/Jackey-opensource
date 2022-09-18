package org.spongepowered.asm.lib;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/ClassVisitor.class */
public abstract class ClassVisitor {
    protected final int api;

    /* renamed from: cv */
    protected ClassVisitor f415cv;

    public ClassVisitor(int api) {
        this(api, null);
    }

    public ClassVisitor(int api, ClassVisitor cv) {
        if (api != 262144 && api != 327680) {
            throw new IllegalArgumentException();
        }
        this.api = api;
        this.f415cv = cv;
    }

    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        if (this.f415cv != null) {
            this.f415cv.visit(version, access, name, signature, superName, interfaces);
        }
    }

    public void visitSource(String source, String debug) {
        if (this.f415cv != null) {
            this.f415cv.visitSource(source, debug);
        }
    }

    public void visitOuterClass(String owner, String name, String desc) {
        if (this.f415cv != null) {
            this.f415cv.visitOuterClass(owner, name, desc);
        }
    }

    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        if (this.f415cv != null) {
            return this.f415cv.visitAnnotation(desc, visible);
        }
        return null;
    }

    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        if (this.api < 327680) {
            throw new RuntimeException();
        }
        if (this.f415cv != null) {
            return this.f415cv.visitTypeAnnotation(typeRef, typePath, desc, visible);
        }
        return null;
    }

    public void visitAttribute(Attribute attr) {
        if (this.f415cv != null) {
            this.f415cv.visitAttribute(attr);
        }
    }

    public void visitInnerClass(String name, String outerName, String innerName, int access) {
        if (this.f415cv != null) {
            this.f415cv.visitInnerClass(name, outerName, innerName, access);
        }
    }

    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        if (this.f415cv != null) {
            return this.f415cv.visitField(access, name, desc, signature, value);
        }
        return null;
    }

    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        if (this.f415cv != null) {
            return this.f415cv.visitMethod(access, name, desc, signature, exceptions);
        }
        return null;
    }

    public void visitEnd() {
        if (this.f415cv != null) {
            this.f415cv.visitEnd();
        }
    }
}
