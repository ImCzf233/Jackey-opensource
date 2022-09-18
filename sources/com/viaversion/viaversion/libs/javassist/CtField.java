package com.viaversion.viaversion.libs.javassist;

import com.viaversion.viaversion.libs.javassist.bytecode.AccessFlag;
import com.viaversion.viaversion.libs.javassist.bytecode.AnnotationsAttribute;
import com.viaversion.viaversion.libs.javassist.bytecode.AttributeInfo;
import com.viaversion.viaversion.libs.javassist.bytecode.Bytecode;
import com.viaversion.viaversion.libs.javassist.bytecode.ClassFile;
import com.viaversion.viaversion.libs.javassist.bytecode.ConstPool;
import com.viaversion.viaversion.libs.javassist.bytecode.Descriptor;
import com.viaversion.viaversion.libs.javassist.bytecode.FieldInfo;
import com.viaversion.viaversion.libs.javassist.bytecode.SignatureAttribute;
import com.viaversion.viaversion.libs.javassist.compiler.CompileError;
import com.viaversion.viaversion.libs.javassist.compiler.Javac;
import com.viaversion.viaversion.libs.javassist.compiler.SymbolTable;
import com.viaversion.viaversion.libs.javassist.compiler.ast.ASTree;
import com.viaversion.viaversion.libs.javassist.compiler.ast.DoubleConst;
import com.viaversion.viaversion.libs.javassist.compiler.ast.IntConst;
import com.viaversion.viaversion.libs.javassist.compiler.ast.StringL;
import java.util.List;
import jdk.internal.dynalink.CallSiteDescriptor;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/CtField.class */
public class CtField extends CtMember {
    static final String javaLangString = "java.lang.String";
    protected FieldInfo fieldInfo;

    public CtField(CtClass type, String name, CtClass declaring) throws CannotCompileException {
        this(Descriptor.m145of(type), name, declaring);
    }

    public CtField(CtField src, CtClass declaring) throws CannotCompileException {
        this(src.fieldInfo.getDescriptor(), src.fieldInfo.getName(), declaring);
        FieldInfo fi = this.fieldInfo;
        fi.setAccessFlags(src.fieldInfo.getAccessFlags());
        ConstPool cp = fi.getConstPool();
        List<AttributeInfo> attributes = src.fieldInfo.getAttributes();
        for (AttributeInfo ainfo : attributes) {
            fi.addAttribute(ainfo.copy(cp, null));
        }
    }

    private CtField(String typeDesc, String name, CtClass clazz) throws CannotCompileException {
        super(clazz);
        ClassFile cf = clazz.getClassFile2();
        if (cf == null) {
            throw new CannotCompileException("bad declaring class: " + clazz.getName());
        }
        this.fieldInfo = new FieldInfo(cf.getConstPool(), name, typeDesc);
    }

    public CtField(FieldInfo fi, CtClass clazz) {
        super(clazz);
        this.fieldInfo = fi;
    }

    @Override // com.viaversion.viaversion.libs.javassist.CtMember
    public String toString() {
        return getDeclaringClass().getName() + "." + getName() + CallSiteDescriptor.TOKEN_DELIMITER + this.fieldInfo.getDescriptor();
    }

    @Override // com.viaversion.viaversion.libs.javassist.CtMember
    protected void extendToString(StringBuffer buffer) {
        buffer.append(' ');
        buffer.append(getName());
        buffer.append(' ');
        buffer.append(this.fieldInfo.getDescriptor());
    }

    protected ASTree getInitAST() {
        return null;
    }

    public Initializer getInit() {
        ASTree tree = getInitAST();
        if (tree == null) {
            return null;
        }
        return Initializer.byExpr(tree);
    }

    public static CtField make(String src, CtClass declaring) throws CannotCompileException {
        Javac compiler = new Javac(declaring);
        try {
            CtMember obj = compiler.compile(src);
            if (obj instanceof CtField) {
                return (CtField) obj;
            }
            throw new CannotCompileException("not a field");
        } catch (CompileError e) {
            throw new CannotCompileException(e);
        }
    }

