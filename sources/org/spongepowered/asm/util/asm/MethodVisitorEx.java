package org.spongepowered.asm.util.asm;

import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.util.Bytecode;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/util/asm/MethodVisitorEx.class */
public class MethodVisitorEx extends MethodVisitor {
    public MethodVisitorEx(MethodVisitor mv) {
        super(Opcodes.ASM5, mv);
    }

    public void visitConstant(byte constant) {
        if (constant > -2 && constant < 6) {
            visitInsn(Bytecode.CONSTANTS_INT[constant + 1]);
        } else {
            visitIntInsn(16, constant);
        }
    }
}
