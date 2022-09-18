package org.spongepowered.asm.lib;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/AnnotationWriter.class */
public final class AnnotationWriter extends AnnotationVisitor {

    /* renamed from: cw */
    private final ClassWriter f412cw;
    private int size;
    private final boolean named;

    /* renamed from: bv */
    private final ByteVector f413bv;
    private final ByteVector parent;
    private final int offset;
    AnnotationWriter next;
    AnnotationWriter prev;

    public AnnotationWriter(ClassWriter cw, boolean named, ByteVector bv, ByteVector parent, int offset) {
        super(Opcodes.ASM5);
        this.f412cw = cw;
        this.named = named;
        this.f413bv = bv;
        this.parent = parent;
        this.offset = offset;
    }

    @Override // org.spongepowered.asm.lib.AnnotationVisitor
    public void visit(String name, Object value) {
        this.size++;
        if (this.named) {
            this.f413bv.putShort(this.f412cw.newUTF8(name));
        }
        if (value instanceof String) {
            this.f413bv.put12(115, this.f412cw.newUTF8((String) value));
        } else if (value instanceof Byte) {
            this.f413bv.put12(66, this.f412cw.newInteger(((Byte) value).byteValue()).index);
        } else if (value instanceof Boolean) {
            this.f413bv.put12(90, this.f412cw.newInteger(((Boolean) value).booleanValue() ? 1 : 0).index);
        } else if (value instanceof Character) {
            this.f413bv.put12(67, this.f412cw.newInteger(((Character) value).charValue()).index);
        } else if (value instanceof Short) {
            this.f413bv.put12(83, this.f412cw.newInteger(((Short) value).shortValue()).index);
        } else if (value instanceof Type) {
            this.f413bv.put12(99, this.f412cw.newUTF8(((Type) value).getDescriptor()));
        } else if (value instanceof byte[]) {
            byte[] v = (byte[]) value;
            this.f413bv.put12(91, v.length);
            for (byte b : v) {
                this.f413bv.put12(66, this.f412cw.newInteger(b).index);
            }
        } else if (value instanceof boolean[]) {
            boolean[] v2 = (boolean[]) value;
            this.f413bv.put12(91, v2.length);
            for (boolean z : v2) {
                this.f413bv.put12(90, this.f412cw.newInteger(z ? 1 : 0).index);
            }
        } else if (value instanceof short[]) {
            short[] v3 = (short[]) value;
            this.f413bv.put12(91, v3.length);
            for (short s : v3) {
                this.f413bv.put12(83, this.f412cw.newInteger(s).index);
            }
        } else if (value instanceof char[]) {
            char[] v4 = (char[]) value;
            this.f413bv.put12(91, v4.length);
            for (char c : v4) {
                this.f413bv.put12(67, this.f412cw.newInteger(c).index);
            }
        } else if (value instanceof int[]) {
            int[] v5 = (int[]) value;
            this.f413bv.put12(91, v5.length);
            for (int i : v5) {
                this.f413bv.put12(73, this.f412cw.newInteger(i).index);
            }
        } else if (value instanceof long[]) {
            long[] v6 = (long[]) value;
            this.f413bv.put12(91, v6.length);
            for (long j : v6) {
                this.f413bv.put12(74, this.f412cw.newLong(j).index);
            }
        } else if (value instanceof float[]) {
            float[] v7 = (float[]) value;
            this.f413bv.put12(91, v7.length);
            for (float f : v7) {
                this.f413bv.put12(70, this.f412cw.newFloat(f).index);
            }
        } else if (value instanceof double[]) {
            double[] v8 = (double[]) value;
            this.f413bv.put12(91, v8.length);
            for (double d : v8) {
                this.f413bv.put12(68, this.f412cw.newDouble(d).index);
            }
        } else {
            Item i2 = this.f412cw.newConstItem(value);
            this.f413bv.put12(".s.IFJDCS".charAt(i2.type), i2.index);
        }
    }