    public FieldInfo getFieldInfo() {
        this.declaringClass.checkModify();
        return this.fieldInfo;
    }

    public FieldInfo getFieldInfo2() {
        return this.fieldInfo;
    }

    @Override // com.viaversion.viaversion.libs.javassist.CtMember
    public CtClass getDeclaringClass() {
        return super.getDeclaringClass();
    }

    @Override // com.viaversion.viaversion.libs.javassist.CtMember
    public String getName() {
        return this.fieldInfo.getName();
    }

    public void setName(String newName) {
        this.declaringClass.checkModify();
        this.fieldInfo.setName(newName);
    }

    @Override // com.viaversion.viaversion.libs.javassist.CtMember
    public int getModifiers() {
        return AccessFlag.toModifier(this.fieldInfo.getAccessFlags());
    }

    @Override // com.viaversion.viaversion.libs.javassist.CtMember
    public void setModifiers(int mod) {
        this.declaringClass.checkModify();
        this.fieldInfo.setAccessFlags(AccessFlag.m146of(mod));
    }

    @Override // com.viaversion.viaversion.libs.javassist.CtMember
    public boolean hasAnnotation(String typeName) {
        FieldInfo fi = getFieldInfo2();
        AnnotationsAttribute ainfo = (AnnotationsAttribute) fi.getAttribute(AnnotationsAttribute.invisibleTag);
        AnnotationsAttribute ainfo2 = (AnnotationsAttribute) fi.getAttribute(AnnotationsAttribute.visibleTag);
        return CtClassType.hasAnnotationType(typeName, getDeclaringClass().getClassPool(), ainfo, ainfo2);
    }

    @Override // com.viaversion.viaversion.libs.javassist.CtMember
    public Object getAnnotation(Class<?> clz) throws ClassNotFoundException {
        FieldInfo fi = getFieldInfo2();
        AnnotationsAttribute ainfo = (AnnotationsAttribute) fi.getAttribute(AnnotationsAttribute.invisibleTag);
        AnnotationsAttribute ainfo2 = (AnnotationsAttribute) fi.getAttribute(AnnotationsAttribute.visibleTag);
        return CtClassType.getAnnotationType(clz, getDeclaringClass().getClassPool(), ainfo, ainfo2);
    }

    @Override // com.viaversion.viaversion.libs.javassist.CtMember
    public Object[] getAnnotations() throws ClassNotFoundException {
        return getAnnotations(false);
    }

    @Override // com.viaversion.viaversion.libs.javassist.CtMember
    public Object[] getAvailableAnnotations() {
        try {
            return getAnnotations(true);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Unexpected exception", e);
        }
    }

    private Object[] getAnnotations(boolean ignoreNotFound) throws ClassNotFoundException {
        FieldInfo fi = getFieldInfo2();
        AnnotationsAttribute ainfo = (AnnotationsAttribute) fi.getAttribute(AnnotationsAttribute.invisibleTag);
        AnnotationsAttribute ainfo2 = (AnnotationsAttribute) fi.getAttribute(AnnotationsAttribute.visibleTag);
        return CtClassType.toAnnotationType(ignoreNotFound, getDeclaringClass().getClassPool(), ainfo, ainfo2);
    }

    @Override // com.viaversion.viaversion.libs.javassist.CtMember
    public String getSignature() {
        return this.fieldInfo.getDescriptor();
    }

    @Override // com.viaversion.viaversion.libs.javassist.CtMember
    public String getGenericSignature() {
        SignatureAttribute sa = (SignatureAttribute) this.fieldInfo.getAttribute(SignatureAttribute.tag);
        if (sa == null) {
            return null;
        }
        return sa.getSignature();
    }

    @Override // com.viaversion.viaversion.libs.javassist.CtMember
    public void setGenericSignature(String sig) {
        this.declaringClass.checkModify();
        this.fieldInfo.addAttribute(new SignatureAttribute(this.fieldInfo.getConstPool(), sig));
    }

