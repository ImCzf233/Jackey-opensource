package org.spongepowered.asm.lib.util;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.Handle;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.TypePath;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/util/Printer.class */
public abstract class Printer {
    public static final String[] OPCODES = new String[200];
    public static final String[] TYPES;
    public static final String[] HANDLE_TAG;
    protected final int api;
    protected final StringBuffer buf = new StringBuffer();
    public final List<Object> text = new ArrayList();

    public abstract void visit(int i, int i2, String str, String str2, String str3, String[] strArr);

    public abstract void visitSource(String str, String str2);

    public abstract void visitOuterClass(String str, String str2, String str3);

    public abstract Printer visitClassAnnotation(String str, boolean z);

    public abstract void visitClassAttribute(Attribute attribute);

    public abstract void visitInnerClass(String str, String str2, String str3, int i);

    public abstract Printer visitField(int i, String str, String str2, String str3, Object obj);

    public abstract Printer visitMethod(int i, String str, String str2, String str3, String[] strArr);

    public abstract void visitClassEnd();

    public abstract void visit(String str, Object obj);

    public abstract void visitEnum(String str, String str2, String str3);

    public abstract Printer visitAnnotation(String str, String str2);

    public abstract Printer visitArray(String str);

    public abstract void visitAnnotationEnd();

    public abstract Printer visitFieldAnnotation(String str, boolean z);

    public abstract void visitFieldAttribute(Attribute attribute);

    public abstract void visitFieldEnd();

    public abstract Printer visitAnnotationDefault();

    public abstract Printer visitMethodAnnotation(String str, boolean z);

    public abstract Printer visitParameterAnnotation(int i, String str, boolean z);

    public abstract void visitMethodAttribute(Attribute attribute);

    public abstract void visitCode();

    public abstract void visitFrame(int i, int i2, Object[] objArr, int i3, Object[] objArr2);

    public abstract void visitInsn(int i);

    public abstract void visitIntInsn(int i, int i2);

    public abstract void visitVarInsn(int i, int i2);

    public abstract void visitTypeInsn(int i, String str);

    public abstract void visitFieldInsn(int i, String str, String str2, String str3);

    public abstract void visitInvokeDynamicInsn(String str, String str2, Handle handle, Object... objArr);

    public abstract void visitJumpInsn(int i, Label label);

    public abstract void visitLabel(Label label);

    public abstract void visitLdcInsn(Object obj);

    public abstract void visitIincInsn(int i, int i2);

    public abstract void visitTableSwitchInsn(int i, int i2, Label label, Label... labelArr);

    public abstract void visitLookupSwitchInsn(Label label, int[] iArr, Label[] labelArr);

    public abstract void visitMultiANewArrayInsn(String str, int i);

    public abstract void visitTryCatchBlock(Label label, Label label2, Label label3, String str);

    public abstract void visitLocalVariable(String str, String str2, String str3, Label label, Label label2, int i);

    public abstract void visitLineNumber(int i, Label label);

    public abstract void visitMaxs(int i, int i2);

    public abstract void visitMethodEnd();

