package com.viaversion.viaversion.libs.javassist.tools.reflect;

import com.viaversion.viaversion.libs.javassist.CannotCompileException;
import com.viaversion.viaversion.libs.javassist.ClassPool;
import com.viaversion.viaversion.libs.javassist.NotFoundException;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/tools/reflect/Loader.class */
public class Loader extends com.viaversion.viaversion.libs.javassist.Loader {
    protected Reflection reflection = new Reflection();

    public static void main(String[] args) throws Throwable {
        Loader cl = new Loader();
        cl.run(args);
    }

    public Loader() throws CannotCompileException, NotFoundException {
        delegateLoadingOf("com.viaversion.viaversion.libs.javassist.tools.reflect.Loader");
        ClassPool pool = ClassPool.getDefault();
        addTranslator(pool, this.reflection);
    }

    public boolean makeReflective(String clazz, String metaobject, String metaclass) throws CannotCompileException, NotFoundException {
        return this.reflection.makeReflective(clazz, metaobject, metaclass);
    }
}
