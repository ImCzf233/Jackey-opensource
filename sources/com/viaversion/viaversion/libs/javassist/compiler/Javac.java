package com.viaversion.viaversion.libs.javassist.compiler;

import com.viaversion.viaversion.libs.javassist.CannotCompileException;
import com.viaversion.viaversion.libs.javassist.CtBehavior;
import com.viaversion.viaversion.libs.javassist.CtClass;
import com.viaversion.viaversion.libs.javassist.CtConstructor;
import com.viaversion.viaversion.libs.javassist.CtField;
import com.viaversion.viaversion.libs.javassist.CtMember;
import com.viaversion.viaversion.libs.javassist.CtMethod;
import com.viaversion.viaversion.libs.javassist.CtPrimitiveType;
import com.viaversion.viaversion.libs.javassist.Modifier;
import com.viaversion.viaversion.libs.javassist.NotFoundException;
import com.viaversion.viaversion.libs.javassist.bytecode.BadBytecode;
import com.viaversion.viaversion.libs.javassist.bytecode.Bytecode;
import com.viaversion.viaversion.libs.javassist.bytecode.CodeAttribute;
import com.viaversion.viaversion.libs.javassist.bytecode.LocalVariableAttribute;
import com.viaversion.viaversion.libs.javassist.compiler.ast.ASTList;
import com.viaversion.viaversion.libs.javassist.compiler.ast.ASTree;
import com.viaversion.viaversion.libs.javassist.compiler.ast.CallExpr;
import com.viaversion.viaversion.libs.javassist.compiler.ast.Declarator;
import com.viaversion.viaversion.libs.javassist.compiler.ast.Expr;
import com.viaversion.viaversion.libs.javassist.compiler.ast.FieldDecl;
import com.viaversion.viaversion.libs.javassist.compiler.ast.Member;
import com.viaversion.viaversion.libs.javassist.compiler.ast.MethodDecl;
import com.viaversion.viaversion.libs.javassist.compiler.ast.Stmnt;
import com.viaversion.viaversion.libs.javassist.compiler.ast.Symbol;
import org.spongepowered.asm.mixin.injection.invoke.arg.ArgsClassGenerator;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/compiler/Javac.class */
public class Javac {
    JvstCodeGen gen;
    SymbolTable stable;
    private Bytecode bytecode;
    public static final String param0Name = "$0";
    public static final String resultVarName = "$_";
    public static final String proceedName = "$proceed";

    public Javac(CtClass thisClass) {
        this(new Bytecode(thisClass.getClassFile2().getConstPool(), 0, 0), thisClass);
    }

    public Javac(Bytecode b, CtClass thisClass) {
        this.gen = new JvstCodeGen(b, thisClass, thisClass.getClassPool());
        this.stable = new SymbolTable();
        this.bytecode = b;
    }

    public Bytecode getBytecode() {
        return this.bytecode;
    }

