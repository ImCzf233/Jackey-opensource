package com.viaversion.viaversion.libs.javassist.expr;

import com.viaversion.viaversion.libs.javassist.CannotCompileException;
import com.viaversion.viaversion.libs.javassist.ClassPool;
import com.viaversion.viaversion.libs.javassist.CtBehavior;
import com.viaversion.viaversion.libs.javassist.CtClass;
import com.viaversion.viaversion.libs.javassist.CtMethod;
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

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/expr/MethodCall.class */
public class MethodCall extends Expr {
    public MethodCall(int pos, CodeIterator i, CtClass declaring, MethodInfo m) {
        super(pos, i, declaring, m);
    }

    private int getNameAndType(ConstPool cp) {
        int pos = this.currentPos;
        int c = this.iterator.byteAt(pos);
        int index = this.iterator.u16bitAt(pos + 1);
        if (c == 185) {
            return cp.getInterfaceMethodrefNameAndType(index);
        }
        return cp.getMethodrefNameAndType(index);
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

    public CtClass getCtClass() throws NotFoundException {
        return this.thisClass.getClassPool().get(getClassName());
    }

    public String getClassName() {
        String cname;
        ConstPool cp = getConstPool();
        int pos = this.currentPos;
        int c = this.iterator.byteAt(pos);
        int index = this.iterator.u16bitAt(pos + 1);
        if (c == 185) {
            cname = cp.getInterfaceMethodrefClassName(index);
        } else {
            cname = cp.getMethodrefClassName(index);
        }
        if (cname.charAt(0) == '[') {
            cname = Descriptor.toClassName(cname);
        }
        return cname;
    }

    public String getMethodName() {
        ConstPool cp = getConstPool();
        int nt = getNameAndType(cp);
        return cp.getUtf8Info(cp.getNameAndTypeName(nt));
    }

    public CtMethod getMethod() throws NotFoundException {
        return getCtClass().getMethod(getMethodName(), getSignature());
    }

    public String getSignature() {
        ConstPool cp = getConstPool();
        int nt = getNameAndType(cp);
        return cp.getUtf8Info(cp.getNameAndTypeDescriptor(nt));
    }

    @Override // com.viaversion.viaversion.libs.javassist.expr.Expr
    public CtClass[] mayThrow() {
        return super.mayThrow();
    }

    public boolean isSuper() {
        return this.iterator.byteAt(this.currentPos) == 183 && !where().getDeclaringClass().getName().equals(getClassName());
    }

    @Override // com.viaversion.viaversion.libs.javassist.expr.Expr
    public void replace(String statement) throws CannotCompileException {
        int opcodeSize;
        String signature;
        String methodname;
        String classname;
        this.thisClass.getClassFile();
        ConstPool constPool = getConstPool();
        int pos = this.currentPos;
        int index = this.iterator.u16bitAt(pos + 1);
        int c = this.iterator.byteAt(pos);
        if (c == 185) {
            opcodeSize = 5;
            classname = constPool.getInterfaceMethodrefClassName(index);
            methodname = constPool.getInterfaceMethodrefName(index);
            signature = constPool.getInterfaceMethodrefType(index);
        } else if (c == 184 || c == 183 || c == 182) {
            opcodeSize = 3;
            classname = constPool.getMethodrefClassName(index);
            methodname = constPool.getMethodrefName(index);
            signature = constPool.getMethodrefType(index);
        } else {
            throw new CannotCompileException("not method invocation");
        }
        Javac jc = new Javac(this.thisClass);
        ClassPool cp = this.thisClass.getClassPool();
        CodeAttribute ca = this.iterator.get();
        try {
            CtClass[] params = Descriptor.getParameterTypes(signature, cp);
            CtClass retType = Descriptor.getReturnType(signature, cp);
            int paramVar = ca.getMaxLocals();
            jc.recordParams(classname, params, true, paramVar, withinStatic());
            int retVar = jc.recordReturnType(retType, true);
            if (c == 184) {
                jc.recordStaticProceed(classname, methodname);
            } else if (c == 183) {
                jc.recordSpecialProceed(Javac.param0Name, classname, methodname, signature, index);
            } else {
                jc.recordProceed(Javac.param0Name, methodname);
            }
            checkResultValue(retType, statement);
            Bytecode bytecode = jc.getBytecode();
            storeStack(params, c == 184, paramVar, bytecode);
            jc.recordLocalVariables(ca, pos);
            if (retType != CtClass.voidType) {
                bytecode.addConstZero(retType);
                bytecode.addStore(retVar, retType);
            }
            jc.compileStmnt(statement);
            if (retType != CtClass.voidType) {
                bytecode.addLoad(retVar, retType);
            }
            replace0(pos, bytecode, opcodeSize);
        } catch (NotFoundException e) {
            throw new CannotCompileException(e);
        } catch (BadBytecode e2) {
            throw new CannotCompileException("broken method");
        } catch (CompileError e3) {
            throw new CannotCompileException(e3);
        }
    }
}
