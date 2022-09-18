package org.spongepowered.asm.lib.commons;

import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.ClassVisitor;
import org.spongepowered.asm.lib.FieldVisitor;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.lib.TypePath;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/commons/ClassRemapper.class */
public class ClassRemapper extends ClassVisitor {
    protected final Remapper remapper;
    protected String className;

    public ClassRemapper(ClassVisitor cv, Remapper remapper) {
        this(Opcodes.ASM5, cv, remapper);
    }

    public ClassRemapper(int api, ClassVisitor cv, Remapper remapper) {
        super(api, cv);
        this.remapper = remapper;
    }

    @Override // org.spongepowered.asm.lib.ClassVisitor
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.className = name;
        super.visit(version, access, this.remapper.mapType(name), this.remapper.mapSignature(signature, false), this.remapper.mapType(superName), interfaces == null ? null : this.remapper.mapTypes(interfaces));
    }

    @Override // org.spongepowered.asm.lib.ClassVisitor
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        AnnotationVisitor av = super.visitAnnotation(this.remapper.mapDesc(desc), visible);
        if (av == null) {
            return null;
        }
        return createAnnotationRemapper(av);
    }

    @Override // org.spongepowered.asm.lib.ClassVisitor
    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        AnnotationVisitor av = super.visitTypeAnnotation(typeRef, typePath, this.remapper.mapDesc(desc), visible);
        if (av == null) {
            return null;
        }
        return createAnnotationRemapper(av);
    }

    @Override // org.spongepowered.asm.lib.ClassVisitor
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        FieldVisitor fv = super.visitField(access, this.remapper.mapFieldName(this.className, name, desc), this.remapper.mapDesc(desc), this.remapper.mapSignature(signature, true), this.remapper.mapValue(value));
        if (fv == null) {
            return null;
        }
        return createFieldRemapper(fv);
    }

    @Override // org.spongepowered.asm.lib.ClassVisitor
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        String newDesc = this.remapper.mapMethodDesc(desc);
        MethodVisitor mv = super.visitMethod(access, this.remapper.mapMethodName(this.className, name, desc), newDesc, this.remapper.mapSignature(signature, false), exceptions == null ? null : this.remapper.mapTypes(exceptions));
        if (mv == null) {
            return null;
        }
        return createMethodRemapper(mv);
    }

    @Override // org.spongepowered.asm.lib.ClassVisitor
    public void visitInnerClass(String name, String outerName, String innerName, int access) {
        super.visitInnerClass(this.remapper.mapType(name), outerName == null ? null : this.remapper.mapType(outerName), innerName, access);
    }

    @Override // org.spongepowered.asm.lib.ClassVisitor
    public void visitOuterClass(String owner, String name, String desc) {
        super.visitOuterClass(this.remapper.mapType(owner), name == null ? null : this.remapper.mapMethodName(owner, name, desc), desc == null ? null : this.remapper.mapMethodDesc(desc));
    }

    protected FieldVisitor createFieldRemapper(FieldVisitor fv) {
        return new FieldRemapper(fv, this.remapper);
    }

    protected MethodVisitor createMethodRemapper(MethodVisitor mv) {
        return new MethodRemapper(mv, this.remapper);
    }

    protected AnnotationVisitor createAnnotationRemapper(AnnotationVisitor av) {
        return new AnnotationRemapper(av, this.remapper);
    }
}
