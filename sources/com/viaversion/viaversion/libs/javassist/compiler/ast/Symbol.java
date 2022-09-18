package com.viaversion.viaversion.libs.javassist.compiler.ast;

import com.viaversion.viaversion.libs.javassist.compiler.CompileError;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/compiler/ast/Symbol.class */
public class Symbol extends ASTree {
    private static final long serialVersionUID = 1;
    protected String identifier;

    public Symbol(String sym) {
        this.identifier = sym;
    }

    public String get() {
        return this.identifier;
    }

    @Override // com.viaversion.viaversion.libs.javassist.compiler.ast.ASTree
    public String toString() {
        return this.identifier;
    }

    @Override // com.viaversion.viaversion.libs.javassist.compiler.ast.ASTree
    public void accept(Visitor v) throws CompileError {
        v.atSymbol(this);
    }
}
