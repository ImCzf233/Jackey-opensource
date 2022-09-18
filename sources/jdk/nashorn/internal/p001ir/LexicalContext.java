package jdk.nashorn.internal.p001ir;

import java.io.File;
import java.util.Iterator;
import java.util.NoSuchElementException;
import jdk.nashorn.internal.runtime.Debug;
import jdk.nashorn.internal.runtime.Source;

/* renamed from: jdk.nashorn.internal.ir.LexicalContext */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/LexicalContext.class */
public class LexicalContext {
    private LexicalContextNode[] stack = new LexicalContextNode[16];
    private int[] flags = new int[16];

    /* renamed from: sp */
    private int f235sp;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !LexicalContext.class.desiredAssertionStatus();
    }

    public void setFlag(LexicalContextNode node, int flag) {
        if (flag != 0) {
            if (!$assertionsDisabled && flag == 1 && (node instanceof Block)) {
                throw new AssertionError();
            }
            for (int i = this.f235sp - 1; i >= 0; i--) {
                if (this.stack[i] == node) {
                    int[] iArr = this.flags;
                    int i2 = i;
                    iArr[i2] = iArr[i2] | flag;
                    return;
                }
            }
        }
        if (!$assertionsDisabled) {
            throw new AssertionError();
        }
    }

    public void setBlockNeedsScope(Block block) {
        for (int i = this.f235sp - 1; i >= 0; i--) {
            if (this.stack[i] == block) {
                int[] iArr = this.flags;
                int i2 = i;
                iArr[i2] = iArr[i2] | 1;
                for (int j = i - 1; j >= 0; j--) {
                    if (this.stack[j] instanceof FunctionNode) {
                        int[] iArr2 = this.flags;
                        int i3 = j;
                        iArr2[i3] = iArr2[i3] | 128;
                        return;
                    }
                }
                continue;
            }
        }
        if (!$assertionsDisabled) {
            throw new AssertionError();
        }
    }

    public int getFlags(LexicalContextNode node) {
        for (int i = this.f235sp - 1; i >= 0; i--) {
            if (this.stack[i] == node) {
                return this.flags[i];
            }
        }
        throw new AssertionError("flag node not on context stack");
    }

    public Block getFunctionBody(FunctionNode functionNode) {
        for (int i = this.f235sp - 1; i >= 0; i--) {
            if (this.stack[i] == functionNode) {
                return (Block) this.stack[i + 1];
            }
        }
        throw new AssertionError(functionNode.getName() + " not on context stack");
    }

    public Iterator<LexicalContextNode> getAllNodes() {
        return new NodeIterator(this, LexicalContextNode.class);
    }

    public FunctionNode getOutermostFunction() {
        return (FunctionNode) this.stack[0];
    }

    public <T extends LexicalContextNode> T push(T node) {
        if ($assertionsDisabled || !contains(node)) {
            if (this.f235sp == this.stack.length) {
                LexicalContextNode[] newStack = new LexicalContextNode[this.f235sp * 2];
                System.arraycopy(this.stack, 0, newStack, 0, this.f235sp);
                this.stack = newStack;
                int[] newFlags = new int[this.f235sp * 2];
                System.arraycopy(this.flags, 0, newFlags, 0, this.f235sp);
                this.flags = newFlags;
            }
            this.stack[this.f235sp] = node;
            this.flags[this.f235sp] = 0;
            this.f235sp++;
            return node;
        }
        throw new AssertionError();
    }

    public boolean isEmpty() {
        return this.f235sp == 0;
    }

    public int size() {
        return this.f235sp;
    }

    public <T extends Node> T pop(T node) {
        this.f235sp--;
        LexicalContextNode popped = this.stack[this.f235sp];
        this.stack[this.f235sp] = null;
        if (popped instanceof Flags) {
            return (T) ((Flags) popped).setFlag(this, this.flags[this.f235sp]);
        }
        return (T) popped;
    }

    public <T extends LexicalContextNode & Flags<T>> T applyTopFlags(T node) {
        if ($assertionsDisabled || node == peek()) {
            return (T) ((Flags) node).setFlag(this, this.flags[this.f235sp - 1]);
        }
        throw new AssertionError();
    }

    public LexicalContextNode peek() {
        return this.stack[this.f235sp - 1];
    }

    public boolean contains(LexicalContextNode node) {
        for (int i = 0; i < this.f235sp; i++) {
            if (this.stack[i] == node) {
                return true;
            }
        }
        return false;
    }

    public LexicalContextNode replace(LexicalContextNode oldNode, LexicalContextNode newNode) {
        int i = this.f235sp - 1;
        while (true) {
            if (i < 0) {
                break;
            } else if (this.stack[i] != oldNode) {
                i--;
            } else if (!$assertionsDisabled && i != this.f235sp - 1) {
                throw new AssertionError("violation of contract - we always expect to find the replacement node on top of the lexical context stack: " + newNode + " has " + this.stack[i + 1].getClass() + " above it");
            } else {
                this.stack[i] = newNode;
            }
        }
        return newNode;
    }

    public Iterator<Block> getBlocks() {
        return new NodeIterator(this, Block.class);
    }

    public Iterator<FunctionNode> getFunctions() {
        return new NodeIterator(this, FunctionNode.class);
    }

    public Block getParentBlock() {
        Iterator<Block> iter = new NodeIterator<>(Block.class, getCurrentFunction());
        iter.next();
        if (iter.hasNext()) {
            return iter.next();
        }
        return null;
    }

    public LabelNode getCurrentBlockLabelNode() {
        if ($assertionsDisabled || (this.stack[this.f235sp - 1] instanceof Block)) {
            if (this.f235sp < 2) {
                return null;
            }
            LexicalContextNode parent = this.stack[this.f235sp - 2];
            if (!(parent instanceof LabelNode)) {
                return null;
            }
            return (LabelNode) parent;
        }
        throw new AssertionError();
    }

    public Iterator<Block> getAncestorBlocks(Block block) {
        Iterator<Block> iter = getBlocks();
        while (iter.hasNext()) {
            Block b = iter.next();
            if (block == b) {
                return iter;
            }
        }
        throw new AssertionError("Block is not on the current lexical context stack");
    }

    public Iterator<Block> getBlocks(final Block block) {
        final Iterator<Block> iter = getAncestorBlocks(block);
        return new Iterator<Block>() { // from class: jdk.nashorn.internal.ir.LexicalContext.1
            boolean blockReturned = false;

            @Override // java.util.Iterator
            public boolean hasNext() {
                return iter.hasNext() || !this.blockReturned;
            }

            @Override // java.util.Iterator
            public Block next() {
                if (this.blockReturned) {
                    return (Block) iter.next();
                }
                this.blockReturned = true;
                return block;
            }

            @Override // java.util.Iterator
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public FunctionNode getFunction(Block block) {
        Iterator<LexicalContextNode> iter = new NodeIterator<>(this, LexicalContextNode.class);
        while (iter.hasNext()) {
            LexicalContextNode next = iter.next();
            if (next == block) {
                while (iter.hasNext()) {
                    LexicalContextNode next2 = iter.next();
                    if (next2 instanceof FunctionNode) {
                        return (FunctionNode) next2;
                    }
                }
                continue;
            }
        }
        if (!$assertionsDisabled) {
            throw new AssertionError();
        }
        return null;
    }

    public Block getCurrentBlock() {
        return getBlocks().next();
    }

    public FunctionNode getCurrentFunction() {
        for (int i = this.f235sp - 1; i >= 0; i--) {
            if (this.stack[i] instanceof FunctionNode) {
                return (FunctionNode) this.stack[i];
            }
        }
        return null;
    }

    public Block getDefiningBlock(Symbol symbol) {
        String name = symbol.getName();
        Iterator<Block> it = getBlocks();
        while (it.hasNext()) {
            Block next = it.next();
            if (next.getExistingSymbol(name) == symbol) {
                return next;
            }
        }
        throw new AssertionError("Couldn't find symbol " + name + " in the context");
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x003f, code lost:
        if (r0.hasNext() == false) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x0042, code lost:
        r0 = r0.next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0052, code lost:
        if ((r0 instanceof jdk.nashorn.internal.p001ir.FunctionNode) == false) goto L9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x005a, code lost:
        return (jdk.nashorn.internal.p001ir.FunctionNode) r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x007d, code lost:
        throw new java.lang.AssertionError("Defining block for symbol " + r0 + " has no function in the context");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public jdk.nashorn.internal.p001ir.FunctionNode getDefiningFunction(jdk.nashorn.internal.p001ir.Symbol r6) {
        /*
            r5 = this;
            r0 = r6
            java.lang.String r0 = r0.getName()
            r7 = r0
            jdk.nashorn.internal.ir.LexicalContext$NodeIterator r0 = new jdk.nashorn.internal.ir.LexicalContext$NodeIterator
            r1 = r0
            r2 = r5
            java.lang.Class<jdk.nashorn.internal.ir.LexicalContextNode> r3 = jdk.nashorn.internal.p001ir.LexicalContextNode.class
            r1.<init>(r2, r3)
            r8 = r0
        L10:
            r0 = r8
            boolean r0 = r0.hasNext()
            if (r0 == 0) goto L81
            r0 = r8
            java.lang.Object r0 = r0.next()
            jdk.nashorn.internal.ir.LexicalContextNode r0 = (jdk.nashorn.internal.p001ir.LexicalContextNode) r0
            r9 = r0
            r0 = r9
            boolean r0 = r0 instanceof jdk.nashorn.internal.p001ir.Block
            if (r0 == 0) goto L7e
            r0 = r9
            jdk.nashorn.internal.ir.Block r0 = (jdk.nashorn.internal.p001ir.Block) r0
            r1 = r7
            jdk.nashorn.internal.ir.Symbol r0 = r0.getExistingSymbol(r1)
            r1 = r6
            if (r0 != r1) goto L7e
        L39:
            r0 = r8
            boolean r0 = r0.hasNext()
            if (r0 == 0) goto L5e
            r0 = r8
            java.lang.Object r0 = r0.next()
            jdk.nashorn.internal.ir.LexicalContextNode r0 = (jdk.nashorn.internal.p001ir.LexicalContextNode) r0
            r10 = r0
            r0 = r10
            boolean r0 = r0 instanceof jdk.nashorn.internal.p001ir.FunctionNode
            if (r0 == 0) goto L5b
            r0 = r10
            jdk.nashorn.internal.ir.FunctionNode r0 = (jdk.nashorn.internal.p001ir.FunctionNode) r0
            return r0
        L5b:
            goto L39
        L5e:
            java.lang.AssertionError r0 = new java.lang.AssertionError
            r1 = r0
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r3 = r2
            r3.<init>()
            java.lang.String r3 = "Defining block for symbol "
            java.lang.StringBuilder r2 = r2.append(r3)
            r3 = r7
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r3 = " has no function in the context"
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r0
        L7e:
            goto L10
        L81:
            java.lang.AssertionError r0 = new java.lang.AssertionError
            r1 = r0
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r3 = r2
            r3.<init>()
            java.lang.String r3 = "Couldn't find symbol "
            java.lang.StringBuilder r2 = r2.append(r3)
            r3 = r7
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r3 = " in the context"
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.p001ir.LexicalContext.getDefiningFunction(jdk.nashorn.internal.ir.Symbol):jdk.nashorn.internal.ir.FunctionNode");
    }

    public boolean isFunctionBody() {
        return getParentBlock() == null;
    }

    public boolean isSplitBody() {
        return this.f235sp >= 2 && (this.stack[this.f235sp - 1] instanceof Block) && (this.stack[this.f235sp - 2] instanceof SplitNode);
    }

    public FunctionNode getParentFunction(FunctionNode functionNode) {
        Iterator<FunctionNode> iter = new NodeIterator<>(this, FunctionNode.class);
        while (iter.hasNext()) {
            FunctionNode next = iter.next();
            if (next == functionNode) {
                if (!iter.hasNext()) {
                    return null;
                }
                return iter.next();
            }
        }
        if (!$assertionsDisabled) {
            throw new AssertionError();
        }
        return null;
    }

    public int getScopeNestingLevelTo(LexicalContextNode until) {
        LexicalContextNode node;
        if ($assertionsDisabled || until != null) {
            int n = 0;
            Iterator<LexicalContextNode> iter = getAllNodes();
            while (iter.hasNext() && (node = iter.next()) != until) {
                if (!$assertionsDisabled && (node instanceof FunctionNode)) {
                    throw new AssertionError();
                }
                if ((node instanceof WithNode) || ((node instanceof Block) && ((Block) node).needsScope())) {
                    n++;
                }
            }
            return n;
        }
        throw new AssertionError();
    }

    private BreakableNode getBreakable() {
        NodeIterator<BreakableNode> iter = new NodeIterator<>(BreakableNode.class, getCurrentFunction());
        while (iter.hasNext()) {
            BreakableNode next = iter.next();
            if (next.isBreakableWithoutLabel()) {
                return next;
            }
        }
        return null;
    }

    public boolean inLoop() {
        return getCurrentLoop() != null;
    }

    public LoopNode getCurrentLoop() {
        Iterator<LoopNode> iter = new NodeIterator<>(LoopNode.class, getCurrentFunction());
        if (iter.hasNext()) {
            return iter.next();
        }
        return null;
    }

    public BreakableNode getBreakable(String labelName) {
        if (labelName != null) {
            LabelNode foundLabel = findLabel(labelName);
            if (foundLabel != null) {
                BreakableNode breakable = null;
                NodeIterator<BreakableNode> iter = new NodeIterator<>(BreakableNode.class, foundLabel);
                while (iter.hasNext()) {
                    breakable = iter.next();
                }
                return breakable;
            }
            return null;
        }
        return getBreakable();
    }

    private LoopNode getContinueTo() {
        return getCurrentLoop();
    }

    public LoopNode getContinueTo(String labelName) {
        if (labelName != null) {
            LabelNode foundLabel = findLabel(labelName);
            if (foundLabel != null) {
                LoopNode loop = null;
                NodeIterator<LoopNode> iter = new NodeIterator<>(LoopNode.class, foundLabel);
                while (iter.hasNext()) {
                    loop = iter.next();
                }
                return loop;
            }
            return null;
        }
        return getContinueTo();
    }

    public Block getInlinedFinally(String labelName) {
        NodeIterator<TryNode> iter = new NodeIterator<>(this, TryNode.class);
        while (iter.hasNext()) {
            Block inlinedFinally = iter.next().getInlinedFinally(labelName);
            if (inlinedFinally != null) {
                return inlinedFinally;
            }
        }
        return null;
    }

    public TryNode getTryNodeForInlinedFinally(String labelName) {
        NodeIterator<TryNode> iter = new NodeIterator<>(this, TryNode.class);
        while (iter.hasNext()) {
            TryNode tryNode = iter.next();
            if (tryNode.getInlinedFinally(labelName) != null) {
                return tryNode;
            }
        }
        return null;
    }

    public LabelNode findLabel(String name) {
        Iterator<LabelNode> iter = new NodeIterator<>(LabelNode.class, getCurrentFunction());
        while (iter.hasNext()) {
            LabelNode next = iter.next();
            if (next.getLabelName().equals(name)) {
                return next;
            }
        }
        return null;
    }

    public boolean isExternalTarget(SplitNode splitNode, BreakableNode target) {
        int i = this.f235sp;
        while (true) {
            int i2 = i;
            i--;
            if (i2 > 0) {
                LexicalContextNode next = this.stack[i];
                if (next == splitNode) {
                    return true;
                }
                if (next == target) {
                    return false;
                }
                if (next instanceof TryNode) {
                    for (Block inlinedFinally : ((TryNode) next).getInlinedFinallies()) {
                        if (TryNode.getLabelledInlinedFinallyBlock(inlinedFinally) == target) {
                            return false;
                        }
                    }
                    continue;
                }
            } else {
                throw new AssertionError(target + " was expected in lexical context " + this + " but wasn't");
            }
        }
    }

    public boolean inUnprotectedSwitchContext() {
        for (int i = this.f235sp; i > 0; i--) {
            LexicalContextNode next = this.stack[i];
            if (next instanceof Block) {
                return this.stack[i - 1] instanceof SwitchNode;
            }
        }
        return false;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[ ");
        for (int i = 0; i < this.f235sp; i++) {
            Object node = this.stack[i];
            sb.append(node.getClass().getSimpleName());
            sb.append('@');
            sb.append(Debug.m67id(node));
            sb.append(':');
            if (node instanceof FunctionNode) {
                FunctionNode fn = (FunctionNode) node;
                Source source = fn.getSource();
                String src = source.toString();
                if (src.contains(File.pathSeparator)) {
                    src = src.substring(src.lastIndexOf(File.pathSeparator));
                }
                sb.append((src + ' ') + fn.getLineNumber());
            }
            sb.append(' ');
        }
        sb.append(" ==> ]");
        return sb.toString();
    }

    /* renamed from: jdk.nashorn.internal.ir.LexicalContext$NodeIterator */
    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/LexicalContext$NodeIterator.class */
    public class NodeIterator<T extends LexicalContextNode> implements Iterator<T> {
        private int index;
        private T next;
        private final Class<T> clazz;
        private LexicalContextNode until;

        NodeIterator(LexicalContext lexicalContext, Class<T> clazz) {
            this(clazz, null);
        }

        NodeIterator(Class<T> clazz, LexicalContextNode until) {
            LexicalContext.this = r5;
            this.index = r5.f235sp - 1;
            this.clazz = clazz;
            this.until = until;
            this.next = findNext();
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.next != null;
        }

        @Override // java.util.Iterator
        public T next() {
            if (this.next == null) {
                throw new NoSuchElementException();
            }
            T lnext = this.next;
            this.next = findNext();
            return lnext;
        }

        private T findNext() {
            Object node;
            for (int i = this.index; i >= 0 && (node = LexicalContext.this.stack[i]) != this.until; i--) {
                if (this.clazz.isAssignableFrom(node.getClass())) {
                    this.index = i - 1;
                    return (T) node;
                }
            }
            return null;
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
