package org.spongepowered.asm.lib.util;

import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.FieldVisitor;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.lib.TypePath;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/util/CheckFieldAdapter.class */
public class CheckFieldAdapter extends FieldVisitor {
    private boolean end;

    public CheckFieldAdapter(FieldVisitor fv) {
        this(Opcodes.ASM5, fv);
        if (getClass() != CheckFieldAdapter.class) {
            throw new IllegalStateException();
        }
    }

    protected CheckFieldAdapter(int api, FieldVisitor fv) {
        super(api, fv);
    }

    @Override // org.spongepowered.asm.lib.FieldVisitor
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        checkEnd();
        CheckMethodAdapter.checkDesc(desc, false);
        return new CheckAnnotationAdapter(super.visitAnnotation(desc, visible));
    }

    @Override // org.spongepowered.asm.lib.FieldVisitor
    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        checkEnd();
        int sort = typeRef >>> 24;
        if (sort != 19) {
            throw new IllegalArgumentException("Invalid type reference sort 0x" + Integer.toHexString(sort));
        }
        CheckClassAdapter.checkTypeRefAndPath(typeRef, typePath);
        CheckMethodAdapter.checkDesc(desc, false);
        return new CheckAnnotationAdapter(super.visitTypeAnnotation(typeRef, typePath, desc, visible));
    }

    @Override // org.spongepowered.asm.lib.FieldVisitor
    public void visitAttribute(Attribute attr) {
        checkEnd();
        if (attr == null) {
            throw new IllegalArgumentException("Invalid attribute (must not be null)");
        }
        super.visitAttribute(attr);
    }

    @Override // org.spongepowered.asm.lib.FieldVisitor
    public void visitEnd() {
        checkEnd();
        this.end = true;
        super.visitEnd();
    }

    private void checkEnd() {
        if (this.end) {
            throw new IllegalStateException("Cannot call a visit method after visitEnd has been called");
        }
    }
}
