package jdk.nashorn.internal.codegen.types;

import jdk.internal.org.objectweb.asm.MethodVisitor;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/types/ArrayType.class */
public class ArrayType extends ObjectType implements BytecodeArrayOps {
    private static final long serialVersionUID = 1;
    static final /* synthetic */ boolean $assertionsDisabled;

    @Override // jdk.nashorn.internal.codegen.types.ObjectType, jdk.nashorn.internal.codegen.types.Type
    public /* bridge */ /* synthetic */ char getBytecodeStackType() {
        return super.getBytecodeStackType();
    }

    @Override // jdk.nashorn.internal.codegen.types.ObjectType, jdk.nashorn.internal.codegen.types.BytecodeOps
    public /* bridge */ /* synthetic */ void _return(MethodVisitor methodVisitor) {
        super._return(methodVisitor);
    }

    @Override // jdk.nashorn.internal.codegen.types.ObjectType, jdk.nashorn.internal.codegen.types.BytecodeOps
    public /* bridge */ /* synthetic */ Type ldc(MethodVisitor methodVisitor, Object obj) {
        return super.ldc(methodVisitor, obj);
    }

    @Override // jdk.nashorn.internal.codegen.types.ObjectType, jdk.nashorn.internal.codegen.types.Type, jdk.nashorn.internal.codegen.types.BytecodeOps
    public /* bridge */ /* synthetic */ Type loadEmpty(MethodVisitor methodVisitor) {
        return super.loadEmpty(methodVisitor);
    }

    @Override // jdk.nashorn.internal.codegen.types.ObjectType, jdk.nashorn.internal.codegen.types.BytecodeOps
    public /* bridge */ /* synthetic */ Type loadForcedInitializer(MethodVisitor methodVisitor) {
        return super.loadForcedInitializer(methodVisitor);
    }

    @Override // jdk.nashorn.internal.codegen.types.ObjectType, jdk.nashorn.internal.codegen.types.BytecodeOps
    public /* bridge */ /* synthetic */ Type loadUndefined(MethodVisitor methodVisitor) {
        return super.loadUndefined(methodVisitor);
    }

    @Override // jdk.nashorn.internal.codegen.types.ObjectType, jdk.nashorn.internal.codegen.types.BytecodeOps
    public /* bridge */ /* synthetic */ void store(MethodVisitor methodVisitor, int i) {
        super.store(methodVisitor, i);
    }

    @Override // jdk.nashorn.internal.codegen.types.ObjectType, jdk.nashorn.internal.codegen.types.BytecodeOps
    public /* bridge */ /* synthetic */ Type add(MethodVisitor methodVisitor, int i) {
        return super.add(methodVisitor, i);
    }

    @Override // jdk.nashorn.internal.codegen.types.ObjectType, jdk.nashorn.internal.codegen.types.Type
    public /* bridge */ /* synthetic */ String getShortDescriptor() {
        return super.getShortDescriptor();
    }

    static {
        $assertionsDisabled = !ArrayType.class.desiredAssertionStatus();
    }

    public ArrayType(Class<?> clazz) {
        super(clazz);
    }

    public Type getElementType() {
        return Type.typeFor(getTypeClass().getComponentType());
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeArrayOps
    public void astore(MethodVisitor method) {
        method.visitInsn(83);
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeArrayOps
    public Type aload(MethodVisitor method) {
        method.visitInsn(50);
        return getElementType();
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeArrayOps
    public Type arraylength(MethodVisitor method) {
        method.visitInsn(190);
        return INT;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeArrayOps
    public Type newarray(MethodVisitor method) {
        method.visitTypeInsn(189, getElementType().getInternalName());
        return this;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeArrayOps
    public Type newarray(MethodVisitor method, int dims) {
        method.visitMultiANewArrayInsn(getInternalName(), dims);
        return this;
    }

    @Override // jdk.nashorn.internal.codegen.types.ObjectType, jdk.nashorn.internal.codegen.types.BytecodeOps
    public Type load(MethodVisitor method, int slot) {
        if ($assertionsDisabled || slot != -1) {
            method.visitVarInsn(25, slot);
            return this;
        }
        throw new AssertionError();
    }

    @Override // jdk.nashorn.internal.codegen.types.ObjectType, jdk.nashorn.internal.codegen.types.Type
    public String toString() {
        return "array<elementType=" + getElementType().getTypeClass().getSimpleName() + '>';
    }

    @Override // jdk.nashorn.internal.codegen.types.ObjectType, jdk.nashorn.internal.codegen.types.BytecodeOps
    public Type convert(MethodVisitor method, Type to) {
        if ($assertionsDisabled || to.isObject()) {
            if (!$assertionsDisabled && to.isArray() && ((ArrayType) to).getElementType() != getElementType()) {
                throw new AssertionError();
            }
            return to;
        }
        throw new AssertionError();
    }
}
