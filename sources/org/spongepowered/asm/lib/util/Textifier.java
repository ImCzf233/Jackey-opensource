package org.spongepowered.asm.lib.util;

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import kotlin.jvm.internal.CharCompanionObject;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.ClassReader;
import org.spongepowered.asm.lib.Handle;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.TypePath;
import org.spongepowered.asm.lib.TypeReference;
import org.spongepowered.asm.lib.signature.SignatureReader;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/util/Textifier.class */
public class Textifier extends Printer {
    public static final int INTERNAL_NAME = 0;
    public static final int FIELD_DESCRIPTOR = 1;
    public static final int FIELD_SIGNATURE = 2;
    public static final int METHOD_DESCRIPTOR = 3;
    public static final int METHOD_SIGNATURE = 4;
    public static final int CLASS_SIGNATURE = 5;
    public static final int TYPE_DECLARATION = 6;
    public static final int CLASS_DECLARATION = 7;
    public static final int PARAMETERS_DECLARATION = 8;
    public static final int HANDLE_DESCRIPTOR = 9;
    protected String tab;
    protected String tab2;
    protected String tab3;
    protected String ltab;
    protected Map<Label, String> labelNames;
    private int access;
    private int valueNumber;

    public Textifier() {
        this(Opcodes.ASM5);
        if (getClass() != Textifier.class) {
            throw new IllegalStateException();
        }
    }

