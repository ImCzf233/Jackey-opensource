package com.viaversion.viaversion.libs.javassist;

import com.viaversion.viaversion.libs.javassist.CtField;
import com.viaversion.viaversion.libs.javassist.bytecode.ClassFile;
import com.viaversion.viaversion.libs.javassist.bytecode.Descriptor;
import com.viaversion.viaversion.libs.javassist.compiler.AccessorMaker;
import com.viaversion.viaversion.libs.javassist.expr.ExprEditor;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.invoke.MethodHandles;
import java.net.URL;
import java.security.ProtectionDomain;
import java.util.Collection;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/CtClass.class */
public abstract class CtClass {
    protected String qualifiedName;
    public static final String version = "3.28.0-GA";
    static final String javaLangObject = "java.lang.Object";
    public static String debugDump = null;
    static CtClass[] primitiveTypes = new CtClass[9];
    public static CtClass booleanType = new CtPrimitiveType("boolean", 'Z', "java.lang.Boolean", "booleanValue", "()Z", 172, 4, 1);
    public static CtClass charType = new CtPrimitiveType("char", 'C', "java.lang.Character", "charValue", "()C", 172, 5, 1);
    public static CtClass byteType = new CtPrimitiveType("byte", 'B', "java.lang.Byte", "byteValue", "()B", 172, 8, 1);
    public static CtClass shortType = new CtPrimitiveType("short", 'S', "java.lang.Short", "shortValue", "()S", 172, 9, 1);
    public static CtClass intType = new CtPrimitiveType("int", 'I', "java.lang.Integer", "intValue", "()I", 172, 10, 1);
    public static CtClass longType = new CtPrimitiveType("long", 'J', "java.lang.Long", "longValue", "()J", 173, 11, 2);
    public static CtClass floatType = new CtPrimitiveType("float", 'F', "java.lang.Float", "floatValue", "()F", 174, 6, 1);
    public static CtClass doubleType = new CtPrimitiveType("double", 'D', "java.lang.Double", "doubleValue", "()D", 175, 7, 2);
    public static CtClass voidType = new CtPrimitiveType("void", 'V', "java.lang.Void", null, null, 177, 0, 0);

    static {
        primitiveTypes[0] = booleanType;
        primitiveTypes[1] = charType;
        primitiveTypes[2] = byteType;
        primitiveTypes[3] = shortType;
        primitiveTypes[4] = intType;
        primitiveTypes[5] = longType;
        primitiveTypes[6] = floatType;
        primitiveTypes[7] = doubleType;
        primitiveTypes[8] = voidType;
    }

    public static void main(String[] args) {
        System.out.println("Javassist version 3.28.0-GA");
        System.out.println("Copyright (C) 1999-2021 Shigeru Chiba. All Rights Reserved.");
    }

    public CtClass(String name) {
        this.qualifiedName = name;
    }

    public String toString() {
        StringBuffer buf = new StringBuffer(getClass().getName());
        buf.append("@");
        buf.append(Integer.toHexString(hashCode()));
        buf.append("[");
        extendToString(buf);
        buf.append("]");
        return buf.toString();
    }

    protected void extendToString(StringBuffer buffer) {
        buffer.append(getName());
    }

    public ClassPool getClassPool() {
        return null;
    }

    public ClassFile getClassFile() {
        checkModify();
        return getClassFile2();
    }

    public ClassFile getClassFile2() {
        return null;
    }

    public AccessorMaker getAccessorMaker() {
        return null;
    }

    public URL getURL() throws NotFoundException {
        throw new NotFoundException(getName());
    }

    public boolean isModified() {
        return false;
    }

    public boolean isFrozen() {
        return true;
    }

    public void freeze() {
    }

    public void checkModify() throws RuntimeException {
        if (isFrozen()) {
            throw new RuntimeException(getName() + " class is frozen");
        }
    }

    public void defrost() {
        throw new RuntimeException("cannot defrost " + getName());
    }

    public boolean isPrimitive() {
        return false;
    }

    public boolean isArray() {
        return false;
    }

    public boolean isKotlin() {
        return hasAnnotation("kotlin.Metadata");
    }

    public CtClass getComponentType() throws NotFoundException {
        return null;
    }

    public boolean subtypeOf(CtClass clazz) throws NotFoundException {
        return this == clazz || getName().equals(clazz.getName());
    }

    public String getName() {
        return this.qualifiedName;
    }

    public final String getSimpleName() {
        String qname = this.qualifiedName;
        int index = qname.lastIndexOf(46);
        if (index < 0) {
            return qname;
        }
        return qname.substring(index + 1);
    }

    public final String getPackageName() {
        String qname = this.qualifiedName;
        int index = qname.lastIndexOf(46);
        if (index < 0) {
            return null;
        }
        return qname.substring(0, index);
    }

    public void setName(String name) {
        checkModify();
        if (name != null) {
            this.qualifiedName = name;
        }
    }

    public String getGenericSignature() {
        return null;
    }

    public void setGenericSignature(String sig) {
        checkModify();
    }

