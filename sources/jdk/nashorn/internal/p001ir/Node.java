package jdk.nashorn.internal.p001ir;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;
import jdk.nashorn.internal.parser.Token;
import jdk.nashorn.internal.parser.TokenType;

/* renamed from: jdk.nashorn.internal.ir.Node */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/Node.class */
public abstract class Node implements Cloneable, Serializable {
    private static final long serialVersionUID = 1;
    public static final int NO_LINE_NUMBER = -1;
    public static final long NO_TOKEN = 0;
    public static final int NO_FINISH = 0;
    protected final int start;
    protected int finish;
    private final long token;

    public abstract Node accept(NodeVisitor<? extends LexicalContext> nodeVisitor);

    public abstract void toString(StringBuilder sb, boolean z);

    public Node(long token, int finish) {
        this.token = token;
        this.start = Token.descPosition(token);
        this.finish = finish;
    }

    public Node(long token, int start, int finish) {
        this.start = start;
        this.finish = finish;
        this.token = token;
    }

    public Node(Node node) {
        this.token = node.token;
        this.start = node.start;
        this.finish = node.finish;
    }

    public boolean isLoop() {
        return false;
    }

    public boolean isAssignment() {
        return false;
    }

    public Node ensureUniqueLabels(LexicalContext lc) {
        return this;
    }

    public final String toString() {
        return toString(true);
    }

    public final String toString(boolean includeTypeInfo) {
        StringBuilder sb = new StringBuilder();
        toString(sb, includeTypeInfo);
        return sb.toString();
    }

    public void toString(StringBuilder sb) {
        toString(sb, true);
    }

    public int getFinish() {
        return this.finish;
    }

    public void setFinish(int finish) {
        this.finish = finish;
    }

    public int getStart() {
        return this.start;
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final boolean equals(Object other) {
        return this == other;
    }

    public final int hashCode() {
        return Long.hashCode(this.token);
    }

    public int position() {
        return Token.descPosition(this.token);
    }

    public int length() {
        return Token.descLength(this.token);
    }

    public TokenType tokenType() {
        return Token.descType(this.token);
    }

    public boolean isTokenType(TokenType type) {
        return tokenType() == type;
    }

    public long getToken() {
        return this.token;
    }

    public static <T extends Node> List<T> accept(NodeVisitor<? extends LexicalContext> visitor, List<T> list) {
        int size = list.size();
        if (size == 0) {
            return list;
        }
        List<T> newList = null;
        for (int i = 0; i < size; i++) {
            T node = list.get(i);
            T newNode = node == null ? null : node.accept(visitor);
            if (newNode != node) {
                if (newList == null) {
                    newList = new ArrayList<>(size);
                    for (int j = 0; j < i; j++) {
                        newList.add(list.get(j));
                    }
                }
                newList.add(newNode);
            } else if (newList != null) {
                newList.add(node);
            }
        }
        return newList == null ? list : newList;
    }

    public static <T extends LexicalContextNode> T replaceInLexicalContext(LexicalContext lc, T oldNode, T newNode) {
        if (lc != null) {
            lc.replace(oldNode, newNode);
        }
        return newNode;
    }
}
