package com.viaversion.viaversion.libs.javassist.bytecode.annotation;

import com.viaversion.viaversion.libs.javassist.ClassPool;
import com.viaversion.viaversion.libs.javassist.bytecode.ConstPool;
import java.io.IOException;
import java.lang.reflect.Method;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/bytecode/annotation/StringMemberValue.class */
public class StringMemberValue extends MemberValue {
    int valueIndex;

    public StringMemberValue(int index, ConstPool cp) {
        super('s', cp);
        this.valueIndex = index;
    }

    public StringMemberValue(String str, ConstPool cp) {
        super('s', cp);
        setValue(str);
    }

    public StringMemberValue(ConstPool cp) {
        super('s', cp);
        setValue("");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.viaversion.viaversion.libs.javassist.bytecode.annotation.MemberValue
    public Object getValue(ClassLoader cl, ClassPool cp, Method m) {
        return getValue();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.viaversion.viaversion.libs.javassist.bytecode.annotation.MemberValue
    public Class<?> getType(ClassLoader cl) {
        return String.class;
    }

    public String getValue() {
        return this.f170cp.getUtf8Info(this.valueIndex);
    }

    public void setValue(String newValue) {
        this.valueIndex = this.f170cp.addUtf8Info(newValue);
    }

    public String toString() {
        return "\"" + getValue() + "\"";
    }

    @Override // com.viaversion.viaversion.libs.javassist.bytecode.annotation.MemberValue
    public void write(AnnotationsWriter writer) throws IOException {
        writer.constValueIndex(getValue());
    }

    @Override // com.viaversion.viaversion.libs.javassist.bytecode.annotation.MemberValue
    public void accept(MemberValueVisitor visitor) {
        visitor.visitStringMemberValue(this);
    }
}