    public void replaceClassName(String oldName, String newName) {
        checkModify();
    }

    public void replaceClassName(ClassMap map) {
        checkModify();
    }

    public synchronized Collection<String> getRefClasses() {
        ClassFile cf = getClassFile2();
        if (cf != null) {
            ClassMap cm = new ClassMap() { // from class: com.viaversion.viaversion.libs.javassist.CtClass.1
                private static final long serialVersionUID = 1;

                @Override // com.viaversion.viaversion.libs.javassist.ClassMap
                public String put(String oldname, String newname) {
                    return put0(oldname, newname);
                }

                @Override // com.viaversion.viaversion.libs.javassist.ClassMap, java.util.HashMap, java.util.AbstractMap, java.util.Map
                public String get(Object jvmClassName) {
                    String n = toJavaName((String) jvmClassName);
                    put0(n, n);
                    return null;
                }

                @Override // com.viaversion.viaversion.libs.javassist.ClassMap
                public void fix(String name) {
                }
            };
            cf.getRefClasses(cm);
            return cm.values();
        }
        return null;
    }

    public boolean isInterface() {
        return false;
    }

    public boolean isAnnotation() {
        return false;
    }

    public boolean isEnum() {
        return false;
    }

    public int getModifiers() {
        return 0;
    }

    public boolean hasAnnotation(Class<?> annotationType) {
        return hasAnnotation(annotationType.getName());
    }

    public boolean hasAnnotation(String annotationTypeName) {
        return false;
    }

    public Object getAnnotation(Class<?> clz) throws ClassNotFoundException {
        return null;
    }

    public Object[] getAnnotations() throws ClassNotFoundException {
        return new Object[0];
    }

    public Object[] getAvailableAnnotations() {
        return new Object[0];
    }

    public CtClass[] getDeclaredClasses() throws NotFoundException {
        return getNestedClasses();
    }

    public CtClass[] getNestedClasses() throws NotFoundException {
        return new CtClass[0];
    }

    public void setModifiers(int mod) {
        checkModify();
    }

    public boolean subclassOf(CtClass superclass) {
        return false;
    }

    public CtClass getSuperclass() throws NotFoundException {
        return null;
    }

    public void setSuperclass(CtClass clazz) throws CannotCompileException {
        checkModify();
    }

    public CtClass[] getInterfaces() throws NotFoundException {
        return new CtClass[0];
    }

    public void setInterfaces(CtClass[] list) {
        checkModify();
    }

    public void addInterface(CtClass anInterface) {
        checkModify();
    }

    public CtClass getDeclaringClass() throws NotFoundException {
        return null;
    }

    @Deprecated
    public final CtMethod getEnclosingMethod() throws NotFoundException {
        CtBehavior b = getEnclosingBehavior();
        if (b == null) {
            return null;
        }
        if (b instanceof CtMethod) {
            return (CtMethod) b;
        }
        throw new NotFoundException(b.getLongName() + " is enclosing " + getName());
    }

    public CtBehavior getEnclosingBehavior() throws NotFoundException {
        return null;
    }

    public CtClass makeNestedClass(String name, boolean isStatic) {
        throw new RuntimeException(getName() + " is not a class");
    }

    public CtField[] getFields() {
        return new CtField[0];
    }

    public CtField getField(String name) throws NotFoundException {
        return getField(name, null);
    }

    public CtField getField(String name, String desc) throws NotFoundException {
        throw new NotFoundException(name);
    }

    public CtField getField2(String name, String desc) {
        return null;
    }

    public CtField[] getDeclaredFields() {
        return new CtField[0];
    }

    public CtField getDeclaredField(String name) throws NotFoundException {
        throw new NotFoundException(name);
    }

    public CtField getDeclaredField(String name, String desc) throws NotFoundException {
        throw new NotFoundException(name);
    }

    public CtBehavior[] getDeclaredBehaviors() {
        return new CtBehavior[0];
    }

    public CtConstructor[] getConstructors() {
        return new CtConstructor[0];
    }

    public CtConstructor getConstructor(String desc) throws NotFoundException {
        throw new NotFoundException("no such constructor");
    }

    public CtConstructor[] getDeclaredConstructors() {
        return new CtConstructor[0];
    }

    public CtConstructor getDeclaredConstructor(CtClass[] params) throws NotFoundException {
        String desc = Descriptor.ofConstructor(params);
        return getConstructor(desc);
    }

    public CtConstructor getClassInitializer() {
        return null;
    }

    public CtMethod[] getMethods() {
        return new CtMethod[0];
    }

    public CtMethod getMethod(String name, String desc) throws NotFoundException {
        throw new NotFoundException(name);
    }

    public CtMethod[] getDeclaredMethods() {
        return new CtMethod[0];
    }

    public CtMethod getDeclaredMethod(String name, CtClass[] params) throws NotFoundException {
        throw new NotFoundException(name);
    }

    public CtMethod[] getDeclaredMethods(String name) throws NotFoundException {
        throw new NotFoundException(name);
    }

