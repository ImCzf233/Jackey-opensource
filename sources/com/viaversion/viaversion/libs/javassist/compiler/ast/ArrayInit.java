package com.viaversion.viaversion.libs.javassist.compiler.ast;

import com.viaversion.viaversion.libs.javassist.compiler.CompileError;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/compiler/ast/ArrayInit.class */
public class ArrayInit extends ASTList {
    private static final long serialVersionUID = 1;

    public ArrayInit(ASTree firstElement) {
        super(firstElement);
    }

    public int size() {
        int s = length();
        if (s == 1 && head() == null) {
            return 0;
        }
        return s;
    }

    @Override // com.viaversion.viaversion.libs.javassist.compiler.ast.ASTList, com.viaversion.viaversion.libs.javassist.compiler.ast.ASTree
    public void accept(Visitor v) throws CompileError {
        v.atArrayInit(this);
    }

    @Override // com.viaversion.viaversion.libs.javassist.compiler.ast.ASTree
    public String getTag() {
        return "array";
    }
}
