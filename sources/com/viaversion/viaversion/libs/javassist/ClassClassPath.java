package com.viaversion.viaversion.libs.javassist;

import java.io.InputStream;
import java.net.URL;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/ClassClassPath.class */
public class ClassClassPath implements ClassPath {
    private Class<?> thisClass;

    public ClassClassPath(Class<?> c) {
        this.thisClass = c;
    }

    public ClassClassPath() {
        this(Object.class);
    }

    @Override // com.viaversion.viaversion.libs.javassist.ClassPath
    public InputStream openClassfile(String classname) throws NotFoundException {
        String filename = '/' + classname.replace('.', '/') + ".class";
        return this.thisClass.getResourceAsStream(filename);
    }

    @Override // com.viaversion.viaversion.libs.javassist.ClassPath
    public URL find(String classname) {
        String filename = '/' + classname.replace('.', '/') + ".class";
        return this.thisClass.getResource(filename);
    }

    public String toString() {
        return this.thisClass.getName() + ".class";
    }
}
