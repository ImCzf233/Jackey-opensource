package com.viaversion.viaversion.libs.javassist.compiler.ast;

import com.viaversion.viaversion.libs.javassist.compiler.CompileError;
import com.viaversion.viaversion.libs.javassist.compiler.TokenId;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/compiler/ast/IntConst.class */
public class IntConst extends ASTree {
    private static final long serialVersionUID = 1;
    protected long value;
    protected int type;

    public IntConst(long v, int tokenId) {
        this.value = v;
        this.type = tokenId;
    }

    public long get() {
        return this.value;
    }

    public void set(long v) {
        this.value = v;
    }

    public int getType() {
        return this.type;
    }

    @Override // com.viaversion.viaversion.libs.javassist.compiler.ast.ASTree
    public String toString() {
        return Long.toString(this.value);
    }

    @Override // com.viaversion.viaversion.libs.javassist.compiler.ast.ASTree
    public void accept(Visitor v) throws CompileError {
        v.atIntConst(this);
    }

    public ASTree compute(int op, ASTree right) {
        if (right instanceof IntConst) {
            return compute0(op, (IntConst) right);
        }
        if (right instanceof DoubleConst) {
            return compute0(op, (DoubleConst) right);
        }
        return null;
    }

    private IntConst compute0(int op, IntConst right) {
        int newType;
        long newValue;
        int type1 = this.type;
        int type2 = right.type;
        if (type1 == 403 || type2 == 403) {
            newType = 403;
        } else if (type1 == 401 && type2 == 401) {
            newType = 401;
        } else {
            newType = 402;
        }
        long value1 = this.value;
        long value2 = right.value;
        switch (op) {
            case 37:
                newValue = value1 % value2;
                break;
            case 38:
                newValue = value1 & value2;
                break;
            case 42:
                newValue = value1 * value2;
                break;
            case 43:
                newValue = value1 + value2;
                break;
            case 45:
                newValue = value1 - value2;
                break;
            case 47:
                newValue = value1 / value2;
                break;
            case 94:
                newValue = value1 ^ value2;
                break;
            case 124:
                newValue = value1 | value2;
                break;
            case TokenId.LSHIFT /* 364 */:
                newValue = this.value << ((int) value2);
                newType = type1;
                break;
            case TokenId.RSHIFT /* 366 */:
                newValue = this.value >> ((int) value2);
                newType = type1;
                break;
            case TokenId.ARSHIFT /* 370 */:
                newValue = this.value >>> ((int) value2);
                newType = type1;
                break;
            default:
                return null;
        }
        return new IntConst(newValue, newType);
    }

    private DoubleConst compute0(int op, DoubleConst right) {
        double newValue;
        double value1 = this.value;
        double value2 = right.value;
        switch (op) {
            case 37:
                newValue = value1 % value2;
                break;
            case 38:
            case 39:
            case 40:
            case 41:
            case 44:
            case 46:
            default:
                return null;
            case 42:
                newValue = value1 * value2;
                break;
            case 43:
                newValue = value1 + value2;
                break;
            case 45:
                newValue = value1 - value2;
                break;
            case 47:
                newValue = value1 / value2;
                break;
        }
        return new DoubleConst(newValue, right.type);
    }
}
