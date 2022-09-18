package com.viaversion.viaversion.libs.javassist.bytecode.analysis;

import com.viaversion.viaversion.libs.javassist.ClassPool;
import com.viaversion.viaversion.libs.javassist.CtClass;
import com.viaversion.viaversion.libs.javassist.NotFoundException;
import com.viaversion.viaversion.libs.javassist.bytecode.BadBytecode;
import com.viaversion.viaversion.libs.javassist.bytecode.CodeIterator;
import com.viaversion.viaversion.libs.javassist.bytecode.ConstPool;
import com.viaversion.viaversion.libs.javassist.bytecode.Descriptor;
import com.viaversion.viaversion.libs.javassist.bytecode.MethodInfo;
import com.viaversion.viaversion.libs.javassist.bytecode.Opcode;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/bytecode/analysis/Executor.class */
public class Executor implements Opcode {
    private final ConstPool constPool;
    private final ClassPool classPool;
    private final Type STRING_TYPE;
    private final Type CLASS_TYPE;
    private final Type THROWABLE_TYPE;
    private int lastPos;

    public Executor(ClassPool classPool, ConstPool constPool) {
        this.constPool = constPool;
        this.classPool = classPool;
        try {
            this.STRING_TYPE = getType("java.lang.String");
            this.CLASS_TYPE = getType("java.lang.Class");
            this.THROWABLE_TYPE = getType("java.lang.Throwable");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void execute(MethodInfo method, int pos, CodeIterator iter, Frame frame, Subroutine subroutine) throws BadBytecode {
        this.lastPos = pos;
        int opcode = iter.byteAt(pos);
        switch (opcode) {
            case 0:
            case 167:
            case 177:
            case 200:
            default:
                return;
            case 1:
                frame.push(Type.UNINIT);
                return;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                frame.push(Type.INTEGER);
                return;
            case 9:
            case 10:
                frame.push(Type.LONG);
                frame.push(Type.TOP);
                return;
            case 11:
            case 12:
            case 13:
                frame.push(Type.FLOAT);
                return;
            case 14:
            case 15:
                frame.push(Type.DOUBLE);
                frame.push(Type.TOP);
                return;
            case 16:
            case 17:
                frame.push(Type.INTEGER);
                return;
            case 18:
                evalLDC(iter.byteAt(pos + 1), frame);
                return;
            case 19:
            case 20:
                evalLDC(iter.u16bitAt(pos + 1), frame);
                return;
            case 21:
                evalLoad(Type.INTEGER, iter.byteAt(pos + 1), frame, subroutine);
                return;
            case 22:
                evalLoad(Type.LONG, iter.byteAt(pos + 1), frame, subroutine);
                return;
            case 23:
                evalLoad(Type.FLOAT, iter.byteAt(pos + 1), frame, subroutine);
                return;
            case 24:
                evalLoad(Type.DOUBLE, iter.byteAt(pos + 1), frame, subroutine);
                return;
            case 25:
                evalLoad(Type.OBJECT, iter.byteAt(pos + 1), frame, subroutine);
                return;
            case 26:
            case 27:
            case 28:
            case 29:
                evalLoad(Type.INTEGER, opcode - 26, frame, subroutine);
                return;
            case 30:
            case 31:
            case 32:
            case 33:
                evalLoad(Type.LONG, opcode - 30, frame, subroutine);
                return;
            case 34:
            case 35:
            case 36:
            case 37:
                evalLoad(Type.FLOAT, opcode - 34, frame, subroutine);
                return;
            case 38:
            case 39:
            case 40:
            case 41:
                evalLoad(Type.DOUBLE, opcode - 38, frame, subroutine);
                return;
            case 42:
            case 43:
            case 44:
            case 45:
                evalLoad(Type.OBJECT, opcode - 42, frame, subroutine);
                return;
            case 46:
                evalArrayLoad(Type.INTEGER, frame);
                return;
            case 47:
                evalArrayLoad(Type.LONG, frame);
                return;
            case 48:
                evalArrayLoad(Type.FLOAT, frame);
                return;
            case 49:
                evalArrayLoad(Type.DOUBLE, frame);
                return;
            case 50:
                evalArrayLoad(Type.OBJECT, frame);
                return;
            case 51:
            case 52:
            case 53:
                evalArrayLoad(Type.INTEGER, frame);
                return;
            case 54:
                evalStore(Type.INTEGER, iter.byteAt(pos + 1), frame, subroutine);
                return;
            case 55:
                evalStore(Type.LONG, iter.byteAt(pos + 1), frame, subroutine);
                return;
            case 56:
                evalStore(Type.FLOAT, iter.byteAt(pos + 1), frame, subroutine);
                return;
            case 57:
                evalStore(Type.DOUBLE, iter.byteAt(pos + 1), frame, subroutine);
                return;
            case 58:
                evalStore(Type.OBJECT, iter.byteAt(pos + 1), frame, subroutine);
                return;
            case 59:
            case 60:
            case 61:
            case 62:
                evalStore(Type.INTEGER, opcode - 59, frame, subroutine);
                return;
            case 63:
            case 64:
            case 65:
            case 66:
                evalStore(Type.LONG, opcode - 63, frame, subroutine);
                return;
            case 67:
            case 68:
            case 69:
            case 70:
                evalStore(Type.FLOAT, opcode - 67, frame, subroutine);
                return;
            case 71:
            case 72:
            case 73:
            case 74:
                evalStore(Type.DOUBLE, opcode - 71, frame, subroutine);
                return;
            case 75:
            case 76:
            case 77:
            case 78:
                evalStore(Type.OBJECT, opcode - 75, frame, subroutine);
                return;
            case 79:
                evalArrayStore(Type.INTEGER, frame);
                return;
            case 80:
                evalArrayStore(Type.LONG, frame);
                return;
            case 81:
                evalArrayStore(Type.FLOAT, frame);
                return;
            case 82:
                evalArrayStore(Type.DOUBLE, frame);
                return;
            case 83:
                evalArrayStore(Type.OBJECT, frame);
                return;
            case 84:
            case 85:
            case 86:
                evalArrayStore(Type.INTEGER, frame);
                return;
            case 87:
                if (frame.pop() == Type.TOP) {
                    throw new BadBytecode("POP can not be used with a category 2 value, pos = " + pos);
                }
                return;
            case 88:
                frame.pop();
                frame.pop();
                return;
            case 89:
                if (frame.peek() == Type.TOP) {
                    throw new BadBytecode("DUP can not be used with a category 2 value, pos = " + pos);
                }
                frame.push(frame.peek());
                return;
            case 90:
            case 91:
                Type type = frame.peek();
                if (type == Type.TOP) {
                    throw new BadBytecode("DUP can not be used with a category 2 value, pos = " + pos);
                }
                int end = frame.getTopIndex();
                int insert = (end - (opcode - 90)) - 1;
                frame.push(type);
                while (end > insert) {
                    frame.setStack(end, frame.getStack(end - 1));
                    end--;
                }
                frame.setStack(insert, type);
                return;
            case 92:
                frame.push(frame.getStack(frame.getTopIndex() - 1));
                frame.push(frame.getStack(frame.getTopIndex() - 1));
                return;
            case 93:
            case 94:
                int end2 = frame.getTopIndex();
                int insert2 = (end2 - (opcode - 93)) - 1;
                Type type1 = frame.getStack(frame.getTopIndex() - 1);
                Type type2 = frame.peek();
                frame.push(type1);
                frame.push(type2);
                while (end2 > insert2) {
                    frame.setStack(end2, frame.getStack(end2 - 2));
                    end2--;
                }
                frame.setStack(insert2, type2);
                frame.setStack(insert2 - 1, type1);
                return;
            case 95:
                Type type12 = frame.pop();
                Type type22 = frame.pop();
                if (type12.getSize() == 2 || type22.getSize() == 2) {
                    throw new BadBytecode("Swap can not be used with category 2 values, pos = " + pos);
                }
                frame.push(type12);
                frame.push(type22);
                return;
            case 96:
                evalBinaryMath(Type.INTEGER, frame);
                return;
            case 97:
                evalBinaryMath(Type.LONG, frame);
                return;
            case 98:
                evalBinaryMath(Type.FLOAT, frame);
                return;
            case 99:
                evalBinaryMath(Type.DOUBLE, frame);
                return;
            case 100:
                evalBinaryMath(Type.INTEGER, frame);
                return;
            case 101:
                evalBinaryMath(Type.LONG, frame);
                return;
            case 102:
                evalBinaryMath(Type.FLOAT, frame);
                return;
            case 103:
                evalBinaryMath(Type.DOUBLE, frame);
                return;
            case 104:
                evalBinaryMath(Type.INTEGER, frame);
                return;
            case 105:
                evalBinaryMath(Type.LONG, frame);
                return;
            case 106:
                evalBinaryMath(Type.FLOAT, frame);
                return;
            case 107:
                evalBinaryMath(Type.DOUBLE, frame);
                return;
            case 108:
                evalBinaryMath(Type.INTEGER, frame);
                return;
            case 109:
                evalBinaryMath(Type.LONG, frame);
                return;
            case 110:
                evalBinaryMath(Type.FLOAT, frame);
                return;
            case 111:
                evalBinaryMath(Type.DOUBLE, frame);
                return;
            case 112:
                evalBinaryMath(Type.INTEGER, frame);
                return;
            case 113:
                evalBinaryMath(Type.LONG, frame);
                return;
            case 114:
                evalBinaryMath(Type.FLOAT, frame);
                return;
            case 115:
                evalBinaryMath(Type.DOUBLE, frame);
                return;
            case 116:
                verifyAssignable(Type.INTEGER, simplePeek(frame));
                return;
            case 117:
                verifyAssignable(Type.LONG, simplePeek(frame));
                return;
            case 118:
                verifyAssignable(Type.FLOAT, simplePeek(frame));
                return;
            case 119:
                verifyAssignable(Type.DOUBLE, simplePeek(frame));
                return;
            case 120:
                evalShift(Type.INTEGER, frame);
                return;
            case 121:
                evalShift(Type.LONG, frame);
                return;
            case 122:
                evalShift(Type.INTEGER, frame);
                return;
            case 123:
                evalShift(Type.LONG, frame);
                return;
            case 124:
                evalShift(Type.INTEGER, frame);
                return;
            case 125:
                evalShift(Type.LONG, frame);
                return;
            case 126:
                evalBinaryMath(Type.INTEGER, frame);
                return;
            case 127:
                evalBinaryMath(Type.LONG, frame);
                return;
            case 128:
                evalBinaryMath(Type.INTEGER, frame);
                return;
            case 129:
                evalBinaryMath(Type.LONG, frame);
                return;
            case 130:
                evalBinaryMath(Type.INTEGER, frame);
                return;
            case 131:
                evalBinaryMath(Type.LONG, frame);
                return;
            case 132:
                int index = iter.byteAt(pos + 1);
                verifyAssignable(Type.INTEGER, frame.getLocal(index));
                access(index, Type.INTEGER, subroutine);
                return;
            case 133:
                verifyAssignable(Type.INTEGER, simplePop(frame));
                simplePush(Type.LONG, frame);
                return;
            case 134:
                verifyAssignable(Type.INTEGER, simplePop(frame));
                simplePush(Type.FLOAT, frame);
                return;
            case 135:
                verifyAssignable(Type.INTEGER, simplePop(frame));
                simplePush(Type.DOUBLE, frame);
                return;
            case 136:
                verifyAssignable(Type.LONG, simplePop(frame));
                simplePush(Type.INTEGER, frame);
                return;
            case 137:
                verifyAssignable(Type.LONG, simplePop(frame));
                simplePush(Type.FLOAT, frame);
                return;
            case 138:
                verifyAssignable(Type.LONG, simplePop(frame));
                simplePush(Type.DOUBLE, frame);
                return;
            case 139:
                verifyAssignable(Type.FLOAT, simplePop(frame));
                simplePush(Type.INTEGER, frame);
                return;
            case 140:
                verifyAssignable(Type.FLOAT, simplePop(frame));
                simplePush(Type.LONG, frame);
                return;
            case 141:
                verifyAssignable(Type.FLOAT, simplePop(frame));
                simplePush(Type.DOUBLE, frame);
                return;
            case 142:
                verifyAssignable(Type.DOUBLE, simplePop(frame));
                simplePush(Type.INTEGER, frame);
                return;
            case 143:
                verifyAssignable(Type.DOUBLE, simplePop(frame));
                simplePush(Type.LONG, frame);
                return;
            case 144:
                verifyAssignable(Type.DOUBLE, simplePop(frame));
                simplePush(Type.FLOAT, frame);
                return;
            case 145:
            case 146:
            case 147:
                verifyAssignable(Type.INTEGER, frame.peek());
                return;
            case 148:
                verifyAssignable(Type.LONG, simplePop(frame));
                verifyAssignable(Type.LONG, simplePop(frame));
                frame.push(Type.INTEGER);
                return;
            case 149:
            case 150:
                verifyAssignable(Type.FLOAT, simplePop(frame));
                verifyAssignable(Type.FLOAT, simplePop(frame));
                frame.push(Type.INTEGER);
                return;
            case 151:
            case 152:
                verifyAssignable(Type.DOUBLE, simplePop(frame));
                verifyAssignable(Type.DOUBLE, simplePop(frame));
                frame.push(Type.INTEGER);
                return;
            case 153:
            case 154:
            case 155:
            case 156:
            case 157:
            case 158:
                verifyAssignable(Type.INTEGER, simplePop(frame));
                return;
            case 159:
            case 160:
            case 161:
            case 162:
            case 163:
            case 164:
                verifyAssignable(Type.INTEGER, simplePop(frame));
                verifyAssignable(Type.INTEGER, simplePop(frame));
                return;
            case 165:
            case 166:
                verifyAssignable(Type.OBJECT, simplePop(frame));
                verifyAssignable(Type.OBJECT, simplePop(frame));
                return;
            case 168:
                frame.push(Type.RETURN_ADDRESS);
                return;
            case 169:
                verifyAssignable(Type.RETURN_ADDRESS, frame.getLocal(iter.byteAt(pos + 1)));
                return;
            case 170:
            case 171:
            case 172:
                verifyAssignable(Type.INTEGER, simplePop(frame));
                return;
            case 173:
                verifyAssignable(Type.LONG, simplePop(frame));
                return;
            case 174:
                verifyAssignable(Type.FLOAT, simplePop(frame));
                return;
            case 175:
                verifyAssignable(Type.DOUBLE, simplePop(frame));
                return;
            case 176:
                try {
                    CtClass returnType = Descriptor.getReturnType(method.getDescriptor(), this.classPool);
                    verifyAssignable(Type.get(returnType), simplePop(frame));
                    return;
                } catch (NotFoundException e) {
                    throw new RuntimeException(e);
                }
            case 178:
                evalGetField(opcode, iter.u16bitAt(pos + 1), frame);
                return;
            case 179:
                evalPutField(opcode, iter.u16bitAt(pos + 1), frame);
                return;
            case 180:
                evalGetField(opcode, iter.u16bitAt(pos + 1), frame);
                return;
            case 181:
                evalPutField(opcode, iter.u16bitAt(pos + 1), frame);
                return;
            case 182:
            case 183:
            case 184:
                evalInvokeMethod(opcode, iter.u16bitAt(pos + 1), frame);
                return;
            case 185:
                evalInvokeIntfMethod(opcode, iter.u16bitAt(pos + 1), frame);
                return;
            case 186:
                evalInvokeDynamic(opcode, iter.u16bitAt(pos + 1), frame);
                return;
            case 187:
                frame.push(resolveClassInfo(this.constPool.getClassInfo(iter.u16bitAt(pos + 1))));
                return;
            case 188:
                evalNewArray(pos, iter, frame);
                return;
            case 189:
                evalNewObjectArray(pos, iter, frame);
                return;
            case 190:
                Type array = simplePop(frame);
                if (!array.isArray() && array != Type.UNINIT) {
                    throw new BadBytecode("Array length passed a non-array [pos = " + pos + "]: " + array);
                }
                frame.push(Type.INTEGER);
                return;
            case 191:
                verifyAssignable(this.THROWABLE_TYPE, simplePop(frame));
                return;
            case 192:
                verifyAssignable(Type.OBJECT, simplePop(frame));
                frame.push(typeFromDesc(this.constPool.getClassInfoByDescriptor(iter.u16bitAt(pos + 1))));
                return;
            case 193:
                verifyAssignable(Type.OBJECT, simplePop(frame));
                frame.push(Type.INTEGER);
                return;
            case 194:
            case 195:
                verifyAssignable(Type.OBJECT, simplePop(frame));
                return;
            case Opcode.WIDE /* 196 */:
                evalWide(pos, iter, frame, subroutine);
                return;
            case 197:
                evalNewObjectArray(pos, iter, frame);
                return;
            case 198:
            case 199:
                verifyAssignable(Type.OBJECT, simplePop(frame));
                return;
            case Opcode.JSR_W /* 201 */:
                frame.push(Type.RETURN_ADDRESS);
                return;
        }
    }

    private Type zeroExtend(Type type) {
        if (type == Type.SHORT || type == Type.BYTE || type == Type.CHAR || type == Type.BOOLEAN) {
            return Type.INTEGER;
        }
        return type;
    }

    private void evalArrayLoad(Type expectedComponent, Frame frame) throws BadBytecode {
        Type index = frame.pop();
        Type array = frame.pop();
        if (array == Type.UNINIT) {
            verifyAssignable(Type.INTEGER, index);
            if (expectedComponent == Type.OBJECT) {
                simplePush(Type.UNINIT, frame);
                return;
            } else {
                simplePush(expectedComponent, frame);
                return;
            }
        }
        Type component = array.getComponent();
        if (component == null) {
            throw new BadBytecode("Not an array! [pos = " + this.lastPos + "]: " + component);
        }
        Type component2 = zeroExtend(component);
        verifyAssignable(expectedComponent, component2);
        verifyAssignable(Type.INTEGER, index);
        simplePush(component2, frame);
    }

    private void evalArrayStore(Type expectedComponent, Frame frame) throws BadBytecode {
        Type value = simplePop(frame);
        Type index = frame.pop();
        Type array = frame.pop();
        if (array == Type.UNINIT) {
            verifyAssignable(Type.INTEGER, index);
            return;
        }
        Type component = array.getComponent();
        if (component == null) {
            throw new BadBytecode("Not an array! [pos = " + this.lastPos + "]: " + component);
        }
        Type component2 = zeroExtend(component);
        verifyAssignable(expectedComponent, component2);
        verifyAssignable(Type.INTEGER, index);
        if (expectedComponent == Type.OBJECT) {
            verifyAssignable(expectedComponent, value);
        } else {
            verifyAssignable(component2, value);
        }
    }

    private void evalBinaryMath(Type expected, Frame frame) throws BadBytecode {
        Type value2 = simplePop(frame);
        Type value1 = simplePop(frame);
        verifyAssignable(expected, value2);
        verifyAssignable(expected, value1);
        simplePush(value1, frame);
    }

    private void evalGetField(int opcode, int index, Frame frame) throws BadBytecode {
        String desc = this.constPool.getFieldrefType(index);
        Type type = zeroExtend(typeFromDesc(desc));
        if (opcode == 180) {
            Type objectType = resolveClassInfo(this.constPool.getFieldrefClassName(index));
            verifyAssignable(objectType, simplePop(frame));
        }
        simplePush(type, frame);
    }

    private void evalInvokeIntfMethod(int opcode, int index, Frame frame) throws BadBytecode {
        String desc = this.constPool.getInterfaceMethodrefType(index);
        Type[] types = paramTypesFromDesc(desc);
        int i = types.length;
        while (i > 0) {
            i--;
            verifyAssignable(zeroExtend(types[i]), simplePop(frame));
        }
        String classInfo = this.constPool.getInterfaceMethodrefClassName(index);
        Type objectType = resolveClassInfo(classInfo);
        verifyAssignable(objectType, simplePop(frame));
        Type returnType = returnTypeFromDesc(desc);
        if (returnType != Type.VOID) {
            simplePush(zeroExtend(returnType), frame);
        }
    }

    private void evalInvokeMethod(int opcode, int index, Frame frame) throws BadBytecode {
        String desc = this.constPool.getMethodrefType(index);
        Type[] types = paramTypesFromDesc(desc);
        int i = types.length;
        while (i > 0) {
            i--;
            verifyAssignable(zeroExtend(types[i]), simplePop(frame));
        }
        if (opcode != 184) {
            Type objectType = resolveClassInfo(this.constPool.getMethodrefClassName(index));
            verifyAssignable(objectType, simplePop(frame));
        }
        Type returnType = returnTypeFromDesc(desc);
        if (returnType != Type.VOID) {
            simplePush(zeroExtend(returnType), frame);
        }
    }

    private void evalInvokeDynamic(int opcode, int index, Frame frame) throws BadBytecode {
        String desc = this.constPool.getInvokeDynamicType(index);
        Type[] types = paramTypesFromDesc(desc);
        int i = types.length;
        while (i > 0) {
            i--;
            verifyAssignable(zeroExtend(types[i]), simplePop(frame));
        }
        Type returnType = returnTypeFromDesc(desc);
        if (returnType != Type.VOID) {
            simplePush(zeroExtend(returnType), frame);
        }
    }

    private void evalLDC(int index, Frame frame) throws BadBytecode {
        Type type;
        int tag = this.constPool.getTag(index);
        switch (tag) {
            case 3:
                type = Type.INTEGER;
                break;
            case 4:
                type = Type.FLOAT;
                break;
            case 5:
                type = Type.LONG;
                break;
            case 6:
                type = Type.DOUBLE;
                break;
            case 7:
                type = this.CLASS_TYPE;
                break;
            case 8:
                type = this.STRING_TYPE;
                break;
            default:
                throw new BadBytecode("bad LDC [pos = " + this.lastPos + "]: " + tag);
        }
        simplePush(type, frame);
    }

    private void evalLoad(Type expected, int index, Frame frame, Subroutine subroutine) throws BadBytecode {
        Type type = frame.getLocal(index);
        verifyAssignable(expected, type);
        simplePush(type, frame);
        access(index, type, subroutine);
    }

    private void evalNewArray(int pos, CodeIterator iter, Frame frame) throws BadBytecode {
        Type type;
        verifyAssignable(Type.INTEGER, simplePop(frame));
        int typeInfo = iter.byteAt(pos + 1);
        switch (typeInfo) {
            case 4:
                type = getType("boolean[]");
                break;
            case 5:
                type = getType("char[]");
                break;
            case 6:
                type = getType("float[]");
                break;
            case 7:
                type = getType("double[]");
                break;
            case 8:
                type = getType("byte[]");
                break;
            case 9:
                type = getType("short[]");
                break;
            case 10:
                type = getType("int[]");
                break;
            case 11:
                type = getType("long[]");
                break;
            default:
                throw new BadBytecode("Invalid array type [pos = " + pos + "]: " + typeInfo);
        }
        frame.push(type);
    }

    private void evalNewObjectArray(int pos, CodeIterator iter, Frame frame) throws BadBytecode {
        int dimensions;
        Type type = resolveClassInfo(this.constPool.getClassInfo(iter.u16bitAt(pos + 1)));
        String name = type.getCtClass().getName();
        int opcode = iter.byteAt(pos);
        if (opcode == 197) {
            dimensions = iter.byteAt(pos + 3);
        } else {
            name = name + "[]";
            dimensions = 1;
        }
        while (true) {
            int i = dimensions;
            dimensions--;
            if (i > 0) {
                verifyAssignable(Type.INTEGER, simplePop(frame));
            } else {
                simplePush(getType(name), frame);
                return;
            }
        }
    }

    private void evalPutField(int opcode, int index, Frame frame) throws BadBytecode {
        String desc = this.constPool.getFieldrefType(index);
        Type type = zeroExtend(typeFromDesc(desc));
        verifyAssignable(type, simplePop(frame));
        if (opcode == 181) {
            Type objectType = resolveClassInfo(this.constPool.getFieldrefClassName(index));
            verifyAssignable(objectType, simplePop(frame));
        }
    }

    private void evalShift(Type expected, Frame frame) throws BadBytecode {
        Type value2 = simplePop(frame);
        Type value1 = simplePop(frame);
        verifyAssignable(Type.INTEGER, value2);
        verifyAssignable(expected, value1);
        simplePush(value1, frame);
    }

    private void evalStore(Type expected, int index, Frame frame, Subroutine subroutine) throws BadBytecode {
        Type type = simplePop(frame);
        if (expected != Type.OBJECT || type != Type.RETURN_ADDRESS) {
            verifyAssignable(expected, type);
        }
        simpleSetLocal(index, type, frame);
        access(index, type, subroutine);
    }

    private void evalWide(int pos, CodeIterator iter, Frame frame, Subroutine subroutine) throws BadBytecode {
        int opcode = iter.byteAt(pos + 1);
        int index = iter.u16bitAt(pos + 2);
        switch (opcode) {
            case 21:
                evalLoad(Type.INTEGER, index, frame, subroutine);
                return;
            case 22:
                evalLoad(Type.LONG, index, frame, subroutine);
                return;
            case 23:
                evalLoad(Type.FLOAT, index, frame, subroutine);
                return;
            case 24:
                evalLoad(Type.DOUBLE, index, frame, subroutine);
                return;
            case 25:
                evalLoad(Type.OBJECT, index, frame, subroutine);
                return;
            case 54:
                evalStore(Type.INTEGER, index, frame, subroutine);
                return;
            case 55:
                evalStore(Type.LONG, index, frame, subroutine);
                return;
            case 56:
                evalStore(Type.FLOAT, index, frame, subroutine);
                return;
            case 57:
                evalStore(Type.DOUBLE, index, frame, subroutine);
                return;
            case 58:
                evalStore(Type.OBJECT, index, frame, subroutine);
                return;
            case 132:
                verifyAssignable(Type.INTEGER, frame.getLocal(index));
                return;
            case 169:
                verifyAssignable(Type.RETURN_ADDRESS, frame.getLocal(index));
                return;
            default:
                throw new BadBytecode("Invalid WIDE operand [pos = " + pos + "]: " + opcode);
        }
    }

    private Type getType(String name) throws BadBytecode {
        try {
            return Type.get(this.classPool.get(name));
        } catch (NotFoundException e) {
            throw new BadBytecode("Could not find class [pos = " + this.lastPos + "]: " + name);
        }
    }

    private Type[] paramTypesFromDesc(String desc) throws BadBytecode {
        try {
            CtClass[] classes = Descriptor.getParameterTypes(desc, this.classPool);
            if (classes == null) {
                throw new BadBytecode("Could not obtain parameters for descriptor [pos = " + this.lastPos + "]: " + desc);
            }
            Type[] types = new Type[classes.length];
            for (int i = 0; i < types.length; i++) {
                types[i] = Type.get(classes[i]);
            }
            return types;
        } catch (NotFoundException e) {
            throw new BadBytecode("Could not find class in descriptor [pos = " + this.lastPos + "]: " + e.getMessage());
        }
    }

    private Type returnTypeFromDesc(String desc) throws BadBytecode {
        try {
            CtClass clazz = Descriptor.getReturnType(desc, this.classPool);
            if (clazz == null) {
                throw new BadBytecode("Could not obtain return type for descriptor [pos = " + this.lastPos + "]: " + desc);
            }
            return Type.get(clazz);
        } catch (NotFoundException e) {
            throw new BadBytecode("Could not find class in descriptor [pos = " + this.lastPos + "]: " + e.getMessage());
        }
    }

    private Type simplePeek(Frame frame) {
        Type type = frame.peek();
        return type == Type.TOP ? frame.getStack(frame.getTopIndex() - 1) : type;
    }

    private Type simplePop(Frame frame) {
        Type type = frame.pop();
        return type == Type.TOP ? frame.pop() : type;
    }

    private void simplePush(Type type, Frame frame) {
        frame.push(type);
        if (type.getSize() == 2) {
            frame.push(Type.TOP);
        }
    }

    private void access(int index, Type type, Subroutine subroutine) {
        if (subroutine == null) {
            return;
        }
        subroutine.access(index);
        if (type.getSize() == 2) {
            subroutine.access(index + 1);
        }
    }

    private void simpleSetLocal(int index, Type type, Frame frame) {
        frame.setLocal(index, type);
        if (type.getSize() == 2) {
            frame.setLocal(index + 1, Type.TOP);
        }
    }

    private Type resolveClassInfo(String info) throws BadBytecode {
        CtClass clazz;
        try {
            if (info.charAt(0) == '[') {
                clazz = Descriptor.toCtClass(info, this.classPool);
            } else {
                clazz = this.classPool.get(info);
            }
            if (clazz == null) {
                throw new BadBytecode("Could not obtain type for descriptor [pos = " + this.lastPos + "]: " + info);
            }
            return Type.get(clazz);
        } catch (NotFoundException e) {
            throw new BadBytecode("Could not find class in descriptor [pos = " + this.lastPos + "]: " + e.getMessage());
        }
    }

    private Type typeFromDesc(String desc) throws BadBytecode {
        try {
            CtClass clazz = Descriptor.toCtClass(desc, this.classPool);
            if (clazz == null) {
                throw new BadBytecode("Could not obtain type for descriptor [pos = " + this.lastPos + "]: " + desc);
            }
            return Type.get(clazz);
        } catch (NotFoundException e) {
            throw new BadBytecode("Could not find class in descriptor [pos = " + this.lastPos + "]: " + e.getMessage());
        }
    }

    private void verifyAssignable(Type expected, Type type) throws BadBytecode {
        if (!expected.isAssignableFrom(type)) {
            throw new BadBytecode("Expected type: " + expected + " Got: " + type + " [pos = " + this.lastPos + "]");
        }
    }
}
