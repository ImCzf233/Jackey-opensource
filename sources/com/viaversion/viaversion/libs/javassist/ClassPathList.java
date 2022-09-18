package com.viaversion.viaversion.libs.javassist;

/* compiled from: ClassPoolTail.java */
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/ClassPathList.class */
final class ClassPathList {
    ClassPathList next;
    ClassPath path;

    public ClassPathList(ClassPath p, ClassPathList n) {
        this.next = n;
        this.path = p;
    }
}
