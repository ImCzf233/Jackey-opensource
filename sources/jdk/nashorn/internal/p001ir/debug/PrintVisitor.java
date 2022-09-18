package jdk.nashorn.internal.p001ir.debug;

import java.util.List;
import jdk.nashorn.internal.p001ir.BinaryNode;
import jdk.nashorn.internal.p001ir.Block;
import jdk.nashorn.internal.p001ir.BlockStatement;
import jdk.nashorn.internal.p001ir.BreakNode;
import jdk.nashorn.internal.p001ir.CaseNode;
import jdk.nashorn.internal.p001ir.CatchNode;
import jdk.nashorn.internal.p001ir.ContinueNode;
import jdk.nashorn.internal.p001ir.ExpressionStatement;
import jdk.nashorn.internal.p001ir.ForNode;
import jdk.nashorn.internal.p001ir.FunctionNode;
import jdk.nashorn.internal.p001ir.IdentNode;
import jdk.nashorn.internal.p001ir.IfNode;
import jdk.nashorn.internal.p001ir.JoinPredecessor;
import jdk.nashorn.internal.p001ir.JoinPredecessorExpression;
import jdk.nashorn.internal.p001ir.LabelNode;
import jdk.nashorn.internal.p001ir.LocalVariableConversion;
import jdk.nashorn.internal.p001ir.Node;
import jdk.nashorn.internal.p001ir.SplitNode;
import jdk.nashorn.internal.p001ir.Statement;
import jdk.nashorn.internal.p001ir.SwitchNode;
import jdk.nashorn.internal.p001ir.ThrowNode;
import jdk.nashorn.internal.p001ir.TryNode;
import jdk.nashorn.internal.p001ir.UnaryNode;
import jdk.nashorn.internal.p001ir.VarNode;
import jdk.nashorn.internal.p001ir.WhileNode;
import jdk.nashorn.internal.p001ir.WithNode;
import jdk.nashorn.internal.p001ir.visitor.SimpleNodeVisitor;

/* renamed from: jdk.nashorn.internal.ir.debug.PrintVisitor */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/debug/PrintVisitor.class */
public final class PrintVisitor extends SimpleNodeVisitor {
    private static final int TABWIDTH = 4;