    static {
        int i = 0;
        int i2 = 0;
        while (true) {
            int j = i2;
            int l = "NOP,ACONST_NULL,ICONST_M1,ICONST_0,ICONST_1,ICONST_2,ICONST_3,ICONST_4,ICONST_5,LCONST_0,LCONST_1,FCONST_0,FCONST_1,FCONST_2,DCONST_0,DCONST_1,BIPUSH,SIPUSH,LDC,,,ILOAD,LLOAD,FLOAD,DLOAD,ALOAD,,,,,,,,,,,,,,,,,,,,,IALOAD,LALOAD,FALOAD,DALOAD,AALOAD,BALOAD,CALOAD,SALOAD,ISTORE,LSTORE,FSTORE,DSTORE,ASTORE,,,,,,,,,,,,,,,,,,,,,IASTORE,LASTORE,FASTORE,DASTORE,AASTORE,BASTORE,CASTORE,SASTORE,POP,POP2,DUP,DUP_X1,DUP_X2,DUP2,DUP2_X1,DUP2_X2,SWAP,IADD,LADD,FADD,DADD,ISUB,LSUB,FSUB,DSUB,IMUL,LMUL,FMUL,DMUL,IDIV,LDIV,FDIV,DDIV,IREM,LREM,FREM,DREM,INEG,LNEG,FNEG,DNEG,ISHL,LSHL,ISHR,LSHR,IUSHR,LUSHR,IAND,LAND,IOR,LOR,IXOR,LXOR,IINC,I2L,I2F,I2D,L2I,L2F,L2D,F2I,F2L,F2D,D2I,D2L,D2F,I2B,I2C,I2S,LCMP,FCMPL,FCMPG,DCMPL,DCMPG,IFEQ,IFNE,IFLT,IFGE,IFGT,IFLE,IF_ICMPEQ,IF_ICMPNE,IF_ICMPLT,IF_ICMPGE,IF_ICMPGT,IF_ICMPLE,IF_ACMPEQ,IF_ACMPNE,GOTO,JSR,RET,TABLESWITCH,LOOKUPSWITCH,IRETURN,LRETURN,FRETURN,DRETURN,ARETURN,RETURN,GETSTATIC,PUTSTATIC,GETFIELD,PUTFIELD,INVOKEVIRTUAL,INVOKESPECIAL,INVOKESTATIC,INVOKEINTERFACE,INVOKEDYNAMIC,NEW,NEWARRAY,ANEWARRAY,ARRAYLENGTH,ATHROW,CHECKCAST,INSTANCEOF,MONITORENTER,MONITOREXIT,,MULTIANEWARRAY,IFNULL,IFNONNULL,".indexOf(44, j);
            if (l <= 0) {
                break;
            }
            int i3 = i;
            i++;
            OPCODES[i3] = j + 1 == l ? null : "NOP,ACONST_NULL,ICONST_M1,ICONST_0,ICONST_1,ICONST_2,ICONST_3,ICONST_4,ICONST_5,LCONST_0,LCONST_1,FCONST_0,FCONST_1,FCONST_2,DCONST_0,DCONST_1,BIPUSH,SIPUSH,LDC,,,ILOAD,LLOAD,FLOAD,DLOAD,ALOAD,,,,,,,,,,,,,,,,,,,,,IALOAD,LALOAD,FALOAD,DALOAD,AALOAD,BALOAD,CALOAD,SALOAD,ISTORE,LSTORE,FSTORE,DSTORE,ASTORE,,,,,,,,,,,,,,,,,,,,,IASTORE,LASTORE,FASTORE,DASTORE,AASTORE,BASTORE,CASTORE,SASTORE,POP,POP2,DUP,DUP_X1,DUP_X2,DUP2,DUP2_X1,DUP2_X2,SWAP,IADD,LADD,FADD,DADD,ISUB,LSUB,FSUB,DSUB,IMUL,LMUL,FMUL,DMUL,IDIV,LDIV,FDIV,DDIV,IREM,LREM,FREM,DREM,INEG,LNEG,FNEG,DNEG,ISHL,LSHL,ISHR,LSHR,IUSHR,LUSHR,IAND,LAND,IOR,LOR,IXOR,LXOR,IINC,I2L,I2F,I2D,L2I,L2F,L2D,F2I,F2L,F2D,D2I,D2L,D2F,I2B,I2C,I2S,LCMP,FCMPL,FCMPG,DCMPL,DCMPG,IFEQ,IFNE,IFLT,IFGE,IFGT,IFLE,IF_ICMPEQ,IF_ICMPNE,IF_ICMPLT,IF_ICMPGE,IF_ICMPGT,IF_ICMPLE,IF_ACMPEQ,IF_ACMPNE,GOTO,JSR,RET,TABLESWITCH,LOOKUPSWITCH,IRETURN,LRETURN,FRETURN,DRETURN,ARETURN,RETURN,GETSTATIC,PUTSTATIC,GETFIELD,PUTFIELD,INVOKEVIRTUAL,INVOKESPECIAL,INVOKESTATIC,INVOKEINTERFACE,INVOKEDYNAMIC,NEW,NEWARRAY,ANEWARRAY,ARRAYLENGTH,ATHROW,CHECKCAST,INSTANCEOF,MONITORENTER,MONITOREXIT,,MULTIANEWARRAY,IFNULL,IFNONNULL,".substring(j, l);
            i2 = l + 1;
        }
        TYPES = new String[12];
        int j2 = 0;
        int i4 = 4;
        while (true) {
            int l2 = "T_BOOLEAN,T_CHAR,T_FLOAT,T_DOUBLE,T_BYTE,T_SHORT,T_INT,T_LONG,".indexOf(44, j2);
            if (l2 <= 0) {
                break;
            }
            int i5 = i4;
            i4++;
            TYPES[i5] = "T_BOOLEAN,T_CHAR,T_FLOAT,T_DOUBLE,T_BYTE,T_SHORT,T_INT,T_LONG,".substring(j2, l2);
            j2 = l2 + 1;
        }
        HANDLE_TAG = new String[10];
        int j3 = 0;
        int i6 = 1;
        while (true) {
            int l3 = "H_GETFIELD,H_GETSTATIC,H_PUTFIELD,H_PUTSTATIC,H_INVOKEVIRTUAL,H_INVOKESTATIC,H_INVOKESPECIAL,H_NEWINVOKESPECIAL,H_INVOKEINTERFACE,".indexOf(44, j3);
            if (l3 > 0) {
                int i7 = i6;
                i6++;
                HANDLE_TAG[i7] = "H_GETFIELD,H_GETSTATIC,H_PUTFIELD,H_PUTSTATIC,H_INVOKEVIRTUAL,H_INVOKESTATIC,H_INVOKESPECIAL,H_NEWINVOKESPECIAL,H_INVOKEINTERFACE,".substring(j3, l3);
                j3 = l3 + 1;
            } else {
                return;
            }
        }
    }

