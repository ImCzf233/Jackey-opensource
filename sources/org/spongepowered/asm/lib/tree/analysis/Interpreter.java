package org.spongepowered.asm.lib.tree.analysis;

import java.util.List;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.analysis.Value;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/tree/analysis/Interpreter.class */
public abstract class Interpreter<V extends Value> {
    protected final int api;

    public abstract V newValue(Type type);

    public abstract V newOperation(AbstractInsnNode abstractInsnNode) throws AnalyzerException;

    public abstract V copyOperation(AbstractInsnNode abstractInsnNode, V v) throws AnalyzerException;

    public abstract V unaryOperation(AbstractInsnNode abstractInsnNode, V v) throws AnalyzerException;

    public abstract V binaryOperation(AbstractInsnNode abstractInsnNode, V v, V v2) throws AnalyzerException;

    public abstract V ternaryOperation(AbstractInsnNode abstractInsnNode, V v, V v2, V v3) throws AnalyzerException;

    public abstract V naryOperation(AbstractInsnNode abstractInsnNode, List<? extends V> list) throws AnalyzerException;

    public abstract void returnOperation(AbstractInsnNode abstractInsnNode, V v, V v2) throws AnalyzerException;

    public abstract V merge(V v, V v2);

    public Interpreter(int api) {
        this.api = api;
    }
}
