package org.spongepowered.asm.lib.util;

import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.lib.signature.SignatureVisitor;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/util/CheckSignatureAdapter.class */
public class CheckSignatureAdapter extends SignatureVisitor {
    public static final int CLASS_SIGNATURE = 0;
    public static final int METHOD_SIGNATURE = 1;
    public static final int TYPE_SIGNATURE = 2;
    private static final int EMPTY = 1;
    private static final int FORMAL = 2;
    private static final int BOUND = 4;
    private static final int SUPER = 8;
    private static final int PARAM = 16;
    private static final int RETURN = 32;
    private static final int SIMPLE_TYPE = 64;
    private static final int CLASS_TYPE = 128;
    private static final int END = 256;
    private final int type;
    private int state;
    private boolean canBeVoid;

    /* renamed from: sv */
    private final SignatureVisitor f427sv;

    public CheckSignatureAdapter(int type, SignatureVisitor sv) {
        this(Opcodes.ASM5, type, sv);
    }

    protected CheckSignatureAdapter(int api, int type, SignatureVisitor sv) {
        super(api);
        this.type = type;
        this.state = 1;
        this.f427sv = sv;
    }

    @Override // org.spongepowered.asm.lib.signature.SignatureVisitor
    public void visitFormalTypeParameter(String name) {
        if (this.type == 2 || (this.state != 1 && this.state != 2 && this.state != 4)) {
            throw new IllegalStateException();
        }
        CheckMethodAdapter.checkIdentifier(name, "formal type parameter");
        this.state = 2;
        if (this.f427sv != null) {
            this.f427sv.visitFormalTypeParameter(name);
        }
    }

    @Override // org.spongepowered.asm.lib.signature.SignatureVisitor
    public SignatureVisitor visitClassBound() {
        if (this.state != 2) {
            throw new IllegalStateException();
        }
        this.state = 4;
        SignatureVisitor v = this.f427sv == null ? null : this.f427sv.visitClassBound();
        return new CheckSignatureAdapter(2, v);
    }

    @Override // org.spongepowered.asm.lib.signature.SignatureVisitor
    public SignatureVisitor visitInterfaceBound() {
        if (this.state != 2 && this.state != 4) {
            throw new IllegalArgumentException();
        }
        SignatureVisitor v = this.f427sv == null ? null : this.f427sv.visitInterfaceBound();
        return new CheckSignatureAdapter(2, v);
    }

    @Override // org.spongepowered.asm.lib.signature.SignatureVisitor
    public SignatureVisitor visitSuperclass() {
        if (this.type != 0 || (this.state & 7) == 0) {
            throw new IllegalArgumentException();
        }
        this.state = 8;
        SignatureVisitor v = this.f427sv == null ? null : this.f427sv.visitSuperclass();
        return new CheckSignatureAdapter(2, v);
    }

    @Override // org.spongepowered.asm.lib.signature.SignatureVisitor
    public SignatureVisitor visitInterface() {
        if (this.state != 8) {
            throw new IllegalStateException();
        }
        SignatureVisitor v = this.f427sv == null ? null : this.f427sv.visitInterface();
        return new CheckSignatureAdapter(2, v);
    }

    @Override // org.spongepowered.asm.lib.signature.SignatureVisitor
    public SignatureVisitor visitParameterType() {
        if (this.type != 1 || (this.state & 23) == 0) {
            throw new IllegalArgumentException();
        }
        this.state = 16;
        SignatureVisitor v = this.f427sv == null ? null : this.f427sv.visitParameterType();
        return new CheckSignatureAdapter(2, v);
    }

    @Override // org.spongepowered.asm.lib.signature.SignatureVisitor
    public SignatureVisitor visitReturnType() {
        if (this.type != 1 || (this.state & 23) == 0) {
            throw new IllegalArgumentException();
        }
        this.state = 32;
        SignatureVisitor v = this.f427sv == null ? null : this.f427sv.visitReturnType();
        CheckSignatureAdapter cv = new CheckSignatureAdapter(2, v);
        cv.canBeVoid = true;
        return cv;
    }

    @Override // org.spongepowered.asm.lib.signature.SignatureVisitor
    public SignatureVisitor visitExceptionType() {
        if (this.state != 32) {
            throw new IllegalStateException();
        }
        SignatureVisitor v = this.f427sv == null ? null : this.f427sv.visitExceptionType();
        return new CheckSignatureAdapter(2, v);
    }

    @Override // org.spongepowered.asm.lib.signature.SignatureVisitor
    public void visitBaseType(char descriptor) {
        if (this.type != 2 || this.state != 1) {
            throw new IllegalStateException();
        }
        if (descriptor == 'V') {
            if (!this.canBeVoid) {
                throw new IllegalArgumentException();
            }
        } else if ("ZCBSIFJD".indexOf(descriptor) == -1) {
            throw new IllegalArgumentException();
        }
        this.state = 64;
        if (this.f427sv != null) {
            this.f427sv.visitBaseType(descriptor);
        }
    }

    @Override // org.spongepowered.asm.lib.signature.SignatureVisitor
    public void visitTypeVariable(String name) {
        if (this.type != 2 || this.state != 1) {
            throw new IllegalStateException();
        }
        CheckMethodAdapter.checkIdentifier(name, "type variable");
        this.state = 64;
        if (this.f427sv != null) {
            this.f427sv.visitTypeVariable(name);
        }
    }

    @Override // org.spongepowered.asm.lib.signature.SignatureVisitor
    public SignatureVisitor visitArrayType() {
        if (this.type != 2 || this.state != 1) {
            throw new IllegalStateException();
        }
        this.state = 64;
        SignatureVisitor v = this.f427sv == null ? null : this.f427sv.visitArrayType();
        return new CheckSignatureAdapter(2, v);
    }

    @Override // org.spongepowered.asm.lib.signature.SignatureVisitor
    public void visitClassType(String name) {
        if (this.type != 2 || this.state != 1) {
            throw new IllegalStateException();
        }
        CheckMethodAdapter.checkInternalName(name, "class name");
        this.state = 128;
        if (this.f427sv != null) {
            this.f427sv.visitClassType(name);
        }
    }

    @Override // org.spongepowered.asm.lib.signature.SignatureVisitor
    public void visitInnerClassType(String name) {
        if (this.state != 128) {
            throw new IllegalStateException();
        }
        CheckMethodAdapter.checkIdentifier(name, "inner class name");
        if (this.f427sv != null) {
            this.f427sv.visitInnerClassType(name);
        }
    }

    @Override // org.spongepowered.asm.lib.signature.SignatureVisitor
    public void visitTypeArgument() {
        if (this.state != 128) {
            throw new IllegalStateException();
        }
        if (this.f427sv != null) {
            this.f427sv.visitTypeArgument();
        }
    }

    @Override // org.spongepowered.asm.lib.signature.SignatureVisitor
    public SignatureVisitor visitTypeArgument(char wildcard) {
        if (this.state != 128) {
            throw new IllegalStateException();
        }
        if ("+-=".indexOf(wildcard) == -1) {
            throw new IllegalArgumentException();
        }
        SignatureVisitor v = this.f427sv == null ? null : this.f427sv.visitTypeArgument(wildcard);
        return new CheckSignatureAdapter(2, v);
    }

    @Override // org.spongepowered.asm.lib.signature.SignatureVisitor
    public void visitEnd() {
        if (this.state != 128) {
            throw new IllegalStateException();
        }
        this.state = 256;
        if (this.f427sv != null) {
            this.f427sv.visitEnd();
        }
    }
}
