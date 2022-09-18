package org.spongepowered.asm.mixin.injection.code;

import java.util.ListIterator;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/injection/code/ReadOnlyInsnList.class */
public class ReadOnlyInsnList extends InsnList {
    private InsnList insnList;

    public ReadOnlyInsnList(InsnList insns) {
        this.insnList = insns;
    }

    public void dispose() {
        this.insnList = null;
    }

    @Override // org.spongepowered.asm.lib.tree.InsnList
    public final void set(AbstractInsnNode location, AbstractInsnNode insn) {
        throw new UnsupportedOperationException();
    }

    @Override // org.spongepowered.asm.lib.tree.InsnList
    public final void add(AbstractInsnNode insn) {
        throw new UnsupportedOperationException();
    }

    @Override // org.spongepowered.asm.lib.tree.InsnList
    public final void add(InsnList insns) {
        throw new UnsupportedOperationException();
    }

    @Override // org.spongepowered.asm.lib.tree.InsnList
    public final void insert(AbstractInsnNode insn) {
        throw new UnsupportedOperationException();
    }

    @Override // org.spongepowered.asm.lib.tree.InsnList
    public final void insert(InsnList insns) {
        throw new UnsupportedOperationException();
    }

    @Override // org.spongepowered.asm.lib.tree.InsnList
    public final void insert(AbstractInsnNode location, AbstractInsnNode insn) {
        throw new UnsupportedOperationException();
    }

    @Override // org.spongepowered.asm.lib.tree.InsnList
    public final void insert(AbstractInsnNode location, InsnList insns) {
        throw new UnsupportedOperationException();
    }

    @Override // org.spongepowered.asm.lib.tree.InsnList
    public final void insertBefore(AbstractInsnNode location, AbstractInsnNode insn) {
        throw new UnsupportedOperationException();
    }

    @Override // org.spongepowered.asm.lib.tree.InsnList
    public final void insertBefore(AbstractInsnNode location, InsnList insns) {
        throw new UnsupportedOperationException();
    }

    @Override // org.spongepowered.asm.lib.tree.InsnList
    public final void remove(AbstractInsnNode insn) {
        throw new UnsupportedOperationException();
    }

    @Override // org.spongepowered.asm.lib.tree.InsnList
    public AbstractInsnNode[] toArray() {
        return this.insnList.toArray();
    }

    @Override // org.spongepowered.asm.lib.tree.InsnList
    public int size() {
        return this.insnList.size();
    }

    @Override // org.spongepowered.asm.lib.tree.InsnList
    public AbstractInsnNode getFirst() {
        return this.insnList.getFirst();
    }

    @Override // org.spongepowered.asm.lib.tree.InsnList
    public AbstractInsnNode getLast() {
        return this.insnList.getLast();
    }

    @Override // org.spongepowered.asm.lib.tree.InsnList
    public AbstractInsnNode get(int index) {
        return this.insnList.get(index);
    }

    @Override // org.spongepowered.asm.lib.tree.InsnList
    public boolean contains(AbstractInsnNode insn) {
        return this.insnList.contains(insn);
    }

    @Override // org.spongepowered.asm.lib.tree.InsnList
    public int indexOf(AbstractInsnNode insn) {
        return this.insnList.indexOf(insn);
    }

    @Override // org.spongepowered.asm.lib.tree.InsnList
    public ListIterator<AbstractInsnNode> iterator() {
        return this.insnList.iterator();
    }

    @Override // org.spongepowered.asm.lib.tree.InsnList
    public ListIterator<AbstractInsnNode> iterator(int index) {
        return this.insnList.iterator(index);
    }

    @Override // org.spongepowered.asm.lib.tree.InsnList
    public final void resetLabels() {
        this.insnList.resetLabels();
    }
}
