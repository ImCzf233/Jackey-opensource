package org.spongepowered.asm.mixin.gen;

import java.util.ArrayList;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.lib.tree.MethodNode;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/gen/AccessorGenerator.class */
public abstract class AccessorGenerator {
    protected final AccessorInfo info;

    public abstract MethodNode generate();

    public AccessorGenerator(AccessorInfo info) {
        this.info = info;
    }

    public final MethodNode createMethod(int maxLocals, int maxStack) {
        MethodNode method = this.info.getMethod();
        MethodNode accessor = new MethodNode(Opcodes.ASM5, (method.access & (-1025)) | 4096, method.name, method.desc, null, null);
        accessor.visibleAnnotations = new ArrayList();
        accessor.visibleAnnotations.add(this.info.getAnnotation());
        accessor.maxLocals = maxLocals;
        accessor.maxStack = maxStack;
        return accessor;
    }
}
