package org.spongepowered.asm.lib.commons;

import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.FieldVisitor;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.lib.TypePath;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/commons/FieldRemapper.class */
public class FieldRemapper extends FieldVisitor {
    private final Remapper remapper;

    public FieldRemapper(FieldVisitor fv, Remapper remapper) {
        this(Opcodes.ASM5, fv, remapper);
    }

    protected FieldRemapper(int api, FieldVisitor fv, Remapper remapper) {
        super(api, fv);
        this.remapper = remapper;
    }

    @Override // org.spongepowered.asm.lib.FieldVisitor
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        AnnotationVisitor av = this.f417fv.visitAnnotation(this.remapper.mapDesc(desc), visible);
        if (av == null) {
            return null;
        }
        return new AnnotationRemapper(av, this.remapper);
    }

    @Override // org.spongepowered.asm.lib.FieldVisitor
    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        AnnotationVisitor av = super.visitTypeAnnotation(typeRef, typePath, this.remapper.mapDesc(desc), visible);
        if (av == null) {
            return null;
        }
        return new AnnotationRemapper(av, this.remapper);
    }
}
