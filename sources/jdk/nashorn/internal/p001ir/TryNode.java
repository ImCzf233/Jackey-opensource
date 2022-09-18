package jdk.nashorn.internal.p001ir;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;

@Immutable
/* renamed from: jdk.nashorn.internal.ir.TryNode */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/TryNode.class */
public final class TryNode extends LexicalContextStatement implements JoinPredecessor {
    private static final long serialVersionUID = 1;
    private final Block body;
    private final List<Block> catchBlocks;
    private final Block finallyBody;
    private final List<Block> inlinedFinallies;
    private final Symbol exception;
    private final LocalVariableConversion conversion;
    static final /* synthetic */ boolean $assertionsDisabled;

    @Override // jdk.nashorn.internal.p001ir.LexicalContextStatement, jdk.nashorn.internal.p001ir.Node
    public /* bridge */ /* synthetic */ Node accept(NodeVisitor nodeVisitor) {
        return super.accept(nodeVisitor);
    }

    static {
        $assertionsDisabled = !TryNode.class.desiredAssertionStatus();
    }

    public TryNode(int lineNumber, long token, int finish, Block body, List<Block> catchBlocks, Block finallyBody) {
        super(lineNumber, token, finish);
        this.body = body;
        this.catchBlocks = catchBlocks;
        this.finallyBody = finallyBody;
        this.conversion = null;
        this.inlinedFinallies = Collections.emptyList();
        this.exception = null;
    }

    private TryNode(TryNode tryNode, Block body, List<Block> catchBlocks, Block finallyBody, LocalVariableConversion conversion, List<Block> inlinedFinallies, Symbol exception) {
        super(tryNode);
        this.body = body;
        this.catchBlocks = catchBlocks;
        this.finallyBody = finallyBody;
        this.conversion = conversion;
        this.inlinedFinallies = inlinedFinallies;
        this.exception = exception;
    }

    @Override // jdk.nashorn.internal.p001ir.Node, jdk.nashorn.internal.p001ir.BreakableNode
    public Node ensureUniqueLabels(LexicalContext lc) {
        return new TryNode(this, this.body, this.catchBlocks, this.finallyBody, this.conversion, this.inlinedFinallies, this.exception);
    }

