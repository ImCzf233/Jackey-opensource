package jdk.nashorn.internal.codegen.types;

import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.runtime.JSType;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/types/IntType.class */
class IntType extends BitwiseType {
    private static final long serialVersionUID = 1;
    private static final CompilerConstants.Call TO_STRING;
    private static final CompilerConstants.Call VALUE_OF;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !IntType.class.desiredAssertionStatus();
        TO_STRING = CompilerConstants.staticCallNoLookup(Integer.class, "toString", String.class, Integer.TYPE);
        VALUE_OF = CompilerConstants.staticCallNoLookup(Integer.class, "valueOf", Integer.class, Integer.TYPE);
    }

    public IntType() {
        super("int", Integer.TYPE, 2, 1);
    }

    @Override // jdk.nashorn.internal.codegen.types.Type
    public Type nextWider() {
        return NUMBER;
    }

    @Override // jdk.nashorn.internal.codegen.types.Type
    public Class<?> getBoxedType() {
        return Integer.class;
    }

    @Override // jdk.nashorn.internal.codegen.types.Type
    public char getBytecodeStackType() {
        return 'I';
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
    public Type ldc(MethodVisitor method, Object c) {
        if ($assertionsDisabled || (c instanceof Integer)) {
            int value = ((Integer) c).intValue();
            switch (value) {
                case -1:
                    method.visitInsn(2);
                    break;
                case 0:
                    method.visitInsn(3);
                    break;
                case 1:
                    method.visitInsn(4);
                    break;
                case 2:
                    method.visitInsn(5);
                    break;
                case 3:
                    method.visitInsn(6);
                    break;
                case 4:
                    method.visitInsn(7);
                    break;
                case 5:
                    method.visitInsn(8);
                    break;
                default:
                    if (value == ((byte) value)) {
                        method.visitIntInsn(16, value);
                        break;
                    } else if (value == ((short) value)) {
                        method.visitIntInsn(17, value);
                        break;
                    } else {
                        method.visitLdcInsn(c);
                        break;
                    }
            }
            return Type.INT;
        }
        throw new AssertionError();
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
    public Type convert(MethodVisitor method, Type to) {
        if (to.isEquivalentTo(this)) {
            return to;
        }
        if (to.isNumber()) {
            method.visitInsn(135);
        } else if (to.isLong()) {
            method.visitInsn(133);
        } else if (!to.isBoolean()) {
            if (to.isString()) {
                invokestatic(method, TO_STRING);
            } else if (to.isObject()) {
                invokestatic(method, VALUE_OF);
            } else {
                throw new UnsupportedOperationException("Illegal conversion " + this + " -> " + to);
            }
        }
        return to;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
    public Type add(MethodVisitor method, int programPoint) {
        if (programPoint == -1) {
            method.visitInsn(96);
        } else {
            method.visitInvokeDynamicInsn("iadd", "(II)I", MATHBOOTSTRAP, new Object[]{Integer.valueOf(programPoint)});
        }
        return INT;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeBitwiseOps
    public Type shr(MethodVisitor method) {
        method.visitInsn(124);
        return INT;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeBitwiseOps
    public Type sar(MethodVisitor method) {
        method.visitInsn(122);
        return INT;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeBitwiseOps
    public Type shl(MethodVisitor method) {
        method.visitInsn(120);
        return INT;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeBitwiseOps
    public Type and(MethodVisitor method) {
        method.visitInsn(126);
        return INT;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeBitwiseOps
    /* renamed from: or */
    public Type mo72or(MethodVisitor method) {
        method.visitInsn(128);
        return INT;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeBitwiseOps
    public Type xor(MethodVisitor method) {
        method.visitInsn(130);
        return INT;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
    public Type load(MethodVisitor method, int slot) {
        if ($assertionsDisabled || slot != -1) {
            method.visitVarInsn(21, slot);
            return INT;
        }
        throw new AssertionError();
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
    public void store(MethodVisitor method, int slot) {
        if ($assertionsDisabled || slot != -1) {
            method.visitVarInsn(54, slot);
            return;
        }
        throw new AssertionError();
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeNumericOps
    public Type sub(MethodVisitor method, int programPoint) {
        if (programPoint == -1) {
            method.visitInsn(100);
        } else {
            method.visitInvokeDynamicInsn("isub", "(II)I", MATHBOOTSTRAP, new Object[]{Integer.valueOf(programPoint)});
        }
        return INT;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeNumericOps
    public Type mul(MethodVisitor method, int programPoint) {
        if (programPoint == -1) {
            method.visitInsn(104);
        } else {
            method.visitInvokeDynamicInsn("imul", "(II)I", MATHBOOTSTRAP, new Object[]{Integer.valueOf(programPoint)});
        }
        return INT;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeNumericOps
    public Type div(MethodVisitor method, int programPoint) {
        if (programPoint == -1) {
            JSType.DIV_ZERO.invoke(method);
        } else {
            method.visitInvokeDynamicInsn("idiv", "(II)I", MATHBOOTSTRAP, new Object[]{Integer.valueOf(programPoint)});
        }
        return INT;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeNumericOps
    public Type rem(MethodVisitor method, int programPoint) {
        if (programPoint == -1) {
            JSType.REM_ZERO.invoke(method);
        } else {
            method.visitInvokeDynamicInsn("irem", "(II)I", MATHBOOTSTRAP, new Object[]{Integer.valueOf(programPoint)});
        }
        return INT;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeNumericOps
    public Type neg(MethodVisitor method, int programPoint) {
        if (programPoint == -1) {
            method.visitInsn(116);
        } else {
            method.visitInvokeDynamicInsn("ineg", "(I)I", MATHBOOTSTRAP, new Object[]{Integer.valueOf(programPoint)});
        }
        return INT;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
    public void _return(MethodVisitor method) {
        method.visitInsn(172);
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
    public Type loadUndefined(MethodVisitor method) {
        method.visitLdcInsn(0);
        return INT;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
    public Type loadForcedInitializer(MethodVisitor method) {
        method.visitInsn(3);
        return INT;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeNumericOps
    public Type cmp(MethodVisitor method, boolean isCmpG) {
        throw new UnsupportedOperationException("cmp" + (isCmpG ? 'g' : 'l'));
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeBitwiseOps
    public Type cmp(MethodVisitor method) {
        throw new UnsupportedOperationException("cmp");
    }
}
