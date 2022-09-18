package com.viaversion.viaversion.libs.javassist.bytecode;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Map;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/bytecode/MethodParametersAttribute.class */
public class MethodParametersAttribute extends AttributeInfo {
    public static final String tag = "MethodParameters";

    public MethodParametersAttribute(ConstPool cp, int n, DataInputStream in) throws IOException {
        super(cp, n, in);
    }

    public MethodParametersAttribute(ConstPool cp, String[] names, int[] flags) {
        super(cp, tag);
        byte[] data = new byte[(names.length * 4) + 1];
        data[0] = (byte) names.length;
        for (int i = 0; i < names.length; i++) {
            ByteArray.write16bit(cp.addUtf8Info(names[i]), data, (i * 4) + 1);
            ByteArray.write16bit(flags[i], data, (i * 4) + 3);
        }
        set(data);
    }

    public int size() {
        return this.info[0] & 255;
    }

    public int name(int i) {
        return ByteArray.readU16bit(this.info, (i * 4) + 1);
    }

    public String parameterName(int i) {
        return getConstPool().getUtf8Info(name(i));
    }

    public int accessFlags(int i) {
        return ByteArray.readU16bit(this.info, (i * 4) + 3);
    }

    @Override // com.viaversion.viaversion.libs.javassist.bytecode.AttributeInfo
    public AttributeInfo copy(ConstPool newCp, Map<String, String> classnames) {
        int s = size();
        ConstPool cp = getConstPool();
        String[] names = new String[s];
        int[] flags = new int[s];
        for (int i = 0; i < s; i++) {
            names[i] = cp.getUtf8Info(name(i));
            flags[i] = accessFlags(i);
        }
        return new MethodParametersAttribute(newCp, names, flags);
    }
}
