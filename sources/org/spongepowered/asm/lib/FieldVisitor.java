package org.spongepowered.asm.lib;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/FieldVisitor.class */
public abstract class FieldVisitor {
    protected final int api;

    /* renamed from: fv */
    protected FieldVisitor f417fv;

    public FieldVisitor(int api) {
        this(api, null);
    }

    public FieldVisitor(int api, FieldVisitor fv) {
        if (api != 262144 && api != 327680) {
            throw new IllegalArgumentException();
        }
        this.api = api;
        this.f417fv = fv;
    }

    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        if (this.f417fv != null) {
            return this.f417fv.visitAnnotation(desc, visible);
        }
        return null;
    }

    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        if (this.api < 327680) {
            throw new RuntimeException();
        }
        if (this.f417fv != null) {
            return this.f417fv.visitTypeAnnotation(typeRef, typePath, desc, visible);
        }
        return null;
    }

    public void visitAttribute(Attribute attr) {
        if (this.f417fv != null) {
            this.f417fv.visitAttribute(attr);
        }
    }

    public void visitEnd() {
        if (this.f417fv != null) {
            this.f417fv.visitEnd();
        }
    }
}
