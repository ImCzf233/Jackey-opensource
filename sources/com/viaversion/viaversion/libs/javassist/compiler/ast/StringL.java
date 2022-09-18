package com.viaversion.viaversion.libs.javassist.compiler.ast;

import com.viaversion.viaversion.libs.javassist.compiler.CompileError;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/compiler/ast/StringL.class */
public class StringL extends ASTree {
    private static final long serialVersionUID = 1;
    protected String text;

    public StringL(String t) {
        this.text = t;
    }

    public String get() {
        return this.text;
    }

    @Override // com.viaversion.viaversion.libs.javassist.compiler.ast.ASTree
    public String toString() {
        return "\"" + this.text + "\"";
    }

    @Override // com.viaversion.viaversion.libs.javassist.compiler.ast.ASTree
    public void accept(Visitor v) throws CompileError {
        v.atStringL(this);
    }
}
