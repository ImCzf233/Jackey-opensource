package com.viaversion.viaversion.libs.javassist.bytecode;

import java.io.DataInputStream;
import java.io.IOException;

/* compiled from: ConstPool.java */
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/bytecode/FieldrefInfo.class */
public class FieldrefInfo extends MemberrefInfo {
    static final int tag = 9;

    public FieldrefInfo(int cindex, int ntindex, int thisIndex) {
        super(cindex, ntindex, thisIndex);
    }

    public FieldrefInfo(DataInputStream in, int thisIndex) throws IOException {
        super(in, thisIndex);
    }

    @Override // com.viaversion.viaversion.libs.javassist.bytecode.ConstInfo
    public int getTag() {
        return 9;
    }

    @Override // com.viaversion.viaversion.libs.javassist.bytecode.MemberrefInfo
    public String getTagName() {
        return "Field";
    }

    @Override // com.viaversion.viaversion.libs.javassist.bytecode.MemberrefInfo
    protected int copy2(ConstPool dest, int cindex, int ntindex) {
        return dest.addFieldrefInfo(cindex, ntindex);
    }
}
