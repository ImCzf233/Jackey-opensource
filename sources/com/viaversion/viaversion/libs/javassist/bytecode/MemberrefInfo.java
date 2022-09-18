package com.viaversion.viaversion.libs.javassist.bytecode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/* compiled from: ConstPool.java */
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/bytecode/MemberrefInfo.class */
public abstract class MemberrefInfo extends ConstInfo {
    int classIndex;
    int nameAndTypeIndex;

    protected abstract int copy2(ConstPool constPool, int i, int i2);

    public abstract String getTagName();

    public MemberrefInfo(int cindex, int ntindex, int thisIndex) {
        super(thisIndex);
        this.classIndex = cindex;
        this.nameAndTypeIndex = ntindex;
    }

    public MemberrefInfo(DataInputStream in, int thisIndex) throws IOException {
        super(thisIndex);
        this.classIndex = in.readUnsignedShort();
        this.nameAndTypeIndex = in.readUnsignedShort();
    }

    public int hashCode() {
        return (this.classIndex << 16) ^ this.nameAndTypeIndex;
    }

    public boolean equals(Object obj) {
        if (obj instanceof MemberrefInfo) {
            MemberrefInfo mri = (MemberrefInfo) obj;
            return mri.classIndex == this.classIndex && mri.nameAndTypeIndex == this.nameAndTypeIndex && mri.getClass() == getClass();
        }
        return false;
    }

    @Override // com.viaversion.viaversion.libs.javassist.bytecode.ConstInfo
    public int copy(ConstPool src, ConstPool dest, Map<String, String> map) {
        int classIndex2 = src.getItem(this.classIndex).copy(src, dest, map);
        int ntIndex2 = src.getItem(this.nameAndTypeIndex).copy(src, dest, map);
        return copy2(dest, classIndex2, ntIndex2);
    }

    @Override // com.viaversion.viaversion.libs.javassist.bytecode.ConstInfo
    public void write(DataOutputStream out) throws IOException {
        out.writeByte(getTag());
        out.writeShort(this.classIndex);
        out.writeShort(this.nameAndTypeIndex);
    }

    @Override // com.viaversion.viaversion.libs.javassist.bytecode.ConstInfo
    public void print(PrintWriter out) {
        out.print(getTagName() + " #");
        out.print(this.classIndex);
        out.print(", name&type #");
        out.println(this.nameAndTypeIndex);
    }
}