    public CtClass getType() throws NotFoundException {
        return Descriptor.toCtClass(this.fieldInfo.getDescriptor(), this.declaringClass.getClassPool());
    }

    public void setType(CtClass clazz) {
        this.declaringClass.checkModify();
        this.fieldInfo.setDescriptor(Descriptor.m145of(clazz));
    }

    public Object getConstantValue() {
        int index = this.fieldInfo.getConstantValue();
        if (index == 0) {
            return null;
        }
        ConstPool cp = this.fieldInfo.getConstPool();
        switch (cp.getTag(index)) {
            case 3:
                int value = cp.getIntegerInfo(index);
                if (!"Z".equals(this.fieldInfo.getDescriptor())) {
                    return Integer.valueOf(value);
                }
                return Boolean.valueOf(value != 0);
            case 4:
                return Float.valueOf(cp.getFloatInfo(index));
            case 5:
                return Long.valueOf(cp.getLongInfo(index));
            case 6:
                return Double.valueOf(cp.getDoubleInfo(index));
            case 7:
            default:
                throw new RuntimeException("bad tag: " + cp.getTag(index) + " at " + index);
            case 8:
                return cp.getStringInfo(index);
        }
    }

    @Override // com.viaversion.viaversion.libs.javassist.CtMember
    public byte[] getAttribute(String name) {
        AttributeInfo ai = this.fieldInfo.getAttribute(name);
        if (ai == null) {
            return null;
        }
        return ai.get();
    }

