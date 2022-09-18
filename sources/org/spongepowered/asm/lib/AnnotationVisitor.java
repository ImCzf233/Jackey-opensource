package org.spongepowered.asm.lib;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/AnnotationVisitor.class */
public abstract class AnnotationVisitor {
    protected final int api;

    /* renamed from: av */
    protected AnnotationVisitor f411av;

    public AnnotationVisitor(int api) {
        this(api, null);
    }

    public AnnotationVisitor(int api, AnnotationVisitor av) {
        if (api != 262144 && api != 327680) {
            throw new IllegalArgumentException();
        }
        this.api = api;
        this.f411av = av;
    }

    public void visit(String name, Object value) {
        if (this.f411av != null) {
            this.f411av.visit(name, value);
        }
    }

    public void visitEnum(String name, String desc, String value) {
        if (this.f411av != null) {
            this.f411av.visitEnum(name, desc, value);
        }
    }

    public AnnotationVisitor visitAnnotation(String name, String desc) {
        if (this.f411av != null) {
            return this.f411av.visitAnnotation(name, desc);
        }
        return null;
    }

    public AnnotationVisitor visitArray(String name) {
        if (this.f411av != null) {
            return this.f411av.visitArray(name);
        }
        return null;
    }

    public void visitEnd() {
        if (this.f411av != null) {
            this.f411av.visitEnd();
        }
    }
}
