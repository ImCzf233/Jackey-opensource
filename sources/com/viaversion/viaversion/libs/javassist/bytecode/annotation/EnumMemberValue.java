package com.viaversion.viaversion.libs.javassist.bytecode.annotation;

import com.viaversion.viaversion.libs.javassist.ClassPool;
import com.viaversion.viaversion.libs.javassist.bytecode.ConstPool;
import com.viaversion.viaversion.libs.javassist.bytecode.Descriptor;
import java.io.IOException;
import java.lang.reflect.Method;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/bytecode/annotation/EnumMemberValue.class */
public class EnumMemberValue extends MemberValue {
    int typeIndex;
    int valueIndex;

    public EnumMemberValue(int type, int value, ConstPool cp) {
        super('e', cp);
        this.typeIndex = type;
        this.valueIndex = value;
    }

    public EnumMemberValue(ConstPool cp) {
        super('e', cp);
        this.valueIndex = 0;
        this.typeIndex = 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.viaversion.viaversion.libs.javassist.bytecode.annotation.MemberValue
    public Object getValue(ClassLoader cl, ClassPool cp, Method m) throws ClassNotFoundException {
        try {
            return getType(cl).getField(getValue()).get(null);
        } catch (IllegalAccessException e) {
            throw new ClassNotFoundException(getType() + "." + getValue());
        } catch (NoSuchFieldException e2) {
            throw new ClassNotFoundException(getType() + "." + getValue());
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.viaversion.viaversion.libs.javassist.bytecode.annotation.MemberValue
    public Class<?> getType(ClassLoader cl) throws ClassNotFoundException {
        return loadClass(cl, getType());
    }

    public String getType() {
        return Descriptor.toClassName(this.f170cp.getUtf8Info(this.typeIndex));
    }

    public void setType(String typename) {
        this.typeIndex = this.f170cp.addUtf8Info(Descriptor.m144of(typename));
    }

    public String getValue() {
        return this.f170cp.getUtf8Info(this.valueIndex);
    }

    public void setValue(String name) {
        this.valueIndex = this.f170cp.addUtf8Info(name);
    }

    public String toString() {
        return getType() + "." + getValue();
    }

    @Override // com.viaversion.viaversion.libs.javassist.bytecode.annotation.MemberValue
    public void write(AnnotationsWriter writer) throws IOException {
        writer.enumConstValue(this.f170cp.getUtf8Info(this.typeIndex), getValue());
    }

    @Override // com.viaversion.viaversion.libs.javassist.bytecode.annotation.MemberValue
    public void accept(MemberValueVisitor visitor) {
        visitor.visitEnumMemberValue(this);
    }
}
