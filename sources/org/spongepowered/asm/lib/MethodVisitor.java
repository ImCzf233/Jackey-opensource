package org.spongepowered.asm.lib;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/MethodVisitor.class */
public abstract class MethodVisitor {
    protected final int api;

    /* renamed from: mv */
    protected MethodVisitor f419mv;

    public MethodVisitor(int api) {
        this(api, null);
    }

    public MethodVisitor(int api, MethodVisitor mv) {
        if (api != 262144 && api != 327680) {
            throw new IllegalArgumentException();
        }
        this.api = api;
        this.f419mv = mv;
    }

    public void visitParameter(String name, int access) {
        if (this.api < 327680) {
            throw new RuntimeException();
        }
        if (this.f419mv != null) {
            this.f419mv.visitParameter(name, access);
        }
    }

    public AnnotationVisitor visitAnnotationDefault() {
        if (this.f419mv != null) {
            return this.f419mv.visitAnnotationDefault();
        }
        return null;
    }

    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        if (this.f419mv != null) {
            return this.f419mv.visitAnnotation(desc, visible);
        }
        return null;
    }

    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        if (this.api < 327680) {
            throw new RuntimeException();
        }
        if (this.f419mv != null) {
            return this.f419mv.visitTypeAnnotation(typeRef, typePath, desc, visible);
        }
        return null;
    }

    public AnnotationVisitor visitParameterAnnotation(int parameter, String desc, boolean visible) {
        if (this.f419mv != null) {
            return this.f419mv.visitParameterAnnotation(parameter, desc, visible);
        }
        return null;
    }

    public void visitAttribute(Attribute attr) {
        if (this.f419mv != null) {
            this.f419mv.visitAttribute(attr);
        }
    }

    public void visitCode() {
        if (this.f419mv != null) {
            this.f419mv.visitCode();
        }
    }

    public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
        if (this.f419mv != null) {
            this.f419mv.visitFrame(type, nLocal, local, nStack, stack);
        }
    }

    public void visitInsn(int opcode) {
        if (this.f419mv != null) {
            this.f419mv.visitInsn(opcode);
        }
    }

    public void visitIntInsn(int opcode, int operand) {
        if (this.f419mv != null) {
            this.f419mv.visitIntInsn(opcode, operand);
        }
    }

    public void visitVarInsn(int opcode, int var) {
        if (this.f419mv != null) {
            this.f419mv.visitVarInsn(opcode, var);
        }
    }

    public void visitTypeInsn(int opcode, String type) {
        if (this.f419mv != null) {
            this.f419mv.visitTypeInsn(opcode, type);
        }
    }

    public void visitFieldInsn(int opcode, String owner, String name, String desc) {
        if (this.f419mv != null) {
            this.f419mv.visitFieldInsn(opcode, owner, name, desc);
        }
    }

    @Deprecated
    public void visitMethodInsn(int opcode, String owner, String name, String desc) {
        if (this.api >= 327680) {
            boolean itf = opcode == 185;
            visitMethodInsn(opcode, owner, name, desc, itf);
        } else if (this.f419mv != null) {
            this.f419mv.visitMethodInsn(opcode, owner, name, desc);
        }
    }

    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        if (this.api < 327680) {
            if (itf != (opcode == 185)) {
                throw new IllegalArgumentException("INVOKESPECIAL/STATIC on interfaces require ASM 5");
            }
            visitMethodInsn(opcode, owner, name, desc);
        } else if (this.f419mv != null) {
            this.f419mv.visitMethodInsn(opcode, owner, name, desc, itf);
        }
    }

    public void visitInvokeDynamicInsn(String name, String desc, Handle bsm, Object... bsmArgs) {
        if (this.f419mv != null) {
            this.f419mv.visitInvokeDynamicInsn(name, desc, bsm, bsmArgs);
        }
    }

    public void visitJumpInsn(int opcode, Label label) {
        if (this.f419mv != null) {
            this.f419mv.visitJumpInsn(opcode, label);
        }
    }

    public void visitLabel(Label label) {
        if (this.f419mv != null) {
            this.f419mv.visitLabel(label);
        }
    }

    public void visitLdcInsn(Object cst) {
        if (this.f419mv != null) {
            this.f419mv.visitLdcInsn(cst);
        }
    }

    public void visitIincInsn(int var, int increment) {
        if (this.f419mv != null) {
            this.f419mv.visitIincInsn(var, increment);
        }
    }

    public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
        if (this.f419mv != null) {
            this.f419mv.visitTableSwitchInsn(min, max, dflt, labels);
        }
    }

    public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
        if (this.f419mv != null) {
            this.f419mv.visitLookupSwitchInsn(dflt, keys, labels);
        }
    }

    public void visitMultiANewArrayInsn(String desc, int dims) {
        if (this.f419mv != null) {
            this.f419mv.visitMultiANewArrayInsn(desc, dims);
        }
    }

    public AnnotationVisitor visitInsnAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        if (this.api < 327680) {
            throw new RuntimeException();
        }
        if (this.f419mv != null) {
            return this.f419mv.visitInsnAnnotation(typeRef, typePath, desc, visible);
        }
        return null;
    }

    public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
        if (this.f419mv != null) {
            this.f419mv.visitTryCatchBlock(start, end, handler, type);
        }
    }

    public AnnotationVisitor visitTryCatchAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        if (this.api < 327680) {
            throw new RuntimeException();
        }
        if (this.f419mv != null) {
            return this.f419mv.visitTryCatchAnnotation(typeRef, typePath, desc, visible);
        }
        return null;
    }

    public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
        if (this.f419mv != null) {
            this.f419mv.visitLocalVariable(name, desc, signature, start, end, index);
        }
    }

    public AnnotationVisitor visitLocalVariableAnnotation(int typeRef, TypePath typePath, Label[] start, Label[] end, int[] index, String desc, boolean visible) {
        if (this.api < 327680) {
            throw new RuntimeException();
        }
        if (this.f419mv != null) {
            return this.f419mv.visitLocalVariableAnnotation(typeRef, typePath, start, end, index, desc, visible);
        }
        return null;
    }

    public void visitLineNumber(int line, Label start) {
        if (this.f419mv != null) {
            this.f419mv.visitLineNumber(line, start);
        }
    }

    public void visitMaxs(int maxStack, int maxLocals) {
        if (this.f419mv != null) {
            this.f419mv.visitMaxs(maxStack, maxLocals);
        }
    }

    public void visitEnd() {
        if (this.f419mv != null) {
            this.f419mv.visitEnd();
        }
    }
}
