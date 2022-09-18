package org.spongepowered.asm.mixin.struct;

import jdk.internal.dynalink.CallSiteDescriptor;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.mixin.transformer.throwables.MixinTransformerError;
import org.spongepowered.asm.util.Bytecode;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/struct/MemberRef.class */
public abstract class MemberRef {
    private static final int[] H_OPCODES = {0, 180, 178, 181, 179, 182, 184, 183, 183, 185};

    public abstract boolean isField();

    public abstract int getOpcode();

    public abstract void setOpcode(int i);

    public abstract String getOwner();

    public abstract void setOwner(String str);

    public abstract String getName();

    public abstract void setName(String str);

    public abstract String getDesc();

    public abstract void setDesc(String str);

    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/struct/MemberRef$Method.class */
    public static final class Method extends MemberRef {
        private static final int OPCODES = 191;
        public final MethodInsnNode insn;

        public Method(MethodInsnNode insn) {
            this.insn = insn;
        }

        @Override // org.spongepowered.asm.mixin.struct.MemberRef
        public boolean isField() {
            return false;
        }

        @Override // org.spongepowered.asm.mixin.struct.MemberRef
        public int getOpcode() {
            return this.insn.getOpcode();
        }

        @Override // org.spongepowered.asm.mixin.struct.MemberRef
        public void setOpcode(int opcode) {
            if ((opcode & 191) == 0) {
                throw new IllegalArgumentException("Invalid opcode for method instruction: 0x" + Integer.toHexString(opcode));
            }
            this.insn.setOpcode(opcode);
        }

        @Override // org.spongepowered.asm.mixin.struct.MemberRef
        public String getOwner() {
            return this.insn.owner;
        }

        @Override // org.spongepowered.asm.mixin.struct.MemberRef
        public void setOwner(String owner) {
            this.insn.owner = owner;
        }

        @Override // org.spongepowered.asm.mixin.struct.MemberRef
        public String getName() {
            return this.insn.name;
        }

        @Override // org.spongepowered.asm.mixin.struct.MemberRef
        public void setName(String name) {
            this.insn.name = name;
        }

        @Override // org.spongepowered.asm.mixin.struct.MemberRef
        public String getDesc() {
            return this.insn.desc;
        }

        @Override // org.spongepowered.asm.mixin.struct.MemberRef
        public void setDesc(String desc) {
            this.insn.desc = desc;
        }
    }

    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/struct/MemberRef$Field.class */
    public static final class Field extends MemberRef {
        private static final int OPCODES = 183;
        public final FieldInsnNode insn;

        public Field(FieldInsnNode insn) {
            this.insn = insn;
        }

        @Override // org.spongepowered.asm.mixin.struct.MemberRef
        public boolean isField() {
            return true;
        }

        @Override // org.spongepowered.asm.mixin.struct.MemberRef
        public int getOpcode() {
            return this.insn.getOpcode();
        }

        @Override // org.spongepowered.asm.mixin.struct.MemberRef
        public void setOpcode(int opcode) {
            if ((opcode & 183) == 0) {
                throw new IllegalArgumentException("Invalid opcode for field instruction: 0x" + Integer.toHexString(opcode));
            }
            this.insn.setOpcode(opcode);
        }

        @Override // org.spongepowered.asm.mixin.struct.MemberRef
        public String getOwner() {
            return this.insn.owner;
        }

        @Override // org.spongepowered.asm.mixin.struct.MemberRef
        public void setOwner(String owner) {
            this.insn.owner = owner;
        }

        @Override // org.spongepowered.asm.mixin.struct.MemberRef
        public String getName() {
            return this.insn.name;
        }

        @Override // org.spongepowered.asm.mixin.struct.MemberRef
        public void setName(String name) {
            this.insn.name = name;
        }

        @Override // org.spongepowered.asm.mixin.struct.MemberRef
        public String getDesc() {
            return this.insn.desc;
        }

        @Override // org.spongepowered.asm.mixin.struct.MemberRef
        public void setDesc(String desc) {
            this.insn.desc = desc;
        }
    }

    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/struct/MemberRef$Handle.class */
    public static final class Handle extends MemberRef {
        private org.spongepowered.asm.lib.Handle handle;

