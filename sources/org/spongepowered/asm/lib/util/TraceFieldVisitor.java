package org.spongepowered.asm.lib.util;

import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.FieldVisitor;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.lib.TypePath;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/util/TraceFieldVisitor.class */
public final class TraceFieldVisitor extends FieldVisitor {

    /* renamed from: p */
    public final Printer f431p;

    public TraceFieldVisitor(Printer p) {
        this(null, p);
    }

    public TraceFieldVisitor(FieldVisitor fv, Printer p) {
        super(Opcodes.ASM5, fv);
        this.f431p = p;
    }

    @Override // org.spongepowered.asm.lib.FieldVisitor
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        Printer p = this.f431p.visitFieldAnnotation(desc, visible);
        AnnotationVisitor av = this.f417fv == null ? null : this.f417fv.visitAnnotation(desc, visible);
        return new TraceAnnotationVisitor(av, p);
    }

    @Override // org.spongepowered.asm.lib.FieldVisitor
    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        Printer p = this.f431p.visitFieldTypeAnnotation(typeRef, typePath, desc, visible);
        AnnotationVisitor av = this.f417fv == null ? null : this.f417fv.visitTypeAnnotation(typeRef, typePath, desc, visible);
        return new TraceAnnotationVisitor(av, p);
    }

    @Override // org.spongepowered.asm.lib.FieldVisitor
    public void visitAttribute(Attribute attr) {
        this.f431p.visitFieldAttribute(attr);
        super.visitAttribute(attr);
    }

    @Override // org.spongepowered.asm.lib.FieldVisitor
    public void visitEnd() {
        this.f431p.visitFieldEnd();
        super.visitEnd();
    }
}
