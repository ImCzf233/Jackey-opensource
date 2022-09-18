package com.viaversion.viaversion.libs.javassist.compiler;

import com.viaversion.viaversion.libs.javassist.bytecode.Bytecode;
import com.viaversion.viaversion.libs.javassist.compiler.ast.ASTList;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/compiler/ProceedHandler.class */
public interface ProceedHandler {
    void doit(JvstCodeGen jvstCodeGen, Bytecode bytecode, ASTList aSTList) throws CompileError;

    void setReturnType(JvstTypeChecker jvstTypeChecker, ASTList aSTList) throws CompileError;
}
