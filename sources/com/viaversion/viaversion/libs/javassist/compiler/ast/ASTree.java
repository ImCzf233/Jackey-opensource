package com.viaversion.viaversion.libs.javassist.compiler.ast;

import com.viaversion.viaversion.libs.javassist.compiler.CompileError;
import java.io.Serializable;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/compiler/ast/ASTree.class */
public abstract class ASTree implements Serializable {
    private static final long serialVersionUID = 1;

    public abstract void accept(Visitor visitor) throws CompileError;

    public ASTree getLeft() {
        return null;
    }

    public ASTree getRight() {
        return null;
    }

    public void setLeft(ASTree _left) {
    }

    public void setRight(ASTree _right) {
    }

    public String toString() {
        StringBuffer sbuf = new StringBuffer();
        sbuf.append('<');
        sbuf.append(getTag());
        sbuf.append('>');
        return sbuf.toString();
    }

    public String getTag() {
        String name = getClass().getName();
        return name.substring(name.lastIndexOf(46) + 1);
    }
}
