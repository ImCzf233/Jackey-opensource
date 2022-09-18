package org.spongepowered.asm.lib.util;

import java.io.PrintWriter;
import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.ClassVisitor;
import org.spongepowered.asm.lib.FieldVisitor;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.lib.TypePath;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/util/TraceClassVisitor.class */
public final class TraceClassVisitor extends ClassVisitor {

    /* renamed from: pw */
    private final PrintWriter f429pw;

    /* renamed from: p */
    public final Printer f430p;

    public TraceClassVisitor(PrintWriter pw) {
        this(null, pw);
    }

    public TraceClassVisitor(ClassVisitor cv, PrintWriter pw) {
        this(cv, new Textifier(), pw);
    }

    public TraceClassVisitor(ClassVisitor cv, Printer p, PrintWriter pw) {
        super(Opcodes.ASM5, cv);
        this.f429pw = pw;
        this.f430p = p;
    }

    @Override // org.spongepowered.asm.lib.ClassVisitor
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.f430p.visit(version, access, name, signature, superName, interfaces);
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override // org.spongepowered.asm.lib.ClassVisitor
    public void visitSource(String file, String debug) {
        this.f430p.visitSource(file, debug);
        super.visitSource(file, debug);
    }

    @Override // org.spongepowered.asm.lib.ClassVisitor
    public void visitOuterClass(String owner, String name, String desc) {
        this.f430p.visitOuterClass(owner, name, desc);
        super.visitOuterClass(owner, name, desc);
    }

    @Override // org.spongepowered.asm.lib.ClassVisitor
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        Printer p = this.f430p.visitClassAnnotation(desc, visible);
        AnnotationVisitor av = this.f415cv == null ? null : this.f415cv.visitAnnotation(desc, visible);
        return new TraceAnnotationVisitor(av, p);
    }

    @Override // org.spongepowered.asm.lib.ClassVisitor
    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        Printer p = this.f430p.visitClassTypeAnnotation(typeRef, typePath, desc, visible);
        AnnotationVisitor av = this.f415cv == null ? null : this.f415cv.visitTypeAnnotation(typeRef, typePath, desc, visible);
        return new TraceAnnotationVisitor(av, p);
    }

    @Override // org.spongepowered.asm.lib.ClassVisitor
    public void visitAttribute(Attribute attr) {
        this.f430p.visitClassAttribute(attr);
        super.visitAttribute(attr);
    }

    @Override // org.spongepowered.asm.lib.ClassVisitor
    public void visitInnerClass(String name, String outerName, String innerName, int access) {
        this.f430p.visitInnerClass(name, outerName, innerName, access);
        super.visitInnerClass(name, outerName, innerName, access);
    }

    @Override // org.spongepowered.asm.lib.ClassVisitor
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        Printer p = this.f430p.visitField(access, name, desc, signature, value);
        FieldVisitor fv = this.f415cv == null ? null : this.f415cv.visitField(access, name, desc, signature, value);
        return new TraceFieldVisitor(fv, p);
    }

    @Override // org.spongepowered.asm.lib.ClassVisitor
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        Printer p = this.f430p.visitMethod(access, name, desc, signature, exceptions);
        MethodVisitor mv = this.f415cv == null ? null : this.f415cv.visitMethod(access, name, desc, signature, exceptions);
        return new TraceMethodVisitor(mv, p);
    }

    @Override // org.spongepowered.asm.lib.ClassVisitor
    public void visitEnd() {
        this.f430p.visitClassEnd();
        if (this.f429pw != null) {
            this.f430p.print(this.f429pw);
            this.f429pw.flush();
        }
        super.visitEnd();
    }
}
