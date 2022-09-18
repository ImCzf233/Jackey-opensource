package jdk.nashorn.internal.codegen.types;

import jdk.internal.org.objectweb.asm.MethodVisitor;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/types/BytecodeOps.class */
public interface BytecodeOps {
    Type dup(MethodVisitor methodVisitor, int i);

    Type pop(MethodVisitor methodVisitor);

    Type swap(MethodVisitor methodVisitor, Type type);

    Type add(MethodVisitor methodVisitor, int i);

    Type load(MethodVisitor methodVisitor, int i);

    void store(MethodVisitor methodVisitor, int i);

    Type ldc(MethodVisitor methodVisitor, Object obj);

    Type loadUndefined(MethodVisitor methodVisitor);

    Type loadForcedInitializer(MethodVisitor methodVisitor);

    Type loadEmpty(MethodVisitor methodVisitor);

    Type convert(MethodVisitor methodVisitor, Type type);

    void _return(MethodVisitor methodVisitor);
}
