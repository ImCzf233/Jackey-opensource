package org.spongepowered.asm.lib.commons;

import java.util.Stack;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.lib.signature.SignatureVisitor;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/commons/SignatureRemapper.class */
public class SignatureRemapper extends SignatureVisitor {

    /* renamed from: v */
    private final SignatureVisitor f422v;
    private final Remapper remapper;
    private Stack<String> classNames;

    public SignatureRemapper(SignatureVisitor v, Remapper remapper) {
        this(Opcodes.ASM5, v, remapper);
    }

    protected SignatureRemapper(int api, SignatureVisitor v, Remapper remapper) {
        super(api);
        this.classNames = new Stack<>();
        this.f422v = v;
        this.remapper = remapper;
    }

    @Override // org.spongepowered.asm.lib.signature.SignatureVisitor
    public void visitClassType(String name) {
        this.classNames.push(name);
        this.f422v.visitClassType(this.remapper.mapType(name));
    }

    @Override // org.spongepowered.asm.lib.signature.SignatureVisitor
    public void visitInnerClassType(String name) {
        String outerClassName = this.classNames.pop();
        String className = outerClassName + '$' + name;
        this.classNames.push(className);
        String remappedOuter = this.remapper.mapType(outerClassName) + '$';
        String remappedName = this.remapper.mapType(className);
        int index = remappedName.startsWith(remappedOuter) ? remappedOuter.length() : remappedName.lastIndexOf(36) + 1;
        this.f422v.visitInnerClassType(remappedName.substring(index));
    }

    @Override // org.spongepowered.asm.lib.signature.SignatureVisitor
    public void visitFormalTypeParameter(String name) {
        this.f422v.visitFormalTypeParameter(name);
    }

    @Override // org.spongepowered.asm.lib.signature.SignatureVisitor
    public void visitTypeVariable(String name) {
        this.f422v.visitTypeVariable(name);
    }

    @Override // org.spongepowered.asm.lib.signature.SignatureVisitor
    public SignatureVisitor visitArrayType() {
        this.f422v.visitArrayType();
        return this;
    }

    @Override // org.spongepowered.asm.lib.signature.SignatureVisitor
    public void visitBaseType(char descriptor) {
        this.f422v.visitBaseType(descriptor);
    }

    @Override // org.spongepowered.asm.lib.signature.SignatureVisitor
    public SignatureVisitor visitClassBound() {
        this.f422v.visitClassBound();
        return this;
    }

    @Override // org.spongepowered.asm.lib.signature.SignatureVisitor
    public SignatureVisitor visitExceptionType() {
        this.f422v.visitExceptionType();
        return this;
    }

    @Override // org.spongepowered.asm.lib.signature.SignatureVisitor
    public SignatureVisitor visitInterface() {
        this.f422v.visitInterface();
        return this;
    }

    @Override // org.spongepowered.asm.lib.signature.SignatureVisitor
    public SignatureVisitor visitInterfaceBound() {
        this.f422v.visitInterfaceBound();
        return this;
    }

    @Override // org.spongepowered.asm.lib.signature.SignatureVisitor
    public SignatureVisitor visitParameterType() {
        this.f422v.visitParameterType();
        return this;
    }

    @Override // org.spongepowered.asm.lib.signature.SignatureVisitor
    public SignatureVisitor visitReturnType() {
        this.f422v.visitReturnType();
        return this;
    }

    @Override // org.spongepowered.asm.lib.signature.SignatureVisitor
    public SignatureVisitor visitSuperclass() {
        this.f422v.visitSuperclass();
        return this;
    }

    @Override // org.spongepowered.asm.lib.signature.SignatureVisitor
    public void visitTypeArgument() {
        this.f422v.visitTypeArgument();
    }

    @Override // org.spongepowered.asm.lib.signature.SignatureVisitor
    public SignatureVisitor visitTypeArgument(char wildcard) {
        this.f422v.visitTypeArgument(wildcard);
        return this;
    }

    @Override // org.spongepowered.asm.lib.signature.SignatureVisitor
    public void visitEnd() {
        this.f422v.visitEnd();
        this.classNames.pop();
    }
}
