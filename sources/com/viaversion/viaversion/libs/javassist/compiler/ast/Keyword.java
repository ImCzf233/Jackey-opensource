package com.viaversion.viaversion.libs.javassist.compiler.ast;

import com.viaversion.viaversion.libs.javassist.compiler.CompileError;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/compiler/ast/Keyword.class */
public class Keyword extends ASTree {
    private static final long serialVersionUID = 1;
    protected int tokenId;

    public Keyword(int token) {
        this.tokenId = token;
    }

    public int get() {
        return this.tokenId;
    }

    @Override // com.viaversion.viaversion.libs.javassist.compiler.ast.ASTree
    public String toString() {
        return "id:" + this.tokenId;
    }

    @Override // com.viaversion.viaversion.libs.javassist.compiler.ast.ASTree
    public void accept(Visitor v) throws CompileError {
        v.atKeyword(this);
    }
}
