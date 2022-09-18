package jdk.nashorn.internal.p001ir.debug;

import com.github.benmanes.caffeine.cache.LocalCacheFactory;
import java.util.ArrayList;
import java.util.List;
import jdk.nashorn.internal.p001ir.AccessNode;
import jdk.nashorn.internal.p001ir.BinaryNode;
import jdk.nashorn.internal.p001ir.Block;
import jdk.nashorn.internal.p001ir.BlockStatement;
import jdk.nashorn.internal.p001ir.BreakNode;
import jdk.nashorn.internal.p001ir.CallNode;
import jdk.nashorn.internal.p001ir.CaseNode;
import jdk.nashorn.internal.p001ir.CatchNode;
import jdk.nashorn.internal.p001ir.ContinueNode;
import jdk.nashorn.internal.p001ir.EmptyNode;
import jdk.nashorn.internal.p001ir.Expression;
import jdk.nashorn.internal.p001ir.ExpressionStatement;
import jdk.nashorn.internal.p001ir.ForNode;
import jdk.nashorn.internal.p001ir.FunctionNode;
import jdk.nashorn.internal.p001ir.IdentNode;
import jdk.nashorn.internal.p001ir.IfNode;
import jdk.nashorn.internal.p001ir.IndexNode;
import jdk.nashorn.internal.p001ir.JoinPredecessorExpression;
import jdk.nashorn.internal.p001ir.LabelNode;
import jdk.nashorn.internal.p001ir.LiteralNode;
import jdk.nashorn.internal.p001ir.Node;
import jdk.nashorn.internal.p001ir.ObjectNode;
import jdk.nashorn.internal.p001ir.PropertyNode;
import jdk.nashorn.internal.p001ir.ReturnNode;
import jdk.nashorn.internal.p001ir.RuntimeNode;
import jdk.nashorn.internal.p001ir.SplitNode;
import jdk.nashorn.internal.p001ir.Statement;
import jdk.nashorn.internal.p001ir.SwitchNode;
import jdk.nashorn.internal.p001ir.TernaryNode;
import jdk.nashorn.internal.p001ir.ThrowNode;
import jdk.nashorn.internal.p001ir.TryNode;
import jdk.nashorn.internal.p001ir.UnaryNode;
import jdk.nashorn.internal.p001ir.VarNode;
import jdk.nashorn.internal.p001ir.WhileNode;
import jdk.nashorn.internal.p001ir.WithNode;
import jdk.nashorn.internal.p001ir.visitor.SimpleNodeVisitor;
import jdk.nashorn.internal.parser.JSONParser;
import jdk.nashorn.internal.parser.Lexer;
import jdk.nashorn.internal.parser.Parser;
import jdk.nashorn.internal.parser.TokenType;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ParserException;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.Source;
import jdk.nashorn.internal.runtime.regexp.joni.constants.AsmConstants;
import org.apache.log4j.spi.Configurator;
import org.spongepowered.asm.mixin.injection.invoke.arg.ArgsClassGenerator;

