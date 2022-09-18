package com.viaversion.viaversion.libs.javassist.bytecode.analysis;

import com.viaversion.viaversion.libs.javassist.bytecode.CodeIterator;
import com.viaversion.viaversion.libs.javassist.bytecode.Opcode;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/bytecode/analysis/Util.class */
public class Util implements Opcode {
    public static int getJumpTarget(int pos, CodeIterator iter) {
        int opcode = iter.byteAt(pos);
        return pos + ((opcode == 201 || opcode == 200) ? iter.s32bitAt(pos + 1) : iter.s16bitAt(pos + 1));
    }

    public static boolean isJumpInstruction(int opcode) {
        return (opcode >= 153 && opcode <= 168) || opcode == 198 || opcode == 199 || opcode == 201 || opcode == 200;
    }

    public static boolean isGoto(int opcode) {
        return opcode == 167 || opcode == 200;
    }

    public static boolean isJsr(int opcode) {
        return opcode == 168 || opcode == 201;
    }

    public static boolean isReturn(int opcode) {
        return opcode >= 172 && opcode <= 177;
    }
}
