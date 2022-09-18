package jdk.nashorn.internal.p001ir;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import jdk.nashorn.internal.codegen.Label;
import jdk.nashorn.internal.p001ir.LexicalContextNode;
import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;

@Immutable
/* renamed from: jdk.nashorn.internal.ir.Block */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/Block.class */
public class Block extends Node implements BreakableNode, Terminal, Flags<Block> {
    private static final long serialVersionUID = 1;
    protected final List<Statement> statements;
    protected final Map<String, Symbol> symbols;
    private final Label entryLabel;
    private final Label breakLabel;
    protected final int flags;
    private final LocalVariableConversion conversion;
    public static final int NEEDS_SCOPE = 1;
    public static final int IS_TERMINAL = 4;
    public static final int IS_GLOBAL_SCOPE = 8;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !Block.class.desiredAssertionStatus();
    }

    public Block(long token, int finish, Statement... statements) {
        super(token, finish);
        this.statements = Arrays.asList(statements);
        this.symbols = new LinkedHashMap();
        this.entryLabel = new Label("block_entry");
        this.breakLabel = new Label("block_break");
        int len = statements.length;
        this.flags = (len <= 0 || !statements[len - 1].hasTerminalFlags()) ? 0 : 4;
        this.conversion = null;
    }

    public Block(long token, int finish, List<Statement> statements) {
        this(token, finish, (Statement[]) statements.toArray(new Statement[statements.size()]));
    }

    private Block(Block block, int finish, List<Statement> statements, int flags, Map<String, Symbol> symbols, LocalVariableConversion conversion) {
        super(block);
        this.statements = statements;
        this.flags = flags;
        this.symbols = new LinkedHashMap(symbols);
        this.entryLabel = new Label(block.entryLabel);
        this.breakLabel = new Label(block.breakLabel);
        this.finish = finish;
        this.conversion = conversion;
    }

    public boolean isGlobalScope() {
        return getFlag(8);
    }

    public boolean hasSymbols() {
        return !this.symbols.isEmpty();
    }

    public Block replaceSymbols(LexicalContext lc, Map<Symbol, Symbol> replacements) {
        if (this.symbols.isEmpty()) {
            return this;
        }
        LinkedHashMap<String, Symbol> newSymbols = new LinkedHashMap<>(this.symbols);
        for (Map.Entry<String, Symbol> entry : newSymbols.entrySet()) {
            Symbol newSymbol = replacements.get(entry.getValue());
            if (!$assertionsDisabled && newSymbol == null) {
                throw new AssertionError("Missing replacement for " + entry.getKey());
            }
            entry.setValue(newSymbol);
        }
        return (Block) Node.replaceInLexicalContext(lc, this, new Block(this, this.finish, this.statements, this.flags, newSymbols, this.conversion));
    }

    public Block copyWithNewSymbols() {
        return new Block(this, this.finish, this.statements, this.flags, new LinkedHashMap(this.symbols), this.conversion);
    }

    @Override // jdk.nashorn.internal.p001ir.Node, jdk.nashorn.internal.p001ir.BreakableNode
    public Node ensureUniqueLabels(LexicalContext lc) {
        return (Node) Node.replaceInLexicalContext(lc, this, new Block(this, this.finish, this.statements, this.flags, this.symbols, this.conversion));
    }

    @Override // jdk.nashorn.internal.p001ir.LexicalContextNode
    public Node accept(LexicalContext lc, NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterBlock(this)) {
            return visitor.leaveBlock(setStatements(lc, Node.accept(visitor, this.statements)));
        }
        return this;
    }

    public List<Symbol> getSymbols() {
        return this.symbols.isEmpty() ? Collections.emptyList() : Collections.unmodifiableList(new ArrayList(this.symbols.values()));
    }

    public Symbol getExistingSymbol(String name) {
        return this.symbols.get(name);
    }

    public boolean isCatchBlock() {
        return this.statements.size() == 1 && (this.statements.get(0) instanceof CatchNode);
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean printType) {
        for (Node statement : this.statements) {
            statement.toString(sb, printType);
            sb.append(';');
        }
    }

    public boolean printSymbols(PrintWriter stream) {
        List<Symbol> values = new ArrayList<>(this.symbols.values());
        Collections.sort(values, new Comparator<Symbol>() { // from class: jdk.nashorn.internal.ir.Block.1
            public int compare(Symbol s0, Symbol s1) {
                return s0.getName().compareTo(s1.getName());
            }
        });
        for (Symbol symbol : values) {
            symbol.print(stream);
        }
        return !values.isEmpty();
    }

    public Block setIsTerminal(LexicalContext lc, boolean isTerminal) {
        return isTerminal ? setFlag(lc, 4) : clearFlag(lc, 4);
    }

    @Override // jdk.nashorn.internal.p001ir.Flags
    public int getFlags() {
        return this.flags;
    }

    @Override // jdk.nashorn.internal.p001ir.Terminal
    public boolean isTerminal() {
        return getFlag(4);
    }

    public Label getEntryLabel() {
        return this.entryLabel;
    }

    @Override // jdk.nashorn.internal.p001ir.BreakableNode
    public Label getBreakLabel() {
        return this.breakLabel;
    }

    @Override // jdk.nashorn.internal.p001ir.JoinPredecessor
    public Block setLocalVariableConversion(LexicalContext lc, LocalVariableConversion conversion) {
        if (this.conversion == conversion) {
            return this;
        }
        return (Block) Node.replaceInLexicalContext(lc, this, new Block(this, this.finish, this.statements, this.flags, this.symbols, conversion));
    }

    @Override // jdk.nashorn.internal.p001ir.JoinPredecessor
    public LocalVariableConversion getLocalVariableConversion() {
        return this.conversion;
    }

    public List<Statement> getStatements() {
        return Collections.unmodifiableList(this.statements);
    }

    public int getStatementCount() {
        return this.statements.size();
    }

    public int getFirstStatementLineNumber() {
        if (this.statements == null || this.statements.isEmpty()) {
            return -1;
        }
        return this.statements.get(0).getLineNumber();
    }

    public Statement getLastStatement() {
        if (this.statements.isEmpty()) {
            return null;
        }
        return this.statements.get(this.statements.size() - 1);
    }

    public Block setStatements(LexicalContext lc, List<Statement> statements) {
        if (this.statements == statements) {
            return this;
        }
        int lastFinish = 0;
        if (!statements.isEmpty()) {
            lastFinish = statements.get(statements.size() - 1).getFinish();
        }
        return (Block) Node.replaceInLexicalContext(lc, this, new Block(this, Math.max(this.finish, lastFinish), statements, this.flags, this.symbols, this.conversion));
    }

    public void putSymbol(Symbol symbol) {
        this.symbols.put(symbol.getName(), symbol);
    }

    public boolean needsScope() {
        return (this.flags & 1) == 1;
    }

    @Override // jdk.nashorn.internal.p001ir.Flags
    public Block setFlags(LexicalContext lc, int flags) {
        if (this.flags == flags) {
            return this;
        }
        return (Block) Node.replaceInLexicalContext(lc, this, new Block(this, this.finish, this.statements, flags, this.symbols, this.conversion));
    }

    @Override // jdk.nashorn.internal.p001ir.Flags
    public Block clearFlag(LexicalContext lc, int flag) {
        return setFlags(lc, this.flags & (flag ^ (-1)));
    }

    @Override // jdk.nashorn.internal.p001ir.Flags
    public Block setFlag(LexicalContext lc, int flag) {
        return setFlags(lc, this.flags | flag);
    }

    @Override // jdk.nashorn.internal.p001ir.Flags
    public boolean getFlag(int flag) {
        return (this.flags & flag) == flag;
    }

    public Block setNeedsScope(LexicalContext lc) {
        if (needsScope()) {
            return this;
        }
        return (Block) Node.replaceInLexicalContext(lc, this, new Block(this, this.finish, this.statements, this.flags | 1, this.symbols, this.conversion));
    }

    public int nextSlot() {
        int next = 0;
        for (Symbol symbol : getSymbols()) {
            if (symbol.hasSlot()) {
                next += symbol.slotCount();
            }
        }
        return next;
    }

    @Override // jdk.nashorn.internal.p001ir.BreakableNode
    public boolean isBreakableWithoutLabel() {
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.Labels
    public List<Label> getLabels() {
        return Collections.unmodifiableList(Arrays.asList(this.entryLabel, this.breakLabel));
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        return LexicalContextNode.Acceptor.accept(this, visitor);
    }
}
