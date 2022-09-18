package jdk.nashorn.internal.p001ir;

import java.util.List;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;

/* renamed from: jdk.nashorn.internal.ir.BlockStatement */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/BlockStatement.class */
public class BlockStatement extends Statement {
    private static final long serialVersionUID = 1;
    private final Block block;

    public BlockStatement(Block block) {
        this(block.getFirstStatementLineNumber(), block);
    }

    public BlockStatement(int lineNumber, Block block) {
        super(lineNumber, block.getToken(), block.getFinish());
        this.block = block;
    }

    private BlockStatement(BlockStatement blockStatement, Block block) {
        super(blockStatement);
        this.block = block;
    }

    public static BlockStatement createReplacement(Statement stmt, List<Statement> newStmts) {
        return createReplacement(stmt, stmt.getFinish(), newStmts);
    }

    public static BlockStatement createReplacement(Statement stmt, int finish, List<Statement> newStmts) {
        return new BlockStatement(stmt.getLineNumber(), new Block(stmt.getToken(), finish, newStmts));
    }

    @Override // jdk.nashorn.internal.p001ir.Statement, jdk.nashorn.internal.p001ir.Terminal
    public boolean isTerminal() {
        return this.block.isTerminal();
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterBlockStatement(this)) {
            return visitor.leaveBlockStatement(setBlock((Block) this.block.accept(visitor)));
        }
        return this;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean printType) {
        this.block.toString(sb, printType);
    }

    public Block getBlock() {
        return this.block;
    }

    public BlockStatement setBlock(Block block) {
        if (this.block == block) {
            return this;
        }
        return new BlockStatement(this, block);
    }
}
