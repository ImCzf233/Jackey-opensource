package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.codegen.Label;

/* renamed from: jdk.nashorn.internal.ir.BreakableNode */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/BreakableNode.class */
public interface BreakableNode extends LexicalContextNode, JoinPredecessor, Labels {
    Node ensureUniqueLabels(LexicalContext lexicalContext);

    boolean isBreakableWithoutLabel();

    Label getBreakLabel();
}
