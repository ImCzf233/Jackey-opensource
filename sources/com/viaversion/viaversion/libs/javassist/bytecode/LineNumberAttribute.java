package com.viaversion.viaversion.libs.javassist.bytecode;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Map;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/bytecode/LineNumberAttribute.class */
public class LineNumberAttribute extends AttributeInfo {
    public static final String tag = "LineNumberTable";

    /* renamed from: com.viaversion.viaversion.libs.javassist.bytecode.LineNumberAttribute$Pc */
    /* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/bytecode/LineNumberAttribute$Pc.class */
    public static class C0586Pc {
        public int index;
        public int line;
    }

    public LineNumberAttribute(ConstPool cp, int n, DataInputStream in) throws IOException {
        super(cp, n, in);
    }

    private LineNumberAttribute(ConstPool cp, byte[] i) {
        super(cp, tag, i);
    }

    public int tableLength() {
        return ByteArray.readU16bit(this.info, 0);
    }

    public int startPc(int i) {
        return ByteArray.readU16bit(this.info, (i * 4) + 2);
    }

    public int lineNumber(int i) {
        return ByteArray.readU16bit(this.info, (i * 4) + 4);
    }

    public int toLineNumber(int pc) {
        int n = tableLength();
        int i = 0;
        while (true) {
            if (i >= n) {
                break;
            } else if (pc >= startPc(i)) {
                i++;
            } else if (i == 0) {
                return lineNumber(0);
            }
        }
        return lineNumber(i - 1);
    }

    public int toStartPc(int line) {
        int n = tableLength();
        for (int i = 0; i < n; i++) {
            if (line == lineNumber(i)) {
                return startPc(i);
            }
        }
        return -1;
    }

    public C0586Pc toNearPc(int line) {
        int n = tableLength();
        int nearPc = 0;
        int distance = 0;
        if (n > 0) {
            distance = lineNumber(0) - line;
            nearPc = startPc(0);
        }
        for (int i = 1; i < n; i++) {
            int d = lineNumber(i) - line;
            if ((d < 0 && d > distance) || (d >= 0 && (d < distance || distance < 0))) {
                distance = d;
                nearPc = startPc(i);
            }
        }
        C0586Pc res = new C0586Pc();
        res.index = nearPc;
        res.line = line + distance;
        return res;
    }

    @Override // com.viaversion.viaversion.libs.javassist.bytecode.AttributeInfo
    public AttributeInfo copy(ConstPool newCp, Map<String, String> classnames) {
        byte[] src = this.info;
        int num = src.length;
        byte[] dest = new byte[num];
        for (int i = 0; i < num; i++) {
            dest[i] = src[i];
        }
        LineNumberAttribute attr = new LineNumberAttribute(newCp, dest);
        return attr;
    }

    public void shiftPc(int where, int gapLength, boolean exclusive) {
        int n = tableLength();
        for (int i = 0; i < n; i++) {
            int pos = (i * 4) + 2;
            int pc = ByteArray.readU16bit(this.info, pos);
            if (pc > where || (exclusive && pc == where)) {
                ByteArray.write16bit(pc + gapLength, this.info, pos);
            }
        }
    }
}
