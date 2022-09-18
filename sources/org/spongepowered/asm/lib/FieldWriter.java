package org.spongepowered.asm.lib;

import com.viaversion.viaversion.libs.javassist.bytecode.AnnotationsAttribute;
import com.viaversion.viaversion.libs.javassist.bytecode.ConstantAttribute;
import com.viaversion.viaversion.libs.javassist.bytecode.DeprecatedAttribute;
import com.viaversion.viaversion.libs.javassist.bytecode.SignatureAttribute;
import com.viaversion.viaversion.libs.javassist.bytecode.SyntheticAttribute;
import com.viaversion.viaversion.libs.javassist.bytecode.TypeAnnotationsAttribute;
import kotlin.jvm.internal.CharCompanionObject;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/FieldWriter.class */
public final class FieldWriter extends FieldVisitor {

    /* renamed from: cw */
    private final ClassWriter f418cw;
    private final int access;
    private final int name;
    private final int desc;
    private int signature;
    private int value;
    private AnnotationWriter anns;
    private AnnotationWriter ianns;
    private AnnotationWriter tanns;
    private AnnotationWriter itanns;
    private Attribute attrs;

    public FieldWriter(ClassWriter cw, int access, String name, String desc, String signature, Object value) {
        super(Opcodes.ASM5);
        if (cw.firstField == null) {
            cw.firstField = this;
        } else {
            cw.lastField.f417fv = this;
        }
        cw.lastField = this;
        this.f418cw = cw;
        this.access = access;
        this.name = cw.newUTF8(name);
        this.desc = cw.newUTF8(desc);
        if (signature != null) {
            this.signature = cw.newUTF8(signature);
        }
        if (value != null) {
            this.value = cw.newConstItem(value).index;
        }
    }

    @Override // org.spongepowered.asm.lib.FieldVisitor
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        ByteVector bv = new ByteVector();
        bv.putShort(this.f418cw.newUTF8(desc)).putShort(0);
        AnnotationWriter aw = new AnnotationWriter(this.f418cw, true, bv, bv, 2);
        if (visible) {
            aw.next = this.anns;
            this.anns = aw;
        } else {
            aw.next = this.ianns;
            this.ianns = aw;
        }
        return aw;
    }

    @Override // org.spongepowered.asm.lib.FieldVisitor
    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        ByteVector bv = new ByteVector();
        AnnotationWriter.putTarget(typeRef, typePath, bv);
        bv.putShort(this.f418cw.newUTF8(desc)).putShort(0);
        AnnotationWriter aw = new AnnotationWriter(this.f418cw, true, bv, bv, bv.length - 2);
        if (visible) {
            aw.next = this.tanns;
            this.tanns = aw;
        } else {
            aw.next = this.itanns;
            this.itanns = aw;
        }
        return aw;
    }

    @Override // org.spongepowered.asm.lib.FieldVisitor
    public void visitAttribute(Attribute attr) {
        attr.next = this.attrs;
        this.attrs = attr;
    }

    @Override // org.spongepowered.asm.lib.FieldVisitor
    public void visitEnd() {
    }

    public int getSize() {
        int size = 8;
        if (this.value != 0) {
            this.f418cw.newUTF8(ConstantAttribute.tag);
            size = 8 + 8;
        }
        if ((this.access & 4096) != 0 && ((this.f418cw.version & CharCompanionObject.MAX_VALUE) < 49 || (this.access & 262144) != 0)) {
            this.f418cw.newUTF8(SyntheticAttribute.tag);
            size += 6;
        }
        if ((this.access & 131072) != 0) {
            this.f418cw.newUTF8(DeprecatedAttribute.tag);
            size += 6;
        }
        if (this.signature != 0) {
            this.f418cw.newUTF8(SignatureAttribute.tag);
            size += 8;
        }
        if (this.anns != null) {
            this.f418cw.newUTF8(AnnotationsAttribute.visibleTag);
            size += 8 + this.anns.getSize();
        }
        if (this.ianns != null) {
            this.f418cw.newUTF8(AnnotationsAttribute.invisibleTag);
            size += 8 + this.ianns.getSize();
        }
        if (this.tanns != null) {
            this.f418cw.newUTF8(TypeAnnotationsAttribute.visibleTag);
            size += 8 + this.tanns.getSize();
        }
        if (this.itanns != null) {
            this.f418cw.newUTF8(TypeAnnotationsAttribute.invisibleTag);
            size += 8 + this.itanns.getSize();
        }
        if (this.attrs != null) {
            size += this.attrs.getSize(this.f418cw, null, 0, -1, -1);
        }
        return size;
    }

    public void put(ByteVector out) {
        int mask = 393216 | ((this.access & 262144) / 64);
        out.putShort(this.access & (mask ^ (-1))).putShort(this.name).putShort(this.desc);
        int attributeCount = 0;
        if (this.value != 0) {
            attributeCount = 0 + 1;
        }
        if ((this.access & 4096) != 0 && ((this.f418cw.version & CharCompanionObject.MAX_VALUE) < 49 || (this.access & 262144) != 0)) {
            attributeCount++;
        }
        if ((this.access & 131072) != 0) {
            attributeCount++;
        }
        if (this.signature != 0) {
            attributeCount++;
        }
        if (this.anns != null) {
            attributeCount++;
        }
        if (this.ianns != null) {
            attributeCount++;
        }
        if (this.tanns != null) {
            attributeCount++;
        }
        if (this.itanns != null) {
            attributeCount++;
        }
        if (this.attrs != null) {
            attributeCount += this.attrs.getCount();
        }
        out.putShort(attributeCount);
        if (this.value != 0) {
            out.putShort(this.f418cw.newUTF8(ConstantAttribute.tag));
            out.putInt(2).putShort(this.value);
        }
        if ((this.access & 4096) != 0 && ((this.f418cw.version & CharCompanionObject.MAX_VALUE) < 49 || (this.access & 262144) != 0)) {
            out.putShort(this.f418cw.newUTF8(SyntheticAttribute.tag)).putInt(0);
        }
        if ((this.access & 131072) != 0) {
            out.putShort(this.f418cw.newUTF8(DeprecatedAttribute.tag)).putInt(0);
        }
        if (this.signature != 0) {
            out.putShort(this.f418cw.newUTF8(SignatureAttribute.tag));
            out.putInt(2).putShort(this.signature);
        }
        if (this.anns != null) {
            out.putShort(this.f418cw.newUTF8(AnnotationsAttribute.visibleTag));
            this.anns.put(out);
        }
        if (this.ianns != null) {
            out.putShort(this.f418cw.newUTF8(AnnotationsAttribute.invisibleTag));
            this.ianns.put(out);
        }
        if (this.tanns != null) {
            out.putShort(this.f418cw.newUTF8(TypeAnnotationsAttribute.visibleTag));
            this.tanns.put(out);
        }
        if (this.itanns != null) {
            out.putShort(this.f418cw.newUTF8(TypeAnnotationsAttribute.invisibleTag));
            this.itanns.put(out);
        }
        if (this.attrs != null) {
            this.attrs.put(this.f418cw, null, 0, -1, -1, out);
        }
    }
}
