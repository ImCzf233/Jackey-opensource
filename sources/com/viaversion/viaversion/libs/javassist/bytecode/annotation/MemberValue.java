package com.viaversion.viaversion.libs.javassist.bytecode.annotation;

import com.viaversion.viaversion.libs.javassist.ClassPool;
import com.viaversion.viaversion.libs.javassist.bytecode.ConstPool;
import com.viaversion.viaversion.libs.javassist.bytecode.Descriptor;
import java.io.IOException;
import java.lang.reflect.Method;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/bytecode/annotation/MemberValue.class */
public abstract class MemberValue {

    /* renamed from: cp */
    ConstPool f170cp;
    char tag;

    public abstract Object getValue(ClassLoader classLoader, ClassPool classPool, Method method) throws ClassNotFoundException;

    public abstract Class<?> getType(ClassLoader classLoader) throws ClassNotFoundException;

    public abstract void accept(MemberValueVisitor memberValueVisitor);

    public abstract void write(AnnotationsWriter annotationsWriter) throws IOException;

    public MemberValue(char tag, ConstPool cp) {
        this.f170cp = cp;
        this.tag = tag;
    }

    public static Class<?> loadClass(ClassLoader cl, String classname) throws ClassNotFoundException, NoSuchClassError {
        try {
            return Class.forName(convertFromArray(classname), true, cl);
        } catch (LinkageError e) {
            throw new NoSuchClassError(classname, e);
        }
    }

    private static String convertFromArray(String classname) {
        int index = classname.indexOf("[]");
        if (index != -1) {
            String rawType = classname.substring(0, index);
            StringBuffer sb = new StringBuffer(Descriptor.m144of(rawType));
            while (index != -1) {
                sb.insert(0, "[");
                index = classname.indexOf("[]", index + 1);
            }
            return sb.toString().replace('/', '.');
        }
        return classname;
    }
}
