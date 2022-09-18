package jdk.nashorn.internal.codegen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jdk.nashorn.internal.p001ir.Block;
import jdk.nashorn.internal.p001ir.FunctionNode;
import jdk.nashorn.internal.p001ir.LexicalContext;
import jdk.nashorn.internal.p001ir.LiteralNode;
import jdk.nashorn.internal.p001ir.Node;
import jdk.nashorn.internal.p001ir.ObjectNode;
import jdk.nashorn.internal.p001ir.PropertyNode;
import jdk.nashorn.internal.p001ir.SplitNode;
import jdk.nashorn.internal.p001ir.Splittable;
import jdk.nashorn.internal.p001ir.Statement;
import jdk.nashorn.internal.p001ir.visitor.SimpleNodeVisitor;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import jdk.nashorn.internal.runtime.logging.Loggable;
import jdk.nashorn.internal.runtime.logging.Logger;
import jdk.nashorn.internal.runtime.options.Options;

/* JADX INFO: Access modifiers changed from: package-private */
@Logger(name = "splitter")
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/Splitter.class */
public final class Splitter extends SimpleNodeVisitor implements Loggable {
    private final Compiler compiler;
    private final FunctionNode outermost;
    private final CompileUnit outermostCompileUnit;
    private final Map<Node, Long> weightCache = new HashMap();
    public static final long SPLIT_THRESHOLD;
    private final DebugLogger log;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !Splitter.class.desiredAssertionStatus();
        SPLIT_THRESHOLD = Options.getIntProperty("nashorn.compiler.splitter.threshold", 32768);
    }

    public Splitter(Compiler compiler, FunctionNode functionNode, CompileUnit outermostCompileUnit) {
        this.compiler = compiler;
        this.outermost = functionNode;
        this.outermostCompileUnit = outermostCompileUnit;
        this.log = initLogger(compiler.getContext());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // jdk.nashorn.internal.runtime.logging.Loggable
    public DebugLogger initLogger(Context context) {
        return context.getLogger(getClass());
    }

    @Override // jdk.nashorn.internal.runtime.logging.Loggable
    public DebugLogger getLogger() {
        return this.log;
    }

    public FunctionNode split(FunctionNode fn, boolean top) {
        FunctionNode functionNode;
        FunctionNode functionNode2 = fn;
        this.log.fine("Initiating split of '", functionNode2.getName(), "'");
        long weight = WeighNodes.weigh(functionNode2);
        if ($assertionsDisabled || this.f247lc.isEmpty()) {
            if (weight >= SPLIT_THRESHOLD) {
                this.log.info("Splitting '", functionNode2.getName(), "' as its weight ", Long.valueOf(weight), " exceeds split threshold ", Long.valueOf(SPLIT_THRESHOLD));
                functionNode2 = (FunctionNode) functionNode2.accept(this);
                if (functionNode2.isSplit()) {
                    weight = WeighNodes.weigh(functionNode2, this.weightCache);
                    functionNode2 = functionNode2.setBody(null, functionNode2.getBody().setNeedsScope(null));
                }
                if (weight >= SPLIT_THRESHOLD) {
                    functionNode2 = functionNode2.setBody(null, splitBlock(functionNode2.getBody(), functionNode2)).setFlag((LexicalContext) null, 16);
                    weight = WeighNodes.weigh(functionNode2.getBody(), this.weightCache);
                }
            }
            if (!$assertionsDisabled && functionNode2.getCompileUnit() != null) {
                throw new AssertionError("compile unit already set for " + functionNode2.getName());
            }
            if (top) {
                if (!$assertionsDisabled && this.outermostCompileUnit == null) {
                    throw new AssertionError("outermost compile unit is null");
                }
                functionNode = functionNode2.setCompileUnit(null, this.outermostCompileUnit);
                this.outermostCompileUnit.addWeight(weight + 40);
            } else {
                functionNode = functionNode2.setCompileUnit(null, findUnit(weight));
            }
            Block body = functionNode.getBody();
            final List<FunctionNode> dc = directChildren(functionNode);
            Block newBody = (Block) body.accept(new SimpleNodeVisitor() { // from class: jdk.nashorn.internal.codegen.Splitter.1
                @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
                public boolean enterFunctionNode(FunctionNode nestedFunction) {
                    return dc.contains(nestedFunction);
                }

                @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
                public Node leaveFunctionNode(FunctionNode nestedFunction) {
                    FunctionNode split = new Splitter(Splitter.this.compiler, nestedFunction, Splitter.this.outermostCompileUnit).split(nestedFunction, false);
                    this.f247lc.replace(nestedFunction, split);
                    return split;
                }
            });
            FunctionNode functionNode3 = functionNode.setBody(null, newBody);
            if (!$assertionsDisabled && functionNode3.getCompileUnit() == null) {
                throw new AssertionError();
            }
            return functionNode3;
        }
        throw new AssertionError("LexicalContext not empty");
    }

    private static List<FunctionNode> directChildren(final FunctionNode functionNode) {
        final List<FunctionNode> dc = new ArrayList<>();
        functionNode.accept(new SimpleNodeVisitor() { // from class: jdk.nashorn.internal.codegen.Splitter.2
            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public boolean enterFunctionNode(FunctionNode child) {
                if (child == functionNode) {
                    return true;
                }
                if (this.f247lc.getParentFunction(child) == functionNode) {
                    dc.add(child);
                    return false;
                }
                return false;
            }
        });
        return dc;
    }

    protected CompileUnit findUnit(long weight) {
        return this.compiler.findUnit(weight);
    }

    private Block splitBlock(Block block, FunctionNode function) {
        List<Statement> splits = new ArrayList<>();
        List<Statement> statements = new ArrayList<>();
        long statementsWeight = 0;
        for (Statement statement : block.getStatements()) {
            long weight = WeighNodes.weigh(statement, this.weightCache);
            if ((statementsWeight + weight >= SPLIT_THRESHOLD || statement.isTerminal()) && !statements.isEmpty()) {
                splits.add(createBlockSplitNode(block, function, statements, statementsWeight));
                statements = new ArrayList<>();
                statementsWeight = 0;
            }
            if (statement.isTerminal()) {
                splits.add(statement);
            } else {
                statements.add(statement);
                statementsWeight += weight;
            }
        }
        if (!statements.isEmpty()) {
            splits.add(createBlockSplitNode(block, function, statements, statementsWeight));
        }
        return block.setStatements(this.f247lc, splits);
    }

    private SplitNode createBlockSplitNode(Block parent, FunctionNode function, List<Statement> statements, long weight) {
        long token = parent.getToken();
        int finish = parent.getFinish();
        String name = function.uniqueName(CompilerConstants.SPLIT_PREFIX.symbolName());
        Block newBlock = new Block(token, finish, statements);
        return new SplitNode(name, newBlock, this.compiler.findUnit(weight + 40));
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterBlock(Block block) {
        if (block.isCatchBlock()) {
            return false;
        }
        long weight = WeighNodes.weigh(block, this.weightCache);
        if (weight < SPLIT_THRESHOLD) {
            this.weightCache.put(block, Long.valueOf(weight));
            return false;
        }
        return true;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveBlock(Block block) {
        if ($assertionsDisabled || !block.isCatchBlock()) {
            Block newBlock = block;
            long weight = WeighNodes.weigh(block, this.weightCache);
            if (weight >= SPLIT_THRESHOLD) {
                FunctionNode currentFunction = this.f247lc.getCurrentFunction();
                newBlock = splitBlock(block, currentFunction);
                weight = WeighNodes.weigh(newBlock, this.weightCache);
                this.f247lc.setFlag(currentFunction, 16);
            }
            this.weightCache.put(newBlock, Long.valueOf(weight));
            return newBlock;
        }
        throw new AssertionError();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveLiteralNode(LiteralNode literal) {
        if (WeighNodes.weigh(literal) < SPLIT_THRESHOLD) {
            return literal;
        }
        FunctionNode functionNode = this.f247lc.getCurrentFunction();
        this.f247lc.setFlag(functionNode, 16);
        if (literal instanceof LiteralNode.ArrayLiteralNode) {
            LiteralNode.ArrayLiteralNode arrayLiteralNode = (LiteralNode.ArrayLiteralNode) literal;
            Node[] value = arrayLiteralNode.getValue();
            int[] postsets = arrayLiteralNode.getPostsets();
            List<Splittable.SplitRange> ranges = new ArrayList<>();
            long totalWeight = 0;
            int lo = 0;
            for (int i = 0; i < postsets.length; i++) {
                int postset = postsets[i];
                Node element = value[postset];
                long weight = WeighNodes.weigh(element);
                totalWeight += 2 + weight;
                if (totalWeight >= SPLIT_THRESHOLD) {
                    CompileUnit unit = this.compiler.findUnit(totalWeight - weight);
                    ranges.add(new Splittable.SplitRange(unit, lo, i));
                    lo = i;
                    totalWeight = weight;
                }
            }
            if (lo != postsets.length) {
                CompileUnit unit2 = this.compiler.findUnit(totalWeight);
                ranges.add(new Splittable.SplitRange(unit2, lo, postsets.length));
            }
            return arrayLiteralNode.setSplitRanges(this.f247lc, ranges);
        }
        return literal;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveObjectNode(ObjectNode objectNode) {
        if (WeighNodes.weigh(objectNode) < SPLIT_THRESHOLD) {
            return objectNode;
        }
        FunctionNode functionNode = this.f247lc.getCurrentFunction();
        this.f247lc.setFlag(functionNode, 16);
        List<Splittable.SplitRange> ranges = new ArrayList<>();
        List<PropertyNode> properties = objectNode.getElements();
        boolean isSpillObject = properties.size() > CodeGenerator.OBJECT_SPILL_THRESHOLD;
        long totalWeight = 0;
        int lo = 0;
        for (int i = 0; i < properties.size(); i++) {
            PropertyNode property = properties.get(i);
            boolean isConstant = LiteralNode.isConstant(property.getValue());
            if (!isConstant || !isSpillObject) {
                long weight = isConstant ? 0L : WeighNodes.weigh(property.getValue());
                totalWeight += 2 + weight;
                if (totalWeight >= SPLIT_THRESHOLD) {
                    CompileUnit unit = this.compiler.findUnit(totalWeight - weight);
                    ranges.add(new Splittable.SplitRange(unit, lo, i));
                    lo = i;
                    totalWeight = weight;
                }
            }
        }
        if (lo != properties.size()) {
            CompileUnit unit2 = this.compiler.findUnit(totalWeight);
            ranges.add(new Splittable.SplitRange(unit2, lo, properties.size()));
        }
        return objectNode.setSplitRanges(this.f247lc, ranges);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterFunctionNode(FunctionNode node) {
        return node == this.outermost;
    }
}
