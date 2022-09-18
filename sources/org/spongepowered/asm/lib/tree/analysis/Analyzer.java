package org.spongepowered.asm.lib.tree.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jdk.nashorn.internal.runtime.regexp.joni.constants.StackType;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.IincInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.JumpInsnNode;
import org.spongepowered.asm.lib.tree.LabelNode;
import org.spongepowered.asm.lib.tree.LookupSwitchInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.TableSwitchInsnNode;
import org.spongepowered.asm.lib.tree.TryCatchBlockNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.lib.tree.analysis.Value;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/tree/analysis/Analyzer.class */
public class Analyzer<V extends Value> implements Opcodes {
    private final Interpreter<V> interpreter;

    /* renamed from: n */
    private int f423n;
    private InsnList insns;
    private List<TryCatchBlockNode>[] handlers;
    private Frame<V>[] frames;
    private Subroutine[] subroutines;
    private boolean[] queued;
    private int[] queue;
    private int top;

    public Analyzer(Interpreter<V> interpreter) {
        this.interpreter = interpreter;
    }

    public Frame<V>[] analyze(String owner, MethodNode m) throws AnalyzerException {
        Type type;
        if ((m.access & StackType.POS) != 0) {
            this.frames = new Frame[0];
            return this.frames;
        }
        this.f423n = m.instructions.size();
        this.insns = m.instructions;
        this.handlers = new List[this.f423n];
        this.frames = new Frame[this.f423n];
        this.subroutines = new Subroutine[this.f423n];
        this.queued = new boolean[this.f423n];
        this.queue = new int[this.f423n];
        this.top = 0;
        for (int i = 0; i < m.tryCatchBlocks.size(); i++) {
            TryCatchBlockNode tcb = m.tryCatchBlocks.get(i);
            int begin = this.insns.indexOf(tcb.start);
            int end = this.insns.indexOf(tcb.end);
            for (int j = begin; j < end; j++) {
                List<TryCatchBlockNode> insnHandlers = this.handlers[j];
                if (insnHandlers == null) {
                    insnHandlers = new ArrayList<>();
                    this.handlers[j] = insnHandlers;
                }
                insnHandlers.add(tcb);
            }
        }
        Subroutine main = new Subroutine(null, m.maxLocals, null);
        List<AbstractInsnNode> subroutineCalls = new ArrayList<>();
        Map<LabelNode, Subroutine> subroutineHeads = new HashMap<>();
        findSubroutine(0, main, subroutineCalls);
        while (!subroutineCalls.isEmpty()) {
            JumpInsnNode jsr = (JumpInsnNode) subroutineCalls.remove(0);
            Subroutine sub = subroutineHeads.get(jsr.label);
            if (sub == null) {
                Subroutine sub2 = new Subroutine(jsr.label, m.maxLocals, jsr);
                subroutineHeads.put(jsr.label, sub2);
                findSubroutine(this.insns.indexOf(jsr.label), sub2, subroutineCalls);
            } else {
                sub.callers.add(jsr);
            }
        }
        for (int i2 = 0; i2 < this.f423n; i2++) {
            if (this.subroutines[i2] != null && this.subroutines[i2].start == null) {
                this.subroutines[i2] = null;
            }
        }
        Frame<V> current = newFrame(m.maxLocals, m.maxStack);
        Frame<V> handler = newFrame(m.maxLocals, m.maxStack);
        current.setReturn(this.interpreter.newValue(Type.getReturnType(m.desc)));
        Type[] args = Type.getArgumentTypes(m.desc);
        int local = 0;
        if ((m.access & 8) == 0) {
            Type ctype = Type.getObjectType(owner);
            local = 0 + 1;
            current.setLocal(0, this.interpreter.newValue(ctype));
        }
        for (int i3 = 0; i3 < args.length; i3++) {
            int i4 = local;
            local++;
            current.setLocal(i4, this.interpreter.newValue(args[i3]));
            if (args[i3].getSize() == 2) {
                local++;
                current.setLocal(local, this.interpreter.newValue(null));
            }
        }
        while (local < m.maxLocals) {
            int i5 = local;
            local++;
            current.setLocal(i5, this.interpreter.newValue(null));
        }
        merge(0, current, null);
        init(owner, m);
        while (this.top > 0) {
            int[] iArr = this.queue;
            int i6 = this.top - 1;
            this.top = i6;
            int insn = iArr[i6];
            Frame<V> f = this.frames[insn];
            Subroutine subroutine = this.subroutines[insn];
            this.queued[insn] = false;
            AbstractInsnNode insnNode = null;
            try {
                insnNode = m.instructions.get(insn);
                int insnOpcode = insnNode.getOpcode();
                int insnType = insnNode.getType();
                if (insnType == 8 || insnType == 15 || insnType == 14) {
                    merge(insn + 1, f, subroutine);
                    newControlFlowEdge(insn, insn + 1);
                } else {
                    current.init(f).execute(insnNode, this.interpreter);
                    subroutine = subroutine == null ? null : subroutine.copy();
                    if (insnNode instanceof JumpInsnNode) {
                        JumpInsnNode j2 = (JumpInsnNode) insnNode;
                        if (insnOpcode != 167 && insnOpcode != 168) {
                            merge(insn + 1, current, subroutine);
                            newControlFlowEdge(insn, insn + 1);
                        }
                        int jump = this.insns.indexOf(j2.label);
                        if (insnOpcode == 168) {
                            merge(jump, current, new Subroutine(j2.label, m.maxLocals, j2));
                        } else {
                            merge(jump, current, subroutine);
                        }
                        newControlFlowEdge(insn, jump);
                    } else if (insnNode instanceof LookupSwitchInsnNode) {
                        LookupSwitchInsnNode lsi = (LookupSwitchInsnNode) insnNode;
                        int jump2 = this.insns.indexOf(lsi.dflt);
                        merge(jump2, current, subroutine);
                        newControlFlowEdge(insn, jump2);
                        for (int j3 = 0; j3 < lsi.labels.size(); j3++) {
                            LabelNode label = lsi.labels.get(j3);
                            int jump3 = this.insns.indexOf(label);
                            merge(jump3, current, subroutine);
                            newControlFlowEdge(insn, jump3);
                        }
                    } else if (insnNode instanceof TableSwitchInsnNode) {
                        TableSwitchInsnNode tsi = (TableSwitchInsnNode) insnNode;
                        int jump4 = this.insns.indexOf(tsi.dflt);
                        merge(jump4, current, subroutine);
                        newControlFlowEdge(insn, jump4);
                        for (int j4 = 0; j4 < tsi.labels.size(); j4++) {
                            LabelNode label2 = tsi.labels.get(j4);
                            int jump5 = this.insns.indexOf(label2);
                            merge(jump5, current, subroutine);
                            newControlFlowEdge(insn, jump5);
                        }
                    } else if (insnOpcode == 169) {
                        if (subroutine == null) {
                            throw new AnalyzerException(insnNode, "RET instruction outside of a sub routine");
                        }
                        for (int i7 = 0; i7 < subroutine.callers.size(); i7++) {
                            JumpInsnNode caller = subroutine.callers.get(i7);
                            int call = this.insns.indexOf(caller);
                            if (this.frames[call] != null) {
                                merge(call + 1, this.frames[call], current, this.subroutines[call], subroutine.access);
                                newControlFlowEdge(insn, call + 1);
                            }
                        }
                    } else if (insnOpcode != 191 && (insnOpcode < 172 || insnOpcode > 177)) {
                        if (subroutine != null) {
                            if (insnNode instanceof VarInsnNode) {
                                int var = ((VarInsnNode) insnNode).var;
                                subroutine.access[var] = true;
                                if (insnOpcode == 22 || insnOpcode == 24 || insnOpcode == 55 || insnOpcode == 57) {
                                    subroutine.access[var + 1] = true;
                                }
                            } else if (insnNode instanceof IincInsnNode) {
                                subroutine.access[((IincInsnNode) insnNode).var] = true;
                            }
                        }
                        merge(insn + 1, current, subroutine);
                        newControlFlowEdge(insn, insn + 1);
                    }
                }
                List<TryCatchBlockNode> insnHandlers2 = this.handlers[insn];
                if (insnHandlers2 != null) {
                    for (int i8 = 0; i8 < insnHandlers2.size(); i8++) {
                        TryCatchBlockNode tcb2 = insnHandlers2.get(i8);
                        if (tcb2.type == null) {
                            type = Type.getObjectType("java/lang/Throwable");
                        } else {
                            type = Type.getObjectType(tcb2.type);
                        }
                        int jump6 = this.insns.indexOf(tcb2.handler);
                        if (newControlFlowExceptionEdge(insn, tcb2)) {
                            handler.init(f);
                            handler.clearStack();
                            handler.push(this.interpreter.newValue(type));
                            merge(jump6, handler, subroutine);
                        }
                    }
                }
            } catch (AnalyzerException e) {
                throw new AnalyzerException(e.node, "Error at instruction " + insn + ": " + e.getMessage(), e);
            } catch (Exception e2) {
                throw new AnalyzerException(insnNode, "Error at instruction " + insn + ": " + e2.getMessage(), e2);
            }
        }
        return this.frames;
    }