    @Override // jdk.nashorn.internal.p001ir.Statement, jdk.nashorn.internal.p001ir.Terminal
    public boolean isTerminal() {
        if (this.body.isTerminal()) {
            for (Block catchBlock : getCatchBlocks()) {
                if (!catchBlock.isTerminal()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.LexicalContextNode
    public Node accept(LexicalContext lc, NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterTryNode(this)) {
            Block newFinallyBody = this.finallyBody == null ? null : (Block) this.finallyBody.accept(visitor);
            Block newBody = (Block) this.body.accept(visitor);
            return visitor.leaveTryNode(setBody(lc, newBody).setFinallyBody(lc, newFinallyBody).setCatchBlocks(lc, Node.accept(visitor, this.catchBlocks)).setInlinedFinallies(lc, Node.accept(visitor, this.inlinedFinallies)));
        }
        return this;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean printType) {
        sb.append("try ");
    }

    public Block getBody() {
        return this.body;
    }

    public TryNode setBody(LexicalContext lc, Block body) {
        if (this.body == body) {
            return this;
        }
        return (TryNode) Node.replaceInLexicalContext(lc, this, new TryNode(this, body, this.catchBlocks, this.finallyBody, this.conversion, this.inlinedFinallies, this.exception));
    }

    public List<CatchNode> getCatches() {
        List<CatchNode> catches = new ArrayList<>(this.catchBlocks.size());
        for (Block catchBlock : this.catchBlocks) {
            catches.add(getCatchNodeFromBlock(catchBlock));
        }
        return Collections.unmodifiableList(catches);
    }

    private static CatchNode getCatchNodeFromBlock(Block catchBlock) {
        return (CatchNode) catchBlock.getStatements().get(0);
    }

    public List<Block> getCatchBlocks() {
        return Collections.unmodifiableList(this.catchBlocks);
    }

    public TryNode setCatchBlocks(LexicalContext lc, List<Block> catchBlocks) {
        if (this.catchBlocks == catchBlocks) {
            return this;
        }
        return (TryNode) Node.replaceInLexicalContext(lc, this, new TryNode(this, this.body, catchBlocks, this.finallyBody, this.conversion, this.inlinedFinallies, this.exception));
    }

    public Symbol getException() {
        return this.exception;
    }

    public TryNode setException(LexicalContext lc, Symbol exception) {
        if (this.exception == exception) {
            return this;
        }
        return (TryNode) Node.replaceInLexicalContext(lc, this, new TryNode(this, this.body, this.catchBlocks, this.finallyBody, this.conversion, this.inlinedFinallies, exception));
    }

    public Block getFinallyBody() {
        return this.finallyBody;
    }

    public Block getInlinedFinally(String labelName) {
        for (Block inlinedFinally : this.inlinedFinallies) {
            LabelNode labelNode = getInlinedFinallyLabelNode(inlinedFinally);
            if (labelNode.getLabelName().equals(labelName)) {
                return labelNode.getBody();
            }
        }
        return null;
    }

    private static LabelNode getInlinedFinallyLabelNode(Block inlinedFinally) {
        return (LabelNode) inlinedFinally.getStatements().get(0);
    }

    public static Block getLabelledInlinedFinallyBlock(Block inlinedFinally) {
        return getInlinedFinallyLabelNode(inlinedFinally).getBody();
    }

    public List<Block> getInlinedFinallies() {
        return Collections.unmodifiableList(this.inlinedFinallies);
    }

    public TryNode setFinallyBody(LexicalContext lc, Block finallyBody) {
        if (this.finallyBody == finallyBody) {
            return this;
        }
        return (TryNode) Node.replaceInLexicalContext(lc, this, new TryNode(this, this.body, this.catchBlocks, finallyBody, this.conversion, this.inlinedFinallies, this.exception));
    }

    public TryNode setInlinedFinallies(LexicalContext lc, List<Block> inlinedFinallies) {
        if (this.inlinedFinallies == inlinedFinallies) {
            return this;
        }
        if (!$assertionsDisabled && !checkInlinedFinallies(inlinedFinallies)) {
            throw new AssertionError();
        }
        return (TryNode) Node.replaceInLexicalContext(lc, this, new TryNode(this, this.body, this.catchBlocks, this.finallyBody, this.conversion, inlinedFinallies, this.exception));
    }

    private static boolean checkInlinedFinallies(List<Block> inlinedFinallies) {
        if (!inlinedFinallies.isEmpty()) {
            Set<String> labels = new HashSet<>();
            for (Block inlinedFinally : inlinedFinallies) {
                List<Statement> stmts = inlinedFinally.getStatements();
                if (!$assertionsDisabled && stmts.size() != 1) {
                    throw new AssertionError();
                }
                LabelNode ln = getInlinedFinallyLabelNode(inlinedFinally);
                if (!$assertionsDisabled && !labels.add(ln.getLabelName())) {
                    throw new AssertionError();
                }
            }
            return true;
        }
        return true;
    }

    @Override // jdk.nashorn.internal.p001ir.JoinPredecessor
    public JoinPredecessor setLocalVariableConversion(LexicalContext lc, LocalVariableConversion conversion) {
        if (this.conversion == conversion) {
            return this;
        }
        return new TryNode(this, this.body, this.catchBlocks, this.finallyBody, conversion, this.inlinedFinallies, this.exception);
    }

    @Override // jdk.nashorn.internal.p001ir.JoinPredecessor
    public LocalVariableConversion getLocalVariableConversion() {
        return this.conversion;
    }
}
