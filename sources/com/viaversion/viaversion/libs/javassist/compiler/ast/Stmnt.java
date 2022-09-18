package com.viaversion.viaversion.libs.javassist.compiler.ast;

import com.viaversion.viaversion.libs.javassist.compiler.CompileError;
import com.viaversion.viaversion.libs.javassist.compiler.TokenId;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/compiler/ast/Stmnt.class */
public class Stmnt extends ASTList implements TokenId {
    private static final long serialVersionUID = 1;
    protected int operatorId;

    public Stmnt(int op, ASTree _head, ASTList _tail) {
        super(_head, _tail);
        this.operatorId = op;
    }

    public Stmnt(int op, ASTree _head) {
        super(_head);
        this.operatorId = op;
    }

    public Stmnt(int op) {
        this(op, null);
    }

    public static Stmnt make(int op, ASTree oprand1, ASTree oprand2) {
        return new Stmnt(op, oprand1, new ASTList(oprand2));
    }

    public static Stmnt make(int op, ASTree op1, ASTree op2, ASTree op3) {
        return new Stmnt(op, op1, new ASTList(op2, new ASTList(op3)));
    }

    @Override // com.viaversion.viaversion.libs.javassist.compiler.ast.ASTList, com.viaversion.viaversion.libs.javassist.compiler.ast.ASTree
    public void accept(Visitor v) throws CompileError {
        v.atStmnt(this);
    }

    public int getOperator() {
        return this.operatorId;
    }

    @Override // com.viaversion.viaversion.libs.javassist.compiler.ast.ASTree
    public String getTag() {
        if (this.operatorId < 128) {
            return "stmnt:" + ((char) this.operatorId);
        }
        return "stmnt:" + this.operatorId;
    }
}
