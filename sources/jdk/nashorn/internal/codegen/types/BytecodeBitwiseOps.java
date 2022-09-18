package jdk.nashorn.internal.codegen.types;

import jdk.internal.org.objectweb.asm.MethodVisitor;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/types/BytecodeBitwiseOps.class */
public interface BytecodeBitwiseOps {
    Type shr(MethodVisitor methodVisitor);

    Type sar(MethodVisitor methodVisitor);

    Type shl(MethodVisitor methodVisitor);

    Type and(MethodVisitor methodVisitor);

    /* renamed from: or */
    Type mo72or(MethodVisitor methodVisitor);

    Type xor(MethodVisitor methodVisitor);

    Type cmp(MethodVisitor methodVisitor);
}
