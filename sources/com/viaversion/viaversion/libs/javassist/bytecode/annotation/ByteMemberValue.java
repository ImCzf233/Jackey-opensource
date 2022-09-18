package com.viaversion.viaversion.libs.javassist.bytecode.annotation;

import com.viaversion.viaversion.libs.javassist.ClassPool;
import com.viaversion.viaversion.libs.javassist.bytecode.ConstPool;
import java.io.IOException;
import java.lang.reflect.Method;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/bytecode/annotation/ByteMemberValue.class */
public class ByteMemberValue extends MemberValue {
    int valueIndex;

    public ByteMemberValue(int index, ConstPool cp) {
        super('B', cp);
        this.valueIndex = index;
    }

    public ByteMemberValue(byte b, ConstPool cp) {
        super('B', cp);
        setValue(b);
    }

    public ByteMemberValue(ConstPool cp) {
        super('B', cp);
        setValue((byte) 0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.viaversion.viaversion.libs.javassist.bytecode.annotation.MemberValue
    public Object getValue(ClassLoader cl, ClassPool cp, Method m) {
        return Byte.valueOf(getValue());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.viaversion.viaversion.libs.javassist.bytecode.annotation.MemberValue
    public Class<?> getType(ClassLoader cl) {
        return Byte.TYPE;
    }

    public byte getValue() {
        return (byte) this.f170cp.getIntegerInfo(this.valueIndex);
    }

    public void setValue(byte newValue) {
        this.valueIndex = this.f170cp.addIntegerInfo(newValue);
    }

    public String toString() {
        return Byte.toString(getValue());
    }

    @Override // com.viaversion.viaversion.libs.javassist.bytecode.annotation.MemberValue
    public void write(AnnotationsWriter writer) throws IOException {
        writer.constValueIndex(getValue());
    }

    @Override // com.viaversion.viaversion.libs.javassist.bytecode.annotation.MemberValue
    public void accept(MemberValueVisitor visitor) {
        visitor.visitByteMemberValue(this);
    }
}
