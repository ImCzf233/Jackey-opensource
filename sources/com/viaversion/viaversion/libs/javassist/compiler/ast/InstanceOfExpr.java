package com.viaversion.viaversion.libs.javassist.compiler.ast;

import com.viaversion.viaversion.libs.javassist.compiler.CompileError;
import jdk.internal.dynalink.CallSiteDescriptor;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/compiler/ast/InstanceOfExpr.class */
public class InstanceOfExpr extends CastExpr {
    private static final long serialVersionUID = 1;

    public InstanceOfExpr(ASTList className, int dim, ASTree expr) {
        super(className, dim, expr);
    }

    public InstanceOfExpr(int type, int dim, ASTree expr) {
        super(type, dim, expr);
    }

    @Override // com.viaversion.viaversion.libs.javassist.compiler.ast.CastExpr, com.viaversion.viaversion.libs.javassist.compiler.ast.ASTree
    public String getTag() {
        return "instanceof:" + this.castType + CallSiteDescriptor.TOKEN_DELIMITER + this.arrayDim;
    }

    @Override // com.viaversion.viaversion.libs.javassist.compiler.ast.CastExpr, com.viaversion.viaversion.libs.javassist.compiler.ast.ASTList, com.viaversion.viaversion.libs.javassist.compiler.ast.ASTree
    public void accept(Visitor v) throws CompileError {
        v.atInstanceOfExpr(this);
    }
}
