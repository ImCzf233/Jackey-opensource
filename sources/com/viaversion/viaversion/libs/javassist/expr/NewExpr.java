package com.viaversion.viaversion.libs.javassist.expr;

import com.viaversion.viaversion.libs.javassist.CannotCompileException;
import com.viaversion.viaversion.libs.javassist.ClassPool;
import com.viaversion.viaversion.libs.javassist.CtBehavior;
import com.viaversion.viaversion.libs.javassist.CtClass;
import com.viaversion.viaversion.libs.javassist.CtConstructor;
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

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/expr/NewExpr.class */
public class NewExpr extends Expr {
    String newTypeName;
    int newPos;

    public NewExpr(int pos, CodeIterator i, CtClass declaring, MethodInfo m, String type, int np) {
        super(pos, i, declaring, m);
        this.newTypeName = type;
        this.newPos = np;
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

    private CtClass getCtClass() throws NotFoundException {
        return this.thisClass.getClassPool().get(this.newTypeName);
    }

    public String getClassName() {
        return this.newTypeName;
    }

    public String getSignature() {
        ConstPool constPool = getConstPool();
        int methodIndex = this.iterator.u16bitAt(this.currentPos + 1);
        return constPool.getMethodrefType(methodIndex);
    }

    public CtConstructor getConstructor() throws NotFoundException {
        ConstPool cp = getConstPool();
        int index = this.iterator.u16bitAt(this.currentPos + 1);
        String desc = cp.getMethodrefType(index);
        return getCtClass().getConstructor(desc);
    }

    @Override // com.viaversion.viaversion.libs.javassist.expr.Expr
    public CtClass[] mayThrow() {
        return super.mayThrow();
    }

    private int canReplace() throws CannotCompileException {
        int op = this.iterator.byteAt(this.newPos + 3);
        if (op == 89) {
            return (this.iterator.byteAt(this.newPos + 4) == 94 && this.iterator.byteAt(this.newPos + 5) == 88) ? 6 : 4;
        } else if (op == 90 && this.iterator.byteAt(this.newPos + 4) == 95) {
            return 5;
        } else {
            return 3;
        }
    }

    @Override // com.viaversion.viaversion.libs.javassist.expr.Expr
    public void replace(String statement) throws CannotCompileException {
        this.thisClass.getClassFile();
        int pos = this.newPos;
        int newIndex = this.iterator.u16bitAt(pos + 1);
        int codeSize = canReplace();
        int end = pos + codeSize;
        for (int i = pos; i < end; i++) {
            this.iterator.writeByte(0, i);
        }
        ConstPool constPool = getConstPool();
        int pos2 = this.currentPos;
        int methodIndex = this.iterator.u16bitAt(pos2 + 1);
        String signature = constPool.getMethodrefType(methodIndex);
        Javac jc = new Javac(this.thisClass);
        ClassPool cp = this.thisClass.getClassPool();
        CodeAttribute ca = this.iterator.get();
        try {
            CtClass[] params = Descriptor.getParameterTypes(signature, cp);
            CtClass newType = cp.get(this.newTypeName);
            int paramVar = ca.getMaxLocals();
            jc.recordParams(this.newTypeName, params, true, paramVar, withinStatic());
            int retVar = jc.recordReturnType(newType, true);
            jc.recordProceed(new ProceedForNew(newType, newIndex, methodIndex));
            checkResultValue(newType, statement);
            Bytecode bytecode = jc.getBytecode();
            storeStack(params, true, paramVar, bytecode);
            jc.recordLocalVariables(ca, pos2);
            bytecode.addConstZero(newType);
            bytecode.addStore(retVar, newType);
            jc.compileStmnt(statement);
            if (codeSize > 3) {
                bytecode.addAload(retVar);
            }
            replace0(pos2, bytecode, 3);
        } catch (NotFoundException e) {
            throw new CannotCompileException(e);
        } catch (BadBytecode e2) {
            throw new CannotCompileException("broken method");
        } catch (CompileError e3) {
            throw new CannotCompileException(e3);
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/expr/NewExpr$ProceedForNew.class */
    static class ProceedForNew implements ProceedHandler {
        CtClass newType;
        int newIndex;
        int methodIndex;

        ProceedForNew(CtClass nt, int ni, int mi) {
            this.newType = nt;
            this.newIndex = ni;
            this.methodIndex = mi;
        }

        @Override // com.viaversion.viaversion.libs.javassist.compiler.ProceedHandler
        public void doit(JvstCodeGen gen, Bytecode bytecode, ASTList args) throws CompileError {
            bytecode.addOpcode(187);
            bytecode.addIndex(this.newIndex);
            bytecode.addOpcode(89);
            gen.atMethodCallCore(this.newType, "<init>", args, false, true, -1, null);
            gen.setType(this.newType);
        }

        @Override // com.viaversion.viaversion.libs.javassist.compiler.ProceedHandler
        public void setReturnType(JvstTypeChecker c, ASTList args) throws CompileError {
            c.atMethodCallCore(this.newType, "<init>", args);
            c.setType(this.newType);
        }
    }
}
