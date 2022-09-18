package com.viaversion.viaversion.libs.javassist.compiler.ast;

import com.viaversion.viaversion.libs.javassist.compiler.CompileError;
import com.viaversion.viaversion.libs.javassist.compiler.MemberResolver;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/compiler/ast/CallExpr.class */
public class CallExpr extends Expr {
    private static final long serialVersionUID = 1;
    private MemberResolver.Method method = null;

    private CallExpr(ASTree _head, ASTList _tail) {
        super(67, _head, _tail);
    }

    public void setMethod(MemberResolver.Method m) {
        this.method = m;
    }

    public MemberResolver.Method getMethod() {
        return this.method;
    }

    public static CallExpr makeCall(ASTree target, ASTree args) {
        return new CallExpr(target, new ASTList(args));
    }

    @Override // com.viaversion.viaversion.libs.javassist.compiler.ast.Expr, com.viaversion.viaversion.libs.javassist.compiler.ast.ASTList, com.viaversion.viaversion.libs.javassist.compiler.ast.ASTree
    public void accept(Visitor v) throws CompileError {
        v.atCallExpr(this);
    }
}
