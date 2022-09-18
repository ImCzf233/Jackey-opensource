package com.viaversion.viaversion.libs.javassist.compiler.ast;

import com.viaversion.viaversion.libs.javassist.compiler.CompileError;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/compiler/ast/BinExpr.class */
public class BinExpr extends Expr {
    private static final long serialVersionUID = 1;

    private BinExpr(int op, ASTree _head, ASTList _tail) {
        super(op, _head, _tail);
    }

    public static BinExpr makeBin(int op, ASTree oprand1, ASTree oprand2) {
        return new BinExpr(op, oprand1, new ASTList(oprand2));
    }

    @Override // com.viaversion.viaversion.libs.javassist.compiler.ast.Expr, com.viaversion.viaversion.libs.javassist.compiler.ast.ASTList, com.viaversion.viaversion.libs.javassist.compiler.ast.ASTree
    public void accept(Visitor v) throws CompileError {
        v.atBinExpr(this);
    }
}