    @Override // com.viaversion.viaversion.libs.javassist.CtMember
    public void setAttribute(String name, byte[] data) {
        this.declaringClass.checkModify();
        this.fieldInfo.addAttribute(new AttributeInfo(this.fieldInfo.getConstPool(), name, data));
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/CtField$Initializer.class */
    public static abstract class Initializer {
        public abstract int compile(CtClass ctClass, String str, Bytecode bytecode, CtClass[] ctClassArr, Javac javac) throws CannotCompileException;

        public abstract int compileIfStatic(CtClass ctClass, String str, Bytecode bytecode, Javac javac) throws CannotCompileException;

        public static Initializer constant(int i) {
            return new IntInitializer(i);
        }

        public static Initializer constant(boolean b) {
            return new IntInitializer(b ? 1 : 0);
        }

        public static Initializer constant(long l) {
            return new LongInitializer(l);
        }

        public static Initializer constant(float l) {
            return new FloatInitializer(l);
        }

        public static Initializer constant(double d) {
            return new DoubleInitializer(d);
        }

        public static Initializer constant(String s) {
            return new StringInitializer(s);
        }

        public static Initializer byParameter(int nth) {
            ParamInitializer i = new ParamInitializer();
            i.nthParam = nth;
            return i;
        }

        public static Initializer byNew(CtClass objectType) {
            NewInitializer i = new NewInitializer();
            i.objectType = objectType;
            i.stringParams = null;
            i.withConstructorParams = false;
            return i;
        }

        public static Initializer byNew(CtClass objectType, String[] stringParams) {
            NewInitializer i = new NewInitializer();
            i.objectType = objectType;
            i.stringParams = stringParams;
            i.withConstructorParams = false;
            return i;
        }

        public static Initializer byNewWithParams(CtClass objectType) {
            NewInitializer i = new NewInitializer();
            i.objectType = objectType;
            i.stringParams = null;
            i.withConstructorParams = true;
            return i;
        }

        public static Initializer byNewWithParams(CtClass objectType, String[] stringParams) {
            NewInitializer i = new NewInitializer();
            i.objectType = objectType;
            i.stringParams = stringParams;
            i.withConstructorParams = true;
            return i;
        }

        public static Initializer byCall(CtClass methodClass, String methodName) {
            MethodInitializer i = new MethodInitializer();
            i.objectType = methodClass;
            i.methodName = methodName;
            i.stringParams = null;
            i.withConstructorParams = false;
            return i;
        }

        public static Initializer byCall(CtClass methodClass, String methodName, String[] stringParams) {
            MethodInitializer i = new MethodInitializer();
            i.objectType = methodClass;
            i.methodName = methodName;
            i.stringParams = stringParams;
            i.withConstructorParams = false;
            return i;
        }

        public static Initializer byCallWithParams(CtClass methodClass, String methodName) {
            MethodInitializer i = new MethodInitializer();
            i.objectType = methodClass;
            i.methodName = methodName;
            i.stringParams = null;
            i.withConstructorParams = true;
            return i;
        }

        public static Initializer byCallWithParams(CtClass methodClass, String methodName, String[] stringParams) {
            MethodInitializer i = new MethodInitializer();
            i.objectType = methodClass;
            i.methodName = methodName;
            i.stringParams = stringParams;
            i.withConstructorParams = true;
            return i;
        }

        public static Initializer byNewArray(CtClass type, int size) throws NotFoundException {
            return new ArrayInitializer(type.getComponentType(), size);
        }

        public static Initializer byNewArray(CtClass type, int[] sizes) {
            return new MultiArrayInitializer(type, sizes);
        }

        public static Initializer byExpr(String source) {
            return new CodeInitializer(source);
        }

        static Initializer byExpr(ASTree source) {
            return new PtreeInitializer(source);
        }

        public void check(String desc) throws CannotCompileException {
        }

        public int getConstantValue(ConstPool cp, CtClass type) {
            return 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/CtField$CodeInitializer0.class */
    public static abstract class CodeInitializer0 extends Initializer {
        abstract void compileExpr(Javac javac) throws CompileError;

        CodeInitializer0() {
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.viaversion.viaversion.libs.javassist.CtField.Initializer
        public int compile(CtClass type, String name, Bytecode code, CtClass[] parameters, Javac drv) throws CannotCompileException {
            try {
                code.addAload(0);
                compileExpr(drv);
                code.addPutfield(Bytecode.THIS, name, Descriptor.m145of(type));
                return code.getMaxStack();
            } catch (CompileError e) {
                throw new CannotCompileException(e);
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.viaversion.viaversion.libs.javassist.CtField.Initializer
        public int compileIfStatic(CtClass type, String name, Bytecode code, Javac drv) throws CannotCompileException {
            try {
                compileExpr(drv);
                code.addPutstatic(Bytecode.THIS, name, Descriptor.m145of(type));
                return code.getMaxStack();
            } catch (CompileError e) {
                throw new CannotCompileException(e);
            }
        }

        int getConstantValue2(ConstPool cp, CtClass type, ASTree tree) {
            if (type.isPrimitive()) {
                if (tree instanceof IntConst) {
                    long value = ((IntConst) tree).get();
                    if (type == CtClass.doubleType) {
                        return cp.addDoubleInfo(value);
                    }
                    if (type == CtClass.floatType) {
                        return cp.addFloatInfo((float) value);
                    }
                    if (type == CtClass.longType) {
                        return cp.addLongInfo(value);
                    }
                    if (type != CtClass.voidType) {
                        return cp.addIntegerInfo((int) value);
                    }
                    return 0;
                } else if (tree instanceof DoubleConst) {
                    double value2 = ((DoubleConst) tree).get();
                    if (type == CtClass.floatType) {
                        return cp.addFloatInfo((float) value2);
                    }
                    if (type == CtClass.doubleType) {
                        return cp.addDoubleInfo(value2);
                    }
                    return 0;
                } else {
                    return 0;
                }
            } else if ((tree instanceof StringL) && type.getName().equals(CtField.javaLangString)) {
                return cp.addStringInfo(((StringL) tree).get());
            } else {
                return 0;
            }
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/CtField$CodeInitializer.class */
    public static class CodeInitializer extends CodeInitializer0 {
        private String expression;

        CodeInitializer(String expr) {
            this.expression = expr;
        }

        @Override // com.viaversion.viaversion.libs.javassist.CtField.CodeInitializer0
        void compileExpr(Javac drv) throws CompileError {
            drv.compileExpr(this.expression);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.viaversion.viaversion.libs.javassist.CtField.Initializer
        public int getConstantValue(ConstPool cp, CtClass type) {
            try {
                ASTree t = Javac.parseExpr(this.expression, new SymbolTable());
                return getConstantValue2(cp, type, t);
            } catch (CompileError e) {
                return 0;
            }
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/CtField$PtreeInitializer.class */
    public static class PtreeInitializer extends CodeInitializer0 {
        private ASTree expression;

        PtreeInitializer(ASTree expr) {
            this.expression = expr;
        }

        @Override // com.viaversion.viaversion.libs.javassist.CtField.CodeInitializer0
        void compileExpr(Javac drv) throws CompileError {
            drv.compileExpr(this.expression);
        }

        @Override // com.viaversion.viaversion.libs.javassist.CtField.Initializer
        public int getConstantValue(ConstPool cp, CtClass type) {
            return getConstantValue2(cp, type, this.expression);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/CtField$ParamInitializer.class */
    public static class ParamInitializer extends Initializer {
        int nthParam;

        ParamInitializer() {
        }

        @Override // com.viaversion.viaversion.libs.javassist.CtField.Initializer
        public int compile(CtClass type, String name, Bytecode code, CtClass[] parameters, Javac drv) throws CannotCompileException {
            if (parameters != null && this.nthParam < parameters.length) {
                code.addAload(0);
                int nth = nthParamToLocal(this.nthParam, parameters, false);
                int s = code.addLoad(nth, type) + 1;
                code.addPutfield(Bytecode.THIS, name, Descriptor.m145of(type));
                return s;
            }
            return 0;
        }

        static int nthParamToLocal(int nth, CtClass[] params, boolean isStatic) {
            int k;
            CtClass longType = CtClass.longType;
            CtClass doubleType = CtClass.doubleType;
            if (isStatic) {
                k = 0;
            } else {
                k = 1;
            }
            for (int i = 0; i < nth; i++) {
                CtClass type = params[i];
                if (type == longType || type == doubleType) {
                    k += 2;
                } else {
                    k++;
                }
            }
            return k;
        }

        @Override // com.viaversion.viaversion.libs.javassist.CtField.Initializer
        public int compileIfStatic(CtClass type, String name, Bytecode code, Javac drv) throws CannotCompileException {
            return 0;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/CtField$NewInitializer.class */
    public static class NewInitializer extends Initializer {
        CtClass objectType;
        String[] stringParams;
        boolean withConstructorParams;

        NewInitializer() {
        }

        @Override // com.viaversion.viaversion.libs.javassist.CtField.Initializer
        public int compile(CtClass type, String name, Bytecode code, CtClass[] parameters, Javac drv) throws CannotCompileException {
            int stacksize;
            code.addAload(0);
            code.addNew(this.objectType);
            code.add(89);
            code.addAload(0);
            if (this.stringParams == null) {
                stacksize = 4;
            } else {
                stacksize = compileStringParameter(code) + 4;
            }
            if (this.withConstructorParams) {
                stacksize += CtNewWrappedMethod.compileParameterList(code, parameters, 1);
            }
            code.addInvokespecial(this.objectType, "<init>", getDescriptor());
            code.addPutfield(Bytecode.THIS, name, Descriptor.m145of(type));
            return stacksize;
        }

        private String getDescriptor() {
            if (this.stringParams == null) {
                if (this.withConstructorParams) {
                    return "(Ljava/lang/Object;[Ljava/lang/Object;)V";
                }
                return "(Ljava/lang/Object;)V";
            } else if (this.withConstructorParams) {
                return "(Ljava/lang/Object;[Ljava/lang/String;[Ljava/lang/Object;)V";
            } else {
                return "(Ljava/lang/Object;[Ljava/lang/String;)V";
            }
        }

        @Override // com.viaversion.viaversion.libs.javassist.CtField.Initializer
        public int compileIfStatic(CtClass type, String name, Bytecode code, Javac drv) throws CannotCompileException {
            String desc;
            code.addNew(this.objectType);
            code.add(89);
            int stacksize = 2;
            if (this.stringParams == null) {
                desc = "()V";
            } else {
                desc = "([Ljava/lang/String;)V";
                stacksize = 2 + compileStringParameter(code);
            }
            code.addInvokespecial(this.objectType, "<init>", desc);
            code.addPutstatic(Bytecode.THIS, name, Descriptor.m145of(type));
            return stacksize;
        }

        protected final int compileStringParameter(Bytecode code) throws CannotCompileException {
            int nparam = this.stringParams.length;
            code.addIconst(nparam);
            code.addAnewarray(CtField.javaLangString);
            for (int j = 0; j < nparam; j++) {
                code.add(89);
                code.addIconst(j);
                code.addLdc(this.stringParams[j]);
                code.add(83);
            }
            return 4;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/CtField$MethodInitializer.class */
    static class MethodInitializer extends NewInitializer {
        String methodName;

        MethodInitializer() {
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.viaversion.viaversion.libs.javassist.CtField.NewInitializer, com.viaversion.viaversion.libs.javassist.CtField.Initializer
        public int compile(CtClass type, String name, Bytecode code, CtClass[] parameters, Javac drv) throws CannotCompileException {
            int stacksize;
            code.addAload(0);
            code.addAload(0);
            if (this.stringParams == null) {
                stacksize = 2;
            } else {
                stacksize = compileStringParameter(code) + 2;
            }
            if (this.withConstructorParams) {
                stacksize += CtNewWrappedMethod.compileParameterList(code, parameters, 1);
            }
            String typeDesc = Descriptor.m145of(type);
            String mDesc = getDescriptor() + typeDesc;
            code.addInvokestatic(this.objectType, this.methodName, mDesc);
            code.addPutfield(Bytecode.THIS, name, typeDesc);
            return stacksize;
        }

        private String getDescriptor() {
            if (this.stringParams == null) {
                if (this.withConstructorParams) {
                    return "(Ljava/lang/Object;[Ljava/lang/Object;)";
                }
                return "(Ljava/lang/Object;)";
            } else if (this.withConstructorParams) {
                return "(Ljava/lang/Object;[Ljava/lang/String;[Ljava/lang/Object;)";
            } else {
                return "(Ljava/lang/Object;[Ljava/lang/String;)";
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.viaversion.viaversion.libs.javassist.CtField.NewInitializer, com.viaversion.viaversion.libs.javassist.CtField.Initializer
        public int compileIfStatic(CtClass type, String name, Bytecode code, Javac drv) throws CannotCompileException {
            String desc;
            int stacksize = 1;
            if (this.stringParams == null) {
                desc = "()";
            } else {
                desc = "([Ljava/lang/String;)";
                stacksize = 1 + compileStringParameter(code);
            }
            String typeDesc = Descriptor.m145of(type);
            code.addInvokestatic(this.objectType, this.methodName, desc + typeDesc);
            code.addPutstatic(Bytecode.THIS, name, typeDesc);
            return stacksize;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/CtField$IntInitializer.class */
    static class IntInitializer extends Initializer {
        int value;

        IntInitializer(int v) {
            this.value = v;
        }

        @Override // com.viaversion.viaversion.libs.javassist.CtField.Initializer
        public void check(String desc) throws CannotCompileException {
            char c = desc.charAt(0);
            if (c != 'I' && c != 'S' && c != 'B' && c != 'C' && c != 'Z') {
                throw new CannotCompileException("type mismatch");
            }
        }

        @Override // com.viaversion.viaversion.libs.javassist.CtField.Initializer
        public int compile(CtClass type, String name, Bytecode code, CtClass[] parameters, Javac drv) throws CannotCompileException {
            code.addAload(0);
            code.addIconst(this.value);
            code.addPutfield(Bytecode.THIS, name, Descriptor.m145of(type));
            return 2;
        }

        @Override // com.viaversion.viaversion.libs.javassist.CtField.Initializer
        public int compileIfStatic(CtClass type, String name, Bytecode code, Javac drv) throws CannotCompileException {
            code.addIconst(this.value);
            code.addPutstatic(Bytecode.THIS, name, Descriptor.m145of(type));
            return 1;
        }

        @Override // com.viaversion.viaversion.libs.javassist.CtField.Initializer
        public int getConstantValue(ConstPool cp, CtClass type) {
            return cp.addIntegerInfo(this.value);
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/CtField$LongInitializer.class */
    static class LongInitializer extends Initializer {
        long value;

        LongInitializer(long v) {
            this.value = v;
        }

        @Override // com.viaversion.viaversion.libs.javassist.CtField.Initializer
        public void check(String desc) throws CannotCompileException {
            if (!desc.equals("J")) {
                throw new CannotCompileException("type mismatch");
            }
        }

        @Override // com.viaversion.viaversion.libs.javassist.CtField.Initializer
        public int compile(CtClass type, String name, Bytecode code, CtClass[] parameters, Javac drv) throws CannotCompileException {
            code.addAload(0);
            code.addLdc2w(this.value);
            code.addPutfield(Bytecode.THIS, name, Descriptor.m145of(type));
            return 3;
        }

        @Override // com.viaversion.viaversion.libs.javassist.CtField.Initializer
        public int compileIfStatic(CtClass type, String name, Bytecode code, Javac drv) throws CannotCompileException {
            code.addLdc2w(this.value);
            code.addPutstatic(Bytecode.THIS, name, Descriptor.m145of(type));
            return 2;
        }

        @Override // com.viaversion.viaversion.libs.javassist.CtField.Initializer
        public int getConstantValue(ConstPool cp, CtClass type) {
            if (type == CtClass.longType) {
                return cp.addLongInfo(this.value);
            }
            return 0;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/CtField$FloatInitializer.class */
    static class FloatInitializer extends Initializer {
        float value;

        FloatInitializer(float v) {
            this.value = v;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.viaversion.viaversion.libs.javassist.CtField.Initializer
        public void check(String desc) throws CannotCompileException {
            if (!desc.equals("F")) {
                throw new CannotCompileException("type mismatch");
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.viaversion.viaversion.libs.javassist.CtField.Initializer
        public int compile(CtClass type, String name, Bytecode code, CtClass[] parameters, Javac drv) throws CannotCompileException {
            code.addAload(0);
            code.addFconst(this.value);
            code.addPutfield(Bytecode.THIS, name, Descriptor.m145of(type));
            return 3;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.viaversion.viaversion.libs.javassist.CtField.Initializer
        public int compileIfStatic(CtClass type, String name, Bytecode code, Javac drv) throws CannotCompileException {
            code.addFconst(this.value);
            code.addPutstatic(Bytecode.THIS, name, Descriptor.m145of(type));
            return 2;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.viaversion.viaversion.libs.javassist.CtField.Initializer
        public int getConstantValue(ConstPool cp, CtClass type) {
            if (type == CtClass.floatType) {
                return cp.addFloatInfo(this.value);
            }
            return 0;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/CtField$DoubleInitializer.class */
    static class DoubleInitializer extends Initializer {
        double value;

        DoubleInitializer(double v) {
            this.value = v;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.viaversion.viaversion.libs.javassist.CtField.Initializer
        public void check(String desc) throws CannotCompileException {
            if (!desc.equals("D")) {
                throw new CannotCompileException("type mismatch");
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.viaversion.viaversion.libs.javassist.CtField.Initializer
        public int compile(CtClass type, String name, Bytecode code, CtClass[] parameters, Javac drv) throws CannotCompileException {
            code.addAload(0);
            code.addLdc2w(this.value);
            code.addPutfield(Bytecode.THIS, name, Descriptor.m145of(type));
            return 3;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.viaversion.viaversion.libs.javassist.CtField.Initializer
        public int compileIfStatic(CtClass type, String name, Bytecode code, Javac drv) throws CannotCompileException {
            code.addLdc2w(this.value);
            code.addPutstatic(Bytecode.THIS, name, Descriptor.m145of(type));
            return 2;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.viaversion.viaversion.libs.javassist.CtField.Initializer
        public int getConstantValue(ConstPool cp, CtClass type) {
            if (type == CtClass.doubleType) {
                return cp.addDoubleInfo(this.value);
            }
            return 0;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/CtField$StringInitializer.class */
    static class StringInitializer extends Initializer {
        String value;

        StringInitializer(String v) {
            this.value = v;
        }

        @Override // com.viaversion.viaversion.libs.javassist.CtField.Initializer
        public int compile(CtClass type, String name, Bytecode code, CtClass[] parameters, Javac drv) throws CannotCompileException {
            code.addAload(0);
            code.addLdc(this.value);
            code.addPutfield(Bytecode.THIS, name, Descriptor.m145of(type));
            return 2;
        }

        @Override // com.viaversion.viaversion.libs.javassist.CtField.Initializer
        public int compileIfStatic(CtClass type, String name, Bytecode code, Javac drv) throws CannotCompileException {
            code.addLdc(this.value);
            code.addPutstatic(Bytecode.THIS, name, Descriptor.m145of(type));
            return 1;
        }

        @Override // com.viaversion.viaversion.libs.javassist.CtField.Initializer
        public int getConstantValue(ConstPool cp, CtClass type) {
            if (type.getName().equals(CtField.javaLangString)) {
                return cp.addStringInfo(this.value);
            }
            return 0;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/CtField$ArrayInitializer.class */
    static class ArrayInitializer extends Initializer {
        CtClass type;
        int size;

        ArrayInitializer(CtClass t, int s) {
            this.type = t;
            this.size = s;
        }

        private void addNewarray(Bytecode code) {
            if (this.type.isPrimitive()) {
                code.addNewarray(((CtPrimitiveType) this.type).getArrayType(), this.size);
            } else {
                code.addAnewarray(this.type, this.size);
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.viaversion.viaversion.libs.javassist.CtField.Initializer
        public int compile(CtClass type, String name, Bytecode code, CtClass[] parameters, Javac drv) throws CannotCompileException {
            code.addAload(0);
            addNewarray(code);
            code.addPutfield(Bytecode.THIS, name, Descriptor.m145of(type));
            return 2;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.viaversion.viaversion.libs.javassist.CtField.Initializer
        public int compileIfStatic(CtClass type, String name, Bytecode code, Javac drv) throws CannotCompileException {
            addNewarray(code);
            code.addPutstatic(Bytecode.THIS, name, Descriptor.m145of(type));
            return 1;
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/CtField$MultiArrayInitializer.class */
    static class MultiArrayInitializer extends Initializer {
        CtClass type;
        int[] dim;

        MultiArrayInitializer(CtClass t, int[] d) {
            this.type = t;
            this.dim = d;
        }

        @Override // com.viaversion.viaversion.libs.javassist.CtField.Initializer
        public void check(String desc) throws CannotCompileException {
            if (desc.charAt(0) != '[') {
                throw new CannotCompileException("type mismatch");
            }
        }

        @Override // com.viaversion.viaversion.libs.javassist.CtField.Initializer
        public int compile(CtClass type, String name, Bytecode code, CtClass[] parameters, Javac drv) throws CannotCompileException {
            code.addAload(0);
            int s = code.addMultiNewarray(type, this.dim);
            code.addPutfield(Bytecode.THIS, name, Descriptor.m145of(type));
            return s + 1;
        }

        @Override // com.viaversion.viaversion.libs.javassist.CtField.Initializer
        public int compileIfStatic(CtClass type, String name, Bytecode code, Javac drv) throws CannotCompileException {
            int s = code.addMultiNewarray(type, this.dim);
            code.addPutstatic(Bytecode.THIS, name, Descriptor.m145of(type));
            return s;
        }
    }
}
