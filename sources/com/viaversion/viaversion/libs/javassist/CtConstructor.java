package com.viaversion.viaversion.libs.javassist;

import com.viaversion.viaversion.libs.javassist.bytecode.BadBytecode;
import com.viaversion.viaversion.libs.javassist.bytecode.Bytecode;
import com.viaversion.viaversion.libs.javassist.bytecode.ClassFile;
import com.viaversion.viaversion.libs.javassist.bytecode.CodeAttribute;
import com.viaversion.viaversion.libs.javassist.bytecode.CodeIterator;
import com.viaversion.viaversion.libs.javassist.bytecode.ConstPool;
import com.viaversion.viaversion.libs.javassist.bytecode.Descriptor;
import com.viaversion.viaversion.libs.javassist.bytecode.MethodInfo;
import com.viaversion.viaversion.libs.javassist.compiler.CompileError;
import com.viaversion.viaversion.libs.javassist.compiler.Javac;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/CtConstructor.class */
public final class CtConstructor extends CtBehavior {
    public CtConstructor(MethodInfo minfo, CtClass declaring) {
        super(declaring, minfo);
    }

    public CtConstructor(CtClass[] parameters, CtClass declaring) {
        this((MethodInfo) null, declaring);
        ConstPool cp = declaring.getClassFile2().getConstPool();
        String desc = Descriptor.ofConstructor(parameters);
        this.methodInfo = new MethodInfo(cp, "<init>", desc);
        setModifiers(1);
    }

    public CtConstructor(CtConstructor src, CtClass declaring, ClassMap map) throws CannotCompileException {
        this((MethodInfo) null, declaring);
        copy(src, true, map);
    }

    public boolean isConstructor() {
        return this.methodInfo.isConstructor();
    }

    public boolean isClassInitializer() {
        return this.methodInfo.isStaticInitializer();
    }

    @Override // com.viaversion.viaversion.libs.javassist.CtBehavior
    public String getLongName() {
        return getDeclaringClass().getName() + (isConstructor() ? Descriptor.toString(getSignature()) : ".<clinit>()");
    }

