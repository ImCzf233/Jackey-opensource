package jdk.nashorn.internal.codegen.types;

import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.runtime.JSType;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/types/LongType.class */
class LongType extends Type {
    private static final long serialVersionUID = 1;
    private static final CompilerConstants.Call VALUE_OF;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !LongType.class.desiredAssertionStatus();
        VALUE_OF = CompilerConstants.staticCallNoLookup(Long.class, "valueOf", Long.class, Long.TYPE);
    }

    protected LongType(String name) {
        super(name, Long.TYPE, 3, 2);
    }

    public LongType() {
        this("long");
    }

    @Override // jdk.nashorn.internal.codegen.types.Type
    public Type nextWider() {
        return NUMBER;
    }

    @Override // jdk.nashorn.internal.codegen.types.Type
    public Class<?> getBoxedType() {
        return Long.class;
    }

    @Override // jdk.nashorn.internal.codegen.types.Type
    public char getBytecodeStackType() {
        return 'J';
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
    public Type load(MethodVisitor method, int slot) {
        if ($assertionsDisabled || slot != -1) {
            method.visitVarInsn(22, slot);
            return LONG;
        }
        throw new AssertionError();
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
    public void store(MethodVisitor method, int slot) {
        if ($assertionsDisabled || slot != -1) {
            method.visitVarInsn(55, slot);
            return;
        }
        throw new AssertionError();
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
    public Type ldc(MethodVisitor method, Object c) {
        if ($assertionsDisabled || (c instanceof Long)) {
            long value = ((Long) c).longValue();
            if (value == 0) {
                method.visitInsn(9);
            } else if (value == serialVersionUID) {
                method.visitInsn(10);
            } else {
                method.visitLdcInsn(c);
            }
            return Type.LONG;
        }
        throw new AssertionError();
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
    public Type convert(MethodVisitor method, Type to) {
        if (isEquivalentTo(to)) {
            return to;
        }
        if (to.isNumber()) {
            method.visitInsn(138);
        } else if (to.isInteger()) {
            invokestatic(method, JSType.TO_INT32_L);
        } else if (to.isBoolean()) {
            method.visitInsn(136);
        } else if (to.isObject()) {
            invokestatic(method, VALUE_OF);
        } else if (!$assertionsDisabled) {
            throw new AssertionError("Illegal conversion " + this + " -> " + to);
        }
        return to;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
    public Type add(MethodVisitor method, int programPoint) {
        if (programPoint == -1) {
            method.visitInsn(97);
        } else {
            method.visitInvokeDynamicInsn("ladd", "(JJ)J", MATHBOOTSTRAP, new Object[]{Integer.valueOf(programPoint)});
        }
        return LONG;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
    public void _return(MethodVisitor method) {
        method.visitInsn(173);
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
    public Type loadUndefined(MethodVisitor method) {
        method.visitLdcInsn(0L);
        return LONG;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
    public Type loadForcedInitializer(MethodVisitor method) {
        method.visitInsn(9);
        return LONG;
    }
}
