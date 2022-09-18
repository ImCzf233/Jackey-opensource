package org.spongepowered.asm.lib.util;

import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Opcodes;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/util/TraceAnnotationVisitor.class */
public final class TraceAnnotationVisitor extends AnnotationVisitor {

    /* renamed from: p */
    private final Printer f428p;

    public TraceAnnotationVisitor(Printer p) {
        this(null, p);
    }

    public TraceAnnotationVisitor(AnnotationVisitor av, Printer p) {
        super(Opcodes.ASM5, av);
        this.f428p = p;
    }

    @Override // org.spongepowered.asm.lib.AnnotationVisitor
    public void visit(String name, Object value) {
        this.f428p.visit(name, value);
        super.visit(name, value);
    }

    @Override // org.spongepowered.asm.lib.AnnotationVisitor
    public void visitEnum(String name, String desc, String value) {
        this.f428p.visitEnum(name, desc, value);
        super.visitEnum(name, desc, value);
    }

    @Override // org.spongepowered.asm.lib.AnnotationVisitor
    public AnnotationVisitor visitAnnotation(String name, String desc) {
        Printer p = this.f428p.visitAnnotation(name, desc);
        AnnotationVisitor av = this.f411av == null ? null : this.f411av.visitAnnotation(name, desc);
        return new TraceAnnotationVisitor(av, p);
    }

    @Override // org.spongepowered.asm.lib.AnnotationVisitor
    public AnnotationVisitor visitArray(String name) {
        Printer p = this.f428p.visitArray(name);
        AnnotationVisitor av = this.f411av == null ? null : this.f411av.visitArray(name);
        return new TraceAnnotationVisitor(av, p);
    }

    @Override // org.spongepowered.asm.lib.AnnotationVisitor
    public void visitEnd() {
        this.f428p.visitAnnotationEnd();
        super.visitEnd();
    }
}
