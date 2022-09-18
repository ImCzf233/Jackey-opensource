package com.viaversion.viaversion.libs.javassist.compiler.ast;

import com.viaversion.viaversion.libs.javassist.compiler.CompileError;
import com.viaversion.viaversion.libs.javassist.compiler.TokenId;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/compiler/ast/Expr.class */
public class Expr extends ASTList implements TokenId {
    private static final long serialVersionUID = 1;
    protected int operatorId;

    public Expr(int op, ASTree _head, ASTList _tail) {
        super(_head, _tail);
        this.operatorId = op;
    }

    Expr(int op, ASTree _head) {
        super(_head);
        this.operatorId = op;
    }

    public static Expr make(int op, ASTree oprand1, ASTree oprand2) {
        return new Expr(op, oprand1, new ASTList(oprand2));
    }

    public static Expr make(int op, ASTree oprand1) {
        return new Expr(op, oprand1);
    }

    public int getOperator() {
        return this.operatorId;
    }

    public void setOperator(int op) {
        this.operatorId = op;
    }

    public ASTree oprand1() {
        return getLeft();
    }

    public void setOprand1(ASTree expr) {
        setLeft(expr);
    }

    public ASTree oprand2() {
        return getRight().getLeft();
    }

    public void setOprand2(ASTree expr) {
        getRight().setLeft(expr);
    }

    @Override // com.viaversion.viaversion.libs.javassist.compiler.ast.ASTList, com.viaversion.viaversion.libs.javassist.compiler.ast.ASTree
    public void accept(Visitor v) throws CompileError {
        v.atExpr(this);
    }

    public String getName() {
        int id = this.operatorId;
        if (id < 128) {
            return String.valueOf((char) id);
        }
        if (350 <= id && id <= 371) {
            return opNames[id - TokenId.NEQ];
        }
        if (id == 323) {
            return "instanceof";
        }
        return String.valueOf(id);
    }

    @Override // com.viaversion.viaversion.libs.javassist.compiler.ast.ASTree
    public String getTag() {
        return "op:" + getName();
    }
}
