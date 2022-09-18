package org.spongepowered.asm.lib;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/CurrentFrame.class */
class CurrentFrame extends Frame {
    @Override // org.spongepowered.asm.lib.Frame
    public void execute(int opcode, int arg, ClassWriter cw, Item item) {
        super.execute(opcode, arg, cw, item);
        Frame successor = new Frame();
        merge(cw, successor, 0);
        set(successor);
        this.owner.inputStackTop = 0;
    }
}
