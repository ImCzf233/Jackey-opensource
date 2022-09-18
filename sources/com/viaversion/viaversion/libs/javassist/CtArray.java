package com.viaversion.viaversion.libs.javassist;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/CtArray.class */
public final class CtArray extends CtClass {
    protected ClassPool pool;
    private CtClass[] interfaces = null;

    public CtArray(String name, ClassPool cp) {
        super(name);
        this.pool = cp;
    }

    @Override // com.viaversion.viaversion.libs.javassist.CtClass
    public ClassPool getClassPool() {
        return this.pool;
    }

    @Override // com.viaversion.viaversion.libs.javassist.CtClass
    public boolean isArray() {
        return true;
    }

    @Override // com.viaversion.viaversion.libs.javassist.CtClass
    public int getModifiers() {
        int mod = 16;
        try {
            mod = 16 | (getComponentType().getModifiers() & 7);
        } catch (NotFoundException e) {
        }
        return mod;
    }

    @Override // com.viaversion.viaversion.libs.javassist.CtClass
    public CtClass[] getInterfaces() throws NotFoundException {
        if (this.interfaces == null) {
            Class<?>[] intfs = Object[].class.getInterfaces();
            this.interfaces = new CtClass[intfs.length];
            for (int i = 0; i < intfs.length; i++) {
                this.interfaces[i] = this.pool.get(intfs[i].getName());
            }
        }
        return this.interfaces;
    }

    @Override // com.viaversion.viaversion.libs.javassist.CtClass
    public boolean subtypeOf(CtClass clazz) throws NotFoundException {
        if (super.subtypeOf(clazz)) {
            return true;
        }
        String cname = clazz.getName();
        if (cname.equals("java.lang.Object")) {
            return true;
        }
        CtClass[] intfs = getInterfaces();
        for (CtClass ctClass : intfs) {
            if (ctClass.subtypeOf(clazz)) {
                return true;
            }
        }
        return clazz.isArray() && getComponentType().subtypeOf(clazz.getComponentType());
    }

    @Override // com.viaversion.viaversion.libs.javassist.CtClass
    public CtClass getComponentType() throws NotFoundException {
        String name = getName();
        return this.pool.get(name.substring(0, name.length() - 2));
    }

    @Override // com.viaversion.viaversion.libs.javassist.CtClass
    public CtClass getSuperclass() throws NotFoundException {
        return this.pool.get("java.lang.Object");
    }

    @Override // com.viaversion.viaversion.libs.javassist.CtClass
    public CtMethod[] getMethods() {
        try {
            return getSuperclass().getMethods();
        } catch (NotFoundException e) {
            return super.getMethods();
        }
    }

    @Override // com.viaversion.viaversion.libs.javassist.CtClass
    public CtMethod getMethod(String name, String desc) throws NotFoundException {
        return getSuperclass().getMethod(name, desc);
    }

    @Override // com.viaversion.viaversion.libs.javassist.CtClass
    public CtConstructor[] getConstructors() {
        try {
            return getSuperclass().getConstructors();
        } catch (NotFoundException e) {
            return super.getConstructors();
        }
    }
}