    protected Textifier(int api) {
        super(api);
        this.tab = "  ";
        this.tab2 = "    ";
        this.tab3 = "      ";
        this.ltab = "   ";
        this.valueNumber = 0;
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
            System.err.println("Prints a disassembled view of the given class.");
            System.err.println("Usage: Textifier [-debug] <fully qualified class name or class file name>");
            return;
        }
        if (args[i].endsWith(".class") || args[i].indexOf(92) > -1 || args[i].indexOf(47) > -1) {
            cr = new ClassReader(new FileInputStream(args[i]));
        } else {
            cr = new ClassReader(args[i]);
        }
        cr.accept(new TraceClassVisitor(new PrintWriter(System.out)), flags);
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.access = access;
        int major = version & CharCompanionObject.MAX_VALUE;
        int minor = version >>> 16;
        this.buf.setLength(0);
        this.buf.append("// class version ").append(major).append('.').append(minor).append(" (").append(version).append(")\n");
        if ((access & 131072) != 0) {
            this.buf.append("// DEPRECATED\n");
        }
        this.buf.append("// access flags 0x").append(Integer.toHexString(access).toUpperCase()).append('\n');
        appendDescriptor(5, signature);
        if (signature != null) {
            TraceSignatureVisitor sv = new TraceSignatureVisitor(access);
            SignatureReader r = new SignatureReader(signature);
            r.accept(sv);
            this.buf.append("// declaration: ").append(name).append(sv.getDeclaration()).append('\n');
        }
        appendAccess(access & (-33));
        if ((access & 8192) != 0) {
            this.buf.append("@interface ");
        } else if ((access & 512) != 0) {
            this.buf.append("interface ");
        } else if ((access & 16384) == 0) {
            this.buf.append("class ");
        }
        appendDescriptor(0, name);
        if (superName != null && !"java/lang/Object".equals(superName)) {
            this.buf.append(" extends ");
            appendDescriptor(0, superName);
            this.buf.append(' ');
        }
        if (interfaces != null && interfaces.length > 0) {
            this.buf.append(" implements ");
            for (String str : interfaces) {
                appendDescriptor(0, str);
                this.buf.append(' ');
            }
        }
        this.buf.append(" {\n\n");
        this.text.add(this.buf.toString());
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitSource(String file, String debug) {
        this.buf.setLength(0);
        if (file != null) {
            this.buf.append(this.tab).append("// compiled from: ").append(file).append('\n');
        }
        if (debug != null) {
            this.buf.append(this.tab).append("// debug info: ").append(debug).append('\n');
        }
        if (this.buf.length() > 0) {
            this.text.add(this.buf.toString());
        }
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitOuterClass(String owner, String name, String desc) {
        this.buf.setLength(0);
        this.buf.append(this.tab).append("OUTERCLASS ");
        appendDescriptor(0, owner);
        this.buf.append(' ');
        if (name != null) {
            this.buf.append(name).append(' ');
        }
        appendDescriptor(3, desc);
        this.buf.append('\n');
        this.text.add(this.buf.toString());
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public Textifier visitClassAnnotation(String desc, boolean visible) {
        this.text.add("\n");
        return visitAnnotation(desc, visible);
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public Printer visitClassTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        this.text.add("\n");
        return visitTypeAnnotation(typeRef, typePath, desc, visible);
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitClassAttribute(Attribute attr) {
        this.text.add("\n");
        visitAttribute(attr);
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitInnerClass(String name, String outerName, String innerName, int access) {
        this.buf.setLength(0);
        this.buf.append(this.tab).append("// access flags 0x");
        this.buf.append(Integer.toHexString(access & (-33)).toUpperCase()).append('\n');
        this.buf.append(this.tab);
        appendAccess(access);
        this.buf.append("INNERCLASS ");
        appendDescriptor(0, name);
        this.buf.append(' ');
        appendDescriptor(0, outerName);
        this.buf.append(' ');
        appendDescriptor(0, innerName);
        this.buf.append('\n');
        this.text.add(this.buf.toString());
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public Textifier visitField(int access, String name, String desc, String signature, Object value) {
        this.buf.setLength(0);
        this.buf.append('\n');
        if ((access & 131072) != 0) {
            this.buf.append(this.tab).append("// DEPRECATED\n");
        }
        this.buf.append(this.tab).append("// access flags 0x").append(Integer.toHexString(access).toUpperCase()).append('\n');
        if (signature != null) {
            this.buf.append(this.tab);
            appendDescriptor(2, signature);
            TraceSignatureVisitor sv = new TraceSignatureVisitor(0);
            SignatureReader r = new SignatureReader(signature);
            r.acceptType(sv);
            this.buf.append(this.tab).append("// declaration: ").append(sv.getDeclaration()).append('\n');
        }
        this.buf.append(this.tab);
        appendAccess(access);
        appendDescriptor(1, desc);
        this.buf.append(' ').append(name);
        if (value != null) {
            this.buf.append(" = ");
            if (value instanceof String) {
                this.buf.append('\"').append(value).append('\"');
            } else {
                this.buf.append(value);
            }
        }
        this.buf.append('\n');
        this.text.add(this.buf.toString());
        Textifier t = createTextifier();
        this.text.add(t.getText());
        return t;
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public Textifier visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        this.buf.setLength(0);
        this.buf.append('\n');
        if ((access & 131072) != 0) {
            this.buf.append(this.tab).append("// DEPRECATED\n");
        }
        this.buf.append(this.tab).append("// access flags 0x").append(Integer.toHexString(access).toUpperCase()).append('\n');
        if (signature != null) {
            this.buf.append(this.tab);
            appendDescriptor(4, signature);
            TraceSignatureVisitor v = new TraceSignatureVisitor(0);
            SignatureReader r = new SignatureReader(signature);
            r.accept(v);
            String genericDecl = v.getDeclaration();
            String genericReturn = v.getReturnType();
            String genericExceptions = v.getExceptions();
            this.buf.append(this.tab).append("// declaration: ").append(genericReturn).append(' ').append(name).append(genericDecl);
            if (genericExceptions != null) {
                this.buf.append(" throws ").append(genericExceptions);
            }
            this.buf.append('\n');
        }
        this.buf.append(this.tab);
        appendAccess(access & (-65));
        if ((access & 256) != 0) {
            this.buf.append("native ");
        }
        if ((access & 128) != 0) {
            this.buf.append("varargs ");
        }
        if ((access & 64) != 0) {
            this.buf.append("bridge ");
        }
        if ((this.access & 512) != 0 && (access & 1024) == 0 && (access & 8) == 0) {
            this.buf.append("default ");
        }
        this.buf.append(name);
        appendDescriptor(3, desc);
        if (exceptions != null && exceptions.length > 0) {
            this.buf.append(" throws ");
            for (String str : exceptions) {
                appendDescriptor(0, str);
                this.buf.append(' ');
            }
        }
        this.buf.append('\n');
        this.text.add(this.buf.toString());
        Textifier t = createTextifier();
        this.text.add(t.getText());
        return t;
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitClassEnd() {
        this.text.add("}\n");
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visit(String name, Object value) {
        this.buf.setLength(0);
        int i = this.valueNumber;
        this.valueNumber = i + 1;
        appendComa(i);
        if (name != null) {
            this.buf.append(name).append('=');
        }
        if (value instanceof String) {
            visitString((String) value);
        } else if (value instanceof Type) {
            visitType((Type) value);
        } else if (value instanceof Byte) {
            visitByte(((Byte) value).byteValue());
        } else if (value instanceof Boolean) {
            visitBoolean(((Boolean) value).booleanValue());
        } else if (value instanceof Short) {
            visitShort(((Short) value).shortValue());
        } else if (value instanceof Character) {
            visitChar(((Character) value).charValue());
        } else if (value instanceof Integer) {
            visitInt(((Integer) value).intValue());
        } else if (value instanceof Float) {
            visitFloat(((Float) value).floatValue());
        } else if (value instanceof Long) {
            visitLong(((Long) value).longValue());
        } else if (value instanceof Double) {
            visitDouble(((Double) value).doubleValue());
        } else if (value.getClass().isArray()) {
            this.buf.append('{');
            if (value instanceof byte[]) {
                byte[] v = (byte[]) value;
                for (int i2 = 0; i2 < v.length; i2++) {
                    appendComa(i2);
                    visitByte(v[i2]);
                }
            } else if (value instanceof boolean[]) {
                boolean[] v2 = (boolean[]) value;
                for (int i3 = 0; i3 < v2.length; i3++) {
                    appendComa(i3);
                    visitBoolean(v2[i3]);
                }
            } else if (value instanceof short[]) {
                short[] v3 = (short[]) value;
                for (int i4 = 0; i4 < v3.length; i4++) {
                    appendComa(i4);
                    visitShort(v3[i4]);
                }
            } else if (value instanceof char[]) {
                char[] v4 = (char[]) value;
                for (int i5 = 0; i5 < v4.length; i5++) {
                    appendComa(i5);
                    visitChar(v4[i5]);
                }
            } else if (value instanceof int[]) {
                int[] v5 = (int[]) value;
                for (int i6 = 0; i6 < v5.length; i6++) {
                    appendComa(i6);
                    visitInt(v5[i6]);
                }
            } else if (value instanceof long[]) {
                long[] v6 = (long[]) value;
                for (int i7 = 0; i7 < v6.length; i7++) {
                    appendComa(i7);
                    visitLong(v6[i7]);
                }
            } else if (value instanceof float[]) {
                float[] v7 = (float[]) value;
                for (int i8 = 0; i8 < v7.length; i8++) {
                    appendComa(i8);
                    visitFloat(v7[i8]);
                }
            } else if (value instanceof double[]) {
                double[] v8 = (double[]) value;
                for (int i9 = 0; i9 < v8.length; i9++) {
                    appendComa(i9);
                    visitDouble(v8[i9]);
                }
            }
            this.buf.append('}');
        }
        this.text.add(this.buf.toString());
    }

    private void visitInt(int value) {
        this.buf.append(value);
    }

    private void visitLong(long value) {
        this.buf.append(value).append('L');
    }

    private void visitFloat(float value) {
        this.buf.append(value).append('F');
    }

    private void visitDouble(double value) {
        this.buf.append(value).append('D');
    }

    private void visitChar(char value) {
        this.buf.append("(char)").append((int) value);
    }

    private void visitShort(short value) {
        this.buf.append("(short)").append((int) value);
    }

    private void visitByte(byte value) {
        this.buf.append("(byte)").append((int) value);
    }

    private void visitBoolean(boolean value) {
        this.buf.append(value);
    }

    private void visitString(String value) {
        appendString(this.buf, value);
    }

    private void visitType(Type value) {
        this.buf.append(value.getClassName()).append(".class");
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitEnum(String name, String desc, String value) {
        this.buf.setLength(0);
        int i = this.valueNumber;
        this.valueNumber = i + 1;
        appendComa(i);
        if (name != null) {
            this.buf.append(name).append('=');
        }
        appendDescriptor(1, desc);
        this.buf.append('.').append(value);
        this.text.add(this.buf.toString());
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public Textifier visitAnnotation(String name, String desc) {
        this.buf.setLength(0);
        int i = this.valueNumber;
        this.valueNumber = i + 1;
        appendComa(i);
        if (name != null) {
            this.buf.append(name).append('=');
        }
        this.buf.append('@');
        appendDescriptor(1, desc);
        this.buf.append('(');
        this.text.add(this.buf.toString());
        Textifier t = createTextifier();
        this.text.add(t.getText());
        this.text.add(")");
        return t;
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public Textifier visitArray(String name) {
        this.buf.setLength(0);
        int i = this.valueNumber;
        this.valueNumber = i + 1;
        appendComa(i);
        if (name != null) {
            this.buf.append(name).append('=');
        }
        this.buf.append('{');
        this.text.add(this.buf.toString());
        Textifier t = createTextifier();
        this.text.add(t.getText());
        this.text.add("}");
        return t;
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitAnnotationEnd() {
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public Textifier visitFieldAnnotation(String desc, boolean visible) {
        return visitAnnotation(desc, visible);
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public Printer visitFieldTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        return visitTypeAnnotation(typeRef, typePath, desc, visible);
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitFieldAttribute(Attribute attr) {
        visitAttribute(attr);
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitFieldEnd() {
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitParameter(String name, int access) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("// parameter ");
        appendAccess(access);
        this.buf.append(' ').append(name == null ? "<no name>" : name).append('\n');
        this.text.add(this.buf.toString());
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public Textifier visitAnnotationDefault() {
        this.text.add(this.tab2 + "default=");
        Textifier t = createTextifier();
        this.text.add(t.getText());
        this.text.add("\n");
        return t;
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public Textifier visitMethodAnnotation(String desc, boolean visible) {
        return visitAnnotation(desc, visible);
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public Printer visitMethodTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        return visitTypeAnnotation(typeRef, typePath, desc, visible);
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public Textifier visitParameterAnnotation(int parameter, String desc, boolean visible) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append('@');
        appendDescriptor(1, desc);
        this.buf.append('(');
        this.text.add(this.buf.toString());
        Textifier t = createTextifier();
        this.text.add(t.getText());
        this.text.add(visible ? ") // parameter " : ") // invisible, parameter ");
        this.text.add(Integer.valueOf(parameter));
        this.text.add("\n");
        return t;
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitMethodAttribute(Attribute attr) {
        this.buf.setLength(0);
        this.buf.append(this.tab).append("ATTRIBUTE ");
        appendDescriptor(-1, attr.type);
        if (attr instanceof Textifiable) {
            ((Textifiable) attr).textify(this.buf, this.labelNames);
        } else {
            this.buf.append(" : unknown\n");
        }
        this.text.add(this.buf.toString());
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitCode() {
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
        this.buf.setLength(0);
        this.buf.append(this.ltab);
        this.buf.append("FRAME ");
        switch (type) {
            case -1:
            case 0:
                this.buf.append("FULL [");
                appendFrameTypes(nLocal, local);
                this.buf.append("] [");
                appendFrameTypes(nStack, stack);
                this.buf.append(']');
                break;
            case 1:
                this.buf.append("APPEND [");
                appendFrameTypes(nLocal, local);
                this.buf.append(']');
                break;
            case 2:
                this.buf.append("CHOP ").append(nLocal);
                break;
            case 3:
                this.buf.append("SAME");
                break;
            case 4:
                this.buf.append("SAME1 ");
                appendFrameTypes(1, stack);
                break;
        }
        this.buf.append('\n');
        this.text.add(this.buf.toString());
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitInsn(int opcode) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append(OPCODES[opcode]).append('\n');
        this.text.add(this.buf.toString());
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitIntInsn(int opcode, int operand) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append(OPCODES[opcode]).append(' ').append(opcode == 188 ? TYPES[operand] : Integer.toString(operand)).append('\n');
        this.text.add(this.buf.toString());
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitVarInsn(int opcode, int var) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append(OPCODES[opcode]).append(' ').append(var).append('\n');
        this.text.add(this.buf.toString());
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitTypeInsn(int opcode, String type) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append(OPCODES[opcode]).append(' ');
        appendDescriptor(0, type);
        this.buf.append('\n');
        this.text.add(this.buf.toString());
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitFieldInsn(int opcode, String owner, String name, String desc) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append(OPCODES[opcode]).append(' ');
        appendDescriptor(0, owner);
        this.buf.append('.').append(name).append(" : ");
        appendDescriptor(1, desc);
        this.buf.append('\n');
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
        this.buf.append(this.tab2).append(OPCODES[opcode]).append(' ');
        appendDescriptor(0, owner);
        this.buf.append('.').append(name).append(' ');
        appendDescriptor(3, desc);
        this.buf.append('\n');
        this.text.add(this.buf.toString());
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitInvokeDynamicInsn(String name, String desc, Handle bsm, Object... bsmArgs) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("INVOKEDYNAMIC").append(' ');
        this.buf.append(name);
        appendDescriptor(3, desc);
        this.buf.append(" [");
        this.buf.append('\n');
        this.buf.append(this.tab3);
        appendHandle(bsm);
        this.buf.append('\n');
        this.buf.append(this.tab3).append("// arguments:");
        if (bsmArgs.length == 0) {
            this.buf.append(" none");
        } else {
            this.buf.append('\n');
            for (Object cst : bsmArgs) {
                this.buf.append(this.tab3);
                if (cst instanceof String) {
                    Printer.appendString(this.buf, (String) cst);
                } else if (cst instanceof Type) {
                    Type type = (Type) cst;
                    if (type.getSort() == 11) {
                        appendDescriptor(3, type.getDescriptor());
                    } else {
                        this.buf.append(type.getDescriptor()).append(".class");
                    }
                } else if (cst instanceof Handle) {
                    appendHandle((Handle) cst);
                } else {
                    this.buf.append(cst);
                }
                this.buf.append(", \n");
            }
            this.buf.setLength(this.buf.length() - 3);
        }
        this.buf.append('\n');
        this.buf.append(this.tab2).append("]\n");
        this.text.add(this.buf.toString());
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitJumpInsn(int opcode, Label label) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append(OPCODES[opcode]).append(' ');
        appendLabel(label);
        this.buf.append('\n');
        this.text.add(this.buf.toString());
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitLabel(Label label) {
        this.buf.setLength(0);
        this.buf.append(this.ltab);
        appendLabel(label);
        this.buf.append('\n');
        this.text.add(this.buf.toString());
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitLdcInsn(Object cst) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("LDC ");
        if (cst instanceof String) {
            Printer.appendString(this.buf, (String) cst);
        } else if (cst instanceof Type) {
            this.buf.append(((Type) cst).getDescriptor()).append(".class");
        } else {
            this.buf.append(cst);
        }
        this.buf.append('\n');
        this.text.add(this.buf.toString());
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitIincInsn(int var, int increment) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("IINC ").append(var).append(' ').append(increment).append('\n');
        this.text.add(this.buf.toString());
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("TABLESWITCH\n");
        for (int i = 0; i < labels.length; i++) {
            this.buf.append(this.tab3).append(min + i).append(": ");
            appendLabel(labels[i]);
            this.buf.append('\n');
        }
        this.buf.append(this.tab3).append("default: ");
        appendLabel(dflt);
        this.buf.append('\n');
        this.text.add(this.buf.toString());
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("LOOKUPSWITCH\n");
        for (int i = 0; i < labels.length; i++) {
            this.buf.append(this.tab3).append(keys[i]).append(": ");
            appendLabel(labels[i]);
            this.buf.append('\n');
        }
        this.buf.append(this.tab3).append("default: ");
        appendLabel(dflt);
        this.buf.append('\n');
        this.text.add(this.buf.toString());
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitMultiANewArrayInsn(String desc, int dims) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("MULTIANEWARRAY ");
        appendDescriptor(1, desc);
        this.buf.append(' ').append(dims).append('\n');
        this.text.add(this.buf.toString());
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public Printer visitInsnAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        return visitTypeAnnotation(typeRef, typePath, desc, visible);
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("TRYCATCHBLOCK ");
        appendLabel(start);
        this.buf.append(' ');
        appendLabel(end);
        this.buf.append(' ');
        appendLabel(handler);
        this.buf.append(' ');
        appendDescriptor(0, type);
        this.buf.append('\n');
        this.text.add(this.buf.toString());
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public Printer visitTryCatchAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("TRYCATCHBLOCK @");
        appendDescriptor(1, desc);
        this.buf.append('(');
        this.text.add(this.buf.toString());
        Textifier t = createTextifier();
        this.text.add(t.getText());
        this.buf.setLength(0);
        this.buf.append(") : ");
        appendTypeReference(typeRef);
        this.buf.append(", ").append(typePath);
        this.buf.append(visible ? "\n" : " // invisible\n");
        this.text.add(this.buf.toString());
        return t;
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("LOCALVARIABLE ").append(name).append(' ');
        appendDescriptor(1, desc);
        this.buf.append(' ');
        appendLabel(start);
        this.buf.append(' ');
        appendLabel(end);
        this.buf.append(' ').append(index).append('\n');
        if (signature != null) {
            this.buf.append(this.tab2);
            appendDescriptor(2, signature);
            TraceSignatureVisitor sv = new TraceSignatureVisitor(0);
            SignatureReader r = new SignatureReader(signature);
            r.acceptType(sv);
            this.buf.append(this.tab2).append("// declaration: ").append(sv.getDeclaration()).append('\n');
        }
        this.text.add(this.buf.toString());
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public Printer visitLocalVariableAnnotation(int typeRef, TypePath typePath, Label[] start, Label[] end, int[] index, String desc, boolean visible) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("LOCALVARIABLE @");
        appendDescriptor(1, desc);
        this.buf.append('(');
        this.text.add(this.buf.toString());
        Textifier t = createTextifier();
        this.text.add(t.getText());
        this.buf.setLength(0);
        this.buf.append(") : ");
        appendTypeReference(typeRef);
        this.buf.append(", ").append(typePath);
        for (int i = 0; i < start.length; i++) {
            this.buf.append(" [ ");
            appendLabel(start[i]);
            this.buf.append(" - ");
            appendLabel(end[i]);
            this.buf.append(" - ").append(index[i]).append(" ]");
        }
        this.buf.append(visible ? "\n" : " // invisible\n");
        this.text.add(this.buf.toString());
        return t;
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitLineNumber(int line, Label start) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("LINENUMBER ").append(line).append(' ');
        appendLabel(start);
        this.buf.append('\n');
        this.text.add(this.buf.toString());
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitMaxs(int maxStack, int maxLocals) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("MAXSTACK = ").append(maxStack).append('\n');
        this.text.add(this.buf.toString());
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("MAXLOCALS = ").append(maxLocals).append('\n');
        this.text.add(this.buf.toString());
    }

    @Override // org.spongepowered.asm.lib.util.Printer
    public void visitMethodEnd() {
    }

    public Textifier visitAnnotation(String desc, boolean visible) {
        this.buf.setLength(0);
        this.buf.append(this.tab).append('@');
        appendDescriptor(1, desc);
        this.buf.append('(');
        this.text.add(this.buf.toString());
        Textifier t = createTextifier();
        this.text.add(t.getText());
        this.text.add(visible ? ")\n" : ") // invisible\n");
        return t;
    }

    public Textifier visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        this.buf.setLength(0);
        this.buf.append(this.tab).append('@');
        appendDescriptor(1, desc);
        this.buf.append('(');
        this.text.add(this.buf.toString());
        Textifier t = createTextifier();
        this.text.add(t.getText());
        this.buf.setLength(0);
        this.buf.append(") : ");
        appendTypeReference(typeRef);
        this.buf.append(", ").append(typePath);
        this.buf.append(visible ? "\n" : " // invisible\n");
        this.text.add(this.buf.toString());
        return t;
    }

    public void visitAttribute(Attribute attr) {
        this.buf.setLength(0);
        this.buf.append(this.tab).append("ATTRIBUTE ");
        appendDescriptor(-1, attr.type);
        if (attr instanceof Textifiable) {
            ((Textifiable) attr).textify(this.buf, null);
        } else {
            this.buf.append(" : unknown\n");
        }
        this.text.add(this.buf.toString());
    }

    protected Textifier createTextifier() {
        return new Textifier();
    }

    protected void appendDescriptor(int type, String desc) {
        if (type == 5 || type == 2 || type == 4) {
            if (desc != null) {
                this.buf.append("// signature ").append(desc).append('\n');
                return;
            }
            return;
        }
        this.buf.append(desc);
    }

    protected void appendLabel(Label l) {
        if (this.labelNames == null) {
            this.labelNames = new HashMap();
        }
        String name = this.labelNames.get(l);
        if (name == null) {
            name = "L" + this.labelNames.size();
            this.labelNames.put(l, name);
        }
        this.buf.append(name);
    }

    protected void appendHandle(Handle h) {
        int tag = h.getTag();
        this.buf.append("// handle kind 0x").append(Integer.toHexString(tag)).append(" : ");
        boolean isMethodHandle = false;
        switch (tag) {
            case 1:
                this.buf.append("GETFIELD");
                break;
            case 2:
                this.buf.append("GETSTATIC");
                break;
            case 3:
                this.buf.append("PUTFIELD");
                break;
            case 4:
                this.buf.append("PUTSTATIC");
                break;
            case 5:
                this.buf.append("INVOKEVIRTUAL");
                isMethodHandle = true;
                break;
            case 6:
                this.buf.append("INVOKESTATIC");
                isMethodHandle = true;
                break;
            case 7:
                this.buf.append("INVOKESPECIAL");
                isMethodHandle = true;
                break;
            case 8:
                this.buf.append("NEWINVOKESPECIAL");
                isMethodHandle = true;
                break;
            case 9:
                this.buf.append("INVOKEINTERFACE");
                isMethodHandle = true;
                break;
        }
        this.buf.append('\n');
        this.buf.append(this.tab3);
        appendDescriptor(0, h.getOwner());
        this.buf.append('.');
        this.buf.append(h.getName());
        if (!isMethodHandle) {
            this.buf.append('(');
        }
        appendDescriptor(9, h.getDesc());
        if (!isMethodHandle) {
            this.buf.append(')');
        }
    }

    private void appendAccess(int access) {
        if ((access & 1) != 0) {
            this.buf.append("public ");
        }
        if ((access & 2) != 0) {
            this.buf.append("private ");
        }
        if ((access & 4) != 0) {
            this.buf.append("protected ");
        }
        if ((access & 16) != 0) {
            this.buf.append("final ");
        }
        if ((access & 8) != 0) {
            this.buf.append("static ");
        }
        if ((access & 32) != 0) {
            this.buf.append("synchronized ");
        }
        if ((access & 64) != 0) {
            this.buf.append("volatile ");
        }
        if ((access & 128) != 0) {
            this.buf.append("transient ");
        }
        if ((access & 1024) != 0) {
            this.buf.append("abstract ");
        }
        if ((access & 2048) != 0) {
            this.buf.append("strictfp ");
        }
        if ((access & 4096) != 0) {
            this.buf.append("synthetic ");
        }
        if ((access & 32768) != 0) {
            this.buf.append("mandated ");
        }
        if ((access & 16384) != 0) {
            this.buf.append("enum ");
        }
    }

    private void appendComa(int i) {
        if (i != 0) {
            this.buf.append(", ");
        }
    }

    private void appendTypeReference(int typeRef) {
        TypeReference ref = new TypeReference(typeRef);
        switch (ref.getSort()) {
            case 0:
                this.buf.append("CLASS_TYPE_PARAMETER ").append(ref.getTypeParameterIndex());
                return;
            case 1:
                this.buf.append("METHOD_TYPE_PARAMETER ").append(ref.getTypeParameterIndex());
                return;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            default:
                return;
            case 16:
                this.buf.append("CLASS_EXTENDS ").append(ref.getSuperTypeIndex());
                return;
            case 17:
                this.buf.append("CLASS_TYPE_PARAMETER_BOUND ").append(ref.getTypeParameterIndex()).append(", ").append(ref.getTypeParameterBoundIndex());
                return;
            case 18:
                this.buf.append("METHOD_TYPE_PARAMETER_BOUND ").append(ref.getTypeParameterIndex()).append(", ").append(ref.getTypeParameterBoundIndex());
                return;
            case 19:
                this.buf.append("FIELD");
                return;
            case 20:
                this.buf.append("METHOD_RETURN");
                return;
            case 21:
                this.buf.append("METHOD_RECEIVER");
                return;
            case 22:
                this.buf.append("METHOD_FORMAL_PARAMETER ").append(ref.getFormalParameterIndex());
                return;
            case 23:
                this.buf.append("THROWS ").append(ref.getExceptionIndex());
                return;
            case 64:
                this.buf.append("LOCAL_VARIABLE");
                return;
            case 65:
                this.buf.append("RESOURCE_VARIABLE");
                return;
            case 66:
                this.buf.append("EXCEPTION_PARAMETER ").append(ref.getTryCatchBlockIndex());
                return;
            case 67:
                this.buf.append("INSTANCEOF");
                return;
            case 68:
                this.buf.append("NEW");
                return;
            case 69:
                this.buf.append("CONSTRUCTOR_REFERENCE");
                return;
            case 70:
                this.buf.append("METHOD_REFERENCE");
                return;
            case 71:
                this.buf.append("CAST ").append(ref.getTypeArgumentIndex());
                return;
            case 72:
                this.buf.append("CONSTRUCTOR_INVOCATION_TYPE_ARGUMENT ").append(ref.getTypeArgumentIndex());
                return;
            case 73:
                this.buf.append("METHOD_INVOCATION_TYPE_ARGUMENT ").append(ref.getTypeArgumentIndex());
                return;
            case 74:
                this.buf.append("CONSTRUCTOR_REFERENCE_TYPE_ARGUMENT ").append(ref.getTypeArgumentIndex());
                return;
            case 75:
                this.buf.append("METHOD_REFERENCE_TYPE_ARGUMENT ").append(ref.getTypeArgumentIndex());
                return;
        }
    }

    private void appendFrameTypes(int n, Object[] o) {
        for (int i = 0; i < n; i++) {
            if (i > 0) {
                this.buf.append(' ');
            }
            if (o[i] instanceof String) {
                String desc = (String) o[i];
                if (desc.startsWith("[")) {
                    appendDescriptor(1, desc);
                } else {
                    appendDescriptor(0, desc);
                }
            } else if (o[i] instanceof Integer) {
                switch (((Integer) o[i]).intValue()) {
                    case 0:
                        appendDescriptor(1, "T");
                        continue;
                    case 1:
                        appendDescriptor(1, "I");
                        continue;
                    case 2:
                        appendDescriptor(1, "F");
                        continue;
                    case 3:
                        appendDescriptor(1, "D");
                        continue;
                    case 4:
                        appendDescriptor(1, "J");
                        continue;
                    case 5:
                        appendDescriptor(1, "N");
                        continue;
                    case 6:
                        appendDescriptor(1, "U");
                        continue;
                }
            } else {
                appendLabel((Label) o[i]);
            }
        }
    }
}
