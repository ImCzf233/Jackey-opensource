package org.spongepowered.asm.lib.tree;

import java.util.ArrayList;
import java.util.List;
import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.ClassVisitor;
import org.spongepowered.asm.lib.FieldVisitor;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.lib.TypePath;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/tree/FieldNode.class */
public class FieldNode extends FieldVisitor {
    public int access;
    public String name;
    public String desc;
    public String signature;
    public Object value;
    public List<AnnotationNode> visibleAnnotations;
    public List<AnnotationNode> invisibleAnnotations;
    public List<TypeAnnotationNode> visibleTypeAnnotations;
    public List<TypeAnnotationNode> invisibleTypeAnnotations;
    public List<Attribute> attrs;

    public FieldNode(int access, String name, String desc, String signature, Object value) {
        this(Opcodes.ASM5, access, name, desc, signature, value);
        if (getClass() != FieldNode.class) {
            throw new IllegalStateException();
        }
    }

    public FieldNode(int api, int access, String name, String desc, String signature, Object value) {
        super(api);
        this.access = access;
        this.name = name;
        this.desc = desc;
        this.signature = signature;
        this.value = value;
    }

    @Override // org.spongepowered.asm.lib.FieldVisitor
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        AnnotationNode an = new AnnotationNode(desc);
        if (visible) {
            if (this.visibleAnnotations == null) {
                this.visibleAnnotations = new ArrayList(1);
            }
            this.visibleAnnotations.add(an);
        } else {
            if (this.invisibleAnnotations == null) {
                this.invisibleAnnotations = new ArrayList(1);
            }
            this.invisibleAnnotations.add(an);
        }
        return an;
    }

    @Override // org.spongepowered.asm.lib.FieldVisitor
    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        TypeAnnotationNode an = new TypeAnnotationNode(typeRef, typePath, desc);
        if (visible) {
            if (this.visibleTypeAnnotations == null) {
                this.visibleTypeAnnotations = new ArrayList(1);
            }
            this.visibleTypeAnnotations.add(an);
        } else {
            if (this.invisibleTypeAnnotations == null) {
                this.invisibleTypeAnnotations = new ArrayList(1);
            }
            this.invisibleTypeAnnotations.add(an);
        }
        return an;
    }

    @Override // org.spongepowered.asm.lib.FieldVisitor
    public void visitAttribute(Attribute attr) {
        if (this.attrs == null) {
            this.attrs = new ArrayList(1);
        }
        this.attrs.add(attr);
    }

    @Override // org.spongepowered.asm.lib.FieldVisitor
    public void visitEnd() {
    }

    public void check(int api) {
        if (api == 262144) {
            if (this.visibleTypeAnnotations != null && this.visibleTypeAnnotations.size() > 0) {
                throw new RuntimeException();
            }
            if (this.invisibleTypeAnnotations != null && this.invisibleTypeAnnotations.size() > 0) {
                throw new RuntimeException();
            }
        }
    }

    public void accept(ClassVisitor cv) {
        FieldVisitor fv = cv.visitField(this.access, this.name, this.desc, this.signature, this.value);
        if (fv == null) {
            return;
        }
        int n = this.visibleAnnotations == null ? 0 : this.visibleAnnotations.size();
        for (int i = 0; i < n; i++) {
            AnnotationNode an = this.visibleAnnotations.get(i);
            an.accept(fv.visitAnnotation(an.desc, true));
        }
        int n2 = this.invisibleAnnotations == null ? 0 : this.invisibleAnnotations.size();
        for (int i2 = 0; i2 < n2; i2++) {
            AnnotationNode an2 = this.invisibleAnnotations.get(i2);
            an2.accept(fv.visitAnnotation(an2.desc, false));
        }
        int n3 = this.visibleTypeAnnotations == null ? 0 : this.visibleTypeAnnotations.size();
        for (int i3 = 0; i3 < n3; i3++) {
            TypeAnnotationNode an3 = this.visibleTypeAnnotations.get(i3);
            an3.accept(fv.visitTypeAnnotation(an3.typeRef, an3.typePath, an3.desc, true));
        }
        int n4 = this.invisibleTypeAnnotations == null ? 0 : this.invisibleTypeAnnotations.size();
        for (int i4 = 0; i4 < n4; i4++) {
            TypeAnnotationNode an4 = this.invisibleTypeAnnotations.get(i4);
            an4.accept(fv.visitTypeAnnotation(an4.typeRef, an4.typePath, an4.desc, false));
        }
        int n5 = this.attrs == null ? 0 : this.attrs.size();
        for (int i5 = 0; i5 < n5; i5++) {
            fv.visitAttribute(this.attrs.get(i5));
        }
        fv.visitEnd();
    }
}