    private void findSubroutine(int insn, Subroutine sub, List<AbstractInsnNode> calls) throws AnalyzerException {
        while (insn >= 0 && insn < this.f423n) {
            if (this.subroutines[insn] != null) {
                return;
            }
            this.subroutines[insn] = sub.copy();
            AbstractInsnNode node = this.insns.get(insn);
            if (node instanceof JumpInsnNode) {
                if (node.getOpcode() == 168) {
                    calls.add(node);
                } else {
                    JumpInsnNode jnode = (JumpInsnNode) node;
                    findSubroutine(this.insns.indexOf(jnode.label), sub, calls);
                }
            } else if (node instanceof TableSwitchInsnNode) {
                TableSwitchInsnNode tsnode = (TableSwitchInsnNode) node;
                findSubroutine(this.insns.indexOf(tsnode.dflt), sub, calls);
                for (int i = tsnode.labels.size() - 1; i >= 0; i--) {
                    LabelNode l = tsnode.labels.get(i);
                    findSubroutine(this.insns.indexOf(l), sub, calls);
                }
            } else if (node instanceof LookupSwitchInsnNode) {
                LookupSwitchInsnNode lsnode = (LookupSwitchInsnNode) node;
                findSubroutine(this.insns.indexOf(lsnode.dflt), sub, calls);
                for (int i2 = lsnode.labels.size() - 1; i2 >= 0; i2--) {
                    LabelNode l2 = lsnode.labels.get(i2);
                    findSubroutine(this.insns.indexOf(l2), sub, calls);
                }
            }
            List<TryCatchBlockNode> insnHandlers = this.handlers[insn];
            if (insnHandlers != null) {
                for (int i3 = 0; i3 < insnHandlers.size(); i3++) {
                    TryCatchBlockNode tcb = insnHandlers.get(i3);
                    findSubroutine(this.insns.indexOf(tcb.handler), sub, calls);
                }
            }
            switch (node.getOpcode()) {
                case 167:
                case 169:
                case 170:
                case 171:
                case 172:
                case 173:
                case 174:
                case 175:
                case 176:
                case 177:
                case 191:
                    return;
                case 168:
                case 178:
                case 179:
                case 180:
                case 181:
                case 182:
                case 183:
                case 184:
                case 185:
                case 186:
                case 187:
                case 188:
                case 189:
                case 190:
                default:
                    insn++;
            }
        }
        throw new AnalyzerException(null, "Execution can fall off end of the code");
    }

