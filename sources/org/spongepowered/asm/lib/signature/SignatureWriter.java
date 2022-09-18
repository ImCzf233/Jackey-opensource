package org.spongepowered.asm.lib.signature;

import org.spongepowered.asm.lib.Opcodes;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/signature/SignatureWriter.class */
public class SignatureWriter extends SignatureVisitor {
    private final StringBuilder buf = new StringBuilder();
    private boolean hasFormals;
    private boolean hasParameters;
    private int argumentStack;

    public SignatureWriter() {
        super(Opcodes.ASM5);
    }

    @Override // org.spongepowered.asm.lib.signature.SignatureVisitor
    public void visitFormalTypeParameter(String name) {
        if (!this.hasFormals) {
            this.hasFormals = true;
            this.buf.append('<');
        }
        this.buf.append(name);
        this.buf.append(':');
    }

    @Override // org.spongepowered.asm.lib.signature.SignatureVisitor
    public SignatureVisitor visitClassBound() {
        return this;
    }

    @Override // org.spongepowered.asm.lib.signature.SignatureVisitor
    public SignatureVisitor visitInterfaceBound() {
        this.buf.append(':');
        return this;
    }

    @Override // org.spongepowered.asm.lib.signature.SignatureVisitor
    public SignatureVisitor visitSuperclass() {
        endFormals();
        return this;
    }

    @Override // org.spongepowered.asm.lib.signature.SignatureVisitor
    public SignatureVisitor visitInterface() {
        return this;
    }

    @Override // org.spongepowered.asm.lib.signature.SignatureVisitor
    public SignatureVisitor visitParameterType() {
        endFormals();
        if (!this.hasParameters) {
            this.hasParameters = true;
            this.buf.append('(');
        }
        return this;
    }

    @Override // org.spongepowered.asm.lib.signature.SignatureVisitor
    public SignatureVisitor visitReturnType() {
        endFormals();
        if (!this.hasParameters) {
            this.buf.append('(');
        }
        this.buf.append(')');
        return this;
    }

    @Override // org.spongepowered.asm.lib.signature.SignatureVisitor
    public SignatureVisitor visitExceptionType() {
        this.buf.append('^');
        return this;
    }

    @Override // org.spongepowered.asm.lib.signature.SignatureVisitor
    public void visitBaseType(char descriptor) {
        this.buf.append(descriptor);
    }

    @Override // org.spongepowered.asm.lib.signature.SignatureVisitor
    public void visitTypeVariable(String name) {
        this.buf.append('T');
        this.buf.append(name);
        this.buf.append(';');
    }

    @Override // org.spongepowered.asm.lib.signature.SignatureVisitor
    public SignatureVisitor visitArrayType() {
        this.buf.append('[');
        return this;
    }

    @Override // org.spongepowered.asm.lib.signature.SignatureVisitor
    public void visitClassType(String name) {
        this.buf.append('L');
        this.buf.append(name);
        this.argumentStack *= 2;
    }

    @Override // org.spongepowered.asm.lib.signature.SignatureVisitor
    public void visitInnerClassType(String name) {
        endArguments();
        this.buf.append('.');
        this.buf.append(name);
        this.argumentStack *= 2;
    }

    @Override // org.spongepowered.asm.lib.signature.SignatureVisitor
    public void visitTypeArgument() {
        if (this.argumentStack % 2 == 0) {
            this.argumentStack++;
            this.buf.append('<');
        }
        this.buf.append('*');
    }

    @Override // org.spongepowered.asm.lib.signature.SignatureVisitor
    public SignatureVisitor visitTypeArgument(char wildcard) {
        if (this.argumentStack % 2 == 0) {
            this.argumentStack++;
            this.buf.append('<');
        }
        if (wildcard != '=') {
            this.buf.append(wildcard);
        }
        return this;
    }

    @Override // org.spongepowered.asm.lib.signature.SignatureVisitor
    public void visitEnd() {
        endArguments();
        this.buf.append(';');
    }

    public String toString() {
        return this.buf.toString();
    }

    private void endFormals() {
        if (this.hasFormals) {
            this.hasFormals = false;
            this.buf.append('>');
        }
    }

    private void endArguments() {
        if (this.argumentStack % 2 != 0) {
            this.buf.append('>');
        }
        this.argumentStack /= 2;
    }
}
