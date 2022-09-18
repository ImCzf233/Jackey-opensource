package com.viaversion.viaversion.libs.javassist.bytecode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/* compiled from: ConstPool.java */
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/bytecode/FloatInfo.class */
public class FloatInfo extends ConstInfo {
    static final int tag = 4;
    float value;

    public FloatInfo(float f, int index) {
        super(index);
        this.value = f;
    }

    public FloatInfo(DataInputStream in, int index) throws IOException {
        super(index);
        this.value = in.readFloat();
    }

    public int hashCode() {
        return Float.floatToIntBits(this.value);
    }

    public boolean equals(Object obj) {
        return (obj instanceof FloatInfo) && ((FloatInfo) obj).value == this.value;
    }

    @Override // com.viaversion.viaversion.libs.javassist.bytecode.ConstInfo
    public int getTag() {
        return 4;
    }

    @Override // com.viaversion.viaversion.libs.javassist.bytecode.ConstInfo
    public int copy(ConstPool src, ConstPool dest, Map<String, String> map) {
        return dest.addFloatInfo(this.value);
    }

    @Override // com.viaversion.viaversion.libs.javassist.bytecode.ConstInfo
    public void write(DataOutputStream out) throws IOException {
        out.writeByte(4);
        out.writeFloat(this.value);
    }

    @Override // com.viaversion.viaversion.libs.javassist.bytecode.ConstInfo
    public void print(PrintWriter out) {
        out.print("Float ");
        out.println(this.value);
    }
}