    /* renamed from: sb */
    private final StringBuilder f246sb;
    private int indent;
    private final String EOLN;
    private final boolean printLineNumbers;
    private final boolean printTypes;
    private int lastLineNumber;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !PrintVisitor.class.desiredAssertionStatus();
    }

    public PrintVisitor() {
        this(true, true);
    }

    public PrintVisitor(boolean printLineNumbers, boolean printTypes) {
        this.lastLineNumber = -1;
        this.EOLN = System.lineSeparator();
        this.f246sb = new StringBuilder();
        this.printLineNumbers = printLineNumbers;
        this.printTypes = printTypes;
    }

    public PrintVisitor(Node root) {
        this(root, true, true);
    }

    public PrintVisitor(Node root, boolean printLineNumbers, boolean printTypes) {
        this(printLineNumbers, printTypes);
        visit(root);
    }

    private void visit(Node root) {
        root.accept(this);
    }

    public String toString() {
        return this.f246sb.append(this.EOLN).toString();
    }

    private void indent() {
        for (int i = this.indent; i > 0; i--) {
            this.f246sb.append(' ');
        }
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterDefault(Node node) {
        node.toString(this.f246sb, this.printTypes);
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterContinueNode(ContinueNode node) {
        node.toString(this.f246sb, this.printTypes);
        printLocalVariableConversion(node);
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterBreakNode(BreakNode node) {
        node.toString(this.f246sb, this.printTypes);
        printLocalVariableConversion(node);
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterThrowNode(ThrowNode node) {
        node.toString(this.f246sb, this.printTypes);
        printLocalVariableConversion(node);
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterBlock(Block block) {
        char lastChar;
        this.f246sb.append(' ');
        this.f246sb.append('{');
        this.indent += 4;
        List<Statement> statements = block.getStatements();
        for (Statement statement : statements) {
            if (this.printLineNumbers) {
                int lineNumber = statement.getLineNumber();
                this.f246sb.append('\n');
                if (lineNumber != this.lastLineNumber) {
                    indent();
                    this.f246sb.append("[|").append(lineNumber).append("|];").append('\n');
                }
                this.lastLineNumber = lineNumber;
            }
            indent();
            statement.accept(this);
            int lastIndex = this.f246sb.length() - 1;
            char charAt = this.f246sb.charAt(lastIndex);
            while (true) {
                lastChar = charAt;
                if (!Character.isWhitespace(lastChar) || lastIndex < 0) {
                    break;
                }
                lastIndex--;
                charAt = this.f246sb.charAt(lastIndex);
            }
            if (lastChar != '}' && lastChar != ';') {
                this.f246sb.append(';');
            }
            if (statement.hasGoto()) {
                this.f246sb.append(" [GOTO]");
            }
            if (statement.isTerminal()) {
                this.f246sb.append(" [TERMINAL]");
            }
        }
        this.indent -= 4;
        this.f246sb.append(this.EOLN);
        indent();
        this.f246sb.append('}');
        printLocalVariableConversion(block);
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterBlockStatement(BlockStatement statement) {
        statement.getBlock().accept(this);
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterBinaryNode(BinaryNode binaryNode) {
        binaryNode.lhs().accept(this);
        this.f246sb.append(' ');
        this.f246sb.append(binaryNode.tokenType());
        this.f246sb.append(' ');
        binaryNode.rhs().accept(this);
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterJoinPredecessorExpression(JoinPredecessorExpression expr) {
        expr.getExpression().accept(this);
        printLocalVariableConversion(expr);
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterIdentNode(IdentNode identNode) {
        identNode.toString(this.f246sb, this.printTypes);
        printLocalVariableConversion(identNode);
        return true;
    }

    private void printLocalVariableConversion(JoinPredecessor joinPredecessor) {
        LocalVariableConversion.toString(joinPredecessor.getLocalVariableConversion(), this.f246sb);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterUnaryNode(final UnaryNode unaryNode) {
        unaryNode.toString(this.f246sb, new Runnable() { // from class: jdk.nashorn.internal.ir.debug.PrintVisitor.1
            @Override // java.lang.Runnable
            public void run() {
                unaryNode.getExpression().accept(PrintVisitor.this);
            }
        }, this.printTypes);
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterExpressionStatement(ExpressionStatement expressionStatement) {
        expressionStatement.getExpression().accept(this);
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterForNode(ForNode forNode) {
        forNode.toString(this.f246sb, this.printTypes);
        forNode.getBody().accept(this);
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterFunctionNode(FunctionNode functionNode) {
        functionNode.toString(this.f246sb, this.printTypes);
        enterBlock(functionNode.getBody());
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterIfNode(IfNode ifNode) {
        ifNode.toString(this.f246sb, this.printTypes);
        ifNode.getPass().accept(this);
        Block fail = ifNode.getFail();
        if (fail != null) {
            this.f246sb.append(" else ");
            fail.accept(this);
        }
        if (ifNode.getLocalVariableConversion() != null) {
            if (!$assertionsDisabled && fail != null) {
                throw new AssertionError();
            }
            this.f246sb.append(" else ");
            printLocalVariableConversion(ifNode);
            this.f246sb.append(";");
            return false;
        }
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterLabelNode(LabelNode labeledNode) {
        this.indent -= 4;
        indent();
        this.indent += 4;
        labeledNode.toString(this.f246sb, this.printTypes);
        labeledNode.getBody().accept(this);
        printLocalVariableConversion(labeledNode);
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterSplitNode(SplitNode splitNode) {
        splitNode.toString(this.f246sb, this.printTypes);
        this.f246sb.append(this.EOLN);
        this.indent += 4;
        indent();
        return true;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveSplitNode(SplitNode splitNode) {
        this.f246sb.append("</split>");
        this.f246sb.append(this.EOLN);
        this.indent -= 4;
        indent();
        return splitNode;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterSwitchNode(SwitchNode switchNode) {
        switchNode.toString(this.f246sb, this.printTypes);
        this.f246sb.append(" {");
        List<CaseNode> cases = switchNode.getCases();
        for (CaseNode caseNode : cases) {
            this.f246sb.append(this.EOLN);
            indent();
            caseNode.toString(this.f246sb, this.printTypes);
            printLocalVariableConversion(caseNode);
            this.indent += 4;
            caseNode.getBody().accept(this);
            this.indent -= 4;
            this.f246sb.append(this.EOLN);
        }
        if (switchNode.getLocalVariableConversion() != null) {
            this.f246sb.append(this.EOLN);
            indent();
            this.f246sb.append("default: ");
            printLocalVariableConversion(switchNode);
            this.f246sb.append("{}");
        }
        this.f246sb.append(this.EOLN);
        indent();
        this.f246sb.append("}");
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterTryNode(TryNode tryNode) {
        tryNode.toString(this.f246sb, this.printTypes);
        printLocalVariableConversion(tryNode);
        tryNode.getBody().accept(this);
        List<Block> catchBlocks = tryNode.getCatchBlocks();
        for (Block catchBlock : catchBlocks) {
            CatchNode catchNode = (CatchNode) catchBlock.getStatements().get(0);
            catchNode.toString(this.f246sb, this.printTypes);
            catchNode.getBody().accept(this);
        }
        Block finallyBody = tryNode.getFinallyBody();
        if (finallyBody != null) {
            this.f246sb.append(" finally ");
            finallyBody.accept(this);
        }
        for (Block inlinedFinally : tryNode.getInlinedFinallies()) {
            inlinedFinally.accept(this);
        }
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterVarNode(VarNode varNode) {
        this.f246sb.append(varNode.isConst() ? "const " : varNode.isLet() ? "let " : "var ");
        varNode.getName().toString(this.f246sb, this.printTypes);
        printLocalVariableConversion(varNode.getName());
        Node init = varNode.getInit();
        if (init != null) {
            this.f246sb.append(" = ");
            init.accept(this);
            return false;
        }
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterWhileNode(WhileNode whileNode) {
        printLocalVariableConversion(whileNode);
        if (whileNode.isDoWhile()) {
            this.f246sb.append("do");
            whileNode.getBody().accept(this);
            this.f246sb.append(' ');
            whileNode.toString(this.f246sb, this.printTypes);
            return false;
        }
        whileNode.toString(this.f246sb, this.printTypes);
        whileNode.getBody().accept(this);
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterWithNode(WithNode withNode) {
        withNode.toString(this.f246sb, this.printTypes);
        withNode.getBody().accept(this);
        return false;
    }
}
