package org.spongepowered.asm.lib.commons;

import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Handle;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.lib.TypePath;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/commons/MethodRemapper.class */
public class MethodRemapper extends MethodVisitor {
    protected final Remapper remapper;

    public MethodRemapper(MethodVisitor mv, Remapper remapper) {
        this(Opcodes.ASM5, mv, remapper);
    }

    protected MethodRemapper(int api, MethodVisitor mv, Remapper remapper) {
        super(api, mv);
        this.remapper = remapper;
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public AnnotationVisitor visitAnnotationDefault() {
        AnnotationVisitor av = super.visitAnnotationDefault();
        return av == null ? av : new AnnotationRemapper(av, this.remapper);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        AnnotationVisitor av = super.visitAnnotation(this.remapper.mapDesc(desc), visible);
        return av == null ? av : new AnnotationRemapper(av, this.remapper);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        AnnotationVisitor av = super.visitTypeAnnotation(typeRef, typePath, this.remapper.mapDesc(desc), visible);
        return av == null ? av : new AnnotationRemapper(av, this.remapper);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public AnnotationVisitor visitParameterAnnotation(int parameter, String desc, boolean visible) {
        AnnotationVisitor av = super.visitParameterAnnotation(parameter, this.remapper.mapDesc(desc), visible);
        return av == null ? av : new AnnotationRemapper(av, this.remapper);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
        super.visitFrame(type, nLocal, remapEntries(nLocal, local), nStack, remapEntries(nStack, stack));
    }

    private Object[] remapEntries(int n, Object[] entries) {
        int i = 0;
        while (i < n) {
            if (!(entries[i] instanceof String)) {
                i++;
            } else {
                Object[] newEntries = new Object[n];
                if (i > 0) {
                    System.arraycopy(entries, 0, newEntries, 0, i);
                }
                do {
                    Object t = entries[i];
                    int i2 = i;
                    i++;
                    newEntries[i2] = t instanceof String ? this.remapper.mapType((String) t) : t;
                } while (i < n);
                return newEntries;
            }
        }
        return entries;
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitFieldInsn(int opcode, String owner, String name, String desc) {
        super.visitFieldInsn(opcode, this.remapper.mapType(owner), this.remapper.mapFieldName(owner, name, desc), this.remapper.mapDesc(desc));
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    @Deprecated
    public void visitMethodInsn(int opcode, String owner, String name, String desc) {
        if (this.api >= 327680) {
            super.visitMethodInsn(opcode, owner, name, desc);
        } else {
            doVisitMethodInsn(opcode, owner, name, desc, opcode == 185);
        }
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        if (this.api < 327680) {
            super.visitMethodInsn(opcode, owner, name, desc, itf);
        } else {
            doVisitMethodInsn(opcode, owner, name, desc, itf);
        }
    }

    private void doVisitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        if (this.f419mv != null) {
            this.f419mv.visitMethodInsn(opcode, this.remapper.mapType(owner), this.remapper.mapMethodName(owner, name, desc), this.remapper.mapMethodDesc(desc), itf);
        }
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitInvokeDynamicInsn(String name, String desc, Handle bsm, Object... bsmArgs) {
        for (int i = 0; i < bsmArgs.length; i++) {
            bsmArgs[i] = this.remapper.mapValue(bsmArgs[i]);
        }
        super.visitInvokeDynamicInsn(this.remapper.mapInvokeDynamicMethodName(name, desc), this.remapper.mapMethodDesc(desc), (Handle) this.remapper.mapValue(bsm), bsmArgs);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitTypeInsn(int opcode, String type) {
        super.visitTypeInsn(opcode, this.remapper.mapType(type));
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitLdcInsn(Object cst) {
        super.visitLdcInsn(this.remapper.mapValue(cst));
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitMultiANewArrayInsn(String desc, int dims) {
        super.visitMultiANewArrayInsn(this.remapper.mapDesc(desc), dims);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public AnnotationVisitor visitInsnAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        AnnotationVisitor av = super.visitInsnAnnotation(typeRef, typePath, this.remapper.mapDesc(desc), visible);
        return av == null ? av : new AnnotationRemapper(av, this.remapper);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
        super.visitTryCatchBlock(start, end, handler, type == null ? null : this.remapper.mapType(type));
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public AnnotationVisitor visitTryCatchAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        AnnotationVisitor av = super.visitTryCatchAnnotation(typeRef, typePath, this.remapper.mapDesc(desc), visible);
        return av == null ? av : new AnnotationRemapper(av, this.remapper);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
        super.visitLocalVariable(name, this.remapper.mapDesc(desc), this.remapper.mapSignature(signature, true), start, end, index);
    }

    @Override // org.spongepowered.asm.lib.MethodVisitor
    public AnnotationVisitor visitLocalVariableAnnotation(int typeRef, TypePath typePath, Label[] start, Label[] end, int[] index, String desc, boolean visible) {
        AnnotationVisitor av = super.visitLocalVariableAnnotation(typeRef, typePath, start, end, index, this.remapper.mapDesc(desc), visible);
        return av == null ? av : new AnnotationRemapper(av, this.remapper);
    }
}
