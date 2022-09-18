package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;

/* renamed from: jdk.nashorn.internal.ir.LexicalContextNode */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/LexicalContextNode.class */
public interface LexicalContextNode {
    Node accept(LexicalContext lexicalContext, NodeVisitor<? extends LexicalContext> nodeVisitor);

    /* renamed from: jdk.nashorn.internal.ir.LexicalContextNode$Acceptor */
    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/LexicalContextNode$Acceptor.class */
    public static class Acceptor {
        public static Node accept(LexicalContextNode node, NodeVisitor<? extends LexicalContext> visitor) {
            LexicalContext lc = visitor.getLexicalContext();
            lc.push(node);
            Node newNode = node.accept(lc, visitor);
            return lc.pop(newNode);
        }
    }
}
