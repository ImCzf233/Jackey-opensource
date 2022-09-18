package com.viaversion.viaversion.libs.javassist.compiler.ast;

import com.viaversion.viaversion.libs.javassist.CtField;
import com.viaversion.viaversion.libs.javassist.compiler.CompileError;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/compiler/ast/Member.class */
public class Member extends Symbol {
    private static final long serialVersionUID = 1;
    private CtField field = null;

    public Member(String name) {
        super(name);
    }

    public void setField(CtField f) {
        this.field = f;
    }

    public CtField getField() {
        return this.field;
    }

    @Override // com.viaversion.viaversion.libs.javassist.compiler.ast.Symbol, com.viaversion.viaversion.libs.javassist.compiler.ast.ASTree
    public void accept(Visitor v) throws CompileError {
        v.atMember(this);
    }
}
