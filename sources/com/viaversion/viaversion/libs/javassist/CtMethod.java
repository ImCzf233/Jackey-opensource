package com.viaversion.viaversion.libs.javassist;

import com.viaversion.viaversion.libs.javassist.bytecode.BadBytecode;
import com.viaversion.viaversion.libs.javassist.bytecode.Bytecode;
import com.viaversion.viaversion.libs.javassist.bytecode.CodeAttribute;
import com.viaversion.viaversion.libs.javassist.bytecode.CodeIterator;
import com.viaversion.viaversion.libs.javassist.bytecode.ConstPool;
import com.viaversion.viaversion.libs.javassist.bytecode.Descriptor;
import com.viaversion.viaversion.libs.javassist.bytecode.MethodInfo;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/CtMethod.class */
public final class CtMethod extends CtBehavior {
    protected String cachedStringRep;

    public CtMethod(MethodInfo minfo, CtClass declaring) {
        super(declaring, minfo);
        this.cachedStringRep = null;
    }

    public CtMethod(CtClass returnType, String mname, CtClass[] parameters, CtClass declaring) {
        this(null, declaring);
        ConstPool cp = declaring.getClassFile2().getConstPool();
        String desc = Descriptor.ofMethod(returnType, parameters);
        this.methodInfo = new MethodInfo(cp, mname, desc);
        setModifiers(1025);
    }

    public CtMethod(CtMethod src, CtClass declaring, ClassMap map) throws CannotCompileException {
        this(null, declaring);
        copy(src, false, map);
    }

    public static CtMethod make(String src, CtClass declaring) throws CannotCompileException {
        return CtNewMethod.make(src, declaring);
    }

    public static CtMethod make(MethodInfo minfo, CtClass declaring) throws CannotCompileException {
        if (declaring.getClassFile2().getConstPool() != minfo.getConstPool()) {
            throw new CannotCompileException("bad declaring class");
        }
        return new CtMethod(minfo, declaring);
    }

    public int hashCode() {
        return getStringRep().hashCode();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.viaversion.viaversion.libs.javassist.CtMember
    public void nameReplaced() {
        this.cachedStringRep = null;
    }

    public final String getStringRep() {
        if (this.cachedStringRep == null) {
            this.cachedStringRep = this.methodInfo.getName() + Descriptor.getParamDescriptor(this.methodInfo.getDescriptor());
        }
        return this.cachedStringRep;
    }

    public boolean equals(Object obj) {
        return obj != null && (obj instanceof CtMethod) && ((CtMethod) obj).getStringRep().equals(getStringRep());
    }

    @Override // com.viaversion.viaversion.libs.javassist.CtBehavior
    public String getLongName() {
        return getDeclaringClass().getName() + "." + getName() + Descriptor.toString(getSignature());
    }

    @Override // com.viaversion.viaversion.libs.javassist.CtMember
    public String getName() {
        return this.methodInfo.getName();
    }

    public void setName(String newname) {
        this.declaringClass.checkModify();
        this.methodInfo.setName(newname);
    }

    public CtClass getReturnType() throws NotFoundException {
        return getReturnType0();
    }

    @Override // com.viaversion.viaversion.libs.javassist.CtBehavior
    public boolean isEmpty() {
        CodeAttribute ca = getMethodInfo2().getCodeAttribute();
        if (ca == null) {
            return (getModifiers() & 1024) != 0;
        }
        CodeIterator it = ca.iterator();
        try {
            if (it.hasNext() && it.byteAt(it.next()) == 177) {
                if (!it.hasNext()) {
                    return true;
                }
            }
            return false;
        } catch (BadBytecode e) {
            return false;
        }
    }

    public void setBody(CtMethod src, ClassMap map) throws CannotCompileException {
        setBody0(src.declaringClass, src.methodInfo, this.declaringClass, this.methodInfo, map);
    }

    public void setWrappedBody(CtMethod mbody, ConstParameter constParam) throws CannotCompileException {
        this.declaringClass.checkModify();
        CtClass clazz = getDeclaringClass();
        try {
            CtClass[] params = getParameterTypes();
            CtClass retType = getReturnType();
            Bytecode code = CtNewWrappedMethod.makeBody(clazz, clazz.getClassFile2(), mbody, params, retType, constParam);
            CodeAttribute cattr = code.toCodeAttribute();
            this.methodInfo.setCodeAttribute(cattr);
            this.methodInfo.setAccessFlags(this.methodInfo.getAccessFlags() & (-1025));
        } catch (NotFoundException e) {
            throw new CannotCompileException(e);
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/CtMethod$ConstParameter.class */
    public static class ConstParameter {
        public static ConstParameter integer(int i) {
            return new IntConstParameter(i);
        }

        public static ConstParameter integer(long i) {
            return new LongConstParameter(i);
        }

        public static ConstParameter string(String s) {
            return new StringConstParameter(s);
        }

        ConstParameter() {
        }

        public int compile(Bytecode code) throws CannotCompileException {
            return 0;
        }

        public String descriptor() {
            return defaultDescriptor();
        }

        public static String defaultDescriptor() {
            return "([Ljava/lang/Object;)Ljava/lang/Object;";
        }

        public String constDescriptor() {
            return defaultConstDescriptor();
        }

        public static String defaultConstDescriptor() {
            return "([Ljava/lang/Object;)V";
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/CtMethod$IntConstParameter.class */
    public static class IntConstParameter extends ConstParameter {
        int param;

        IntConstParameter(int i) {
            this.param = i;
        }

        @Override // com.viaversion.viaversion.libs.javassist.CtMethod.ConstParameter
        public int compile(Bytecode code) throws CannotCompileException {
            code.addIconst(this.param);
            return 1;
        }

        @Override // com.viaversion.viaversion.libs.javassist.CtMethod.ConstParameter
        public String descriptor() {
            return "([Ljava/lang/Object;I)Ljava/lang/Object;";
        }

        @Override // com.viaversion.viaversion.libs.javassist.CtMethod.ConstParameter
        public String constDescriptor() {
            return "([Ljava/lang/Object;I)V";
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/CtMethod$LongConstParameter.class */
    static class LongConstParameter extends ConstParameter {
        long param;

        LongConstParameter(long l) {
            this.param = l;
        }

        @Override // com.viaversion.viaversion.libs.javassist.CtMethod.ConstParameter
        public int compile(Bytecode code) throws CannotCompileException {
            code.addLconst(this.param);
            return 2;
        }

        @Override // com.viaversion.viaversion.libs.javassist.CtMethod.ConstParameter
        public String descriptor() {
            return "([Ljava/lang/Object;J)Ljava/lang/Object;";
        }

        @Override // com.viaversion.viaversion.libs.javassist.CtMethod.ConstParameter
        public String constDescriptor() {
            return "([Ljava/lang/Object;J)V";
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/CtMethod$StringConstParameter.class */
    public static class StringConstParameter extends ConstParameter {
        String param;

        StringConstParameter(String s) {
            this.param = s;
        }

        @Override // com.viaversion.viaversion.libs.javassist.CtMethod.ConstParameter
        public int compile(Bytecode code) throws CannotCompileException {
            code.addLdc(this.param);
            return 1;
        }

        @Override // com.viaversion.viaversion.libs.javassist.CtMethod.ConstParameter
        public String descriptor() {
            return "([Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;";
        }

        @Override // com.viaversion.viaversion.libs.javassist.CtMethod.ConstParameter
        public String constDescriptor() {
            return "([Ljava/lang/Object;Ljava/lang/String;)V";
        }
    }
}
