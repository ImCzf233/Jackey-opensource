package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.p001ir.LexicalContextNode;

/* renamed from: jdk.nashorn.internal.ir.Flags */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/Flags.class */
public interface Flags<T extends LexicalContextNode> {
    int getFlags();

    boolean getFlag(int i);

    T clearFlag(LexicalContext lexicalContext, int i);

    T setFlag(LexicalContext lexicalContext, int i);

    T setFlags(LexicalContext lexicalContext, int i);
}
