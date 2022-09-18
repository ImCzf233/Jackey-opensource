package jdk.nashorn.internal.p001ir.visitor;

import jdk.nashorn.internal.p001ir.LexicalContext;

/* renamed from: jdk.nashorn.internal.ir.visitor.SimpleNodeVisitor */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/visitor/SimpleNodeVisitor.class */
public abstract class SimpleNodeVisitor extends NodeVisitor<LexicalContext> {
    public SimpleNodeVisitor() {
        super(new LexicalContext());
    }
}
