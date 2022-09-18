package org.spongepowered.asm.lib.tree.analysis;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.InvokeDynamicInsnNode;
import org.spongepowered.asm.lib.tree.LdcInsnNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/tree/analysis/SourceInterpreter.class */
public class SourceInterpreter extends Interpreter<SourceValue> implements Opcodes {
    public SourceInterpreter() {
        super(Opcodes.ASM5);
    }

    protected SourceInterpreter(int api) {
        super(api);
    }

    @Override // org.spongepowered.asm.lib.tree.analysis.Interpreter
    public SourceValue newValue(Type type) {
        if (type == Type.VOID_TYPE) {
            return null;
        }
        return new SourceValue(type == null ? 1 : type.getSize());
    }

    @Override // org.spongepowered.asm.lib.tree.analysis.Interpreter
    public SourceValue newOperation(AbstractInsnNode insn) {
        int size;
        switch (insn.getOpcode()) {
            case 9:
            case 10:
            case 14:
            case 15:
                size = 2;
                break;
            case 18:
                Object cst = ((LdcInsnNode) insn).cst;
                size = ((cst instanceof Long) || (cst instanceof Double)) ? 2 : 1;
                break;
            case 178:
                size = Type.getType(((FieldInsnNode) insn).desc).getSize();
                break;
            default:
                size = 1;
                break;
        }
        return new SourceValue(size, insn);
    }

    public SourceValue copyOperation(AbstractInsnNode insn, SourceValue value) {
        return new SourceValue(value.getSize(), insn);
    }

    public SourceValue unaryOperation(AbstractInsnNode insn, SourceValue value) {
        int size;
        switch (insn.getOpcode()) {
            case 117:
            case 119:
            case 133:
            case 135:
            case 138:
            case 140:
            case 141:
            case 143:
                size = 2;
                break;
            case 180:
                size = Type.getType(((FieldInsnNode) insn).desc).getSize();
                break;
            default:
                size = 1;
                break;
        }
        return new SourceValue(size, insn);
    }

    public SourceValue binaryOperation(AbstractInsnNode insn, SourceValue value1, SourceValue value2) {
        int size;
        switch (insn.getOpcode()) {
            case 47:
            case 49:
            case 97:
            case 99:
            case 101:
            case 103:
            case 105:
            case 107:
            case 109:
            case 111:
            case 113:
            case 115:
            case 121:
            case 123:
            case 125:
            case 127:
            case 129:
            case 131:
                size = 2;
                break;
            default:
                size = 1;
                break;
        }
        return new SourceValue(size, insn);
    }

    public SourceValue ternaryOperation(AbstractInsnNode insn, SourceValue value1, SourceValue value2, SourceValue value3) {
        return new SourceValue(1, insn);
    }

    @Override // org.spongepowered.asm.lib.tree.analysis.Interpreter
    public SourceValue naryOperation(AbstractInsnNode insn, List<? extends SourceValue> values) {
        int size;
        int opcode = insn.getOpcode();
        if (opcode == 197) {
            size = 1;
        } else {
            String desc = opcode == 186 ? ((InvokeDynamicInsnNode) insn).desc : ((MethodInsnNode) insn).desc;
            size = Type.getReturnType(desc).getSize();
        }
        return new SourceValue(size, insn);
    }

    public void returnOperation(AbstractInsnNode insn, SourceValue value, SourceValue expected) {
    }

    public SourceValue merge(SourceValue d, SourceValue w) {
        if ((d.insns instanceof SmallSet) && (w.insns instanceof SmallSet)) {
            Set<AbstractInsnNode> s = ((SmallSet) d.insns).union((SmallSet) w.insns);
            if (s == d.insns && d.size == w.size) {
                return d;
            }
            return new SourceValue(Math.min(d.size, w.size), s);
        } else if (d.size != w.size || !d.insns.containsAll(w.insns)) {
            HashSet<AbstractInsnNode> s2 = new HashSet<>();
            s2.addAll(d.insns);
            s2.addAll(w.insns);
            return new SourceValue(Math.min(d.size, w.size), s2);
        } else {
            return d;
        }
    }
}
