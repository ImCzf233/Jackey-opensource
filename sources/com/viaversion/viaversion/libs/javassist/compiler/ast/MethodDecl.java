package com.viaversion.viaversion.libs.javassist.compiler.ast;

import com.viaversion.viaversion.libs.javassist.compiler.CompileError;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/compiler/ast/MethodDecl.class */
public class MethodDecl extends ASTList {
    private static final long serialVersionUID = 1;
    public static final String initName = "<init>";

    public MethodDecl(ASTree _head, ASTList _tail) {
        super(_head, _tail);
    }

    public boolean isConstructor() {
        Symbol sym = getReturn().getVariable();
        return sym != null && "<init>".equals(sym.get());
    }

    public ASTList getModifiers() {
        return (ASTList) getLeft();
    }

    public Declarator getReturn() {
        return (Declarator) tail().head();
    }

    public ASTList getParams() {
        return (ASTList) sublist(2).head();
    }

    public ASTList getThrows() {
        return (ASTList) sublist(3).head();
    }

    public Stmnt getBody() {
        return (Stmnt) sublist(4).head();
    }

    @Override // com.viaversion.viaversion.libs.javassist.compiler.ast.ASTList, com.viaversion.viaversion.libs.javassist.compiler.ast.ASTree
    public void accept(Visitor v) throws CompileError {
        v.atMethodDecl(this);
    }
}
