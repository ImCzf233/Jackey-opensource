package org.spongepowered.asm.lib.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.MethodVisitor;

/* loaded from: Jackey Client b2.jar:org/spongepowered/asm/lib/tree/LookupSwitchInsnNode.class */
public class LookupSwitchInsnNode extends AbstractInsnNode {
    public LabelNode dflt;
    public List<Integer> keys;
    public List<LabelNode> labels;

    public LookupSwitchInsnNode(LabelNode dflt, int[] keys, LabelNode[] labels) {
        super(171);
        this.dflt = dflt;
        this.keys = new ArrayList(keys == null ? 0 : keys.length);
        this.labels = new ArrayList(labels == null ? 0 : labels.length);
        if (keys != null) {
            for (int i : keys) {
                this.keys.add(Integer.valueOf(i));
            }
        }
        if (labels != null) {
            this.labels.addAll(Arrays.asList(labels));
        }
    }

    @Override // org.spongepowered.asm.lib.tree.AbstractInsnNode
    public int getType() {
        return 12;
    }

    @Override // org.spongepowered.asm.lib.tree.AbstractInsnNode
    public void accept(MethodVisitor mv) {
        int[] keys = new int[this.keys.size()];
        for (int i = 0; i < keys.length; i++) {
            keys[i] = this.keys.get(i).intValue();
        }
        Label[] labels = new Label[this.labels.size()];
        for (int i2 = 0; i2 < labels.length; i2++) {
            labels[i2] = this.labels.get(i2).getLabel();
        }
        mv.visitLookupSwitchInsn(this.dflt.getLabel(), keys, labels);
        acceptAnnotations(mv);
    }

    @Override // org.spongepowered.asm.lib.tree.AbstractInsnNode
    public AbstractInsnNode clone(Map<LabelNode, LabelNode> labels) {
        LookupSwitchInsnNode clone = new LookupSwitchInsnNode(clone(this.dflt, labels), null, clone(this.labels, labels));
        clone.keys.addAll(this.keys);
        return clone.cloneAnnotations(this);
    }
}
