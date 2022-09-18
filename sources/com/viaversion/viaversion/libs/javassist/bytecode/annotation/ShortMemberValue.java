package com.viaversion.viaversion.libs.javassist.bytecode.annotation;

import com.viaversion.viaversion.libs.javassist.ClassPool;
import com.viaversion.viaversion.libs.javassist.bytecode.ConstPool;
import java.io.IOException;
import java.lang.reflect.Method;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/bytecode/annotation/ShortMemberValue.class */
public class ShortMemberValue extends MemberValue {
    int valueIndex;

    public ShortMemberValue(int index, ConstPool cp) {
        super('S', cp);
        this.valueIndex = index;
    }

    public ShortMemberValue(short s, ConstPool cp) {
        super('S', cp);
        setValue(s);
    }

    public ShortMemberValue(ConstPool cp) {
        super('S', cp);
        setValue((short) 0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.viaversion.viaversion.libs.javassist.bytecode.annotation.MemberValue
    public Object getValue(ClassLoader cl, ClassPool cp, Method m) {
        return Short.valueOf(getValue());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.viaversion.viaversion.libs.javassist.bytecode.annotation.MemberValue
    public Class<?> getType(ClassLoader cl) {
        return Short.TYPE;
    }

    public short getValue() {
        return (short) this.f170cp.getIntegerInfo(this.valueIndex);
    }

    public void setValue(short newValue) {
        this.valueIndex = this.f170cp.addIntegerInfo(newValue);
    }

    public String toString() {
        return Short.toString(getValue());
    }

    @Override // com.viaversion.viaversion.libs.javassist.bytecode.annotation.MemberValue
    public void write(AnnotationsWriter writer) throws IOException {
        writer.constValueIndex(getValue());
    }

    @Override // com.viaversion.viaversion.libs.javassist.bytecode.annotation.MemberValue
    public void accept(MemberValueVisitor visitor) {
        visitor.visitShortMemberValue(this);
    }
}
