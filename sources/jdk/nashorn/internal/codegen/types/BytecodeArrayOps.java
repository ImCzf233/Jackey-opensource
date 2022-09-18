package jdk.nashorn.internal.codegen.types;

import jdk.internal.org.objectweb.asm.MethodVisitor;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/types/BytecodeArrayOps.class */
interface BytecodeArrayOps {
    Type aload(MethodVisitor methodVisitor);

    void astore(MethodVisitor methodVisitor);

    Type arraylength(MethodVisitor methodVisitor);

    Type newarray(MethodVisitor methodVisitor);

    Type newarray(MethodVisitor methodVisitor, int i);
}
