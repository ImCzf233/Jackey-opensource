package jdk.nashorn.internal.p001ir;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import jdk.nashorn.internal.codegen.types.Type;

/* renamed from: jdk.nashorn.internal.ir.OptimisticLexicalContext */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/OptimisticLexicalContext.class */
public class OptimisticLexicalContext extends LexicalContext {
    private final boolean isEnabled;
    private final Deque<List<Assumption>> optimisticAssumptions = new ArrayDeque();

    /* renamed from: jdk.nashorn.internal.ir.OptimisticLexicalContext$Assumption */
    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/OptimisticLexicalContext$Assumption.class */
    public class Assumption {
        Symbol symbol;
        Type type;

        Assumption(Symbol symbol, Type type) {
            OptimisticLexicalContext.this = this$0;
            this.symbol = symbol;
            this.type = type;
        }

        public String toString() {
            return this.symbol.getName() + "=" + this.type;
        }
    }

    public OptimisticLexicalContext(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public boolean isEnabled() {
        return this.isEnabled;
    }

    public void logOptimisticAssumption(Symbol symbol, Type type) {
        if (this.isEnabled) {
            List<Assumption> peek = this.optimisticAssumptions.peek();
            peek.add(new Assumption(symbol, type));
        }
    }

    public List<Assumption> getOptimisticAssumptions() {
        return Collections.unmodifiableList(this.optimisticAssumptions.peek());
    }

    public boolean hasOptimisticAssumptions() {
        return !this.optimisticAssumptions.isEmpty() && !getOptimisticAssumptions().isEmpty();
    }

    @Override // jdk.nashorn.internal.p001ir.LexicalContext
    public <T extends LexicalContextNode> T push(T node) {
        if (this.isEnabled && (node instanceof FunctionNode)) {
            this.optimisticAssumptions.push(new ArrayList());
        }
        return (T) super.push(node);
    }

    @Override // jdk.nashorn.internal.p001ir.LexicalContext
    public <T extends Node> T pop(T node) {
        T popped = (T) super.pop(node);
        if (this.isEnabled && (node instanceof FunctionNode)) {
            this.optimisticAssumptions.pop();
        }
        return popped;
    }
}
