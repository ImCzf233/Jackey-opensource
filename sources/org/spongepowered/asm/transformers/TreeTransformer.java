package org.spongepowered.asm.transformers;

import org.spongepowered.asm.lib.ClassReader;
import org.spongepowered.asm.lib.ClassWriter;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.service.ILegacyClassTransformer;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/transformers/TreeTransformer.class */
public abstract class TreeTransformer implements ILegacyClassTransformer {
    private ClassReader classReader;
    private ClassNode classNode;

    protected final ClassNode readClass(byte[] basicClass) {
        return readClass(basicClass, true);
    }

    public final ClassNode readClass(byte[] basicClass, boolean cacheReader) {
        ClassReader classReader = new ClassReader(basicClass);
        if (cacheReader) {
            this.classReader = classReader;
        }
        ClassNode classNode = new ClassNode();
        classReader.accept(classNode, 8);
        return classNode;
    }

    public final byte[] writeClass(ClassNode classNode) {
        if (this.classReader != null && this.classNode == classNode) {
            this.classNode = null;
            ClassWriter writer = new MixinClassWriter(this.classReader, 3);
            this.classReader = null;
            classNode.accept(writer);
            return writer.toByteArray();
        }
        this.classNode = null;
        ClassWriter writer2 = new MixinClassWriter(3);
        classNode.accept(writer2);
        return writer2.toByteArray();
    }
}
