package org.spongepowered.asm.lib.util;

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.spi.Configurator;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.ClassReader;
import org.spongepowered.asm.lib.Handle;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.TypePath;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/util/ASMifier.class */
public class ASMifier extends Printer {
    protected final String name;

    /* renamed from: id */
    protected final int f426id;
    protected Map<Label, String> labelNames;
    private static final int ACCESS_CLASS = 262144;
    private static final int ACCESS_FIELD = 524288;
    private static final int ACCESS_INNER = 1048576;

    public ASMifier() {
        this(Opcodes.ASM5, "cw", 0);
        if (getClass() != ASMifier.class) {
            throw new IllegalStateException();
        }
    }

    protected ASMifier(int api, String name, int id) {
        super(api);
        this.name = name;
        this.f426id = id;
    }

    public static void main(String[] args) throws Exception {
        ClassReader cr;
        int i = 0;
        int flags = 2;
        boolean ok = true;
        if (args.length < 1 || args.length > 2) {
            ok = false;
        }
        if (ok && "-debug".equals(args[0])) {
            i = 1;
            flags = 0;
            if (args.length != 2) {
                ok = false;
            }
        }
        if (!ok) {
            System.err.println("Prints the ASM code to generate the given class.");
            System.err.println("Usage: ASMifier [-debug] <fully qualified class name or class file name>");
            return;
        }
        if (args[i].endsWith(".class") || args[i].indexOf(92) > -1 || args[i].indexOf(47) > -1) {
            cr = new ClassReader(new FileInputStream(args[i]));
        } else {
            cr = new ClassReader(args[i]);
        }
        cr.accept(new TraceClassVisitor(null, new ASMifier(), new PrintWriter(System.out)), flags);
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        String simpleName;
        int n = name.lastIndexOf(47);
        if (n == -1) {
            simpleName = name;
        } else {
            this.text.add("package asm." + name.substring(0, n).replace('/', '.') + ";\n");
            simpleName = name.substring(n + 1);
        }
        this.text.add("import java.util.*;\n");
        this.text.add("import org.objectweb.asm.*;\n");
        this.text.add("public class " + simpleName + "Dump implements Opcodes {\n\n");
        this.text.add("public static byte[] dump () throws Exception {\n\n");
        this.text.add("ClassWriter cw = new ClassWriter(0);\n");
        this.text.add("FieldVisitor fv;\n");
        this.text.add("MethodVisitor mv;\n");
        this.text.add("AnnotationVisitor av0;\n\n");
        this.buf.setLength(0);
        this.buf.append("cw.visit(");
        switch (version) {
            case 46:
                this.buf.append("V1_2");
                break;
            case 47:
                this.buf.append("V1_3");
                break;
            case 48:
                this.buf.append("V1_4");
                break;
            case 49:
                this.buf.append("V1_5");
                break;
            case 50:
                this.buf.append("V1_6");
                break;
            case 51:
                this.buf.append("V1_7");
                break;
            case Opcodes.V1_1 /* 196653 */:
                this.buf.append("V1_1");
                break;
            default:
                this.buf.append(version);
                break;
        }
        this.buf.append(", ");
        appendAccess(access | 262144);
        this.buf.append(", ");
        appendConstant(name);
        this.buf.append(", ");
        appendConstant(signature);
        this.buf.append(", ");
        appendConstant(superName);
        this.buf.append(", ");
        if (interfaces != null && interfaces.length > 0) {
            this.buf.append("new String[] {");
            int i = 0;
            while (i < interfaces.length) {
                this.buf.append(i == 0 ? " " : ", ");
                appendConstant(interfaces[i]);
                i++;
            }
            this.buf.append(" }");
        } else {
            this.buf.append(Configurator.NULL);
        }
        this.buf.append(");\n\n");
        this.text.add(this.buf.toString());
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitSource(String file, String debug) {
        this.buf.setLength(0);
        this.buf.append("cw.visitSource(");
        appendConstant(file);
        this.buf.append(", ");
        appendConstant(debug);
        this.buf.append(");\n\n");
        this.text.add(this.buf.toString());
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitOuterClass(String owner, String name, String desc) {
        this.buf.setLength(0);
        this.buf.append("cw.visitOuterClass(");
        appendConstant(owner);
        this.buf.append(", ");
        appendConstant(name);
        this.buf.append(", ");
        appendConstant(desc);
        this.buf.append(");\n\n");
        this.text.add(this.buf.toString());
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public ASMifier visitClassAnnotation(String desc, boolean visible) {
        return visitAnnotation(desc, visible);
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public ASMifier visitClassTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        return visitTypeAnnotation(typeRef, typePath, desc, visible);
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitClassAttribute(Attribute attr) {
        visitAttribute(attr);
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitInnerClass(String name, String outerName, String innerName, int access) {
        this.buf.setLength(0);
        this.buf.append("cw.visitInnerClass(");
        appendConstant(name);
        this.buf.append(", ");
        appendConstant(outerName);
        this.buf.append(", ");
        appendConstant(innerName);
        this.buf.append(", ");
        appendAccess(access | 1048576);
        this.buf.append(");\n\n");
        this.text.add(this.buf.toString());
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public ASMifier visitField(int access, String name, String desc, String signature, Object value) {
        this.buf.setLength(0);
        this.buf.append("{\n");
        this.buf.append("fv = cw.visitField(");
        appendAccess(access | 524288);
        this.buf.append(", ");
        appendConstant(name);
        this.buf.append(", ");
        appendConstant(desc);
        this.buf.append(", ");
        appendConstant(signature);
        this.buf.append(", ");
        appendConstant(value);
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
        ASMifier a = createASMifier("fv", 0);
        this.text.add(a.getText());
        this.text.add("}\n");
        return a;
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public ASMifier visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        this.buf.setLength(0);
        this.buf.append("{\n");
        this.buf.append("mv = cw.visitMethod(");
        appendAccess(access);
        this.buf.append(", ");
        appendConstant(name);
        this.buf.append(", ");
        appendConstant(desc);
        this.buf.append(", ");
        appendConstant(signature);
        this.buf.append(", ");
        if (exceptions != null && exceptions.length > 0) {
            this.buf.append("new String[] {");
            int i = 0;
            while (i < exceptions.length) {
                this.buf.append(i == 0 ? " " : ", ");
                appendConstant(exceptions[i]);
                i++;
            }
            this.buf.append(" }");
        } else {
            this.buf.append(Configurator.NULL);
        }
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
        ASMifier a = createASMifier("mv", 0);
        this.text.add(a.getText());
        this.text.add("}\n");
        return a;
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitClassEnd() {
        this.text.add("cw.visitEnd();\n\n");
        this.text.add("return cw.toByteArray();\n");
        this.text.add("}\n");
        this.text.add("}\n");
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visit(String name, Object value) {
        this.buf.setLength(0);
        this.buf.append("av").append(this.f426id).append(".visit(");
        appendConstant(this.buf, name);
        this.buf.append(", ");
        appendConstant(this.buf, value);
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitEnum(String name, String desc, String value) {
        this.buf.setLength(0);
        this.buf.append("av").append(this.f426id).append(".visitEnum(");
        appendConstant(this.buf, name);
        this.buf.append(", ");
        appendConstant(this.buf, desc);
        this.buf.append(", ");
        appendConstant(this.buf, value);
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public ASMifier visitAnnotation(String name, String desc) {
        this.buf.setLength(0);
        this.buf.append("{\n");
        this.buf.append("AnnotationVisitor av").append(this.f426id + 1).append(" = av");
        this.buf.append(this.f426id).append(".visitAnnotation(");
        appendConstant(this.buf, name);
        this.buf.append(", ");
        appendConstant(this.buf, desc);
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
        ASMifier a = createASMifier("av", this.f426id + 1);
        this.text.add(a.getText());
        this.text.add("}\n");
        return a;
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public ASMifier visitArray(String name) {
        this.buf.setLength(0);
        this.buf.append("{\n");
        this.buf.append("AnnotationVisitor av").append(this.f426id + 1).append(" = av");
        this.buf.append(this.f426id).append(".visitArray(");
        appendConstant(this.buf, name);
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
        ASMifier a = createASMifier("av", this.f426id + 1);
        this.text.add(a.getText());
        this.text.add("}\n");
        return a;
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitAnnotationEnd() {
        this.buf.setLength(0);
        this.buf.append("av").append(this.f426id).append(".visitEnd();\n");
        this.text.add(this.buf.toString());
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public ASMifier visitFieldAnnotation(String desc, boolean visible) {
        return visitAnnotation(desc, visible);
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public ASMifier visitFieldTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        return visitTypeAnnotation(typeRef, typePath, desc, visible);
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitFieldAttribute(Attribute attr) {
        visitAttribute(attr);
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitFieldEnd() {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitEnd();\n");
        this.text.add(this.buf.toString());
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitParameter(String parameterName, int access) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitParameter(");
        appendString(this.buf, parameterName);
        this.buf.append(", ");
        appendAccess(access);
        this.text.add(this.buf.append(");\n").toString());
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public ASMifier visitAnnotationDefault() {
        this.buf.setLength(0);
        this.buf.append("{\n").append("av0 = ").append(this.name).append(".visitAnnotationDefault();\n");
        this.text.add(this.buf.toString());
        ASMifier a = createASMifier("av", 0);
        this.text.add(a.getText());
        this.text.add("}\n");
        return a;
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public ASMifier visitMethodAnnotation(String desc, boolean visible) {
        return visitAnnotation(desc, visible);
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public ASMifier visitMethodTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        return visitTypeAnnotation(typeRef, typePath, desc, visible);
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public ASMifier visitParameterAnnotation(int parameter, String desc, boolean visible) {
        this.buf.setLength(0);
        this.buf.append("{\n").append("av0 = ").append(this.name).append(".visitParameterAnnotation(").append(parameter).append(", ");
        appendConstant(desc);
        this.buf.append(", ").append(visible).append(");\n");
        this.text.add(this.buf.toString());
        ASMifier a = createASMifier("av", 0);
        this.text.add(a.getText());
        this.text.add("}\n");
        return a;
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitMethodAttribute(Attribute attr) {
        visitAttribute(attr);
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitCode() {
        this.text.add(this.name + ".visitCode();\n");
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
        this.buf.setLength(0);
        switch (type) {
            case -1:
            case 0:
                declareFrameTypes(nLocal, local);
                declareFrameTypes(nStack, stack);
                if (type == -1) {
                    this.buf.append(this.name).append(".visitFrame(Opcodes.F_NEW, ");
                } else {
                    this.buf.append(this.name).append(".visitFrame(Opcodes.F_FULL, ");
                }
                this.buf.append(nLocal).append(", new Object[] {");
                appendFrameTypes(nLocal, local);
                this.buf.append("}, ").append(nStack).append(", new Object[] {");
                appendFrameTypes(nStack, stack);
                this.buf.append('}');
                break;
            case 1:
                declareFrameTypes(nLocal, local);
                this.buf.append(this.name).append(".visitFrame(Opcodes.F_APPEND,").append(nLocal).append(", new Object[] {");
                appendFrameTypes(nLocal, local);
                this.buf.append("}, 0, null");
                break;
            case 2:
                this.buf.append(this.name).append(".visitFrame(Opcodes.F_CHOP,").append(nLocal).append(", null, 0, null");
                break;
            case 3:
                this.buf.append(this.name).append(".visitFrame(Opcodes.F_SAME, 0, null, 0, null");
                break;
            case 4:
                declareFrameTypes(1, stack);
                this.buf.append(this.name).append(".visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[] {");
                appendFrameTypes(1, stack);
                this.buf.append('}');
                break;
        }
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitInsn(int opcode) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitInsn(").append(OPCODES[opcode]).append(");\n");
        this.text.add(this.buf.toString());
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitIntInsn(int opcode, int operand) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitIntInsn(").append(OPCODES[opcode]).append(", ").append(opcode == 188 ? TYPES[operand] : Integer.toString(operand)).append(");\n");
        this.text.add(this.buf.toString());
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitVarInsn(int opcode, int var) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitVarInsn(").append(OPCODES[opcode]).append(", ").append(var).append(");\n");
        this.text.add(this.buf.toString());
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitTypeInsn(int opcode, String type) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitTypeInsn(").append(OPCODES[opcode]).append(", ");
        appendConstant(type);
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitFieldInsn(int opcode, String owner, String name, String desc) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitFieldInsn(").append(OPCODES[opcode]).append(", ");
        appendConstant(owner);
        this.buf.append(", ");
        appendConstant(name);
        this.buf.append(", ");
        appendConstant(desc);
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    @Deprecated
    public void visitMethodInsn(int opcode, String owner, String name, String desc) {
        if (this.api >= 327680) {
            super.visitMethodInsn(opcode, owner, name, desc);
        } else {
            doVisitMethodInsn(opcode, owner, name, desc, opcode == 185);
        }
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        if (this.api < 327680) {
            super.visitMethodInsn(opcode, owner, name, desc, itf);
        } else {
            doVisitMethodInsn(opcode, owner, name, desc, itf);
        }
    }

    private void doVisitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitMethodInsn(").append(OPCODES[opcode]).append(", ");
        appendConstant(owner);
        this.buf.append(", ");
        appendConstant(name);
        this.buf.append(", ");
        appendConstant(desc);
        this.buf.append(", ");
        this.buf.append(itf ? "true" : "false");
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitInvokeDynamicInsn(String name, String desc, Handle bsm, Object... bsmArgs) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitInvokeDynamicInsn(");
        appendConstant(name);
        this.buf.append(", ");
        appendConstant(desc);
        this.buf.append(", ");
        appendConstant(bsm);
        this.buf.append(", new Object[]{");
        for (int i = 0; i < bsmArgs.length; i++) {
            appendConstant(bsmArgs[i]);
            if (i != bsmArgs.length - 1) {
                this.buf.append(", ");
            }
        }
        this.buf.append("});\n");
        this.text.add(this.buf.toString());
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitJumpInsn(int opcode, Label label) {
        this.buf.setLength(0);
        declareLabel(label);
        this.buf.append(this.name).append(".visitJumpInsn(").append(OPCODES[opcode]).append(", ");
        appendLabel(label);
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitLabel(Label label) {
        this.buf.setLength(0);
        declareLabel(label);
        this.buf.append(this.name).append(".visitLabel(");
        appendLabel(label);
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitLdcInsn(Object cst) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitLdcInsn(");
        appendConstant(cst);
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitIincInsn(int var, int increment) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitIincInsn(").append(var).append(", ").append(increment).append(");\n");
        this.text.add(this.buf.toString());
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
        this.buf.setLength(0);
        for (Label label : labels) {
            declareLabel(label);
        }
        declareLabel(dflt);
        this.buf.append(this.name).append(".visitTableSwitchInsn(").append(min).append(", ").append(max).append(", ");
        appendLabel(dflt);
        this.buf.append(", new Label[] {");
        int i = 0;
        while (i < labels.length) {
            this.buf.append(i == 0 ? " " : ", ");
            appendLabel(labels[i]);
            i++;
        }
        this.buf.append(" });\n");
        this.text.add(this.buf.toString());
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
        this.buf.setLength(0);
        for (Label label : labels) {
            declareLabel(label);
        }
        declareLabel(dflt);
        this.buf.append(this.name).append(".visitLookupSwitchInsn(");
        appendLabel(dflt);
        this.buf.append(", new int[] {");
        int i = 0;
        while (i < keys.length) {
            this.buf.append(i == 0 ? " " : ", ").append(keys[i]);
            i++;
        }
        this.buf.append(" }, new Label[] {");
        int i2 = 0;
        while (i2 < labels.length) {
            this.buf.append(i2 == 0 ? " " : ", ");
            appendLabel(labels[i2]);
            i2++;
        }
        this.buf.append(" });\n");
        this.text.add(this.buf.toString());
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitMultiANewArrayInsn(String desc, int dims) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitMultiANewArrayInsn(");
        appendConstant(desc);
        this.buf.append(", ").append(dims).append(");\n");
        this.text.add(this.buf.toString());
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public ASMifier visitInsnAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        return visitTypeAnnotation("visitInsnAnnotation", typeRef, typePath, desc, visible);
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
        this.buf.setLength(0);
        declareLabel(start);
        declareLabel(end);
        declareLabel(handler);
        this.buf.append(this.name).append(".visitTryCatchBlock(");
        appendLabel(start);
        this.buf.append(", ");
        appendLabel(end);
        this.buf.append(", ");
        appendLabel(handler);
        this.buf.append(", ");
        appendConstant(type);
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public ASMifier visitTryCatchAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        return visitTypeAnnotation("visitTryCatchAnnotation", typeRef, typePath, desc, visible);
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitLocalVariable(");
        appendConstant(name);
        this.buf.append(", ");
        appendConstant(desc);
        this.buf.append(", ");
        appendConstant(signature);
        this.buf.append(", ");
        appendLabel(start);
        this.buf.append(", ");
        appendLabel(end);
        this.buf.append(", ").append(index).append(");\n");
        this.text.add(this.buf.toString());
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public Printer visitLocalVariableAnnotation(int typeRef, TypePath typePath, Label[] start, Label[] end, int[] index, String desc, boolean visible) {
        this.buf.setLength(0);
        this.buf.append("{\n").append("av0 = ").append(this.name).append(".visitLocalVariableAnnotation(");
        this.buf.append(typeRef);
        if (typePath == null) {
            this.buf.append(", null, ");
        } else {
            this.buf.append(", TypePath.fromString(\"").append(typePath).append("\"), ");
        }
        this.buf.append("new Label[] {");
        int i = 0;
        while (i < start.length) {
            this.buf.append(i == 0 ? " " : ", ");
            appendLabel(start[i]);
            i++;
        }
        this.buf.append(" }, new Label[] {");
        int i2 = 0;
        while (i2 < end.length) {
            this.buf.append(i2 == 0 ? " " : ", ");
            appendLabel(end[i2]);
            i2++;
        }
        this.buf.append(" }, new int[] {");
        int i3 = 0;
        while (i3 < index.length) {
            this.buf.append(i3 == 0 ? " " : ", ").append(index[i3]);
            i3++;
        }
        this.buf.append(" }, ");
        appendConstant(desc);
        this.buf.append(", ").append(visible).append(");\n");
        this.text.add(this.buf.toString());
        ASMifier a = createASMifier("av", 0);
        this.text.add(a.getText());
        this.text.add("}\n");
        return a;
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitLineNumber(int line, Label start) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitLineNumber(").append(line).append(", ");
        appendLabel(start);
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitMaxs(int maxStack, int maxLocals) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitMaxs(").append(maxStack).append(", ").append(maxLocals).append(");\n");
        this.text.add(this.buf.toString());
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitMethodEnd() {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitEnd();\n");
        this.text.add(this.buf.toString());
    }

    public ASMifier visitAnnotation(String desc, boolean visible) {
        this.buf.setLength(0);
        this.buf.append("{\n").append("av0 = ").append(this.name).append(".visitAnnotation(");
        appendConstant(desc);
        this.buf.append(", ").append(visible).append(");\n");
        this.text.add(this.buf.toString());
        ASMifier a = createASMifier("av", 0);
        this.text.add(a.getText());
        this.text.add("}\n");
        return a;
    }

    public ASMifier visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        return visitTypeAnnotation("visitTypeAnnotation", typeRef, typePath, desc, visible);
    }

    public ASMifier visitTypeAnnotation(String method, int typeRef, TypePath typePath, String desc, boolean visible) {
        this.buf.setLength(0);
        this.buf.append("{\n").append("av0 = ").append(this.name).append(".").append(method).append("(");
        this.buf.append(typeRef);
        if (typePath == null) {
            this.buf.append(", null, ");
        } else {
            this.buf.append(", TypePath.fromString(\"").append(typePath).append("\"), ");
        }
        appendConstant(desc);
        this.buf.append(", ").append(visible).append(");\n");
        this.text.add(this.buf.toString());
        ASMifier a = createASMifier("av", 0);
        this.text.add(a.getText());
        this.text.add("}\n");
        return a;
    }

    public void visitAttribute(Attribute attr) {
        this.buf.setLength(0);
        this.buf.append("// ATTRIBUTE ").append(attr.type).append('\n');
        if (attr instanceof ASMifiable) {
            if (this.labelNames == null) {
                this.labelNames = new HashMap();
            }
            this.buf.append("{\n");
            ((ASMifiable) attr).asmify(this.buf, "attr", this.labelNames);
            this.buf.append(this.name).append(".visitAttribute(attr);\n");
            this.buf.append("}\n");
        }
        this.text.add(this.buf.toString());
    }

    protected ASMifier createASMifier(String name, int id) {
        return new ASMifier(Opcodes.ASM5, name, id);
    }

    void appendAccess(int access) {
        boolean first = true;
        if ((access & 1) != 0) {
            this.buf.append("ACC_PUBLIC");
            first = false;
        }
        if ((access & 2) != 0) {
            this.buf.append("ACC_PRIVATE");
            first = false;
        }
        if ((access & 4) != 0) {
            this.buf.append("ACC_PROTECTED");
            first = false;
        }
        if ((access & 16) != 0) {
            if (!first) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_FINAL");
            first = false;
        }
        if ((access & 8) != 0) {
            if (!first) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_STATIC");
            first = false;
        }
        if ((access & 32) != 0) {
            if (!first) {
                this.buf.append(" + ");
            }
            if ((access & 262144) == 0) {
                this.buf.append("ACC_SYNCHRONIZED");
            } else {
                this.buf.append("ACC_SUPER");
            }
            first = false;
        }
        if ((access & 64) != 0 && (access & 524288) != 0) {
            if (!first) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_VOLATILE");
            first = false;
        }
        if ((access & 64) != 0 && (access & 262144) == 0 && (access & 524288) == 0) {
            if (!first) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_BRIDGE");
            first = false;
        }
        if ((access & 128) != 0 && (access & 262144) == 0 && (access & 524288) == 0) {
            if (!first) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_VARARGS");
            first = false;
        }
        if ((access & 128) != 0 && (access & 524288) != 0) {
            if (!first) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_TRANSIENT");
            first = false;
        }
        if ((access & 256) != 0 && (access & 262144) == 0 && (access & 524288) == 0) {
            if (!first) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_NATIVE");
            first = false;
        }
        if ((access & 16384) != 0 && ((access & 262144) != 0 || (access & 524288) != 0 || (access & 1048576) != 0)) {
            if (!first) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_ENUM");
            first = false;
        }
        if ((access & 8192) != 0 && ((access & 262144) != 0 || (access & 1048576) != 0)) {
            if (!first) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_ANNOTATION");
            first = false;
        }
        if ((access & 1024) != 0) {
            if (!first) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_ABSTRACT");
            first = false;
        }
        if ((access & 512) != 0) {
            if (!first) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_INTERFACE");
            first = false;
        }
        if ((access & 2048) != 0) {
            if (!first) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_STRICT");
            first = false;
        }
        if ((access & 4096) != 0) {
            if (!first) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_SYNTHETIC");
            first = false;
        }
        if ((access & 131072) != 0) {
            if (!first) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_DEPRECATED");
            first = false;
        }
        if ((access & 32768) != 0) {
            if (!first) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_MANDATED");
            first = false;
        }
        if (first) {
            this.buf.append('0');
        }
    }

    protected void appendConstant(Object cst) {
        appendConstant(this.buf, cst);
    }

    static void appendConstant(StringBuffer buf, Object cst) {
        if (cst == null) {
            buf.append(Configurator.NULL);
        } else if (cst instanceof String) {
            appendString(buf, (String) cst);
        } else if (cst instanceof Type) {
            buf.append("Type.getType(\"");
            buf.append(((Type) cst).getDescriptor());
            buf.append("\")");
        } else if (cst instanceof Handle) {
            buf.append("new Handle(");
            Handle h = (Handle) cst;
            buf.append("Opcodes.").append(HANDLE_TAG[h.getTag()]).append(", \"");
            buf.append(h.getOwner()).append("\", \"");
            buf.append(h.getName()).append("\", \"");
            buf.append(h.getDesc()).append("\")");
        } else if (cst instanceof Byte) {
            buf.append("new Byte((byte)").append(cst).append(')');
        } else if (cst instanceof Boolean) {
            buf.append(((Boolean) cst).booleanValue() ? "Boolean.TRUE" : "Boolean.FALSE");
        } else if (cst instanceof Short) {
            buf.append("new Short((short)").append(cst).append(')');
        } else if (cst instanceof Character) {
            int c = ((Character) cst).charValue();
            buf.append("new Character((char)").append(c).append(')');
        } else if (cst instanceof Integer) {
            buf.append("new Integer(").append(cst).append(')');
        } else if (cst instanceof Float) {
            buf.append("new Float(\"").append(cst).append("\")");
        } else if (cst instanceof Long) {
            buf.append("new Long(").append(cst).append("L)");
        } else if (cst instanceof Double) {
            buf.append("new Double(\"").append(cst).append("\")");
        } else if (cst instanceof byte[]) {
            byte[] v = (byte[]) cst;
            buf.append("new byte[] {");
            int i = 0;
            while (i < v.length) {
                buf.append(i == 0 ? "" : ",").append((int) v[i]);
                i++;
            }
            buf.append('}');
        } else if (cst instanceof boolean[]) {
            boolean[] v2 = (boolean[]) cst;
            buf.append("new boolean[] {");
            int i2 = 0;
            while (i2 < v2.length) {
                buf.append(i2 == 0 ? "" : ",").append(v2[i2]);
                i2++;
            }
            buf.append('}');
        } else if (cst instanceof short[]) {
            short[] v3 = (short[]) cst;
            buf.append("new short[] {");
            int i3 = 0;
            while (i3 < v3.length) {
                buf.append(i3 == 0 ? "" : ",").append("(short)").append((int) v3[i3]);
                i3++;
            }
            buf.append('}');
        } else if (cst instanceof char[]) {
            char[] v4 = (char[]) cst;
            buf.append("new char[] {");
            int i4 = 0;
            while (i4 < v4.length) {
                buf.append(i4 == 0 ? "" : ",").append("(char)").append((int) v4[i4]);
                i4++;
            }
            buf.append('}');
        } else if (cst instanceof int[]) {
            int[] v5 = (int[]) cst;
            buf.append("new int[] {");
            int i5 = 0;
            while (i5 < v5.length) {
                buf.append(i5 == 0 ? "" : ",").append(v5[i5]);
                i5++;
            }
            buf.append('}');
        } else if (cst instanceof long[]) {
            long[] v6 = (long[]) cst;
            buf.append("new long[] {");
            int i6 = 0;
            while (i6 < v6.length) {
                buf.append(i6 == 0 ? "" : ",").append(v6[i6]).append('L');
                i6++;
            }
            buf.append('}');
        } else if (cst instanceof float[]) {
            float[] v7 = (float[]) cst;
            buf.append("new float[] {");
            int i7 = 0;
            while (i7 < v7.length) {
                buf.append(i7 == 0 ? "" : ",").append(v7[i7]).append('f');
                i7++;
            }
            buf.append('}');
        } else if (cst instanceof double[]) {
            double[] v8 = (double[]) cst;
            buf.append("new double[] {");
            int i8 = 0;
            while (i8 < v8.length) {
                buf.append(i8 == 0 ? "" : ",").append(v8[i8]).append('d');
                i8++;
            }
            buf.append('}');
        }
    }

    private void declareFrameTypes(int n, Object[] o) {
        for (int i = 0; i < n; i++) {
            if (o[i] instanceof Label) {
                declareLabel((Label) o[i]);
            }
        }
    }

    private void appendFrameTypes(int n, Object[] o) {
        for (int i = 0; i < n; i++) {
            if (i > 0) {
                this.buf.append(", ");
            }
            if (o[i] instanceof String) {
                appendConstant(o[i]);
            } else if (o[i] instanceof Integer) {
                switch (((Integer) o[i]).intValue()) {
                    case 0:
                        this.buf.append("Opcodes.TOP");
                        continue;
                    case 1:
                        this.buf.append("Opcodes.INTEGER");
                        continue;
                    case 2:
                        this.buf.append("Opcodes.FLOAT");
                        continue;
                    case 3:
                        this.buf.append("Opcodes.DOUBLE");
                        continue;
                    case 4:
                        this.buf.append("Opcodes.LONG");
                        continue;
                    case 5:
                        this.buf.append("Opcodes.NULL");
                        continue;
                    case 6:
                        this.buf.append("Opcodes.UNINITIALIZED_THIS");
                        continue;
                }
            } else {
                appendLabel((Label) o[i]);
            }
        }
    }

    protected void declareLabel(Label l) {
        if (this.labelNames == null) {
            this.labelNames = new HashMap();
        }
        if (this.labelNames.get(l) == null) {
            String name = "l" + this.labelNames.size();
            this.labelNames.put(l, name);
            this.buf.append("Label ").append(name).append(" = new Label();\n");
        }
    }

    protected void appendLabel(Label l) {
        this.buf.append(this.labelNames.get(l));
    }
}
