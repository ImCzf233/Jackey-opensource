package org.spongepowered.asm.mixin.injection.struct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.util.Bytecode;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/injection/struct/InjectionNodes.class */
public class InjectionNodes extends ArrayList<InjectionNode> {
    private static final long serialVersionUID = 1;

    /* loaded from: Jackey Client b2.jar:org/spongepowered/asm/mixin/injection/struct/InjectionNodes$InjectionNode.class */
    public static class InjectionNode implements Comparable<InjectionNode> {
        private static int nextId = 0;

        /* renamed from: id */
        private final int f439id;
        private final AbstractInsnNode originalTarget;
        private AbstractInsnNode currentTarget;
        private Map<String, Object> decorations;

        public InjectionNode(AbstractInsnNode node) {
            this.originalTarget = node;
            this.currentTarget = node;
            int i = nextId;
            nextId = i + 1;
            this.f439id = i;
        }

        public int getId() {
            return this.f439id;
        }

        public AbstractInsnNode getOriginalTarget() {
            return this.originalTarget;
        }

        public AbstractInsnNode getCurrentTarget() {
            return this.currentTarget;
        }

        public InjectionNode replace(AbstractInsnNode target) {
            this.currentTarget = target;
            return this;
        }

        public InjectionNode remove() {
            this.currentTarget = null;
            return this;
        }

        public boolean matches(AbstractInsnNode node) {
            return this.originalTarget == node || this.currentTarget == node;
        }

        public boolean isReplaced() {
            return this.originalTarget != this.currentTarget;
        }

        public boolean isRemoved() {
            return this.currentTarget == null;
        }

        public <V> InjectionNode decorate(String key, V value) {
            if (this.decorations == null) {
                this.decorations = new HashMap();
            }
            this.decorations.put(key, value);
            return this;
        }

        public boolean hasDecoration(String key) {
            return (this.decorations == null || this.decorations.get(key) == null) ? false : true;
        }

        public <V> V getDecoration(String key) {
            if (this.decorations == null) {
                return null;
            }
            return (V) this.decorations.get(key);
        }

        public int compareTo(InjectionNode other) {
            if (other == null) {
                return Integer.MAX_VALUE;
            }
            return hashCode() - other.hashCode();
        }

        public String toString() {
            return String.format("InjectionNode[%s]", Bytecode.describeNode(this.currentTarget).replaceAll("\\s+", " "));
        }
    }

    public InjectionNode add(AbstractInsnNode node) {
        InjectionNode injectionNode = get(node);
        if (injectionNode == null) {
            injectionNode = new InjectionNode(node);
            add((InjectionNodes) injectionNode);
        }
        return injectionNode;
    }

    public InjectionNode get(AbstractInsnNode node) {
        Iterator<InjectionNode> it = iterator();
        while (it.hasNext()) {
            InjectionNode injectionNode = it.next();
            if (injectionNode.matches(node)) {
                return injectionNode;
            }
        }
        return null;
    }

    public boolean contains(AbstractInsnNode node) {
        return get(node) != null;
    }

    public void replace(AbstractInsnNode oldNode, AbstractInsnNode newNode) {
        InjectionNode injectionNode = get(oldNode);
        if (injectionNode != null) {
            injectionNode.replace(newNode);
        }
    }

    public void remove(AbstractInsnNode node) {
        InjectionNode injectionNode = get(node);
        if (injectionNode != null) {
            injectionNode.remove();
        }
    }
}
