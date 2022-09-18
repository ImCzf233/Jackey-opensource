package com.viaversion.viaversion.libs.javassist.expr;

import com.viaversion.viaversion.libs.javassist.CannotCompileException;
import com.viaversion.viaversion.libs.javassist.ClassPool;
import com.viaversion.viaversion.libs.javassist.CtBehavior;
import com.viaversion.viaversion.libs.javassist.CtClass;
import com.viaversion.viaversion.libs.javassist.NotFoundException;
import com.viaversion.viaversion.libs.javassist.bytecode.BadBytecode;
import com.viaversion.viaversion.libs.javassist.bytecode.Bytecode;
import com.viaversion.viaversion.libs.javassist.bytecode.CodeAttribute;
import com.viaversion.viaversion.libs.javassist.bytecode.CodeIterator;
import com.viaversion.viaversion.libs.javassist.bytecode.ConstPool;
import com.viaversion.viaversion.libs.javassist.bytecode.MethodInfo;
import com.viaversion.viaversion.libs.javassist.compiler.CompileError;
import com.viaversion.viaversion.libs.javassist.compiler.Javac;
import com.viaversion.viaversion.libs.javassist.compiler.JvstCodeGen;
import com.viaversion.viaversion.libs.javassist.compiler.JvstTypeChecker;
import com.viaversion.viaversion.libs.javassist.compiler.ProceedHandler;
import com.viaversion.viaversion.libs.javassist.compiler.ast.ASTList;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/expr/Instanceof.class */
public class Instanceof extends Expr {
    public Instanceof(int pos, CodeIterator i, CtClass declaring, MethodInfo m) {
        super(pos, i, declaring, m);
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

    public CtClass getType() throws NotFoundException {
        ConstPool cp = getConstPool();
        int pos = this.currentPos;
        int index = this.iterator.u16bitAt(pos + 1);
        String name = cp.getClassInfo(index);
        return this.thisClass.getClassPool().getCtClass(name);
    }

    @Override // com.viaversion.viaversion.libs.javassist.expr.Expr
    public CtClass[] mayThrow() {
        return super.mayThrow();
    }

    @Override // com.viaversion.viaversion.libs.javassist.expr.Expr
    public void replace(String statement) throws CannotCompileException {
        this.thisClass.getClassFile();
        getConstPool();
        int pos = this.currentPos;
        int index = this.iterator.u16bitAt(pos + 1);
        Javac jc = new Javac(this.thisClass);
        ClassPool cp = this.thisClass.getClassPool();
        CodeAttribute ca = this.iterator.get();
        try {
            CtClass[] params = {cp.get("java.lang.Object")};
            CtClass retType = CtClass.booleanType;
            int paramVar = ca.getMaxLocals();
            jc.recordParams("java.lang.Object", params, true, paramVar, withinStatic());
            int retVar = jc.recordReturnType(retType, true);
            jc.recordProceed(new ProceedForInstanceof(index));
            jc.recordType(getType());
            checkResultValue(retType, statement);
            Bytecode bytecode = jc.getBytecode();
            storeStack(params, true, paramVar, bytecode);
            jc.recordLocalVariables(ca, pos);
            bytecode.addConstZero(retType);
            bytecode.addStore(retVar, retType);
            jc.compileStmnt(statement);
            bytecode.addLoad(retVar, retType);
            replace0(pos, bytecode, 3);
        } catch (NotFoundException e) {
            throw new CannotCompileException(e);
        } catch (BadBytecode e2) {
            throw new CannotCompileException("broken method");
        } catch (CompileError e3) {
            throw new CannotCompileException(e3);
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/expr/Instanceof$ProceedForInstanceof.class */
    static class ProceedForInstanceof implements ProceedHandler {
        int index;

        ProceedForInstanceof(int i) {
            this.index = i;
        }

        @Override // com.viaversion.viaversion.libs.javassist.compiler.ProceedHandler
        public void doit(JvstCodeGen gen, Bytecode bytecode, ASTList args) throws CompileError {
            if (gen.getMethodArgsLength(args) != 1) {
                throw new CompileError("$proceed() cannot take more than one parameter for instanceof");
            }
            gen.atMethodArgs(args, new int[1], new int[1], new String[1]);
            bytecode.addOpcode(193);
            bytecode.addIndex(this.index);
            gen.setType(CtClass.booleanType);
        }

        @Override // com.viaversion.viaversion.libs.javassist.compiler.ProceedHandler
        public void setReturnType(JvstTypeChecker c, ASTList args) throws CompileError {
            c.atMethodArgs(args, new int[1], new int[1], new String[1]);
            c.setType(CtClass.booleanType);
        }
    }
}
