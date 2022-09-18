package org.spongepowered.asm.mixin.transformer;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.struct.MemberRef;
import org.spongepowered.asm.mixin.transformer.ClassInfo;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/transformer/ClassContext.class */
public abstract class ClassContext {
    private final Set<ClassInfo.Method> upgradedMethods = new HashSet();

    public abstract String getClassRef();

    abstract ClassNode getClassNode();

    abstract ClassInfo getClassInfo();

    public void addUpgradedMethod(MethodNode method) {
        ClassInfo.Method md = getClassInfo().findMethod(method);
        if (md == null) {
            throw new IllegalStateException("Meta method for " + method.name + " not located in " + this);
        }
        this.upgradedMethods.add(md);
    }

    public void upgradeMethods() {
        for (MethodNode method : getClassNode().methods) {
            upgradeMethod(method);
        }
    }

    private void upgradeMethod(MethodNode method) {
        Iterator<AbstractInsnNode> iter = method.instructions.iterator();
        while (iter.hasNext()) {
            AbstractInsnNode insn = iter.next();
            if (insn instanceof MethodInsnNode) {
                MemberRef methodRef = new MemberRef.Method((MethodInsnNode) insn);
                if (methodRef.getOwner().equals(getClassRef())) {
                    ClassInfo.Method md = getClassInfo().findMethod(methodRef.getName(), methodRef.getDesc(), 10);
                    upgradeMethodRef(method, methodRef, md);
                }
            }
        }
    }

    public void upgradeMethodRef(MethodNode containingMethod, MemberRef methodRef, ClassInfo.Method method) {
        if (methodRef.getOpcode() == 183 && this.upgradedMethods.contains(method)) {
            methodRef.setOpcode(182);
        }
    }
}