    public Printer(int api) {
        this.api = api;
    }

    public Printer visitClassTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        throw new RuntimeException("Must be overriden");
    }

    public Printer visitFieldTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        throw new RuntimeException("Must be overriden");
    }

    public void visitParameter(String name, int access) {
        throw new RuntimeException("Must be overriden");
    }

    public Printer visitMethodTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        throw new RuntimeException("Must be overriden");
    }

    @Deprecated
    public void visitMethodInsn(int opcode, String owner, String name, String desc) {
        if (this.api >= 327680) {
            boolean itf = opcode == 185;
            visitMethodInsn(opcode, owner, name, desc, itf);
            return;
        }
        throw new RuntimeException("Must be overriden");
    }

    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        if (this.api < 327680) {
            if (itf != (opcode == 185)) {
                throw new IllegalArgumentException("INVOKESPECIAL/STATIC on interfaces require ASM 5");
            }
            visitMethodInsn(opcode, owner, name, desc);
            return;
        }
        throw new RuntimeException("Must be overriden");
    }

    public Printer visitInsnAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        throw new RuntimeException("Must be overriden");
    }

    public Printer visitTryCatchAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        throw new RuntimeException("Must be overriden");
    }

    public Printer visitLocalVariableAnnotation(int typeRef, TypePath typePath, Label[] start, Label[] end, int[] index, String desc, boolean visible) {
        throw new RuntimeException("Must be overriden");
    }

    public List<Object> getText() {
        return this.text;
    }

    public void print(PrintWriter pw) {
        printList(pw, this.text);
    }

    public static void appendString(StringBuffer buf, String s) {
        buf.append('\"');
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '\n') {
                buf.append("\\n");
            } else if (c == '\r') {
                buf.append("\\r");
            } else if (c == '\\') {
                buf.append("\\\\");
            } else if (c == '\"') {
                buf.append("\\\"");
            } else if (c < ' ' || c > 127) {
                buf.append("\\u");
                if (c < 16) {
                    buf.append("000");
                } else if (c < 256) {
                    buf.append("00");
                } else if (c < 4096) {
                    buf.append('0');
                }
                buf.append(Integer.toString(c, 16));
            } else {
                buf.append(c);
            }
        }
        buf.append('\"');
    }

    static void printList(PrintWriter pw, List<?> l) {
        for (int i = 0; i < l.size(); i++) {
            Object o = l.get(i);
            if (o instanceof List) {
                printList(pw, (List) o);
            } else {
                pw.print(o.toString());
            }
        }
    }
}
