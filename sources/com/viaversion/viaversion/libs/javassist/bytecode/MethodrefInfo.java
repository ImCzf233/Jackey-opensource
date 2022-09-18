package com.viaversion.viaversion.libs.javassist.bytecode;

import java.io.DataInputStream;
import java.io.IOException;

/* compiled from: ConstPool.java */
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/bytecode/MethodrefInfo.class */
public class MethodrefInfo extends MemberrefInfo {
    static final int tag = 10;

    public MethodrefInfo(int cindex, int ntindex, int thisIndex) {
        super(cindex, ntindex, thisIndex);
    }

    public MethodrefInfo(DataInputStream in, int thisIndex) throws IOException {
        super(in, thisIndex);
    }

    @Override // com.viaversion.viaversion.libs.javassist.bytecode.ConstInfo
    public int getTag() {
        return 10;
    }

    @Override // com.viaversion.viaversion.libs.javassist.bytecode.MemberrefInfo
    public String getTagName() {
        return "Method";
    }

    @Override // com.viaversion.viaversion.libs.javassist.bytecode.MemberrefInfo
    protected int copy2(ConstPool dest, int cindex, int ntindex) {
        return dest.addMethodrefInfo(cindex, ntindex);
    }
}
