package com.viaversion.viaversion.libs.javassist.compiler.ast;

import com.viaversion.viaversion.libs.javassist.compiler.CompileError;
import com.viaversion.viaversion.libs.javassist.compiler.TokenId;
import jdk.internal.dynalink.CallSiteDescriptor;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/compiler/ast/CastExpr.class */
public class CastExpr extends ASTList implements TokenId {
    private static final long serialVersionUID = 1;
    protected int castType;
    protected int arrayDim;

    public CastExpr(ASTList className, int dim, ASTree expr) {
        super(className, new ASTList(expr));
        this.castType = TokenId.CLASS;
        this.arrayDim = dim;
    }

    public CastExpr(int type, int dim, ASTree expr) {
        super(null, new ASTList(expr));
        this.castType = type;
        this.arrayDim = dim;
    }

    public int getType() {
        return this.castType;
    }

    public int getArrayDim() {
        return this.arrayDim;
    }

    public ASTList getClassName() {
        return (ASTList) getLeft();
    }

    public ASTree getOprand() {
        return getRight().getLeft();
    }

    public void setOprand(ASTree t) {
        getRight().setLeft(t);
    }

    @Override // com.viaversion.viaversion.libs.javassist.compiler.ast.ASTree
    public String getTag() {
        return "cast:" + this.castType + CallSiteDescriptor.TOKEN_DELIMITER + this.arrayDim;
    }

    @Override // com.viaversion.viaversion.libs.javassist.compiler.ast.ASTList, com.viaversion.viaversion.libs.javassist.compiler.ast.ASTree
    public void accept(Visitor v) throws CompileError {
        v.atCastExpr(this);
    }
}
