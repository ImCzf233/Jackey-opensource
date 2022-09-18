package org.spongepowered.asm.lib.commons;

import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Opcodes;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/commons/AnnotationRemapper.class */
public class AnnotationRemapper extends AnnotationVisitor {
    protected final Remapper remapper;

    public AnnotationRemapper(AnnotationVisitor av, Remapper remapper) {
        this(Opcodes.ASM5, av, remapper);
    }

    protected AnnotationRemapper(int api, AnnotationVisitor av, Remapper remapper) {
        super(api, av);
        this.remapper = remapper;
    }

    @Override // org.spongepowered.asm.lib.AnnotationVisitor
    public void visit(String name, Object value) {
        this.f411av.visit(name, this.remapper.mapValue(value));
    }

    @Override // org.spongepowered.asm.lib.AnnotationVisitor
    public void visitEnum(String name, String desc, String value) {
        this.f411av.visitEnum(name, this.remapper.mapDesc(desc), value);
    }

    @Override // org.spongepowered.asm.lib.AnnotationVisitor
    public AnnotationVisitor visitAnnotation(String name, String desc) {
        AnnotationVisitor v = this.f411av.visitAnnotation(name, this.remapper.mapDesc(desc));
        if (v == null) {
            return null;
        }
        return v == this.f411av ? this : new AnnotationRemapper(v, this.remapper);
    }

    @Override // org.spongepowered.asm.lib.AnnotationVisitor
    public AnnotationVisitor visitArray(String name) {
        AnnotationVisitor v = this.f411av.visitArray(name);
        if (v == null) {
            return null;
        }
        return v == this.f411av ? this : new AnnotationRemapper(v, this.remapper);
    }
}
