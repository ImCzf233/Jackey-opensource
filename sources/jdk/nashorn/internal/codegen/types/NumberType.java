package jdk.nashorn.internal.codegen.types;

import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.runtime.JSType;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/types/NumberType.class */
class NumberType extends NumericType {
    private static final long serialVersionUID = 1;
    private static final CompilerConstants.Call VALUE_OF;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !NumberType.class.desiredAssertionStatus();
        VALUE_OF = CompilerConstants.staticCallNoLookup(Double.class, "valueOf", Double.class, Double.TYPE);
    }

    public NumberType() {
        super("double", Double.TYPE, 4, 2);
    }

    @Override // jdk.nashorn.internal.codegen.types.Type
    public Type nextWider() {
        return OBJECT;
    }

    @Override // jdk.nashorn.internal.codegen.types.Type
    public Class<?> getBoxedType() {
        return Double.class;
    }

    @Override // jdk.nashorn.internal.codegen.types.Type
    public char getBytecodeStackType() {
        return 'D';
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeNumericOps
    public Type cmp(MethodVisitor method, boolean isCmpG) {
        method.visitInsn(isCmpG ? 152 : 151);
        return INT;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
    public Type load(MethodVisitor method, int slot) {
        if ($assertionsDisabled || slot != -1) {
            method.visitVarInsn(24, slot);
            return NUMBER;
        }
        throw new AssertionError();
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
    public void store(MethodVisitor method, int slot) {
        if ($assertionsDisabled || slot != -1) {
            method.visitVarInsn(57, slot);
            return;
        }
        throw new AssertionError();
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
    public Type loadUndefined(MethodVisitor method) {
        method.visitLdcInsn(Double.valueOf(Double.NaN));
        return NUMBER;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
    public Type loadForcedInitializer(MethodVisitor method) {
        method.visitInsn(14);
        return NUMBER;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
    public Type ldc(MethodVisitor method, Object c) {
        if ($assertionsDisabled || (c instanceof Double)) {
            double value = ((Double) c).doubleValue();
            if (Double.doubleToLongBits(value) == 0) {
                method.visitInsn(14);
            } else if (value == 1.0d) {
                method.visitInsn(15);
            } else {
                method.visitLdcInsn(Double.valueOf(value));
            }
            return NUMBER;
        }
        throw new AssertionError();
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
    public Type convert(MethodVisitor method, Type to) {
        if (isEquivalentTo(to)) {
            return null;
        }
        if (to.isInteger()) {
            invokestatic(method, JSType.TO_INT32_D);
        } else if (to.isLong()) {
            invokestatic(method, JSType.TO_LONG_D);
        } else if (to.isBoolean()) {
            invokestatic(method, JSType.TO_BOOLEAN_D);
        } else if (to.isString()) {
            invokestatic(method, JSType.TO_STRING_D);
        } else if (to.isObject()) {
            invokestatic(method, VALUE_OF);
        } else {
            throw new UnsupportedOperationException("Illegal conversion " + this + " -> " + to);
        }
        return to;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
    public Type add(MethodVisitor method, int programPoint) {
        method.visitInsn(99);
        return NUMBER;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeNumericOps
    public Type sub(MethodVisitor method, int programPoint) {
        method.visitInsn(103);
        return NUMBER;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeNumericOps
    public Type mul(MethodVisitor method, int programPoint) {
        method.visitInsn(107);
        return NUMBER;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeNumericOps
    public Type div(MethodVisitor method, int programPoint) {
        method.visitInsn(111);
        return NUMBER;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeNumericOps
    public Type rem(MethodVisitor method, int programPoint) {
        method.visitInsn(115);
        return NUMBER;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeNumericOps
    public Type neg(MethodVisitor method, int programPoint) {
        method.visitInsn(119);
        return NUMBER;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
    public void _return(MethodVisitor method) {
        method.visitInsn(175);
    }
}
