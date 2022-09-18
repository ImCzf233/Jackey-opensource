package org.spongepowered.asm.lib.util;

import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.lib.Type;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/util/CheckAnnotationAdapter.class */
public class CheckAnnotationAdapter extends AnnotationVisitor {
    private final boolean named;
    private boolean end;

    public CheckAnnotationAdapter(AnnotationVisitor av) {
        this(av, true);
    }

    public CheckAnnotationAdapter(AnnotationVisitor av, boolean named) {
        super(Opcodes.ASM5, av);
        this.named = named;
    }

    @Override // org.spongepowered.asm.lib.AnnotationVisitor
    public void visit(String name, Object value) {
        checkEnd();
        checkName(name);
        if (!(value instanceof Byte) && !(value instanceof Boolean) && !(value instanceof Character) && !(value instanceof Short) && !(value instanceof Integer) && !(value instanceof Long) && !(value instanceof Float) && !(value instanceof Double) && !(value instanceof String) && !(value instanceof Type) && !(value instanceof byte[]) && !(value instanceof boolean[]) && !(value instanceof char[]) && !(value instanceof short[]) && !(value instanceof int[]) && !(value instanceof long[]) && !(value instanceof float[]) && !(value instanceof double[])) {
            throw new IllegalArgumentException("Invalid annotation value");
        }
        if (value instanceof Type) {
            int sort = ((Type) value).getSort();
            if (sort == 11) {
                throw new IllegalArgumentException("Invalid annotation value");
            }
        }
        if (this.f411av != null) {
            this.f411av.visit(name, value);
        }
    }

    @Override // org.spongepowered.asm.lib.AnnotationVisitor
    public void visitEnum(String name, String desc, String value) {
        checkEnd();
        checkName(name);
        CheckMethodAdapter.checkDesc(desc, false);
        if (value == null) {
            throw new IllegalArgumentException("Invalid enum value");
        }
        if (this.f411av != null) {
            this.f411av.visitEnum(name, desc, value);
        }
    }

    @Override // org.spongepowered.asm.lib.AnnotationVisitor
    public AnnotationVisitor visitAnnotation(String name, String desc) {
        checkEnd();
        checkName(name);
        CheckMethodAdapter.checkDesc(desc, false);
        return new CheckAnnotationAdapter(this.f411av == null ? null : this.f411av.visitAnnotation(name, desc));
    }

    @Override // org.spongepowered.asm.lib.AnnotationVisitor
    public AnnotationVisitor visitArray(String name) {
        checkEnd();
        checkName(name);
        return new CheckAnnotationAdapter(this.f411av == null ? null : this.f411av.visitArray(name), false);
    }

    @Override // org.spongepowered.asm.lib.AnnotationVisitor
    public void visitEnd() {
        checkEnd();
        this.end = true;
        if (this.f411av != null) {
            this.f411av.visitEnd();
        }
    }

    private void checkEnd() {
        if (this.end) {
            throw new IllegalStateException("Cannot call a visit method after visitEnd has been called");
        }
    }

    private void checkName(String name) {
        if (this.named && name == null) {
            throw new IllegalArgumentException("Annotation value name must not be null");
        }
    }
}
