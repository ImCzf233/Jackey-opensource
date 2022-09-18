package org.spongepowered.asm.mixin.injection.invoke.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.analysis.Analyzer;
import org.spongepowered.asm.lib.tree.analysis.AnalyzerException;
import org.spongepowered.asm.lib.tree.analysis.BasicInterpreter;
import org.spongepowered.asm.lib.tree.analysis.BasicValue;
import org.spongepowered.asm.lib.tree.analysis.Frame;
import org.spongepowered.asm.lib.tree.analysis.Interpreter;
import org.spongepowered.asm.mixin.injection.struct.Target;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/injection/invoke/util/InsnFinder.class */
public class InsnFinder {
    private static final Logger logger = LogManager.getLogger("mixin");

    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/injection/invoke/util/InsnFinder$AnalyzerState.class */
    public enum AnalyzerState {
        SEARCH,
        ANALYSE,
        COMPLETE
    }

    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/injection/invoke/util/InsnFinder$AnalysisResultException.class */
    public static class AnalysisResultException extends RuntimeException {
        private static final long serialVersionUID = 1;
        private AbstractInsnNode result;

        public AnalysisResultException(AbstractInsnNode popNode) {
            this.result = popNode;
        }

        public AbstractInsnNode getResult() {
            return this.result;
        }
    }

    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/injection/invoke/util/InsnFinder$PopAnalyzer.class */
    public static class PopAnalyzer extends Analyzer<BasicValue> {
        protected final AbstractInsnNode node;

        /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/injection/invoke/util/InsnFinder$PopAnalyzer$PopFrame.class */
        class PopFrame extends Frame<BasicValue> {
            private AbstractInsnNode current;
            private AnalyzerState state = AnalyzerState.SEARCH;
            private int depth = 0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public PopFrame(int locals, int stack) {
                super(locals, stack);
                PopAnalyzer.this = this$0;
            }

            @Override // org.spongepowered.asm.lib.tree.analysis.Frame
            public void execute(AbstractInsnNode insn, Interpreter<BasicValue> interpreter) throws AnalyzerException {
                this.current = insn;
                super.execute(insn, interpreter);
            }

            public void push(BasicValue value) throws IndexOutOfBoundsException {
                if (this.current == PopAnalyzer.this.node && this.state == AnalyzerState.SEARCH) {
                    this.state = AnalyzerState.ANALYSE;
                    this.depth++;
                } else if (this.state == AnalyzerState.ANALYSE) {
                    this.depth++;
                }
                super.push((PopFrame) value);
            }

            @Override // org.spongepowered.asm.lib.tree.analysis.Frame
            public BasicValue pop() throws IndexOutOfBoundsException {
                if (this.state == AnalyzerState.ANALYSE) {
                    int i = this.depth - 1;
                    this.depth = i;
                    if (i == 0) {
                        this.state = AnalyzerState.COMPLETE;
                        throw new AnalysisResultException(this.current);
                    }
                }
                return (BasicValue) super.pop();
            }
        }

        public PopAnalyzer(AbstractInsnNode node) {
            super(new BasicInterpreter());
            this.node = node;
        }

        @Override // org.spongepowered.asm.lib.tree.analysis.Analyzer
        protected Frame<BasicValue> newFrame(int locals, int stack) {
            return new PopFrame(locals, stack);
        }
    }

    public AbstractInsnNode findPopInsn(Target target, AbstractInsnNode node) {
        try {
            new PopAnalyzer(node).analyze(target.classNode.name, target.method);
            return null;
        } catch (AnalyzerException ex) {
            if (ex.getCause() instanceof AnalysisResultException) {
                return ((AnalysisResultException) ex.getCause()).getResult();
            }
            logger.catching(ex);
            return null;
        }
    }
}
