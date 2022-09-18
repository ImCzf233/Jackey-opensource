package org.spongepowered.asm.transformers;

import org.spongepowered.asm.lib.ClassReader;
import org.spongepowered.asm.lib.ClassWriter;
import org.spongepowered.asm.mixin.transformer.ClassInfo;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/transformers/MixinClassWriter.class */
public class MixinClassWriter extends ClassWriter {
    public MixinClassWriter(int flags) {
        super(flags);
    }

    public MixinClassWriter(ClassReader classReader, int flags) {
        super(classReader, flags);
    }

    @Override // org.spongepowered.asm.lib.ClassWriter
    protected String getCommonSuperClass(String type1, String type2) {
        return ClassInfo.getCommonSuperClass(type1, type2).getName();
    }
}
