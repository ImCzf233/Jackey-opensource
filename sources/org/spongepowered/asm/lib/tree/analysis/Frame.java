package org.spongepowered.asm.lib.tree.analysis;

import com.viaversion.viaversion.libs.javassist.bytecode.Opcode;
import java.util.ArrayList;
import java.util.List;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.IincInsnNode;
import org.spongepowered.asm.lib.tree.InvokeDynamicInsnNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MultiANewArrayInsnNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.lib.tree.analysis.Value;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/tree/analysis/Frame.class */
public class Frame<V extends Value> {
    private V returnValue;
    private V[] values;
    private int locals;
    private int top;

    public Frame(int nLocals, int nStack) {
        this.values = (V[]) new Value[nLocals + nStack];
        this.locals = nLocals;
    }

    public Frame(Frame<? extends V> src) {
        this(src.locals, src.values.length - src.locals);
        init(src);
    }

    public Frame<V> init(Frame<? extends V> src) {
        this.returnValue = src.returnValue;
        System.arraycopy(src.values, 0, this.values, 0, this.values.length);
        this.top = src.top;
        return this;
    }

    public void setReturn(V v) {
        this.returnValue = v;
    }

    public int getLocals() {
        return this.locals;
    }

    public int getMaxStackSize() {
        return this.values.length - this.locals;
    }

    public V getLocal(int i) throws IndexOutOfBoundsException {
        if (i >= this.locals) {
            throw new IndexOutOfBoundsException("Trying to access an inexistant local variable");
        }
        return this.values[i];
    }

    public void setLocal(int i, V value) throws IndexOutOfBoundsException {
        if (i >= this.locals) {
            throw new IndexOutOfBoundsException("Trying to access an inexistant local variable " + i);
        }
        this.values[i] = value;
    }

    public int getStackSize() {
        return this.top;
    }

    public V getStack(int i) throws IndexOutOfBoundsException {
        return this.values[i + this.locals];
    }

    public void clearStack() {
        this.top = 0;
    }

    public V pop() throws IndexOutOfBoundsException {
        if (this.top == 0) {
            throw new IndexOutOfBoundsException("Cannot pop operand off an empty stack.");
        }
        V[] vArr = this.values;
        int i = this.top - 1;
        this.top = i;
        return vArr[i + this.locals];
    }

    public void push(V value) throws IndexOutOfBoundsException {
        if (this.top + this.locals >= this.values.length) {
            throw new IndexOutOfBoundsException("Insufficient maximum stack size.");
        }
        V[] vArr = this.values;
        int i = this.top;
        this.top = i + 1;
        vArr[i + this.locals] = value;
    }