    @Override // com.viaversion.viaversion.libs.javassist.CtMember
    public String getName() {
        if (this.methodInfo.isStaticInitializer()) {
            return "<clinit>";
        }
        return this.declaringClass.getSimpleName();
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0078, code lost:
        if (r0.hasNext() == false) goto L20;
     */
    @Override // com.viaversion.viaversion.libs.javassist.CtBehavior
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean isEmpty() {
        /*
            r6 = this;
            r0 = r6
            com.viaversion.viaversion.libs.javassist.bytecode.MethodInfo r0 = r0.getMethodInfo2()
            com.viaversion.viaversion.libs.javassist.bytecode.CodeAttribute r0 = r0.getCodeAttribute()
            r7 = r0
            r0 = r7
            if (r0 != 0) goto Le
            r0 = 0
            return r0
        Le:
            r0 = r7
            com.viaversion.viaversion.libs.javassist.bytecode.ConstPool r0 = r0.getConstPool()
            r8 = r0
            r0 = r7
            com.viaversion.viaversion.libs.javassist.bytecode.CodeIterator r0 = r0.iterator()
            r9 = r0
            r0 = r9
            r1 = r9
            int r1 = r1.next()     // Catch: com.viaversion.viaversion.libs.javassist.bytecode.BadBytecode -> L81
            int r0 = r0.byteAt(r1)     // Catch: com.viaversion.viaversion.libs.javassist.bytecode.BadBytecode -> L81
            r12 = r0
            r0 = r12
            r1 = 177(0xb1, float:2.48E-43)
            if (r0 == r1) goto L7b
            r0 = r12
            r1 = 42
            if (r0 != r1) goto L7f
            r0 = r9
            r1 = r9
            int r1 = r1.next()     // Catch: com.viaversion.viaversion.libs.javassist.bytecode.BadBytecode -> L81
            r2 = r1
            r10 = r2
            int r0 = r0.byteAt(r1)     // Catch: com.viaversion.viaversion.libs.javassist.bytecode.BadBytecode -> L81
            r1 = 183(0xb7, float:2.56E-43)
            if (r0 != r1) goto L7f
            r0 = r8
            r1 = r6
            java.lang.String r1 = r1.getSuperclassName()     // Catch: com.viaversion.viaversion.libs.javassist.bytecode.BadBytecode -> L81
            r2 = r9
            r3 = r10
            r4 = 1
            int r3 = r3 + r4
            int r2 = r2.u16bitAt(r3)     // Catch: com.viaversion.viaversion.libs.javassist.bytecode.BadBytecode -> L81
            int r0 = r0.isConstructor(r1, r2)     // Catch: com.viaversion.viaversion.libs.javassist.bytecode.BadBytecode -> L81
            r1 = r0
            r11 = r1
            if (r0 == 0) goto L7f
            java.lang.String r0 = "()V"
            r1 = r8
            r2 = r11
            java.lang.String r1 = r1.getUtf8Info(r2)     // Catch: com.viaversion.viaversion.libs.javassist.bytecode.BadBytecode -> L81
            boolean r0 = r0.equals(r1)     // Catch: com.viaversion.viaversion.libs.javassist.bytecode.BadBytecode -> L81
            if (r0 == 0) goto L7f
            r0 = r9
            r1 = r9
            int r1 = r1.next()     // Catch: com.viaversion.viaversion.libs.javassist.bytecode.BadBytecode -> L81
            int r0 = r0.byteAt(r1)     // Catch: com.viaversion.viaversion.libs.javassist.bytecode.BadBytecode -> L81
            r1 = 177(0xb1, float:2.48E-43)
            if (r0 != r1) goto L7f
            r0 = r9
            boolean r0 = r0.hasNext()     // Catch: com.viaversion.viaversion.libs.javassist.bytecode.BadBytecode -> L81
            if (r0 != 0) goto L7f
        L7b:
            r0 = 1
            goto L80
        L7f:
            r0 = 0
        L80:
            return r0
        L81:
            r10 = move-exception
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.viaversion.viaversion.libs.javassist.CtConstructor.isEmpty():boolean");
    }

    private String getSuperclassName() {
        ClassFile cf = this.declaringClass.getClassFile2();
        return cf.getSuperclass();
    }

    public boolean callsSuper() throws CannotCompileException {
        CodeAttribute codeAttr = this.methodInfo.getCodeAttribute();
        if (codeAttr != null) {
            CodeIterator it = codeAttr.iterator();
            try {
                int index = it.skipSuperConstructor();
                return index >= 0;
            } catch (BadBytecode e) {
                throw new CannotCompileException(e);
            }
        }
        return false;
    }

    @Override // com.viaversion.viaversion.libs.javassist.CtBehavior
    public void setBody(String src) throws CannotCompileException {
        if (src == null) {
            if (isClassInitializer()) {
                src = ";";
            } else {
                src = "super();";
            }
        }
        super.setBody(src);
    }

    public void setBody(CtConstructor src, ClassMap map) throws CannotCompileException {
        setBody0(src.declaringClass, src.methodInfo, this.declaringClass, this.methodInfo, map);
    }

    public void insertBeforeBody(String src) throws CannotCompileException {
        CtClass cc = this.declaringClass;
        cc.checkModify();
        if (isClassInitializer()) {
            throw new CannotCompileException("class initializer");
        }
        CodeAttribute ca = this.methodInfo.getCodeAttribute();
        CodeIterator iterator = ca.iterator();
        Bytecode b = new Bytecode(this.methodInfo.getConstPool(), ca.getMaxStack(), ca.getMaxLocals());
        b.setStackDepth(ca.getMaxStack());
        Javac jv = new Javac(b, cc);
        try {
            jv.recordParams(getParameterTypes(), false);
            jv.compileStmnt(src);
            ca.setMaxStack(b.getMaxStack());
            ca.setMaxLocals(b.getMaxLocals());
            iterator.skipConstructor();
            int pos = iterator.insertEx(b.get());
            iterator.insert(b.getExceptionTable(), pos);
            this.methodInfo.rebuildStackMapIf6(cc.getClassPool(), cc.getClassFile2());
        } catch (NotFoundException e) {
            throw new CannotCompileException(e);
        } catch (BadBytecode e2) {
            throw new CannotCompileException(e2);
        } catch (CompileError e3) {
            throw new CannotCompileException(e3);
        }
    }

    @Override // com.viaversion.viaversion.libs.javassist.CtBehavior
    int getStartPosOfBody(CodeAttribute ca) throws CannotCompileException {
        CodeIterator ci = ca.iterator();
        try {
            ci.skipConstructor();
            return ci.next();
        } catch (BadBytecode e) {
            throw new CannotCompileException(e);
        }
    }

    public CtMethod toMethod(String name, CtClass declaring) throws CannotCompileException {
        return toMethod(name, declaring, null);
    }

    public CtMethod toMethod(String name, CtClass declaring, ClassMap map) throws CannotCompileException {
        CtMethod method = new CtMethod(null, declaring);
        method.copy(this, false, map);
        if (isConstructor()) {
            MethodInfo minfo = method.getMethodInfo2();
            CodeAttribute ca = minfo.getCodeAttribute();
            if (ca != null) {
                removeConsCall(ca);
                try {
                    this.methodInfo.rebuildStackMapIf6(declaring.getClassPool(), declaring.getClassFile2());
                } catch (BadBytecode e) {
                    throw new CannotCompileException(e);
                }
            }
        }
        method.setName(name);
        return method;
    }

    private static void removeConsCall(CodeAttribute ca) throws CannotCompileException {
        CodeIterator iterator = ca.iterator();
        try {
            int pos = iterator.skipConstructor();
            if (pos >= 0) {
                int mref = iterator.u16bitAt(pos + 1);
                String desc = ca.getConstPool().getMethodrefType(mref);
                int num = Descriptor.numOfParameters(desc) + 1;
                if (num > 3) {
                    pos = iterator.insertGapAt(pos, num - 3, false).position;
                }
                int i = pos;
                int pos2 = pos + 1;
                iterator.writeByte(87, i);
                iterator.writeByte(0, pos2);
                iterator.writeByte(0, pos2 + 1);
                Descriptor.Iterator it = new Descriptor.Iterator(desc);
                while (true) {
                    it.next();
                    if (!it.isParameter()) {
                        break;
                    }
                    int i2 = pos2;
                    pos2++;
                    iterator.writeByte(it.is2byte() ? 88 : 87, i2);
                }
            }
        } catch (BadBytecode e) {
            throw new CannotCompileException(e);
        }
    }
}