    public CtMethod getDeclaredMethod(String name) throws NotFoundException {
        throw new NotFoundException(name);
    }

    public CtConstructor makeClassInitializer() throws CannotCompileException {
        throw new CannotCompileException("not a class");
    }

    public void addConstructor(CtConstructor c) throws CannotCompileException {
        checkModify();
    }

    public void removeConstructor(CtConstructor c) throws NotFoundException {
        checkModify();
    }

    public void addMethod(CtMethod m) throws CannotCompileException {
        checkModify();
    }

    public void removeMethod(CtMethod m) throws NotFoundException {
        checkModify();
    }

    public void addField(CtField f) throws CannotCompileException {
        addField(f, (CtField.Initializer) null);
    }

    public void addField(CtField f, String init) throws CannotCompileException {
        checkModify();
    }

    public void addField(CtField f, CtField.Initializer init) throws CannotCompileException {
        checkModify();
    }

    public void removeField(CtField f) throws NotFoundException {
        checkModify();
    }

    public byte[] getAttribute(String name) {
        return null;
    }

    public void setAttribute(String name, byte[] data) {
        checkModify();
    }

    public void instrument(CodeConverter converter) throws CannotCompileException {
        checkModify();
    }

    public void instrument(ExprEditor editor) throws CannotCompileException {
        checkModify();
    }

    public Class<?> toClass() throws CannotCompileException {
        return getClassPool().toClass(this);
    }

    public Class<?> toClass(Class<?> neighbor) throws CannotCompileException {
        return getClassPool().toClass(this, neighbor);
    }

    public Class<?> toClass(MethodHandles.Lookup lookup) throws CannotCompileException {
        return getClassPool().toClass(this, lookup);
    }

    public Class<?> toClass(ClassLoader loader, ProtectionDomain domain) throws CannotCompileException {
        ClassPool cp = getClassPool();
        if (loader == null) {
            loader = cp.getClassLoader();
        }
        return cp.toClass(this, null, loader, domain);
    }

    @Deprecated
    public final Class<?> toClass(ClassLoader loader) throws CannotCompileException {
        return getClassPool().toClass(this, null, loader, null);
    }

    public void detach() {
        ClassPool cp = getClassPool();
        CtClass obj = cp.removeCached(getName());
        if (obj != this) {
            cp.cacheCtClass(getName(), obj, false);
        }
    }

    public boolean stopPruning(boolean stop) {
        return true;
    }

    public void prune() {
    }

    public void incGetCounter() {
    }

    public void rebuildClassFile() {
    }

    public byte[] toBytecode() throws IOException, CannotCompileException {
        ByteArrayOutputStream barray = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(barray);
        try {
            toBytecode(out);
            return barray.toByteArray();
        } finally {
            out.close();
        }
    }

    public void writeFile() throws NotFoundException, IOException, CannotCompileException {
        writeFile(".");
    }

    public void writeFile(String directoryName) throws CannotCompileException, IOException {
        DataOutputStream out = makeFileOutput(directoryName);
        try {
            toBytecode(out);
        } finally {
            out.close();
        }
    }

    public DataOutputStream makeFileOutput(String directoryName) {
        String classname = getName();
        String filename = directoryName + File.separatorChar + classname.replace('.', File.separatorChar) + ".class";
        int pos = filename.lastIndexOf(File.separatorChar);
        if (pos > 0) {
            String dir = filename.substring(0, pos);
            if (!dir.equals(".")) {
                new File(dir).mkdirs();
            }
        }
        return new DataOutputStream(new BufferedOutputStream(new DelayedFileOutputStream(filename)));
    }

    public void debugWriteFile() {
        debugWriteFile(".");
    }

    public void debugWriteFile(String directoryName) {
        try {
            boolean p = stopPruning(true);
            writeFile(directoryName);
            defrost();
            stopPruning(p);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/CtClass$DelayedFileOutputStream.class */
    public static class DelayedFileOutputStream extends OutputStream {
        private FileOutputStream file = null;
        private String filename;

        DelayedFileOutputStream(String name) {
            this.filename = name;
        }

        private void init() throws IOException {
            if (this.file == null) {
                this.file = new FileOutputStream(this.filename);
            }
        }

        @Override // java.io.OutputStream
        public void write(int b) throws IOException {
            init();
            this.file.write(b);
        }

        @Override // java.io.OutputStream
        public void write(byte[] b) throws IOException {
            init();
            this.file.write(b);
        }

        @Override // java.io.OutputStream
        public void write(byte[] b, int off, int len) throws IOException {
            init();
            this.file.write(b, off, len);
        }

        @Override // java.io.OutputStream, java.io.Flushable
        public void flush() throws IOException {
            init();
            this.file.flush();
        }

        @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            init();
            this.file.close();
        }
    }

    public void toBytecode(DataOutputStream out) throws CannotCompileException, IOException {
        throw new CannotCompileException("not a class");
    }

    public String makeUniqueName(String prefix) {
        throw new RuntimeException("not available in " + getName());
    }

    public void compress() {
    }
}