        public Handle(org.spongepowered.asm.lib.Handle handle) {
            this.handle = handle;
        }

        public org.spongepowered.asm.lib.Handle getMethodHandle() {
            return this.handle;
        }

        @Override // org.spongepowered.asm.mixin.struct.MemberRef
        public boolean isField() {
            switch (this.handle.getTag()) {
                case 1:
                case 2:
                case 3:
                case 4:
                    return true;
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    return false;
                default:
                    throw new MixinTransformerError("Invalid tag " + this.handle.getTag() + " for method handle " + this.handle + ".");
            }
        }

        @Override // org.spongepowered.asm.mixin.struct.MemberRef
        public int getOpcode() {
            int opcode = MemberRef.opcodeFromTag(this.handle.getTag());
            if (opcode == 0) {
                throw new MixinTransformerError("Invalid tag " + this.handle.getTag() + " for method handle " + this.handle + ".");
            }
            return opcode;
        }

        @Override // org.spongepowered.asm.mixin.struct.MemberRef
        public void setOpcode(int opcode) {
            int tag = MemberRef.tagFromOpcode(opcode);
            if (tag == 0) {
                throw new MixinTransformerError("Invalid opcode " + Bytecode.getOpcodeName(opcode) + " for method handle " + this.handle + ".");
            }
            boolean itf = tag == 9;
            this.handle = new org.spongepowered.asm.lib.Handle(tag, this.handle.getOwner(), this.handle.getName(), this.handle.getDesc(), itf);
        }

        @Override // org.spongepowered.asm.mixin.struct.MemberRef
        public String getOwner() {
            return this.handle.getOwner();
        }

        @Override // org.spongepowered.asm.mixin.struct.MemberRef
        public void setOwner(String owner) {
            boolean itf = this.handle.getTag() == 9;
            this.handle = new org.spongepowered.asm.lib.Handle(this.handle.getTag(), owner, this.handle.getName(), this.handle.getDesc(), itf);
        }

        @Override // org.spongepowered.asm.mixin.struct.MemberRef
        public String getName() {
            return this.handle.getName();
        }

        @Override // org.spongepowered.asm.mixin.struct.MemberRef
        public void setName(String name) {
            boolean itf = this.handle.getTag() == 9;
            this.handle = new org.spongepowered.asm.lib.Handle(this.handle.getTag(), this.handle.getOwner(), name, this.handle.getDesc(), itf);
        }

        @Override // org.spongepowered.asm.mixin.struct.MemberRef
        public String getDesc() {
            return this.handle.getDesc();
        }

        @Override // org.spongepowered.asm.mixin.struct.MemberRef
        public void setDesc(String desc) {
            boolean itf = this.handle.getTag() == 9;
            this.handle = new org.spongepowered.asm.lib.Handle(this.handle.getTag(), this.handle.getOwner(), this.handle.getName(), desc, itf);
        }
    }

    public String toString() {
        String name = Bytecode.getOpcodeName(getOpcode());
        Object[] objArr = new Object[5];
        objArr[0] = name;
        objArr[1] = getOwner();
        objArr[2] = getName();
        objArr[3] = isField() ? CallSiteDescriptor.TOKEN_DELIMITER : "";
        objArr[4] = getDesc();
        return String.format("%s for %s.%s%s%s", objArr);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof MemberRef)) {
            return false;
        }
        MemberRef other = (MemberRef) obj;
        return getOpcode() == other.getOpcode() && getOwner().equals(other.getOwner()) && getName().equals(other.getName()) && getDesc().equals(other.getDesc());
    }

    public int hashCode() {
        return toString().hashCode();
    }

    static int opcodeFromTag(int tag) {
        if (tag < 0 || tag >= H_OPCODES.length) {
            return 0;
        }
        return H_OPCODES[tag];
    }

    static int tagFromOpcode(int opcode) {
        for (int tag = 1; tag < H_OPCODES.length; tag++) {
            if (H_OPCODES[tag] == opcode) {
                return tag;
            }
        }
        return 0;
    }
}
