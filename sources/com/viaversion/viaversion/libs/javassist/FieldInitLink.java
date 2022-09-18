package com.viaversion.viaversion.libs.javassist;

import com.viaversion.viaversion.libs.javassist.CtField;

/* compiled from: CtClassType.java */
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/FieldInitLink.class */
public class FieldInitLink {
    FieldInitLink next = null;
    CtField field;
    CtField.Initializer init;

    public FieldInitLink(CtField f, CtField.Initializer i) {
        this.field = f;
        this.init = i;
    }
}
