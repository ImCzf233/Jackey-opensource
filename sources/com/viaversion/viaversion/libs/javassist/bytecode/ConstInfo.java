package com.viaversion.viaversion.libs.javassist.bytecode;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/* compiled from: ConstPool.java */
/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/bytecode/ConstInfo.class */
public abstract class ConstInfo {
    int index;

    public abstract int getTag();

    public abstract int copy(ConstPool constPool, ConstPool constPool2, Map<String, String> map);

    public abstract void write(DataOutputStream dataOutputStream) throws IOException;

    public abstract void print(PrintWriter printWriter);

    public ConstInfo(int i) {
        this.index = i;
    }

    public String getClassName(ConstPool cp) {
        return null;
    }

    public void renameClass(ConstPool cp, String oldName, String newName, Map<ConstInfo, ConstInfo> cache) {
    }

    public void renameClass(ConstPool cp, Map<String, String> classnames, Map<ConstInfo, ConstInfo> cache) {
    }

    public String toString() {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        PrintWriter out = new PrintWriter(bout);
        print(out);
        return bout.toString();
    }
}
