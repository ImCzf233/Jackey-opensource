package com.viaversion.viaversion.libs.javassist.bytecode;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/bytecode/LongVector.class */
public final class LongVector {
    static final int ASIZE = 128;
    static final int ABITS = 7;
    static final int VSIZE = 8;
    private ConstInfo[][] objects;
    private int elements;

    /* JADX WARN: Type inference failed for: r1v1, types: [com.viaversion.viaversion.libs.javassist.bytecode.ConstInfo[], com.viaversion.viaversion.libs.javassist.bytecode.ConstInfo[][]] */
    public LongVector() {
        this.objects = new ConstInfo[8];
        this.elements = 0;
    }

    /* JADX WARN: Type inference failed for: r1v4, types: [com.viaversion.viaversion.libs.javassist.bytecode.ConstInfo[], com.viaversion.viaversion.libs.javassist.bytecode.ConstInfo[][]] */
    public LongVector(int initialSize) {
        int vsize = ((initialSize >> 7) & (-8)) + 8;
        this.objects = new ConstInfo[vsize];
        this.elements = 0;
    }

    public int size() {
        return this.elements;
    }

    public int capacity() {
        return this.objects.length * 128;
    }

    public ConstInfo elementAt(int i) {
        if (i < 0 || this.elements <= i) {
            return null;
        }
        return this.objects[i >> 7][i & 127];
    }

    /* JADX WARN: Type inference failed for: r0v21, types: [com.viaversion.viaversion.libs.javassist.bytecode.ConstInfo[], com.viaversion.viaversion.libs.javassist.bytecode.ConstInfo[][], java.lang.Object] */
    public void addElement(ConstInfo value) {
        int nth = this.elements >> 7;
        int offset = this.elements & 127;
        int len = this.objects.length;
        if (nth >= len) {
            ?? r0 = new ConstInfo[len + 8];
            System.arraycopy(this.objects, 0, r0, 0, len);
            this.objects = r0;
        }
        if (this.objects[nth] == null) {
            this.objects[nth] = new ConstInfo[128];
        }
        this.objects[nth][offset] = value;
        this.elements++;
    }
}