    @Override // org.spongepowered.asm.lib.AnnotationVisitor
    public void visitEnum(String name, String desc, String value) {
        this.size++;
        if (this.named) {
            this.f413bv.putShort(this.f412cw.newUTF8(name));
        }
        this.f413bv.put12(101, this.f412cw.newUTF8(desc)).putShort(this.f412cw.newUTF8(value));
    }

    @Override // org.spongepowered.asm.lib.AnnotationVisitor
    public AnnotationVisitor visitAnnotation(String name, String desc) {
        this.size++;
        if (this.named) {
            this.f413bv.putShort(this.f412cw.newUTF8(name));
        }
        this.f413bv.put12(64, this.f412cw.newUTF8(desc)).putShort(0);
        return new AnnotationWriter(this.f412cw, true, this.f413bv, this.f413bv, this.f413bv.length - 2);
    }

    @Override // org.spongepowered.asm.lib.AnnotationVisitor
    public AnnotationVisitor visitArray(String name) {
        this.size++;
        if (this.named) {
            this.f413bv.putShort(this.f412cw.newUTF8(name));
        }
        this.f413bv.put12(91, 0);
        return new AnnotationWriter(this.f412cw, false, this.f413bv, this.f413bv, this.f413bv.length - 2);
    }

    @Override // org.spongepowered.asm.lib.AnnotationVisitor
    public void visitEnd() {
        if (this.parent != null) {
            byte[] data = this.parent.data;
            data[this.offset] = (byte) (this.size >>> 8);
            data[this.offset + 1] = (byte) this.size;
        }
    }

    public int getSize() {
        int size = 0;
        AnnotationWriter annotationWriter = this;
        while (true) {
            AnnotationWriter aw = annotationWriter;
            if (aw != null) {
                size += aw.f413bv.length;
                annotationWriter = aw.next;
            } else {
                return size;
            }
        }
    }

    public void put(ByteVector out) {
        int n = 0;
        int size = 2;
        AnnotationWriter last = null;
        for (AnnotationWriter aw = this; aw != null; aw = aw.next) {
            n++;
            size += aw.f413bv.length;
            aw.visitEnd();
            aw.prev = last;
            last = aw;
        }
        out.putInt(size);
        out.putShort(n);
        AnnotationWriter annotationWriter = last;
        while (true) {
            AnnotationWriter aw2 = annotationWriter;
            if (aw2 != null) {
                out.putByteArray(aw2.f413bv.data, 0, aw2.f413bv.length);
                annotationWriter = aw2.prev;
            } else {
                return;
            }
        }
    }

    public static void put(AnnotationWriter[] panns, int off, ByteVector out) {
        int size = 1 + (2 * (panns.length - off));
        for (int i = off; i < panns.length; i++) {
            size += panns[i] == null ? 0 : panns[i].getSize();
        }
        out.putInt(size).putByte(panns.length - off);
        for (int i2 = off; i2 < panns.length; i2++) {
            AnnotationWriter last = null;
            int n = 0;
            for (AnnotationWriter aw = panns[i2]; aw != null; aw = aw.next) {
                n++;
                aw.visitEnd();
                aw.prev = last;
                last = aw;
            }
            out.putShort(n);
            AnnotationWriter annotationWriter = last;
            while (true) {
                AnnotationWriter aw2 = annotationWriter;
                if (aw2 != null) {
                    out.putByteArray(aw2.f413bv.data, 0, aw2.f413bv.length);
                    annotationWriter = aw2.prev;
                }
            }
        }
    }

    public static void putTarget(int typeRef, TypePath typePath, ByteVector out) {
        switch (typeRef >>> 24) {
            case 0:
            case 1:
            case 22:
                out.putShort(typeRef >>> 16);
                break;
            case 19:
            case 20:
            case 21:
                out.putByte(typeRef >>> 24);
                break;
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
                out.putInt(typeRef);
                break;
            default:
                out.put12(typeRef >>> 24, (typeRef & 16776960) >> 8);
                break;
        }
        if (typePath == null) {
            out.putByte(0);
            return;
        }
        int length = (typePath.f421b[typePath.offset] * 2) + 1;
        out.putByteArray(typePath.f421b, typePath.offset, length);
    }
}
