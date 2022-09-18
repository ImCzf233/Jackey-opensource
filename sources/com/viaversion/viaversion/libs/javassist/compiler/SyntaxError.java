package com.viaversion.viaversion.libs.javassist.compiler;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/compiler/SyntaxError.class */
public class SyntaxError extends CompileError {
    private static final long serialVersionUID = 1;

    public SyntaxError(Lex lexer) {
        super("syntax error near \"" + lexer.getTextAround() + "\"", lexer);
    }
}
