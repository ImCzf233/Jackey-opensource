package org.spongepowered.asm.lib.util;

import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.Handle;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.lib.TypePath;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/util/TraceMethodVisitor.class */
public final class TraceMethodVisitor extends MethodVisitor {

    /* renamed from: p */
    public final Printer f432p;

    public TraceMethodVisitor(Printer p) {
        this(null, p);
    }

    public TraceMethodVisitor(MethodVisitor mv, Printer p) {
        super(Opcodes.ASM5, mv);
        this.f432p = p;
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitParameter(String name, int access) {
        this.f432p.visitParameter(name, access);
        super.visitParameter(name, access);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        Printer p = this.f432p.visitMethodAnnotation(desc, visible);
        AnnotationVisitor av = this.f419mv == null ? null : this.f419mv.visitAnnotation(desc, visible);
        return new TraceAnnotationVisitor(av, p);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        Printer p = this.f432p.visitMethodTypeAnnotation(typeRef, typePath, desc, visible);
        AnnotationVisitor av = this.f419mv == null ? null : this.f419mv.visitTypeAnnotation(typeRef, typePath, desc, visible);
        return new TraceAnnotationVisitor(av, p);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitAttribute(Attribute attr) {
        this.f432p.visitMethodAttribute(attr);
        super.visitAttribute(attr);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public AnnotationVisitor visitAnnotationDefault() {
        Printer p = this.f432p.visitAnnotationDefault();
        AnnotationVisitor av = this.f419mv == null ? null : this.f419mv.visitAnnotationDefault();
        return new TraceAnnotationVisitor(av, p);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public AnnotationVisitor visitParameterAnnotation(int parameter, String desc, boolean visible) {
        Printer p = this.f432p.visitParameterAnnotation(parameter, desc, visible);
        AnnotationVisitor av = this.f419mv == null ? null : this.f419mv.visitParameterAnnotation(parameter, desc, visible);
        return new TraceAnnotationVisitor(av, p);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitCode() {
        this.f432p.visitCode();
        super.visitCode();
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
        this.f432p.visitFrame(type, nLocal, local, nStack, stack);
        super.visitFrame(type, nLocal, local, nStack, stack);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitInsn(int opcode) {
        this.f432p.visitInsn(opcode);
        super.visitInsn(opcode);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitIntInsn(int opcode, int operand) {
        this.f432p.visitIntInsn(opcode, operand);
        super.visitIntInsn(opcode, operand);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitVarInsn(int opcode, int var) {
        this.f432p.visitVarInsn(opcode, var);
        super.visitVarInsn(opcode, var);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitTypeInsn(int opcode, String type) {
        this.f432p.visitTypeInsn(opcode, type);
        super.visitTypeInsn(opcode, type);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitFieldInsn(int opcode, String owner, String name, String desc) {
        this.f432p.visitFieldInsn(opcode, owner, name, desc);
        super.visitFieldInsn(opcode, owner, name, desc);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    @Deprecated
    public void visitMethodInsn(int opcode, String owner, String name, String desc) {
        if (this.api >= 327680) {
            super.visitMethodInsn(opcode, owner, name, desc);
            return;
        }
        this.f432p.visitMethodInsn(opcode, owner, name, desc);
        if (this.f419mv != null) {
            this.f419mv.visitMethodInsn(opcode, owner, name, desc);
        }
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        if (this.api < 327680) {
            super.visitMethodInsn(opcode, owner, name, desc, itf);
            return;
        }
        this.f432p.visitMethodInsn(opcode, owner, name, desc, itf);
        if (this.f419mv != null) {
            this.f419mv.visitMethodInsn(opcode, owner, name, desc, itf);
        }
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitInvokeDynamicInsn(String name, String desc, Handle bsm, Object... bsmArgs) {
        this.f432p.visitInvokeDynamicInsn(name, desc, bsm, bsmArgs);
        super.visitInvokeDynamicInsn(name, desc, bsm, bsmArgs);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitJumpInsn(int opcode, Label label) {
        this.f432p.visitJumpInsn(opcode, label);
        super.visitJumpInsn(opcode, label);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitLabel(Label label) {
        this.f432p.visitLabel(label);
        super.visitLabel(label);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitLdcInsn(Object cst) {
        this.f432p.visitLdcInsn(cst);
        super.visitLdcInsn(cst);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitIincInsn(int var, int increment) {
        this.f432p.visitIincInsn(var, increment);
        super.visitIincInsn(var, increment);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
        this.f432p.visitTableSwitchInsn(min, max, dflt, labels);
        super.visitTableSwitchInsn(min, max, dflt, labels);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
        this.f432p.visitLookupSwitchInsn(dflt, keys, labels);
        super.visitLookupSwitchInsn(dflt, keys, labels);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitMultiANewArrayInsn(String desc, int dims) {
        this.f432p.visitMultiANewArrayInsn(desc, dims);
        super.visitMultiANewArrayInsn(desc, dims);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public AnnotationVisitor visitInsnAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        Printer p = this.f432p.visitInsnAnnotation(typeRef, typePath, desc, visible);
        AnnotationVisitor av = this.f419mv == null ? null : this.f419mv.visitInsnAnnotation(typeRef, typePath, desc, visible);
        return new TraceAnnotationVisitor(av, p);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
        this.f432p.visitTryCatchBlock(start, end, handler, type);
        super.visitTryCatchBlock(start, end, handler, type);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public AnnotationVisitor visitTryCatchAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        Printer p = this.f432p.visitTryCatchAnnotation(typeRef, typePath, desc, visible);
        AnnotationVisitor av = this.f419mv == null ? null : this.f419mv.visitTryCatchAnnotation(typeRef, typePath, desc, visible);
        return new TraceAnnotationVisitor(av, p);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
        this.f432p.visitLocalVariable(name, desc, signature, start, end, index);
        super.visitLocalVariable(name, desc, signature, start, end, index);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public AnnotationVisitor visitLocalVariableAnnotation(int typeRef, TypePath typePath, Label[] start, Label[] end, int[] index, String desc, boolean visible) {
        Printer p = this.f432p.visitLocalVariableAnnotation(typeRef, typePath, start, end, index, desc, visible);
        AnnotationVisitor av = this.f419mv == null ? null : this.f419mv.visitLocalVariableAnnotation(typeRef, typePath, start, end, index, desc, visible);
        return new TraceAnnotationVisitor(av, p);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitLineNumber(int line, Label start) {
        this.f432p.visitLineNumber(line, start);
        super.visitLineNumber(line, start);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitMaxs(int maxStack, int maxLocals) {
        this.f432p.visitMaxs(maxStack, maxLocals);
        super.visitMaxs(maxStack, maxLocals);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitEnd() {
        this.f432p.visitMethodEnd();
        super.visitEnd();
    }
}
