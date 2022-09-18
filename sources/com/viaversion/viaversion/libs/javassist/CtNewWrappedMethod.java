package com.viaversion.viaversion.libs.javassist;

import com.viaversion.viaversion.libs.javassist.CtMember;
import com.viaversion.viaversion.libs.javassist.CtMethod;
import com.viaversion.viaversion.libs.javassist.bytecode.AccessFlag;
import com.viaversion.viaversion.libs.javassist.bytecode.BadBytecode;
import com.viaversion.viaversion.libs.javassist.bytecode.Bytecode;
import com.viaversion.viaversion.libs.javassist.bytecode.ClassFile;
import com.viaversion.viaversion.libs.javassist.bytecode.MethodInfo;
import com.viaversion.viaversion.libs.javassist.bytecode.SyntheticAttribute;
import com.viaversion.viaversion.libs.javassist.compiler.JvstCodeGen;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/CtNewWrappedMethod.class */
public class CtNewWrappedMethod {
    private static final String addedWrappedMethod = "_added_m$";

    public static CtMethod wrapped(CtClass returnType, String mname, CtClass[] parameterTypes, CtClass[] exceptionTypes, CtMethod body, CtMethod.ConstParameter constParam, CtClass declaring) throws CannotCompileException {
        CtMethod mt = new CtMethod(returnType, mname, parameterTypes, declaring);
        mt.setModifiers(body.getModifiers());
        try {
            mt.setExceptionTypes(exceptionTypes);
            Bytecode code = makeBody(declaring, declaring.getClassFile2(), body, parameterTypes, returnType, constParam);
            MethodInfo minfo = mt.getMethodInfo2();
            minfo.setCodeAttribute(code.toCodeAttribute());
            return mt;
        } catch (NotFoundException e) {
            throw new CannotCompileException(e);
        }
    }

    public static Bytecode makeBody(CtClass clazz, ClassFile classfile, CtMethod wrappedBody, CtClass[] parameters, CtClass returnType, CtMethod.ConstParameter cparam) throws CannotCompileException {
        boolean isStatic = Modifier.isStatic(wrappedBody.getModifiers());
        Bytecode code = new Bytecode(classfile.getConstPool(), 0, 0);
        int stacksize = makeBody0(clazz, classfile, wrappedBody, isStatic, parameters, returnType, cparam, code);
        code.setMaxStack(stacksize);
        code.setMaxLocals(isStatic, parameters, 0);
        return code;
    }

    public static int makeBody0(CtClass clazz, ClassFile classfile, CtMethod wrappedBody, boolean isStatic, CtClass[] parameters, CtClass returnType, CtMethod.ConstParameter cparam, Bytecode code) throws CannotCompileException {
        String desc;
        int stacksize2;
        if (!(clazz instanceof CtClassType)) {
            throw new CannotCompileException("bad declaring class" + clazz.getName());
        }
        if (!isStatic) {
            code.addAload(0);
        }
        int stacksize = compileParameterList(code, parameters, isStatic ? 0 : 1);
        if (cparam == null) {
            stacksize2 = 0;
            desc = CtMethod.ConstParameter.defaultDescriptor();
        } else {
            stacksize2 = cparam.compile(code);
            desc = cparam.descriptor();
        }
        checkSignature(wrappedBody, desc);
        try {
            String bodyname = addBodyMethod((CtClassType) clazz, classfile, wrappedBody);
            if (isStatic) {
                code.addInvokestatic(Bytecode.THIS, bodyname, desc);
            } else {
                code.addInvokespecial(Bytecode.THIS, bodyname, desc);
            }
            compileReturn(code, returnType);
            if (stacksize < stacksize2 + 2) {
                stacksize = stacksize2 + 2;
            }
            return stacksize;
        } catch (BadBytecode e) {
            throw new CannotCompileException(e);
        }
    }

    private static void checkSignature(CtMethod wrappedBody, String descriptor) throws CannotCompileException {
        if (!descriptor.equals(wrappedBody.getMethodInfo2().getDescriptor())) {
            throw new CannotCompileException("wrapped method with a bad signature: " + wrappedBody.getDeclaringClass().getName() + '.' + wrappedBody.getName());
        }
    }

    private static String addBodyMethod(CtClassType clazz, ClassFile classfile, CtMethod src) throws BadBytecode, CannotCompileException {
        Map<CtMethod, String> bodies = clazz.getHiddenMethods();
        String bodyname = bodies.get(src);
        if (bodyname == null) {
            do {
                bodyname = addedWrappedMethod + clazz.getUniqueNumber();
            } while (classfile.getMethod(bodyname) != null);
            ClassMap map = new ClassMap();
            map.put(src.getDeclaringClass().getName(), clazz.getName());
            MethodInfo body = new MethodInfo(classfile.getConstPool(), bodyname, src.getMethodInfo2(), map);
            int acc = body.getAccessFlags();
            body.setAccessFlags(AccessFlag.setPrivate(acc));
            body.addAttribute(new SyntheticAttribute(classfile.getConstPool()));
            classfile.addMethod(body);
            bodies.put(src, bodyname);
            CtMember.Cache cache = clazz.hasMemberCache();
            if (cache != null) {
                cache.addMethod(new CtMethod(body, clazz));
            }
        }
        return bodyname;
    }

    public static int compileParameterList(Bytecode code, CtClass[] params, int regno) {
        return JvstCodeGen.compileParameterList(code, params, regno);
    }

    private static void compileReturn(Bytecode code, CtClass type) {
        if (type.isPrimitive()) {
            CtPrimitiveType pt = (CtPrimitiveType) type;
            if (pt != CtClass.voidType) {
                String wrapper = pt.getWrapperName();
                code.addCheckcast(wrapper);
                code.addInvokevirtual(wrapper, pt.getGetMethodName(), pt.getGetMethodDescriptor());
            }
            code.addOpcode(pt.getReturnOp());
            return;
        }
        code.addCheckcast(type);
        code.addOpcode(176);
    }
}
