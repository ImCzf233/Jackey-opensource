package com.viaversion.viaversion.libs.javassist.bytecode.annotation;

import com.viaversion.viaversion.libs.javassist.ClassPool;
import com.viaversion.viaversion.libs.javassist.bytecode.ConstPool;
import java.io.IOException;
import java.lang.reflect.Method;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/bytecode/annotation/CharMemberValue.class */
public class CharMemberValue extends MemberValue {
    int valueIndex;

    public CharMemberValue(int index, ConstPool cp) {
        super('C', cp);
        this.valueIndex = index;
    }

    public CharMemberValue(char c, ConstPool cp) {
        super('C', cp);
        setValue(c);
    }

    public CharMemberValue(ConstPool cp) {
        super('C', cp);
        setValue((char) 0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.viaversion.viaversion.libs.javassist.bytecode.annotation.MemberValue
    public Object getValue(ClassLoader cl, ClassPool cp, Method m) {
        return Character.valueOf(getValue());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.viaversion.viaversion.libs.javassist.bytecode.annotation.MemberValue
    public Class<?> getType(ClassLoader cl) {
        return Character.TYPE;
    }

    public char getValue() {
        return (char) this.f170cp.getIntegerInfo(this.valueIndex);
    }

    public void setValue(char newValue) {
        this.valueIndex = this.f170cp.addIntegerInfo(newValue);
    }

    public String toString() {
        return Character.toString(getValue());
    }

    @Override // com.viaversion.viaversion.libs.javassist.bytecode.annotation.MemberValue
    public void write(AnnotationsWriter writer) throws IOException {
        writer.constValueIndex(getValue());
    }

    @Override // com.viaversion.viaversion.libs.javassist.bytecode.annotation.MemberValue
    public void accept(MemberValueVisitor visitor) {
        visitor.visitCharMemberValue(this);
    }
}