/* renamed from: jdk.nashorn.internal.ir.debug.JSONWriter */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/debug/JSONWriter.class */
public final class JSONWriter extends SimpleNodeVisitor {
    private final StringBuilder buf = new StringBuilder();
    private final boolean includeLocation;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !JSONWriter.class.desiredAssertionStatus();
    }

    public static String parse(Context context, String code, String name, boolean includeLoc) {
        Parser parser = new Parser(context.getEnv(), Source.sourceFor(name, code), new Context.ThrowErrorManager(), context.getEnv()._strict, context.getLogger(Parser.class));
        JSONWriter jsonWriter = new JSONWriter(includeLoc);
        try {
            FunctionNode functionNode = parser.parse();
            functionNode.accept(jsonWriter);
            return jsonWriter.getString();
        } catch (ParserException e) {
            e.throwAsEcmaException();
            return null;
        }
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterJoinPredecessorExpression(JoinPredecessorExpression joinPredecessorExpression) {
        Expression expr = joinPredecessorExpression.getExpression();
        if (expr != null) {
            expr.accept(this);
            return false;
        }
        nullValue();
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterDefault(Node node) {
        objectStart();
        location(node);
        return true;
    }

    private boolean leave() {
        objectEnd();
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveDefault(Node node) {
        objectEnd();
        return null;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterAccessNode(AccessNode accessNode) {
        enterDefault(accessNode);
        type("MemberExpression");
        comma();
        property("object");
        accessNode.getBase().accept(this);
        comma();
        property("property", accessNode.getProperty());
        comma();
        property("computed", false);
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterBlock(Block block) {
        enterDefault(block);
        type("BlockStatement");
        comma();
        array("body", block.getStatements());
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterBinaryNode(BinaryNode binaryNode) {
        String name;
        enterDefault(binaryNode);
        if (binaryNode.isAssignment()) {
            name = "AssignmentExpression";
        } else if (binaryNode.isLogical()) {
            name = "LogicalExpression";
        } else {
            name = "BinaryExpression";
        }
        type(name);
        comma();
        property("operator", binaryNode.tokenType().getName());
        comma();
        property("left");
        binaryNode.lhs().accept(this);
        comma();
        property("right");
        binaryNode.rhs().accept(this);
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterBreakNode(BreakNode breakNode) {
        enterDefault(breakNode);
        type("BreakStatement");
        comma();
        String label = breakNode.getLabelName();
        if (label != null) {
            property("label", label);
        } else {
            property("label");
            nullValue();
        }
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterCallNode(CallNode callNode) {
        enterDefault(callNode);
        type("CallExpression");
        comma();
        property("callee");
        callNode.getFunction().accept(this);
        comma();
        array("arguments", callNode.getArgs());
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterCaseNode(CaseNode caseNode) {
        enterDefault(caseNode);
        type("SwitchCase");
        comma();
        Node test = caseNode.getTest();
        property("test");
        if (test != null) {
            test.accept(this);
        } else {
            nullValue();
        }
        comma();
        array("consequent", caseNode.getBody().getStatements());
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterCatchNode(CatchNode catchNode) {
        enterDefault(catchNode);
        type("CatchClause");
        comma();
        property("param");
        catchNode.getException().accept(this);
        comma();
        Node guard = catchNode.getExceptionCondition();
        if (guard != null) {
            property("guard");
            guard.accept(this);
            comma();
        }
        property("body");
        catchNode.getBody().accept(this);
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterContinueNode(ContinueNode continueNode) {
        enterDefault(continueNode);
        type("ContinueStatement");
        comma();
        String label = continueNode.getLabelName();
        if (label != null) {
            property("label", label);
        } else {
            property("label");
            nullValue();
        }
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterEmptyNode(EmptyNode emptyNode) {
        enterDefault(emptyNode);
        type("EmptyStatement");
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterExpressionStatement(ExpressionStatement expressionStatement) {
        Node expression = expressionStatement.getExpression();
        if (expression instanceof RuntimeNode) {
            expression.accept(this);
            return false;
        }
        enterDefault(expressionStatement);
        type("ExpressionStatement");
        comma();
        property("expression");
        expression.accept(this);
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterBlockStatement(BlockStatement blockStatement) {
        enterDefault(blockStatement);
        type("BlockStatement");
        comma();
        property("block");
        blockStatement.getBlock().accept(this);
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterForNode(ForNode forNode) {
        enterDefault(forNode);
        if (forNode.isForIn() || (forNode.isForEach() && forNode.getInit() != null)) {
            type("ForInStatement");
            comma();
            Node init = forNode.getInit();
            if (!$assertionsDisabled && init == null) {
                throw new AssertionError();
            }
            property("left");
            init.accept(this);
            comma();
            Node modify = forNode.getModify();
            if (!$assertionsDisabled && modify == null) {
                throw new AssertionError();
            }
            property("right");
            modify.accept(this);
            comma();
            property("body");
            forNode.getBody().accept(this);
            comma();
            property("each", forNode.isForEach());
        } else {
            type("ForStatement");
            comma();
            Node init2 = forNode.getInit();
            property("init");
            if (init2 != null) {
                init2.accept(this);
            } else {
                nullValue();
            }
            comma();
            Node test = forNode.getTest();
            property("test");
            if (test != null) {
                test.accept(this);
            } else {
                nullValue();
            }
            comma();
            Node update = forNode.getModify();
            property("update");
            if (update != null) {
                update.accept(this);
            } else {
                nullValue();
            }
            comma();
            property("body");
            forNode.getBody().accept(this);
        }
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterFunctionNode(FunctionNode functionNode) {
        String name;
        boolean program = functionNode.isProgram();
        if (program) {
            return emitProgram(functionNode);
        }
        enterDefault(functionNode);
        if (functionNode.isDeclared()) {
            name = "FunctionDeclaration";
        } else {
            name = "FunctionExpression";
        }
        type(name);
        comma();
        property("id");
        FunctionNode.Kind kind = functionNode.getKind();
        if (functionNode.isAnonymous() || kind == FunctionNode.Kind.GETTER || kind == FunctionNode.Kind.SETTER) {
            nullValue();
        } else {
            functionNode.getIdent().accept(this);
        }
        comma();
        array("params", functionNode.getParameters());
        comma();
        arrayStart("defaults");
        arrayEnd();
        comma();
        property("rest");
        nullValue();
        comma();
        property("body");
        functionNode.getBody().accept(this);
        comma();
        property("generator", false);
        comma();
        property("expression", false);
        return leave();
    }

    private boolean emitProgram(FunctionNode functionNode) {
        enterDefault(functionNode);
        type("Program");
        comma();
        List<Statement> stats = functionNode.getBody().getStatements();
        int size = stats.size();
        int idx = 0;
        arrayStart("body");
        for (Node stat : stats) {
            stat.accept(this);
            if (idx != size - 1) {
                comma();
            }
            idx++;
        }
        arrayEnd();
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterIdentNode(IdentNode identNode) {
        enterDefault(identNode);
        String name = identNode.getName();
        if ("this".equals(name)) {
            type("ThisExpression");
        } else {
            type("Identifier");
            comma();
            property("name", identNode.getName());
        }
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterIfNode(IfNode ifNode) {
        enterDefault(ifNode);
        type("IfStatement");
        comma();
        property("test");
        ifNode.getTest().accept(this);
        comma();
        property("consequent");
        ifNode.getPass().accept(this);
        Node elsePart = ifNode.getFail();
        comma();
        property("alternate");
        if (elsePart != null) {
            elsePart.accept(this);
        } else {
            nullValue();
        }
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterIndexNode(IndexNode indexNode) {
        enterDefault(indexNode);
        type("MemberExpression");
        comma();
        property("object");
        indexNode.getBase().accept(this);
        comma();
        property("property");
        indexNode.getIndex().accept(this);
        comma();
        property("computed", true);
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterLabelNode(LabelNode labelNode) {
        enterDefault(labelNode);
        type("LabeledStatement");
        comma();
        property("label", labelNode.getLabelName());
        comma();
        property("body");
        labelNode.getBody().accept(this);
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterLiteralNode(LiteralNode literalNode) {
        enterDefault(literalNode);
        if (literalNode instanceof LiteralNode.ArrayLiteralNode) {
            type("ArrayExpression");
            comma();
            array("elements", ((LiteralNode.ArrayLiteralNode) literalNode).getElementExpressions());
        } else {
            type("Literal");
            comma();
            property("value");
            Object value = literalNode.getValue();
            if (value instanceof Lexer.RegexToken) {
                Lexer.RegexToken regex = (Lexer.RegexToken) value;
                this.buf.append(quote('/' + regex.getExpression() + '/' + regex.getOptions()));
            } else {
                String str = literalNode.getString();
                this.buf.append(literalNode.isString() ? quote(ArgsClassGenerator.GETTER_PREFIX + str) : str);
            }
        }
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterObjectNode(ObjectNode objectNode) {
        enterDefault(objectNode);
        type("ObjectExpression");
        comma();
        array("properties", objectNode.getElements());
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterPropertyNode(PropertyNode propertyNode) {
        Node key = propertyNode.getKey();
        Node value = propertyNode.getValue();
        if (value != null) {
            objectStart();
            location(propertyNode);
            property(LocalCacheFactory.KEY);
            key.accept(this);
            comma();
            property("value");
            value.accept(this);
            comma();
            property("kind", "init");
            objectEnd();
            return false;
        }
        Node getter = propertyNode.getGetter();
        if (getter != null) {
            objectStart();
            location(propertyNode);
            property(LocalCacheFactory.KEY);
            key.accept(this);
            comma();
            property("value");
            getter.accept(this);
            comma();
            property("kind", PropertyDescriptor.GET);
            objectEnd();
        }
        Node setter = propertyNode.getSetter();
        if (setter != null) {
            if (getter != null) {
                comma();
            }
            objectStart();
            location(propertyNode);
            property(LocalCacheFactory.KEY);
            key.accept(this);
            comma();
            property("value");
            setter.accept(this);
            comma();
            property("kind", PropertyDescriptor.SET);
            objectEnd();
            return false;
        }
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterReturnNode(ReturnNode returnNode) {
        enterDefault(returnNode);
        type("ReturnStatement");
        comma();
        Node arg = returnNode.getExpression();
        property("argument");
        if (arg != null) {
            arg.accept(this);
        } else {
            nullValue();
        }
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterRuntimeNode(RuntimeNode runtimeNode) {
        RuntimeNode.Request req = runtimeNode.getRequest();
        if (req == RuntimeNode.Request.DEBUGGER) {
            enterDefault(runtimeNode);
            type("DebuggerStatement");
            return leave();
        }
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterSplitNode(SplitNode splitNode) {
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterSwitchNode(SwitchNode switchNode) {
        enterDefault(switchNode);
        type("SwitchStatement");
        comma();
        property("discriminant");
        switchNode.getExpression().accept(this);
        comma();
        array("cases", switchNode.getCases());
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterTernaryNode(TernaryNode ternaryNode) {
        enterDefault(ternaryNode);
        type("ConditionalExpression");
        comma();
        property("test");
        ternaryNode.getTest().accept(this);
        comma();
        property("consequent");
        ternaryNode.getTrueExpression().accept(this);
        comma();
        property("alternate");
        ternaryNode.getFalseExpression().accept(this);
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterThrowNode(ThrowNode throwNode) {
        enterDefault(throwNode);
        type("ThrowStatement");
        comma();
        property("argument");
        throwNode.getExpression().accept(this);
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterTryNode(TryNode tryNode) {
        enterDefault(tryNode);
        type("TryStatement");
        comma();
        property("block");
        tryNode.getBody().accept(this);
        comma();
        List<? extends Node> catches = tryNode.getCatches();
        List<CatchNode> guarded = new ArrayList<>();
        CatchNode unguarded = null;
        if (catches != null) {
            for (Node n : catches) {
                CatchNode cn = (CatchNode) n;
                if (cn.getExceptionCondition() != null) {
                    guarded.add(cn);
                } else if (!$assertionsDisabled && unguarded != null) {
                    throw new AssertionError("too many unguarded?");
                } else {
                    unguarded = cn;
                }
            }
        }
        array("guardedHandlers", guarded);
        comma();
        property("handler");
        if (unguarded != null) {
            unguarded.accept(this);
        } else {
            nullValue();
        }
        comma();
        property("finalizer");
        Node finallyNode = tryNode.getFinallyBody();
        if (finallyNode != null) {
            finallyNode.accept(this);
        } else {
            nullValue();
        }
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterUnaryNode(UnaryNode unaryNode) {
        boolean prefix;
        String operator;
        enterDefault(unaryNode);
        TokenType tokenType = unaryNode.tokenType();
        if (tokenType == TokenType.NEW) {
            type("NewExpression");
            comma();
            CallNode callNode = (CallNode) unaryNode.getExpression();
            property("callee");
            callNode.getFunction().accept(this);
            comma();
            array("arguments", callNode.getArgs());
        } else {
            switch (tokenType) {
                case INCPOSTFIX:
                    prefix = false;
                    operator = "++";
                    break;
                case DECPOSTFIX:
                    prefix = false;
                    operator = "--";
                    break;
                case INCPREFIX:
                    operator = "++";
                    prefix = true;
                    break;
                case DECPREFIX:
                    operator = "--";
                    prefix = true;
                    break;
                default:
                    prefix = true;
                    operator = tokenType.getName();
                    break;
            }
            type(unaryNode.isAssignment() ? "UpdateExpression" : "UnaryExpression");
            comma();
            property("operator", operator);
            comma();
            property("prefix", prefix);
            comma();
            property("argument");
            unaryNode.getExpression().accept(this);
        }
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterVarNode(VarNode varNode) {
        Node init = varNode.getInit();
        if ((init instanceof FunctionNode) && ((FunctionNode) init).isDeclared()) {
            init.accept(this);
            return false;
        }
        enterDefault(varNode);
        type("VariableDeclaration");
        comma();
        arrayStart("declarations");
        objectStart();
        location(varNode.getName());
        type("VariableDeclarator");
        comma();
        property("id");
        varNode.getName().accept(this);
        comma();
        property("init");
        if (init != null) {
            init.accept(this);
        } else {
            nullValue();
        }
        objectEnd();
        arrayEnd();
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterWhileNode(WhileNode whileNode) {
        enterDefault(whileNode);
        type(whileNode.isDoWhile() ? "DoWhileStatement" : "WhileStatement");
        comma();
        if (whileNode.isDoWhile()) {
            property("body");
            whileNode.getBody().accept(this);
            comma();
            property("test");
            whileNode.getTest().accept(this);
        } else {
            property("test");
            whileNode.getTest().accept(this);
            comma();
            property("body");
            whileNode.getBody().accept(this);
        }
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterWithNode(WithNode withNode) {
        enterDefault(withNode);
        type("WithStatement");
        comma();
        property("object");
        withNode.getExpression().accept(this);
        comma();
        property("body");
        withNode.getBody().accept(this);
        return leave();
    }

    private JSONWriter(boolean includeLocation) {
        this.includeLocation = includeLocation;
    }

    private String getString() {
        return this.buf.toString();
    }

    private void property(String key, String value, boolean escape) {
        this.buf.append('\"');
        this.buf.append(key);
        this.buf.append("\":");
        if (value != null) {
            if (escape) {
                this.buf.append('\"');
            }
            this.buf.append(value);
            if (escape) {
                this.buf.append('\"');
            }
        }
    }

    private void property(String key, String value) {
        property(key, value, true);
    }

    private void property(String key, boolean value) {
        property(key, Boolean.toString(value), false);
    }

    private void property(String key, int value) {
        property(key, Integer.toString(value), false);
    }

    private void property(String key) {
        property(key, (String) null);
    }

    private void type(String value) {
        property("type", value);
    }

    private void objectStart(String name) {
        this.buf.append('\"');
        this.buf.append(name);
        this.buf.append("\":{");
    }

    private void objectStart() {
        this.buf.append('{');
    }

    private void objectEnd() {
        this.buf.append('}');
    }

    private void array(String name, List<? extends Node> nodes) {
        int size = nodes.size();
        int idx = 0;
        arrayStart(name);
        for (Node node : nodes) {
            if (node != null) {
                node.accept(this);
            } else {
                nullValue();
            }
            if (idx != size - 1) {
                comma();
            }
            idx++;
        }
        arrayEnd();
    }

    private void arrayStart(String name) {
        this.buf.append('\"');
        this.buf.append(name);
        this.buf.append('\"');
        this.buf.append(':');
        this.buf.append('[');
    }

    private void arrayEnd() {
        this.buf.append(']');
    }

    private void comma() {
        this.buf.append(',');
    }

    private void nullValue() {
        this.buf.append(Configurator.NULL);
    }

    private void location(Node node) {
        if (this.includeLocation) {
            objectStart("loc");
            Source src = this.f247lc.getCurrentFunction().getSource();
            property("source", src.getName());
            comma();
            objectStart("start");
            int start = node.getStart();
            property("line", src.getLine(start));
            comma();
            property("column", src.getColumn(start));
            objectEnd();
            comma();
            objectStart(AsmConstants.END);
            int end = node.getFinish();
            property("line", src.getLine(end));
            comma();
            property("column", src.getColumn(end));
            objectEnd();
            objectEnd();
            comma();
        }
    }

    private static String quote(String str) {
        return JSONParser.quote(str);
    }
}
