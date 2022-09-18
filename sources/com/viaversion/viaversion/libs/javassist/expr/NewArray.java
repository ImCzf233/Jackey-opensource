package com.viaversion.viaversion.libs.javassist.expr;

import com.viaversion.viaversion.libs.javassist.CannotCompileException;
import com.viaversion.viaversion.libs.javassist.CtBehavior;
import com.viaversion.viaversion.libs.javassist.CtClass;
import com.viaversion.viaversion.libs.javassist.CtPrimitiveType;
import com.viaversion.viaversion.libs.javassist.NotFoundException;
import com.viaversion.viaversion.libs.javassist.bytecode.BadBytecode;
import com.viaversion.viaversion.libs.javassist.bytecode.Bytecode;
import com.viaversion.viaversion.libs.javassist.bytecode.CodeAttribute;
import com.viaversion.viaversion.libs.javassist.bytecode.CodeIterator;
import com.viaversion.viaversion.libs.javassist.bytecode.ConstPool;
import com.viaversion.viaversion.libs.javassist.bytecode.Descriptor;
import com.viaversion.viaversion.libs.javassist.bytecode.MethodInfo;
import com.viaversion.viaversion.libs.javassist.compiler.CompileError;
import com.viaversion.viaversion.libs.javassist.compiler.Javac;
import com.viaversion.viaversion.libs.javassist.compiler.JvstCodeGen;
import com.viaversion.viaversion.libs.javassist.compiler.JvstTypeChecker;
import com.viaversion.viaversion.libs.javassist.compiler.ProceedHandler;
import com.viaversion.viaversion.libs.javassist.compiler.ast.ASTList;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/expr/NewArray.class */
public class NewArray extends Expr {
    int opcode;

    public NewArray(int pos, CodeIterator i, CtClass declaring, MethodInfo m, int op) {
        super(pos, i, declaring, m);
        this.opcode = op;
    }

    @Override // com.viaversion.viaversion.libs.javassist.expr.Expr
    public CtBehavior where() {
        return super.where();
    }

    @Override // com.viaversion.viaversion.libs.javassist.expr.Expr
    public int getLineNumber() {
        return super.getLineNumber();
    }

    @Override // com.viaversion.viaversion.libs.javassist.expr.Expr
    public String getFileName() {
        return super.getFileName();
    }

    @Override // com.viaversion.viaversion.libs.javassist.expr.Expr
    public CtClass[] mayThrow() {
        return super.mayThrow();
    }

    public CtClass getComponentType() throws NotFoundException {
        if (this.opcode == 188) {
            int atype = this.iterator.byteAt(this.currentPos + 1);
            return getPrimitiveType(atype);
        } else if (this.opcode == 189 || this.opcode == 197) {
            int index = this.iterator.u16bitAt(this.currentPos + 1);
            String desc = getConstPool().getClassInfo(index);
            int dim = Descriptor.arrayDimension(desc);
            return Descriptor.toCtClass(Descriptor.toArrayComponent(desc, dim), this.thisClass.getClassPool());
        } else {
            throw new RuntimeException("bad opcode: " + this.opcode);
        }
    }

    CtClass getPrimitiveType(int atype) {
        switch (atype) {
            case 4:
                return CtClass.booleanType;
            case 5:
                return CtClass.charType;
            case 6:
                return CtClass.floatType;
            case 7:
                return CtClass.doubleType;
            case 8:
                return CtClass.byteType;
            case 9:
                return CtClass.shortType;
            case 10:
                return CtClass.intType;
            case 11:
                return CtClass.longType;
            default:
                throw new RuntimeException("bad atype: " + atype);
        }
    }

    public int getDimension() {
        if (this.opcode == 188) {
            return 1;
        }
        if (this.opcode == 189 || this.opcode == 197) {
            int index = this.iterator.u16bitAt(this.currentPos + 1);
            String desc = getConstPool().getClassInfo(index);
            return Descriptor.arrayDimension(desc) + (this.opcode == 189 ? 1 : 0);
        }
        throw new RuntimeException("bad opcode: " + this.opcode);
    }

