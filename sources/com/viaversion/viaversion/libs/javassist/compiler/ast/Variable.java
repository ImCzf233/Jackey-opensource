package com.viaversion.viaversion.libs.javassist.compiler.ast;

import com.viaversion.viaversion.libs.javassist.compiler.CompileError;
import jdk.internal.dynalink.CallSiteDescriptor;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/compiler/ast/Variable.class */
public class Variable extends Symbol {
    private static final long serialVersionUID = 1;
    protected Declarator declarator;

    public Variable(String sym, Declarator d) {
        super(sym);
        this.declarator = d;
    }

    public Declarator getDeclarator() {
        return this.declarator;
    }

    @Override // com.viaversion.viaversion.libs.javassist.compiler.ast.Symbol, com.viaversion.viaversion.libs.javassist.compiler.ast.ASTree
    public String toString() {
        return this.identifier + CallSiteDescriptor.TOKEN_DELIMITER + this.declarator.getType();
    }

    @Override // com.viaversion.viaversion.libs.javassist.compiler.ast.Symbol, com.viaversion.viaversion.libs.javassist.compiler.ast.ASTree
    public void accept(Visitor v) throws CompileError {
        v.atVariable(this);
    }
}