    public Frame<V>[] getFrames() {
        return this.frames;
    }

    public List<TryCatchBlockNode> getHandlers(int insn) {
        return this.handlers[insn];
    }

    protected void init(String owner, MethodNode m) throws AnalyzerException {
    }

    protected Frame<V> newFrame(int nLocals, int nStack) {
        return new Frame<>(nLocals, nStack);
    }

    protected Frame<V> newFrame(Frame<? extends V> src) {
        return new Frame<>(src);
    }

    protected void newControlFlowEdge(int insn, int successor) {
    }

    protected boolean newControlFlowExceptionEdge(int insn, int successor) {
        return true;
    }

    protected boolean newControlFlowExceptionEdge(int insn, TryCatchBlockNode tcb) {
        return newControlFlowExceptionEdge(insn, this.insns.indexOf(tcb.handler));
    }

    private void merge(int insn, Frame<V> frame, Subroutine subroutine) throws AnalyzerException {
        boolean changes;
        Frame<V> oldFrame = this.frames[insn];
        Subroutine oldSubroutine = this.subroutines[insn];
        if (oldFrame == null) {
            this.frames[insn] = newFrame(frame);
            changes = true;
        } else {
            changes = oldFrame.merge(frame, this.interpreter);
        }
        if (oldSubroutine == null) {
            if (subroutine != null) {
                this.subroutines[insn] = subroutine.copy();
                changes = true;
            }
        } else if (subroutine != null) {
            changes |= oldSubroutine.merge(subroutine);
        }
        if (changes && !this.queued[insn]) {
            this.queued[insn] = true;
            int[] iArr = this.queue;
            int i = this.top;
            this.top = i + 1;
            iArr[i] = insn;
        }
    }

    private void merge(int insn, Frame<V> beforeJSR, Frame<V> afterRET, Subroutine subroutineBeforeJSR, boolean[] access) throws AnalyzerException {
        boolean changes;
        Frame<V> oldFrame = this.frames[insn];
        Subroutine oldSubroutine = this.subroutines[insn];
        afterRET.merge(beforeJSR, access);
        if (oldFrame == null) {
            this.frames[insn] = newFrame(afterRET);
            changes = true;
        } else {
            changes = oldFrame.merge(afterRET, this.interpreter);
        }
        if (oldSubroutine != null && subroutineBeforeJSR != null) {
            changes |= oldSubroutine.merge(subroutineBeforeJSR);
        }
        if (changes && !this.queued[insn]) {
            this.queued[insn] = true;
            int[] iArr = this.queue;
            int i = this.top;
            this.top = i + 1;
            iArr[i] = insn;
        }
    }
}