    public int getCreatedDimensions() {
        if (this.opcode == 197) {
            return this.iterator.byteAt(this.currentPos + 3);
        }
        return 1;
    }

    @Override // com.viaversion.viaversion.libs.javassist.expr.Expr
    public void replace(String statement) throws CannotCompileException {
        try {
            replace2(statement);
        } catch (NotFoundException e) {
            throw new CannotCompileException(e);
        } catch (BadBytecode e2) {
            throw new CannotCompileException("broken method");
        } catch (CompileError e3) {
            throw new CannotCompileException(e3);
        }
    }

    private void replace2(String statement) throws CompileError, NotFoundException, BadBytecode, CannotCompileException {
        String desc;
        int index;
        int codeLength;
        this.thisClass.getClassFile();
        ConstPool constPool = getConstPool();
        int pos = this.currentPos;
        int dim = 1;
        if (this.opcode == 188) {
            index = this.iterator.byteAt(this.currentPos + 1);
            CtPrimitiveType cpt = (CtPrimitiveType) getPrimitiveType(index);
            desc = "[" + cpt.getDescriptor();
            codeLength = 2;
        } else if (this.opcode == 189) {
            index = this.iterator.u16bitAt(pos + 1);
            String desc2 = constPool.getClassInfo(index);
            if (desc2.startsWith("[")) {
                desc = "[" + desc2;
            } else {
                desc = "[L" + desc2 + ";";
            }
            codeLength = 3;
        } else if (this.opcode == 197) {
            index = this.iterator.u16bitAt(this.currentPos + 1);
            desc = constPool.getClassInfo(index);
            dim = this.iterator.byteAt(this.currentPos + 3);
            codeLength = 4;
        } else {
            throw new RuntimeException("bad opcode: " + this.opcode);
        }
        CtClass retType = Descriptor.toCtClass(desc, this.thisClass.getClassPool());
        Javac jc = new Javac(this.thisClass);
        CodeAttribute ca = this.iterator.get();
        CtClass[] params = new CtClass[dim];
        for (int i = 0; i < dim; i++) {
            params[i] = CtClass.intType;
        }
        int paramVar = ca.getMaxLocals();
        jc.recordParams("java.lang.Object", params, true, paramVar, withinStatic());
        checkResultValue(retType, statement);
        int retVar = jc.recordReturnType(retType, true);
        jc.recordProceed(new ProceedForArray(retType, this.opcode, index, dim));
        Bytecode bytecode = jc.getBytecode();
        storeStack(params, true, paramVar, bytecode);
        jc.recordLocalVariables(ca, pos);
        bytecode.addOpcode(1);
        bytecode.addAstore(retVar);
        jc.compileStmnt(statement);
        bytecode.addAload(retVar);
        replace0(pos, bytecode, codeLength);
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/expr/NewArray$ProceedForArray.class */
    public static class ProceedForArray implements ProceedHandler {
        CtClass arrayType;
        int opcode;
        int index;
        int dimension;

        ProceedForArray(CtClass type, int op, int i, int dim) {
            this.arrayType = type;
            this.opcode = op;
            this.index = i;
            this.dimension = dim;
        }

        @Override // com.viaversion.viaversion.libs.javassist.compiler.ProceedHandler
        public void doit(JvstCodeGen gen, Bytecode bytecode, ASTList args) throws CompileError {
            int num = gen.getMethodArgsLength(args);
            if (num != this.dimension) {
                throw new CompileError("$proceed() with a wrong number of parameters");
            }
            gen.atMethodArgs(args, new int[num], new int[num], new String[num]);
            bytecode.addOpcode(this.opcode);
            if (this.opcode == 189) {
                bytecode.addIndex(this.index);
            } else if (this.opcode == 188) {
                bytecode.add(this.index);
            } else {
                bytecode.addIndex(this.index);
                bytecode.add(this.dimension);
                bytecode.growStack(1 - this.dimension);
            }
            gen.setType(this.arrayType);
        }

        @Override // com.viaversion.viaversion.libs.javassist.compiler.ProceedHandler
        public void setReturnType(JvstTypeChecker c, ASTList args) throws CompileError {
            c.setType(this.arrayType);
        }
    }
}
