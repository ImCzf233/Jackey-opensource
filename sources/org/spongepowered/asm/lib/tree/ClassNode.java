package org.spongepowered.asm.lib.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.ClassVisitor;
import org.spongepowered.asm.lib.FieldVisitor;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.lib.TypePath;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/tree/ClassNode.class */
public class ClassNode extends ClassVisitor {
    public int version;
    public int access;
    public String name;
    public String signature;
    public String superName;
    public List<String> interfaces;
    public String sourceFile;
    public String sourceDebug;
    public String outerClass;
    public String outerMethod;
    public String outerMethodDesc;
    public List<AnnotationNode> visibleAnnotations;
    public List<AnnotationNode> invisibleAnnotations;
    public List<TypeAnnotationNode> visibleTypeAnnotations;
    public List<TypeAnnotationNode> invisibleTypeAnnotations;
    public List<Attribute> attrs;
    public List<InnerClassNode> innerClasses;
    public List<FieldNode> fields;
    public List<MethodNode> methods;

    public ClassNode() {
        this(Opcodes.ASM5);
        if (getClass() != ClassNode.class) {
            throw new IllegalStateException();
        }
    }

    public ClassNode(int api) {
        super(api);
        this.interfaces = new ArrayList();
        this.innerClasses = new ArrayList();
        this.fields = new ArrayList();
        this.methods = new ArrayList();
    }

    @Override // org.spongepowered.asm.lib.ClassVisitor
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.version = version;
        this.access = access;
        this.name = name;
        this.signature = signature;
        this.superName = superName;
        if (interfaces != null) {
            this.interfaces.addAll(Arrays.asList(interfaces));
        }
    }

    @Override // org.spongepowered.asm.lib.ClassVisitor
    public void visitSource(String file, String debug) {
        this.sourceFile = file;
        this.sourceDebug = debug;
    }

    @Override // org.spongepowered.asm.lib.ClassVisitor
    public void visitOuterClass(String owner, String name, String desc) {
        this.outerClass = owner;
        this.outerMethod = name;
        this.outerMethodDesc = desc;
    }

    @Override // org.spongepowered.asm.lib.ClassVisitor
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

    @Override // org.spongepowered.asm.lib.ClassVisitor
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

    @Override // org.spongepowered.asm.lib.ClassVisitor
    public void visitAttribute(Attribute attr) {
        if (this.attrs == null) {
            this.attrs = new ArrayList(1);
        }
        this.attrs.add(attr);
    }

    @Override // org.spongepowered.asm.lib.ClassVisitor
    public void visitInnerClass(String name, String outerName, String innerName, int access) {
        InnerClassNode icn = new InnerClassNode(name, outerName, innerName, access);
        this.innerClasses.add(icn);
    }

    @Override // org.spongepowered.asm.lib.ClassVisitor
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        FieldNode fn = new FieldNode(access, name, desc, signature, value);
        this.fields.add(fn);
        return fn;
    }

    @Override // org.spongepowered.asm.lib.ClassVisitor
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodNode mn = new MethodNode(access, name, desc, signature, exceptions);
        this.methods.add(mn);
        return mn;
    }

    @Override // org.spongepowered.asm.lib.ClassVisitor
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
            for (FieldNode f : this.fields) {
                f.check(api);
            }
            for (MethodNode m : this.methods) {
                m.check(api);
            }
        }
    }

    public void accept(ClassVisitor cv) {
        String[] interfaces = new String[this.interfaces.size()];
        this.interfaces.toArray(interfaces);
        cv.visit(this.version, this.access, this.name, this.signature, this.superName, interfaces);
        if (this.sourceFile != null || this.sourceDebug != null) {
            cv.visitSource(this.sourceFile, this.sourceDebug);
        }
        if (this.outerClass != null) {
            cv.visitOuterClass(this.outerClass, this.outerMethod, this.outerMethodDesc);
        }
        int n = this.visibleAnnotations == null ? 0 : this.visibleAnnotations.size();
        for (int i = 0; i < n; i++) {
            AnnotationNode an = this.visibleAnnotations.get(i);
            an.accept(cv.visitAnnotation(an.desc, true));
        }
        int n2 = this.invisibleAnnotations == null ? 0 : this.invisibleAnnotations.size();
        for (int i2 = 0; i2 < n2; i2++) {
            AnnotationNode an2 = this.invisibleAnnotations.get(i2);
            an2.accept(cv.visitAnnotation(an2.desc, false));
        }
        int n3 = this.visibleTypeAnnotations == null ? 0 : this.visibleTypeAnnotations.size();
        for (int i3 = 0; i3 < n3; i3++) {
            TypeAnnotationNode an3 = this.visibleTypeAnnotations.get(i3);
            an3.accept(cv.visitTypeAnnotation(an3.typeRef, an3.typePath, an3.desc, true));
        }
        int n4 = this.invisibleTypeAnnotations == null ? 0 : this.invisibleTypeAnnotations.size();
        for (int i4 = 0; i4 < n4; i4++) {
            TypeAnnotationNode an4 = this.invisibleTypeAnnotations.get(i4);
            an4.accept(cv.visitTypeAnnotation(an4.typeRef, an4.typePath, an4.desc, false));
        }
        int n5 = this.attrs == null ? 0 : this.attrs.size();
        for (int i5 = 0; i5 < n5; i5++) {
            cv.visitAttribute(this.attrs.get(i5));
        }
        for (int i6 = 0; i6 < this.innerClasses.size(); i6++) {
            this.innerClasses.get(i6).accept(cv);
        }
        for (int i7 = 0; i7 < this.fields.size(); i7++) {
            this.fields.get(i7).accept(cv);
        }
        for (int i8 = 0; i8 < this.methods.size(); i8++) {
            this.methods.get(i8).accept(cv);
        }
        cv.visitEnd();
    }
}
