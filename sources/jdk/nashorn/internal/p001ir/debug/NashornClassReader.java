package jdk.nashorn.internal.p001ir.debug;

import com.viaversion.viaversion.libs.javassist.bytecode.CodeAttribute;
import com.viaversion.viaversion.libs.javassist.bytecode.Opcode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jdk.internal.org.objectweb.asm.Attribute;
import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Label;
import jdk.nashorn.internal.p001ir.debug.NashornTextifier;

/* renamed from: jdk.nashorn.internal.ir.debug.NashornClassReader */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/debug/NashornClassReader.class */
public class NashornClassReader extends ClassReader {
    private final Map<String, List<Label>> labelMap = new HashMap();
    private static String[] type;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !NashornClassReader.class.desiredAssertionStatus();
        type = new String[]{"<error>", "UTF8", "<error>", "Integer", "Float", "Long", "Double", "Class", "String", "Fieldref", "Methodref", "InterfaceMethodRef", "NameAndType", "<error>", "<error>", "MethodHandle", "MethodType", "<error>", "Invokedynamic"};
    }

    public NashornClassReader(byte[] bytecode) {
        super(bytecode);
        parse(bytecode);
    }

    public List<Label> getExtraLabels(String className, String methodName, String methodDesc) {
        String key = fullyQualifiedName(className, methodName, methodDesc);
        return this.labelMap.get(key);
    }

    private static int readByte(byte[] bytecode, int index) {
        return (byte) (bytecode[index] & 255);
    }

    private static int readShort(byte[] bytecode, int index) {
        return ((short) ((bytecode[index] & 255) << 8)) | (bytecode[index + 1] & 255);
    }

    private static int readInt(byte[] bytecode, int index) {
        return ((bytecode[index] & 255) << 24) | ((bytecode[index + 1] & 255) << 16) | ((bytecode[index + 2] & 255) << 8) | (bytecode[index + 3] & 255);
    }

    private static long readLong(byte[] bytecode, int index) {
        int hi = readInt(bytecode, index);
        int lo = readInt(bytecode, index + 4);
        return (hi << 32) | lo;
    }

    private static String readUTF(int index, int utfLen, byte[] bytecode) {
        int endIndex = index + utfLen;
        char[] buf = new char[utfLen * 2];
        int strLen = 0;
        int st = 0;
        char cc = 0;
        int i = index;
        while (i < endIndex) {
            int i2 = i;
            i++;
            byte b = bytecode[i2];
            switch (st) {
                case 0:
                    int c = b & 255;
                    if (c < 128) {
                        int i3 = strLen;
                        strLen++;
                        buf[i3] = (char) c;
                        break;
                    } else if (c < 224 && c > 191) {
                        cc = (char) (c & 31);
                        st = 1;
                        break;
                    } else {
                        cc = (char) (c & 15);
                        st = 2;
                        break;
                    }
                    break;
                case 1:
                    int i4 = strLen;
                    strLen++;
                    buf[i4] = (char) ((cc << 6) | (b & 63));
                    st = 0;
                    break;
                case 2:
                    cc = (char) ((cc << 6) | (b & 63));
                    st = 1;
                    break;
            }
        }
        return new String(buf, 0, strLen);
    }

    private String parse(byte[] bytecode) {
        int magic = readInt(bytecode, 0);
        int u = 0 + 4;
        if ($assertionsDisabled || magic == -889275714) {
            readShort(bytecode, u);
            int u2 = u + 2;
            readShort(bytecode, u2);
            int u3 = u2 + 2;
            int cpc = readShort(bytecode, u3);
            int u4 = u3 + 2;
            ArrayList<Constant> cp = new ArrayList<>(cpc);
            cp.add(null);
            int i = 1;
            while (i < cpc) {
                int tag = readByte(bytecode, u4);
                u4++;
                switch (tag) {
                    case 1:
                        int len = readShort(bytecode, u4);
                        int u5 = u4 + 2;
                        cp.add(new DirectInfo(cp, tag, readUTF(u5, len, bytecode)));
                        u4 = u5 + len;
                        break;
                    case 2:
                    case 13:
                    case 14:
                    case 17:
                    default:
                        if (!$assertionsDisabled) {
                            throw new AssertionError(tag);
                        }
                        break;
                    case 3:
                        cp.add(new DirectInfo(cp, tag, Integer.valueOf(readInt(bytecode, u4))));
                        u4 += 4;
                        break;
                    case 4:
                        cp.add(new DirectInfo(cp, tag, Float.valueOf(Float.intBitsToFloat(readInt(bytecode, u4)))));
                        u4 += 4;
                        break;
                    case 5:
                        cp.add(new DirectInfo(cp, tag, Long.valueOf(readLong(bytecode, u4))));
                        cp.add(null);
                        i++;
                        u4 += 8;
                        break;
                    case 6:
                        cp.add(new DirectInfo(cp, tag, Double.valueOf(Double.longBitsToDouble(readLong(bytecode, u4)))));
                        cp.add(null);
                        i++;
                        u4 += 8;
                        break;
                    case 7:
                        cp.add(new IndexInfo(cp, tag, readShort(bytecode, u4)));
                        u4 += 2;
                        break;
                    case 8:
                        cp.add(new IndexInfo(cp, tag, readShort(bytecode, u4)));
                        u4 += 2;
                        break;
                    case 9:
                    case 10:
                    case 11:
                        cp.add(new IndexInfo2(cp, tag, readShort(bytecode, u4), readShort(bytecode, u4 + 2)));
                        u4 += 4;
                        break;
                    case 12:
                        cp.add(new IndexInfo2(cp, tag, readShort(bytecode, u4), readShort(bytecode, u4 + 2)));
                        u4 += 4;
                        break;
                    case 15:
                        int kind = readByte(bytecode, u4);
                        if (!$assertionsDisabled && (kind < 1 || kind > 9)) {
                            throw new AssertionError(kind);
                        }
                        cp.add(new IndexInfo2(cp, tag, kind, readShort(bytecode, u4 + 1)) { // from class: jdk.nashorn.internal.ir.debug.NashornClassReader.2
                            @Override // jdk.nashorn.internal.p001ir.debug.NashornClassReader.IndexInfo2, jdk.nashorn.internal.p001ir.debug.NashornClassReader.IndexInfo
                            public String toString() {
                                return "#" + this.index + ' ' + this.f244cp.get(this.index2).toString();
                            }
                        });
                        u4 += 3;
                        break;
                    case 16:
                        cp.add(new IndexInfo(cp, tag, readShort(bytecode, u4)));
                        u4 += 2;
                        break;
                    case 18:
                        cp.add(new IndexInfo2(cp, tag, readShort(bytecode, u4), readShort(bytecode, u4 + 2)) { // from class: jdk.nashorn.internal.ir.debug.NashornClassReader.1
                            @Override // jdk.nashorn.internal.p001ir.debug.NashornClassReader.IndexInfo2, jdk.nashorn.internal.p001ir.debug.NashornClassReader.IndexInfo
                            public String toString() {
                                return "#" + this.index + ' ' + this.f244cp.get(this.index2).toString();
                            }
                        });
                        u4 += 4;
                        break;
                }
                i++;
            }
            readShort(bytecode, u4);
            int u6 = u4 + 2;
            int cls = readShort(bytecode, u6);
            String thisClassName = cp.get(cls).toString();
            int u7 = u6 + 2 + 2;
            int ifc = readShort(bytecode, u7);
            int u8 = u7 + 2 + (ifc * 2);
            int fc = readShort(bytecode, u8);
            int u9 = u8 + 2;
            for (int i2 = 0; i2 < fc; i2++) {
                int u10 = u9 + 2;
                readShort(bytecode, u10);
                int u11 = u10 + 2 + 2;
                int ac = readShort(bytecode, u11);
                u9 = u11 + 2;
                for (int j = 0; j < ac; j++) {
                    int u12 = u9 + 2;
                    u9 = u12 + 4 + readInt(bytecode, u12);
                }
            }
            int mc = readShort(bytecode, u9);
            int u13 = u9 + 2;
            for (int i3 = 0; i3 < mc; i3++) {
                readShort(bytecode, u13);
                int u14 = u13 + 2;
                int methodNameIndex = readShort(bytecode, u14);
                int u15 = u14 + 2;
                String methodName = cp.get(methodNameIndex).toString();
                int methodDescIndex = readShort(bytecode, u15);
                int u16 = u15 + 2;
                String methodDesc = cp.get(methodDescIndex).toString();
                int ac2 = readShort(bytecode, u16);
                u13 = u16 + 2;
                for (int j2 = 0; j2 < ac2; j2++) {
                    int nameIndex = readShort(bytecode, u13);
                    int u17 = u13 + 2;
                    String attrName = cp.get(nameIndex).toString();
                    int attrLen = readInt(bytecode, u17);
                    int u18 = u17 + 4;
                    if (CodeAttribute.tag.equals(attrName)) {
                        readShort(bytecode, u18);
                        int u19 = u18 + 2;
                        readShort(bytecode, u19);
                        int u20 = u19 + 2;
                        int len2 = readInt(bytecode, u20);
                        int u21 = u20 + 4;
                        parseCode(bytecode, u21, len2, fullyQualifiedName(thisClassName, methodName, methodDesc));
                        int u22 = u21 + len2;
                        int elen = readShort(bytecode, u22);
                        int u23 = u22 + 2 + (elen * 8);
                        int ac22 = readShort(bytecode, u23);
                        u13 = u23 + 2;
                        for (int k = 0; k < ac22; k++) {
                            int u24 = u13 + 2;
                            int aclen = readInt(bytecode, u24);
                            u13 = u24 + 4 + aclen;
                        }
                    } else {
                        u13 = u18 + attrLen;
                    }
                }
            }
            int ac3 = readShort(bytecode, u13);
            int u25 = u13 + 2;
            for (int i4 = 0; i4 < ac3; i4++) {
                readShort(bytecode, u25);
                int u26 = u25 + 2;
                u25 = u26 + 4 + readInt(bytecode, u26);
            }
            return thisClassName;
        }
        throw new AssertionError(Integer.toHexString(magic));
    }

    private static String fullyQualifiedName(String className, String methodName, String methodDesc) {
        return className + '.' + methodName + methodDesc;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private void parseCode(byte[] bytecode, int index, int len, String desc) {
        List<Label> labels = new ArrayList<>();
        this.labelMap.put(desc, labels);
        boolean wide = false;
        int i = index;
        while (i < index + len) {
            byte b = bytecode[i];
            labels.add(new NashornTextifier.NashornLabel(b, i - index));
            switch (b & 255) {
                case 16:
                case 18:
                case 188:
                    i += 2;
                    break;
                case 17:
                case 19:
                case 20:
                case 153:
                case 154:
                case 155:
                case 156:
                case 157:
                case 158:
                case 159:
                case 160:
                case 161:
                case 162:
                case 163:
                case 164:
                case 165:
                case 166:
                case 167:
                case 168:
                case 178:
                case 179:
                case 180:
                case 181:
                case 182:
                case 183:
                case 184:
                case 187:
                case 189:
                case 192:
                case 193:
                case 198:
                case 199:
                    i += 3;
                    break;
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 54:
                case 55:
                case 56:
                case 57:
                case 58:
                    i += wide ? 3 : 2;
                    break;
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                case 50:
                case 51:
                case 52:
                case 53:
                case 59:
                case 60:
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case 66:
                case 67:
                case 68:
                case 69:
                case 70:
                case 71:
                case 72:
                case 73:
                case 74:
                case 75:
                case 76:
                case 77:
                case 78:
                case 79:
                case 80:
                case 81:
                case 82:
                case 83:
                case 84:
                case 85:
                case 86:
                case 87:
                case 88:
                case 89:
                case 90:
                case 91:
                case 92:
                case 93:
                case 94:
                case 95:
                case 96:
                case 97:
                case 98:
                case 99:
                case 100:
                case 101:
                case 102:
                case 103:
                case 104:
                case 105:
                case 106:
                case 107:
                case 108:
                case 109:
                case 110:
                case 111:
                case 112:
                case 113:
                case 114:
                case 115:
                case 116:
                case 117:
                case 118:
                case 119:
                case 120:
                case 121:
                case 122:
                case 123:
                case 124:
                case 125:
                case 126:
                case 127:
                case 128:
                case 129:
                case 130:
                case 131:
                case 133:
                case 134:
                case 135:
                case 136:
                case 137:
                case 138:
                case 139:
                case 140:
                case 141:
                case 142:
                case 143:
                case 144:
                case 145:
                case 146:
                case 147:
                case 148:
                case 149:
                case 150:
                case 151:
                case 152:
                case 172:
                case 173:
                case 174:
                case 175:
                case 176:
                case 177:
                case 190:
                case 191:
                case 194:
                case 195:
                default:
                    i++;
                    break;
                case 132:
                    i += wide ? 5 : 3;
                    break;
                case 169:
                    i += wide ? 4 : 2;
                    break;
                case 170:
                    while (true) {
                        i++;
                        if (((i - index) & 3) == 0) {
                            readInt(bytecode, i);
                            int i2 = i + 4;
                            int lo = readInt(bytecode, i2);
                            int i3 = i2 + 4;
                            int hi = readInt(bytecode, i3);
                            i = i3 + 4 + (4 * ((hi - lo) + 1));
                            break;
                        }
                    }
                case 171:
                    while (true) {
                        i++;
                        if (((i - index) & 3) == 0) {
                            readInt(bytecode, i);
                            int i4 = i + 4;
                            int npairs = readInt(bytecode, i4);
                            i = i4 + 4 + (8 * npairs);
                            break;
                        }
                    }
                case 185:
                case 186:
                case 200:
                case Opcode.JSR_W /* 201 */:
                    i += 5;
                    break;
                case Opcode.WIDE /* 196 */:
                    wide = true;
                    i++;
                    break;
                case 197:
                    i += 4;
                    break;
            }
            if (wide) {
                wide = false;
            }
        }
    }

    public void accept(ClassVisitor classVisitor, Attribute[] attrs, int flags) {
        super.accept(classVisitor, attrs, flags);
    }

    protected Label readLabel(int offset, Label[] labels) {
        Label label = super.readLabel(offset, labels);
        label.info = Integer.valueOf(offset);
        return label;
    }

    /* renamed from: jdk.nashorn.internal.ir.debug.NashornClassReader$Constant */
    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/debug/NashornClassReader$Constant.class */
    public static abstract class Constant {

        /* renamed from: cp */
        protected ArrayList<Constant> f244cp;
        protected int tag;

        protected Constant(ArrayList<Constant> cp, int tag) {
            this.f244cp = cp;
            this.tag = tag;
        }

        final String getType() {
            String str = NashornClassReader.type[this.tag];
            while (true) {
                String str2 = str;
                if (str2.length() < 16) {
                    str = str2 + " ";
                } else {
                    return str2;
                }
            }
        }
    }

    /* renamed from: jdk.nashorn.internal.ir.debug.NashornClassReader$IndexInfo */
    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/debug/NashornClassReader$IndexInfo.class */
    public static class IndexInfo extends Constant {
        protected final int index;

        IndexInfo(ArrayList<Constant> cp, int tag, int index) {
            super(cp, tag);
            this.index = index;
        }

        public String toString() {
            return this.f244cp.get(this.index).toString();
        }
    }

    /* renamed from: jdk.nashorn.internal.ir.debug.NashornClassReader$IndexInfo2 */
    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/debug/NashornClassReader$IndexInfo2.class */
    public static class IndexInfo2 extends IndexInfo {
        protected final int index2;

        IndexInfo2(ArrayList<Constant> cp, int tag, int index, int index2) {
            super(cp, tag, index);
            this.index2 = index2;
        }

        @Override // jdk.nashorn.internal.p001ir.debug.NashornClassReader.IndexInfo
        public String toString() {
            return super.toString() + ' ' + this.f244cp.get(this.index2).toString();
        }
    }

    /* renamed from: jdk.nashorn.internal.ir.debug.NashornClassReader$DirectInfo */
    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/debug/NashornClassReader$DirectInfo.class */
    public static class DirectInfo<T> extends Constant {
        protected final T info;

        DirectInfo(ArrayList<Constant> cp, int tag, T info) {
            super(cp, tag);
            this.info = info;
        }

        public String toString() {
            return this.info.toString();
        }
    }
}
