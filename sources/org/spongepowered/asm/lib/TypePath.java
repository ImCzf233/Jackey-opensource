package org.spongepowered.asm.lib;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/TypePath.class */
public class TypePath {
    public static final int ARRAY_ELEMENT = 0;
    public static final int INNER_TYPE = 1;
    public static final int WILDCARD_BOUND = 2;
    public static final int TYPE_ARGUMENT = 3;

    /* renamed from: b */
    byte[] f421b;
    int offset;

    public TypePath(byte[] b, int offset) {
        this.f421b = b;
        this.offset = offset;
    }

    public int getLength() {
        return this.f421b[this.offset];
    }

    public int getStep(int index) {
        return this.f421b[this.offset + (2 * index) + 1];
    }

    public int getStepArgument(int index) {
        return this.f421b[this.offset + (2 * index) + 2];
    }

    public static TypePath fromString(String typePath) {
        char c;
        if (typePath == null || typePath.length() == 0) {
            return null;
        }
        int n = typePath.length();
        ByteVector out = new ByteVector(n);
        out.putByte(0);
        int i = 0;
        while (i < n) {
            int i2 = i;
            i++;
            char c2 = typePath.charAt(i2);
            if (c2 == '[') {
                out.put11(0, 0);
            } else if (c2 == '.') {
                out.put11(1, 0);
            } else if (c2 == '*') {
                out.put11(2, 0);
            } else if (c2 >= '0' && c2 <= '9') {
                int typeArg = c2 - '0';
                while (i < n && (c = typePath.charAt(i)) >= '0' && c <= '9') {
                    typeArg = ((typeArg * 10) + c) - 48;
                    i++;
                }
                if (i < n && typePath.charAt(i) == ';') {
                    i++;
                }
                out.put11(3, typeArg);
            }
        }
        out.data[0] = (byte) (out.length / 2);
        return new TypePath(out.data, 0);
    }

    public String toString() {
        int length = getLength();
        StringBuilder result = new StringBuilder(length * 2);
        for (int i = 0; i < length; i++) {
            switch (getStep(i)) {
                case 0:
                    result.append('[');
                    break;
                case 1:
                    result.append('.');
                    break;
                case 2:
                    result.append('*');
                    break;
                case 3:
                    result.append(getStepArgument(i)).append(';');
                    break;
                default:
                    result.append('_');
                    break;
            }
        }
        return result.toString();
    }
}
