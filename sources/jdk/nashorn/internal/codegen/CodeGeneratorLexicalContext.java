package jdk.nashorn.internal.codegen;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import jdk.nashorn.internal.IntDeque;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.p001ir.Block;
import jdk.nashorn.internal.p001ir.Expression;
import jdk.nashorn.internal.p001ir.FunctionNode;
import jdk.nashorn.internal.p001ir.LexicalContext;
import jdk.nashorn.internal.p001ir.LexicalContextNode;
import jdk.nashorn.internal.p001ir.Node;
import jdk.nashorn.internal.p001ir.Symbol;
import jdk.nashorn.internal.p001ir.WithNode;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/CodeGeneratorLexicalContext.class */
public final class CodeGeneratorLexicalContext extends LexicalContext {
    private int dynamicScopeCount;
    private final Map<SharedScopeCall, SharedScopeCall> scopeCalls = new HashMap();
    private final Deque<CompileUnit> compileUnits = new ArrayDeque();
    private final Deque<MethodEmitter> methodEmitters = new ArrayDeque();
    private final Deque<Expression> discard = new ArrayDeque();
    private final Deque<Map<String, Collection<Label>>> unwarrantedOptimismHandlers = new ArrayDeque();
    private final Deque<StringBuilder> slotTypesDescriptors = new ArrayDeque();
    private final IntDeque splitNodes = new IntDeque();
    private int[] nextFreeSlots = new int[16];
    private int nextFreeSlotsSize;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !CodeGeneratorLexicalContext.class.desiredAssertionStatus();
    }

    private boolean isWithBoundary(Object node) {
        return (node instanceof Block) && !isEmpty() && (peek() instanceof WithNode);
    }

    @Override // jdk.nashorn.internal.p001ir.LexicalContext
    public <T extends LexicalContextNode> T push(T node) {
        if (isWithBoundary(node)) {
            this.dynamicScopeCount++;
        } else if (node instanceof FunctionNode) {
            if (((FunctionNode) node).inDynamicContext()) {
                this.dynamicScopeCount++;
            }
            this.splitNodes.push(0);
        }
        return (T) super.push(node);
    }

    public void enterSplitNode() {
        this.splitNodes.getAndIncrement();
        pushFreeSlots(this.methodEmitters.peek().getUsedSlotsWithLiveTemporaries());
    }

    public void exitSplitNode() {
        int count = this.splitNodes.decrementAndGet();
        if ($assertionsDisabled || count >= 0) {
            return;
        }
        throw new AssertionError();
    }

    @Override // jdk.nashorn.internal.p001ir.LexicalContext
    public <T extends Node> T pop(T node) {
        T popped = (T) super.pop(node);
        if (isWithBoundary(node)) {
            this.dynamicScopeCount--;
            if (!$assertionsDisabled && this.dynamicScopeCount < 0) {
                throw new AssertionError();
            }
        } else if (node instanceof FunctionNode) {
            if (((FunctionNode) node).inDynamicContext()) {
                this.dynamicScopeCount--;
                if (!$assertionsDisabled && this.dynamicScopeCount < 0) {
                    throw new AssertionError();
                }
            }
            if (!$assertionsDisabled && this.splitNodes.peek() != 0) {
                throw new AssertionError();
            }
            this.splitNodes.pop();
        }
        return popped;
    }

    public boolean inDynamicScope() {
        return this.dynamicScopeCount > 0;
    }

    public boolean inSplitNode() {
        return !this.splitNodes.isEmpty() && this.splitNodes.peek() > 0;
    }

    public MethodEmitter pushMethodEmitter(MethodEmitter newMethod) {
        this.methodEmitters.push(newMethod);
        return newMethod;
    }

    public MethodEmitter popMethodEmitter(MethodEmitter oldMethod) {
        if ($assertionsDisabled || this.methodEmitters.peek() == oldMethod) {
            this.methodEmitters.pop();
            if (!this.methodEmitters.isEmpty()) {
                return this.methodEmitters.peek();
            }
            return null;
        }
        throw new AssertionError();
    }

    public void pushUnwarrantedOptimismHandlers() {
        this.unwarrantedOptimismHandlers.push(new HashMap());
        this.slotTypesDescriptors.push(new StringBuilder());
    }

    public Map<String, Collection<Label>> getUnwarrantedOptimismHandlers() {
        return this.unwarrantedOptimismHandlers.peek();
    }

    public Map<String, Collection<Label>> popUnwarrantedOptimismHandlers() {
        this.slotTypesDescriptors.pop();
        return this.unwarrantedOptimismHandlers.pop();
    }

    public CompileUnit pushCompileUnit(CompileUnit newUnit) {
        this.compileUnits.push(newUnit);
        return newUnit;
    }

    public CompileUnit popCompileUnit(CompileUnit oldUnit) {
        if ($assertionsDisabled || this.compileUnits.peek() == oldUnit) {
            CompileUnit unit = this.compileUnits.pop();
            if (!$assertionsDisabled && !unit.hasCode()) {
                throw new AssertionError("compile unit popped without code");
            }
            unit.setUsed();
            if (!this.compileUnits.isEmpty()) {
                return this.compileUnits.peek();
            }
            return null;
        }
        throw new AssertionError();
    }

    public boolean hasCompileUnits() {
        return !this.compileUnits.isEmpty();
    }

    public Collection<SharedScopeCall> getScopeCalls() {
        return Collections.unmodifiableCollection(this.scopeCalls.values());
    }

    public SharedScopeCall getScopeCall(CompileUnit unit, Symbol symbol, Type valueType, Type returnType, Type[] paramTypes, int flags) {
        SharedScopeCall scopeCall = new SharedScopeCall(symbol, valueType, returnType, paramTypes, flags);
        if (this.scopeCalls.containsKey(scopeCall)) {
            return this.scopeCalls.get(scopeCall);
        }
        scopeCall.setClassAndName(unit, getCurrentFunction().uniqueName(":scopeCall"));
        this.scopeCalls.put(scopeCall, scopeCall);
        return scopeCall;
    }

    public SharedScopeCall getScopeGet(CompileUnit unit, Symbol symbol, Type valueType, int flags) {
        return getScopeCall(unit, symbol, valueType, valueType, null, flags);
    }

    public void onEnterBlock(Block block) {
        pushFreeSlots(assignSlots(block, isFunctionBody() ? 0 : getUsedSlotCount()));
    }

    private void pushFreeSlots(int freeSlots) {
        if (this.nextFreeSlotsSize == this.nextFreeSlots.length) {
            int[] newNextFreeSlots = new int[this.nextFreeSlotsSize * 2];
            System.arraycopy(this.nextFreeSlots, 0, newNextFreeSlots, 0, this.nextFreeSlotsSize);
            this.nextFreeSlots = newNextFreeSlots;
        }
        int[] iArr = this.nextFreeSlots;
        int i = this.nextFreeSlotsSize;
        this.nextFreeSlotsSize = i + 1;
        iArr[i] = freeSlots;
    }

    public int getUsedSlotCount() {
        return this.nextFreeSlots[this.nextFreeSlotsSize - 1];
    }

    public void releaseSlots() {
        this.nextFreeSlotsSize--;
        int undefinedFromSlot = this.nextFreeSlotsSize == 0 ? 0 : this.nextFreeSlots[this.nextFreeSlotsSize - 1];
        if (!this.slotTypesDescriptors.isEmpty()) {
            this.slotTypesDescriptors.peek().setLength(undefinedFromSlot);
        }
        this.methodEmitters.peek().undefineLocalVariables(undefinedFromSlot, false);
    }

    private int assignSlots(Block block, int firstSlot) {
        int fromSlot = firstSlot;
        MethodEmitter method = this.methodEmitters.peek();
        for (Symbol symbol : block.getSymbols()) {
            if (symbol.hasSlot()) {
                symbol.setFirstSlot(fromSlot);
                int toSlot = fromSlot + symbol.slotCount();
                method.defineBlockLocalVariable(fromSlot, toSlot);
                fromSlot = toSlot;
            }
        }
        return fromSlot;
    }

    public static Type getTypeForSlotDescriptor(char typeDesc) {
        switch (typeDesc) {
            case 'A':
            case 'a':
                return Type.OBJECT;
            case 'D':
            case 'd':
                return Type.NUMBER;
            case 'I':
            case 'i':
                return Type.INT;
            case 'J':
            case 'j':
                return Type.LONG;
            case 'U':
            case 'u':
                return Type.UNKNOWN;
            default:
                throw new AssertionError();
        }
    }

    public void pushDiscard(Expression expr) {
        this.discard.push(expr);
    }

    public boolean popDiscardIfCurrent(Expression expr) {
        if (isCurrentDiscard(expr)) {
            this.discard.pop();
            return true;
        }
        return false;
    }

    public boolean isCurrentDiscard(Expression expr) {
        return this.discard.peek() == expr;
    }

    public int quickSlot(Type type) {
        return this.methodEmitters.peek().defineTemporaryLocalVariable(type.getSlots());
    }
}
