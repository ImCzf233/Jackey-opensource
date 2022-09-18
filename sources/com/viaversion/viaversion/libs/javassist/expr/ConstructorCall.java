package com.viaversion.viaversion.libs.javassist.expr;

import com.viaversion.viaversion.libs.javassist.CtClass;
import com.viaversion.viaversion.libs.javassist.CtConstructor;
import com.viaversion.viaversion.libs.javassist.CtMethod;
import com.viaversion.viaversion.libs.javassist.NotFoundException;
import com.viaversion.viaversion.libs.javassist.bytecode.CodeIterator;
import com.viaversion.viaversion.libs.javassist.bytecode.MethodInfo;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/expr/ConstructorCall.class */
public class ConstructorCall extends MethodCall {
    public ConstructorCall(int pos, CodeIterator i, CtClass decl, MethodInfo m) {
        super(pos, i, decl, m);
    }

    @Override // com.viaversion.viaversion.libs.javassist.expr.MethodCall
    public String getMethodName() {
        return isSuper() ? "super" : "this";
    }

    @Override // com.viaversion.viaversion.libs.javassist.expr.MethodCall
    public CtMethod getMethod() throws NotFoundException {
        throw new NotFoundException("this is a constructor call.  Call getConstructor().");
    }

    public CtConstructor getConstructor() throws NotFoundException {
        return getCtClass().getConstructor(getSignature());
    }

    @Override // com.viaversion.viaversion.libs.javassist.expr.MethodCall
    public boolean isSuper() {
        return super.isSuper();
    }
}