    public void execute(AbstractInsnNode insn, Interpreter<V> interpreter) throws AnalyzerException {
        Value local;
        switch (insn.getOpcode()) {
            case 0:
            case 167:
            case 169:
                return;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
                push(interpreter.newOperation(insn));
                return;
            case 19:
            case 20:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
            case Opcode.WIDE /* 196 */:
            default:
                throw new RuntimeException("Illegal opcode " + insn.getOpcode());
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
                push(interpreter.copyOperation(insn, getLocal(((VarInsnNode) insn).var)));
                return;
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
                V value2 = pop();
                push(interpreter.binaryOperation(insn, pop(), value2));
                return;
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
                V value1 = interpreter.copyOperation(insn, pop());
                int var = ((VarInsnNode) insn).var;
                setLocal(var, value1);
                if (value1.getSize() == 2) {
                    setLocal(var + 1, interpreter.newValue(null));
                }
                if (var > 0 && (local = getLocal(var - 1)) != null && local.getSize() == 2) {
                    setLocal(var - 1, interpreter.newValue(null));
                    return;
                }
                return;
            case 79:
            case 80:
            case 81:
            case 82:
            case 83:
            case 84:
            case 85:
            case 86:
                V value3 = pop();
                V value22 = pop();
                interpreter.ternaryOperation(insn, pop(), value22, value3);
                return;
            case 87:
                if (pop().getSize() == 2) {
                    throw new AnalyzerException(insn, "Illegal use of POP");
                }
                return;
            case 88:
                if (pop().getSize() == 1 && pop().getSize() != 1) {
                    throw new AnalyzerException(insn, "Illegal use of POP2");
                }
                return;
            case 89:
                V value12 = pop();
                if (value12.getSize() != 1) {
                    throw new AnalyzerException(insn, "Illegal use of DUP");
                }
                push(value12);
                push(interpreter.copyOperation(insn, value12));
                return;
            case 90:
                V value13 = pop();
                V value23 = pop();
                if (value13.getSize() != 1 || value23.getSize() != 1) {
                    throw new AnalyzerException(insn, "Illegal use of DUP_X1");
                }
                push(interpreter.copyOperation(insn, value13));
                push(value23);
                push(value13);
                return;
            case 91:
                V value14 = pop();
                if (value14.getSize() == 1) {
                    V value24 = pop();
                    if (value24.getSize() == 1) {
                        V value32 = pop();
                        if (value32.getSize() == 1) {
                            push(interpreter.copyOperation(insn, value14));
                            push(value32);
                            push(value24);
                            push(value14);
                            return;
                        }
                    } else {
                        push(interpreter.copyOperation(insn, value14));
                        push(value24);
                        push(value14);
                        return;
                    }
                }
                throw new AnalyzerException(insn, "Illegal use of DUP_X2");
            case 92:
                V value15 = pop();
                if (value15.getSize() == 1) {
                    V value25 = pop();
                    if (value25.getSize() == 1) {
                        push(value25);
                        push(value15);
                        push(interpreter.copyOperation(insn, value25));
                        push(interpreter.copyOperation(insn, value15));
                        return;
                    }
                    throw new AnalyzerException(insn, "Illegal use of DUP2");
                }
                push(value15);
                push(interpreter.copyOperation(insn, value15));
                return;
            case 93:
                V value16 = pop();
                if (value16.getSize() == 1) {
                    V value26 = pop();
                    if (value26.getSize() == 1) {
                        V value33 = pop();
                        if (value33.getSize() == 1) {
                            push(interpreter.copyOperation(insn, value26));
                            push(interpreter.copyOperation(insn, value16));
                            push(value33);
                            push(value26);
                            push(value16);
                            return;
                        }
                    }
                } else {
                    V value27 = pop();
                    if (value27.getSize() == 1) {
                        push(interpreter.copyOperation(insn, value16));
                        push(value27);
                        push(value16);
                        return;
                    }
                }
                throw new AnalyzerException(insn, "Illegal use of DUP2_X1");
            case 94:
                V value17 = pop();
                if (value17.getSize() == 1) {
                    V value28 = pop();
                    if (value28.getSize() == 1) {
                        V value34 = pop();
                        if (value34.getSize() == 1) {
                            V value4 = pop();
                            if (value4.getSize() == 1) {
                                push(interpreter.copyOperation(insn, value28));
                                push(interpreter.copyOperation(insn, value17));
                                push(value4);
                                push(value34);
                                push(value28);
                                push(value17);
                                return;
                            }
                        } else {
                            push(interpreter.copyOperation(insn, value28));
                            push(interpreter.copyOperation(insn, value17));
                            push(value34);
                            push(value28);
                            push(value17);
                            return;
                        }
                    }
                } else {
                    V value29 = pop();
                    if (value29.getSize() == 1) {
                        V value35 = pop();
                        if (value35.getSize() == 1) {
                            push(interpreter.copyOperation(insn, value17));
                            push(value35);
                            push(value29);
                            push(value17);
                            return;
                        }
                    } else {
                        push(interpreter.copyOperation(insn, value17));
                        push(value29);
                        push(value17);
                        return;
                    }
                }
                throw new AnalyzerException(insn, "Illegal use of DUP2_X2");
            case 95:
                V value210 = pop();
                V value18 = pop();
                if (value18.getSize() != 1 || value210.getSize() != 1) {
                    throw new AnalyzerException(insn, "Illegal use of SWAP");
                }
                push(interpreter.copyOperation(insn, value210));
                push(interpreter.copyOperation(insn, value18));
                return;
            case 96:
            case 97:
            case 98:
            case 99:
            case 100:
            case 101:
            case 102:
            case 103:
            case 104:
            case 105:
            case 106:
            case 107:
            case 108:
            case 109:
            case 110:
            case 111:
            case 112:
            case 113:
            case 114:
            case 115:
                V value211 = pop();
                push(interpreter.binaryOperation(insn, pop(), value211));
                return;
            case 116:
            case 117:
            case 118:
            case 119:
                push(interpreter.unaryOperation(insn, pop()));
                return;
            case 120:
            case 121:
            case 122:
            case 123:
            case 124:
            case 125:
            case 126:
            case 127:
            case 128:
            case 129:
            case 130:
            case 131:
                V value212 = pop();
                push(interpreter.binaryOperation(insn, pop(), value212));
                return;
            case 132:
                int var2 = ((IincInsnNode) insn).var;
                setLocal(var2, interpreter.unaryOperation(insn, getLocal(var2)));
                return;
            case 133:
            case 134:
            case 135:
            case 136:
            case 137:
            case 138:
            case 139:
            case 140:
            case 141:
            case 142:
            case 143:
            case 144:
            case 145:
            case 146:
            case 147:
                push(interpreter.unaryOperation(insn, pop()));
                return;
            case 148:
            case 149:
            case 150:
            case 151:
            case 152:
                V value213 = pop();
                push(interpreter.binaryOperation(insn, pop(), value213));
                return;
            case 153:
            case 154:
            case 155:
            case 156:
            case 157:
            case 158:
                interpreter.unaryOperation(insn, pop());
                return;
            case 159:
            case 160:
            case 161:
            case 162:
            case 163:
            case 164:
            case 165:
            case 166:
                V value214 = pop();
                interpreter.binaryOperation(insn, pop(), value214);
                return;
            case 168:
                push(interpreter.newOperation(insn));
                return;
            case 170:
            case 171:
                interpreter.unaryOperation(insn, pop());
                return;
            case 172:
            case 173:
            case 174:
            case 175:
            case 176:
                V value19 = pop();
                interpreter.unaryOperation(insn, value19);
                interpreter.returnOperation(insn, value19, this.returnValue);
                return;
            case 177:
                if (this.returnValue != null) {
                    throw new AnalyzerException(insn, "Incompatible return type");
                }
                return;
            case 178:
                push(interpreter.newOperation(insn));
                return;
            case 179:
                interpreter.unaryOperation(insn, pop());
                return;
            case 180:
                push(interpreter.unaryOperation(insn, pop()));
                return;
            case 181:
                V value215 = pop();
                interpreter.binaryOperation(insn, pop(), value215);
                return;
            case 182:
            case 183:
            case 184:
            case 185:
                List<V> values = new ArrayList<>();
                String desc = ((MethodInsnNode) insn).desc;
                for (int i = Type.getArgumentTypes(desc).length; i > 0; i--) {
                    values.add(0, pop());
                }
                if (insn.getOpcode() != 184) {
                    values.add(0, pop());
                }
                if (Type.getReturnType(desc) == Type.VOID_TYPE) {
                    interpreter.naryOperation(insn, values);
                    return;
                } else {
                    push(interpreter.naryOperation(insn, values));
                    return;
                }
            case 186:
                List<V> values2 = new ArrayList<>();
                String desc2 = ((InvokeDynamicInsnNode) insn).desc;
                for (int i2 = Type.getArgumentTypes(desc2).length; i2 > 0; i2--) {
                    values2.add(0, pop());
                }
                if (Type.getReturnType(desc2) == Type.VOID_TYPE) {
                    interpreter.naryOperation(insn, values2);
                    return;
                } else {
                    push(interpreter.naryOperation(insn, values2));
                    return;
                }
            case 187:
                push(interpreter.newOperation(insn));
                return;
            case 188:
            case 189:
            case 190:
                push(interpreter.unaryOperation(insn, pop()));
                return;
            case 191:
                interpreter.unaryOperation(insn, pop());
                return;
            case 192:
            case 193:
                push(interpreter.unaryOperation(insn, pop()));
                return;
            case 194:
            case 195:
                interpreter.unaryOperation(insn, pop());
                return;
            case 197:
                List<V> values3 = new ArrayList<>();
                for (int i3 = ((MultiANewArrayInsnNode) insn).dims; i3 > 0; i3--) {
                    values3.add(0, pop());
                }
                push(interpreter.naryOperation(insn, values3));
                return;
            case 198:
            case 199:
                interpreter.unaryOperation(insn, pop());
                return;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean merge(Frame<? extends V> frame, Interpreter<V> interpreter) throws AnalyzerException {
        if (this.top != frame.top) {
            throw new AnalyzerException(null, "Incompatible stack heights");
        }
        boolean changes = false;
        for (int i = 0; i < this.locals + this.top; i++) {
            V v = interpreter.merge(this.values[i], frame.values[i]);
            if (!v.equals(this.values[i])) {
                this.values[i] = v;
                changes = true;
            }
        }
        return changes;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean merge(Frame<? extends V> frame, boolean[] access) {
        boolean changes = false;
        for (int i = 0; i < this.locals; i++) {
            if (!access[i] && !this.values[i].equals(frame.values[i])) {
                this.values[i] = frame.values[i];
                changes = true;
            }
        }
        return changes;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getLocals(); i++) {
            sb.append(getLocal(i));
        }
        sb.append(' ');
        for (int i2 = 0; i2 < getStackSize(); i2++) {
            sb.append(getStack(i2).toString());
        }
        return sb.toString();
    }
}
