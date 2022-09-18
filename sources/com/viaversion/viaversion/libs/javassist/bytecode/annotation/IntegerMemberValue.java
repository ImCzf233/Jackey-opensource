package com.viaversion.viaversion.libs.javassist.bytecode.annotation;

import com.viaversion.viaversion.libs.javassist.ClassPool;
import com.viaversion.viaversion.libs.javassist.bytecode.ConstPool;
import java.io.IOException;
import java.lang.reflect.Method;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/bytecode/annotation/IntegerMemberValue.class */
public class IntegerMemberValue extends MemberValue {
    int valueIndex;

    public IntegerMemberValue(int index, ConstPool cp) {
        super('I', cp);
        this.valueIndex = index;
    }

    public IntegerMemberValue(ConstPool cp, int value) {
        super('I', cp);
        setValue(value);
    }

    public IntegerMemberValue(ConstPool cp) {
        super('I', cp);
        setValue(0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.viaversion.viaversion.libs.javassist.bytecode.annotation.MemberValue
    public Object getValue(ClassLoader cl, ClassPool cp, Method m) {
        return Integer.valueOf(getValue());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.viaversion.viaversion.libs.javassist.bytecode.annotation.MemberValue
    public Class<?> getType(ClassLoader cl) {
        return Integer.TYPE;
    }

    public int getValue() {
        return this.f170cp.getIntegerInfo(this.valueIndex);
    }

    public void setValue(int newValue) {
        this.valueIndex = this.f170cp.addIntegerInfo(newValue);
    }

    public String toString() {
        return Integer.toString(getValue());
    }

    @Override // com.viaversion.viaversion.libs.javassist.bytecode.annotation.MemberValue
    public void write(AnnotationsWriter writer) throws IOException {
        writer.constValueIndex(getValue());
    }

    @Override // com.viaversion.viaversion.libs.javassist.bytecode.annotation.MemberValue
    public void accept(MemberValueVisitor visitor) {
        visitor.visitIntegerMemberValue(this);
    }
}