    public CtMember compile(String src) throws CompileError {
        Parser p = new Parser(new Lex(src));
        ASTList mem = p.parseMember1(this.stable);
        try {
            if (mem instanceof FieldDecl) {
                return compileField((FieldDecl) mem);
            }
            CtBehavior cb = compileMethod(p, (MethodDecl) mem);
            CtClass decl = cb.getDeclaringClass();
            cb.getMethodInfo2().rebuildStackMapIf6(decl.getClassPool(), decl.getClassFile2());
            return cb;
        } catch (CannotCompileException e) {
            throw new CompileError(e.getMessage());
        } catch (BadBytecode bb) {
            throw new CompileError(bb.getMessage());
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/compiler/Javac$CtFieldWithInit.class */
    public static class CtFieldWithInit extends CtField {
        private ASTree init = null;

        CtFieldWithInit(CtClass type, String name, CtClass declaring) throws CannotCompileException {
            super(type, name, declaring);
        }

        protected void setInit(ASTree i) {
            this.init = i;
        }

        @Override // com.viaversion.viaversion.libs.javassist.CtField
        protected ASTree getInitAST() {
            return this.init;
        }
    }

    private CtField compileField(FieldDecl fd) throws CompileError, CannotCompileException {
        Declarator d = fd.getDeclarator();
        CtFieldWithInit f = new CtFieldWithInit(this.gen.resolver.lookupClass(d), d.getVariable().get(), this.gen.getThisClass());
        f.setModifiers(MemberResolver.getModifiers(fd.getModifiers()));
        if (fd.getInit() != null) {
            f.setInit(fd.getInit());
        }
        return f;
    }

    private CtBehavior compileMethod(Parser p, MethodDecl md) throws CompileError {
        int mod = MemberResolver.getModifiers(md.getModifiers());
        CtClass[] plist = this.gen.makeParamList(md);
        CtClass[] tlist = this.gen.makeThrowsList(md);
        recordParams(plist, Modifier.isStatic(mod));
        MethodDecl md2 = p.parseMethod2(this.stable, md);
        try {
            if (md2.isConstructor()) {
                CtConstructor cons = new CtConstructor(plist, this.gen.getThisClass());
                cons.setModifiers(mod);
                md2.accept(this.gen);
                cons.getMethodInfo().setCodeAttribute(this.bytecode.toCodeAttribute());
                cons.setExceptionTypes(tlist);
                return cons;
            }
            Declarator r = md2.getReturn();
            CtClass rtype = this.gen.resolver.lookupClass(r);
            recordReturnType(rtype, false);
            CtMethod method = new CtMethod(rtype, r.getVariable().get(), plist, this.gen.getThisClass());
            method.setModifiers(mod);
            this.gen.setThisMethod(method);
            md2.accept(this.gen);
            if (md2.getBody() != null) {
                method.getMethodInfo().setCodeAttribute(this.bytecode.toCodeAttribute());
            } else {
                method.setModifiers(mod | 1024);
            }
            method.setExceptionTypes(tlist);
            return method;
        } catch (NotFoundException e) {
            throw new CompileError(e.toString());
        }
    }

    public Bytecode compileBody(CtBehavior method, String src) throws CompileError {
        CtClass rtype;
        try {
            int mod = method.getModifiers();
            recordParams(method.getParameterTypes(), Modifier.isStatic(mod));
            if (method instanceof CtMethod) {
                this.gen.setThisMethod((CtMethod) method);
                rtype = ((CtMethod) method).getReturnType();
            } else {
                rtype = CtClass.voidType;
            }
            recordReturnType(rtype, false);
            boolean isVoid = rtype == CtClass.voidType;
            if (src == null) {
                makeDefaultBody(this.bytecode, rtype);
            } else {
                Parser p = new Parser(new Lex(src));
                SymbolTable stb = new SymbolTable(this.stable);
                Stmnt s = p.parseStatement(stb);
                if (p.hasMore()) {
                    throw new CompileError("the method/constructor body must be surrounded by {}");
                }
                boolean callSuper = false;
                if (method instanceof CtConstructor) {
                    callSuper = !((CtConstructor) method).isClassInitializer();
                }
                this.gen.atMethodBody(s, callSuper, isVoid);
            }
            return this.bytecode;
        } catch (NotFoundException e) {
            throw new CompileError(e.toString());
        }
    }

    private static void makeDefaultBody(Bytecode b, CtClass type) {
        int value;
        int op;
        if (type instanceof CtPrimitiveType) {
            CtPrimitiveType pt = (CtPrimitiveType) type;
            op = pt.getReturnOp();
            if (op == 175) {
                value = 14;
            } else if (op == 174) {
                value = 11;
            } else if (op == 173) {
                value = 9;
            } else if (op == 177) {
                value = 0;
            } else {
                value = 3;
            }
        } else {
            op = 176;
            value = 1;
        }
        if (value != 0) {
            b.addOpcode(value);
        }
        b.addOpcode(op);
    }

    public boolean recordLocalVariables(CodeAttribute ca, int pc) throws CompileError {
        LocalVariableAttribute va = (LocalVariableAttribute) ca.getAttribute(LocalVariableAttribute.tag);
        if (va == null) {
            return false;
        }
        int n = va.tableLength();
        for (int i = 0; i < n; i++) {
            int start = va.startPc(i);
            int len = va.codeLength(i);
            if (start <= pc && pc < start + len) {
                this.gen.recordVariable(va.descriptor(i), va.variableName(i), va.index(i), this.stable);
            }
        }
        return true;
    }

    public boolean recordParamNames(CodeAttribute ca, int numOfLocalVars) throws CompileError {
        LocalVariableAttribute va = (LocalVariableAttribute) ca.getAttribute(LocalVariableAttribute.tag);
        if (va == null) {
            return false;
        }
        int n = va.tableLength();
        for (int i = 0; i < n; i++) {
            int index = va.index(i);
            if (index < numOfLocalVars) {
                this.gen.recordVariable(va.descriptor(i), va.variableName(i), index, this.stable);
            }
        }
        return true;
    }

    public int recordParams(CtClass[] params, boolean isStatic) throws CompileError {
        return this.gen.recordParams(params, isStatic, ArgsClassGenerator.GETTER_PREFIX, "$args", "$$", this.stable);
    }

    public int recordParams(String target, CtClass[] params, boolean use0, int varNo, boolean isStatic) throws CompileError {
        return this.gen.recordParams(params, isStatic, ArgsClassGenerator.GETTER_PREFIX, "$args", "$$", use0, varNo, target, this.stable);
    }

    public void setMaxLocals(int max) {
        this.gen.setMaxLocals(max);
    }

    public int recordReturnType(CtClass type, boolean useResultVar) throws CompileError {
        this.gen.recordType(type);
        return this.gen.recordReturnType(type, "$r", useResultVar ? resultVarName : null, this.stable);
    }

    public void recordType(CtClass t) {
        this.gen.recordType(t);
    }

    public int recordVariable(CtClass type, String name) throws CompileError {
        return this.gen.recordVariable(type, name, this.stable);
    }

    public void recordProceed(String target, final String method) throws CompileError {
        Parser p = new Parser(new Lex(target));
        final ASTree texpr = p.parseExpression(this.stable);
        ProceedHandler h = new ProceedHandler() { // from class: com.viaversion.viaversion.libs.javassist.compiler.Javac.1
            @Override // com.viaversion.viaversion.libs.javassist.compiler.ProceedHandler
            public void doit(JvstCodeGen gen, Bytecode b, ASTList args) throws CompileError {
                ASTree expr = new Member(method);
                if (texpr != null) {
                    expr = Expr.make(46, texpr, expr);
                }
                gen.compileExpr(CallExpr.makeCall(expr, args));
                gen.addNullIfVoid();
            }

            @Override // com.viaversion.viaversion.libs.javassist.compiler.ProceedHandler
            public void setReturnType(JvstTypeChecker check, ASTList args) throws CompileError {
                ASTree expr = new Member(method);
                if (texpr != null) {
                    expr = Expr.make(46, texpr, expr);
                }
                CallExpr.makeCall(expr, args).accept(check);
                check.addNullIfVoid();
            }
        };
        this.gen.setProceedHandler(h, proceedName);
    }

    public void recordStaticProceed(final String targetClass, final String method) throws CompileError {
        ProceedHandler h = new ProceedHandler() { // from class: com.viaversion.viaversion.libs.javassist.compiler.Javac.2
            @Override // com.viaversion.viaversion.libs.javassist.compiler.ProceedHandler
            public void doit(JvstCodeGen gen, Bytecode b, ASTList args) throws CompileError {
                Expr expr = Expr.make(35, new Symbol(targetClass), new Member(method));
                gen.compileExpr(CallExpr.makeCall(expr, args));
                gen.addNullIfVoid();
            }

            @Override // com.viaversion.viaversion.libs.javassist.compiler.ProceedHandler
            public void setReturnType(JvstTypeChecker check, ASTList args) throws CompileError {
                Expr expr = Expr.make(35, new Symbol(targetClass), new Member(method));
                CallExpr.makeCall(expr, args).accept(check);
                check.addNullIfVoid();
            }
        };
        this.gen.setProceedHandler(h, proceedName);
    }

    public void recordSpecialProceed(String target, final String classname, final String methodname, final String descriptor, final int methodIndex) throws CompileError {
        Parser p = new Parser(new Lex(target));
        final ASTree texpr = p.parseExpression(this.stable);
        ProceedHandler h = new ProceedHandler() { // from class: com.viaversion.viaversion.libs.javassist.compiler.Javac.3
            @Override // com.viaversion.viaversion.libs.javassist.compiler.ProceedHandler
            public void doit(JvstCodeGen gen, Bytecode b, ASTList args) throws CompileError {
                gen.compileInvokeSpecial(texpr, methodIndex, descriptor, args);
            }

            @Override // com.viaversion.viaversion.libs.javassist.compiler.ProceedHandler
            public void setReturnType(JvstTypeChecker c, ASTList args) throws CompileError {
                c.compileInvokeSpecial(texpr, classname, methodname, descriptor, args);
            }
        };
        this.gen.setProceedHandler(h, proceedName);
    }

    public void recordProceed(ProceedHandler h) {
        this.gen.setProceedHandler(h, proceedName);
    }

    public void compileStmnt(String src) throws CompileError {
        Parser p = new Parser(new Lex(src));
        SymbolTable stb = new SymbolTable(this.stable);
        while (p.hasMore()) {
            Stmnt s = p.parseStatement(stb);
            if (s != null) {
                s.accept(this.gen);
            }
        }
    }

    public void compileExpr(String src) throws CompileError {
        ASTree e = parseExpr(src, this.stable);
        compileExpr(e);
    }

    public static ASTree parseExpr(String src, SymbolTable st) throws CompileError {
        Parser p = new Parser(new Lex(src));
        return p.parseExpression(st);
    }

    public void compileExpr(ASTree e) throws CompileError {
        if (e != null) {
            this.gen.compileExpr(e);
        }
    }
}
