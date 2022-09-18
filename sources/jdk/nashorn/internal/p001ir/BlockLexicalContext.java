package jdk.nashorn.internal.p001ir;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/* renamed from: jdk.nashorn.internal.ir.BlockLexicalContext */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/BlockLexicalContext.class */
public class BlockLexicalContext extends LexicalContext {
    private final Deque<List<Statement>> sstack = new ArrayDeque();
    protected Statement lastStatement;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !BlockLexicalContext.class.desiredAssertionStatus();
    }

    @Override // jdk.nashorn.internal.p001ir.LexicalContext
    public <T extends LexicalContextNode> T push(T node) {
        T pushed = (T) super.push(node);
        if (node instanceof Block) {
            this.sstack.push(new ArrayList());
        }
        return pushed;
    }

    public List<Statement> popStatements() {
        return this.sstack.pop();
    }

    protected Block afterSetStatements(Block block) {
        return block;
    }

    @Override // jdk.nashorn.internal.p001ir.LexicalContext
    public <T extends Node> T pop(T node) {
        T expected = node;
        if (node instanceof Block) {
            List<Statement> newStatements = popStatements();
            T expected2 = ((Block) node).setStatements(this, newStatements);
            expected = afterSetStatements((Block) expected2);
            if (!this.sstack.isEmpty()) {
                this.lastStatement = lastStatement(this.sstack.peek());
            }
        }
        return (T) super.pop(expected);
    }

    public void appendStatement(Statement statement) {
        if ($assertionsDisabled || statement != null) {
            this.sstack.peek().add(statement);
            this.lastStatement = statement;
            return;
        }
        throw new AssertionError();
    }

    public Node prependStatement(Statement statement) {
        if ($assertionsDisabled || statement != null) {
            this.sstack.peek().add(0, statement);
            return statement;
        }
        throw new AssertionError();
    }

    public void prependStatements(List<Statement> statements) {
        if ($assertionsDisabled || statements != null) {
            this.sstack.peek().addAll(0, statements);
            return;
        }
        throw new AssertionError();
    }

    public Statement getLastStatement() {
        return this.lastStatement;
    }

    private static Statement lastStatement(List<Statement> statements) {
        int s = statements.size();
        if (s == 0) {
            return null;
        }
        return statements.get(s - 1);
    }
}
