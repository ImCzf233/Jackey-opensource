package jdk.nashorn.internal.p001ir;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectOutputStream;
import jdk.nashorn.internal.codegen.CompileUnit;
import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;

@Immutable
/* renamed from: jdk.nashorn.internal.ir.SplitNode */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/SplitNode.class */
public class SplitNode extends LexicalContextStatement implements CompileUnitHolder {
    private static final long serialVersionUID = 1;
    private final String name;
    private final CompileUnit compileUnit;
    private final Block body;

    @Override // jdk.nashorn.internal.p001ir.LexicalContextStatement, jdk.nashorn.internal.p001ir.Node
    public /* bridge */ /* synthetic */ Node accept(NodeVisitor nodeVisitor) {
        return super.accept(nodeVisitor);
    }

    public SplitNode(String name, Block body, CompileUnit compileUnit) {
        super(body.getFirstStatementLineNumber(), body.getToken(), body.getFinish());
        this.name = name;
        this.body = body;
        this.compileUnit = compileUnit;
    }

    private SplitNode(SplitNode splitNode, Block body, CompileUnit compileUnit) {
        super(splitNode);
        this.name = splitNode.name;
        this.body = body;
        this.compileUnit = compileUnit;
    }

    public Block getBody() {
        return this.body;
    }

    private SplitNode setBody(LexicalContext lc, Block body) {
        if (this.body == body) {
            return this;
        }
        return (SplitNode) Node.replaceInLexicalContext(lc, this, new SplitNode(this, body, this.compileUnit));
    }

    @Override // jdk.nashorn.internal.p001ir.LexicalContextNode
    public Node accept(LexicalContext lc, NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterSplitNode(this)) {
            return visitor.leaveSplitNode(setBody(lc, (Block) this.body.accept(visitor)));
        }
        return this;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean printType) {
        sb.append("<split>(");
        sb.append(this.compileUnit.getClass().getSimpleName());
        sb.append(") ");
        this.body.toString(sb, printType);
    }

    public String getName() {
        return this.name;
    }

    @Override // jdk.nashorn.internal.p001ir.CompileUnitHolder
    public CompileUnit getCompileUnit() {
        return this.compileUnit;
    }

    public SplitNode setCompileUnit(LexicalContext lc, CompileUnit compileUnit) {
        if (this.compileUnit == compileUnit) {
            return this;
        }
        return (SplitNode) Node.replaceInLexicalContext(lc, this, new SplitNode(this, this.body, compileUnit));
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        throw new NotSerializableException(getClass().getName());
    }
}
