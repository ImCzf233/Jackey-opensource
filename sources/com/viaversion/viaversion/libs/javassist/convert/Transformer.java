package com.viaversion.viaversion.libs.javassist.convert;

import com.viaversion.viaversion.libs.javassist.CannotCompileException;
import com.viaversion.viaversion.libs.javassist.CtClass;
import com.viaversion.viaversion.libs.javassist.bytecode.BadBytecode;
import com.viaversion.viaversion.libs.javassist.bytecode.CodeAttribute;
import com.viaversion.viaversion.libs.javassist.bytecode.CodeIterator;
import com.viaversion.viaversion.libs.javassist.bytecode.ConstPool;
import com.viaversion.viaversion.libs.javassist.bytecode.MethodInfo;
import com.viaversion.viaversion.libs.javassist.bytecode.Opcode;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/convert/Transformer.class */
public abstract class Transformer implements Opcode {
    private Transformer next;

    public abstract int transform(CtClass ctClass, int i, CodeIterator codeIterator, ConstPool constPool) throws CannotCompileException, BadBytecode;

    public Transformer(Transformer t) {
        this.next = t;
    }

    public Transformer getNext() {
        return this.next;
    }

    public void initialize(ConstPool cp, CodeAttribute attr) {
    }

    public void initialize(ConstPool cp, CtClass clazz, MethodInfo minfo) throws CannotCompileException {
        initialize(cp, minfo.getCodeAttribute());
    }

    public void clean() {
    }

    public int extraLocals() {
        return 0;
    }

    public int extraStack() {
        return 0;
    }
}
